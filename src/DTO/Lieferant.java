/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import javax.persistence.*;

/**
 * Spezialisierte Lieferatenklasse.
 * Erbt von der Geschäftspartnerklasse.
 * Wird nicht in einer eigenen Tabelle sondern in der Tabelle "Geschäftspartner"
 * unter "Typ" als "Lieferant" dargestellt
 * @author Marco Loewe
 */
@Entity
@DiscriminatorValue("Lieferant")
public class Lieferant extends Geschaeftspartner {
    
    /**
     * Standardkonstruktor, generiert.
     */
    public Lieferant() {
        
    }
    
    public Lieferant(Anschrift Lieferadresse, Anschrift Rechnungsadresse, 
            double Kreditlimit, boolean LKZ) {
        super(Lieferadresse, Rechnungsadresse, Kreditlimit, LKZ);
    }
}
