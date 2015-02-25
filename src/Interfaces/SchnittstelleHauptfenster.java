
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
import java.awt.Component;
import javax.swing.JInternalFrame;

/**
 *
 * @author Luca Terrasi
 * 
 * Schnittstelle für die Hauptfenster.
 */
/* 05.01.2015 Terrasi, Erstellung. */
/* 18.02.2015 TER, getestet und freigegeben */

public interface SchnittstelleHauptfenster {
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit dem das Zentrum eines Frames ausgerechnet werden soll
     * und dort dann die aufzurufenden Maske platziert und sichtbar
     * darstellt wird..
     * @param jif , Component die im plaziert und dargestellt werden soll.
     */
    public void setzeCenterJIF(JInternalFrame jif);
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine Component zum zwischenabspeichern
     * übergibt.
     * @param component  , Component die gesepiechert werden soll
     * um sie gegebenenfalls im laufe der Durchführung wiederverwenden
     * zu können.
     */
    public void setzeComponent(JInternalFrame component);
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine gespeicherte Component zurückerhält.
     * @return, eine gespeicherte Component.
     */
    public JInternalFrame getComponent();
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode die einen String  der Statuszeile eines Hauptfenster übergibt.
     *@param status , der anzuzeigende String 
     */ 
    public void setzeStatusMeldung(String status);
    
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der ein Component übergeben wird.
     * @param component Component die übergeben wird.
     */
    public void setzeFrame(Component component);
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt die letzte Anzeige.
     * @return InternalFrame mit der letzten Anzeige.
     */
    public JInternalFrame gibLetzteAnzeige();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Ruft die Suche auf.
     * @param comp aufrufende Komponente
     */
    public void rufeSuche(JInternalFrame comp);
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt den Titel zurück.
     * @return Text mit Titel
     */
    public String gibTitel();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return Artikelanlegen
     */
    public ArtikelAnlegen gibArtikelAnlegenFenster();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return Auftragskopfanlegen
     */
    public AuftragskopfAnlegen gibAuftragskopfanlegenFenster();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return Geschäftspartner anlegen
     */
    public GeschaeftspartnerAnlegen gibGeschaeftspartneranlegenFenster();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return Artikeländerneinstieg
     */
    public ArtikelAEndernEinstieg gibArtikelaendernEinstieg();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return Geschäftspartner aendern Einstieg.
     */
    public GeschaeftspartnerAEndernEinstieg gibGeschaeftspartnerAendernEinstieg();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return ZkändernEinstieg
     */
    public ZahlungskonditionenAEndernEinstieg gibZkAendernEinstieg();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return Auftragskopfänderneinstieg
     */
    public AuftragskopfAendern gibAkAendernEinstieg();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return auftragspositionändern
     */
    public AuftragspositionAendern gibPositionAendernEinstieg();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return zkanlegen
     */
    public ZahlungskonditionAnlegen gibZkAnlegen();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das anlegen Fenster.
     * @return auftragspositionanzeigen
     */
    public AuftragspositionAnzeigen gibApAnzeigen();
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt das jeweilige Menü.
     * @return Hauptmenü
     */
    public JInternalFrame gibMenu();
}
