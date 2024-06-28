/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.controller;


import hu.anzek.repteri_jegyertekesites.model.Felhasznalo;
import hu.anzek.repteri_jegyertekesites.model.request_models.AuthenticationRequest;
import hu.anzek.repteri_jegyertekesites.service.FelhasznaloService;
import hu.anzek.repteri_jegyertekesites.util.JwtUtil;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/web")
public class WebBongeszoController {
    
    private static final Logger logger = LoggerFactory.getLogger(WebBongeszoController.class);
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FelhasznaloService felhasznaloService;
    
    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("message", "Érvénytelen felhasználó, vagy jelszó!");
        return "error";
    }
    
    @GetMapping("/login")
    public String loginUser() {
        return "login";
    }

    @PostMapping("/login")
    public String createAuthenticationWebToken(@RequestBody 
                                               AuthenticationRequest authenticationRequest,
                                               Map<String, String> model) throws Exception {
//        
//        String username = authenticationRequest.getFelhasznalonev();
//        String password = authenticationRequest.getJelszo();  
//        try {        
//            Authentication authentication = authenticationManager.authenticate(
//                                                new UsernamePasswordAuthenticationToken(username, 
//                                                                                        password)
//                                            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);   
//            logger.info("Hitelesites-2: {} {} {}", authentication.getName(), authentication.getAuthorities(), authentication.isAuthenticated());                        
//            final Felhasznalo userDetails = this.felhasznaloService.loadFelhasznaloByUsername(username);
//            userDetails.toConsole(password);            
//            final String jwt = this.jwtUtil.generateToken(userDetails);      
//            // válasz 
//            model.put("token", jwt);
//            return "home";          
//            
//        } catch (AuthenticationException e) {
//            model.put("message", "Érvénytelen felhasználó, vagy jelszó!");
//            return "error";            
//        }     
        return "";
    }
    
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    
    @PostMapping("/regisztracio")
    public Felhasznalo registerNewUser(@RequestBody Felhasznalo felhasznalo) throws Exception {
        return this.felhasznaloService.create(felhasznalo);
    }    
}
