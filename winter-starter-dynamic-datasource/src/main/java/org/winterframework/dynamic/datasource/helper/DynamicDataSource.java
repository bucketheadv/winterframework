package org.winterframework.dynamic.datasource.helper;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author liuql92
 * @version 1.0
 * @date 2023/5/15 6:47 PM
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSource() + "DataSource";
    }
}
