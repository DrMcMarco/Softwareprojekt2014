package GUI_Internalframes;

import DAO.ApplicationException;
import DAO.DataAccessObject;
import DTO.Auftragskopf;
import DTO.Auftragsposition;
import DTO.Zahlungskondition;
import javax.swing.JOptionPane;
import Documents.*;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTextField;
import Interfaces.*;
import JFrames.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.table.DefaultTableModel;


/* 10.12.2014 Terrasi, angelegt, Sschnittstellenimplementierung, Dokumentation und Logiküberarbetung */
/* 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button */
/* 06.01.2015 Terrasi, Anwendungslogik für das ändern und anzeigen eines Auftragskopfs. */
/* 08.01.2015 Terrasi, Überarbeitung der Anwendungslogik für anzeigen/ändern Status und das
 * hinzufügen von weiteren Funktion. 
 /* 13.01.2015 Terrasi, Implementierung der DAO-Methoden zum anlegen eines 
 Auftragskopfs und von Auftragspositionen. Desweiteren deren wiedergabe in 
 ener Tabelle. */
/* 14.01.2015 Terrasi, Implementierung der Löschmethode der DAO und Überprüfung
 der Eingabefelder überarbeitet.*/
/* 17.01.2015 Schulz Such button und setMethode Artikel eingefügt.*/
/* 17.01.2015 Terrasi, Überarbeitung der ladeZahlungskonditionen-Methode.*/
/**
 *
 * @author Luca Terrasi
 *
 * Die Maske um einen Auftragskopf anzulegen wird erzeugt. Es können alle
 * Eingaben die für einen Auftragskopf notwendig sind eingegeben werden. Es
 * findet eine Überprüfung auf Vollständigkeit der eingaben statt und es wird
 * dementsprechend eine Meldung ausgegeben. Desweitere besitzt die Maske die
 * Funktionen um einen Auftrgaskopf mit den dazu angelegten Auftragspositionen
 * zu löschen, bearbeiten und anzulegen. Die Maske besitzt anhand von Methoden
 * auch die Fähigkeit sich in verschiedenen Stati anzeigen zu lassen.
 *
 */
public class AuftragskopfAnlegen extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {
    /*
     Deklaration von Speicher und Referenzvariablen.
     */

    Component letzteComponent;
    GUIFactory factory;
    DataAccessObject dao;
    InterfaceMainView hauptFenster;
    DefaultTableModel dtm;

    /*
     Variablen für die erzeugung der Spalten und Zeilen für die Auftragspositins-
     tabelle.
     */
    Vector spaltenNamen;
    Vector zeilen;

    /*
     Hilfsvaribalen
     */
    private Collection<Zahlungskondition> zahlungskondditionAusDatenbank;
    private ArrayList<String> listeVonZahlungskonditionen = new ArrayList<>();
    private ArrayList<String> zahlungskonditionFuerCombobox = new ArrayList<>();

    private ArrayList<Auftragsposition> auftragspositionen = new ArrayList<>();

    Auftragsposition position; // Objekt Erzeugung für die Positionstabelle.

    private String status = "";
    private String typ;
    private HashMap<Long, Integer> artikel;
    private String auftragsText;
    private Long geschaeftspartnerID;
    private Date abschlussdatum;
    private Date lieferdatum;

    /*
     Varibalendefinition für Datumseingaben.
     */
    public Date heute;// heutiges Datum
    public SimpleDateFormat format; //Umwandler für Datum

    /*
     Variable für die Zählung von Auftragspositionen.
     */
//    private Integer positionsZaehler = 1;

    /*
     Syntaxvariablen
     */
    private static final String PEIS_SYNTAX = "|(\\d*,?\\d{1,2})|(\\d{0,3}(\\.\\d{3})*,?\\d{1,2})";
    private static final String POSITIONSNUMMER_SYNTAX = "|\\d{1,9}?";
    private static final String GESCHAEFTSPARTNER_SYNTAX = "|\\d{1,8}?";


    /*
     Ausgabetexte für Meldungen
     */
    //Ausgaben für Fehlermeldungen.
    final String FEHLERMELDUNG_TITEL = "Fehlerhafte Eingabe";
    final String FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL = "Fehlerhafte Eingabe";
    final String FEHLERMELDUNG_LIEFERDATUM_TEXT = "Das Lieferdatum darf nicht in der Vergangenheit liegen.\n"
            + "Bitte überprüfen Sie ihr Lieferdatum";
    final String FEHLERMELDUNG_ABSCHLUSSDATUM_TEXT = "Das Abschlussdatum darf nicht in der Vergangenheit liegen.\n"
            + "Bitte überprüfen Sie ihr Abschlussdatum";
    final String FEHLERMELDUNG_ABSCHLUSSDATUMVORLIEFERDATUM_TEXT = "Das Abschlussdatum darf vor dem Lieferdatum liegen.\n"
            + "Bitte überprüfen Sie ihr Abschlussdatum";
    final String FEHLERMELDUNG_UNGUELTIGESDATUM = "Üngültigesdatum. Bitte geben Sie eine gültiges Datum ein. (z.B 01.01.2016)";
    final String fehlermeldung_unvollstaendig_auftragskopf_text = "Es wurden nicht alle Eingaben getätigt.\n"
            + "Bitte geben Sie die benötigten Eingaben für den Auftragskopf in die markierten Eingabefelder ein.";
    final String fehlermeldung_unvollstaendig_auftragsposition_text = "Es wurden nicht alle Eingaben getätigt.\n"
            + "Bitte geben Sie die benötigten Eingaben für eine Auftragsposition in die markierten Eingabefelder ein.";
    final String fehlermeldung_unvollstaendig_keineauftragsposZumAKopf_text = "Es muss mindestens eine Auftragsposition"
            + "zum Auftragskopf angelegt "
            + "\n werden, damit Sie einen Auftrag anlegen können.\n Bitte geben sie alle nötigen Eingaben ein.";
    final String fehlermeldung_unvollstaendig = "Es wurden unvollständig Eingaben für eine Auftragsposition getätigt.\n"
            + "Wollen Sie eine Auftragsposition zum Auftragskopf anlegen?.";
    final String fehlermeldungPreis_text = "Der eingegebene Preis ist nicht richtig! "
            + "\n Bitte geben Sie den Preis richtig ein. (z.B. 99,99 oder 99.999,99)";
    final String fehlermeldungMaterial_text = "Die eingegebene Materialnummer ist nicht richtig! "
            + "\n Bitte geben Sie eine gültige Materialnummer, die aus acht Ziffern besteht, ein. (z.B. 1234567)";
    final String fehlermeldungPositionsnummer_text = "Die eingegebene Positionsnummer ist nicht richtig! "
            + "\n Bitte geben Sie eine gültige Positionsnummer ein. (z.B. 1 oder 999999999)";
    final String fehlermeldungGeschaeftspartnerID_text = "Keine Gültige Geschäftspartner-ID. \n"
            + " Bitte geben sie eine gültige Geschäftspartner-ID ein.";
    final String fehlermeldungKeinePositionGewaehlt = " Bitte eine Position wählen.";

    //Ausgaben bei Systemmeldungen.
    final String ERFOLGREICHEANMELDUNG = "Ihr Auftrags wurde erfolgreich angelegt.";
    final String ERFOLGREICHESLOESCHEN = "Der Auftrag wurde erfolgreich gelöscht.";
    final String LOESCHENMELDUNG = "Sind Sie sicher, dass sie den Auftrag löschen möchten ?";
    final String LOESCHEN_TITEL = "Löschen eines Auftrags";
    final String ERFOLGREICHGEAENDERT_TEXT = "Der Auftrag wurde erfolgreich angelegt.";
    final String ERFOLGREICHGEAENDERT_TITEL = "Auftrag geändert";

//    final String KEINEVERFUEGBARKEIT_TEXT = ""
    final String aenderungVonDaten_Text = "Es wurden Daten geändert. Wollen sie wirklich"
            + "die Daten überspeichern?";
    final String aenderungVonDaten_Titel = "Änderung von Daten";
    final String keineAenderungen_Titel = "Auftragskopf bereits angelegt";
    final String keineAenderung_Text = "Der Auftrag existiert bereits.";
    /*
     Speichervariablen
     */
    ArrayList<Component> fehlendeEingaben;// ArrayList für Eingabefelder des Auftragkopfes.
    ArrayList<Component> fehlendeEingabenAuftragsposition;// ArrayList für die Eingabefelder der Auftragsposition.
    Double gesamtAuftragswert = 0.00;
    Double einzelwert = 0.00;
    Double summenWertFuerPos = 0.00;

    /*
     Variablen für Farben
     */
    Color warningfarbe = Color.YELLOW;
    Color hintergrundfarbe = Color.WHITE;

    /*
     Variable für die Erzeugung der Spaltenüberschriften der Auftragspositionstabelle.
     */
    final String[] tabelle = {"Positionsnummer", "Materialnummer", "Menge", "Einzelwert", "Erfassungsdatum"};

