/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.Component;

/**
 *
 * @author Luca Terrasi
 * 
 * Schnittstelle für die Hauptfenster.
 * 
 * 08.01.2015 Erstellung und Dokumentation.
 */
public interface InterfaceMainView {
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt */
    /*----------------------------------------------------------*/
    /**
     * Methode mit das Zentrum eines Frames ausgerehnet werden soll
     * und dort dann die aufzurufenden Masken platziert und sichtbar
     * darstellt.
     * @param jif , Component die im plaziert und dargestellt werden soll.
     */
    public void setCenterJIF(Component jif);
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt */
    /*----------------------------------------------------------*/
    /**
     * Methode mit das Zentrum eines Frames ausgerehnet werden soll
     * und dort dann die aufzurufenden Masken platziert und sichtbar
     * darstellt.
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
}
