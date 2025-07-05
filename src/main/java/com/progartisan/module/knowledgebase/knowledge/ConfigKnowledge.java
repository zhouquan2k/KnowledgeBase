package com.progartisan.module.knowledgebase.knowledge;

import io.leanddd.component.data.RepositoryImpl;
import io.leanddd.component.framework.Repository;
import com.progartisan.module.knowledgebase.example.model.Example;
import com.progartisan.module.knowledgebase.knowledge.model.Document;
import com.progartisan.module.knowledgebase.knowledge.model.Knowledge;
import com.progartisan.module.knowledgebase.knowledge.model.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.repository.CrudRepository;

@Configuration
@DependsOn("Init")
public class ConfigKnowledge {
    @Bean
    public Repository<Knowledge> myKnowledgeRepository(KnowledgeRepository springRepo) {
        return new RepositoryImpl<Knowledge>(Knowledge.class, springRepo);
    }

    @Bean
    public Repository<Tag> myTagRepository(TagRepository springRepo) {
        return new RepositoryImpl<Tag>(Tag.class, springRepo);
    }

    @Bean
    public Repository<Document> myDocumentRepository(DocumentRepository springRepo) {
        return new RepositoryImpl<Document>(Document.class, springRepo);
    }
}

interface DocumentRepository extends CrudRepository<Document, String> {
}

interface TagRepository extends CrudRepository<Tag, String> {
}

interface KnowledgeRepository extends CrudRepository<Knowledge, String> {
}
