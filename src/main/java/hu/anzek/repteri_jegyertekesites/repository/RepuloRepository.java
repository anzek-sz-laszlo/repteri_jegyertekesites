/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.repository;


import hu.anzek.repteri_jegyertekesites.model.Repulo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface RepuloRepository extends JpaRepository<Repulo, Long> {
    
    // @Query(value = "SELECT DISTINCT r.* FROM repulo r WHERE r.jaratszam = :jaratszam", nativeQuery = true)
    public Optional<Repulo> findByJaratszam(String jaratszam);    
}
