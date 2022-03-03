package org.winterframework.mongodb.core;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @author sven
 * Created on 2022/3/3 11:03 下午
 */
public class DefaultMongoMappingContext extends MongoMappingContext {
    public DefaultMongoMappingContext(ApplicationContext applicationContext, MongoProperties properties, MongoCustomConversions conversions) throws ClassNotFoundException {
        PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        mapper.from(properties.isAutoIndexCreation()).to(this::setAutoIndexCreation);
        this.setInitialEntitySet((new EntityScanner(applicationContext)).scan(new Class[]{Document.class}));
        Class<?> strategyClass = properties.getFieldNamingStrategy();
        if (strategyClass != null) {
            this.setFieldNamingStrategy((FieldNamingStrategy) BeanUtils.instantiateClass(strategyClass));
        }
        this.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
    }
}
