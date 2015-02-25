/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import javax.persistence.*;

/**
 * Spezialisierte Kundenklasse.
 * Erbt von der Geschäftspartnerklasse.
 * Wird nicht in einer eigenen Tabelle sondern in der Tabelle "Geschäftspartner"
 * unter "Typ" als "Kunde" dargestellt
 * @author Marco Loewe
 */
@Entity
@DiscriminatorValue("Kunde")
public class Kunde extends Geschaeftspartner {
    
    /**
     * Standardkonstrukor, generiert.
     */
    public Kunde() {
        
    }
    
    /**
     * Generiert.
     * @param Lieferadresse Lieferanschrift des Kunden
     * @param Rechnungsadresse Rechnungsanschrift des Kunden
     * @param Kreditlimit Kreditlimit des Kunden
     * @param LKZ gibt an ob der Kunde gelöscht ist oder nicht
     */
    public Kunde(Anschrift Lieferadresse, Anschrift Rechnungsadresse, 
            double Kreditlimit, boolean LKZ) {
        super(Lieferadresse, Rechnungsadresse, Kreditlimit, LKZ);
    }
}
