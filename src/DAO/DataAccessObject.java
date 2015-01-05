/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
//import
import DTO.Anschrift;
import DTO.Artikel;
import DTO.Artikelkategorie;
import DTO.Auftragskopf;
import DTO.Auftragsposition;
import DTO.Barauftragskopf;
import DTO.Benutzer;
import DTO.Bestellauftragskopf;
import DTO.Geschaeftspartner;
import DTO.Kunde;
import DTO.Lieferanschrift;
import DTO.Lieferant;
import DTO.Rechnungsanschrift;
import DTO.Sofortauftragskopf;
import DTO.Status;
import DTO.Terminauftragskopf;
import DTO.Zahlungskondition;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
/**
 *
 * @author Simon <Simon.Simon at your.org>
 */
public class DataAccessObject {
    
    /**.
     * EntityManager verwaltet alle Persistenten Klassen
     */
    private final EntityManager em;
    
    /**.
     * Standartkonstruktor
     */
    public DataAccessObject() {
        this.em = Persistence.createEntityManagerFactory(
            "Softwareprojekt2014PU").createEntityManager();
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /** Methode zur dynamischen Suche.
     * Hier wird die Klasse Parser benutzt.
     * @param eingabe Sucheingabe 
     * @param tabelle Tabelle in der gesucht werden soll
     * @return gibt eine Collection<?> zurück mit allen gefunden Datensätzen
     * @throws DAO.ApplicationException Die Exception wird durchgereicht
     */
    public Collection<?> suchAbfrage(String eingabe, String tabelle) 
        throws ApplicationException {
        //Datendeklaration
        ArrayList<String> dbAttrListe = null;
        Auftragskopf auftrag = null;
        List<?> sqlErgebnisListe = null;
        String sqlAbfrage = null;
        Parser parser = new Parser();
        //Parse den Suchausdruck und hole die DB-Attr. Namen
        dbAttrListe = parser.parse(eingabe, tabelle);
        //Prüfe, ob das Parsen erfolgreich war.
        if (dbAttrListe == null) {
            throw new ApplicationException("Fehler", 
                    "Beim Parsen ist ein Fehler aufgetreten!");
        }
        //Sql-Statement Dynamisch erzeugen
        sqlAbfrage = "SELECT ST FROM " + tabelle + " ST WHERE ";
        //Iteriere über alle Input Einträge
        for (int i = 0; i < dbAttrListe.size(); i++) {
            //Hole Abfrage aus der Liste
            sqlAbfrage += ("ST." + dbAttrListe.get(i));
            //Prüfe, ob mehrere Einträge vorhanden sind, diese müssen mit AND
            //Verknüpft werden.Beim letzten durchlauf wird kein AND mehr gesetzt
            if (dbAttrListe.size() > 1 && i < dbAttrListe.size() - 1) {
                sqlAbfrage += " AND ";
            }
        }

        try {
            //Ergebnis aus der DB laden und als Liste speichern
            sqlErgebnisListe = em.createQuery(sqlAbfrage).getResultList();
            
        } catch (Exception e) {
            throw new ApplicationException("Fehler", 
                    "Die Daten konnten nicht gefunden werden!");
        }
        

        return sqlErgebnisListe;
    }
//<editor-fold defaultstate="collapsed" desc="create-Methoden">
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Methode zur Erzeugung eines Artikels.
     *
     * @param Kategorie
     * @param Artikeltext
     * @param Bestelltext
     * @param Verkaufswert
     * @param Einkaufswert
     * @param MwST
     * @param Frei
     * @param Reserviert
     * @param Zulauf
     * @param Verkauft
     * @throws DAO.ApplicationException Die Exception wird durchgereicht
     */
    public void createItem(String Kategorie, String Artikeltext,
            String Bestelltext, double Verkaufswert, double Einkaufswert,
            double MwST, int Frei, int Reserviert, int Zulauf, int Verkauft)
            throws ApplicationException {
        //Suche die Artikelkategorie anhand des Kategorienamen
        Artikelkategorie cat = this.getCategory(Kategorie);
        
        if (cat == null) {
            throw new ApplicationException("Fehler",
                    "Der Kategoriename existiert nicht!");
        }
        
        Artikel item = new Artikel(cat, Artikeltext, Bestelltext,
                Verkaufswert, Einkaufswert, MwST, Frei, Reserviert,
                Zulauf, Verkauft, false);
        //Prüfen, ob das Objekt erstellt wurde
        if (item == null) {
            throw new ApplicationException("Fehler",
                    "Die Werte waren ungültig!");
        }
        //Transaktion starten
        em.getTransaction().begin();
        //Objekt persistieren
        em.persist(item);
        //Transaktion abschliessen
        em.getTransaction().commit();
        
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Methode zur Erzeugung von Artikelkategorien
     * @param Kategoriename Name der Kategorie
     * @param Beschreibung Kategoriebeschreibung
     * @param Kommentar Kommentar zur Kategorie
     * @param LKZ Löschkennzeichen
     * @throws DAO.ApplicationException Die Exception wird durchgereicht
     */
    public void createCategory(String Kategoriename,
            String Beschreibung, String Kommentar, boolean LKZ)
            throws ApplicationException {
        //Objekt erzeugen
        Artikelkategorie cat = new Artikelkategorie(Kategoriename, Beschreibung,
                Kommentar, LKZ);
        //Prüfen, ob das Objekt erstellt wurde
        if (cat == null) {
            throw new ApplicationException("Fehler",
                    "Die Werte waren ungültig!");
        }
        //Transaktion starten
        em.getTransaction().begin();
        //Objekt persistieren
        em.persist(cat);
        //Transaktion abschliessen
        em.getTransaction().commit();
    }
    
    /**
     * Methode zur Erzeugung eines Auftragskopfes und seinen Positionen
     * @param Typ Typ des Auftrags (möglich sind: Barauftrag, Sofortauftrag, Terminauftrag, Bestellauftrag)
     * @param Artikel HashMap die ein Artikel(ArtikelID) und die bestellte Menge enthält
     * @param Auftragstext 
     * @param GeschaeftspartnerID Eindeutige Nummer eines Geschäftspartners
     * @param ZahlungskonditionID Eindeute Nummer einer Zahlungskondition
     * @param Status Status des Auftrags
     * @param Abschlussdatum Auftragsabschlussdatum
     * @param Lieferdatum Datum der Lieferung
     * @throws ApplicationException 
     */
    public void erstelleAuftragskopf(String Typ, HashMap<Long, Integer> Artikel, 
            String Auftragstext, long GeschaeftspartnerID,
            long ZahlungskonditionID, String Status, 
            Date Abschlussdatum, Date Lieferdatum) 
            throws ApplicationException {
        
        //Variable für den berechneten Auftragswert
        double Auftragswert = 0;
        
        //Hole den Geschäftspartner anhand der ID aus der Datenbank
        Geschaeftspartner businessPartner = em.find(Geschaeftspartner.class, 
                GeschaeftspartnerID);
        
        //Prüfe ob der Geschäftspartner mit dieser ID existiert
        if(businessPartner == null) {
            throw new ApplicationException("Fehler", 
                    "Der angegebene Kunde konnte nicht gefunden werden.");
        }
        
        //Hole Zahlungskondition anhand der ID aus der Datenbank
        Zahlungskondition paymentCondition = em.find(Zahlungskondition.class,
                ZahlungskonditionID);
        
        //Prüfe ob die Zahlungskondition mit dieser ID existiert
        if(paymentCondition == null) {
            throw new ApplicationException("Fehler", 
                    "Zahlungskondition konnte nicht gefunden werden.");
        }
        
        //Suche den Status anhand des Namnes in der Datenbank
        Status state = this.getStatusByName(Status);
        
        //Prüfen ob ein Status mit diesem Namen existiert
        if (state == null) {
            throw new ApplicationException("Fehler", 
                    "Status konnte nicht gefunden werden");
        }
        
        //Hole das aktuelle Systemdatum
        Calendar cal = Calendar.getInstance();
        Date Erfassungsdatum = cal.getTime();
        
        Auftragskopf orderhead = null;
        ArrayList<Auftragsposition> positionen = new ArrayList<>();
        
        //Anhand des übergebenen Typs wird ein entsprechendes Objekt erzeugt
        if(Typ.equals("Barauftrag")) {
            orderhead = new Barauftragskopf(Auftragstext, Auftragswert, businessPartner,
                    state, Abschlussdatum, Erfassungsdatum, Lieferdatum);
        } else if(Typ.equals("Sofortauftrag")) {
            orderhead = new Sofortauftragskopf(Auftragstext, Auftragswert, 
                    businessPartner, state, paymentCondition, 
                    Abschlussdatum, Erfassungsdatum, Lieferdatum);
        } else if(Typ.equals("Terminauftrag")) {
            orderhead = new Terminauftragskopf(Auftragstext, Auftragswert, 
                    businessPartner, state, paymentCondition, 
                    Abschlussdatum, Erfassungsdatum, Lieferdatum);
        } else if(Typ.equals("Bestellauftrag")) {
            orderhead = new Bestellauftragskopf(Auftragstext, Auftragswert, 
                    businessPartner, state, paymentCondition, 
                    Abschlussdatum, Erfassungsdatum, Lieferdatum);
        //Wenn keine gültige Auftragsart übergeben wurde
        } else {
            throw new ApplicationException("Fehler", "Ungültige Auftragsart!");
        }
        
        //Prüfe ob der Auftragskopf nicht erstellt worden ist
        if (orderhead == null) {
            throw new ApplicationException("Fehler", 
                    "Fehler bei der Erzeugung des Auftragskopfes");
        }
        
        try {
            
            //Transaktion starten
            em.getTransaction().begin();
            //Auftragskopf persistieren
            em.persist(orderhead);
            
            //Hole alle ID aus der Artikelliste
            Set<Long> artikelIDS = Artikel.keySet();
        
            //Für jede ID...
            for (long ID : artikelIDS) {
            
                //...wird der entsprechende Artikel in der Datenbank gesucht
                Artikel artikel = em.find(Artikel.class, ID);
            
                //Es wird geprüft ob der Artikel existiert
                if(artikel == null) {
                throw new ApplicationException("Fehler", "Der angegebene Artikel konne nicht gefunden werden");
                }
            
                //Neue Position anlegen
                Auftragsposition ap = new Auftragsposition();
                
                //Verweis auf den gerade erstellten Auftrag
                ap.setAuftrag(orderhead);
                
                //Verweis auf den jweiligen Artikel
                ap.setArtikel(artikel);
                
                //Handelt es sich um einen Bestellauftrag ergibt sich der
                //Wert der Position aus dem Einkaufswert mal der Menge
                if (orderhead instanceof Bestellauftragskopf) {
                    ap.setEinzelwert(artikel.getEinkaufswert() * Artikel.get(ID));
                    
                //In allen anderen Fällen ergibt sich der Wert der Position aus
                //dem Verkaufswert mal der Menge
                } else {
                    ap.setEinzelwert(artikel.getVerkaufswert() * Artikel.get(ID));    
                }
                
                //Zusammenrechnen des Auftragswerts
                Auftragswert += ap.getEinzelwert();
                
                //Setzen der Menge
                ap.setMenge(Artikel.get(ID));
                
                //Setzen des Erfasungsdatums
                ap.setErfassungsdatum(Erfassungsdatum);
            
                //Füge die Position zur Liste aller Positionen hinzu
                positionen.add(ap);
                
                //Persistiere die angelegte Position
                em.persist(ap);
            }
            
            //Füge die Liste aller Positionen dem Auftrag hinzu
            //Dadurch kann man über den Auftrag auf die hinterlegten Positionen
            //zugreifen
            orderhead.setPositionsliste(positionen);
            
            //Setzen des berechneten Auftragswerts
            orderhead.setWert(Auftragswert);
            
            //Persistieren den Auftrag mit der Positionsliste
            em.persist(orderhead);
            
            //Transaktion beenden
            em.getTransaction().commit();
            
        } catch (Exception e) {
            
            //Falls ein Fehler auftritt, mache die Transaktion rückgängig
            //(Auftragskopf + Positionen) und gebe eine Fehlermeldung aus
            em.getTransaction().rollback();
            throw new ApplicationException("Fehler", 
                    "Fehler beim persistieren der Daten");
        }
    }
    
    /**
     * Methode zur Erzeugung eines Status
     * @param Status Name des Status
     * @throws ApplicationException
     */
    public void createStatus(String Status)
            throws ApplicationException {
        
        //Neues Status-Objekt anlegen
        Status state = new Status();
        //Attribute setzen
        state.setStatus(Status);
        
        //Wenn bei der Erzeugung des Objektes ein Fehler auftritt
        if (state == null) {
            throw new ApplicationException("Fehler",
                    "Bei der Erzeugung des Status ist ein Fehler aufgetreten");
        }
        
        //Transaktion starten
        em.getTransaction().begin();
        //Objekt persistieren
        em.persist(state);
        //Transaktion abschließen
        em.getTransaction().commit();
    }
    
    /**
     * Methode zur Erzeugung einer Anschrift
     * @param Typ Art der Anschrift (Rechnungs-/Lieferanschrift)
     * @param Name
     * @param Vorname
     * @param Titel
     * @param Strasse
     * @param Hausnummer
     * @param PLZ
     * @param Ort
     * @param Staat
     * @param Telefon
     * @param Fax
     * @param Email
     * @param Geburtsdatum
     * @return anschrift Erzeugtes Objekt vom Typ Anschrift
     * @throws ApplicationException 
     */
    public Anschrift createAdress(String Typ, String Name, String Vorname,
            String Titel, String Strasse, String Hausnummer, String PLZ,
            String Ort, String Staat, String Telefon, String Fax,
            String Email, Date Geburtsdatum) throws ApplicationException {
        
        //In der Anschrift wird zusätzlich ein Erfassungsdatum gehalten.
        //Das Erfassungsdatum ist gleich dem Systemdatum.
        Calendar cal = Calendar.getInstance();
        Date Erfassungsdatum = cal.getTime();
        
        Anschrift anschrift = null;
        
        //Anhand des übergebenen Typs wird ein entsprechendes Objekt erzeugt
        if (Typ.equals("Lieferadresse")) {
        
            //Erzeugung des persistenten Anschrift-Objektes
            anschrift = new Lieferanschrift(Name, Vorname, Titel,
                    Strasse, Hausnummer, PLZ, Ort, Staat, Telefon, Fax, Email,
                    Geburtsdatum, Erfassungsdatum, false);
        } else if (Typ.equals("Rechnungsadresse")) {
            
            //Erzeugung des persistenten Anschrift-Objektes
            anschrift = new Rechnungsanschrift(Name, Vorname, Titel,
                    Strasse, Hausnummer, PLZ, Ort, Staat, Telefon, Fax, Email,
                    Geburtsdatum, Erfassungsdatum, false);
            
        //Wenn der Typ ungültig ist
        } else {
            throw new ApplicationException("Fehler", 
                    "Der angegebene Adresstyp existiert nicht");
        }
        
        //Wenn bei der Erzeugung ein Fehler auftritt
        if(anschrift == null) {
            throw new ApplicationException("Fehler",
                    "Beim Anlegen der Anschrift ist ein Fehler aufgetreten");
        }
        
        return anschrift;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * 
     * @param Auftragsart
     * @param LieferzeitSofort
     * @param SperrzeitWunsch
     * @param Skontozeit1
     * @param Skontozeit2
     * @param Skonto1
     * @param Skonto2
     * @param Mahnzeit1
     * @param Mahnzeit2
     * @param Mahnzeit3
     * @throws ApplicationException 
     */
    public void createPaymentConditions(String Auftragsart, 
            double LieferzeitSofort, double SperrzeitWunsch, double Skontozeit1,
            double Skontozeit2, double Skonto1, double Skonto2, 
            double Mahnzeit1, double Mahnzeit2, double Mahnzeit3) 
            throws ApplicationException {
        
        Zahlungskondition conditions = new Zahlungskondition(Auftragsart, 
                LieferzeitSofort, SperrzeitWunsch, Skontozeit1, Skontozeit2, 
                Skonto1, Skonto2, Mahnzeit1, Mahnzeit2, Mahnzeit3);
        
        if (conditions == null) {
            throw new ApplicationException("Fehler", 
                    "Die Daten sind fehlerhaft!");
        }
        
        //Transaktion starten
        em.getTransaction().begin();
        //Objekt persistieren
        em.persist(conditions);
        //Transaktion schließen
        em.getTransaction().commit();
    }
    
    
    /**
     * Methode zur Erzeugung von Geschäftspartnern
     * Es muss zuerst eine Anschrift geholt oder angelegt werden
     * @param Typ Typ des Geschäftspartners(Kunde, Lieferant)
     * @param Lieferadresse
     * @param Rechnungsadresse
     * @param Kreditlimit
     * @param LKZ
     * @throws ApplicationException 
     */
    public void createBusinessPartner(String Typ, Anschrift Lieferadresse, 
            Anschrift Rechnungsadresse, double Kreditlimit, boolean LKZ) 
            throws ApplicationException {
        
        try {
        
            Geschaeftspartner geschaeftspartner = null;
        
            //Anhand des übergebenen Typs wird ein entsprechendes Objekt erzeugt
            if (Typ.equals("Kunde")) {
                geschaeftspartner = new Kunde(Lieferadresse, Rechnungsadresse, 
                        Kreditlimit, LKZ);
            } else if (Typ.equals("Lieferant")) {
                geschaeftspartner = new Lieferant(Lieferadresse, Rechnungsadresse, 
                        Kreditlimit, LKZ);
                
            //Wenn der Geschäftspartnertyp ungültig ist
            } else {
                throw new ApplicationException("Fehler", 
                        "Der angegebene Geschäftspartnertyp existiert nicht.");
            }
        
            //Prüft ob der Geschäftspartner existiert
            if (geschaeftspartner == null) {
                throw new ApplicationException("Fehler", 
                        "Bei der Erzeugung des Kunde ist ein Fehler aufgetreten");
            }
        
            //Transaktion starten
            em.getTransaction().begin();
        
            //Prüft ob die Lieferanschrift gleich der Rechnungsanschrift
            if (Lieferadresse.equals(Rechnungsadresse) || 
                    em.find(Anschrift.class, 
                            Lieferadresse.getAnschriftID()) == null) {
                
                //In diesem Fall muss nur eine Adresse persistiert werden
                em.persist(Lieferadresse);
                
            //Ist Lieferanschrift ungleich Rechnungsanschrift...
            } else {
                //...wird geprüft ob die Lieferanschrift schon existiert
                if (em.find(Anschrift.class, Lieferadresse.getAnschriftID()) == null) {
                    //Wenn nicht, wird die Adresse erzeugt
                    em.persist(Lieferadresse);
                }
                //Es wird ebenfalls geprüft ob die Rechnungsadresse schon existiert
                if (em.find(Anschrift.class, Rechnungsadresse.getAnschriftID()) == null) {    
                    //Wenn nicht, wird diese auc hhier erzeugt
                    em.persist(Rechnungsadresse);
                }
            }
        
            //Nachdem die Anschriften persistent sind, wird der der
            //Geschäftspartner persistent gemacht
            em.persist(geschaeftspartner);
            
            //Transaktion schließen
            em.getTransaction().commit();
            
        } catch (Exception e) {
            
            //Transaktion wird im Fehlerfall rückgängig gemacht
            em.getTransaction().rollback();
            throw new ApplicationException("Fehler", 
                    "Beim Speichern der Daten ist ein Fehler aufgetreten");
        }
    }
    
//</editor-fold>
  
//<editor-fold defaultstate="collapsed" desc="update-Methoden">

    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Passt den Artikelbestand gemäß dem übergebenen Status an.
     * Diese Methode wird NUR von der setzeAuftragsstatus() - Methode
     * aufgerufen!
     * @param positionen Alle Positionen deren Artikelbestand angepasst werden
     * @param bestandsart Bestand der verändert werden soll
     * @throws ApplicationException Wirft eine ApplicationException bei Fehlern
     */
    private void setzeArtikelBestand(Collection<Auftragsposition> positionen, 
            String bestandsart) throws ApplicationException {
        
        try {
            //Iteriere über alle Positionen
            for (Auftragsposition position : positionen) {
                //Prüfe, ob es sich um einen Zulauf an Artikeln handelt
                if ("Zulauf".equals(bestandsart)) {
                    //Erhöhe die Menge des Benstandszulauf
                    position.getArtikel().setZulauf(position.getArtikel()
                            .getZulauf() + position.getMenge());
                } else if ("Reserviert".equals(bestandsart)) {
                    //Verringer die Anzahl von Bestandfrei
                    position.getArtikel().setFrei(
                            position.getArtikel().getFrei() 
                                    - position.getMenge());
                    //Und Erhöhe den Reservierbestand
                    position.getArtikel().setReserviert(
                            position.getArtikel().getReserviert() 
                                    + position.getMenge());
                } else if ("Frei".equals(bestandsart)) {
                    //Verringer die Anzahl von Bestandzulauf
                    position.getArtikel().setZulauf(
                            position.getArtikel().getZulauf() 
                                    - position.getMenge());
                    //Und Erhöhe den Freibestand
                    position.getArtikel().setFrei(
                            position.getArtikel().getFrei() 
                                    + position.getMenge());
                } else if ("Verkauft".equals(bestandsart)) {
                    //Verringer den Bestandreserviert
                    position.getArtikel().setReserviert(
                            position.getArtikel().getReserviert() 
                                    - position.getMenge());
                    //Und Erhöhe den Verkauftbestand
                    position.getArtikel().setVerkauft(
                            position.getArtikel().getVerkauft() 
                                    + position.getMenge());
                } else if ("RueckgaengigBestellung".equals(bestandsart)) {
                    //Verringer die Anzahl von Bestandzulauf
                    position.getArtikel().setZulauf(
                            position.getArtikel().getZulauf() 
                                    - position.getMenge());
                } else if ("RueckgaengigVerkauf".equals(bestandsart)) {
                    //Verringer die Anzahl von Bestandreserviert
                    position.getArtikel().setReserviert(
                            position.getArtikel().getReserviert() 
                                    - position.getMenge());
                    //Und Erhöhe den Freibestand
                    position.getArtikel().setFrei(
                            position.getArtikel().getFrei() 
                                    + position.getMenge());
                }
                    
                //Artikel persistieren
                em.persist(position.getArtikel());
            }
            
        } catch (Exception exc) {
            throw new ApplicationException("Fehler", "Der Bestand konnte " 
                    + "nicht in der Datenbank angepasst werden!");
        }
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Setzt den Status eines Auftrags in erfasst,freigegeben und abgeschlossen.
     * Es werden Fehlerfälle abgefangen und anschliessend je nach auftragsart
     * eine Verfügbarkeitsprüfung durchgeführt und die Artikelbestände angepasst
     * Die Anpassung der Bestände und der Status des Auftrags wird in einer
     * Transaktion abgehandelt.
     * @param auftrag Auftrag deren Status geändert werden soll.
     * @param status Der Status in dem der Auftrag übergehen soll.
     * @throws DAO.ApplicationException Application Exception bei Fehlern.
     */
    public void setzeAuftragsstatus(Auftragskopf auftrag, Status status) 
        throws ApplicationException {
        //Prüfe zu aller erst, ob ein Status übergeben worden ist
        if (status == null) {
            throw new ApplicationException("Fehler", "Der Status wurde " + 
                    "nicht übergeben!");
        }
        
        try {
            //Transaktion starten
            em.getTransaction().begin();

            //Der Status wird erstmalig auf erfasst gesetzt.
            //Hier muss noch keine Bestandsführung durchgeführt werden, da 
            //die Materialien erst ab freigegeben gebucht werden.
            if ("erfasst".equals(status.getStatus()) 
                    && auftrag.getStatus() == null) {
                auftrag.setStatus(status);
            }
            //Wenn der Auftrag bereits im Status Abgeschlossen ist,
            //ist es nicht mehr möglich ihn zu ändern
            else if (auftrag.getStatus().getStatus().equals("abgeschlossen")) {
                throw new ApplicationException("Fehler", "Der Auftrag kann " 
                        + "in keinen anderen Status mehr versetzt werden!");
            }
            //Für den Fall, dass der Status direkt von erfasst in abgeschlossen
            //überführt werden soll, wird eine Exception geworfen
            else if (auftrag.getStatus().getStatus().equals("erfasst") && 
                    status.getStatus().equals("abgeschlossen")) {
                throw new ApplicationException("Fehler", "Der Auftrag kann " 
                        + "nicht von erfasst nach abgeschlossen "
                        + "versetzt werden!");
            }
            //Für den Fall, dass ein Auftrag zurück in den Erfasst Status  
            //gesetzt werden soll, müssen alle Materialbuchungen 
            //rückgängig gemacht werden.
            else if (status.getStatus().equals("erfasst")) {
                //Handelt es sich um eine Bestellung unserer Seits
                if (auftrag instanceof Bestellauftragskopf) {
                    //Wir setzen den vorgemerkten Bestand 
                    //vom Zulauf wieder zurück
                    this.setzeArtikelBestand(auftrag.getPositionsliste(), 
                            "RueckgaengigBestellung");
                } else {
                    //Wir erniedrigen den Bestand von reserviert wieder
                    //und erhöhen den Freibestand
                    this.setzeArtikelBestand(auftrag.getPositionsliste(), 
                            "RueckgaengigVerkauf");
                }
                //Zum Schluss übernehmen wir den Status
                auftrag.setStatus(status);
            } else {
                //Prüfe zunächst die Verfügbarkeit
                if (auftrag.pruefeVerfügbarkeit(status.getStatus())) {
                    //Handelt es sich um eine Bestellung unserer Seits
                    if (auftrag instanceof Bestellauftragskopf) {
                        //Bei Freigegeben werden die Bestände 
                        //unter Zulauf erhöht
                        if (status.getStatus().equals("freigegeben")) {
                            this.setzeArtikelBestand(
                                    auftrag.getPositionsliste(), "Zulauf");
                        //Bei abgeschlossen werden sie von 
                        //Zulauf auf Frei gebucht
                        } else if (status.getStatus().equals("abgeschlossen")) {
                            this.setzeArtikelBestand(
                                    auftrag.getPositionsliste(), "Frei");
                        }
                    //Bei einer Kundenbestellung
                    } else {
                        //Bei freigegeben werden die Bestände
                        //auf Reserviert gebucht
                        if (status.getStatus().equals("freigegeben")) {
                            this.setzeArtikelBestand(
                                    auftrag.getPositionsliste(), "Reserviert");
                        //Bei abgeschlossen Buchen wir von 
                        //Reserviert auf Verkauft
                        } else if (status.getStatus().equals("abgeschlossen")) {
                            this.setzeArtikelBestand(
                                    auftrag.getPositionsliste(), "Verkauft");
                        }
                    }
                    //Zum Schluss übernehmen wir den Status
                    auftrag.setStatus(status);
                }
            }
            //Commit Ausführen
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new ApplicationException("Fehler", e.getMessage());
        }
    }
    
    /**.
     * Methode zum Ändern von Artikeldaten
     * @param Artikelnummer ID des Artikels
     * @param Kategorie Kategorie
     * @param Artikeltext Artikelname
     * @param Bestelltext Bestelltext
     * @param Verkaufswert Wert für den der Artikel verkauft wird
     * @param Einkaufswert Wert für den der Artikel eingekauft wird
     * @param MwST Mehrwertsteuersatz des Artikels in %
     * @param Frei Menge der frei verwendbaren Artikelmengen
     * @throws ApplicationException wenn der Artikel nicht gefunden werden kann
     *                              oder wenn beim Ändern ein Fehler auftritt
     */
    public void setzeArtikel(long Artikelnummer, String Kategorie, 
            String Artikeltext, String Bestelltext, double Verkaufswert, 
            double Einkaufswert, double MwST, int Frei) 
            throws ApplicationException {
        
        //Hole den Artikel anhand der ID aus der Datenbank
        Artikel artikel = em.find(Artikel.class, Artikelnummer);
        
        //Wenn der Artikel nicht gefunden werden kann, wird eine Fehlermeldung 
        //ausgegeben
        if(artikel == null) {
            throw new ApplicationException("Fehler", 
                    "Der Artikel konnte nicht gefunden werden");
        }
        
        try {
            //Artikeldaten lokal aktualisieren
            artikel.setKategorie(this.getCategory(Kategorie));
            artikel.setArtikeltext(Artikeltext);
            artikel.setBestelltext(Bestelltext);
            artikel.setVerkaufswert(Verkaufswert);
            artikel.setEinkaufswert(Einkaufswert);
            artikel.setMwST(MwST);
            artikel.setFrei(Frei);

            //Transaktion starten
            em.getTransaction().begin();
            //Artikel persistieren
            em.persist(artikel);
            //Transaktion beenden
            em.getTransaction().commit();
        
        } catch(Exception e) {
            
            //Wenn beim Ändern bzw. Persistieren der Daten ein Fehler auftritt
            //wird eine Fehlermeldung ausgegeben
            throw new ApplicationException("Fehler", 
                    "Beim Verarbeiten der Daten ist ein Fehler aufgetreten. "
                            + "Die Daten wurden nicht geändert.");
            
        }
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="get-Methoden">
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * 
     * @param id
     * @return
     * @throws ApplicationException 
     */
    public Zahlungskondition getPaymentConditionsById(long id) 
            throws ApplicationException {
        //Konditionen aus der DB laden
        Zahlungskondition conditions = em.find(Zahlungskondition.class, id);
        //Prüfen, ob Daten gefunden worden sind
        if (conditions == null) {
            throw new ApplicationException("Fehler", 
                    "Keine Zahlungskonditionen gefunden!");
        }
        
        return conditions;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    public Collection<Artikelkategorie> gibAlleKategorien() 
            throws ApplicationException {

        return this.em.createQuery("SELECT ST FROM Artikelkategorie ST", 
                Artikelkategorie.class).getResultList();
        
    }
    
//    /**
//     * 
//     * @param type
//     * @return
//     * @throws ApplicationException 
//     */
//    public Zahlungskondition getPaymentConditionsByType(Auftragsart type) 
//            throws ApplicationException {
//        String sqlQuery = null;
//        Zahlungskondition conditions = null;
//        //Prüfe, ob eine Auftragsart übergeben wurde
//        if (type == null) {
//            throw new ApplicationException("Fehler", 
//                    "Übergeben Sie eine gültige Auftragsart!");
//        }
//        //Sql-Statement erstellen
//        sqlQuery =
//                "SELECT ST FROM Zahlungskondition ST WHERE ST.Auftragsart = '" +
//                type.getAuftragsart() + "'";
//        
//        try {
//            //Konditionen aus der DB Laden
//            conditions = (Zahlungskondition) 
//                    em.createQuery(sqlQuery).getSingleResult();
//        } catch (Exception e) {
//            throw new ApplicationException("Fehler", e.getMessage());
//        }
//        
//        //Prüfen, ob was gefunden wurde
//        if (conditions == null)
//            throw new ApplicationException("Fehler",
//                    "Es wurde keine Kategorie gefunden!");
//        
//        return conditions;
//    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     *
     * @param name
     * @return
     * @throws ApplicationException
     */
    public Artikelkategorie getCategory(String name)
        throws ApplicationException {
        String sqlQuery = null;
        Artikelkategorie cat = null;
        if (name == null)
            throw new ApplicationException("Fehler",
                    "Geben Sie eine Kategorie an!");
        sqlQuery =
                "SELECT ST FROM Artikelkategorie ST WHERE ST.Kategoriename = '" +
                name + "'";
        try {
            cat = (Artikelkategorie) em.createQuery(sqlQuery).getSingleResult();
        } catch (Exception e) {
            throw new ApplicationException("Fehler", e.getMessage());
        }
        
        
        if (cat == null)
            throw new ApplicationException("Fehler",
                    "Es wurde keine Kategorie gefunden!");
        
        return cat;
    }
    
    /**
     * Methode zum Holen eine Artikels
     * @param Artikelnummer Nummer(ID) eines Artikels
     * @return Das persistente Objekt dieses Artikels
     * @throws ApplicationException wenn der Artikel nicht gefunden wird
     */
    public Artikel getItem(long Artikelnummer)
            throws ApplicationException {
        
        //Suche den Artikel mit der angegebenen ID aus der Datenbank
        Artikel item = em.find(Artikel.class, Artikelnummer);
        
        //Artikel existiert nicht
        if (item == null) {
            throw new ApplicationException("Fehler",
                    "Es wurde kein Artikel gefunden!");
        }
        
        return item;
    }
    
    public Kunde getCustomer(long Kundennummer) throws ApplicationException {
        
        Kunde kunde = em.find(Kunde.class, Kundennummer);
        
        if (kunde == null) {
            throw new ApplicationException("Fehler", 
                    "Der Kunde konnte nicht gefunden werden");
        }
        
        return kunde;
    }
    
    public Status getStatusByName(String name) throws ApplicationException {
        
        Query query = null;
        Status status = null;
        if (name == null)
            throw new ApplicationException("Fehler",
                    "Geben Sie eine Kategorie an!");
        query = em.createQuery("SELECT ST FROM Status ST "
                + "WHERE ST.Status LIKE :name").setParameter("name", name);
        try {
            status = (Status) query.getSingleResult();
        } catch (Exception e) {
            throw new ApplicationException("Fehler", e.getMessage());
        }
        
        if (status == null)
            throw new ApplicationException("Fehler",
                    "Es wurde kein Status gefunden!");
        
        return status; 
    }
    
    /**
     * Methode zum Holen von Aufträgen
     * @param Auftragsnummer Einzigartige Nummer des Auftrags
     * @return Der persistente Auftrag
     * @throws ApplicationException falls der Auftrag nicht gefunden werden kann
     */
    public Auftragskopf getOrderHead(long Auftragsnummer) throws ApplicationException {
        
        //Persistente Abbildung des Auftrags holen
        Auftragskopf auftragskopf = em.find(Auftragskopf.class, Auftragsnummer);
        
        //Falls der Auftrag nicht existiert
        if (auftragskopf == null) {
            throw new ApplicationException("Fehler", "Der Auftrag konnte nicht gefunden werden");
        }
        
        return auftragskopf;
    }
    
    /**.
     * Die Methode gibt die Anzahl der Positionen eines Auftrages zurück.
     * @param AuftragskopfId ID des Auftrags.
     * @return Anzahl der Positionen des angegebenen Auftrags.
     * @throws ApplicationException wenn der Auftrag nicht gefunden werden kann.
     */
    public int gibAnzahlPositionen(long AuftragskopfId) 
        throws ApplicationException {
        
        //Hole Auftrag anhand der ID aus der Datenbank
        Auftragskopf ak = em.find(Auftragskopf.class, AuftragskopfId);
        
        //Wenn der Auftrag nicht gefunden werden kann, wird eine Fehlermeldung
        //ausgegeben
        if(ak == null) {
            throw new ApplicationException("Fehler", 
                    "Der Auftrag konnte nicht gefunden werden");
        }
        
        //Gib die Anzahl der Positionen zurück
        return ak.getPositionsliste().size();
        
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="remove-Methoden">
//Subject to change
//</editor-fold>
    
    /**
     * Loginfunktion
     * @param username Benutzername
     * @param password Passwort
     * @return gibt an ob der Login erfolgreich war oder nicht
     * @throws DAO.ApplicationException Die Exception wird durchgereicht
     */
    public Benutzer doLogin(String username, String password) 
            throws ApplicationException {
        
        boolean loginSuccessful = false;
        //Suche Benutzer anhand des Benutzernames in der Datenbank
        Benutzer benutzer = em.find(Benutzer.class, username);
        
        //Benutzer wurde nicht gefunden
        if(benutzer == null) {
            //Werfe ApplicationException, implizit Funktionsabbruch
            throw new ApplicationException("Meldung", 
                    "Der angegebene Benutzer wurde nicht gefunden");
        }
        
        //Eingegebenes Passwort(als MD5 Hash) stimmt nicht dem Passwort in der 
        //Datenbank (ebenfalls MD5 Hash) überein
        if(benutzer.getPasswort().equals(getHash(password))) {
            return benutzer;
        } else {
            return null;
        }
    }
    
    /**
     * Methode zur Generierung eines MD5 Hashs aus dem Passwort
     * @param password Eingegebenes Passwort
     * @return MD5 Hash des Passwortes
     */
    private String getHash(String password) throws ApplicationException {
      
        String digest = null;
        try {
            //Instanzierung des Hash Dienstes
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Erstellung des Hashs
            byte[] hash = md.digest(password.getBytes("UTF-8"));
           
            //Konvertierung des Byte Arrays in einen Hexadezimalstring
            StringBuilder sb = new StringBuilder(2*hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b&0xff));
            }
          
            digest = sb.toString();
            
            return digest;
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            throw new ApplicationException("Fehler", ex.getMessage());
        }
    }
    
    //Tbd
    public void close() throws ApplicationException {
        em.close();
        System.exit(0);
    }
}
