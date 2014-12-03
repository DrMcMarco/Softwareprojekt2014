/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Marco
 */
public class AuftragspositionPK implements java.io.Serializable {
    
    private long Auftrag;
    
    private long Positionsnummer;
    
    private long Artikel;
    
    public AuftragspositionPK() {
        
    }

    public AuftragspositionPK(long Auftrag, long Artikel) {
        this.Auftrag = Auftrag;
        this.Artikel = Artikel;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
