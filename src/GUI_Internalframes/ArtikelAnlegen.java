package GUI_Internalframes;

import Documents.UniversalDocument;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import JFrames.*;
import DAO.*;
import DTO.Artikel;
import DTO.Artikelkategorie;
import Interfaces.SchnittstelleHauptfenster;
import Interfaces.SchnittstelleFensterFunktionen;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * GUI Klasse für Artikel verwalten. Diese Klasse beinhaltet alle Methoden, die
 * benötigt werden, um einen Arikel anzulegen, einen angelegten Artikel zu
 * ändern sowie einen angelegten Artikel anzeigen zu lassen. Je nach dem von
 * welchem Button aus diese Klasse aufgerufen wird, passt sie sich entsprechend
 * an und ändert sich zum Beispiel in die Sicht Artikel ändern. Falls der Button
 * Artikel anlegen betätigt wird, ändert sich die Sicht in Artikel anlegen.
 *
 * @author Tahir
 *
 */
public class ArtikelAnlegen extends javax.swing.JInternalFrame
        implements SchnittstelleFensterFunktionen {

    /**
     * Variable, die für die Navigation benoetigt wird, in ihr wird gespeichert,
     * welche View zuletzt geöffnet war.
     */
    private Component c;
    /**
     * Varibale für die GUI Factory.
     */
    private GUIFactory factory;
    /**
     * Variable für die DAO.
     */
    private DataAccessObject dao;
    /**
     * Variable für das Start Fenster. Es kann sein, dass sich ein Admin
     * anmeldet, dann waere unser StartFenster die StartAdmin. Falls sich ein
     * User anmeldet, ist unser StartFenster Start.
     */
    private SchnittstelleHauptfenster hauptFenster;
    /**
     * Varibale fue die Artikelnummer.
     */
    private int artikelnummer;
    /**
     * Arraylist, um fehlerhafte Componenten zu speichern.
     */
    private ArrayList<Component> fehlerhafteComponenten;
    /**
     * Collection, um die aus der Datenbank geladenen Kategorien zu speichern.
     */
    private Collection<Artikelkategorie> kategorienAusDatenbank;
    /**
     * ArrayList, um die Namen der aus der Datenbank geladenen Kategorien zu.
     * speichern, diese werden der Combobox uebergeben
     */
    private ArrayList<String> kategorienFuerCombobox;
    /**
     * Varible, die die Standard Farbe eine Combobox beinhaltet.
     */
    private final Color JCB_FARBE_STANDARD = new Color(214, 217, 223);
    /**
     * Variable, die die Standard Farbe eines Textfieldes beinhaltet.
     */
    private final Color JTF_FARBE_STANDARD = new Color(255, 255, 255);
    /**
     * Varibale, die die Farbe fuer fehlerhafte Componenten beinhaltet.
     */
    private final Color FARBE_FEHLERHAFT = Color.YELLOW;
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer den Preis.
     */
    private final String PREUFUNG_PREIS = "|(\\d*,?\\d{1,2})"
            + "|(\\d{0,3}(\\.\\d{3})*,?\\d{1,2})";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String TITEL_PFLICHTFELDER_AUSFUELLEN = "Pflichtfelder nicht "
            + "ausgefüllt";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String MELDUNG_PFLICHTFELDER_AUSFUELLEN = "Einige"
            + " Pflichtfelder wurden nicht ausgefüllt.\nBitte füllen"
            + " Sie diese aus!";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String MELDUNG_EINZELWERT_FALSCH = "Der eingegebene"
            + " Preis ist nicht richtig!\nBitte geben Sie einen richtigen"
            + " Preis ein. (z.B. 9,99 oder 99.999,99)";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String TITEL_FEHLERHAFTE_EINGABE = "Fehlerhafte Eingabe";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String MELDUNG_AENDERUNGEN_SPEICHERN = "Möchten Sie die"
            + " Änderungen speichern?";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String FEHLER = "Fehler";
    /**
     * Variable, um die Statusmeldungen ausgeben zu koennen.
     */
    private String statuszeile;
    /**
     * Varibale, die die maximale Anzahl von fehlerhaften Componenten
     * beinhaltet.
     */
    private final int maxAnzahlFehlerhafterComponenten = 6;
    /**
     * Number Formatter wird benoetigt fuer das Parsen der Eingaben, sowie das
     * Anzeigen von Preisen.
     */
    private NumberFormat nf;

    /**
     * Variablen für den Titel der jeweilige Maske.
     */
    private final String ARTIKEL_ANLEGEN = "Artikel anlegen";
    /**
     * Variablen für den Titel der jeweilige Maske.
     */
    private final String ARTIKEL_AENDERN = "Artikel ändern";
    /**
     * Variablen für den Titel der jeweilige Maske.
     */
    private final String ARTIKEL_ANZEIGEN = "Artikel anzeigen";
    /**
     * Variable, um zu pruefen, ob alle Eingaben, wenn welche gemacht wurden, ok
     * sind.
     */
    private boolean formularOK = true;

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     *
     * @param factory beinhaltet das factory Obejekt
     * @param mainView beinhaltet das Objekt des StartFenster
     */
    public ArtikelAnlegen(GUIFactory factory, SchnittstelleHauptfenster mainView) {
        initComponents();
        this.hauptFenster = mainView;
//        factory und die dao werden gesetzt
        this.factory = factory;
//        DAO wird von der statischen Klasse GUIFactory uebernommen
        this.dao = GUIFactory.getDAO();
        fehlerhafteComponenten = new ArrayList<>();
//      Kategorien werden aus der Datenbank geladen
        ladeKategorienAusDatenbank();
        nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
//        Documente werden gesetzt
        jTF_Artikelname.setDocument(
                new UniversalDocument("0123456789-.´+_=/&!'§%()[]{}? ",
                        true, 30));
        jTF_Einzelwert.setDocument(
                new UniversalDocument("0123456789.,", false, 20));
        jTF_Bestellwert.setDocument(
                new UniversalDocument("0123456789.,", false, 20));
    }


    /*
     * Historie:
     * 10.12.2014   Sen     angelegt
     * 15.12.2014   Sen     ueberarbeitet
     */
    /**
     * Diese Methode laedt aus der Datenbank die Kategorien eines Artikels.
     * Diese werden anschließend der Combobox der Kategorien uebergeben.
     */
    private void ladeKategorienAusDatenbank() {
        try {
            kategorienAusDatenbank = this.dao.gibAlleKategorien();
//            StringArray fuer das Model der Combobox mit der Groeße
//            der aus der Datenbank geladenen Collection erzeugen;
            kategorienFuerCombobox = new ArrayList<>();
//            Die Combobox soll zunaechst Bitte auswaehlen beinhalten
            kategorienFuerCombobox.add("Bitte auswählen");
//         UEber die Kategorien der Datenbank wird itteritert und die jeweiligen
//         Kategorienamen werden in die Arraylist fuer die Combobox hinzugefuegt
            Iterator<Artikelkategorie> it = kategorienAusDatenbank.iterator();
            while (it.hasNext()) {
                kategorienFuerCombobox.add(it.next().getKategoriename());
            }
//            alle Kategorien geladen, also wird die Combobox gesetzt
            jCB_Kategorie.setModel(new DefaultComboBoxModel(
                    kategorienFuerCombobox.toArray()));
        } catch (NullPointerException ex) {
        }
    }

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     * 10.12.2014   Sen     ueberarbeitet
     */
    /**
     * Methode für die Überprüfung der Daten. Falls ein Textfeld nicht gefüllt
     * ist, wird sie der ArrayList für fehlerhafte Componenten hinzugefuegt.
     * Falls bei eine Compobox der selektierte Index auf 0 ("Bitte auswählen")
     * steht, wird diese ebenfalls in die ArrayList uebernommen
     */
    @Override
    public void ueberpruefen() {
        if (jTF_Artikelname.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Artikelname);
        }
        if (jTA_Artikelbeschreibung.getText().equals("")) {
            fehlerhafteComponenten.add(jTA_Artikelbeschreibung);
        }
        if (jCB_Kategorie.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Kategorie);
        }
        if (jTF_Einzelwert.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Einzelwert);
        }
        if (jTF_Bestellwert.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Bestellwert);
        }
        if (jCB_MwST.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_MwST);
        }
    }


    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     * 10.12.2014   Sen     ueberarbeitet
     * 18.01.2015   Sen     ueberarbeitet
     */
    /**
     * Schnittstellen Methode, die die Eingaben zurücksetzt, beim Zurücksetzen
     * wird auch die Hintergrundfarbe zurückgesetzt.
     */
    @Override
    public final void zuruecksetzen() {
        try {
            artikelnummer = this.dao.gibNaechsteArtikelnummer();
            jTF_Artikelnummer.setText("" + artikelnummer);
            jTF_Artikelname.setText("");
            jTF_Artikelname.setBackground(JTF_FARBE_STANDARD);
            jTA_Artikelbeschreibung.setText("");
            jTA_Artikelbeschreibung.setBackground(JTF_FARBE_STANDARD);
            jCB_Kategorie.setSelectedIndex(0);
            jCB_Kategorie.setBackground(JCB_FARBE_STANDARD);
            jTF_Einzelwert.setText("");
            jTF_Einzelwert.setBackground(JTF_FARBE_STANDARD);
            jTF_Bestellwert.setText("");
            jTF_Bestellwert.setBackground(JTF_FARBE_STANDARD);
            jCB_MwST.setSelectedIndex(0);
            jCB_MwST.setBackground(JCB_FARBE_STANDARD);

            fehlerhafteComponenten.clear();
            formularOK = true;
        } catch (NullPointerException e) {
        }
    }

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     * 01.12.2014   Sen     ueberarbeitet
     */
    /**
     * Schnittstellen Methode, die bei focusloost ueberprueft, ob Eingaben
     * korrekt sind.
     *
     * @param textfield textfield, von wo die Daten geprueft werden sollen
     * @param syntax Pruefsyntax(Regulaerer Ausdruck)
     * @param fehlermelgungtitel Titel der Fehlermeldung, die erzugt wird
     * @param fehlermeldung Text der Fehlermeldung
     */
    @Override
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax,
            String fehlermelgungtitel, String fehlermeldung) {
//            Prüfung, ob die Eingabe mit der Synstax passen
        if (!textfield.getText().matches(syntax)) {
            formularOK = false;
//            Eingabe nicht korrekt, also wird eine Fehlermeldung ausgegeben
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
//            Focus wird auf das Feld für die Eingabe des Einzelwertes
//            gesetzt und der Eingabefeld wird auf leer gesetzt.
            textfield.requestFocusInWindow();
            textfield.setText("");
        } else if (!textfield.getText().equals("")) {
//            Eingabe ist ok
            try {
//            Eingabe wird in ein double geparst    
                double einzelwert = nf.parse(textfield.getText()).doubleValue();
//                hinzugefuegt am 04.02 um 15:30 test
                formularOK = true;
//            der format wird angepasst und ausgegeben, Hintergrund des Feldes
//            wird auf normalgestzt    
                textfield.setText(nf.format(einzelwert));
                textfield.setBackground(JTF_FARBE_STANDARD);
            } catch (ParseException ex) {
//            Fehler beim Parsen
            }
//            hinzugefuegt am 04.02 um 15:30 test!
        } else {
            formularOK = true;
        }
    }

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Schnittstellen Methode, die am Ende der Speichern Aktion aufgerufen wird.
     * Aufgabe ist es, den Hintergrund der jeweiligen Componenten der List in
     * die uebergebene Farbe zu setzen.
     *
     * @param list liste mit den Componenten, wo der Hintergrund gefaerbt werden
     * soll
     * @param fehlermelgungtitel Titel der Fehlermeldung, dass nicht alle
     * eingaben gemacht wurden
     * @param fehlermeldung Text der Meldung
     * @param farbe Farbe, in die die Hintergruende gefaerbt werden sollen
     */
    @Override
    public void fehlEingabenMarkierung(ArrayList<Component> list,
            String fehlermelgungtitel, String fehlermeldung, Color farbe) {
//          fehlerhafteComponenten ist nicht leer
//          (es sind fehlerhafte Componenten vorhanden)
//          eine Meldung wird ausgegeben  
        JOptionPane.showMessageDialog(null, fehlermeldung, fehlermelgungtitel,
                JOptionPane.WARNING_MESSAGE);
//          an die erste fehlerhafte Componenten wird der Focus gesetzt  
        list.get(0).requestFocusInWindow();
//          ueber die fehlerhaften Komponenten wird iteriert und bei
//          allen fehlerhaften 
//          Componenten wird der Hintergrund in der fehlerhaften Farbe gefaerbt 
        for (int i = 0; i <= list.size() - 1; i++) {
            list.get(i).setBackground(farbe);
        }
//          ArrayList fue fehlerhafte Componenten wird geleert
        list.clear();
    }

    /*
     * Historie:
     * 08.12.2014   Sen     angelegt
     */
    /**
     * Methode, die beim Schließen des Fenster aufgerufen wird.
     */
    private void beendenEingabeNachfrage() {
//        varibablen fuer Titel und Text der erzeugten Meldung
        String meldung = "Möchten Sie die Eingaben verwerfen?";
        String titel = "Eingaben verwerfen";
//        Falls Titel des Fensters Artikel anlegen oder Artikel aendern
//        ist wird eine Nachfrage gemacht  
        if (this.getTitle().equals(ARTIKEL_ANLEGEN)) {
//            zunaechst wird ueberprueft, ob alle Eingaben getaetigt wurden
            ueberpruefen();
//            Falls anzahl fehlerhafter Componenten kleiner ist als max Anzahl 
//            von fehlerhaften Componenten
//            heist dass, dass Eingaben getaetigt wurden
            if (fehlerhafteComponenten.size()
                    < maxAnzahlFehlerhafterComponenten) {
//            Also nachfrage
                int antwort = JOptionPane.showConfirmDialog(null,
                        meldung, titel, JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (antwort == JOptionPane.YES_OPTION) {
                    zuruecksetzen();
                    this.setVisible(false);
                    zurueckInsHauptmenue();
                } else {
                    fehlerhafteComponenten.clear();
                }
            } else {
//            keine Eingaben getaetigt, direkt Fenster schließen 
                zuruecksetzen();
                this.setVisible(false);
                zurueckInsHauptmenue();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField4 = new javax.swing.JTextField();
        jTB_Menueleiste = new javax.swing.JToolBar();
        jB_Zurueck = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_AnzeigenAEndern = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        jS_Trenner = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jTF_Artikelnummer = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTF_Einzelwert = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTF_Bestandsmenge_FREI = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTF_Bestellwert = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jCB_MwST = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jTF_Artikelname = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTF_Bestandsmenge_RES = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTF_Bestandsmenge_ZULAUF = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTF_Bestandsmenge_VERKAUFT = new javax.swing.JTextField();
        jCB_Kategorie = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jSP_Artikelbeschreibung = new javax.swing.JScrollPane();
        jTA_Artikelbeschreibung = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();

        jTextField4.setText("jTextField4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Artikel anlegen");
        setPreferredSize(new java.awt.Dimension(800, 590));
        setRequestFocusEnabled(false);
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jTB_Menueleiste.setBorder(null);
        jTB_Menueleiste.setRollover(true);
        jTB_Menueleiste.setEnabled(false);

        jB_Zurueck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Home2.PNG"))); // NOI18N
        jB_Zurueck.setToolTipText("Hauptmenü");
        jB_Zurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ZurueckActionPerformed(evt);
            }
        });
        jTB_Menueleiste.add(jB_Zurueck);

        jB_Speichern.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Speichern.PNG"))); // NOI18N
        jB_Speichern.setToolTipText("Speichern");
        jB_Speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SpeichernActionPerformed(evt);
            }
        });
        jTB_Menueleiste.add(jB_Speichern);

        jB_AnzeigenAEndern.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Bearbeiten.PNG"))); // NOI18N
        jB_AnzeigenAEndern.setToolTipText("Bearbeiten");
        jB_AnzeigenAEndern.setActionCommand("Anzeigen/Ändern");
        jB_AnzeigenAEndern.setEnabled(false);
        jB_AnzeigenAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_AnzeigenAEndernActionPerformed(evt);
            }
        });
        jTB_Menueleiste.add(jB_AnzeigenAEndern);

        jB_Loeschen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Löschen.PNG"))); // NOI18N
        jB_Loeschen.setToolTipText("Löschen");
        jB_Loeschen.setEnabled(false);
        jB_Loeschen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_LoeschenActionPerformed(evt);
            }
        });
        jTB_Menueleiste.add(jB_Loeschen);

        jB_Suchen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Suche.PNG"))); // NOI18N
        jB_Suchen.setToolTipText("Allgemeine Suche");
        jB_Suchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuchenActionPerformed(evt);
            }
        });
        jTB_Menueleiste.add(jB_Suchen);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Artikelname*:");

        jTF_Artikelnummer.setText("1");
        jTF_Artikelnummer.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Artikelbeschreibung*:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Einzelwert*:");

        jTF_Einzelwert.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_EinzelwertFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_EinzelwertFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Bestandsmenge FREI:");

        jTF_Bestandsmenge_FREI.setEnabled(false);
        jTF_Bestandsmenge_FREI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_Bestandsmenge_FREIFocusLost(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Kategorie*:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Bestellwert*:");

        jTF_Bestellwert.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_BestellwertFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_BestellwertFocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("MwST Satz*:");

        jCB_MwST.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "0", "7", "19" }));
        jCB_MwST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_MwSTActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Artikel-ID:");

        jTF_Artikelname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_ArtikelnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_ArtikelnameFocusLost(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("€");

        jLabel10.setText("Stk.");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("€");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Allgemeine Daten:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Preisdaten:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Bestand:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Bestandsmenge RESERVIERT:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Bestandsmenge ZULAUF:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Bestandsmenge VERKAUFT:");

        jLabel19.setText("Stk.");

        jTF_Bestandsmenge_RES.setEnabled(false);

        jLabel20.setText("Stk.");

        jTF_Bestandsmenge_ZULAUF.setEnabled(false);

        jLabel21.setText("Stk.");

        jTF_Bestandsmenge_VERKAUFT.setEnabled(false);

        jCB_Kategorie.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "Kategorie 1", "Kategorie 2" }));
        jCB_Kategorie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_KategorieActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("%");

        jTA_Artikelbeschreibung.setColumns(5);
        jTA_Artikelbeschreibung.setRows(5);
        jTA_Artikelbeschreibung.setTabSize(2);
        jTA_Artikelbeschreibung.setPreferredSize(new java.awt.Dimension(166, 94));
        jTA_Artikelbeschreibung.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTA_ArtikelbeschreibungFocusLost(evt);
            }
        });
        jSP_Artikelbeschreibung.setViewportView(jTA_Artikelbeschreibung);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel12.setText("*Pflichtfelder");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jS_Trenner)
            .addComponent(jTB_Menueleiste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTF_Einzelwert, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTF_Bestellwert, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCB_MwST, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel14))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jCB_Kategorie, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(209, 209, 209)
                        .addComponent(jLabel15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel1))
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTF_Artikelname, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTF_Artikelnummer, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jSP_Artikelbeschreibung, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTF_Bestandsmenge_RES, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel19))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTF_Bestandsmenge_FREI, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(28, 28, 28)
                                .addComponent(jTF_Bestandsmenge_VERKAUFT, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTF_Bestandsmenge_ZULAUF, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)))))
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTB_Menueleiste, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jS_Trenner, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel8)
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTF_Artikelnummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTF_Bestandsmenge_FREI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_Artikelname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jTF_Bestandsmenge_RES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jSP_Artikelbeschreibung, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCB_Kategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel11)
                            .addComponent(jTF_Einzelwert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTF_Bestellwert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jCB_MwST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel20)
                            .addComponent(jTF_Bestandsmenge_ZULAUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTF_Bestandsmenge_VERKAUFT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     * Historie:
     * 20.12.2014   Sen     angelegt
     * 25.12.2014   Sen     ueberarbeitet
     * 30.12.2014   Sen     Funtkion fuer Sicht Artikel anlegen implementiert
     * 05.01.2015   Sen     Funtkion fuer Sicht Artikel aendern implementiert
     * 04.02.2015   Sen     Fehler beseitigt, z.B. wenn falscher Preis 
     *                      eingegeben wird und direkt auf speichern geklickt 
     *                      wird
     */
    /**
     * Methode für das Speichern der Daten. Falls die Sicht Aritkel anlegen
     * geoeffnet ist, wird eine Artikel angelegt. Ist Sicht Artikel aendern
     * geoffnet, wird der Artikel ueberschieben
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed
//      zunaechst werdne die Eingaben ueberprueft.    
        if (formularOK) {
            ueberpruefen();
//      falls fehlerhafteComponenten leer ist (es sind keine fehlerhaften
//      Componenten verfuegbar), 
//      werden die Eingaben in die entsprechenden Variablen gespeichert
            if (fehlerhafteComponenten.isEmpty()) {
//                  Sicht Artikel aendern ist geoffnet,
//                  also wird zunaechst der Artikel aus der Datenbank geladen.
                long artikelnr;
                String artikelname;
                String artikelbeschreibung;
                String kategorie;
                double einzelwert;
                double bestellwert;
                int mwst;
                int bestandsmengeFREI = 0;
                int bestandsmengeRESERVIERT = 0;
                int bestandsmengeZULAUF = 0;
                int bestandsmengeVERKAUFT = 0;
                artikelname = jTF_Artikelname.getText();
                artikelbeschreibung = jTA_Artikelbeschreibung.getText();
                kategorie = (String) jCB_Kategorie.getSelectedItem();

                try {
//                einzel- und bestelltwert sowie die mwst und die bestandsmenge
//                muessen geparst werden, da die dao Methode int und
//                double Typen moechte
                    einzelwert = nf.parse(jTF_Einzelwert.getText())
                            .doubleValue();
                    bestellwert = nf.parse(jTF_Bestellwert.getText())
                            .doubleValue();
                    artikelnr = nf.parse(jTF_Artikelnummer.getText())
                            .longValue();

                    mwst = nf.parse((String) jCB_MwST.getSelectedItem())
                            .intValue();
//                Ueberpruefung in welche Sicht wir sind
                    if (this.getTitle().equals(ARTIKEL_ANLEGEN)) {
//                  Sicht Artikel anlegen-->
//                  neuer Artikel wird in datenbank geschrieben
                        this.dao.erstelleArtikel(kategorie, artikelname,
                                artikelbeschreibung,
                                einzelwert,
                                bestellwert,
                                mwst, bestandsmengeFREI,
                                bestandsmengeRESERVIERT,
                                bestandsmengeZULAUF,
                                bestandsmengeVERKAUFT);
//              Meldung fuer die Statuszeile wird angepasst
                        statuszeile = "Artikel mit der Artikel-ID "
                                + artikelnr + " wurde erfolgreich angelegt.";
                        this.hauptFenster.setzeStatusMeldung(statuszeile);
//              das Formular wird zurueckgesetzt  
                        zuruecksetzen();
                    } else {
//                  Artikel aus Datenbank ist Variable artikelVorher  
                        Artikel artikelVorher = this.dao.gibArtikel(artikelnr);
//                  vergleich Artikel erzeugen mit den Eingabedaten
                        Artikel artikelNachher = new Artikel(
                                artikelVorher.getKategorie(),
                                artikelname, artikelbeschreibung,
                                einzelwert, bestellwert, mwst,
                                artikelVorher.getFrei(),
                                artikelVorher.getReserviert(),
                                artikelVorher.getZulauf(),
                                artikelVorher.getVerkauft());
//                  ArtikelID des Nachher Artikel setzen
                        artikelNachher.setArtikelID(artikelnr);
                        if (artikelVorher.getKategorie().getKategoriename()
                                .equals(kategorie)
                                && artikelVorher.equals(artikelNachher)) {
//                          Falls die Kategorienamen und auch die Artikel selbst
//                          gleich sind ist keine Veränderung
                            JOptionPane.showMessageDialog(null,
                                    "Es wurden keine Änderungen gemacht!",
                                    "Keine Änderungen",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
//                     etwas ist nicht gleich
//                     Abfrage, ob Änderungen gespeichert werden sollen
                            int antwort = JOptionPane.showConfirmDialog(null,
                                    MELDUNG_AENDERUNGEN_SPEICHERN,
                                    ARTIKEL_AENDERN, JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (antwort == JOptionPane.YES_OPTION) {
//                              falls ja, wird der Artikel geaendert
//                              Meldung fuer die Statuszeile wird angepasst
                                statuszeile = "Artikel mit der Artikel-ID "
                                        + artikelnr
                                        + " wurde erfolgreich geändert.";
                                this.hauptFenster.setzeStatusMeldung(statuszeile);
                                this.dao.aendereArtikel(artikelnr, kategorie,
                                        artikelname, artikelbeschreibung,
                                        einzelwert,
                                        bestellwert,
                                        mwst, artikelVorher.getFrei());
                                zuruecksetzen(); // Formular zuruecksetzen
                                this.setVisible(false);
                                zurueckInsHauptmenue();
                            } else {
//                              Nein button wird geklickt, keine Aktion nur
//                              fehlerhafte Komponenten müssen geleert werden
                                fehlerhafteComponenten.clear();
                            }
                        }

                    }
//              Fehler abfangen
                } catch (ApplicationException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(),
                            FEHLER, JOptionPane.ERROR_MESSAGE);
                } catch (ParseException | NullPointerException e) {
                }
            } else {
//          fehlerhafteComponenten ist nicht leer
//          (es sind fehlerhafte Componenten vorhanden)
//          wird methode fuer das Markieren ausgefuehrt
                fehlEingabenMarkierung(fehlerhafteComponenten,
                        TITEL_PFLICHTFELDER_AUSFUELLEN,
                        MELDUNG_PFLICHTFELDER_AUSFUELLEN, FARBE_FEHLERHAFT);
            }
        }
//      Variable wird wieder auf true gesetzt,
//      da nochmals eine Pruefung stattfindet 
        formularOK = true;
    }//GEN-LAST:event_jB_SpeichernActionPerformed

    /*
     * Historie:
     * 27.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTF_ArtikelnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_ArtikelnameFocusLost
        if (!jTF_Artikelname.getText().equals("")) {
            jTF_Artikelname.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_ArtikelnameFocusLost

    /*
     * Historie:
     * 27.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jCB_KategorieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_KategorieActionPerformed
        if (jCB_Kategorie.getSelectedIndex() != 0) {
            jCB_Kategorie.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_KategorieActionPerformed

    /*
     * Historie:
     * 27.11.2014   Sen     angelegt
     */
    /**
     * Methode wür die Überprüfung der Eingabe des Einzelwertes.
     */
    private void jTF_EinzelwertFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_EinzelwertFocusLost
//      Aufruf der Methode zur Ueberpruefung
        ueberpruefungVonFocusLost(jTF_Einzelwert, PREUFUNG_PREIS,
                TITEL_FEHLERHAFTE_EINGABE, MELDUNG_EINZELWERT_FALSCH);
    }//GEN-LAST:event_jTF_EinzelwertFocusLost

    /*
     * Historie:
     * 27.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTF_BestellwertFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_BestellwertFocusLost
//      Aufruf der Methode zur Ueberpruefung
        ueberpruefungVonFocusLost(jTF_Bestellwert, PREUFUNG_PREIS,
                TITEL_FEHLERHAFTE_EINGABE, MELDUNG_EINZELWERT_FALSCH);
    }//GEN-LAST:event_jTF_BestellwertFocusLost

    /*
     * Historie:
     * 27.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jCB_MwSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_MwSTActionPerformed
        if (jCB_MwST.getSelectedIndex() != 0) {
            jCB_MwST.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_MwSTActionPerformed

    /*
     * Historie:
     * 27.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTF_Bestandsmenge_FREIFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_Bestandsmenge_FREIFocusLost
        if (!jTF_Bestandsmenge_FREI.getText().equals("")) {
            jTF_Bestandsmenge_FREI.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_Bestandsmenge_FREIFocusLost

    /*
     * Historie:
     * 27.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTA_ArtikelbeschreibungFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTA_ArtikelbeschreibungFocusLost
        if (!jTA_Artikelbeschreibung.getText().equals("")) {
            jTA_Artikelbeschreibung.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTA_ArtikelbeschreibungFocusLost

    /*
     * Historie:
     * 27.11.2014   Terrasi     angelegt
     * 20.01.2015   Sen         ueberarbeitet
     * 03.02.2015   Sen         grundlegend ueberarbeitet     
     * 04.02.2015   Sen         Fehler beseitigt, z.B. wenn falscher Preis eingegeben wird 
     *                          und direkt auf zurueck geklickt wird
     */
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt Event, der ausgeloest wird
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
//      Falls formular ok ist, wird die Zurueck Aktion durchgefuehrt  
        if (formularOK) {
            if (this.getTitle().equals(ARTIKEL_ANLEGEN)) {
                beendenEingabeNachfrage();
            } else if (this.getTitle().equals(ARTIKEL_AENDERN)) {
//      zunaechst werdne die Eingaben ueberprueft.    
                ueberpruefen();
//      falls fehlerhafteComponenten leer ist 
//      (es sind keine fehlerhaften Componenten verfuegbar), 
//      werden die Eingaben in die entsprechenden Variablen gespeichert
                if (fehlerhafteComponenten.isEmpty()) {
                    String artikelname;
                    String artikelbeschreibung;
                    String kategorie;
                    double einzelwert;
                    double bestellwert;
                    int mwst;
                    int bestandsmengeFREI = 0;
                    int bestandsmengeRESERVIERT = 0;
                    int bestandsmengeZULAUF = 0;
                    int bestandsmengeVERKAUFT = 0;
                    artikelname = jTF_Artikelname.getText();
                    artikelbeschreibung = jTA_Artikelbeschreibung.getText();
                    kategorie = (String) jCB_Kategorie.getSelectedItem();

                    try {
//                einzel- und bestelltwert sowie die mwst und die bestandsmenge
//                muessen geparst werden, da die dao Methode int und
//                double Typen moechte
                        einzelwert = nf.parse(jTF_Einzelwert.getText())
                                .doubleValue();
                        bestellwert = nf.parse(jTF_Bestellwert.getText())
                                .doubleValue();
                        mwst = nf.parse((String) jCB_MwST.getSelectedItem())
                                .intValue();
//                Ueberpruefung in welche Sicht wir sind
                        if (this.getTitle().equals(ARTIKEL_AENDERN)) {
//                  Sicht Artikel aendern ist geoffnet, also
//                  wird zunaechst der Artikel aus der Datenbank geladen.
                            long artikelnr = nf.parse(jTF_Artikelnummer
                                    .getText()).longValue();
//                  Artikel aus Datenbank ist Variable artikelVorher  
                            Artikel artikelVorher = this.dao.gibArtikel(artikelnr);
//                  vergleich Artikel erzeugen mit den Eingabedaten
                            Artikel artikelNachher = new Artikel(
                                    artikelVorher.getKategorie(),
                                    artikelname, artikelbeschreibung,
                                    einzelwert, bestellwert, mwst,
                                    artikelVorher.getFrei(),
                                    artikelVorher.getReserviert(),
                                    artikelVorher.getZulauf(),
                                    artikelVorher.getVerkauft());
//                  ArtikelID des Nachher Artikel setzen
                            artikelNachher.setArtikelID(artikelnr);
                            if (artikelVorher.getKategorie().getKategoriename()
                                    .equals(kategorie)
                                    && artikelVorher.equals(artikelNachher)) {
//                            zuruecksetzen(); // Formular zuruecksetzen
                                this.setVisible(false);
                                zurueckInsHauptmenue();
                            } else {
//                     etwas ist nicht gleich
//                     Abfrage, ob Änderungen gespeichert werden sollen
                                int antwort = JOptionPane.showConfirmDialog(
                                        null, MELDUNG_AENDERUNGEN_SPEICHERN,
                                        ARTIKEL_AENDERN,
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
                                if (antwort == JOptionPane.YES_OPTION) {
//                                  Meldung fuer die Statuszeile wird angepasst
                                    statuszeile = "Artikel mit der Artikel-ID "
                                            + artikelnr
                                            + " wurde erfolgreich geändert. ";
                                    this.hauptFenster
                                            .setzeStatusMeldung(statuszeile);
//                                  falls ja, wird der Artikel geaendert
                                    this.dao.aendereArtikel(artikelnr,
                                            kategorie, artikelname,
                                            artikelbeschreibung, einzelwert,
                                            bestellwert, mwst,
                                            artikelVorher.getFrei());
                                    this.setVisible(false);
                                    zurueckInsHauptmenue();
                                } else if (antwort == JOptionPane.CLOSED_OPTION) {
//                                 das x wird geklickt, es soll nichts passieren
                                } else {
                                    this.setVisible(false);
                                    zurueckInsHauptmenue();
                                }
                            }
                        }
//                  Fehler abfangen
                    } catch (ApplicationException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(),
                                FEHLER, JOptionPane.ERROR_MESSAGE);
                    } catch (ParseException | NullPointerException e) {
                    }
                } else {
//                     etwas ist nicht gleich
//                     Abfrage, ob Änderungen gespeichert werden sollen
                    int antwort = JOptionPane.showConfirmDialog(null,
                            MELDUNG_AENDERUNGEN_SPEICHERN, ARTIKEL_AENDERN,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (antwort == JOptionPane.YES_OPTION) {
                        fehlEingabenMarkierung(fehlerhafteComponenten,
                                TITEL_PFLICHTFELDER_AUSFUELLEN,
                                MELDUNG_PFLICHTFELDER_AUSFUELLEN,
                                FARBE_FEHLERHAFT);
                    } else {
                        this.setVisible(false); // diese Sicht ausblenden 
                        zurueckInsHauptmenue();
                    }
                }
            } else {
                this.setVisible(false); // diese Sicht ausblenden 
                zurueckInsHauptmenue();
            }
        }
//      Variable wird wieder auf true gesetzt, da nochmals eine Pruefung stattfindet 
        formularOK = true;
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*
     * Historie:
     * 03.02.2015   Sen     angelegt
     */
    /**
     * Methode, die das zurueckspringen in das Hauptmenue ermoeglicht.
     */
    private void zurueckInsHauptmenue() {
        c = null;   //Initialisierung der Componentspeichervariable
//        Erhalten über GUIFactorymethode die letzte aufgerufene
//        View und speichern diese in Variable
        c = this.factory.zurueckButton();
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        c.setVisible(true);// Übergebene Component wird sichtbar gemacht
    }

    /*
     * Historie:
     * 12.12.2014   Sen     angelegt
     */
    /**
     *
     * @param evt Event, der ausgeloest wird
     */
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        beendenEingabeNachfrage();
    }//GEN-LAST:event_formInternalFrameClosing

    /*
     * Historie:
     * 29.12.2014   Sen     angelegt
     * 04.02.2015   Sen     Fehler beseitigt, z.B. wenn falscher Preis
     *                      eingegeben wird und direkt auf loeschen geklickt
     *                      wird
     */
    /**
     * Methode fuer das Loeschen eines Artikel.
     *
     * @param evt Event der ausgeloest wird
     */
    private void jB_LoeschenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_LoeschenActionPerformed
