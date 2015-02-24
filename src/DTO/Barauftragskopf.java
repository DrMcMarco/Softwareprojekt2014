/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;
import java.util.Date;
import javax.persistence.*;

/**
 * Spezialisierte Barauftragsklasse.
 * Erbt von der Auftragskopfklasse.
 * Wird nicht in einer eigenen Tabelle abgelegt sondern in der Tabelle 
 * "Auftragskopf" unter "Auftragsart" als "Barauftrag" dargestellt.
 * @author Marco Loewe
 */
@Entity
@DiscriminatorValue("Barauftrag")
public class Barauftragskopf extends Auftragskopf {
    
    
    /**
     * Standardkonstruktor
     */
    public Barauftragskopf() {
        
    }
    
    /**
     * Erstellt einen neuen Barauftragskopf.
     * Enthält keine Zahlungskondition.
     * @param Auftragstext Zusätzlicher Text zum Auftrag
     * @param Wert Wert des Auftrags (wird berechnet)
     * @param Geschaeftspartner Geschäftspartner der diesem Auftrag zugewiesen wird
     * @param Status Status des Auftrags
     * @param Abschlussdatum Datum an dem der Auftrag abgeschlossen wurde
     * @param Erfassungsdatum Datum an dem der Auftrag angelegt wurde
     * @param Lieferdatum Datum an dem der Auftrag geliefert wurde
     */
    public Barauftragskopf(String Auftragstext, double Wert, 
            Geschaeftspartner Geschaeftspartner, Status Status, 
            Date Abschlussdatum, Date Erfassungsdatum, Date Lieferdatum) {
        super(Auftragstext, Wert, Geschaeftspartner, Status, Abschlussdatum, 
                Erfassungsdatum, Lieferdatum);
    }
    
}
