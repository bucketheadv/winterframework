package org.winterframework.mongodb.configuration;

import com.mongodb.MongoClientSettings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.winterframework.mongodb.constants.Const;
import org.winterframework.mongodb.properties.MongoConfig;

/**
 * @author sven
 * Created on 2022/3/2 10:56 下午
 */
@Component
@ConditionalOnProperty(prefix = Const.configPrefix, value = "enabled", havingValue = "true")
public class WinterMongodbAutoConfiguration implements EnvironmentAware {
    private Environment environment;
    @Bean
    public MongoConfig mongoConfig() {
        return Binder.get(environment).bind(Const.configPrefix, MongoConfig.class).get();
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
