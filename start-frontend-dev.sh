#!/bin/bash

# 设置前端框架目录
FRONTEND_DIR="/Users/quanzhou/Workspace/FrontendFramework"

# 检查目录是否存在
if [ ! -d "$FRONTEND_DIR" ]; then
    echo "错误: $FRONTEND_DIR 目录不存在"
    exit 1
fi

# 运行容器
docker run -it \
    --name kb-frontend-dev \
    -p 8002:8080 \
    -v "$FRONTEND_DIR:/app" \
    -v "/Users/quanzhou/Workspace/KnowledgeBase/all-modules.js:/app/modules/all-modules.js" \
    -v "/Users/quanzhou/Workspace/KnowledgeBase/kb.config.js:/app/modules/kb.config.js" \
    -v "$PWD/webfrontend:/app/src/kb" \
    -v /app/node_modules \
    -e "VUE_APP_API_BASE_URL=http://host.docker.internal:58070" \
    -w /app \
    node:20-alpine \
    sh -c "npm install && npm run serve" 