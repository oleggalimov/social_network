package org.olegalimov.examples.social.network.filter;

import lombok.AllArgsConstructor;
import org.olegalimov.examples.social.network.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.olegalimov.examples.social.network.constant.CommonConstant.WebSocket.WS_ENDPOINT;

@Component
@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final String WS_TOKEN_KEY = "access_token";
    private static final String HTTP_TOKEN_KEY = "Authorization";
    private static final String HTTP_TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && JwtUtils.validateJwtToken(jwt)) {
                String userId = JwtUtils.getUserIdFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        var isWsRequest = request.getRequestURI().startsWith(WS_ENDPOINT);

        String headerAuth = isWsRequest ? request.getParameter(WS_TOKEN_KEY) : request.getHeader(HTTP_TOKEN_KEY);

        if (StringUtils.hasText(headerAuth)) {
            if (isWsRequest) {
                return headerAuth;
            } else if (headerAuth.startsWith(HTTP_TOKEN_PREFIX)) {
                return headerAuth.substring(7);
            }
        }

        return null;
    }
}
