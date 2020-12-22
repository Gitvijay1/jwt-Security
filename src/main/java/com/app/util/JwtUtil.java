package com.app.util;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${app.secret}")
	private String secret;
	public String genereteToken(String subject) {
		return Jwts.builder()
				.setSubject("subject")
				.setIssuer("Naresh it")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(10)))
				.signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret.getBytes()))
				.compact();
	}
	
	//2 read claims
	public Claims getClaim(String token) {
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}
	
	//3 read expairy date from token
	public Date getExpDae(String token) {
		return getClaim(token).getExpiration();
	}
	
	//4 read username from token
	public String getUserName(String token) {
		return getClaim(token).getSubject();
	}
	
	//5 validate ExpDate
	public boolean isTokenExp(String token) {
		Date expDae = getExpDae(token);
		return expDae.before(new Date(System.currentTimeMillis()));
	}
	
	//6 validate username in token and database and ExpDate
	public boolean valiDateToken(String token,String username) {
		String tokenUserName = getUserName(token);
		return (username.equals(tokenUserName) && !isTokenExp(token));
	}
	

}
