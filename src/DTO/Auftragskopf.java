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
 *
 * @author Marco
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "Autragsart")
@Table(name = "Auftragskopf")
public abstract class Auftragskopf implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AuftragskopfID;
    
    private String Auftragstext;
    
    private double Wert;
    
    @OneToOne
    @JoinColumn(name = "Geschäftspartner")
    private Geschaeftspartner Geschaeftspartner;
    
    @ManyToOne
    @JoinColumn(name = "Status")
    private Status Status;
    
    @Temporal(TemporalType.DATE)
    private Date Abschlussdatum;
    
    @Temporal(TemporalType.DATE)
    private Date Erfassungsdatum;
    
    @Temporal(TemporalType.DATE)
    private Date Lieferdatum;
    
    @OneToMany(mappedBy = "Auftrag", cascade = CascadeType.ALL)
    private ArrayList<Auftragsposition> Positionsliste;

    public Auftragskopf() {
    }

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
    }

    public long getAuftragskopfID() {
        return AuftragskopfID;
    }

    public String getAuftragstext() {
        return Auftragstext;
    }

    public void setAuftragstext(String Auftragstext) {
        this.Auftragstext = Auftragstext;
    }

    public double getWert() {
        return Wert;
    }

    public void setWert(double Wert) {
        this.Wert = Wert;
    }

    public Geschaeftspartner getGeschaeftspartner() {
        return Geschaeftspartner;
    }

    public void setGeschaeftspartner(Geschaeftspartner Geschaeftspartner) {
        this.Geschaeftspartner = Geschaeftspartner;
    }
    
    public Status getStatus() {
        return Status;
    }

    public Date getAbschlussdatum() {
        return Abschlussdatum;
    }

    public void setAbschlussdatum(Date Abschlussdatum) {
        this.Abschlussdatum = Abschlussdatum;
    }

    public Date getErfassungsdatum() {
        return Erfassungsdatum;
    }

    public void setErfassungsdatum(Date Erfassungsdatum) {
        this.Erfassungsdatum = Erfassungsdatum;
    }

    public Date getLieferdatum() {
        return Lieferdatum;
    }

    public void setLieferdatum(Date Lieferdatum) {
        this.Lieferdatum = Lieferdatum;
    }

    public ArrayList<Auftragsposition> getPositionsliste() {
        return Positionsliste;
    }

    public void setPositionsliste(ArrayList<Auftragsposition> Positionsliste) {
        this.Positionsliste = Positionsliste;
    }
    
    public void addPosition(Artikel artikel, int Menge) {
        Auftragsposition ap = new Auftragsposition();
        ap.setAuftrag(this);
        ap.setArtikel(artikel);
        ap.setMenge(Menge);
        ap.setEinzelwert(artikel.getVerkaufswert() * Menge);
        ap.setErfassungsdatum(this.Erfassungsdatum);
        this.Positionsliste.add(ap);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.AuftragskopfID ^ (this.AuftragskopfID >>> 32));
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
     * Je nach Status wird eine Verfügbarkeitsprüfung 
     * und eine Bestandsführung angestoßen
     * @param status Der Status in den der Auftrag versetzt werden soll
     * @throws ApplicationException reicht die Exception weiter
     * 
     */
    public void setStatus(Status status) throws ApplicationException {
        
        //Prüfe zu aller erst, ob ein Status übergeben worden ist
        if (status == null) {
            throw new ApplicationException("Fehler", "Der Status wurde " + 
                    "nicht übergeben!");
        }
        
        //Der Status wird erstmalig auf erfasst gesetzt.
        //Hier muss noch keine Bestandsführung durchgeführt werden, da 
        //die Materialien erst ab freigegeben gebucht werden.
        if (status.getStatus().equals("erfasst") && this.Status == null) {
            this.Status = status;
        }
        //Wenn der Auftrag bereits im Status Abgeschlossen ist,
        //ist es nicht mehr möglich ihn zu ändern
        else if (this.Status.getStatus().equals("abgeschlossen")) {
            throw new ApplicationException("Fehler", "Der Auftrag kann " + 
                    "in keinen anderen Status mehr versetzt werden!");
        }
        //Für den Fall, dass der Status direkt von erfasst in abgeschlossen
        //überführt werden soll, wird eine Exception geworfen
        else if (this.Status.getStatus().equals("erfasst") && 
                status.getStatus().equals("abgeschlossen")) {
            throw new ApplicationException("Fehler", "Der Auftrag kann " + 
                    "nicht von erfasst nach abgeschlossen versetzt werden!");
        }
        //Für den Fall, dass ein Auftrag zurück in den Erfasst Status gesetzt 
        //werden soll, müssen alle Materialbuchungen rückgängig gemacht werden
        else if (status.getStatus().equals("erfasst")) {
            //Handelt es sich um eine Bestellung unserer Seits
            if (this instanceof Bestellauftragskopf) {
                //Wir setzen den vorgemerkten Bestand vom Zulauf wieder zurück
                this.setzeBestand("RueckgaengigBestellung");
            } else {
                //Wir erniedrigen den Bestand von reserviert wieder
                //und erhöhen den Freibestand
                this.setzeBestand("RueckgaengigVerkauf");
            }
            //Zum Schluss übernehmen wir den Status
            this.Status = status;
        } else {
            //Prüfe zunächst die Verfügbarkeit
            if (this.pruefeVerfügbarkeit(status.getStatus())) {
                //Handelt es sich um eine Bestellung unserer Seits
                if (this instanceof Bestellauftragskopf) {
                    //Bei Freigegeben werden die Bestände unter Zulauf erhöht
                    if (status.getStatus().equals("freigegeben")) {
                        this.setzeBestand("Zulauf");
                    //Bei abgeschlossen werden sie von Zulauf auf Frei gebucht
                    } else if (status.getStatus().equals("abgeschlossen")) {
                        this.setzeBestand("Frei");
                    }
                //Bei einer Kundenbestellung
                } else {
                    //Bei freigegeben werden die Bestände auf Reserviert gebucht
                    if (status.getStatus().equals("freigegeben")) {
                        this.setzeBestand("Reserviert");
                    //Bei abgeschlossen Buchen wir von Reserviert auf Verkauft
                    } else if (status.getStatus().equals("abgeschlossen")) {
                        this.setzeBestand("Verkauft");
                    }
                }
                //Zum Schluss übernehmen wir den Status muss noch persistiert werden!
                this.Status = status;
            }
        }
    }
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * 
     * @throws ApplicationException 
     */
    private void setzeBestand(String statusArt) throws ApplicationException {
        GUIFactory.getDAO().setzeArtikelBestand(this.Positionsliste, statusArt);
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Führt eine Verfügbarkeitsprüfung in Abhängigkeit des Auftragsstatus und
     * Auftragsart durch
     * @return True: Der Bestand ist hinreichend für die Bestellmenge
     *         False: Der Bestand ist nicht hinreichen für die Bestellmenge
     * @throws ApplicationException Wirft eine Exception falls das Kreditlimit
     *                              Des Kunden nicht ausreichend ist.
     */
    private boolean pruefeVerfügbarkeit(String status) 
            throws ApplicationException {
        //Flag, ob Bestand verfügbar ist.
        boolean verfuegbar = true;
        //Prüfe, ob es sich, um eine Bestellung handelt
        if (this instanceof Bestellauftragskopf) {
            if (status.equals("abgeschlossen")) {
                for (int i = 0; i < this.Positionsliste.size() && verfuegbar; 
                        i++) {
                    //Setze Flag, ob der Zulauf hinreichend für den Abschluss
                    //Des Bestellauftrages ist und der Bestand auf Frei
                    //Übertragen werden kann
                    verfuegbar = 
                            this.Positionsliste.get(i).getArtikel().getZulauf() 
                            >= this.Positionsliste.get(i).getMenge();
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
                    for (int i = 0; i < this.Positionsliste.size() && 
                            verfuegbar; i++) {
                        //Setze das Flag nach den Kriterium, ob die Anzahl Frei
                        //kleiner o. gleich der Menge 
                        //aus der Bestellung entspricht
                        verfuegbar = 
                                this.Positionsliste.get(i).getArtikel().
                                        getFrei() >= 
                                this.Positionsliste.get(i).getMenge(); 
                    }
                } else if (status.equals("abgeschlossen")) {
                    //Durchlaufe alle Positionen des Auftrags
                    for (int i = 0; i < this.Positionsliste.size() && 
                            verfuegbar; i++) {
                        //Setze das Flag nach den Kriterium, ob die Anzahl Frei
                        //kleiner gleich der Menge aus der Bestellung entspricht
                        verfuegbar = 
                                this.Positionsliste.get(i).getArtikel().
                                        getReserviert() >= 
                                this.Positionsliste.get(i).getMenge();
                    }
                }
            //Kreditlimit ist nicht hinreichend
            } else {
                throw new ApplicationException("Fehler", "Das Kreditlimit " + 
                        "reicht nicht aus!");
            }
        }
        return verfuegbar;
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
