# error
- ~구글 , 네이버 api 로그인~
- 도메인 연결
- intellij git 연동 commit 잔디 문제
      (github commit 은 제대로 반영)
- ~github commit -> code Deploy to EC2 instance error~
# 추가
- ~배포~ 
- ~자동 배포~
- chatgpt api 등록
- 자동 기록 저장
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

# 다이어그램
<p align="center">
  <img src="[이미지URL](https://github.com/hiyigh/blog/issues/1#issue-2070145581)">
</p>

# 트러블 슈팅
---              
- 소셜 로그인
  - Oauth2 로그인, 기존 로그인 token 처리
  - google 로그인 token : secret access key (관련 키 재발급)
 
- EC2
  - 무한 로딩 : memory swap
  - bash 언어 : \
- github 
  - 잔디 -> 사용자 정보 전달 문제 ? 
- AWS
  - 접속 실패 : 로컬은 문제 없이 동작, dns 접속만 안됨 -> ec2 public dns 연결 문제 -> 포트 번호 설정 문제
- Deploy
  - DeploymentLimitExceededException -> deploy log 확인 -> Missing credential -> IAM 역할 문제? -> EC2 IAM role 미부여 -> EC2 등록 후 재배포, 재실행 (언제 잘 못된건지) 
