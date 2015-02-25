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
 * Entitätsklasse für Artikel.
 * - Verweist auf ein Kategorie-Objekt
 * - Enthält zum Zweck der Versionierung Vorgänger und Nachfolger falls ein
 *   Artikel, der in einem Auftrag vorkommt, geändert wird
 * @author Marco Loewe
 * @version 1.0
 */

@Entity
public class Artikel implements Serializable {
    
    /**
     * ID des Artikels, wird generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ArtikelId;
    
    /**
     * Verweis auf ein Kategorie-Objekt (Fremdschlüssel).
     * n:1-Beziehung, eine Kategorie kann mehrere Artikel enthalten
     */
    @ManyToOne
    @JoinColumn(name = "Kategorie")
    private Artikelkategorie Kategorie;
    
    /**
     * Name des Artikels.
     */
    private String Artikeltext;
    
    /**
     * Kommentar für die Bestellung des Artikels.
     */
    private String Bestelltext;
    
    /**
     * Preis des Artikels, wenn er verkauft wird.
     */
    private double Verkaufswert;
    
    /**
     * Preis des Artikels, wenn er eingekauft wird.
     */
    private double Einkaufswert;
    
    /**
     * Mehrwertsteuersatz des Artikels (in Prozenz).
     */
    private int MwST;
    
    /**
     * Menge an frei verfügbarem Bestand für den Artikel.
     * - wird durch abgeschlossene Bestellaufträge erzeugt
     */
    private int Frei;
    
    /**
     * Menge an reserviertem Bestand für den Atikel.
     * - wird durch freigegebene Verkaufsaufträge erzeugt
     */
    private int Reserviert;
    
    /**
     * Menge an bestellten aber noch nicht gelieferten Artikeln.
     * - wird durch freigegebene Bestellaufträge erzeugt
     */
    private int Zulauf;
    
    /**
     * Menge an verkauften Artikeln.
     * - wird durch abgeschlossene Verkaufsaufträge erzeugt
     */
    private int Verkauft;
    
    /**
     * Gibt an ob der Artikel gelöscht ist oder nicht.
     * - Ein gelöschter Artikel bleibt weiterhin in der Datenbank bestehen, wird
     *   im Programm aber nicht angezeigt
     */
    private boolean LKZ;
    
    /**
     * Vorgänger des Artikels.
     * - 1:1-Beziehung, ein Artikle kann nur einen Vorgänger haben
     */
    @OneToOne
    @JoinColumn(name = "Vorgänger")
    private Artikel Vorgaenger;
    
    /**
     * Nachfolger des Artikels
     * - 1:1-Beziehung, ein Artikel kann nur einen Nachfolger haben
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Nachfolger")
    private Artikel Nachfolger;

    public Artikel() {
    }

    /**
     * Konstruktur.
     * @param Kategorie Kategorie
     * @param Artikeltext Artikeltext
     * @param Bestelltext Bestelltext
     * @param Verkaufswert Verkaufswert
     * @param Einkaufswert Einkaufswert
     * @param MwST MwST
     * @param Frei Frei
     * @param Reserviert Reserviert
     * @param Zulauf Zulauf
     * @param Verkauft Verkauft
     */
    public Artikel(Artikelkategorie Kategorie, String Artikeltext, 
            String Bestelltext, double Verkaufswert, double Einkaufswert, 
            int MwST, int Frei, int Reserviert, int Zulauf, int Verkauft) {
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
        this.LKZ = false;
        this.Vorgaenger = null;
        this.Nachfolger = null;
    }
    
    public Artikel(Artikelkategorie Kategorie, String Artikeltext, 
            String Bestelltext, double Verkaufswert, double Einkaufswert, 
            int MwST, int Frei, int Reserviert, int Zulauf, int Verkauft, 
            Artikel Vorgaenger) {
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
        this.LKZ = false;
        this.Vorgaenger = Vorgaenger;
        this.Nachfolger = null;
    }

    /**
     * Generiert.
     * @return id
     */
    public long getArtikelID() {
        return ArtikelId;
    }

    /**
     * Generiert.
     * @param ArtikelID id
     */
    public void setArtikelID(long ArtikelID) {
        this.ArtikelId = ArtikelID;
    }

    /**
     * Generiert.
     * @return kat
     */
    public Artikelkategorie getKategorie() {
        return Kategorie;
    }

    /**
     * Generiert.
     * @param Kategorie kat
     */
    public void setKategorie(Artikelkategorie Kategorie) {
        this.Kategorie = Kategorie;
    }

    /**
     * Generiert.
     * @return text
     */
    public String getArtikeltext() {
        return Artikeltext;
    }

