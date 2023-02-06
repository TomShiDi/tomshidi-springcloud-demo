FROM tomshidi/jdk:jdk8u212_aliyun_1

# 环境变量
ENV WORK_PATH /home/project/app
ENV APP_NAME *.jar
ENV APPLICATION_PROPERTIES core-demo/src/main/resources/bootstrap.yml
ENV JAVA_OPTS -Xmx1024m -Xms512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m

EXPOSE 8090


#ADD

#COPY
COPY $APPLICATION_PROPERTIES $WORK_PATH/config/application.properties

#COPY
COPY core-demo/target/$APP_NAME $WORK_PATH/core-demo.jar

# WORKDIR
WORKDIR $WORK_PATH

#GMT+8
RUN echo "Asia/Shanghai" > /etc/timezone

ENTRYPOINT exec java $JAVA_OPTS -jar -Djava.security.egd=file:/dev/./urandom -jar $WORK_PATH/core-demo.jar
