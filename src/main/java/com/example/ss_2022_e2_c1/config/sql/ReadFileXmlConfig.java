package com.example.ss_2022_e2_c1.config.sql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class ReadFileXmlConfig {

    @Bean
    public Map<String, SQLGetter> sqlGetters() throws IOException {
        Resource[] resources = getResources();
        return Arrays.stream(resources)
                .map(this::generateSQLGetterSimple)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private AbstractMap.SimpleEntry<String, SQLGetter> generateSQLGetterSimple(Resource resource) {
        try {
            File sqlXmlFile = resource.getFile();
            String fileName = sqlXmlFile.getName().replace(".xml", "");
            String path = sqlXmlFile.getAbsolutePath();
            SQLGetter sqlGetter = new SQLGetter(path);
            return new AbstractMap.SimpleEntry<>(fileName, sqlGetter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Resource[] getResources() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("classpath:sql/**/*.xml");
    }
}
