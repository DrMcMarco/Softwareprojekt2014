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
public class GPTyp implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long TypID;
    
    private String Beschreibung;
    
    @OneToMany(mappedBy = "GeschaeftspartnerTyp")
    private ArrayList<Geschaeftspartner> GPListe;

    public GPTyp() {
    }

    public GPTyp(long TypID, String Beschreibung) {
        this.TypID = TypID;
        this.Beschreibung = Beschreibung;
    }

    public long getTypID() {
        return TypID;
    }

    public void setTypID(long TypID) {
        this.TypID = TypID;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String Beschreibung) {
        this.Beschreibung = Beschreibung;
    }

    public ArrayList<Geschaeftspartner> getGPListe() {
        return GPListe;
    }

    public void setGPListe(ArrayList<Geschaeftspartner> GPListe) {
        this.GPListe = GPListe;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (int) (this.TypID ^ (this.TypID >>> 32));
        hash = 73 * hash + Objects.hashCode(this.Beschreibung);
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
        final GPTyp other = (GPTyp) obj;
        if (this.TypID != other.TypID) {
            return false;
        }
        if (!Objects.equals(this.Beschreibung, other.Beschreibung)) {
            return false;
        }
        return true;
    }
}
