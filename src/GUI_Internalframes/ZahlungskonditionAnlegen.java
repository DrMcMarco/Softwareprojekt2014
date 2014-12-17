package GUI_Internalframes;

import DAO.DataAccessObject;
import JFrames.GUIFactory;
import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Tahir
 *
 * 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button
 */
public class ZahlungskonditionAnlegen extends javax.swing.JInternalFrame {

    /*
     Hilfsvariablen
     */
    Component c;
    GUIFactory factory;
    DataAccessObject dao;
//  Insanzvariablen eines Artikels
    private int zknummer = 1;
    private int skontozeit1;
    private int skontozeit2;
    private int mahnzeitzeit1;
    private int mahnzeitzeit2;
    private int mahnzeitzeit3;

//  ArrayList, um fehlerhafte Componenten zu speichern.    
    private ArrayList<Component> fehlerhafteComponenten;
//  ArrayList, um angelegte Artikel zu speichern     
    public ArrayList<Component> zkListe;
//  Insantzvariablen für die standard Farben der Componenten    
    private final Color JCB_FARBE_STANDARD = new Color(214, 217, 223);
    private final Color JTF_FARBE_STANDARD = new Color(255, 255, 255);
//  Insantzvariablen für die Farben von fehlerhaften Componenten         
//    private final Color FARBE_FEHLERHAFT = new Color(255, 239, 219);
    private final Color FARBE_FEHLERHAFT = new Color(255, 165, 79);
//  Insantzvariablen für die Meldungen         
    private final String TITEL_PFLICHTFELDER = "Felder nicht ausgefüllt";
    private final String TEXT_PFLICHTFELDER = "Einige Felder wurden nicht ausgefüllt! Bitte füllen Sie diese aus!";
    private final String TEXT_LIEFERZEIT = "Das eingegebene Lieferdatumdatum liegt in der Vergangenheit! \nDas Lieferdatum muss in der Zukunft liegen.";
    private final String TITEL_FEHLERHAFTE_EINGABE = "Fehlerhafte Eingabe";
    private final String TEXT_LIEFERZEIT_2 = "Das eingegebene Lieferdatum ist in nicht gültig! \nBitte geben Sie ein gültiges Lieferdatm Datum ein. (z.B. 16.12.2014)";
    private final String TEXT_SPERRZEIT = "Die eingegebene Sperrzeit liegt in der Vergangenheit! \nDas Lieferdatum muss in der Zukunft liegen.";
    private final String TEXT_SPERRZEIT_2 = "Die eingegebene Sperrzeit ist in nicht gültig! \nBitte geben Sie eine gültige Sperrzeit ein.";
    private final String STATUSZEILE = "Zahlungskondition wurde angelegt!";

    private final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.mm.yyyy");

