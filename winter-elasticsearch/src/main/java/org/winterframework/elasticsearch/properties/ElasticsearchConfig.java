package org.winterframework.elasticsearch.properties;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/3/4 1:19 下午
 */
@Data
public class ElasticsearchConfig {
    private Map<String, ElasticsearchProperties> template;

    @Data
    public static class ElasticsearchProperties {
        private List<String> uris;
    }
}
