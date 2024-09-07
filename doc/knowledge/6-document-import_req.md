## 模块

project-name: knowledgebase
module-name: knowledge

## 用户故事

- Role：知识库管理员
- Action：将doc目录下(及其子目录)的所有文档倒入系统，系统根据文件名后缀将不同部分倒入数据库中相应字段
- Benefit：实现文档的自动化导入和分类，减少手动操作，提高效率
- Priority: P0

## 场景：文档导入

- 系统扫描doc目录及其子目录，识别所有文件
- 系统根据文件名后缀将文件内容导入数据库中相应字段，将导入的文档信息保存到数据库中
    - *_task.md -> rawInput
    - *_req.md -> requirement
    - *_design_ui.md -> uiDesign
    - *_design.md -> design
    - *_design_tasks.md -> taskDesign
- 系统管理员可以在系统中查看已导入的文档

## 业务对象

- Document: documentID，documentName, documentPath, projectName, rawInput, requirement, uiDesign, design, taskDesign
- document-tag: documentID, tagID
- Project: projectName, projectDesc, projectPath

## 开发相关

- 无
