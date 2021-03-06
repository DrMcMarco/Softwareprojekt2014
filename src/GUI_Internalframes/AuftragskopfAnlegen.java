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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

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
/* 10.12.2014 Terrasi, angelegt, Sschnittstellenimplementierung, 
 Dokumentation und Logiküberarbetung */
/* 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button */
/* 06.01.2015 Terrasi, Anwendungslogik für das ändern und anzeigen eines
 Auftragskopfs. */
/* 08.01.2015 Terrasi, Überarbeitung der Anwendungslogik für anzeigen/ändern 
 Status und das hinzufügen von weiteren Funktion. */
/* 13.01.2015 Terrasi, Implementierung der DAO-Methoden zum anlegen eines 
 Auftragskopfs und von Auftragspositionen. Desweiteren deren wiedergabe in 
 einer Tabelle. */
/* 14.01.2015 Terrasi, Implementierung der Löschmethode der DAO und Überprüfung
 der Eingabefelder überarbeitet.*/
/* 17.01.2015 Schulz Such button und setMethode Artikel eingefügt.*/
/* 17.01.2015 Terrasi, Überarbeitung der ladeZahlungskonditionen-Methode.*/
/* 18.02.2015 TER, getestet und freigegeben */
public class AuftragskopfAnlegen extends javax.swing.JInternalFrame
        implements SchnittstelleFensterFunktionen {

    // Speichervariablen.
    Component letzteComponent;
    GUIFactory factory;
    DataAccessObject dao;
    SchnittstelleHauptfenster hauptFenster;

    DefaultTableModel dtm;// Variable für Tabelle.

    private String typ;

    // Hilfsvariablen
    private LinkedHashMap<Long, Integer> artikel;
    private String auftragsText;
    private Long geschaeftspartnerID;
    private Integer zahhlungskonditionID;
    private Date abschlussdatum;
    private Date lieferdatum;
    private Date berechnetesLieferdatum;

    // ArrayList für Eingabefelder des Auftragkopfes.
    private ArrayList<Component> fehlendeEingaben;

    // ArrayList für die Eingabefelder der Auftragsposition.
    private ArrayList<Component> fehlendeEingabenAuftragsposition;
    private Double gesamtAuftragswert = 0.00;// Variable für  Auftragswert
    private Double einzelwert = 0.00;// Variable für Einzelwert
    private Double summenWertFuerPos = 0.00;//Variable für die Summe der Positionswerte.
    private Collection<Zahlungskondition> zahlungskondditionAusDatenbank;
    private ArrayList<String> zahlungskonditionFuerCombobox = new ArrayList<>();

    // Boolische Variable die benötigt wird um zu prüfen ob die Daten bereits 
    // gespeichert worden sind.
    private boolean gespeichert = false;

    // Speichervariable für die Sperrzeit der Zahlungskondition.
    private Integer sperrzeit = 0;

    // Variablen für die erzeugung der Spalten und Zeilen für die
    // Auftragspositinstabelle. 
    private Vector spaltenNamen;

    // Speichervariablen für Datenbankdaten.
    private Auftragskopf kopf;
    private String dbGeschaeftspartnerID;
    private String dbAuftragstext;
    private String dbAuftragsart;
    private String dbZahlungskondition;
    private String dbStatus;
    private String dbLieferdatum;
    private String dbAbschlussdatum;

    private HashMap<Long, Integer> dbAuftragspositionen = new HashMap<>();
    private ArrayList<Auftragsposition> auftragspositionen = new ArrayList<>();

    private Auftragsposition position; // Objekt Erzeugung für die Positionstabelle.

    // Variable, um zu pruefen, ob alle Eingaben, wenn welche gemacht wurden, ok
    // sind.
    private boolean formularOK = true;

    // Varibalendefinition für Datumseingaben.
    public Date heute;// heutiges Datum
    public SimpleDateFormat format; //Umwandler für Datum
    //Umwandler für Datum um Tag zu erhalten
    public SimpleDateFormat tagesformat;

    // Konstanten für Farben
    Color warningfarbe = Color.YELLOW;
    private Color hintergrundfarbe = Color.WHITE;

    // Syntax
    private static final String MATERIALNUMMER_SYNTAX = "|\\d{1,9}?";

    // Augabetexte für Meldungen.
    private final String FEHLERMELDUNG_TITEL = "Fehlerhafte Eingabe";
    private final String FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL = "Fehlerhafte Eingabe";

    private final String FEHLERMELDUNG_LIEFERDATUM_TEXT = "Das Lieferdatum darf nicht "
            + "in der Vergangenheit liegen.\n"
            + "Bitte überprüfen Sie ihr Lieferdatum";

    private final String FEHLERMELDUNG_UNGUELTIGESDATUM_TEXT = "Üngültigesdatum."
            + " Bitte geben Sie eine gültiges Datum ein. (z.B 01.01.2016)";
    private final String FEHLERMELDUNG_UNVOLLSTAENDIGAUFTRAGSKOPF_TEXT = "Es wurden "
            + "nicht alle Eingaben getätigt.\n"
            + "Bitte geben Sie die benötigten Eingaben für den Auftragskopf "
            + "in die markierten Eingabefelder ein.";

    private final String FEHLERMELDUNG_UNVOLLSTAENDIGAUFTRAGSPOSITION_TEXT = "Es "
            + "wurden "
            + "nicht alle Eingaben getätigt.\n"
            + "Bitte geben Sie die benötigten Eingaben für eine Auftragsposition"
            + " in die markierten Eingabefelder ein.";

    private final String FEHLERMELDUNG_UNVOLLSTAENDIG_KEINEPOSITIONZUMKOPF_TEXT = "Es"
            + " muss mindestens eine Auftragsposition"
            + " zum Auftragskopf angelegt "
            + "\n werden. Bitte geben Sie alle notwendigen  Eingaben ein.";

    private final String FEHLERMELDUNGMENGE_TEXT = "Bitte eine gültige Menge "
            + "eingeben";

    private final String FEHLERMELDUNG_LIEFERDATUM_VOR_SPERRZEIT_TEXT
            = "Lieferdatum"
            + " muss bei einem Terminauftrag "
            + "\n mindestens ";

    private final String FEHLERMELDUNGMATERIAL_TEXT = "Die eingegebene "
            + "Materialnummer "
            + "ist nicht richtig! "
            + "\nBitte geben Sie eine gültige Materialnummer, die aus acht "
            + "Ziffern besteht, ein. (z.B. 1234567)";

    private final String FEHLERMELDUNGKEINEPOSITIONGEWAEHLT
            = " Bitte eine Position "
            + "wählen.";

    private final String FEHLERMELDUNGKEINEMATERIALNUMMER
            = "Bitte eine Materialnummer "
            + "eingeben.";

    private final String FEHLERMELDUNG_KEINEZAHLUNGSKONDITION_TITEL = "Keine "
            + "Zahlungskondition vorhanden.";
    private final String FEHLERMELDUNG_KEINEZAHLUNGSKONDITION_TEXT
            = "Die Auftragsart "
            + "besitzt keine Zahlungskondition."
            + "\n Bitte legen Sie mindestens eine Zahlungskondition für die "
            + "Auftragsart an,"
            + "\n oder whlen Sie eine andere Auftragsart aus.";

//Ausgaben bei Systemmeldungen.
    private final String ERFOLGREICHEANMELDUNG = "Ihr Auftrags wurde erfolgreich "
            + "angelegt.";
    private final String ERFOLGREICHESLOESCHEN = "Der Auftrag wurde erfolgreich"
            + " gelöscht.";
    private final String LOESCHENMELDUNG = "Sind Sie sicher, dass sie den Auftrag"
            + " löschen möchten ?";
    private final String LOESCHEN_TITEL = "Löschen eines Auftrags";
    private final String ERFOLGREICHGEAENDERT_TEXT = "Der Auftrag wurde erfolgreich"
            + " geändert.";
    final String ERFOLGREICHGEAENDERT_TITEL = "Auftrag geändert";

    private final String LIEFERUNGAMWOCHENENDE_TITEL = "Lieferdatum an "
            + "einem Wochenende.";
    private final String LIEFERUNGAMWOCHENENDE_TEXT = "Das Lieferdatum fällt "
            + "auf ein Wochende."
            + " Wollen Sie das Lieferdatum beibehalten?";

    private final String AENDERUNGVONDATEN_TEXT = "Es wurden Daten geändert. "
            + "Wollen sie wirklich"
            + "die Daten überspeichern?";
    private final String AENDERUNGVONDATEN_TITEL = "Änderung von Daten";
    private final String KEINEAENDERUNGEN_Titel = "Auftragskopf bereits angelegt";
    private final String KEINEAENDERUNGEN_TEXT = "Der Auftrag existiert bereits.";
    private final String DATENVERWERFEN_TITEL = "Daten verwerfen";
    private final String DATENVERWERFEN_TEXT = "Es wurden Daten eingegeben. Wollen Sie"
            + "diese Verwerfen ?";

    private final String FEHLERMELDUNG_STATUS_TITEL = "Abgeschlossener Auftrag";
    private final String FEHLERMMELDUNG_STATUSABGESCHLOSSEN = "Der Auftrag ist "
            + "bereits abgeschlossen und kann nicht"
            + "\n bearbeitet werden. ";

    private final String FEHLERMELDUNG_KEINAUFTRAG_TITEL = "Fehler beim Aufruf "
            + "des Auftrags.";

    // Variable für die Erzeugung der Spaltenüberschriften der 
    // Auftragspositionstabelle.
    private final String[] tabelle = {"Positionsnummer", "Materialnummer", "Menge",
        "Einzelwert", "Erfassungsdatum"};

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Konstruktor, Erzeugung eines AuftragskopfAnlegenobjektes.
     *
     * @param factory, Übergabe eines GUIFactoryobjektes.
     * @param mainView, Übergabe eines InterfaceMainViewobjektes.
     */
    public AuftragskopfAnlegen(GUIFactory factory, SchnittstelleHauptfenster mainView) {
        initComponents();
        // Übergabe der Parameter.
        this.factory = factory;
        this.hauptFenster = mainView;
        // Initialisierung der HashMap für Artikel
        artikel = new LinkedHashMap<>();
        try {
            // Variable, die ein Datum in ein vorgegebenes Format umwandelt.
            format = new SimpleDateFormat("dd.MM.yyyy");// Format dd.MM.yyyy
            tagesformat = new SimpleDateFormat("EEE");// Format für den Tag
            // Initialisierung des Datums
            heute = new Date();
            heute = format.parse(format.format(heute));// Datum parsen
            lieferdatum = heute;
            berechnetesLieferdatum = heute;
            abschlussdatum = heute;
        } catch (ParseException e) {// Fehlerbehandlung
            // Methodenaufruf um Meldung in der Statuszeile 
            // anzeigen zu lassen.
            this.hauptFenster.setzeStatusMeldung(e.getMessage());
        }

        //Initialisierung der Speichervariablen
        fehlendeEingaben = new ArrayList<Component>();
        fehlendeEingabenAuftragsposition = new ArrayList<Component>();

        //Voreingaben werden in die Eingabefelder gesetzt.
        erfassungsdatum_jFormattedTextField.setText(format.format(heute));
        erfassungsdatum_auftragsposition_jFormattedTextField.setText(
                format.format(heute));
        lieferdatum_jFormattedTextField.setText(format.format(heute));

        // Zuweisung der Documents an die Eingabefelder
        geschaeftspartner_jTextField.setDocument(
                new UniversalDocument("1234567890", false, 10));
        menge_jTextField.setDocument(
                new UniversalDocument("1234567890", false, 10));
        materialnummer_jTextField.setDocument(
                new UniversalDocument("1234567890", false, 10));

        // Radiobuttons und deren Zuweisung in eine Radiobuttongroup. 
        // Es wird immer ein Radiobutton selektiert.         
        erfasst_jRadioButton.setActionCommand("Erfasst");
        freigegeben_jRadioButton.setActionCommand("Freigegeben");
        abgeschlossen_jRadioButton.setActionCommand("Abgeschlossen");
        buttonGroup1.add(erfasst_jRadioButton);
        buttonGroup1.add(freigegeben_jRadioButton);
        buttonGroup1.add(abgeschlossen_jRadioButton);

        // Initialisierung eines DefaultTableModels.
        dtm = new DefaultTableModel() {
            // Methode wird überschrieben damit die Tabellenzeilen nicht
            // bearbeitet werden. Keine MAnipulation der einzelnen Zeilen
            // möglich.
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Initialisierung der Spalten für die Positionstabelle
        spaltenNamen = new Vector();
        // Übergabe der Spaltennamen in die Spalten
        for (String s : tabelle) {
            spaltenNamen.addElement(s);
        }
        // Übergabe der Spalten an das DefaultTableModel
        dtm.setColumnIdentifiers(spaltenNamen);
        // Übergabe des DefaultTableModel am die Tabelle
        auftragsposition_jTable.setModel(dtm);

        //Spalten der Positionstabelle können nicht verschoben werden
        auftragsposition_jTable.getTableHeader().setReorderingAllowed(false);
        // Mit DAO-Methode "gibNaechsteAuftragsID" wird die nächste Auftrags-ID
        // in dem entsprechendem Eingabefeld angezeigt.
        auftragskopfID_jTextField.setText(String.valueOf(
                GUIFactory.getDAO().gibNaechsteAuftragsID()));
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
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
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
        positionLoeschen_jButton = new javax.swing.JButton();
        jLSkontozeit1 = new javax.swing.JLabel();
        jLSkontozeit2 = new javax.swing.JLabel();
        jLMahnzeit1 = new javax.swing.JLabel();
        jLMahnzeit2 = new javax.swing.JLabel();
        jLMahnzeit3 = new javax.swing.JLabel();
        Skontozeit1_jTextField = new javax.swing.JTextField();
        Skontozeit2_jTextField = new javax.swing.JTextField();
        Mahnzeit1_jTextField = new javax.swing.JTextField();
        Mahnzeit2_jTextField = new javax.swing.JTextField();
        Mahnzeit3_jTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Auftragskopf anlegen");
        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(901, 550));
        setVisible(true);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(550, 550));

        jPanel1.setPreferredSize(new java.awt.Dimension(650, 880));

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        jB_Zurueck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Home2.PNG"))); // NOI18N
        jB_Zurueck.setToolTipText("Hauptmenü");
        jB_Zurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ZurueckActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Zurueck);

        jB_Speichern.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Speichern.PNG"))); // NOI18N
        jB_Speichern.setToolTipText("Speichern");
        jB_Speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SpeichernActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Bearbeiten.PNG"))); // NOI18N
        jB_Anzeigen.setToolTipText("Bearbeiten");
        jB_Anzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_AnzeigenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Löschen.PNG"))); // NOI18N
        jB_Loeschen.setToolTipText("Löschen");
        jB_Loeschen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_LoeschenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Suche.PNG"))); // NOI18N
        jB_Suchen.setToolTipText("Allgemeine Suche");
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
        geschaeftspartnerID_jLabel.setText("Geschäftspartner-ID*:");

        auftragstext_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        auftragstext_jLabel.setLabelFor(auftragstext_jTextArea);
        auftragstext_jLabel.setText("Auftragstext* :");
        auftragstext_jLabel.setToolTipText("");

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
        lieferdatum_jLabel.setText("Lieferdatum*       :");
        lieferdatum_jLabel.setToolTipText("");

        lieferdatum_jFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        lieferdatum_jFormattedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lieferdatum_jFormattedTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lieferdatum_jFormattedTextFieldFocusLost(evt);
            }
        });

        abschlussdatum_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        abschlussdatum_jLabel.setLabelFor(abschlussdatum_jFormattedTextField);
        abschlussdatum_jLabel.setText("Abschlussdatum   :");
        abschlussdatum_jLabel.setToolTipText("");

        abschlussdatum_jFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        abschlussdatum_jFormattedTextField.setEnabled(false);

        auftragswert_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        auftragswert_jLabel.setLabelFor(auftragswert_jTextField);
        auftragswert_jLabel.setText("Aufragswert :");

        auftragswert_jTextField.setEnabled(false);

        erfasst_jRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        erfasst_jRadioButton.setSelected(true);
        erfasst_jRadioButton.setText("Erfasst");
        erfasst_jRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                erfasst_jRadioButtonActionPerformed(evt);
            }
        });

        freigegeben_jRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        freigegeben_jRadioButton.setText("Freigegeben");
        freigegeben_jRadioButton.setEnabled(false);
        freigegeben_jRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freigegeben_jRadioButtonActionPerformed(evt);
            }
        });

        abgeschlossen_jRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        abgeschlossen_jRadioButton.setText("Abgeschlossen");
        abgeschlossen_jRadioButton.setEnabled(false);
        abgeschlossen_jRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abgeschlossen_jRadioButtonActionPerformed(evt);
            }
        });

        status_jLabel.setText("Status               :");

        eurosymbol_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        eurosymbol_jLabel.setText("€");

        zahlungskonditionen_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        zahlungskonditionen_jLabel.setText("Zahlungskonditionen :");

        zahlungskonditionen_jComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keine Zahlungskonditionen" }));
        zahlungskonditionen_jComboBox.setEnabled(false);
        zahlungskonditionen_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zahlungskonditionen_jComboBoxActionPerformed(evt);
            }
        });

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
            public void focusLost(java.awt.event.FocusEvent evt) {
                auftragstext_jTextAreaFocusLost(evt);
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
        materialnummer_jLabel.setText("Artikel - ID         :");

        menge_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        menge_jLabel.setText("Menge              :");

        einzelwert_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        einzelwert_jLabel.setText("Einzelwert          :");

        erfassungsdatum_jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        erfassungsdatum_jLabel1.setText("Erfassungsdatum :");

        positionsnummer_jTextField.setEnabled(false);

        materialnummer_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                materialnummer_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                materialnummer_jTextFieldFocusLost(evt);
            }
        });

        menge_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                menge_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                menge_jTextFieldFocusLost(evt);
            }
        });

        einzelwert_jTextField.setEnabled(false);

        erfassungsdatum_auftragsposition_jFormattedTextField.setEditable(false);
        erfassungsdatum_auftragsposition_jFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        erfassungsdatum_auftragsposition_jFormattedTextField.setEnabled(false);

        geschaeftspartner_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                geschaeftspartner_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                geschaeftspartner_jTextFieldFocusLost(evt);
            }
        });

        positionLoeschen_jButton.setText("Position entfernen");
        positionLoeschen_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionLoeschen_jButtonActionPerformed(evt);
            }
        });

        jLSkontozeit1.setText("Skontozeit 1 :");

        jLSkontozeit2.setText("Skontozeit 2 :");

        jLMahnzeit1.setText("Mahnzeit 1 :");

        jLMahnzeit2.setText("Mahnzeit 2 :");

        jLMahnzeit3.setText("Mahnzeit 3 :");

        Skontozeit1_jTextField.setEnabled(false);

        Skontozeit2_jTextField.setEnabled(false);

        Mahnzeit1_jTextField.setEnabled(false);

        Mahnzeit2_jTextField.setEnabled(false);

        Mahnzeit3_jTextField.setEnabled(false);

        jLabel2.setText("Tage");

        jLabel3.setText("Tage");

        jLabel4.setText("Tage");

        jLabel5.setText("Tage");

        jLabel6.setText("Tage");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel12.setText("*Pflichtfelder");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(auftragsID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(geschaeftspartnerID_jLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(geschaeftspartner_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(auftragswert_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(auftragswert_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(eurosymbol_jLabel))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(auftragsart_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(zahlungskonditionen_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(zahlungskonditionen_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(auftragsart_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(auftragskopfdaten_titel_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(auftragspositions_titel_jLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(NeuePosition_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(positionLoeschen_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(erfassungsdatum_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(erfassungsdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(abschlussdatum_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lieferdatum_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(abschlussdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(lieferdatum_jFormattedTextField)
                                            .addGap(1, 1, 1))))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(positionsnummer_jLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(materialnummer_jLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(positionsnummer_jTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(materialnummer_jTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(auftragstext_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(erfassungsdatum_jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(einzelwert_jLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(erfassungsdatum_auftragsposition_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(einzelwert_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(menge_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menge_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(112, 112, 112)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(abgeschlossen_jRadioButton)
                                            .addComponent(freigegeben_jRadioButton)
                                            .addComponent(erfasst_jRadioButton)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(jLSkontozeit2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(status_jLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                                            .addComponent(jLSkontozeit1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLMahnzeit1)
                                                        .addGap(45, 45, 45)))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLMahnzeit2)
                                                    .addGap(45, 45, 45)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLMahnzeit3)
                                                .addGap(45, 45, 45)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Skontozeit1_jTextField)
                                            .addComponent(Skontozeit2_jTextField)
                                            .addComponent(Mahnzeit1_jTextField)
                                            .addComponent(Mahnzeit2_jTextField)
                                            .addComponent(Mahnzeit3_jTextField))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(auftragskopfdaten_titel_jLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(auftragsID_jLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(geschaeftspartner_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(geschaeftspartnerID_jLabel)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(auftragsart_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(zahlungskonditionen_jLabel)
                            .addComponent(zahlungskonditionen_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(auftragsart_jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(auftragswert_jLabel)
                    .addComponent(auftragswert_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eurosymbol_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLSkontozeit1)
                            .addComponent(Skontozeit1_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLSkontozeit2)
                            .addComponent(Skontozeit2_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLMahnzeit1)
                            .addComponent(Mahnzeit1_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLMahnzeit2)
                            .addComponent(Mahnzeit2_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLMahnzeit3)
                            .addComponent(Mahnzeit3_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(auftragstext_jLabel)
                    .addComponent(jScrollPane1))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(erfassungsdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(erfassungsdatum_jLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lieferdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lieferdatum_jLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(abschlussdatum_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(abschlussdatum_jLabel)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(erfasst_jRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(freigegeben_jRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(abgeschlossen_jRadioButton))
                    .addComponent(status_jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(auftragspositions_titel_jLabel)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(positionsnummer_jLabel)
                            .addComponent(positionsnummer_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(materialnummer_jLabel)
                            .addComponent(materialnummer_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(menge_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(menge_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(einzelwert_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(einzelwert_jLabel))
                            .addGap(31, 31, 31))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(erfassungsdatum_auftragsposition_jFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(erfassungsdatum_jLabel1)
                            .addComponent(jLabel12))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NeuePosition_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(positionLoeschen_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(826, Short.MAX_VALUE)))
        );

        zahlungskonditionen_jLabel.getAccessibleContext().setAccessibleName("  Zahlungskonditionen :");
        materialnummer_jLabel.getAccessibleContext().setAccessibleName("Materialnummer :");
        materialnummer_jLabel.getAccessibleContext().setAccessibleDescription("");

        jScrollPane3.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBounds(0, 0, 830, 607);
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Selektiert das Eingabefeld des Lieferdatums
     *
     * @param evt
     */
    private void lieferdatum_jFormattedTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lieferdatum_jFormattedTextFieldFocusGained
        //Selektion des Eingabefeldes
        lieferdatum_jFormattedTextField.selectAll();
    }//GEN-LAST:event_lieferdatum_jFormattedTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Selektiert das Eingabefeld der Materialnummer
     *
     * @param evt
     */
    private void materialnummer_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_materialnummer_jTextFieldFocusGained
        materialnummer_jTextField.selectAll();//Selektion des Eingabefeldes
        //Übergabe der nächsten Positionsnummer
        positionsnummer_jTextField.setText(String.valueOf(artikel.size() + 1));
    }//GEN-LAST:event_materialnummer_jTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 13.01.2014 TER, Überarbeitung der Funktion */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Selektiert das Eingabefeld der Menge
     *
     * @param evt
     */
    private void menge_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_menge_jTextFieldFocusGained
        //Übergabe eines leeren Strings an das Eingabefeld
        menge_jTextField.setText("");
        menge_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_menge_jTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed für die Combox der Auftragsarten. Die Combobox für die
     * Zahlungskonditionen wird nur freigegeben wenn in der Auftragsart nicht
     * "Barauftrag" gewählt wird.
     *
     * @param evt
     */
    private void auftragsart_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_auftragsart_jComboBoxActionPerformed
        // Klassenvariablen
        Zahlungskondition zahlungskondition;
        Calendar calender = new GregorianCalendar();
        Long artikelid;
        Double neuerWert;
        // Stringvariable für die Zahlungskondition
        final String KEINEZK = "Keine Zahlungskonditionen";
        final String BESTELLAUFTRAG = "Bestellauftrag";
        final String BARAUFTRAG = "Barauftrag";
        final String TERMINAUFTRAG = "Terminauftrag";
        final String SOFORTAUFTRAG = "Sofortauftrag";

        try {
            // Falls in der Combox nicht der erste Eintrag ausgewählt wird,
            // wird die Combobox für die Zahlungskonditionen auf enable(true) 
            // gesetezt.
            if (auftragsart_jComboBox.getSelectedIndex() != 0) {
                zahlungskonditionen_jComboBox.setEnabled(true);
                // Methode fr das laden der Zahlungskonditionen zur jeweiligen
                // Auftragsart.
                ladeZahlungskonditionenAusDatenbank(
                        auftragsart_jComboBox.getSelectedItem().toString());
                // Zahlungskonditionscombobox erhält neue Liste von Konditionen.
                zahlungskonditionen_jComboBox.setModel(
                        new DefaultComboBoxModel(
                                zahlungskonditionFuerCombobox.toArray()));
            } else {//Combobox für die Zahlungskonditionen wird nicht wählbar gemacht
                // Liste für Konditionen wird geleert.
                zahlungskonditionFuerCombobox.clear();
                // Übergabe eines Wertes an die Konditionscombobox
                zahlungskonditionFuerCombobox.add(KEINEZK);
                // Zahlungskonditionscombobox erhält neue Liste von Konditionen.
                zahlungskonditionen_jComboBox.setModel(
                        new DefaultComboBoxModel(
                                zahlungskonditionFuerCombobox.toArray()));
                zahlungskonditionen_jComboBox.setEnabled(false);
            }
            // Speichervariable erhält vorgabewert von 0.0
            gesamtAuftragswert = 0.00;

            // If-Anweisung in der geprüft wird, 
            // welche Auftragsart gewählt worden ist.
            if (auftragsart_jComboBox.getSelectedItem().equals(BARAUFTRAG)) {
                // Einstellungen der Maske, wenn es ein Barauftrag ist.
                lieferdatum_jFormattedTextField.setEnabled(false);
                lieferdatum_jFormattedTextField.setText(format.format(heute));

                Skontozeit1_jTextField.setText("");
                Skontozeit2_jTextField.setText("");
                Mahnzeit1_jTextField.setText("");
                Mahnzeit2_jTextField.setText("");
                Mahnzeit3_jTextField.setText("");

            } else {// Falls es kein Barauftrag ist

                // Aufruf der Zahlungskondition-Methode 
                // gibZahlungskonditionNachId.
                zahlungskondition = GUIFactory.getDAO().
                        gibZahlungskonditionNachId(Long.parseLong(
                                        zahlungskonditionen_jComboBox.
                                        getSelectedItem().
                                        toString()));
                // Falls Sofortauftrag ausgewählt wurde.
                if (auftragsart_jComboBox.getSelectedItem().
                        equals(SOFORTAUFTRAG)) {
                    //Aufruf der Zahlungskondition-Methode getLieferzeitSofort
                    sperrzeit = zahlungskondition.getLieferzeitSofort();

                    // Sperrzeit wird auf den Tag des Datums drauf berechnet
                    calender.setTime(heute);
                    calender.add(Calendar.DAY_OF_MONTH, sperrzeit);
                    // Neuberechnetes Datum wird gespeichert.
                    berechnetesLieferdatum = calender.getTime();

                    // Methode um Skonto und Mahnzeiten anzeigen zu lassen
                    gibSkontoUndMahnzeiten(zahlungskondition, false);

                } else if (auftragsart_jComboBox.getSelectedItem().
                        equals(TERMINAUFTRAG)) {//Falls es ein terminauftrag ist.
                    sperrzeit = zahlungskondition.getSperrzeitWunsch();
                    // Sperrzeit wird auf den Tag des Datums drauf berechnet
                    calender.setTime(heute);
                    calender.add(Calendar.DAY_OF_MONTH, sperrzeit);
                    // Neuberechnetes Datum wird gespeichert.
                    berechnetesLieferdatum = calender.getTime();

                    // Methode um Skonto und Mahnzeiten anzeigen zu lassen. 
                    gibSkontoUndMahnzeiten(zahlungskondition, true);

                }

                if (auftragsart_jComboBox.getSelectedItem().toString().
                        equals(BESTELLAUFTRAG)) {

                    // Methodenaufruf um Skonto-/Mahnzeit und neues
                    // Lieferdatum anzeigen zu lassen
                    gibSkontoUndMahnzeiten(zahlungskondition, true);
                    // Heutiges Datum wird als Lieferdatum gesetzt
                    lieferdatum = heute;
                    // Heutiges Datum im Eingabefeld des Lieferdatums angezeigt.
                    lieferdatum_jFormattedTextField.setText(
                            format.format(heute));

                    // Da es ein Bestellauftrag ist, werden alle 
                    // Positionswerte neuberechnet. Für die Neuberechnung
                    // werden die Einkaufwerte der jeweilige Artikel genommen.
                    if (auftragspositionen.size() > 0) {// Ob Positionsliste leer ist.
                        // Positionstabelle löscht alle bisherigen angezeigten 
                        // Zeilen.
                        dtm.setRowCount(0);
                        // Durch Positionsliste interieren.
                        for (int i = 0; i < auftragspositionen.size(); i++) {

                            summenWertFuerPos = 0.0;//Positionswert auf 0 setzen

                            // Artikel-ID des Artikel der jeweiligen Position
                            artikelid = auftragspositionen.get(i).
                                    getArtikel().getArtikelID();

                            // Einkaufswert des Artikels erhalten
                            neuerWert = (GUIFactory.getDAO().
                                    gibArtikelOhneLKZ(artikelid)).
                                    getEinkaufswert();

                            // Position den neuen Einzelwert übergeben
                            auftragspositionen.get(i).setEinzelwert(neuerWert);

                            // Summe der Position berechnen und speichern
                            summenWertFuerPos = auftragspositionen.get(i).
                                    getEinzelwert() * artikel.get(artikelid);

                            // Objekt erzeugen mit den Daten der Position
                            Object[] neuesObj = new Object[]{i + 1,
                                auftragspositionen.get(i).getArtikel().
                                getArtikelID(),
                                artikel.get(artikelid), summenWertFuerPos,
                                gibDatumAlsString(abschlussdatum)};

                            // Objekt dem DefaultTabelModel übergeben
                            dtm.addRow(neuesObj);

                            // Positionswert auf Gesamtauftragswert drauf 
                            // rechnen.
                            gesamtAuftragswert += summenWertFuerPos;

                        }
                    }
                } // Falls es kein Bestellauftrag sein sollte.
                else if (!(auftragsart_jComboBox.getSelectedItem().toString().
                        equals(BESTELLAUFTRAG))) {
                    // Ob Positionsliste leer ist.
                    if (auftragspositionen.size() > 0) {
                        // Positionstabelle löscht alle bisherigen angezeigten 
                        // Zeilen.
                        dtm.setRowCount(0);
                        // Durch Positionsliste interieren.
                        for (int i = 0; i < auftragspositionen.size(); i++) {
                            summenWertFuerPos = 0.0;//Positionswert auf 0 setzen
                            // Artikel-ID des Artikel der jeweiligen Position
                            artikelid = auftragspositionen.get(i).
                                    getArtikel().getArtikelID();
                            // Verkaufswert des Artikels erhalten
                            neuerWert = (GUIFactory.getDAO().
                                    gibArtikelOhneLKZ(artikelid)).
                                    getVerkaufswert();
                            // Position den neuen Einzelwert übergeben
                            auftragspositionen.get(i).setEinzelwert(neuerWert);

                            // Summe der Position berechnen und speichern
                            summenWertFuerPos = auftragspositionen.get(i).
                                    getEinzelwert() * artikel.get(artikelid);

                            // Objekt erzeugen mit den Daten der Position
                            Object[] neuesObj = new Object[]{i + 1,
                                auftragspositionen.get(i).getArtikel().
                                getArtikelID(),
                                artikel.get(artikelid), summenWertFuerPos,
                                gibDatumAlsString(abschlussdatum)};

                            // Objekt dem DefaultTabelModel übergeben
                            dtm.addRow(neuesObj);

                            // Positionswert auf Gesamtauftragswert drauf 
                            // rechnen.
                            gesamtAuftragswert += summenWertFuerPos;
                        }
                    }
                }

                // Eingabefelder für Positionen erhalten einen leeren String.
                materialnummer_jTextField.setText("");
                menge_jTextField.setText("");
                einzelwert_jTextField.setText("");

                // Gesamtauftragswert wird im Auftragswerteingabefeld angezeigt.
                auftragswert_jTextField.setText(
                        String.valueOf(gesamtAuftragswert));

                // Tabelle erhält das neue DefaulTableModel
                auftragsposition_jTable.setModel(dtm);

            }
        } catch (ApplicationException e) {// Fehlerbehandlung
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    FEHLERMELDUNG_TITEL,
                    JOptionPane.ERROR_MESSAGE);

        } catch (NumberFormatException e) {// Fehlerbehandlung
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null, FEHLERMELDUNG_KEINEZAHLUNGSKONDITION_TEXT,
                    FEHLERMELDUNG_KEINEZAHLUNGSKONDITION_TITEL,
                    JOptionPane.ERROR_MESSAGE);
            // Barauftrag wird als Aufragsart gewählt.
            auftragsart_jComboBox.setSelectedIndex(0);
            // ActionPerformed fr Auftragsarten wird aufgerufen
            auftragsart_jComboBoxActionPerformed(null);
        }
    }//GEN-LAST:event_auftragsart_jComboBoxActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der die Skontozeiten und Mahnzeiten einer Zahlungskondition
     * in den passenden Eingabefelder angezeigt werden und die das Eingabefeld
     * des Lieferdatum wählbar macht oder nicht.
     *
     * @param zahlungskondition, Zhalungskonditionsobjekt
     * @param enabledSetzen, boolische Variable die bestimmt ob Liferdatum
     * wählbar ist oder nicht
     */
    public void gibSkontoUndMahnzeiten(Zahlungskondition zahlungskondition,
            boolean enabledSetzen) {
        // Neues Datum wird angezeigt und die passenden
        // Skonto und Mahnzeiten der jeweiligen Kondition
        // werden in den entsprechenden Felder angezeigt.
        lieferdatum = berechnetesLieferdatum;
        lieferdatum_jFormattedTextField.setText(
                format.format(berechnetesLieferdatum));
        lieferdatum_jFormattedTextField.
                setEnabled(enabledSetzen);
        // Skonto und Mahnzeiten werden an die 
        // Felder übergeben.
        Skontozeit1_jTextField.setText(
                String.valueOf(
                        zahlungskondition.getSkontozeit1()));
        Skontozeit2_jTextField.setText(
                String.valueOf(
                        zahlungskondition.getSkontozeit2()));
        Mahnzeit1_jTextField.setText(
                String.valueOf(
                        zahlungskondition.getMahnzeit1()));
        Mahnzeit2_jTextField.setText(
                String.valueOf(
                        zahlungskondition.getMahnzeit2()));
        Mahnzeit3_jTextField.setText(
                String.valueOf(
                        zahlungskondition.getMahnzeit3()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 13.01.2014 TER, Überarbeitung der Funktion. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Beim Focuslost des Eingabefeldes für die Materialnummer, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void materialnummer_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_materialnummer_jTextFieldFocusLost
        // Klassenvariablen
        long artikelnummer;
        int positionsnummer = 0;
        final String BESTELLAUFTRAG = "Bestellauftrag";

        try {// Try-Block

            //Speicher der materialnummer aus dem Eingabefeld 
            artikelnummer = Long.valueOf(materialnummer_jTextField.getText());

            if (auftragsart_jComboBox.getSelectedItem().toString().
                    equals(BESTELLAUFTRAG)) {
                // Einkaufswert des Material wird ermittelt und gspeichert.
                einzelwert = (GUIFactory.getDAO().gibArtikel(
                        (Long.parseLong(materialnummer_jTextField.getText()))).
                        getEinkaufswert());
                // Neuer Einzelwert wird in das Eingabefeld des einzelwertes 
                // angezeigt.
                einzelwert_jTextField.setText(String.valueOf(einzelwert));

            } else {
                // Verkaufswert des Material wird ermittelt und gspeichert.
                einzelwert = (GUIFactory.getDAO().gibArtikel(
                        (Long.parseLong(materialnummer_jTextField.getText()))).
                        getVerkaufswert());
                // Neuer Einzelwert wird in das Eingabefeld des einzelwertes 
                // angezeigt.
                einzelwert_jTextField.setText(String.valueOf(einzelwert));
            }

            // Falls Artikelhashmap den Artkel bereits entält
            if (artikel.containsKey(artikelnummer)) {
                // Erzeugung eines Iterators für die Schlsselwerte der 
                // Artikelhashmap.
                Iterator<Long> it = artikel.keySet().iterator();

                int i = 0;// Indexvariable
                // Schleife für das durchinterieren der Schlsselwerte
                while (it.hasNext()) {
                    i++;// Indexvariable wird eins hochgezählt
                    // Falls Artikelnummern gleich sind
                    if (it.next() == artikelnummer) {
                        // Positionsnummer erhält die nächste Position
                        positionsnummer = i;
                    }
                }
                // Die nchste Positionsnummer wird dem Positionseingaebfeld 
                // übergeben.
                positionsnummer_jTextField.setText(
                        String.valueOf(positionsnummer));
            }
            // Hintergrundsfarbe des MAterialseinagebfeldes wird Weiß gesetzt
            materialnummer_jTextField.setBackground(hintergrundfarbe);

        } catch (ApplicationException e) {// Fehlerbehandlung.
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
            // Fokus in das Materialeingabefeld setzen.
            materialnummer_jTextField.requestFocusInWindow();
            // Eingabefeld slektieren.
            geschaeftspartner_jTextField.selectAll();

        } catch (NumberFormatException e) {// Fehlerbehandlung.
            // Aufruf der "ueberpruefungVonFocusLost"- Methode
            ueberpruefungVonFocusLost(materialnummer_jTextField,
                    MATERIALNUMMER_SYNTAX,
                    FEHLERMELDUNG_TITEL, FEHLERMELDUNGMATERIAL_TEXT);
            // Einzelwerteingabefeld erhält leeren String.
            einzelwert_jTextField.setText("");
            // Hintergrundsfarbe des MAterialseinagebfeldes wird Weiß gesetzt
            materialnummer_jTextField.setBackground(hintergrundfarbe);
        }
    }//GEN-LAST:event_materialnummer_jTextFieldFocusLost

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Selektier das Eingabefeld des Auftragstextes
     *
     * @param evt
     */
    private void auftragstext_jTextAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragstext_jTextAreaFocusGained
        auftragstext_jTextArea.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_auftragstext_jTextAreaFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi, angelegt und dokumentiert */
    /* 17.01.2015 Terrasi, gibZahlungskonditionenFürAuftragsart implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
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
            zahlungskondditionAusDatenbank
                    = GUIFactory.getDAO().
                    gibZahlungskonditionenFürAuftragsart(
                            auftragsart);// Aufruf der Zahlungskonditionen zur jeweiligen Aufragsart.

            //ArrayList für die Zahlungskonditionscombobox wird gleert
            zahlungskonditionFuerCombobox.clear();
            // Erzeugung eines Iterator fr die Arraylist der Konditionen
            Iterator<Zahlungskondition> it
                    = zahlungskondditionAusDatenbank.iterator();

            while (it.hasNext()) {
                // Hinzufügen einer Kondition in die ArrayList der konditionen
                zahlungskonditionFuerCombobox.add(
                        String.valueOf(it.next().getZahlungskonditionID()));
            }

        } catch (NullPointerException |
                NoSuchElementException |
                ApplicationException e) {// Fehlerbehandlung.
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null,
                    FEHLERMELDUNG_KEINEZAHLUNGSKONDITION_TEXT,
                    FEHLERMELDUNG_KEINEZAHLUNGSKONDITION_TITEL,
                    JOptionPane.ERROR_MESSAGE);
            // Barauftrag wird als Aufragsart gewählt.
            auftragsart_jComboBox.setSelectedIndex(0);
            // ActionPerformed fr Auftragsarten wird aufgerufen
            auftragsart_jComboBoxActionPerformed(null);

        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2015 Terrasi, angelegt  */
    /* 13.01.2015 Terrasi, gibZahlungskonditionenFürAuftragsart implementiert */
    /* 18.02.2015 TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Action beim betätigen des Speicher-Buttons. Es wird die Prüfmethode
     * aufgerufen die wieder gibt ob irgendwelche Eingaben nicht getätigt worden
     * sind und markiert diese farblich.
     *
     * @param evt
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed

        // Klassenvariablen
        boolean positionenSindGleich = true;
        HashMap<Long, Integer> dbAuftragspositionen = new HashMap<>();

        String jetzigerStatus;
        String jetzigeZahlungskonditionen;
        final Integer keineZK = 0;

        // ArrayList für die leeren Eingabefelder wird geleert.
        fehlendeEingaben.clear();

        if (formularOK) {// Falls Formul ok ist

            // Aufruf der Schnittstellenmethode um auf Vollständigkeit der 
            // Eingaben zu prüfen.
            ueberpruefen();

            try {// Try-Block
                if (fehlendeEingaben.isEmpty()) {// Falls Liste leere ist.

                    // Falls mindestens eine Auftragsposition vorhanden ist.
                    if (auftragspositionen.isEmpty() == false) {

                        // Aufruf des Geschäftspartner. Falls es der
                        // Geschäftspartner nicht gültig ist oder
                        // ein Kunde ist und der Auftrag ein 
                        // Bestellauftrag sein sollte,
                        // wird dieser Fall dann von der Exception 
                        // behandelt.
                        GUIFactory.getDAO().gibGeschaeftspartner(
                                Long.valueOf(
                                        geschaeftspartner_jTextField.getText()));

                        // Speichern der Eingaben in Variablen
                        typ = auftragsart_jComboBox.getSelectedItem().toString();
                        auftragsText = auftragstext_jTextArea.getText();
                        geschaeftspartnerID = Long.parseLong(
                                geschaeftspartner_jTextField.getText());

                        // Lieferdatum parsen
                        lieferdatum = format.parse(
                                lieferdatum_jFormattedTextField.getText());
                        // Prfen ob das Lieferdatum auf ein Wochenendtag fällt.
                        if (tagesformat.format(
                                lieferdatum).equals("So")
                                || tagesformat.format(
                                        lieferdatum).
                                equals("Sa")) {

                            //PopUp mit "JA/Nein"-Abfrage.
                            int antwort = JOptionPane.showConfirmDialog(
                                    rootPane, LIEFERUNGAMWOCHENENDE_TEXT,
                                    LIEFERUNGAMWOCHENENDE_TITEL,
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            // Falls verneint wird, wird Barauftrag ausgewählt und
                            // Skonto sowie Mahnzeiteneingabefelder erhalten einen 
                            // leeren String.
                            if (antwort == JOptionPane.NO_OPTION) {
                                auftragsart_jComboBox.setSelectedIndex(0);
                                auftragsart_jComboBoxActionPerformed(evt);
                                Skontozeit1_jTextField.setText("");
                                Skontozeit2_jTextField.setText("");
                                Mahnzeit1_jTextField.setText("");
                                Mahnzeit2_jTextField.setText("");
                                Mahnzeit3_jTextField.setText("");
                                lieferdatum_jFormattedTextField.
                                        requestFocusInWindow();
                            } else {// Falls bejaht wird

                                // Wird geprüft ob Fenster das "Auftragskopf anlegen"-Fenster ist.
                                if (this.getTitle().equals("Auftragskopf anlegen")) {

                                    // Falls Auftragsart Barauftrag ist.
                                    if (auftragsart_jComboBox.getSelectedIndex() == 0) {

                                        // Ein Auftragskopfobjekt erzeugen
                                        GUIFactory.getDAO().erstelleAuftragskopf(typ,
                                                artikel,
                                                auftragsText, geschaeftspartnerID,
                                                keineZK,
                                                "erfasst", null, lieferdatum);
                                    } else {
                                        // Gewählte Zahlungskondition erhalten.
                                        zahhlungskonditionID = Integer.parseInt(
                                                zahlungskonditionen_jComboBox.
                                                getSelectedItem().toString());

                                        // Ein Auftragskopfobjekt erzeugen
                                        GUIFactory.getDAO().erstelleAuftragskopf(typ,
                                                artikel,
                                                auftragsText, geschaeftspartnerID,
                                                zahhlungskonditionID,
                                                "erfasst", null, lieferdatum);
                                    }
                                    // Meldung an Statuszeile übergeben.
                                    this.hauptFenster.setzeStatusMeldung(ERFOLGREICHEANMELDUNG);

                                    // Methode die bestimmte Eingabefelder leert
                                    zuruecksetzen();
                                } else if (this.getTitle().equals(
                                        "Auftragskopf ändern")) {
                                    // Position von DB aufrufen und speichern.
                                    dbAuftragspositionen = GUIFactory.getDAO().
                                            gibAuftragspositionen(
                                                    kopf.getAuftragskopfID());
                                    // Prüfung ob Positionen gleich Artikelhashmap ist.
                                    // Es soll geprüft werden ob die Positionen in der DB gleich
                                    // ist wie die neuen Positionen aus dem Fenster. 
                                    if (dbAuftragspositionen.size()
                                            == artikel.size()) {
                                        // Schleife für Positionen
                                        for (long artikelid : artikel.keySet()) {
                                            // Falls DB-Positionen Artikel enthalten.
                                            if (dbAuftragspositionen.
                                                    containsKey(artikelid)
                                                    && positionenSindGleich) {
                                                //Prfung ob Db-Position gleich neuer Position ist
                                                if (dbAuftragspositionen.get(artikelid)
                                                        == artikel.get(artikelid)) {
                                                    // Positionen sind gleich
                                                    positionenSindGleich = true;

                                                } else {
                                                    // Positionen sind nicht gleich.
                                                    positionenSindGleich = false;
                                                }
                                            } else {
                                                // Positionen sind nicht gleich.
                                                positionenSindGleich = false;

                                            }
                                        }
                                    } else {
                                        // Positionen sind nicht gleich.
                                        positionenSindGleich = false;
                                    }

                                    // Ermitteln welcher Auftragsstatus gewählt 
                                    // worden ist.
                                    ButtonModel bm = buttonGroup1.getSelection();
                                    if (bm.getActionCommand().equals("Erfasst")) {
                                        jetzigerStatus = "erfasst";
                                    } else if (bm.getActionCommand().
                                            equals("Freigegeben")) {
                                        jetzigerStatus = "freigegeben";
                                    } else {
                                        jetzigerStatus = "abgeschlossen";
                                    }
                                    // Falls Auftragsart nicht Barauftrag ist.
                                    if (!(auftragsart_jComboBox.
                                            getSelectedIndex() == 0)) {
                                        jetzigeZahlungskonditionen
                                                = zahlungskonditionen_jComboBox.
                                                getSelectedItem().toString();
                                    } else {// Falls Barauftrag.
                                        jetzigeZahlungskonditionen = "0";
                                    }
                                    // Vergleich der Einagben mit den Daten die 
                                    // in der DB hinterlegt sind.
                                    if (!(dbGeschaeftspartnerID.equals(
                                            geschaeftspartner_jTextField.getText())
                                            && dbAuftragsart.equals(
                                                    auftragsart_jComboBox.
                                                    getSelectedItem().
                                                    toString())
                                            && dbZahlungskondition.
                                            equals(jetzigeZahlungskonditionen)
                                            && dbAuftragstext.
                                            equals(auftragstext_jTextArea.
                                                    getText())
                                            && dbStatus.
                                            equals(jetzigerStatus)
                                            && dbLieferdatum.
                                            equals(lieferdatum_jFormattedTextField.
                                                    getText())
                                            && dbAbschlussdatum.equals(
                                                    abschlussdatum_jFormattedTextField.
                                                    getText())
                                            && positionenSindGleich == true)) { // Daten sind nicht gleich.

                                        //PopUp mit "JA/Nein"-Abfrage.
                                        int antwortAenderung = JOptionPane.showConfirmDialog(
                                                rootPane, AENDERUNGVONDATEN_TEXT,
                                                AENDERUNGVONDATEN_TITEL,
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE);

                                        // Falls bejaht wird der Auftragskopf verändert 
                                        // gespeichert.
                                        if (antwortAenderung == JOptionPane.YES_OPTION) {

                                            GUIFactory.getDAO().
                                                    aendereAuftrag(
                                                            Long.parseLong(
                                                                    auftragskopfID_jTextField.
                                                                    getText()),
                                                            geschaeftspartnerID,
                                                            Long.parseLong(
                                                                    jetzigeZahlungskonditionen),
                                                            artikel, auftragsText,
                                                            jetzigerStatus, null, lieferdatum);

                                            // Formular zurücksetzen.
                                            zuruecksetzen();
                                            gespeichert = true;
                                            // Zurück ins Hauptmenü sprigen.
                                            zurueckInsHauptmenue();
                                            // Meldung an Statuszeile übergeben.
                                            this.hauptFenster.setzeStatusMeldung(
                                                    ERFOLGREICHGEAENDERT_TEXT);
                                        } else {// Falls Frage mit NEIN beantwortet wird.
                                            gespeichert = false;
                                        }

                                    } else {// Falls Eingaben gleich sind wie Daten die in der DB hinterlegt siind.

                                        JOptionPane.showMessageDialog(null,
                                                KEINEAENDERUNGEN_TEXT,
                                                KEINEAENDERUNGEN_Titel,
                                                JOptionPane.OK_OPTION);
                                    }

                                }
                            }

                        } else {// Falls Lieferdatum ein Wochentag ist.
                            // Wird geprüft ob Fenster das "Auftragskopf anlegen"-Fenster ist.
                            if (this.getTitle().equals("Auftragskopf anlegen")) {

                                // Aufruf des Geschäftspartner. Falls es der
                                // Geschäftspartner nicht gültig ist oder
                                // ein Kunde ist und der Auftrag ein 
                                // Bestellauftrag sein sollte,
                                // wird dieser Fall dann von der Exception 
                                // behandelt.
                                GUIFactory.getDAO().gibGeschaeftspartner(
                                        Long.valueOf(
                                                geschaeftspartner_jTextField.getText()));

                                // Falls Auftragsart Barauftrag ist.
                                if (auftragsart_jComboBox.getSelectedIndex() == 0) {

                                    // Ein Auftragskopfobjekt erzeugen
                                    GUIFactory.getDAO().erstelleAuftragskopf(typ,
                                            artikel,
                                            auftragsText, geschaeftspartnerID,
                                            keineZK,
                                            "erfasst", null, lieferdatum);
                                } else {
                                    // Gewählte Zahlungskondition erhalten.
                                    zahhlungskonditionID = Integer.parseInt(
                                            zahlungskonditionen_jComboBox.
                                            getSelectedItem().toString());

                                    // Ein Auftragskopfobjekt erzeugen
                                    GUIFactory.getDAO().erstelleAuftragskopf(typ,
                                            artikel,
                                            auftragsText, geschaeftspartnerID,
                                            zahhlungskonditionID,
                                            "erfasst", null, lieferdatum);
                                }
                                // Meldung an Statuszeile übergeben.
                                this.hauptFenster.setzeStatusMeldung(ERFOLGREICHEANMELDUNG);

                                // Methode die bestimmte Eingabefelder leert
                                zuruecksetzen();
                            } else if (this.getTitle().equals(
                                    "Auftragskopf ändern")) {
                                // Position von DB aufrufen und speichern.
                                dbAuftragspositionen = GUIFactory.getDAO().
                                        gibAuftragspositionen(
                                                kopf.getAuftragskopfID());
                                // Prüfung ob Positionen gleich Artikelhashmap ist.
                                // Es soll geprüft werden ob die Positionen in der DB gleich
                                // ist wie die neuen Positionen aus dem Fenster. 
                                if (dbAuftragspositionen.size()
                                        == artikel.size()) {
                                    // Schleife für Positionen
                                    for (long artikelid : artikel.keySet()) {
                                        // Falls DB-Positionen Artikel enthalten.
                                        if (dbAuftragspositionen.
                                                containsKey(artikelid)
                                                && positionenSindGleich) {
                                            //Prfung ob Db-Position gleich neuer Position ist
                                            if (dbAuftragspositionen.get(artikelid)
                                                    == artikel.get(artikelid)) {
                                                // Positionen sind gleich
                                                positionenSindGleich = true;

                                            } else {
                                                // Positionen sind nicht gleich.
                                                positionenSindGleich = false;
                                            }
                                        } else {
                                            // Positionen sind nicht gleich.
                                            positionenSindGleich = false;

                                        }
                                    }
                                } else {
                                    // Positionen sind nicht gleich.
                                    positionenSindGleich = false;
                                }

                                // Ermitteln welcher Auftragsstatus gewählt 
                                // worden ist.
                                ButtonModel bm = buttonGroup1.getSelection();
                                if (bm.getActionCommand().equals("Erfasst")) {
                                    jetzigerStatus = "erfasst";
                                } else if (bm.getActionCommand().
                                        equals("Freigegeben")) {
                                    jetzigerStatus = "freigegeben";
                                } else {
                                    jetzigerStatus = "abgeschlossen";
                                }
                                // Falls Auftragsart nicht Barauftrag ist.
                                if (!(auftragsart_jComboBox.
                                        getSelectedIndex() == 0)) {
                                    jetzigeZahlungskonditionen
                                            = zahlungskonditionen_jComboBox.
                                            getSelectedItem().toString();
                                } else {// Falls Barauftrag.
                                    jetzigeZahlungskonditionen = "0";
                                }
                                // Vergleich der Einagben mit den Daten die 
                                // in der DB hinterlegt sind.
                                if (!(dbGeschaeftspartnerID.equals(
                                        geschaeftspartner_jTextField.getText())
                                        && dbAuftragsart.equals(
                                                auftragsart_jComboBox.
                                                getSelectedItem().
                                                toString())
                                        && dbZahlungskondition.
                                        equals(jetzigeZahlungskonditionen)
                                        && dbAuftragstext.
                                        equals(auftragstext_jTextArea.
                                                getText())
                                        && dbStatus.
                                        equals(jetzigerStatus)
                                        && dbLieferdatum.
                                        equals(lieferdatum_jFormattedTextField.
                                                getText())
                                        && dbAbschlussdatum.equals(
                                                abschlussdatum_jFormattedTextField.
                                                getText())
                                        && positionenSindGleich == true)) { // Daten sind nicht gleich.

                                    //PopUp mit "JA/Nein"-Abfrage.
                                    int antwortAenderung = JOptionPane.showConfirmDialog(
                                            rootPane, AENDERUNGVONDATEN_TEXT,
                                            AENDERUNGVONDATEN_TITEL,
                                            JOptionPane.YES_NO_OPTION,
                                            JOptionPane.QUESTION_MESSAGE);

                                    // Falls bejaht wird der Auftragskopf verändert 
                                    // gespeichert.
                                    if (antwortAenderung == JOptionPane.YES_OPTION) {

                                        GUIFactory.getDAO().
                                                aendereAuftrag(
                                                        Long.parseLong(
                                                                auftragskopfID_jTextField.
                                                                getText()),
                                                        geschaeftspartnerID,
                                                        Long.parseLong(
                                                                jetzigeZahlungskonditionen),
                                                        artikel, auftragsText,
                                                        jetzigerStatus, null, lieferdatum);

                                        // Formular zurücksetzen.
                                        zuruecksetzen();
                                        gespeichert = true;
                                        // Zurück ins Hauptmenü sprigen.
                                        zurueckInsHauptmenue();
                                        // Meldung an Statuszeile übergeben.
                                        this.hauptFenster.setzeStatusMeldung(
                                                ERFOLGREICHGEAENDERT_TEXT);
                                    } else {// Falls Frage mit NEIN beantwortet wird.
                                        gespeichert = false;
                                    }

                                } else {// Falls Eingaben gleich sind wie Daten die in der DB hinterlegt siind.

                                    JOptionPane.showMessageDialog(null,
                                            KEINEAENDERUNGEN_TEXT,
                                            KEINEAENDERUNGEN_Titel,
                                            JOptionPane.OK_OPTION);
                                }

                            }

                        }
                    } else {// Falls nicht mindestens eine Position angelegt worden ist.

                        // Wenn nicht mindestens eine Auftragsposition zum 
                        // Auftragskopf angelegt worden ist.
                        if (auftragsposition_jTable.getModel().
                                getRowCount() == 0) {
                            // Methodenaufruf um daraufhinzuweisen das nicht 
                            // alle eingaben getätigt worden sind
                            fehlEingabenMarkierung(
                                    fehlendeEingabenAuftragsposition,
                                    FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL,
                                    FEHLERMELDUNG_UNVOLLSTAENDIG_KEINEPOSITIONZUMKOPF_TEXT,
                                    warningfarbe);
                        }
                    }
                } else {// Falls nicht alle eingaben getätigt worden sind.
                    // Methodenaufruf um daraufhinzuweisen das nicht alle 
                    // eingaben getätigt worden sind
                    fehlEingabenMarkierung(fehlendeEingaben,
                            FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL,
                            FEHLERMELDUNG_UNVOLLSTAENDIGAUFTRAGSKOPF_TEXT,
                            warningfarbe);
                }
            } catch (ApplicationException e) {// Fehlerbehandlung.
                // Fehlermeldung als PopUp
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
            } catch (ParseException e) {// Fehlerbehandlung.
                // Fehlermeldung als PopUp
                JOptionPane.showMessageDialog(null,
                        FEHLERMELDUNG_UNGUELTIGESDATUM_TEXT,
                        FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
            }
        }
        formularOK = true;// Formular auf true setzen.
    }//GEN-LAST:event_jB_SpeichernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2015 Terrasi, angelegt und dokumnetiert */
    /* 18.02.2015 TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Selektiert das Eingabefeld des Geschäftspartners
     *
     * @param evt
     */
    private void geschaeftspartner_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_geschaeftspartner_jTextFieldFocusGained

        geschaeftspartner_jTextField.selectAll();//Eingabefeld wird selektiert
    }//GEN-LAST:event_geschaeftspartner_jTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2015, Terrasi, angelegt und dokumnetiert */
    /* 07.02.2015, Überarbeitung, Bei einer eingabe ist die 
     Hintegrundsfarbe Weiß*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Beim Focuslost des Eingabefeldes für den Geschäftspartner, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void geschaeftspartner_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_geschaeftspartner_jTextFieldFocusLost
        // Falls Eingabe getätigt worden ist.
        if (!(geschaeftspartner_jTextField.getText().isEmpty())) {
            // Hintergrundsfarbe wird gesetzt
            geschaeftspartner_jTextField.setBackground(hintergrundfarbe);

        }


    }//GEN-LAST:event_geschaeftspartner_jTextFieldFocusLost

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2015, Terrasi, angelegt und dokumnetiert */
    /* 07.02.2015, Überarbeitungder Logik */
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Beim Focuslost des Eingabefeldes für das Lieferdatum, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void lieferdatum_jFormattedTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lieferdatum_jFormattedTextFieldFocusLost
        // Klassenvariable.
        String TERMINAUFTRAG = "Terminauftrag";
        // Damit keine Meldung 2mal ausgegeben wird.
        if (evt.isTemporary()) {
            return;
        }

        format.setLenient(false);// 

        try {// Try-Block.
            //Lieferdatum wird geparst.
            lieferdatum = format.parse(
                    lieferdatum_jFormattedTextField.getText());

            // Wird geprüft ob das Eingabefeld nicht leer ist.
            if (!lieferdatum_jFormattedTextField.getText().equals("")) {
                if (lieferdatum.before(heute)) {//Datum liegt in der Vergangenheit

                    formularOK = false;
                    //Ausgabe eine Fehlermeldung
                    JOptionPane.showMessageDialog(null,
                            FEHLERMELDUNG_LIEFERDATUM_TEXT,
                            FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);

                    //Mit dem Focus in das Eingabefeld springen
                    lieferdatum_jFormattedTextField.requestFocusInWindow();
                    lieferdatum_jFormattedTextField.selectAll();
                } else if (auftragsart_jComboBox.getSelectedItem().
                        equals(TERMINAUFTRAG)
                        && lieferdatum.before(berechnetesLieferdatum)) {// Falls Datum vor dem berechnetem Datum liegt.

                    formularOK = false;
                    //Ausgabe eine Fehlermeldung
                    JOptionPane.showMessageDialog(null,
                            FEHLERMELDUNG_LIEFERDATUM_VOR_SPERRZEIT_TEXT
                            + sperrzeit + " Tage in der Zukunft liegen.",
                            FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);

                    //Mit dem Focus in das Eingabefeld springen
                    lieferdatum_jFormattedTextField.setText(format.
                            format(berechnetesLieferdatum));
                    // Fokus in Iengabefeld setzen.
                    lieferdatum_jFormattedTextField.requestFocusInWindow();
                    lieferdatum_jFormattedTextField.selectAll();

                }

            }
            //Setzen der Hintergrundsfarbe des Eingabefeldes.
            lieferdatum_jFormattedTextField.setBackground(hintergrundfarbe);
        } catch (ParseException e) {//Exception wird abgefangen. Fehlerbehandlung.

            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(rootPane,
                    FEHLERMELDUNG_UNGUELTIGESDATUM_TEXT, FEHLERMELDUNG_TITEL,
                    JOptionPane.ERROR_MESSAGE);

            //Mit dem Focus in das Eingabefeld springen
            lieferdatum_jFormattedTextField.setText(heute.toString());
            lieferdatum_jFormattedTextField.requestFocusInWindow();
            lieferdatum_jFormattedTextField.selectAll();
        }
    }//GEN-LAST:event_lieferdatum_jFormattedTextFieldFocusLost

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, Dokumentation und Logik. */
    /* 13.01.2015 Terrasi, Logik implementiert*/
    /* 07.02.2014 Terrasi, Überarbeitung der Berechnung des Gesamtwertes des 
     Auftrags*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Aktion in der überprüft wird ob alle erforderlichen Eingaben für eine
     * Auftragsposition vorhanden sind und diese dann zum jeweiligen
     * Auftrgaskopf anlegt
     *
     * @param evt
     */
    private void NeuePosition_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NeuePosition_jButtonActionPerformed
        // Klassenvariable.

        // Speichervariable für die Artikelmenge.
        Integer artikelMenge = 0;
        int neueMenge = 0;
        int index = -1;
        Long id;
        double einzelwert = 0.0;

        // ArrayList für Eingabefelder der Positionen leeren.
        fehlendeEingabenAuftragsposition.clear();

        //Aufruf der Schnittstellenmethode um auf Vollständigkeit 
        // der Eingaben zu prüfen.
        ueberpruefen();

        try {// Try-Block

            // Falls Eingaben für eine Position fehlt.
            if (fehlendeEingabenAuftragsposition.isEmpty()) {
                //Wenn ein Artikel bereits in einer Position angelegte worden ist.
                if (artikel.containsKey(Long.parseLong(
                        materialnummer_jTextField.getText()))) {

                    // Neue Menge des artikels berechnen und speichern.
                    artikelMenge = artikel.get(Long.parseLong(
                            materialnummer_jTextField.getText()))
                            + Integer.parseInt(menge_jTextField.getText());
                    // Übergabe der Menge an die HashMap.
                    artikel.put(
                            Long.parseLong(
                                    materialnummer_jTextField.getText()),
                            artikelMenge);
                    // Variablen zurück setzen.
                    artikelMenge = 0;
                    index = -1;
                    // Schleife um ber alle Positionen zu interieren.
                    for (int i = 0; i < auftragspositionen.size(); i++) {
                        // Wenn Position gleichen Artikel enthält wie Eingabe.
                        if (auftragspositionen.get(i).getArtikel().getArtikelID()
                                == Long.parseLong(
                                        materialnummer_jTextField.getText())) {
                            // Speichervairable die darauf hinweist das Artikel
                            // schon in Position enthalten ist.
                            index = i;
                            // Speichern der neuen Menge.
                            neueMenge = artikel.get(
                                    auftragspositionen.get(i).
                                    getArtikel().getArtikelID());
                        }
                    }
                } else {// Falls Material noch nicht in irgend einer Position angelegt ist.

                    // Hashmap für Artikel erhält einen Artikel und dessen Menge
                    artikel.put(Long.parseLong(
                            materialnummer_jTextField.getText()),
                            Integer.parseInt(menge_jTextField.getText()));

                    //Erzeugung eines Auftragspositionsobjektes
                    position = new Auftragsposition(null,
                            GUIFactory.getDAO().gibArtikel(
                                    Long.parseLong(
                                            materialnummer_jTextField.getText())),
                            Integer.parseInt(menge_jTextField.getText()),
                            Double.parseDouble(einzelwert_jTextField.getText()),
                            abschlussdatum);

                    //Auftragsposition wird der Liste an Auftragspositionen zugewiesen.
                    auftragspositionen.add(position);
                }
                // Positionstabelle löscht alle Zeilen
                dtm.setRowCount(0);

                // Schleife mit der alle Auftragspositionen ausgelesen werden
                for (int i = 0; i < auftragspositionen.size(); i++) {

                    // Für den gleichen Artikel
                    if (i == index) {

                        // Summe jeder einzelnen Position wird berechnet 
                        // und in Speichervairable gespeichert.
                        summenWertFuerPos = Double.parseDouble(
                                einzelwert_jTextField.getText())
                                * artikel.get(Long.parseLong(
                                                materialnummer_jTextField.getText()));

                        // Erzeugung eines Objektes für die Positionstabelle.
                        Object[] neuesObj = new Object[]{i + 1,
                            auftragspositionen.get(i).getArtikel().getArtikelID(),
                            neueMenge, summenWertFuerPos,
                            gibDatumAlsString(abschlussdatum)};

                        // Positionspreise werten aufsummiert und gespeichert
                        gesamtAuftragswert += summenWertFuerPos;

                        // Defaultmodell erhält das Objectobjekt und fügt eine
                        // neue Zeile hinzu.
                        dtm.addRow(neuesObj);

                    } else {// neuen Artikel anlegen

                        // Summe jeder einzelnen Position wird berechnet und 
                        // in Speichervairable gespeichert.
                        id = auftragspositionen.get(i).getArtikel().
                                getArtikelID();
                        // Summer für Auftragsposition berechnen und speichern.
                        summenWertFuerPos = auftragspositionen.get(i).
                                getEinzelwert()
                                * artikel.get(id);

                        //Erzeugung eines Objects mit den Daten der Position
                        Object[] neuesObj = new Object[]{i + 1,
                            auftragspositionen.get(i).getArtikel().
                            getArtikelID(),
                            artikel.get(id), summenWertFuerPos,
                            gibDatumAlsString(abschlussdatum)};

                        // Positionspreise werten aufsummiert und gespeichert
                        gesamtAuftragswert += summenWertFuerPos;

                        // Defaultmodell erhält das Objectobjekt und fügt eine
                        // neue Zeile hinzu.
                        dtm.addRow(neuesObj);

                    }
                }
                // Tabelle bekommt Defaultmodell mit den Zeilen und den 
                // jeweiligen Objekten zugewiesen und gibt diese wieder
                auftragsposition_jTable.setModel(dtm);

                // Auftragswertfeld erhält die aufsummierte Menge
                // an Positionswerten.
                auftragswert_jTextField.
                        setText(String.valueOf(gesamtAuftragswert));

                // Speichervairablen werden wieder auf null gesetzt
                gesamtAuftragswert = 0.00;
                summenWertFuerPos = 0.00;

            } else {// Falls Eingaben noch fählen
                fehlEingabenMarkierung(fehlendeEingabenAuftragsposition,
                        FEHLERMELDUNG_UNVOLLSTAENDIG_TITEL,
                        FEHLERMELDUNG_UNVOLLSTAENDIGAUFTRAGSPOSITION_TEXT,
                        warningfarbe);
            }
        } catch (ApplicationException e) {// Fehlerbehandlung.
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {// Fehlerbehandlung.
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null, FEHLERMELDUNGKEINEMATERIALNUMMER,
                    FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
        }
        // ArrayList für die fehlenden Eingaben wird geleert.
        fehlendeEingabenAuftragsposition.clear();

    }//GEN-LAST:event_NeuePosition_jButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, Dokumentation und Logik. */
    /* 16.12.2014 Terrasi, Logik implementiert*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        // Klassenvariable.
        String jetzigerStatus = "";

        if (formularOK) {// Wenn Formular ok ist.

            if (this.getTitle().equals("Auftragskopf anlegen")) {// Falls Auftragskopf anlegen.

                // Wird geprüft ob irgendwo Eingaben getätigt worden sind.
                if (!(geschaeftspartner_jTextField.getText().equals("")
                        && auftragsart_jComboBox.getSelectedIndex() == 0
                        && auftragstext_jTextArea.getText().equals("")
                        && lieferdatum_jFormattedTextField.getText().
                        equals(erfassungsdatum_auftragsposition_jFormattedTextField.getText())
                        && materialnummer_jTextField.getText().equals("")
                        && menge_jTextField.getText().equals("")
                        && auftragsposition_jTable.getRowCount() == 0)) {

                    //PopUp mit "JA/Nein"-Abfrage.
                    int antwort = JOptionPane.showConfirmDialog(rootPane,
                            DATENVERWERFEN_TEXT,
                            DATENVERWERFEN_TITEL, JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    //Falls bejaht wird, werden die Daten verworfen.
                    if (antwort == JOptionPane.YES_OPTION) {

                        // Aufruf der "zurueckInsHauptmenue"-Methode.
                        zurueckInsHauptmenue();
                    }

                } else {// Falls keine Eingaben getätigt worden sind. 

                    // Aufruf der "zurueckInsHauptmenue"-Methode.
                    zurueckInsHauptmenue();
                }
            } else if (this.getTitle().equals("Auftragskopf anzeigen")) {
                // Aufruf der "zurueckInsHauptmenue"-Methode.
                zurueckInsHauptmenue();

            } else {// Falls "Auftragskopf ändern" ist

                // Ermitteln welcher Auftragsstatus gewählt worden ist
                ButtonModel bm = buttonGroup1.getSelection();
                if (bm.getActionCommand().equals("Erfasst")) {
                    jetzigerStatus = "erfasst";
                } else if (bm.getActionCommand().equals("Freigegeben")) {
                    jetzigerStatus = "freigegeben";
                } else {
                    jetzigerStatus = "abgeschlossen";
                }
                // Falls Eingaben nicht gleich sind wie Daten aus der DB.
                if (!(geschaeftspartner_jTextField.getText().equals(dbGeschaeftspartnerID)
                        && auftragsart_jComboBox.getSelectedItem().toString().equals(dbAuftragsart)
                        && auftragstext_jTextArea.getText().equals(dbAuftragstext)
                        && lieferdatum_jFormattedTextField.getText().
                        equals(dbLieferdatum)
                        && abschlussdatum_jFormattedTextField.getText().equals(
                                dbAbschlussdatum)
                        && jetzigerStatus.equals(dbStatus)
                        && materialnummer_jTextField.getText().equals("")
                        && menge_jTextField.getText().equals("")
                        && dbAuftragspositionen.size() == artikel.size())
                        && gespeichert == false) {

                    //PopUp mit "JA/Nein"-Abfrage.
                    int antwort = JOptionPane.showConfirmDialog(rootPane,
                            DATENVERWERFEN_TEXT,
                            DATENVERWERFEN_TITEL, JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    //Falls bejaht wird, werden die Daten verworfen..
                    if (antwort == JOptionPane.YES_OPTION) {
                        // Aufruf der "zurueckInsHauptmenue"-Methode.
                        zurueckInsHauptmenue();
                    }
                } else {// Falls Eingaben gleich sind wie Daten aus DB.
                    // Aufruf der "zurueckInsHauptmenue"-Methode.
                    zurueckInsHauptmenue();

                }

            }
        }


    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man direkt ins Hauptmenü kehrt.
     */
    public void zurueckInsHauptmenue() {
        zuruecksetzen();// Eingabefelder werden zurückgesetzt.
        letzteComponent = null;// Initialisierung der Componentspeichervariable
        //Erhalten über GUIFactorymethode die letzte aufgerufene View und 
        // speichern diese in Variable.
        letzteComponent = this.factory.zurueckButton();
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        // Übergebene Component wird sichtbar gemacht.
        letzteComponent.setVisible(true);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 08.01.2015 Terrasi AuftragskopfAnlegenMaske mit der Anzeigen/Ändern-
     Funktion angepasst.*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Beim betätigen des Anzeigen/Ändern Buttons wird je nach Modus die Maske
     * passend angezeigt.
     *
     * @param evt, Event
     */
    private void jB_AnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AnzeigenActionPerformed
        try {// Try-Block.
            //Falls Fenster "Auftragskopf anzeigen" ist.
            if (this.getTitle().equals("Auftragskopf anzeigen")) {
                // Falls Auftrag abgeschlossen ist.
                if ((GUIFactory.getDAO().gibAuftragskopf(
                        Long.parseLong(auftragskopfID_jTextField.getText())).
                        getStatus().getStatus().equals("abgeschlossen"))) {

                    //Ausgabe einer Fehlermeldung
                    JOptionPane.showMessageDialog(null,
                            FEHLERMMELDUNG_STATUSABGESCHLOSSEN,
                            FEHLERMELDUNG_STATUS_TITEL,
                            JOptionPane.WARNING_MESSAGE);

                } else {// Falls Auftrag nicht abgeshlossen ist.
                    //Button in Ändernstatus setzen.
                    this.setzeStatusAenderButton();
                    // Eingaben des Auftrags mit Methode in die passenden
                    // Eingabefelder übergeben.
                    setzeEingabe(GUIFactory.getDAO().gibAuftragskopf(
                            Long.parseLong(
                                    auftragskopfID_jTextField.getText())));
                }

            }
        } catch (ApplicationException e) {// Fehlerbehandlung.
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    FEHLERMELDUNG_KEINAUFTRAG_TITEL, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jB_AnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi, angelegt */
    /* 14.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
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

        if (formularOK) {
            // Erzeugung einer Abfrage und Speicherung der antwort
            int antwort = JOptionPane.showConfirmDialog(rootPane,
                    LOESCHENMELDUNG,
                    LOESCHEN_TITEL, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            try {// Try-Block

                if (antwort == JOptionPane.YES_OPTION) {// Abfrage ob gegebene Antwort "Ja" ist.

                    // Aufruf der Löschmethode der DAO.
                    GUIFactory.getDAO().loescheAuftrag(
                            Long.parseLong(auftragskopfID_jTextField.getText()));

                    // Meldung wird an Statuszeile übergeben.
                    this.hauptFenster.setzeStatusMeldung(ERFOLGREICHESLOESCHEN);

                    zurueckInsHauptmenue();// Zurück ins Hauptmenü springen.
                }
            } catch (ApplicationException | NullPointerException e) {// Fehlerbehandlung.
                // Fehlermeldung als PopUp
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jB_LoeschenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Sch, angelegt */
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        this.hauptFenster.rufeSuche(this);// Suche das Fenster übergeben.
    }//GEN-LAST:event_jB_SuchenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi, angelegt und dokumentiert*/
    /* 07.02.2015 Terrasi, Überarbeitung der Neuberechnung des Gesamtwertes*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Methode um eine ausgewählte Auftragsposition aus der
     * Auftragspositionstabelle zu löschen.
     *
     * @param evt
     */
    private void positionLoeschen_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionLoeschen_jButtonActionPerformed

        try {
            // Artikel wird aus der Artikelhashmap entfernt.
            artikel.remove(auftragspositionen.
                    get(auftragsposition_jTable.getSelectedRow())
                    .getArtikel().getArtikelID());
            // Gewählte Position wird aus der Tabelle gelöscht
            auftragspositionen.remove(auftragsposition_jTable.getSelectedRow());

            dtm.setRowCount(0);// Tabelle wird geleert.

            // Schleife um über alle Position zu interieren.
            for (int i = 0; i < auftragspositionen.size(); i++) {

                if (auftragsart_jComboBox.getSelectedItem().
                        equals("Bestellauftrag")) {// Falls Auftragsart Bestellauftrag ist
                    // Neue Summe für die Position berechnen.
                    summenWertFuerPos = auftragspositionen.get(i).
                            getArtikel().getEinkaufswert()
                            * artikel.get(auftragspositionen.get(i).
                                    getArtikel().getArtikelID());
                } else {// Alle anderen Auftragsarten
                    summenWertFuerPos = auftragspositionen.get(i).
                            getArtikel().getVerkaufswert()
                            * artikel.get(auftragspositionen.get(i).
                                    getArtikel().getArtikelID());
                }

                // Objekterzeugung
                Object[] neuesObj = new Object[]{i + 1,
                    auftragspositionen.get(i).getArtikel().getArtikelID(),
                    artikel.get(
                    auftragspositionen.get(i).getArtikel().
                    getArtikelID()), summenWertFuerPos,
                    gibDatumAlsString(abschlussdatum)};

                dtm.addRow(neuesObj);//Übergabe des Objektes an die DefaultTableModel

                // Neuer Positionswert wird auf den bisherigen Auftragswert 
                // drauf gerechnet.
                gesamtAuftragswert += summenWertFuerPos;

            }
            // Übergabe des Auftragswertes an das passende Eingabefeld.
            auftragswert_jTextField.setText(String.valueOf(gesamtAuftragswert));
            gesamtAuftragswert = 0.00;// speichervariabel wird auf 0 gesetzt.

            // Tabelle bekommt neuen DefaultTableModel
            auftragsposition_jTable.setModel(dtm);

        } catch (ArrayIndexOutOfBoundsException e) {// Fehlerbehandlung.
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null,
                    FEHLERMELDUNGKEINEPOSITIONGEWAEHLT, FEHLERMELDUNG_TITEL,
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_positionLoeschen_jButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt und dokumentiert*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Methode für den FocusLost des Auftragstexteingabefeldes.
     *
     * @param evt
     */
    private void auftragstext_jTextAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragstext_jTextAreaFocusLost
        // Falls Eingaben getätigt worden sind.
        if (!(auftragstext_jTextArea.getText().isEmpty())) {
            // Hintergrundsfarbe wird gesetzt
            auftragstext_jTextArea.setBackground(hintergrundfarbe);
        }
    }//GEN-LAST:event_auftragstext_jTextAreaFocusLost

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt und dokumentiert*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Methode für den FocusLost des Mengeneingabefeldes.
     *
     * @param evt
     */
    private void menge_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_menge_jTextFieldFocusLost
        //Setzen der Hintergrundsfarbe des Eingabefeldes
        menge_jTextField.setBackground(hintergrundfarbe);
        // Methodenaufruf von ueberpruefungVonFocusLost
        ueberpruefungVonFocusLost(menge_jTextField, MATERIALNUMMER_SYNTAX,
                FEHLERMELDUNGMENGE_TEXT, FEHLERMELDUNGMENGE_TEXT);
    }//GEN-LAST:event_menge_jTextFieldFocusLost

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt und dokumentiert*/
    /* 13.01.2015 Terrasi, Überarbeitung der Logik*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed für die Zahlungskonditionscombobox.
     *
     * @param evt, event
     */
    private void zahlungskonditionen_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zahlungskonditionen_jComboBoxActionPerformed
        // Klassenvariablen.
        final String TERMINAUFTRAG = "Terminauftrag";
        final String SOFORTAUFTRAG = "Sofortauftrag";
        Zahlungskondition zahlungskondition;

        // Erzeugung eines Calenderobjektes.
        Calendar calender = new GregorianCalendar();
        try {// Try-Block.
            // Zahlungskondition aus Datenbank holen mit DAO-Methode.
            zahlungskondition
                    = GUIFactory.getDAO().
                    gibZahlungskonditionNachId(Long.parseLong(
                                    zahlungskonditionen_jComboBox.getSelectedItem().
                                    toString()));

            // Falls Terminauftrag gewählt worden ist.
            if ((auftragsart_jComboBox.getSelectedItem().equals(TERMINAUFTRAG))) {
                //Berechnung des Lieferdatums.
                sperrzeit = zahlungskondition.getSperrzeitWunsch();
                calender.setTime(heute);
                calender.add(Calendar.DAY_OF_MONTH, sperrzeit);

                berechnetesLieferdatum = calender.getTime();

                lieferdatum = berechnetesLieferdatum;

                // Übergabe des neuen Lieferdatums an das eingabefeld
                lieferdatum_jFormattedTextField.setText(
                        format.format(berechnetesLieferdatum));
                // Lieferdatum ist enabled true
                lieferdatum_jFormattedTextField.setEnabled(true);

                // Übergabe der Skonto und Mahnzeiten an die entsprechenden 
                // Eingabefelder.
                Skontozeit1_jTextField.setText(
                        String.valueOf(zahlungskondition.getSkontozeit1()));
                Skontozeit2_jTextField.setText(
                        String.valueOf(zahlungskondition.getSkontozeit2()));
                Mahnzeit1_jTextField.setText(
                        String.valueOf(zahlungskondition.getMahnzeit1()));
                Mahnzeit2_jTextField.setText(
                        String.valueOf(zahlungskondition.getMahnzeit2()));
                Mahnzeit3_jTextField.setText(
                        String.valueOf(zahlungskondition.getMahnzeit3()));

            } else if ((auftragsart_jComboBox.
                    getSelectedItem().equals(SOFORTAUFTRAG))) {

                //Berechnung des Lieferdatums.
                sperrzeit = zahlungskondition.getLieferzeitSofort();
                calender.add(Calendar.DAY_OF_MONTH, sperrzeit);
                calender.setTime(heute);
                calender.add(Calendar.DAY_OF_MONTH, sperrzeit);

                berechnetesLieferdatum = calender.getTime();

                lieferdatum = berechnetesLieferdatum;

                // Übergabe des neuen Lieferdatums an das eingabefeld
                lieferdatum_jFormattedTextField.setText(
                        format.format(berechnetesLieferdatum));
                // Lieferdatum ist enabled true
                lieferdatum_jFormattedTextField.setEnabled(false);

                // Übergabe der Skonto und Mahnzeiten an die entsprechenden 
                // Eingabefelder.
                Skontozeit1_jTextField.setText(
                        String.valueOf(zahlungskondition.getSkontozeit1()));
                Skontozeit2_jTextField.setText(
                        String.valueOf(zahlungskondition.getSkontozeit2()));
                Mahnzeit1_jTextField.setText(
                        String.valueOf(zahlungskondition.getMahnzeit1()));
                Mahnzeit2_jTextField.setText(
                        String.valueOf(zahlungskondition.getMahnzeit2()));
                Mahnzeit3_jTextField.setText(
                        String.valueOf(zahlungskondition.getMahnzeit3()));
            } else {// Falls kein Sofortauftrag oder Terminauftrag gewählt worden ist.

                // Heutiges Datum wird als Lieferdatum angegeben.
                lieferdatum = heute;
                lieferdatum_jFormattedTextField.setText(format.format(heute));
                lieferdatum_jFormattedTextField.setEnabled(true);
            }

        } catch (ApplicationException e) {// Fehlerbehandlung.
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_zahlungskonditionen_jComboBoxActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt,Logik und dokumentiert*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed bei der man keine Position anlegen kann.
     *
     * @param evt, Event
     */
    private void freigegeben_jRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_freigegeben_jRadioButtonActionPerformed
        // Position anlegen und löschen sind enabled = false
        NeuePosition_jButton.setEnabled(false);
        positionLoeschen_jButton.setEnabled(false);
        // Hintergrundfarbe der Eingabefelder für eine Position werden Weiß
        // gesetzt.
        materialnummer_jTextField.setBackground(hintergrundfarbe);
        menge_jTextField.setBackground(hintergrundfarbe);

    }//GEN-LAST:event_freigegeben_jRadioButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt,Logik und dokumentiert*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed bei der man eine Position anlegen kann.
     *
     * @param evt, Event
     */
    private void erfasst_jRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_erfasst_jRadioButtonActionPerformed
        // Position anlegen und löschen sind enabled = true
        NeuePosition_jButton.setEnabled(true);
        positionLoeschen_jButton.setEnabled(true);
    }//GEN-LAST:event_erfasst_jRadioButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt,Logik und dokumentiert*/
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed bei der man keine Position anlegen kann.
     *
     * @param evt, Event
     */
    private void abgeschlossen_jRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abgeschlossen_jRadioButtonActionPerformed
        // Position anlegen und löschen sind enabled = false
        NeuePosition_jButton.setEnabled(false);
        positionLoeschen_jButton.setEnabled(false);
        // Hintergrundfarbe der Eingabefelder für eine Position werden Weiß
        // gesetzt.
        materialnummer_jTextField.setBackground(hintergrundfarbe);
        menge_jTextField.setBackground(hintergrundfarbe);
    }//GEN-LAST:event_abgeschlossen_jRadioButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden.
     */
    @Override
    public void zuruecksetzen() {

        // Klassenvariablen
        //Variablen für den Auftragswert werden alle auf 0 gesetzt.
        gesamtAuftragswert = 0.00;
        summenWertFuerPos = 0.00;
        einzelwert = 0.00;

        try {// Try-Block
            // Dateobjekt
            heute = new Date();
            heute = format.parse(format.format(heute));

            //Eingabefelder erhalten einen leeren String
            geschaeftspartner_jTextField.setText("");
            auftragswert_jTextField.setText("");
            positionsnummer_jTextField.setText("");
            auftragskopfID_jTextField.setText("");

            auftragsart_jComboBox.setSelectedIndex(0);

            materialnummer_jTextField.setText("");
            menge_jTextField.setText("");
            einzelwert_jTextField.setText("");
            auftragstext_jTextArea.setText("");

            //Eingabefelder für das Erfassungsdatum erhalten das heutige Datum
            erfassungsdatum_jFormattedTextField.setText(format.format(heute));
            lieferdatum_jFormattedTextField.setText(format.format(heute));
            abschlussdatum_jFormattedTextField.setText("");

            Skontozeit1_jTextField.setText("");
            Skontozeit2_jTextField.setText("");

            Mahnzeit1_jTextField.setText("");
            Mahnzeit2_jTextField.setText("");
            Mahnzeit3_jTextField.setText("");

            // Listen und Maps werden geleert.
            artikel.clear();
            auftragspositionen.clear();
            dbAuftragspositionen.clear();

            // Setzen der Hintergrundfarbe auf Weiß.
            geschaeftspartner_jTextField.setBackground(hintergrundfarbe);
            auftragstext_jTextArea.setBackground(hintergrundfarbe);
            materialnummer_jTextField.setBackground(hintergrundfarbe);
            menge_jTextField.setBackground(hintergrundfarbe);
            // Boolische Variablen erhaten Standardwert
            gespeichert = false;
            formularOK = true;
            // Positionstabelle wird geleert.
            dtm.setRowCount(0);
            //Tabelle erhält neue DefaultTableModel.
            auftragsposition_jTable.setModel(dtm);
            // Eingabefeld erhält mit DAO-Methode die nächste Auftrags-ID.
            auftragskopfID_jTextField.setText(String.valueOf(
                    GUIFactory.getDAO().gibNaechsteAuftragsID()));
        } catch (ParseException e) {// Fehlerbehandlung.
            // Meldung an Statuszeile übergeben.
            this.hauptFenster.setzeStatusMeldung(e.getMessage());
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015, TER, getestet,freigegeben und dokumentiert */
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
//        Eingabefelder für Auftragsposition werden in Variable 
//         "fehlendeEingabenAuftragsposition" festgehalten.
        if (materialnummer_jTextField.getText().equals("")) {
            fehlendeEingabenAuftragsposition.add(materialnummer_jTextField);
        }
        if (menge_jTextField.getText().equals("")) {
            fehlendeEingabenAuftragsposition.add(menge_jTextField);
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
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
        //Falls Eingabe nicht mit der Syntax übereinstimmt.
        if (!textfield.getText().matches(syntax)) {
            formularOK = false;
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
            //Mit dem Focus in das übergebene Eingabefeld springen
            textfield.requestFocusInWindow();
            textfield.selectAll();
        } else if (!textfield.getText().equals("")) {
            formularOK = true;

            textfield.setBackground(hintergrundfarbe);

        } else {
            formularOK = true;
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der die Eingabefelder die nicht ausgefüllt
     * worden sind, farblich markiert werden und eine Meldung ausgegeben wird,
     * inder der Benutzer darauf hingewiesen wird alle Eingaben zu tätigen.
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
        if (!list.isEmpty()) {
            // Fokus gelangt in das erste leere Eingabefeld
            list.get(0).requestFocusInWindow();
        }
        // Alle leeren Eingabefelder werden farblich markiert.
        for (int i = 0; i <= list.size() - 1; i++) {
            list.get(i).setBackground(farbe);
        }
        //ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
        list.clear();
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 08.01.2015 Terrasi Anwendungslogik überarbeitet*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Internalframe nicht mehr als Anzeigefenster
     * dargestellt wird, sondern als Fenster in dem man Daten ändern kann.
     */
    public void setzeStatusAender() {
        // Titel wird gesetzt.
        this.setTitle("Auftragskopf ändern");
        zuruecksetzen();// Eingabefelder werden zurück gesetzt.

        // Komponenten werden auf Enabled gesetzt oder nicht.
        this.geschaeftspartner_jTextField.setEnabled(true);
        this.auftragskopfID_jTextField.setEnabled(false);
        this.auftragswert_jTextField.setEnabled(false);
        this.auftragsart_jComboBox.setEnabled(false);

        // Wird geprüft welche Auftragsart aufgerufen worden ist. 
        // Je nachdem werden bestimmte Felder auf Enabled gesetzt oder nicht.
        // Falls Barauftrag
        if (this.auftragsart_jComboBox.getSelectedIndex() == 0) {
            this.lieferdatum_jFormattedTextField.setEnabled(false);
            this.zahlungskonditionen_jComboBox.setEnabled(false);
        } else if (this.auftragsart_jComboBox.getSelectedIndex() == 1) {// Sofortauftrag
            this.lieferdatum_jFormattedTextField.setEnabled(false);
            this.zahlungskonditionen_jComboBox.setEnabled(true);
        } else {// Falls Bestellauftrag oder Terminauftrag.
            this.zahlungskonditionen_jComboBox.setEnabled(true);
        }
        // Komponenten werden auf Enabled gesetzt oder nicht.
        this.auftragstext_jTextArea.setEnabled(true);
        this.erfassungsdatum_jFormattedTextField.setEnabled(false);
        this.erfasst_jRadioButton.setEnabled(true);
        this.freigegeben_jRadioButton.setEnabled(true);
        this.abgeschlossen_jRadioButton.setEnabled(true);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(true);
        this.menge_jTextField.setEnabled(true);
        this.erfassungsdatum_auftragsposition_jFormattedTextField.
                setEnabled(false);
        this.auftragsposition_jTable.setEnabled(true);
        jB_Anzeigen.setEnabled(false);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(true);
        NeuePosition_jButton.setEnabled(true);
        positionLoeschen_jButton.setEnabled(true);
        //Übergabe des Fenster an das Hauptfenster.
        this.hauptFenster.setzeComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 08.01.2015 Terrasi Anwendungslogik überarbeitet*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Internalframe nicht mehr als Anzeigefenster
     * dargestellt wird, sondern als Fenster, indem man Daten ändern kann.
     */
    public void setzeStatusAenderButton() {
        this.setTitle("Auftragskopf ändern");
        // Komponenten werden auf Enabled gesetzt oder nicht.
        this.geschaeftspartner_jTextField.setEnabled(true);
        this.auftragskopfID_jTextField.setEnabled(false);
        this.auftragswert_jTextField.setEnabled(false);
        this.auftragsart_jComboBox.setEnabled(false);

        // Wird geprüft welche Auftragsart aufgerufen worden ist. 
        // Je nachdem werden bestimmte Felder auf Enabled gesetzt oder nicht.
        // Falls Barauftrag
        if (this.auftragsart_jComboBox.getSelectedIndex() == 0) {
            this.lieferdatum_jFormattedTextField.setEnabled(false);
            this.zahlungskonditionen_jComboBox.setEnabled(false);
        } else if (this.auftragsart_jComboBox.getSelectedIndex() == 1) {// Sofortauftrag
            this.lieferdatum_jFormattedTextField.setEnabled(false);
            this.zahlungskonditionen_jComboBox.setEnabled(true);
        } else {// Falls Bestellauftrag oder Terminauftrag.
            this.zahlungskonditionen_jComboBox.setEnabled(true);
        }
        // Komponenten werden auf Enabled gesetzt oder nicht.
        this.auftragstext_jTextArea.setEnabled(true);
        this.erfassungsdatum_jFormattedTextField.setEnabled(false);
        this.erfasst_jRadioButton.setEnabled(true);
        this.freigegeben_jRadioButton.setEnabled(true);
        this.abgeschlossen_jRadioButton.setEnabled(true);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(true);
        this.menge_jTextField.setEnabled(true);
        this.erfassungsdatum_auftragsposition_jFormattedTextField.
                setEnabled(false);
        this.auftragsposition_jTable.setEnabled(true);
        jB_Anzeigen.setEnabled(false);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(true);
        NeuePosition_jButton.setEnabled(true);
        positionLoeschen_jButton.setEnabled(true);
        //Übergabe des Fenster an das Hauptfenster.
        this.hauptFenster.setzeComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 08.01.2015 Terrasi Anwendungslogik überarbeitet*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Internalframe wieder als Anzeigefenster dargestellt
     * wird. Dadurch sind keine Eingaben möglich.
     */
    public void setzeStatusAnzeigen() {
        this.setTitle("Auftragskopf anzeigen");
        // Komponenten werden auf Enabled gesetzt oder nicht.
        this.geschaeftspartner_jTextField.setEnabled(false);
        this.auftragskopfID_jTextField.setEnabled(false);
        this.auftragswert_jTextField.setEnabled(false);
        this.auftragsart_jComboBox.setEnabled(false);
        this.zahlungskonditionen_jComboBox.setEnabled(false);
        this.auftragstext_jTextArea.setEnabled(false);
        this.erfassungsdatum_jFormattedTextField.setEnabled(false);
        this.lieferdatum_jFormattedTextField.setEnabled(false);
        this.erfasst_jRadioButton.setEnabled(false);
        this.freigegeben_jRadioButton.setEnabled(false);
        this.abgeschlossen_jRadioButton.setEnabled(false);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(false);
        this.menge_jTextField.setEnabled(false);
        this.erfassungsdatum_auftragsposition_jFormattedTextField.
                setEnabled(false);
        this.auftragsposition_jTable.setEnabled(false);
        jB_Anzeigen.setEnabled(true);
        jB_Speichern.setEnabled(false);
        jB_Loeschen.setEnabled(false);
        NeuePosition_jButton.setEnabled(false);
        positionLoeschen_jButton.setEnabled(false);
        //Übergabe des Fenster an das Hauptfenster.
        this.hauptFenster.setzeComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Internalframe als Anlegenfenster dargestellt wird um
     * einen Auftragskopf alegen zu können.
     */
    public void setzeStatusAnlegen() {
        this.setTitle("Auftragskopf anlegen");
        zuruecksetzen();// Zurücksetzen des Formulars.
        // Komponenten werden auf Enabled gesetzt oder nicht.
        this.geschaeftspartner_jTextField.setEnabled(true);
        this.auftragskopfID_jTextField.setEnabled(false);

        this.auftragswert_jTextField.setEnabled(false);
        this.auftragswert_jTextField.setText("");

        this.auftragsart_jComboBox.setEnabled(true);
        this.zahlungskonditionen_jComboBox.setEnabled(false);
        this.auftragstext_jTextArea.setEnabled(true);
        this.erfassungsdatum_jFormattedTextField.setEnabled(false);
        this.lieferdatum_jFormattedTextField.setEnabled(false);
        this.erfasst_jRadioButton.setEnabled(true);
        this.erfasst_jRadioButton.setSelected(true);
        this.freigegeben_jRadioButton.setEnabled(false);
        this.abgeschlossen_jRadioButton.setEnabled(false);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(true);
        this.menge_jTextField.setEnabled(true);
        this.erfassungsdatum_auftragsposition_jFormattedTextField.
                setEnabled(false);
        this.auftragsposition_jTable.setEnabled(true);
        jB_Anzeigen.setEnabled(false);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(false);
        NeuePosition_jButton.setEnabled(true);
        positionLoeschen_jButton.setEnabled(true);
        //Übergabe des Fenster an das Hauptfenster.
        this.hauptFenster.setzeComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine HashMAp von Artikel übergibt und speichert.
     *
     * @param artikel, HashMap die Artikel-ID und deren Menge enhält.
     */
    public void setArtikel(LinkedHashMap<Long, Integer> artikel) {
        this.artikel = artikel;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 TER angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine Artikel-ID übergibt und den FocusLost des
     * Eingabefeldes aufruft, um zu überprüfen ob die ID auch gültig ist.
     *
     * @param id , String der die Artikel-ID rpräsentiert
     */
    public void setArtikelid_jTextField(String id) {
        this.materialnummer_jTextField.setText(id);// Übergabe der ID
        materialnummer_jTextFieldFocusLost(null);// FocusLost
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man eine Auftragsart in der Combobox auswählt.
     *
     * @param auftragsArt, String der eine Auftragsart repräsentiert.
     */
    public void setAuftragsart_jComboBox(String auftragsArt) {
        auftragsart_jComboBox.setSelectedItem(auftragsArt);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Auftragskopf-ID zu setzen.
     *
     * @param id, String repräsentiert eine Auftragskopf-ID
     */
    public void setAuftragskopfID_jTextField(String id) {
        this.auftragskopfID_jTextField.setText(id);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Auftragstext zu setzen.
     *
     * @param auftragstext , String repräsentiert einen Auftragstext
     */
    public void setAuftragstext_jTextArea(String auftragstext) {
        this.auftragstext_jTextArea.setText(auftragstext);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Auftragswert zu setzen.
     *
     * @param wert, String repräsentiert eine Auftragswert
     */
    public void setAuftragswert_jTextField(String wert) {
        this.auftragswert_jTextField.setText(wert);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Geschäftspartner-ID zu setzen.
     *
     * @param id, String repräsentiert eine Geschäftspartner-ID
     */
    public void setGeschaeftspartnerID(String id) {
        this.geschaeftspartner_jTextField.setText(id);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Erfassungsdatum zu setzen.
     *
     * @param erfassungsdatumAuftragsposition , String repräsentiert ein
     * Erfassungsdatum.
     */
    public void setErfassungsdatum_auftragsposition_jFormattedTextField(
            String erfassungsdatumAuftragsposition) {
        this.erfassungsdatum_auftragsposition_jFormattedTextField.
                setText(erfassungsdatumAuftragsposition);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Erfassungsdatum zu setzen.
     *
     * @param erfassungsdatum , String repräsentiert ein Erfassungsdatum.
     */
    public void setErfassungsdatum_jFormattedTextField(String erfassungsdatum) {
        this.erfassungsdatum_jFormattedTextField.setText(erfassungsdatum);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Lieferdatum zu setzen.
     *
     * @param lieferdatum , String repräsentiert ein Lieferdatum.
     */
    public void setLieferdatum_jFormattedTextField(String lieferdatum) {
        this.lieferdatum_jFormattedTextField.setText(lieferdatum);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Materialnummer zu setzen.
     *
     * @param materialnummer , String repräsentiert eine Materialnummer.
     */
    public void setMaterialnummer_jTextField(String materialnummer) {
        this.materialnummer_jTextField.setText(materialnummer);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Menge zu setzen.
     *
     * @param menge , String repräsentiert eine Menge.
     */
    public void setMenge_jTextField(String menge) {
        this.menge_jTextField.setText(menge);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Positionsnummer zu setzen.
     *
     * @param positionsnummer, String repräsentiert eine Menge.
     */
    public void setPositionsnummer_jTextField(String positionsnummer) {
        this.positionsnummer_jTextField.setText(positionsnummer);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode um Auftragskopf zu setzen.
     *
     * @param auftragskopf , String repräsentiert eine Menge.
     */
    public void setzeEingabe(Auftragskopf auftragskopf) {
        try {// Try-Block
            // Positionen des Auftrags aufrufen.
            auftragspositionen = auftragskopf.getPositionsliste();
            // Falls Auftragsstatus erfasst ist.
            if (auftragskopf.getStatus().getStatus().equals("erfasst")) {
                erfasst_jRadioButton.setSelected(true);
                freigegeben_jRadioButton.setEnabled(true);
                abgeschlossen_jRadioButton.setEnabled(false);

                geschaeftspartner_jTextField.setEnabled(true);
                auftragstext_jTextArea.setEnabled(true);
                lieferdatum_jFormattedTextField.setEnabled(true);
                materialnummer_jTextField.setEnabled(true);
                menge_jTextField.setEnabled(true);

                auftragsposition_jTable.setEnabled(true);
                NeuePosition_jButton.setEnabled(true);
                positionLoeschen_jButton.setEnabled(true);

            } else if (auftragskopf.getStatus().getStatus().equals("freigegeben")) {
                freigegeben_jRadioButton.setSelected(true);

                erfasst_jRadioButton.setEnabled(true);
                abgeschlossen_jRadioButton.setSelected(false);

                geschaeftspartner_jTextField.setEnabled(true);
                auftragstext_jTextArea.setEnabled(true);
                lieferdatum_jFormattedTextField.setEnabled(true);
                materialnummer_jTextField.setEnabled(true);
                menge_jTextField.setEnabled(true);

                auftragsposition_jTable.setEnabled(true);
                NeuePosition_jButton.setEnabled(false);
                positionLoeschen_jButton.setEnabled(false);
            } else {// Falls Status abgeschlossen ist.
                abgeschlossen_jRadioButton.setSelected(true);
                erfasst_jRadioButton.setEnabled(false);
                freigegeben_jRadioButton.setEnabled(false);

                geschaeftspartner_jTextField.setEnabled(false);
                auftragstext_jTextArea.setEnabled(false);
                lieferdatum_jFormattedTextField.setEnabled(false);
                materialnummer_jTextField.setEnabled(false);
                menge_jTextField.setEnabled(false);

                auftragsposition_jTable.setEnabled(false);
                NeuePosition_jButton.setEnabled(false);
                positionLoeschen_jButton.setEnabled(false);
            }

            // Übergabe der der Auftragsdaten in die passenden Eingabefelder.
            this.geschaeftspartner_jTextField.setText(
                    String.valueOf(auftragskopf.getGeschaeftspartner().
                            getGeschaeftspartnerID()));
            this.auftragskopfID_jTextField.setText(
                    String.valueOf(auftragskopf.getAuftragskopfID()));
            this.auftragswert_jTextField.setText(
                    String.valueOf(auftragskopf.getWert()));

            if (auftragsart_jComboBox.getItemAt(0).toString().
                    equals(auftragskopf.getTyp())) {
                auftragsart_jComboBox.setSelectedIndex(0);
                lieferdatum_jFormattedTextField.setEnabled(false);
            }

            //Hasmap wird geleert
            artikel.clear();
            // DefaultTable Model wird geleert.
            dtm.setRowCount(0);
            // Über Positionen interieren
            for (int i = 0; i < auftragspositionen.size(); i++) {
                // Artikel und Menge in HashMap übergeben.
                artikel.put(auftragspositionen.get(i).getArtikel().
                        getArtikelID(),
                        auftragspositionen.get(i).getMenge());
                // Positionswert berechnen.
                summenWertFuerPos = auftragspositionen.get(i).getEinzelwert();

                Object[] neuesObj = new Object[]{i + 1,
                    auftragspositionen.get(i).getArtikel().getArtikelID(),
                    auftragspositionen.get(i).getMenge(), summenWertFuerPos,
                    gibDatumAlsString(heute)};

                gesamtAuftragswert += summenWertFuerPos;

                dtm.addRow(neuesObj);

            }

            if (auftragsart_jComboBox.getItemAt(1).toString().equals(
                    auftragskopf.getTyp())) {// Falls Auftragsart Sofortauftrag gewählt ist.
                auftragsart_jComboBox.setSelectedIndex(1);
                // Passende Zahlungskonditionen interieren.
                for (int i = 0; i < zahlungskonditionen_jComboBox.getItemCount(); i++) {
                    if (zahlungskonditionen_jComboBox.getItemAt(i).toString().
                            equals(String.valueOf(auftragskopf.
                                            getZahlungskondition().
                                            getZahlungskonditionID()))) {
                        zahlungskonditionen_jComboBox.setSelectedIndex(i);
                    }
                }
                lieferdatum_jFormattedTextField.setEnabled(false);
            }
            if (auftragsart_jComboBox.getItemAt(2).toString().
                    equals(auftragskopf.getTyp())) {// Falls Auftragsart Terminauftrag gewählt ist.
                auftragsart_jComboBox.setSelectedIndex(2);
                // Passende Zahlungskonditionen interieren.
                for (int i = 0; i < zahlungskonditionen_jComboBox.getItemCount(); i++) {
                    if (zahlungskonditionen_jComboBox.getItemAt(i).toString().
                            equals(String.valueOf(auftragskopf.
                                            getZahlungskondition().
                                            getZahlungskonditionID()))) {
                        zahlungskonditionen_jComboBox.setSelectedIndex(i);
                    }
                }
                lieferdatum_jFormattedTextField.setEnabled(true);
            }
            if (auftragsart_jComboBox.getItemAt(3).toString().
                    equals(auftragskopf.getTyp())) {// Falls Auftragsart Bestellauftrag gewählt ist.
                auftragsart_jComboBox.setSelectedIndex(3);
                // Passende Zahlungskonditionen interieren.
                for (int i = 0; i < zahlungskonditionen_jComboBox.getItemCount(); i++) {
                    if (zahlungskonditionen_jComboBox.getItemAt(i).toString().
                            equals(String.valueOf(
                                            auftragskopf.
                                            getZahlungskondition().
                                            getZahlungskonditionID()))) {
                        zahlungskonditionen_jComboBox.setSelectedIndex(i);
                    }
                }

                lieferdatum_jFormattedTextField.setEnabled(true);
            }
            // Übergabe der der Auftragsdaten in die passenden Eingabefelder.
            this.auftragstext_jTextArea.setText(auftragskopf.getAuftragstext());

            this.erfassungsdatum_jFormattedTextField.
                    setText(String.valueOf(gibDatumAlsString(
                                            auftragskopf.getErfassungsdatum())));
            heute = format.parse(gibDatumAlsString(
                    auftragskopf.getErfassungsdatum()));
            this.lieferdatum_jFormattedTextField.
                    setText(String.valueOf(gibDatumAlsString(
                                            auftragskopf.getLieferdatum())));
            // Prfung ob eine Abschlussdatum vorhanden ist.
            if (auftragskopf.getAbschlussdatum() == null) {// Kein Abschlussdatum.
                this.abschlussdatum_jFormattedTextField.
                        setText("");
            } else {
                this.abschlussdatum_jFormattedTextField.
                        setText(String.valueOf(gibDatumAlsString(
                                                auftragskopf.getAbschlussdatum())));

            }

            // Übergabe des DefaultTableModel
            auftragsposition_jTable.setModel(dtm);
            // Übergabe des Auftragswertes in das passende Eingabefeld
            auftragswert_jTextField.setText(String.valueOf(gesamtAuftragswert));
            // Variablen erhalten den Wert 0.
            gesamtAuftragswert = 0.00;
            summenWertFuerPos = 0.00;

            // Erzeugung eines Objektes mit den Werten die in der DB
            // hinterlegt sind
            kopf = GUIFactory.getDAO().
                    gibAuftragskopf(Long.parseLong(
                                    auftragskopfID_jTextField.getText()));

            // Werte aus DB in Variablen speichern.
            dbAuftragspositionen = GUIFactory.getDAO().
                    gibAuftragspositionen(kopf.getAuftragskopfID());
            dbGeschaeftspartnerID
                    = String.valueOf(
                            kopf.getGeschaeftspartner().
                            getGeschaeftspartnerID());

            dbAuftragsart = kopf.getTyp();

            dbAuftragstext = kopf.getAuftragstext();

            // Falls keine Zahlungskonditionen vorhanden sind.
            if (kopf.getZahlungskondition() == null) {
                dbZahlungskondition = "0";
            } else {// Falls vorhanden.
                dbZahlungskondition
                        = String.valueOf(kopf.getZahlungskondition().
                                getZahlungskonditionID());
            }
            // Werte aus DB in Variablen speichern.
            dbStatus = kopf.getStatus().getStatus();
            dbLieferdatum = gibDatumAlsString(kopf.getLieferdatum());

            // Prfung ob eine Abschlussdatum vorhanden ist.
            if (kopf.getAbschlussdatum() == null) {// Kein Abschlussdatum.
                dbAbschlussdatum = "";
            } else {
                dbAbschlussdatum = gibDatumAlsString(kopf.getAbschlussdatum());

            }
            if (this.getTitle().equals("Auftragskopf anzeigen")) {
                setzeStatusAnzeigen();
            }

        } catch (ApplicationException | ParseException e) {// Fehlerbehandlung.

            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben  */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der ein Auftrag der anhand der Suche übergeben wird,
     * ausgelesen wird und die Daten in den passenden Eingabefeldern anzeigt.
     *
     * @param auftragskopf, Auftragsobjekt
     */
    public void setzeEingabeFuerSuche(Auftragskopf auftragskopf) {
        try {// Try-Block.
            // Überprüfung welche Auftragsart gewählt worden ist.
            if (auftragsart_jComboBox.getItemAt(0).toString().
                    equals(auftragskopf.getTyp())) {
                auftragsart_jComboBox.setSelectedIndex(0);
                zahlungskonditionen_jComboBox.addItem("Bitte wählen");
            }
            if (auftragsart_jComboBox.getItemAt(1).toString().
                    equals(auftragskopf.getTyp())) {
                auftragsart_jComboBox.setSelectedIndex(1);
            }
            if (auftragsart_jComboBox.getItemAt(2).toString().
                    equals(auftragskopf.getTyp())) {
                auftragsart_jComboBox.setSelectedIndex(2);
            }
            if (auftragsart_jComboBox.getItemAt(3).toString().
                    equals(auftragskopf.getTyp())) {
                auftragsart_jComboBox.setSelectedIndex(3);
            }
            // Initialisierung der Positionen des Auftrags 
            auftragspositionen = auftragskopf.getPositionsliste();

            // Übergabe der Auftragsdaten an die passenden Eingabefelder.
            this.auftragstext_jTextArea.setText(auftragskopf.getAuftragstext());
            this.geschaeftspartner_jTextField.setText(
                    String.valueOf(auftragskopf.getGeschaeftspartner().
                            getGeschaeftspartnerID()));
            this.auftragskopfID_jTextField.setText(
                    String.valueOf(auftragskopf.getAuftragskopfID()));
            this.auftragswert_jTextField.setText(
                    String.valueOf(auftragskopf.getWert()));
            this.erfassungsdatum_jFormattedTextField.
                    setText(
                            String.valueOf(gibDatumAlsString(
                                            auftragskopf.getErfassungsdatum())));
            this.lieferdatum_jFormattedTextField.
                    setText(String.valueOf(gibDatumAlsString(
                                            auftragskopf.getLieferdatum())));

            if (auftragskopf.getAbschlussdatum() == null) {// Falls kein Abschlussdatum vorhanden ist.
                this.abschlussdatum_jFormattedTextField.
                        setText("");
            } else {// Datum vorhanden.

                this.abschlussdatum_jFormattedTextField.
                        setText(String.valueOf(gibDatumAlsString(
                                                auftragskopf.getAbschlussdatum())));

            }

            // Artikelhashmap wird geleert.
            artikel.clear();

            dtm.setRowCount(0);// DefaultTableModel leeren.
            // Schleife um über Positionen zu interieren.
            for (int i = 0; i < auftragspositionen.size(); i++) {

                artikel.put(auftragspositionen.get(i).getArtikel().
                        getArtikelID(),
                        auftragspositionen.get(i).getMenge());

                summenWertFuerPos = auftragspositionen.get(i).getEinzelwert();

                Object[] neuesObj = new Object[]{i + 1,
                    auftragspositionen.get(i).getArtikel().getArtikelID(),
                    auftragspositionen.get(i).getMenge(), summenWertFuerPos,
                    gibDatumAlsString(abschlussdatum)};

                gesamtAuftragswert += summenWertFuerPos;

                dtm.addRow(neuesObj);

            }

            auftragsposition_jTable.setModel(dtm);// Übergabe eines DefaultTableModels
            // bergabe des Auftragswertes
            auftragswert_jTextField.setText(String.valueOf(gesamtAuftragswert));

            gesamtAuftragswert = 0.00;
            summenWertFuerPos = 0.00;

            // Erzeugung eines Objektes mit den Werten die in der DB
            // hinterlegt sind
            kopf = GUIFactory.getDAO().
                    gibAuftragskopf(Long.parseLong(
                                    auftragskopfID_jTextField.getText()));

            // Übergabe der Db-Daten an die Speichervariablen.
            dbAuftragspositionen = GUIFactory.getDAO().
                    gibAuftragspositionen(kopf.getAuftragskopfID());
            dbGeschaeftspartnerID
                    = String.valueOf(
                            kopf.getGeschaeftspartner().
                            getGeschaeftspartnerID());

            dbAuftragsart = kopf.getTyp();

            dbAuftragstext = kopf.getAuftragstext();

            if (kopf.getZahlungskondition() == null) {// Falls keine Zahlungskonditionen vorhanden sind.
                dbZahlungskondition = "0";
            } else {

                dbZahlungskondition
                        = String.valueOf(kopf.getZahlungskondition().
                                getZahlungskonditionID());
            }

            // Übergabe des Status.
            dbStatus = kopf.getStatus().getStatus();

            // Überprüfung welcher Status der Auftrag hat.
            if (kopf.getStatus().getStatus().equals("erfasst")) {
                erfasst_jRadioButton.setSelected(true);
            } else if (auftragskopf.getStatus().getStatus().equals("freigegeben")) {
                freigegeben_jRadioButton.setSelected(true);
            } else {
                abgeschlossen_jRadioButton.setSelected(true);
            }
            // Übergabe des Lieferdatums aus der DB.
            dbLieferdatum = gibDatumAlsString(kopf.getLieferdatum());

            if (kopf.getAbschlussdatum() == null) {// Falls kein Abschlussdatum vrhanden ist.
                dbAbschlussdatum = "";
            } else {
                dbAbschlussdatum = gibDatumAlsString(kopf.getAbschlussdatum());
            }
            // Prüfung welcher in welchem Status das Fenster angezeigt werden soll.
            if (this.getTitle().equals("Auftragskopf anzeigen")) {
                setzeStatusAnzeigen();
            } else if (this.getTitle().equals("Auftragskopf ändern")) {
                setzeStatusAenderButton();
            } else {
                setzeStatusAnlegen();
            }

        } catch (ApplicationException e) {// Fehlerbehandlung.
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    FEHLERMELDUNG_TITEL, JOptionPane.ERROR_MESSAGE);
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode die ein Dateobjekt in ein passenden String umwandelt.
     *
     * @param date, Datumsobjekt das als String umgewandelt wird.
     * @return String Objekt, Datum als String
     */
    private String gibDatumAlsString(Date date) {
        // Speichervariablen für den Tag und den Monat des Datums
        String tagAlsString;
        String monatAlsString;

        // Erzeugung eines Calendarobjektes
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Tag des Datums wird gespeichert.
        int tag = cal.get(Calendar.DAY_OF_MONTH);
        // Monat des Datums wird gespeichert
        int mon = cal.get(Calendar.MONTH);
        mon = mon + 1;
        // Jahr des Datums wird gespeichert
        int jahr = cal.get(Calendar.YEAR);

        // Falls Tag einstellig ist, wird es mit einer "0" ergänzt und 
        // zweistellig gespeichert.
        if (tag < 10) {
            tagAlsString = "0" + tag;
        } else {
            tagAlsString = "" + tag;
        }
        // Falls Monat einstellig ist, wird es mit einer "0" ergänzt und 
        // zweistellig gespeichert.
        if (mon < 10) {
            monatAlsString = "0" + mon;
        } else {
            monatAlsString = "" + mon;
        }

        //Zusammenfhrung der einzelnen Speichervariablen.
        String ausgabeDatum = tagAlsString + "." + monatAlsString + "." + jahr;
        return ausgabeDatum;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Mahnzeit1_jTextField;
    private javax.swing.JTextField Mahnzeit2_jTextField;
    private javax.swing.JTextField Mahnzeit3_jTextField;
    private javax.swing.JButton NeuePosition_jButton;
    private javax.swing.JTextField Skontozeit1_jTextField;
    private javax.swing.JTextField Skontozeit2_jTextField;
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
    private javax.swing.JLabel jLMahnzeit1;
    private javax.swing.JLabel jLMahnzeit2;
    private javax.swing.JLabel jLMahnzeit3;
    private javax.swing.JLabel jLSkontozeit1;
    private javax.swing.JLabel jLSkontozeit2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JFormattedTextField lieferdatum_jFormattedTextField;
    private javax.swing.JLabel lieferdatum_jLabel;
    private javax.swing.JLabel materialnummer_jLabel;
    private javax.swing.JTextField materialnummer_jTextField;
    private javax.swing.JLabel menge_jLabel;
    private javax.swing.JTextField menge_jTextField;
    private javax.swing.JButton positionLoeschen_jButton;
    private javax.swing.JLabel positionsnummer_jLabel;
    private javax.swing.JTextField positionsnummer_jTextField;
    private javax.swing.JLabel status_jLabel;
    private javax.swing.JComboBox zahlungskonditionen_jComboBox;
    private javax.swing.JLabel zahlungskonditionen_jLabel;
    // End of variables declaration//GEN-END:variables
}
