package jwttest;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Author Rem
 * @Date 2019-06-23
 */

public class CreateTest {

    public static void main(String[] args) {
        JwtBuilder builder = Jwts.builder()
                .setId("123")
                .setSubject("小明")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 300000))
                .claim("roles","admin")
                .signWith(SignatureAlgorithm.HS256, "itcast");
        System.out.println(builder.compact());
    }
}
