/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author Marco
 */
@Entity
public class Auftragsart implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long AuftragsartID;
    
    private String Auftragsart;
    
    @OneToMany(mappedBy = "Auftragsart")
    private ArrayList<Auftragskopf> Auftragsliste;
    
    @OneToMany(mappedBy = "Auftragsart")
    private ArrayList<Zahlungskondition> ZKListe;

    public Auftragsart() {
    }

    public Auftragsart(long AuftragsartID, String Auftragsart, ArrayList<Auftragskopf> Auftragsliste, ArrayList<Zahlungskondition> ZKListe) {
        this.AuftragsartID = AuftragsartID;
        this.Auftragsart = Auftragsart;
    }

    public long getAuftragsartID() {
        return AuftragsartID;
    }

    public void setAuftragsartID(long AuftragsartID) {
        this.AuftragsartID = AuftragsartID;
    }

    public String getAuftragsart() {
        return Auftragsart;
    }

    public void setAuftragsart(String Auftragsart) {
        this.Auftragsart = Auftragsart;
    }

    public ArrayList<Auftragskopf> getAuftragsliste() {
        return Auftragsliste;
    }

    public void setAuftragsliste(ArrayList<Auftragskopf> Auftragsliste) {
        this.Auftragsliste = Auftragsliste;
    }

    public ArrayList<Zahlungskondition> getZKListe() {
        return ZKListe;
    }

    public void setZKListe(ArrayList<Zahlungskondition> ZKListe) {
        this.ZKListe = ZKListe;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (int) (this.AuftragsartID ^ (this.AuftragsartID >>> 32));
        hash = 13 * hash + Objects.hashCode(this.Auftragsart);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Auftragsart other = (Auftragsart) obj;
        if (this.AuftragsartID != other.AuftragsartID) {
            return false;
        }
        if (!Objects.equals(this.Auftragsart, other.Auftragsart)) {
            return false;
        }
        return true;
    }
}
