# wanted-pre-onboarding-backend

## 지원자의 성명
- 정세훈 (Sehun Jeong)

## 애플리케이션의 실행 방법
1. Spring Boot 애플리케이션을 실행합니다.
2. 다음 엔드포인트를 호출하여 API를 사용합니다:
    - 회원가입: `POST http://localhost:8090/join`
    - 로그인: `POST http://localhost:8090/login`
    - 게시글 작성: `POST http://localhost:8090/write`
    - 게시글 목록 조회: `GET http://localhost:8090/boardList/{page}`
    - 게시글 상세 조회: `GET http://localhost:8090/boardDetail/{id}`
    - 게시글 수정: `PUT http://localhost:8090/boardEdit/{id}`
    - 게시글 삭제: `DELETE http://localhost:8090/boardDelete/{id}`


## 데이터베이스 테이블 구조
- `Member`: 사용자 정보를 저장합니다. 주요 컬럼으로는 `email`, `password`, `roles`가 있습니다.
- `Board`: 게시글 정보를 저장합니다. 주요 컬럼으로는 `num`, `title`, `content`, `writer`가 있습니다.
- `member_roles`: 사용자의 권한 정보를 저장합니다. `Member` 테이블과 연관되어 있습니다.

## 구현한 API의 동작을 촬영한 데모 영상 링크
- [데모 영상 링크](https://youtu.be/nHT4MR504xc)

## 구현 방법 및 이유
- Spring Boot와 JPA를 사용하여 웹 애플리케이션의 백엔드를 구현하였습니다.
- Lombok 라이브러리를 사용하여 코드의 중복을 줄이고 가독성을 향상시켰습니다.
- JWT를 사용하여 사용자 인증과 권한 부여를 처리하였습니다.
- 데이터 유효성 검사를 위해 Java Validation API를 활용하였습니다.

## API 명세

### 회원가입
- **Endpoint**: `POST http://localhost:8090/join`
- **Request**:
    - email: String
    - password: String
- **Response (성공 시)**:
    - 메시지: String (예: "회원가입 성공!")
- **Response (실패 시)**:
    - 이메일 유효성 검사 실패:
      - error: String (예: "올바른 이메일 형식을 입력해주세요.")
    - 비밀번호 유효성 검사 실패:
      - error: String (예: "비밀번호를 8자 이상 입력해주세요.")

### 로그인
- **Endpoint**: `POST http://localhost:8090/login`
- **Request**:
    - email: String
    - password: String
- **Response (성공 시)**:
    - tokenInfo:
      - grantType: String ("Bearer")
      - accessToken: String (게시글 작성, 수정, 삭제시 이 토큰을 Headers에 사용)
      - refreshToken: String
- **Response (실패 시)**:
      - 이메일 유효성 검사 실패:
        - error: String (예: "올바른 이메일 형식을 입력해주세요.")
      - 비밀번호 유효성 검사 실패:
        - error: String (예: "비밀번호를 8자 이상 입력해주세요.")
      - ID 혹은 비밀번호 불일치:
        - error: String (예: "자격 증명에 실패하였습니다.")
  
### 게시글 작성
- **Endpoint**: `POST http://localhost:8090/write`
- **Headers**:
    - Authorization: Bearer `your_token`
- **Request Body**:
    - title: String (게시글 제목)
    - content: String (게시글 내용)
- **Note**: 작성자는 JWT 토큰에서 자동으로 추출됩니다.
- **Response (성공 시)**:
    - 메시지: String (예: "게시글 작성 성공!")

### 게시글 목록 조회
- **Endpoint**: `GET http://localhost:8090/boardList/{page}`
- **Request Parameters**:
    - page: Integer (페이지 번호)
    - reqCnt: Integer (선택사항, 요청할 게시글 수)
- **Response**:
    - list: Array of
      - num: Integer (게시글 번호)
      - title: String (게시글 제목)
      - content: String (게시글 내용)
      - writer: String (작성자의 이메일 주소)

### 게시글 상세 조회
- **Endpoint**: `GET http://localhost:8090/boardDetail/{id}`
- **Request Parameters**:
    - id: Integer (게시글 번호)
- **Response**:
    - num: Integer (게시글 번호)
    - title: String (게시글 제목)
    - content: String (게시글 내용)
    - writer: String (작성자의 이메일 주소)

### 게시글 수정
- **Endpoint**: `PUT http://localhost:8090/boardEdit/{id}`
- **Headers**:
    - Authorization: Bearer `your_token`
- **Request Parameters**:
    - id: Integer (게시글 번호)
- **Request Body**:
    - content: String (수정할 내용)
- **Response**:
    - board:
      - num: Integer (게시글 번호)
      - title: String (게시글 제목)
      - content: String (수정된 게시글 내용)
      - writer: String (작성자의 이메일 주소)

### 게시글 삭제
- **Endpoint**: `DELETE http://localhost:8090/boardDelete/{id}`
- **Headers**:
    - Authorization: Bearer `your_token`
- **Request Parameters**:
    - id: Integer (게시글 번호)
- **Response (성공 시)**:
    - 메시지: String (예: "게시글 삭제 성공")


