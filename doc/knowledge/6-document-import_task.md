## document-import 文档倒入

### 需求描述

将doc目录下(及其子目录)的所有文档倒入(import)。
系统文件名不同后缀的所有文件作为一个文档的不同部分，倒入到数据库中。
根据文件名后缀有以下几种类型的文档：*_task.md, *_req.md, *_design_ui.md, *_design.md, *_design_tasks.md
分别倒入对应的字段rawInput, requirement, uiDesign, design, taskDesign
支持多次import以获取最新的文档内容

### 业务对象

- Document: documentID，documentName, documentPath, rawInput, requirement, uiDesign, design, taskDesign
- document-tag: documentID, tagID
- Project: projectName, projectDesc, projectPath


