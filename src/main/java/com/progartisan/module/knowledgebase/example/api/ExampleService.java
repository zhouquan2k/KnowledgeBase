package com.progartisan.module.knowledgebase.example.api;

import com.progartisan.module.knowledgebase.example.model.Assistant;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import com.progartisan.module.knowledgebase.example.model.Example;

@RequestMapping("/api/examples")
public interface ExampleService {

    @GetMapping
    List<Example> getExamples(@RequestParam Map<String, String> params);

    @GetMapping("/{exampleId}")
    Example getExample(@PathVariable String exampleId);

    @PostMapping
    Example saveExample(@RequestBody Example example);

    @DeleteMapping("/{exampleId}")
    void deleteExample(@PathVariable String exampleId);

    @GetMapping("/assistants")
    List<Assistant> getAssistants();
}
