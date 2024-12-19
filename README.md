# 토큰 로그인 서버 구현
1. 클라이언트가 서버에게 회원가입 요청 보냄
2. 회원가입 요청을 받은 서버는 검증 후, 문제가 없다면 회원가입 성공 응답
3. 암호는 BCryptPasswordEncoder에 의해 암호화 됨
4. 로그인 성공시, 유저는 AuthCredential을 받음
5. AuthCredential 객체는 내부에 jti, accessToken, refreshToken을 가짐 
6. jti는 jwt 토큰의 고유 식별자로 사용됨 - Jwt Token Id
7. header에 가지고 온 accessToken의 payload에 jti, email등 정보를 담음
8. 만약 accessToken 내부의 expiration이 만료 시간을 초과한 경우 exception을 보냄
9. exception을 받은 클라이언트는 refreshToken으로 재요청을 함
10. 클라이언트는 재요청시 refreshToken을 body에 가지고 요청함
11. body에 담겨온 refreshToken이 올바르다면, AuthCredential을 다시 발급함
12. 새로 저장된 AuthCredential을 통해 로그인 성공 여부 인가 여부를 판단함

## 객체 설명
- JwtAuthenticationFilter: 스프링 내부적으로 처리하기 전, http 요청을 처리하는 미들웨어. OncePerRequestFilter를 상속받으며, filter 통과시 웹 서버로 요청을 정상적으로 보냄

- AuthCredential: jti, accessToken, refreshToken을 프로퍼티로 가지는 객체

- WhiteListUrl: uri를 판단하여 서버에서 허용하는 uri인지 판단하는 객체

- JtiProvider: jti를 발급하는 인터페이스, 현재 UUID로 발급되는 jti키를 추후 변경성을 위해 추상화시킴

- TokenProvider: 토큰 생성과 토큰 파싱을 통해 토큰 정보를 전달하는 객체. 현재 jjwt로 구현된 구현체에서 추후 변경성을 위해 추상화시킴

- KeyProvider: jwt를 생성하고 파싱하기 위한 SecretKey를 발급하는 객체. 현재 application.yml에 저장된 키를 추후 변경성을 위해 추상화시킴

- GlobalExceptionHandler: 스프링 서버에서 발생하는 exception을 잡아서 처리해주는 전역 예외처리 객체, 스프링 빈에 등록되어 전역적으로 사용됨

- ErrorCode: 커스텀 예외를 처리하기 위해 Enum class 사용으로 재사용성을 높임

- PasswordConfig: PasswordEncoder 구현체 중 하나인 BCryptPasswordEncoder를 빈으로 등록하여 비밀번호를 암호화하고 검증하는 데 사용됨

- MemberRepository, JwtRepository: 현재 인메모리 DB를 사용중이고 추후 메모리 DB 혹은, RDB로의 전환을 위해서 추상화 시킴