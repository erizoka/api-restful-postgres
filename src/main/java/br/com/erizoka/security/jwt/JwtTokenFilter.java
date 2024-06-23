package br.com.erizoka.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getServletPath();
		
		if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/auth/signin") || path.startsWith("/auth/refresh")) {
            chain.doFilter(request, response); // Skip JWT validation for permitted paths
            return;
        }
		
		String token = tokenProvider.resolveToken(httpRequest);
		
		if (token != null && tokenProvider.validadeToken(token)) {
			Authentication auth = tokenProvider.getAuthentication(token);
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);
	}
}
