package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.*;
import DAO.DataAccessObject;
import Documents.UniversalDocument;
import Interfaces.InterfaceMainView;
import Interfaces.InterfaceViewsFunctionality;
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
 * GUI Klasse für Geschaeftspartner (GP) verwalten. Diese Klasse beinhaltet alle
 * Methoden, die benötigt werden, um einen GP anzulegen, einen angelegten GP zu
 * ändern sowie einen angelegten GP anzeigen zu lassen. Je nach dem von welchem
 * Button aus diese Klasse aufgerufen wird, passt sie sich entsprechend an und
 * ändert sich zum Beispiel in die Sicht GP ändern. Falls der Button GP anlegen
 * betätigt wird, ändert sich die Sicht in GP anlegen.
 *
 * @author Tahir
 *
 */
public class GeschaeftspartnerAnlegen extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {

    /**
     * Variable, die für die Navigation benoetigt wird, in ihr wird gespeichert,
     * welche View zuletzt geöffnet war.
     */
    private Component c;
    /**
     * Varibale für die GUI Factory
     */
    private GUIFactory factory;
    /**
     * Variable für die DAO
     */
    private DataAccessObject dao;
    /**
     * Variable für das Start Fenster. Es kann sein, dass sich ein Admin
     * anmeldet, dann waere unser StartFenster die StartAdmin. Falls sich ein
     * User anmeldet, ist unser StartFenster Start.
     */
    private InterfaceMainView hauptFenster;
    /**
     * Variable fuer die Rechnungsanschrift
     */
//    private Anschrift anschrift;
    /**
     * Variable fuer die Lieferanschrift
     */
//    private Anschrift lieferanschrift;
    /**
     * Variable fuer das aktuelle Datum
     */
    private String aktuellesDatum;
    /**
     * Varibale fue die GP nummer
     */
    private int geschaeftspartnerNr;
    /**
     * Arraylist, um fehlerhafte Componenten zu speichern
     */
    private ArrayList<Component> fehlerhafteComponenten;
    /**
     * Varible, die die Standard Farbe eine Combobox beinhaltet
     */
    private final Color JCB_FARBE_STANDARD = new Color(214, 217, 223);
    /**
     * Variable, die die Standard Farbe eines Textfieldes beinhaltet
     */
    private final Color JTF_FARBE_STANDARD = new Color(255, 255, 255);
    /**
     * Varibale, die die Farbe fuer fehlerhafte Componenten beinhaltet
     */
    private final Color FARBE_FEHLERHAFT = Color.YELLOW;
    /**
     * Varibalen fuer die Meldungen
     */
    private final String MELDUNG_PFLICHTFELDER_TITEL = "Felder nicht ausgefüllt";
    private final String MELDUNG_PFLICHTFELDER_TEXT = "Einige Felder wurden nicht ausgefüllt! Bitte füllen Sie diese aus!";
    private final String MELDUNG_AENDERUNGEN_SPEICHERN = "Möchten Sie die Änderungen speichern?";
    private final String RECHNUNGSANSCHRIFT = "Rechnungsanschrift";
    private final String LIEFERANSCHRIFT = "Lieferanschrift";
    private final String DEUTSCHLAND = "Deutschland";
    private final String GP_ANLEGEN = "Geschäftspartner anlegen";
    private final String GP_AENDERN = "Geschäftspartner ändern";
    private final String GP_ANZEIGEN = "Geschäftspartner anzeigen";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer den Preis
     */
    private final String PRUEFUNG_PREIS = "|(\\d*,?\\d{1,2})|(\\d{0,3}(\\.\\d{3})*,?\\d{1,2})";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer die Hausnummer
     */
    private final String PRUEFUNG_HAUSNUMMER = "|[1-9][0-9]{0,2}[a-zA-Z]?";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer die PLZ
     */
    private final String PRUEFUNG_PLZ = "|[0-9]{5}";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer die e-Mail
     */
    private final String PRUEFUNG_EMAIL = "|^[a-zA-Z0-9][\\w\\.-]*@(?:[a-zA-Z0-9][a-zA-Z0-9_-]+\\.)+[A-Z,a-z]{2,5}$";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer die Telefonnummer
     */
    private final String PRUEFUNG_TELEFON = "|^([+][ ]?[1-9][0-9][ ]?[-]?[ ]?|[(]?[0][ ]?)[0-9]{3,4}[-)/ ]?[ ]?[1-9][-0-9 ]{3,16}$";
    /**
     * Varibalen fuer den Format des Datums
     */
    private final SimpleDateFormat FORMAT;
    /**
     * Number Formatter wird benoetigt fuer das Parsen der Eingaben, sowie das
     * Anzeigen von Preisen
     */
    private NumberFormat nf;
    /**
     * Varibalen fuer das aktuelle Datum
     */
    private Calendar cal = Calendar.getInstance();
    /**
     * Varibalen fuer die Eingabe des Geburtsdatum
     */
    private Date eingabeGeburtsdatum;
    /**
     * Varibale, die die maximale Anzahl von fehlerhaften Componenten beinhaltet
     */
    private int anzahlFehlerhafterComponenten = 16;
    /**
     * Variable, um zu pruefen, ob alle Eingaben, wenn welche gemacht wurden, ok
     * sind
     */
    private boolean formularOK = true;

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     *
     * @param factory beinhaltet das factory Obejekt
     * @param mainView beinhaltet das Objekt des StartFenster
     */
    public GeschaeftspartnerAnlegen(GUIFactory factory, InterfaceMainView mainView) {
        initComponents();
        fehlerhafteComponenten = new ArrayList<>();
//        factory und die dao werden gesetzt
        this.factory = factory;
        this.dao = GUIFactory.getDAO();
        this.hauptFenster = mainView;
//        Format des Datums wird festgelegt
        FORMAT = new SimpleDateFormat("dd.MM.yyyy");
        FORMAT.setLenient(false);
//        anschrift = null;
//        lieferanschrift = null;
        nf = NumberFormat.getInstance();

        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
//      Documente der Textfelder werden gesetzt
        jTF_Name.setDocument(new UniversalDocument(" &/-.´", true));
        jTF_Vorname.setDocument(new UniversalDocument(" &/-.´", true));
        jTF_Telefon.setDocument(new UniversalDocument("0123456789/-+", false));
        jTF_Fax.setDocument(new UniversalDocument("0123456789/-+", false));
        jTF_EMail.setDocument(new UniversalDocument("0123456789@-_.", true));
        jTF_Kreditlimit.setDocument(new UniversalDocument("0123456789.,", false));
        jTF_StrasseRechnungsanschrift.setDocument(new UniversalDocument("-.´", true));
        jTF_StrasseLieferanschrift.setDocument(new UniversalDocument("-.´", true));
        jTF_HausnummerRechnungsanschrift.setDocument(new UniversalDocument("0123456789", true));
        jTF_HausnummerLieferanschrift.setDocument(new UniversalDocument("0123456789", true));
        jTF_PLZRechnungsanschrift.setDocument(new UniversalDocument("0123456789", false));
        jTF_PLZLieferanschrift.setDocument(new UniversalDocument("0123456789", false));
        jTF_OrtRechnungsanschrift.setDocument(new UniversalDocument("-", true));
        jTF_OrtLieferanschrift.setDocument(new UniversalDocument("-", true));
//      Aktuelles Datum wird geholt
        GregorianCalendar aktuellesDaum = new GregorianCalendar();
        DateFormat df_aktuellesDatum = DateFormat.getDateInstance(DateFormat.MEDIUM);    //05.12.2014     
        aktuellesDatum = df_aktuellesDatum.format(aktuellesDaum.getTime());
//      Erfassungsdatum wird auf das aktuelle Datum gesetzt
        DateFormat df_jTF = DateFormat.getDateInstance();
        df_jTF.setLenient(false);
        DateFormatter dform1 = new DateFormatter(df_jTF);
        dform1.setOverwriteMode(true);
        dform1.setAllowsInvalid(false);
        DefaultFormatterFactory dff1 = new DefaultFormatterFactory(dform1);
        jFTF_Erfassungsdatum.setFormatterFactory(dff1);
        jFTF_Erfassungsdatum.setText(aktuellesDatum);
//      Das aussehen des Textfeldes fuer die Eingabe des Geburtsdatum wird festgelegt
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
//        this.zuruecksetzen();
    }

