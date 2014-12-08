package Interfaces;

import javax.swing.JTextField;

/**
 *
 * @author Luca
 *
 * Schnittstelle für die einzelnen Masken in denen die gleichen Methoden
 * aufgerufen werden müssen
 */
public interface InterfaceViewsFunctionality {
    /*    
     Methode mit der die Eingaben aus den Eingabefeldern gelöscht werden und 
     die Eingabefelder wieder leer sind.
     */
    public void zuruecksetzen();

    /*
     Methode mit der auf vollständigkeit der Eingaben geprüft wird.
     */
    public void ueberpruefen();
    
    /*
    Methode mit der die Richtigkeit der Eingabe beim Focuslost geprüft wird.
    */
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax, 
            String fehlermelgungtitel, String fehlermeldung);
}
