package Interfaces;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTextField;
import DAO.*;

/**
 *
 * @author Luca Terrasi 
 *
 * Schnittstelle für die einzelnen Masken in denen die gleichen Methoden
 * aufgerufen werden müssen
 * 
 * 10.12.2014 Terrasi, Erstellung und Dokumentation
 */
public interface InterfaceViewsFunctionality {
   
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt*/
    /*----------------------------------------------------------*/
    /*    
     * Methode mit der die Eingaben aus den Eingabefeldern gelöscht werden und 
     * die Eingabefelder wieder leer sind.
     */
    public void zuruecksetzen();

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt*/
    /*----------------------------------------------------------*/
    /*
     * Methode mit der auf vollständigkeit der Eingaben geprüft wird.
     */
    public void ueberpruefen();

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der die Richtigkeit der Eingabe beim Focuslost geprüft wird.
     *
     * @param textfield, das zu übergeben JTextfield, indem der Focusgesetzt
     * ist.
     * @param syntax, String mit dem eine Eingabe auf das richtige Format hin
     * geprüft wird.
     * @param fehlermelgungtitel, Srting der den Titel der Fehlmeldung enthält.
     * @param fehlermeldung, String der die Fehlmeldung enthält.
     */
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax,
            String fehlermelgungtitel, String fehlermeldung);

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt*/
    /*----------------------------------------------------------*/
    /**
     * Methode mir der die Eingabefelder ohne einer entsprechenden Eingabe,
     * farblich markiert werden und eine Nachricht aufruft, die den Benutzer
     * darauf hinweist alle Eingaben zu tätigen.
     *
     * @param list, Arraylist in der die Components die keine Eingaben erhalten
     * haben, gespeichert sind.
     * @param fehlermelgungtitel, Srting der den Titel der Fehlmeldung enthält.
     * @param fehlermeldung, String der die Fehlmeldung enthält.
     * @param farbe, Color in der der Hintergrund der Components markiert werden
     * soll
     */
    public void fehlEingabenMarkierung(ArrayList<Component> list,
            String fehlermelgungtitel, String fehlermeldung, Color farbe);

  
}
