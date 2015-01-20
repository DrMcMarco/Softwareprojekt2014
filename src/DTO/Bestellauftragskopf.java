/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import DAO.ApplicationException;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
/**
 *
 * @author Marco
 */
@Entity
@DiscriminatorValue("Bestellauftrag")
public class Bestellauftragskopf extends Auftragskopf {
    
    public Bestellauftragskopf() {
        
    }
    
    public Bestellauftragskopf(String Auftragstext, double Wert, 
            Geschaeftspartner Geschaeftspartner, Status Status, 
            Zahlungskondition Zahlungskondition, Date Abschlussdatum, 
            Date Erfassungsdatum, Date Lieferdatum) {
//        super(Auftragstext, Wert, Geschaeftspartner, Status, Abschlussdatum, 
//                Erfassungsdatum, Lieferdatum);
        super(Auftragstext, Wert, Geschaeftspartner, Status, Abschlussdatum, 
                Erfassungsdatum, Lieferdatum, Zahlungskondition);
    }
  
//Könnte später noch gebraucht werden
    @Override
    public void addPosition(Artikel artikel, int Menge) {
        Auftragsposition ap = new Auftragsposition();
        ap.setAuftrag(this);
        ap.setArtikel(artikel);
        ap.setMenge(Menge);
        ap.setEinzelwert(artikel.getEinkaufswert());
        ap.setErfassungsdatum(super.getErfassungsdatum());
        super.setWert(super.getWert() + ap.getEinzelwert());
        super.getPositionsliste().add(ap);
    }
}
