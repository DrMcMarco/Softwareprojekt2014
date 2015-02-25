package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.*;
import DAO.DataAccessObject;
import Documents.UniversalDocument;
import Interfaces.SchnittstelleHauptfenster;
import Interfaces.SchnittstelleFensterFunktionen;
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
public class GeschaeftspartnerAnlegen extends javax.swing.JInternalFrame implements SchnittstelleFensterFunktionen {

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
     * Variable fuer das aktuelle Datum.
     */
    private String aktuellesDatum;
    /**
     * Varibale fue die GP nummer.
     */
    private int geschaeftspartnerNr;
    /**
     * Arraylist, um fehlerhafte Componenten zu speichern.
     */
    private ArrayList<Component> fehlerhafteComponenten;
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
     * Varibalen fuer die Meldungen.
     */
    private final String MELDUNG_PFLICHTFELDER_TITEL
            = "Pflichtfelder nicht ausgefüllt";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String MELDUNG_PFLICHTFELDER_TEXT = "Einige Pflichtfelder"
            + " wurden nicht ausgefüllt.\nBitte füllen Sie diese aus.";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String MELDUNG_AENDERUNGEN_SPEICHERN = "Möchten Sie"
            + " die Änderungen speichern?";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String FEHLER = "Fehler";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String FEHLERHAFTE_EINGABE = "Fehlerhafte Eingabe";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String UNVOLLSTAENDIGE_EINGABE = "Unvollständige Eingabe";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String RECHNUNGSANSCHRIFT = "Rechnungsanschrift";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String LIEFERANSCHRIFT = "Lieferanschrift";
    /**
     * Varibalen fur Adresse.
     */
    private final String DEUTSCHLAND = "Deutschland";
    /**
     * Varibalen fuer die Titel.
     */
    private final String GP_ANLEGEN = "Geschäftspartner anlegen";
    /**
     * Varibalen fuer die Titel.
     */
    private final String GP_AENDERN = "Geschäftspartner ändern";
    /**
     * Varibalen fuer die Titel.
     */
    private final String GP_ANZEIGEN = "Geschäftspartner anzeigen";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer den Preis
     */
    private final String PRUEFUNG_PREIS = "|(\\d*,?\\d{1,2})|"
            + "(\\d{0,3}(\\.\\d{3})*,?\\d{1,2})";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer die Hausnummer.
     */
    private final String PRUEFUNG_HAUSNUMMER = "|[1-9][0-9]{0,2}[a-zA-Z]?";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer die PLZ.
     */
    private final String PRUEFUNG_PLZ = "|[0-9]{5}";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer die e-Mail.
     */
    private final String PRUEFUNG_EMAIL = "|^[a-zA-Z0-9][\\w\\.-]"
            + "*@(?:[a-zA-Z0-9][a-zA-Z0-9_-]+\\.)+[A-Z,a-z]{2,5}$";
    /**
     * Regulaerer Ausdruck fuer die Pruefung der Eingabe fuer die Telefonnummer.
     */
    private final String PRUEFUNG_TELEFON = "|^([+][ ]?[1-9][0-9][ ]?[-]?[ ]?"
            + "|[(]?[0][ ]?)[0-9]{3,4}[-)/ ]?[ ]?[1-9][-0-9 ]{3,16}$";
    /**
     * Varibalen fuer den Format des Datums.
     */
    private final SimpleDateFormat FORMAT;
    /**
     * Number Formatter wird benoetigt fuer das Parsen der Eingaben, sowie das
     * Anzeigen von Preisen.
     */
    private NumberFormat nf;
    /**
     * Varibalen fuer das aktuelle Datum.
     */
    private Calendar cal = Calendar.getInstance();
    /**
     * Varibalen fuer die Eingabe des Geburtsdatum.
     */
    private Date eingabeGeburtsdatum;
    /**
     * Varibale, die die maximale Anzahl von fehlerhaften Componenten
     * beinhaltet.
     */
    private int anzahlFehlerhafterComponenten = 16;
    /**
     * Variable, um zu pruefen, ob alle Eingaben, wenn welche gemacht wurden, ok
     * sind.
     */
    private boolean formularOK = true;
    /**
     * Variable, um die Statusmeldungen ausgeben zu koennen.
     */
    private String STATUSZEILE;

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     *
     * @param factory beinhaltet das factory Obejekt
     * @param mainView beinhaltet das Objekt des StartFenster
     */
    public GeschaeftspartnerAnlegen(GUIFactory factory,
            SchnittstelleHauptfenster mainView) {
        initComponents();
        fehlerhafteComponenten = new ArrayList<>();
//        factory und die dao werden gesetzt
        this.factory = factory;
        this.dao = GUIFactory.getDAO();
        this.hauptFenster = mainView;
//        Format des Datums wird festgelegt
        FORMAT = new SimpleDateFormat("dd.MM.yyyy");
        FORMAT.setLenient(false);
        nf = NumberFormat.getInstance();

        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
//      Documente der Textfelder werden gesetzt
        jTF_Name.setDocument(new UniversalDocument(" &/-.´", true, 30));
        jTF_Vorname.setDocument(new UniversalDocument(" &/-.´", true, 30));
        jTF_Telefon.setDocument(new UniversalDocument("0123456789/-+", false,
                12));
        jTF_Fax.setDocument(new UniversalDocument("0123456789/-+", false, 12));
        jTF_EMail.setDocument(new UniversalDocument("0123456789@-_.", true,
                30));
        jTF_Kreditlimit.setDocument(new UniversalDocument("0123456789.,", false,
                20));
        jTF_StrasseRechnungsanschrift.setDocument(new UniversalDocument("-.´ ",
                true, 30));
        jTF_StrasseLieferanschrift.setDocument(new UniversalDocument("-.´ ",
                true, 30));
        jTF_HausnummerRechnungsanschrift.
                setDocument(new UniversalDocument("0123456789", true, 4));
        jTF_HausnummerLieferanschrift.
                setDocument(new UniversalDocument("0123456789", true, 4));
        jTF_PLZRechnungsanschrift.
                setDocument(new UniversalDocument("0123456789", false, 5));
        jTF_PLZLieferanschrift.
                setDocument(new UniversalDocument("0123456789", false, 5));
        jTF_OrtRechnungsanschrift.
                setDocument(new UniversalDocument("-", true, 30));
        jTF_OrtLieferanschrift.
                setDocument(new UniversalDocument("-", true, 30));
//      Aktuelles Datum wird geholt
        GregorianCalendar aktuellesDaum = new GregorianCalendar();
        DateFormat df_aktuellesDatum = DateFormat
                .getDateInstance(DateFormat.MEDIUM);    //05.12.2014     
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
//      Das aussehen des Textfeldes fuer die Eingabe des Geburtsdatum 
//      wird festgelegt
        MaskFormatter mf = null;
        try {
            mf = new MaskFormatter("##.##.####");
        } catch (ParseException e) {
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
     * Historie:
     * 02.12.2014   Sen     angelegt
     * 16.12.2014   Sen     ueberarbeitet
     */
    /**
     * Methode für die Überprüfung der Daten. Falls ein Textfeld nicht gefüllt
     * ist, wird sie der ArrayList für fehlerhafte Componenten hinzugefuegt.
     * Falls bei eine Compobox der selektierte Index auf 0 ("Bitte auswählen")
     * steht, wird diese ebenfalls in die ArrayList uebernommen.
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

    /*
     * Historie:
     * 02.12.2014   Sen     angelegt
     * 11.12.2014   Sen     ueberarbeitet
     * 14.01.2015   Sen     ueberarbeitet
     */
    /**
     * Schnittstellen Methode, die die Eingaben zurücksetzt, beim Zurücksetzen
     * wird auch die Hintergrundfarbe zurückgesetzt.
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

//          fehlerhafteComponenten werden immer gelöscht, wenn das Formular
//          zureckgesetzt wird!
            fehlerhafteComponenten.clear();
            formularOK = true;
        } catch (NullPointerException e) {
        }
    }

    /*
     * Historie:
     * 02.12.2014   Sen     angelegt
     * 09.12.2014   Sen     ueberarbeitet
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
//            Eingabe ist nicht ok, also wird die vairbale
//            formular ok auf false gesetzt
            formularOK = false;
//            Eingabe nicht korrekt, also wird eine Fehlermeldung ausgegeben
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
//            Focus wird auf das Feld für die Eingabe des Einzelwertes
//            gesetzt und der Eingabefeld wird auf leer gesetzt.
            textfield.requestFocusInWindow();
            textfield.setText("");
        } else if (!textfield.getText().equals("")) {
            formularOK = true;
//            Eingabe ist ok, nun Pruefung ob etwas im Feld steht, 
//            falls ja wird Hitnergrund auf normal gestzt
            textfield.setBackground(JTF_FARBE_STANDARD);
        } else {
            formularOK = true;
        }
    }

    /*
     * Historie:
     * 05.12.2014   Sen     angelegt
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
//      ueber die fehlerhaften Komponenten wird iteriert und bei allen
//      fehlerhaften Componenten wird der Hintergrund in der fehlerhaften
//      Farbe gefaerbt 
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
//        Falls Titel des Fensters GP anlegen oder GP aendern ist wird
//        eine Nachfrage gemacht 
        if (this.getTitle().equals(GP_ANLEGEN)) {
//            zunaechst wird ueberprueft, ob alle Eingaben getaetigt wurden
            ueberpruefen();
//            Falls anzahl fehlerhafter Componenten kleiner ist als max Anzahl
//            von fehlerhaften Componenten
//            heist dass, dass Eingaben getaetigt wurden
            if (fehlerhafteComponenten.size() < anzahlFehlerhafterComponenten) {
                int antwort = JOptionPane.showConfirmDialog(null, meldung,
                        titel, JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (antwort == JOptionPane.YES_OPTION) {
//                  Also nachfrage
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
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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

        jB_AnzeigenAEndern.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Bearbeiten.PNG"))); // NOI18N
        jB_AnzeigenAEndern.setToolTipText("Bearbeiten");
        jB_AnzeigenAEndern.setActionCommand("Anzeigen/Ändern");
        jB_AnzeigenAEndern.setEnabled(false);
        jB_AnzeigenAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_AnzeigenAEndernActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_AnzeigenAEndern);

        jB_Loeschen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Löschen.PNG"))); // NOI18N
        jB_Loeschen.setToolTipText("Löschen");
        jB_Loeschen.setEnabled(false);
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

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Persönliche Daten:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Geschäftspartner-ID:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Typ*:");

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
        jLabel3.setText("Kreditlimit*:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("€");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Name*:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Vorname*:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Anrede*:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Telefon*:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Fax*:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("e-Mail*:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Geburtsdatum*:");

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
        jLabel15.setText("Straße*:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Hausnr.*:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("PLZ*:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Ort*:");

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
        jLabel19.setText("Straße*:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Ort*:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("PLZ*:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Hausnr.*:");

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

        jCHB_WieAnschrift.setText("wie Rechnungsanschrift");
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

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel24.setText("*Pflichtfelder");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTF_OrtRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTF_StrasseRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTF_HausnummerRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel22)))
                                    .addComponent(jLabel19)))
                            .addComponent(jTF_PLZRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTF_StrasseLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCHB_WieAnschrift)
                            .addComponent(jTF_OrtLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_HausnummerLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_PLZLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel13)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7)))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jCB_Anrede, 0, 136, Short.MAX_VALUE)
                                .addComponent(jTF_GeschaeftspartnerID))
                            .addComponent(jTF_EMail, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFTF_Geburtsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_Telefon, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCHB_Kunde))
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTF_Kreditlimit, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCHB_Lieferant)
                            .addComponent(jFTF_Erfassungsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_Vorname, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_Fax, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel23)
                    .addComponent(jCHB_WieAnschrift))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTF_StrasseRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jTF_StrasseLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTF_HausnummerRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jTF_HausnummerLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jTF_PLZRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(jTF_PLZLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTF_OrtLieferanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jTF_OrtRechnungsanschrift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     * Historie:
     * 13.12.2014   Sen     angelegt
     */
    /**
     * Methode um die Lieferanschrift wie die Rechnungsanschrift zu setzen.
     */
    private void jCHB_WieAnschriftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCHB_WieAnschriftActionPerformed
        String meldung = "Bitte geben Sie zunächst die"
                + " komplette Anschrift ein.";
//        Pruefung, ob Checkbox wie anschrift geklickt wurde
        if (jCHB_WieAnschrift.isSelected()) {
//     fals ja, wird geprueft, ob alle Felder der rechnungsanschrift belegt sind 
            if (!jTF_StrasseRechnungsanschrift.getText().equals("")
                    && !jTF_HausnummerRechnungsanschrift.getText().equals("")
                    && !jTF_PLZRechnungsanschrift.getText().equals("")
                    && !jTF_OrtRechnungsanschrift.getText().equals("")) {
//                 alle Felder belegt, also werden die Felder der Liefeanschrift
//                 wie die Felder
//                 der Rechnungsanschift gesetzt
                jTF_StrasseLieferanschrift
                        .setText(jTF_StrasseRechnungsanschrift.getText());
                jTF_StrasseLieferanschrift.setEnabled(false);
                jTF_HausnummerLieferanschrift
                        .setText(jTF_HausnummerRechnungsanschrift.getText());
                jTF_HausnummerLieferanschrift.setEnabled(false);
                jTF_PLZLieferanschrift
                        .setText(jTF_PLZRechnungsanschrift.getText());
                jTF_PLZLieferanschrift.setEnabled(false);
                jTF_OrtLieferanschrift
                        .setText(jTF_OrtRechnungsanschrift.getText());
                jTF_OrtLieferanschrift.setEnabled(false);
            } else {
//                nicht alle Felder sind belegt
//                Prufueung, welche nicht belegt ist und zuvor eine Meldung
//                Focus wird auf das nicht belegte Feld gesetzt
                JOptionPane.showMessageDialog(null, meldung,
                        UNVOLLSTAENDIGE_EINGABE, JOptionPane.ERROR_MESSAGE);
                jCHB_WieAnschrift.setSelected(false);
                if (jTF_StrasseRechnungsanschrift.getText().equals("")) {
                    jTF_StrasseRechnungsanschrift.requestFocusInWindow();
                } else if (jTF_HausnummerRechnungsanschrift.getText()
                        .equals("")) {
                    jTF_HausnummerRechnungsanschrift.requestFocusInWindow();
                } else if (jTF_PLZRechnungsanschrift.getText().equals("")) {
                    jTF_PLZRechnungsanschrift.requestFocusInWindow();
                } else {
                    jTF_OrtRechnungsanschrift.requestFocusInWindow();
                }
            }
        } else {
//            Checkbox wie anschrift nicht geklickt, also werden die Felder
//            der Lieferschrift geleert
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
     * Historie:
     * 03.12.2014   Sen     angelegt
     */
    /**
     * Methode, um den Typ des Geschäftspartners zu wählen in standard gefärbt.
     */
    private void jCHB_KundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCHB_KundeActionPerformed
        if (jCHB_Kunde.isSelected()) {
            jCHB_Lieferant.setSelected(false);
        }
    }//GEN-LAST:event_jCHB_KundeActionPerformed

    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */
    /**
     * Methode, um den Typ des Geschäftspartners zu wählen. in standard gefärbt.
     */
    private void jCHB_LieferantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCHB_LieferantActionPerformed
        if (jCHB_Lieferant.isSelected()) {
            jCHB_Kunde.setSelected(false);
        }
    }//GEN-LAST:event_jCHB_LieferantActionPerformed

    /*
     * Historie:
     * 20.12.2014   Sen     angelegt
     * 28.12.2014   Sen     ueberarbeitet
     * 30.12.2014   Sen     Funtkion fuer Sicht GP anlegen implementiert
     * 10.01.2015   Sen     Funtkion fuer Sicht GP aendern implementiert
     * 04.02.2015   Sen     Fehler beseitigt, z.B. wenn falscher Preis 
     *                      eingegeben wird und direkt auf speichern geklickt
     *                      wird
     */
    /**
     * Methode für das Speichern der Daten. Falls die Sicht Aritkel anlegen
     * geoeffnet ist, wird eine Artikel angelegt. Ist Sicht Artikel aendern
     * geoffnet, wird der Artikel ueberschieben.
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed
//      falls das Formular ok ist, d.h. es sind vorher keine Fehlermeldungen 
//      enstanden, dann wird die Speichern Aktion durchgefuehrt  
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
            long gpnr;
//      falls fehlerhafteComponenten leer ist (es sind keine fehlerhaften
//      Componenten verfuegbar), 
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
                hausnummerAnschrift = jTF_HausnummerRechnungsanschrift
                        .getText();
                plzAnschrift = jTF_PLZRechnungsanschrift.getText();
                ortAnschrift = jTF_OrtRechnungsanschrift.getText();

                strasseLieferanschrift = jTF_StrasseLieferanschrift.getText();
                hausnummerLieferanschrift = jTF_HausnummerLieferanschrift
                        .getText();
                plzLieferanschrift = jTF_PLZLieferanschrift.getText();
                ortLieferanschrift = jTF_OrtLieferanschrift.getText();
                try {
                    kreditlimit = nf.parse(jTF_Kreditlimit.getText())
                            .doubleValue();
                    gpnr = nf.parse(jTF_GeschaeftspartnerID.getText())
                            .longValue();
//                Pruefung, ob typ des GP ausgewaehlt ist
                    if (jCHB_Kunde.isSelected()
                            || jCHB_Lieferant.isSelected()) {
//                Ueberpruefung in welche Sicht wir sind
                        if (this.getTitle().equals(GP_ANLEGEN)) {
//                    Sicht GP anlegen--> neuer GP wird in datenbank geschrieben
//                    zunaechst muss aber die rechnungsanschrift erstellt werden
                            rechnungsanschrift = this.dao
                                    .erstelleAnschrift(RECHNUNGSANSCHRIFT,
                                            name, vorname,
                                            titel, strasseAnschrift,
                                            hausnummerAnschrift, plzAnschrift,
                                            ortAnschrift, DEUTSCHLAND, telefon,
                                            fax, eMail, eingabeGeburtsdatum);
//                      Pruefung, ob checkbox wie anschrift gesetzt
                            if (jCHB_WieAnschrift.isSelected()) {
//                      Falls checkbox wie anschrift geklickt ist, ist die 
//                      Lieferanschrift = die rechnungsanschrift
                                lieferanschrift = rechnungsanschrift;
                            } else {
//                          nicht gesetzt neue Lieferanschrift erzeugen  
                                lieferanschrift = this.dao
                                        .erstelleAnschrift(LIEFERANSCHRIFT,
                                                name, vorname, titel,
                                                strasseLieferanschrift,
                                                hausnummerLieferanschrift,
                                                plzLieferanschrift,
                                                ortLieferanschrift,
                                                DEUTSCHLAND, telefon, fax,
                                                eMail, eingabeGeburtsdatum);
                            }
//                        Nun einen neuene GP mit beiden Anschriften erzeugen
                            this.dao.erstelleGeschaeftspartner(typ,
                                    lieferanschrift, rechnungsanschrift,
                                    kreditlimit);
//                          Meldung fuer die Statuszeile wird angepasst
                            STATUSZEILE = "Geschäftspartner mit der"
                                    + " Geschäftspartner-ID " + gpnr
                                    + " wurde erfolgreich angelegt. ";
                            this.hauptFenster.setzeStatusMeldung(STATUSZEILE);
                            zuruecksetzen();
                        } else {
//                  Sicht GP aendern ist geoffnet, also wird zunaechst der GP
//                  aus der Datenbank geladen.
//                        Vergleichsanschriften werden erzeugt
                            Anschrift rechnungsanschriftNachher;
                            Anschrift lieferanschriftNachher;
//                        das eingebene geburtsdatum wird nochmals geholt
                            eingabeGeburtsdatum = FORMAT
                                    .parse(jFTF_Geburtsdatum.getText());
//                      GP aus Datenbank ist Variable gpVorher  
                            Geschaeftspartner gpVorher = this.dao
                                    .gibGeschaeftspartner(gpnr);
//                       vergleich GP erzeugen mit den Eingabedaten,
//                       doch zunaechst muessen die Anschriften erzeugt werden
                            Geschaeftspartner gpNachher;
//                      rechnungsanschrift nachher wird erzeugt
                            rechnungsanschriftNachher
                                    = new Rechnungsanschrift(name, vorname,
                                            titel, strasseAnschrift,
                                            hausnummerAnschrift,
                                            plzAnschrift, ortAnschrift,
                                            DEUTSCHLAND, telefon, fax, eMail,
                                            eingabeGeburtsdatum,
                                            gpVorher.getRechnungsadresse()
                                            .getErfassungsdatum());
                            if (jCHB_WieAnschrift.isSelected()) {
                                lieferanschriftNachher
                                        = rechnungsanschriftNachher;
                            } else {
                                lieferanschriftNachher
                                        = new Lieferanschrift(name, vorname,
                                                titel, strasseLieferanschrift,
                                                hausnummerLieferanschrift,
                                                plzLieferanschrift,
                                                ortLieferanschrift, DEUTSCHLAND,
                                                telefon, fax, eMail,
                                                eingabeGeburtsdatum,
                                                gpVorher.getLieferadresse()
                                                .getErfassungsdatum());
                            }
//                      Pruefung, elchen Typ der GP hat 
                            if (jCHB_Kunde.isSelected()) {
//                            GP ist Kunde, also Kunde erzeugen
                                gpNachher
                                        = new Kunde(lieferanschriftNachher,
                                                rechnungsanschriftNachher,
                                                kreditlimit, false);
                            } else {
//                            GP ist Lieferant, Lieferant erzeugen
                                gpNachher
                                        = new Lieferant(lieferanschriftNachher,
                                                rechnungsanschriftNachher,
                                                kreditlimit, false);
                            }
//                      Vergeleich, ob der GP sich geaendert hat
                            if (gpVorher.equals(gpNachher)) {
//                            Keine Veraenderung, Meldung
                                JOptionPane.showMessageDialog(null,
                                        "Es wurden keine Änderungen gemacht!",
                                        "Keine Änderungen",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
//                              Aenderung -> Abfrage, ob Änderungen
//                              gespeichert werden sollen
                                int antwort = JOptionPane
                                        .showConfirmDialog(null,
                                                MELDUNG_AENDERUNGEN_SPEICHERN,
                                                GP_AENDERN,
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE);
                                if (antwort == JOptionPane.YES_OPTION) {
//                              falls ja, gp in DP aendern
                                    this.dao.aendereGeschaeftspartner(gpVorher
                                            .getGeschaeftspartnerID(),
                                            jCHB_WieAnschrift.isSelected(),
                                            kreditlimit, name, vorname, titel,
                                            strasseAnschrift,
                                            hausnummerAnschrift,
                                            plzAnschrift, ortAnschrift,
                                            strasseLieferanschrift,
                                            hausnummerLieferanschrift,
                                            plzLieferanschrift,
                                            ortLieferanschrift, DEUTSCHLAND,
                                            telefon, fax, eMail,
                                            eingabeGeburtsdatum);
//                                  Meldung fuer die Statuszeile wird angepasst
                                    STATUSZEILE = "Geschäftspartner mit der"
                                            + " Geschäftspartner-ID " + gpnr
                                            + " wurde erfolgreich geändert. ";
                                    this.hauptFenster
                                            .setzeStatusMeldung(STATUSZEILE);
                                    zuruecksetzen(); // Formular zuruecksetzen
                                    this.setVisible(false); 
//                                Änderung am 03.02.2015
                                    zurueckInsHauptmenue();
//                                ende Änderung
                                } else {
//                              Nein button wird geklickt, keine Aktion nur 
//                              fehlerhafte Komponenten müssen geleert werden
                                    fehlerhafteComponenten.clear();
                                }
                            }

                        }
                    } else {
//                    Checkbox Kunde und Lieferant nicht ausgewaehlt-> 
//                    Meldung das der Typ des GP ausgeaehlt sein muss
                        JOptionPane.showMessageDialog(null, "Bitte wählen Sie "
                                + "den Typ des Geschäftspartners.",
                                UNVOLLSTAENDIGE_EINGABE,
                                JOptionPane.ERROR_MESSAGE);
                        jCHB_Kunde.requestFocusInWindow();
                    }
//          Abfangen der Fehler
                } catch (ApplicationException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(),
                            FEHLER, JOptionPane.ERROR_MESSAGE);
                } catch (ParseException | NullPointerException e) {
                }
            } else {
//            fehlerhafteComponenten ist nicht leer (es sind 
//            fehlerhafte Componenten vorhanden)
//          wird methode fuer das Markieren ausgefuehrt
                fehlEingabenMarkierung(fehlerhafteComponenten,
                        MELDUNG_PFLICHTFELDER_TITEL, MELDUNG_PFLICHTFELDER_TEXT,
                        FARBE_FEHLERHAFT);
            }
        }
//      Varibale formularOk muss wieder als true gesetzt werden,
//      da wieder geprueft wird.    
        formularOK = true;
    }//GEN-LAST:event_jB_SpeichernActionPerformed

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jCB_AnredeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_AnredeActionPerformed
        if (jCB_Anrede.getSelectedIndex() != 0) {
            jCB_Anrede.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_AnredeActionPerformed

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTF_NameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_NameFocusLost
        if (!jTF_Name.getText().equals("")) {
            jTF_Name.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_NameFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTF_VornameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_VornameFocusLost
        if (!jTF_Vorname.getText().equals("")) {
            jTF_Vorname.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_VornameFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Wenn nicht, wird eine Meldung ausgegeben.
     *
     */
    private void jTF_TelefonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_TelefonFocusLost
        String meldung = "Die eingegebene Telefonnummer ist nicht"
                + " richtig!\nBitte geben Sie eine richtige Telefonnummer"
                + " ein. (z.B. 01771234567)";
        ueberpruefungVonFocusLost(jTF_Telefon, PRUEFUNG_TELEFON,
                FEHLERHAFTE_EINGABE, meldung);
    }//GEN-LAST:event_jTF_TelefonFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Wenn nicht, wird eine Meldung ausgegeben.
     *
     */
    private void jTF_FaxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_FaxFocusLost
        String meldung = "Die eingegebene Faxnummer ist nicht richtig!\nBitte "
                + "geben Sie eine richtige Faxnummer ein. (z.B. 0234123123)";
        ueberpruefungVonFocusLost(jTF_Fax, PRUEFUNG_TELEFON,
                FEHLERHAFTE_EINGABE, meldung);
    }//GEN-LAST:event_jTF_FaxFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     * 05.12.2014   Sen     ueberarbeitet
     */
    /**
     * Methode prüft, ob das eingebene Geburstdatum ok ist. Falls ja, wird der
     * Hintergrund in standard gefärbt. Wenn nicht, wird eine Meldung
     * ausgegeben.
     *
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
                if (eingabeGeburtsdatum.after(heutigesDatum)) {
//                  Datum ist in zukunft, formularOK wird auf false gesetzt  
                    formularOK = false;
//                    Falls ja Fehlermedung
                    meldung = "Das eingegebene Geburtsdatum ist in der"
                            + " Zukunft!\nBitte geben Sie ein gültiges"
                            + " Geburtsdatum ein. (z.B. 01.01.1980)";
                    JOptionPane.showMessageDialog(null, meldung,
                            FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
                    jFTF_Geburtsdatum.requestFocusInWindow();
                    jFTF_Geburtsdatum.setValue(null);
//                    Pruefung ob GP volljaehrig
                } else if (eingabeGeburtsdatum.after(tagVor18jahren)) {
//                  GP ist unter 18, formularOK wird auf false gesetzt 
                    formularOK = false;
//                    Falls nicht, Fehlermeldung
                    meldung = "Der eingegebene Geschäftspartner ist nicht"
                            + " volljährig!\nEs können nur volljährige "
                            + "Geschäftspartner angelegt werden.";
                    JOptionPane.showMessageDialog(null, meldung,
                            FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
                    jFTF_Geburtsdatum.requestFocusInWindow();
                    jFTF_Geburtsdatum.setValue(null);
                } else if (eingabeGeburtsdatum.before(tagVor100jahren)) {
//   Pruefung, ob das eingegebene Geburtsdatum ein vier stelliges Jahr enthaelt,
//   denn falls der Benutzer 01.01.1### eingibt, interpretiert der
//   Formatter das so, als ob das Jahr 0.01.1 eingegeben wurde.
//   Da dies aber nicht gueltig sein soll, kommt diese Ueberpruefung
//                  Das Date Objekt wird in ein String umgewandelt  
                    String s = gibDatumAusDatenbank(eingabeGeburtsdatum);
//                  Ueberpruefung, ob das Jahr vier stellig ist  
                    if ((s.substring(6, s.length())).length() != 4) {
//                    Es ist vier Stellig, es wird eine Parse Exception geworfen
                        throw new ParseException("Fehler", ERROR);
                    }
//                 GP ist ueber 100 Jahre alt, formularOK wird auf false gesetzt
                    formularOK = false;
//                    Falls nicht, Fehlermeldung
                    meldung = "Der eingegebene Geschäftspartner ist über 100 "
                            + "Jahre alt!\nGeschäftspartner, die über 100 Jahre"
                            + " alt sind können nicht angelegt werden.";
                    JOptionPane.showMessageDialog(null, meldung,
                            FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
                    jFTF_Geburtsdatum.requestFocusInWindow();
                    jFTF_Geburtsdatum.setValue(null);
                } else {
                    formularOK = true;
//                    Eingabe OK, Hintergrund wird in normal gesetzt
                    jFTF_Geburtsdatum.setBackground(JTF_FARBE_STANDARD);
                }
            } catch (ParseException e) {
//              Datum ist in ungueltig, formularOK wird auf false gesetzt 
                formularOK = false;
//            Parse Exception, da EingabeDatum nicht gueltig --> Fehlermeldung
                meldung = "Das eingegebene Geburtsdatum ist nicht"
                        + " gültig!\nBitte geben Sie ein gültiges Geburtsdatum"
                        + " ein. (z.B. 01.01.1980)";
                JOptionPane.showMessageDialog(null, meldung,
                        FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
                jFTF_Geburtsdatum.requestFocusInWindow();
                jFTF_Geburtsdatum.setValue(null);
            }
        }
    }//GEN-LAST:event_jFTF_GeburtsdatumFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    private void jTF_EMailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_EMailFocusLost
        String meldung = "Die eingegebene e-Mail Adresse ist nicht "
                + "gültig!\nBitte geben Sie eine gültige e-Mail"
                + " Adresse ein. (z.B. max.mustermann@w-hs.de)";
        ueberpruefungVonFocusLost(jTF_EMail, PRUEFUNG_EMAIL,
                FEHLERHAFTE_EINGABE, meldung);
    }//GEN-LAST:event_jTF_EMailFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    private void jTF_KreditlimitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_KreditlimitFocusLost
//        Pruefung, ob Krediitlimit mit Synstax uebereinstimmt, 
//        falls nein wird eine Meldung ausgegeben
        if (!jTF_Kreditlimit.getText().matches(PRUEFUNG_PREIS)) {
//          Kreditlimit ist nicht ok, formularOK wird auf false gesetzt 
            formularOK = false;
            String meldung = "Der eingegebene Wert ist nicht richtig!\nBitte"
                    + " geben Sie einen richtigen Wert ein."
                    + " (z.B. 9,99 oder 99.999,99)";
            JOptionPane.showMessageDialog(null, meldung, FEHLERHAFTE_EINGABE,
                    JOptionPane.ERROR_MESSAGE);
            jTF_Kreditlimit.requestFocusInWindow();
            jTF_Kreditlimit.setText("");
        } else if (!jTF_Kreditlimit.getText().equals("")) {
            try {
                formularOK = true;
                double eingabeKreditlimit = nf.parse(jTF_Kreditlimit.getText())
                        .doubleValue();
                jTF_Kreditlimit.setText(nf.format(eingabeKreditlimit));
                jTF_Kreditlimit.setBackground(JTF_FARBE_STANDARD);
            } catch (ParseException ex) {
            }
        } else {
            formularOK = true;
        }
    }//GEN-LAST:event_jTF_KreditlimitFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTF_StrasseRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseRechnungsanschriftFocusLost
        if (!jTF_StrasseRechnungsanschrift.getText().equals("")) {
            jTF_StrasseRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_StrasseRechnungsanschriftFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTF_StrasseLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseLieferanschriftFocusLost
        if (!jTF_StrasseLieferanschrift.getText().equals("")) {
            jTF_StrasseLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_StrasseLieferanschriftFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    private void jTF_HausnummerRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerRechnungsanschriftFocusLost
        String meldung = "Die eingegebene Hausnummer ist nicht richtig!\nSie"
                + " können eine maximal dreistellige Hausnummer gefolgt"
                + " von\neinem optionalen Buchstaben eingeben."
                + " (z.B. 10 oder 100A)";
        ueberpruefungVonFocusLost(jTF_HausnummerRechnungsanschrift,
                PRUEFUNG_HAUSNUMMER, FEHLERHAFTE_EINGABE, meldung);


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
        String meldung = "Die eingegebene Hausnummer ist nicht "
                + "richtig!\nSie können eine maximal dreistellige Hausnummer"
                + " gefolgt von\neinem optionalen Buchstaben eingeben."
                + " (z.B. 10 oder 100A)";
        ueberpruefungVonFocusLost(jTF_HausnummerLieferanschrift,
                PRUEFUNG_HAUSNUMMER, FEHLERHAFTE_EINGABE, meldung);
    }//GEN-LAST:event_jTF_HausnummerLieferanschriftFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    private void jTF_PLZRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZRechnungsanschriftFocusLost
        String meldung = "Die eingegebene Postleitzahl ist nicht "
                + "richtig!\nBitte geben Sie eine fünfstellige "
                + "Postleitzahl ein. (z.B. 45968)";
        ueberpruefungVonFocusLost(jTF_PLZRechnungsanschrift, PRUEFUNG_PLZ,
                FEHLERHAFTE_EINGABE, meldung);
    }//GEN-LAST:event_jTF_PLZRechnungsanschriftFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt. Bei Fehleingaben wird eine Meldung ausgegeben.
     */
    private void jTF_PLZLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZLieferanschriftFocusLost
        String meldung = "Die eingegebene Postleitzahl ist nicht "
                + "richtig!\nBitte geben Sie eine fünfstellige Postleitzahl"
                + " ein. (z.B. 45968)";
        ueberpruefungVonFocusLost(jTF_PLZLieferanschrift, PRUEFUNG_PLZ,
                FEHLERHAFTE_EINGABE, meldung);
    }//GEN-LAST:event_jTF_PLZLieferanschriftFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTF_OrtRechnungsanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtRechnungsanschriftFocusLost
        if (!jTF_OrtRechnungsanschrift.getText().equals("")) {
            jTF_OrtRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_OrtRechnungsanschriftFocusLost

    /*
     * Historie:
     * 29.11.2014   Sen     angelegt
     */
    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     */
    private void jTF_OrtLieferanschriftFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtLieferanschriftFocusLost
        if (!jTF_OrtLieferanschrift.getText().equals("")) {
            jTF_OrtLieferanschrift.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_OrtLieferanschriftFocusLost

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_NameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_NameFocusGained
        jTF_Name.selectAll();
    }//GEN-LAST:event_jTF_NameFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_VornameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_VornameFocusGained
        jTF_Vorname.selectAll();
    }//GEN-LAST:event_jTF_VornameFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_TelefonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_TelefonFocusGained
        jTF_Telefon.selectAll();
    }//GEN-LAST:event_jTF_TelefonFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_FaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_FaxFocusGained
        jTF_Fax.selectAll();
    }//GEN-LAST:event_jTF_FaxFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_EMailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_EMailFocusGained
        jTF_EMail.selectAll();
    }//GEN-LAST:event_jTF_EMailFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_KreditlimitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_KreditlimitFocusGained
        jTF_Kreditlimit.selectAll();
    }//GEN-LAST:event_jTF_KreditlimitFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_StrasseRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseRechnungsanschriftFocusGained
        jTF_StrasseRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_StrasseRechnungsanschriftFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_StrasseLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_StrasseLieferanschriftFocusGained
        jTF_StrasseLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_StrasseLieferanschriftFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_HausnummerRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerRechnungsanschriftFocusGained
        jTF_HausnummerRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_HausnummerRechnungsanschriftFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_HausnummerLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_HausnummerLieferanschriftFocusGained
        jTF_HausnummerLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_HausnummerLieferanschriftFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_PLZRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZRechnungsanschriftFocusGained
        jTF_PLZRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_PLZRechnungsanschriftFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_PLZLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_PLZLieferanschriftFocusGained
        jTF_PLZLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_PLZLieferanschriftFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_OrtRechnungsanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtRechnungsanschriftFocusGained
        jTF_OrtRechnungsanschrift.selectAll();
    }//GEN-LAST:event_jTF_OrtRechnungsanschriftFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void jTF_OrtLieferanschriftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_OrtLieferanschriftFocusGained
        jTF_OrtLieferanschrift.selectAll();
    }//GEN-LAST:event_jTF_OrtLieferanschriftFocusGained

    /*
     * Historie:
     * 30.11.2014   Sen     angelegt
     */
    /**
     * Wenn in das Textfeld der Focus kommt, wird die Eingabe selektiert.
     */
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        beendenEingabeNachfrage();
    }//GEN-LAST:event_formInternalFrameClosing

    /*
     * Historie:
     * 29.11.2014   Terrasi     angelegt
     * 04.02.2015   Sen         Fehler beseitigt, z.B. wenn falscher Preis 
     *                          eingegeben wird und direkt auf zurueck geklickt
     *                          wird
     */
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt Event, der ausgeloest wird
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
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
//      falls fehlerhafteComponenten leer ist (es sind keine fehlerhaften 
//      Componenten verfuegbar), 
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
                    hausnummerAnschrift = jTF_HausnummerRechnungsanschrift
                            .getText();
                    plzAnschrift = jTF_PLZRechnungsanschrift.getText();
                    ortAnschrift = jTF_OrtRechnungsanschrift.getText();

                    strasseLieferanschrift = jTF_StrasseLieferanschrift
                            .getText();
                    hausnummerLieferanschrift = jTF_HausnummerLieferanschrift
                            .getText();
                    plzLieferanschrift = jTF_PLZLieferanschrift.getText();
                    ortLieferanschrift = jTF_OrtLieferanschrift.getText();

                    try {
                        kreditlimit = nf.parse(jTF_Kreditlimit.getText())
                                .doubleValue();
//                Pruefung, ob typ des GP ausgewaehlt ist
                        if (jCHB_Kunde.isSelected()
                                || jCHB_Lieferant.isSelected()) {
//                  Sicht GP aendern ist geoffnet, also wird zunaechst
//                  der GP aus der Datenbank geladen.
                            long gpnr;
//                        Vergleichsanschriften werden erzeugt
                            Anschrift rechnungsanschriftNachher;
                            Anschrift lieferanschriftNachher;
//                        das eingebene geburtsdatum wird nochmals geholt
                            eingabeGeburtsdatum = FORMAT
                                    .parse(jFTF_Geburtsdatum.getText());
                            gpnr = nf.parse(jTF_GeschaeftspartnerID.getText())
                                    .longValue();
//                      GP aus Datenbank ist Variable gpVorher  
                            Geschaeftspartner gpVorher = this.dao
                                    .gibGeschaeftspartner(gpnr);
//                       vergleich GP erzeugen mit den Eingabedaten,
//                       doch zunaechst muessen die Anschriften erzeugt werden
                            Geschaeftspartner gpNachher;
//                      rechnungsanschrift nachher wird erzeugt
                            rechnungsanschriftNachher
                                    = new Rechnungsanschrift(name, vorname,
                                            titel, strasseAnschrift,
                                            hausnummerAnschrift,
                                            plzAnschrift, ortAnschrift,
                                            DEUTSCHLAND, telefon, fax, eMail,
                                            eingabeGeburtsdatum,
                                            gpVorher.getRechnungsadresse()
                                            .getErfassungsdatum());
                            if (jCHB_WieAnschrift.isSelected()) {
                                lieferanschriftNachher
                                        = rechnungsanschriftNachher;
                            } else {
                                lieferanschriftNachher
                                        = new Lieferanschrift(name, vorname,
                                                titel, strasseLieferanschrift,
                                                hausnummerLieferanschrift,
                                                plzLieferanschrift,
                                                ortLieferanschrift,
                                                DEUTSCHLAND, telefon, fax,
                                                eMail, eingabeGeburtsdatum,
                                                gpVorher.getLieferadresse()
                                                .getErfassungsdatum());
                            }
//                      Pruefung, elchen Typ der GP hat 
                            if (jCHB_Kunde.isSelected()) {
//                            GP ist Kunde, also Kunde erzeugen
                                gpNachher = new Kunde(lieferanschriftNachher,
                                        rechnungsanschriftNachher, kreditlimit,
                                        false);
                            } else {
//                            GP ist Lieferant, Lieferant erzeugen
                                gpNachher
                                        = new Lieferant(lieferanschriftNachher,
                                                rechnungsanschriftNachher,
                                                kreditlimit, false);
                            }
//                          Vergeleich, ob der GP sich geaendert hat
                            if (gpVorher.equals(gpNachher)) {
                                this.setVisible(false); //diese Sicht ausblenden
                                zurueckInsHauptmenue();
                            } else {
//                Aenderung -> Abfrage, ob Änderungen gespeichert werden sollen
                                int antwort
                                        = JOptionPane.showConfirmDialog(null,
                                                MELDUNG_AENDERUNGEN_SPEICHERN,
                                                GP_AENDERN,
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE);
                                if (antwort == JOptionPane.YES_OPTION) {
//                              falls ja, gp in DP aendern
                                    this.dao
                                            .aendereGeschaeftspartner(gpVorher.getGeschaeftspartnerID(), jCHB_WieAnschrift.isSelected(),
                                                    kreditlimit, name, vorname, titel, strasseAnschrift, hausnummerAnschrift,
                                                    plzAnschrift, ortAnschrift, strasseLieferanschrift, hausnummerLieferanschrift,
                                                    plzLieferanschrift, ortLieferanschrift, DEUTSCHLAND, telefon, fax, eMail, eingabeGeburtsdatum);
//                                  Meldung fuer die Statuszeile wird angepasst
                                    STATUSZEILE = "Geschäftspartner mit der"
                                            + " Geschäftspartner-ID " + gpnr
                                            + " wurde erfolgreich geändert. ";
                                    this.hauptFenster
                                            .setzeStatusMeldung(STATUSZEILE);
                                    this.setVisible(false);
                                    zurueckInsHauptmenue();
                                } else if (antwort == JOptionPane.CLOSED_OPTION) {
//                                 das x wird geklickt, es soll nichts passieren
                                } else {
                                    this.setVisible(false);
                                    zurueckInsHauptmenue();
                                }
                            }
                        } else {
//                     etwas ist nicht gleich
//                     Abfrage, ob Änderungen gespeichert werden sollen
                            int antwort = JOptionPane.showConfirmDialog(null,
                                    MELDUNG_AENDERUNGEN_SPEICHERN, GP_AENDERN,
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (antwort == JOptionPane.YES_OPTION) {
//                    Checkbox Kunde und Lieferant nicht ausgewaehlt
//                    -> Meldung das der Typ des GP ausgeaehlt sein muss
                                JOptionPane.showMessageDialog(null,
                                        "Bitte wählen Sie den Typ "
                                        + "des Geschäftspartners.",
                                        UNVOLLSTAENDIGE_EINGABE,
                                        JOptionPane.ERROR_MESSAGE);
                                jCHB_Kunde.requestFocusInWindow();
                            } else {
                                this.setVisible(false); //diese Sicht ausblenden
                                zurueckInsHauptmenue();
                            }
                        }
//          Abfangen der Fehler
                    } catch (ApplicationException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(),
                                FEHLER, JOptionPane.ERROR_MESSAGE);
                    } catch (ParseException | NullPointerException e) {
                    }
                } else {
//                     etwas ist nicht gleich
//                     Abfrage, ob Änderungen gespeichert werden sollen
                    int antwort = JOptionPane.showConfirmDialog(null,
                            MELDUNG_AENDERUNGEN_SPEICHERN, GP_AENDERN,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (antwort == JOptionPane.YES_OPTION) {
                        fehlEingabenMarkierung(fehlerhafteComponenten,
                                MELDUNG_PFLICHTFELDER_TITEL,
                                MELDUNG_PFLICHTFELDER_TEXT,
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
//      Variable wird wieder auf true gesetzt, da nochmals eine Pruefung 
//      stattfindet 
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
//        Erhalten über GUIFactorymethode die letzte aufgerufene View
//        und speichern diese in Variable
        c = this.factory.zurueckButton();
        this.setVisible(false); // Internalframe wird nicht mehr dargestellt
        c.setVisible(true); // Übergebene Component wird sichtbar gemacht
    }

    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     * 04.02.2015   Sen     Fehler beseitigt, z.B. wenn falscher Preis  
     *                      eingegeben wird und direkt auf loeschen geklickt 
     *                      wird.
     */
    /**
     * Methode fuer das Loeschen eines GP.
     *
     * @param evt Event der ausgeloest wird
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
                long gpnr = nf.parse(jTF_GeschaeftspartnerID
                        .getText()).longValue();
                int antwort = JOptionPane.showConfirmDialog(null,
                        meldung, titel, JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (antwort == JOptionPane.YES_OPTION) {
                    this.dao.loescheGeschaeftspartner(gpnr);
//              Meldung fuer die Statuszeile wird angepasst
                    STATUSZEILE = "Geschäftspartner mit der "
                            + "Geschäftspartner-ID " + gpnr
                            + " wurde erfolgreich gelöscht. ";
                    this.hauptFenster.setzeStatusMeldung(STATUSZEILE);
                    zurueckInsHauptmenue();
                }
            } catch (ApplicationException e) {
//              Ein Fehler ist aufgetreten, dieser wird ausgegeben  
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        FEHLER, JOptionPane.ERROR_MESSAGE);
            } catch (ParseException | NullPointerException e) {
            }
        }
//      Variable wird wieder auf true gesetzt, da nochmals eine Pruefung 
//      stattfindet 
        formularOK = true;
    }//GEN-LAST:event_jB_LoeschenActionPerformed

    /*
     * Historie:
     * 15.01.2015   Sen     angelegt
     * 17.01.2015   Schulz  Suchbutton event eingefügt
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
        if (this.getTitle().equals(GP_ANZEIGEN)) {
            setzeFormularInGPAEndernFuerButton();
        }
    }//GEN-LAST:event_jB_AnzeigenAEndernActionPerformed

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP nummer
     */
    public JTextField gibjTF_GeschaeftspartnerID() {
        return jTF_GeschaeftspartnerID;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return checkbox der GP kunde
     */
    public JCheckBox gibjCHB_Kunde() {
        return jCHB_Kunde;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return checkbox der GP lieferant
     */
    public JCheckBox gibjCHB_Lieferant() {
        return jCHB_Lieferant;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return checkbox der GP anrede
     */
    public JComboBox gibjCB_Anrede() {
        return jCB_Anrede;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP name
     */
    public JTextField gibjTF_Name() {
        return jTF_Name;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP vorname
     */
    public JTextField gibjTF_Vorname() {
        return jTF_Vorname;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP telefon
     */
    public JTextField gibjTF_Telefon() {
        return jTF_Telefon;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP fax
     */
    public JTextField gibjTF_Fax() {
        return jTF_Fax;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return jftextfield der GP geburstdatum
     */
    public JFormattedTextField gibjFTF_Geburtsdatum() {
        return jFTF_Geburtsdatum;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return jftextfield der GP geburstdatum
     */
    public JFormattedTextField gibjFTF_Erfassungsdatum() {
        return jFTF_Erfassungsdatum;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP email
     */
    public JTextField gibjTF_EMail() {
        return jTF_EMail;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP kreditlimit
     */
    public JTextField gibjTF_Kreditlimit() {
        return jTF_Kreditlimit;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return checkbox der GP wie anschrift
     */
    public JCheckBox gibjCHB_WieAnschrift() {
        return jCHB_WieAnschrift;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP strasse rechnungsanschrift
     */
    public JTextField gibjTF_StrasseRechnungsanschrift() {
        return jTF_StrasseRechnungsanschrift;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP hausnummer rechnungsanschrift
     */
    public JTextField gibjTF_HausnummerRechnungsanschrift() {
        return jTF_HausnummerRechnungsanschrift;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP plz rechnungsanschrift
     */
    public JTextField gibjTF_PLZRechnungsanschrift() {
        return jTF_PLZRechnungsanschrift;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP ort rechnungsanschrift
     */
    public JTextField gibjTF_OrtRechnungsanschrift() {
        return jTF_OrtRechnungsanschrift;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP strasse lieferanschrift
     */
    public JTextField gibjTF_StrasseLieferanschrift() {
        return jTF_StrasseLieferanschrift;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP hausnummer lieferanschrift
     */
    public JTextField gibjTF_HausnummerLieferanschrift() {
        return jTF_HausnummerLieferanschrift;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP plz lieferanschrift
     */
    public JTextField gibjTF_PLZLieferanschrift() {
        return jTF_PLZLieferanschrift;
    }

    /*
     * Historie:
     * 12.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht GP aendern Einstieg.
     *
     * @return textfield der GP ort lieferanschrift
     */
    public JTextField gibjTF_OrtLieferanschrift() {
        return jTF_OrtLieferanschrift;
    }

    /*
     * Historie:
     * 07.01.2015   Sen     angelegt
     * 08.01.2015 Terrasi, implemtiereung der Referenzvariable "hauptfenster"
     */
    /**
     * Methode, die das Formular in die Sicht GP anlegen aendert.
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
//        jB_AnzeigenAEndern.setText("Anzeigen/Ändern");
        jB_Loeschen.setEnabled(false);
//      Übergibt der Referenz des Hauptfensters das Internaframe
        this.hauptFenster.setzeComponent(this);
    }

    /*
     * Historie:
     * 07.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    /**
     * Methode, die das Formular in die Sicht GP aendern aendert.
     */
    public void setzeFormularInGPAEndern() {
        zuruecksetzen();
        setzeFormularInGPAEndernFuerButton();
//        Übergibt der Referenz des Hauptfensters das Internaframe
        this.hauptFenster.setzeComponent(this);
    }

    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    /**
     * Methode wird aufgerufen, wenn auf Button Anzeigen/Aendern geklickt wird,
     * das Formular wird nicht zurueckgesetzt, deswegen musste eine extra
     * Methode geschrieben werden.
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
        jB_Loeschen.setEnabled(true);
    }

    /*
     * Historie:
     * 07.01.2015   Sen     angelegt
     * 08.01.2015 Terrasi, implemtiereung der Referenzvariable "hauptfenster"
     */
    /**
     * Methode, die das Formular in die Sicht GP anzeigen aendert.
     */
    public void setzeFormularInGPAnzeigen() {
        zuruecksetzen();
        setzeFormularInGPAnzeigenFuerButton();
//      Übergibt der Referenz des Hauptfensters das Internaframe
        this.hauptFenster.setzeComponent(this);
    }

    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    /**
     * Methode wird aufgerufen, wenn auf Button Anzeigen/Aendern geklickt wird,
     * das Formular wird nicht zurueckgesetzt, deswegen musste eine extra
     * Methode geschrieben werden.
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
        jB_Loeschen.setEnabled(false);

    }

    /* Historie:
     * 17.01.2015   Schulz  Anlegen
     * 18.01.2015   Sen     uberarbeitet
     */
    /**
     * Methode fuer das Setzen alle Anschriftsfelder.
     *
     * @param anschriftDaten Die zu übergebenen Daten
     *
     */
    public void setzeAnschrift(Anschrift anschriftDaten) {
        this.jTF_StrasseRechnungsanschrift.setText(anschriftDaten.getStrasse());
        this.jTF_StrasseRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        this.jTF_HausnummerRechnungsanschrift.
                setText(anschriftDaten.getHausnummer());
        this.jTF_HausnummerRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        this.jTF_PLZRechnungsanschrift.setText(anschriftDaten.getPLZ());
        this.jTF_PLZRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
        this.jTF_OrtRechnungsanschrift.setText(anschriftDaten.getOrt());
        this.jTF_OrtRechnungsanschrift.setBackground(JTF_FARBE_STANDARD);
    }

    /*
     * Historie:
     * 17.01.2015   Sen     angelegt
     */
    /**
     * setzeMethode, die aus der Suche aufgerufen wird. Es wird die Sicht GP
     * Anzeigen aufgerufen und der gesuchte GP angezeigt.
     *
     * @param gp artikel aus der Suche
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
        jFTF_Erfassungsdatum
                .setText(gibDatumAusDatenbank(la.getErfassungsdatum()));
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

    /*
     * Historie:
     * 14.12.2014   Sen     angelegt
     */
    /**
     * Methode, die aus dem uebergebenem Date Objekt ein String mit dem
     * richtigen Format zureueckgibt.
     *
     * @param date date objekt, welches uebergeben wird
     * @return String Datum im richtigen Format (z.B. 01.01.2014)
     */
    private String gibDatumAusDatenbank(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
//        tag, monat, jahr werden aus dem Date Objekt herausgenommen
        int tag = cale.get(Calendar.DAY_OF_MONTH);
        int mon = cale.get(Calendar.MONTH);
//        auf den monat wird eine 1 addiert, da sie von 0 anfaengt,
//        d.h 0 = januar
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
    private javax.swing.JLabel jLabel24;
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
