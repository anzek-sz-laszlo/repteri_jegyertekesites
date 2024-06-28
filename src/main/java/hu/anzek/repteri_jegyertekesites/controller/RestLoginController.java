/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.controller;


import hu.anzek.repteri_jegyertekesites.model.Felhasznalo;
import hu.anzek.repteri_jegyertekesites.model.request_models.AuthenticationRequest;
import hu.anzek.repteri_jegyertekesites.service.FelhasznaloService;
import hu.anzek.repteri_jegyertekesites.util.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author User
 */
@RestController
@RequestMapping("/api")
public class RestLoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FelhasznaloService felhasznaloService;
    
    @PostMapping("/login")
    public Map<String, String> createAuthenticationRestToken(@RequestBody 
                                                             AuthenticationRequest authenticationRequest) throws Exception {   
        String username = authenticationRequest.getFelhasznalonev();
        String password = authenticationRequest.getJelszo();  
        Map<String, String> tokenMap = new HashMap<>();
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                                                new UsernamePasswordAuthenticationToken(username, 
                                                                                        password)
                                            );
            SecurityContextHolder.getContext().setAuthentication(authentication);    
            final Felhasznalo userDetails = this.felhasznaloService.loadFelhasznaloByUsername(username);
            final String jwt = this.jwtUtil.generateToken(userDetails);
            
            // válasz
            tokenMap.put("token", jwt);            
        } catch (AuthenticationException e) {                    
            tokenMap.put("token", "Sikertelen authetikáció! " +  e.getMessage());
        }     
        return tokenMap;
    }

    @PostMapping("/regisztracio")
    public Felhasznalo registerNewUser(@RequestBody Felhasznalo felhasznalo) throws Exception {
        return this.felhasznaloService.create(felhasznalo);
    }    
}
