package com.example.ss_2022_e2_c1.config.sql;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Configuration
public class ReadFileXmlConfig {

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return (b) -> {
            try {
                Stream.of(new PathMatchingResourcePatternResolver().getResources("classpath*:sql/**/*.xml"))
                        .forEach(resource -> {
                            try (InputStream is = resource.getInputStream()) {
                                String filename = resource.getFilename();
                                String beanName = Objects.requireNonNull(filename).substring(0, filename.lastIndexOf('.'));
                                b.registerSingleton(beanName, new SQLGetter(new String(is.readAllBytes())));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
