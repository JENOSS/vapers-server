# vapers-server

베이퍼스 - 전자담배 기기/액상 판매 어플리케이션 (Server-side)

# Tech

- Java 11, Spring boot 2.7.3
- Spring Data JPA, Spring Security, Spring Cloud Config, Spring Cloud Gateway, Spring Cloud Eureka, Resilience4j, OpenFeign
- Mysql, RabbitMQ, Kafka, Docker
&nbsp;

# About

- 전자담배 기기/액상 판매 어플리케이션 서버 사이드 개발
- MSA, Spring Cloud 학습 위한 토이 프로젝트
- Microservice 간 통신은 FeignClient (동기) / Kafka (비동기) 이용
- Circuit Breaker 를 통해 서비스가 장애 전파 방지
- Spring Security 를 이용한 로그인 구현
- 로그인, 회원 정보 / 상품 등록, 상품 정보, 상품 검색 / 주문하기, 주문 취소, 주문 정보 등의 기능 구현
- JWT 토큰(Access Token, Refresh Token) 을 이용한 Stateless 인증 구현
- 클라이언트에서 원활한 에러 처리를 위해 Exception Handler 로 서버 에러 통합 관리
&nbsp;


# Architecture
![vapers-architecture](https://user-images.githubusercontent.com/46643781/189474389-7fe3aced-6f99-4a19-b142-2631e6c1da7c.jpg)
&nbsp;



# Authorization
![vapers-authjpg](https://user-images.githubusercontent.com/46643781/189474379-fbbf8ccd-c5d4-4a27-955b-1471ce7ca03e.jpg)
