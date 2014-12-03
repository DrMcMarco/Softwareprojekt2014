/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
/**
 *
 * @author Marco
 */
@Entity
@IdClass(AuftragspositionPK.class)
public class Auftragsposition implements java.io.Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "Auftrag")
    private Auftragskopf Auftrag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Positionsnummer;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "Artikel")
    private Artikel Artikel;
    
    private int Menge;
    
    private double Einzelwert;
    
    @Temporal(TemporalType.DATE)
    private Date Erfassungsdatum;

    public Auftragsposition() {
    }

    public Auftragsposition(Auftragskopf Auftrag, long Positionsnummer, Artikel Artikel, int Menge, double Einzelwert, Date Erfassungsdatum) {
        this.Auftrag = Auftrag;
        this.Positionsnummer = Positionsnummer;
        this.Artikel = Artikel;
        this.Menge = Menge;
        this.Einzelwert = Einzelwert;
        this.Erfassungsdatum = Erfassungsdatum;
    }

    public Auftragskopf getAuftrag() {
        return Auftrag;
    }

    public void setAuftrag(Auftragskopf Auftrag) {
        this.Auftrag = Auftrag;
    }

    public long getPositionsnummer() {
        return Positionsnummer;
    }

    public void setPositionsnummer(long Positionsnummer) {
        this.Positionsnummer = Positionsnummer;
    }

    public Artikel getArtikel() {
        return Artikel;
    }

    public void setArtikel(Artikel Artikel) {
        this.Artikel = Artikel;
    }

    public int getMenge() {
        return Menge;
    }

    public void setMenge(int Menge) {
        this.Menge = Menge;
    }

    public double getEinzelwert() {
        return Einzelwert;
    }

    public void setEinzelwert(double Einzelwert) {
        this.Einzelwert = Einzelwert;
    }

    public Date getErfassungsdatum() {
        return Erfassungsdatum;
    }

    public void setErfassungsdatum(Date Erfassungsdatum) {
        this.Erfassungsdatum = Erfassungsdatum;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.Auftrag);
        hash = 19 * hash + (int) (this.Positionsnummer ^ (this.Positionsnummer >>> 32));
        hash = 19 * hash + Objects.hashCode(this.Artikel);
        hash = 19 * hash + this.Menge;
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.Einzelwert) ^ (Double.doubleToLongBits(this.Einzelwert) >>> 32));
        hash = 19 * hash + Objects.hashCode(this.Erfassungsdatum);
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
        final Auftragsposition other = (Auftragsposition) obj;
        if (!Objects.equals(this.Auftrag, other.Auftrag)) {
            return false;
        }
        if (this.Positionsnummer != other.Positionsnummer) {
            return false;
        }
        if (!Objects.equals(this.Artikel, other.Artikel)) {
            return false;
        }
        if (this.Menge != other.Menge) {
            return false;
        }
        if (Double.doubleToLongBits(this.Einzelwert) != Double.doubleToLongBits(other.Einzelwert)) {
            return false;
        }
        if (!Objects.equals(this.Erfassungsdatum, other.Erfassungsdatum)) {
            return false;
        }
        return true;
    }
    
    
    
}
