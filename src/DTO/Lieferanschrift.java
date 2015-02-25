/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;
import java.util.Date;
import javax.persistence.*;

/**
 * Spezialisierte Lieferanschriftklasse.
 * Erbt von der Klasse Anschrift.
 * Wird nicht in einer eigenen Tabelle sondern in "Anschrift" unter "Typ" als
 * "Lieferanschrift" dargestellt.
 * @author Marco Loewe
 */
@Entity
@DiscriminatorValue("Lieferanschrift")
public class Lieferanschrift extends Anschrift {
    
    /**
     * Standardkonstruktor, generiert.
     */
    public Lieferanschrift() {
        
    }
    
    /**
     * Generiert.
     * @param Name Name
     * @param Vorname Vorname
     * @param Titel Anrede
     * @param Strasse Straße
     * @param Hausnummer Hausnummer
     * @param PLZ Postleitzahl
     * @param Ort Ort
     * @param Staat Staat
     * @param Telefon Telefon
     * @param FAX Faxnummer
     * @param Email Email
     * @param Geburtsdatum Geburtsdatum
     * @param Erfassungsdatum Erfassungsdatum
     */
    public Lieferanschrift(String Name, String Vorname, String Titel, 
            String Strasse, String Hausnummer, String PLZ, String Ort, 
            String Staat, String Telefon, String FAX, String Email, 
            Date Geburtsdatum, Date Erfassungsdatum) {
        super(Name, Vorname, Titel, Strasse, Hausnummer, PLZ, Ort, Staat, 
                Telefon, FAX, Email, Geburtsdatum, Erfassungsdatum);
    }
    
}
