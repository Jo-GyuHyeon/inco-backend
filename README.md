# Spring Boot (JWT && REST API)
> " Spring Boot Application for Securing a REST API with JSON Web Token (JWT)"


## About

Intercomcis App에 Rest API 데이터를 제공합니다. 유저가 Spring Boot Server 로 로그인을 하면, 서버는 유저의 정보에 기반한 토큰을 발급하여 유저에게 JSON Web Token (JWT)를 전달해줍니다. 그 후, 유저가 서버에 요청을 할 때 마다 JWT를 포함하여 Rest API를 요청합니다.
구독한 웹툰이 업데이트 될때 Spring boot Server는 Intercomcis App으로 Notification Push Service를 제공합니다.

- - - 
## Technologies

* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Spring Security](https://projects.spring.io/spring-security/)
* [Mysql](https://www.mysql.com/)
* [MyBatis](http://www.mybatis.org/mybatis-3/ko/index.html)
* [Maven](https://maven.apache.org/)
* [Travis CI](https://travis-ci.org/)
* [Swagger UI](https://swagger.io/)


## Configuration

> Java8를 설치해야 합니다.
> Spring Tool Suite(STS)를 사용하였습니다.


## Main building blocks
 * Spring Boot 1.4.7.RELEASE go to http://docs.spring.io/spring-boot/docs/1.4.7.RELEASE/reference/htmlsingle/ to learn more about spring boot
 * JSON Web Token go to https://jwt.io/ to decode your generated token and learn more
 
 
 
 
 Spring Boot
======================

* 스프링부트는 Spring 프로젝트가 제공하는 다양한 라이브러리와 프레임워크로 단독실행되는, 실행하기만 하면 되는 상용화 가능한 수준의, 스프링 기반 애플리케이션을 쉽게 만들어낼 수 있다.
* 최소한의 설정으로 스프링 플랫폼과 서드파티 라이브러리들을 사용할 수 있도록 하고 있다.


### 스프링부트 기능
> 사용하려는 컨포넌트를 종속적으로 추가하면 @EnableAutoConfiguration 에노테이션으로 결합에 필요한 설정이 자동으로 이루어진다.
> 기본설정되어 있는 'starter' 컴포넌트들을 쉽게 추가
> Maven이나 Gradle 같은 빌드 도구를 사용한다(Ant로는 가능하지 않다)
> Web 어플리케이션의 경우, 내장 Tomcat을 시작 (Jetty와 Undertow로 전환 가능).
> 단독실행가능한 스프링애플리케이션을 생성한다.
> 빌드하면 단일 jar 파일이 생긴다.
> 상용화에 필요한 통계, 상태 점검 및 외부설정을 제공


## Building from Source
You don't need to build from source to use Spring Boot, but if you want to try out the latest and
greatest, You also need JDK 1.8.
```
$ ./mvnw clean install

$ ./mvnw package
```
 
 
## To run the application
Use one of the several ways of running a Spring Boot application. Below are just three options:

1. Build using maven goal: `mvn clean package` and execute the resulting artifact as follows `java -jar springboot-jwt-0.0.1-SNAPSHOT.jar` or
2. On Unix/Linux based systems: run `mvn clean package` then run the resulting jar as any other executable `./springboot-jwt-0.0.1-SNAPSHOT.jar`
3. Build and start as a Travis CI

  
##### Travis CI
Github 계정으로 Travis CI에 로그인을 하신뒤 Repository를 생성합니다.
TravisCI는 상세한 CI 설정은 프로젝트에 존재하는 .travis.yml로 할수있습니다. 
프로젝트에 .travis.yml을 생성후 아래 코드를 추가합니다.
```
language: java 
jdk: 
    - openjdk8 
    
before_install :
  - chmod 755 mvnw

branches: 
    only: 
    - master 
 
# Travis CI 서버의 Home 
cache: 
    directories: 
        - '$HOME/pom.xml' 
       
    script: "./mvnw package" 


# CI 실행 완료시 메일로 알람 
notifications: 
    email: 
        recipients: 
            - xxxx@xxxx.com
```


## Swagger

 * Setting Up Swagger 2 with a Spring REST API
 
 #### Using it with Maven

```
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.7.0</version>
		</dependency>

		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.7.0</version>
		</dependency>
```
  #### SwaggerConfig 클래스 생성
  
  ```
@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
}
  ```

