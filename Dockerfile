# Pull base image.
FROM frangarcia/vertx:3.4.1-jdk8

MAINTAINER Fran Garcia <francisco@engagesciences.com>

# Expose
EXPOSE 8080 8181

WORKDIR /root/thechallenge

# Run
CMD ["./gradlew","run"]
