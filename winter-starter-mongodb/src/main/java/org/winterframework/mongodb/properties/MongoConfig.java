package org.winterframework.mongodb.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.winterframework.mongodb.constants.Const;

import java.util.Map;

/**
 * @author sven
 * Created on 2022/3/2 11:10 下午
 */
@Getter
@Setter
@ConfigurationProperties(prefix = Const.configPrefix)
public class MongoConfig {
    /**
     * 是否启用winter mongodb
     */
    private boolean enabled = false;

    /**
     * 配置映射
     */
    private Map<String, MongoProperties> template;
}
