package com.jwt.jwtauthentication.config;

import com.jwt.jwtauthentication.helper.JwtUtil;
import com.jwt.jwtauthentication.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // jwt nibo
        // bearer diye start hoiche kina check korbo
        // tarpor token take validate korbo
        String requestTokenHeader = request.getHeader("Authorization");
        String userName = null;
        String jwtToken =null;
        // null and format checker
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            try {
                userName = this.jwtUtil.extractUsername(jwtToken);
//                userName = jwtUtil.extractClaim(jwtToken, Claims::getSubject);
//                userName = jwtUtil.extractClaim(jwtToken, Claims::getSubject);

            }catch (Exception e){
                e.printStackTrace();

            }

            // fine Area
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userName);

            if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                //
//                Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, new ArrayList<>());
//                SecurityContextHolder.getContext().setAuthentication(authentication);


            }else {
                System.out.println("token is not validated");
            }

        }

        filterChain.doFilter(request,response);





    }
}
