/**
 * GCP 模块配置文件
 * 定义 GCP 模块的 store、router 等扩展
 */

export default {
  name: 'kb',
  
  // Store 模块配置
  store: [
  ],
  
  // Router 模块配置
  router: [
    {
      name: 'kb',
      path: '@kb/router'
    }
  ],

  // 应用路由配置
    appRoute: {
      name: 'app',
      path: '/',
      children: [
        // 动态路由会在运行时添加到这里
      ]
    },
  
  // 其他配置可以在这里添加
  components: [],
  plugins: []
}; 