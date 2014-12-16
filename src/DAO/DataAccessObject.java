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
    /**
     * EntityManager verwaltet alle Persistenten Klassen
     */
    private final EntityManager em;
    
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
        ArrayList<String> dbIdentifier = null;
        Auftragskopf headOrder = null;
        List<?> sqlResultSet = null;
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
        //Iteriere über alle Input Einträge
        for (int i = 0; i < dbIdentifier.size(); i++) {
            //Hole Abfrage aus der Liste
            sqlQuery += ("ST." + dbIdentifier.get(i));
            //Prüfe, ob mehrere Einträge vorhanden sind, diese müssen mit AND
            //Verknüpft werden.Beim letzten durchlauf wird kein AND mehr gesetzt
            if (dbIdentifier.size() > 1 && i < dbIdentifier.size() - 1) {
                sqlQuery += " AND ";
            }
        }

        try {
            //Ergebnis aus der DB laden und als Liste speichern
            sqlResultSet = em.createQuery(sqlQuery).getResultList();
            
        } catch(Exception e) {
            throw new ApplicationException("Fehler", 
                    "Die Daten konnten nicht gefunden werden!");
        } 
        

        return sqlResultSet;
    }
//<editor-fold defaultstate="collapsed" desc="create-Methoden">
    
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
        Artikelkategorie cat = em.createQuery(
               "Select ST FROM Artikelkategorie ST WHERE ST.Kategoriename = '" +
                        Kategorie + "' ", 
                Artikelkategorie.class).getSingleResult();
        
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
    
    public void createOrderHead(String Typ, HashMap<Long, Integer> Artikel, 
            String Auftragstext, long GeschaeftspartnerID,
            long ZahlungskonditionID, double Wert, String Status, 
            Date Abschlussdatum, Date Lieferdatum) 
            throws ApplicationException {
        
        Geschaeftspartner businessPartner = em.find(Geschaeftspartner.class, 
                GeschaeftspartnerID);
        
        if(businessPartner == null) {
            throw new ApplicationException("Fehler", 
                    "Der angegebene Kunde konnte nicht gefunden werden.");
        }
        
        Zahlungskondition paymentCondition = this.getPaymentConditionsById(ZahlungskonditionID);
        
        if(paymentCondition == null) {
            throw new ApplicationException("Fehler", 
                    "Zahlungskondition konnte nicht gefunden werden.");
        }
        
        Status state = this.getStatusByName(Status);
        
        Calendar cal = Calendar.getInstance();
        Date Erfassungsdatum = cal.getTime();
        
        Auftragskopf orderhead = null;
        
        if(Typ.equals("Barauftrag")) {
            orderhead = new Barauftragskopf(Auftragstext, Wert, businessPartner,
                    state, Abschlussdatum, Erfassungsdatum, Lieferdatum);
        } else if(Typ.equals("Sofortauftrag")) {
            orderhead = new Sofortauftragskopf(Auftragstext, Wert, 
                    businessPartner, state, paymentCondition, 
                    Abschlussdatum, Erfassungsdatum, Lieferdatum);
        } else if(Typ.equals("Terminauftrag")) {
            orderhead = new Terminauftragskopf(Auftragstext, Wert, 
                    businessPartner, state, paymentCondition, 
                    Abschlussdatum, Erfassungsdatum, Lieferdatum);
        } else if(Typ.equals("Bestellauftrag")) {
            orderhead = new Bestellauftragskopf(Auftragstext, Wert, 
                    businessPartner, state, paymentCondition, 
                    Abschlussdatum, Erfassungsdatum, Lieferdatum);
        } else {
            throw new ApplicationException("Fehler", "Ungültige Auftragsart!");
        }
        
        if (orderhead == null) {
            throw new ApplicationException("Fehler", 
                    "Fehler bei der Erzeugung des Auftragskopfes");
        }
        
        Set<Long> artikelIDS = Artikel.keySet();
        
        for (long ID : artikelIDS) {
            
            Artikel artikel = em.find(Artikel.class, ID);
            
            if(artikel == null) {
                throw new ApplicationException("Fehler", "Der angegebene Artikel konne nicht gefunden werden");
            }
            
            orderhead.addPosition(artikel, Artikel.get(ID));
            
        }
        try {
            em.getTransaction().begin();
            em.persist(orderhead);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
                    Geburtsdatum, Erfassungsdatum, false);
        } else if (typ.equals("Rechnungsadresse")) {
            
            //Erzeugung des persistenten Anschrift-Objektes
            anschrift = new Rechnungsanschrift(Name, Vorname, Titel,
                    Strasse, Hausnummer, PLZ, Ort, Staat, Telefon, Fax, Email,
                    Geburtsdatum, Erfassungsdatum, false);
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
    public void createPaymentConditions( double LieferzeitSofort, 
            double SperrzeitWunsch, double Skontozeit1,
            double Skontozeit2, double Skonto1, double Skonto2, 
            double Mahnzeit1, double Mahnzeit2, double Mahnzeit3) 
            throws ApplicationException {
        
        Zahlungskondition conditions = new Zahlungskondition(LieferzeitSofort, 
                SperrzeitWunsch, Skontozeit1, Skontozeit2, Skonto1, Skonto2, 
                Mahnzeit1, Mahnzeit2, Mahnzeit3);
        
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
     * @return
     * @throws ApplicationException 
     */
    public Collection<Artikelkategorie> getAllCategories() 
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
    
    public boolean checkItemStock(HashMap<Long, Integer> itemMap, Status state) 
            throws ApplicationException {
        
        
        return true;
    }
}
