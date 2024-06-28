/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.service;


import hu.anzek.repteri_jegyertekesites.model.Foglalas;
import hu.anzek.repteri_jegyertekesites.model.Repulo;
import hu.anzek.repteri_jegyertekesites.repository.FoglalasRepository;
import hu.anzek.repteri_jegyertekesites.repository.RepuloRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


/**
 *
 * @author User
 */
@Service
public class FoglalasService {
    @Autowired
    private FoglalasRepository foglalasRepository;

    @Autowired
    private RepuloRepository repuloRepository;

    public List<Foglalas> getAllFoglalas() {
        return this.foglalasRepository.findAll();
    }
    
    public Foglalas foglalasJaratszamra(String jaratszam, String utasNev) {
        System.out.println("\nFoglalas indul jaratszam = " + jaratszam + " utas = " + utasNev);
        Repulo repulo = this.repuloRepository.findByJaratszam(jaratszam).orElseThrow(
                                                     () -> new RuntimeException("Repülő nem található")
                                                );
        System.out.println("Foglalas repulogep-Id = " + repulo.getId());
                                                if (repulo.getFoglalasok().size() >= repulo.getFerohelyekSzama()) {
                                                    throw new RuntimeException("Nincs elérhető hely");
                                                }
                                                Foglalas foglalas = new Foglalas();
                                                foglalas.setUtasNev(utasNev);
                                                foglalas.setFoglalasIdeje(LocalDateTime.now());
                                                foglalas.setRepulo(repulo);
        System.out.println("Foglalas mentes jaratszam = " + jaratszam + " utas = " + utasNev + "\n");
        return this.foglalasRepository.save(foglalas);    
    }
    
    public Mono<Foglalas> foglalasRepuloIdre(Long repuloId, String utasNev) {
        return Mono.fromCallable(() -> {
                                        Repulo repulo = this.repuloRepository.findById(repuloId).orElseThrow(
                                                                        () -> new RuntimeException("Repülő nem található")
                                        );
                                        if (repulo.getFoglalasok().size() >= repulo.getFerohelyekSzama()) {
                                            throw new RuntimeException("Nincs elérhető hely");
                                        }
                                        Foglalas foglalas = new Foglalas();
                                        foglalas.setUtasNev(utasNev);
                                        foglalas.setFoglalasIdeje(LocalDateTime.now());
                                        foglalas.setRepulo(repulo);
                                        return this.foglalasRepository.save(foglalas);
                                    }
        ).subscribeOn(Schedulers.boundedElastic());
    }
}
