#!/bin/bash

REPOSITORY=/home/ec2-user/blog_auto_deploy
PROJECT_NAME=blog

echo "> Build 파일 복사"

LATEST_JAR=$(ls -tr $REPOSITORY/$PROJECT_NAME/build/libs/*.jar | tail -n 1)
cp $LATEST_JAR $REPOSITORY/

echo "> 현재 구동중인 어플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl blog | grep jar | awk '{print $1}')

echo "> 현재 구동중인 어플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 어플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-mariadb.properties,/home/ec2-user/app/application-credential.properties \
    $REPOSITORY/$JAR_NAME 2>&1 &
