#!/usr/bin/env bash
#
# 一键打包部署脚本
# 用法: ./deploy.sh [选项]
#   --build-only  仅打包并构建镜像，不启动容器
#   --app-only    仅重新构建并重启应用服务（依赖 MySQL/Redis 已运行）
#   --down        停止并移除所有相关容器
#   --skip-tests  打包时跳过测试（默认已跳过）
#
set -e

# 项目根目录（脚本所在目录）
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]:-$0}")" && pwd)"
cd "$ROOT_DIR"

# Docker Compose 配置
COMPOSE_FILE="docker/docker-compose.yml"
ENV_FILE="docker/.env"

# 颜色输出
red='\033[0;31m'
green='\033[0;32m'
yellow='\033[1;33m'
nc='\033[0m'

info()  { echo -e "${green}[INFO]${nc} $*"; }
warn()  { echo -e "${yellow}[WARN]${nc} $*"; }
err()   { echo -e "${red}[ERROR]${nc} $*"; }

# 检查必要文件
check_env() {
    if [[ ! -f pom.xml ]]; then
        err "未在项目根目录找到 pom.xml，请于项目根目录执行脚本"
        exit 1
    fi
    if [[ ! -f $COMPOSE_FILE ]]; then
        err "未找到 $COMPOSE_FILE"
        exit 1
    fi
}

# Maven 打包（admin 模块及其依赖）
do_build() {
    info "Maven 打包 (admin 模块) ..."
    if [[ -x mvnw ]]; then ./mvnw package -pl admin -am -DskipTests -q; else mvn package -pl admin -am -DskipTests -q; fi
    if [[ ! -f admin/target/admin-*.jar ]]; then
        err "打包后未找到 admin/target/admin-*.jar"
        exit 1
    fi
    info "Maven 打包完成"
}

# 构建并启动（或仅构建）
do_deploy() {
    local build_only=false
    local app_only=false
    local down=false

    for arg in "$@"; do
        case "$arg" in
            --build-only) build_only=true ;;
            --app-only)   app_only=true ;;
            --down)       down=true ;;
        esac
    done

    check_env

    # 统一 docker compose 命令（可选 --env-file）
    compose_cmd() {
        if [[ -f "$ENV_FILE" ]]; then
            docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" "$@"
        else
            docker compose -f "$COMPOSE_FILE" "$@"
        fi
    }

    if [[ "$down" == true ]]; then
        info "停止并移除容器 ..."
        compose_cmd down
        info "已停止"
        exit 0
    fi

    if [[ "$app_only" == true ]]; then
        info "仅重新构建并重启应用服务 ..."
        do_build
        compose_cmd up -d --build app
        info "应用已重启"
        exit 0
    fi

    do_build

    if [[ "$build_only" == true ]]; then
        info "构建 Docker 镜像（不启动）..."
        compose_cmd build
        info "仅构建完成，未启动容器"
        exit 0
    fi

    info "构建 Docker 镜像并启动服务 ..."
    if [[ ! -f "$ENV_FILE" ]]; then
        warn "未找到 $ENV_FILE，将使用默认环境变量；可复制 docker/.env.example 为 docker/.env 并修改"
    fi
    compose_cmd up -d --build

    info "部署完成"
    echo ""
    echo "  应用端口: ${APP_PORT:-9001} (可通过 docker/.env 中 APP_PORT 修改)"
    echo "  停止服务: ./deploy.sh --down"
    echo ""
}

do_deploy "$@"
