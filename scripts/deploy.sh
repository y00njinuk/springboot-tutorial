#!/bin/bash

REPOSITORY=/root/app/step2
PROJECT_NAME=springboot-tutorial

cd $REPOSITORY/$PROJECT_NAME

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl ${PROJECT_NAME} | grep jar | awk '{print $1}')

echo "> 현재 구동중인 애플리케이션 pid : $CURRENT_PID"
if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR NAME : $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

# 스프링의 설정 파일 위치를 지정한다.
# classpath는 jar 안에 있는 resources 디렉토리를 기준으로 경로가 생성된다.
# application-oauth.properties는 프로젝트 외부에서 관리해야 하므로 절대 경로를 사용한다.
nohup java -jar \
 -Dspring.config.location=\
classpath:/application.properties,\
classpath:/application-real.properties,\
/root/app/application-oauth.properties,\
/root/app/application-real-db.properties\
 -Dspring.profiles.active=real\
 $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
