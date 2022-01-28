package omg.excelmanager.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import omg.excelmanager.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;
@Slf4j
public class JwtUtil {
    public final static String USER_ID = "USER_NAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String USER_TYPE = "USER_TYPE";
    private final static String SECRET_KEY="Make China Great Again";
    private final static long EXPIRATION_TIME=3600_000_000L;
    public final static String AUTH_NAME = "Token";
    public static String generateToken(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put(USER_ID, user.getUserId());
        String token = Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
        return token;
    }

    public static HttpServletRequest validateTokenAndAddUserIdToHeader(HttpServletRequest request) {
        String token = request.getHeader(AUTH_NAME);
        if (token != null) {
            // parse the token.
            try {
                Map<String, Object> body = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();
                return new CustomHttpServletRequest(request, body);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new TokenValidationException(e.getMessage());
            }
        } else {
            throw new TokenValidationException("Missing token");
        }
    }

    public static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private Map<String, String> claims;

        public CustomHttpServletRequest(HttpServletRequest request, Map<String, ?> claims) {
            super(request);
            this.claims = new HashMap<>();
            claims.forEach((k, v) -> this.claims.put(k, String.valueOf(v)));
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (claims != null && claims.containsKey(name)) {
                return Collections.enumeration(Arrays.asList(claims.get(name)));
            }
            return super.getHeaders(name);
        }

    }

    static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }
    public static     Map<String, Object>  validateToken(String token) {
       return  Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

    }
}
