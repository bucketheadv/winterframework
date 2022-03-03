package org.winterframework.mongodb.configuration;

import com.mongodb.MongoClientSettings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.lang.NonNull;
import org.winterframework.mongodb.properties.MongoConfig;

/**
 * @author sven
 * Created on 2022/3/2 10:56 下午
 */
@Configuration
@ConditionalOnProperty(prefix = "winter.data.mongodb", value = "enabled", havingValue = "true")
public class WinterMongodbAutoConfiguration implements EnvironmentAware {
    private Environment environment;
    @Bean
    public MongoConfig mongoConfig() {
        return Binder.get(environment).bind("winter.data.mongodb", MongoConfig.class).get();
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder().build();
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoDefinitionRegistry mongoDefinitionRegistry(MongoConfig mongoConfig, MongoClientSettings mongoClientSettings, MongoCustomConversions mongoCustomConversions) {
        return new MongoDefinitionRegistry(mongoConfig, mongoClientSettings, mongoCustomConversions);
    }
}
