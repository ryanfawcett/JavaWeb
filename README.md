# Java Web

常见的WEB服务器

- Tomcat
- Jetty
- JBoss（应用服务器，内嵌了一个 tomcat 服务器）
- WebLogic（应用）
- WebSphere（应用）

WEB服务器和应用服务器的区别：

1. 应用服务器实现了 JavaEE 的所有规范 （JavaEE 共有13个不同的规范）
2. WEB 服务器只实现了 JavaEE 中的 Servlet + JSP 两个核心的规范

## 1. tomcat

### 概述

Tomcat 是 Apache 下的一款开源的轻量级WEB服务器，别名 Catalina，tomcat 是由 Java 语言编写的。

### 下载

- Apache 官网：https://www.apache.org
- Tomcat 官网：https://tomcat.apache.org/

### tomcat目录介绍

- bin：用于存放 tomcat 服务器的命令的文件夹，比如启动/关闭等
- conf：用于存放 tomcat 的配置文件的文件夹，如 `server.xml`文件中可以配置 tomcat 的端口等，其默认端口为 8080
- lib：tomcat 所需要的所有的 Jar 包文件
- logs：日志信息，tomcat 启动等信息都会存储在这个文件夹中
- temp：存储临时文件
- webapps：用于部署应用程序的目录
- works：用于存放 JSP 编译之后的字节码文件

### 运行

**注意：要运行tomcat，必须先有JRE**

1. JAVA_HOME：JDK 的根目录
2. CATALINA_HOME：tomcat 的根目录
3. 启动
   1. Windows：/bin/startup.bat
   2. Linux：/bin/startup.sh

### 解决tomcat控制台乱码

1. 找到 CATALINA_HOME/conf/logging.properties 配置文件
2. 在配置文件中搜索 `java.util.logging.ConsoleHandler.encoding=UTF-8`
3. 将 `UTF-8` 修改为 `GBK`，重启 tomcat 即可

## 2. BS系统结构中的角色和协议

### BS系统结构中的角色

- 浏览器
- WEB 服务器
- Webapp
- 数据库

### BS系统结构中的协议

- 浏览器 - WEB 服务器：HTTP
- WEB 服务器 - Webapp：Servlet
- Webapp - 数据库：JDBC

## 3.servlet

### 什么是 Servlet?

Servlet 是一套 JavaEE 的规范，其提供了一套接口，所有实现了这套接口的 Java 程序都可以在 WEB 服务器中运行。Servlets 通过 HTTP 接收和响应 WEB 客户端的请求。

### Servlet 规范中的目录结构

- webapps
  - YOUR APP NAME
    - WEB-INF(强制)
      - classes：编译后的字节码文件
      - lib：用于存放依赖的第三方JAR包，包括Servlet/JSP等
      - web.xml(强制，建议从其他的 webapp 中复制)

### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
                      https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
  version="6.0"
  metadata-complete="true">
  
</web-app>

```

### Tomcat 10及以上版本部署的问题

JavaEE 被 Oracle 捐献给了 Apache，从 tomcat 10 开始，JavaEE 将改为 JakartaEE，老版本的web项目将无法直接在 tomcat 10+ 的版本中运行，因为 Servlet 的路径也由原来的 `javax.servlet` 改为了 `jakarta.servlet`，如果需要迁移到 tomcat 10 或更新的版本，可以使用其开发的迁移工具：https://github.com/apache/tomcat-jakartaee-migration

### Servlet 向浏览器返回HTML

```java
public void service(ServletRequest req, ServletResponse resp) {
  resp.setContentType("text/html");
  PrintWriter out = resp.getWriter();
  out.print("<h1>Hello World</h1>");
}
```

### Servlet 的生命周期

1. 用户在第一次发送请求时，Servlet 对象被实例化（调用其无参构造方法）
2. Servlet 对象创建后，tomcat 会调用其 init 方法完成初始化
3. 初始化完成后，tomcat 调用其 service 方法处理用户请求

Servlet 对象只有在用户发送第一次请求时创建，这说明 Servlet 对象是单实例的，但是不符合单例模式（单例模式中对象的构造方式是私有化的），之所以是单实例的，是因为 Servlet 对象是由 tomcat 创建并且维护的。需要注意的是：Servlet 的无参构造方法 和 init 方法只会在用户第一次请求时被调用，而 service 方法则是用户的每次请求都会调用

### 如何在 tomcat 启动时创建 Servlet

```xml
<?xml version="1.0" encoding="UTF-8"?>

<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
         version="6.0"
         metadata-complete="true">
    <servlet>
        <servlet-name>MyServlet</servlet-name>
        <servlet-class>cn.example.servlet.MyServlet</servlet-class>
        <load-on-startup>0</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>MyServlet</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>
</web-app>
```

`<load-on-startup>`标签的值越小，优先级越高

### ServletConfig

1. ServletConfig 是一个 Servlet 规范中的接口，web 服务器会实现这个接口
2. ServletConfig 对象由 tomcat 创建，并在创建 Servlet 对象时将此对象通过 Servlet 的 init 方法注入 Servlet 实例中
3. ServletConfig 中封装的就是 web.xml 文件中注册的配置信息
