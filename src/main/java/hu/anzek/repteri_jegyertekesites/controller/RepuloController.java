/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.controller;


import hu.anzek.repteri_jegyertekesites.model.Repulo;
import hu.anzek.repteri_jegyertekesites.model.request_models.RepuloRequest;
import hu.anzek.repteri_jegyertekesites.repository.RepuloRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Az alábbi osztály második fele minta egy adat(folyam) kezelés- bemutatásához, 
 * Abban a részben a megoldás a reaktív programozás alapelvei szerint történik, nem blokkoló módon, 
 * ami általában nagyobb hatékonyságot és skálázhatóságot biztosít, ha sok a konkurens kérés.
 * @author User
 */
@RestController
@RequestMapping("/api/repulok")
public class RepuloController {
    
    @Autowired
    private RepuloRepository repuloRepo;
    
    @GetMapping
    public ResponseEntity<List<Repulo>> osszesRepulo1() {    
        return ResponseEntity.ok(this.repuloRepo.findAll());
    }
      
    @GetMapping("/all")
    public List<Repulo> osszesRepulo2() {
        return this.repuloRepo.findAll();        
    }
    
    @PostMapping("/addgep")
    public ResponseEntity<Repulo> hozzaadRepulo(@RequestBody 
                                                RepuloRequest repuloRequest) {
        Repulo repulo = new Repulo();
        repulo.setJaratszam(repuloRequest.getJaratszam());
        repulo.setFerohelyekSzama(repuloRequest.getFerohelyekSzama());
        if(this.repuloRepo.findByJaratszam(repuloRequest.getJaratszam()) == null ){
            return ResponseEntity.ok( this.repuloRepo.save(repulo));
        }
        return ResponseEntity.ok(null);
    }
////////////// És akkor most a WebFlux -os változatok ///////////////////////////
    
    // Ha egyetlen elemet (vagy egyetlen kollekciót) várunk vissza, 
    // - vagy egyetlen elemet szeretnénk kibocsátani,
    // akkor a Mono<T> publisher kell, vagy hibaüzenetet    
    @PostMapping("/addgep/mono")
    public Mono<Repulo> hozzaadRepuloMono(@RequestBody RepuloRequest repuloRequest) {
        Repulo repulo = new Repulo();
        repulo.setJaratszam(repuloRequest.getJaratszam());
        repulo.setFerohelyekSzama(repuloRequest.getFerohelyekSzama());
        return Mono.fromCallable(() -> this.repuloRepo.save(repulo));
    }

    // Ha több elemet várunk vissza, DE(!) ezeket egyesével szeretnénk visszadni, vagy kibocsátani.
    // Illetve akkor is, ha egy adatfolyamot (stream) szeretnénk kezelni, (amely folyamatosan ad vissza egyesével, elemeket).
    // Akkor tehát a Flux<T> publisher [0..n] számú elemet bocsát ki (egyesével !).
    @GetMapping("/fluxlist")
    public Flux<Repulo> osszesRepuloFlux() {
        // a Flux.fromIterable(...) minden elemet külön-külön "bocsát ki" a visszakapott listából.
        return Flux.fromIterable(this.repuloRepo.findAll());
    }    
    
    // 1, a két pulisher burkoló osztály közötti konverzió alkalmazása:
    @GetMapping("/mono-to-flux-list")
    public Flux<Repulo> osszesRepuloMono() {
        // létrehizunk egy Mono<List<T>>-publishert, aszinkron módon hívott repo lekérdezéssel ami visszaad egy listát:
        Mono<List<Repulo>> monoList = Mono.fromCallable(() -> this.repuloRepo.findAll());
        return monoList.flatMapMany(Flux::fromIterable);         
    }  
    
    // 2, a két pulisher burkoló osztály közötti konverzió alkalmazása:
    @GetMapping("/flux-to-mono-list")
    public Mono<List<Repulo>> fluxToMono() {
        // "fromIterable(x)" metódus segítségével 
        // a Flux<Repulo>-t egy Mono<List<Repulo>>-vá alakítjuk. 
        // a metódus összegyűjti a Flux összes elemét és bepakolja egy listába, 
        // majd azt egyetlen Mono- kollekcióként adja vissza
        Flux<Repulo> flux = Flux.fromIterable(this.repuloRepo.findAll()); 
        return flux.collectList();
    }    
}
