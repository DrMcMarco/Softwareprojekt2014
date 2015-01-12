package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.*;
import DAO.DataAccessObject;
import Documents.UniversalDocument;
import Interfaces.InterfaceMainView;
import JFrames.GUIFactory;
import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Tahir
 *
 * 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button 08.01.2015
 * Terrasi, Implementierung der Anzeigen/Ändern Funktion, hinzufügen der
 * Schnittstelle für InternalFrames
 */
public class GeschaeftspartnerAnlegen extends javax.swing.JInternalFrame {

    Component c;
    GUIFactory factory;
    DataAccessObject dao;
    InterfaceMainView hauptFenster;

    Anschrift anschrift;
    Anschrift lieferanschrift;

    private String aktuellesDatum;

//  ArrayList, um fehlerhafte Componenten zu speichern.    
    private ArrayList<Component> fehlerhafteComponenten;
//  ArrayList, um angelegte Artikel zu speichern     
//    public ArrayList<Zahlungskondition> zkListe;
//  Insantzvariablen für die standard Farben der Componenten    
    private final Color JCB_FARBE_STANDARD = new Color(214, 217, 223);
    private final Color JTF_FARBE_STANDARD = new Color(255, 255, 255);
//  Insantzvariablen für die Farben von fehlerhaften Componenten         
//    private final Color FARBE_FEHLERHAFT = new Color(255, 239, 219);
//    private final Color FARBE_FEHLERHAFT = new Color(255, 165, 79);
    private final Color FARBE_FEHLERHAFT = Color.YELLOW;
//  Insantzvariablen für die Meldungen         
    private final String MELDUNG_PFLICHTFELDER_TITEL = "Felder nicht ausgefüllt";
    private final String MELDUNG_PFLICHTFELDER_TEXT = "Einige Felder wurden nicht ausgefüllt! Bitte füllen Sie diese aus!";

    private final String PRUEFUNG_PREIS = "|(\\d*,?\\d{1,2})|(\\d{0,3}(\\.\\d{3})*,?\\d{1,2})";
    private final String PRUEFUNG_HAUSNUMMER = "|[1-9][0-9]{0,2}[a-zA-Z]?";
    private final String PRUEFUNG_PLZ = "|[0-9]{5}";
    private final String PRUEFUNG_EMAIL = "|^[a-zA-Z0-9][\\w\\.-]*@(?:[a-zA-Z0-9][a-zA-Z0-9_-]+\\.)+[A-Z,a-z]{2,5}$";
    private final String PRUEFUNG_TELEFON = "|^([+][ ]?[1-9][0-9][ ]?[-]?[ ]?|[(]?[0][ ]?)[0-9]{3,4}[-)/ ]?[ ]?[1-9][-0-9 ]{3,16}$";

    private int geschaeftspartnerNr = 1;
    private final SimpleDateFormat FORMAT;

    private boolean sichtAnlegenAEndern;
    private NumberFormat nf;
    Calendar cal = Calendar.getInstance();

    Date eingabeDatum;

    Date tempGebuDate;

