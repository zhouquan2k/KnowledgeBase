import KbRoot from '@kb/view/root';

export const rootPath = "/kb";
const Routes = [{
    name: '_KB',
    path: rootPath,
    component: KbRoot,
    redirect: '/kb/examples',
    meta: { title: 'KnowledgeBase' },
    isFolder: true,
    children: [
        {
            name: 'KB_Example',
            meta: { title: 'Examples' },
            path: 'examples',
            component: (resolve) => require(['@kb/view/example/example'], resolve)
        },
        {
            name: 'KB_Knowledge',
            meta: { title: 'Knowledge' },
            path: 'knowledge',
            component: (resolve) => require(['@kb/view/knowledge/knowledge'], resolve)
        }
    ]
}];

export function getRoutes() {
    return Routes;
}