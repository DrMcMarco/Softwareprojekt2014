package JFrames;

import DAO.DataAccessObject;
import DAO.Parser;
import GUI_Internalframes.*;
import Hauptmenue.Hauptmenue_User;
import Interfaces.SchnittstelleHauptfenster;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.PersistenceException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Luca Terrasi
 *
 *
 * Hauptfenster des Users. Hier befindet sich die Navigation der Software mit
 * der die einzelnen Masken aufgerufen werden können mit denen der User seine
 * Daten anlegen,ändern oder sich anzeigen lassen kann.
 *
 */
/*10.12.2014 Terrasi,Erstellung */
/*16.12.2014 Terrasi, Überarbeitung und Zuweisung
 * der Navigationsfunktion */
/* 02.01.2015 Terrasi, Überarbeitung der  Navigationsfünktion */
/* 06.01.2015 Terrasi, Statuszeile implementiert */
/* 08.01.2015 Terrasi, Anwendungslogik überarbeitet und alle Komponenten */
/* 18.02.2015 Sen, Tastenkuerzel hinzugefuegt zusammengefügt.*/
/* 18.02.2015 TER, getestet und freigegeben */
public class Start extends javax.swing.JFrame implements SchnittstelleHauptfenster {

    /**
     * Definition der Attribute
     */
    GUIFactory factory;
    DataAccessObject dao;
    
    // Erzeugung von variablen für die einzelnen Masken.
    public Hauptmenue_User hauptmenueuser;
    public AllgemeineSuche suche;
    public SucheDetailAnzeige detailSuche;

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

    public StatistikAnzeige statistikAnzeige;

    public Login logInFester;


    //Hilfsvariablen
    private Dimension desktopSize;//Speichervariable für die Größe der DesktopPane.
    private Dimension jInternalFrameSize;//Speichervariable für die Größe des InternalFrames.
    private JInternalFrame letzteComponente;// Speichervariable für Components.
    private JInternalFrame letzteAnzeige;
    //Stringvariablen für die einzelnen Meldungen die ausgegeben werden können.
    private final String BEENDEN_MELDUNG = "Wollen sie sich wirklich abmelden?";
    private final String BEENDEN_MELDUNG_TYP = "Programm beenden";

    private final String AUFTRAGSKOPF_AENDERN_EINSTIEG = "Auftragskopf "
            + "ändern Einstieg";
    private final String AUFTRAGSKOPF_ANZEIGEN_EINSTIEG = "Auftragskopf "
            + "anzeigen Einstieg";

    private final String AUFTRAGSPOSITION_AENDERN_EINSTIEG = "Auftragsposition "
            + "ändern Einstieg";
    private final String AUFTRAGSPOSITION_ANZEIGEN_EINSTIEG = "Auftragsposition "
            + "anzeigen Einstieg";

    private final String ARTIKEL_AENDERN_EINSTIEG = "Artikel "
            + "ändern Einstieg";
    private final String ARTIKEL_ANZEIGEN_EINSTIEG = "Artikel "
            + "anzeigen Einstieg";

    private final String ZK_AENDERN_EINSTIEG = "Zahlungskondition "
            + "ändern Einstieg";
    private final String ZK_AZEIGEN_EINSTIEG = "Zahlungskondition "
            + "anzeigen Einstieg";

    private final String GP_AENDERN_EINSTIEG = "Geschäftspartner "
            + "ändern Einstieg";
    private final String GP_ANZEIGEN_EINSTIEG = "Geschäftspartner "
            + "anzeigen Einstieg";

