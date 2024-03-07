package org.winterframework.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.winterframework.core.filter.StartUpLoadFilter;

@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = StartUpLoadFilter.class)
})
public class WinterAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WinterAppApplication.class, args);
    }

}
