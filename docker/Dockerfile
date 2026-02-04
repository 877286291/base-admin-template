# 使用前需在项目根目录执行: mvn package -pl admin -am -DskipTests
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

COPY admin/target/admin-*.jar app.jar

EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.jar"]
