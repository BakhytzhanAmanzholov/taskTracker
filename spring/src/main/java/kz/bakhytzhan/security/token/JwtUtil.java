package kz.bakhytzhan.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.bakhytzhan.security.models.Person;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
// This is a class for Token, here we prescribe its properties.
@Service
public class JwtUtil {
    private String SECRET_KEY = "secret";
    public String generateToken(Person user) {
        Map<String, Object> claims = new HashMap<>();
        return  createToken(claims, user.getEmail());
    }
    public String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt
                (new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).
                signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
}
