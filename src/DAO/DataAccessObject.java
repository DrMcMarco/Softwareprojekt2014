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
import DTO.Auftragsart;
import DTO.Auftragskopf;
import DTO.Auftragsposition;
import DTO.Benutzer;
import DTO.Geschaeftspartner;
import DTO.Kunde;
import DTO.Lieferanschrift;
import DTO.Lieferant;
import DTO.Rechnungsanschrift;
import DTO.Status;
import DTO.Zahlungskondition;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;
/**
 *
 * @author Simon <Simon.Simon at your.org>
 */
public class DataAccessObject {
    /**
     * EntityManager verwaltet alle Persistenten Klassen
     */
    private EntityManager em;
    
    public DataAccessObject() {
        this.em = Persistence.createEntityManagerFactory(
            "Softwareprojekt2014PU").createEntityManager();
    }
    
    /**
     * Methode zur dynamischen Suche
     * @param input Sucheingabe 
     * @param table Tabelle in der gesucht werden soll
     * @return gibt eine Collection<?> zurück mit allen gefunden Datensätzen
     * @throws DAO.ApplicationException Die Exception wird durchgereicht
     */
    public Collection<?> searchQuery(String input, String table) 
            throws ApplicationException {
        //Datendeklaration
        HashMap<String, String> dbIdentifier = null;
        List<?> sqlResultSet = null;
        String key, value = null;
        Iterator<Entry<String, String>> iterator = null;
        Entry entry = null;
        String sqlQuery = null;
        Parser parser = new Parser();
        //Parse den Suchausdruck und hole die DB-Attr. Namen
        dbIdentifier = parser.parse(input, table);
        //Prüfe, ob das Parsen erfolgreich war.
        if (dbIdentifier == null) {
            throw new ApplicationException("Fehler", 
                    "Beim Parsen ist ein Fehler aufgetreten!");
        }
        //Sql-Statement Dynamisch erzeugen
        sqlQuery = "SELECT ST FROM " + table + " ST WHERE ";
        iterator = dbIdentifier.entrySet().iterator();
        
        while (iterator.hasNext()) {
            //Nach dem ersten durchlauf muss ein And angeknüpft werden
            if (entry != null)
                sqlQuery += " AND ";
            //Eintrag aus der Map holen und die Daten auslesen
            entry = iterator.next();
            key = (String) entry.getKey();
            value = (String) entry.getValue();
            //Sql-Statement erweitern
            sqlQuery += ("ST." + key + " = " + value);
        }

        try {
            //Ergebnis aus der DB laden und als Liste speichern
            sqlResultSet = em.createQuery(sqlQuery).getResultList();
            
        } catch(Exception e) {
            throw new ApplicationException("Fehler", 
                    "Die Daten konnten nicht geladen werden!");
        } 
        

        return sqlResultSet;
    }
//<editor-fold defaultstate="collapsed" desc="create-Methoden">
    
    /**
     * Methode zur Erzeugung eines Artikels.
     *
     * !Überlegung die Kategorie als String zu übergeben und von hier aus
     * das Objekt aus der DB suchen!
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
    public void createItem(Artikelkategorie Kategorie, String Artikeltext,
            String Bestelltext, double Verkaufswert, double Einkaufswert,
            double MwST, int Frei, int Reserviert, int Zulauf, int Verkauft)
            throws ApplicationException {
        Artikel item = new Artikel(Kategorie, Artikeltext, Bestelltext,
                Verkaufswert, Einkaufswert, MwST, Frei, Reserviert,
                Zulauf, Verkauft);
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
     *
     * @param Auftragstext
     * @param Auftragsart
     * @param Wert
     * @param Status
     * @param Abschlussdatum
     * @param Erfassungsdatum
     * @param Lieferdatum
     */
    public void createOrderHead(String Auftragstext, Auftragsart Auftragsart,
            double Wert, Status Status, Date Abschlussdatum,
            Date Erfassungsdatum, Date Lieferdatum) {
        
        ArrayList<Auftragsposition> position = new ArrayList<>();
        //Kunde erstellen
        //Auftragskopf order = new Auftragskopf
        
        
        
    }
    
