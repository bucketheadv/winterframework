package org.winterframework.elasticsearch.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.winterframework.elasticsearch.constants.Const;

import java.util.List;
import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/3/4 1:19 下午
 */
@Data
@ConfigurationProperties(prefix = Const.configPrefix)
public class ElasticsearchConfig {
    /**
     * 是否启用winter elasticsearch
     */
    private boolean enabled = false;

    /**
     * 配置映射
     */
    private Map<String, ElasticsearchProperties> template;

    @Data
    public static class ElasticsearchProperties {
        /**
         * url地址
         */
        private List<String> uris;
    }
}
