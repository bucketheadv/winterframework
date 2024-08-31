package org.winterframework.core.startup;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.winterframework.core.props.AutoloadProperties;
import org.winterframework.core.support.ApplicationContextHolder;

import java.io.IOException;

/**
 * @author qinglin.liu
 * created at 2024/3/7 13:38
 */
@Slf4j
public class StartUpLoadFilter extends TypeExcludeFilter {

    private AutoloadProperties autoloadProperties;

    public StartUpLoadFilter() {
        try {
            Environment env = ApplicationContextHolder.getApplicationContext().getEnvironment();
            autoloadProperties = Binder.get(env).bind(AutoloadProperties.AUTOLOAD_EXCLUDE, AutoloadProperties.class).get();
        } catch (Exception e) {
            log.warn("{} 绑定不成功, {}", AutoloadProperties.AUTOLOAD_EXCLUDE, e.getMessage());
        }
    }

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        String className = metadataReader.getClassMetadata().getClassName();
        if (autoloadProperties == null) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(autoloadProperties.getIncludes())) {
            for (String whitelist : autoloadProperties.getIncludes()) {
                if (className.matches(whitelist)) {
                    return false;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(autoloadProperties.getExcludes())) {
            for (String pattern : autoloadProperties.getExcludes()) {
                if (className.matches(pattern)) {
                    return true;
                }
            }
        }
        return false;
    }
}
