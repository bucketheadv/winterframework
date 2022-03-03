package org.winterframework.mongodb.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;

import java.util.Map;

/**
 * @author sven
 * Created on 2022/3/2 11:10 下午
 */
@Data
public class MongoConfig {
    private Map<String, MongoProperties> template;
}
