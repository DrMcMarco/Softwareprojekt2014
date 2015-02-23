/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.ArrayList;
import javax.persistence.*;

/**
 * Entitätsklasse für Artikelkategorien.
 * @author Marco Loewe
 */

@Entity
public class Artikelkategorie implements java.io.Serializable {
    
    /**
     * ID der Artikelkategorie, wird generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long KategorieId;
    
    /**
     * Name der Kategorie.
     */
    private String Kategoriename;
    
    /**
     * Liste aller Kategorien für eine Kategorie.
     */
    @OneToMany(mappedBy = "Kategorie", cascade = CascadeType.ALL)
    public ArrayList<Artikel> Artikelliste;
    
    /**
     * Beschreibung der Kategorie.
     */
    private String Beschreibung;
    
    /**
     * Kommentar für die Kategorie.
     */
    private String Kommentar;
    
    /**
     * Gibt an ob die Kategorie gelöscht ist oder nicht.
     * - Eine gelöschte Kategorie bleibt weiterhin in der Datenbank bestehen, 
     *   wird im Programm aber nicht angezeigt
     */
    private boolean LKZ;

    public Artikelkategorie() {
    }

    public Artikelkategorie(String Kategoriename, String Beschreibung, 
            String Kommentar) {
        this.Kategoriename = Kategoriename;
        this.Beschreibung = Beschreibung;
        this.Kommentar = Kommentar;
        this.LKZ = false;
    }

    public long getKategorieID() {
        return KategorieId;
    }

    public void setKategorieID(long KategorieID) {
        this.KategorieId = KategorieID;
    }

    public String getKategoriename() {
        return Kategoriename;
    }

    public void setKategoriename(String Kategoriename) {
        this.Kategoriename = Kategoriename;
    }

    public ArrayList<Artikel> getArtikelliste() {
        return Artikelliste;
    }

    public void setArtikelliste(ArrayList<Artikel> Artikelliste) {
        this.Artikelliste = Artikelliste;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String Beschreibung) {
        this.Beschreibung = Beschreibung;
    }

    public String getKommentar() {
        return Kommentar;
    }

    public void setKommentar(String Kommentar) {
        this.Kommentar = Kommentar;
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
        hash = 11 * hash + (int) (this.KategorieId ^ (this.KategorieId >>> 32));
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
        final Artikelkategorie other = (Artikelkategorie) obj;
        if (this.KategorieId != other.KategorieId) {
            return false;
        }
        return true;
    }
}
