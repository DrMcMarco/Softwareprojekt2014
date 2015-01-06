/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DTO;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*----------------------------------------------------------*/
/* Datum Name Was                                           */
/* 06.01.15 sch angelegt                                    */
/*----------------------------------------------------------*/
/**
 *
 * @author Simon <Simon.Simon at your.org>
 */
@Entity
public class Parameterdaten implements Serializable {
    
    /**
     * Serie.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Schlüssel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Das Suchkuerzel für die Suche.
     */
    private String suchkuerzel;
    
    /**
     * Der DB-Attributenname.
     */
    private String dbAttribut;
    
    /**
     * Das hinterlegte Datentyp in der DB.
     */
    private String datentyp;
    
    /**
     * Die Feldlänge für die GUI.
     */
    private int feldlaenge;
    
    /**
     * Tabelle, wo sich das Attribut befindet.
     */
    private String tabelle;
    
    /**
     * Generiert.
     * @return getId
     */
    public Long getId() {
        return id;
    }

    /**
     * Generiert.
     * @param id setId
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Generiert.
     * @return getSuchkuerzel
     */
    public String getSuchkuerzel() {
        return suchkuerzel;
    }

    /**
     * Generiert.
     * @param suchkuerzel suchkuerzel
     */
    public void setSuchkuerzel(String suchkuerzel) {
        this.suchkuerzel = suchkuerzel;
    }

    /**
     * Generiert.
     * @return getDbAttribut
     */
    public String getDbAttribut() {
        return dbAttribut;
    }

    /**
     * Generiert.
     * @param dbAttribut dbAttribut
     */
    public void setDbAttribut(String dbAttribut) {
        this.dbAttribut = dbAttribut;
    }

    /**
     * Generiert.
     * @return getDatentyp
     */
    public String getDatentyp() {
        return datentyp;
    }

    /**
     * Generiert.
     * @param datentyp datentyp
     */
    public void setDatentyp(String datentyp) {
        this.datentyp = datentyp;
    }

    /**
     * Generiert.
     * @return getFeldlaenge
     */
    public int getFeldlaenge() {
        return feldlaenge;
    }

    /**
     * Generiert.
     * @param feldlaenge feldlaenge
     */
    public void setFeldlaenge(int feldlaenge) {
        this.feldlaenge = feldlaenge;
    }

    /**
     * Generiert.
     * @return getTabelle
     */
    public String getTabelle() {
        return tabelle;
    }

    /**
     * Generiert.
     * @param tabelle 
     */
    public void setTabelle(String tabelle) {
        this.tabelle = tabelle;
    }

    /**
     * Generiert.
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.suchkuerzel);
        hash = 79 * hash + Objects.hashCode(this.dbAttribut);
        hash = 79 * hash + Objects.hashCode(this.datentyp);
        hash = 79 * hash + this.feldlaenge;
        hash = 79 * hash + Objects.hashCode(this.tabelle);
        return hash;
    }
    
    /**
     * Generiert.
     * @param obj Das zu überprüfende Objekt.
     * @return Ob Objekte gleich sind.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Parameterdaten other = (Parameterdaten) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.suchkuerzel, other.suchkuerzel)) {
            return false;
        }
        if (!Objects.equals(this.dbAttribut, other.dbAttribut)) {
            return false;
        }
        if (!Objects.equals(this.datentyp, other.datentyp)) {
            return false;
        }
        if (this.feldlaenge != other.feldlaenge) {
            return false;
        }
        if (!Objects.equals(this.tabelle, other.tabelle)) {
            return false;
        }
        return true;
    }
    
    /**
     * Generiert.
     * @return ToString.
     */
    @Override
    public String toString() {
        return "DTO.Parameter[ id=" + id + " ]";
    }
    
}
