/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 * Primärschlüsselklasse für Auftragspositionen.
 * Muss bei zusammengesetzten Primärschlüsseln angelegt werden.
 * Beinhaltet die Bestandteile des Primärschlüssels.
 * @author Marco Loewe
 */
public class AuftragspositionPK implements java.io.Serializable {
    
    /**
     * Auftrags-ID
     */
    private long Auftrag;
    
    /**
     * Artikel-ID
     */
    private long Artikel;
    
    public AuftragspositionPK() {
        
    }

    public AuftragspositionPK(long Auftrag, long Positionsnummer) {
        this.Auftrag = Auftrag;
        this.Artikel = Positionsnummer;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.Auftrag ^ (this.Auftrag >>> 32));
        hash = 89 * hash + (int) (this.Artikel ^ (this.Artikel >>> 32));
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
        final AuftragspositionPK other = (AuftragspositionPK) obj;
        if (this.Auftrag != other.Auftrag) {
            return false;
        }
        if (this.Artikel != other.Artikel) {
            return false;
        }
        return true;
    }

    
    
    
}
