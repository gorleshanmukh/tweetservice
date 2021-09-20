FROM openjdk:11
ADD target/tweetservice.jar tweetservice.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","tweetservice.jar"]