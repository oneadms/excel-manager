package omg.excelmanager.jwt;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import omg.excelmanager.model.entity.User;
import omg.excelmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class jwtUtil {
    public final static String USER_NAME = "USER_NAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String USER_TYPE = "USER_TYPE";
    private final static String SECRET_KEY="Make China Great Again";
    private final static long EXPIRATION_TIME=3600_000_000L;
    public final static String AUTH_NAME = "Token";
    public static String generateToken(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put(USER_NAME, user.getUsername());
        map.put(PASSWORD, user.getPassword());
        map.put(USER_TYPE, user.getUserId());

        String token = Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
        return token;
    }

    public static     Map<String, Object>  validateToken(String token) {
       return  Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

    }
}
