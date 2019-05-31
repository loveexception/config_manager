# configmanager
## 目前支持
* Nutz
* Nutzboot
* Druid
* Shiro
* Thymeleaf
* Quartz 定时任务
* SLog日志记录
* 支付宝
* 微信公众平台
* 阿里云消息推送
* 阿里云短信
* 高德地图
* 七牛云
* XSS攻击过滤 SQL注入过滤
* Excel 导出数据

本压缩包是一个maven工程, eclipse/idea均可按maven项目导入

MainLauncher是入口,启动即可

## 环境要求

* 必须JDK8+
* eclipse或idea等IDE开发工具,可选

## 配置信息位置

数据库配置信息,jetty端口等配置信息,均位于src/main/resources/application.properties

## 命令下启动

仅供测试用,使用mvn命令即可
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

请注意,当前需要package + nutzboot:shade, 单独执行package或者nutzboot:shade是不行的


