/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Date;
import javax.persistence.*;

/**
 * Spezialisierte Terminauftragsklasse.
 * Erbt von der Auftragskopfklasse.
 * Wird nicht in einer eigenen Tabelle abgelegt sondern in der Tabelle 
 * "Auftragskopf" unter "Auftragsart" als "Terminauftrag" dargestellt.
 * @author Marco Loewe
 */
@Entity
@DiscriminatorValue("Terminauftrag")
public class Terminauftragskopf extends Auftragskopf {
    
    /**
     * Standardkonstruktor, generiert.
     */
    public Terminauftragskopf() {
        
    }
    
    /**
     * Erstellt einen neuen Terminauftragskopf.
     * Enthält Zahlungskondition.
     * @param Auftragstext Zusätzlicher Text zum Auftrag
     * @param Wert Wert des Auftrags (wird berechnet)
     * @param Geschaeftspartner Geschäftspartner der diesem Auftrag zugewiesen wird
     * @param Status Status des Auftrags
     * @param Abschlussdatum Datum an dem der Auftrag abgeschlossen wurde
     * @param Erfassungsdatum Datum an dem der Auftrag angelegt wurde
     * @param Lieferdatum Datum an dem der Auftrag geliefert wurde
     * @param Zahlungskondition Zahlungskondition für diesen Auftrag
     */
    public Terminauftragskopf(String Auftragstext, double Wert, 
            Geschaeftspartner Geschaeftspartner, Status Status, 
            Zahlungskondition Zahlungskondition, Date Abschlussdatum, 
            Date Erfassungsdatum, Date Lieferdatum) {
        super(Auftragstext, Wert, Geschaeftspartner, Status, Abschlussdatum, 
                Erfassungsdatum, Lieferdatum, Zahlungskondition);
    }
}
