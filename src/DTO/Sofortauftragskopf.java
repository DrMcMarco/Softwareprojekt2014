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
@DiscriminatorValue("Sofortauftrag")
public class Sofortauftragskopf extends Auftragskopf {
    
    @OneToOne
    private Zahlungskondition Zahlungskondition;
    
    public Sofortauftragskopf() {
        
    }
    
    public Sofortauftragskopf(String Auftragstext, double Wert, Status Status, 
            Zahlungskondition Zahlungskondition, Date Abschlussdatum, 
            Date Erfassungsdatum, Date Lieferdatum) {
        super(Auftragstext, Wert, Status, Abschlussdatum, Erfassungsdatum, 
                Lieferdatum);
        this.Zahlungskondition = Zahlungskondition;
    }

    public Zahlungskondition getZahlungskondition() {
        return Zahlungskondition;
    }

    public void setZahlungskondition(Zahlungskondition Zahlungskondition) {
        this.Zahlungskondition = Zahlungskondition;
    }
    
}
