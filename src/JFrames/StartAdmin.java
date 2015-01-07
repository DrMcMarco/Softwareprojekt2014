package JFrames;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import GUI_Internalframes.*;
import Suche.*;
import UserHauptmenue.Hauptmenue_User;
import AdminHauptmenue.Hauptmenue_Admin;
import DAO.DataAccessObject;
import java.awt.Component;
import java.awt.Dimension;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import DAO.ApplicationException;
import DAO.DataAccessObject;
import DAO.Parser;
import DTO.Artikel;
import DTO.Auftragskopf;
import DTO.Kunde;
import DTO.Sofortauftragskopf;
import DTO.Status;
import DTO.Zahlungskondition;
import GUI_Internalframes.*;
import Interfaces.InterfaceViewsFunctionality;
import UserHauptmenue.Hauptmenue_User;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.PersistenceException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Luca Terrasi
 *
 *
 * Hauptfenster des Admins. Hier befindet sich die Navigation der Software mit
 * der die einzelnen Masken aufgerufen werden können mit denen der Admin seine
 * Daten anlegen,ändern oder sich anzeigen lassen kann. er kann desweiteren
 * weitere Systemuser anlegen,ändern oder auch löschen.
 *
 *
 * 10.12.2014 Terrasi,Erstellung 
 * 16.12.2014 Terrasi, Überarbeitung und Zuweisung der Navigationsfunktion 
 * 02.01.2015 Terrasi, Überarbeitung der Navigationsfünktion 
 * 06.01.2015 Terrasi,Statuszeile implementiert
 */
public class StartAdmin extends javax.swing.JFrame {

    /**
     * Definition der Attribute
     */
    GUIFactory factory;
    DataAccessObject dao;
    // Erzeugung von variablen für die einzelnen Masken.
    public Hauptmenue_Admin hauptmenueadmin;

    public AuftragskopfAnlegen auftragskopfanlegen;
    public AuftragskopfAendern auftragskopfaendern;

    public AuftragspositionAendern auftragsspositionaender;
    public AuftragspositionAnzeigen auftragsspositionanzeigen;

    public ArtikelAnlegen artikelanlegen;
    public ArtikelAEndernEinstieg artikelaendern;

    public GeschaeftspartnerAnlegen geschaeftspartneranlegen;
    public GeschaeftspartnerAEndernEinstieg geschaeftspartneraendern;

    public ZahlungskonditionAnlegen zahlungskonditionanlegen;
    public ZahlungskonditionenAEndernEinstieg zahlungskonditionaendern;

    public User_anlegen useranlegen;
    public User_andernEinstieg useraendern;

    //Hilfsvariablen
    Dimension desktopSize;//Speichervariable für die Größe der DesktopPane.
    Dimension jInternalFrameSize;//Speichervariable für die Größe des InternalFrames.
    Component c;// Speichervariable für Components.

    //Stringvariablen für die einzelnen Meldungen die ausgegeben werden können.
    private final String Beenden_Meldung = "Wollen sie wirklich das Programm beenden und sich abmelden?";
    private final String Beenden_Meldung_Typ = "Programm beenden";

