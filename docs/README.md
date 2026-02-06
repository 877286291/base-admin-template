# Docker Compose 部署

在项目根目录执行。镜像不包含 Maven 打包，需先在宿机构建好 jar 再构建镜像。

## 一键打包部署

项目根目录提供 `deploy.sh`，可一键完成 Maven 打包、Docker 镜像构建与容器启动：

```bash
# 完整部署（打包 + 构建镜像 + 启动 MySQL / Redis / 应用）
./deploy.sh

# 仅打包并构建镜像，不启动容器
./deploy.sh --build-only

# 仅重新构建并重启应用（依赖 MySQL/Redis 已运行）
./deploy.sh --app-only

# 停止并移除所有相关容器
./deploy.sh --down
```

首次部署前建议复制 `docker/.env.example` 为 `docker/.env` 并按需修改环境变量。

## 手动启动

```bash
# 1. 在项目根目录打包
mvn package -pl admin -am -DskipTests

# 2. 构建镜像并启动
cd /path/to/base-admin-template
docker compose -f docker/docker-compose.yml up -d
```

首次会构建应用镜像（复制 `admin/target/admin-*.jar`）并启动 MySQL、Redis、应用。应用端口 **9001**，上下文路径 `/api/v1`，接口示例：`http://localhost:9001/api/v1/...`。

## 环境变量（可选）

复制 `docker/.env.example` 为 `docker/.env` 后按需修改，或在启动前导出变量：

| 变量 | 默认值 | 说明 |
|------|--------|------|
| MYSQL_ROOT_PASSWORD | root | MySQL root 密码 |
| MYSQL_DATABASE | base_admin | 数据库名 |
| MYSQL_PORT | 3306 | MySQL 端口 |
| MYSQL_USER / MYSQL_PASSWORD | root / root | 应用连接 MySQL 的用户与密码 |
| REDIS_PORT | 6379 | Redis 端口 |
| REDIS_PASSWORD | 空 | Redis 密码（无则留空） |
| APP_PORT | 9001 | 应用映射端口 |

## 停止与清理

```bash
docker compose -f docker/docker-compose.yml down
# 删除数据卷（会清空 MySQL/Redis 数据）
docker compose -f docker/docker-compose.yml down -v
```

## 仅重新构建应用镜像

```bash
# 先重新打包 jar
mvn package -pl admin -am -DskipTests
# 再构建镜像并启动应用
docker compose -f docker/docker-compose.yml build app --no-cache
docker compose -f docker/docker-compose.yml up -d app
```
