package org.winterframework.mongodb.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.UuidRepresentation;
import org.springframework.boot.ssl.SslBundles;

/**
 * @author qinglin.liu
 * @create 2023/10/11 17:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongoProps {
    /**
     * 连接地址
     */
    private String connectionString;

    /**
     * UUID生成策略
     */
    private UuidRepresentation uuidRepresentation;

    /**
     * 是否自动创建索引
     */
    private boolean autoIndexCreation;

    /**
     * 字段名策略
     */
    private Class<?> fieldNamingStrategy;

    /**
     * 是否使用ssl
     */
    @Builder.Default
    private Boolean sslEnabled = false;

    /**
     * ssl bundle
     */
    private String sslBundle;

    private SslBundles sslBundles;
}
