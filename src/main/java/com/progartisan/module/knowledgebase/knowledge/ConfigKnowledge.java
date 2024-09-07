package com.progartisan.module.knowledgebase.knowledge;

import com.progartisan.component.data.impl.RepositoryImpl2;
import com.progartisan.component.framework.Repository;
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
        return new RepositoryImpl2<Knowledge>(Knowledge.class, springRepo);
    }

    @Bean
    public Repository<Tag> myTagRepository(TagRepository springRepo) {
        return new RepositoryImpl2<Tag>(Tag.class, springRepo);
    }

    @Bean
    public Repository<Document> myDocumentRepository(DocumentRepository springRepo) {
        return new RepositoryImpl2<Document>(Document.class, springRepo);
    }
}

interface DocumentRepository extends CrudRepository<Document, String> {
}

interface TagRepository extends CrudRepository<Tag, String> {
}

interface KnowledgeRepository extends CrudRepository<Knowledge, String> {
}
