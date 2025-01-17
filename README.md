# 509 프로젝트

## 🏁 프로젝트 기획 및 소개
- 내 주변 오프라인 마트에서 물품의 공동구매를 원하는 소비자들을 모아주는 플랫폼
- 빠른시간 내에 소량구매를 원하는 소비자가 타겟인 서비스

## 👨‍👩‍👧‍👦 Our Team
|권이슬|김나영|박대현|이가연|최원용|
|:-:|:--:|:---:|:---:|:---:|

## 🏆 API 명세

### 1️⃣ Market
<img src="src/main/resources/assets/market.png" width="1100">

### 2️⃣ Notification
<img src="src/main/resources/assets/notice.png" width="1100">

### 4️⃣ Chat
<img src="src/main/resources/assets/chat.png" width="1100">

### 5️⃣ Party
<img src="src/main/resources/assets/party_1.png" width="1100">
<img src="src/main/resources/assets/party_2.png" width="1100">

### 6️⃣ Penalty
<img src="src/main/resources/assets/penalty.png" width="1100">

### 7️⃣ Item
<img src="src/main/resources/assets/item.png" width="1100">

### 8️⃣ Auth
<img src="src/main/resources/assets/auth.png" width="1100">

### 3️⃣ User
<img src="src/main/resources/assets/user.png" width="1100">

## 📋 ERD Diagram
<img src="https://file.notion.so/f/f/83c75a39-3aba-4ba4-a792-7aefe4b07895/1ef21ce0-8ab7-40db-9d8c-b986774137ca/ERD.png?table=block&id=4bb354ff-0c71-4fe9-b417-adcc1bd47b55&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&expirationTimestamp=1730944800000&signature=4l4OSwH8ZhEFGM00w0UM_F2LxMVRaZv4wPrvSm8Etds&downloadName=ERD.png" width="1100">

## 📋 와이어프레임
<img src="https://velog.velcdn.com/images/abxl/post/b61b59bd-f211-427a-ad57-5e5b5899daeb/image.png" width="1100">
<img src="https://velog.velcdn.com/images/abxl/post/525ff361-fe0d-49e0-a697-8a40c8d2ede6/image.png" width="1100">

## 🎈 인프라 설계도
<img src="https://file.notion.so/f/f/83c75a39-3aba-4ba4-a792-7aefe4b07895/05aaa232-1217-463c-8935-d5a138ee08f9/image.png?table=block&id=49c98eed-ea92-42da-a6f4-6a1ef8055cbd&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&expirationTimestamp=1730944800000&signature=M-i7x0Sgt8hobb9CIkTd3E4YYtkEp1YpeL31Pznc8XA&downloadName=image.png" width="1100">

## 🕹️ 프로젝트 핵심기능

### 🔔 채팅
> * WebSocket과 STOMP를 사용하여 실시간 채팅기능 구현
> * 사용자가 채팅방 입장시, 입장전 오고갔던 메세지 확인 가능

### 🔔 알림
> * 이벤트 발생시 SSE를 이용하여 실시간, 지속적인 알림 기능 제공
> * 단방향 서비스를 제공하여 리소스 부담이 적고 구현이 간단

### 🔔 품목 업데이트
> * 더미 데이터가 아닌 공공데이터를 통해 실제 사용되는 품목 데이터를 이용
> * Spring Batch 와 Webclient 를 통해 데이터를 동기방식으로 병렬처리

## ❓ Trouble Shooting

### 파티
- 문제상황
  - PartyResponse 클래스에서 DateTimeFormatter를 static 필드로 선언된 것을 발견, 이를 제거한 후 포스트맨에서 formatter 객체 정보까지 응답에 포함되는 문제가 발생.
- 해결방안
  - DateTimeFormatter를 생성자 내부로 이동시켜, static 필드로 선언하지 않고 생성자 내에서만 사용하는 방식으로 변경.

### 페널티
- 문제상황 
  - 페널티 이모지 표기 시 페널티 테이블에서 해당 사용자의 최근 3개월 내의 페널티 부여 내역을 조회해야 함
  - 이때, 해당 조건을 만족하는 쿼리메서드를 사용하고도 추가적으로 내역의 기간에 대한 처리가 필요해 관리 리소스가 커지는 문제가 발생
- 해결방안 
  - 페널티 내역의 조회 가능 상태를 추가하여 3개월이 지난 내역의 상태는 조회 불가 상태를 줌
  - 페널티 내역 조회 시 조회 가능한 상태인 3개월 내의 부여 내역만 조회되도록 처리

### WebSocket과 Security병합
- 문제상황
  - 채팅기능 구현코드를 데모버전으로 만들었을때 정상작동했지만, 팀원코드와 병합하면서 Security 인증문제 발생
- 해결방안
  - WebSocket과 STOMP관련 경로들을 Security설정에서 permit

### CORS정책 충돌
- 문제상황
  - WebSocket에서 설정된 allowCredentials(true)로 인해, 특정 자격증명있는 요청만 허용하도록 설정
  - 하지만 setAllowedOrigins("*")이 모든 자격증명있는 요청을 허용하기 때문에 CORS 정책 충돌 발생 
- 해결방안
  - setAllowedOriginPatterns(allowedOrigin)으로 수정함으로써, 정책충돌 해결 및 특정 도메인 주소를 지정함으로써 보안 강화

### 채팅 JWT인증
- 문제상황
  - 채팅기능 사용시, Spring Security뿐만 아니라 JWT인증 과정도 필요함을 인지
  - HandShakerInterceptor와 ChannelInterceptor의 특성을 파악한 후 적절한 방법을 선택 필요
- 해결방안
  - 메세지마다 인증하는 ChannelInterceptor 방법은 현재 프로젝트 로직상 너무 인증이 잦고, 성능상 오버헤드 염려
  - 초기 연결시 인증으로 해결할 수 있는 HandShakerInterceptor 사용으로 해결 

### API 요청 데이터 제한 & 처리지연
- 문제상황
  - API 서버에서 데이터요청 회당 1000건 제한으로 여러번 요청필요, 직렬 동기처리로 지연 발생
  - 데이터파싱과 DB 저장시 처리시간 지연 
  - 중복검증단계에 MySQL DB 접근으로 처리시간 지연
- 해결방안
  - API 요청 라이브러리 RestTemplate 에서 WebClient 로 변경하여 요청을 동기방식에서 비동기방식으로 변경
  - Spring Batch 도입해 데이터 파싱과 DB 저장을 청크로 묶어 병렬처리
  - 데이터 중복 확인을 위해 검색이 빠른 Hash Set 자료구조 이용

## 📝 Technologies & Tools 📝
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> 
<img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/> 
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"/> 
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"/> 
<img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=for-the-badge&logo=IntelliJIDEA&logoColor=white"/> 
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/>
<img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"/>
<img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white"/>
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/>
<img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/> 
<img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"/>

## 링크
### 팀노션 
* https://www.notion.so/teamsparta/6-93f92b3fa6bb4f04848b0c0079992349