    /**
     * Methode zur Erzeugung eines Status
     * @param Status
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
     * @param AnschriftTyp
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
     * @throws ApplicationException 
     */
    public Anschrift createAdress(String typ, String Name, String Vorname,
            String Titel, String Strasse, String Hausnummer, String PLZ,
            String Ort, String Staat, String Telefon, String Fax,
            String Email, Date Geburtsdatum) throws ApplicationException {
        
        //In der Anschrift wird zusätzlich ein Erfassungsdatum gehalten.
        //Das Erfassungsdatum ist gleich dem Systemdatum.
        Calendar cal = Calendar.getInstance();
        Date Erfassungsdatum = cal.getTime();
        
        Anschrift anschrift = null;
        
        if (typ.equals("Lieferadresse")) {
        
            //Erzeugung des persistenten Anschrift-Objektes
            anschrift = new Lieferanschrift(Name, Vorname, Titel,
                    Strasse, Hausnummer, PLZ, Ort, Staat, Telefon, Fax, Email,
                    Geburtsdatum, Erfassungsdatum);
        } else if (typ.equals("Rechnungsadresse")) {
            
            //Erzeugung des persistenten Anschrift-Objektes
            anschrift = new Rechnungsanschrift(Name, Vorname, Titel,
                    Strasse, Hausnummer, PLZ, Ort, Staat, Telefon, Fax, Email,
                    Geburtsdatum, Erfassungsdatum);
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
    public void createPaymentConditions(Auftragsart Auftragsart, 
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
    
    public void createBusinessPartner(String Typ, Anschrift Lieferadresse, 
            Anschrift Rechnungsadresse, double Kreditlimit, boolean LKZ) 
            throws ApplicationException {
        
        try {
        
            Geschaeftspartner geschaeftspartner = null;
        
            if (Typ.equals("Kunde")) {
                geschaeftspartner = new Kunde(Lieferadresse, Rechnungsadresse, 
                        Kreditlimit, LKZ);
            } else if (Typ.equals("Lieferant")) {
                geschaeftspartner = new Lieferant(Lieferadresse, Rechnungsadresse, 
                        Kreditlimit, LKZ);
            } else {
                throw new ApplicationException("Fehler", 
                        "Der angegebene Geschäftspartnertyp existiert nicht.");
            }
        
            if (geschaeftspartner == null) {
                throw new ApplicationException("Fehler", 
                        "Bei der Erzeugung des Kunde ist ein Fehler aufgetreten");
            }
        
            em.getTransaction().begin();
        
            if (Lieferadresse.equals(Rechnungsadresse)) {
                em.persist(Lieferadresse);
            } else {
                em.persist(Lieferadresse);
                em.persist(Rechnungsadresse);
            }
        
            em.persist(geschaeftspartner);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ApplicationException("Fehler", 
                    "Beim Speichern der Daten ist ein Fehler aufgetreten");
        }
    }
    
//</editor-fold>
  
//<editor-fold defaultstate="collapsed" desc="update-Methoden">
//Subject to change
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="get-Methoden">
    
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
    
    /**
     * 
     * @param type
     * @return
     * @throws ApplicationException 
     */
    public Zahlungskondition getPaymentConditionsByType(Auftragsart type) 
            throws ApplicationException {
        String sqlQuery = null;
        Zahlungskondition conditions = null;
        //Prüfe, ob eine Auftragsart übergeben wurde
        if (type == null) {
            throw new ApplicationException("Fehler", 
                    "Übergeben Sie eine gültige Auftragsart!");
        }
        //Sql-Statement erstellen
        sqlQuery =
                "SELECT ST FROM Zahlungskondition ST WHERE ST.Auftragsart = '" +
                type.getAuftragsart() + "'";
        
        try {
            //Konditionen aus der DB Laden
            conditions = (Zahlungskondition) 
                    em.createQuery(sqlQuery).getSingleResult();
        } catch (Exception e) {
            throw new ApplicationException("Fehler", e.getMessage());
        }
        
        //Prüfen, ob was gefunden wurde
        if (conditions == null)
            throw new ApplicationException("Fehler",
                    "Es wurde keine Kategorie gefunden!");
        
        return conditions;
    }
    
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
    public boolean doLogin(String username, String password) 
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
            loginSuccessful = true;
        }
        
        return loginSuccessful;
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
}
