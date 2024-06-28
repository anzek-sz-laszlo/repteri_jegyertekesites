/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.repository;


import hu.anzek.repteri_jegyertekesites.model.Felhasznalo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface FelhasznaloRepository extends JpaRepository<Felhasznalo, Long> {
    public Felhasznalo findByFelhasznalonev(String felhasznalonev);
}