    /**
     * Creates new form AuftragskopfAnlegen
     */
    public AuftragskopfAnlegen(GUIFactory factory, InterfaceMainView mainView) {
        initComponents();

        this.factory = factory;
        this.dao = factory.getDAO();
        this.hauptFenster = mainView;
        artikel = new HashMap<>();

        heute = new Date();
        lieferdatum = heute;
        abschlussdatum = heute;

        //Initialisierung der Speichervariblen
        listeVonZahlungskonditionen = new ArrayList<>();
        fehlendeEingaben = new ArrayList<Component>();
        fehlendeEingabenAuftragsposition = new ArrayList<Component>();

//        position = new Auftragsposition;
        // Variable, die ein Datum in ein vorgegebenes Format umwandelt.
        format = new SimpleDateFormat("dd.MM.yyyy");// Format dd.MM.yyyy

        /*
         Voreingaben werden in die Eingabefelder gesetzt.
         */
//        ladeZahlungskonditionenAusDatenbank();
//        zahlungskonditionen_jComboBox.setModel(new DefaultComboBoxModel(zahlungskonditionFuerCombobox.toArray()));
        erfassungsdatum_jFormattedTextField.setText(format.format(heute));
        erfassungsdatum_auftragsposition_jFormattedTextField.setText(format.format(heute));
        lieferdatum_jFormattedTextField.setText(format.format(heute));
        abschlussdatum_jFormattedTextField.setText(format.format(heute));


        /*
         Zuweisung von verschiedenen Documents 
         */
        geschaeftspartner_jTextField.setDocument(new UniversalDocument("1234567890", false));
        positionsnummer_jTextField.setDocument(new UniversalDocument("1234567890", false));
        auftragswert_jTextField.setDocument(new UniversalDocument("1234567890,.", false));
        einzelwert_jTextField.setDocument(new UniversalDocument("1234567890,.", false));
        menge_jTextField.setDocument(new UniversalDocument("1234567890", false));

        /*
         Radiobuttons und Zuweisung in eine Radiobuttongroup. 
         Es wird immer ein Radiobutton selektiert.
         */
        erfasst_jRadioButton.setActionCommand("Erfasst");
        freigegeben_jRadioButton.setActionCommand("Freigegeben");
        abgeschlossen_jRadioButton.setActionCommand("Abgeschlossen");
        buttonGroup1.add(erfasst_jRadioButton);
        buttonGroup1.add(freigegeben_jRadioButton);
        buttonGroup1.add(abgeschlossen_jRadioButton);

        status = erfasst_jRadioButton.getText();

        dtm = new DefaultTableModel();
        spaltenNamen = new Vector();

        for (String s : tabelle) {
            spaltenNamen.addElement(s);
        }

        dtm.setColumnIdentifiers(spaltenNamen);
        auftragsposition_jTable.setModel(dtm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jB_Zurueck = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_Anzeigen = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        NeuePosition_jButton = new javax.swing.JButton();
        auftragsID_jLabel = new javax.swing.JLabel();
        geschaeftspartnerID_jLabel = new javax.swing.JLabel();
        auftragstext_jLabel = new javax.swing.JLabel();
        auftragskopfID_jTextField = new javax.swing.JTextField();
        erfassungsdatum_jLabel = new javax.swing.JLabel();
        erfassungsdatum_jFormattedTextField = new javax.swing.JFormattedTextField();
        auftragsart_jLabel = new javax.swing.JLabel();
        auftragsart_jComboBox = new javax.swing.JComboBox();
        lieferdatum_jLabel = new javax.swing.JLabel();
        lieferdatum_jFormattedTextField = new javax.swing.JFormattedTextField();
        abschlussdatum_jLabel = new javax.swing.JLabel();
        abschlussdatum_jFormattedTextField = new javax.swing.JFormattedTextField();
        auftragswert_jLabel = new javax.swing.JLabel();
        auftragswert_jTextField = new javax.swing.JTextField();
        erfasst_jRadioButton = new javax.swing.JRadioButton();
        freigegeben_jRadioButton = new javax.swing.JRadioButton();
        abgeschlossen_jRadioButton = new javax.swing.JRadioButton();
        status_jLabel = new javax.swing.JLabel();
        eurosymbol_jLabel = new javax.swing.JLabel();
        zahlungskonditionen_jLabel = new javax.swing.JLabel();
        zahlungskonditionen_jComboBox = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        auftragsposition_jTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        auftragstext_jTextArea = new javax.swing.JTextArea();
        auftragskopfdaten_titel_jLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        positionsnummer_jLabel = new javax.swing.JLabel();
        auftragspositions_titel_jLabel = new javax.swing.JLabel();
        materialnummer_jLabel = new javax.swing.JLabel();
        menge_jLabel = new javax.swing.JLabel();
        einzelwert_jLabel = new javax.swing.JLabel();
        erfassungsdatum_jLabel1 = new javax.swing.JLabel();
        positionsnummer_jTextField = new javax.swing.JTextField();
        materialnummer_jTextField = new javax.swing.JTextField();
        menge_jTextField = new javax.swing.JTextField();
        einzelwert_jTextField = new javax.swing.JTextField();
        erfassungsdatum_auftragsposition_jFormattedTextField = new javax.swing.JFormattedTextField();
        geschaeftspartner_jTextField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setTitle("Auftragskopf anlegen");
        setAutoscrolls(true);
        setVisible(true);

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        jB_Zurueck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/home.PNG"))); // NOI18N
        jB_Zurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ZurueckActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Zurueck);

        jB_Speichern.setText("Speichern");
        jB_Speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SpeichernActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setText("Anzeige/Ändern");
        jB_Anzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_AnzeigenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setText("Löschen");
        jB_Loeschen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_LoeschenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setText("Suchen");
        jB_Suchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuchenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Suchen);

        jSeparator1.setEnabled(false);

