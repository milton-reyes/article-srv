FROM openjdk:8-jdk as mvnbuild

ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"
ARG SHA=c35a1803a6e70a126e80b2b3ae33eed961f83ed74d18fcd16909b2d44d7dada3203f1ffe726c17ef8dcca2dcaa9fca676987befeadc9b9f759967a8cb77181c0
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

ARG ATB_SRV_P=${ARTICLE_SRV_PORT}
ARG ATB_URL=${ATABEY_URL}
ARG ATB_DB_PORT=${ATABEY_DB_PORT}
ARG ATB_DB_NAME=${ATABEY_DB_NAME}
ARG ATB_U=${ATABEY_MASTER_U}
ARG ATB_P=${ATABEY_MASTER_P}

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
    && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
    && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
    && rm -f /tmp/apache-maven.tar.gz \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

ENV ARTICLE_SRV_PORT=$ATB_SRV_P
ENV ATABEY_URL=$ATB_URL
ENV ATABEY_DB_PORT=$ATB_DB_PORT
ENV ATABEY_DB_NAME=$ATB_DB_NAME
ENV ATABEY_MASTER_U=$ATB_U
ENV ATABEY_MASTER_P=$ATB_P

#RUN echo ${ATABEY_URL}

COPY pom.xml pom.xml
COPY src src

RUN /usr/bin/mvn -version
#RUN mvn package
RUN /usr/bin/mvn -B -f pom.xml clean install
#RUN rm -rf /tmp/article-srv
#RUN mvn -B -f /tmp/pom.xml dependency:resolve

#FROM openjdk:8-jdk

#COPY --from=mvnbuild /root/.m2 /root/.m2
#VOLUME /root/.m2
ENTRYPOINT ["java", "-jar", "/root/.m2/repository/com/yukaju/article-srv/0.0.1/article-srv-0.0.1.jar"]
