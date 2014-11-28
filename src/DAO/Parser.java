/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;

/**
 *
 * @author Simon <Simon.Simon at your.org>
 * Mögliche Präfixe zur Suche
 * Input: NR = Kundennummer
 *        NAME = Artikelname
 *        DATUM = Auftragskopfeingangsdatum
 *        STATUS = Auftragskopfstatus
 *        TYPE = Auftragsart
 *        FREI = Artikelbestandfrei
 *        RES = Artikelbestandreserviert
 *        ZUL = Artikelbestandzulauf
 *        VER = Artikelbestandverkauft
 */
public class Parser {
    /**
     * Konstante zur Trennung der einzelnen Sucheingaben
     */
    private static final String TRENNZEICHENEINGABE = ",";
    /**
     * Konstante zur Trennung der einzelnen Querys
     */
    private static final String TRENNZEICHENQUERY = ":";
    
    private static final HashMap<String, String> BEZEICHNER = new 
            HashMap<String, String>() {{
              put("nr","ID");
              put("name","NAME");
              put("vname","VORNAME");
              put("datum","ERFASSUNGSDATUM");
              put("status","STATUS");
              put("frei","BESTANDSMENGEFREI");
              put("res","BESTANDSMENGERESERVIERT");
              put("zul","BESTANDSMENGEZULAUF");
              put("ver","BESTANDSMENGEVERKAUFT");
              put("typ","AUFTRAGSART");
            }};
    
    /**
     * Parst den übergebenen String und gibt die DB-Attributnamen zurück
     * @param input Sucheingabe
     * @param table Tabelle in der gesucht werden soll (Hier nur null Prüfung)
     * @return Es wird eine Hashmap zurückgegeben mit dem Inhalt der 
     *         DB-Attributnamen zur weiteren Generierung der SQL-Statements
     * @throws ApplicationException Sollten Eingaben ungültig sein,
     *         so wird eine AE geworfen.
     */
    public HashMap<String, String> parse(String input, String table) 
            throws ApplicationException {
        //Daten Deklaration
        String[] praefixList = null;
        StringTokenizer st = null;
        //Evt. andere Collection nehmen?
        HashMap<String, String> searchQuerys = new HashMap<>();
        Iterator<Entry<String, String>> iterator = null;
        String inputNoSpace = "";
        String searchIdentifier = null;
        String databaseIdentifier = null;
        String value = null;
        
        //Prüfe, ob die Sucheingabe und die Table null sind
        //Wenn ja, dann wirf eine ApplicationException
        if (input == null || table == null) {
            throw new ApplicationException("No Input", "Es gab keine Eingabe!");
        }
        //Initialisiere StringTokenizer
        st = new StringTokenizer(input);
        //Durchlaufe solange wie Tokens vorhanden sind
        while (st.hasMoreTokens()) {
            inputNoSpace += st.nextToken();
        }
        //Speicher alle praefixe in das array unter gegebenen Trennzeichen
        praefixList = inputNoSpace.split(TRENNZEICHENEINGABE);
        //Iteriere über alle Suchattribute
        for (String praefix : praefixList) {
            //Identifiziere das Attribut nach dem gesucht werden soll
            searchIdentifier = praefix.split(TRENNZEICHENQUERY)[0];
            //Identifiziere den Wert nach dem gesucht werden soll
            value = praefix.split(TRENNZEICHENQUERY)[1];
            //Ermittle den Datenbanken Namen aus der Hashmap
            databaseIdentifier = BEZEICHNER.get(searchIdentifier);
            //Prüfe, ob der User eine gültige Eingabe gemacht hat.
            if (databaseIdentifier == null) {
                throw new ApplicationException("Fehler", 
                        "Das Suchkürzel: " + searchIdentifier + " ist Falsch!");
            }
            //Attribute als DB-Spalten Namen speichern
            searchQuerys.put(databaseIdentifier, value);
        }
        return searchQuerys;
    }
    
    
}
