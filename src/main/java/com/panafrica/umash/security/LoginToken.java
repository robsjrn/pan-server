/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.security;

import com.panafrica.umash.configuration.Appconstants;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;    
import org.json.JSONObject;

public class LoginToken {
    private Appconstants  appconst;
    tokendetails tkn;
    public String createJWT(String id, String subject ) {
       long ttlMillis= Long.valueOf(appconst.getConfigdata().getTtlMillis());
       String issuer =appconst.getConfigdata().getTokenIssuer();
//The JWT signature algorithm we will be using to sign the token
SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

long nowMillis = System.currentTimeMillis();
Date now = new Date(nowMillis);

//We will sign our JWT with our ApiKey secret
byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(appconst.getConfigdata().getApikey());
Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

  //Let's set the JWT Claims
JwtBuilder builder = Jwts.builder().setId(id)
                                .setIssuedAt(now)
                                .setSubject(subject)
                                .setIssuer(issuer)
                                .signWith(signatureAlgorithm, signingKey);

 //if it has been specified, let's add the expiration
if (ttlMillis >= 0) {
    long expMillis = nowMillis + ttlMillis;
    Date exp = new Date(expMillis);
    builder.setExpiration(exp);
}

 //Builds the JWT and serializes it to a compact, URL-safe string
return builder.compact();
}
    
    public tokendetails parseJWT(String jwt) {
       tkn = new   tokendetails();
       
      boolean  status= true;
//This line will throw an exception if it is not a signed JWS (as expected)

try {
Claims claims = Jwts.parser()         
   .setSigningKey(DatatypeConverter.parseBase64Binary(appconst.getConfigdata().getApikey()))
   .parseClaimsJws(jwt).getBody();
  
JSONObject jsonObject = new JSONObject(claims.getSubject());
            tkn.setUsername((String) jsonObject.get("username"));


}catch(Exception ex){
    ex.printStackTrace();
    tkn=null;
}
return tkn;
}
}