    /**
     * Methode für die Überprüfung der Daten. Falls ein Textfeld nicht gefüllt
     * ist, wird sie der ArrayList für fehlerhafte Componenten hinzugefuegt.
     * Falls bei eine Compobox der selektierte Index auf 0 ("Bitte auswählen")
     * steht, wird diese ebenfalls in die ArrayList uebernommen
     */
    /*
     * Historie:
     * 02.12.2014   Sen     angelegt
     * 16.12.2014   Sen     ueberarbeitet
     */
    @Override
    public void ueberpruefen() {
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

    /**
     * Schnittstellen Methode, die die Eingaben zurücksetzt, beim Zurücksetzen
     * wird auch die Hintergrundfarbe zurückgesetzt.
     */
    /*
     * Historie:
     * 02.12.2014   Sen     angelegt
     * 11.12.2014   Sen     ueberarbeitet
     * 14.01.2015   Sen     ueberarbeitet
     */
    @Override
    public void zuruecksetzen() {
        try {
            geschaeftspartnerNr = this.dao.gibNaechsteGeschaeftpartnerID();
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
            jFTF_Erfassungsdatum.setText(aktuellesDatum);
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

//        fehlerhafteComponenten werden immer gelöscht, wenn das Formular zureckgesetzt wird!
            fehlerhafteComponenten.clear();
            formularOK = true;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Schnittstellen Methode, die bei focusloost ueberprueft, ob Eingaben
     * korrekt sind
     *
     * @param textfield textfield, von wo die Daten geprueft werden sollen
     * @param syntax Pruefsyntax(Regulaerer Ausdruck)
     * @param fehlermelgungtitel Titel der Fehlermeldung, die erzugt wird
     * @param fehlermeldung Text der Fehlermeldung
     */
    /*
     * Historie:
     * 02.12.2014   Sen     angelegt
     * 09.12.2014   Sen     ueberarbeitet
     */
    @Override
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax, String fehlermelgungtitel, String fehlermeldung) {
//            Prüfung, ob die Eingabe mit der Synstax passen
        if (!textfield.getText().matches(syntax)) {
//            Eingabe ist nicht ok, also wird die vairbale formular ok auf false gesetzt
            formularOK = false;
//            Eingabe nicht korrekt, also wird eine Fehlermeldung ausgegeben            
            JOptionPane.showMessageDialog(null, fehlermeldung, fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
//            Focus wird auf das Feld für die Eingabe des Einzelwertes gesetzt und der Eingabefeld wird auf leer gesetzt.
            textfield.requestFocusInWindow();
            textfield.setText("");
        } else if (!textfield.getText().equals("")) {
//            Eingabe ist ok, nun Pruefung ob etwas im Feld steht, falls ja wird Hitnergrund auf normal gestzt
            textfield.setBackground(JTF_FARBE_STANDARD);
        }
    }

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
    /*
     * Historie:
     * 05.12.2014   Sen     angelegt
     */
    @Override
    public void fehlEingabenMarkierung(ArrayList<Component> list, String fehlermelgungtitel, String fehlermeldung, Color farbe) {
//          fehlerhafteComponenten ist nicht leer (es sind fehlerhafte Componenten vorhanden)
//          eine Meldung wird ausgegeben  
        JOptionPane.showMessageDialog(null, fehlermeldung, fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
//          an die erste fehlerhafte Componenten wird der Focus gesetzt  
        list.get(0).requestFocusInWindow();
//          ueber die fehlerhaften Komponenten wird iteriert und bei allen fehlerhaften Componenten wird der Hintergrund in der fehlerhaften Farbe gefaerbt 
        for (int i = 0; i <= list.size() - 1; i++) {
            list.get(i).setBackground(farbe);
        }
//          ArrayList fue fehlerhafte Componenten wird geleert
        list.clear();
    }

    /**
     * Methode, die beim Schließen des Fenster aufgerufen wird.
     */
    /*
     * Historie:
     * 08.12.2014   Sen     angelegt
     */
    private void beendenEingabeNachfrage() {
//        varibablen fuer Titel und Text der erzeugten Meldung
        String meldung = "Möchten Sie die Eingaben verwerfen? Klicken Sie auf JA, wenn Sie die Eingaben verwerfen möchten.";
        String titel = "Achtung Eingaben gehen verloren!";
//        Falls Titel des Fensters GP anlegen oder GP aendern ist wird eine Nachfrage gemacht 
        if (this.getTitle().equals(GP_ANLEGEN)) {
//            zunaechst wird ueberprueft, ob alle Eingaben getaetigt wurden
            ueberpruefen();
//            Falls anzahl fehlerhafter Componenten kleiner ist als max Anzahl von fehlerhaften Componenten
//            heist dass, dass Eingaben getaetigt wurden
            if (fehlerhafteComponenten.size() < anzahlFehlerhafterComponenten) {
                int antwort = JOptionPane.showConfirmDialog(null, meldung, titel, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (antwort == JOptionPane.YES_OPTION) {
//            Also nachfrage
                    zuruecksetzen();
                    this.setVisible(false);
//                    jB_ZurueckActionPerformed(null);
                    zurueckInsHauptmenue();
                } else {
                    fehlerhafteComponenten.clear();
                }
            } else {
//            keine Eingaben getaetigt, direkt Fenster schließen 
                zuruecksetzen();
                this.setVisible(false);
//                hinzugefuegt 2.2.2015
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

        jToolBar1 = new javax.swing.JToolBar();
        jB_Zurueck = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_AnzeigenAEndern = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setTitle("Geschäftspartner anlegen");
        setPreferredSize(new java.awt.Dimension(680, 620));
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

        jB_AnzeigenAEndern.setText("Anzeige/Ändern");
        jB_AnzeigenAEndern.setActionCommand("Anzeigen/Ändern");
        jB_AnzeigenAEndern.setEnabled(false);
        jB_AnzeigenAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_AnzeigenAEndernActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_AnzeigenAEndern);

        jB_Loeschen.setText("Löschen");
        jB_Loeschen.setEnabled(false);
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

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Persönliche Daten:");

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

        jCB_Anrede.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "Firma", "Herr ", "Frau" }));
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
                .addContainerGap(25, Short.MAX_VALUE))
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
                .addContainerGap(134, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
     /**
     * Methode um die Lieferanschrift wie die Rechnungsanschrift zu setzen.
     */
    /*
     * Historie:
     * 13.12.2014   Sen     angelegt
     */
    private void jCHB_WieAnschriftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCHB_WieAnschriftActionPerformed
        String meldung = "Bitte geben Sie zunächst die komplette Anschrift ein.";
        String titel = "Unvollständige Eingabe";
//        Pruefung, ob Checkbox wie anschrift geklickt wurde
        if (jCHB_WieAnschrift.isSelected()) {
//             fals ja, wird geprueft, ob alle Felder der rechnungsanschrift belegt sind 
            if (!jTF_StrasseRechnungsanschrift.getText().equals("") && !jTF_HausnummerRechnungsanschrift.getText().equals("")
                    && !jTF_PLZRechnungsanschrift.getText().equals("") && !jTF_OrtRechnungsanschrift.getText().equals("")) {
//                 alle Felder belegt, also werden die Felder der Liefeanschrift wie die Felder
//                 der Rechnungsanschift gesetzt
                jTF_StrasseLieferanschrift.setText(jTF_StrasseRechnungsanschrift.getText());
                jTF_StrasseLieferanschrift.setEnabled(false);
                jTF_HausnummerLieferanschrift.setText(jTF_HausnummerRechnungsanschrift.getText());
                jTF_HausnummerLieferanschrift.setEnabled(false);
                jTF_PLZLieferanschrift.setText(jTF_PLZRechnungsanschrift.getText());
                jTF_PLZLieferanschrift.setEnabled(false);
                jTF_OrtLieferanschrift.setText(jTF_OrtRechnungsanschrift.getText());
                jTF_OrtLieferanschrift.setEnabled(false);
            } else {
//                nicht alle Felder sind belegt
//                Prufueung, welche nicht belegt ist und zuvor eine Meldung
//                Focus wird auf das nicht belegte Feld gesetzt
                JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
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
//            Checkbox wie anschrift nicht geklickt, also werden die Felder der Lieferschrift geleert
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
    /**
     * Methode, um den Typ des Geschäftspartners zu wählen. in standard gefärbt.
     */
    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */
    private void jCHB_KundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCHB_KundeActionPerformed
        if (jCHB_Kunde.isSelected()) {
            jCHB_Lieferant.setSelected(false);
        }
    }//GEN-LAST:event_jCHB_KundeActionPerformed
    /**
     * Methode, um den Typ des Geschäftspartners zu wählen. in standard gefärbt.
     *//*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */
    private void jCHB_LieferantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCHB_LieferantActionPerformed
        if (jCHB_Lieferant.isSelected()) {
            jCHB_Kunde.setSelected(false);
        }
    }//GEN-LAST:event_jCHB_LieferantActionPerformed
    /**
     * Methode für das Speichern der Daten. Falls die Sicht Aritkel anlegen
     * geoeffnet ist, wird eine Artikel angelegt. Ist Sicht Artikel aendern
     * geoffnet, wird der Artikel ueberschieben
     */
    /*
     * Historie:
     * 20.12.2014   Sen     angelegt
     * 28.12.2014   Sen     ueberarbeitet
     * 30.12.2014   Sen     Funtkion fuer Sicht GP anlegen implementiert
     * 10.01.2015   Sen     Funtkion fuer Sicht GP aendern implementiert
     * 04.02.2015   Sen     Fehler beseitigt, z.B. wenn falscher Preis eingegeben wird 
     *                      und direkt auf speichern geklickt wird
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed
//      falls das Formular ok ist, d.h. es sind vorher keine Fehlermeldungen enstanden, 
//      dann wird die Speichern Aktion durchgefuehrt  
        if (formularOK) {
//      zunaechst werden die Eingaben ueberprueft.    
            ueberpruefen();
            String typ;
            String titel;
            String name;
            String vorname;
            String telefon;
            String fax;
            String eMail;
            double kreditlimit;
            String strasseAnschrift;
            String strasseLieferanschrift;
            String hausnummerAnschrift;
            String hausnummerLieferanschrift;
            String plzAnschrift;
            String plzLieferanschrift;
            String ortAnschrift;
            String ortLieferanschrift;
            Anschrift rechnungsanschrift;
            Anschrift lieferanschrift;
//      falls fehlerhafteComponenten leer ist (es sind keine fehlerhaften Componenten verfuegbar), 
//      koennen wir sicher sein, dass alle Felder ausgefuellt sind, nun
//      werden die Eingaben in die entsprechenden Variablen gespeichert
            if (fehlerhafteComponenten.isEmpty()) {
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
                eMail = jTF_EMail.getText();
                strasseAnschrift = jTF_StrasseRechnungsanschrift.getText();
                hausnummerAnschrift = jTF_HausnummerRechnungsanschrift.getText();
                plzAnschrift = jTF_PLZRechnungsanschrift.getText();
                ortAnschrift = jTF_OrtRechnungsanschrift.getText();

                strasseLieferanschrift = jTF_StrasseLieferanschrift.getText();
                hausnummerLieferanschrift = jTF_HausnummerLieferanschrift.getText();
                plzLieferanschrift = jTF_PLZLieferanschrift.getText();
                ortLieferanschrift = jTF_OrtLieferanschrift.getText();

                try {
                    kreditlimit = nf.parse(jTF_Kreditlimit.getText()).doubleValue();
//                Pruefung, ob typ des GP ausgewaehlt ist
                    if (jCHB_Kunde.isSelected() || jCHB_Lieferant.isSelected()) {
//                Ueberpruefung in welche Sicht wir sind
                        if (this.getTitle().equals(GP_ANLEGEN)) {
//                      Sicht GP anlegen--> neuer GP wird in datenbank geschrieben
//                      zunaechst muss aber die rechnungsanschrift erstellt werden 
                            rechnungsanschrift = this.dao.erstelleAnschrift(RECHNUNGSANSCHRIFT, name, vorname,
                                    titel, strasseAnschrift, hausnummerAnschrift, plzAnschrift,
                                    ortAnschrift, DEUTSCHLAND, telefon, fax, eMail, eingabeGeburtsdatum);
//                      Pruefung, ob checkbox wie anschrift gesetzt
                            if (jCHB_WieAnschrift.isSelected()) {
//                      Falls checkbox wie anschrift geklickt ist, ist die Lieferanschrift = die rechnungsanschrift
                                lieferanschrift = rechnungsanschrift;
                            } else {
//                          nicht gesetzt neue Lieferanschrift erzeugen  
                                lieferanschrift = this.dao.erstelleAnschrift(LIEFERANSCHRIFT, name, vorname,
                                        titel, strasseLieferanschrift, hausnummerLieferanschrift, plzLieferanschrift,
                                        ortLieferanschrift, DEUTSCHLAND, telefon, fax, eMail, eingabeGeburtsdatum);
                            }
//                        Nun einen neuene GP mit beiden Anschriften erzeugen
                            this.dao.erstelleGeschaeftspartner(typ, lieferanschrift, rechnungsanschrift, kreditlimit);
                            zuruecksetzen();
                        } else {
//                  Sicht GP aendern ist geoffnet, also wird zunaechst der GP aus der Datenbank geladen.
                            long gpnr;
//                        Vergleichsanschriften werden erzeugt
                            Anschrift rechnungsanschriftNachher;
                            Anschrift lieferanschriftNachher;
//                        das eingebene geburtsdatum wird nochmals geholt
                            eingabeGeburtsdatum = FORMAT.parse(jFTF_Geburtsdatum.getText());
                            gpnr = nf.parse(jTF_GeschaeftspartnerID.getText()).longValue();
//                      GP aus Datenbank ist Variable gpVorher  
                            Geschaeftspartner gpVorher = this.dao.gibGeschaeftspartner(gpnr);
//                       vergleich GP erzeugen mit den Eingabedaten, doch zunaechst muessen die Anschriften erzeugt werden
                            Geschaeftspartner gpNachher;
//                      rechnungsanschrift nachher wird erzeugt
                            rechnungsanschriftNachher = new Rechnungsanschrift(name, vorname, titel, strasseAnschrift, hausnummerAnschrift,
                                    plzAnschrift, ortAnschrift, DEUTSCHLAND, telefon, fax, eMail, eingabeGeburtsdatum, gpVorher.getRechnungsadresse().getErfassungsdatum());
                            if (jCHB_WieAnschrift.isSelected()) {
                                lieferanschriftNachher = rechnungsanschriftNachher;
                            } else {
                                lieferanschriftNachher = new Lieferanschrift(name, vorname, titel, strasseLieferanschrift, hausnummerLieferanschrift,
                                        plzLieferanschrift, ortLieferanschrift, DEUTSCHLAND, telefon, fax, eMail, eingabeGeburtsdatum, gpVorher.getLieferadresse().getErfassungsdatum());
                            }
//                      Pruefung, elchen Typ der GP hat 
                            if (jCHB_Kunde.isSelected()) {
//                            GP ist Kunde, also Kunde erzeugen
                                gpNachher = new Kunde(lieferanschriftNachher, rechnungsanschriftNachher, kreditlimit, false);
                            } else {
//                            GP ist Lieferant, Lieferant erzeugen
                                gpNachher = new Lieferant(lieferanschriftNachher, rechnungsanschriftNachher, kreditlimit, false);
                            }
//                      Vergeleich, ob der GP sich geaendert hat
                            if (gpVorher.equals(gpNachher)) {
//                            Keine Veraenderung, Meldung
                                JOptionPane.showMessageDialog(null, "Es wurden keine Änderungen gemacht!", "Keine Änderungen", JOptionPane.INFORMATION_MESSAGE);
                            } else {
//                            Aenderung -> Abfrage, ob Änderungen gespeichert werden sollen
                                int antwort = JOptionPane.showConfirmDialog(null, MELDUNG_AENDERUNGEN_SPEICHERN, GP_AENDERN, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                            System.out.println(gpVorher.getLieferadresse());
//                            System.out.println(gpVorher.getRechnungsadresse());
//                            System.out.println("");
//                            System.out.println(gpNachher.getLieferadresse());
//                            System.out.println(gpNachher.getRechnungsadresse());
                                if (antwort == JOptionPane.YES_OPTION) {
//                              falls ja, gp in DP aendern
                                    this.dao.aendereGeschaeftspartner(gpVorher.getGeschaeftspartnerID(), jCHB_WieAnschrift.isSelected(),
                                            kreditlimit, name, vorname, titel, strasseAnschrift, hausnummerAnschrift,
                                            plzAnschrift, ortAnschrift, strasseLieferanschrift, hausnummerLieferanschrift,
                                            plzLieferanschrift, ortLieferanschrift, DEUTSCHLAND, telefon, fax, eMail, eingabeGeburtsdatum);
                                    zuruecksetzen(); // Formular zuruecksetzen
                                    this.setVisible(false); // diese Sicht ausblenden 
//                                Änderung am 03.02.2015
//                                jB_ZurueckActionPerformed(null); // Button Zurueck Action ausführen
                                    zurueckInsHauptmenue();
//                                ende Änderung
                                } else {
//                              Nein button wird geklickt, keine Aktion nur fehlerhafte Komponenten müssen geleert werden
                                    fehlerhafteComponenten.clear();
                                }
                            }

                        }
                    } else {
//                    Checkbox Kunde und Lieferant nicht ausgewaehlt-> Meldung das der Typ des GP ausgeaehlt sein muss
                        JOptionPane.showMessageDialog(null, "Bitte wählen Sie den Typ des Geschäftspartners.", "Unvollständige Eingabe", JOptionPane.ERROR_MESSAGE);
                        jCHB_Kunde.requestFocusInWindow();
                    }
//          Abfangen der Fehler
                } catch (ApplicationException | ParseException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            } else {
//            fehlerhafteComponenten ist nicht leer (es sind fehlerhafte Componenten vorhanden)
////          wird methode fuer das Markieren ausgefuehrt 
//            JOptionPane.showMessageDialog(null, MELDUNG_PFLICHTFELDER_TEXT, MELDUNG_PFLICHTFELDER_TITEL, JOptionPane.ERROR_MESSAGE);
////          an die erste fehlerhafte Componenten wird der Focus gesetzt  
//            fehlerhafteComponenten.get(0).requestFocusInWindow();
////          ueber die fehlerhaften Komponenten wird iteriert und bei allen fehlerhaften Componenten wird der Hintergrund in der fehlerhaften Farbe gefaerbt 
//            for (int i = 0; i <= fehlerhafteComponenten.size() - 1; i++) {
//                fehlerhafteComponenten.get(i).setBackground(FARBE_FEHLERHAFT);
//            }
////          ArrayList fue fehlerhafte Componenten wird geleert
//            fehlerhafteComponenten.clear();
                fehlEingabenMarkierung(fehlerhafteComponenten, MELDUNG_PFLICHTFELDER_TITEL, MELDUNG_PFLICHTFELDER_TEXT, FARBE_FEHLERHAFT);
            }
        }
//      Varibale formularOk muss wieder als true gesetzt werden, da wieder geprueft wird.    
        formularOK = true;
    }//GEN-LAST:event_jB_SpeichernActionPerformed
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jCB_AnredeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_AnredeActionPerformed
        if (jCB_Anrede.getSelectedIndex() != 0) {
            jCB_Anrede.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_AnredeActionPerformed
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_NameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_NameFocusLost
        if (!jTF_Name.getText().equals("")) {
            jTF_Name.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_NameFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_VornameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_VornameFocusLost
        if (!jTF_Vorname.getText().equals("")) {
            jTF_Vorname.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_VornameFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Wenn nicht, wird eine Meldung ausgegeben.
     *
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_TelefonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_TelefonFocusLost
//        if (!jTF_Telefon.getText().matches(PRUEFUNG_TELEFON)) {
//            String meldung = "Die eingegebene Telefonnummer ist nicht richtig! \n Bitte geben Sie eine richtige Telefonnummer ein. (z.B. 1234123)";
//            String titel = "Fehlerhafte Eingabe";
//            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//            jTF_Telefon.requestFocusInWindow();
//            jTF_Telefon.setText("");
//        } else if (!jTF_Telefon.getText().equals("")) {
//            jTF_Telefon.setBackground(JTF_FARBE_STANDARD);
//        }
        String meldung = "Die eingegebene Telefonnummer ist nicht richtig! \n Bitte geben Sie eine richtige Telefonnummer ein. (z.B. 017712312312)";
        String titel = "Fehlerhafte Eingabe";
        ueberpruefungVonFocusLost(jTF_Telefon, PRUEFUNG_TELEFON, titel, meldung);

    }//GEN-LAST:event_jTF_TelefonFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Wenn nicht, wird eine Meldung ausgegeben.
     *
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_FaxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_FaxFocusLost
//        if (!jTF_Fax.getText().matches(PRUEFUNG_TELEFON)) {
//            String meldung = "Die eingegebene Faxnummer ist nicht richtig! \n Bitte geben Sie eine richtige Faxnummer ein. (z.B. 1234123)";
//            String titel = "Fehlerhafte Eingabe";
//            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//            jTF_Fax.requestFocusInWindow();
//            jTF_Fax.setText("");
//        } else if (!jTF_Fax.getText().equals("")) {
//            jTF_Fax.setBackground(JTF_FARBE_STANDARD);
//        }

//        if (!textfield.getText().matches(syntax)) {
//            JOptionPane.showMessageDialog(null, fehlermeldung, fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
//            textfield.requestFocusInWindow();
//            textfield.setText("");
//        } else if (!textfield.getText().equals("")) {
//            textfield.setBackground(JTF_FARBE_STANDARD);
//        }
        String meldung = "Die eingegebene Faxnummer ist nicht richtig! \n Bitte geben Sie eine richtige Faxnummer ein. (z.B. 017712312312)";
        String titel = "Fehlerhafte Eingabe";
        ueberpruefungVonFocusLost(jTF_Fax, PRUEFUNG_TELEFON, titel, meldung);

    }//GEN-LAST:event_jTF_FaxFocusLost
    /**
     * Methode prüft, ob das eingebene Geburstdatum ok ist. Falls ja, wird der
     * Hintergrund in standard gefärbt. Wenn nicht, wird eine Meldung
     * ausgegeben.
     *
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     * 05.12.2014   Sen     ueberarbeitet
     */
    private void jFTF_GeburtsdatumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTF_GeburtsdatumFocusLost
        String meldung;
        String titel;
//        Pruefung, ob Eingabe getatigt wurde 
        if (!jFTF_Geburtsdatum.getText().equals("##.##.####")) {
//            heutiges Datum wird erzeugt
            Date heutigesDatum;
            try {
//                EingabeDatum wird in richtigen Format geparst
                eingabeGeburtsdatum = FORMAT.parse(jFTF_Geburtsdatum.getText());
                heutigesDatum = FORMAT.parse(aktuellesDatum);
//              der Tag vor 18 Jahren wird berechnet
                cal.setTime(heutigesDatum);
                cal.add(Calendar.YEAR, -18);
                Date tagVor18jahren = cal.getTime();
                cal.add(Calendar.YEAR, -82);
                Date tagVor100jahren = cal.getTime();
//              Pruefung, ob eingabe in der Zukunft
//                
//                    System.out.println(heutigesDatum);
//                    System.out.println(tagVor100jahren);
                if (eingabeGeburtsdatum.after(heutigesDatum)) {
//                  Datum ist in zukunft, formularOK wird auf false gesetzt  
                    formularOK = false;
//                    Falls ja Fehlermedung
                    meldung = "Das eingegebene Geburtsdatum ist in der Zukunft! \nBitte geben Sie ein gültiges Geburtsdatm Datum ein. (z.B. 01.01.1980)";
                    titel = "Fehlerhafte Eingabe";
                    JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                    jFTF_Geburtsdatum.requestFocusInWindow();
                    jFTF_Geburtsdatum.setValue(null);
//                    Pruefung ob GP volljaehrig
                } else if (eingabeGeburtsdatum.after(tagVor18jahren)) {
//                  GP ist unter 18, formularOK wird auf false gesetzt 
                    formularOK = false;
//                    Falls nicht, Fehlermeldung
                    meldung = "Der eingebene Geschäftspartner ist nicht volljährig!";
                    titel = "Fehlerhafte Eingabe";
                    JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                    jFTF_Geburtsdatum.requestFocusInWindow();
                    jFTF_Geburtsdatum.setValue(null);
                } else if (eingabeGeburtsdatum.before(tagVor100jahren)) {
//                  GP ist ueber 100 Jahre alt, formularOK wird auf false gesetzt 
                    formularOK = false;
//                    Falls nicht, Fehlermeldung
                    meldung = "Der eingebene Geschäftspartner ist über 100 Jahre alt!";
                    titel = "Fehlerhafte Eingabe";
                    JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                    jFTF_Geburtsdatum.requestFocusInWindow();
                    jFTF_Geburtsdatum.setValue(null);
                } else {
//                    Eingabe OK, Hintergrund wird in normal gesetzt
                    jFTF_Geburtsdatum.setBackground(JTF_FARBE_STANDARD);
                }
            } catch (ParseException e) {
//              Datum ist in ungueltig, formularOK wird auf false gesetzt 
                formularOK = false;
//                Parse Exception, da EingabeDatum nicht gueltig --> Fehlermeldung
                meldung = "Das eingegebene Datum ist nicht gülitig! \nBitte geben Sie ein gültiges Geburtsdatum ein. (z.B. 01.01.1980)";
                titel = "Fehlerhafte Eingabe";
                JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
                jFTF_Geburtsdatum.requestFocusInWindow();
                jFTF_Geburtsdatum.setValue(null);
            }
        }
    }//GEN-LAST:event_jFTF_GeburtsdatumFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_EMailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_EMailFocusLost
//        if (!jTF_EMail.getText().matches(PRUEFUNG_EMAIL)) {
//            String meldung = "Die einegebene eMail ist nicht richtig! \nBitte geben Sie eine richtige eMail Adresse ein. (z.B. abc@abc.de)";
//            String titel = "Fehlerhafte Eingabe";
//            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//            jTF_EMail.requestFocusInWindow();
//            jTF_EMail.setText("");
//        } else if (!jTF_EMail.getText().equals("")) {
//            jTF_EMail.setBackground(JTF_FARBE_STANDARD);
//        }

        //        if (!textfield.getText().matches(syntax)) {
//            JOptionPane.showMessageDialog(null, fehlermeldung, fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
//            textfield.requestFocusInWindow();
//            textfield.setText("");
//        } else if (!textfield.getText().equals("")) {
//            textfield.setBackground(JTF_FARBE_STANDARD);
//        }
        String meldung = "Die einegebene eMail ist nicht richtig! \nBitte geben Sie eine richtige eMail Adresse ein. (z.B. abc@abc.de)";
        String titel = "Fehlerhafte Eingabe";

        ueberpruefungVonFocusLost(jTF_EMail, PRUEFUNG_EMAIL, titel, meldung);


    }//GEN-LAST:event_jTF_EMailFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_KreditlimitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_KreditlimitFocusLost
//        Pruefung, ob Krediitlimit mit Synstax uebereinstimmt, falls nein wird eine Meldung ausgegeben
        if (!jTF_Kreditlimit.getText().matches(PRUEFUNG_PREIS)) {
//          Kreditlimit ist nicht ok, formularOK wird auf false gesetzt 
            formularOK = false;
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
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_StrasseRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseRechnungsanschriftFocusLost
        if (!jTF_StrasseRechnungsanschrift.getText().equals("")) {
            jTF_StrasseRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_StrasseRechnungsanschriftFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_StrasseLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseLieferanschriftFocusLost
        if (!jTF_StrasseLieferanschrift.getText().equals("")) {
            jTF_StrasseLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_StrasseLieferanschriftFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_HausnummerRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerRechnungsanschriftFocusLost
//        if (!jTF_HausnummerRechnungsanschrift.getText().matches(PRUEFUNG_HAUSNUMMER)) {
//            String meldung = "Die eingegebene Hausnummer ist nicht richtig! \n Bitte geben Sie eine richtige Hausnummer ein. (z.B. 10A oder 10)";
//            String titel = "Fehlerhafte Eingabe";
//            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//            jTF_HausnummerRechnungsanschrift.requestFocusInWindow();
//            jTF_HausnummerRechnungsanschrift.setText("");
//        } else if (!jTF_HausnummerRechnungsanschrift.getText().equals("")) {
//            jTF_HausnummerRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
//        }

        //        if (!textfield.getText().matches(syntax)) {
//            JOptionPane.showMessageDialog(null, fehlermeldung, fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
//            textfield.requestFocusInWindow();
//            textfield.setText("");
//        } else if (!textfield.getText().equals("")) {
//            textfield.setBackground(JTF_FARBE_STANDARD);
//        }
        String meldung = "Die eingegebene Hausnummer ist nicht richtig! \n Bitte geben Sie eine richtige Hausnummer ein. (z.B. 10A oder 10)";
        String titel = "Fehlerhafte Eingabe";
        ueberpruefungVonFocusLost(jTF_HausnummerRechnungsanschrift, PRUEFUNG_HAUSNUMMER, titel, meldung);


    }//GEN-LAST:event_jTF_HausnummerRechnungsanschriftFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_HausnummerLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerLieferanschriftFocusLost
//        if (!jTF_HausnummerLieferanschrift.getText().matches(PRUEFUNG_HAUSNUMMER)) {
//            String meldung = "Die eingegebene Hausnummer ist nicht richtig! \n Bitte geben Sie eine richtige Hausnummer ein. (z.B. 10A oder 10)";
//            String titel = "Fehlerhafte Eingabe";
//            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//            jTF_HausnummerLieferanschrift.requestFocusInWindow();
//            jTF_HausnummerLieferanschrift.setText("");
//        } else if (!jTF_HausnummerLieferanschrift.getText().equals("")) {
//            jTF_HausnummerLieferanschrift.setBackground(JTF_FARBE_STANDARD);
//        }

        String meldung = "Die eingegebene Hausnummer ist nicht richtig! \n Bitte geben Sie eine richtige Hausnummer ein. (z.B. 10A oder 10)";
        String titel = "Fehlerhafte Eingabe";
        ueberpruefungVonFocusLost(jTF_HausnummerLieferanschrift, PRUEFUNG_HAUSNUMMER, titel, meldung);
    }//GEN-LAST:event_jTF_HausnummerLieferanschriftFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_PLZRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZRechnungsanschriftFocusLost
//        if (!jTF_PLZRechnungsanschrift.getText().matches(PRUEFUNG_PLZ)) {
//            String meldung = "Die eingegebene Postleitzahl ist nicht richtig! \n Bitte geben Sie eine richtige Postleitzahl ein. (z.B. 45968)";
//            String titel = "Fehlerhafte Eingabe";
//            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//            jTF_PLZRechnungsanschrift.requestFocusInWindow();
//            jTF_PLZRechnungsanschrift.setText("");
//        } else if (!jTF_PLZRechnungsanschrift.getText().equals("")) {
//            jTF_PLZRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
//        }
        String meldung = "Die eingegebene Postleitzahl ist nicht richtig! \n Bitte geben Sie eine richtige Postleitzahl ein. (z.B. 45968)";
        String titel = "Fehlerhafte Eingabe";
        ueberpruefungVonFocusLost(jTF_PLZRechnungsanschrift, PRUEFUNG_PLZ, titel, meldung);
    }//GEN-LAST:event_jTF_PLZRechnungsanschriftFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_PLZLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZLieferanschriftFocusLost
//        if (!jTF_PLZLieferanschrift.getText().matches(PRUEFUNG_PLZ)) {
//            String meldung = "Die eingegebene Postleitzahl ist nicht richtig! \n Bitte geben Sie eine richtige Postleitzahl ein. (z.B. 45968)";
//            String titel = "Fehlerhafte Eingabe";
//            JOptionPane.showMessageDialog(null, meldung, titel, JOptionPane.ERROR_MESSAGE);
//            jTF_PLZLieferanschrift.requestFocusInWindow();
//            jTF_PLZLieferanschrift.setText("");
//        } else if (!jTF_PLZLieferanschrift.getText().equals("")) {
//            jTF_PLZLieferanschrift.setBackground(JTF_FARBE_STANDARD);
//        }

        String meldung = "Die eingegebene Postleitzahl ist nicht richtig! \n Bitte geben Sie eine richtige Postleitzahl ein. (z.B. 45968)";
        String titel = "Fehlerhafte Eingabe";
        ueberpruefungVonFocusLost(jTF_PLZLieferanschrift, PRUEFUNG_PLZ, titel, meldung);
    }//GEN-LAST:event_jTF_PLZLieferanschriftFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_OrtRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtRechnungsanschriftFocusLost
        if (!jTF_OrtRechnungsanschrift.getText().equals("")) {
            jTF_OrtRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_OrtRechnungsanschriftFocusLost
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    private void jTF_OrtLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtLieferanschriftFocusLost
        if (!jTF_OrtLieferanschrift.getText().equals("")) {
            jTF_OrtLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_OrtLieferanschriftFocusLost
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_NameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_NameFocusGained
        jTF_Name.selectAll();
    }//GEN-LAST:event_jTF_NameFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_VornameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_VornameFocusGained
        jTF_Vorname.selectAll();
    }//GEN-LAST:event_jTF_VornameFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_TelefonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_TelefonFocusGained
        jTF_Telefon.selectAll();
    }//GEN-LAST:event_jTF_TelefonFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_FaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_FaxFocusGained
        jTF_Fax.selectAll();
    }//GEN-LAST:event_jTF_FaxFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_EMailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_EMailFocusGained
        jTF_EMail.selectAll();
    }//GEN-LAST:event_jTF_EMailFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_KreditlimitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_KreditlimitFocusGained
        jTF_Kreditlimit.selectAll();
    }//GEN-LAST:event_jTF_KreditlimitFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_StrasseRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseRechnungsanschriftFocusGained
        jTF_StrasseRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_StrasseRechnungsanschriftFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_StrasseLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseLieferanschriftFocusGained
        jTF_StrasseLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_StrasseLieferanschriftFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_HausnummerRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerRechnungsanschriftFocusGained
        jTF_HausnummerRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_HausnummerRechnungsanschriftFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_HausnummerLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerLieferanschriftFocusGained
        jTF_HausnummerLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_HausnummerLieferanschriftFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_PLZRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZRechnungsanschriftFocusGained
        jTF_PLZRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_PLZRechnungsanschriftFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_PLZLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZLieferanschriftFocusGained
        jTF_PLZLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_PLZLieferanschriftFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_OrtRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtRechnungsanschriftFocusGained
        jTF_OrtRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_OrtRechnungsanschriftFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void jTF_OrtLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtLieferanschriftFocusGained
        jTF_OrtLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_OrtLieferanschriftFocusGained
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        beendenEingabeNachfrage();
    }//GEN-LAST:event_formInternalFrameClosing

    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt Event, der ausgeloest wird
     */
    /*
     * Historie:
     * 29.11.2014   Terrasi     angelegt
     * 04.02.2015   Sen         Fehler beseitigt, z.B. wenn falscher Preis eingegeben wird 
     *                          und direkt auf zurueck geklickt wird
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
//        c = null;   //Initialisierung der Componentspeichervariable
//        //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
//        c = this.factory.zurueckButton();
//        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
//        c.setVisible(true);// Übergebene Component wird sichtbar gemacht
////        this.setzeFormularZurueck();

//          Falls formular ok ist, wird die Zurueck Aktion durchgefuehrt 
        if (formularOK) {
            if (this.getTitle().equals(GP_ANLEGEN)) {
                beendenEingabeNachfrage();
            } else if (this.getTitle().equals(GP_AENDERN)) {
//      zunaechst werden die Eingaben ueberprueft.    
                ueberpruefen();
                String typ;
                String titel;
                String name;
                String vorname;
                String telefon;
                String fax;
                String eMail;
                double kreditlimit;
                String strasseAnschrift;
                String strasseLieferanschrift;
                String hausnummerAnschrift;
                String hausnummerLieferanschrift;
                String plzAnschrift;
                String plzLieferanschrift;
                String ortAnschrift;
                String ortLieferanschrift;
                Anschrift rechnungsanschrift;
                Anschrift lieferanschrift;
//      falls fehlerhafteComponenten leer ist (es sind keine fehlerhaften Componenten verfuegbar), 
//      koennen wir sicher sein, dass alle Felder ausgefuellt sind, nun
//      werden die Eingaben in die entsprechenden Variablen gespeichert
                if (fehlerhafteComponenten.isEmpty()) {
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
                    eMail = jTF_EMail.getText();
                    strasseAnschrift = jTF_StrasseRechnungsanschrift.getText();
                    hausnummerAnschrift = jTF_HausnummerRechnungsanschrift.getText();
                    plzAnschrift = jTF_PLZRechnungsanschrift.getText();
                    ortAnschrift = jTF_OrtRechnungsanschrift.getText();

                    strasseLieferanschrift = jTF_StrasseLieferanschrift.getText();
                    hausnummerLieferanschrift = jTF_HausnummerLieferanschrift.getText();
                    plzLieferanschrift = jTF_PLZLieferanschrift.getText();
                    ortLieferanschrift = jTF_OrtLieferanschrift.getText();

                    try {
                        kreditlimit = nf.parse(jTF_Kreditlimit.getText()).doubleValue();
//                Pruefung, ob typ des GP ausgewaehlt ist
                        if (jCHB_Kunde.isSelected() || jCHB_Lieferant.isSelected()) {
//                  Sicht GP aendern ist geoffnet, also wird zunaechst der GP aus der Datenbank geladen.
                            long gpnr;
//                        Vergleichsanschriften werden erzeugt
                            Anschrift rechnungsanschriftNachher;
                            Anschrift lieferanschriftNachher;
//                        das eingebene geburtsdatum wird nochmals geholt
                            eingabeGeburtsdatum = FORMAT.parse(jFTF_Geburtsdatum.getText());
                            gpnr = nf.parse(jTF_GeschaeftspartnerID.getText()).longValue();
//                      GP aus Datenbank ist Variable gpVorher  
                            Geschaeftspartner gpVorher = this.dao.gibGeschaeftspartner(gpnr);
//                       vergleich GP erzeugen mit den Eingabedaten, doch zunaechst muessen die Anschriften erzeugt werden
                            Geschaeftspartner gpNachher;
//                      rechnungsanschrift nachher wird erzeugt
                            rechnungsanschriftNachher = new Rechnungsanschrift(name, vorname, titel, strasseAnschrift, hausnummerAnschrift,
                                    plzAnschrift, ortAnschrift, DEUTSCHLAND, telefon, fax, eMail, eingabeGeburtsdatum, gpVorher.getRechnungsadresse().getErfassungsdatum());
                            if (jCHB_WieAnschrift.isSelected()) {
                                lieferanschriftNachher = rechnungsanschriftNachher;
                            } else {
                                lieferanschriftNachher = new Lieferanschrift(name, vorname, titel, strasseLieferanschrift, hausnummerLieferanschrift,
                                        plzLieferanschrift, ortLieferanschrift, DEUTSCHLAND, telefon, fax, eMail, eingabeGeburtsdatum, gpVorher.getLieferadresse().getErfassungsdatum());
                            }
//                      Pruefung, elchen Typ der GP hat 
                            if (jCHB_Kunde.isSelected()) {
//                            GP ist Kunde, also Kunde erzeugen
                                gpNachher = new Kunde(lieferanschriftNachher, rechnungsanschriftNachher, kreditlimit, false);
                            } else {
//                            GP ist Lieferant, Lieferant erzeugen
                                gpNachher = new Lieferant(lieferanschriftNachher, rechnungsanschriftNachher, kreditlimit, false);
                            }
//                      Vergeleich, ob der GP sich geaendert hat
                            if (gpVorher.equals(gpNachher)) {
                                this.setVisible(false); // diese Sicht ausblenden 
                                zurueckInsHauptmenue();
                            } else {
//                            Aenderung -> Abfrage, ob Änderungen gespeichert werden sollen
                                int antwort = JOptionPane.showConfirmDialog(null, MELDUNG_AENDERUNGEN_SPEICHERN, GP_AENDERN, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (antwort == JOptionPane.YES_OPTION) {
//                              falls ja, gp in DP aendern
                                    this.dao.aendereGeschaeftspartner(gpVorher.getGeschaeftspartnerID(), jCHB_WieAnschrift.isSelected(),
                                            kreditlimit, name, vorname, titel, strasseAnschrift, hausnummerAnschrift,
                                            plzAnschrift, ortAnschrift, strasseLieferanschrift, hausnummerLieferanschrift,
                                            plzLieferanschrift, ortLieferanschrift, DEUTSCHLAND, telefon, fax, eMail, eingabeGeburtsdatum);
                                    this.setVisible(false); // diese Sicht ausblenden 
                                    zurueckInsHauptmenue();
                                } else {
                                    this.setVisible(false); // diese Sicht ausblenden 
                                    zurueckInsHauptmenue();
                                }
                            }
                        } else {
                            //                     etwas ist nicht gleich
//                     Abfrage, ob Änderungen gespeichert werden sollen
                            int antwort = JOptionPane.showConfirmDialog(null, MELDUNG_AENDERUNGEN_SPEICHERN, GP_AENDERN, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (antwort == JOptionPane.YES_OPTION) {
//                    Checkbox Kunde und Lieferant nicht ausgewaehlt-> Meldung das der Typ des GP ausgeaehlt sein muss
                                JOptionPane.showMessageDialog(null, "Bitte wählen Sie den Typ des Geschäftspartners.", "Unvollständige Eingabe", JOptionPane.ERROR_MESSAGE);
                                jCHB_Kunde.requestFocusInWindow();
                            } else {
//                    zuruecksetzen(); // Formular zuruecksetzen
                                this.setVisible(false); // diese Sicht ausblenden 
                                zurueckInsHauptmenue();
                            }
                        }
//          Abfangen der Fehler
                    } catch (ApplicationException | ParseException | NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
//                     etwas ist nicht gleich
//                     Abfrage, ob Änderungen gespeichert werden sollen
                    int antwort = JOptionPane.showConfirmDialog(null, MELDUNG_AENDERUNGEN_SPEICHERN, GP_AENDERN, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (antwort == JOptionPane.YES_OPTION) {
                        fehlEingabenMarkierung(fehlerhafteComponenten, MELDUNG_PFLICHTFELDER_TITEL, MELDUNG_PFLICHTFELDER_TEXT, FARBE_FEHLERHAFT);
                    } else {
//                    zuruecksetzen(); // Formular zuruecksetzen
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
    /**
     * Methode, die das zurueckspringen in das Hauptmenue ermoeglicht.
     */
    /*
     * Historie:
     * 03.02.2015   Sen     angelegt
     */
    private void zurueckInsHauptmenue() {
        c = null;   //Initialisierung der Componentspeichervariable
        //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
        c = this.factory.zurueckButton();
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        c.setVisible(true);// Übergebene Component wird sichtbar gemacht
//        this.setzeFormularZurueck();
    }

    /**
     * Methode fuer das Loeschen eines GP.
     *
     * @param evt Event der ausgeloest wird
     */
    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     * 04.02.2015   Sen     Fehler beseitigt, z.B. wenn falscher Preis eingegeben wird 
     *                      und direkt auf loeschen geklickt wird
     */
    private void jB_LoeschenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_LoeschenActionPerformed
