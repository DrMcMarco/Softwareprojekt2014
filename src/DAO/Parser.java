/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import JFrames.GUIFactory;
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
    /**
     * Konstante zur Trennung der einzelnen Sucheingaben.
     */
    private static final String TRENNZEICHEN = ";";
    
    /**
     * Platzhalter für genau ein Zeichen in SQL.
     */
    private static final char PLATZHALTEREINZEICHEN = '_';
    
    /**
     * Platzhalter für genau 0-n Zeichen in SQL.
     */
    private static final char PLATZHALTERMULTIPLEZEICHEN = '%';
    
    /**
     * Platzhalter für genau 0-n Zeichen in Linux schreibweise.
     */
    private static final char PLATZHALTERMULTIPLEZEICHENAPP = '*';
    
    /**
     * Platzhalter für genau 0-n Zeichen in Linux schreibweise.
     */
    private static final char PLATZHALTEREINZEICHENAPP = '?';
    
    /**
     * Konstante zur Trennung der einzelnen Querys.
     */
    private static final String[] OPERATOR = {"<=" , ">=", "<>", "<",
                                              ">", "="};
    /**
     * SQL-Statement zur Betrachtung von nicht gelöschten Daten.
     */
    private static final String LKZ = " AND LKZ = 0 ";
    
    /**
     * SQL-Statement zur Benutzung von Platzhaltern wird LIKE verwendet.
     */
    private static final String OPERATORLIKE = "LIKE";
    
    /**
     * Hashmap mit allen Schlüßelwörtern.
     */
    private final HashMap<String, String> attribute;
    
    /**
     * Konstruktor mit Übergabe der Suchkuerzel.
     * @param ttrbt Alle Suchschlüsselwörter
     */
    public Parser(HashMap<String, String> ttrbt) {
        this.attribute = ttrbt;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Parst den übergebenen String und gibt die DB-Attributnamen zurück.
     * @param eingabe Sucheingabe
     * @param tabelle Tabelle in der gesucht werden soll (Hier nur null Prüfung)
     * @return Es wird eine Hashmap zurückgegeben mit dem Inhalt der 
     *         DB-Attributnamen zur weiteren Generierung der SQL-Statements
     * @throws ApplicationException Sollten Eingaben ungültig sein,
     *         so wird eine AE geworfen.
     * 
     */
    public ArrayList<String> parse(String eingabe, String tabelle) 
        throws ApplicationException {
        //Daten Deklaration
        String[] praefixListe = null;
        StringTokenizer st = null;
        ArrayList<String> abfrageErgebnis = new ArrayList<>();
        String eingabeOhneLeerzeichen = "";
        String suchAttr = null;
        String dbAttr = null;
        String wert = null;
        String datentyp = "";
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
        
        //Wandel alles in Kleinbuchstaben um.
        eingabeOhneLeerzeichen = eingabeOhneLeerzeichen.toLowerCase();
        
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
                    dbAttr = attribute.get(suchAttr);
                    //Prüfe, ob der User eine gültige Eingabe gemacht hat.
                    if (dbAttr == null) {
                        throw new ApplicationException("Fehler", 
                            "Das Suchkürzel: " + suchAttr + " ist Falsch!");
                    }
                    //Prüfe auf platzhalter
                    if (wert.contains(new String(new char[] 
                    {PLATZHALTEREINZEICHENAPP})) 
                            || wert.contains(new String(
                                new char[] {PLATZHALTERMULTIPLEZEICHENAPP}))) {
                        //Geht das mit den platzhaltern nur wenn man = Nimmt?
                        if (splitOp.equals(OPERATOR[5])) {
                            //Ersetze alle ? durch _ (SQL Anpassung)
                            wert = wert.replace(PLATZHALTEREINZEICHENAPP, 
                                PLATZHALTEREINZEICHEN);
                            //Ersetze alle * durch % (SQL Anpassung)
                            wert = wert.replace(PLATZHALTERMULTIPLEZEICHENAPP, 
                                PLATZHALTERMULTIPLEZEICHEN);
                            
                            //Prüfe, um was für ein Datentyp es sich handelt.
                            datentyp = GUIFactory.getDAO()
                                            .gibDatentypVonSuchAttribut(wert);
                            
                            //Prüfe, ob es sich um ein String handelt.
                            if ("String".equals(datentyp)) {
                                //Hier müssen für das SQL-Statement hochkommas
                                //hinzugefügt werden.
                                wert = "'" + wert + "'";
                            }
                            //SQL-Statement aus suchkürzel , wert und lkz 
                            //konkatenieren und in die Ergebnisliste einfügen.
                            abfrageErgebnis.add(dbAttr + " " + OPERATORLIKE 
                                    + " " + wert + LKZ);
                        } else {
                            throw new ApplicationException("Fehler", 
                                    "Operator falsch?");
                        }
                    } else {
                        //Prüfe, um was für ein Datentyp es sich handelt.
                        datentyp = GUIFactory.getDAO()
                                        .gibDatentypVonSuchAttribut(wert);
                        //Prüfe, ob es sich um ein String handelt.
                        if ("String".equals(datentyp)) {
                            //Hier müssen für das SQL-Statement hochkommas
                            //hinzugefügt werden.
                            wert = "'" + wert + "'";
                        }
                        
                        //SQL-Statement aus suchkürzel , wert und 
                        //lkz konkatenieren und in die Ergebnisliste einfügen.
                        abfrageErgebnis.add(dbAttr + " " + splitOp + " " + wert
                            + LKZ);
                    }
                    
                    //Beende die 2. Schleife sobald ein Operator gefunden wurde.
                    //Es müssen keine weiteren Operatoren mehr gesucht werden.
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
