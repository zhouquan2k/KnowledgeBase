<template>
  <div>
    <el-tree :data="tags" node-key="tagId" :props="defaultProps" @node-click="handleNodeClick" default-expand-all
      :expand-on-click-node="false">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <div>
          <el-input v-if="editingTagId === data.tagId" v-model="data.tagName" size="mini" @blur="updateTag(data)"
            @keyup.enter.native="updateTag(data)"></el-input>
          <span v-else>{{ data.tagName }}</span>
        </div>
        <div>
          <el-button type="text" icon="el-icon-edit" @click="editTag(data)"></el-button>
          <el-button type="text" icon="el-icon-delete" @click="deleteTag(data)"></el-button>
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
    // TODO move to common utils
    buildTree(data) {
      const itemMap = {};
      // 首先构建一个映射关系，用于快速查找节点
      data.forEach(item => {
        itemMap[item.tagId] = { ...item, children: [] };
      });
      const tree = [];
      // 遍历所有节点，将它们放置在正确的位置
      data.forEach(item => {
        const node = itemMap[item.tagId];
        if (!item.parentTagId) {
          tree.push(node);
        } else {
          // 如果父节点存在，将当前节点加入到父节点的children中
          const parent = itemMap[item.parentTagId];
          if (parent) {
            parent.children.push(node);
          }
          else {
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
      this.$confirm('此操作将永久删除该标签, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        await knowledgeApi.deleteTag(tag.tagId);
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
        this.fetchTags();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleNodeClick(data) {
      this.$emit('change', data);
    }
  }
};
</script>

<style scoped lang="scss">
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