        NeuePosition_jButton.setText("Neue Position anlegen");
        NeuePosition_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NeuePosition_jButtonActionPerformed(evt);
            }
        });

        auftragsID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        auftragsID_jLabel.setLabelFor(auftragskopfID_jTextField);
        auftragsID_jLabel.setText("Auftragskopf-ID:");

        geschaeftspartnerID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        geschaeftspartnerID_jLabel.setText("Geschäftspartner-ID :");

        auftragstext_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        auftragstext_jLabel.setLabelFor(auftragstext_jTextArea);
        auftragstext_jLabel.setText("Auftragstext :");

        auftragskopfID_jTextField.setEnabled(false);

        erfassungsdatum_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        erfassungsdatum_jLabel.setLabelFor(erfassungsdatum_jFormattedTextField);
        erfassungsdatum_jLabel.setText("Erfassungsdatum :");

        erfassungsdatum_jFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        erfassungsdatum_jFormattedTextField.setText(" ");
        erfassungsdatum_jFormattedTextField.setEnabled(false);

        auftragsart_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        auftragsart_jLabel.setText("Auftragsart :");

        auftragsart_jComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Barauftrag", "Sofortauftrag", "Terminauftrag", "Bestellauftrag" }));
        auftragsart_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                auftragsart_jComboBoxActionPerformed(evt);
            }
        });

        lieferdatum_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lieferdatum_jLabel.setLabelFor(lieferdatum_jFormattedTextField);
        lieferdatum_jLabel.setText("Lieferdatum        :");

        lieferdatum_jFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        lieferdatum_jFormattedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lieferdatum_jFormattedTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lieferdatum_jFormattedTextFieldFocusLost(evt);
            }
        });
        lieferdatum_jFormattedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lieferdatum_jFormattedTextFieldActionPerformed(evt);
            }
        });

        abschlussdatum_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        abschlussdatum_jLabel.setLabelFor(abschlussdatum_jFormattedTextField);
        abschlussdatum_jLabel.setText("Abschlussdatum   :");
        abschlussdatum_jLabel.setToolTipText("");

        abschlussdatum_jFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        abschlussdatum_jFormattedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                abschlussdatum_jFormattedTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                abschlussdatum_jFormattedTextFieldFocusLost(evt);
            }
        });
        abschlussdatum_jFormattedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abschlussdatum_jFormattedTextFieldActionPerformed(evt);
            }
        });

        auftragswert_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        auftragswert_jLabel.setLabelFor(auftragswert_jTextField);
        auftragswert_jLabel.setText("Aufragswert :");

        auftragswert_jTextField.setText("100,00");
        auftragswert_jTextField.setToolTipText("");
        auftragswert_jTextField.setEnabled(false);
        auftragswert_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                auftragswert_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                auftragswert_jTextFieldFocusLost(evt);
            }
        });

        erfasst_jRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        erfasst_jRadioButton.setSelected(true);
        erfasst_jRadioButton.setText("Erfasst");

        freigegeben_jRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        freigegeben_jRadioButton.setText("Freigegeben");
        freigegeben_jRadioButton.setEnabled(false);

        abgeschlossen_jRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        abgeschlossen_jRadioButton.setText("Abgeschlossen");
        abgeschlossen_jRadioButton.setEnabled(false);

        status_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        status_jLabel.setText("  Status :");

        eurosymbol_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        eurosymbol_jLabel.setText("€");

        zahlungskonditionen_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        zahlungskonditionen_jLabel.setText("Zahlungskonditionen :");

        zahlungskonditionen_jComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));
        zahlungskonditionen_jComboBox.setEnabled(false);

        auftragsposition_jTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        auftragsposition_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        auftragsposition_jTable.setEnabled(false);
        jScrollPane2.setViewportView(auftragsposition_jTable);

        auftragstext_jTextArea.setColumns(20);
        auftragstext_jTextArea.setRows(5);
        auftragstext_jTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                auftragstext_jTextAreaFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(auftragstext_jTextArea);

        auftragskopfdaten_titel_jLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        auftragskopfdaten_titel_jLabel.setText("Auftragskopf Daten :");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Auftragspositionen :");

        positionsnummer_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        positionsnummer_jLabel.setText("Positionsnummer :");

        auftragspositions_titel_jLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        auftragspositions_titel_jLabel.setText("Auftragspositionsdaten :");

        materialnummer_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        materialnummer_jLabel.setText("Materialnummer :");

        menge_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        menge_jLabel.setText("Menge :");

        einzelwert_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        einzelwert_jLabel.setText("Einzelwert :");

        erfassungsdatum_jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        erfassungsdatum_jLabel1.setText("Erfassungsdatum :");

        positionsnummer_jTextField.setToolTipText("");
        positionsnummer_jTextField.setEnabled(false);
        positionsnummer_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                positionsnummer_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                positionsnummer_jTextFieldFocusLost(evt);
            }
        });

        materialnummer_jTextField.setText("12345678");
        materialnummer_jTextField.setToolTipText("");
        materialnummer_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                materialnummer_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                materialnummer_jTextFieldFocusLost(evt);
            }
        });

        menge_jTextField.setText("10");
        menge_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                menge_jTextFieldFocusGained(evt);
            }
        });

        einzelwert_jTextField.setToolTipText("");
        einzelwert_jTextField.setEnabled(false);
        einzelwert_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                einzelwert_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                einzelwert_jTextFieldFocusLost(evt);
            }
        });

        erfassungsdatum_auftragsposition_jFormattedTextField.setEditable(false);
        erfassungsdatum_auftragsposition_jFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        erfassungsdatum_auftragsposition_jFormattedTextField.setEnabled(false);
        erfassungsdatum_auftragsposition_jFormattedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                erfassungsdatum_auftragsposition_jFormattedTextFieldFocusGained(evt);
            }
        });

        geschaeftspartner_jTextField.setText("jTextField1");
        geschaeftspartner_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                geschaeftspartner_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                geschaeftspartner_jTextFieldFocusLost(evt);
            }
        });

        jButton2.setText("Position entfernen");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(auftragsID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(geschaeftspartnerID_jLabel))
                                                .addGap(22, 22, 22)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(geschaeftspartner_jTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(140, 140, 140)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(lieferdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(erfassungsdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGap(1, 1, 1))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(positionsnummer_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(materialnummer_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(140, 140, 140)
                                        .addComponent(abschlussdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(146, 146, 146)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(abgeschlossen_jRadioButton)
                                            .addComponent(freigegeben_jRadioButton)
                                            .addComponent(erfasst_jRadioButton)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGap(42, 42, 42)
                                            .addComponent(menge_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(menge_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(einzelwert_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(erfassungsdatum_jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(einzelwert_jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                                .addComponent(erfassungsdatum_auftragsposition_jFormattedTextField))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(auftragstext_jLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(auftragswert_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(auftragswert_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(19, 19, 19)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(eurosymbol_jLabel)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(status_jLabel)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(auftragsart_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(zahlungskonditionen_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(zahlungskonditionen_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(auftragsart_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(erfassungsdatum_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lieferdatum_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(abschlussdatum_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))))
                            .addComponent(auftragskopfdaten_titel_jLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(NeuePosition_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(positionsnummer_jLabel)
                                            .addComponent(materialnummer_jLabel)))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(auftragspositions_titel_jLabel))
                                .addContainerGap(22, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 100, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(auftragskopfdaten_titel_jLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(auftragsart_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(geschaeftspartnerID_jLabel)
                                .addComponent(geschaeftspartner_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(auftragsID_jLabel)
                            .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zahlungskonditionen_jLabel)
                            .addComponent(zahlungskonditionen_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(auftragsart_jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(auftragswert_jLabel)
                    .addComponent(auftragswert_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eurosymbol_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(auftragstext_jLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(erfasst_jRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(freigegeben_jRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(abgeschlossen_jRadioButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(erfassungsdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(erfassungsdatum_jLabel)
                            .addComponent(status_jLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lieferdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lieferdatum_jLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(abschlussdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(abschlussdatum_jLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(auftragspositions_titel_jLabel)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(menge_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(menge_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(positionsnummer_jLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(einzelwert_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(einzelwert_jLabel)
                            .addComponent(materialnummer_jLabel)
                            .addComponent(materialnummer_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(positionsnummer_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(erfassungsdatum_auftragsposition_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(erfassungsdatum_jLabel1))))
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NeuePosition_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 616, Short.MAX_VALUE)))
        );

        zahlungskonditionen_jLabel.getAccessibleContext().setAccessibleName("  Zahlungskonditionen :");

        setBounds(0, 0, 797, 670);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Selektiert das Eingabefeld des Auftragswerts
     *
     * @param evt
     */
    private void auftragswert_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragswert_jTextFieldFocusGained
        auftragswert_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        auftragswert_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
        auftragswert_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_auftragswert_jTextFieldFocusGained

    /**
     * Selektiert das Eingabefeld des Lieferdatums
     *
     * @param evt
     */
    private void lieferdatum_jFormattedTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lieferdatum_jFormattedTextFieldFocusGained
        lieferdatum_jFormattedTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        lieferdatum_jFormattedTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_lieferdatum_jFormattedTextFieldFocusGained

    /**
     * Selektiert das Eingabefeld des Abschlussdatums
     *
     * @param evt
     */
    private void abschlussdatum_jFormattedTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_abschlussdatum_jFormattedTextFieldFocusGained
        abschlussdatum_jFormattedTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        abschlussdatum_jFormattedTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_abschlussdatum_jFormattedTextFieldFocusGained

    /**
     * Selektiert das Eingabefeld der Positionsnummer
     *
     * @param evt
     */
    private void positionsnummer_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_positionsnummer_jTextFieldFocusGained
        positionsnummer_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        positionsnummer_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
        positionsnummer_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_positionsnummer_jTextFieldFocusGained

    /**
     * Selektiert das Eingabefeld der Materialnummer
     *
     * @param evt
     */
    private void materialnummer_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_materialnummer_jTextFieldFocusGained
        materialnummer_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        materialnummer_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
        materialnummer_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_materialnummer_jTextFieldFocusGained

    /**
     * Selektiert das Eingabefeld der Menge
     *
     * @param evt
     */
    private void menge_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_menge_jTextFieldFocusGained
        menge_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        menge_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
        menge_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_menge_jTextFieldFocusGained

    /**
     * Selektiert das Eingabefeld des Einzelwerts
     *
     * @param evt
     */
    private void einzelwert_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_einzelwert_jTextFieldFocusGained
        einzelwert_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        einzelwert_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
        einzelwert_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_einzelwert_jTextFieldFocusGained

    /**
     * ActionPerformed für die Combox der Auftragsarten. Die Combobox für die
     * Zahlungskonditionen wird nur freigegeben wenn in der Auftragsart nicht
     * "Barauftrag" gewählt wird.
     *
     * @param evt
     */
    private void auftragsart_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_auftragsart_jComboBoxActionPerformed

        //Falls in der Combox nicht der erste Eintrag ausgewählt wird, wird die Combobox für die 
        // Zahlungskonditionen auf enable(true) gesetezt
        if (auftragsart_jComboBox.getSelectedIndex() != 0) {
            zahlungskonditionen_jComboBox.setEnabled(true);
            ladeZahlungskonditionenAusDatenbank(auftragsart_jComboBox.getSelectedItem().toString());
            zahlungskonditionen_jComboBox.setModel(new DefaultComboBoxModel(zahlungskonditionFuerCombobox.toArray()));
        } else {//Combobox für die Zahlungskonditionen wird nicht wählbar gemacht
            zahlungskonditionen_jComboBox.addItem("Bitte wählen");
            zahlungskonditionen_jComboBox.setEnabled(false);
        }

    }//GEN-LAST:event_auftragsart_jComboBoxActionPerformed

    /**
     * Beim Focuslost des Eingabefeldes für den Auftragswert, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void auftragswert_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragswert_jTextFieldFocusLost
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(auftragswert_jTextField, PEIS_SYNTAX,
                FEHLERMELDUNG_TITEL, fehlermeldungPreis_text);

    }//GEN-LAST:event_auftragswert_jTextFieldFocusLost

    /**
     * Beim Focuslost des Eingabefeldes für den Einzelwert, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void einzelwert_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_einzelwert_jTextFieldFocusLost
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(einzelwert_jTextField, PEIS_SYNTAX,
                FEHLERMELDUNG_TITEL, fehlermeldungPreis_text);
    }//GEN-LAST:event_einzelwert_jTextFieldFocusLost

    /**
     * Selektiert das Eingabefeld des Erfassungsdatums der Auftragsposition
     *
     * @param evt
     */
    private void erfassungsdatum_auftragsposition_jFormattedTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_erfassungsdatum_auftragsposition_jFormattedTextFieldFocusGained
//        erfassungsdatum_auftragsposition_jFormattedTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
        erfassungsdatum_auftragsposition_jFormattedTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_erfassungsdatum_auftragsposition_jFormattedTextFieldFocusGained

    /**
     * Beim Focuslost des Eingabefeldes für die Materialnummer, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void materialnummer_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_materialnummer_jTextFieldFocusLost
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        try {
            GUIFactory.getDAO().getItem((Long.parseLong(materialnummer_jTextField.getText())));
            if (auftragsart_jComboBox.getSelectedItem().toString().equals("Bestellauftrag")) {

                einzelwert = (GUIFactory.getDAO().getItem(
                        (Long.parseLong(materialnummer_jTextField.getText()))).getEinkaufswert());
                einzelwert_jTextField.setText(String.valueOf(einzelwert));

            } else {
                einzelwert = (GUIFactory.getDAO().getItem(
                        (Long.parseLong(materialnummer_jTextField.getText()))).getVerkaufswert());
                einzelwert_jTextField.setText(String.valueOf(einzelwert));
            }

        } catch (ApplicationException | NumberFormatException e) {
            //Fehlermeldung das ein gültiger wert eingegeben werden soll.
            this.hauptFenster.setStatusMeldung(e.getMessage());
        }
    }//GEN-LAST:event_materialnummer_jTextFieldFocusLost

    /**
     * Beim Focuslost des Eingabefeldes für die Positionsnummer, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void positionsnummer_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_positionsnummer_jTextFieldFocusLost
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(positionsnummer_jTextField, POSITIONSNUMMER_SYNTAX,
                FEHLERMELDUNG_TITEL, fehlermeldungPositionsnummer_text);
    }//GEN-LAST:event_positionsnummer_jTextFieldFocusLost

    /**
     * Selektier das Eingabefeld des Auftragstextes
     *
     * @param evt
     */
    private void auftragstext_jTextAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragstext_jTextAreaFocusGained
        auftragstext_jTextArea.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        auftragstext_jTextArea.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_auftragstext_jTextAreaFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi, angelegt und dokumentiert */
    /* 17.01.2015 Terrasi, gibZahlungskonditionenFürAuftragsart implementiert */
    /*----------------------------------------------------------*/
    /**
     * Methode um die Zahlungskonditionen die in der Datenbank gespeichert sind,
     * aufzurufen und diese in einer ArrayList zu speichern.
     *
     * @param auftragsart, Stringvariable die eine Auftragsart repräsentiert.
     */
    private void ladeZahlungskonditionenAusDatenbank(String auftragsart) {
        try {
            //Collection erhält Zahlugskonditionen aus der Datenbank.   
//            zahlungskondditionAusDatenbank = GUIFactory.getDAO().gibAlleZahlungskonditionen();//Aufruf der gibAlleZahlungskonditionen()-Methode.
            zahlungskondditionAusDatenbank
                    = GUIFactory.getDAO().gibZahlungskonditionenFürAuftragsart(auftragsart);// Aufruf der Zahlungskonditionen zur jeweiligen Aufragsart.
            //ArrayList für die Combobox
            zahlungskonditionFuerCombobox.clear();
            if (auftragsart.equals("Barauftrag")) {
                zahlungskonditionFuerCombobox.add("Bitte auswählen");

            } else {
                Iterator<Zahlungskondition> it = zahlungskondditionAusDatenbank.iterator();
                while (it.hasNext()) {

                    zahlungskonditionFuerCombobox.add(
                            String.valueOf(it.next().getZahlungskonditionID()));

                }
            }
        } catch (NullPointerException | NoSuchElementException | ApplicationException ex) {//Abfangen von fehlern
            this.hauptFenster.setStatusMeldung(ex.getMessage());// Fehlermeldung wird in Statuszeile ausgegeben.
        }
    }

    /**
     * Action beim betätigen des Speicher-Buttons. Es wird die Prüfmethode
     * aufgerufen die wieder gibt ob irgendwelche Eingaben nicht getätigt worden
     * sind und markiert diese farblich.
     *
     * @param evt
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed

        Auftragskopf kopf;

        String dbGeschaeftspartnerID;
        String dbAuftragsart;
        String dbZahlungskondition;
        String dbStatus;
        String dbLieferdatum;
        String dbAbschlussdatum;
        
        boolean positionenSindGleich = true;
        
        String jetzigerStatus = "";
        String jetzigeZahlungskonditionen = "null";

//Aufruf der Schnittstellenmethode um auf Vollständigkeit der Eingaben zu prüfen.
        ueberpruefen();
        try {
            if (fehlendeEingaben.isEmpty()) {
                if (auftragspositionen.isEmpty() == false) {
                    typ = auftragsart_jComboBox.getSelectedItem().toString();
                    auftragsText = auftragstext_jTextArea.getText();
                    geschaeftspartnerID = Long.parseLong(geschaeftspartner_jTextField.getText());
                   
                    if (this.getTitle().equals("Auftragskopf anlegen")) {

                        //Auftragskopf und Position anlegen
                        //Überprüfung ob Gp vorhanden ist(Focuslost
                        GUIFactory.getDAO().erstelleAuftragskopf(typ, artikel,
                                auftragsText, geschaeftspartnerID,
                                Integer.parseInt(zahlungskonditionen_jComboBox.getSelectedItem().toString()),
                                "erfasst", abschlussdatum, lieferdatum);
                        this.hauptFenster.setStatusMeldung(ERFOLGREICHEANMELDUNG);
                        zuruecksetzen();//Methode die bestimmte Eingabefelder leert
                    } else if (this.getTitle().equals("Auftragskopf ändern")) {

                        kopf = GUIFactory.getDAO().
                                getOrderHead(Long.parseLong(auftragskopfID_jTextField.getText()));

                        dbGeschaeftspartnerID
                                = String.valueOf(kopf.getGeschaeftspartner().getGeschaeftspartnerID());
                        dbAuftragsart = kopf.getTyp();

                        if (kopf.getZahlungskondition() == null) {
                            dbZahlungskondition = "null";
                        } else {

                            dbZahlungskondition
                                    = String.valueOf(kopf.getZahlungskondition().getZahlungskonditionID());
                        }
                        dbStatus = kopf.getStatus().getStatus();
                        dbLieferdatum = gibDatumAlsString(kopf.getLieferdatum());
                        dbAbschlussdatum = gibDatumAlsString(kopf.getAbschlussdatum());
                        ArrayList<Auftragsposition> dbAuftragspositionen = new ArrayList<>();
                        dbAuftragspositionen = kopf.getPositionsliste();

                        
                        if (dbAuftragspositionen.size() == auftragspositionen.size()) {
                            for (int i = 0; i < dbAuftragspositionen.size() && positionenSindGleich == true; i++) {
                                if (dbAuftragspositionen.get(i).getArtikel().getArtikelID()
                                        == auftragspositionen.get(i).getArtikel().getArtikelID()
                                        && dbAuftragspositionen.get(i).getPositionsnummer()
                                        == auftragspositionen.get(i).getPositionsnummer()
                                        && dbAuftragspositionen.get(i).getEinzelwert()
                                        == auftragspositionen.get(i).getEinzelwert()
                                        && gibDatumAlsString(dbAuftragspositionen.get(i).getErfassungsdatum()).
                                        equals(gibDatumAlsString(auftragspositionen.get(i).getErfassungsdatum()))
                                        && dbAuftragspositionen.get(i).getMenge()
                                        == auftragspositionen.get(i).getMenge()) {
                                    positionenSindGleich = true;
                                } else {
                                    positionenSindGleich = false;
                                }
                            }
                        }

                        
                        ButtonModel bm = buttonGroup1.getSelection();
                        if(bm.getActionCommand().equals("Erfasst")){
                            jetzigerStatus = "erfasst";
                        }else if(bm.getActionCommand().equals("Freigegeben")){
                            jetzigerStatus = "freigegeben";
                        }else{
                            jetzigerStatus = "abgeschlossen";
                        }
                        
                        if(!(auftragsart_jComboBox.getSelectedIndex() == 0)){
                            jetzigeZahlungskonditionen = zahlungskonditionen_jComboBox.getSelectedItem().toString();
                        }else{
                            jetzigeZahlungskonditionen = "null";
                        }
                        
                        
                        
                        if (!(dbGeschaeftspartnerID.equals(geschaeftspartner_jTextField.getText())
                                && dbAuftragsart.equals(auftragsart_jComboBox.getSelectedItem().toString())
                                && dbZahlungskondition.equals(jetzigeZahlungskonditionen)
                                && dbStatus.equals(jetzigerStatus)
                                && dbLieferdatum.equals(lieferdatum_jFormattedTextField.getText())
                                && dbAbschlussdatum.equals(abschlussdatum_jFormattedTextField.getText())
                                && positionenSindGleich != true)) {
                            if (kopf.pruefeVerfuegbarkeit(buttonGroup1.getSelection().toString()) == true) {
                                
                                int antwort = JOptionPane.showConfirmDialog(rootPane, aenderungVonDaten_Text,
                                        aenderungVonDaten_Titel, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                //Falls bejaht wird der Auftragskopf verändert gespeichert.
                                if (antwort == JOptionPane.YES_OPTION) {
                                    
                                    GUIFactory.getDAO().
                                            aendereAuftrag(Long.parseLong(auftragskopfID_jTextField.getText()),
                                                    geschaeftspartnerID,
                                                    Long.parseLong(zahlungskonditionen_jComboBox.getSelectedItem().toString()),
                                                    artikel, auftragsText, status, abschlussdatum, lieferdatum);
                                    zuruecksetzen();
                                    jB_ZurueckActionPerformed(evt);
                                    this.hauptFenster.setStatusMeldung(ERFOLGREICHGEAENDERT_TEXT);
                                } else {
                                }
                            }

                        }else{
                            
                        JOptionPane.showMessageDialog(null, keineAenderung_Text,
                                keineAenderungen_Titel, JOptionPane.OK_OPTION);
                        }
                       
                    }

                } else {
                    
                    if (auftragsposition_jTable.getModel().getRowCount() == 0) {//Wenn nicht mindestens eine Auftragsposition zum Auftragskopf angelegt worden ist
                        // Methodenaufruf um daraufhinzuweisen das nicht alle eingaben getätigt worden sind
                        fehlEingabenMarkierung(fehlendeEingabenAuftragsposition,
                                FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL,
                                fehlermeldung_unvollstaendig_keineauftragsposZumAKopf_text,
                                warningfarbe);
                    }
//                    else {//Eine Auftragsposition ist bereits angelegt.
//                        if (fehlendeEingabenAuftragsposition.size() != 5) {// Es wurden nicht vollständig Eingaben
                    //für die Auftragsposition getätigt und in mindestens einem Eingabefeld ist eine Eingabe
                    // getätigt worden.

//                            int antwort = JOptionPane.showConfirmDialog(rootPane, fehlermeldung_unvollstaendig,
//                                    FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                            //Falls bejaht wird und man eine weitere Auftragsposition anlegen möchte
//                            if (antwort == JOptionPane.YES_OPTION) {
//                                fehlendeEingabenAuftragsposition.get(0).requestFocusInWindow();// Fokus gelangt in das erste leere Eingabefeld
//                                // Alle leeren Eingabefelder werden farblich markiert.
//                                for (int i = 0; i <= fehlendeEingabenAuftragsposition.size() - 1; i++) {
//                                    fehlendeEingabenAuftragsposition.get(i).setBackground(warningfarbe);
//                                }
//                                fehlendeEingabenAuftragsposition.clear();//ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
////                            } else {
//                                //Keine Reaktion
////                            }
//                            // Alle leeren Eingabefelder werden farblich markiert.
//                            fehlendeEingabenAuftragsposition.get(0).requestFocusInWindow();// Fokus gelangt in das erste leere Eingabefeld
//                            for (int i = 0; i <= fehlendeEingabenAuftragsposition.size() - 1; i++) {
//                                fehlendeEingabenAuftragsposition.get(i).setBackground(warningfarbe);
//                            }
//                            fehlendeEingabenAuftragsposition.clear();//ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
//                        } else {
//
//                        }
                    // Methodenaufruf um daraufhinzuweisen das nicht alle eingaben 
                    // getätigt worden sind
//                        fehlEingabenMarkierung(fehlendeEingabenAuftragsposition,
//                                FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL,
//                                fehlermeldung_unvollstaendig_auftragsposition_text,
//                                warningfarbe);
                }
//                }
            } else {
                // Methodenaufruf um daraufhinzuweisen das nicht alle eingaben 
                // getätigt worden sind
                fehlEingabenMarkierung(fehlendeEingaben,
                        FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL,
                        fehlermeldung_unvollstaendig_auftragskopf_text, warningfarbe);
            }
        } catch (ApplicationException e) {
            this.hauptFenster.setStatusMeldung(e.getMessage());
        }
    }//GEN-LAST:event_jB_SpeichernActionPerformed

    /**
     * Selektiert das Eingabefeld des Geschäftspartners
     *
     * @param evt
     */
    private void geschaeftspartner_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_geschaeftspartner_jTextFieldFocusGained
        geschaeftspartner_jTextField.setBackground(hintergrundfarbe);// Hintergrundsfarbe wird gesetzt
        geschaeftspartner_jTextField.selectAll();//Eingabefeld wird selektiert
    }//GEN-LAST:event_geschaeftspartner_jTextFieldFocusGained

    /**
     * Beim Focuslost des Eingabefeldes für den Geschäftspartner, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void geschaeftspartner_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_geschaeftspartner_jTextFieldFocusLost
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(geschaeftspartner_jTextField,
                GESCHAEFTSPARTNER_SYNTAX, FEHLERMELDUNG_TITEL,
                fehlermeldungGeschaeftspartnerID_text);
    }//GEN-LAST:event_geschaeftspartner_jTextFieldFocusLost

    /**
     * Beim Focuslost des Eingabefeldes für das Lieferdatum, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void lieferdatum_jFormattedTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lieferdatum_jFormattedTextFieldFocusLost
        if (evt.isTemporary()) {
            return;
        }
        format.setLenient(false);
        try {
            lieferdatum = format.parse(lieferdatum_jFormattedTextField.getText());//Lieferdatum wird geparst
            if (!lieferdatum_jFormattedTextField.getText().equals("")) {//Falls das Eingabefeld nicht leer ist wird geprüft
                if (lieferdatum.before(heute)) {//Datum liegt in der Vergangenheit
                    //Ausgabe eine Fehlermeldung
                    JOptionPane.showMessageDialog(null, FEHLERMELDUNG_LIEFERDATUM_TEXT,
                            FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
                    //Mit dem Focus in das Eingabefeld springen
                    lieferdatum_jFormattedTextField.requestFocusInWindow();
                    lieferdatum_jFormattedTextField.selectAll();
                }
            }
        } catch (ParseException e) {// Exception wird abgefangen, falls nicht geparst werden kann.
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(rootPane,
                    FEHLERMELDUNG_UNGUELTIGESDATUM, FEHLERMELDUNG_TITEL,
                    JOptionPane.ERROR_MESSAGE);
            //Mit dem Focus in das Eingabefeld springen
            lieferdatum_jFormattedTextField.requestFocusInWindow();
            lieferdatum_jFormattedTextField.selectAll();
        }
    }//GEN-LAST:event_lieferdatum_jFormattedTextFieldFocusLost

    /**
     * Beim Focuslost des Eingabefeldes für das Abschlussdatum, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void abschlussdatum_jFormattedTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_abschlussdatum_jFormattedTextFieldFocusLost
        if (evt.isTemporary()) {
            return;
        }
        format.setLenient(false);
        try {
            abschlussdatum = format.parse(abschlussdatum_jFormattedTextField.getText());//Abschlussdatum wird geparst
            lieferdatum = format.parse(lieferdatum_jFormattedTextField.getText());//Lieferdatum wird geparst
            if (!abschlussdatum_jFormattedTextField.getText().equals("")) {//Falls das Eingabefeld nicht leer ist wird geprüft
                if (!abschlussdatum.before(heute)) {//Datum liegt in der Zukunft                    
                    if (abschlussdatum.before(lieferdatum)) {//Wenn Abschlussdatum vor dem Lieferdatum liegt
                        //Ausgabe eine Fehlermeldung
                        JOptionPane.showMessageDialog(null,
                                FEHLERMELDUNG_ABSCHLUSSDATUMVORLIEFERDATUM_TEXT,
                                FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
                        //In das Eingabefeld springen
                        abschlussdatum_jFormattedTextField.requestFocusInWindow();
                        abschlussdatum_jFormattedTextField.selectAll();
                    }
                } else {//Wenn Abschlussdatum vor dem Erfassungsdatum liegt
                    //Ausgabe einer Fehlermeldung
                    JOptionPane.showMessageDialog(null, FEHLERMELDUNG_ABSCHLUSSDATUM_TEXT,
                            FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
                    //In das Eingabefeld springen
                    abschlussdatum_jFormattedTextField.requestFocusInWindow();
                    abschlussdatum_jFormattedTextField.selectAll();
                }
            }
        } catch (ParseException e) {// Exception wird abgefangen, falls nicht geparst werden kann.
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(rootPane,
                    FEHLERMELDUNG_UNGUELTIGESDATUM, FEHLERMELDUNG_TITEL,
                    JOptionPane.ERROR_MESSAGE);
            //In das Eingabefeld springen
            abschlussdatum_jFormattedTextField.requestFocusInWindow();
            abschlussdatum_jFormattedTextField.selectAll();
        }
    }//GEN-LAST:event_abschlussdatum_jFormattedTextFieldFocusLost

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, Dokumentation und Logik. */
    /* 13.01.2015 Terrasi, Logik implementiert*/
    /*----------------------------------------------------------*/
    /**
     * Aktion in der überprüft wird ob alle erforderlichen Eingaben für eine
     * Auftragsposition vorhanden sind und diese dann zum jeweiligen
     * Auftrgaskopf anlegt
     *
     * @param evt
     */
    private void NeuePosition_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NeuePosition_jButtonActionPerformed

        Integer artikelMenge = 0;

        //Aufruf der Schnittstellenmethode um auf Vollständigkeit der Eingaben zu prüfen.
        ueberpruefen();
        HashMap<Long, Integer> map = new HashMap<>();
        try {
            if (fehlendeEingaben.isEmpty()) {
                if (fehlendeEingabenAuftragsposition.isEmpty()) {
                    if (artikel.containsKey(Long.parseLong(materialnummer_jTextField.getText()))) {

                        artikelMenge = artikel.get(Long.parseLong(materialnummer_jTextField.getText()))
                                + Integer.parseInt(menge_jTextField.getText());

                        artikel.put(Long.parseLong(materialnummer_jTextField.getText()), artikelMenge);

                        artikelMenge = 0;

                        for (int i = 0; i < auftragspositionen.size(); i++) {

                            if (auftragspositionen.get(i).getArtikel().getArtikelID()
                                    == Long.parseLong(materialnummer_jTextField.getText())) {

                                auftragspositionen.get(i).setMenge(auftragspositionen.get(i).getMenge()
                                        + Integer.parseInt(menge_jTextField.getText()));

                            }
                        }
                    } else {

                        //Pos anlegen
                        artikel.put(Long.parseLong(materialnummer_jTextField.getText()),
                                Integer.parseInt(menge_jTextField.getText()));

                        position = new Auftragsposition(null,
                                GUIFactory.getDAO().getItem(Long.parseLong(materialnummer_jTextField.getText())),
                                Integer.parseInt(menge_jTextField.getText()),
                                Double.parseDouble(einzelwert_jTextField.getText()),
                                abschlussdatum);

                        auftragspositionen.add(position);

                    }

                    dtm.setRowCount(0);

                    for (int i = 0; i < auftragspositionen.size(); i++) {

                        summenWertFuerPos = auftragspositionen.get(i).getEinzelwert()
                                * auftragspositionen.get(i).getMenge();

                        Object[] neuesObj = new Object[]{i + 1,
                            auftragspositionen.get(i).getArtikel().getArtikelID(),
                            auftragspositionen.get(i).getMenge(), summenWertFuerPos,
                            gibDatumAlsString(abschlussdatum)};

                        gesamtAuftragswert += summenWertFuerPos;

                        dtm.addRow(neuesObj);

                    }

                    auftragsposition_jTable.setModel(dtm);

                    auftragswert_jTextField.setText(String.valueOf(gesamtAuftragswert));
                    gesamtAuftragswert = 0.00;
                    summenWertFuerPos = 0.00;

                } else {

                    if (auftragsposition_jTable.getRowCount() == 0) {//Wenn nicht mindestens eine Auftragsposition zum Auftragskopf angelegt worden ist
                        // Methodenaufruf um daraufhinzuweisen das nicht alle eingaben getätigt worden sind
                        fehlEingabenMarkierung(fehlendeEingabenAuftragsposition,
                                FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL,
                                fehlermeldung_unvollstaendig_keineauftragsposZumAKopf_text,
                                warningfarbe);
                    } else {//Eine Auftragsposition ist bereits angelegt.
                        if (fehlendeEingabenAuftragsposition.size() != 5) {// Es wurden nicht vollständig Eingaben
                            //für die Auftragsposition getätigt und in mindestens einem Eingabefeld ist eine Eingabe
                            // getätigt worden.

                            int antwort = JOptionPane.showConfirmDialog(rootPane, fehlermeldung_unvollstaendig,
                                    FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            //Falls bejaht wird und man eine weitere Auftragsposition anlegen möchte
                            if (antwort == JOptionPane.YES_OPTION) {
                                fehlendeEingabenAuftragsposition.get(0).requestFocusInWindow();// Fokus gelangt in das erste leere Eingabefeld
                                // Alle leeren Eingabefelder werden farblich markiert.
                                for (int i = 0; i <= fehlendeEingabenAuftragsposition.size() - 1; i++) {
                                    fehlendeEingabenAuftragsposition.get(i).setBackground(warningfarbe);
                                }
                                fehlendeEingabenAuftragsposition.clear();//ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
                            } else {
                                //Keine Reaktion
                            }
                            // Alle leeren Eingabefelder werden farblich markiert.
                            fehlendeEingabenAuftragsposition.get(0).requestFocusInWindow();// Fokus gelangt in das erste leere Eingabefeld
                            for (int i = 0; i <= fehlendeEingabenAuftragsposition.size() - 1; i++) {
                                fehlendeEingabenAuftragsposition.get(i).setBackground(warningfarbe);
                            }
                            fehlendeEingabenAuftragsposition.clear();//ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
                        } else {

                        }
                        // Methodenaufruf um daraufhinzuweisen das nicht alle eingaben 
                        // getätigt worden sind
                        fehlEingabenMarkierung(fehlendeEingabenAuftragsposition,
                                FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL,
                                fehlermeldung_unvollstaendig_auftragsposition_text,
                                warningfarbe);
                    }
                }
            } else {
                // Methodenaufruf um daraufhinzuweisen das nicht alle eingaben 
                // getätigt worden sind
                fehlEingabenMarkierung(fehlendeEingaben,
                        FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL,
                        fehlermeldung_unvollstaendig_auftragskopf_text, warningfarbe);
            }

        } catch (ApplicationException e) {
            this.hauptFenster.setStatusMeldung(e.getMessage());
        }catch(NumberFormatException e){
            this.hauptFenster.setStatusMeldung("Bitte eine Materialnummer eingeben.");
        }
    }//GEN-LAST:event_NeuePosition_jButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, Dokumentation und Logik. */
    /* 16.12.2014 Terrasi, Logik implementiert*/
    /*----------------------------------------------------------*/
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        zuruecksetzen();// Eingabefelder werden zurückgesetzt.
        letzteComponent = null;   //Initialisierung der Componentspeichervariable
        //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
        letzteComponent = this.factory.zurueckButton();
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        letzteComponent.setVisible(true);// Übergebene Component wird sichtbar gemacht
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 08.01.2015 Terrasi AuftragskopfAnlegenMaske mit der Anzeigen/Ändern-
     Funktion angepasst.*/
    /**
     *
     * Beim betätigen des Anzeigen/Ändern Buttons wird je nach Modus die Maske
     * passend angezeigt.
     *
     * @param
     */
    private void jB_AnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AnzeigenActionPerformed
        if (jB_Anzeigen.getText().equals("Anzeigen")) {// Überprüfung des Namens des Ändern/Anzeige-Buttons.
            this.setStatusAnzeigen();// Button in Anzeigestatus setzen.
        } else {
            this.setStatusAender();// Button in Ändernstatus setzen.
        }
    }//GEN-LAST:event_jB_AnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi, angelegt */
    /* 14.01.2015 Terrasi, Logik implementiert */
    /*----------------------------------------------------------*/
    /**
     * Methode die beim Löschen genutzt wird. Es erscheint eine ABfrage die der
     * Benutzer bejahen muss um den ausgewählten Auftrag zu löschen. Der
     * ausgewählte Auftrag wird in der DB mit einem Löschkennzeichen versehen.
     * Nachdem der Auftrag gelöscht wurden ist, erscheint in der Statuszeile
     * eine Meldung ber die erfolgreiche Löschung des Auftrags und alle
     * Eingabefelder werden geleert. Bei einem Fehler wird auch eine
     * entsprechende Fehlermeldung in der Statuszeile angezeigt.
     *
     * @param evt
     */
    private void jB_LoeschenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_LoeschenActionPerformed
        // Erzeugung einer Abfrage und Speicherung der antwort
        int antwort = JOptionPane.showConfirmDialog(rootPane, LOESCHENMELDUNG,
                LOESCHEN_TITEL, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        try {// Try-Block

            if (antwort == JOptionPane.YES_OPTION) { // Abfrage ob gegebene Antwort "Ja" ist.

                // Aufruf der Löschmethode der DAO.
                GUIFactory.getDAO().loescheAuftrag(Long.parseLong(auftragskopfID_jTextField.getText()));
                this.hauptFenster.setStatusMeldung(ERFOLGREICHESLOESCHEN);// Meldung wird an Statuszeile übergeben.
                zuruecksetzen();// Felder werden zurückgesetzt.
            }
        } catch (ApplicationException | NullPointerException e) { // Abfanagen von Fehlern.
            this.hauptFenster.setStatusMeldung(e.getMessage()); // Ausgabe der Fehlermeldung.
        }
    }//GEN-LAST:event_jB_LoeschenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Sch, angelegt */
    /*----------------------------------------------------------*/
    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        this.hauptFenster.rufeSuche(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /*----------------------------------------------------------*/
    private void lieferdatum_jFormattedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lieferdatum_jFormattedTextFieldActionPerformed
        lieferdatum_jFormattedTextField.selectAll();
    }//GEN-LAST:event_lieferdatum_jFormattedTextFieldActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /*----------------------------------------------------------*/
    private void abschlussdatum_jFormattedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abschlussdatum_jFormattedTextFieldActionPerformed
        abschlussdatum_jFormattedTextField.selectAll();
    }//GEN-LAST:event_abschlussdatum_jFormattedTextFieldActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi, angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode um eine Auftragsposition aus der Auftragspositionstabelle zu
     * löschen.
     *
     * @param evt
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            artikel.remove(auftragspositionen.get(auftragsposition_jTable.getSelectedRow()).getArtikel().getArtikelID());
            auftragspositionen.remove(auftragsposition_jTable.getSelectedRow());

            dtm.setRowCount(0);

            for (int i = 0; i < auftragspositionen.size(); i++) {

                summenWertFuerPos = auftragspositionen.get(i).getEinzelwert()
                        * auftragspositionen.get(i).getMenge();

                Object[] neuesObj = new Object[]{i + 1,
                    auftragspositionen.get(i).getArtikel().getArtikelID(),
                    auftragspositionen.get(i).getMenge(), summenWertFuerPos,
                    gibDatumAlsString(abschlussdatum)};

                dtm.addRow(neuesObj);

                gesamtAuftragswert += summenWertFuerPos;

            }

            auftragswert_jTextField.setText(String.valueOf(gesamtAuftragswert));
            gesamtAuftragswert = 0.00;
            auftragsposition_jTable.setModel(dtm);

        } catch (ArrayIndexOutOfBoundsException e) {
            this.hauptFenster.setStatusMeldung(fehlermeldungKeinePositionGewaehlt);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden.
     */
    @Override
    public void zuruecksetzen() {

        //Variablen für den Auftragswert werden alle auf 0 gesetzt.
        gesamtAuftragswert = 0.00;
        summenWertFuerPos = 0.00;
        einzelwert = 0.00;

        //Eingabefelder erhalten einen leeren String
        geschaeftspartner_jTextField.setText("");
        auftragswert_jTextField.setText("");
        positionsnummer_jTextField.setText("");

        auftragsart_jComboBox.setSelectedIndex(0);
        zahlungskonditionen_jComboBox.setSelectedIndex(0);

//        positionsnummer_jTextField.setText(String.valueOf(positionsZaehler));
        materialnummer_jTextField.setText("");
        menge_jTextField.setText("");
        einzelwert_jTextField.setText("");
        auftragstext_jTextArea.setText("");
        //Eingabefelder für das Erfassungsdatum erhalten das heutige Datum
//        erfassungsdatum_jFormattedTextField.setText(format.format(heute));
//        erfassungsdatum_auftragsposition_jFormattedTextField.setText(format.format(heute));
        artikel.clear();
        auftragspositionen.clear();

        dtm.setRowCount(0);

        auftragsposition_jTable.setModel(dtm);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der geprüft wird ob alle Eingaben getätigt
     * worden sind.
     */
    @Override
    public void ueberpruefen() {
        //IF-Anweisungen mit denen geprüft wird welche Eingabefelder keine Eingabe 
        // erhalten haben. Diese Eingabefelder werden in passende Speichervariablen festgehalten.

        //Eingabefelder für Auftragskopf werden in Variable "fehlendeEingaben" festgehalten.
        if (geschaeftspartner_jTextField.getText().equals("")) {
            fehlendeEingaben.add(geschaeftspartner_jTextField);
        }
        if (auftragstext_jTextArea.getText().equals("")) {
            fehlendeEingaben.add(auftragstext_jTextArea);
        }
        if (lieferdatum_jFormattedTextField.getText().equals("")) {
            fehlendeEingaben.add(lieferdatum_jFormattedTextField);
        }
        if (abschlussdatum_jFormattedTextField.getText().equals("")) {
            fehlendeEingaben.add(abschlussdatum_jFormattedTextField);
        }
        //Eingabefelder für Auftragsposition werden in Variable 
        // "fehlendeEingabenAuftragsposition" feestgehalten.
//        if (materialnummer_jTextField.getText().equals("")) {
//            fehlendeEingabenAuftragsposition.add(materialnummer_jTextField);
//        }
//        if (menge_jTextField.getText().equals("")) {
//            fehlendeEingabenAuftragsposition.add(menge_jTextField);
//        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der die Eingaben beim FocusLost auf Richtigkeit
     * geprüft werden.
     *
     * @param textfield, das zu übergeben JTextfield, indem der Focusgesetzt
     * ist.
     * @param syntax, String mit dem eine Eingabe auf das richtige Format hin
     * geprüft wird.
     * @param fehlermelgungtitel, Srting der den Titel der Fehlmeldung enthält.
     * @param fehlermeldung, String der die Fehlmeldung enthält.
     */
    @Override
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax,
            String fehlermelgungtitel, String fehlermeldung) {
        if (!textfield.getText().matches(syntax)) {//Falls Eingabe nicht mit der Syntax übereinstimmt.
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, fehlermeldung, fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
            //Mit dem Focus in das übergebene Eingabefeld springen
            textfield.requestFocusInWindow();
            textfield.selectAll();
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der die Eingabefelder die nicht ausgefüllt
     * worden sind, farblich markiert werden und eine Meldung ausgegeben wird,
     * inder der Benutzer darauf hingewiesen wird, alle Eingaben zu tätigen.
     *
     * @param list, Arraylist in der die Components die keine Eingaben erhalten
     * haben, gespeichert sind.
     * @param fehlermelgungtitel, Srting der den Titel der Fehlmeldung enthält.
     * @param fehlermeldung, String der die Fehlmeldung enthält.
     * @param farbe, Color in der der Hintergrund der Components markiert werden
     * soll
     */
    @Override
    public void fehlEingabenMarkierung(ArrayList<Component> list,
            String fehlermelgungtitel, String fehlermeldung, Color farbe) {
        //Meldung die darauf hinweist das nicht alle Eingaben getätigt worden sind.
        JOptionPane.showMessageDialog(null, fehlermeldung,
                fehlermelgungtitel, JOptionPane.WARNING_MESSAGE);
        list.get(0).requestFocusInWindow();// Fokus gelangt in das erste leere Eingabefeld
        // Alle leeren Eingabefelder werden farblich markiert.
        for (int i = 0; i <= list.size() - 1; i++) {
            list.get(i).setBackground(farbe);
        }

        list.clear();//ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 08.01.2015 Terrasi Anwendungslogik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Internalframe nicht mehr als Anzeigefenster
     * dargestellt wird, sondern als Fenster in dem man Daten ändern kann.
     */
    public void setStatusAender() {
        this.setTitle("Auftragskopf ändern");
//        zuruecksetzen();
        this.geschaeftspartner_jTextField.setEnabled(true);
        this.auftragskopfID_jTextField.setEnabled(false);
        this.auftragswert_jTextField.setEnabled(false);
        this.auftragsart_jComboBox.setEnabled(true);

        if (this.auftragsart_jComboBox.getSelectedIndex() == 0) {
            this.zahlungskonditionen_jComboBox.addItem("Bitte wählen");
            this.zahlungskonditionen_jComboBox.setEnabled(false);
        } else {
            this.zahlungskonditionen_jComboBox.setEnabled(true);
        }
        this.auftragstext_jTextArea.setEnabled(true);
        this.erfassungsdatum_jFormattedTextField.setEnabled(false);
        this.lieferdatum_jFormattedTextField.setEnabled(true);
        this.abschlussdatum_jFormattedTextField.setEnabled(true);
        this.erfasst_jRadioButton.setEnabled(true);
        this.freigegeben_jRadioButton.setEnabled(true);
        this.abgeschlossen_jRadioButton.setEnabled(true);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(true);
        this.menge_jTextField.setEnabled(true);
        this.erfassungsdatum_auftragsposition_jFormattedTextField.setEnabled(false);
        this.auftragsposition_jTable.setEnabled(true);
        jB_Anzeigen.setText("Anzeigen");
        jB_Anzeigen.setEnabled(true);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(true);
        this.hauptFenster.setComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 08.01.2015 Terrasi Anwendungslogik überarbeitet*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Internalframe wieder als Anzeigefenster dargestellt
     * wird. Dadurch sind keine Eingaben möglich.
     */
    public void setStatusAnzeigen() {
        this.setTitle("Auftragsposition anzeigen");
//        zuruecksetzen();
        this.geschaeftspartner_jTextField.setEnabled(false);
        this.auftragskopfID_jTextField.setEnabled(false);
        this.auftragswert_jTextField.setEnabled(false);
        this.auftragsart_jComboBox.setEnabled(false);
        this.zahlungskonditionen_jComboBox.setEnabled(false);
        this.auftragstext_jTextArea.setEnabled(false);
        this.erfassungsdatum_jFormattedTextField.setEnabled(false);
        this.lieferdatum_jFormattedTextField.setEnabled(false);
        this.abschlussdatum_jFormattedTextField.setEnabled(false);
        this.erfasst_jRadioButton.setEnabled(false);
        this.freigegeben_jRadioButton.setEnabled(false);
        this.abgeschlossen_jRadioButton.setEnabled(false);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(false);
        this.menge_jTextField.setEnabled(false);
        this.erfassungsdatum_auftragsposition_jFormattedTextField.setEnabled(false);
        this.auftragsposition_jTable.setEnabled(false);
        jB_Anzeigen.setText("Ändern");
        jB_Anzeigen.setEnabled(true);
        jB_Speichern.setEnabled(false);
        jB_Loeschen.setEnabled(false);
        this.hauptFenster.setComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Internalframe als Anlegenfenster dargestellt wird um
     * einen Auftragskopf alegen zu können.
     */
    public void setStatusAnlegen() {
        this.setTitle("Auftragskopf anlegen");
        zuruecksetzen();
        this.geschaeftspartner_jTextField.setEnabled(true);
        this.auftragskopfID_jTextField.setEnabled(false);
        this.auftragswert_jTextField.setEnabled(false);
        this.auftragsart_jComboBox.setEnabled(true);
        this.zahlungskonditionen_jComboBox.setEnabled(false);
        this.auftragstext_jTextArea.setEnabled(true);
        this.erfassungsdatum_jFormattedTextField.setEnabled(false);
        this.lieferdatum_jFormattedTextField.setEnabled(true);
        this.abschlussdatum_jFormattedTextField.setEnabled(true);
        this.erfasst_jRadioButton.setEnabled(true);
        this.freigegeben_jRadioButton.setEnabled(false);
        this.abgeschlossen_jRadioButton.setEnabled(false);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(true);
        this.menge_jTextField.setEnabled(true);
        this.erfassungsdatum_auftragsposition_jFormattedTextField.setEnabled(false);
        this.auftragsposition_jTable.setEnabled(true);
        jB_Anzeigen.setText("Anzeigen");
        jB_Anzeigen.setEnabled(false);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(true);
        this.hauptFenster.setComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setArtikel(HashMap<Long, Integer> artikel) {
        this.artikel = artikel;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setAbschlussdatum(Date abschlussdatum) {
        this.abschlussdatum = abschlussdatum;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setAbschlussdatum_jFormattedTextField(JFormattedTextField abschlussdatum_jFormattedTextField) {
        this.abschlussdatum_jFormattedTextField = abschlussdatum_jFormattedTextField;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 sch angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setArtikelid_jTextField(String id) {
        this.materialnummer_jTextField.setText(id);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setAuftragsart_jComboBox(String auftragsArt) {
        auftragsart_jComboBox.setSelectedItem(auftragsArt);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setAuftragskopfID_jTextField(String id) {
        this.auftragskopfID_jTextField.setText(id);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setAuftragspositionInTabelle_jTable(Object[] obj) {
        dtm.addRow(obj);
        auftragsposition_jTable.setModel(dtm);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setAuftragstext_jTextArea(String auftragstext) {
        this.auftragstext_jTextArea.setText(auftragstext);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setAuftragswert_jTextField(String wert) {
        this.auftragswert_jTextField.setText(wert);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setGeschaeftspartnerID(String id) {
        this.geschaeftspartner_jTextField.setText(id);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setErfassungsdatum_auftragsposition_jFormattedTextField(String erfassungsdatumAuftragsposition) {
        this.erfassungsdatum_auftragsposition_jFormattedTextField.setText(erfassungsdatumAuftragsposition);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setErfassungsdatum_jFormattedTextField(String erfassungsdatum) {
        this.erfassungsdatum_jFormattedTextField.setText(erfassungsdatum);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setLieferdatum_jFormattedTextField(String lieferdatum) {
        this.lieferdatum_jFormattedTextField.setText(lieferdatum);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setMaterialnummer_jTextField(String materialnummer) {
        this.materialnummer_jTextField.setText(materialnummer);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setMenge_jTextField(String menge) {
        this.menge_jTextField.setText(menge);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setPositionsnummer_jTextField(String positionsnummer) {
        this.positionsnummer_jTextField.setText(positionsnummer);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setzeEingabe(Auftragskopf auftragskopf) {
//    public void setzeEingabe(String geschaeftsPID, String auftragsKopfID,
//            String auftragsWert, String auftragsText, String auftragsArt,
//            String erfassungsDatum, String lieferDatum, String abschlussDatum,
//            String status) {ssss
        auftragspositionen = auftragskopf.getPositionsliste();

        this.geschaeftspartner_jTextField.setText(String.valueOf(auftragskopf.getGeschaeftspartner().getGeschaeftspartnerID()));
        this.auftragskopfID_jTextField.setText(String.valueOf(auftragskopf.getAuftragskopfID()));
        this.auftragswert_jTextField.setText(String.valueOf(auftragskopf.getWert()));

//        this.auftragsart_jComboBox.setSelectedItem(auftragsArt);
        if (auftragsart_jComboBox.getItemAt(0).toString().equals(auftragskopf.getTyp())) {
            zahlungskonditionen_jComboBox.addItem("Bitte wählen");
            auftragsart_jComboBox.setSelectedIndex(0);
        }
        if (auftragsart_jComboBox.getItemAt(1).toString().equals(auftragskopf.getTyp())) {
            auftragsart_jComboBox.setSelectedIndex(1);
        }
        if (auftragsart_jComboBox.getItemAt(2).toString().equals(auftragskopf.getTyp())) {
            auftragsart_jComboBox.setSelectedIndex(2);
        }
        if (auftragsart_jComboBox.getItemAt(3).toString().equals(auftragskopf.getTyp())) {
            auftragsart_jComboBox.setSelectedIndex(3);
        }
        this.auftragstext_jTextArea.setText(auftragskopf.getAuftragstext());

        this.erfassungsdatum_jFormattedTextField.
                setText(String.valueOf(gibDatumAlsString(auftragskopf.getErfassungsdatum())));
        this.lieferdatum_jFormattedTextField.
                setText(String.valueOf(gibDatumAlsString(auftragskopf.getLieferdatum())));
        this.abschlussdatum_jFormattedTextField.
                setText(String.valueOf(gibDatumAlsString(auftragskopf.getAbschlussdatum())));

        //Hasmap bekommt positionen
        artikel.clear();

        for (int i = 0; i < auftragspositionen.size(); i++) {

            artikel.put(auftragspositionen.get(i).getArtikel().getArtikelID(),
                    auftragspositionen.get(i).getMenge());

            summenWertFuerPos = auftragspositionen.get(i).getEinzelwert()
                    * auftragspositionen.get(i).getMenge();

            Object[] neuesObj = new Object[]{i + 1,
                auftragspositionen.get(i).getArtikel().getArtikelID(),
                auftragspositionen.get(i).getMenge(), summenWertFuerPos,
                gibDatumAlsString(abschlussdatum)};

            gesamtAuftragswert += summenWertFuerPos;

            dtm.addRow(neuesObj);

        }

        auftragsposition_jTable.setModel(dtm);

        auftragswert_jTextField.setText(String.valueOf(gesamtAuftragswert));
//        liste.clear();
        gesamtAuftragswert = 0.00;
        summenWertFuerPos = 0.00;
    }

    private String gibDatumAlsString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int tag = cal.get(Calendar.DAY_OF_MONTH);
        int mon = cal.get(Calendar.MONTH);
        mon = mon + 1;
        int jahr = cal.get(Calendar.YEAR);
        String tagAlsString;
        String monatAlsString;
        if (tag < 10) {
            tagAlsString = "0" + tag;
        } else {
            tagAlsString = "" + tag;
        }

        if (mon < 10) {
            monatAlsString = "0" + mon;
        } else {
            monatAlsString = "" + mon;
        }

        String ausgabeDatum = tagAlsString + "." + monatAlsString + "." + jahr;
        return ausgabeDatum;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NeuePosition_jButton;
    private javax.swing.JRadioButton abgeschlossen_jRadioButton;
    private javax.swing.JFormattedTextField abschlussdatum_jFormattedTextField;
    private javax.swing.JLabel abschlussdatum_jLabel;
    private javax.swing.JLabel auftragsID_jLabel;
    private javax.swing.JComboBox auftragsart_jComboBox;
    private javax.swing.JLabel auftragsart_jLabel;
    private javax.swing.JTextField auftragskopfID_jTextField;
    private javax.swing.JLabel auftragskopfdaten_titel_jLabel;
    private javax.swing.JTable auftragsposition_jTable;
    private javax.swing.JLabel auftragspositions_titel_jLabel;
    private javax.swing.JLabel auftragstext_jLabel;
    private javax.swing.JTextArea auftragstext_jTextArea;
    private javax.swing.JLabel auftragswert_jLabel;
    private javax.swing.JTextField auftragswert_jTextField;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel einzelwert_jLabel;
    private javax.swing.JTextField einzelwert_jTextField;
    private javax.swing.JRadioButton erfasst_jRadioButton;
    private javax.swing.JFormattedTextField erfassungsdatum_auftragsposition_jFormattedTextField;
    private javax.swing.JFormattedTextField erfassungsdatum_jFormattedTextField;
    private javax.swing.JLabel erfassungsdatum_jLabel;
    private javax.swing.JLabel erfassungsdatum_jLabel1;
    private javax.swing.JLabel eurosymbol_jLabel;
    private javax.swing.JRadioButton freigegeben_jRadioButton;
    private javax.swing.JLabel geschaeftspartnerID_jLabel;
    private javax.swing.JTextField geschaeftspartner_jTextField;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JFormattedTextField lieferdatum_jFormattedTextField;
    private javax.swing.JLabel lieferdatum_jLabel;
    private javax.swing.JLabel materialnummer_jLabel;
    private javax.swing.JTextField materialnummer_jTextField;
    private javax.swing.JLabel menge_jLabel;
    private javax.swing.JTextField menge_jTextField;
    private javax.swing.JLabel positionsnummer_jLabel;
    private javax.swing.JTextField positionsnummer_jTextField;
    private javax.swing.JLabel status_jLabel;
    private javax.swing.JComboBox zahlungskonditionen_jComboBox;
    private javax.swing.JLabel zahlungskonditionen_jLabel;
    // End of variables declaration//GEN-END:variables
}
