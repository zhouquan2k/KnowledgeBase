package com.progartisan.module.knowledgebase.example;

import com.progartisan.component.data.impl.RepositoryImpl2;
import com.progartisan.component.framework.Repository;
import com.progartisan.module.knowledgebase.example.model.Example;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.repository.CrudRepository;

@Configuration
@DependsOn("Init")
public class ConfigExample {
    @Bean
    public Repository<Example> myExampleRepository(ExampleRepository springRepo) {
        return new RepositoryImpl2<Example>(Example.class, springRepo);
    }
}

interface ExampleRepository extends CrudRepository<Example, String> {
}

