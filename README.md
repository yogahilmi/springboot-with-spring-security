# Project com.tasanah/springsecurity
# Template structure
```shell
   |-----domain
   |-----dto
   |-----exceptions
   |-----infra
   |-------client
   |-------http
   |-------kafka
   |-----repository
   |-----services
   |-----spi
   |-----transformer
```

Contains all domain Entities, Value Object, Event which serve business Domain

This package speaks up the data transfer story between this service(domain) with other.
Avoid distracting the Domain package.

Express the business exception in class level will help the core implementation but also support debugging as well
Refers to any kind of integration regarding infra dependencies such as Database, Pubsub, Protocol layer.

Reflect business data layer operation

Centralize the implementation of business logic

This package says out loud the Core business operation by Domain expert


# Swagger
```shell
http://localhost:8080/swagger-ui/index.html#/
```
Please follow this sample which is well aligned with Swagger API Annotation
[Swagger API Controller Sample](https://github.com/springdoc/springdoc-openapi-demos/tree/master/springdoc-openapi-spring-boot-2-webmvc/src/main/java/org/springdoc/demo/app2/api)


# Testing
Note : Windows user can use [choco](https://community.chocolatey.org/packages/make) to install make

```shell
make unit
```
This might require Docker Engine for TestContainer
```shell
make integration-test
```

```shell
make test
```
```shell
make report
```

```shell
./target/sites/jacoco/index.html
```
Update your `code coverage target` in [pom.xml](pom.xml) at line 36 `<code.coverage.core.target>11%</code.coverage.core.target>`
```shell
make build
```

```shell
make image
```

```shell
make run-local
```

```shell
make reset
```
