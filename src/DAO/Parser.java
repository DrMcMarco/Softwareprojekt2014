/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import JFrames.GUIFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/*----------------------------------------------------------*/
/* Datum Name Was                                           */
/* 11.11.14 sch angelegt                                    */
/*----------------------------------------------------------*/
/**
 *
 * @author Simon Schulz
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
     * SQL-Statement zur Aufsteigender Sortierung.
     */
    private static final String AUFSTEIGEND = " ASC ";
    
    /**
     * SQL-Statement zur Absteigender Sortierung.
     */
    private static final String ABSTEIGEND = " DESC ";
    
    /**
     * SQL-Statement zur Sortierung.
     */
    private static final String SORTIEREN = " ORDER BY ";
    
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
     * Join-Verknüpfung für Abfragen über mehrere Tabellen.
     */
    private String joinBefehl;
    
    /**
     * Attribut nach dem sortiert werden soll.
     */
    private String sortierAttribut;
    
    /**
     * Konstruktor mit Übergabe der Suchkuerzel.
     * @param ttrbt Alle Suchschlüsselwörter
     */
    public Parser(HashMap<String, String> ttrbt) {
        this.attribute = ttrbt;
        this.sortierAttribut = LEER;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 11.11.14 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Parst den übergebenen String und gibt die DB-Attributnamen zurück.
     * @param eingabe Sucheingabe
     * @param tabelle Tabelle in der gesucht werden soll (Hier nur null Prüfung)
     * @param sortierung String nach dem Sortiert werden soll.
     * @return Es wird eine Hashmap zurückgegeben mit dem Inhalt der 
     *         DB-Attributnamen zur weiteren Generierung der SQL-Statements
     * @throws ApplicationException Sollten Eingaben ungültig sein,
     *         so wird eine AE geworfen.
     * 
     */
    public String parse(String eingabe, String tabelle, String sortierung) 
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
        
        //Entferne alle Leerzeichen
        eingabeOhneLeerzeichen = this.loescheLeerzeichen(st);
        //Speicher alle praefixe in das array unter gegebenen Trennzeichen
        praefixListe = eingabeOhneLeerzeichen.split(TRENNZEICHEN);
        
        //Prüfe, ob ein Trennzeichen gefunden wurde
        if (praefixListe == null) {
            throw new ApplicationException(FEHLER_TITEL, "Es erfolgte keine "
                    + "gültige Eingabe!");
        }
        
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
                            
                            //SQL-Statement aus suchkürzel , wert, tabelle 
                            //und operator in die Ergebnisliste einfügen.
                            abfrageErgebnis.add(this.setzeAbfrage(dbAttr, wert, 
                                    tabelle, OPERATORLIKE));
                        } else {
                            throw new ApplicationException(FEHLER_TITEL, 
                                    "Platzhalter können nur mit dem "
                                            + "= Operator genutzt werden!");
                        }
                    } else {
                        //SQL-Statement aus suchkürzel , wert, tabelle 
                        //und operator in die Ergebnisliste einfügen.
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
        //Wegen dem ä muss mit " escaped werden
        if ("Geschäftspartner".equals(tabelle)) {
            tabelle = "\"" + tabelle + "\"";
        }
        
        //Sql-Statement Dynamisch erzeugen.
        //Es werden die joint befehle vor das where gesetzt.
        //Im falle einer suche über nur einer tabelle ist dieser String leer.
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
        //Prüfe, ob eine Eingabe getätigt wurde
        if (eingabe.equals(LEER)) {
            //Wenn nicht wird das SQL Statement so aufgebaut, dass das
            //LKZ nicht mit einem UND Verknüpft wird, da es als einziges 
            //Literal in der Abfrage steht.
            sqlAbfrage += "" + tabelle + "." + LKZ;
            sortierung = LEER;
        } else {
            //LKZ Wird mit AND Verknüpft.
            sqlAbfrage += " AND " + tabelle + "." + LKZ;
        }
        
        //Es wird das komplette SQL Statement hier zusammengesetzt
        //es wird hier noch eine UND Verknüpfung zum LKZ gemacht, da nur 
        //Ergebnisse angezeigt werden soll, die nicht als gelöscht vorgemerkt 
        //sind. desweiteren wird noch die Sortierung nach dem Literal, welches
        //An erster Stelle in der Suchabfrage steht, angehängt.
        //DESC ASC muss geklärt werden
        switch (sortierung) {
            case "absteigend" :
                sqlAbfrage += SORTIEREN + this.sortierAttribut + ABSTEIGEND;
                break;
            case "aufsteigend" :
                sqlAbfrage += SORTIEREN + this.sortierAttribut + AUFSTEIGEND;
                break;
            default :
                sqlAbfrage += LEER;
                break;
        }

        return sqlAbfrage;
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 05.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Löscht alle Leerzeichen, bis auf die, die als Wert (Ergebnis) einer
     * Abfrage stehen(*).
     * @param st StringTokenizer mit dem gesamt Ausdruck.
     * @return Gibt den vollständig geparsten Ausdruck ohne Leerzeichen(*)
     * zurück
     */
    public String loescheLeerzeichen(StringTokenizer st) {
        String naechsterAusdruck = LEER;
        boolean operatorOffen = false;
        String ergebnisOhneLeer = LEER;
        //Durchlaufe den Gesamtenausdruck und parse ihn so, dass 
        //Alle Leerzeichen verschwinden, AUSSER die, die im Ergebnis stehen 
        //sollen und somit auch in der Datenbank.
        //Beispiel: art ikel name = Schoene Schuhe -> artikelname=Schoene Schuhe
        while (st.hasMoreTokens()) {
            naechsterAusdruck = st.nextToken();
            //Durchlaufe und guck ob ein operator vorhanden ist
            //und ob ein zugehöriges trennzeichen vorhanden ist
            for (int i = 0; i <= naechsterAusdruck.length() - 1; i++) {
                //Prüfe, ob im Zeichen ein Operator ist.
                if (naechsterAusdruck.substring(i, i).equals(OPERATOR[0]) 
                        || naechsterAusdruck.substring(i, 
                                i + 1).equals(OPERATOR[1])
                        || naechsterAusdruck.substring(i, 
                                i + 1).equals(OPERATOR[2])
                        || naechsterAusdruck.substring(i, 
                                i + 1).equals(OPERATOR[3])
                        || naechsterAusdruck.substring(i, 
                                i + 1).equals(OPERATOR[4])
                        || naechsterAusdruck.substring(i, 
                                i + 1).equals(OPERATOR[5])) {
                    //Ist ein Operator vorhanden setzen wir die bool
                    //Variable "operatorOffen" auf true, das bedeutet, dass
                    //Es ein Operator gibt der durch ein ; zu schliessen ist
                    //Alle folgenden Tokens werden nun mit einem Leerzeichen
                    //versehen. -> ausdruck>=SC next next ... ;
                    operatorOffen = true;
                }
                //Ist der Operator geöffnet muss zur gleichen Zeit auch geprüft
                //Werden, ob dieser auch wieder durch ein ; geschlossen wird
                if (operatorOffen) {
                    //ist das Zeichen ein ; 
                    if (naechsterAusdruck.substring(i, 
                            i + 1).equals(TRENNZEICHEN)) {
                        //Dann wird der Ausdruck geschlossen.
                        operatorOffen = false;
                    }
                }
            }
            //Ist ein operator offen also ohne trennzeichen im Ausdruck
            //So muss überprüft werden ob die nächsten Tokens mit einem 
            //Leerzeichen versehen werden.
            if (operatorOffen) {
                //Gibt es noch weitere Tokens?
                if (st.hasMoreElements()) {
                    //Sollte der nächste Ausdruck mit einem Operator oder 
                    //Einem ; Enden, so müssen wir kein Leerzeichen einfügen
                    if (naechsterAusdruck.endsWith(OPERATOR[0]) 
                            || naechsterAusdruck.endsWith(OPERATOR[1])
                            || naechsterAusdruck.endsWith(OPERATOR[2])
                            || naechsterAusdruck.endsWith(OPERATOR[3])
                            || naechsterAusdruck.endsWith(OPERATOR[4])
                            || naechsterAusdruck.endsWith(OPERATOR[5])
                            || naechsterAusdruck.endsWith(TRENNZEICHEN)) {
                        //Wenn ein Leerzeichen sich am Ende des Ausdrucks 
                        //befindet, so muss dieser entfernt werden.
                        if (ergebnisOhneLeer.endsWith(" ")) {
                            //Der Ausdruck wird ersetzt durch sich selbst als
                            //Substring jedoch ohne das letzte Element.
                            ergebnisOhneLeer = ergebnisOhneLeer
                                    .substring(0, 
                                            ergebnisOhneLeer.length());
                        }
                        //So wird anschliessend der nächste Ausdruck an den 
                        //Gesamten Ausdruck gehängt. BSP: ausdruck=nächsterAusdr
                        ergebnisOhneLeer += naechsterAusdruck;
                    //Sollte sich am nächsten Ausdruck keines der oben 
                    //genannten Zeichen befinden, so wird ein Leerzeichen
                    //angehängt BSP: ausdruck=nächsterausdrLEER
                    } else {
                        ergebnisOhneLeer += naechsterAusdruck + " ";
                    }

                    //Sind am Ende angekommen und 
                    //es gibt keine weitere tokens mehr
                    //st.hasMoreTokens()
                } else {
                    //Zum Schluss wird der ausdruck nur noch angehängt
                    ergebnisOhneLeer += naechsterAusdruck;
                }
                //Wenn kein Operator offen ist d.h zu jedem 
                //Operator gibt es ein ;
                //operatorOffen = false
            } else {
                //Falls sich am Ende des gesamt Ausdruck ein Leerzeichen
                //befindet
                if (ergebnisOhneLeer.endsWith(" ")) {
                    //So wird dieses entfernt wie oben bereits beschrieben.
                    ergebnisOhneLeer = ergebnisOhneLeer
                                    .substring(0, ergebnisOhneLeer
                                            .length() - 1);
                }
                //Der Ausdruck wird ohne Leerzeichen angehängt.
                //BSP: ausdruck>ausdruck;nächsterausdruck
                ergebnisOhneLeer += naechsterAusdruck;
            }
            
        }
        //Gib den Gesamtausdruck zurück.
        return ergebnisOhneLeer;
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
        //Join Abfrage über Auftragskopf -> Geschäftspartner -> Anschrift
        String sqlAuftragPartnerJoin = " left join \"Geschäftspartner\" on "
                + "AUFTRAGSKOPF.\"Geschäftspartner\" = "
                + "\"Geschäftspartner\".GESCHAEFTSPARTNERID left join "
                + "ANSCHRIFT on "
                + "\"Geschäftspartner\".RECHNUNGSADRESSE_ANSCHRIFTID "
                + "= ANSCHRIFT.ANSCHRIFTID ";
        //Join Abfrage über Auftragskopf -> Status
        String sqlAuftragStatusJoin = " left join STATUS on "
                + "AUFTRAGSKOPF.STATUS = STATUS.STATUSID ";
        //Suchparameter um nach Status zu suchen
        String sqlAuftragStatusNachName = " STATUS.STATUS ";
        //Join Abfrage über Auftragskopf -> Zahlungskonditionen
        String sqlAuftragZkJoin  = " left join ZAHLUNGSKONDITION on "
                + "AUFTRAGSKOPF.ZAHLUNGSKONDITION_ZAHLUNGSKONDITIONID = "
                + "ZAHLUNGSKONDITION.ZAHLUNGSKONDITIONID ";
        //Suchparameter um nach Auftragsart zu suchen
        String sqlAuftragZkNachArt = " ZAHLUNGSKONDITION.AUFTRAGSART ";
        //Join Abfrage über Artikel -> Artikelkategorie
        String sqlArtikelKatJoin = " left join ARTIKELKATEGORIE on "
                + "ARTIKEL.KATEGORIE = ARTIKELKATEGORIE.ID ";
        //Suchparameter um nach Kategoriename zu suchen
        String sqlArtikelKategorieNachName = " ARTIKELKATEGORIE.KATEGORIENAME ";
        //Join Abfrage über Geschäftspartner -> Anschrift
        String sqlPartnerAnschriftJoin = " left join ANSCHRIFT " 
                + "ON \"Geschäftspartner\".RECHNUNGSADRESSE_ANSCHRIFTID = "
                + "ANSCHRIFT.ANSCHRIFTID ";
        //Suchparameter um nach GP Namen zu suchen
        String sqlPartnerAnschriftNachName = " ANSCHRIFT.\"NAME\" ";
        //Suchparameter um nach Vornamen zu suchen
        String sqlPartnerAnschriftNachVName = " ANSCHRIFT.\"VORNAME\" ";
        //Suchparameter um nach Email zu suchen
        String sqlPartnerAnschriftNachEmail = "  ANSCHRIFT.\"EMAIL\" ";
        String datentyp = LEER;
        
        //Prüfe, ob der Wert dem Datentyp entspricht.
        this.pruefeDatentyp(dbAttribut, dbWert, tabelle);
        
        //Hole Datentyp aus db.
        datentyp = GUIFactory.getDAO().gibDatentypVonSuchAttribut(dbAttribut, 
                tabelle);

        //Prüfe,ob es sich um ein String oder Datum handelt.
        //Prüfe,ob es sich um ein String oder Datum handelt.
        switch (datentyp) {
            case "String":
                //Hier müssen für das SQL-Statement hochkommas
                //hinzugefügt werden.
                dbWert = "'" + dbWert + "'";
                break;
            case "Date":
                //Hole ein gültiges Datumsformat in Abhängigkeit des Musters
                //Für SQL-Statement.
                dbWert = DatumParser.gibgueltigesDatumFormat(dbWert);
                //Hier müssen für das SQL-Statement hochkommas
                //hinzugefügt werden.
                dbWert = "'" + dbWert + "'";
                break;
            case "Double":
                //Bei Double werden alle , durch . ersetzt.
                dbWert = dbWert.replaceAll(",", ".");
                break;
            default:
                break;
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
        //Auftrag -> Geschäftspartner
        if ("\"GeschäftspartnerFK\"".equals(dbAttribut)
                && "Auftragskopf".equals(tabelle)) {
            //Setze das dbAttribut dem Fremdschlüssel Attribut
            dbAttribut = sqlPartnerAnschriftNachName;
            //Prüfe, ob ein joinbefehl bereits gesetzt wurde
            if (LEER.equals(this.joinBefehl)) {
                //Wenn nicht, dann setze den join befehl für die Verbindung
                //der Tabellen.
                this.joinBefehl = sqlAuftragPartnerJoin;
                //prüfe, ob der joinbefehl erweitert werden muss
            } else if (!this.joinBefehl.contains(sqlAuftragPartnerJoin)) {
                //wenn ja dann hänge den nächsten join befehl an.
                this.joinBefehl += " " + sqlAuftragPartnerJoin;
            }
            //Auftrag -> Geschäftspartner
        } else if (("GeschäftspartnerFKVNAME").equals(dbAttribut)
                && "Auftragskopf".equals(tabelle)) {
            //Setze das dbAttribut dem Fremdschlüssel Attribut
            dbAttribut = sqlPartnerAnschriftNachVName;
            //Prüfe, ob ein joinbefehl bereits gesetzt wurde
            if (LEER.equals(this.joinBefehl)) {
                //Wenn nicht, dann setze den join befehl für die Verbindung
                //der Tabellen.
                this.joinBefehl = sqlAuftragPartnerJoin;
                //prüfe, ob der joinbefehl erweitert werden muss
            } else if (!this.joinBefehl.contains(sqlAuftragPartnerJoin)) {
                //wenn ja dann hänge den nächsten join befehl an.
                this.joinBefehl += " " + sqlAuftragPartnerJoin;
            }
            //Auftrag -> Status
        } else if (("StatusFK").equals(dbAttribut)
                && "Auftragskopf".equals(tabelle)) {
            dbAttribut = sqlAuftragStatusNachName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlAuftragStatusJoin;
            } else if (!this.joinBefehl.contains(sqlAuftragStatusJoin)) {
                this.joinBefehl += " " + sqlAuftragStatusJoin;
            }
            //Auftrag -> Zahlungskondition
        } else if (("ZAHLUNGSKONDITION_"
                + "ZAHLUNGSKONDITIONIDFK").equals(dbAttribut)
                && "Auftragskopf".equals(tabelle)) {
            dbAttribut = sqlAuftragZkNachArt;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlAuftragZkJoin;
            } else if (!this.joinBefehl.contains(sqlAuftragZkJoin)) {
                this.joinBefehl += " " + sqlAuftragZkJoin;
            }
            //Geschäftspartner -> Anschrift
        } else if (("RECHNUNGSADRESSE_ANSCHRIFTIDFKNAME").equals(dbAttribut)
                && "Geschäftspartner".equals(tabelle)) {
            dbAttribut = sqlPartnerAnschriftNachName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlPartnerAnschriftJoin;
            } else if (!this.joinBefehl.contains(sqlPartnerAnschriftJoin)) {
                this.joinBefehl += " " + sqlPartnerAnschriftJoin;
            }
            //Geschäftspartner -> Anschrift
        } else if (("RECHNUNGSADRESSE_ANSCHRIFTIDFKVNAME").equals(dbAttribut)
                && "Geschäftspartner".equals(tabelle)) {
            dbAttribut = sqlPartnerAnschriftNachVName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlPartnerAnschriftJoin;
            } else if (!this.joinBefehl.contains(sqlPartnerAnschriftJoin)) {
                this.joinBefehl += " " + sqlPartnerAnschriftJoin;
            }
            //Geschäftspartner -> Anschrift
        } else if (("RECHNUNGSADRESSE_ANSCHRIFTIDFKEMAIL").equals(dbAttribut)
                && "Geschäftspartner".equals(tabelle)) {
            dbAttribut = sqlPartnerAnschriftNachEmail;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlPartnerAnschriftJoin;
            } else if (!this.joinBefehl.contains(sqlPartnerAnschriftJoin)) {
                this.joinBefehl += " " + sqlPartnerAnschriftJoin;
            }
            //Artikel -> Kategorie
        } else if (("KategorieFKNAME").equals(dbAttribut)
                && "Artikel".equals(tabelle)) {
            //Setze das dbAttribut dem Fremdschlüssel Attribut
            dbAttribut = sqlArtikelKategorieNachName;
            if (LEER.equals(this.joinBefehl)) {
                this.joinBefehl = sqlArtikelKatJoin;
            } else if (!this.joinBefehl.contains(sqlArtikelKatJoin)) {
                this.joinBefehl += " " + sqlArtikelKatJoin;
            }
        }
        //Anschliessend muss der eigentliche Attributenname
        //wieder gesetzt werden.
        dbAttribut = dbAttribut.replace("FK", "");
        //Prüfe, ob das Attribute nach dem Sortiert werden soll, 
        //Bereits gesetzt wurde. Im ersten druchlauf, wird das Attribut gesetzt
        //, da nach dem ersten Literal in der Suchabfrage sortiert wird.
        if (this.sortierAttribut.equals(LEER)) {
            this.sortierAttribut = dbAttribut;
        }
        //Gib das Statement zurück Bsp: Name = test
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
        Date datum = null;
        //Hole Datentyp aus DB
        datentyp = GUIFactory.getDAO().gibDatentypVonSuchAttribut(
                dbAttribut, tabelle);
        
        //Prüfe ob es sich um dein Datum handelt
        if ("Date".equals(datentyp)) {
            try {
                //Versuche es zu parse. Wenn das Datum nicht geparset werden 
                //Kann liegt ein Fehler im Format vor 
                //-> Der Benutzer wird informiert.
                datum = DatumParser.gibDatum(dbWert);
                //Prüfe ob gültiges Datum erkannt wurde.
                if (datum == null) {
                    throw new NullPointerException("Bitte geben Sie ein "
                            + "gültiges Datum ein!");
                }
            } catch (Exception ex) {
                throw new ApplicationException(FEHLER_TITEL, ex.getMessage());
            }
        //alle anderen Datentypen
        } else {
            //Es müssen alle . durch Komma ersetzt werden, da das System
            //Sonst den Typ Double nicht erkennt...
            if ("Double".equals(datentyp)) {
                dbWert = dbWert.replaceAll("\\.", ",");
            }
            
            //Platzhalter sind nur bei Texteingaben zu verweden
            //Prüfe, ob im Wert Platzhalter vorhanden sind
            if (dbWert.contains(new String(new char[] {PLATZHALTEREINZEICHEN})) 
                            || dbWert.contains(new String(
                                new char[] {PLATZHALTERMULTIPLEZEICHEN}))) {
                //Prüfe, ob der Datentyp nicht String ist, wenn ja 
                //Muss ein fehler ausgegeben werden.
                if (!datentyp.equals("String")) {
                    throw new ApplicationException(FEHLER_TITEL, "Die "
                            + "Platzhalter sind nur bei "
                            + "Text Eingaben zu verwenden!");
                }
            }
            
            //Ermittle den Typ des übergebenen wertes.
            Object objektTyp = gibTypAusString(dbWert);
            //Prüfe, ob der Typ gefunden wurde
            if (objektTyp == null) {
                throw new ApplicationException(FEHLER_TITEL, 
                        "Der Datentyp konnte nicht gefunden werden!");
            }
            //Es wird dynamisch der Datentyp ermittelt.
            String typ = objektTyp.getClass().getSimpleName();
            //Prüfe ob der eingegebene Wert dem Datentyp-Format entspricht.
            //Wichtig ist, dass int und long ok sind! Ebenso String!
            if (!typ.equals(datentyp) && (!"Integer".equals(typ) 
                    || !"Long".equals(datentyp)) && !"String".equals(datentyp)) {
                //Generiere Fehlermeldung
                switch (datentyp) {
                    //Es wurde eine natürliche Zahl erwartet
                    case "Integer":
                        typ = "Der Wert muss eine natürliche Zahl sein!";
                        break;
                    //Es wurde eine gleitkomma Zahl erwartet
                    case "Double":
                        typ = "Der Wert muss eine gleitkomma Zahl sein!";
                        break;
                    //Es wurde ein Text erwartet    
                    case "String":
                        typ = "Der Wert muss ein Text sein!";
                        break;
                    //Es wurde eine Zahl erwartet    
                    case "Long":
                        typ = "Der Wert muss eine Zahl sein!";
                        break;
                    //Es wurde ein unbestimmter Typ eingegeben    
                    default:
                        typ = "Der Typ konnte nicht ermittelt werden!";
                        break;
                }
                //Delegiere die Exception weiter.
                throw new ApplicationException(FEHLER_TITEL, 
                        "Die Ergebniseingabe: " + "" + dbWert 
                                + " ist ungültig! \n " + typ);
                
            }
        }

        return true;
    }
    
    /**
     * Ermittelt mit einem Scanner, um was für einen Typ es sich handelt,
     * der sich im String text befindet.
     * @param textWert der Zu üerprüfende String
     * @return Datentyp des Wertes im String.
     */
    public Object gibTypAusString(String textWert) {
        Scanner sc = new Scanner(textWert);
        Object ergebnis = null;
        //Handelt es sich um ein Integer Wert?
        if (sc.hasNextInt()) {
            ergebnis = sc.nextInt();
        //Handelt es sich um ein Long Wert?
        } else if (sc.hasNextLong()) {
            ergebnis = sc.nextLong();
        //Handelt es sich um ein Double Wert?
        } else if (sc.hasNextDouble()) {
            ergebnis = sc.nextDouble();
        //Handelt es sich um ein Objekt Wert?
        } else if (sc.hasNext()) {
            ergebnis = sc.next();
        //Handelt es sich um ein Text Wert?
        } else {
            ergebnis = textWert;
        }
        return ergebnis;
    }
    
}
