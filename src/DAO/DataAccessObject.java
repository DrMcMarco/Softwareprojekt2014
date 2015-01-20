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
import DTO.Parameterdaten;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.metamodel.Attribute;
/**
 *
 * @author Simon <Simon.Simon at your.org>
 */
public class DataAccessObject {
    
    /**
     * Fehler Titel für Exception.
     */
    private static final String FEHLER_TITEL = "Fehler";
    
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
     * @param sortierung Absteigend oder Aufsteigende sortierung.
     * @return gibt eine Collection<?> zurück mit allen gefunden Datensätzen
     * @throws DAO.ApplicationException Die Exception wird durchgereicht
     * 
     * TO-DO: Anpassung wenn nach einem String gesucht wird müssen hochkommas
     * hinzugefügt werden
     */
    public Collection<?> suchAbfrage(String eingabe, String tabelle, 
            String sortierung) 
        throws ApplicationException {
        //Datendeklaration
        String sqlAbfrage = null;
        List<?> sqlErgebnisListe = null;
        String maxAnzReihen = " FETCH NEXT 20 ROWS ONLY";
        //Erstelle den Parser und übergebe die Suchattribute.
        Parser parser = new Parser(this.gibAlleSuchAttribute());
        //Parse den Suchausdruck und hole das SQL-Statement
        sqlAbfrage = parser.parse(eingabe, tabelle, sortierung);
        //Prüfe, ob das Parsen erfolgreich war.
        if (sqlAbfrage == null) {
            throw new ApplicationException(FEHLER_TITEL, 
                    "Beim Parsen ist ein Fehler aufgetreten!");
        }
        

        try {
            //Um die Klasse zu identifizieren, 
            //muss das ä durch ae getauscht werden, da die Tabelle mit ä
            //und die Klasse mit ae geschrieben wird.
            if ("Geschäftspartner".equals(tabelle)) {
                tabelle = tabelle.replace("ä", "ae");
            }
            //Ergebnis aus der DB laden und als Liste speichern
            //Es wird zum einen das SQL-Statement übergeben und zum anderen
            //Der Class-Type zur typisierung der Objekte in der Liste
            //Es wird zudem noch die Maxanzahl an Datensätzen gesetzt die 
            //ausgegeben werden soll.
            sqlErgebnisListe = em.createNativeQuery(sqlAbfrage + maxAnzReihen, 
                    Class.forName("DTO." + tabelle)).getResultList();

        } catch (Exception e) {
            throw new ApplicationException(FEHLER_TITEL, 
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
     * @param kategorie
     * @param artikeltext
     * @param bestelltext
     * @param verkaufswert
     * @param einkaufswert
     * @param MwST
     * @param Frei
     * @param Reserviert
     * @param Zulauf
     * @param Verkauft
     * @throws DAO.ApplicationException Die Exception wird durchgereicht
     */
    public void createItem(String kategorie, String artikeltext, 
        String bestelltext, double verkaufswert, double einkaufswert,
        int MwST, int Frei, int Reserviert, int Zulauf, int Verkauft)
        throws ApplicationException {
        //Suche die Artikelkategorie anhand des Kategorienamen
        Artikelkategorie cat = this.getCategory(kategorie);
        
        if (cat == null) {
            throw new ApplicationException(FEHLER_TITEL,
                    "Der Kategoriename existiert nicht!");
        }
        
        Artikel item = new Artikel(cat, artikeltext, bestelltext,
                verkaufswert, einkaufswert, MwST, Frei, Reserviert,
                Zulauf, Verkauft);
        //Prüfen, ob das Objekt erstellt wurde
        if (item == null) {
            throw new ApplicationException(FEHLER_TITEL,
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
            String Beschreibung, String Kommentar)
            throws ApplicationException {
        //Objekt erzeugen
        Artikelkategorie cat = new Artikelkategorie(Kategoriename, Beschreibung,
                Kommentar);
        //Prüfen, ob das Objekt erstellt wurde
        if (cat == null) {
            throw new ApplicationException(FEHLER_TITEL,
                    "Die Werte waren ungültig!");
        }
        //Transaktion starten
        em.getTransaction().begin();
        //Objekt persistieren
        em.persist(cat);
        //Transaktion abschliessen
        em.getTransaction().commit();
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.12.14   loe     angelegt                              */
    /* 22.12.14   loe     überarbeitet                          */
    /*----------------------------------------------------------*/
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
        
        if (Artikel.isEmpty()) {
            throw new ApplicationException("Fehler", 
                    "Es wurden keine Auftragspositionen angegeben.");
        }
        
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
        
        Auftragskopf ak = null;
        ArrayList<Auftragsposition> positionen = new ArrayList<>();
        
        //Anhand des übergebenen Typs wird ein entsprechendes Objekt erzeugt
        switch (Typ) {
            case "Barauftrag":
                ak = new Barauftragskopf(Auftragstext, Auftragswert, businessPartner,
                        state, Abschlussdatum, Erfassungsdatum, Lieferdatum);
                break;
            case "Sofortauftrag":
                ak = new Sofortauftragskopf(Auftragstext, Auftragswert,
                        businessPartner, state, paymentCondition,
                        Abschlussdatum, Erfassungsdatum, Lieferdatum);
                break;
            case "Terminauftrag":
                ak = new Terminauftragskopf(Auftragstext, Auftragswert,
                        businessPartner, state, paymentCondition,
                        Abschlussdatum, Erfassungsdatum, Lieferdatum);
                break;
            case "Bestellauftrag":
                ak = new Bestellauftragskopf(Auftragstext, Auftragswert,
                        businessPartner, state, paymentCondition,
                        Abschlussdatum, Erfassungsdatum, Lieferdatum);
                //Wenn keine gültige Auftragsart übergeben wurde
                break;
            default:
                throw new ApplicationException("Fehler", "Ungültige Auftragsart!");
        }
        
        //Prüfe ob der Auftragskopf nicht erstellt worden ist
        if (ak == null) {
            throw new ApplicationException("Fehler", 
                    "Fehler bei der Erzeugung des Auftragskopfes");
        }
        
        try {
            
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
            
                ak.addPosition(artikel, Artikel.get(ID));
                
            }
            
            //Transaktion starten
            em.getTransaction().begin();
            
            //Persistieren den Auftrag mit der Positionsliste
            em.persist(ak);
            
            //Transaktion beenden
            em.getTransaction().commit();
            
        } catch (Exception e) {
            
            //Falls ein Fehler auftritt, mache die Transaktion rückgängig
            //(Auftragskopf + Positionen) und gebe eine Fehlermeldung aus

            throw new ApplicationException("Fehler", 
                    e.getMessage());
        }
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 10.12.14   loe     angelegt                              */
    /*----------------------------------------------------------*/
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
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 10.12.14   loe     angelegt                              */
    /* 06.01.15   loe     überarbeitet                          */
    /*----------------------------------------------------------*/
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
        switch (Typ) {
            case "Lieferadresse":
                //Erzeugung des persistenten Anschrift-Objektes
                anschrift = new Lieferanschrift(Name, Vorname, Titel,
                        Strasse, Hausnummer, PLZ, Ort, Staat, Telefon, Fax, Email,
                        Geburtsdatum, Erfassungsdatum);
                break;
            case "Rechnungsadresse":
                //Erzeugung des persistenten Anschrift-Objektes
                anschrift = new Rechnungsanschrift(Name, Vorname, Titel,
                        Strasse, Hausnummer, PLZ, Ort, Staat, Telefon, Fax, Email,
                        Geburtsdatum, Erfassungsdatum);
                
                //Wenn der Typ ungültig ist
                break;
            default:
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
            int LieferzeitSofort, int SperrzeitWunsch, int Skontozeit1,
            int Skontozeit2, double Skonto1, double Skonto2, 
            int Mahnzeit1, int Mahnzeit2, int Mahnzeit3) 
            throws ApplicationException {
        
        Zahlungskondition conditions = new Zahlungskondition(Auftragsart, 
                LieferzeitSofort, SperrzeitWunsch, Skontozeit1, Skontozeit2, 
                Skonto1, Skonto2, Mahnzeit1, Mahnzeit2, Mahnzeit3);
        
        if (conditions == null) {
            throw new ApplicationException("Fehler", 
                    "Beim Anlegen der Zahlungskondition ist ein Fehler aufgetreten!");
        }
        
        //Transaktion starten
        em.getTransaction().begin();
        //Objekt persistieren
        em.persist(conditions);
        //Transaktion schließen
        em.getTransaction().commit();
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 10.12.14   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Methode zur Erzeugung von Geschäftspartnern
     * Es muss zuerst eine Anschrift geholt oder angelegt werden
     * @param Typ Typ des Geschäftspartners(Kunde, Lieferant)
     * @param Lieferadresse
     * @param Rechnungsadresse
     * @param Kreditlimit
     * @throws ApplicationException 
     */
    public void createBusinessPartner(String Typ, Anschrift Lieferadresse, 
            Anschrift Rechnungsadresse, double Kreditlimit) 
            throws ApplicationException {
        
        try {
        
            Geschaeftspartner geschaeftspartner = null;
        
            //Anhand des übergebenen Typs wird ein entsprechendes Objekt erzeugt
            if (Typ.equals("Kunde")) {
                geschaeftspartner = new Kunde();
            } else if (Typ.equals("Lieferant")) {
                geschaeftspartner = new Lieferant();
                
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
           
            geschaeftspartner.setKreditlimit(Kreditlimit);
            geschaeftspartner.setLKZ(false);
            
            //Wenn die Anschriften identisch sind, wird nur die Rechnungsadresse persistiert
            if (Rechnungsadresse.equals(Lieferadresse)) {
                geschaeftspartner.setRechnungsadresse(Rechnungsadresse);
                geschaeftspartner.setLieferadresse(Rechnungsadresse);
                
            //Ansonsten werden beide persistiert    
            } else {
                geschaeftspartner.setRechnungsadresse(Rechnungsadresse);
                geschaeftspartner.setLieferadresse(Lieferadresse);
            }
        
            //Transaktion starten
            em.getTransaction().begin();
            
            //Nachdem die Anschriften persistent sind, wird der der
            //Geschäftspartner persistent gemacht
            em.persist(geschaeftspartner);
            
            //Transaktion schließen
            em.getTransaction().commit();
            
        } catch (Exception e) {
            
            //Transaktion wird im Fehlerfall rückgängig gemacht
            throw new ApplicationException("Fehler", 
                    "Beim Speichern der Daten ist ein Fehler aufgetreten");
        }
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Methode zum Erstellen eines Benutzer
     * @param Benutzername Der Name des Benutzer (eindeutig)
     * @param Passwort Passwort, wird als MD5-Hash in der Datenbank abgelegt
     * @param istAdmin Bestimmt ob der Benutzer ein Admin ist oder nicht
     * @throws ApplicationException wenn der Benutzer nicht angelegt werden konnte
     */
    public void erstelleBenutzer(String Benutzername, String Passwort, 
            boolean istAdmin) throws ApplicationException {
        
        //Nach nach einem Benutzer mit dem angegebenen Benutzernamen in der
        //Datenbank
        Benutzer benutzer = em.find(Benutzer.class, Benutzername);
        
        //Wenn ein Benutzer in der Datenbank gefunden wird, ist der
        //Benutzername bereits vergeben und es muss ein neuer ausgewählt werden
        if (benutzer != null) {
            throw new ApplicationException("Fehler", 
                    "Es wurde bereits ein Benutzer mit diesem Benutzernamen erstellt.");
        } else {
            
            //Neuen Benutzer anlegen
            benutzer = new Benutzer();
  
            //Wenn beim Anlegen des Benutzer ein Fehler auftritt wird eine 
            //entsprechende Exception geworfen
            if (benutzer == null) {
                throw new ApplicationException("Fehler", 
                        "Fehler beim Anlegen des Benutzers");
            }

            //Übergebene Werte setzen
            //Das Passwort wird vorher in einen MD5-Hashwert umgewandelt
            benutzer.setBenutzername(Benutzername);
            benutzer.setPasswort(this.getHash(Passwort));
            benutzer.setIstAdmin(istAdmin);

            //Transaktion starten
            em.getTransaction().begin();
            //Benutzer persistieren
            em.persist(benutzer);
            //Transaktion beenden
            em.getTransaction().commit();
            
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
            throw new ApplicationException(FEHLER_TITEL, "Der Bestand konnte " 
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
            throw new ApplicationException(FEHLER_TITEL, "Der Status wurde " + 
                    "nicht übergeben!");
        }
        
        try {
            //Transaktion starten
            //em.getTransaction().begin();

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
                throw new ApplicationException(FEHLER_TITEL, "Der Auftrag kann " 
                        + "in keinen anderen Status mehr versetzt werden!");
            }
            //Für den Fall, dass der Status direkt von erfasst in abgeschlossen
            //überführt werden soll, wird eine Exception geworfen
            else if (auftrag.getStatus().getStatus().equals("erfasst") && 
                    status.getStatus().equals("abgeschlossen")) {
                throw new ApplicationException(FEHLER_TITEL, "Der Auftrag kann " 
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
                if (auftrag.pruefeVerfuegbarkeit(status.getStatus())) {
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
            //em.getTransaction().commit();
        } catch (Exception e) {
            throw new ApplicationException(FEHLER_TITEL, e.getMessage());
        }
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 07.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
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
    public void aendereArtikel(long Artikelnummer, String Kategorie, 
            String Artikeltext, String Bestelltext, double Verkaufswert, 
            double Einkaufswert, int MwST, int Frei) 
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
   
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.01.15   loe     angelegt                              */
    /* 13.01.15   loe     überarbeitet                          */
    /* 14.01.15   loe     überarbeitet                          */
    /* 17.01.15   loe     grundlegend überarbeitet              */
    /* 18.01.15   loe     Verbesserungen/letzte Änderungen/     */
    /*                    Kommentare                            */
    /*----------------------------------------------------------*/
    /**
     * Methode zum Ändern von Aufträgen und Auftragspositionen.
     * @param AuftragskopfID ID des Auftrags (eindeutig)
     * @param GeschaeftspartnerID ID des Geschäftspartners (eindeutig)
     * @param ZahlungskonditionID ID einer Zahlungskondition (eindeutig)
     * @param Artikel HashMap: Key = Artikel-ID, Value = Menge
     * @param Auftragstext Zusatztext für einen Auftrag
     * @param Status Name des Status
     * @param Abschlussdatum Abschlussdatum
     * @param Lieferdatum Lieferdatum
     * @return ein String der verschiedene Statusnachrichten ausgibt
     * @throws ApplicationException wenn zu den übergebenen IDs keine passenden
     *                              Objekte gefunden werden können
     */
    public String aendereAuftrag(long AuftragskopfID, long GeschaeftspartnerID,
            long ZahlungskonditionID, HashMap<Long, Integer> Artikel, 
            String Auftragstext, String Status, Date Abschlussdatum, 
            Date Lieferdatum) throws ApplicationException {
        
        //String der je nach Ergebnis der Abfrage eine spezifische Meldung 
        //enthält
        String ergebnis = "";
        
        //Auftragswert, wird später aufsummiert
        double Auftragswert = 0;
        
        //Liste aller Auftragspositionen
        ArrayList<Auftragsposition> positionen = new ArrayList<>();
        
        //Suche den Auftragskopf anhand der ID in der Datenbank
        Auftragskopf ak = em.find(Auftragskopf.class, AuftragskopfID);
        
        //Suche den Geschäftspartner anhand der ID in der Datenbank
        Geschaeftspartner gp = em.find(Geschaeftspartner.class, 
                GeschaeftspartnerID);
        
        //Suche die Zahlungskondition anhand der ID in der Datenbank
        Zahlungskondition zk = em.find(Zahlungskondition.class,
                ZahlungskonditionID);
        
        //Wenn der Auftrag nicht gefunden werden konnte
        if (ak == null) {
            throw new ApplicationException("Fehler",
                    "Der Auftragskopf konnte nicht gefunden werden.");
        }
        
        //Wenn der Geschäftspartner nicht gefunden werden konnte
        if (gp == null) {
            throw new ApplicationException("Fehler",
                    "Der Geschäftspartner konnte nicht gefunden werden.");
        }
        
        //Wenn die Zahlungskondition nicht gefunden werden konnte
        if (zk == null) {
            throw new ApplicationException("Fehler",
                    "Die Zahlungskondition konnte nicht gefunden werden.");
        }
        
        //Je nach dem aktuellen Status des Auftrags
        switch (ak.getStatus().getStatus().toLowerCase()) {
            case "abgeschlossen":
                ergebnis = "Dieser Auftrag ist abgeschlossen und kann daher nicht "
                        + "mehr geändert werden.";
                break;
            case "freigegeben":
                //TODO: Weitere Attribute? + Fehlerbehandelung
                em.getTransaction().begin();
                ak.setAuftragstext(Auftragstext);
                this.setzeAuftragsstatus(ak, this.getStatusByName(Status));
                em.persist(ak);
                em.getTransaction().commit();
                ergebnis = "Dieser Auftrag ist bereits freigegeben, "
                        + "daher kann nur der Status geändert werden.";
                break;
            default:
                
                try {
                    
                    //Transaktion starten
                    em.getTransaction().begin();
                    
                    //Auftragsattribute setzen
                    ak.setAuftragstext(Auftragstext);
                    ak.setAbschlussdatum(Abschlussdatum);
                    ak.setLieferdatum(Lieferdatum);
                    ak.setGeschaeftspartner(gp);
                    
                    //Methode zum Aktualisieren der Positionen
                    this.aenderePositionen(ak, Artikel);
                    
                    //Nachdem die Positionen aktualisiert wurden, wird der 
                    //Auftragswert neu berechnet
                    ak.berrechneAuftragswert();
                    
                    /*
                        Wenn alle vorherigen Schritte fehlerfrei durchlaufen 
                        wurden, wird der Auftragsstatus gesetzt.
                        Bei den Statusänderungen:
                            - erfasst -> freigegeben
                            - freigegben -> erfasst
                            - freigegeben -> abgeschlossen
                        wird zusätzlich durch diese Methode die Verfügbarkeits-
                        prüfung und die Bestandsführung durchgeführt
                    */
                    this.setzeAuftragsstatus(ak, this.getStatusByName(Status));
                    
                    //Auftragskopf persistieren
                    em.persist(ak);
                    
                    //Transaktion beenden
                    em.getTransaction().commit();
                    
                    ergebnis = "Änderungen erfolgreich durchgeführt.";
                
                } catch (Exception e) {
                    
                    //Sollte eine Fehler auftreten, wird ein Rollback der 
                    //Transaktion ausgelöst
                    em.getTransaction().rollback();
                    
                    throw new ApplicationException("Fehler", 
                            e.getMessage());
                    
                }
                
                break;
        }
        return ergebnis;
    }
    
        
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 17.01.15   loe     angelegt                              */
    /* 18.01.15   loe     überarbeitet                          */
    /*----------------------------------------------------------*/
    /**
     * Methode zum Ändern der Positionen.
     * Diese Methode sollte nur in der Methode "aendereAuftrag" aufgerufen werden!
     * @param Auftrag Auftragskopf-Objekt
     * @param artikel HashMap: Key = Artikel-ID, Value = Menge
     * @throws ApplicationException wenn zu den übergebenen IDs keine passenden
     *                              Objekte gefunden werden können
     */
    private void aenderePositionen(Auftragskopf Auftrag, HashMap<Long, Integer> artikel) 
            throws ApplicationException {
        
        //Positionsliste aus dem Auftrag holen und zwischenspeichern
        ArrayList<Auftragsposition> positionsliste = Auftrag.getPositionsliste();
        
        //Artikel-IDs aus der HashMap holen
        Set<Long> artikelSet = artikel.keySet();
        
        //Für jede Artikel-ID im Set...
        for (long ID : artikelSet) {
            
            //...wird der entsprechende Artikel in der Datenbank gesucht
            Artikel artikelObj = em.find(Artikel.class, ID);
            
            //Wenn der Artikel nicht gefunden werden kann
            if (artikelObj == null) {
                throw new ApplicationException("Fehler", 
                        "Der Artikel mit der ID " + ID + " konnte nicht gefunden"
                                + " werden.");
            }
            
            /*
                ...wird, zusammen mit der Auftrags-ID, versucht eine 
                Auftragsposition zu finden.
                Wird keine gefunden, gibt die Methode "null" zurück
            */
            Auftragsposition ap = this.gibAuftragspositionNachArtikel
                                            (Auftrag.getAuftragskopfID(), ID);
            
            //Wurde eine Auftragsposition gefunden...
            if (ap != null) {
                
                //...wird geprüft, ob die Position vorher bereits existiert hat
                //aber gelöscht wurde
                if (ap.isLKZ()) {
                    //WEnn ja, wird vor dem Ändern das LKZ entfernt
                    ap.setLKZ(false);
                }
                
                //Suche die Auftragsposition in der Positionsliste
                int index = Auftrag.getPositionsliste().indexOf(ap);
                //Setze den Artikel in der Position
                Auftrag.getPositionsliste().get(index).setArtikel(artikelObj);
                //Setze die Menge in der Position
                Auftrag.getPositionsliste().get(index).setMenge(artikel.get(ID));
                //Lösche die Position aus der zwischengespeicherten Positionsliste
                positionsliste.remove(ap);
            
            //Wenn in der Datenbank keine Position zu den IDs gefunden wurde...
            } else {
                
                //...wird eine neue Position erzeugt und der Positionsliste
                //hinzugefügt
                Auftrag.addPosition(artikelObj, artikel.get(ID));               
            }
        }
        
        //Alle Position die am Ende der Schleife noch in der zwischengespeicherten
        //Positionsliste vorkommen, hat der Benutzer in der GUI gelöscht
        for (Auftragsposition ap : positionsliste) {
            
            //Für diese Positionen wird das LKZ gesetzt
            this.loeschePosition(Auftrag.getAuftragskopfID(), ap.getPositionsnummer());
            
        }
        
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.01.15   loe     angelegt                              */
    /* 13.01.15   loe     überarbeitet                          */
    /* 14.01.15   loe     überarbeitet                          */
    /*----------------------------------------------------------*/
    /**
     * Ändert sowohl die Attribute des Geschäftspartners als auch der Adresse
     * @param GeschaeftspartnerID ID des Geschäftspartners in der Datenbank
     * @param modus Status der Checkbox 'wie Anschrift' in der Geschäftspartnerverwaltung
     * @param Kreditlimit Kreditlimit des Geschäftspartners
     * @param Name Nachname
     * @param Vorname Vorname
     * @param Titel Anrede
     * @param rStrasse Straße (ohne Hausnummer) (Rechnung)
     * @param rHausnummer Hausnummer (Rechnung)
     * @param rPLZ Postleitzahl (Rechnung)
     * @param rOrt Ort (Rechnung)
     * @param lStrasse Straße (ohne Hausnummer) (Liefer)
     * @param lHausnummer Hausnummer (Liefer)
     * @param lPLZ Postleitzahl (Liefer)
     * @param lOrt Ort (Liefer)
     * @param Staat Staat (normalerweise Deutschland)
     * @param Telefon Telefonnummer
     * @param Fax Faxnummer
     * @param Email Emailadresse
     * @param Geburtsdatum Geburtsdatum des Geschäftspartners (über 18!)
     * @throws ApplicationException wenn der Geschäftspartner oder die Anschrift nicht gefunden werden können
     */
    public void aendereGeschaeftspartner(long GeschaeftspartnerID, boolean modus,
            double Kreditlimit, String Name, String Vorname, String Titel, 
            String rStrasse, String rHausnummer, String rPLZ, String rOrt, 
            String lStrasse, String lHausnummer, String lPLZ, String lOrt,
            String Staat, String Telefon, String Fax, String Email, 
            Date Geburtsdatum) throws ApplicationException {
        
        //Variablen für die Anschriften
        Anschrift rechnungsanschrift = null;
        Anschrift lieferanschrift = null;
        
        //Suche einen Geschäftspartner anhand der ID in der Datenbank
        Geschaeftspartner gp = em.find(Geschaeftspartner.class, GeschaeftspartnerID);
        
        //Wenn der Geschäftspartner nicht gefunden werden kann oder gelöscht ist
        if (gp == null || gp.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Der Geschäftspartner konnte nicht gefunden werden");
        }
        
        //Anschriften aus dem Geschäftspartner-Objekt holen
        rechnungsanschrift = gp.getRechnungsadresse();
        lieferanschrift = gp.getLieferadresse();
        
        //Grundlegende Attribute ändern
        gp.setKreditlimit(Kreditlimit);
        
        rechnungsanschrift.setName(Name);
        rechnungsanschrift.setVorname(Vorname);
        rechnungsanschrift.setTitel(Titel);
        rechnungsanschrift.setStaat(Staat);
        rechnungsanschrift.setTelefon(Telefon);
        rechnungsanschrift.setFAX(Fax);
        rechnungsanschrift.setEmail(Email);
        rechnungsanschrift.setGeburtsdatum(Geburtsdatum);
        
        lieferanschrift.setName(Name);
        lieferanschrift.setVorname(Vorname);
        lieferanschrift.setTitel(Titel);
        lieferanschrift.setStaat(Staat);
        lieferanschrift.setTelefon(Telefon);
        lieferanschrift.setFAX(Fax);
        lieferanschrift.setEmail(Email);
        lieferanschrift.setGeburtsdatum(Geburtsdatum); 
        
        //Wenn die beiden Adressen gleich sind...
        if (gp.getRechnungsadresse().equals(gp.getLieferadresse())) {

            /*
             *   ...prüfe:
             *       - Möchte der Kunde seine Lieferanschrift = Rechnungsanschrift
             *         setzen (Variable modus) UND
             *       - Hat sich die Anschrift überhaupt verändert
             */
            if (modus && (!rStrasse.equals(rechnungsanschrift.getStrasse()) || 
                          !rHausnummer.equals(rechnungsanschrift.getHausnummer()) ||
                          !rPLZ.equals(rechnungsanschrift.getPLZ()) || 
                          !rOrt.equals(rechnungsanschrift.getOrt()))) {
                
                //Wenn ja, ändere die Adresse
                rechnungsanschrift.setStrasse(rStrasse);
                rechnungsanschrift.setHausnummer(rHausnummer);
                rechnungsanschrift.setPLZ(rPLZ);
                rechnungsanschrift.setOrt(rOrt);  
            }
            
            /*
             *   ...prüfe:
             *       - Möchte der Kunde unterschiedliche Anschriften haben 
             *         (Variable modus) UND
             *       - Hat sich die Adresse überhaupt verändert
             */
            if (!modus && (!lStrasse.equals(rechnungsanschrift.getStrasse()) || 
                           !lHausnummer.equals(rechnungsanschrift.getHausnummer()) ||
                           !lPLZ.equals(rechnungsanschrift.getPLZ()) || 
                           !lOrt.equals(rechnungsanschrift.getOrt()))) {
                
                //Wenn ja, ändere die Rechnungsanschrift
                rechnungsanschrift.setStrasse(rStrasse);
                rechnungsanschrift.setHausnummer(rHausnummer);
                rechnungsanschrift.setPLZ(rPLZ);
                rechnungsanschrift.setOrt(rOrt);
                
                //Da vorher beide Anschriften gleich waren, muss jetzt eine neue
                //Lieferanschrift erstellt werden
                lieferanschrift = this.createAdress("Lieferadresse", Name, 
                        Vorname, Titel, lStrasse, lHausnummer, lPLZ, lOrt,
                        Staat, Telefon, Fax, Email, Geburtsdatum);
                
            }
        
        //Wenn die Anschriften unterschiedlich sind...
        } else {
            
                //...wird grundsätzlich die Rechnungsanschrift überschrieben.
                //Falls keine Änderungen gemacht worden sind, werden die gleichen
                //Werte in die Datenbank geschrieben
                rechnungsanschrift.setStrasse(rStrasse);
                rechnungsanschrift.setHausnummer(rHausnummer);
                rechnungsanschrift.setPLZ(rPLZ);
                rechnungsanschrift.setOrt(rOrt);
                
                //Prüfe, ob der Kunde seine Lieferanschrift = Rechnungsanschrift
                //setzen will
                if (modus) {
                    
                    //Wenn ja, tue genau das
                    lieferanschrift = rechnungsanschrift;
                    
                } else {
                
                    //Ansonsten, ändere ebenfalls die Lieferanschrift
                    lieferanschrift.setStrasse(lStrasse);
                    lieferanschrift.setHausnummer(lHausnummer);
                    lieferanschrift.setPLZ(lPLZ);
                    lieferanschrift.setOrt(lOrt);    
                }
        }
        
        //Die bearbeiten Anschrift werden dem Geschäftspartner zugewiesen
        gp.setLieferadresse(lieferanschrift);
        gp.setRechnungsadresse(rechnungsanschrift);
        
        //Transaktions starten
        em.getTransaction().begin();
        //Geschäftspartner persistieren
        //Die Anschriften werden über Kaskaden automatisch persistiert
        em.merge(gp);
        //Transaktion beenden
        em.getTransaction().commit();
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 16.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Ändert die eine Zahlungskondition
     * @param ZahlungskonditionsID ID der Zahlungskondition in der Datenbank
     * @param Auftragsart Auftragsart für diese Zahlungskondition
     * @param LieferzeitSofort Festgelegte Lieferzeit in Tagen
     * @param SperrzeitWunsch Festgelegt Wunschzeit in Tagen
     * @param Skontozeit1 Erste Skontozeit in Tagen
     * @param Skontozeit2 Zweite Skontozeit in Tagen
     * @param Skonto1 Erster Skontosatz
     * @param Skonto2 Zweiter Skontosatz
     * @param Mahnzeit1 Erste Manhzeit in Tagen
     * @param Mahnzeit2 Zweite Mahnzeit in Tagen
     * @param Mahnzeit3 Dritte Mahnzeit in Tagen
     * @throws ApplicationException wenn die Zahlungskondition nicht gefunden werden kann
     */
    public void aendereZahlungskondition(long ZahlungskonditionsID, 
            String Auftragsart, int LieferzeitSofort, int SperrzeitWunsch, 
            int Skontozeit1, int Skontozeit2, double Skonto1, double Skonto2, 
            int Mahnzeit1, int Mahnzeit2, int Mahnzeit3) 
            throws ApplicationException {
        
        //Zahlungskondition anhand der ID aus der Datenbank holen
        Zahlungskondition zk = em.find(Zahlungskondition.class, 
                ZahlungskonditionsID);
        
        //Wenn die Zahlungskondition nicht gefunden werden kann
        if (zk == null) {
            throw new ApplicationException("Fehler", 
                    "Die Zahlungskondition konnte nicht aktualisiert werden.");
        }
        
        //Ändern der Attribute
        zk.setAuftragsart(Auftragsart);
        zk.setLieferzeitSofort(LieferzeitSofort);
        zk.setSperrzeitWunsch(SperrzeitWunsch);
        zk.setSkontozeit1(Skontozeit1);
        zk.setSkontozeit2(Skontozeit2);
        zk.setSkonto1(Skonto1);
        zk.setSkonto2(Skonto2);
        zk.setMahnzeit1(Mahnzeit1);
        zk.setMahnzeit2(Mahnzeit2);
        zk.setMahnzeit3(Mahnzeit3);
        
        //Transaktions starten
        em.getTransaction().begin();
        //Zahlungskondition persistieren
        em.persist(zk);
        //Transaktion beenden
        em.getTransaction().commit();
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Methode zum Ändern eines Benutzers
     * @param Benutzername Name des Benutzers (eindeutig)
     * @param Passwort Passwort des Benutzers, wird als MD5-Hash in der Datenbank abgelegt
     * @param istAdmin Gibt an ob ein Benutzer ein Admin ist oder nicht
     * @throws ApplicationException wenn der Benutzer nicht gefunden werden konnte
     */
    public void aendereBenutzer(String Benutzername, String Passwort, 
            boolean istAdmin) throws ApplicationException {
        
        Benutzer benutzer = em.find(Benutzer.class, Benutzername);
        
        if (benutzer == null) {
            throw new ApplicationException("Fehler", 
                    "Der Benutzer konnte nicht gefunden werden.");
        }
        
        benutzer.setPasswort(this.getHash(Passwort));
        benutzer.setIstAdmin(istAdmin);
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="get-Methoden">
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 06.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Gibt alle Suchattribute die zu einer Tabelle assoziiert sind.
     * @param tabelle tabelle
     * @return suchattribute
     * @throws DAO.ApplicationException bei fehler in pu.
     */
    public Collection<String> gibSuchAttribute(String tabelle) 
        throws ApplicationException {
        String sqlAbfrage = "SELECT ST FROM Parameterdaten ST "
                + "WHERE ST.tabelle = '" + tabelle + "'";
        Collection<Parameterdaten> parameterdaten = null;
        Collection<String> suchAttribute = new ArrayList<>();
        
        try {
            //Persistenteklasse laden
            parameterdaten = this.em.createQuery(sqlAbfrage, 
                    Parameterdaten.class).getResultList();
        } catch (PersistenceException e) {
            throw new ApplicationException(FEHLER_TITEL, "");
        }
        //Prüfe, ob ein Datensatz gefunden wurde.
        if (parameterdaten == null) {
            throw new ApplicationException(FEHLER_TITEL, "");
        }
        
        //Iteriere über alle Datensätze und füge der Hashmap jeweils 
        //Als Key das Suchattribut und als Value das dazugehörige DbAttribut.
        for (Parameterdaten prmtr : parameterdaten) {
            suchAttribute.add(prmtr.getSuchkuerzel());
        }
        
        return suchAttribute;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 06.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Gibt alle Suchkuerzel und deren DBAttribut zurück.
     * @return hashmap<Suchkuerzel, DBAttribut>.
     * @throws DAO.ApplicationException Bei Fehler in pu.
     */
    public HashMap<String, String> gibAlleSuchAttribute() 
        throws ApplicationException {
        String sqlAbfrage = "SELECT ST FROM Parameterdaten ST";
        Collection<Parameterdaten> parameterdaten = null;
        HashMap<String, String> suchAttribute = new HashMap<>();
        
        try {
            //Persistenteklasse laden
            parameterdaten = this.em.createQuery(sqlAbfrage, 
                    Parameterdaten.class).getResultList();
        } catch (PersistenceException e) {
            throw new ApplicationException(FEHLER_TITEL, "");
        }
        //Prüfe, ob ein Datensatz gefunden wurde.
        if (parameterdaten == null) {
            throw new ApplicationException(FEHLER_TITEL, "");
        }
        
        //Iteriere über alle Datensätze und füge der Hashmap jeweils 
        //Als Key das Suchattribut und als Value das dazugehörige DbAttribut.
        for (Parameterdaten prmtr : parameterdaten) {
            suchAttribute.put(prmtr.getSuchkuerzel(), prmtr.getDbAttribut());
        }
        
        return suchAttribute;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 06.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Gibt den Datentyp eines Attributs zurück.
     * @param attribut Das Attribut.
     * @param tabelle Die Tabelle
     * @return Datentyp.
     * @throws DAO.ApplicationException Fehler bei pu.
     */
    public String gibDatentypVonSuchAttribut(String attribut, String tabelle) 
        throws ApplicationException {
        Parameterdaten prmtr = null;
        //SQL-Statement um den Datentyp zu einem DBAttribut zu bekommen.
        String sqlAbfrage = "SELECT ST FROM Parameterdaten "
                    + "ST WHERE ST.dbAttribut = '" + attribut 
                + "' AND ST.tabelle = '" + tabelle + "'";
        
        try {
            //Persistenteklasse laden
            prmtr = this.em.createQuery(sqlAbfrage, 
                    Parameterdaten.class).getSingleResult();
        } catch (PersistenceException e) {
            throw new ApplicationException(FEHLER_TITEL, "Das Suchkürzel ist "
                    + "nicht auf diese Tabelle (" + tabelle + ") anwendbar.");
        }
        //Prüfe, ob ein Datensatz gefunden wurde.
        if (prmtr == null) {
            throw new ApplicationException(FEHLER_TITEL, "");
        }
        //Gib den Datentyp des DBAttributs zurück.
        return prmtr.getDatentyp();
    }
    
    
    
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
        if (conditions == null || conditions.isLKZ()) {
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

        List<Artikelkategorie> ergebnis = this.em.createQuery("SELECT ST FROM Artikelkategorie ST", 
                Artikelkategorie.class).getResultList();
        
        ArrayList<Artikelkategorie> liste = new ArrayList<>();
        
        for (Artikelkategorie k : ergebnis) {
            if (!k.isLKZ()) {
                liste.add(k);
            }
        }
        return liste;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     *  TO-DO :  Query "SELECT ST FROM Artikelkategorie ST WHERE ST.Kategoriename = 'a'" selected no result, but expected unique result.
     * @param name
     * @return
     * @throws ApplicationException
     */
    public Artikelkategorie getCategory(String name)
        throws ApplicationException {
        String sqlQuery = null;
        Artikelkategorie cat = null;
        if (name == null)
            throw new ApplicationException(FEHLER_TITEL,
                    "Geben Sie eine Kategorie an!");
        sqlQuery =
                "SELECT ST FROM Artikelkategorie ST WHERE ST.Kategoriename = '" +
                name + "'";
        try {
            cat = (Artikelkategorie) em.createQuery(sqlQuery).getSingleResult();
        } catch (Exception e) {
            throw new ApplicationException(FEHLER_TITEL, e.getMessage());
        }
        
        
        if (cat == null || cat.isLKZ())
            throw new ApplicationException(FEHLER_TITEL,
                    "Es wurde keine Kategorie gefunden!");
        
        return cat;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 18.12.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
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
        if (item == null || item.isLKZ()) {
            throw new ApplicationException("Fehler",
                    "Es wurde kein Artikel gefunden!");
        }
        
        return item;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 18.12.14   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Gibt einen Kunden anhand der ID zurück
     * @param Kundennummer ID des Kunden
     * @return die persistente Abbildung des Kunden
     * @throws ApplicationException wenn der Kunde nicht gefunden werden kann
     */
    public Kunde getCustomer(long Kundennummer) throws ApplicationException {
        
        Kunde kunde = em.find(Kunde.class, Kundennummer);
        
        if (kunde == null || kunde.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Der Kunde konnte nicht gefunden werden");
        }
        
        return kunde;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 18.12.14   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Gibt einen Lieferanten anhand der ID zurück
     * @param Lieferantennummer ID des Lieferanten
     * @return die persistente Abbildung eines Lieferanten
     * @throws ApplicationException wenn der Lieferant nicht gefunden werden kann
     */
    public Lieferant gibLieferant(long Lieferantennummer)
            throws ApplicationException {
        
        Lieferant lieferant = em.find(Lieferant.class, Lieferantennummer);
        
        if (lieferant == null || lieferant.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Der Lieferant konnte nicht gefunden werden");
        }
        
        return lieferant;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 06.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Gibt einen Geschäftspartner anhand der ID zurück
     * @param GeschaeftspartnerID ID des Geschäftspartners
     * @return eine persistente Abbildung des gefundenen Geschäftspartner
     * @throws ApplicationException wenn der Geschäftspartner nicht gefunden werden kann
     */
    public Geschaeftspartner gibGeschaeftspartner(long GeschaeftspartnerID) 
            throws ApplicationException {
        
        Geschaeftspartner gp = em.find(Geschaeftspartner.class, GeschaeftspartnerID);
        
        if (gp == null || gp.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Der Geschäftspartner konnte nicht gefunden werden");
        }
        
        return gp;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 18.12.14   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Sucht den Status anhand des Namens in der Datenbank
     * @param name Name des Status
     * @return die persistente Abbildung des Status
     * @throws ApplicationException 
     */
    public Status getStatusByName(String name) throws ApplicationException {
        
        Query query = null;
        Status status = null;
        if (name == null)
            throw new ApplicationException("Fehler",
                    "Geben Sie eine Kategorie an!");
        query = em.createQuery("SELECT ST FROM Status ST "
                + "WHERE ST.Status LIKE :name").setParameter("name", name.toLowerCase());
        try {
            status = (Status) query.getSingleResult();
        } catch (Exception e) {
            throw new ApplicationException("Fehler", e.getMessage());
        }
        
        if (status == null || status.isLKZ())
            throw new ApplicationException("Fehler",
                    "Es wurde kein Status gefunden!");
        
        return status; 
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 18.12.14   loe     angelegt                              */
    /* 22.12.14   loe     überarbeitet                          */
    /*----------------------------------------------------------*/
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
        if (auftragskopf == null || auftragskopf.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Der Auftrag konnte nicht gefunden werden");
        }
        
        return auftragskopf;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 06.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
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
        if(ak == null || ak.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Der Auftrag konnte nicht gefunden werden");
        }
        
        //Gib die Anzahl der Positionen zurück
        return ak.getPositionsliste().size();      
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 13.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Ermittelt die nächste Artikelnummer und gibt diese zurück
     * @return die nächste Artikelnummer
     */
    public int gibNaechsteArtikelnummer() {
        
        //Ermittelt die Anzahl der angelegten Artikel und erhöht diesen Wert um 1
        return this.em.createQuery("SELECT ST FROM Artikel ST",
                Artikel.class).getResultList().size() + 1;
        
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 13.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Ermittelt die nächste Geschäftspartner ID und gibt diese zurück
     * @return die nächste Geschäftspartner ID
     */
    public int gibNaechsteGeschaeftpartnerID() {
        
        //Ermittelt die Anzahl der angelegten Geschäftspartner und erhöht diesen Wert um 1
        return this.em.createQuery("SELECT ST FROM Geschaeftspartner ST",
                Geschaeftspartner.class).getResultList().size() + 1;
        
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 13.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Ermittel die nächste Zahlungskonditions ID und gibt diese zurück
     * @return die nächste Zahlungskonditions ID
     */
    public int gibNaechsteZahlungskonditionID() {
        
        //Ermittelt die Anzahl der angelegten Zahlungskonditionen und erhöht diesen Wert um 1
        return this.em.createQuery("SELECT ST FROM Zahlungskondition ST",
                Zahlungskondition.class).getResultList().size() + 1;
        
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 06.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Erstellt eine Liste aller Zahlungskonditionen die in der Datenbank vorhanden 
     * sind zurück.
     * @return die oben genannte Liste
     */
    public Collection<Zahlungskondition> gibAlleZahlungskonditionen() {
        
        List<Zahlungskondition> ergebnis = this.em.createQuery("SELECT ST FROM Zahlungskondition ST",
                Zahlungskondition.class).getResultList();
        
        ArrayList<Zahlungskondition> liste = new ArrayList<>();
        
        for (Zahlungskondition zk : ergebnis) {
            if(!zk.isLKZ()) {
                liste.add(zk);
            }
        }
        return liste;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 06.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Gibt eine Auftragsposition eines Auftrags zurück
     * @param AuftragskopfID ID des Auftragskopfes
     * @param Positionsnummer Nummer einer Position im Auftrag
     * @return eine persistene Abbilung der gefundenen Auftragsposition
     * @throws ApplicationException wenn der Auftrag oder die Position nicht gefunden werden kann
     */
    public Auftragsposition gibAuftragsposition(long AuftragskopfID, 
            long Positionsnummer) throws ApplicationException {
        
        Auftragsposition ap = (Auftragsposition) this.em.createQuery
                 ("SELECT ST "
                + "FROM Auftragsposition ST "
                + "WHERE ST.Auftrag = " + AuftragskopfID + " AND "
                      + "ST.Positionsnummer = " + Positionsnummer).getSingleResult();
        
        if (ap == null || ap.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Die Auftragsposition konnte nicht gefunden werden");
        }
        
        return ap;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 16.01.15   loe     angelegt                              */
    /* 17.01.15   loe     überarbeitet                          */
    /* 18.01.15   loe     überarbeitet                          */
    /*----------------------------------------------------------*/
    /**
     * Methode um eine Auftragsposition anhand des Auftrags und des Artikels zu 
     * holen.
     * Diese Methode wird nur von der Methode "aenderePositionen()" aufgerufen!
     * @param AuftragskopfID ID des Auftrags
     * @param Artikelnummer ID des Artikels
     * @return eine Auftragsposition
     * @throws ApplicationException Wenn weder Auftrag noch Artikel in der DB 
     *                              gefunden werden können
     */
    private Auftragsposition gibAuftragspositionNachArtikel(long AuftragskopfID, 
            long Artikelnummer) throws ApplicationException {
        
        //Artikel sowie Auftrag aus der Datenbank holen
        Artikel a = em.find(Artikel.class, Artikelnummer);   
        Auftragskopf ak = em.find(Auftragskopf.class, AuftragskopfID);
        
        //Prüfen ob beide vorhanden sind
        if (a == null) {
            throw new ApplicationException("Fehler", 
                    "Der Artikel konnte nicht gefunden werden.");
        }
        
        if (ak == null) {
            throw new ApplicationException("Fehler", 
                    "Der Auftrag konnte nicht gefunden werden.");
        }
        
        Auftragsposition ap = null;
        
        //Query für das Selektieren einer Auftragsposition anhand der AuftragsID
        //sowie des Artikels
        Query query = this.em.createQuery
                 ("SELECT ST "
                + "FROM Auftragsposition ST "
                + "WHERE ST.Auftrag = :auftrag AND ST.Artikel = :artikel");
        query.setParameter("auftrag", ak);
        query.setParameter("artikel", a);
        
        //Ergebnisliste
        //Es sollte zwar nur ein Eintrag gefunden werden "getSingleResult()"
        //wirft allerdings sofort eine Exception falls kein Eintrag gefunden wird
        List<Auftragsposition> ergebnis = query.getResultList();
        
        //Wenn die Ergebnisliste nicht leer ist...
        if (!ergebnis.isEmpty()) {
            //...holen den ersten Eintrag (einziges Ergebnis)
            ap = ergebnis.get(0);
        }
        
        return ap;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Sucht einen Benutzer in der Datenbank
     * @param Benutzername Name des Benutzers (eindeutig)
     * @return eine persistente Abbildung des gefundenen Benutzers
     * @throws ApplicationException wenn der Benutzer nicht gefunden werden konnte
     */
    public Benutzer gibBenutzer(String Benutzername) 
            throws ApplicationException {
        
        Benutzer benutzer = em.find(Benutzer.class, Benutzername);
        
        if (benutzer == null) {
            throw new ApplicationException("Fehler", 
                    "Der Benutzer konnte nicht gefunden werden.");
        }
        
        return benutzer;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 13.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Gibt die Namen der Spalten für eine Entity (Tabelle) als HashSet zurück
     * @param entity Entityklasse zu der die Metadaten geholt werden sollen (Übergabe: entity.class)
     * @return HashSet das die Namen der Spalten enthält
     */
    public HashSet<String> gibMetadaten(Class entity) {
        
        //Über die Metadaten werden die Attribute (Spaltennamen) der angegebenen Entity ermittelt
        Set<Attribute> attribute = em.getMetamodel().entity(entity).getAttributes();
        
        //HashSet das nur die Namen der Attribute enthält
        HashSet<String> namen = new HashSet<>();
        
        //Für jedes Attribute im Set wird der Name gelesen und in das HashSet geschrieben
        for(Attribute a : attribute) {
            namen.add(a.getName());
        }
        
        return namen;
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Gibt eine Liste mit Zahlungskonditionen für eine Auftragsart zurück
     * @param Auftragsart Der Name der Auftragsart
     * @return eine Liste mit Zahlungskonditionen zu dieser Auftragsart
     * @throws ApplicationException wenn es zu der Auftragsart keine 
     *         Zahlungskonditionen gibt
     */
    public List<Zahlungskondition> gibZahlungskonditionenFürAuftragsart(String Auftragsart) 
            throws ApplicationException {
        
        List<Zahlungskondition> ergebnis = em.createQuery("SELECT ST FROM "
                        + "Zahlungskondition ST WHERE ST.Auftragsart LIKE '" 
                        + Auftragsart + "'").getResultList();
        
        if (ergebnis.isEmpty()) {
            throw new ApplicationException("Fehler", 
                    "Für diese Auftragsart gibt es keine Zahlungskonditionen");
        }
        
        return ergebnis;
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="remove-Methoden">

    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 06.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Methode zum Setzen eines Löschkennzeichens für Artikel
     * @param Artikelnummer ID des Artikels
     * @throws ApplicationException wenn der Artikel nicht gefunden werden kann oder bereits gelöscht ist
     */
    public void loescheArtikel(long Artikelnummer) throws ApplicationException {
        
        //Holen des Artikels aus der Datenbank anhand der ID
        Artikel artikel = em.find(Artikel.class, Artikelnummer);
        
        //Wenn der Artikel nicht gefunden werden kann wird eine entsprechende Exception geworfen
        if (artikel == null) {
            throw new ApplicationException("Fehler", 
                    "Der Artikel konnte nicht gefunden werden.");
        }
        
        //Wenn der Artikel bereits "gelöscht" ist wird eine entsprechende Exception geworfen
        if (artikel.isLKZ()) {
            throw new ApplicationException("Fehler",
                    "Diese Artikel ist bereits mit einem Löschkennzeichen versehen");
        }
        
        Query query = em.createQuery("select ap from Auftragskopf ak, "
                + "in(ak.Positionsliste) ap where ap.Artikel.Id = :artikelnummer"
                + " and (ak.Status.Status LIKE 'erfasst' or "
                + "ak.Status.Status LIKE 'freigegeben') "
                + "and (ak.LKZ = 0 or ap.LKZ = 0)")
                .setParameter("artikelnummer", Artikelnummer);
        
        List<Auftragsposition> ergebnis = (List<Auftragsposition>) query.getResultList();
        
        if (!ergebnis.isEmpty()) {
            throw new ApplicationException("Fehler", 
                    "Der Artikel wird noch Aufträgen verwendet und kann"
                            + " daher nicht gelöscht werden.");
        }
        
        //Setzen des Löschkennzeichens für den Artikel
        artikel.setLKZ(true);
        
        //Transaktion starten
        em.getTransaction().begin();
        //Artikel persistieren
        em.persist(artikel);
        //Transaktion beenden
        em.getTransaction().commit();
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 06.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Methode zum Setzen eines Löschkennzeichens für Geschäftspartner und dazugehörige Anschriften
     * @param GeschaeftspartnerID ID des Geschäftspartners
     * @throws ApplicationException wenn der Geschäftspartner nicht gefunden werden kann oder bereits gelöscht ist
     */
    public void loescheGeschaeftspartner(long GeschaeftspartnerID) 
            throws ApplicationException {
        
        //Holen des Geschäftspartners aus der Datenbank anhand der ID
        Geschaeftspartner gp = em.find(Geschaeftspartner.class,
                GeschaeftspartnerID);
        
        //Variablen für die Anschriften initialisieren
        Anschrift liefer = null;
        Anschrift rechnung = null;
        
        //Wenn der Geschäftspartner nicht gefunden werden kann wird eine entsprechende Exception geworfen
        if (gp == null) {
            throw new ApplicationException("Fehler", 
                    "Der Geschäftspartner konnte nicht gefunden werden.");
        }
        
        //Wenn der Geschäftspartner bereits "gelöscht" ist wird eine entsprechende Exception geworfen
        if (gp.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Der Geschäftspartner ist bereits mit einem Löschkennzeichen versehen.");
        }
        
        Query query = em.createQuery("select ak from Auftragskopf ak "
                + "where ak.Geschaeftspartner.GeschaeftspartnerID = :gpid "
                + "and ak.LKZ = false").setParameter("gpid", GeschaeftspartnerID);
        
        List<Auftragskopf> ergebnis = (List<Auftragskopf>) query.getResultList();
        
        if (!ergebnis.isEmpty()) {
            throw new ApplicationException("Fehler", 
                    "Der Geschäftspartner wird noch in Aufträgen verwendet "
                            + "und kann daher nicht gelöscht werden.");
        }
        
        //Anschriften des Geschäftspartners holen
        liefer = gp.getLieferadresse();
        rechnung = gp.getRechnungsadresse();
        
        //Löschkennzeichen für diese Anschriften und den Geschäftspartner setzen
        liefer.setLKZ(true);
        rechnung.setLKZ(true);
        gp.setLKZ(true);
        
        //Transaktion starten
        em.getTransaction().begin();
        //Anschriften und Geschäftspartner persistieren
        em.persist(em.merge(liefer));
        em.persist(em.merge(rechnung));
        em.persist(gp);
        //Transaktion beenden
        em.getTransaction().commit();
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 06.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Methode zum Setzen eines Löschkennzeichens für Aufträge und dazugehörige Positionen.
     * @param AuftragskopfID ID des Auftragkopfes
     * @throws ApplicationException wenn der Auftrag nicht gefunden werden kann oder bereits gelöscht ist
     */
    public void loescheAuftrag(long AuftragskopfID) 
            throws ApplicationException {
        
        //Holen des Auftrags aus der Datenbank anhand der ID
        Auftragskopf ak = em.find(Auftragskopf.class, AuftragskopfID);
        
        //Wenn der Auftrag nicht gefunden werden kann wird eine entsprechende Exception geworfen
        if (ak == null) {
            throw new ApplicationException("Fehler", 
                    "Der Auftrag konnte nicht gefunden werden.");
        }
        
        //Wenn der Auftrag bereits "gelöscht" ist wird eine entsprechende Exception geworfen
        if (ak.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Der Auftrag ist bereits mit einem Löschlennzeichen versehen.");
        }
        
        if (ak.getStatus().getStatus().equals("freigegeben")) {
            if (ak instanceof Bestellauftragskopf) {
                this.setzeArtikelBestand(ak.getPositionsliste(), "RueckgaengigBestellung");
            } else {
                this.setzeArtikelBestand(ak.getPositionsliste(), "RueckgaengigVerkauf");
            }
        }
        
        //Transaktion starten
        em.getTransaction().begin();
        
        //Jede Position des Auftrags wird mit einem LKZ versehen und persistiert
        for (Auftragsposition ap : ak.getPositionsliste()) {
            ap.setLKZ(true);
            em.persist(ap);
        }
        
        //Setzen des LKZs für den Auftrag
        ak.setLKZ(true);
        
        //Auftrag persistieren
        em.persist(ak);
        
        //Transaktion beenden
        em.getTransaction().commit();
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 06.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Methode zum Setzen eines Löschkennzeichens für eine Zahlungskondition
     * @param ZahlungskonditionsID ID der Zahlungskondition
     * @throws ApplicationException wenn die Zahlungskondition nicht gefunden werden kann oder bereis gelöscht ist
     */
    public void loescheZahlungskondition(long ZahlungskonditionsID) 
            throws ApplicationException {
        
        //Holen der Zahlungskondition aus der Datenbank anhand der ID
        Zahlungskondition zk = em.find(Zahlungskondition.class, ZahlungskonditionsID);
        
        //Wenn die Zahlungskondition nicht existiert wird eine entsprechende Exception geworfen
        if (zk == null) {
            throw new ApplicationException("Fehler", 
                    "Die Zahlungskondition konnte nicht gefunden werden.");
        }
        
        //Wenn die Zahlungskondition bereits "gelöscht" ist wird ebenfalls eine Exception
        //geworfen
        if (zk.isLKZ()) {
            throw new ApplicationException("Fehler", 
                    "Diese Zahlungskondition ist bereits mit einem Löschkenneichen versehen.");
        }
        
        Query query = em.createQuery("select ak from Auftragskopf ak "
                + "where ak.Zahlungskondition.ZahlungskonditionID = :zk "
                + "and ak.LKZ = false").setParameter("zk", ZahlungskonditionsID);
        
        List<Auftragskopf> ergebnis = (List<Auftragskopf>) query.getResultList();
        
        if (!ergebnis.isEmpty()) {
            throw new ApplicationException("Fehler", 
                    "Die Zahlungskondition wird noch in Aufträgen verwendet "
                            + "und kann daher nicht gelöscht werden.");
        }
        
        //Löschkennzeichen setzen
        zk.setLKZ(true);
        
        //Transaktions starten
        em.getTransaction().begin();
        //Zahlungskondition persistieren
        em.persist(zk);
        //Transaktion beenden
        em.getTransaction().commit();
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 13.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Methode zum Löschen eines Benutzer (richtig löschen, kein LKZ!)
     * @param Benutzername Name des Benutzers (eindeutig)
     * @throws ApplicationException wenn der Benutzer nicht gefunden werden kann
     */
    public void loescheBenutzer(String Benutzername) 
            throws ApplicationException {
        
        //Sucht den Benutzer anhand des Benutzernamens in der Datenbank
        Benutzer benutzer = em.find(Benutzer.class, Benutzername);
        
        //Wenn der Benutzer nicht gefunden werden kann
        if (benutzer == null) {
            throw new ApplicationException("Fehler", 
                    "Der Benutzer konnte nicht gefunden werden.");
        }
        
        //Transaktion starten
        em.getTransaction().begin();
        //Benutzer löschen
        em.remove(benutzer);
        //Transaktion beenden
        em.getTransaction().commit();
    }
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
    /**
     * Setzt das Löschkennzeichen für eine Auftragsposition.
     * Diese Methode muss innerhalb einer Transaktion aufgerufen werden, weil
     * sie selber keine Transaktion startet!
     * @param AuftragsID ID des Auftrags
     * @param Positionnummer Nummer der Position im Auftrag
     * @throws ApplicationException wenn der Auftrag oder die Position nicht
     *                              gefunden werden kann
     */
    public void loeschePosition(long AuftragsID, long Positionnummer) 
            throws ApplicationException {
        
        //Suche den Auftragskopf anhand der ID in der Datenbank
        Auftragskopf ak = em.find(Auftragskopf.class, AuftragsID);
        
        Auftragsposition ap = null;
        
        //SQL-Query für das Selektieren einer Auftragsposition anhand
        //des Auftrags und der Positionnummer
        Query query = em.createQuery("SELECT ST "
                        + "FROM Auftragsposition ST "
                        + "WHERE ST.Auftrag = :auftrag "
                        + "AND ST.Positionsnummer = :position");        
        query.setParameter("auftrag", ak);
        query.setParameter("position", Positionnummer);
        
        //Hole das erste Ergebnis
        ap = (Auftragsposition) query.getSingleResult();
        
        //Wenn die Posistion nicht gefunden werden kann
        if (ap == null) {
            throw new ApplicationException("Fehler", 
                    "Die Auftragsposition konnte nicht gefunden werden.");
        }
        
        //Setze das Löschkennzeichen
        ap.setLKZ(true);
        
        //Auftragsposition persisieren
        em.persist(ap);
    }

    
//</editor-fold>
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 10.12.14   loe     angelegt                              */
    /*----------------------------------------------------------*/
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
    
    /*----------------------------------------------------------*/
    /* Datum      Name    Was                                   */
    /* 12.01.15   loe     angelegt                              */
    /*----------------------------------------------------------*/
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
