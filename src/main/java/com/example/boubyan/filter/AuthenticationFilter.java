package com.example.boubyan.filter;

import com.example.boubyan.exception.NotFound;
import com.example.boubyan.exception.NotFoundStudent;
import com.example.boubyan.repository.BoubyanRepository;
import com.example.boubyan.security.JwtTokenUtil;
import com.example.boubyan.security.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class AuthenticationFilter extends OncePerRequestFilter {


    private final ObjectMapper mapper;
    @Autowired
private JwtUserDetailsService jwtUserDetailsService;

@Autowired
private JwtTokenUtil jwtTokenUtil;
@Autowired
UserDetailsService userDetailsService;
    public AuthenticationFilter() {
        mapper = new ObjectMapper();
    }

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        final String requestTokenHeader = request.getHeader("Authorization");
        if (!req.getRequestURI().equals("/login")&&!req.getRequestURI().equals("/addStudent")) {
            String username = null;
            String jwtToken = null;


            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);

                    UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);
                    if(username==null||!userDetails.getUsername().equals(username)){

                        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        mapper.writeValue(httpServletResponse.getWriter(), "Token Is Wrong ");
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    mapper.writeValue(httpServletResponse.getWriter(), "Token Not Found");
                    return;
                } catch (ExpiredJwtException e) {

                    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    mapper.writeValue(httpServletResponse.getWriter(), "Token Expired");
                    return;
                }
            } else {

                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                mapper.writeValue(httpServletResponse.getWriter(), "UNAUTHORIZED");
                return;
            }


            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);


                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            chain.doFilter(request, response);

    }


        else{ chain.doFilter(request, response);}





}}