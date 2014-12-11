/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
//import
import DTO.ATyp;
import DTO.Anschrift;
import DTO.Artikel;
import DTO.Artikelkategorie;
import DTO.Auftragsart;
import DTO.Benutzer;
import DTO.Status;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
        HashSet<?> sqlResultSet = null;
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
        Artikel a = null;
        try {
            //sqlResultSet = (HashSet<?>) em.createQuery(sqlQuery).getResultList();
            a = (Artikel) em.createQuery(sqlQuery).getSingleResult();
        } catch(Exception e) {
            throw new ApplicationException("", e.getMessage());
        }
        
        
        
        if (table.equals("Artikel")) {
            
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
    public void createAdress(ATyp AnschriftTyp, String Name, String Vorname,
            String Titel, String Strasse, String Hausnummer, String PLZ,
            String Ort, String Staat, String Telefon, String Fax,
            String Email, Date Geburtsdatum) throws ApplicationException {
        
        //In der Anschrift wird zusätzlich ein Erfassungsdatum gehalten.
        //Das Erfassungsdatum ist gleich dem Systemdatum.
        Calendar cal = Calendar.getInstance();
        Date Erfassungsdatum = cal.getTime();
        
        //Erzeugung des persistenten Anschrift-Objektes
        Anschrift anschrift = new Anschrift(AnschriftTyp, Name, Vorname, Titel,
                Strasse, Hausnummer, PLZ, Ort, Staat, Telefon, Fax, Email,
                Geburtsdatum, Erfassungsdatum);
        
        //Wenn bei der Erzeugung ein Fehler auftritt
        if(anschrift == null) {
            throw new ApplicationException("Fehler",
                    "Beim Anlegen der Anschrift ist ein Fehler aufgetreten");
        }
        
        //Transaktion starten
        em.getTransaction().begin();
        //Objekt persistieren
        em.persist(em);
        //Transaktion schließen
        em.getTransaction().commit();
    }
    
//</editor-fold>
  
//<editor-fold defaultstate="collapsed" desc="update-Methoden">
//Subject to change
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="get-Methoden">
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
    
    /**
     * Methode zum Holen eines Anschriftstyps
     * @param Beschreibung Name des Anschriftstyps
     * @return Persistentes Anschriftstyp-Objekt
     * @throws ApplicationException wenn der Anschriftstyp nicht gefunden werden
     *         kann
     */
    public ATyp getAdresstypeByName(String Beschreibung) 
            throws ApplicationException {
        
        ATyp typ = null;
        try {
            //Query zum Suchen eines Anschriftstyps anhand der Beschreibung
            Query query = em.createQuery("SELECT t FROM ATyp t WHERE "
                    + "T.Beschreibung LIKE :beschreibung")
                    .setParameter("beschreibung", Beschreibung);
            
            //Nur der erste Treffer soll geholt werden
            typ = (ATyp) query.getSingleResult();
            
            //Wenn kein Objekt gefunden wurde
            if(typ == null) {
                throw new ApplicationException("Fehler",
                        "Anschriftstyp wurde nicht gefunden");
            }
            
            return typ;
        //Wenn bei der Ausführung der Query was schief geht  
        } catch (Exception e) {
            throw new ApplicationException("Fehler", e.getMessage());
        }
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
