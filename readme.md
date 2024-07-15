# **LOOK Shop**

나만의 웹 사이트 개발 프로젝트

Site - ~~[https://lookshop.cf/](https://lookshop.cf/)~~ (현재는 운영 X)

Front - [https://github.com/coorr/shopping-front](https://github.com/coorr/shopping-front)


## **개발 환경**

- IntelliJ
- Visual Studio Code
- Postman
- GitHub
- PostgreSQL
- Heidisql

<br />

## **사용 기술**

### **백엔드**

### **주요 프레임워크 / 라이브러리**

- Java 11 openjdk
- SpringBoot
- SpringBoot Security
- Spring Data JPA

### **Build tool**

- Gradle

### **Database**

- PostreSQL

### **Infra**

- AWS EC2
- AWS S3
- ~~Travis CI~~
- Github Actions
- AWS CodeDeploy
- AWS Route53

### **프론트엔드**

- React
- CSS, SCSS
- Bootstrap

### **기타 주요 라이브러리**

- Lombok
- Jwt
- React-daum-postcode

<br />

## **핵심 키워드**

- 로그 추적기(AOP) 및 오류발생(Sentry) 에러트래킹
- Mockito 단위테스트 100개
- JPA, Hibernate 사용한 도메인 설계
- AWS/리눅스 기반 CI/CD 무중단 배포 인프라 구축
- MVC 프레임워크 기반 백엔드 서버 구축
- 기획부터 배포 유지 보수까지 전과정 개발과 운영 경험 확보

<br />

## 시스템 아키텍처

![image](https://github.com/coorr/Algorithm/blob/main/img/shop/shop_architecture.png)

<br />

## ****E-R 다이어그램****

![image](https://github.com/coorr/Algorithm/blob/main/img/shop/shop_erd.png)

<br />

## ****프로젝트 목적****

여러가지 강의와 책을 통해 머릿속에 다양한 프로젝트(블로그, 커뮤니티, 쇼핑몰 등)

많은 기획안이 생각났습니다. 그 중에서 쇼핑몰을 선택하게 된 이유는 각양각색 기능들이 있고

복잡한 관계형 DB, 알림, 광고, 서비스, 운영 등 여러가지 경험을 할 수 있다고 생각하여

만들어 보기로 결정하였습니다.

웹 개발자로서 웹 애플리케이션의 전 과정에 대해 최소 한번쯤은 숙지를 해야한다고 판단하여,

로컬이 아닌 실제 운영 서버를 돌려서 실제 사용자들이 사용할 수 있게 하였습니다.

<br />
<br />

## 핵심 기능

### **단위 테스트**

개발한 코드들을 수시로 빠르게 검증을 받을 수 있고, 기능을 수정하거나 리팩토링을 할 때에
검증을 받을 수 있는 단위 테스트를 진행하였습니다. Controller, *Service, *Repository 각각 단위
메서드를 테스트 하기 위해 `Mockito` 활용하였습니다.

**[Junit Mock 사용하기](https://coor.tistory.com/30)**

<br />

### JPA Pagination을 이용한 무한 스크롤

메인화면의 경우 최신 게시물 순으로 12개씩 끊어서 스크롤 감지에 따라 다음 게시물들을 조회하는 무한 스크롤 페이징을 구현했으며,

동적으로 페이지네이션 조건을 적용하기 위해서 가장 작은 `lastId` 값을 구해서 항상 0번째 페이지를 가져오도록 합니다.

페이지 번호를 바꾸는 대신, 페이지네이션의 조건을 매번 동적으로 바꿔주었습니다.

<br />

### 관리자 권한 CRUD

게시글 작성, 수정, 삭제의 경우 관리자 권한을 가진 계정만 인가하여 권한을 제한하였고
특정 URL 요청 시 `관리자 권한`을 가진 계정만 할 수 있도록 시큐리티 설정 하였습니다.

![image](https://github.com/coorr/Algorithm/blob/main/img/shop/shop_admin.png)

[관리자 계정에서만 보이는 글 수정 삭제 버튼]

![image](https://github.com/coorr/Algorithm/blob/main/img/shop/shop_admin_revised.png)

[관리자 계정에서만 보이는 글 수정 화면]

<br />

### **로그 추적기**

스프링 `AOP` 기술을 사용하여 프로젝트의 ***Controller, *Service, *Repository** 에 포인트컷을 지정,
로그를 찍고 요청별로 로그를 추적하기 위해 쓰레드 로컬을 사용하여 로그 추적기를 구현 및
운영서버에 기능을 추가하였습니다.

![image](https://github.com/coorr/Algorithm/blob/main/img/shop/shop_errlog.png)

[EC2 서버에서 저장된 로그를 실제로 확인하는 모습]

![image](https://github.com/coorr/Algorithm/blob/main/img/shop/shop_log.png)

로그 구조는 각 요청별로 쓰레드의 고유 난수번호가 찍히게 되어있으며, 각 계층별 호출에 따른 깊이표현과 응답속도가 표시되도록 되어있습니다.

또한 롤링 정책을 하루단위로 발행하여 로그파일 관리를 편리하게 했으며, 에러발생시 해당 로그를 바로 캐치하여 별도의 로그파일에 기록하도록 하고

서버 장애 발생시 로그 확인을 훨씬 쉽게 하였습니다.

![image](https://github.com/coorr/Algorithm/blob/main/img/shop/shop_sentry.png)

EC2 에러 로그를 SSH 명령어를 입력하여 들어가서 확인했으나

보다 편리하고 빠르게 이용할 수 있는 `Sentry` 이용하여 에러를 확인할 수 있게 **리팩토링** 하였습니다.

<br />

### 반응형 웹

부트스트랩을 이용하여 작은 모바일 환경은 물론 태블릿 대형 화면에서도 문제없이 작동하는
반응형 웹을 구현하였습니다.


![image](https://github.com/coorr/Algorithm/blob/main/img/shop/shop_mobile.PNG)


<br />

### **SEO 최적화 - 도메인과 https 프로토콜**

배포된 웹 앱을 제대로 서비스하기위해 `letsencrypt`로 ssl 인증을 받아 `https` 보안 프로토콜을
적용했으며, `freenom`에서 도메인을 등록하고 AWS Route 53을 이용하여 도메인을 연결하였습니다.

**[NGINX + HTTPS 적용하기](https://coor.tistory.com/31)**

<br />

### Github 액션으로 스택 마이그레이션

(진행중)

트래비스가 org에서 com으로 변경된뒤 기존 무료 정책에서 구독형 유료 모델로 변경되었으며
무료 credit을 모두 사용한 후 월 요금 30달러를 지불해야 하는 상황을 맞이했습니다.

개인적으로 개인프로젝트에서 서버도 아니고 CI 툴을 사용하기 위해 비용을 지불하는것은
불필요하다고 판단했고, 코드 관리가 Github에서 이루어지는 만큼

CI 역시 같은 Github내에서 진행되는것이 보다 바람직하다고 판단하여 `Github Actions`로 스택마이그레이션을 진행하고 있습니다.