    private int anzahlFehlerhafterComponenten = 16;

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     */
    public GeschaeftspartnerAnlegen(GUIFactory factory, InterfaceMainView mainView) {
        initComponents();
        fehlerhafteComponenten = new ArrayList<>();
        this.factory = factory;
        this.dao = factory.getDAO();
        this.hauptFenster = mainView;
        FORMAT = new SimpleDateFormat("dd.MM.yyyy");
        FORMAT.setLenient(false);
        anschrift = null;
        lieferanschrift = null;
        nf = NumberFormat.getInstance();

        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        jTF_Name.setDocument(new UniversalDocument("-.´", true));
        jTF_Vorname.setDocument(new UniversalDocument("-.´", true));
        jTF_Telefon.setDocument(new UniversalDocument("0123456789/-", false));
        jTF_Fax.setDocument(new UniversalDocument("0123456789/", false));
        jTF_EMail.setDocument(new UniversalDocument("0123456789@-.", true));
        jTF_Kreditlimit.setDocument(new UniversalDocument("0123456789.,", false));
        jTF_StrasseRechnungsanschrift.setDocument(new UniversalDocument("-.´", true));
        jTF_StrasseLieferanschrift.setDocument(new UniversalDocument("-.´", true));
        jTF_HausnummerRechnungsanschrift.setDocument(new UniversalDocument("0123456789", true));
        jTF_HausnummerLieferanschrift.setDocument(new UniversalDocument("0123456789", true));
        jTF_PLZRechnungsanschrift.setDocument(new UniversalDocument("0123456789", false));
        jTF_PLZLieferanschrift.setDocument(new UniversalDocument("0123456789", false));
        jTF_OrtRechnungsanschrift.setDocument(new UniversalDocument("-", true));
        jTF_OrtLieferanschrift.setDocument(new UniversalDocument("-", true));

        this.setzeFormularZurueck();

        GregorianCalendar aktuellesDaum = new GregorianCalendar();
        DateFormat df_aktuellesDatum = DateFormat.getDateInstance(DateFormat.MEDIUM);    //05.12.2014     
        aktuellesDatum = df_aktuellesDatum.format(aktuellesDaum.getTime());

        DateFormat df_jTF = DateFormat.getDateInstance();
        df_jTF.setLenient(false);
        DateFormatter dform1 = new DateFormatter(df_jTF);
//        DateFormatter dform2 = new DateFormatter(df_jTF);
        dform1.setOverwriteMode(true);
//        dform2.setOverwriteMode(true);
        dform1.setAllowsInvalid(false);
//        dform2.setAllowsInvalid(false);
        DefaultFormatterFactory dff1 = new DefaultFormatterFactory(dform1);
//        DefaultFormatterFactory dff2 = new DefaultFormatterFactory(dform2);
        jFTF_Erfassungsdatum.setFormatterFactory(dff1);
        jFTF_Erfassungsdatum.setText(aktuellesDatum);
//        jFTF_Geburtsdatum.setFormatterFactory(dff2);

        MaskFormatter mf = null;
        try {
            mf = new MaskFormatter("##.##.####");
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
        mf.setValueContainsLiteralCharacters(false);
        mf.setPlaceholder("########");
        mf.setPlaceholderCharacter('#');
        mf.setOverwriteMode(true);
        DefaultFormatterFactory dff = new DefaultFormatterFactory(mf);
        jFTF_Geburtsdatum.setFormatterFactory(dff);
        jFTF_Geburtsdatum.setText("##.##.####");
    }

    /*
     * Methode die überprüft, welche Componenten fehlerhaft sind.
     * Fehlerhafte Componenten werden in einer ArrayList gespeichert.
     */
    private void ueberpruefeFormular() {
        if (jCB_Anrede.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Anrede);
        }
        if (jTF_Name.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Name);
        }
        if (jTF_Vorname.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Vorname);
        }
        if (jTF_Telefon.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Telefon);
        }
        if (jTF_Fax.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Fax);
        }
        if (jFTF_Geburtsdatum.getText().equals("##.##.####")) {
            fehlerhafteComponenten.add(jFTF_Geburtsdatum);
        }
        if (jTF_EMail.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_EMail);
        }
        if (jTF_Kreditlimit.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Kreditlimit);
        }
        if (jTF_StrasseRechnungsanschrift.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_StrasseRechnungsanschrift);
        }
        if (jTF_HausnummerRechnungsanschrift.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_HausnummerRechnungsanschrift);
        }
        if (jTF_PLZRechnungsanschrift.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_PLZRechnungsanschrift);
        }
        if (jTF_OrtRechnungsanschrift.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_OrtRechnungsanschrift);
        }
        if (!jCHB_WieAnschrift.isSelected()) {
            if (jTF_StrasseLieferanschrift.getText().equals("")) {
                fehlerhafteComponenten.add(jTF_StrasseLieferanschrift);
            }
            if (jTF_HausnummerLieferanschrift.getText().equals("")) {
                fehlerhafteComponenten.add(jTF_HausnummerLieferanschrift);
            }
            if (jTF_PLZLieferanschrift.getText().equals("")) {
                fehlerhafteComponenten.add(jTF_PLZLieferanschrift);
            }
            if (jTF_OrtLieferanschrift.getText().equals("")) {
                fehlerhafteComponenten.add(jTF_OrtLieferanschrift);
            }
        }
    }

    /*
     * Methode, die die das Formular zurücksetzt.
     */
    public final void setzeFormularZurueck() {
        jTF_GeschaeftspartnerID.setText("" + geschaeftspartnerNr);
        jCHB_Kunde.setSelected(false);
        jCHB_Lieferant.setSelected(false);
        jCB_Anrede.setSelectedIndex(0);
        jCB_Anrede.setBackground(JCB_FARBE_STANDARD);
        jTF_Name.setText("");
        jTF_Name.setBackground(JTF_FARBE_STANDARD);
        jTF_Vorname.setText("");
        jTF_Vorname.setBackground(JTF_FARBE_STANDARD);
        jTF_Telefon.setText("");
        jTF_Telefon.setBackground(JTF_FARBE_STANDARD);
        jTF_Fax.setText("");
        jTF_Fax.setBackground(JTF_FARBE_STANDARD);
        jFTF_Geburtsdatum.setValue(null);
        jFTF_Geburtsdatum.setBackground(JTF_FARBE_STANDARD);
        jTF_EMail.setText("");
        jTF_EMail.setBackground(JTF_FARBE_STANDARD);
        jTF_Kreditlimit.setText("");
        jTF_Kreditlimit.setBackground(JTF_FARBE_STANDARD);
        jTF_StrasseRechnungsanschrift.setText("");
        jTF_StrasseRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_HausnummerRechnungsanschrift.setText("");
        jTF_HausnummerRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_PLZRechnungsanschrift.setText("");
        jTF_PLZRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_OrtRechnungsanschrift.setText("");
        jTF_OrtRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        jCHB_WieAnschrift.setSelected(false);
        jCHB_WieAnschriftActionPerformed(null);
        jTF_StrasseLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_HausnummerLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_PLZLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_OrtLieferanschrift.setBackground(JTF_FARBE_STANDARD);
    }

    private void beendenEingabeNachfrage() {
        ueberpruefeFormular();
        if (sichtAnlegenAEndern) {
            if (fehlerhafteComponenten.size() < anzahlFehlerhafterComponenten) {
                String meldung = "Möchten Sie die Eingaben verwerfen? Klicken Sie auf JA, wenn Sie die Eingaben verwerfen möchten.";
                String titel = "Achtung Eingaben gehen verloren!";
                int antwort = JOptionPane.showConfirmDialog(null, meldung, titel, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (antwort == JOptionPane.YES_OPTION) {
                    fehlerhafteComponenten.clear();
                    this.setVisible(false);
                    setzeFormularZurueck();
                } else {
                    fehlerhafteComponenten.clear();
                }
            }
        } else {
            this.setVisible(false);
            setzeFormularZurueck();
            fehlerhafteComponenten.clear();
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

        jToolBar1 = new javax.swing.JToolBar();
        jB_Zurueck = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_Anzeigen = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jTF_Statuszeile = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTF_GeschaeftspartnerID = new javax.swing.JTextField();
        jCHB_Kunde = new javax.swing.JCheckBox();
        jCHB_Lieferant = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTF_Telefon = new javax.swing.JTextField();
        jCB_Anrede = new javax.swing.JComboBox();
        jTF_Name = new javax.swing.JTextField();
        jTF_Fax = new javax.swing.JTextField();
        jTF_Vorname = new javax.swing.JTextField();
        jFTF_Erfassungsdatum = new javax.swing.JFormattedTextField();
        jFTF_Geburtsdatum = new javax.swing.JFormattedTextField();
        jTF_EMail = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTF_StrasseRechnungsanschrift = new javax.swing.JTextField();
        jTF_HausnummerRechnungsanschrift = new javax.swing.JTextField();
        jTF_OrtRechnungsanschrift = new javax.swing.JTextField();
        jTF_HausnummerLieferanschrift = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTF_OrtLieferanschrift = new javax.swing.JTextField();
        jTF_StrasseLieferanschrift = new javax.swing.JTextField();
        jCHB_WieAnschrift = new javax.swing.JCheckBox();
        jTF_Kreditlimit = new javax.swing.JTextField();
        jTF_PLZRechnungsanschrift = new javax.swing.JTextField();
        jTF_PLZLieferanschrift = new javax.swing.JTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Geschäftspartner anlegen");
        setPreferredSize(new java.awt.Dimension(680, 660));
        setRequestFocusEnabled(false);
        setVisible(false);
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

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        jB_Zurueck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/home.PNG"))); // NOI18N
        jB_Zurueck.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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
        jB_Anzeigen.setActionCommand("Anzeigen/Ändern");
        jB_Anzeigen.setEnabled(false);
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setText("Löschen");
        jB_Loeschen.setEnabled(false);
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setText("Suchen");
        jToolBar1.add(jB_Suchen);

        jTF_Statuszeile.setText("Statuszeile");
        jTF_Statuszeile.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Persöhnliche Daten:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Geschäftspartner-ID:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Typ:");

        jTF_GeschaeftspartnerID.setText("1");
        jTF_GeschaeftspartnerID.setEnabled(false);

        jCHB_Kunde.setText("Kunde");
        jCHB_Kunde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCHB_KundeActionPerformed(evt);
            }
        });

        jCHB_Lieferant.setText("Lieferant");
        jCHB_Lieferant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCHB_LieferantActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Kreditlimit:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("€");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Name:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Vorname:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Anrede:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Telefon:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Fax:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("e-Mail:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Geburtsdatum:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Erfassungsdatum:");

        jTF_Telefon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_TelefonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_TelefonFocusLost(evt);
            }
        });

        jCB_Anrede.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "Herr ", "Frau" }));
        jCB_Anrede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_AnredeActionPerformed(evt);
            }
        });

        jTF_Name.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_NameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_NameFocusLost(evt);
            }
        });

        jTF_Fax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_FaxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_FaxFocusLost(evt);
            }
        });

        jTF_Vorname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_VornameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_VornameFocusLost(evt);
            }
        });

        jFTF_Erfassungsdatum.setEditable(false);
        jFTF_Erfassungsdatum.setEnabled(false);
        jFTF_Erfassungsdatum.setFocusable(false);

        jFTF_Geburtsdatum.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFTF_Geburtsdatum.setToolTipText("Format: TT:MM:JJJJ");
        jFTF_Geburtsdatum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTF_GeburtsdatumFocusLost(evt);
            }
        });

        jTF_EMail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_EMailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_EMailFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Rechnungsanschrift:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Straße:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Hausnr.:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("PLZ:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Ort:");

        jTF_StrasseRechnungsanschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_StrasseRechnungsanschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_StrasseRechnungsanschriftFocusLost(evt);
            }
        });

        jTF_HausnummerRechnungsanschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_HausnummerRechnungsanschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_HausnummerRechnungsanschriftFocusLost(evt);
            }
        });

        jTF_OrtRechnungsanschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_OrtRechnungsanschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_OrtRechnungsanschriftFocusLost(evt);
            }
        });

        jTF_HausnummerLieferanschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_HausnummerLieferanschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_HausnummerLieferanschriftFocusLost(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("Straße:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Ort:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("PLZ:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Hausnr.:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Lieferanschrift:");

        jTF_OrtLieferanschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_OrtLieferanschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_OrtLieferanschriftFocusLost(evt);
            }
        });

        jTF_StrasseLieferanschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_StrasseLieferanschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_StrasseLieferanschriftFocusLost(evt);
            }
        });

        jCHB_WieAnschrift.setText("wie Anschrift");
        jCHB_WieAnschrift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCHB_WieAnschriftActionPerformed(evt);
            }
        });

        jTF_Kreditlimit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_KreditlimitFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_KreditlimitFocusLost(evt);
            }
        });

        jTF_PLZRechnungsanschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_PLZRechnungsanschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_PLZRechnungsanschriftFocusLost(evt);
            }
        });

        jTF_PLZLieferanschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_PLZLieferanschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_PLZLieferanschriftFocusLost(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
            .addComponent(jTF_Statuszeile)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7))
                            .addComponent(jLabel16)
                            .addComponent(jLabel14)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel18))
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTF_Name)
                            .addComponent(jCB_Anrede, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTF_GeschaeftspartnerID)
                            .addComponent(jTF_Telefon)
                            .addComponent(jFTF_Geburtsdatum, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTF_EMail, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTF_OrtRechnungsanschrift, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(jTF_HausnummerRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_StrasseRechnungsanschrift, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(jTF_PLZRechnungsanschrift))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCHB_Kunde))
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22)
                            .addComponent(jLabel19)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel20))))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCHB_Lieferant)
                            .addComponent(jCHB_WieAnschrift)
                            .addComponent(jTF_HausnummerLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_StrasseLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTF_Vorname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jTF_Fax, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFTF_Erfassungsdatum, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTF_Kreditlimit))
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTF_PLZLieferanschrift, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTF_OrtLieferanschrift, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTF_GeschaeftspartnerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCHB_Kunde)
                    .addComponent(jLabel2)
                    .addComponent(jCHB_Lieferant))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jCB_Anrede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTF_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jTF_Vorname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_Telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jTF_Fax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFTF_Geburtsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jFTF_Erfassungsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTF_EMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTF_Kreditlimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel23)
                            .addComponent(jCHB_WieAnschrift))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTF_StrasseRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTF_HausnummerRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTF_PLZRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTF_OrtRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTF_StrasseLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTF_HausnummerLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTF_PLZLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTF_OrtLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(jTF_Statuszeile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /*
     * Methode um die Lieferanschrift gleich die Anschrift zu setzen. 
     */
    private void jCHB_WieAnschriftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCHB_WieAnschriftActionPerformed
        if (jCHB_WieAnschrift.isSelected()) {
            if (!jTF_StrasseRechnungsanschrift.getText().equals("") && !jTF_HausnummerRechnungsanschrift.getText().equals("")
                    && !jTF_PLZRechnungsanschrift.getText().equals("") && !jTF_OrtRechnungsanschrift.getText().equals("")) {
                jTF_StrasseLieferanschrift.setText(jTF_StrasseRechnungsanschrift.getText());
                jTF_StrasseLieferanschrift.setEnabled(false);
                jTF_HausnummerLieferanschrift.setText(jTF_HausnummerRechnungsanschrift.getText());
                jTF_HausnummerLieferanschrift.setEnabled(false);
                jTF_PLZLieferanschrift.setText(jTF_PLZRechnungsanschrift.getText());
                jTF_PLZLieferanschrift.setEnabled(false);
                jTF_OrtLieferanschrift.setText(jTF_OrtRechnungsanschrift.getText());
                jTF_OrtLieferanschrift.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Bitte geben Sie zunächst die komplette Anschrift ein.", "Unvollständige Eingabe", JOptionPane.ERROR_MESSAGE);
                jCHB_WieAnschrift.setSelected(false);
                if (jTF_StrasseRechnungsanschrift.getText().equals("")) {
                    jTF_StrasseRechnungsanschrift.requestFocusInWindow();
                } else if (jTF_HausnummerRechnungsanschrift.getText().equals("")) {
                    jTF_HausnummerRechnungsanschrift.requestFocusInWindow();
                } else if (jTF_PLZRechnungsanschrift.getText().equals("")) {
                    jTF_PLZRechnungsanschrift.requestFocusInWindow();
                } else {
                    jTF_OrtRechnungsanschrift.requestFocusInWindow();
                }
            }
        } else {
            jTF_StrasseLieferanschrift.setText("");
            jTF_StrasseLieferanschrift.setEnabled(true);
            jTF_HausnummerLieferanschrift.setText("");
            jTF_HausnummerLieferanschrift.setEnabled(true);
            jTF_PLZLieferanschrift.setText("");
            jTF_PLZLieferanschrift.setEnabled(true);
            jTF_OrtLieferanschrift.setText("");
            jTF_OrtLieferanschrift.setEnabled(true);
        }
    }//GEN-LAST:event_jCHB_WieAnschriftActionPerformed
    /*
     * Methode, um den Typ des Geschäftspartners zu wählen.
     */
    private void jCHB_KundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCHB_KundeActionPerformed
        if (jCHB_Kunde.isSelected()) {
            jCHB_Lieferant.setSelected(false);
        }
    }//GEN-LAST:event_jCHB_KundeActionPerformed
    /*
     * Methode, um den Typ des Geschäftspartners zu wählen.
     */
    private void jCHB_LieferantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCHB_LieferantActionPerformed
        if (jCHB_Lieferant.isSelected()) {
            jCHB_Kunde.setSelected(false);
        }
    }//GEN-LAST:event_jCHB_LieferantActionPerformed
    /*
     * Methode, die beim Drucken auf Speichern ausgeführt wird.
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed
        //      zunaechst werdne die Eingaben ueberprueft.    
        ueberpruefeFormular();
        String typ;
        String titel;
        String name;
        String vorname;
        String telefon;
        String fax;
//        Date geburtsdatum;
        String erfassungsdatum;
        String eMail;
        String kreditlimit;
        String strasseAnschrift;
        String strasseLieferanschrift;
        String hausnummerAnschrift;
        String hausnummerLieferanschrift;
        String plzAnschrift;
        String plzLieferanschrift;
        String ortAnschrift;
        String ortLieferanschrift;
//      falls fehlerhafteComponenten leer ist (es sind keine fehlerhaften Componenten verfuegbar), 
//      koennen wir sicher sein, dass alle Felder ausgefuellt sind, nun
//      werden die Eingaben in die entsprechenden Variablen gespeichert
        if (fehlerhafteComponenten.isEmpty()) {
            if (jCHB_Kunde.isSelected() || jCHB_Lieferant.isSelected()) {
                if (jCHB_Kunde.isSelected()) {
                    typ = "Kunde";
                } else {
                    typ = "Lieferant";
                }
                titel = (String) jCB_Anrede.getSelectedItem();
                name = jTF_Name.getText();
                vorname = jTF_Vorname.getText();
                telefon = jTF_Telefon.getText();
                fax = jTF_Fax.getText();
//                geburtsdatum = jFTF_Geburtsdatum.getText();
                erfassungsdatum = jFTF_Erfassungsdatum.getText();
                eMail = jTF_EMail.getText();
                kreditlimit = jTF_Kreditlimit.getText();
                strasseAnschrift = jTF_StrasseRechnungsanschrift.getText();
                hausnummerAnschrift = jTF_HausnummerRechnungsanschrift.getText();
                plzAnschrift = jTF_PLZRechnungsanschrift.getText();
                ortAnschrift = jTF_OrtRechnungsanschrift.getText();
                double k = 0;
                try {
                    k = nf.parse(jTF_Kreditlimit.getText()).doubleValue();
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    anschrift = this.dao.createAdress("Rechnungsadresse", name, vorname,
                            titel, strasseAnschrift, hausnummerAnschrift, plzAnschrift,
                            ortAnschrift, "Deutschland", telefon, fax, eMail, eingabeDatum);

                } catch (ApplicationException e) {
                    System.out.println(e.getMessage());
                }

                if (jCHB_WieAnschrift.isSelected()) {
                    strasseLieferanschrift = strasseAnschrift;
                    hausnummerLieferanschrift = hausnummerAnschrift;
                    plzLieferanschrift = plzAnschrift;
                    ortLieferanschrift = ortAnschrift;
                    lieferanschrift = anschrift;
                } else {
                    strasseLieferanschrift = jTF_StrasseLieferanschrift.getText();
                    hausnummerLieferanschrift = jTF_HausnummerLieferanschrift.getText();
                    plzLieferanschrift = jTF_PLZLieferanschrift.getText();
                    ortLieferanschrift = jTF_OrtLieferanschrift.getText();
                    System.out.println(tempGebuDate);
                    try {
                        lieferanschrift = this.dao.createAdress("Lieferadresse", name, vorname,
                                titel, strasseLieferanschrift, hausnummerLieferanschrift, plzLieferanschrift,
                                ortLieferanschrift, "Deutschland", telefon, fax, eMail, eingabeDatum);

                    } catch (ApplicationException e) {
                        System.out.println(e.getMessage());
                    }
                }
                try {

                    this.dao.createBusinessPartner(typ, lieferanschrift, anschrift, k);
                } catch (ApplicationException e) {
                    System.out.println(e.getMessage());
                }

//                System.out.println("Geschäftspartner: \n"
//                        + "Geschäftspartnernummer:      " + geschaeftspartnerNr + "\n"
//                        + "Typ:                         " + typ + "\n"
//                        + "Titel:                       " + titel + "\n"
//                        + "Name:                        " + name + "\n"
//                        + "Vorname:                     " + vorname + "\n"
//                        + "Telefon:                     " + telefon + "\n"
//                        + "Fax:                         " + fax + "\n"
//                        + "Geburtsdatum:                " + eingabeDatum + "\n"
//                        + "Erfassungsdatum:             " + erfassungsdatum + "\n"
//                        + "eMail:                       " + eMail + "\n"
//                        + "Kreditlimit:                 " + kreditlimit + "\n"
//                        + "Straße Anschrift:            " + strasseAnschrift + "\n"
//                        + "Hausnummer Anschrift:        " + hausnummerAnschrift + "\n"
//                        + "PLZ Anschrift:               " + plzAnschrift + "\n"
//                        + "Ort Anschrift:               " + ortAnschrift + "\n"
//                        + "Straße LAnschrift:           " + strasseLieferanschrift + "\n"
//                        + "Hausnummer LAnschrift:       " + hausnummerLieferanschrift + "\n"
//                        + "PLZ LAnschrift:              " + plzLieferanschrift + "\n"
//                        + "Ort LAnschrift:              " + ortLieferanschrift + "\n");
                geschaeftspartnerNr++;
                setzeFormularZurueck();

            } else {
                JOptionPane.showMessageDialog(null, "Bitte wählen Sie den Typ des Geschäftspartners.", "Unvollständige Eingabe", JOptionPane.ERROR_MESSAGE);
                jCHB_Kunde.requestFocusInWindow();
            }
        } else {
//          fehlerhafteComponenten ist nicht leer (es sind fehlerhafte Componenten vorhanden)
//          eine Meldung wird ausgegeben  
            JOptionPane.showMessageDialog(null, MELDUNG_PFLICHTFELDER_TEXT, MELDUNG_PFLICHTFELDER_TITEL, JOptionPane.ERROR_MESSAGE);
//          an die erste fehlerhafte Componenten wird der Focus gesetzt  
            fehlerhafteComponenten.get(0).requestFocusInWindow();
//          ueber die fehlerhaften Komponenten wird iteriert und bei allen fehlerhaften Componenten wird der Hintergrund in der fehlerhaften Farbe gefaerbt 
            for (int i = 0; i <= fehlerhafteComponenten.size() - 1; i++) {
                fehlerhafteComponenten.get(i).setBackground(FARBE_FEHLERHAFT);
            }
//          ArrayList fue fehlerhafte Componenten wird geleert
            fehlerhafteComponenten.clear();
        }

    }//GEN-LAST:event_jB_SpeichernActionPerformed
    /*
     * Methode die prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jCB_AnredeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_AnredeActionPerformed
        if (jCB_Anrede.getSelectedIndex() != 0) {
            jCB_Anrede.setBackground(JCB_FARBE_STANDARD);
            jTF_Statuszeile.setText("");
        }
    }//GEN-LAST:event_jCB_AnredeActionPerformed
    /*
     * Methode die prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jTF_NameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_NameFocusLost
        if (!jTF_Name.getText().equals("")) {
            jTF_Name.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_NameFocusLost
    /*
     * Methode die prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jTF_VornameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_VornameFocusLost
        if (!jTF_Vorname.getText().equals("")) {
            jTF_Vorname.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_VornameFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Wenn Eingabe korrekt ist, wird der Hintergrund in standard gefärbt.
     * Wenn nicht, wird eine Meldung ausgegeben.
     */
    private void jTF_TelefonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_TelefonFocusLost
        if (!jTF_Telefon.getText().matches(PRUEFUNG_TELEFON)) {
            String meldung = "Die eingegebene Telefonnummer ist nicht richtig! \n Bitte geben Sie eine richtige Telefonnummer ein. (z.B. 1234123)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_Telefon.requestFocusInWindow();
            jTF_Telefon.setText("");
        } else if (!jTF_Telefon.getText().equals("")) {
            jTF_Telefon.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_TelefonFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Wenn Eingabe korrekt ist, wird der Hintergrund in standard gefärbt.
     * Wenn nicht, wird eine Meldung ausgegeben.
     */
    private void jTF_FaxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_FaxFocusLost
        if (!jTF_Fax.getText().matches(PRUEFUNG_TELEFON)) {
            String meldung = "Die eingegebene Faxnummer ist nicht richtig! \n Bitte geben Sie eine richtige Faxnummer ein. (z.B. 1234123)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_Fax.requestFocusInWindow();
            jTF_Fax.setText("");
        } else if (!jTF_Fax.getText().equals("")) {
            jTF_Fax.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_FaxFocusLost
    /*
     * Methode die prüft, ob das einegegebene Geburtsdatum gültig ist.
     */
    private void jFTF_GeburtsdatumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTF_GeburtsdatumFocusLost
//        String eingabeGeburtsdatum = jFTF_Geburtsdatum.getText();
//        String eingabeJahr = eingabeGeburtsdatum.substring(6, eingabeGeburtsdatum.length());
//        if (!jFTF_Geburtsdatum.getText().equals("##.##.####")) {
//            if (eingabeJahr.length() == 4 && (eingabeJahr.startsWith("20") || eingabeJahr.startsWith("19"))) {
//                try {
//                    Date tempAktDate = FORMAT.parse(aktuellesDatum);
//                    tempGebuDate = FORMAT.parse(eingabeGeburtsdatum);
////                Date temp = FORMAT.parse("09.12.1996");
////                Date temp1 = FORMAT.parse("09.12.2014");
////                long achtzehn = temp1.getTime() - temp.getTime();
//
//                    cal.setTime(tempAktDate);
//                    cal.add(Calendar.YEAR, -18);
//                    Date dateBefore18Years = cal.getTime();
//
////                System.out.println(temp.getTime());
////                System.out.println(temp1.getTime());
//                    if (tempGebuDate.getTime() > tempAktDate.getTime()) {
//                        String meldung = "Das eingegebene Geburtsdatum ist in der Zukunft! \nBitte geben Sie ein gültiges Geburtsdatm Datum ein. (z.B. 01.01.1980)";
//                        String titel = "Fehlerhafte Eingabe";
//                        JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//                        jFTF_Geburtsdatum.requestFocusInWindow();
//                        jFTF_Geburtsdatum.setText("##.##.####");
////                } else if (tempGebuDate.getTime() + achtzehn > tempAktDate.getTime()) {
//                    } else if (tempGebuDate.getTime() > dateBefore18Years.getTime()) {
//                        String meldung = "Der eingebene Geschäftspartner ist nicht volljährig!";
//                        String titel = "Fehlerhafte Eingabe";
//                        JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//                        jFTF_Geburtsdatum.requestFocusInWindow();
//                        jFTF_Geburtsdatum.setText("##.##.####");
//                    } else {
//                        jFTF_Geburtsdatum.setBackground(JTF_FARBE_STANDARD);
//                    }
//                } catch (ParseException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            } else {
//                // eingabe Ungültig z.B. 19999;
//                String meldung = "Das eingegebene Geburtsdatum ist in nicht gültig! \nBitte geben Sie ein gültiges Geburtsdatm Datum ein. (z.B. 01.01.1990)";
//                String titel = "Fehlerhafte Eingabe";
//                JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//                jFTF_Geburtsdatum.requestFocusInWindow();
//                jFTF_Geburtsdatum.setText("");
//            }
//        } else {
//            //jFTF_Geburtsdatum.setText("01.01.1990");
//            jFTF_Geburtsdatum.setValue(null);
//        }

        if (!jFTF_Geburtsdatum.getText().equals("##.##.####")) {
            Date heute;
            try {
                eingabeDatum = FORMAT.parse(jFTF_Geburtsdatum.getText());
                heute = FORMAT.parse(aktuellesDatum);

                cal.setTime(heute);
                cal.add(Calendar.YEAR, -18);
                Date dateBefore18Years = cal.getTime();

                if (eingabeDatum.after(heute)) {
                    String meldung = "Das eingegebene Geburtsdatum ist in der Zukunft! \nBitte geben Sie ein gültiges Geburtsdatm Datum ein. (z.B. 01.01.1980)";
                    String titel = "Fehlerhafte Eingabe";
                    JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                    jFTF_Geburtsdatum.requestFocusInWindow();
                    jFTF_Geburtsdatum.setValue(null);
                } else if (eingabeDatum.after(dateBefore18Years)) {
                    String meldung = "Der eingebene Geschäftspartner ist nicht volljährig!";
                    String titel = "Fehlerhafte Eingabe";
                    JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                    jFTF_Geburtsdatum.requestFocusInWindow();
                    jFTF_Geburtsdatum.setValue(null);
                } else {
                    jFTF_Geburtsdatum.setBackground(JTF_FARBE_STANDARD);
                }
            } catch (ParseException e) {
                String meldung = "Das eingegebene Datum ist nicht gülitig! \nBitte geben Sie ein gültiges Geburtsdatm Datum ein. (z.B. 01.01.1980)";
                String titel = "Fehlerhafte Eingabe";
                JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                jFTF_Geburtsdatum.requestFocusInWindow();
                jFTF_Geburtsdatum.setValue(null);
            }
        }
    }//GEN-LAST:event_jFTF_GeburtsdatumFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Wenn Eingabe korrekt ist, wird der Hintergrund in standard gefärbt.
     * Wenn nicht, wird eine Meldung ausgegeben.
     */
    private void jTF_EMailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_EMailFocusLost
        if (!jTF_EMail.getText().matches(PRUEFUNG_EMAIL)) {
            String meldung = "Die einegebene eMail ist nicht richtig! \nBitte geben Sie eine richtige eMail Adresse ein. (z.B. abc@abc.de)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_EMail.requestFocusInWindow();
            jTF_EMail.setText("");
        } else if (!jTF_EMail.getText().equals("")) {
            jTF_EMail.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_EMailFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Wenn Eingabe korrekt ist, wird der Hintergrund in standard gefärbt.
     * Wenn nicht, wird eine Meldung ausgegeben.
     */
    private void jTF_KreditlimitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_KreditlimitFocusLost
        if (!jTF_Kreditlimit.getText().matches(PRUEFUNG_PREIS)) {
            String meldung = "Der eingegebene Preis ist nicht richtig! \n Bitte geben Sie einen richtigen Preis ein. (z.B. 9,99 oder 12.123,99)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_Kreditlimit.requestFocusInWindow();
            jTF_Kreditlimit.setText("");
        } else if (!jTF_Kreditlimit.getText().equals("")) {
            try {
                double eingabeKreditlimit = nf.parse(jTF_Kreditlimit.getText()).doubleValue();
                jTF_Kreditlimit.setText(nf.format(eingabeKreditlimit));
                jTF_Kreditlimit.setBackground(JTF_FARBE_STANDARD);
            } catch (ParseException ex) {
                System.out.println("Fehler in der Methode jTF_eMailFocusLost()");
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_jTF_KreditlimitFocusLost
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_StrasseRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseRechnungsanschriftFocusLost
        if (!jTF_StrasseRechnungsanschrift.getText().equals("")) {
            jTF_StrasseRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_StrasseRechnungsanschriftFocusLost
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_StrasseLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseLieferanschriftFocusLost
        if (!jTF_StrasseLieferanschrift.getText().equals("")) {
            jTF_StrasseLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_StrasseLieferanschriftFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Wenn Eingabe korrekt ist, wird der Hintergrund in standard gefärbt.
     * Wenn nicht, wird eine Meldung ausgegeben.
     */
    private void jTF_HausnummerRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerRechnungsanschriftFocusLost
        if (!jTF_HausnummerRechnungsanschrift.getText().matches(PRUEFUNG_HAUSNUMMER)) {
            String meldung = "Die eingegebene Hausnummer ist nicht richtig! \n Bitte geben Sie eine richtige Hausnummer ein. (z.B. 10A oder 10)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_HausnummerRechnungsanschrift.requestFocusInWindow();
            jTF_HausnummerRechnungsanschrift.setText("");
        } else if (!jTF_HausnummerRechnungsanschrift.getText().equals("")) {
            jTF_HausnummerRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_HausnummerRechnungsanschriftFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Wenn Eingabe korrekt ist, wird der Hintergrund in standard gefärbt.
     * Wenn nicht, wird eine Meldung ausgegeben.
     */
    private void jTF_HausnummerLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerLieferanschriftFocusLost
        if (!jTF_HausnummerLieferanschrift.getText().matches(PRUEFUNG_HAUSNUMMER)) {
            String meldung = "Die eingegebene Hausnummer ist nicht richtig! \n Bitte geben Sie eine richtige Hausnummer ein. (z.B. 10A oder 10)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_HausnummerLieferanschrift.requestFocusInWindow();
            jTF_HausnummerLieferanschrift.setText("");
        } else if (!jTF_HausnummerLieferanschrift.getText().equals("")) {
            jTF_HausnummerLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_HausnummerLieferanschriftFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Wenn Eingabe korrekt ist, wird der Hintergrund in standard gefärbt.
     * Wenn nicht, wird eine Meldung ausgegeben.
     */
    private void jTF_PLZRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZRechnungsanschriftFocusLost
        if (!jTF_PLZRechnungsanschrift.getText().matches(PRUEFUNG_PLZ)) {
            String meldung = "Die eingegebene Postleitzahl ist nicht richtig! \n Bitte geben Sie eine richtige Postleitzahl ein. (z.B. 45968)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_PLZRechnungsanschrift.requestFocusInWindow();
            jTF_PLZRechnungsanschrift.setText("");
        } else if (!jTF_PLZRechnungsanschrift.getText().equals("")) {
            jTF_PLZRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_PLZRechnungsanschriftFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Wenn Eingabe korrekt ist, wird der Hintergrund in standard gefärbt.
     * Wenn nicht, wird eine Meldung ausgegeben.
     */
    private void jTF_PLZLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZLieferanschriftFocusLost
        if (!jTF_PLZLieferanschrift.getText().matches(PRUEFUNG_PLZ)) {
            String meldung = "Die eingegebene Postleitzahl ist nicht richtig! \n Bitte geben Sie eine richtige Postleitzahl ein. (z.B. 45968)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_PLZLieferanschrift.requestFocusInWindow();
            jTF_PLZLieferanschrift.setText("");
        } else if (!jTF_PLZLieferanschrift.getText().equals("")) {
            jTF_PLZLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_PLZLieferanschriftFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jTF_OrtRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtRechnungsanschriftFocusLost
        if (!jTF_OrtRechnungsanschrift.getText().equals("")) {
            jTF_OrtRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_OrtRechnungsanschriftFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jTF_OrtLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtLieferanschriftFocusLost
        if (!jTF_OrtLieferanschrift.getText().equals("")) {
            jTF_OrtLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_OrtLieferanschriftFocusLost
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_NameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_NameFocusGained
        jTF_Name.selectAll();
    }//GEN-LAST:event_jTF_NameFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_VornameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_VornameFocusGained
        jTF_Vorname.selectAll();
    }//GEN-LAST:event_jTF_VornameFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_TelefonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_TelefonFocusGained
        jTF_Telefon.selectAll();
    }//GEN-LAST:event_jTF_TelefonFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_FaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_FaxFocusGained
        jTF_Fax.selectAll();
    }//GEN-LAST:event_jTF_FaxFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_EMailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_EMailFocusGained
        jTF_EMail.selectAll();
    }//GEN-LAST:event_jTF_EMailFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_KreditlimitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_KreditlimitFocusGained
        jTF_Kreditlimit.selectAll();
    }//GEN-LAST:event_jTF_KreditlimitFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_StrasseRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseRechnungsanschriftFocusGained
        jTF_StrasseRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_StrasseRechnungsanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_StrasseLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseLieferanschriftFocusGained
        jTF_StrasseLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_StrasseLieferanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_HausnummerRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerRechnungsanschriftFocusGained
        jTF_HausnummerRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_HausnummerRechnungsanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_HausnummerLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerLieferanschriftFocusGained
        jTF_HausnummerLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_HausnummerLieferanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_PLZRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZRechnungsanschriftFocusGained
        jTF_PLZRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_PLZRechnungsanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_PLZLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZLieferanschriftFocusGained
        jTF_PLZLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_PLZLieferanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_OrtRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtRechnungsanschriftFocusGained
        jTF_OrtRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_OrtRechnungsanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_OrtLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtLieferanschriftFocusGained
        jTF_OrtLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_OrtLieferanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        beendenEingabeNachfrage();
    }//GEN-LAST:event_formInternalFrameClosing

    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        c = null;   //Initialisierung der Componentspeichervariable
        //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
        c = this.factory.zurueckButton();
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        c.setVisible(true);// Übergebene Component wird sichtbar gemacht
        this.setzeFormularZurueck();
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    public JTextField gibjTF_GeschaeftspartnerID() {
        return jTF_GeschaeftspartnerID;
    }

    public JCheckBox gibjCHB_Kunde() {
        return jCHB_Kunde;
    }

    public JCheckBox gibjCHB_Lieferant() {
        return jCHB_Lieferant;
    }

    public JComboBox gibjCB_Anrede() {
        return jCB_Anrede;
    }

    public JTextField gibjTF_Name() {
        return jTF_Name;
    }

    public JTextField gibjTF_Vorname() {
        return jTF_Vorname;
    }

    public JTextField gibjTF_Telefon() {
        return jTF_Telefon;
    }

    public JTextField gibjTF_Fax() {
        return jTF_Fax;
    }

    public JFormattedTextField gibjFTF_Geburtsdatum() {
        return jFTF_Geburtsdatum;
    }

    public JFormattedTextField gibjFTF_Erfassungsdatum() {
        return jFTF_Erfassungsdatum;
    }

    public JTextField gibjTF_EMail() {
        return jTF_EMail;
    }

    public JTextField gibjTF_Kreditlimit() {
        return jTF_Kreditlimit;
    }

    public JCheckBox gibjCHB_WieAnschrift() {
        return jCHB_WieAnschrift;
    }

    public JTextField gibjTF_StrasseRechnungsanschrift() {
        return jTF_StrasseRechnungsanschrift;
    }

    public JTextField gibjTF_HausnummerRechnungsanschrift() {
        return jTF_HausnummerRechnungsanschrift;
    }

    public JTextField gibjTF_PLZRechnungsanschrift() {
        return jTF_PLZRechnungsanschrift;
    }

    public JTextField gibjTF_OrtRechnungsanschrift() {
        return jTF_OrtRechnungsanschrift;
    }

    public JTextField gibjTF_StrasseLieferanschrift() {
        return jTF_StrasseLieferanschrift;
    }

    public JTextField gibjTF_HausnummerLieferanschrift() {
        return jTF_HausnummerLieferanschrift;
    }

    public JTextField gibjTF_PLZLieferanschrift() {
        return jTF_PLZLieferanschrift;
    }

    public JTextField gibjTF_OrtLieferanschrift() {
        return jTF_OrtLieferanschrift;
    }

    public void speichereLieferadresse() {
        jTF_StrasseLieferanschriftFocusLost(null);
        jTF_HausnummerLieferanschriftFocusLost(null);
        jTF_PLZLieferanschriftFocusLost(null);
        jTF_OrtLieferanschriftFocusLost(null);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, implemtiereung der Referenzvariable "hauptfenster"*/
    /*----------------------------------------------------------*/
    public void setzeFormularInGPAnlegenAEndern() {
        setzeFormularZurueck();
        jCHB_Kunde.setEnabled(true);
        jCHB_Lieferant.setEnabled(true);
        jCB_Anrede.setEnabled(true);
        jTF_Name.setEnabled(true);
        jTF_Vorname.setEnabled(true);
        jTF_Telefon.setEnabled(true);
        jTF_Fax.setEnabled(true);
        jFTF_Geburtsdatum.setEnabled(true);
        jTF_EMail.setEnabled(true);
        jTF_Kreditlimit.setEnabled(true);
        jCHB_WieAnschrift.setEnabled(true);
        jTF_StrasseRechnungsanschrift.setEnabled(true);
        jTF_HausnummerRechnungsanschrift.setEnabled(true);
        jTF_PLZRechnungsanschrift.setEnabled(true);
        jTF_OrtRechnungsanschrift.setEnabled(true);
        jTF_StrasseLieferanschrift.setEnabled(true);
        jTF_HausnummerLieferanschrift.setEnabled(true);
        jTF_PLZLieferanschrift.setEnabled(true);
        jTF_OrtLieferanschrift.setEnabled(true);
        sichtAnlegenAEndern = true;
        this.hauptFenster.setComponent(this);//Übergibt der Referenz des Hauptfensters das Internaframe
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, implemtiereung der Referenzvariable "hauptfenster"*/
    /*----------------------------------------------------------*/
    public void setzeFormularInGPAnzeigen() {
        setzeFormularZurueck();
        jCHB_Kunde.setEnabled(false);
        jCHB_Lieferant.setEnabled(false);
        jCB_Anrede.setEnabled(false);
        jTF_Name.setEnabled(false);
        jTF_Vorname.setEnabled(false);
        jTF_Telefon.setEnabled(false);
        jTF_Fax.setEnabled(false);
        jFTF_Geburtsdatum.setEnabled(false);
        jTF_EMail.setEnabled(false);
        jTF_Kreditlimit.setEnabled(false);
        jCHB_WieAnschrift.setEnabled(false);
        jTF_StrasseRechnungsanschrift.setEnabled(false);
        jTF_HausnummerRechnungsanschrift.setEnabled(false);
        jTF_PLZRechnungsanschrift.setEnabled(false);
        jTF_OrtRechnungsanschrift.setEnabled(false);
        jTF_StrasseLieferanschrift.setEnabled(false);
        jTF_HausnummerLieferanschrift.setEnabled(false);
        jTF_PLZLieferanschrift.setEnabled(false);
        jTF_OrtLieferanschrift.setEnabled(false);
        sichtAnlegenAEndern = false;
        this.hauptFenster.setComponent(this);//Übergibt der Referenz des Hauptfensters das Internaframe
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JComboBox jCB_Anrede;
    private javax.swing.JCheckBox jCHB_Kunde;
    private javax.swing.JCheckBox jCHB_Lieferant;
    private javax.swing.JCheckBox jCHB_WieAnschrift;
    private javax.swing.JFormattedTextField jFTF_Erfassungsdatum;
    private javax.swing.JFormattedTextField jFTF_Geburtsdatum;
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
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTF_EMail;
    private javax.swing.JTextField jTF_Fax;
    private javax.swing.JTextField jTF_GeschaeftspartnerID;
    private javax.swing.JTextField jTF_HausnummerLieferanschrift;
    private javax.swing.JTextField jTF_HausnummerRechnungsanschrift;
    private javax.swing.JTextField jTF_Kreditlimit;
    private javax.swing.JTextField jTF_Name;
    private javax.swing.JTextField jTF_OrtLieferanschrift;
    private javax.swing.JTextField jTF_OrtRechnungsanschrift;
    private javax.swing.JTextField jTF_PLZLieferanschrift;
    private javax.swing.JTextField jTF_PLZRechnungsanschrift;
    private javax.swing.JTextField jTF_Statuszeile;
    private javax.swing.JTextField jTF_StrasseLieferanschrift;
    private javax.swing.JTextField jTF_StrasseRechnungsanschrift;
    private javax.swing.JTextField jTF_Telefon;
    private javax.swing.JTextField jTF_Vorname;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
