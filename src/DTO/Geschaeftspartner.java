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
 * Entitätsklasse für Geschäftspartner.
 * - Spaltet sich in Kunde und Lieferant auf, allerdings wird nur diese Entity
 *   in der Datenbank abgelegt. Um welche Art von Geschäftspartner es sich
 *   handelt wird in der Spalte "Typ" festgehalten
 * @author Marco
 */

@Entity
@Inheritance
@DiscriminatorColumn(name = "Typ")
@Table(name = "Geschäftspartner")
public abstract class Geschaeftspartner implements Serializable {
    
    /**
     * Eindeutige Nummer für einen Geschäftspartner.
     * Wird generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long GeschaeftspartnerID;
    
    /**
     * Lieferadresse eines Geschäftspartner.
     * - 1:1-Beziehung, ein Geschäftspartner kann genau eine Lieferadresse
     *   besitzen
     * - Ist eventuell identisch mit der Rechnungsadresse
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Anschrift Lieferadresse;
    
    /**
     * Rechnungsadresse eines Geschäftspartner.
     * - 1:1-Beziehung, ein Geschäftspartner kann genau eine Rechnungsadresse
     *   besitzen
     * - Sollte immer angegeben werden
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Anschrift Rechnungsadresse;
    
    /**
     * Kreditlimit eines Geschäftspartners.
     * Wird beim Freigeben von Aufträgen überprüft.
     */
    private double Kreditlimit;
    
    /**
     * Gibt an ob ein Geschäftspartner gelöscht ist oder nicht.
     */
    private boolean LKZ;
    
    public Geschaeftspartner() {
        
    }

    public Geschaeftspartner(Anschrift Lieferadresse, 
            Anschrift Rechnungsadresse, double Kreditlimit, boolean LKZ) {
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
        int hash = 7;
        hash = 89 * hash + (int) (this.GeschaeftspartnerID ^ (this.GeschaeftspartnerID >>> 32));
        hash = 89 * hash + Objects.hashCode(this.Lieferadresse);
        hash = 89 * hash + Objects.hashCode(this.Rechnungsadresse);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.Kreditlimit) ^ (Double.doubleToLongBits(this.Kreditlimit) >>> 32));
        hash = 89 * hash + (this.LKZ ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final Geschaeftspartner other = (Geschaeftspartner) obj;
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
    
    
    /**
     * Gibt den Typ des Geschäftspartners zurück.
     * Muss so gemacht werden, da man auf die Spalte die durch den
     * DiscriminatorValue gesetzt wird, nicht über JPA zugreifen kann
     * @return 
     */
    public String getTyp(){
        
        DiscriminatorValue val = 
                this.getClass().getAnnotation( DiscriminatorValue.class );

        return val == null ? null : val.value();
    }
    
    
}
