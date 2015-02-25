package Interfaces;

import javax.swing.JInternalFrame;

/**
 *
 * @author Luca Terrasi
 */
/* 10.12.2014 Terrasi, Erstellung. */
/* 18.02.2015 TER, getestet und freigegeben */
public interface SchnittstelleJTreeFunktionen {

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 16.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode openJtreeNode die für eine Baumstruktur verwendet
     * werden soll.
     *
     * @param node, Stringvariable
     *
     */
    public void oeffneJtreeZweige(String node);

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 16.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode setzeComponenteSichtbar die verwendet werden soll um
 einzelne Frames sichtbar zu machen.
     *
     * @param frame ein Fenster.
     */
    public void setzeComponenteSichtbar(JInternalFrame frame);
}
