package org.winterframework.rocketmq.filter;

import com.google.common.collect.Sets;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;

import java.util.Set;

/**
 * @author qinglinl
 * Created on 2022/3/4 11:04 下午
 */
public class SkipFilter implements AutoConfigurationImportFilter {
    private static final Set<String> SKIP_SET = Sets.newHashSet(
            "org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration"
    );

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < autoConfigurationClasses.length; i++) {
            matches[i] = !SKIP_SET.contains(autoConfigurationClasses[i]);
        }
        return matches;
    }
}