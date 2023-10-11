package org.winterframework.mongodb.configuration;

import com.google.common.collect.Lists;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.StandardMongoClientSettingsBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoDatabaseFactorySupport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.lang.NonNull;
import org.winterframework.mongodb.core.DefaultMappingMongoConverter;
import org.winterframework.mongodb.core.DefaultMongoMappingContext;
import org.winterframework.mongodb.properties.MongoConfig;
import org.winterframework.mongodb.properties.MongoProps;

import java.util.Map;

/**
 * @author sven
 * Created on 2022/3/2 11:13 下午
 */
@Slf4j
public class MongoDefinitionRegistry implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, ApplicationContextAware {
    private final MongoClientSettings mongoClientSettings;

    private final MongoCustomConversions mongoCustomConversions;

    private final MongoConfig mongoConfig;

    private ApplicationContext applicationContext;

    public MongoDefinitionRegistry(MongoConfig mongoConfig, MongoClientSettings mongoClientSettings, MongoCustomConversions mongoCustomConversions) {
        this.mongoConfig = mongoConfig;
        this.mongoClientSettings = mongoClientSettings;
        this.mongoCustomConversions = mongoCustomConversions;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Map<String, MongoProps> configMap = mongoConfig.getTemplate();
        for (String name : configMap.keySet()) {
            MongoProps mongoProperties = configMap.get(name);

            ConnectionString connectionString = new ConnectionString(mongoProperties.getConnectionString());

            MongoProperties.Ssl ssl = new MongoProperties.Ssl();
            ssl.setBundle(mongoProperties.getSslBundle());
            ssl.setEnabled(mongoProperties.getSslEnabled());

            StandardMongoClientSettingsBuilderCustomizer builderCustomizer = new StandardMongoClientSettingsBuilderCustomizer(
                    connectionString, mongoProperties.getUuidRepresentation(), ssl, mongoProperties.getSslBundles());
            MongoClient mongoClient = new MongoClientFactory(Lists.newArrayList(builderCustomizer)).createMongoClient(mongoClientSettings);

            BeanDefinition mongoDatabaseFactorySupportBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(SimpleMongoClientDatabaseFactory.class)
                    .addConstructorArgValue(mongoClient)
                    .addConstructorArgValue(connectionString.getDatabase())
                    .getBeanDefinition();
            String mongoDatabaseFactorySupportKey = name + MongoDatabaseFactorySupport.class.getSimpleName();
            beanDefinitionRegistry.registerBeanDefinition(mongoDatabaseFactorySupportKey, mongoDatabaseFactorySupportBeanDefinition);

            BeanDefinition mongoMappingContextBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DefaultMongoMappingContext.class)
                    .addConstructorArgValue(applicationContext)
                    .addConstructorArgValue(mongoProperties)
                    .addConstructorArgValue(mongoCustomConversions)
                    .getBeanDefinition();
            String mongoMappingContextKey = name + MongoMappingContext.class.getSimpleName();
            beanDefinitionRegistry.registerBeanDefinition(mongoMappingContextKey, mongoMappingContextBeanDefinition);

            BeanDefinition mongoConverterBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DefaultMappingMongoConverter.class)
                    .addConstructorArgReference(mongoDatabaseFactorySupportKey)
                    .addConstructorArgReference(mongoMappingContextKey)
                    .addConstructorArgValue(mongoCustomConversions)
                    .getBeanDefinition();
            String mongoConverterKey = name + MongoConverter.class.getSimpleName();
            beanDefinitionRegistry.registerBeanDefinition(mongoConverterKey, mongoConverterBeanDefinition);

            BeanDefinition mongoTemplateBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(MongoTemplate.class)
                    .addConstructorArgReference(mongoDatabaseFactorySupportKey)
                    .addConstructorArgReference(mongoConverterKey)
                    .getBeanDefinition();
            String mongoTemplateKey = name + MongoTemplate.class.getSimpleName();
            beanDefinitionRegistry.registerBeanDefinition(mongoTemplateKey, mongoTemplateBeanDefinition);
            log.info("注册MongoTemplate: [{}] 成功.", mongoTemplateKey);
        }
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
