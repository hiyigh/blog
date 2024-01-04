# Error
- ~구글 , 네이버 api 로그인~
- 도메인 연결
- intellij git 연동 commit 잔디 문제 ??
      (github commit 은 제대로 반영)
# 추가
- ~배포~ 
- ~자동 배포~ 
- 무중단 배포
# Project
---

### 목적
웹 개발을 목적으로 하는 개발자로서 전반적인 과정에 대한 숙지가 필요하다 생각했고, 앞으로 개인적인 기록을 남기기 위한 블로그를 만들기로 하였습니다. 

### 개발 환경
- Intellij
- Github
- Mysql bench

### 주요 라이브러리
- Java 11
- Spring Boot  2.5.6 version
- Spring Boot Security
- Spring Jpa
- Mybatis

### Build Tool
- Gradle

### DataBase
- MariaDB

### Infra
- AWS EC2
- AWS RDS
- AWS S3
- AWS Route53

### 기타
- Lombok
- Toast Ui Editor

# 구현
---

- 게시글 구현과 작성 권한 부여
    - 게시글 CRUD 를 구현하고
    spring boots security 라이브러리를 사용하여
    admin 권한을 가진 사용자만 게시글을 작성할 수 있도록 하였습니다.
- 댓글과 하위 댓글 구현
    - 셀프 조인 참조로 댓글 간 계층을 나타낼 수 있도록 하였고,
      해당 변수를 사용한 재귀적 방식으로 트리 구조 객체를 반환하도록 설계하였습니다.
        
- 카테고리 구현
    - 기초적인 카테고리 구현하였으며,        
      cascade 구문을 추가하여 카테고리 삭제시 게시글도 같이 삭제되도록 구현하였습니다.
        
- 소셜 로그인
    - Oauth2 인증 방식과 Spring Boots Security 를 사용하여 전달 받은 토큰으로 사용자를 인증할 수 있도록 하였습니다.
    - 새로운 소셜 로그인 api 추가를 위해 interface 를 사용하여 객체 생성과, 유저 정보를 전달받을수 있도록 하였습니다.
- 자동 저장 기능
    - 글 작성 시 AJAX 요청을 통해 임시 저장할 수 있는 기능을 추가하였습니다.
- 캐싱
    - 고정된 카테고리 목록, 메인 화면상의 게시글을 매 번 호출 하는 것은 비효율적이기 때문에 EhCache 라이브러리를 사용하여 캐시 처리하였고, 변동이 생기는 경우 캐시가 삭제되도록 하였습니다.
- 태그 , 검색 기능
    - 검색어로 데이터를 불러오기 위해 like % 쿼리를 작성하여 검색 기능을 만들었습니다.
    - 태그의 다른 기능들은 tagify 라이브러리를 사용하였습니다.
- 페이징
    - 페이징 처리를 위해 페이징 박스 핸들러 클래스 작성하였고, 
    해당 클래스를 사용하여 현재 위치와 페이지 박스 숫자를 가져와 게시글 목록을 조회할 수 있도록 구현하였습니다.
