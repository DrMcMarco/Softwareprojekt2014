/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author Marco
 */
@Entity
public class ATyp implements java.io.Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long TypID;
    
    @OneToMany(mappedBy = "AnschriftTyp")
    private ArrayList<Anschrift> Anschriftsliste;
    
    private String Beschreibung;

    public ATyp() {
    }

    public ATyp(String Beschreibung) {
        this.Beschreibung = Beschreibung;
    }

    public long getTypID() {
        return TypID;
    }
    
    public ArrayList<Anschrift> getAnschriftsliste() {
        return Anschriftsliste;
    }

    public void setAnschriftsliste(ArrayList<Anschrift> Anschriftsliste) {
        this.Anschriftsliste = Anschriftsliste;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String Beschreibung) {
        this.Beschreibung = Beschreibung;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (this.TypID ^ (this.TypID >>> 32));
        hash = 71 * hash + Objects.hashCode(this.Beschreibung);
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
        final ATyp other = (ATyp) obj;
        if (this.TypID != other.TypID) {
            return false;
        }
        if (!Objects.equals(this.Beschreibung, other.Beschreibung)) {
            return false;
        }
        return true;
    }
}
