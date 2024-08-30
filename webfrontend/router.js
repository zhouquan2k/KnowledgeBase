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
    ]
}];

export function getRoutes() {
    return Routes;
}