    private static Timer timer;


    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Konstruktor, Erzeugung
     * 
     * @param login, Loginklasse
     */
    public Start(Login login) {
        initComponents();
        // Try-Block
        try {
            factory = new GUIFactory();// Erzeugung eines Guifactoryobjektes.
        } catch (PersistenceException e) {// Fehlerbehandlung falls bei der 
            // Erzeugung entwas nicht funktioniert hat.
        }
        
        letzteComponente = null; // Initialisierung der Hilfsvariable für die Components.
        this.letzteAnzeige = null;

        this.logInFester = login;

        //Initialisierung der einzelnen Masken.
        hauptmenueuser = new Hauptmenue_User(factory, this);
        hauptmenueuser.setName("HauptmenüUser");

        auftragskopfanlegen = new AuftragskopfAnlegen(factory, this);
        this.suche = new AllgemeineSuche(factory, this);
        detailSuche = new SucheDetailAnzeige(this);

        auftragskopfaendern = new AuftragskopfAendern(factory, auftragskopfanlegen,
                this);
        auftragsspositionanzeigen = new AuftragspositionAnzeigen(factory,
                this);
        auftragsspositionaender = new AuftragspositionAendern(factory, 
                auftragsspositionanzeigen, this);
        artikelanlegen = new ArtikelAnlegen(factory, this);
        artikelaendern = new ArtikelAEndernEinstieg(factory, artikelanlegen, this);
        geschaeftspartneranlegen = new GeschaeftspartnerAnlegen(factory, this);
        geschaeftspartneraendern = new GeschaeftspartnerAEndernEinstieg(factory, 
                geschaeftspartneranlegen, this);
        zahlungskonditionanlegen = new ZahlungskonditionAnlegen(factory, this);
        zahlungskonditionaendern = new ZahlungskonditionenAEndernEinstieg(
                factory, zahlungskonditionanlegen, this);
        statistikAnzeige = new StatistikAnzeige();
        
        
        // Aufruf der setzeCenterJIF-Methode
        setzeCenterJIF(hauptmenueuser);
        
        // Zuweisung der Masken an die Hauptansicht
        desktopPane.add(hauptmenueuser);
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
        desktopPane.add(suche);
        desktopPane.add(detailSuche);
        desktopPane.add(statistikAnzeige);
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
        suche.setVisible(false);
        detailSuche.setVisible(false);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
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
        statistik_jmn = new javax.swing.JMenu();
        umsatz_mn = new javax.swing.JMenuItem();
        umsatz_einkauf_mn = new javax.swing.JMenuItem();
        artikel_absatz_mn = new javax.swing.JMenuItem();
        artikel_verkauft_mn = new javax.swing.JMenuItem();
        kat_umsatz_mn = new javax.swing.JMenuItem();
        jM_Logout = new javax.swing.JMenu();
        jMI_Logout = new javax.swing.JMenuItem();
        jM_Hilfe = new javax.swing.JMenu();
        jMI_Benutzerhandbuch = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Hauptmenü");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        statusMeldung_jTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        statusMeldung_jTextField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        statusMeldung_jTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        statusMeldung_jTextField.setEnabled(false);

        jM_Navigation.setMnemonic('n');
        jM_Navigation.setText("Navigation");

        jM_AuftragVerwalten.setMnemonic('a');
        jM_AuftragVerwalten.setText("Aufträge verwalten");
        jM_AuftragVerwalten.setActionCommand("JMenu1");

        jM_Auftragskopf.setText("Auftragskopf");

        jMI_AuftragskopfAnlegen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragskopfAnlegen.setText("Anlegen");
        jMI_AuftragskopfAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragskopfAnlegenActionPerformed(evt);
            }
        });
        jM_Auftragskopf.add(jMI_AuftragskopfAnlegen);

        jMI_AuftragkopfAEndern.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragkopfAEndern.setText("Ändern");
        jMI_AuftragkopfAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragkopfAEndernActionPerformed(evt);
            }
        });
        jM_Auftragskopf.add(jMI_AuftragkopfAEndern);

        jMI_AuftragskopfAnzeigen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragskopfAnzeigen.setText("Anzeigen");
        jMI_AuftragskopfAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragskopfAnzeigenActionPerformed(evt);
            }
        });
        jM_Auftragskopf.add(jMI_AuftragskopfAnzeigen);

        jM_AuftragVerwalten.add(jM_Auftragskopf);

        jM_Auftragspos.setText("Auftragsposition");

        jMI_AuftragsposAEndern.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragsposAEndern.setText("Ändern");
        jMI_AuftragsposAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragsposAEndernActionPerformed(evt);
            }
        });
        jM_Auftragspos.add(jMI_AuftragsposAEndern);

        jMI_AuftragsposAnzeigen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
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
        jM_ArtikelVerwalten.setText("Artikel verwalten");
        jM_ArtikelVerwalten.setActionCommand("JMenu1");

        jMI_ArtikelAnlegen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMI_ArtikelAnlegen.setText("Anlegen");
        jMI_ArtikelAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ArtikelAnlegenActionPerformed(evt);
            }
        });
        jM_ArtikelVerwalten.add(jMI_ArtikelAnlegen);

        jMI_ArtikelAEndern.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMI_ArtikelAEndern.setText("Ändern");
        jMI_ArtikelAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ArtikelAEndernActionPerformed(evt);
            }
        });
        jM_ArtikelVerwalten.add(jMI_ArtikelAEndern);

        jMI_ArtikelAnzeigen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMI_ArtikelAnzeigen.setText("Anzeigen");
        jMI_ArtikelAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ArtikelAnzeigenActionPerformed(evt);
            }
        });
        jM_ArtikelVerwalten.add(jMI_ArtikelAnzeigen);

        jM_Navigation.add(jM_ArtikelVerwalten);

        jM_GPVerwalten.setText("Geschäftspartner verwalten");
        jM_GPVerwalten.setActionCommand("JMenu1");

        jMI_GPAnlegen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMI_GPAnlegen.setText("Anlegen");
        jMI_GPAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_GPAnlegenActionPerformed(evt);
            }
        });
        jM_GPVerwalten.add(jMI_GPAnlegen);

        jMI_GPAEndern.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMI_GPAEndern.setText("Ändern");
        jMI_GPAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_GPAEndernActionPerformed(evt);
            }
        });
        jM_GPVerwalten.add(jMI_GPAEndern);

        jMI_GPAnzeigen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMI_GPAnzeigen.setText("Anzeigen");
        jMI_GPAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_GPAnzeigenActionPerformed(evt);
            }
        });
        jM_GPVerwalten.add(jMI_GPAnzeigen);

        jM_Navigation.add(jM_GPVerwalten);

        jM_ZKVerwalten.setText("Zahlungskonditionen verwalten");
        jM_ZKVerwalten.setActionCommand("JMenu1");

        jMI_ZKAnlegen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMI_ZKAnlegen.setText("Anlegen");
        jMI_ZKAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ZKAnlegenActionPerformed(evt);
            }
        });
        jM_ZKVerwalten.add(jMI_ZKAnlegen);

        jMI_ZKAEndern.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMI_ZKAEndern.setText("Ändern");
        jMI_ZKAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ZKAEndernActionPerformed(evt);
            }
        });
        jM_ZKVerwalten.add(jMI_ZKAEndern);

        jMI_ZKAnzeigen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMI_ZKAnzeigen.setText("Anzeigen");
        jMI_ZKAnzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_ZKAnzeigenActionPerformed(evt);
            }
        });
        jM_ZKVerwalten.add(jMI_ZKAnzeigen);

        jM_Navigation.add(jM_ZKVerwalten);

        menuBar.add(jM_Navigation);

        statistik_jmn.setText("Statistik");

        umsatz_mn.setText("Gesamt Umsatz");
        umsatz_mn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                umsatz_mnActionPerformed(evt);
            }
        });
        statistik_jmn.add(umsatz_mn);

        umsatz_einkauf_mn.setText("Umsatz-Einkauf Vergleich");
        umsatz_einkauf_mn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                umsatz_einkauf_mnActionPerformed(evt);
            }
        });
        statistik_jmn.add(umsatz_einkauf_mn);

        artikel_absatz_mn.setText("Artikel Umsatz");
        artikel_absatz_mn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                artikel_absatz_mnActionPerformed(evt);
            }
        });
        statistik_jmn.add(artikel_absatz_mn);

        artikel_verkauft_mn.setText("Artikel Verkaufsmenge");
        artikel_verkauft_mn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                artikel_verkauft_mnActionPerformed(evt);
            }
        });
        statistik_jmn.add(artikel_verkauft_mn);

        kat_umsatz_mn.setText("Kategorie Umsatz");
        kat_umsatz_mn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kat_umsatz_mnActionPerformed(evt);
            }
        });
        statistik_jmn.add(kat_umsatz_mn);

        menuBar.add(statistik_jmn);

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
            .addComponent(statusMeldung_jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
            .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusMeldung_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 20.02.2015 Sen     Ueberarbeitet.*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Action die ausgeführt wird, wenn das Programm geschlossen werden soll,
     * indem auf den X-Button geklickt wird. Es erscheint eine entsprechene
     * Meldung, die der User bestätogen muss um das Program zu beenden.
     *
     * @param evt
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // ActionPerformed für Logout wird ausgeführt.
        jMI_LogoutActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode in der definiert wird was beim betätigen des Logouts in der
     * Navigation passiert
     *
     * @param evt
     */
    private void jMI_LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_LogoutActionPerformed
        // Erzeugen eine Meldung mit Abfrage
        int antwort = JOptionPane.showConfirmDialog(rootPane, BEENDEN_MELDUNG,
                BEENDEN_MELDUNG_TYP, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (antwort == JOptionPane.YES_OPTION) {// Bei positiver Antwort.
            this.logInFester.getAnmeldeFenster().zurueckSetzen();
            this.logInFester.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_jMI_LogoutActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik und Dokumentation.*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode in der definiert wird was aufgerufen wird wenn man den
     * Hilfebutton betätigt.
     *
     * @param evt
     */
    private void jMI_BenutzerhandbuchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_BenutzerhandbuchActionPerformed

        //Prüfe ob die Klasse Desktop für die aktuelle Plattform unterstützt 
        //wird
        if (Desktop.isDesktopSupported()) {
            //Wenn ja, versuche das Benutzerhandbuch über die Klasse Desktop zu
            //öffnen. Es muss eine Anwendung zur Darstellung von PDF-Dateien auf
            //dem System vorhanden sein
            try {
                File pdf = new File("Benutzerhandbuch_SWP_GR3.pdf");
                Desktop.getDesktop().open(pdf);
            //Wenn die Datei nicht gefunden werden kann    
            } catch (IOException e) {
                
            }
        }

    }//GEN-LAST:event_jMI_BenutzerhandbuchActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 08.01.2015 Terrasi AuftragskopfAnlegenMaske mit der Anzeigen/Ändern-
     Funktion angepasst.*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die AuftrgaskopfAnlegen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_AuftragskopfAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragskopfAnlegenActionPerformed
        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        auftragskopfanlegen.setzeStatusAnlegen();

        setzeCenterJIF(auftragskopfanlegen);
        setzeComponent(auftragskopfanlegen);
    }//GEN-LAST:event_jMI_AuftragskopfAnlegenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 08.01.2015 Terrasi AuftragskopfÄndernMaske hinzugefügt*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die AuftrgaskopfAendern Maske aufgerufen wird.
     * @param evt
     */
    private void jMI_AuftragkopfAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragkopfAEndernActionPerformed
        //Überprüfung ob ein Internalframe bereits sichtbar ist.
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt.
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);
        hauptmenueuser.setVisible(false);

        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        auftragskopfaendern.zuruecksetzen();
        auftragskopfaendern.setTitle(AUFTRAGSKOPF_AENDERN_EINSTIEG);
        setzeCenterJIF(auftragskopfaendern);
        setzeComponent(auftragskopfaendern);
    }//GEN-LAST:event_jMI_AuftragkopfAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 08.01.2015 Terrasi AuftragskopfAnzeigenMaske hinzugefügt*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die AuftrgaskopfAnzeigen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_AuftragskopfAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragskopfAnzeigenActionPerformed
        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        auftragskopfaendern.zuruecksetzen();
        auftragskopfaendern.setTitle(AUFTRAGSKOPF_ANZEIGEN_EINSTIEG);
        setzeCenterJIF(auftragskopfaendern);
        setzeComponent(auftragskopfaendern);
    }//GEN-LAST:event_jMI_AuftragskopfAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 08.01.2015 Terrasi AuftragspositionÄndernMaske hinzugefügt*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Auftrgaspositionändern Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_AuftragsposAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragsposAEndernActionPerformed

        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        auftragsspositionaender.zuruecksetzen();
        auftragsspositionaender.setTitle(AUFTRAGSPOSITION_AENDERN_EINSTIEG);
        setzeCenterJIF(auftragsspositionaender);
        setzeComponent(auftragsspositionaender);
    }//GEN-LAST:event_jMI_AuftragsposAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 08.01.2015 Terrasi AuftragspositionAnzeigenMaske hinzugefügt*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Auftrgaspositionanzeigen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_AuftragsposAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragsposAnzeigenActionPerformed

        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        auftragsspositionaender.zuruecksetzen();
        auftragsspositionaender.setTitle(AUFTRAGSPOSITION_ANZEIGEN_EINSTIEG);
        setzeCenterJIF(auftragsspositionaender);
        setzeComponent(auftragsspositionaender);
    }//GEN-LAST:event_jMI_AuftragsposAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 07.01.2015 Sen anlegen Funktion hinzugefuegt */
    /* 09.01.2015 Sen anlegen Funktion View erweitert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Artikelanlegen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_ArtikelAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ArtikelAnlegenActionPerformed

        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        artikelanlegen.setzeFormularInArtikelAnlegen();
        setzeCenterJIF(artikelanlegen);
        setzeComponent(artikelanlegen);
    }//GEN-LAST:event_jMI_ArtikelAnlegenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 07.01.2015 Sen aendern Funktion View hinzugefuegt */
    /* 09.01.2015 Sen aendern Funktion View erweitert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Artikeländern Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_ArtikelAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ArtikelAEndernActionPerformed
        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        artikelaendern.setTitle(ARTIKEL_AENDERN_EINSTIEG);
        artikelaendern.zuruecksetzen();
        artikelanlegen.setzeFormularInArtikelAEndern();
        setzeCenterJIF(artikelaendern);
        setzeComponent(artikelaendern);
        artikelaendern.gibjTF_Artikel_ID().requestFocusInWindow();
    }//GEN-LAST:event_jMI_ArtikelAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 07.01.2015 Sen anzeigen Funktion hinzugefuegt */
    /* 09.01.2015 Sen anzeigen Funktion erweitert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Artikelanzeigen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_ArtikelAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ArtikelAnzeigenActionPerformed
        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        artikelaendern.setTitle(ARTIKEL_ANZEIGEN_EINSTIEG);
        artikelaendern.zuruecksetzen();
        artikelanlegen.setzeFormularInArtikelAnzeigen();
        setzeCenterJIF(artikelaendern);
        setzeComponent(artikelaendern);
    }//GEN-LAST:event_jMI_ArtikelAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 07.01.2015 Sen anlegen Funktion hinzugefuegt */
    /* 09.01.2015 Sen anlegen Funktion View erweitert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Geschäftspartneranlegen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_GPAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_GPAnlegenActionPerformed
        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        geschaeftspartneranlegen.setzeFormularInGPAnlegen();
        setzeCenterJIF(geschaeftspartneranlegen);
        setzeComponent(geschaeftspartneranlegen);
    }//GEN-LAST:event_jMI_GPAnlegenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 07.01.2015 Sen aendern Funktion hinzugefuegt */
    /* 09.01.2015 Sen aendern Funktion View erweitert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Geschäftspartnerändern Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_GPAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_GPAEndernActionPerformed
        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        geschaeftspartneraendern.setTitle(GP_AENDERN_EINSTIEG);
        geschaeftspartneraendern.zuruecksetzen();
        geschaeftspartneranlegen.setzeFormularInGPAEndern();
        setzeCenterJIF(geschaeftspartneraendern);
        setzeComponent(geschaeftspartneraendern);
    }//GEN-LAST:event_jMI_GPAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 07.01.2015 Sen anzeigen Funktion hinzugefuegt */
    /* 09.01.2015 Sen anzeigen Funktion View erweitert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Geschäftspartneranzeigen Maske aufgerufen wird
     *
     * @param evt
     */
    private void jMI_GPAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_GPAnzeigenActionPerformed

        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        geschaeftspartneraendern.setTitle(GP_ANZEIGEN_EINSTIEG);
        geschaeftspartneraendern.zuruecksetzen();
        geschaeftspartneranlegen.setzeFormularInGPAnzeigen();
        setzeCenterJIF(geschaeftspartneraendern);
        setzeComponent(geschaeftspartneraendern);
    }//GEN-LAST:event_jMI_GPAnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet */
    /* 07.01.2015 Sen anlegen Funktion hinzugefuegt */
    /* 09.01.2015 Sen anlegen Funktion View erweitert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Zahlungskonditionenanlegen Maske aufgerufen
     * wird
     *
     * @param evt
     */
    private void jMI_ZKAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ZKAnlegenActionPerformed
        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        zahlungskonditionanlegen.setzeFormularInZKAnlegen();
        setzeCenterJIF(zahlungskonditionanlegen);
        setzeComponent(zahlungskonditionanlegen);
    }//GEN-LAST:event_jMI_ZKAnlegenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik  und Dokumentation.*/
    /* 02.01.2015 Terrasi Logik überarbeitet*/
    /* 07.01.2015 Sen aendern Funktion hinzugefuegt */
    /* 09.01.2015 Sen aendern Funktion View erweitert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Zahlungskonditionenändern Maske aufgerufen
     * wird
     *
     * @param evt
     */
    private void jMI_ZKAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ZKAEndernActionPerformed

        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        zahlungskonditionaendern.setTitle(ZK_AENDERN_EINSTIEG);
        zahlungskonditionaendern.zuruecksetzen();
        zahlungskonditionanlegen.setzeFormularInZKAEndern();
        setzeCenterJIF(zahlungskonditionaendern);
        setzeComponent(zahlungskonditionaendern);
    }//GEN-LAST:event_jMI_ZKAEndernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 07.01.2015 Sen anzeigen Funktion hinzugefuegt */
    /* 09.01.2015 Sen anzeigen Funktion View erweitert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed in der die Zahlungskonditionenanzeigen Maske aufgerufen
     * wird
     *
     * @param evt
     */
    private void jMI_ZKAnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_ZKAnzeigenActionPerformed
        //Überprüfung ob ein Internalframe bereits sichtbar ist
        if (getComponent() != null) {
            //Angezeigte Internaframe wird nicht mehr sichtbar dargestellt
            getComponent().setVisible(false);
        }
        factory.setComponent(hauptmenueuser);

        hauptmenueuser.setVisible(false);
        this.suche.setVisible(false);
        this.detailSuche.setVisible(false);

        zahlungskonditionaendern.setTitle(ZK_AZEIGEN_EINSTIEG);
        zahlungskonditionaendern.zuruecksetzen();
        zahlungskonditionanlegen.setzeFormularInZKAnzeigen();
        setzeCenterJIF(zahlungskonditionaendern);
        setzeComponent(zahlungskonditionaendern);
    }//GEN-LAST:event_jMI_ZKAnzeigenActionPerformed

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed für Statistik ArtikelAbsatz
     */
    private void artikel_absatz_mnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_artikel_absatz_mnActionPerformed

        this.statistikAnzeige.setzeChart(GUIFactory.getDAO().gibChartArtikelAbsatz());
    }//GEN-LAST:event_artikel_absatz_mnActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed für Statistik UmsatzAuftragswert
     */
    private void umsatz_mnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_umsatz_mnActionPerformed

        this.statistikAnzeige.setzeChart(GUIFactory.getDAO().gibChartUmsatzAuftragswert());
    }//GEN-LAST:event_umsatz_mnActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed für Statistik ArtikelMenge
     */
    private void artikel_verkauft_mnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_artikel_verkauft_mnActionPerformed
        this.statistikAnzeige.setzeChart(GUIFactory.getDAO().gibChartArtikelMenge());
    }//GEN-LAST:event_artikel_verkauft_mnActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed für Statistik ArtikelkategorieAbsatz
     */
    private void kat_umsatz_mnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kat_umsatz_mnActionPerformed
        this.statistikAnzeige.setzeChart(GUIFactory.getDAO().gibChartArtikelkategorieAbsatz());
    }//GEN-LAST:event_kat_umsatz_mnActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed für Statistik UmsatzEinkaufAuftragswert
     */
    private void umsatz_einkauf_mnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_umsatz_einkauf_mnActionPerformed
        this.statistikAnzeige.setzeChart(GUIFactory.getDAO().gibChartUmsatzEinkaufAuftragswert());
    }//GEN-LAST:event_umsatz_einkauf_mnActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi Logik und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode setzeCenterJIF Methode ermittelt das Zentrum der DesktopPane und
 setzt das übergebene Internalframe auf die ermittelten Koordinaten.
     *
     * @param jif ,übergebenes InternalFrame
     */
    @Override
    public void setzeCenterJIF(JInternalFrame jif) {
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
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der eine übergebene Component angezeigt wird.
     * @param component eine komponente
     */
    @Override
    public void setzeFrame(Component component) {
        component.setVisible(true);
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return hauptmenü
     */
    @Override
    public JInternalFrame gibMenu() {
        return this.hauptmenueuser;
    }

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     */
    @Override
    public void rufeSuche(JInternalFrame comp) {
        this.suche.setzeModelCombobox(this.gibTabellenFuerSuche(comp));
        this.suche.setVisible(true);
        this.letzteAnzeige = comp;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return letzteAnzeige
     */
    @Override
    public JInternalFrame gibLetzteAnzeige() {
        return this.letzteAnzeige;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return, Titels des letzten InternalFrames.
     */
    @Override
    public String gibTitel() {
        return this.letzteAnzeige.getTitle();
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return artikelanlegen
     */
    @Override
    public ArtikelAnlegen gibArtikelAnlegenFenster() {
        return this.artikelanlegen;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return auftragskopfanlegen
     */
    @Override
    public AuftragskopfAnlegen gibAuftragskopfanlegenFenster() {
        return this.auftragskopfanlegen;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return geschaeftspartneranlegen
     */
    @Override
    public GeschaeftspartnerAnlegen gibGeschaeftspartneranlegenFenster() {
        return this.geschaeftspartneranlegen;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return zahlungskonditionaendern
     */
    @Override
    public ZahlungskonditionenAEndernEinstieg gibZkAendernEinstieg() {
        return this.zahlungskonditionaendern;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return geschaeftspartneraendern
     */
    @Override
    public GeschaeftspartnerAEndernEinstieg gibGeschaeftspartnerAendernEinstieg() {
        return this.geschaeftspartneraendern;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return artikelaendern
     */
    @Override
    public ArtikelAEndernEinstieg gibArtikelaendernEinstieg() {
        return this.artikelaendern;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return auftragskopfaendern
     */
    @Override
    public AuftragskopfAendern gibAkAendernEinstieg() {
        return this.auftragskopfaendern;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return auftragsspositionaender
     */
    @Override
    public AuftragspositionAendern gibPositionAendernEinstieg() {
        return this.auftragsspositionaender;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return zahlungskonditionanlegen
     */
    @Override
    public ZahlungskonditionAnlegen gibZkAnlegen() {
        return this.zahlungskonditionanlegen;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode
     * @return auftragspositionanzeigen
     */
    @Override
    public AuftragspositionAnzeigen gibApAnzeigen() {
        return this.auftragsspositionanzeigen;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.12.2014 SCH angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Gibt die Tabellennamen je nach Fenster zurück.
     *
     * @param comp Fenster
     * @return Tabellen
     */
    public String[] gibTabellenFuerSuche(JInternalFrame comp) {
        String[] tabellen = null;
        //Suche wurde vom Hauptmenü aus gestartet
        if (comp == null) {
            tabellen = new String[]{"Auftragskopf", "Auftragsposition",
                "Artikel", "Artikelkategorie", "Geschäftspartner",
                "Zahlungskondition", "Anschrift", "Status"};
        } else {
            //Prüfe, um welches Fenster es sich handelt und setze die Tabellen
            switch (comp.getTitle()) {
                case "Artikel anlegen":
                    tabellen = new String[]{"Artikelkategorie"};
                    break;
                case "Artikel ändern":
                    tabellen = new String[]{"Artikelkategorie"};
                    break;
                case "Auftragskopf anlegen":
                    tabellen = new String[]{"Geschäftspartner", "Artikel"};
                    break;
                case "Auftragskopf ändern":
                    tabellen = new String[]{"Geschäftspartner", "Artikel"};
                    break;
                case "Geschäftspartner anlegen":
                    tabellen = new String[]{"Anschrift"};
                    break;
                case "Geschäftspartner ändern":
                    tabellen = new String[]{"Anschrift"};
                    break;
                case "Artikel ändern Einstieg":
                    tabellen = new String[]{"Artikel"};
                    break;
                case "Artikel anzeigen":
                    tabellen = new String[]{"Artikel"};
                    break;
                case "Artikel anzeigen Einstieg":
                    tabellen = new String[]{"Artikel"};
                    break;
                case "Geschäftspartner ändern Einstieg":
                    tabellen = new String[]{"Geschäftspartner"};
                    break;
                case "Geschäftspartner anzeigen":
                    tabellen = new String[]{"Geschäftspartner"};
                    break;
                case "Geschäftspartner anzeigen Einstieg":
                    tabellen = new String[]{"Geschäftspartner"};
                    break;
                case "Zahlungskondition ändern Einstieg":
                    tabellen = new String[]{"Zahlungskondition"};
                    break;
                case "Zahlungskondition anzeigen":
                    tabellen = new String[]{"Zahlungskondition"};
                    break;
                case "Zahlungskondition anzeigen Einstieg":
                    tabellen = new String[]{"Zahlungskondition"};
                    break;
                case "Auftragsposition ändern Einstieg":
                    tabellen = new String[]{"Auftragskopf"};
                    break;
                case "Auftragsposition anzeigen Einstieg":
                    tabellen = new String[]{"Auftragskopf"};
                    break;
                case "Auftragskopf anzeigen Einstieg":
                    tabellen = new String[]{"Auftragskopf"};
                    break;
                case "Auftragskopf ändern Einstieg":
                    tabellen = new String[]{"Auftragskopf"};
                    break;
                case "Auftragskopf anzeigen":
                    tabellen = new String[]{"Auftragskopf"};
                    break;
                default:
                    break;
            }
        }
        return tabellen;
    }

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine übergebene Component speichert.
     *
     * @param component eine Komponente
     */
    @Override
    public void setzeComponent(JInternalFrame component) {
        letzteComponente = component;
    }

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man die aktuell übergebene Component erhält.
     *
     * @return Aktuelle Komponente
     */
    @Override
    public JInternalFrame getComponent() {
        
        return letzteComponente;
    }

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt und Logik implementiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man der Statuszeile eine Mitteilung übergibt
     * die darauf hin in der Statuszeile angeziegt wird.
     *
     * @param status, Übergebener String der dann in der Zeile angegeben werden
     * soll.
     */
    @Override
    public void setzeStatusMeldung(String status) {
        // Erzeugung eines Peeptons.
        Toolkit.getDefaultToolkit().beep();

        statusMeldung_jTextField.setText(status);//Meldung wird angezeigt.

        timer = new Timer();// Timer
        timer.schedule(new TimerTask() {
            public void run() {
                statusMeldung_jTextField.setText("");

            }
        }, 5 * 1000);// 5 Sekunden.

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
                new Start(new Login()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem artikel_absatz_mn;
    private javax.swing.JMenuItem artikel_verkauft_mn;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem jMI_ArtikelAEndern;
    private javax.swing.JMenuItem jMI_ArtikelAnlegen;
    private javax.swing.JMenuItem jMI_ArtikelAnzeigen;
    private javax.swing.JMenuItem jMI_AuftragkopfAEndern;
    private javax.swing.JMenuItem jMI_AuftragskopfAnlegen;
    private javax.swing.JMenuItem jMI_AuftragskopfAnzeigen;
    private javax.swing.JMenuItem jMI_AuftragsposAEndern;
    private javax.swing.JMenuItem jMI_AuftragsposAnzeigen;
    private javax.swing.JMenuItem jMI_Benutzerhandbuch;
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
    private javax.swing.JMenuItem kat_umsatz_mn;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu statistik_jmn;
    private javax.swing.JTextField statusMeldung_jTextField;
    private javax.swing.JMenuItem umsatz_einkauf_mn;
    private javax.swing.JMenuItem umsatz_mn;
    // End of variables declaration//GEN-END:variables

}
