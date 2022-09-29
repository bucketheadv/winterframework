package org.winterframework.elasticsearch.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.lang.NonNull;
import org.winterframework.elasticsearch.constants.Const;
import org.winterframework.elasticsearch.properties.ElasticsearchConfig;

import java.util.Collections;

/**
 * @author qinglinl
 * Created on 2022/3/4 1:01 下午
 */
@Configuration
@ConditionalOnProperty(prefix = Const.configPrefix, value = "enabled", havingValue = "true")
public class WinterElasticsearchAutoConfiguration implements EnvironmentAware {
    private Environment environment;
    @Bean
    public ElasticsearchConfig elasticsearchConfig() {
        return Binder.get(environment).bind(Const.configPrefix, ElasticsearchConfig.class).get();
    }

    @Bean
    @ConditionalOnMissingBean
    ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(Collections.emptyList());
    }

    @Bean
    @ConditionalOnMissingBean
    SimpleElasticsearchMappingContext mappingContext(ApplicationContext applicationContext, ElasticsearchCustomConversions elasticsearchCustomConversions) throws ClassNotFoundException {
        SimpleElasticsearchMappingContext mappingContext = new SimpleElasticsearchMappingContext();
        mappingContext.setInitialEntitySet((new EntityScanner(applicationContext)).scan(Document.class));
        mappingContext.setSimpleTypeHolder(elasticsearchCustomConversions.getSimpleTypeHolder());
        return mappingContext;
    }

    @Bean
    @ConditionalOnMissingBean
    ElasticsearchConverter elasticsearchConverter(SimpleElasticsearchMappingContext mappingContext, ElasticsearchCustomConversions elasticsearchCustomConversions) {
        MappingElasticsearchConverter converter = new MappingElasticsearchConverter(mappingContext);
        converter.setConversions(elasticsearchCustomConversions);
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean
    public ElasticsearchDefinitionRegistry elasticsearchDefinitionRegistry(ElasticsearchConfig elasticsearchConfig, ElasticsearchConverter elasticsearchConverter) {
        return new ElasticsearchDefinitionRegistry(elasticsearchConfig, elasticsearchConverter);
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
