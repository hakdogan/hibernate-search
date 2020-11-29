# Hibernate Search

Hibernate Search is a library that allows keeping your local `Apache Lucene` indexes or `ElasticSearch` cluster in sync with your data that extracts from `Hibernate ORM` based on your domain model. This repository shows you how to use it.

## Prerequisites

* JDK 1.8 or later
* Maven 3.x.x
* Docker for Elasticsearch and PostgreSQL

## Preparing the infrastructure

```shell script
docker run --rm --name postgresql \
  -e POSTGRES_USER=postgres \ 
  -e POSTGRES_PASSWORD=postgres \ 
  -e POSTGRES_DB=jugistanbul \ 
  -p 5432:5432 \ 
  postgres:${POSTGRES_VERSION}
```
```shell script
docker run --rm --name elasticsearch  \
  -e "discovery.type=single-node" \
  -p 9200:9200 \ 
  docker.elastic.co/elasticsearch/elasticsearch-oss:${ELASTICSEARCH_VERSION}
```

# To run

```shell script
mvn spring-boot:run
```