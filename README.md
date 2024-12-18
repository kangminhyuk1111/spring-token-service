# 토큰 로그인 서버 구현
로그인 -> return authcredential -> 헤더에 jti랑 accesstoken 물고오기 -> jti로 찾은 authcredential 내부에 accesstoken 검증
accesstoken 유효하면 인가시킴 -> accesstoken이 유효하지 않으면 refreshtoken 체크 -> jti로 찾은 authcredential에 refresh 토큰과 사용자 토큰 일치 확인
일치한다면 새로운 authcredential 발급 -> 다시 요청할 때, 클라이언트가 새로운 accesstoken으로 요청 보내길 기대