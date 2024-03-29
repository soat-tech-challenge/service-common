package br.com.grupo63.techchallenge.common.config.auth.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtService jwtService;
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String authHeader = ((HttpServletRequest) request).getHeader("Authorization");
            log.debug("Auth header: {}", authHeader);

            if (!StringUtils.hasLength(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
                throw new GeneralSecurityException("Missing or invalid authorization header");
            }

            String jwt = authHeader.substring(7);
            Claims claims = jwtService.getClaims(jwt);
            request.setAttribute("clientId", claims.get("sub"));

            filterChain.doFilter(request, response);
        } catch (GeneralSecurityException e) {
            log.info("Unauthorized: {}", e.getMessage());
            response.getWriter().write("Unauthorized: Missing or incorrect JWT token.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Unexpected error during JWT filter");
        }
    }
}