/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import GUI_Internalframes.ArtikelAnlegen;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author Luca Terrasi
 * 
 * Schnittstelle für die Hauptfenster.
 * 
 * 05.01.2015 Erstellung und Dokumentation.
 */
public interface InterfaceMainView {
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt */
    /*----------------------------------------------------------*/
    /**
     * Methode mit dem das Zentrum eines Frames ausgerechnet werden soll
     * und dort dann die aufzurufenden Maske platziert und sichtbar
     * darstellt wird..
     * @param jif , Component die im plaziert und dargestellt werden soll.
     */
    public void setCenterJIF(Component jif);
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine Component zum zwischenabspeichern
     * übergibt.
     * @param component  , Component die gesepiechert werden soll
     * um sie gegebenenfalls im laufe der Durchführung wiederverwenden
     * zu können.
     */
    public void setComponent(Component component);
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine gespeicherte Component zurückerhält.
     * @return, eine gespeicherte Component.
     */
    public Component getComponent();
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt */
    /*----------------------------------------------------------*/
    /**
     * Methode die einen String  der Statuszeile eines Hauptfenster übergibt.
     *@param status , der anzuzeigende String 
     */ 
    public void setStatusMeldung(String status);
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der ein Component übergeben wird.
     * @param component, Component die übergeben wird.
     */
    public void setFrame(Component component);
    
    /**
     * 
     * @return 
     */
    public JInternalFrame gibLetzteAnzeige();
    
    /**
     * 
     * @param comp 
     */
    public void rufeSuche(JInternalFrame comp);
    
    public String gibTitel();
    
    public ArtikelAnlegen gibArtikelAnlegenFenster();
    
}
