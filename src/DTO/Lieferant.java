/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import javax.persistence.*;
/**
 *
 * @author Marco
 */
@Entity
@DiscriminatorValue("Lieferant")
public class Lieferant extends Geschaeftspartner {
    
    public Lieferant() {
        
    }
    
    public Lieferant(Anschrift Lieferadresse, Anschrift Rechnungsadresse, 
            double Kreditlimit, boolean LKZ) {
        super(Lieferadresse, Rechnungsadresse, Kreditlimit, LKZ);
    }
}
