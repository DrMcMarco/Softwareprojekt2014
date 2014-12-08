/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author Tahir
 */
public class Artikel {

    private int artikelnummer;
    private String artikelname;
    private String artikelbeschreibung;
    private String kategorie;
    private String einzelwert;
    private String bestellwert;
    private String mwst;
    private String bestandsmengeFREI;
    private String bestandsmengeRES;
    private String bestandsmengeVERKAUFT;
    private String bestandsmengeZULAUF;

    public Artikel(int artikelnummer, String artikelname, String artikelbeschreibung, String kategorie, String einzelwert, String bestellwert,
            String mwst, String bestandsmengeFrei, String bestandsmengeRES, String bestandsmengeVERKAUFT, String bestandsmengeZULAUF) {
        this.artikelnummer = artikelnummer;
        this.artikelname = artikelname;
        this.artikelbeschreibung = artikelbeschreibung;
        this.kategorie = kategorie;
        this.einzelwert = einzelwert;
        this.bestellwert = bestellwert;
        this.mwst = mwst;
        this.bestandsmengeFREI = bestandsmengeFrei;
        this.bestandsmengeRES = bestandsmengeRES;
        this.bestandsmengeVERKAUFT = bestandsmengeVERKAUFT;
        this.bestandsmengeZULAUF = bestandsmengeZULAUF;
    }
    
    @Override
    public String toString() {
        return "Artikel: \n" 
                + "Artikelnummer:               " + this.artikelnummer + "\n"
                + "Artikelname:                 " + this.artikelname + "\n"
                + "Artikelbeschreibung:         " + this.artikelbeschreibung + "\n"
                + "Kategorie:                   " + this.kategorie + "\n"
                + "Einzelwert:                  " + this.einzelwert + "\n"
                + "Bestellwert:                 " + this.bestellwert+ "\n"
                + "MwST:                        " + this.mwst + "\n"
                + "Bestandsmenge FREI:          " + this.bestandsmengeFREI + "\n"
                + "Bestandsmenge RESERVIERT:    " + this.bestandsmengeRES + "\n"
                + "Bestandsmenge VERKAUFT:      " + this.bestandsmengeVERKAUFT + "\n"
                + "Bestandsmenge ZULAUF:        " + this.bestandsmengeZULAUF + "\n";
    }

}
