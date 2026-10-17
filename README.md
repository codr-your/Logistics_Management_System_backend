
---
# 物料出入库物流管理系统 - 后端
> 基于 SpringBoot2.7.6 + MyBatis + MySQL 开发，提供RESTful API，支撑前端物料仓储管理业务，提供库存流水、统计图表接口。

## 项目简介
后端提供用户、物料、出入库单据、库存流水的全套接口；完成数据持久化、业务逻辑校验、统计计算；提供库存卡片汇总、流水报表、近7天趋势图接口，供前端可视化看板使用。

## 技术栈
- 核心框架：SpringBoot 2.7.6
- ORM框架：MyBatis
- 数据库：MySQL 8.0
- 构建工具：Maven（pom.xml）
- 工具：Lombok，VO/DO分层实体设计

## 功能接口模块
1. 用户模块：登录接口，身份校验
2. 物料管理：物料CRUD、物料编码唯一性校验
3. 出入库单据：新增单据提交，单据提交后锁定不可修改删除
4. 库存流水：每次出入库自动生成库存变动流水记录，流水分页筛选查询
5. 统计接口：
   - 库存指标卡片统计接口
   - 库存流水汇总报表接口
   - 近7天库存变化图表接口

## 环境准备
### 1. 环境要求
- JDK 17
- MySQL 8.0
- Maven 3.6+

### 2. 运行步骤
1. 创建MySQL数据库，执行项目内SQL脚本，导入表结构与初始数据
2. 修改 `application.yml` 数据库连接配置（账号、密码、数据库名）
3. Maven加载依赖
```bash
mvn clean install
```
### 3. 启动 SpringBoot 主类，端口 8081

## 项目目录结构
```bash
src/main/java
├── controller  # 接口控制器，接收前端请求
├── service     # 业务逻辑层
├── mapper      # MyBatis数据库映射
├── entity      # 数据库实体DO
├── vo          # 视图返回VO对象
├── util        # 工具类
└── LogisticsApplication.java # 项目启动类
```

## 前端对接
前端仓库地址：Logistics_Management_System_front
接口规范：RESTful JSON
跨域：后端已配置跨域允许前端开发环境访问

## 数据库说明
核心数据表：用户表、物料表、出入库单据表、库存流水表。单据提交时自动生成库存流水记录，保证库存变动可追溯。
