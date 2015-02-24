/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import GUI_Internalframes.ArtikelAEndernEinstieg;
import GUI_Internalframes.ArtikelAnlegen;
import GUI_Internalframes.AuftragskopfAendern;
import GUI_Internalframes.AuftragskopfAnlegen;
import GUI_Internalframes.AuftragspositionAendern;
import GUI_Internalframes.AuftragspositionAnzeigen;
import GUI_Internalframes.GeschaeftspartnerAEndernEinstieg;
import GUI_Internalframes.GeschaeftspartnerAnlegen;
import GUI_Internalframes.ZahlungskonditionAnlegen;
import GUI_Internalframes.ZahlungskonditionenAEndernEinstieg;
import Hauptmenue.Hauptmenue_User;
import java.awt.Component;
import javax.swing.JInternalFrame;

/**
 *
 * @author Luca Terrasi
 * 
 * Schnittstelle für die Hauptfenster.
 * 
 * 05.01.2015 Terrasi, Erstellung und Dokumentation.
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
    public void setCenterJIF(JInternalFrame jif);
    
    
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
    public void setComponent(JInternalFrame component);
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine gespeicherte Component zurückerhält.
     * @return, eine gespeicherte Component.
     */
    public JInternalFrame getComponent();
    
    
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
     * @param component Component die übergeben wird.
     */
    public void setFrame(Component component);
    
    /**
     * Gibt die letzte Anzeige.
     * @return InternalFrame mit der letzten Anzeige.
     */
    public JInternalFrame gibLetzteAnzeige();
    
    /**
     * Ruft die Suche auf.
     * @param comp aufrufende Komponente
     */
    public void rufeSuche(JInternalFrame comp);
    
    /**
     * Gibt den Titel zurück.
     * @return Text mit Titel
     */
    public String gibTitel();
    
    /**
     * Gibt das anlegen Fenster.
     * @return Artikelanlegen
     */
    public ArtikelAnlegen gibArtikelAnlegenFenster();
    
    /**
     * Gibt das anlegen Fenster.
     * @return Auftragskopfanlegen
     */
    public AuftragskopfAnlegen gibAuftragskopfanlegenFenster();
    
    /**
     * Gibt das anlegen Fenster.
     * @return Geschäftspartner anlegen
     */
    public GeschaeftspartnerAnlegen gibGeschaeftspartneranlegenFenster();
    
    /**
     * Gibt das anlegen Fenster.
     * @return Artikeländerneinstieg
     */
    public ArtikelAEndernEinstieg gibArtikelaendernEinstieg();
    
    /**
     * Gibt das anlegen Fenster.
     * @return Geschäftspartner aendern Einstieg.
     */
    public GeschaeftspartnerAEndernEinstieg gibGeschaeftspartnerAendernEinstieg();
    
    /**
     * Gibt das anlegen Fenster.
     * @return ZkändernEinstieg
     */
    public ZahlungskonditionenAEndernEinstieg gibZkAendernEinstieg();
    
    /**
     * Gibt das anlegen Fenster.
     * @return Auftragskopfänderneinstieg
     */
    public AuftragskopfAendern gibAkAendernEinstieg();
    
    /**
     * Gibt das anlegen Fenster.
     * @return auftragspositionändern
     */
    public AuftragspositionAendern gibPositionAendernEinstieg();
    
    /**
     * Gibt das anlegen Fenster.
     * @return zkanlegen
     */
    public ZahlungskonditionAnlegen gibZkAnlegen();
    
    /**
     * Gibt das anlegen Fenster.
     * @return auftragspositionanzeigen
     */
    public AuftragspositionAnzeigen gibApAnzeigen();
    
    /**
     * Gibt das jeweilige Menü.
     * @return Hauptmenü
     */
    public JInternalFrame gibMenu();
}
