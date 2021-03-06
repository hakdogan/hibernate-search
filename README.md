# Hibernate Search
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=org.jugistanbul%3Ahibernate-search&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.jugistanbul%3Ahibernate-search)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=org.jugistanbul%3Ahibernate-search&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=org.jugistanbul%3Ahibernate-search)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=org.jugistanbul%3Ahibernate-search&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=org.jugistanbul%3Ahibernate-search)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.jugistanbul%3Ahibernate-search&metric=coverage)](https://sonarcloud.io/dashboard?id=org.jugistanbul%3Ahibernate-search)

Hibernate Search is a library that allows keeping your local `Apache Lucene` indexes or `ElasticSearch` cluster in sync with your data that extracts from `Hibernate ORM` based on your domain model. This repository was created for an article published in the `Java Advent Calendar` and shows you how to use Hibernate Search in a concrete example.

## Prerequisites

* JDK 1.8 or later
* Maven 3.x.x
* Docker for Elasticsearch and PostgreSQL

## Preparing the infrastructure

The entities of the application are stored in `PostgreSQL` and indexed in `ElasticSearch` so we need instances of them.

```shell script
docker run --rm --name postgresql \
  -e POSTGRES_USER=postgres \ 
  -e POSTGRES_PASSWORD=postgres \ 
  -e POSTGRES_DB=jugistanbul \ 
  -p 5432:5432 \ 
  postgres:${POSTGRESQL_VERSION}
```
```shell script
docker run --rm --name elasticsearch  \
  -e "discovery.type=single-node" \
  -p 9200:9200 \ 
  docker.elastic.co/elasticsearch/elasticsearch-oss:${ELASTICSEARCH_VERSION}
```

## Usage examples

```json
http -v GET localhost:8080/search/event/quarkus
GET /search/event/Quarkus HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/2.3.0



HTTP/1.1 200 OK
Content-Length: 43
Content-Type: application/json

[
  {
    "id": 3,
    "name": "Introduction to Quarkus"
  }
]

http -v GET localhost:8080/search/host/title/consultion
GET /search/host/title/consultion HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/2.3.0



HTTP/1.1 200 OK
Content-Length: 187
Content-Type: application/json

[
  {
    "events": [
                {
                  "id": 2,
                  "name": "CI & CD with Azure Devops"
                },
                {
                  "id": 3,
                  "name": "Introduction to Quarkus"
                }
              ],
              "id": 2,
              "firstname": "Huseyin",
              "lastname": "Akdogan",
              "title": "Expert Software Consultant"
  }
]

http -v GET localhost:8080/search/event/quar
GET /search/event/Quar HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/2.3.0



HTTP/1.1 404 Not Found
Content-Length: 91
Content-Type: application/json

{
    "code": 404,
    "error": "No event found",
    "exceptionType": "javax.ws.rs.WebApplicationException"
}

```

## To run

```shell script
mvn quarkus:dev
```

## To tests

```shell script
mvn verify
```

## Relevant article
[How to Keep Elasticsearch in Sync with Relational Databases?
](https://hakdogan.medium.com/how-to-keep-elasticsearch-in-sync-with-relational-databases-a8c4c2b4c9fe)