package jwttest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @Author Rem
 * @Date 2019-06-23
 */

public class PraseJwtTest {

    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiLlsI_mmI4iLCJpYXQiOjE1NjEyOTg5ODcsImV4cCI6MTU2MTI5OTI4Nywicm9sZXMiOiJhZG1pbiJ9._0l-jh-GyBdmVIZIcFZI0C82lUM9JDD9a4IreFjLrsI")
                .getBody();

        System.out.println("id:" + claims.getId());
        System.out.println("姓名:" + claims.getSubject());
        //System.out.println("登陆日期:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        //System.out.println("过期日期:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("角色:" + claims.get("roles"));
    }
}
