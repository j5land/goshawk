FROM openjdk:8-jre

# 时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo Asia/Shanghai > /etc/timezone \
  && dpkg-reconfigure -f noninteractive tzdata

WORKDIR /app/bin

COPY target/goshawk-console-zipkin.jar ./app.jar

# 日志目录
VOLUME /app/log

EXPOSE 9001

CMD ["java", "-jar", "/app/bin/app.jar"]