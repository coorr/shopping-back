#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=shopping-back

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f $PROJECT_NAME)

echo "> 현재 구동중인 애플리케이션 pid : $CURRENT_PID"

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새애플리케이션 배포"
JAR_NAME=$(ls -tr /home/ec2-user/app/step2/ | tail -n 1)

echo "> JAR Name : $JAR_NAME"
echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
        $REPOSITORY/$JAR_NAME 2>&1 &

cp $REPOSITORY/zip/*.jar $REPOSITORY/

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/
echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}*.jar)
echo "현재 구동중인 애플리케이션 pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi
echo "> 새애플리케이션 배포"
JAR_NAME=$(ls -tr /home/ec2-user/app/step2/ | tail -n 1)
echo "> JAR Name: $JAR_NAME"
nohup java -jar \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &