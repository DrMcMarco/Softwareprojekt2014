/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DTO.Geschaeftspartner;
import JFrames.GUIFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final String LKZ = " LKZ = 0 ";
    
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
     * Int Konstante für WAHR.
     */
    private static final int WAHR = 1;
    
    /**
     * Hashmap mit allen Schlüßelwörtern.
     */
    private final HashMap<String, String> attribute;
    
    /**
     * Join-Verknüpfung für Abfragen über mehrere Tabellen.
     */
    private String joinBefehl;
    
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
    public String parse(String eingabe, String tabelle) 
        throws ApplicationException {
        //Daten Deklaration
        String[] praefixListe = null;
        StringTokenizer st = null;
        ArrayList<String> abfrageErgebnis = new ArrayList<>();
        String eingabeOhneLeerzeichen = LEER;
        String suchAttr = null;
        String dbAttr = null;
        String wert = null;
        String sqlAbfrage = LEER;
        this.joinBefehl = LEER;
        //Prüfe, ob die Sucheingabe und die Table null sind
        //Wenn ja, dann wirf eine ApplicationException
        if (eingabe == null || tabelle == null) {
            throw new ApplicationException(FEHLER_TITEL, FEHLER_KEINE_EINGABE);
        }
        //Initialisiere StringTokenizer
        st = new StringTokenizer(eingabe);
        
//        char[] c = eingabe.toCharArray();
//        
//        for (int i = 0; i < c.length; i++) {
//            String t = "" + c[i];
//            for (int k = 0; k < OPERATOR.length; k++) {
//                if (OPERATOR[k].equals(t)) {
//                    
//                }
//            }
//            
//        }
         
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
                            
                            
                            //SQL-Statement aus suchkürzel , wert und lkz 
                            //konkatenieren und in die Ergebnisliste einfügen.
                            abfrageErgebnis.add(this.setzeAbfrage(dbAttr, wert, 
                                    tabelle, OPERATORLIKE));
                        } else {
                            throw new ApplicationException(FEHLER_TITEL, 
                                    "Operator falsch?");
                        }
                    } else {
                        
                        //SQL-Statement aus suchkürzel , wert und 
                        //lkz konkatenieren und in die Ergebnisliste einfügen.
                        abfrageErgebnis.add(this.setzeAbfrage(dbAttr, wert, 
                                    tabelle, splitOp));
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
        
        if ("Geschäftspartner".equals(tabelle)) {
            tabelle = "\"" + tabelle + "\"";
        }
        
        //Sql-Statement Dynamisch erzeugen
        sqlAbfrage = "SELECT * FROM " + tabelle + this.joinBefehl + " WHERE ";
        //Iteriere über alle Input Einträge
        for (int i = 0; i < abfrageErgebnis.size(); i++) {
            //Hole Abfrage aus der Liste
            sqlAbfrage += abfrageErgebnis.get(i);
            //Prüfe, ob mehrere Einträge vorhanden sind, diese müssen mit AND
            //Verknüpft werden.Beim letzten durchlauf wird kein AND mehr gesetzt
            if (abfrageErgebnis.size() > 1 && i < abfrageErgebnis.size() - 1) {
                sqlAbfrage += " AND ";
            }
        }
        //Löschkennzeichen = 0 Zu letzt anfügen
        sqlAbfrage += " AND " + tabelle + "." + LKZ;
        return sqlAbfrage;
    }
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Setzt das SQL-Statement je nach belieben.
     * @param dbAttribut DBAttribut
     * @param dbWert Der Wert
     * @param tabelle Die Tabelle
     * @param operator Der benutzte Operator
     * @return SQL-Statement
     * @throws ApplicationException Bei fehler
     */
    public String setzeAbfrage(String dbAttribut, String dbWert, 
        String tabelle, String operator) throws ApplicationException {
        
        String sqlAuftragPartnerJoin = " left join \"Geschäftspartner\" on "
                + "AUFTRAGSKOPF.\"Geschäftspartner\" = "
                + "\"Geschäftspartner\".GESCHAEFTSPARTNERID left join "
                + "ANSCHRIFT on "
                + "\"Geschäftspartner\".RECHNUNGSADRESSE_ANSCHRIFTID "
                + "= ANSCHRIFT.ANSCHRIFTID ";
        
//        String sqlAuftragPartnerNachName = "  ANSCHRIFT.\"NAME\" ";
//        String sqlAuftragPartnerNachVName = "  ANSCHRIFT.\"VNAME\" ";
        
        String sqlAuftragStatusJoin = " left join STATUS on "
                + "AUFTRAGSKOPF.STATUS = STATUS.STATUSID ";
        String sqlAuftragStatusNachName = " STATUS.STATUS ";
        
        String sqlAuftragZkJoin  = " left join ZAHLUNGSKONDITION on "
                + "AUFTRAGSKOPF.ZAHLUNGSKONDITION_ZAHLUNGSKONDITIONID = "
                + "ZAHLUNGSKONDITION.ZAHLUNGSKONDITIONID ";
        
        String sqlAuftragZkNachArt = " ZAHLUNGSKONDITION.AUFTRAGSART ";
        
        String sqlArtikelKatJoin = " left join ARTIKELKATEGORIE on "
                + "ARTIKEL.KATEGORIE = ARTIKELKATEGORIE.ID ";
        
        String sqlArtikelKategorieNachName = " ARTIKELKATEGORIE.KATEGORIENAME ";
        
        String sqlPartnerAnschriftJoin = " left join ANSCHRIFT " 
                + "ON \"Geschäftspartner\".RECHNUNGSADRESSE_ANSCHRIFTID = "
                + "ANSCHRIFT.ANSCHRIFTID ";
        String sqlPartnerAnschriftNachName = " ANSCHRIFT.\"NAME\" ";
        
        String sqlPartnerAnschriftNachVName = " ANSCHRIFT.\"VORNAME\" ";
        
        String sqlPartnerAnschriftNachEmail = "  ANSCHRIFT.\"EMAIL\" ";
        String datentyp = LEER;
        
        //Prüfe, ob der Wert dem Datentyp entspricht.
        this.pruefeDatentyp(dbAttribut, dbWert, tabelle);
        
        //Hole Datentyp aus db.
        datentyp = GUIFactory.getDAO().gibDatentypVonSuchAttribut(dbAttribut, 
                tabelle);

        //Prüfe,ob es sich um ein String oder Datum handelt.
        if ("String".equals(datentyp) 
                || "Date".equals(datentyp)) {
            //Hier müssen für das SQL-Statement hochkommas
            //hinzugefügt werden.
            dbWert = "'" + dbWert + "'";
        } else if ("Double".equals(datentyp)) {
            dbWert = dbWert.replaceAll(",", ".");
        }
        //Prüfe ob es sich um GeschäftspartnerAttr handelt
        if ("Geschäftspartner".equals(dbAttribut)
            || "GeschäftspartnerFK".equals(dbAttribut)) {
            //Bei geschaeftspartner muss man " setzen wegenÄ
            dbAttribut = "\"" + dbAttribut + "\"";
        }

        //Besondere Suche über Fremdschlüssel, es werden
        //Joins integiert, um über bestimmte 
        //Suchparameter an die ID's zu kommen.
        if ("\"GeschäftspartnerFK\"".equals(dbAttribut)
                && "Auftragskopf".equals(tabelle)) {
            
            dbAttribut = sqlPartnerAnschriftNachName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlAuftragPartnerJoin;
            } else if (!this.joinBefehl.contains(sqlAuftragPartnerJoin)) {
                this.joinBefehl += " " + sqlAuftragPartnerJoin;
            }
        } else if (("GeschäftspartnerFKVNAME").equals(dbAttribut)
                && "Auftragskopf".equals(tabelle)) {
            dbAttribut = sqlPartnerAnschriftNachVName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlAuftragPartnerJoin;
            } else if (!this.joinBefehl.contains(sqlAuftragPartnerJoin)) {
                this.joinBefehl += " " + sqlAuftragPartnerJoin;
            }
        } else if (("StatusFK").equals(dbAttribut)
                && "Auftragskopf".equals(tabelle)) {
            dbAttribut = sqlAuftragStatusNachName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlAuftragStatusJoin;
            } else if (!this.joinBefehl.contains(sqlAuftragStatusJoin)) {
                this.joinBefehl += " " + sqlAuftragStatusJoin;
            }
        } else if (("ZAHLUNGSKONDITION_"
                + "ZAHLUNGSKONDITIONIDFK").equals(dbAttribut)
                && "Auftragskopf".equals(tabelle)) {
            dbAttribut = sqlAuftragZkNachArt;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlAuftragZkJoin;
            } else if (!this.joinBefehl.equals(sqlAuftragZkJoin)) {
                this.joinBefehl += " " + sqlAuftragZkJoin;
            }
            
        } else if (("RECHNUNGSADRESSE_ANSCHRIFTIDFKNAME").equals(dbAttribut)
                && "Geschäftspartner".equals(tabelle)) {
            dbAttribut = sqlPartnerAnschriftNachName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlPartnerAnschriftJoin;
            } else if (!this.joinBefehl.equals(sqlPartnerAnschriftJoin)) {
                this.joinBefehl += " " + sqlPartnerAnschriftJoin;
            }
        } else if (("RECHNUNGSADRESSE_ANSCHRIFTIDFKVNAME").equals(dbAttribut)
                && "Geschäftspartner".equals(tabelle)) {
            dbAttribut = sqlPartnerAnschriftNachVName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlPartnerAnschriftJoin;
            } else if (!this.joinBefehl.equals(sqlPartnerAnschriftJoin)) {
                this.joinBefehl += " " + sqlPartnerAnschriftJoin;
            }
        } else if (("RECHNUNGSADRESSE_ANSCHRIFTIDFKEMAIL").equals(dbAttribut)
                && "Geschäftspartner".equals(tabelle)) {
            dbAttribut = sqlPartnerAnschriftNachEmail;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlPartnerAnschriftJoin;
            } else if (!this.joinBefehl.equals(sqlPartnerAnschriftJoin)) {
                this.joinBefehl += " " + sqlPartnerAnschriftJoin;
            }
            
        } else if (("KategorieFKNAME").equals(dbAttribut)
                && "Artikel".equals(tabelle)) {
            dbAttribut = sqlArtikelKategorieNachName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlArtikelKatJoin;
            } else if (!this.joinBefehl.equals(sqlArtikelKatJoin)) {
                this.joinBefehl += " " + sqlArtikelKatJoin;
            }
        }
        //Anschliessend muss der eigentliche Attributenname
        //wieder gesetzt werden.
        dbAttribut = dbAttribut.replace("FK", "");
        
        return dbAttribut + " " + operator + " " + dbWert;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 08.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Es wird geprüft, ob der übergebene Wert dem Datentyp aus der DB 
     * entspricht. Im Fehlerfall wird Benutzer informiert.
     * @param dbAttribut das DbAttribut
     * @param dbWert der Wert
     * @param tabelle Die Tabelle
     * @return True wenn der übergebene Wert dem Datentyp entspricht.
     * @throws ApplicationException Benutzerausgabe.
     */
    public boolean pruefeDatentyp(String dbAttribut, String dbWert,
        String tabelle) throws ApplicationException {
        String datentyp = LEER;
        //Hole Datentyp aus DB
        datentyp = GUIFactory.getDAO().gibDatentypVonSuchAttribut(
                dbAttribut, tabelle);
        
        //Prüfe ob es sich um dein Datum handelt
        if ("Date".equals(datentyp)) {
            //Erstelle Datum
            Date d; //new SimpleDateFormat("dd.mm.yyyy");
            try {
                //Versuche es zu parse. Wenn das Datum nicht geparset werden 
                //Kann liegt ein Fehler im Format vor 
                //-> Der Benutzer wird informiert.
                d = DatumParser.stringToDate(dbWert);
            } catch (Exception ex) {
                throw new ApplicationException(FEHLER_TITEL, 
                        "Die Ergebniseingabe: " + "" + dbWert 
                                + " ist ungültig! \n Der Wert "
                                + "muss ein gültiges Datum sein!");
            }
        } else {
            //Es müssen alle . durch Komma ersetzt werden, da das System
            //Sonst den Typ Double nicht erkennt...
            if ("Double".equals(datentyp)) {
                dbWert = dbWert.replaceAll("\\.", ",");
            }
            //Ermittle den Typ des übergebenen wertes.
            Object objektTyp = gibObjektNachTyp(dbWert);
            //Prüfe, ob der Typ gefunden wurde
            if (objektTyp == null) {
                throw new ApplicationException(FEHLER_TITEL, 
                        "Der Datentyp konnte nicht gefunden werden!");
            }
            //Es wird dynamisch der Datentyp ermittelt.
            String typ = objektTyp.getClass().getSimpleName();
            //Prüfe ob der eingegebene Wert dem Datentyp-Format entspricht.
            //Wichtig ist, dass int und long ok sind!
            if (!typ.equals(datentyp) && (!typ.equals("Integer") 
                    && !datentyp.equals("Long"))) {
                //Generiere Fehlermeldung
                switch (datentyp) {
                    case "Integer":
                        typ = "Der Wert muss eine natürliche Zahl sein!";
                        break;
                    case "Double":
                        typ = "Der Wert muss eine gleitkomma Zahl sein!";
                        break;
                    case "String":
                        typ = "Der Wert muss ein Text sein!";
                        break;
                    default:
                        typ = "Der Typ konnte nicht ermittelt werden!";
                        break;
                }
                throw new ApplicationException(FEHLER_TITEL, 
                        "Die Ergebniseingabe: " + "" + dbWert 
                                + " ist ungültig! \n " + typ);
            }
        }

        return true;
    }
    //TBD
    static Object gibObjektNachTyp(String dbWert) {
        Scanner sc = new Scanner(dbWert);
        return
            sc.hasNextInt() ? sc.nextInt() :
            sc.hasNextLong() ? sc.nextLong() :
            sc.hasNextDouble() ? sc.nextDouble() :
            sc.hasNext() ? sc.next() :
            dbWert;
    }
    //TBD
    public Object gibObjektNachTyp1(String s) {
        Scanner sc = new Scanner(s);
        Object ergebnis = null;
        
        if (sc.hasNextInt()) {
            ergebnis = sc.nextInt();
        } else if (sc.hasNextLong()) {
            ergebnis = sc.nextLong();
        } else if (sc.hasNextDouble()) {
            ergebnis = sc.nextDouble();
        } else if (sc.hasNext()) {
            ergebnis = sc.next();
        } else {
            ergebnis = s;
        }
        return ergebnis;
    }
    
    public static void main(String[] args) {
        try {
            GUIFactory gui = new GUIFactory();
            
            Collection<?>  a = GUIFactory.getDAO().suchAbfrage(
                    "eingangsdatum <= 01.01.2015", "Auftragskopf");
            
            for (Object o : a) {
                System.out.println(o.toString());
                
                
//                Geschaeftspartner gp = (Geschaeftspartner) o;
//                System.out.println(gp.getLieferadresse().getName());
            }
            Calendar d = Calendar.getInstance();
            d.setTime(DatumParser.stringToDate("12/12/2000")); 
            System.out.println(d.getTime());
        } catch (ApplicationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
