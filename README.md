# Spring Boot Soft CQRS 예제
Spring Boot 기반 Soft CQRS(Command Query Responsibility Segregation) 패턴을 공부하기 위해 작성된 토이 프로젝트이다.

# 프로젝트 목적
- CQRS를 기초 수준에서 이해하고 적용
- 쓰기 모델과 조회 모델의 책임 분리
- 오버엔지니어링은 배제하면서도 추후 확장을 고려한 설계 구조 학습
    - DB 분리
    - Event Sourcing
    - Axon, Kafka 등의 프레임워크

# 핵심 설계 포인트
### 1.Soft CQRS 적용
- 쓰기(Command)와 조회(Query)를 패키지/모델/코드 수준에서 분리
- 데이터 베이스는 하나만 사용 (DB분리는 트래픽/성능 요구가 커졌을 때 선택하는 최적화 옵션이라 판단)

---
### 2.쓰기 모델 (Command)
- 쓰기 로직은 다음 흐름으로 처리
    - Command Controller
    - Application Service (Command Handler)
    - Domain Aggregate
- 비즈니스 규칙과 불변식은 도메인 메서드에 위치
- 모든 상태 변경은 트랜잭션 경계 안에서 처리

---
### 3.조회 모델
- 조회 로직은 쓰기와 완전히 분리
- 조회 결과는 JPA Entity가 아닌, Projection DTO로 직접 변환
- JdbcTemplate 기반 SQL 조회
    - 조회는 조인, 집계, 정렬 등 요구사항이 복잡해지기 쉬움
    - 최적화를 위해 SQL 기반 Projection 방식을 선택
- 상태 변경, 비즈니스 규칙, 트랜잭션 관리 모두 포함하지 않는다.
- 화면에 필요한 데이터를 효윹적으로 조회하여 반환하는 역할만 수행한다.

# 요청 처리 흐름
- 쓰기 흐름
```json
Client
 → Command Controller
 → Application Service (Command Handler)
 → Domain Aggregate
 → JPA Repository
```

- 조회 흐름
```json
Client
→ Query Controller
→ Query DAO (JdbcTemplate / QueryDSL)
→ Projection DTO
```

# 패키지 구조
```json
com.example.cqrs
 ├─ command
 │   ├─ api            // Command Controller
 │   ├─ application    // Command Handler / UseCase
 │   ├─ domain         // Aggregate 및 도메인 로직
 │   └─ infra          // JPA Repository, 영속성 구현
 ├─ query
 │   ├─ api            // Query Controller
 │   ├─ dao            // 조회 전용 DAO
 │   └─ dto            // Projection DTO
 └─ common
     ├─ exception
     └─ config

```

# 기술 스택
- java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- JDBC (JdbcTemplate)
- H2 Database
- Gradle

