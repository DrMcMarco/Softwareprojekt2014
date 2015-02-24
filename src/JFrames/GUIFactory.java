package JFrames;

import DAO.DataAccessObject;
import java.awt.Component;
import java.util.ArrayList;

/**
 *
 * @author Luca
 */
/* 16.12.2014 Terrasi, Erstellung */
/* 18.02.2015 TER, getestet und freigegeben */
public class GUIFactory {

    // Variablen

    static ArrayList<Component> liste;
    
    // Erzeugung der DAO.
    private static final DataAccessObject DAO = new DataAccessObject();

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 16.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Konstruktorerzeugung
     */
    public GUIFactory() {
        liste = new ArrayList<>();// Initialisierung der ArrayList.
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 16.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der eine  Komponente übergeben und in einer Liste 
     * gespeichert wird.
     * @param component, übergabe Parameter einer Component
     */
    public void setComponent(Component component) {
        liste.add(component);// Hinzufügen einer Component.
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 16.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man die zuvor übergebene Component aufruft und erhält.
     * 
     * @return component, gibt die zu letzt angelegte Componente aus der Liste
     * 
     */
    public Component zurueckButton() {
        Component component = null;
        try {
            component = liste.get(liste.size() - 1);// letzte Komponente
            liste.remove(liste.size() - 1);// Liste wird kleiner
            
        } catch (ArrayIndexOutOfBoundsException e) {// Fehlerbehandlung
        }
        return component;// Rückgabe
    }


    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 16.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Rckgabe der DAO.
     * @return the DAO
     */
    public static DataAccessObject getDAO() {
        return DAO;
    }

}
