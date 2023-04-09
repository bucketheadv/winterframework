package org.winterframework.ctrip.apollo.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/3/8 2:16 下午
 */
@Getter
@Setter
public class ApolloProperties {
    private boolean enableDefaultListener = true;

    private String meta;
}
