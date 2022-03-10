package org.winterframework.ctrip.apollo.properties;

import lombok.Data;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:16 下午
 */
@Data
public class ApolloProperties {
    private boolean enableDefaultListener = true;

    private String meta;
}
