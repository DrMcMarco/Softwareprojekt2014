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
@DiscriminatorValue("Barauftrag")
public class Barauftragskopf extends Auftragskopf {
    
    
    public Barauftragskopf() {
        
    }
    
    public Barauftragskopf(String Auftragstext, double Wert, Status Status, 
            Date Abschlussdatum, Date Erfassungsdatum, Date Lieferdatum) {
        super(Auftragstext, Wert, Status, Abschlussdatum, Erfassungsdatum, Lieferdatum);
    }
    
}
