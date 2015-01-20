/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import DAO.ApplicationException;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Marco
 */
@Entity
@DiscriminatorValue("Sofortauftrag")
public class Sofortauftragskopf extends Auftragskopf {
    
    public Sofortauftragskopf() {
        
    }
    
    public Sofortauftragskopf(String Auftragstext, double Wert, 
            Geschaeftspartner Geschaeftspartner, Status Status, 
            Zahlungskondition Zahlungskondition, Date Abschlussdatum, 
            Date Erfassungsdatum, Date Lieferdatum) {
        super(Auftragstext, Wert, Geschaeftspartner, Status, Abschlussdatum, 
                Erfassungsdatum, Lieferdatum, Zahlungskondition);
    }
}
