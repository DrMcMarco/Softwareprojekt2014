package GUI_Internalframes;

import Documents.UniversalDocument;
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
import javax.swing.JOptionPane;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Tahir
 * 
 * 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button
 */
public class GeschaeftspartnerAnlegen extends javax.swing.JInternalFrame {

    Component c;
    GUIFactory factory;
    
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
    private final Color FARBE_FEHLERHAFT = new Color(255, 165, 79);
//  Insantzvariablen für die Meldungen         
    private final String MELDUNG_PFLICHTFELDER_TITEL = "Felder nicht ausgefüllt";
    private final String MELDUNG_PFLICHTFELDER_TEXT = "Einige Felder wurden nicht ausgefüllt! Bitte füllen Sie diese aus!";

    private final String PRUEFUNG_PREIS = "|(\\d*,?\\d{1,2})|(\\d{0,3}(\\.\\d{3})*,?\\d{1,2})";
    private final String PRUEFUNG_HAUSNUMMER = "|[1-9][0-9]{0,2}[a-zA-Z]?";
    private final String PRUEFUNG_PLZ = "|[0-9]{5}";
    private final String PRUEFUNG_EMAIL = "|^[a-zA-Z0-9][\\w\\.-]*@(?:[a-zA-Z0-9][a-zA-Z0-9_-]+\\.)+[A-Z,a-z]{2,5}$";
    private final String PRUEFUNG_TELEFON = "|^([+][ ]?[1-9][0-9][ ]?[-]?[ ]?|[(]?[0][ ]?)[0-9]{3,4}[-)/ ]?[ ]?[1-9][-0-9 ]{3,16}$";

    private int geschaeftspartnerNr = 1;
    private final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.mm.yyyy");

