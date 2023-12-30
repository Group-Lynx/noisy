# Noisy

## 运行该项目

1. 下载 Docker 官网提供的 [Docker Desktop](https://www.docker.com/products/docker-desktop/) 安装包。
2. 安装 Docker Desktop，并启动它（可能需要注册账户）。
3. 打开命令行，`cd` 至项目根目录（即该 `README.md` 所在的目录）
4. 运行指令 `docker compose up --build`
5. 等待所有容器启动（`noisy-database`, `noisy-backend`, `noisy-frontend` 共三个）。
6. 完全启动后，可以在 http://localhost:3000/ 访问到应用程序。

## 开发该项目

- 前端环境
  - [Node.js v20.10.0 LTS](https://nodejs.org/)
- 后端环境
  - [Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/)
  - [Apache Maven 3.9.6](https://maven.apache.org/download.cgi)
  - [MySQL](https://dev.mysql.com/downloads/)
- 部署所需工具
  - [Docker Desktop](https://www.docker.com/products/docker-desktop/)
