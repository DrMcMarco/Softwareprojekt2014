/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import DAO.ApplicationException;
import JFrames.GUIFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import javax.persistence.*;

/**
 * Entitiyklasse für den Aufragskopf.
 * - Spaltet sich in vier Auftragsarten auf, allerdings wird diese Entity
 *   in der Datenbank abgelegt. Unterschieden werden diese durch eine
 *   Tabellenspalte "Auftragsart"
 * @author Marco
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "Autragsart")
@Table(name = "Auftragskopf")
public abstract class Auftragskopf implements Serializable {
    
    /**
     * ID des Auftragskopfs
     * Primärschlüssel
     * Wird von der Datenbank generiert
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AuftragskopfID;
    
    /**
     * Gedaxht für Anmerkungen/Notizen zum Auftrag
     */
    private String Auftragstext;
    
    /**
     * Auftragswert
     * Wird anhand der vorhandenen Positionen errechnet
     */
    private double Wert;
    
    /**
     * Assoziation zur Tabelle Geschäftspartner
     */
    @OneToOne
    @JoinColumn(name = "Geschäftspartner")
    private Geschaeftspartner Geschaeftspartner;
    
    /**
     * Assoziation zur Tabelle Status
     */
    @ManyToOne
    @JoinColumn(name = "Status")
    private Status Status;
    
    /**
     * Abschlussdatum
     */
    @Temporal(TemporalType.DATE)
    private Date Abschlussdatum;
    
    /**
     * Erfassungsdatum
     */
    @Temporal(TemporalType.DATE)
    private Date Erfassungsdatum;
    
    /**
     * Lieferdatum
     * wird anhand der Zahlungskondition errechnet
     */
    @Temporal(TemporalType.DATE)
    private Date Lieferdatum;
    
    /**
     * Liste aller Auftragspositionen für diesen Auftrag
     * Assoziation zur Tabelle Auftragsposition
     * 
     * Wird der Auftrag persistiert werden auch alle zugewiesenen
     * Auftragspositionen persistiert (Cascade)
     */
    @OneToMany(mappedBy = "Auftrag", cascade = CascadeType.ALL)
    private ArrayList<Auftragsposition> Positionsliste;
    
    /**
     * Aufträge werden nicht real gelöscht, sondern mit einem Löschkennzeichen
     * versehen
     */
    private boolean LKZ;

    /**
     * Generiert
     * Standardkonstruktor
     */
    public Auftragskopf() {
    }

    /**
     * Generiert.
     * @param Auftragstext Anmerkungen zum Auftrag
     * @param Wert Auftragswert, wird später berrechnet
     * @param Geschaeftspartner Geschäftspartnerobjekt
     * @param Status Statusobjekt
     * @param Abschlussdatum Abschlussdatum
     * @param Erfassungsdatum Erfassungsdatum, wird intern bestimmt
     * @param Lieferdatum Lieferdatum, wird berechnet
     */
    public Auftragskopf(String Auftragstext, double Wert,
            Geschaeftspartner Geschaeftspartner, Status Status, 
            Date Abschlussdatum, Date Erfassungsdatum, Date Lieferdatum) {
        this.Auftragstext = Auftragstext;
        this.Wert = Wert;
        this.Geschaeftspartner = Geschaeftspartner;
        this.Abschlussdatum = Abschlussdatum;
        this.Erfassungsdatum = Erfassungsdatum;
        this.Lieferdatum = Lieferdatum;
        this.Positionsliste = new ArrayList<>();
        this.Status = Status;
        this.LKZ = false;
    }

    /**
     * Generiert.
     * @return ID des Auftrags
     */
    public long getAuftragskopfID() {
        return AuftragskopfID;
    }

    /**
     * 
     * @return 
     */
    public String getAuftragstext() {
        return Auftragstext;
    }

    /**
     * 
     * @param Auftragstext 
     */
    public void setAuftragstext(String Auftragstext) {
        this.Auftragstext = Auftragstext;
    }

    /**
     * 
     * @return 
     */
    public double getWert() {
        return Wert;
    }

    /**
     * 
     * @param Wert 
     */
    public void setWert(double Wert) {
        this.Wert = Wert;
    }

    /**
     * 
     * @return 
     */
    public Geschaeftspartner getGeschaeftspartner() {
        return Geschaeftspartner;
    }

    /**
     * 
     * @param Geschaeftspartner 
     */
    public void setGeschaeftspartner(Geschaeftspartner Geschaeftspartner) {
        this.Geschaeftspartner = Geschaeftspartner;
    }
    
    /**
     * 
     * @return 
     */
    public Status getStatus() {
        return Status;
    }

    /**
     * 
     * @return 
     */
    public Date getAbschlussdatum() {
        return Abschlussdatum;
    }

    /**
     * 
     * @param Abschlussdatum 
     */
    public void setAbschlussdatum(Date Abschlussdatum) {
        this.Abschlussdatum = Abschlussdatum;
    }

    /**
     * 
     * @return 
     */
    public Date getErfassungsdatum() {
        return Erfassungsdatum;
    }

    /**
     * 
     * @param Erfassungsdatum 
     */
    public void setErfassungsdatum(Date Erfassungsdatum) {
        this.Erfassungsdatum = Erfassungsdatum;
    }

    /**
     * 
     * @return 
     */
    public Date getLieferdatum() {
        return Lieferdatum;
    }

    /**
     * 
     * @param Lieferdatum 
     */
    public void setLieferdatum(Date Lieferdatum) {
        this.Lieferdatum = Lieferdatum;
    }

    /**
     * Gibt alle Positionen eines Auftrags, für die kein LKZ gesetzt ist, zurück
     * @return eine Positionsliste
     */
    public ArrayList<Auftragsposition> getPositionsliste() {
        ArrayList<Auftragsposition> ergebnis = new ArrayList<>();
        //Iteration über die Positionsliste
        for (Auftragsposition ap : Positionsliste) {
            //Ist die Position nicht mit einem LKZ versehen..
            if (!ap.isLKZ()) {
                //...wird sie einer Ergebnisliste hinzugefügt
                ergebnis.add(ap);
            }
        }
        return ergebnis;
    }

    /**
     * 
     * @param Positionsliste 
     */
    public void setPositionsliste(ArrayList<Auftragsposition> Positionsliste) {
        this.Positionsliste = Positionsliste;
    }

    /**
     * 
     * @return 
     */
    public boolean isLKZ() {
        return LKZ;
    }

    /**
     * 
     * @param LKZ 
     */
    public void setLKZ(boolean LKZ) {
        this.LKZ = LKZ;
    }

    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 15.12.14   loe     angelegt                              */
    /* 22.12.14   loe     aufgrund von Problemen entfernt       */
    /* 17.01.15   loe     neu angelegt                          */
    /* 18.01.15   loe     überarbeitet                          */
    /*----------------------------------------------------------*/
    /**
     * Fügt einem Auftrag eine neue Position hinzu
     * @param artikel ein Artikel-Objekt
     * @param Menge die Menge an verkauften Artikeln
     */
    public void addPosition(Artikel artikel, int Menge) {
        //Neue Position anlegen und mit Attributen füllen
        Auftragsposition ap = new Auftragsposition();
        ap.setPositionsnummer(this.Positionsliste.size()+1);
        ap.setAuftrag(this);
        ap.setArtikel(artikel);
        ap.setMenge(Menge);
        ap.setEinzelwert(artikel.getVerkaufswert() * Menge);
        ap.setErfassungsdatum(this.Erfassungsdatum);
        //Wert der Position zum Gesamtwert hinzufügen
        this.Wert = this.Wert + ap.getEinzelwert();
        //Position der Liste hinzufügen
        this.Positionsliste.add(ap);
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Berechnet den Auftragswert neu.
     * Sollte nur von der "aendereAuftrag"-Methode in der DAO verwendet werden!
     */
    public void berrechneAuftragswert() {   
        double Auftragswert = 0;
        //Iteration über die Positionsliste
        for (Auftragsposition ap : this.Positionsliste) {
            //Wenn für die Position kein LKZ gesetzt ist...
            if (!ap.isLKZ()) {
                //...füge den Einzelwert der Position dem Gesamtwert hinzu
                Auftragswert = Auftragswert + ap.getEinzelwert();
            }     
        }
        //Wenn alle Position abgearbeitet sind, setze den Auftragswert
        this.Wert = Auftragswert;   
    }

    /**
     * Generiert.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.AuftragskopfID ^ (this.AuftragskopfID >>> 32));
        return hash;
    }

    /**
     * Generiert.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Auftragskopf other = (Auftragskopf) obj;
        if (this.AuftragskopfID != other.AuftragskopfID) {
            return false;
        }
        return true;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Setzt den Status eines Auftrags
     * @param status Der Status in den der Auftrag versetzt werden soll
     * 
     */
    public void setStatus(Status status) {
        this.Status = status;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Führt eine Verfügbarkeitsprüfung in Abhängigkeit des Auftragsstatus und
     * Auftragsart durch.
     * @param status Der zu setzende Status
     * @return True: Der Bestand ist hinreichend für die Bestellmenge
     *         False: Der Bestand ist nicht hinreichen für die Bestellmenge
     * @throws ApplicationException Wirft eine Exception falls das Kreditlimit
     *                              Des Kunden nicht ausreichend ist.
     */
    public boolean pruefeVerfuegbarkeit(String status) 
        throws ApplicationException {
        //Flag, ob Bestand verfügbar ist.
        boolean verfuegbar = true;
        //Prüfe, ob es sich, um eine Bestellung handelt
        if (this instanceof Bestellauftragskopf) {
            if (status.equals("abgeschlossen")) {
                for (int i = 0; i < this.getPositionsliste().size() 
                        && verfuegbar; i++) {
                    //Setze Flag, ob der Zulauf hinreichend für den Abschluss
                    //Des Bestellauftrages ist und der Bestand auf Frei
                    //Übertragen werden kann
                    verfuegbar = 
                            this.getPositionsliste().get(i).getArtikel()
                                    .getZulauf() 
                            >= this.getPositionsliste().get(i).getMenge();
                }
            } else {
                //Bei einer Bestellung wird nur der Zulauf erhöht
                //Es bestehen keine Abhängigkeiten
                verfuegbar = true;
            }
        //Alle anderen Auftragsarten
        } else {
            //Prüfe Kreditlimit des Kunden (Limit >= Auftragswert)
            if (this.pruefeKreditlimit()) {
                //Prüfe, welcher Status gesetzt werden soll
                if (status.equals("freigegeben")) {
                    //Durchlaufe alle Positionen des Auftrags
                    for (int i = 0; i < this.getPositionsliste().size()  
                            && verfuegbar; i++) {
                        //Setze das Flag nach den Kriterium, ob die Anzahl Frei
                        //kleiner o. gleich der Menge 
                        //aus der Bestellung entspricht
                        verfuegbar = 
                                this.getPositionsliste().get(i).getArtikel()
                                        .getFrei() 
                                >= this.getPositionsliste().get(i).getMenge(); 
                    }
                } else if (status.equals("abgeschlossen")) {
                    //Durchlaufe alle Positionen des Auftrags
                    for (int i = 0; i < this.getPositionsliste().size() 
                            && verfuegbar; i++) {
                        //Setze das Flag nach den Kriterium, ob die Anzahl Frei
                        //kleiner gleich der Menge aus der Bestellung entspricht
                        verfuegbar = 
                                this.getPositionsliste().get(i).getArtikel()
                                        .getReserviert() 
                                >= this.getPositionsliste().get(i).getMenge();
                    }
                }
            //Kreditlimit ist nicht hinreichend
            } else {
                throw new ApplicationException("Fehler", "Das Kreditlimit " 
                        + "reicht nicht aus!");
            }
        }
        return verfuegbar;
    }
    
    public String getTyp(){
        
        DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );

        return val == null ? null : val.value();
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Das Kreditlimit wird gegen den Auftragswert (Gesamtwert aller Positionen
     * inklusive Steuern) geprüft.
     * 
     * @return True: Kreditlimit ist größer oder gleich dem Auftragswert
     *         False: Kreditlimit ist kleiner dem Auftragswert
     */
    public boolean pruefeKreditlimit() {
        return this.Geschaeftspartner.getKreditlimit() >= this.Wert;
    }
}