    private NumberFormat nf;
    Calendar cal = Calendar.getInstance();

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     */
    public GeschaeftspartnerAnlegen(GUIFactory factory) {
        initComponents();
        fehlerhafteComponenten = new ArrayList<>();
        this.factory = factory;
        nf = NumberFormat.getInstance();

        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        jTF_Name.setDocument(new UniversalDocument("-.´", true));
        jTF_Vorname.setDocument(new UniversalDocument("-.´", true));
        jTF_Telefon.setDocument(new UniversalDocument("0123456789/-", false));
        jTF_Fax.setDocument(new UniversalDocument("0123456789/", false));
        jTF_eMail.setDocument(new UniversalDocument("0123456789@-.", true));
        jTF_Kreditlimit.setDocument(new UniversalDocument("0123456789.,", false));
        jTF_StrasseAnschrift.setDocument(new UniversalDocument("-.´", true));
        jTF_StrasseLieferanschrift.setDocument(new UniversalDocument("-.´", true));
        jTF_HausnummerAnschrift.setDocument(new UniversalDocument("0123456789", true));
        jTF_HausnummerLieferanschrift.setDocument(new UniversalDocument("0123456789", true));
        jTF_PLZAnschrift.setDocument(new UniversalDocument("0123456789", false));
        jTF_PLZLieferanschrift.setDocument(new UniversalDocument("0123456789", false));
        jTF_OrtAnschrift.setDocument(new UniversalDocument("-", true));
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
        if (jCB_Titel.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Titel);
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
        if (jFTF_Geburtsdatum.getText().equals(aktuellesDatum)) {
            fehlerhafteComponenten.add(jFTF_Geburtsdatum);
        }
        if (jTF_eMail.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_eMail);
        }
        if (jTF_Kreditlimit.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Kreditlimit);
        }
        if (jTF_StrasseAnschrift.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_StrasseAnschrift);
        }
        if (jTF_HausnummerAnschrift.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_HausnummerAnschrift);
        }
        if (jTF_PLZAnschrift.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_PLZAnschrift);
        }
        if (jTF_OrtAnschrift.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_OrtAnschrift);
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
        jTF_Geschaeftspartnernummer.setText("" + geschaeftspartnerNr);
        jCHB_Kunde.setSelected(false);
        jCHB_Lieferant.setSelected(false);
        jCB_Titel.setSelectedIndex(0);
        jCB_Titel.setBackground(JCB_FARBE_STANDARD);
        jTF_Name.setText("");
        jTF_Name.setBackground(JTF_FARBE_STANDARD);
        jTF_Vorname.setText("");
        jTF_Vorname.setBackground(JTF_FARBE_STANDARD);
        jTF_Telefon.setText("");
        jTF_Telefon.setBackground(JTF_FARBE_STANDARD);
        jTF_Fax.setText("");
        jTF_Fax.setBackground(JTF_FARBE_STANDARD);
        jFTF_Geburtsdatum.setText(aktuellesDatum);
        jFTF_Geburtsdatum.setBackground(JTF_FARBE_STANDARD);
        jTF_eMail.setText("");
        jTF_eMail.setBackground(JTF_FARBE_STANDARD);
        jTF_Kreditlimit.setText("");
        jTF_Kreditlimit.setBackground(JTF_FARBE_STANDARD);
        jTF_StrasseAnschrift.setText("");
        jTF_StrasseAnschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_HausnummerAnschrift.setText("");
        jTF_HausnummerAnschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_PLZAnschrift.setText("");
        jTF_PLZAnschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_OrtAnschrift.setText("");
        jTF_OrtAnschrift.setBackground(JTF_FARBE_STANDARD);
        jCHB_WieAnschrift.setSelected(false);
        jCHB_WieAnschriftActionPerformed(null);
        jTF_StrasseLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_HausnummerLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_PLZLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        jTF_OrtLieferanschrift.setBackground(JTF_FARBE_STANDARD);
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
        jB_Abbrechen = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_Anzeigen = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jTF_Statuszeile = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTF_Geschaeftspartnernummer = new javax.swing.JTextField();
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
        jCB_Titel = new javax.swing.JComboBox();
        jTF_Name = new javax.swing.JTextField();
        jTF_Fax = new javax.swing.JTextField();
        jTF_Vorname = new javax.swing.JTextField();
        jFTF_Erfassungsdatum = new javax.swing.JFormattedTextField();
        jFTF_Geburtsdatum = new javax.swing.JFormattedTextField();
        jTF_eMail = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTF_StrasseAnschrift = new javax.swing.JTextField();
        jTF_HausnummerAnschrift = new javax.swing.JTextField();
        jTF_OrtAnschrift = new javax.swing.JTextField();
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
        jTF_PLZAnschrift = new javax.swing.JTextField();
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

        jB_Zurueck.setText("Zurück");
        jB_Zurueck.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jB_Zurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ZurueckActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Zurueck);

        jB_Abbrechen.setText("Abbrechen");
        jToolBar1.add(jB_Abbrechen);

        jB_Speichern.setText("Speichern");
        jB_Speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SpeichernActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setText("Anzeige/Ändern");
        jB_Anzeigen.setActionCommand("Anzeigen/Ändern");
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setText("Löschen");
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

        jTF_Geschaeftspartnernummer.setText("1");
        jTF_Geschaeftspartnernummer.setEnabled(false);

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
        jLabel7.setText("Titel:");

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

        jCB_Titel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "Herr ", "Frau" }));
        jCB_Titel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_TitelActionPerformed(evt);
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

        jFTF_Geburtsdatum.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFTF_Geburtsdatum.setToolTipText("Format: TT:MM:JJJJ");
        jFTF_Geburtsdatum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTF_GeburtsdatumFocusLost(evt);
            }
        });

        jTF_eMail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_eMailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_eMailFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Anschrift:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Straße:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Hausnr.:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("PLZ:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Ort:");

        jTF_StrasseAnschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_StrasseAnschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_StrasseAnschriftFocusLost(evt);
            }
        });

        jTF_HausnummerAnschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_HausnummerAnschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_HausnummerAnschriftFocusLost(evt);
            }
        });

        jTF_OrtAnschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_OrtAnschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_OrtAnschriftFocusLost(evt);
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

        jTF_PLZAnschrift.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTF_PLZAnschriftFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_PLZAnschriftFocusLost(evt);
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
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
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
                            .addComponent(jCB_Titel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTF_Geschaeftspartnernummer)
                            .addComponent(jTF_Telefon)
                            .addComponent(jFTF_Geburtsdatum, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTF_eMail, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTF_OrtAnschrift, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(jTF_HausnummerAnschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_StrasseAnschrift, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(jTF_PLZAnschrift))
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
                .addContainerGap(54, Short.MAX_VALUE))
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
                    .addComponent(jTF_Geschaeftspartnernummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCHB_Kunde)
                    .addComponent(jLabel2)
                    .addComponent(jCHB_Lieferant))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jCB_Titel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(jTF_eMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jTF_StrasseAnschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTF_HausnummerAnschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTF_PLZAnschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTF_OrtAnschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            if (!jTF_StrasseAnschrift.getText().equals("") && !jTF_HausnummerAnschrift.getText().equals("")
                    && !jTF_PLZAnschrift.getText().equals("") && !jTF_OrtAnschrift.getText().equals("")) {
                jTF_StrasseLieferanschrift.setText(jTF_StrasseAnschrift.getText());
                jTF_StrasseLieferanschrift.setEnabled(false);
                jTF_HausnummerLieferanschrift.setText(jTF_HausnummerAnschrift.getText());
                jTF_HausnummerLieferanschrift.setEnabled(false);
                jTF_PLZLieferanschrift.setText(jTF_PLZAnschrift.getText());
                jTF_PLZLieferanschrift.setEnabled(false);
                jTF_OrtLieferanschrift.setText(jTF_OrtAnschrift.getText());
                jTF_OrtLieferanschrift.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Bitte geben Sie zunächst die komplette Anschrift ein.", "Unvollständige Eingabe", JOptionPane.ERROR_MESSAGE);
                jCHB_WieAnschrift.setSelected(false);
                if (jTF_StrasseAnschrift.getText().equals("")) {
                    jTF_StrasseAnschrift.requestFocusInWindow();
                } else if (jTF_HausnummerAnschrift.getText().equals("")) {
                    jTF_HausnummerAnschrift.requestFocusInWindow();
                } else if (jTF_PLZAnschrift.getText().equals("")) {
                    jTF_PLZAnschrift.requestFocusInWindow();
                } else {
                    jTF_OrtAnschrift.requestFocusInWindow();
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
        String geburtsdatum;
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
//      werden die Eingaben in die entsprechenden Variablen gespeichert
        if (fehlerhafteComponenten.isEmpty()) {
            if (jCHB_Kunde.isSelected() || jCHB_Lieferant.isSelected()) {
                if (jCHB_Kunde.isSelected()) {
                    typ = "Kunde";
                } else {
                    typ = "Lieferant";
                }
                titel = (String) jCB_Titel.getSelectedItem();
                name = jTF_Name.getText();
                vorname = jTF_Vorname.getText();
                telefon = jTF_Telefon.getText();
                fax = jTF_Fax.getText();
                geburtsdatum = jFTF_Geburtsdatum.getText();
                erfassungsdatum = jFTF_Erfassungsdatum.getText();
                eMail = jTF_eMail.getText();
                kreditlimit = jTF_Kreditlimit.getText();
                strasseAnschrift = jTF_StrasseAnschrift.getText();
                hausnummerAnschrift = jTF_HausnummerAnschrift.getText();
                plzAnschrift = jTF_PLZAnschrift.getText();
                ortAnschrift = jTF_OrtAnschrift.getText();
                if (jCHB_WieAnschrift.isSelected()) {
                    strasseLieferanschrift = strasseAnschrift;
                    hausnummerLieferanschrift = hausnummerAnschrift;
                    plzLieferanschrift = plzAnschrift;
                    ortLieferanschrift = ortAnschrift;
                } else {
                    strasseLieferanschrift = jTF_StrasseLieferanschrift.getText();
                    hausnummerLieferanschrift = jTF_HausnummerLieferanschrift.getText();
                    plzLieferanschrift = jTF_PLZLieferanschrift.getText();
                    ortLieferanschrift = jTF_OrtLieferanschrift.getText();
                }

                System.out.println("Geschäftspartner: \n"
                        + "Geschäftspartnernummer:      " + geschaeftspartnerNr + "\n"
                        + "Typ:                         " + typ + "\n"
                        + "Titel:                       " + titel + "\n"
                        + "Name:                        " + name + "\n"
                        + "Vorname:                     " + vorname + "\n"
                        + "Telefon:                     " + telefon + "\n"
                        + "Fax:                         " + fax + "\n"
                        + "Geburtsdatum:                " + geburtsdatum + "\n"
                        + "Erfassungsdatum:             " + erfassungsdatum + "\n"
                        + "eMail:                       " + eMail + "\n"
                        + "Kreditlimit:                 " + kreditlimit + "\n"
                        + "Straße Anschrift:            " + strasseAnschrift + "\n"
                        + "Hausnummer Anschrift:        " + hausnummerAnschrift + "\n"
                        + "PLZ Anschrift:               " + plzAnschrift + "\n"
                        + "Ort Anschrift:               " + ortAnschrift + "\n"
                        + "Straße LAnschrift:           " + strasseLieferanschrift + "\n"
                        + "Hausnummer LAnschrift:       " + hausnummerLieferanschrift + "\n"
                        + "PLZ LAnschrift:              " + plzLieferanschrift + "\n"
                        + "Ort LAnschrift:              " + ortLieferanschrift + "\n");

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
    private void jCB_TitelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_TitelActionPerformed
        if (jCB_Titel.getSelectedIndex() != 0) {
            jCB_Titel.setBackground(JCB_FARBE_STANDARD);
            jTF_Statuszeile.setText("");
        }
    }//GEN-LAST:event_jCB_TitelActionPerformed
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
        String eingabeGeburtsdatum = jFTF_Geburtsdatum.getText();
        String eingabeJahr = eingabeGeburtsdatum.substring(6, eingabeGeburtsdatum.length());
        if (!jFTF_Geburtsdatum.getText().equals("##.##.####")) {
            if (eingabeJahr.length() == 4 && (eingabeJahr.startsWith("20") || eingabeJahr.startsWith("19"))) {
                try {
                    Date tempAktDate = FORMAT.parse(aktuellesDatum);
                    Date tempGebuDate = FORMAT.parse(eingabeGeburtsdatum);
//                Date temp = FORMAT.parse("09.12.1996");
//                Date temp1 = FORMAT.parse("09.12.2014");
//                long achtzehn = temp1.getTime() - temp.getTime();

                    cal.setTime(tempAktDate);
                    cal.add(Calendar.YEAR, -18);
                    Date dateBefore18Years = cal.getTime();

//                System.out.println(temp.getTime());
//                System.out.println(temp1.getTime());
                    if (tempGebuDate.getTime() > tempAktDate.getTime()) {
                        String meldung = "Das eingegebene Geburtsdatum ist in der Zukunft! \nBitte geben Sie ein gültiges Geburtsdatm Datum ein. (z.B. 01.01.1980)";
                        String titel = "Fehlerhafte Eingabe";
                        JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                        jFTF_Geburtsdatum.requestFocusInWindow();
                        jFTF_Geburtsdatum.setText("##.##.####");
//                } else if (tempGebuDate.getTime() + achtzehn > tempAktDate.getTime()) {
                    } else if (tempGebuDate.getTime() > dateBefore18Years.getTime()) {
                        String meldung = "Der eingebene Geschäftspartner ist nicht volljährig!";
                        String titel = "Fehlerhafte Eingabe";
                        JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                        jFTF_Geburtsdatum.requestFocusInWindow();
                        jFTF_Geburtsdatum.setText("##.##.####");
                    } else {
                        jFTF_Geburtsdatum.setBackground(JTF_FARBE_STANDARD);
                    }
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                // eingabe Ungültig z.B. 19999;
                String meldung = "Das eingegebene Geburtsdatum ist in nicht gültig! \nBitte geben Sie ein gültiges Geburtsdatm Datum ein. (z.B. 01.01.1990)";
                String titel = "Fehlerhafte Eingabe";
                JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                jFTF_Geburtsdatum.requestFocusInWindow();
                jFTF_Geburtsdatum.setText("");
            }
        } else {
            //jFTF_Geburtsdatum.setText("01.01.1990");
            jFTF_Geburtsdatum.setValue(null);
        }
//        Lucas Code:
//        Date heute = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//        format.setLenient(false);
//        Date datum;
//        
//        SimpleDateFormat formatjahr = new SimpleDateFormat("yyyy");
//        SimpleDateFormat formatdatum = new SimpleDateFormat("dd.MM.yyyy");
//        int aktuellesjahr = Integer.parseInt(formatjahr.format(heute));
//        int geburtsjahr = 0;
//        Date geburtstag_Datum;
//        try {
//            datum = format.parse(jFTF_Geburtsdatum.getText());
//            geburtstag_Datum = formatdatum.parse(jFTF_Geburtsdatum.getText());
//            geburtsjahr = Integer.parseInt(formatjahr.format(geburtstag_Datum));
//            
//            if (datum.after(heute)) {
//                JOptionPane.showMessageDialog(rootPane, "Datum darf nicht in der "
//                        + "Zukunft liegen", "Fehler",
//                        JOptionPane.WARNING_MESSAGE);
//                jFTF_Geburtsdatum.requestFocusInWindow();
//                jFTF_Geburtsdatum.selectAll();
//            } else if (!(aktuellesjahr - geburtsjahr >= 18)) {
//                JOptionPane.showMessageDialog(rootPane, " Der Geschäftspartner muss mindestens"
//                        + " 18 Jahre alt "
//                        + " sein.", "Fehler ", JOptionPane.WARNING_MESSAGE);
//                jFTF_Geburtsdatum.requestFocusInWindow();
//                jFTF_Geburtsdatum.selectAll();
//            }
//            
//        } catch (ParseException e) {
//            JOptionPane.showMessageDialog(rootPane, "Gültiges Datum eingeben", "Fehler",
//                    JOptionPane.WARNING_MESSAGE);
//            jFTF_Geburtsdatum.requestFocusInWindow();
//            jFTF_Geburtsdatum.selectAll();
//        }

    }//GEN-LAST:event_jFTF_GeburtsdatumFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Wenn Eingabe korrekt ist, wird der Hintergrund in standard gefärbt.
     * Wenn nicht, wird eine Meldung ausgegeben.
     */
    private void jTF_eMailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_eMailFocusLost
        if (!jTF_eMail.getText().matches(PRUEFUNG_EMAIL)) {
            String meldung = "Die einegebene eMail ist nicht richtig! \nBitte geben Sie eine richtige eMail Adresse ein. (z.B. abc@abc.de)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_eMail.requestFocusInWindow();
            jTF_eMail.setText("");
        } else if (!jTF_eMail.getText().equals("")) {
            jTF_eMail.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_eMailFocusLost
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
    private void jTF_StrasseAnschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseAnschriftFocusLost
        if (!jTF_StrasseAnschrift.getText().equals("")) {
            jTF_StrasseAnschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_StrasseAnschriftFocusLost
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
    private void jTF_HausnummerAnschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerAnschriftFocusLost
        if (!jTF_HausnummerAnschrift.getText().matches(PRUEFUNG_HAUSNUMMER)) {
            String meldung = "Die eingegebene Hausnummer ist nicht richtig! \n Bitte geben Sie eine richtige Hausnummer ein. (z.B. 10A oder 10)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_HausnummerAnschrift.requestFocusInWindow();
            jTF_HausnummerAnschrift.setText("");
        } else if (!jTF_HausnummerAnschrift.getText().equals("")) {
            jTF_HausnummerAnschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_HausnummerAnschriftFocusLost
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
    private void jTF_PLZAnschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZAnschriftFocusLost
        if (!jTF_PLZAnschrift.getText().matches(PRUEFUNG_PLZ)) {
            String meldung = "Die eingegebene Postleitzahl ist nicht richtig! \n Bitte geben Sie eine richtige Postleitzahl ein. (z.B. 45968)";
            String titel = "Fehlerhafte Eingabe";
            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
            jTF_PLZAnschrift.requestFocusInWindow();
            jTF_PLZAnschrift.setText("");
        } else if (!jTF_PLZAnschrift.getText().equals("")) {
            jTF_PLZAnschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_PLZAnschriftFocusLost
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
    private void jTF_OrtAnschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtAnschriftFocusLost
        if (!jTF_OrtAnschrift.getText().equals("")) {
            jTF_OrtAnschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_OrtAnschriftFocusLost
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
    private void jTF_eMailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_eMailFocusGained
        jTF_eMail.selectAll();
    }//GEN-LAST:event_jTF_eMailFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_KreditlimitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_KreditlimitFocusGained
        jTF_Kreditlimit.selectAll();
    }//GEN-LAST:event_jTF_KreditlimitFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_StrasseAnschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseAnschriftFocusGained
        jTF_StrasseAnschrift.selectAll();
    }//GEN-LAST:event_jTF_StrasseAnschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_StrasseLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseLieferanschriftFocusGained
        jTF_StrasseLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_StrasseLieferanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_HausnummerAnschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerAnschriftFocusGained
        jTF_HausnummerAnschrift.selectAll();
    }//GEN-LAST:event_jTF_HausnummerAnschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_HausnummerLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerLieferanschriftFocusGained
        jTF_HausnummerLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_HausnummerLieferanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_PLZAnschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZAnschriftFocusGained
        jTF_PLZAnschrift.selectAll();
    }//GEN-LAST:event_jTF_PLZAnschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_PLZLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZLieferanschriftFocusGained
        jTF_PLZLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_PLZLieferanschriftFocusGained
    /*
     * Methode, um bei Eingabe des Feldes den Inhalt zu selektieren.
     */
    private void jTF_OrtAnschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtAnschriftFocusGained
        jTF_OrtAnschrift.selectAll();
    }//GEN-LAST:event_jTF_OrtAnschriftFocusGained
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
        this.setVisible(false);
        this.setzeFormularZurueck();
    }//GEN-LAST:event_formInternalFrameClosing

    
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird.
     * Es wird von der Guifactory die letzte aufgerufene Component abgefragt 
     * wodurch man die jetzige Component verlässt und zur übergebnen Component 
     * zurück kehrt.
     * @param evt 
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        c = null;   //Initialisierung der Componentspeichervariable
        //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
        c = this.factory.zurueckButton(); 
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        c.setVisible(true);// Übergebene Component wird sichtbar gemacht
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_Abbrechen;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JComboBox jCB_Titel;
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
    private javax.swing.JTextField jTF_Fax;
    private javax.swing.JTextField jTF_Geschaeftspartnernummer;
    private javax.swing.JTextField jTF_HausnummerAnschrift;
    private javax.swing.JTextField jTF_HausnummerLieferanschrift;
    private javax.swing.JTextField jTF_Kreditlimit;
    private javax.swing.JTextField jTF_Name;
    private javax.swing.JTextField jTF_OrtAnschrift;
    private javax.swing.JTextField jTF_OrtLieferanschrift;
    private javax.swing.JTextField jTF_PLZAnschrift;
    private javax.swing.JTextField jTF_PLZLieferanschrift;
    private javax.swing.JTextField jTF_Statuszeile;
    private javax.swing.JTextField jTF_StrasseAnschrift;
    private javax.swing.JTextField jTF_StrasseLieferanschrift;
    private javax.swing.JTextField jTF_Telefon;
    private javax.swing.JTextField jTF_Vorname;
    private javax.swing.JTextField jTF_eMail;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
