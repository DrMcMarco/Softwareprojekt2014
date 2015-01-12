/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*----------------------------------------------------------*/
/* Datum Name Was                                           */
/* 09.01.15 sch angelegt                                    */
/*----------------------------------------------------------*/
/**
 *
 * @author Simon <Simon.Simon at your.org>
 */
public class DatumParser {
    
    /**
     * Platzhalter für Trennzeichen im Regex.
     */
    private static final String PLATZHALTER = "{§}";
    
    /**
     * Alle erlaubten Trennzeichen für ein Datum.
     */
    private static final String[] DATUM_TRENNZEICHEN = {"/", "-", "."};

    /**
     * Deutsches Muster Format.
     */
    private static final String DEUTSCHES_FORMAT = "dd{§}MM{§}yyyy";
    
    /**
     * Englisches Muster Format.
     */
    private static final String ENGLISCHES_FORMAT = "yyyy{§}MM{§}dd";

    /**
     * Englisches Datumsformat Regex-Muster.
     */
    private static final String ENGLISCH_RGX = "\\d{4}{§}\\d{2}{§}\\d{2}";
    
    /**
     * Deutsches Datumsformat Regex-Muster.
     */
    private static final String DEUTSCH_RGX = "\\d{2}{§}\\d{2}{§}\\d{4}.*";

    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 09.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Gibt ein gültiges Datum zurück.
     * @param eingabeDatum das zu prüfende Datum.
     * @return gültiges Datum im Date-Format.
     */
    public static Date gibDatum(String eingabeDatum) {
        Date date = null;
        String aktuellesFormat = "";
        int tag = 0;
        int monat = 0; 
        int jahr = 0;
        //Hole das Datumsformat, wenn das Datum nicht DE oder ENG ist, dann
        //wurde ein ungültiges Datum eingegeben und eine Exc wird geworfen.
        String datumFormat = gibDatumFormat(eingabeDatum);
        //Datumformat ist ungültig.
        if (datumFormat == null) {
            throw new IllegalArgumentException("Geben Sie ein Datum in einem "
                    + "gültigen Format an! Deutsch: tt MM yyyy "
                    + "Englisch: yyyy MM dd");
        }
        
        for (String trenner : DATUM_TRENNZEICHEN) {
            //Ersetze die Platzhalter im Musterformat durch die vom benutzer 
            //gesetzen.
            aktuellesFormat = datumFormat.replace(PLATZHALTER, trenner);
            
            try {
                //Versuche, dass Datum anhand des Formats und der Eingabe zu
                //Parsen.
                date = new SimpleDateFormat(
                        aktuellesFormat).parse(eingabeDatum);
            } catch (ParseException ex) {
                //Hier wird nichts weiter unternommen da mehrere Formate 
                //Überprüft werden und es zu keinem Abbruch kommen darf.
                ex.getMessage();
            }
            
            //Beende die Schleife sobald das Datum geparst werden konnte
            //->Das Trennzeichen wurde gefunden.
            if (date != null) {
                //Da die String.split Methode kein "." erkennt,
                //Muss ein \\ hinzugefügt werden -> escape-Operator.
                if (DATUM_TRENNZEICHEN[2].equals(trenner)) {
                    trenner = "\\" + trenner;
                }
                //Prüfe, um welches Datumformat es sich handelt und setze
                //dementsprechend das Datum. dd mm yyyy - yyyy mm dd
                if (datumFormat.equals(DEUTSCHES_FORMAT)) {
                    //Parse TAG von String nach int
                    tag = Integer.parseInt(eingabeDatum.split(trenner)[0]);
                    //Parse MONAT von String nach int
                    monat = Integer.parseInt(eingabeDatum.split(trenner)[1]);
                    //Parse JAHR von String nach int
                    jahr = Integer.parseInt(eingabeDatum.split(trenner)[2]);
                } else {
                    //Parse TAG von String nach int
                    tag = Integer.parseInt(eingabeDatum.split(trenner)[2]);
                    //Parse MONAT von String nach int
                    monat = Integer.parseInt(eingabeDatum.split(trenner)[1]);
                    //Parse JAHR von String nach int
                    jahr = Integer.parseInt(eingabeDatum.split(trenner)[0]);
                }
                //Prüfe auf Plausiblität.
                checkDatum(tag, monat, jahr);
                break;
            }
        }

        return date;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 09.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Gibt das Datumsformat zurück.
     * @param eingabeDatum Ein Datum.
     * @return Datumformat (DE, ENG)
     */
    private static String gibDatumFormat(String eingabeDatum) {
        String format = null;
        String engMuster = "";
        String deutschMuster = "";
        //Iteriere über alle Trennzeichen
        for (String trenner : DATUM_TRENNZEICHEN) {
            //ersetze den Platzhalter im Regex durch den aktuellen
            engMuster = ENGLISCH_RGX.replace(PLATZHALTER, trenner);
            deutschMuster = DEUTSCH_RGX.replace(PLATZHALTER, trenner);
            //Prüfe, ob das Eingabedatum dem englischen Datumsmuster entspricht
            if (eingabeDatum.matches(engMuster)) {
                //Setze das Format und brich die Schleife ab.
                format = ENGLISCHES_FORMAT;
                break;
            }
            //Prüfe, ob das Eingabedatum dem deutschen Datumsmuster entspricht
            if (eingabeDatum.matches(deutschMuster)) {
                //Setze das Format und brich die Schleife ab.
                format = DEUTSCHES_FORMAT;
                break;
            }
        }
        return format;
    }

    /**
     * Prüft das gegebene Datum auf Plausiblität.
     * @param tag Der Tag des Datums.
     * @param monat der Monat des Datums.
     * @param jahr Das Jahr des Datums.
     */
    private static void checkDatum(int tag, int monat, int jahr) {

        if (tag < 0) {
            throw new IllegalArgumentException("Tag darf nicht negativ sein.");
        }
        if (monat < 0) {
            throw new IllegalArgumentException("Monat darf nicht "
                    + "negativ sein.");
        }
        if (jahr < 1000) {
            throw new IllegalArgumentException("Jahreszahlen unter 1000 "
                    + "sind nicht zugelassen.");
        }
        if (monat > 12) {
            throw new IllegalArgumentException("Monat darf nicht grösser "
                    + "als 12 sein.");
        }
        if (tag > 31) {
            throw new IllegalArgumentException("Tag darf nicht "
                    + "grösser als 31 sein.");
        }
        if (tag < 29) {
            return;
        }
        if (tag == 29) {
            //Wenn nicht Februar
            if (monat != 2) {
                return;
            }
            if (jahr % 4 == 0 & (jahr % 100 != 0 | jahr % 1000 == 0)) {
                return;
            } else {
                throw new IllegalArgumentException("29.02." + jahr 
                        + " ist kein reales Datum.");
            }
        }
        
        if (tag == 30) {
            //Wenn nicht Februar
            if (monat != 2) {
                return;
            } else {
                throw new IllegalArgumentException("30.02. ist "
                        + "kein reales Datum.");
            }
        }
        
        if (monat == 2 | monat == 4 | monat == 6 | monat == 9 | monat == 11) {
            throw new IllegalArgumentException("31." + monat 
                    + ". ist kein reales Datum.");
        }
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 09.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Parst das gegebene Datum in ein Komformes Datum um. (String)
     * Es werden nur die Trennzeichen ersetzt.
     * @param eingabeDatum Datum Bsp:01-02-2014 01/02/2014 2000/01/01 2000/01.01
     * @return Gültiges Datum Bsp: 01.02.2014 01.02.2014 2000-01-01 2000-01-01
     */
    public static String gibgueltigesDatumFormat(String eingabeDatum) {
        String datum = eingabeDatum;
        String datumformat = gibDatumFormat(eingabeDatum);
        String trennsymbol = "";
        
        //Iteriere über alle erlaubten Trennzeichen
        for (String trenner : DATUM_TRENNZEICHEN) {
            //Prüfe ob trennzeichen enthalten ist
            if (datum.contains(trenner)) {
                //Merke das Zeichen
                trennsymbol = trenner;
                //Prüfe ob es sich um "." handelt
                if (DATUM_TRENNZEICHEN[2].equals(trenner)) {
                    //Setze escape operator
                    trennsymbol = "\\" + trennsymbol;
                }
                //Prüfe ob es sich um ein Deutsches Format handelt
                if (datumformat.equals(DEUTSCHES_FORMAT)) {
                    //Ersetze alle zeichen durch .
                    datum = datum.replaceAll(trennsymbol, 
                            DATUM_TRENNZEICHEN[2]);
                } else {
                    //Bei englischem Muster ersetze alle Zeichen durch -
                    datum = datum.replaceAll(trennsymbol, 
                            DATUM_TRENNZEICHEN[1]);
                }
            }
        }
        
        return datum;
    }
    
}
