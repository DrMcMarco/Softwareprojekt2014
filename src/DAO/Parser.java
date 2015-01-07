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
     * Leer-String.
     */
    private static final String LEER = "";
    
    /**
     * Fehler Titel für Exception.
     */
    private static final String FEHLER_TITEL = "Fehler";
    
    /**
     * Fehlermeldung für Exception.
     */
    private static final String FEHLER_KEINE_EINGABE = "Es gab keine Eingabe!";
    
    /**
     * Fehlermeldung für Exception.
     */
    private static final String FEHLER_OPERATOR = "Der Operator war ungültig!";
    
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
        String eingabeOhneLeerzeichen = LEER;
        String suchAttr = null;
        String dbAttr = null;
        String wert = null;
        String datentyp = LEER;
        String sqlAuftragPartnerNachName = " (SELECT GESCHAEFTSPARTNERID FROM "
                + "\"Geschäftspartner\", ANSCHRIFT " 
                + " where \"Geschäftspartner\".LIEFERADRESSE_ANSCHRIFTID = "
                + "ANSCHRIFT.ANSCHRIFTID " 
                + " AND ANSCHRIFT.\"NAME\" = '§')";
        String sqlAuftragStatusNachName = " (SELECT STATUSID FROM STATUS "
                + "where STATUS = '§')";
        String sqlAuftragZkNachArt = " (SELECT ZAHLUNGSKONDITIONID FROM "
                + "ZAHLUNGSKONDITION where AUFTRAGSART = '§')";
        //Prüfe, ob die Sucheingabe und die Table null sind
        //Wenn ja, dann wirf eine ApplicationException
        if (eingabe == null || tabelle == null) {
            throw new ApplicationException(FEHLER_TITEL, FEHLER_KEINE_EINGABE);
        }
        //Initialisiere StringTokenizer
        st = new StringTokenizer(eingabe);
        
        //Durchlaufe alle Token
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            eingabeOhneLeerzeichen += s;

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
                    //Wandel alles in Kleinbuchstaben um
                    suchAttr = suchAttr.toLowerCase();
                    //Ermittle den Datenbanken Namen aus der Hashmap
                    dbAttr = attribute.get(suchAttr);
                    //Prüfe, ob der User eine gültige Eingabe gemacht hat.
                    if (dbAttr == null) {
                        throw new ApplicationException(FEHLER_TITEL, 
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
                                            .gibDatentypVonSuchAttribut(dbAttr, 
                                                    tabelle);
                            
                            //Prüfe,ob es sich um ein String oder Datum handelt.
                            if ("String".equals(datentyp) 
                                    || "Date".equals(datentyp)) {
                                //Hier müssen für das SQL-Statement hochkommas
                                //hinzugefügt werden.
                                wert = "'" + wert + "'";
                            }
                            //Prüfe ob es sich um GeschäftspartnerAttr handelt
                            if ("Geschäftspartner".equals(dbAttr)) {
                                //Bei geschaeftspartner muss man " setzen wegenÄ
                                dbAttr = "\"" + dbAttr + "\"";
                            }
                            
                            //Besondere Suche über Fremdschlüssel, es werden
                            //Sub-Statements integiert, um über bestimmte 
                            //Suchparameter an die ID's zu kommen.
                            if ("GeschäftspartnerFK".equals(dbAttr)
                                    && "Auftragskopf".equals(tabelle)) {
                                //Es wird der Platzhalter durch das 
                                //Suchkriterium ersetzt
                                sqlAuftragPartnerNachName 
                                    = sqlAuftragPartnerNachName.replace(
                                        "'§'", "" + wert + "");
                                //Anschliessend wird das SQL-Statement so 
                                //verändert, dass die 
                                //ID über die FK übergeben wird.
                                wert = sqlAuftragPartnerNachName;
                                
                            } else if (("StatusFK").equals(dbAttr)
                                    && "Auftragskopf".equals(tabelle)) {
                                sqlAuftragStatusNachName 
                                    = sqlAuftragStatusNachName.replace(
                                        "'§'", "" + wert + "");
                                wert = sqlAuftragStatusNachName;
                            } else if (("ZAHLUNGSKONDITION_"
                                    + "ZAHLUNGSKONDITIONIDFK").equals(dbAttr)
                                    && "Auftragskopf".equals(tabelle)) {
                                sqlAuftragZkNachArt 
                                    = sqlAuftragZkNachArt.replace(
                                        "'§'", "" + wert + "");
                                wert = sqlAuftragZkNachArt;
                            }
                            //Anschliessend muss der eigentliche Attributenname
                            //wieder gesetzt werden.
                            dbAttr = dbAttr.replace("FK", "");
                            //SQL-Statement aus suchkürzel , wert und lkz 
                            //konkatenieren und in die Ergebnisliste einfügen.
                            abfrageErgebnis.add(dbAttr + " " + OPERATORLIKE 
                                    + " " + wert + LKZ);
                        } else {
                            throw new ApplicationException(FEHLER_TITEL, 
                                    "Operator falsch?");
                        }
                    } else {
                        //Prüfe, um was für ein Datentyp es sich handelt.
                        datentyp = GUIFactory.getDAO()
                                        .gibDatentypVonSuchAttribut(dbAttr, 
                                                tabelle);
                        //Prüfe,ob es sich um ein String oder Datum handelt.
                        if ("String".equals(datentyp) 
                                || "Date".equals(datentyp)) {
                            //Hier müssen für das SQL-Statement hochkommas
                            //hinzugefügt werden.
                            wert = "'" + wert + "'";
                        }
                        //Prüfe ob es sich um GeschäftspartnerAttr handelt
                        if ("Geschäftspartner".equals(dbAttr)) {
                            //Bei geschaeftspartner muss man " setzen wegenÄ
                            dbAttr = "\"" + dbAttr + "\"";
                        }
                        //Besondere Suche über Fremdschlüssel, es werden
                        //Sub-Statements integiert, um über bestimmte 
                        //Suchparameter an die ID's zu kommen.
                        if ("GeschäftspartnerFK".equals(dbAttr)
                                && "Auftragskopf".equals(tabelle)) {
                            //Es wird der Platzhalter durch das 
                            //Suchkriterium ersetzt
                            sqlAuftragPartnerNachName 
                                = sqlAuftragPartnerNachName.replace(
                                    "'§'", "" + wert + "");
                            //Anschliessend wird das SQL-Statement so 
                            //verändert, dass die 
                            //ID über die FK übergeben wird.
                            wert = sqlAuftragPartnerNachName;
                        } else if (("StatusFK").equals(dbAttr)
                                && "Auftragskopf".equals(tabelle)) {
                            sqlAuftragStatusNachName 
                                = sqlAuftragStatusNachName.replace(
                                    "'§'", "" + wert + "");
                            wert = sqlAuftragStatusNachName;
                        } else if (("ZAHLUNGSKONDITION_"
                                + "ZAHLUNGSKONDITIONIDFK").equals(dbAttr)
                                && "Auftragskopf".equals(tabelle)) {
                            sqlAuftragZkNachArt 
                                = sqlAuftragZkNachArt.replace(
                                    "'§'", "" + wert + "");
                            wert = sqlAuftragZkNachArt;
                        }
                        //Anschliessend muss der eigentliche Attributenname
                        //wieder gesetzt werden.
                        dbAttr = dbAttr.replace("FK", "");
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
            if (LEER.equals(wert)) {
                throw new ApplicationException(FEHLER_TITEL, FEHLER_OPERATOR);
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
