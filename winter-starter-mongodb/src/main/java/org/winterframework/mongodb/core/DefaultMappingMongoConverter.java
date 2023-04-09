package org.winterframework.mongodb.core;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @author sven
 * Created on 2022/3/3 10:59 下午
 */
public class DefaultMappingMongoConverter extends MappingMongoConverter {
    public DefaultMappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context, MongoCustomConversions conversions) {
        super(new DefaultDbRefResolver(factory), context);
        super.setCustomConversions(conversions);
    }
}
