## UI设计

### UI结构

- 文档页面 类似于 @知识管理页面
    - Project选择下拉列表（顶部）
    - 倒入按钮（顶部）
    - 文档tag区域（左侧）
    - 文档列表区域（中间）
    - 文件详情区域（右侧）

### UI交互

- 用户选择Project，系统会显示该Project的Tags、以及文档列表
- 用户点击倒入按钮，系统会扫描指定目录及下级目录下的所有文件，根据文件名后缀将文件内容导入数据库中相应字段，
- 用户可以在文档页面查看当前Project的倒入的文档列表和文档的详情

### 需要的后端API

- 从Project对应的目录下导入文档：扫描Project目录及其子目录下的所有文件，根据文件名后缀将文件内容导入数据库中相应字段
- 获取Project下的文档列表
- 获取文档详情