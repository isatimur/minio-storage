FROM ysihaoy/scala-play:2.12.2-2.6.0-sbt-0.13.15


ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""

# caching dependencies
COPY ["build.sbt", "/tmp/build/"]
COPY ["project/plugins.sbt", "project/build.properties", "/tmp/build/project/"]
RUN cd /tmp/build && \
  sbt compile && \
  sbt test:compile && \
  rm -rf /tmp/build

# copy code
COPY ../app/docker /root/app/
WORKDIR /root/app
RUN sbt compile && sbt test:compile

EXPOSE 8080
CMD ["sbt"]