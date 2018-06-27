FROM memorais/tomee:8-jdk-7.0.2-plus-openjdk-alpine
MAINTAINER Loïck MAHIEUX <loick111@gmail.com>

WORKDIR /usr/local/tomee/

RUN apk --update add --no-cache curl libarchive readline pcre gmp libedit zlib db unixodbc libuuid openjdk8 \
    && apk --update add --no-cache --virtual build-dependencies bash alpine-sdk autoconf libarchive-dev gmp-dev pcre-dev readline-dev libedit-dev zlib-dev db-dev unixodbc-dev util-linux-dev linux-headers \
    && apk add geos-dev --update-cache --virtual build-dependencies --repository http://dl-3.alpinelinux.org/alpine/edge/testing/ --allow-untrusted

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin

ENV SWIPL_VER=7.5.7
ENV SWIPL_CHECKSUM=648d15a534a3503ef281cc7c2ead7ee913866c3d7cb1d1344508ee24b96b4512

RUN curl -O http://www.swi-prolog.org/download/devel/src/swipl-$SWIPL_VER.tar.gz \
    && echo "$SWIPL_CHECKSUM  swipl-$SWIPL_VER.tar.gz" >> swipl-$SWIPL_VER.tar.gz-CHECKSUM \
    && sha256sum -c swipl-$SWIPL_VER.tar.gz-CHECKSUM

RUN tar -xzf swipl-$SWIPL_VER.tar.gz \
    && cd swipl-$SWIPL_VER && ./configure --with-world && make && make install \
    && apk del build-dependencies \
    && cd .. && rm -rf swipl-$SWIPL_VER.tar.gz swipl-$SWIPL_VER.tar.gz-CHECKSUM swipl-$SWIPL_VER

ENV JAVA_OPTS="-Djava.library.path=/usr/local/lib/swipl-7.5.7/lib/x86_64-linux"

ENV DB_HOST db
ENV DB_PORT 3306
ENV DB_SSL no
ENV DB_NAME cocktailize
ENV DB_USERNAME root
ENV DB_PASSWORD root

COPY ./docker/start.sh .
RUN ["chmod", "u+x", "./start.sh"]

COPY ./prolog ./webapps/prolog

COPY ./target/cocktailize-1.0-SNAPSHOT.war ./webapps/cocktailize.war

RUN ls /usr/lib/jvm/java-1.8-openjdk/bin

ENTRYPOINT ["./start.sh"]