/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;


/**
 *
 * @author User
 */
@Data
@Entity
public class Repulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jaratszam;
    private int ferohelyekSzama;

    @OneToMany(mappedBy = "repulo")
    @JsonIgnoreProperties("repulo")
    //@JsonManagedReference    
    private List<Foglalas> foglalasok;
}
