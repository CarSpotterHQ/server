# 1. 빌드 단계 (Build stage)
FROM eclipse-temurin:21-jdk as build

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 코드 복사
COPY . .

RUN chmod +x gradlew

# Gradle 또는 Maven 빌드 (여기서는 Gradle 사용 예시)
RUN ./gradlew build -x test --no-daemon --stacktrace

# 2. 실행 단계 (Runtime stage)
FROM eclipse-temurin:21-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 단계에서 생성된 jar 파일을 복사
COPY --from=build /app/build/libs/*.jar backend.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "backend.jar"]

# 8080 포트 오픈
EXPOSE 8080
