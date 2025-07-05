// modules/all-modules.js
// 排除 tests 目录下的 js 文件
const kbModule = require.context('../src/kb', true, /^(?!.*tests).*\.js$/);
// const sysModule = require.context('../src/system', true, /^(?!.*tests).*\.js$/);

const AllModules = {
  '@kb/': kbModule,
};

export default AllModules; 