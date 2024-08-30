## 设计思路
针对系统管理员管理fine tuning所需的example数据的需求，我们将设计一个后端服务来支持新建、编辑、查看example的详细信息，包括选择assistor，并在分tab页中分别编辑输入文本和输出文本的功能。前端UI将提供相应的界面与后端服务交互，实现用户故事中的场景。

## 模块
project-name: knowledgebase
module-name: example

## API设计

### Endpoints
- GET /api/examples
  - Operation: getExamples 获取example列表
  - Response: List<Example>
- GET /api/examples/{exampleId}
  - Operation: getExample 获取单个example的详情
  - PathParam: exampleId
  - Response: Example
- POST /api/examples
  - Operation: saveExample 保存example
  - BodyParam: Example
  - Response: Example
- DELETE /api/examples/{exampleId}
  - Operation: deleteExample 删除example
  - PathParam: exampleId
  - Response: void
- GET /api/assistants
  - Operation: getAssistants 获取assistant列表
  - Response: List<Assistant>

### Schema
``` yaml
Example:
  type: object
  properties:
    exampleId:
      type: string
    assistantId:
      type: string
    inputText:
      type: string
    outputText:
      type: string
      
Assistant:
    type: object
    properties:
      assistantId:
        type: string
      assistantName:
        type: string
      description:
        type: string
      systemText:
        type: text
```

## Artifacts
| 变更模块 | backend/frontend | 变更文件类型       | 变更文件                          | 需要变更? |
|----------|------------------|--------------|-------------------------------|-----------|
| example  | backend          | api          | api/ExampleService.java       | 是 |
| example  | backend          | domain       | model/Example.java            | 是 |
| example | backend          | domain       | model/Assistant.java          | 是 |
| example  | backend          | mapper       | infra/ExampleMapper.java      | 是 |
| example  | backend          | mapper_xml   | infra/ExampleMapper.xml       | 是 |
| example  | frontend         | api_js       | example_api.js                | 是 |
| example  | frontend         | ui_vue       | example.vue                   | 是 |
| example  | backend          | service_impl | model/ExampleServiceImpl.java | 是 |
