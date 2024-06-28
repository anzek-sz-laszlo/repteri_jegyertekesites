/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
@Entity
public class Felhasznalo implements UserDetails {

    private static final long serialVersionUID = 1;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String felhasznalonev;
    private String jelszo;
    // a dolgozó teljes neve, munkaköre, stb...
    private String kiegeszites;
    // a hozzáférés jelege (pl: user, admin, stb...)
    private String szerep;

    public Felhasznalo() {
    }

    public Felhasznalo(String felhasznalonev,
                       String jelszo,
                       String kiegeszites,
                       String szerep) {
        this.felhasznalonev = felhasznalonev;
        this.jelszo = jelszo;        
        this.kiegeszites = kiegeszites;
        this.szerep = szerep;
    }

    @Override
    public String toString(){
        return "Felhasznalo(UserDetails) {\n"
                + "                         - username     : " + this.felhasznalonev + "\n" 
                + "                         - kiegesztes   : " + this.kiegeszites +"\n" 
                + "                         - szerep       : " + this.szerep + "\n" 
                + " - jelszo : [" + this.jelszo + "]\n" 
                + " }" ;
    }
    
    public void toConsole(String s) {    
        System.out.println(s + "\n" + this.toString());    
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev = felhasznalonev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getSzerep() {
        return szerep;
    }

    public void setSzerep(String szerep) {
        this.szerep = szerep;
    }    

    public String getKiegeszites() {
        return kiegeszites;
    }

    public void setKiegeszites(String kiegeszites) {
        this.kiegeszites = kiegeszites;
    }
        
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.szerep));
    }

    @Override
    public String getPassword() {
        return this.jelszo;
    }

    @Override
    public String getUsername() {
        return this.felhasznalonev;
    }
}