    /**
     * Creates new form Start
     */
    public StartAdmin() {
        initComponents();

        // Try-Block
        try {
            factory = new GUIFactory();// Erzeugung eines Guifactoryobjektes.
            dao = new DataAccessObject();// Erzeugung eines DAO-Objektes.
        } catch (PersistenceException e) {// Fehlerbehandlung falls bei der 
            // Erzeugung entwas nicht funktioniert hat.
            System.out.println(e.getMessage());// Fehlerausgabe.
        }

        c = null; // Initialisierung der Hilfsvariable für die Components.

        //Initialisierung der einzelnen Masken.
        hauptmenueadmin = new Hauptmenue_Admin(factory, this);
        hauptmenueadmin.setName("HauptmenüAdmin");

        auftragskopfanlegen = new AuftragskopfAnlegen(factory);
        auftragskopfaendern = new AuftragskopfAendern(factory);
        auftragsspositionaender = new AuftragspositionAendern(factory);
        auftragsspositionanzeigen = new AuftragspositionAnzeigen(factory);
        artikelanlegen = new ArtikelAnlegen(factory);
        artikelaendern = new ArtikelAEndernEinstieg(factory, artikelanlegen);
        geschaeftspartneranlegen = new GeschaeftspartnerAnlegen(factory);
        geschaeftspartneraendern = new GeschaeftspartnerAEndernEinstieg(factory, geschaeftspartneranlegen);
        zahlungskonditionanlegen = new ZahlungskonditionAnlegen(factory);
        zahlungskonditionaendern = new ZahlungskonditionenAEndernEinstieg(factory, zahlungskonditionanlegen);
        useranlegen = new User_anlegen(factory);
        useraendern = new User_andernEinstieg(factory);

        // Aufruf der setCenterJIF-Methode
        setCenterJIF(hauptmenueadmin);
        
        // Zuweisung der Masken an die Hauptansicht
        desktopPane.add(hauptmenueadmin);
        desktopPane.add(auftragskopfanlegen);
        desktopPane.add(auftragskopfaendern);
        desktopPane.add(auftragsspositionanzeigen);
        desktopPane.add(auftragsspositionaender);
        desktopPane.add(artikelanlegen);
        desktopPane.add(artikelaendern);
        desktopPane.add(geschaeftspartneranlegen);
        desktopPane.add(geschaeftspartneraendern);
        desktopPane.add(zahlungskonditionanlegen);
        desktopPane.add(zahlungskonditionaendern);
        desktopPane.add(useranlegen);
        desktopPane.add(useraendern);

        //Frames werden nicht sichtbar dargestellt. 
        auftragskopfaendern.setVisible(false);
        auftragskopfanlegen.setVisible(false);
        auftragsspositionaender.setVisible(false);
        auftragsspositionanzeigen.setVisible(false);
        artikelanlegen.setVisible(false);
        artikelaendern.setVisible(false);
        geschaeftspartneranlegen.setVisible(false);
        geschaeftspartneraendern.setVisible(false);
        zahlungskonditionanlegen.setVisible(false);
        zahlungskonditionaendern.setVisible(false);
        useranlegen.setVisible(false);
        useraendern.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        statusMeldung_jTextField = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        jM_Navigation = new javax.swing.JMenu();
        jM_AuftragVerwalten = new javax.swing.JMenu();
        jM_Auftragskopf = new javax.swing.JMenu();
        jMI_AuftragskopfAnlegen = new javax.swing.JMenuItem();
        jMI_AuftragkopfAEndern = new javax.swing.JMenuItem();
        jMI_AuftragskopfAnzeigen = new javax.swing.JMenuItem();
        jM_Auftragspos = new javax.swing.JMenu();
        jMI_AuftragsposAEndern = new javax.swing.JMenuItem();
        jMI_AuftragsposAnzeigen = new javax.swing.JMenuItem();
        jM_ArtikelVerwalten = new javax.swing.JMenu();
        jMI_ArtikelAnlegen = new javax.swing.JMenuItem();
        jMI_ArtikelAEndern = new javax.swing.JMenuItem();
        jMI_ArtikelAnzeigen = new javax.swing.JMenuItem();
        jM_GPVerwalten = new javax.swing.JMenu();
        jMI_GPAnlegen = new javax.swing.JMenuItem();
        jMI_GPAEndern = new javax.swing.JMenuItem();
        jMI_GPAnzeigen = new javax.swing.JMenuItem();
        jM_ZKVerwalten = new javax.swing.JMenu();
        jMI_ZKAnlegen = new javax.swing.JMenuItem();
        jMI_ZKAEndern = new javax.swing.JMenuItem();
        jMI_ZKAnzeigen = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMI_BenutzerAnlegen = new javax.swing.JMenuItem();
        jMI_BenutzerÄndern = new javax.swing.JMenuItem();
        jMI_BenutzerAnzeigen = new javax.swing.JMenuItem();
        jM_Logout = new javax.swing.JMenu();
        jMI_Logout = new javax.swing.JMenuItem();
        jM_Hilfe = new javax.swing.JMenu();
        jMI_Benutzerhandbuch = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("HauptmenüAdmin");
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        statusMeldung_jTextField.setEditable(false);
        statusMeldung_jTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        desktopPane.add(statusMeldung_jTextField);
        statusMeldung_jTextField.setBounds(0, 340, 786, 30);
        desktopPane.setLayer(statusMeldung_jTextField, javax.swing.JLayeredPane.PALETTE_LAYER);

        jM_Navigation.setMnemonic('n');
        jM_Navigation.setText("Navigation");

        jM_AuftragVerwalten.setMnemonic('a');
        jM_AuftragVerwalten.setText("Auftrag Verwalten");
        jM_AuftragVerwalten.setActionCommand("JMenu1");

        jM_Auftragskopf.setText("Auftragskopf");

        jMI_AuftragskopfAnlegen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragskopfAnlegen.setText("Anlegen");
        jMI_AuftragskopfAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragskopfAnlegenActionPerformed(evt);
            }
        });
        jM_Auftragskopf.add(jMI_AuftragskopfAnlegen);

        jMI_AuftragkopfAEndern.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragkopfAEndern.setText("Ändern");
        jMI_AuftragkopfAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragkopfAEndernActionPerformed(evt);
            }
        });
        jM_Auftragskopf.add(jMI_AuftragkopfAEndern);

        jMI_AuftragskopfAnzeigen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragskopfAnzeigen.setText("Anzeigen");
        jMI_AuftragskopfAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragskopfAnzeigenActionPerformed(evt);
            }
        });
        jM_Auftragskopf.add(jMI_AuftragskopfAnzeigen);

        jM_AuftragVerwalten.add(jM_Auftragskopf);

        jM_Auftragspos.setText("Auftragsposition");

        jMI_AuftragsposAEndern.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragsposAEndern.setText("Ändern");
        jMI_AuftragsposAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragsposAEndernActionPerformed(evt);
            }
        });
        jM_Auftragspos.add(jMI_AuftragsposAEndern);

        jMI_AuftragsposAnzeigen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragsposAnzeigen.setText("Anzeigen");
        jMI_AuftragsposAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragsposAnzeigenActionPerformed(evt);
            }
        });
        jM_Auftragspos.add(jMI_AuftragsposAnzeigen);

        jM_AuftragVerwalten.add(jM_Auftragspos);

        jM_Navigation.add(jM_AuftragVerwalten);

        jM_ArtikelVerwalten.setMnemonic('a');
        jM_ArtikelVerwalten.setText("Artikel Verwalten");
        jM_ArtikelVerwalten.setActionCommand("JMenu1");

        jMI_ArtikelAnlegen.setText("Anlegen");
        jMI_ArtikelAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ArtikelAnlegenActionPerformed(evt);
            }
        });
        jM_ArtikelVerwalten.add(jMI_ArtikelAnlegen);

        jMI_ArtikelAEndern.setText("Ändern");
        jMI_ArtikelAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ArtikelAEndernActionPerformed(evt);
            }
        });
        jM_ArtikelVerwalten.add(jMI_ArtikelAEndern);

        jMI_ArtikelAnzeigen.setText("Anzeigen");
        jMI_ArtikelAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ArtikelAnzeigenActionPerformed(evt);
            }
        });
        jM_ArtikelVerwalten.add(jMI_ArtikelAnzeigen);

        jM_Navigation.add(jM_ArtikelVerwalten);

        jM_GPVerwalten.setText("Geschäftspartner Verwalten");
        jM_GPVerwalten.setActionCommand("JMenu1");

        jMI_GPAnlegen.setText("Anlegen");
        jMI_GPAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_GPAnlegenActionPerformed(evt);
            }
        });
        jM_GPVerwalten.add(jMI_GPAnlegen);

        jMI_GPAEndern.setText("Ändern");
        jMI_GPAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_GPAEndernActionPerformed(evt);
            }
        });
        jM_GPVerwalten.add(jMI_GPAEndern);

        jMI_GPAnzeigen.setText("Anzeigen");
        jMI_GPAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_GPAnzeigenActionPerformed(evt);
            }
        });
        jM_GPVerwalten.add(jMI_GPAnzeigen);

        jM_Navigation.add(jM_GPVerwalten);

        jM_ZKVerwalten.setText("Zahlungskonditionen Verwalten");
        jM_ZKVerwalten.setActionCommand("JMenu1");

        jMI_ZKAnlegen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMI_ZKAnlegen.setText("Anlegen");
        jMI_ZKAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ZKAnlegenActionPerformed(evt);
            }
        });
        jM_ZKVerwalten.add(jMI_ZKAnlegen);

        jMI_ZKAEndern.setText("Ändern");
        jMI_ZKAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ZKAEndernActionPerformed(evt);
            }
        });
        jM_ZKVerwalten.add(jMI_ZKAEndern);

        jMI_ZKAnzeigen.setText("Anzeigen");
        jMI_ZKAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ZKAnzeigenActionPerformed(evt);
            }
        });
        jM_ZKVerwalten.add(jMI_ZKAnzeigen);

        jM_Navigation.add(jM_ZKVerwalten);

        jMenu1.setText("Benutzer Verwalten");

        jMI_BenutzerAnlegen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMI_BenutzerAnlegen.setText("Anlegen");
        jMI_BenutzerAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_BenutzerAnlegenActionPerformed(evt);
            }
        });
        jMenu1.add(jMI_BenutzerAnlegen);

        jMI_BenutzerÄndern.setText("Ändern");
        jMI_BenutzerÄndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_BenutzerÄndernActionPerformed(evt);
            }
        });
        jMenu1.add(jMI_BenutzerÄndern);

        jMI_BenutzerAnzeigen.setText("Anzeigen");
        jMI_BenutzerAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_BenutzerAnzeigenActionPerformed(evt);
            }
        });
        jMenu1.add(jMI_BenutzerAnzeigen);

        jM_Navigation.add(jMenu1);

        menuBar.add(jM_Navigation);

        jM_Logout.setText("Logout");
        jM_Logout.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jMI_Logout.setText("Abmelden");
        jMI_Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_LogoutActionPerformed(evt);
            }
        });
        jM_Logout.add(jMI_Logout);

        menuBar.add(jM_Logout);
        jM_Logout.getAccessibleContext().setAccessibleDescription("");

        jM_Hilfe.setText("Hilfe");

        jMI_Benutzerhandbuch.setText("Benutzerhandbuch");
        jMI_Benutzerhandbuch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_BenutzerhandbuchActionPerformed(evt);
            }
        });
        jM_Hilfe.add(jMI_Benutzerhandbuch);

        menuBar.add(jM_Hilfe);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /*----------------------------------------------------------*/
    /**
     * Methode in der definiert wird was beim betätigen des Logouts in der
     * Navigation passiert
     *
     * @param evt
     */
    private void jMI_LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_LogoutActionPerformed
        // Erzeugen eine Meldung mit Abfrage
        int antwort = JOptionPane.showConfirmDialog(rootPane, Beenden_Meldung,
                Beenden_Meldung_Typ, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (antwort == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jMI_LogoutActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /*----------------------------------------------------------*/
    /**
     * Methode in der definiert wird was aufgerufen wird wenn man den
     * Hilfebutton betätigt.
     *
     * @param evt
     */
    private void jMI_BenutzerhandbuchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_BenutzerhandbuchActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Benutzerhandbuch.", "Informationen",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMI_BenutzerhandbuchActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die AuftrgaskopfAnlegen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_AuftragskopfAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragskopfAnlegenActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(auftragskopfanlegen);
        setComponent(auftragskopfanlegen);
    }//GEN-LAST:event_jMI_AuftragskopfAnlegenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die AuftrgaskopfAendern Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_AuftragkopfAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragkopfAEndernActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(auftragskopfaendern);
        setComponent(auftragskopfaendern);
    }//GEN-LAST:event_jMI_AuftragkopfAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /*----------------------------------------------------------*/
    /**
     * Action die ausgeführt wird, wenn das Programm geschlossen werden soll,
     * indem auf den X-Button geklickt wird. Es erscheint eine entsprechene
     * Meldung, die der User bestätogen muss um das Program zu beenden.
     *
     * @param evt
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // Erzeugen eine Meldung mit Abfrage
        int antwort = JOptionPane.showConfirmDialog(rootPane, Beenden_Meldung,
                Beenden_Meldung_Typ, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (antwort == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Auftrgaspositionändern Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_AuftragsposAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragsposAEndernActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(auftragsspositionaender);
        setComponent(auftragsspositionaender);
    }//GEN-LAST:event_jMI_AuftragsposAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Auftrgaspositionanzeigen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_AuftragsposAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragsposAnzeigenActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(auftragsspositionanzeigen);
        setComponent(auftragsspositionanzeigen);
    }//GEN-LAST:event_jMI_AuftragsposAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Artikelanlegen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_ArtikelAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ArtikelAnlegenActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(artikelanlegen);
        setComponent(artikelanlegen);
    }//GEN-LAST:event_jMI_ArtikelAnlegenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Artikeländern Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_ArtikelAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ArtikelAEndernActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(artikelaendern);
        setComponent(artikelaendern);
    }//GEN-LAST:event_jMI_ArtikelAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Artikelanzeigen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_ArtikelAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ArtikelAnzeigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMI_ArtikelAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die AuftrgaskopfAnzeigen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_AuftragskopfAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragskopfAnzeigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMI_AuftragskopfAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Geschäftspartneranlegen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_GPAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_GPAnlegenActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(geschaeftspartneranlegen);
        setComponent(geschaeftspartneranlegen);
    }//GEN-LAST:event_jMI_GPAnlegenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Geschäftspartnerändern Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_GPAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_GPAEndernActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(geschaeftspartneraendern);
        setComponent(geschaeftspartneraendern);
    }//GEN-LAST:event_jMI_GPAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Geschäftspartneranzeigen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_GPAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_GPAnzeigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMI_GPAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Zahlungskonditionenanlegen Maske aufgerufen
     * wird
     *
     * @param evt
     */
    private void jMI_ZKAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ZKAnlegenActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(zahlungskonditionanlegen);
        setComponent(zahlungskonditionanlegen);
    }//GEN-LAST:event_jMI_ZKAnlegenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Zahlungskonditionenändern Maske aufgerufen
     * wird
     *
     * @param evt
     */
    private void jMI_ZKAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ZKAEndernActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(zahlungskonditionaendern);
        setComponent(zahlungskonditionaendern);
    }//GEN-LAST:event_jMI_ZKAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Zahlungskonditionenanzeigen Maske aufgerufen
     * wird
     *
     * @param evt
     */
    private void jMI_ZKAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ZKAnzeigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMI_ZKAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Benutzeranlegen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_BenutzerAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_BenutzerAnlegenActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(useranlegen);
        setComponent(useranlegen);
    }//GEN-LAST:event_jMI_BenutzerAnlegenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Benutzerändern Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_BenutzerÄndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_BenutzerÄndernActionPerformed
        if (getComponent() != null) {//Überprüfung ob ein Internalframe bereits sichtbar ist
            getComponent().setVisible(false);//Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
        }
        factory.setComponent(hauptmenueadmin);
        hauptmenueadmin.setVisible(false);
        setCenterJIF(useraendern);
        setComponent(useraendern);
    }//GEN-LAST:event_jMI_BenutzerÄndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Benutzeranzeigen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_BenutzerAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_BenutzerAnzeigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMI_BenutzerAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik und Dokumentation */
    /*----------------------------------------------------------*/
    /**
     * Methode setCenterJIF Methode ermittelt das Zentrum der DesktopPane und
     * setzt das übergebene Internalframe auf die ermittelten Koordinaten.
     *
     * @param jif ,übergebenes InternalFrame
     */
    public void setCenterJIF(Component jif) {
        desktopSize = desktopPane.getSize();
        jInternalFrameSize = jif.getSize();
        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
        jif.setLocation(width, height);
        jif.setVisible(true);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der eine übergebene Component angezeigt wird.
     *
     * @param component
     */
    public void setFrame(Component component) {
        component.setVisible(true);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine übergebene Component speichert.
     *
     * @param component
     */
    public void setComponent(Component component) {
        c = component;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man die aktuell übergebene Component erhält.
     */
    public Component getComponent() {
        return c;
    }

    /**
     * 06.01.2015 Methode mit der man der Statuszeile eine Mitteilung pbergibt
     * die darauf hin in der Statuszeile angeziegt wird.
     *
     * @param status, Übergebener String der dann in der Zeile angegeben werden
     * soll.
     */
    public void setStatusMeldung(String status) {
        statusMeldung_jTextField.setText(status);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem jMI_ArtikelAEndern;
    private javax.swing.JMenuItem jMI_ArtikelAnlegen;
    private javax.swing.JMenuItem jMI_ArtikelAnzeigen;
    private javax.swing.JMenuItem jMI_AuftragkopfAEndern;
    private javax.swing.JMenuItem jMI_AuftragskopfAnlegen;
    private javax.swing.JMenuItem jMI_AuftragskopfAnzeigen;
    private javax.swing.JMenuItem jMI_AuftragsposAEndern;
    private javax.swing.JMenuItem jMI_AuftragsposAnzeigen;
    private javax.swing.JMenuItem jMI_BenutzerAnlegen;
    private javax.swing.JMenuItem jMI_BenutzerAnzeigen;
    private javax.swing.JMenuItem jMI_Benutzerhandbuch;
    private javax.swing.JMenuItem jMI_BenutzerÄndern;
    private javax.swing.JMenuItem jMI_GPAEndern;
    private javax.swing.JMenuItem jMI_GPAnlegen;
    private javax.swing.JMenuItem jMI_GPAnzeigen;
    private javax.swing.JMenuItem jMI_Logout;
    private javax.swing.JMenuItem jMI_ZKAEndern;
    private javax.swing.JMenuItem jMI_ZKAnlegen;
    private javax.swing.JMenuItem jMI_ZKAnzeigen;
    private javax.swing.JMenu jM_ArtikelVerwalten;
    private javax.swing.JMenu jM_AuftragVerwalten;
    private javax.swing.JMenu jM_Auftragskopf;
    private javax.swing.JMenu jM_Auftragspos;
    private javax.swing.JMenu jM_GPVerwalten;
    private javax.swing.JMenu jM_Hilfe;
    private javax.swing.JMenu jM_Logout;
    private javax.swing.JMenu jM_Navigation;
    private javax.swing.JMenu jM_ZKVerwalten;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField statusMeldung_jTextField;
    // End of variables declaration//GEN-END:variables

}
