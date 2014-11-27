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
    private static final String TRENNZEICHEN = ",";
    
    /**
     * Parst den übergebenen String und erstellt ein dynamisches SQL-Statement
     * @param input Sucheingabe
     * @param table Tabelle in der gesucht werden soll
     * @throws ApplicationException Sollten Eingaben ungültig sein,
     *         so wird eine AE geworfen.
     */
    public void parse(String input, String table) throws ApplicationException {
        //Daten Deklaration
        String[] praefixList = null;
        StringTokenizer st = null;
        String inputNoSpace = "";
        
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
        praefixList = inputNoSpace.split(TRENNZEICHEN);
        
    }
    
    
}
