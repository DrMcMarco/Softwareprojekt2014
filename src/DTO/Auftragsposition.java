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
 * Entitätsklasse für Auftragspositionen.
 * - Enthält einen zusammengesetzten Primärschlüssen und brauch daher eine 
 *   Primärschlüsselklasse (AuftragspositionPK)
 * @author Marco Loewe
 */
@Entity
@IdClass(AuftragspositionPK.class)
public class Auftragsposition implements java.io.Serializable {
    
    /**
     * Primärschlüssel, zusammen mit Artikel.
     * Verweis auf den Auftrag (Fremdschlüssel).
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "Auftrag")
    private Auftragskopf Auftrag;
    
    /**
     * Primärschlüssel, zusammen mit Auftrag.
     * Verweis auf einen Artikel (Fremdschlüssel).
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "Artikel")
    private Artikel Artikel;
    
    /**
     * Nummer einer Position.
     * Wird automatisch hochgezählt.
     */
    private long Positionsnummer;
    
    /**
     * Bestellte Menge eines Artikel.
     */
    private int Menge;
    
    /**
     * Wert einer Position.
     * Einzelwert = Artikel-Einzelwert * Menge.
     */
    private double Einzelwert;
    
    /**
     * Datum an dem die Position angelegt wurde.
     */
    @Temporal(TemporalType.DATE)
    private Date Erfassungsdatum;
    
    /**
     * Gibt an ob die Position gelöscht ist oder nicht.
     * Gelöschte Positionen haben keinen Einfluss mehr auf den Auftragswert.
     */
    private boolean LKZ;

    /**
     * Standardkonstruktor.
     */
    public Auftragsposition() {
    }

    /**
     * Konstruktor mit allen Attributen
     * @param Auftrag Der Auftrag zu die die Position gehört
     * @param Artikel Der Artikel der in dieser Position vermerkt werden soll
     * @param Menge Die bestellte/eingekaufte Menge
     * @param Einzelwert Der Wert der Position
     * @param Erfassungsdatum Datum an dem die Position erstellt wird
     */
    public Auftragsposition(Auftragskopf Auftrag, Artikel Artikel, int Menge, 
            double Einzelwert, Date Erfassungsdatum) {
        this.Auftrag = Auftrag;
        this.Artikel = Artikel;
        this.Menge = Menge;
        this.Einzelwert = Einzelwert;
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

    /**
     * Setzt die Menge einer Position.
     * Wenn die Menge geändert wird, wird auch der Einzelwert neu berechnet.
     * @param Menge die neue Menge
     */
    public void setMenge(int Menge) {
        this.Menge = Menge;
        
        //Je nach Auftragsart muss der Einzelwert anders berechnet werden
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
