/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.service;


import hu.anzek.repteri_jegyertekesites.model.Felhasznalo;
import hu.anzek.repteri_jegyertekesites.repository.FelhasznaloRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class FelhasznaloService implements UserDetailsService {
    
    private final FelhasznaloRepository repo;
    private final PasswordEncoder passwordEncoder;

    public FelhasznaloService(FelhasznaloRepository repo,
                              PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }
    
    
    public Felhasznalo create(Felhasznalo user) throws Exception{
        if(user.getId() != null) throw new Exception("Hibas adat: nem lehet Id azonositoja ez nem uj felvitel!");
        if(this.repo.findByFelhasznalonev(user.getFelhasznalonev()) != null) throw new Exception("Hibas adat: ez a felhasznalonev mar letezik!");
        
        user.setJelszo(passwordEncoder.encode(user.getJelszo()));                
        return this.repo.save(user);
    }
    
    public Felhasznalo update(Felhasznalo user) throws Exception{
        if(user.getId() == null) throw new Exception("Hibas adat: nincs meg ID azonositoja (nem letezo adat)!");
        else if( ! this.repo.existsById(user.getId()) ) throw new Exception("Nincs ilyen azonositoval tarolt user!");
        
        user.setJelszo(passwordEncoder.encode(user.getJelszo())); 
        return this.repo.save(user);
    }    
    
    public void delete(Felhasznalo user) throws Exception {
        if(user.getId() == null) throw new Exception("Hibas adat: nincs meg ID azonositoja (nem letezo adat)!");
        else if( ! this.repo.existsById(user.getId()) ) throw new Exception("Nincs ilyen azonositoval tarolt user!");        
        this.repo.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Felhasznalo user = this.repo.findByFelhasznalonev(username);            
        if (user == null) {
            throw new UsernameNotFoundException("Nem talalhato ilyen user: " + username);
        }
        // Megadjuk a megkapott felhatalmazások listáját az egyes rendszer-összetvő funkciók eléréséhez:
        User userSpring = new User(user.getUsername(), user.getPassword(), user.getAuthorities());           
        return userSpring;
    }

    public Felhasznalo loadFelhasznaloByUsername(String felhasznalonev) throws UsernameNotFoundException {
        Felhasznalo user = this.repo.findByFelhasznalonev(felhasznalonev);
        if (user == null) {
               throw new UsernameNotFoundException("Nem talalhato ilyen user: " + felhasznalonev);
        }
        return user;
    }
    
}
