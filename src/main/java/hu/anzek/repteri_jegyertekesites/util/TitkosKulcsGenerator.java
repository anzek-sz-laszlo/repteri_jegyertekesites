/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.util;


import java.security.SecureRandom;
import java.util.Base64;


/**
 *
 * @author User
 */
public class TitkosKulcsGenerator {
    // egy byte 8 bit ez pedig 256 különböző értéket tud ábrázolni, 
    // 256 bit, egyetlen karakter ábrázolása. 
    // azonban a különböző kódolási módszerek különböző karakterláncokat eredményeznek ugyanabból a byte-sorozatból.
    // 256 bites kulcs (32 bájt) ábrázolása Hexadecimális formátum: Minden byte két hexadecimális karakterrel ábrázolható (0-255 között minden érték két hexadecimális karakterrel írható le, pl. 00-tól ff-ig).
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32]; // 256 bits
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println(encodedKey);
    }    
}
