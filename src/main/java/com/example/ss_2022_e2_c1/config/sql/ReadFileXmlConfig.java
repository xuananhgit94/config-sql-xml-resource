package com.example.ss_2022_e2_c1.config.sql;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Configuration
public class ReadFileXmlConfig {

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return (b) -> {
            try {
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources("classpath*:sql/**/*.xml");
                Stream.of(resources).forEach((r)->{
                    try {
                        String filename = r.getFilename();
                        String beanName = Objects.requireNonNull(filename).substring(0, filename.lastIndexOf('.'));
                        Objects.requireNonNull(b).registerSingleton(beanName, new SQLGetter(r.getFile().getAbsolutePath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
