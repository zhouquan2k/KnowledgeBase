<template>
  <div>
    <el-tree :data="tags" node-key="tagId" :props="defaultProps" @node-click="handleNodeClick" default-expand-all
      :expand-on-click-node="false" draggable @node-drop="handleNodeDrop">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <div>
          <el-input v-if="editingTagId === data.tagId" v-model="data.tagName" size="mini" @blur="updateTag(data)"
            @keyup.enter.native="updateTag(data)"></el-input>
          <span v-else @dblclick="editTag(data)">{{ data.tagName }}</span>
        </div>
        <div>
          <el-button type="text" icon="el-icon-close" @click="deleteTag(data)"></el-button>
        </div>
      </span>
    </el-tree>
  </div>
</template>

<script>
import { knowledgeApi } from './knowledge_api';

export default {
  data() {
    return {
      tags: [],
      editingTagId: null,
      defaultProps: {
        children: 'children',
        label: 'tagName'
      }
    };
  },
  created() {
    this.fetchTags();
  },
  methods: {
    async fetchTags() {
      const tags = await knowledgeApi.getTagTree();
      this.tags = this.buildTree(tags);
    },
    buildTree(data) {
      const itemMap = {};
      data.forEach(item => {
        itemMap[item.tagId] = { ...item, children: [] };
      });
      const tree = [];
      data.forEach(item => {
        const node = itemMap[item.tagId];
        if (!item.parentTagId) {
          tree.push(node);
        } else {
          const parent = itemMap[item.parentTagId];
          if (parent) {
            parent.children.push(node);
          } else {
            tree.push(node);
          }
        }
      });
      return tree;
    },
    editTag(tag) {
      this.editingTagId = tag.tagId;
    },
    async updateTag(tag) {
      if (this.editingTagId) {
        await knowledgeApi.updateTag(tag.tagId, tag);
        this.editingTagId = null;
        this.fetchTags();
      }
    },
    async deleteTag(tag) {
      await this.$Confirm('此操作将永久删除该标签, 是否继续?');
      await knowledgeApi.deleteTag(tag.tagId);
      this.$success('删除成功');
      this.fetchTags();
    },
    handleNodeClick(data) {
      this.$emit('change', data);
    },
    async handleNodeDrop(draggingNode, dropNode, dropType) {
      const draggedData = draggingNode.data;
      const targetData = dropNode.data;
      if (dropType === 'inner') {
        draggedData.parentTagId = targetData.tagId;
      } else {
        draggedData.parentTagId = targetData.parentTagId;
      }
      await knowledgeApi.moveTag(draggedData.tagId, draggedData.parentTagId);
      this.fetchTags();
    }
  }
};
</script>

<style scoped lang="scss">
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  font-size: 12px;
  padding-right: 8px;
}
</style>
