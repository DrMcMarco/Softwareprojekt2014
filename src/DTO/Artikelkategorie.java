/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.ArrayList;
import javax.persistence.*;

/**
 *
 * @author Marco
 */

@Entity
public class Artikelkategorie implements java.io.Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    
    private String Kategoriename;
    
    @OneToMany(mappedBy = "Kategorie", cascade = CascadeType.ALL)
    public ArrayList<Artikel> Artikelliste;
    
    private String Beschreibung;
    private String Kommentar;
    private boolean LKZ;

    public Artikelkategorie() {
    }

    public Artikelkategorie(String Kategoriename, String Beschreibung, 
            String Kommentar, boolean LKZ) {
        this.Kategoriename = Kategoriename;
        this.Beschreibung = Beschreibung;
        this.Kommentar = Kommentar;
        this.LKZ = LKZ;
    }

    public long getKategorieID() {
        return Id;
    }

    public void setKategorieID(long KategorieID) {
        this.Id = KategorieID;
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
        hash = 11 * hash + (int) (this.Id ^ (this.Id >>> 32));
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
        if (this.Id != other.Id) {
            return false;
        }
        return true;
    }
}
