FROM openjdk:16-alpine as build
WORKDIR /app
COPY . /app
RUN chmod +x gradlew
RUN ./gradlew build -x test

FROM openjdk:16-alpine
WORKDIR /home/app
COPY --from=build /app/build/docker/layers/libs /home/app/libs
COPY --from=build /app/build/docker/layers/resources /home/app/resources
COPY --from=build /app/build/docker/layers/application.jar /home/app/application.jar
EXPOSE 8080
CMD ["java", "-jar", "/home/app/application.jar"]