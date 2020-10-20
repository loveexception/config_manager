#!/usr/bin/env bash
git log --oneline >./src/main/resources/version.log
mvn clean package nutzboot:shade -o -DskipTests -Dmaven.test.skip=true
scp ./target/config-manager.jar root@172.16.16.9:/opt/jar/configManager
curl 'http://172.16.16.9:9001/index.html?processname=java-config-manager&action=restart' \
  -H 'Connection: keep-alive' \
  -H 'Upgrade-Insecure-Requests: 1' \
  -H 'DNT: 1' \
  -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36' \
  -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9' \
  -H 'Referer: http://172.16.16.9:9001/?message=Page%20refreshed%20at%20Wed%20Aug%2012%2016%3A28%3A01%202020' \
  -H 'Accept-Language: zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,ja;q=0.6' \
  -H 'Cookie: tico_theme=black; tico_user_names=%5B%22chenchen%2C123456%22%2C%22admin%2C123456%22%2C%22ticozd_admin%2C123456%22%5D; tico_user_info=%7B%22token%22%3A%222383601b715944258f9e5a6c28c9bb9b%22%2C%22user_id%22%3A%221%22%2C%22cn_name%22%3Anull%2C%22login_name%22%3A%22admin%22%2C%22dept_id%22%3A%22100%22%2C%22location_id%22%3A%22100000%22%2C%22adcode%22%3A%22100000%22%7D; sid=udkmgd5jdkjcfr49k051gtgnr6' \
  --compressed

  echo finish
say --voice="Ting-Ting" 您好,布署完成