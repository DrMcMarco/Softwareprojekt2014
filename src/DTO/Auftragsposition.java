/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import JFrames.GUIFactory;
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
    @ManyToOne
    @JoinColumn(name = "Artikel")
    private Artikel Artikel;
    
    private long Positionsnummer;
    
    private int Menge;
    
    private double Einzelwert;
    
    @Temporal(TemporalType.DATE)
    private Date Erfassungsdatum;
    
    private boolean LKZ;

    public Auftragsposition() {
    }

    public Auftragsposition(Auftragskopf Auftrag, Artikel Artikel, int Menge, 
            double Einzelwert, Date Erfassungsdatum) {
        this.Auftrag = Auftrag;
        this.Artikel = Artikel;
        this.Menge = Menge;
        this.Einzelwert = Einzelwert;
//        this.Einzelwert = Einzelwert * Menge;
        this.Erfassungsdatum = Erfassungsdatum;
        this.LKZ = false;
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
        
        if (Auftrag instanceof Bestellauftragskopf) {
            this.Einzelwert = this.Artikel.getEinkaufswert() * this.Menge;
        } else {
            this.Einzelwert = this.Artikel.getVerkaufswert() * this.Menge;
        }
        
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

    public boolean isLKZ() {
        return LKZ;
    }

    public void setLKZ(boolean LKZ) {
        this.LKZ = LKZ;
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
        return true;
    }
    
 
    
}
