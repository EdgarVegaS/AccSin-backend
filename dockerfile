FROM openjdk:11-jre-slim
RUN useradd -ms /bin/bash/ accsinuser
USER accsinuser
WORKDIR /home/accsinuser
ADD ./accsin-0.0.1-SNAPSHOT.jar accsin-0.0.1-SNAPSHOT.jar
EXPOSE 9100
CMD ["java", "-jar", "accsin-0.0.1-SNAPSHOT.jar"]