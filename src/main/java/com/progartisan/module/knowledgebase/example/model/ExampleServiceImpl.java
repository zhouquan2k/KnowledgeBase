package com.progartisan.module.knowledgebase.example.model;

import io.leanddd.component.common.Util;
import io.leanddd.component.meta.Command;
import io.leanddd.component.meta.Query;
import io.leanddd.component.framework.Repository;
import io.leanddd.component.meta.Service;
import io.leanddd.component.data.helper.CrudServiceImpl;
import com.progartisan.module.knowledgebase.example.api.ExampleService;
import com.progartisan.module.knowledgebase.example.model.Example;
import com.progartisan.module.knowledgebase.example.api.ExampleService;
import com.progartisan.module.knowledgebase.example.infra.ExampleMapper;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Service(value = "Example管理", name = "example")
@Named
@Slf4j
public class ExampleServiceImpl extends CrudServiceImpl<Example> implements ExampleService {

    private final ExampleMapper exampleMapper;

    public ExampleServiceImpl(ExampleMapper exampleMapper, Repository<Example> exampleRepository) {
        super(exampleRepository);
        this.exampleMapper = exampleMapper;
    }

    @Override
    @Query
    public List<Example> getExamples(Map<String, String> params) {
        return exampleMapper.queryExamples(params);
    }

    @Override
    @Query
    public Example getExample(String exampleId) {
        return super.getById(exampleId);
    }

    @Override
    @Command
    public Example saveExample(Example example) {
        if (Util.isEmpty(example.getExampleId())) {
            return super.create(example);
        } else {
            return super.update(example.getExampleId(), example);
        }
    }

    @Override
    @Command
    public void deleteExample(String exampleId) {
        super.delete(exampleId);
    }

    @Override
    @Query
    public List<Assistant> getAssistants() {
        return exampleMapper.queryAssistants();
    }
}
