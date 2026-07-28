package com.ardademirtas.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	@Value("${jwt.secret.key}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

	public String generateToken(UserDetails userDetails) {

		Map<String, String> mapClaims = new HashMap<>();

		mapClaims.put("ROLE", "ADMIN");

		String compact = Jwts.builder().subject(userDetails.getUsername()).claims(mapClaims).signWith(getKey())
				.issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + jwtExpiration)).compact();

		return compact;

	}

	public <T> T exportToken(String token, Function<Claims, T> functionClaims) {

		Claims claims = getClaims(token);
	                             

		return functionClaims.apply(claims);

	}
	
	

	  public Claims   getClaims(String token) {
		  
			Claims claims = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
			
			return claims;
		  
		  
	  }

	public String getUserNameByToken(String token) {

		String userName = exportToken(token, Claims::getSubject);

		return userName;

	}

	public boolean isTokenValid(String token) {

		Date date = exportToken(token, Claims::getExpiration);

		return   new Date().before(date);

	}
	
	
	
	   public Object getClaimsRole(String token) {
		   
	     Claims claims = getClaims(token);
	     
           Object object = claims.get("ROLE");
	              
	              
		     return  object;            
  
	   }
		

	public SecretKey getKey() {

		byte[] decode = Decoders.BASE64.decode(secretKey);

		return Keys.hmacShaKeyFor(decode);

	}

}
