
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
  - 별도 처리된 화면 확인 위해 thymeleaf로 index 기본적 html만 추가 했습니다.
    
- 테스트 코드 작성
    - 대부분 작업을 테스트 코드를 작성하며 진행했습니다.
    - 시간이 부족했지만 기능의 이해를 돕고, 작업을 확인 할 수 있을 거 같아 테스트 코드 작성을 위해 노력했습니다.

* SecurityJavaConfig
  - SpringSecurity 이용한 가입, 로그인, 로그아웃을 원하진 않으시는 것 같아서 설정을 조금씩 바꾸며 만들어 봤습니다.
  - 실제 DB 확인 작업시 오류가 생겨 cors, csrf원인으로 diable 하여 작업했습니다. (실제 커머스에선 안돼지만, 기본적 DB 데이터를 확인 할 수 있도록 작업하고자 했습니다.)

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
    - 암호화된 토큰을 어떻게 만들지 고민이 많았습니다.
      기본적인 방식부터 우선 작업 한 것으로 사용자의 패스워드 일부를 토큰으로 이용하도록 했습니다.
      (실제 쓰이면 안 되는 방식입니다..)
      
#### 개별적 추가해 본 세부 기능들
- email 등록
    - email 도메인 정보들 DB 보관
        - ex ) @gmail.com에서 @제외한 gmail.com만을 보관
    - 상품 등록하는 partner들의 회사 도메인 등 추가 서비스 연계 가능성
    - 회원 가입시 사용자들이 입력한 메일 주소의 패턴 형식들을 기본적으로 FE에서 처리 하고, DB 연계된 로직을 추가 해봤습니다.
  - #### Exception
         WrongDomainException : 보통 "@"까지 입력하는 실수가 잦을 것 같아, 제외시킬 수 있도록 예외 발생 처리 했습니다.
         InvalidEmailException : 기본적인 메일 형식 체크 방식
            - gmailcom 또는 gmailc.om 등의 기본적 형식을 맞추도록 작업 했습니다.
    

      