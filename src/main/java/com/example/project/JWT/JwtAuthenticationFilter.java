package com.example.project.JWT;

import com.example.project.DAO.MemberDAO;
import com.example.project.service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;
@Order(0)
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest,
                                    HttpServletResponse httpResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = "";
        String username = "";
        try {
            token = parseBearerToken(httpRequest); // 토큰을 가져온다
            username = parseUserSpecification(token); // 토큰으로 사용자 정보를 가져온다
            UserDetails userDetail = userDetailsService.loadUserByUsername(username); // db에서 사용자 정보를 가져온다
            /** UsernamePasswordAuthenticationToken
             *  사용자 인증 정보를 담고있는 토큰 객체
             *
             *  userDetail : 사용자의 정보가 담긴 객체
             *  "" : 비밀번호(실제 비밀번호 검증은 AuthenticationManager가 하기 때문에 빈 문자열로 동적으로 처리)
             *  userDetail.getAuthorities() : 사용자의 권한
             */
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetails(httpRequest)); // 추가적인 인증 세부 정보를 설정
            SecurityContextHolder.getContext().setAuthentication(authentication); // 인증된 사용자의 정보를 SecurityContext에 저장
        }catch (Exception e) {
            token = parseBearerToken(httpRequest); // 토큰을 가져온다
            if (token != null) {
                String refreshToken = jwtUtil.refreshToken(token);
                // 재발급된 토큰을 헤더에 넣어준다.
                httpResponse.addHeader(HttpHeaders.AUTHORIZATION, refreshToken);
            }
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }

    private String parseBearerToken(HttpServletRequest httpRequest) {
        return Optional.ofNullable(httpRequest.getHeader(HttpHeaders.AUTHORIZATION)) // http 요청에서 AUTHORIZATION헤더를 가져온다
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer ")) // Bearer로 시작하는지 확인
                .map(token -> token.substring(7)) // 실제 토큰값 추출
                .orElse(null); // 해당없으면 null
    }

    private String parseUserSpecification(String token) {
        String username = jwtUtil.validateToken(token);
        return username;
    }
}