    Calendar cal = Calendar.getInstance();
    private String aktuellesDatum;

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     */
    public ZahlungskonditionAnlegen(GUIFactory factory) {
        initComponents();

        this.factory = factory;
        this.dao = factory.getDAO();

        fehlerhafteComponenten = new ArrayList<>();
        zkListe = new ArrayList<>();

        GregorianCalendar aktuellesDaum = new GregorianCalendar();
        DateFormat df_aktuellesDatum = DateFormat.getDateInstance(DateFormat.MEDIUM);    //05.12.2014     
        aktuellesDatum = df_aktuellesDatum.format(aktuellesDaum.getTime());

//        DateFormat df_jTF = DateFormat.getDateInstance();
//        df_jTF.setLenient(false);
////        DateFormatter dform1 = new DateFormatter(df_jTF);
//        DateFormatter dform2 = new DateFormatter(df_jTF);
////        dform1.setOverwriteMode(true);
//        dform2.setOverwriteMode(true);
////        dform1.setAllowsInvalid(false);
//        dform2.setAllowsInvalid(false);
////        DefaultFormatterFactory dff1 = new DefaultFormatterFactory(dform1);
//        DefaultFormatterFactory dff2 = new DefaultFormatterFactory(dform2);
////        jFTF_LieferzeitSOFORT.setFormatterFactory(dff1);
////        jFTF_LieferzeitSOFORT.setText(aktuellesDatum);
//        jFTF_SperrzeitWUNSCH.setFormatterFactory(dff2);
//        jFTF_SperrzeitWUNSCH.setText(aktuellesDatum);
        MaskFormatter mf = null;
        try {
            mf = new MaskFormatter("##.##.####");
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
//        mf.setValueContainsLiteralCharacters(false);
        mf.setPlaceholder("########");
        mf.setPlaceholderCharacter('#');
        mf.setOverwriteMode(true);
        DefaultFormatterFactory dff = new DefaultFormatterFactory(mf);
        jFTF_LieferzeitSOFORT.setFormatterFactory(dff);

        MaskFormatter mf1 = null;
        try {
            mf1 = new MaskFormatter("##.##.####");
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
//        mf.setValueContainsLiteralCharacters(false);
        mf1.setPlaceholder("########");
        mf1.setPlaceholderCharacter('#');
        mf1.setOverwriteMode(true);
        DefaultFormatterFactory dff1 = new DefaultFormatterFactory(mf1);
        jFTF_SperrzeitWUNSCH.setFormatterFactory(dff1);
    }

    /*
     * Methode für die Überprüfung der Daten. Falls ein Textfeld nicht gefüllt ist, wird sie der ArrayList für   
     * fehlerhafte Componenten hinzugefuegt. Falls bei eine Compobox der selektierte Index auf 0 ("Bitte auswählen")
     * steht, wird diese ebenfalls in die ArrayList uebernommen 
     */
    private void ueberpruefeFormular() {
        skontozeit1 = ((Number) jSP_Skontozeit1.getValue()).intValue();
        skontozeit2 = ((Number) jSP_Skontozeit2.getValue()).intValue();
        mahnzeitzeit1 = ((Number) jSP_Mahnzeit1.getValue()).intValue();
        mahnzeitzeit2 = ((Number) jSP_Mahnzeit2.getValue()).intValue();
        mahnzeitzeit3 = ((Number) jSP_Mahnzeit3.getValue()).intValue();

        if (jCB_Auftragsart.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Auftragsart);
        }
        if (jFTF_LieferzeitSOFORT.getText().equals("##.##.####")) {
            fehlerhafteComponenten.add(jFTF_LieferzeitSOFORT);
        }
        if (jFTF_SperrzeitWUNSCH.getText().equals("##.##.####")) {
            fehlerhafteComponenten.add(jFTF_SperrzeitWUNSCH);
        }
        if (skontozeit1 == 0) {
//            System.out.println(((JSpinner.NumberEditor) jSP_Skontozeit1.getEditor()).getTextField().getText());
//            System.out.println(jSP_Skontozeit1.getBackground());
            fehlerhafteComponenten.add(jSP_Skontozeit1);
            ((JSpinner.NumberEditor) jSP_Skontozeit1.getEditor()).getTextField().setBackground(FARBE_FEHLERHAFT);
//            System.out.println(((JSpinner.NumberEditor) jSP_Skontozeit1.getEditor()).getTextField().getBackground());
        }
        if (skontozeit2 == 0) {
            fehlerhafteComponenten.add(jSP_Skontozeit2);
        }
        if (jCB_Skonto1.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Skonto1);
        }
        if (jCB_Skonto2.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Skonto2);
        }
        if (mahnzeitzeit1 == 0) {
            fehlerhafteComponenten.add(jSP_Mahnzeit1);
        }
        if (mahnzeitzeit2 == 0) {
            fehlerhafteComponenten.add(jSP_Mahnzeit2);
        }
        if (mahnzeitzeit3 == 0) {
            fehlerhafteComponenten.add(jSP_Mahnzeit3);
        }
    }

    /*
     * Methode, die die Eingaben zurücksetzt, beim Zurücksetzen wird auch die Hintergrundfarbe zurückgesetzt. 
     */
    public final void setzeFormularZurueck() {
        jTF_ZahlungskonditionID.setText("" + zknummer);
        jCB_Auftragsart.setSelectedIndex(0);
        jCB_Auftragsart.setBackground(JCB_FARBE_STANDARD);
//        jFTF_LieferzeitSOFORT.setText(aktuellesDatum);
        jFTF_LieferzeitSOFORT.setBackground(JTF_FARBE_STANDARD);
//        jFTF_SperrzeitWUNSCH.setText(aktuellesDatum);
        jFTF_SperrzeitWUNSCH.setBackground(JTF_FARBE_STANDARD);
        jSP_Skontozeit1.setValue(0);
        jSP_Skontozeit1.setBackground(JTF_FARBE_STANDARD);
        jSP_Skontozeit2.setValue(0);
        jSP_Skontozeit2.setBackground(JTF_FARBE_STANDARD);
        jCB_Skonto1.setSelectedIndex(0);
        jCB_Skonto1.setBackground(JCB_FARBE_STANDARD);
        jCB_Skonto2.setSelectedIndex(0);
        jCB_Skonto2.setBackground(JCB_FARBE_STANDARD);
        jSP_Mahnzeit1.setValue(0);
        jSP_Mahnzeit1.setBackground(JTF_FARBE_STANDARD);
        jSP_Mahnzeit2.setValue(0);
        jSP_Mahnzeit2.setBackground(JTF_FARBE_STANDARD);
        jSP_Mahnzeit3.setValue(0);
        jSP_Mahnzeit3.setBackground(JTF_FARBE_STANDARD);
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
        jTF_Statuszeile = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jB_Zurueck = new javax.swing.JButton();
        jB_Abbrechen = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_Anzeigen = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jTF_ZahlungskonditionID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jCB_Auftragsart = new javax.swing.JComboBox();
        jFTF_SperrzeitWUNSCH = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jFTF_LieferzeitSOFORT = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jCB_Skonto1 = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jCB_Skonto2 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTF_Statuszeile1 = new javax.swing.JTextField();
        jSP_Skontozeit1 = new javax.swing.JSpinner();
        jSP_Skontozeit2 = new javax.swing.JSpinner();
        jSP_Mahnzeit1 = new javax.swing.JSpinner();
        jSP_Mahnzeit2 = new javax.swing.JSpinner();
        jSP_Mahnzeit3 = new javax.swing.JSpinner();

        jTextField4.setText("jTextField4");

        jTF_Statuszeile.setText("Statuszeile");
        jTF_Statuszeile.setEnabled(false);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Zahlungskonditonen anlegen");
        setPreferredSize(new java.awt.Dimension(500, 720));
        setVisible(true);

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Auftragsart:");

        jTF_ZahlungskonditionID.setText("1");
        jTF_ZahlungskonditionID.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Lieferzeit SOFORT:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Skontozeit 2:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Mahnzeit 1:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Skontozeit 1:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Skonto 1:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Skonto 2:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Zahlungskondition-ID:");

        jCB_Auftragsart.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "Barauftrag", "Sofortauftrag", "Terminauftrag", "Bestellauftrag" }));
        jCB_Auftragsart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_AuftragsartActionPerformed(evt);
            }
        });

        jFTF_SperrzeitWUNSCH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTF_SperrzeitWUNSCHFocusLost(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Sperrzeit WUNSCH:");

        jFTF_LieferzeitSOFORT.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jFTF_LieferzeitSOFORT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTF_LieferzeitSOFORTFocusLost(evt);
            }
        });
        jFTF_LieferzeitSOFORT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFTF_LieferzeitSOFORTActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Mahnzeit 2:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Mahnzeit 3:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Tage");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Tage");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Tage");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Tage");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Tage");

        jCB_Skonto1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "1,0", "2,0", "3,0" }));
        jCB_Skonto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_Skonto1ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("%");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("%");

        jCB_Skonto2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "1,0", "2,0", "3,0" }));
        jCB_Skonto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_Skonto2ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Skontodaten:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Mahnzeiten:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Allgemeine Daten:");

        jTF_Statuszeile1.setText("Statuszeile");
        jTF_Statuszeile1.setEnabled(false);

        jSP_Skontozeit1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 31, 1));
        jSP_Skontozeit1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Skontozeit1PropertyChange(evt);
            }
        });

        jSP_Skontozeit2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 31, 1));
        jSP_Skontozeit2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Skontozeit2PropertyChange(evt);
            }
        });

        jSP_Mahnzeit1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 31, 1));
        jSP_Mahnzeit1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Mahnzeit1PropertyChange(evt);
            }
        });

        jSP_Mahnzeit2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 31, 1));
        jSP_Mahnzeit2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Mahnzeit2PropertyChange(evt);
            }
        });

        jSP_Mahnzeit3.setModel(new javax.swing.SpinnerNumberModel(0, 0, 31, 1));
        jSP_Mahnzeit3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Mahnzeit3PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel20)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(67, 67, 67)
                        .addComponent(jSP_Mahnzeit1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(67, 67, 67)
                        .addComponent(jSP_Mahnzeit2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(jTF_ZahlungskonditionID, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jCB_Auftragsart, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(27, 27, 27)
                        .addComponent(jFTF_LieferzeitSOFORT, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(24, 24, 24)
                        .addComponent(jFTF_SperrzeitWUNSCH, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(76, 76, 76)
                        .addComponent(jCB_Skonto1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(76, 76, 76)
                        .addComponent(jCB_Skonto2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(67, 67, 67)
                        .addComponent(jSP_Mahnzeit3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSP_Skontozeit1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSP_Skontozeit2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(119, Short.MAX_VALUE))
            .addComponent(jTF_Statuszeile1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel8))
                    .addComponent(jTF_ZahlungskonditionID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jCB_Auftragsart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2))
                    .addComponent(jFTF_LieferzeitSOFORT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel9))
                    .addComponent(jFTF_SperrzeitWUNSCH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jSP_Skontozeit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel12)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jSP_Skontozeit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jCB_Skonto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel17)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCB_Skonto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel18))))
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jSP_Mahnzeit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jSP_Mahnzeit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jSP_Mahnzeit3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jTF_Statuszeile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jCB_AuftragsartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_AuftragsartActionPerformed
        if (jCB_Auftragsart.getSelectedIndex() != 0) {
            jCB_Auftragsart.setBackground(JCB_FARBE_STANDARD);
            jTF_Statuszeile1.setText("");
        }
    }//GEN-LAST:event_jCB_AuftragsartActionPerformed

    /*
     * Methode, die prüft, ob das eingegebene Lieferdatum gültig ist. 
     */
    private void jFTF_LieferzeitSOFORTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTF_LieferzeitSOFORTFocusLost
        String eingabeLieferzeit = jFTF_LieferzeitSOFORT.getText();
        String eingabeJahr = eingabeLieferzeit.substring(6, eingabeLieferzeit.length());
        if (!jFTF_LieferzeitSOFORT.getText().equals("##.##.####")) {

            if (eingabeJahr.length() == 4 && eingabeJahr.startsWith("20")) {
                try {
                    Date tempAktDate = FORMAT.parse(aktuellesDatum);
                    Date tempLiefDate = FORMAT.parse(eingabeLieferzeit);
//                ?
//                ? 
//                ?
                    if (tempAktDate.getTime() > tempLiefDate.getTime()) {
                        JOptionPane.showMessageDialog(null, TEXT_LIEFERZEIT, TITEL_FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
                        jFTF_LieferzeitSOFORT.requestFocusInWindow();
                        jFTF_LieferzeitSOFORT.selectAll();
//                        jFTF_LieferzeitSOFORT.removeAll();
                        jFTF_LieferzeitSOFORT.setValue(null);
                    } else {
                        jFTF_LieferzeitSOFORT.setBackground(JTF_FARBE_STANDARD);
                    }
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                // eingabe Ungültig z.B. 19999;
                JOptionPane.showMessageDialog(null, TEXT_LIEFERZEIT_2 + "(z.B. " + aktuellesDatum + ")", TITEL_FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
                jFTF_LieferzeitSOFORT.requestFocusInWindow();
//                jFTF_LieferzeitSOFORT.setText("##.##.####");
                jFTF_LieferzeitSOFORT.setValue(null);
            }
        } else {
//            jFTF_LieferzeitSOFORT.setText("12.12.2018");
            jFTF_LieferzeitSOFORT.setValue(null);
        }
    }//GEN-LAST:event_jFTF_LieferzeitSOFORTFocusLost

    /*
     * Methode, die prüft, ob das eingegebene Sperrdatum gültig ist. 
     */
    private void jFTF_SperrzeitWUNSCHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTF_SperrzeitWUNSCHFocusLost
        String eingabeSperrzeit = jFTF_SperrzeitWUNSCH.getText();
        String eingabeJahr = eingabeSperrzeit.substring(6, eingabeSperrzeit.length());
        if (!jFTF_SperrzeitWUNSCH.getText().equals("##.##.####")) {
            if (eingabeJahr.length() == 4 && eingabeJahr.startsWith("20")) {
                try {
                    Date tempAktDate = FORMAT.parse(aktuellesDatum);
                    Date tempLiefDate = FORMAT.parse(eingabeSperrzeit);
//                ?
//                ? 
//                ?
                    if (tempAktDate.getTime() > tempLiefDate.getTime()) {
                        JOptionPane.showMessageDialog(null, TEXT_SPERRZEIT, TITEL_FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
                        jFTF_SperrzeitWUNSCH.requestFocusInWindow();
//                        jFTF_SperrzeitWUNSCH.setText("##.##.####");
                        jFTF_SperrzeitWUNSCH.setValue(null);
                    } else {
                        jFTF_SperrzeitWUNSCH.setBackground(JTF_FARBE_STANDARD);
                    }
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                // eingabe Ungültig z.B. 19999;
                JOptionPane.showMessageDialog(null, TEXT_SPERRZEIT_2 + "(z.B. " + aktuellesDatum + ")", TITEL_FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
                jFTF_SperrzeitWUNSCH.requestFocusInWindow();
//                jFTF_SperrzeitWUNSCH.setText("##.##.####");
                jFTF_SperrzeitWUNSCH.setValue(null);
            }
        } else {
//            jFTF_SperrzeitWUNSCH.setText("##.##.####");
            jFTF_SperrzeitWUNSCH.setValue(null);
        }
//        if (!jFTF_SperrzeitWUNSCH.getText().equals("")) {
//            jFTF_SperrzeitWUNSCH.setBackground(JTF_FARBE_STANDARD);
//        }
    }//GEN-LAST:event_jFTF_SperrzeitWUNSCHFocusLost

    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jSP_Skontozeit1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Skontozeit1PropertyChange
        jSP_Skontozeit1.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Skontozeit1PropertyChange

    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jSP_Skontozeit2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Skontozeit2PropertyChange
        jSP_Skontozeit2.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Skontozeit2PropertyChange

    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jCB_Skonto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_Skonto1ActionPerformed
        if (jCB_Skonto1.getSelectedIndex() != 0) {
            jCB_Skonto1.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_Skonto1ActionPerformed

    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jCB_Skonto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_Skonto2ActionPerformed
        if (jCB_Skonto2.getSelectedIndex() != 0) {
            jCB_Skonto2.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_Skonto2ActionPerformed

    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jSP_Mahnzeit1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Mahnzeit1PropertyChange
        jSP_Mahnzeit1.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Mahnzeit1PropertyChange

    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jSP_Mahnzeit2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Mahnzeit2PropertyChange
        jSP_Mahnzeit2.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Mahnzeit2PropertyChange

    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jSP_Mahnzeit3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Mahnzeit3PropertyChange
        jSP_Mahnzeit3.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Mahnzeit3PropertyChange

    /*
     * Methode für das Speichern der Daten. 
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed
        //      zunaechst werdne die Eingaben ueberprueft.    
        ueberpruefeFormular();
//      falls fehlerhafteComponenten leer ist (es sind keine fehlerhaften Componenten verfuegbar), 
//      werden die Eingaben in die entsprechenden Variablen gespeichert
        if (fehlerhafteComponenten.isEmpty()) {

            String auftragsart = (String) jCB_Auftragsart.getSelectedItem();
            String lieferzeitSOFORT = jFTF_LieferzeitSOFORT.getText();
            String sperrzeitWUNSCH = jFTF_SperrzeitWUNSCH.getText();
            String skonto1 = (String) jCB_Skonto1.getSelectedItem();
            String skonto2 = (String) jCB_Skonto2.getSelectedItem();
//          Artikel wird in ArrayList für Artikel hinzugefuegt     

//            zkListe.add(new Zahlungskondition("" + zknummer, auftragsart, lieferzeitSOFORT, sperrzeitWUNSCH, "" + skontozeit1, "" + skontozeit2, skonto1, skonto2, "" + mahnzeitzeit1, "" + mahnzeitzeit2, "" + mahnzeitzeit3));
//          provisorisch wird der Artikel ausgegeben  
//            System.out.println(zkListe.get(zkListe.size() - 1).toString());
//          die artikelnummer wird erhöht  
//            zknummer++;
            System.out.println(STATUSZEILE);
            jTF_Statuszeile1.setText(STATUSZEILE);
//          das Formular wird zurueckgesetzt  
            setzeFormularZurueck();
        } else {
//          fehlerhafteComponenten ist nicht leer (es sind fehlerhafte Componenten vorhanden)
//          eine Meldung wird ausgegeben  
            JOptionPane.showMessageDialog(null, TEXT_PFLICHTFELDER, TITEL_PFLICHTFELDER, JOptionPane.ERROR_MESSAGE);
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

    private void jFTF_LieferzeitSOFORTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTF_LieferzeitSOFORTActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jFTF_LieferzeitSOFORTActionPerformed

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
    }//GEN-LAST:event_jB_ZurueckActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_Abbrechen;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JComboBox jCB_Auftragsart;
    private javax.swing.JComboBox jCB_Skonto1;
    private javax.swing.JComboBox jCB_Skonto2;
    private javax.swing.JFormattedTextField jFTF_LieferzeitSOFORT;
    private javax.swing.JFormattedTextField jFTF_SperrzeitWUNSCH;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner jSP_Mahnzeit1;
    private javax.swing.JSpinner jSP_Mahnzeit2;
    private javax.swing.JSpinner jSP_Mahnzeit3;
    private javax.swing.JSpinner jSP_Skontozeit1;
    private javax.swing.JSpinner jSP_Skontozeit2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTF_Statuszeile;
    private javax.swing.JTextField jTF_Statuszeile1;
    private javax.swing.JTextField jTF_ZahlungskonditionID;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
