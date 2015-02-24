/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import DAO.ApplicationException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 * Entitiyklasse für den Aufragskopf.
 * - Spaltet sich in vier Auftragsarten auf, allerdings wird nur diese Entity
 *   in der Datenbank abgelegt. Unterschieden werden diese durch eine
 *   Tabellenspalte "Auftragsart"
 * @author Marco Loewe
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "Auftragsart")
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
     * Gedacht für Anmerkungen/Notizen zum Auftrag
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
    
    @OneToOne
    private Zahlungskondition Zahlungskondition;
    
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
     * Konstruktor ohne Zahlungskondition, für Baraufträge
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
     * Konstruktor mit Zahlungskondition, für alles außer Baraufträge
     * @param Auftragstext Anmerkungen zum Auftrag
     * @param Wert Auftragswert, wird später berrechnet
     * @param Geschaeftspartner Geschäftspartnerobjekt
     * @param Status Statusobjekt
     * @param Abschlussdatum Abschlussdatum
     * @param Erfassungsdatum Erfassungsdatum, wird intern bestimmt
     * @param Lieferdatum Lieferdatum, wird berechnet
     * @param Zahlungskondition Zahlungskondition für diesen Auftrag
     */
    public Auftragskopf(String Auftragstext, double Wert,
            Geschaeftspartner Geschaeftspartner, Status Status, 
            Date Abschlussdatum, Date Erfassungsdatum, Date Lieferdatum, 
            Zahlungskondition Zahlungskondition) {
        this.Auftragstext = Auftragstext;
        this.Wert = Wert;
        this.Geschaeftspartner = Geschaeftspartner;
        this.Abschlussdatum = Abschlussdatum;
        this.Erfassungsdatum = Erfassungsdatum;
        this.Lieferdatum = Lieferdatum;
        this.Positionsliste = new ArrayList<>();
        this.Status = Status;
        this.LKZ = false;
        this.Zahlungskondition = Zahlungskondition;
    }
    /**
     * Generiert.
     * @return ID des Auftrags
     */
    public long getAuftragskopfID() {
        return AuftragskopfID;
    }

    /**
     * Generiert.
     * @return den Auftragstext
     */
    public String getAuftragstext() {
        return Auftragstext;
    }

    /**
     * Generiert.
     * @param Auftragstext der neue Auftragstext
     */
    public void setAuftragstext(String Auftragstext) {
        this.Auftragstext = Auftragstext;
    }

    /**
     * Generiert.
     * @return den Wert des Auftrags
     */
    public double getWert() {
        return Wert;
    }

    /**
     * Generiert
     * @return den zugewiesenen Geschäftspartner
     */
    public Geschaeftspartner getGeschaeftspartner() {
        return Geschaeftspartner;
    }

    /**
     * Generiert
     * @param Geschaeftspartner der neue Geschäftspartner
     */
    public void setGeschaeftspartner(Geschaeftspartner Geschaeftspartner) {
        this.Geschaeftspartner = Geschaeftspartner;
    }
    
    /**
     * Generiert
     * @return den zugewiesenen Status
     */
    public Status getStatus() {
        return Status;
    }

    /**
     * Generiert.
     * @return das Abschlussdatum (null bedeutet das der Auftrag noch nicht abgeschlossen ist)
     */
    public Date getAbschlussdatum() {
        return Abschlussdatum;
    }

    /**
     * Generiert.
     * @param Abschlussdatum das neue Abschlussdatum
     */
    public void setAbschlussdatum(Date Abschlussdatum) {
        this.Abschlussdatum = Abschlussdatum;
    }

    /**
     * Generiert
     * @return das Erfassungsdatum 
     */
    public Date getErfassungsdatum() {
        return Erfassungsdatum;
    }

    /**
     * Generiert.
     * @param Erfassungsdatum das neue Erfassungsdatum
     */
    public void setErfassungsdatum(Date Erfassungsdatum) {
        this.Erfassungsdatum = Erfassungsdatum;
    }

    /**
     * Generiert.
     * @return das Lieferdatum
     */
    public Date getLieferdatum() {
        return Lieferdatum;
    }

    /**
     * Generiert.
     * @param Lieferdatum das neue Lieferdatum
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
    
    public ArrayList<Auftragsposition> getPositionslisteOhneLKZ() {
       
        return this.Positionsliste;
        
    }

    /**
     * Generiert
     * @param Positionsliste die neue Positionsliste
     */
    public void setPositionsliste(ArrayList<Auftragsposition> Positionsliste) {
        this.Positionsliste = Positionsliste;
    }

    /**
     * Generiert.
     * @return ob das Löschkennzeichen gesetzt ist oder nicht
     */
    public boolean isLKZ() {
        return LKZ;
    }

    /**
     * Generiert.
     * @param LKZ der neue Wert für das Löschkennzeichen 
     */
    public void setLKZ(boolean LKZ) {
        this.LKZ = LKZ;
    }

    /**
     * Gibt die Zahlungskondition des Auftrags zurück
     * @return ein Zahlungskondtions-Objekt
     */
    public Zahlungskondition getZahlungskondition() {
        return Zahlungskondition;
    }

    /**
     * Setzt die Zahlungskondition für den Auftrag
     * @param Zahlungskondition ein Zahlungskonditions-Objekt
     */
    public void setZahlungskondition(Zahlungskondition Zahlungskondition) {
        this.Zahlungskondition = Zahlungskondition;
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
        
        if (this instanceof Bestellauftragskopf) {
            ap.setEinzelwert(artikel.getEinkaufswert() * Menge);
        } else {
            ap.setEinzelwert(artikel.getVerkaufswert() * Menge);
        }

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
    public void berechneAuftragswert() {   
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
     * @param obj das Objekt mit dem dieser Auftrag verglichen werden soll
     * @return ob dieser Auftrag gleich dem übergebenen Objekt ist
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

    /**
     * Generiert.
     * @return ein Hashwert dieses Auftrag-Objektes
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (int) (this.AuftragskopfID ^ (this.AuftragskopfID >>> 32));
        hash = 31 * hash + Objects.hashCode(this.Auftragstext);
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.Wert) ^ (Double.doubleToLongBits(this.Wert) >>> 32));
        hash = 31 * hash + Objects.hashCode(this.Geschaeftspartner);
        hash = 31 * hash + Objects.hashCode(this.Status);
        hash = 31 * hash + Objects.hashCode(this.Abschlussdatum);
        hash = 31 * hash + Objects.hashCode(this.Erfassungsdatum);
        hash = 31 * hash + Objects.hashCode(this.Lieferdatum);
        hash = 31 * hash + Objects.hashCode(this.Positionsliste);
        hash = 31 * hash + Objects.hashCode(this.Zahlungskondition);
        hash = 31 * hash + (this.LKZ ? 1 : 0);
        return hash;
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
