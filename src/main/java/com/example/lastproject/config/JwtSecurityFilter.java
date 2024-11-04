package com.example.lastproject.config;

import com.example.lastproject.common.dto.AuthUser;
import com.example.lastproject.domain.user.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    /*
    OncePerRequestFilter 을 상속받아 매 요청마다 한 번만 필터링하도록 처리
    HTTP 요청의 헤더에서 JWT 를 추출하고 이를 검증하여 인증 정보를 설정
    JWT 에 대한 유효성 검사를 수행하고 발생하는 다양한 예외에 대해 적절한 HTTP 응답을 반환
    최종적으로 필터 체인을 계속 진행하여 다른 필터나 핸들러로 요청을 전달
     */

    private final JwtUtil jwtUtil;

    /**
     * OncePerRequestFilter 추상 클래스의 추상 메서드 구현체
     * HTTP 요청을 필터링하고 필요한 인증 절차를 수행
     * SecurityContextHolder 에 인증 정보를 설정
     *
     * @param httpRequest  Http 요청 객체
     * @param httpResponse Http 응답 객체
     * @param chain        다음 필터 또는 요청 처리기로 요청 전달
     * @throws ServletException Http 요청 처리 중 발생할 수 있는 예외
     * @throws IOException      입출력 처리 중 발생할 수 있는 예외
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest httpRequest,
            @NonNull HttpServletResponse httpResponse,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {

        // Authorization 헤더에서 JWT 를 추출
        String authorizationHeader = httpRequest.getHeader("Authorization");

        // JWT 가 "Bearer "로 시작하는지 판별
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            // JWT 문자열에서 실제 토큰만 추출
            String jwt = jwtUtil.substringToken(authorizationHeader);

            try {

                /*
                Claims - 페이로드 부분에 포함된 정보 -> 인증된 사용자에 대한 정보를 포함
                이 claims 를 JWT 에서 추출함
                 */
                Claims claims = jwtUtil.extractClaims(jwt);
                Long userId = Long.valueOf(claims.getSubject()); // 사용자 ID 추출
                String email = claims.get("email", String.class); // 사용자 email 추출
                UserRole userRole = UserRole.of(claims.get("userRole", String.class)); // 사용자 권한 추출

                /*
                  SecurityContext - 사용자의 인증 정보를 저장하는 컨테이너 & 특정 작업에 대한 접근 제어 수행

                  SecurityContextHolder - 이를 저장하고 관리하는 클래스
                  -> 현재 스레드에 대한 SecurityContext 저장, 조회 가능

                  SecurityContextHolder 가 비어있는지 판별하고 비어있다면 인증을 처리함
                 */
                if (SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

                    AuthUser authUser = new AuthUser(userId, email, userRole);

                    // authUser 정보를 담은 토큰 생성 및 SecurityContext 에 인증 정보 저장
                    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (SecurityException | MalformedJwtException e) {
                log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
            } catch (ExpiredJwtException e) {
                log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
            } catch (UnsupportedJwtException e) {
                log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
            } catch (Exception e) {
                log.error("Internal server error", e);
                httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }

}
