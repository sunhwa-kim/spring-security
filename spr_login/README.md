
### Spring Security 이용한 회원 가입, 로그인 세션


#### 환경
- Gradle, jdk11, JPA, H2DB
#### 이용
* inMemory 기반 DB 환경 
    - jdbc:h2:~/test 파일 생성 
    - jdbc:h2:tcp://localhost/~/test 접속
    
* 화면 : SecurityJavaConfig
    - "/","/register/**" : 첫 화면, 가입화면 다수 접근
    - "/users","/session", etc.. : 그 외 화면들 인증 요구
    
- 테스트 코드 작성

* SecurityJavaConfig
  - 실제 DB 확인 작업시 오류막고자 cors, csrf원인으로 diable 하여 작업했습니다.

#### packages
- common : 관리자에 의한 api로 기본 사용자 등록, 추가, 수정, 삭제

- register : 회원 가입

- session : 로그인, 로그아웃

#### 기본적으로 주요하게 넣은 기능들
- password 암호화
    - spring-security에서 제공하는 default로 BCryptPasswordEncoder를 이용했습니다.
  - DB로 암호화 되어 저장된 패스워드를 확인 할 수 있습니다.
    
- session
  - 로그인 된 사용자들을 지속적으로 인식 할 수 있게 합니다.
    
- accessTocken
    - 기본적인 방식부터 우선 작업 한 것으로 사용자의 패스워드 일부를 토큰으로 이용하도록 했습니다.
    

      