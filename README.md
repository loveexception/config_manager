# configmanager

## 目前支持

-   Nutz
-   Nutzboot
-   Druid
-   Shiro
-   Thymeleaf
-   Quartz 定时任务
-   SLog 日志记录
-   支付宝
-   微信公众平台
-   阿里云消息推送
-   阿里云短信
-   高德地图
-   七牛云
-   XSS 攻击过滤 SQL 注入过滤
-   Excel 导出数据

本压缩包是一个 maven 工程, eclipse/idea 均可按 maven 项目导入

MainLauncher 是入口,启动即可

## 环境要求

-   必须 JDK8+
-   eclipse 或 idea 等 IDE 开发工具,可选

## 配置信息位置

数据库配置信息,jetty 端口等配置信息,均位于 src/main/resources/application.properties

## 命令下启动

仅供测试用,使用 mvn 命令即可

```
// for windows
set MAVEN_OPTS="-Dfile.encoding=UTF-8"
mvn compile nutzboot:run

// for *uix
export MAVEN_OPTS="-Dfile.encoding=UTF-8"
mvn compile nutzboot:run
```

## 项目打包

```
mvn clean package nutzboot:shade
```

不去服务上查看新包版本。

```
mvn clean package nutzboot:shade -o -DskipTests -Dmaven.test.skip=true  

mvn clean package nutzboot:shade -o -DskipTests
```

mvn clean package nutzboot:shade  -DskipTests
请注意,当前需要 package + nutzboot:shade, 单独执行 package 或者 nutzboot:shade 是不行的

## start 命令:

```
	mvn clean  compile nutzboot:run -o
```

新的 git 地址
http://172.16.16.1/NimbleLink1.0/configmanager.git
