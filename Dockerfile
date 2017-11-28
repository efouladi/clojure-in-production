FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/clojure-in-production.jar /clojure-in-production/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/clojure-in-production/app.jar"]
