package org.winterframework.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WinterMybatisGeneratorApplication {

    public static void main(String[] args) throws Exception {
        URL url = WinterMybatisGeneratorApplication.class.getClassLoader().getResource("");
        assert url != null;
        String path = url.getPath();
        File configFile = new File(path + "/mybatis-generator-configuration.xml");
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration configuration = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator generator = new MyBatisGenerator(configuration, callback, warnings);
        generator.generate(null);
    }
}
