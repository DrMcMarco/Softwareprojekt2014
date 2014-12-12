/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;
import java.util.Date;
import javax.persistence.*;
/**
 *
 * @author Marco
 */
@Entity
@DiscriminatorValue("Lieferanschrift")
public class Lieferanschrift extends Anschrift {
    
    public Lieferanschrift() {
        
    }
    
    public Lieferanschrift(String Name, String Vorname, String Titel, 
            String Strasse, String Hausnummer, String PLZ, String Ort, 
            String Staat, String Telefon, String FAX, String Email, 
            Date Geburtsdatum, Date Erfassungsdatum) {
        super(Name, Vorname, Titel, Strasse, Hausnummer, PLZ, Ort, Staat, 
                Telefon, FAX, Email, Geburtsdatum, Erfassungsdatum);
    }
    
}