    /**
     * Generiert.
     * @param Artikeltext text
     */
    public void setArtikeltext(String Artikeltext) {
        this.Artikeltext = Artikeltext;
    }

    /**
     * Generiert.
     * @return text
     */
    public String getBestelltext() {
        return Bestelltext;
    }

    /**
     * Generiert.
     * @param Bestelltext text
     */
    public void setBestelltext(String Bestelltext) {
        this.Bestelltext = Bestelltext;
    }

    /**
     * Generiert.
     * @return wert
     */
    public double getVerkaufswert() {
        return Verkaufswert;
    }

    /**
     * Generiert.
     * @param Verkaufswert wert
     */
    public void setVerkaufswert(double Verkaufswert) {
        this.Verkaufswert = Verkaufswert;
    }

    /**
     * Generiert.
     * @return wert
     */
    public double getEinkaufswert() {
        return Einkaufswert;
    }

    /**
     * Generiert.
     * @param Einkaufswert wert
     */
    public void setEinkaufswert(double Einkaufswert) {
        this.Einkaufswert = Einkaufswert;
    }

    /**
     * Generiert.
     * @return mwst
     */
    public int getMwST() {
        return MwST;
    }

    /**
     * Generiert.
     * @param mwst
     */
    public void setMwST(int MwST) {
        this.MwST = MwST;
    }

    /**
     * Generiert.
     * @return frei
     */
    public int getFrei() {
        return Frei;
    }

    /**
     * Generiert.
     * @param Frei frei
     */
    public void setFrei(int Frei) {
        this.Frei = Frei;
    }

    /**
     * Generiert.
     * @return reserviert
     */
    public int getReserviert() {
        return Reserviert;
    }

    /**
     * Generiert.
     * @param Reserviert reserv
     */
    public void setReserviert(int Reserviert) {
        this.Reserviert = Reserviert;
    }

    /**
     * Generiert.
     * @return zulauf
     */
    public int getZulauf() {
        return Zulauf;
    }

    /**
     * Generiert.
     * @param Zulauf zulauf
     */
    public void setZulauf(int Zulauf) {
        this.Zulauf = Zulauf;
    }

    /**
     * Generiert.
     * @return verkauft
     */
    public int getVerkauft() {
        return Verkauft;
    }

    /**
     * Generiert.
     * @param Verkauft verkauft
     */
    public void setVerkauft(int Verkauft) {
        this.Verkauft = Verkauft;
    }

    /**
     * Generiert.
     * @return lkz
     */
    public boolean isLKZ() {
        return LKZ;
    }

    /**
     * Generiert.
     * @param LKZ lkz
     */
    public void setLKZ(boolean LKZ) {
        this.LKZ = LKZ;
    }

    /**
     * Generiert.
     * @return vorg
     */
    public Artikel getVorgaenger() {
        return Vorgaenger;
    }

    /**
     * Generiert.
     * @param Vorgaenger vorg
     */
    public void setVorgaenger(Artikel Vorgaenger) {
        this.Vorgaenger = Vorgaenger;
    }

    /**
     * Generiert.
     * @return nachf
     */
    public Artikel getNachfolger() {
        return Nachfolger;
    }

    /**
     * Generiert.
     * @param Nachfolger nachf
     */
    public void setNachfolger(Artikel Nachfolger) {
        this.Nachfolger = Nachfolger;
    }


    /**
     * Generiert.
     * @param obj obj
     * @return wahr oder falsch
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Artikel other = (Artikel) obj;
        if (this.ArtikelId != other.ArtikelId) {
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
        if (Double.doubleToLongBits(this.Verkaufswert) != Double.doubleToLongBits(other.Verkaufswert)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Einkaufswert) != Double.doubleToLongBits(other.Einkaufswert)) {
            return false;
        }
        if (this.MwST != other.MwST) {
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
        if (this.LKZ != other.LKZ) {
            return false;
        }
        if (!Objects.equals(this.Vorgaenger, other.Vorgaenger)) {
            return false;
        }
        if (!Objects.equals(this.Nachfolger, other.Nachfolger)) {
            return false;
        }
        return true;
    }
    
    

    

    /**
     * Generiert.
     * @return tostring
     */
    @Override
    public String toString() {
        return "Artikel{" + "ArtikelID=" + ArtikelId + ", Kategorie=" + 
                Kategorie + ", Artikeltext=" + Artikeltext + ", Bestelltext=" + 
                Bestelltext + ", Verkaufswert=" + Verkaufswert + 
                ", Einkaufswert=" + Einkaufswert + ", MwST=" + MwST + 
                ", Frei=" + Frei + ", Reserviert=" + Reserviert + ", Zulauf=" 
                + Zulauf + ", Verkauft=" + Verkauft + '}';
    }
    
    
}
