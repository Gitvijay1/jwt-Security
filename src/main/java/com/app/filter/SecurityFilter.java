package com.app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.util.JwtUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException
	{
		//1 read tokem from autherization header
		String token = request.getHeader("Authorization");
		if(token !=null) {
			//do validatio
			String userName = util.getUserName(token);
			//username should not be empty context-authentcation should be empty
			if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				//validate token
				boolean isValid = util.valiDateToken(token, userDetails.getUsername());
				if(isValid) {
					UsernamePasswordAuthenticationToken authToken=
							new UsernamePasswordAuthenticationToken
							(
							userName,
							userDetails.getPassword(),
							userDetails.getAuthorities()
							);
					authToken.setDetails(new WebAuthenticationDetailsSource()
							.buildDetails(request));
					//fianl object storing securityContext with user deatils(uanem,pssword)
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
