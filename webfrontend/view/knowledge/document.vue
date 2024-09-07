<template>
  <el-container class="container">
    <el-aside :width="leftPanelWidth + 'px'">
      <TagTree @change="onTagTreeSelected" ref="tagTree" @project-change="onProjectChange" />
    </el-aside>
    <Splitter @resize="(newWidth) => leftPanelWidth = newWidth" />
    <el-aside v-if="documentList.length > 1" width="200px">
      <el-menu :default-active="activeDocumentId" @select="fetchDocument">
        <el-menu-item v-for="document in documentList" :key="document.documentId" :index="document.documentId">
          {{ document.documentName }}
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <div class="toolbar right"><el-button type="primary" style="margin-bottom: 5px;" @click="importDocuments">导入文档</el-button></div>
      <el-form :model="currentDocument" label-width="80px" v-if="currentDocument" :cols="2">
        <el-row>
          <el-col :span="12">
            <el-form-item label="文档名称">
              <el-input v-model="currentDocument.documentName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="文档路径">
              <el-input v-model="currentDocument.documentPath"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-tabs v-model="activeTab">
          <el-tab-pane label="原始输入" name="rawInput">
            <el-input type="textarea" v-model="currentDocument.rawInput" rows="30"></el-input>
          </el-tab-pane>
          <el-tab-pane label="需求" name="requirement">
            <el-input type="textarea" v-model="currentDocument.requirement" rows="30"></el-input>
          </el-tab-pane>
          <el-tab-pane label="UI设计" name="uiDesign">
            <el-input type="textarea" v-model="currentDocument.uiDesign" rows="30"></el-input>
          </el-tab-pane>
          <el-tab-pane label="设计" name="design">
            <el-input type="textarea" v-model="currentDocument.design" rows="30"></el-input>
          </el-tab-pane>
          <el-tab-pane label="任务设计" name="taskDesign">
            <el-input type="textarea" v-model="currentDocument.taskDesign" rows="30"></el-input>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { knowledgeApi } from './knowledge_api';
import Splitter from '@/components/splitter.vue';
import TagTree from './tag_tree.vue';

export default {
  components: {
    Splitter, TagTree
  },
  data() {
    return {
      leftPanelWidth: 200,
      documentList: [],
      currentDocument: null,
      activeDocumentId: '',
      activeTag: null,
      currentProject: null,
      activeTab: 'rawInput',
    };
  },
  methods: {
    onProjectChange(project) {
      this.activeTag = null;
      this.currentProject = project;
      this.fetchDocuments(null);
    },
    onTagTreeSelected(tag) {
      this.activeTag = tag;
      this.fetchDocuments(tag?.tagId);
    },
    async fetchDocuments(tagId) {
      if (!this.currentProject) return;
      this.documentList = await knowledgeApi.getDocuments(this.currentProject.projectName, tagId);
      if (this.documentList.length > 0) {
        this.fetchDocument(this.documentList[0].documentId);
      }
      else {
        this.activeDocumentId = '';
        this.currentDocument = null;
      }
    },
    async fetchDocument(documentId) {
      this.currentDocument = await knowledgeApi.getDocument(documentId);
      this.activeDocumentId = documentId;
    },
    async importDocuments() {
      if (!this.currentProject) {
        this.$message.error('请先选择一个项目');
        return;
      }
      await knowledgeApi.importDocuments(this.currentProject.projectName);
      this.$message.success('文档导入成功');
      this.fetchDocuments(this.activeTag?.tagId);
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  height: 100vh;
  width: 100%;
}

.el-aside {
  background-color: #ffffff;
  padding: 0px 0px;
  border: 1px solid #dcdcdc;
  margin-bottom: 0px !important;
}

.el-main {
  padding: 7px;
  border: 1px solid #dcdcdc;
}

.el-form-item {
  margin-bottom: 5px;
}

.el-menu-item {
  line-height: 40px;
  height: 40px;
}
</style>