//       Loeschen geht nur in der Sicht Artikel aendern
        String titel = "Artikel löschen";
        String text = "Soll der Artikel wirklich gelöscht werden?";
//      erst wenn das Formular ok ist, wird die Loeschen Aktion durchgefuehrt 
        if (formularOK) {
            if (this.getTitle().equals(ARTIKEL_AENDERN)) {
                try {
//                 Artikel wird aus Datenbank geladen, 
//                 Nachfrage, ob er wirklick geloescht werden soll, 
//                 falls ja wird er geloescht
                    long artikelnr = nf.parse(jTF_Artikelnummer.getText())
                            .longValue();
                    int antwort = JOptionPane.showConfirmDialog(null,
                            text, titel, JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (antwort == JOptionPane.YES_OPTION) {
                        this.dao.loescheArtikel(artikelnr);
//                      Meldung fuer die Statuszeile wird angepasst
                        statuszeile = "Artikel mit der Artikel-ID "
                                + artikelnr + " wurde erfolgreich gelöscht. ";
                        this.hauptFenster.setzeStatusMeldung(statuszeile);
//                    jB_ZurueckActionPerformed(evt);
                        zurueckInsHauptmenue();
                    }
                } catch (ApplicationException e) {
//                  Ein Fehler ist aufgereten, dieser wird ausgegeben  
                    JOptionPane.showMessageDialog(null, e.getMessage(),
                            FEHLER, JOptionPane.ERROR_MESSAGE);
                } catch (ParseException e) {
                }
            }
        }
//      Variable wird wieder auf true gesetzt,
//      da nochmals eine Pruefung stattfindet 
        formularOK = true;
    }//GEN-LAST:event_jB_LoeschenActionPerformed

    /*
     * Historie:
     * 15.01.2015   Sen     angelegt
     */
    /**
     * Methode fuer den Aufruf der Suche Maske.
     *
     * @param evt Event der ausgeloest wird
     */
    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        this.hauptFenster.rufeSuche(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed

    /*
     * Historie:
     * 16.01.2015   Sen     angelegt
     */
    /**
     * Methode fuer den Button Anzeigen/AEnden.
     *
     * @param evt Event der ausgeloest wird
     */
    private void jB_AnzeigenAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AnzeigenAEndernActionPerformed
        if (this.getTitle().equals(ARTIKEL_ANZEIGEN)) {
//          ist Sicht Artikel anzeigen geoffnet, muss wird
//          die Sicht in Artikel aendern aendern
            setzFormularInArtikelAEndernFuerButton();
        }
    }//GEN-LAST:event_jB_AnzeigenAEndernActionPerformed

    /*
     * Historie:
     * 16.01.2015   Sen     angelegt
     */
    /**
     * Methode, die dafuer sorgt, dass der vorhandene Text beim Eintritt des
     * Focuses selektiert wird.
     *
     * @param evt Event der ausgeloest wird
     */
    private void jTF_ArtikelnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_ArtikelnameFocusGained
        jTF_Artikelname.selectAll();
    }//GEN-LAST:event_jTF_ArtikelnameFocusGained

    /*
     * Historie:
     * 16.01.2015   Sen     angelegt
     */
    /**
     * Methode, die dafuer sorgt, dass der vorhandene Text beim Eintritt des
     * Focuses selektiert wird.
     *
     * @param evt Event der ausgeloest wird
     */
    private void jTF_EinzelwertFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_EinzelwertFocusGained
        jTF_Einzelwert.selectAll();
    }//GEN-LAST:event_jTF_EinzelwertFocusGained

    /*
     * Historie:
     * 16.01.2015   Sen     angelegt
     */
    /**
     * Methode, die dafuer sorgt, dass der vorhandene Text beim Eintritt des
     * Focuses selektiert wird.
     *
     * @param evt Event der ausgeloest wird
     */
    private void jTF_BestellwertFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_BestellwertFocusGained
        jTF_Bestellwert.selectAll();
    }//GEN-LAST:event_jTF_BestellwertFocusGained

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return textfield der Artikelnummer
     */
    public JTextField gibjTF_Artikelnummer() {
        return jTF_Artikelnummer;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return textfield der Artikelname
     */
    public JTextField gibjTF_Artikelname() {
        return jTF_Artikelname;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return texttarea der Artikelbeschreibung
     */
    public JTextArea gibjTA_Artikelbeschreibung() {
        return jTA_Artikelbeschreibung;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return combobox der Artikelkategorie
     */
    public JComboBox gibjCB_Artikelkategorie() {
        return jCB_Kategorie;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return textfield des Einzelwerts
     */
    public JTextField gibjTF_Einzelwert() {
        return jTF_Einzelwert;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return textfield des Bestellwerts
     */
    public JTextField gibjTF_Bestellwert() {
        return jTF_Bestellwert;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return combobox der MwSt
     */
    public JComboBox gibjCB_MwST() {
        return jCB_MwST;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return textfield der Bestandsmenge FREI
     */
    public JTextField gibjTF_BestandsmengeFREI() {
        return jTF_Bestandsmenge_FREI;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return textfield der Bestandsmenge RESERVIERT
     */
    public JTextField gibjTF_BestandsmengeRESERVIERT() {
        return jTF_Bestandsmenge_RES;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return textfield der Bestandsmenge ZULAUF
     */
    public JTextField gibjTF_BestandsmengeZULAUF() {
        return jTF_Bestandsmenge_ZULAUF;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht Artikel aendern Einstieg.
     *
     * @return textfield der Bestandsmenge VERKAUFT
     */
    public JTextField gibjTF_BestandsmengeVERKAUFT() {
        return jTF_Bestandsmenge_VERKAUFT;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * Methode, die das Formular in die Sicht Artikel anlegen aendert.
     */
    public void setzeFormularInArtikelAnlegen() {
        zuruecksetzen();
        this.setTitle(ARTIKEL_ANLEGEN);
        jTF_Artikelname.setEnabled(true);
        jTA_Artikelbeschreibung.setEnabled(true);
        jCB_Kategorie.setEnabled(true);
        jTF_Einzelwert.setEnabled(true);
        jTF_Bestellwert.setEnabled(true);
        jCB_MwST.setEnabled(true);

        jB_Speichern.setEnabled(true);
        jB_AnzeigenAEndern.setEnabled(false);
        jB_Loeschen.setEnabled(false);

//      Übergibt der Referenz des Hauptfensters das Internaframe
        this.hauptFenster.setzeComponent(this);
//      setzt die Bestandsmengen  
        jTF_Bestandsmenge_FREI.setText("0");
        jTF_Bestandsmenge_RES.setText("0");
        jTF_Bestandsmenge_VERKAUFT.setText("0");
        jTF_Bestandsmenge_ZULAUF.setText("0");
    }

    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    /**
     * Methode, die das Formular in die Sicht Artikel aendern aendert.
     */
    public void setzeFormularInArtikelAEndern() {
        zuruecksetzen();
        setzFormularInArtikelAEndernFuerButton();
//      Übergibt der Referenz des Hauptfensters das Internaframe
        this.hauptFenster.setzeComponent(this);
    }

    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     */
    /**
     * Methode wird aufgerufen, wenn auf Button Anzeigen/Aendern geklickt wird,
     * das Formular wird nicht zurueckgesetzt, deswegen musste eine extra
     * Methode geschrieben werden.
     */
    private void setzFormularInArtikelAEndernFuerButton() {
        this.setTitle(ARTIKEL_AENDERN);
        jTF_Artikelname.setEnabled(true);
        jTA_Artikelbeschreibung.setEnabled(true);
        jCB_Kategorie.setEnabled(true);
        jTF_Einzelwert.setEnabled(true);
        jTF_Bestellwert.setEnabled(true);
        jCB_MwST.setEnabled(true);

        jB_Speichern.setEnabled(true);
        jB_AnzeigenAEndern.setEnabled(false);
        jB_Loeschen.setEnabled(true);
    }

    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    /**
     * Methode, die das Formular in die Sicht Artikel anzeigen aendert.
     */
    public void setzeFormularInArtikelAnzeigen() {
        zuruecksetzen();
        setzFormularInArtikelAnzeigenFuerButton();
//      Übergibt der Referenz des Hauptfensters das Internaframe
        this.hauptFenster.setzeComponent(this);
    }

    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     */
    /**
     * Methode wird aufgerufen, wenn auf Button Anzeigen/Aendern geklickt wird,
     * das Formular wird nicht zurueckgesetzt, deswegen musste eine extra
     * Methode geschrieben werden.
     */
    private void setzFormularInArtikelAnzeigenFuerButton() {
        this.setTitle(ARTIKEL_ANZEIGEN);
        jTF_Artikelname.setEnabled(false);
        jTA_Artikelbeschreibung.setEnabled(false);
        jCB_Kategorie.setEnabled(false);
        jTF_Einzelwert.setEnabled(false);
        jTF_Bestellwert.setEnabled(false);
        jCB_MwST.setEnabled(false);

        jB_Speichern.setEnabled(false);
        jB_AnzeigenAEndern.setEnabled(true);
        jB_Loeschen.setEnabled(false);
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * setter Methode, die fuer die Suche benoetigt wird. In der Suche kann eine
     * Kategorie gesucht werden und in in das Formular eingetargen werden
     *
     * @param kategorie Datum Name Was 17.01.2015 SEN angelegt
     */
    public void setzteKategorie(Artikelkategorie kategorie) {
        jCB_Kategorie.setSelectedItem(kategorie.getKategoriename());
    }

    /*
     * Historie:
     * 17.01.2015   Sen     angelegt
     */
    /**
     * setzeMethode, die aus der Suche aufgerufen wird. Es wird die Sicht Atikel
     * Anzeigen aufgerufen und der gesuchte Artikel angezeigt
     *
     * @param artikel artikel aus der Suche
     */
    public void zeigeArtikelAusSucheAn(Artikel artikel) {
        setzeFormularInArtikelAnzeigen();
        jTF_Artikelnummer.setText("" + artikel.getArtikelID());
        jTF_Artikelname.setText(artikel.getArtikeltext());
        jTA_Artikelbeschreibung.setText(artikel.getBestelltext());
        jCB_Kategorie.setSelectedItem(artikel.getKategorie()
                .getKategoriename());
        jTF_Einzelwert.setText("" + nf.format(artikel.getVerkaufswert()));
        jTF_Bestellwert.setText("" + nf.format(artikel.getEinkaufswert()));
        jCB_MwST.setSelectedItem("" + artikel.getMwST());
        jTF_Bestandsmenge_FREI.setText("" + artikel.getFrei());
        jTF_Bestandsmenge_RES.setText("" + artikel.getReserviert());
        jTF_Bestandsmenge_ZULAUF.setText("" + artikel.getZulauf());
        jTF_Bestandsmenge_VERKAUFT.setText("" + artikel.getVerkauft());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_AnzeigenAEndern;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JComboBox jCB_Kategorie;
    private javax.swing.JComboBox jCB_MwST;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jSP_Artikelbeschreibung;
    private javax.swing.JSeparator jS_Trenner;
    private javax.swing.JTextArea jTA_Artikelbeschreibung;
    private javax.swing.JToolBar jTB_Menueleiste;
    private javax.swing.JTextField jTF_Artikelname;
    private javax.swing.JTextField jTF_Artikelnummer;
    private javax.swing.JTextField jTF_Bestandsmenge_FREI;
    private javax.swing.JTextField jTF_Bestandsmenge_RES;
    private javax.swing.JTextField jTF_Bestandsmenge_VERKAUFT;
    private javax.swing.JTextField jTF_Bestandsmenge_ZULAUF;
    private javax.swing.JTextField jTF_Bestellwert;
    private javax.swing.JTextField jTF_Einzelwert;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
