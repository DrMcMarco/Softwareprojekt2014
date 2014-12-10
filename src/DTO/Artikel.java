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
public class Artikel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long ArtikelID;
    
    @ManyToOne
    @JoinColumn(name = "Kategorie")
    private Artikelkategorie Kategorie;
    
    private String Artikeltext;
    private String Bestelltext;
    private double Verkaufswert;
    private double Einkaufswert;
    private double MwST;
    private int Frei;
    private int Reserviert;
    private int Zulauf;
    private int Verkauft;

    public Artikel() {
    }

    public Artikel(Artikelkategorie Kategorie, String Artikeltext, 
            String Bestelltext, double Verkaufswert, double Einkaufswert, 
            double MwST, int Frei, int Reserviert, int Zulauf, int Verkauft) {
        this.Kategorie = Kategorie;
        this.Artikeltext = Artikeltext;
        this.Bestelltext = Bestelltext;
        this.Verkaufswert = Verkaufswert;
        this.Einkaufswert = Einkaufswert;
        this.MwST = MwST;
        this.Frei = Frei;
        this.Reserviert = Reserviert;
        this.Zulauf = Zulauf;
        this.Verkauft = Verkauft;
    }

    public long getArtikelID() {
        return ArtikelID;
    }

    public void setArtikelID(long ArtikelID) {
        this.ArtikelID = ArtikelID;
    }

    public Artikelkategorie getKategorie() {
        return Kategorie;
    }

    public void setKategorie(Artikelkategorie Kategorie) {
        this.Kategorie = Kategorie;
    }

    public String getArtikeltext() {
        return Artikeltext;
    }

    public void setArtikeltext(String Artikeltext) {
        this.Artikeltext = Artikeltext;
    }

    public String getBestelltext() {
        return Bestelltext;
    }

    public void setBestelltext(String Bestelltext) {
        this.Bestelltext = Bestelltext;
    }

    public double getVerkaufswert() {
        return Verkaufswert;
    }

    public void setVerkaufswert(double Verkaufswert) {
        this.Verkaufswert = Verkaufswert;
    }

    public double getEinkaufswert() {
        return Einkaufswert;
    }

    public void setEinkaufswert(double Einkaufswert) {
        this.Einkaufswert = Einkaufswert;
    }

    public double getMwST() {
        return MwST;
    }

    public void setMwST(double MwST) {
        this.MwST = MwST;
    }

    public int getFrei() {
        return Frei;
    }

    public void setFrei(int Frei) {
        this.Frei = Frei;
    }

    public int getReserviert() {
        return Reserviert;
    }

    public void setReserviert(int Reserviert) {
        this.Reserviert = Reserviert;
    }

    public int getZulauf() {
        return Zulauf;
    }

    public void setZulauf(int Zulauf) {
        this.Zulauf = Zulauf;
    }

    public int getVerkauft() {
        return Verkauft;
    }

    public void setVerkauft(int Verkauft) {
        this.Verkauft = Verkauft;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (this.ArtikelID ^ (this.ArtikelID >>> 32));
        hash = 79 * hash + Objects.hashCode(this.Kategorie);
        hash = 79 * hash + Objects.hashCode(this.Artikeltext);
        hash = 79 * hash + Objects.hashCode(this.Bestelltext);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.Verkaufswert) ^ 
                (Double.doubleToLongBits(this.Verkaufswert) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.Einkaufswert) ^ 
                (Double.doubleToLongBits(this.Einkaufswert) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.MwST) ^ 
                (Double.doubleToLongBits(this.MwST) >>> 32));
        hash = 79 * hash + this.Frei;
        hash = 79 * hash + this.Reserviert;
        hash = 79 * hash + this.Zulauf;
        hash = 79 * hash + this.Verkauft;
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
        final Artikel other = (Artikel) obj;
        if (this.ArtikelID != other.ArtikelID) {
            return false;
        }
        if (!Objects.equals(this.Kategorie, other.Kategorie)) {
            return false;
        }
        if (!Objects.equals(this.Artikeltext, other.Artikeltext)) {
            return false;
        }
        if (!Objects.equals(this.Bestelltext, other.Bestelltext)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Verkaufswert) != 
                Double.doubleToLongBits(other.Verkaufswert)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Einkaufswert) != 
                Double.doubleToLongBits(other.Einkaufswert)) {
            return false;
        }
        if (Double.doubleToLongBits(this.MwST) != 
                Double.doubleToLongBits(other.MwST)) {
            return false;
        }
        if (this.Frei != other.Frei) {
            return false;
        }
        if (this.Reserviert != other.Reserviert) {
            return false;
        }
        if (this.Zulauf != other.Zulauf) {
            return false;
        }
        if (this.Verkauft != other.Verkauft) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Artikel{" + "ArtikelID=" + ArtikelID + ", Kategorie=" + 
                Kategorie + ", Artikeltext=" + Artikeltext + ", Bestelltext=" + 
                Bestelltext + ", Verkaufswert=" + Verkaufswert + 
                ", Einkaufswert=" + Einkaufswert + ", MwST=" + MwST + 
                ", Frei=" + Frei + ", Reserviert=" + Reserviert + ", Zulauf=" 
                + Zulauf + ", Verkauft=" + Verkauft + '}';
    }
    
    
}
