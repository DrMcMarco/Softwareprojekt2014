/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/*----------------------------------------------------------*/
/* Datum Name Was                                           */
/* 11.11.14 sch angelegt                                    */
/*----------------------------------------------------------*/
/**
 *
 * @author Simon <Simon.Simon at your.org>
 * Klasse Parser
 */
public class Parser {
    /**.
     * Konstante zur Trennung der einzelnen Sucheingaben
     */
    private static final String TRENNZEICHEN = ";";
    /**.
     * Konstante zur Trennung der einzelnen Querys
     */
    private static final String[] OPERATOR = {"<=" , ">=", "<>", "<",
                                              ">", "="};
    /**.
     * Hashmap mit allen Schlüßelwörtern
     */
    private static final HashMap<String, String> ATTRIBUTE = new 
            HashMap<String, String>() { {
                put("nr", "Id");
                put("name", "Name");
/*------------Artikel spezifische Eingaben----------------------*/              
                put("atext", "Artikeltext");
                put("btext", "Bestelltext");
                put("wert", "Einkaufswert");
                put("name", "Name");
                put("mwst", "Mwst");
                put("kat", "Kategorie");
                put("frei", "Frei");
                put("res", "Reserviert");
                put("zul", "Zulauf");
                put("ver", "Verkauft");
/*------------Anschrift spezifische Eingaben----------------------*/
                put("email", "EMAIL");
                put("erfassungsdatum", "ERFASSUNGSDATUM");
                put("fax", "FAX");
                put("gebdatum", "GEBURTSDATUM");
                put("hsnr", "HAUSNUMMER");
                put("name", "NAME");
                put("ort", "ORT");
                put("plz", "PLZ");
                put("staat", "STAAT");
                put("strasse", "STRASSE");
                put("telefon", "TELEFON");
                put("titel", "TITEL");
                put("vname", "VORNAME");
                put("typ", "TYP");
/*------------Kategorie spezifische Eingaben----------------------*/
                put("beschreibung", "BESCHREIBUNG");
                put("katname", "KATEGORIENAME");
                put("katkommentar", "KOMMENTAR");
/*------------Auftragskopf spezifische Eingaben----------------------*/
                put("abschlussdatum", "ABSCHLUSSDATUM");
                put("text", "AUFTRAGSTEXT");
                put("eingangsdatum", "ERFASSUNGSDATUM");
                put("lieferdatum", "LIEFERDATUM");
                put("wert", "WERT");
                put("auftragsart", "AUTRAGSART");
                put("geschaeftspartner", "Geschäftspartner");
                put("status", "STATUS");
                put("zahlungskondition", 
                        "ZAHLUNGSKONDITION_ZAHLUNGSKONDITIONID");
/*------------Geschäftspartner spezifische Eingaben----------------------*/
                put("kredit", "KREDITLIMIT");
                put("partnerart", "TYP");
                put("typ", "LIEFERADRESSE_ANSCHRIFTID");
                put("typ", "RECHNUNGSADRESSE_ANSCHRIFTID");
/*------------Status spezifische Eingaben----------------------*/
                put("status", "STATUS");
/*------------Zahlungskondition spezifische Eingaben----------------------*/
                put("auftragsart", "AUFTRAGSART");
                put("lieferzeit", "LIEFERZEITSOFORT");
                put("mahnzeit1", "MAHNZEIT1");
                put("mahnzeit2", "MAHNZEIT2");
                put("mahnzeit3", "MAHNZEIT3");
                put("skonto1", "SKONTO1");
                put("skonto2", "SKONTO2");
                put("skontozeit1", "SKONTOZEIT1");
                put("skontozeit2", "SKONTOZEIT2");
                put("sperrzeitwunsch", "SPERRZEITWUNSCH");
            } };
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**.
     * Parst den übergebenen String und gibt die DB-Attributnamen zurück
     * @param eingabe Sucheingabe
     * @param tabelle Tabelle in der gesucht werden soll (Hier nur null Prüfung)
     * @return Es wird eine Hashmap zurückgegeben mit dem Inhalt der 
     *         DB-Attributnamen zur weiteren Generierung der SQL-Statements
     * @throws ApplicationException Sollten Eingaben ungültig sein,
     *         so wird eine AE geworfen.
     * 
     * TO-DO: prüfung auf illegale zeichen, und nicht einhaltung der regeln
     */
    public ArrayList<String> parse(String eingabe, String tabelle) 
        throws ApplicationException {
        //Daten Deklaration
        String[] praefixListe = null;
        StringTokenizer st = null;
        //Evt. andere Collection nehmen?
        HashMap<String, String> suchAbfragenMap = new HashMap<>();
        ArrayList<String> abfrageErgebnis = new ArrayList<>();
        String eingabeOhneLeerzeichen = "";
        String suchAttr = null;
        String dbAttr = null;
        String wert = null;
        
        //Prüfe, ob die Sucheingabe und die Table null sind
        //Wenn ja, dann wirf eine ApplicationException
        if (eingabe == null || tabelle == null) {
            throw new ApplicationException("No Input", "Es gab keine Eingabe!");
        }
        //Initialisiere StringTokenizer
        st = new StringTokenizer(eingabe);
        //Durchlaufe alle Token
        while (st.hasMoreTokens()) {
            eingabeOhneLeerzeichen += st.nextToken();
        }
        //Speicher alle praefixe in das array unter gegebenen Trennzeichen
        praefixListe = eingabeOhneLeerzeichen.split(TRENNZEICHEN);
        //Iteriere über alle Suchattribute
        for (String praefix : praefixListe) {
            //Iteriere über alle Operatoren
            for (String splitOp : OPERATOR) {
                //Identifiziere das Attribut nach dem gesucht werden soll
                suchAttr = praefix.split(splitOp)[0];
                //Wenn Operator gefunden wurde führen wir fort
                if (praefix.split(splitOp).length > 1) {
                    //Identifiziere den Wert nach dem gesucht werden soll
                    wert = praefix.split(splitOp)[1];
                    //Ermittle den Datenbanken Namen aus der Hashmap
                    dbAttr = ATTRIBUTE.get(suchAttr);
                    //Prüfe, ob der User eine gültige Eingabe gemacht hat.
                    if (dbAttr == null) {
                        throw new ApplicationException("Fehler", 
                            "Das Suchkürzel: " + suchAttr + " ist Falsch!");
                    }
                    //To-Do: Operatoren müssen noch an DB.Stdt angepasst werden
                    abfrageErgebnis.add(dbAttr + " " + splitOp + " " + wert);
                    //Beende die 2. Schleife sobald ein Operator gefunden wurde.
                    break;
                }
            }
            //Überprüfung, wenn alle operatoren durchlaufen sind und keiner
            //gefunden werden konnte -> Falsche Operator eingabe -> Fehler
            if ("".equals(wert)) {
                throw new ApplicationException("Fehler", "Der Operator "
                        + "war ungültig!");
            }
        }
        
        return abfrageErgebnis;
    }
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    public boolean checkInput(String input) {
        
        
        
        return true;
    }
}
