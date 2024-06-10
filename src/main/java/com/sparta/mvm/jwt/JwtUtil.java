package com.sparta.mvm.jwt;

import com.sparta.mvm.exception.ErrorEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // Header KEY 값
    public static final String ACCESS_TOKEN_HEADER = "AccessToken";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";

    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 30 * 1000L; // 1000 = 1초
    private final long REFRESH_TOKEN_TIME = 60 * 60 * 24 * 14 * 1000L;

    // Base64 Encode 한 SecretKey
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String username, long setExpirationTime) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .setExpiration(new Date(date.getTime() + setExpirationTime)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String createRefreshToken(String username) {
        return createToken(username, REFRESH_TOKEN_TIME);
    }

    public String createAccessToken(String username) {
        return createToken(username, TOKEN_TIME);
    }

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res, String headerName) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(headerName, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }


    public void initJwtCookie(HttpServletResponse res) {

        Cookie cookie = new Cookie(REFRESH_TOKEN_HEADER, ""); // Name-Value
        Cookie cookie2 = new Cookie(ACCESS_TOKEN_HEADER, "");
        cookie.setPath("/");
        cookie2.setPath("/");

        // Response 객체에 Cookie 추가
        res.addCookie(cookie);
        res.addCookie(cookie2);

    }


    public void addRefreshJwtToCookie(String token, HttpServletResponse res) {
        addJwtToCookie(token, res, REFRESH_TOKEN_HEADER);
    }

    public void addAccessJwtToCookie(String token, HttpServletResponse res) {
        addJwtToCookie(token, res, ACCESS_TOKEN_HEADER);
    }


    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // 토큰 검증
    public void validToken(String token, JwtTokenType jwtTokenType, HttpServletRequest request) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            request.setAttribute("NOT_VALID_TOKEN", ErrorEnum.NOT_VALID_TOKEN);
            throw new RuntimeException("유효하지 않는 토큰 오류");
        } catch (ExpiredJwtException e) {
            if (jwtTokenType.equals(JwtTokenType.ACCESS_TOKEN)) {
                request.setAttribute("EXPIRED_TOKEN", ErrorEnum.EXPIRED_TOKEN_VALUE);
                throw new RuntimeException("만료된 토큰 오류");
            } else {
                request.setAttribute("EXPIRED_TOKEN", ErrorEnum.EXPIRED_REFRESH_TOKEN_VALUE);
                throw new RuntimeException("만료된 리프레시 토큰 오류");
            }
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("기타");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("기타");
        }
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // HttpServletRequest 에서 Cookie Value : JWT 가져오기
    public String getTokenFromRequest(HttpServletRequest req, String headerName) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(headerName)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public String getAccessTokenFromRequest(HttpServletRequest req) {
        return getTokenFromRequest(req, ACCESS_TOKEN_HEADER);
    }

    public String getRefreshTokenFromRequest(HttpServletRequest req) {
        return getTokenFromRequest(req, REFRESH_TOKEN_HEADER);
    }
}