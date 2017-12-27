package utils;

import domain.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.mindrot.jbcrypt.BCrypt;

import java.security.Key;
import java.util.Date;

public class SecurityHelper {

    public static String generateSessionToken(String email) {

        Date expires = new Date(new Date().getTime() + 120000);
        Key key = MacProvider.generateKey();
        String compactJws = Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, key)
                .setExpiration(expires)
                .compact();

        return compactJws;
    }

    public static boolean checkPassword(String dtoPass, String userPass){

        return BCrypt.checkpw(dtoPass, userPass);
    }

    public static String generatePassword(String password){

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
