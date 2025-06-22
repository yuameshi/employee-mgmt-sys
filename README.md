# 开发环境

开发用环境，若自身部署时环境不同应视情况修改依赖和代码

> Java: 17.0.10-ms
> Apache Maven: 3.9.9
> MySQL: MiraiDB 15.1
> Apache Tomcat: 11.0.5

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

清提前导入数据库数据，即运行 SQL 脚本，以创建所需的表和数据。

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