//        Variablen fuer Meldung und titel
        String meldung = "Soll der Geschäftspartner wirklich gelöscht werden?";
        String titel = "Geschäftspartner löschen";
//      erst wenn das Formular ok ist, wird die Loeschen Aktion durchgefuehrt   
        if (formularOK) {
            try {
//          GP wird aus Datenbank geladen, 
//          Nachfrage, ob er wirklick geloescht werden soll, 
//          falls ja wird er geloescht
                long gpnr = nf.parse(jTF_GeschaeftspartnerID.getText()).longValue();
                int antwort = JOptionPane.showConfirmDialog(null, meldung, titel, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (antwort == JOptionPane.YES_OPTION) {
                    this.dao.loescheGeschaeftspartner(gpnr);
//                jB_ZurueckActionPerformed(evt);
                    zurueckInsHauptmenue();
                }
            } catch (ParseException | ApplicationException | NullPointerException ex) {
                System.out.println(ex.getMessage());
            }
        }
//      Variable wird wieder auf true gesetzt, da nochmals eine Pruefung stattfindet 
        formularOK = true;
    }//GEN-LAST:event_jB_LoeschenActionPerformed
    /**
     * Methode fuer den Aufruf der Suche Maske
     *
     * @param evt Event der ausgeloest wird
     */
    /*
     * Historie:
     * 15.01.2015   Sen     angelegt
     * 17.01.2015   Schulz  Suchbutton event eingefügt
     */
    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        this.hauptFenster.rufeSuche(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed
    /**
     * Methode fuer den Button Anzeigen/AEnden
     *
     * @param evt Event der ausgeloest wird
     */
    /*
     * Historie:
     * 16.01.2015   Sen     angelegt
     */
    private void jB_AnzeigenAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AnzeigenAEndernActionPerformed
//        if (this.getTitle().equals(GP_AENDERN)) {
//            setzeFormularInGPAnzeigenFuerButton();
//        } else 
        if (this.getTitle().equals(GP_ANZEIGEN)) {
            setzeFormularInGPAEndernFuerButton();
        }
    }//GEN-LAST:event_jB_AnzeigenAEndernActionPerformed
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP nummer
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_GeschaeftspartnerID() {
        return jTF_GeschaeftspartnerID;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return checkbox der GP kunde
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JCheckBox gibjCHB_Kunde() {
        return jCHB_Kunde;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return checkbox der GP lieferant
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JCheckBox gibjCHB_Lieferant() {
        return jCHB_Lieferant;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return checkbox der GP anrede
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JComboBox gibjCB_Anrede() {
        return jCB_Anrede;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP name
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_Name() {
        return jTF_Name;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP vorname
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_Vorname() {
        return jTF_Vorname;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP telefon
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_Telefon() {
        return jTF_Telefon;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP fax
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_Fax() {
        return jTF_Fax;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return jftextfield der GP geburstdatum
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JFormattedTextField gibjFTF_Geburtsdatum() {
        return jFTF_Geburtsdatum;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return jftextfield der GP geburstdatum
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JFormattedTextField gibjFTF_Erfassungsdatum() {
        return jFTF_Erfassungsdatum;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP email
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_EMail() {
        return jTF_EMail;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP kreditlimit
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_Kreditlimit() {
        return jTF_Kreditlimit;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return checkbox der GP wie anschrift
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JCheckBox gibjCHB_WieAnschrift() {
        return jCHB_WieAnschrift;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP strasse rechnungsanschrift
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_StrasseRechnungsanschrift() {
        return jTF_StrasseRechnungsanschrift;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP hausnummer rechnungsanschrift
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_HausnummerRechnungsanschrift() {
        return jTF_HausnummerRechnungsanschrift;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP plz rechnungsanschrift
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_PLZRechnungsanschrift() {
        return jTF_PLZRechnungsanschrift;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP ort rechnungsanschrift
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_OrtRechnungsanschrift() {
        return jTF_OrtRechnungsanschrift;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP strasse lieferanschrift
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_StrasseLieferanschrift() {
        return jTF_StrasseLieferanschrift;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP hausnummer lieferanschrift
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_HausnummerLieferanschrift() {
        return jTF_HausnummerLieferanschrift;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP plz lieferanschrift
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_PLZLieferanschrift() {
        return jTF_PLZLieferanschrift;
    }

    /**
     * getter Methode fuer die Sicht GP aendern Einstieg
     *
     * @return textfield der GP ort lieferanschrift
     */
    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    public JTextField gibjTF_OrtLieferanschrift() {
        return jTF_OrtLieferanschrift;
    }

    /**
     * Methode, die das Formular in die Sicht GP anlegen aendert
     */
    /*
     * Historie:
     * 07.01.2015   Sen     angelegt
     /* 08.01.2015 Terrasi, implemtiereung der Referenzvariable "hauptfenster"
     */
    public void setzeFormularInGPAnlegen() {
        zuruecksetzen();
        this.setTitle(GP_ANLEGEN);
//      benoetigte felder werden auf enabled true gesetzt
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
//      buttons werden gestzt
        jB_Speichern.setEnabled(true);
        jB_AnzeigenAEndern.setEnabled(false);
        jB_AnzeigenAEndern.setText("Anzeigen/Ändern");
        jB_Loeschen.setEnabled(false);

        this.hauptFenster.setComponent(this);//Übergibt der Referenz des Hauptfensters das Internaframe
    }

    /**
     * Methode, die das Formular in die Sicht GP aendern aendert
     */
    /*
     * Historie:
     * 07.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    public void setzeFormularInGPAEndern() {
        zuruecksetzen();
        setzeFormularInGPAEndernFuerButton();
        this.hauptFenster.setComponent(this);//Übergibt der Referenz des Hauptfensters das Internaframe
    }

    /**
     * Methode wird aufgerufen, wenn auf Button Anzeigen/Aendern geklickt wird,
     * das Formular wird nicht zurueckgesetzt, deswegen musste eine extra
     * Methode geschrieben werden
     */
    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    private void setzeFormularInGPAEndernFuerButton() {
        this.setTitle(GP_AENDERN);

        jCHB_Kunde.setEnabled(false);
        jCHB_Lieferant.setEnabled(false);
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

        if (jCHB_WieAnschrift.isSelected()) {
            jTF_StrasseLieferanschrift.setEnabled(false);
            jTF_HausnummerLieferanschrift.setEnabled(false);
            jTF_PLZLieferanschrift.setEnabled(false);
            jTF_OrtLieferanschrift.setEnabled(false);
        } else {
            jTF_StrasseLieferanschrift.setEnabled(true);
            jTF_HausnummerLieferanschrift.setEnabled(true);
            jTF_PLZLieferanschrift.setEnabled(true);
            jTF_OrtLieferanschrift.setEnabled(true);
        }

        jB_Speichern.setEnabled(true);
        jB_AnzeigenAEndern.setEnabled(false);
        jB_AnzeigenAEndern.setText("Anzeigen");
        jB_Loeschen.setEnabled(true);
    }

    /**
     * Methode, die das Formular in die Sicht GP anzeigen aendert
     */
    /*
     * Historie:
     * 07.01.2015   Sen     angelegt
     * 08.01.2015 Terrasi, implemtiereung der Referenzvariable "hauptfenster"
     */
    public void setzeFormularInGPAnzeigen() {
        zuruecksetzen();
        setzeFormularInGPAnzeigenFuerButton();
        this.hauptFenster.setComponent(this);//Übergibt der Referenz des Hauptfensters das Internaframe
    }

    /**
     * Methode wird aufgerufen, wenn auf Button Anzeigen/Aendern geklickt wird,
     * das Formular wird nicht zurueckgesetzt, deswegen musste eine extra
     * Methode geschrieben werden
     */
    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    private void setzeFormularInGPAnzeigenFuerButton() {
        this.setTitle(GP_ANZEIGEN);

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

        jB_Speichern.setEnabled(false);
        jB_AnzeigenAEndern.setEnabled(true);
        jB_AnzeigenAEndern.setText("Ändern");
        jB_Loeschen.setEnabled(false);

    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Schulz, Anlegen*/
    /*----------------------------------------------------------*/
    /**
     * Methode fuer das Setzen alle Anschriftsfelder
     *
     * @param anschriftDaten Die zu übergebenen Daten
     *
     */
    /* Historie:
     * 17.01.2015   Schulz  Anlegen
     * 18.01.2015   Sen     uberarbeitet
     */
    public void setzeAnschrift(Anschrift anschriftDaten) {
        this.jTF_StrasseRechnungsanschrift.setText(anschriftDaten.getStrasse());
        this.jTF_StrasseRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        this.jTF_HausnummerRechnungsanschrift.setText(anschriftDaten.getHausnummer());
        this.jTF_HausnummerRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        this.jTF_PLZRechnungsanschrift.setText(anschriftDaten.getPLZ());
        this.jTF_PLZRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        this.jTF_OrtRechnungsanschrift.setText(anschriftDaten.getOrt());
        this.jTF_OrtRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
    }

    /**
     * setzeMethode, die aus der Suche aufgerufen wird. Es wird die Sicht GP
     * Anzeigen aufgerufen und der gesuchte GP angezeigt
     *
     * @param gp artikel aus der Suche
     */
    /*
     * Historie:
     * 17.01.2015   Sen     angelegt
     */
    public void zeigeGPausSucheAN(Geschaeftspartner gp) {
        setzeFormularInGPAnzeigen();
        Anschrift la = gp.getLieferadresse();
        Anschrift ra = gp.getRechnungsadresse();
        String typ = gp.getTyp();
        if (typ.equals("Kunde")) {
            jCHB_Kunde.setSelected(true);
        } else {
            jCHB_Lieferant.setSelected(true);
        }
        jTF_GeschaeftspartnerID.setText("" + gp.getGeschaeftspartnerID());
        jCB_Anrede.setSelectedItem(ra.getTitel());
        jTF_Name.setText(ra.getName());
        jTF_Vorname.setText(ra.getVorname());
        jTF_Telefon.setText(ra.getTelefon());
        jTF_Fax.setText(ra.getFAX());
        jFTF_Geburtsdatum.setText(gibDatumAusDatenbank(la.getGeburtsdatum()));
        jFTF_Erfassungsdatum.setText(gibDatumAusDatenbank(la.getErfassungsdatum()));
        jTF_EMail.setText(ra.getEmail());
        jTF_Kreditlimit.setText("" + nf.format(gp.getKreditlimit()));
        jTF_StrasseRechnungsanschrift.setText(ra.getStrasse());
        jTF_HausnummerRechnungsanschrift.setText(ra.getHausnummer());
        jTF_PLZRechnungsanschrift.setText(ra.getPLZ());
        jTF_OrtRechnungsanschrift.setText(ra.getOrt());
        if (ra.getStrasse().equals(la.getStrasse())
                && ra.getHausnummer().equals(la.getHausnummer())
                && ra.getOrt().equals(la.getOrt())
                && ra.getPLZ().equals(la.getPLZ())) {
            jCHB_WieAnschrift.setSelected(true);
            jTF_StrasseLieferanschrift.setText(ra.getStrasse());
            jTF_StrasseLieferanschrift.setEnabled(false);
            jTF_HausnummerLieferanschrift.setText(ra.getHausnummer());
            jTF_HausnummerLieferanschrift.setEnabled(false);
            jTF_PLZLieferanschrift.setText(ra.getPLZ());
            jTF_PLZLieferanschrift.setEnabled(false);
            jTF_OrtLieferanschrift.setText(ra.getOrt());
            jTF_OrtLieferanschrift.setEnabled(false);
        } else {
            jCHB_WieAnschrift.setSelected(false);
            jTF_StrasseLieferanschrift.setText(la.getStrasse());
            jTF_HausnummerLieferanschrift.setText(la.getHausnummer());
            jTF_PLZLieferanschrift.setText(la.getPLZ());
            jTF_OrtLieferanschrift.setText(la.getOrt());
        }
    }

    /**
     * Methode, die aus dem uebergebenem Date Objekt ein String mit dem
     * richtigen Format zureueckgibt.
     *
     * @param date date objekt, welches uebergeben wird
     * @return String Datum im richtigen Format (z.B. 01.01.2014)
     */
    /*
     * Historie:
     * 14.12.2014   Sen     angelegt
     */
    private String gibDatumAusDatenbank(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
//        tag, monat, jahr werden aus dem Date Objekt herausgenommen
        int tag = cale.get(Calendar.DAY_OF_MONTH);
        int mon = cale.get(Calendar.MONTH);
//        auf den monat wird eine 1 addiert, da sie von 0 anfaengt, d.h 0 = januar
        mon = mon + 1;
        int jahr = cale.get(Calendar.YEAR);
        String tagAlsString;
        String monatAlsString;
//        Preufung ob Tag einstellig
        if (tag < 10) {
//            Falls ja, wird eine 0 davorgetan also wird aus 8 = 08
            tagAlsString = "0" + tag;
        } else {
//            wenn nicht, wird es in ein String gepackt
            tagAlsString = "" + tag;
        }
        if (mon < 10) {
            monatAlsString = "0" + mon;
        } else {
            monatAlsString = "" + mon;
        }
//      Ausgabe String wird erzeugen
        String ausgabeDatum = tagAlsString + "." + monatAlsString + "." + jahr;
        return ausgabeDatum;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_AnzeigenAEndern;
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
    private javax.swing.JTextField jTF_StrasseLieferanschrift;
    private javax.swing.JTextField jTF_StrasseRechnungsanschrift;
    private javax.swing.JTextField jTF_Telefon;
    private javax.swing.JTextField jTF_Vorname;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
