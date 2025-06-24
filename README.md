# 开发环境

开发用环境，若自身部署时环境不同应视情况修改依赖和代码

> Java 版本：openjdk 17.0.10-ms
>
> 依赖管理：Apache Maven 3.9.9
>
> 数据库: MariaDB 15.1/Distrib 10.11.5 （代码与 MySQL 8 兼容）
>
> 服务器：Apache Tomcat 11.0.5

# 预编译文件

本项目已预编译为二进制文件，位于`target`目录下，详情请看[运行及部署](#运行及部署)部分。

使用预编译文件需要修改`target/employee-mgmt-sys/WEB-INF/classes/jdbc.properties`文件中的数据库配置且**不能使用 war 包（除非你能改数据库配置）**，以适应自身的数据库环境。

# 安装依赖

执行以下命令以安装该项目依赖

```bash
$ mvn install -f "pom.xml"
```

# 运行及部署

执行以下命令以生成并部署 war 包/exploded 文件夹 到 tomcat 目录下并修改自身 tomcat 配置以运行

```bash
$ mvn package -f "pom.xml"
```

war 包为位于`target`目录下的 `employee-mgmt-sys.war` 文件；

exploded 为 `target` 目录下的 `employee-mgmt-sys` 目录。

将其放置到 Tomcat 的 `webapps` 目录下即可运行

_本项目对 Tomcat 的 server.xml 进行了相关配置（即修改虚拟目录的设定）_
请在 Tomcat 目录的`conf`目录下找到 `server.xml` 文件并在`Host`标签内部修改以下内容：

```xml
<Context path="/" docBase="employee-mgmt-sys" debug="1" reloadable="true" />
```

# 数据库配置

请提前导入数据库数据，即运行 SQL 脚本，以创建所需的表和数据。

> 请注意
>
> -   在运行脚本之前，请确保数据库已创建并且可以连接。
> -   如果数据库中已经存在相同的表或数据，脚本将清除它们，请注意备份。
> -   脚本为根目录下的`employee.sql`文件，请根据自身情况检查和修改。

数据库配置文件位于`src/main/resources/jdbc.properties`，请根据自身数据库配置修改。

以下为示例：

```properties
jdbc.username=guest
jdbc.password=guest
jdbc.driverClassName=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/employee?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
```

# 登录系统

管理员账户：

> 用户名：admin
>
> 密码：admin
>
> 权限：此账户为管理员，可以执行页面上列出的全部操作

普通用户账户：

> 用户名：user
>
> 密码：user
>
> 权限：此账户为普通用户，尽可以执行查询操作

# 设计思路

本系统采用典型的 Java Web 三层架构设计，分为表示层（Servlet/JSP）、业务逻辑层（Service）、数据访问层（DAO），实现了代码解耦和易于维护。

## 核心流程

-   用户通过前端页面提交请求，Servlet 解析请求并调用相应 Service 方法。
-   Service 层处理业务逻辑，必要时调用 DAO 层进行数据持久化。
-   处理结果通过 JSP 页面反馈给用户，支持友好的错误提示和权限校验。

## 权限与安全

-   系统通过自定义的 LoginFilter 实现登录拦截，所有受限资源（除登录、首页等）仅允许已登录用户访问，未登录用户会被自动重定向到登录页。
-   登录用户信息存储于 Session，所有敏感操作（如员工信息增加、修改、删除）均需管理员权限。
-   附带部分异常处理，保证系统稳定性和用户体验。

# 员工筛选（filter）功能实现

员工筛选功能支持按部门、姓名、手机号、性别等多条件组合查询，具体实现思路如下：

-   前端页面通过表单提交筛选条件，Servlet 的 filter 方法接收参数。
-   若无任何筛选条件，则返回所有员工列表。
-   若仅输入手机号，直接精确查找并跳转至员工详情页。
-   其余条件（部门、姓名、性别）分别调用 Service 层方法获取对应员工 ID 列表。
-   对多个条件的结果集取交集，确保筛选结果同时满足所有条件。
-   若筛选结果为空，页面友好提示“无符合条件的员工”。
-   若有结果，则查询详细信息并展示。
-   所有筛选条件和结果均通过 request 属性传递给 JSP 页面，便于前端回显和交互。
