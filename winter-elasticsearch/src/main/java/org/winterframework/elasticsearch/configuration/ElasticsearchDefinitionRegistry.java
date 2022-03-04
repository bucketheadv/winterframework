package org.winterframework.elasticsearch.configuration;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.winterframework.elasticsearch.properties.ElasticsearchConfig;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qinglinl
 * Created on 2022/3/4 1:05 下午
 */
@Slf4j
public class ElasticsearchDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {
    private final ElasticsearchConfig elasticsearchConfig;
    private final ElasticsearchConverter elasticsearchConverter;

    public ElasticsearchDefinitionRegistry(ElasticsearchConfig elasticsearchConfig, ElasticsearchConverter elasticsearchConverter) {
        this.elasticsearchConfig = elasticsearchConfig;
        this.elasticsearchConverter = elasticsearchConverter;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Map<String, ElasticsearchConfig.ElasticsearchProperties> configMap = elasticsearchConfig.getTemplate();
        for (String name : configMap.keySet()) {
            ElasticsearchConfig.ElasticsearchProperties properties = configMap.get(name);
            List<HttpHost> hosts = properties.getUris().stream().map(HttpHost::create).collect(Collectors.toList());
            RestClientBuilder restClientBuilder = RestClient.builder(hosts.toArray(new HttpHost[]{}));

            BeanDefinition restHighLevelClientBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(RestHighLevelClient.class)
                    .addConstructorArgValue(restClientBuilder)
                    .getBeanDefinition();
            String restHighLevelClientKey = name + "RestHighLevelClient";
            beanDefinitionRegistry.registerBeanDefinition(restHighLevelClientKey, restHighLevelClientBeanDefinition);

            BeanDefinition elasticsearchRestTemplateBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(ElasticsearchRestTemplate.class)
                    .addConstructorArgReference(restHighLevelClientKey)
                    .addConstructorArgValue(elasticsearchConverter)
                    .getBeanDefinition();
            String elasticsearchRestTemplateKey = name + "ElasticsearchRestTemplate";
            beanDefinitionRegistry.registerBeanDefinition(elasticsearchRestTemplateKey, elasticsearchRestTemplateBeanDefinition);
            log.info("注册ElasticsearchRestTemplate: [{}] 成功", elasticsearchRestTemplateKey);
        }
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
