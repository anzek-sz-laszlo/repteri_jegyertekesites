/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.util;


import hu.anzek.repteri_jegyertekesites.model.Felhasznalo;
import hu.anzek.repteri_jegyertekesites.service.FelhasznaloService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 *
 * @author User
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private FelhasznaloService felhasznaloService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain chain) throws IOException, ServletException {

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = this.jwtUtil.extractUsername(jwt);
        }
        if ((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) {
            UserDetails userDetails = this.felhasznaloService.loadUserByUsername(username);
            Felhasznalo user = this.felhasznaloService.loadFelhasznaloByUsername(username);   
            if (jwtUtil.validateToken(jwt, user)) {            
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                                        userDetails, null, userDetails.getAuthorities()
                                                        );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }    
}
