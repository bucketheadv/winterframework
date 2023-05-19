package org.winterframework.dynamic.datasource.configuration;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.NonNull;
import org.winterframework.core.tool.StringTool;
import org.winterframework.dynamic.datasource.helper.DynamicDataSource;
import org.winterframework.dynamic.datasource.properties.WinterDataSourceProperties;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author liuql92
 * @version 1.0
 * @date 2023/5/15 6:31 PM
 */
@Slf4j
@AllArgsConstructor
public class WinterDynamicDefinitionRegistry implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private WinterDataSourceProperties winterDataSourceProperties;

    private static final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private static ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        String defaultDataSource = "";
        String dynamicDataSourceName = "dynamicDataSource";
        for (String dataSourceName : winterDataSourceProperties.getDynamic().keySet()) {
            WinterDataSourceProperties.MySqlProperties dataSourceProperties = winterDataSourceProperties.getDynamic().get(dataSourceName);
            boolean isPrimary = dataSourceName.equals(winterDataSourceProperties.getDefaultDataSource());

            BeanDefinition dataSourceBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(HikariDataSource.class)
                    .addPropertyValue("driverClassName", dataSourceProperties.getDriverClassName())
                    .addPropertyValue("jdbcUrl", dataSourceProperties.getUrl())
                    .addPropertyValue("username", dataSourceProperties.getUsername())
                    .addPropertyValue("password", dataSourceProperties.getPassword())
                    .addPropertyValue("dataSourceJNDI", dataSourceProperties.getJndiName())
                    .addPropertyValue("poolName", dataSourceName + "DataSourcePool")
                    .setPrimary(isPrimary)
                    .getBeanDefinition();

            String dataSourceBeanName = dataSourceName + "DataSource";
            log.info("注册数据源: {}", dataSourceBeanName);
            if (isPrimary) {
                defaultDataSource = dataSourceBeanName;
            }

            registry.registerBeanDefinition(dataSourceBeanName, dataSourceBeanDefinition);

            // SqlSessionFactoryBean的数据源需要设置为dynamicDataSource数据源
            BeanDefinition sqlSessionFactoryBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(SqlSessionFactoryBean.class)
                    .addPropertyValue("mapperLocations", resolveMapperLocations(dataSourceProperties.getMapperLocations()))
                    .addPropertyValue("configLocation", resolveConfigLocation(dataSourceProperties.getConfigLocation()))
                    .addPropertyReference("dataSource", dynamicDataSourceName)
                    .setPrimary(isPrimary)
                    .getBeanDefinition();

            String sqlSessionFactoryBeanName = dataSourceName + "SqlSessionFactoryBean";
            registry.registerBeanDefinition(sqlSessionFactoryBeanName, sqlSessionFactoryBeanDefinition);
            log.info("注册 SqlSessionFactoryBean: {}", sqlSessionFactoryBeanName);

            BeanDefinition platformTransactionManagerBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DataSourceTransactionManager.class)
                    .addConstructorArgReference(dynamicDataSourceName)
                    .setPrimary(isPrimary)
                    .getBeanDefinition();
            String platformTransactionManagerBeanName = dataSourceName + "PlatformTransactionManager";
            registry.registerBeanDefinition(platformTransactionManagerBeanName, platformTransactionManagerBeanDefinition);
            log.info("注册事务管理器: {}", platformTransactionManagerBeanName);
        }

        Map<String, DataSource> dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
        BeanDefinition dynamicDataSourceBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DynamicDataSource.class)
                .addPropertyReference("defaultTargetDataSource", defaultDataSource)
                .addPropertyValue("targetDataSources", dataSourceMap)
                .setPrimary(true)
                .getBeanDefinition();
        registry.registerBeanDefinition(dynamicDataSourceName, dynamicDataSourceBeanDefinition);
        log.info("注册 dynamicDataSource");
    }

    private Resource[] resolveMapperLocations(String... mapperLocations) {
        if (mapperLocations == null) {
            return new Resource[0];
        }
        List<Resource> resources = Lists.newArrayList();
        for (String mapperLocation : mapperLocations) {
            try {
                Resource[] mappers = resourcePatternResolver.getResources(mapperLocation);
                resources.addAll(Arrays.asList(mappers));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return resources.toArray(new Resource[]{});
    }

    private Resource resolveConfigLocation(String configLocation) {
        if (StringTool.isBlank(configLocation)) {
            return null;
        }
        return resourcePatternResolver.getResource(configLocation);
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 不实现
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        WinterDynamicDefinitionRegistry.applicationContext = applicationContext;
    }
}
