/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Simon <Simon.Simon at your.org>
 * Klasse Parser
 */
public class Parser {
    /**
     * Konstante zur Trennung der einzelnen Sucheingaben
     */
    private static final String SPLITINPUT = ";";
    /**
     * Konstante zur Trennung der einzelnen Querys
     */
    private static final String[] SPLITOPERATOR = {"<=" , ">=", "<>", "<",
                                                ">", "="};
    /**
     * Hashmap mit allen Schlüßelwörtern
     */
    private static final HashMap<String, String> IDENTIFIER = new 
            HashMap<String, String>() {{
              put("nr","Id");
              put("name","Name");
//************Artikel spezifische Eingaben              
              put("atext","Artikeltext");
              put("btext","Bestelltext");
              put("wert","Einkaufswert");
              put("name","Name");
              put("mwst","Mwst");
              put("akat","Kategorie");
              put("frei","Frei");
              put("res","Reserviert");
              put("zul","Zulauf");
              put("ver","Verkauft");
//***********              
              put("kategorie", "Kategoriename");
              put("vname","Vorname");
              put("datum","Erfassungsdatum");
              put("status","Status");
              put("typ","Auftragsart");
            }};
    
    /**
     * Parst den übergebenen String und gibt die DB-Attributnamen zurück
     * @param input Sucheingabe
     * @param table Tabelle in der gesucht werden soll (Hier nur null Prüfung)
     * @return Es wird eine Hashmap zurückgegeben mit dem Inhalt der 
     *         DB-Attributnamen zur weiteren Generierung der SQL-Statements
     * @throws ApplicationException Sollten Eingaben ungültig sein,
     *         so wird eine AE geworfen.
     * 
     * TODO: prüfung auf illegale zeichen, und nicht einhaltung der regeln
     */
    public ArrayList<String> parse(String input, String table) 
            throws ApplicationException {
        //Daten Deklaration
        String[] praefixList = null;
        StringTokenizer st = null;
        //Evt. andere Collection nehmen?
        HashMap<String, String> searchQuerys = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();
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
        //Durchlaufe alle Token
        while (st.hasMoreTokens()) {
            inputNoSpace += st.nextToken();
        }
        //Speicher alle praefixe in das array unter gegebenen Trennzeichen
        praefixList = inputNoSpace.split(SPLITINPUT);
        //Iteriere über alle Suchattribute
        for (String praefix : praefixList) {
            //Iteriere über alle Operatoren
            for (String split : SPLITOPERATOR) {
                //Identifiziere das Attribut nach dem gesucht werden soll
                searchIdentifier = praefix.split(split)[0];
                //Wenn Operator gefunden wurde führen wir fort
                if (praefix.split(split).length > 1) {
                    //Identifiziere den Wert nach dem gesucht werden soll
                    value = praefix.split(split)[1];
                    //Ermittle den Datenbanken Namen aus der Hashmap
                    databaseIdentifier = IDENTIFIER.get(searchIdentifier);
                    //Prüfe, ob der User eine gültige Eingabe gemacht hat.
                    if (databaseIdentifier == null) {
                        throw new ApplicationException("Fehler", 
                        "Das Suchkürzel: " + searchIdentifier + " ist Falsch!");
                    }
                    result.add(databaseIdentifier + " " + split + " " + value);
                    //Beende die 2. Schleife sobald ein Operator gefunden wurde.
                    break;
                }
            }//Überprüfung, wenn alle operatoren durchlaufen sind und keiner
            //gefunden werden konnte -> Falsche Operator eingabe -> Fehler
        }
        
        return result;
    }
    
    public boolean checkInput(String input) {
        
        
        
        return true;
    }
}
