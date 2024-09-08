# 面向AI的知识库

## 1. 背景和建设目标

知识库是AI系统能够获取有效输出的重要条件（特别是对于需要专用知识的情况下）。本系统用于辅助建设AI系统所需的知识库。

- 知识库的建设：面向人：知识的输入、更新、组织
- 知识库输出到AI：面向AI：转换成AI需要的知识库形式
- 直接向AI提供知识检索：面向程序。节省AI检索的token
- 可用于整理AI fine tuning的example数据

## 2. 成本分析

- 自建方案

主要是信息的输入、组织、转换、检索。提供知识库的管理UI，自建成本不高。

- 已有知识库系统的了解：

已有知识库不是针对用于AI场景。1.共享维护 2.面向人类 3.多媒体。而提供AI的知识库 1.主要是文字、2.不关注共享维护场景
3.主要面向AI。且需要考虑学习成本

## 3. 建设计划

1. 整理AI finetuning example数据（9月赶上fine tuning免费）
   本周末完成，计划3-4天投入使用
2. 开发模式知识库
3. 需求/设计知识库

## 4. 建设方案

-
    1. java + vue:

    - 技术熟悉
    - 可以作为Devartisan的案例（时间紧，不是重点）
-
    2. ~~python + vue~~

## 5. 用户

知识库用户

## 6. 需求分析

### 6.1 场景 finetuning example的整理

名称：example
目标：为实现finetuning，提供example管理

- example-edit 【P0】输入/查看
- example-list 【P0】新建、删除
- example-export【P0】输出成训练格式的文本 P0
- example-tag 【P1】tag分类 可先实现单个的、平面的分类：如按assistor分类

### 6.2 场景 开发模式知识库的整理

目标：可以将开发模式知识低成本（时间、费用）的输出到AI

- 知识的输入 【P0】，知识输入，知识打tag（相当于对tag的创建）
- 知识的组织 【P0】: tag树展示和管理
- 提供查询API：自己实现查询知识不通过AI 【P1】
- 查询时可选的加入:所有/部分父级知识。 【P1】
- 输出成AI所需的PDF/html 【P1】


### 6.3 场景 项目用到的 需求知识库 设计知识库

目标： 积累、管理AI生成的制品，并在后续的开发过程中作为上下文使用。

- 文档的倒入 【P0】
- 文档输出：PDF，或转成html 【P0】
- 文档的组织，复用tag 【P0】 包括文档的查看/编辑，tag编辑

## 7 设计

### 实体

- example：exampleID、assistantID、输入、输出
- assistant：assistantID、system、
- exmaple-tag:  exampleID、tagID [p2]
- tag: tagID, tagName/Path, order,

- knowledge: knowledgeID、title、content
- knowledge-tag: knowledgeID、tagID

- document： documentID，documentName, rawInput, requirement, uiDesign, design, taskDesign
- document-tag: documentID, tagID




