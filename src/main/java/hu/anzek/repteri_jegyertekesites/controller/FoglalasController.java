/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.controller;


import hu.anzek.repteri_jegyertekesites.model.Foglalas;
import hu.anzek.repteri_jegyertekesites.model.request_models.FogalalasJaratRequest;
import hu.anzek.repteri_jegyertekesites.model.request_models.FoglalasIdRequest;
import hu.anzek.repteri_jegyertekesites.service.FoglalasService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/api/foglalasok")
public class FoglalasController {
    @Autowired
    private FoglalasService service;
    
    @GetMapping
    public ResponseEntity<List<Foglalas>> letrehozFoglalasJarat() {
        return ResponseEntity.ok(this.service.getAllFoglalas());
    }  
    
    @PostMapping("/jaratra")
    public ResponseEntity<Foglalas> letrehozFoglalasJarat(@RequestBody FogalalasJaratRequest foglalasRequest) {
        return ResponseEntity.ok(this.service.foglalasJaratszamra(foglalasRequest.getJaratszam(), foglalasRequest.getUtasNev()));
    }  

///// ugyan ez reaktív (ReactiveCrudRepository -val  ////////////////////    
    @PostMapping
    public Mono<Foglalas> letrehozFoglalasId(@RequestBody FoglalasIdRequest foglalasRequest) {
        return this.service.foglalasRepuloIdre(foglalasRequest.getRepuloId(), foglalasRequest.getUtasNev());
    }    
}
