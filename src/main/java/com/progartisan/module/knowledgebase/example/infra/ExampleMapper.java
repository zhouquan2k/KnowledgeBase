package com.progartisan.module.knowledgebase.example.infra;

import com.progartisan.component.data.BaseMapper;
import com.progartisan.module.knowledgebase.example.model.Assistant;
import com.progartisan.module.knowledgebase.example.model.Example;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.DependsOn;

import java.util.List;
import java.util.Map;

@Mapper
@DependsOn("Init")
public interface ExampleMapper extends BaseMapper<Example> {

    List<Example> queryExamples(@Param("params") Map<String, String> params);

    List<Assistant> queryAssistants();
}
