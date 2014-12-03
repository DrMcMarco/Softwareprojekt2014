/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author Marco
 */

@Entity
public class Geschaeftspartner implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long GeschaeftspartnerID;
    
    @OneToOne
    @JoinColumn(name = "Typ")
    private GPTyp GeschaeftspartnerTyp;
    
    @OneToOne
    private Anschrift Lieferadresse;
    
    @OneToOne
    private Anschrift Rechnungsadresse;
    
    private double Kreditlimit;
    
    private boolean LKZ;
    
    public Geschaeftspartner() {
        
    }

    public Geschaeftspartner(long GeschaeftspartnerID, GPTyp GeschaeftspartnerTyp, Anschrift Lieferadresse, Anschrift Rechnungsadresse, double Kreditlimit, boolean LKZ) {
        this.GeschaeftspartnerID = GeschaeftspartnerID;
        this.GeschaeftspartnerTyp = GeschaeftspartnerTyp;
        this.Lieferadresse = Lieferadresse;
        this.Rechnungsadresse = Rechnungsadresse;
        this.Kreditlimit = Kreditlimit;
        this.LKZ = LKZ;
    }

    public long getGeschaeftspartnerID() {
        return GeschaeftspartnerID;
    }

    public void setGeschaeftspartnerID(long GeschaeftspartnerID) {
        this.GeschaeftspartnerID = GeschaeftspartnerID;
    }

    public GPTyp getGeschaeftspartnerTyp() {
        return GeschaeftspartnerTyp;
    }

    public void setGeschaeftspartnerTyp(GPTyp GeschaeftspartnerTyp) {
        this.GeschaeftspartnerTyp = GeschaeftspartnerTyp;
    }

    public Anschrift getLieferadresse() {
        return Lieferadresse;
    }

    public void setLieferadresse(Anschrift Lieferadresse) {
        this.Lieferadresse = Lieferadresse;
    }

    public Anschrift getRechnungsadresse() {
        return Rechnungsadresse;
    }

    public void setRechnungsadresse(Anschrift Rechnungsadresse) {
        this.Rechnungsadresse = Rechnungsadresse;
    }

    public double getKreditlimit() {
        return Kreditlimit;
    }

    public void setKreditlimit(double Kreditlimit) {
        this.Kreditlimit = Kreditlimit;
    }

    public boolean isLKZ() {
        return LKZ;
    }

    public void setLKZ(boolean LKZ) {
        this.LKZ = LKZ;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.GeschaeftspartnerID ^ (this.GeschaeftspartnerID >>> 32));
        hash = 83 * hash + Objects.hashCode(this.GeschaeftspartnerTyp);
        hash = 83 * hash + Objects.hashCode(this.Lieferadresse);
        hash = 83 * hash + Objects.hashCode(this.Rechnungsadresse);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.Kreditlimit) ^ (Double.doubleToLongBits(this.Kreditlimit) >>> 32));
        hash = 83 * hash + (this.LKZ ? 1 : 0);
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
        final Geschaeftspartner other = (Geschaeftspartner) obj;
        if (this.GeschaeftspartnerID != other.GeschaeftspartnerID) {
            return false;
        }
        if (!Objects.equals(this.GeschaeftspartnerTyp, other.GeschaeftspartnerTyp)) {
            return false;
        }
        if (!Objects.equals(this.Lieferadresse, other.Lieferadresse)) {
            return false;
        }
        if (!Objects.equals(this.Rechnungsadresse, other.Rechnungsadresse)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Kreditlimit) != Double.doubleToLongBits(other.Kreditlimit)) {
            return false;
        }
        if (this.LKZ != other.LKZ) {
            return false;
        }
        return true;
    }
    
    
    
}
