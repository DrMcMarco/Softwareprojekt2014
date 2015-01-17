package GUI_Internalframes;

import DAO.ApplicationException;
import DAO.DataAccessObject;
import DTO.Zahlungskondition;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import Interfaces.InterfaceMainView;
import Interfaces.InterfaceViewsFunctionality;

/**
 *
 * @author Tahir * Klassenhistorie: 27.11.2014 Sen, angelegt 28.11.2014 Sen,
 * textfelder, Comboboxe, Buttons angelegt 01.12.2014 Sen, grundlegende
 * Funktionen implementiert 02.12.2014 Sen, beendenNachfrage() und
 * ueberprufeFormular() Methoden implementiert 05.12.2014 Sen,
 * setzteFormularZurueck() und ..focusLost() Methoden implementiert 08.12.2014
 * Sen, angelegt Methoden erweitert 07.12.2014 Sen, Componenten mit leben
 * befuellt 10.12.2014 Sen, grundlegenden Ueberarbeitung der Maske, Fehler
 * korigiert 11.12.2014 Sen, taskleiste implementiert und funktionen erweitert
 * 15.12.2014 Sen, ArtikelAnlegen Sicht zum groeßten Teils implementiert
 * 17.12.2014 Sen, speichern Button impelementiert, ein Aritkel kann nun in die
 * Datenbank geschrieben werden 19.12.2014 Terrasi, Funktionsimplementierung im
 * "Zurück"-Button der Schnittstelle für InternalFrames 20.12.2014 Sen,
 * ArtikelAnlegen in AritkelAENdern Funktion angefangen 25.12.2014 Sen, methode
 * zum Ändern von ArtikelAnlegen in ArtikelÄndern implementiert 26.12.2014 Sen,
 * ArtikelAnlegen in AritkelAnzeigen Funktion angefangen 01.01.2015 Sen, Methode
 * zum Ändern von ArtikelAnlegen in ArtikelAnzeigen implementiert 02.01.2015
 * Sen, Löschen von Artikel Funktion implementiert 07.01.2015 Sen, Löschen von
 * Artikel Funktion Fehler korriegiert 08.01.2015 Terrasi, Implementierung der
 * Anzeigen/Ändern Funktion, hinzufügen 12.01.2015 Sen, Artikel aus Datenbank
 * laden und diese anzegien lassen angelegt, bzw Felder mit den Daten der
 * Artikel befuellt 14.01.2015 Sen, ArtikelAendern speichern Funktion angefangen
 * 15.01.2015 Sen, ArtikelAendern speichern Funktion anbgeschlossen
 *
 * 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button 08.01.2015
 * Terrasi, Implementierung der Anzeigen/Ändern Funktion, hinzufügen der
 * Schnittstelle für InternalFrames
 */
public class ZahlungskonditionAnlegen extends javax.swing.JInternalFrame {

    /*
     Hilfsvariablen
     */
    Component c;
    GUIFactory factory;
    DataAccessObject dao;
    InterfaceMainView hauptFenster;

//  Insanzvariablen eines Artikels
    private int zknummer;
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
    private final Color FARBE_FEHLERHAFT = Color.YELLOW;
//  Insantzvariablen für die Meldungen         
    private final String TITEL_PFLICHTFELDER = "Felder nicht ausgefüllt";
    private final String TEXT_PFLICHTFELDER = "Einige Felder wurden nicht ausgefüllt! Bitte füllen Sie diese aus!";
//    private final String TEXT_LIEFERZEIT = "Das eingegebene Lieferdatumdatum liegt in der Vergangenheit! \nDas Lieferdatum muss in der Zukunft liegen.";
    private final String TEXT_LIEFERZEIT = "Bitte geben Sie eine zweistellige Lieferzeit ein!";
    private final String TITEL_FEHLERHAFTE_EINGABE = "Fehlerhafte Eingabe";
    private final String TEXT_LIEFERZEIT_2 = "Das eingegebene Lieferdatum ist in nicht gültig! \nBitte geben Sie ein gültiges Lieferdatm Datum ein. (z.B. 16.12.2014)";
//    private final String TEXT_SPERRZEIT = "Die eingegebene Sperrzeit liegt in der Vergangenheit! \nDas Lieferdatum muss in der Zukunft liegen.";
    private final String TEXT_SPERRZEIT = "Bitte geben Sie eine zweistellige Sperrzeit ein!";
    private final String TEXT_SPERRZEIT_2 = "Die eingegebene Sperrzeit ist in nicht gültig! \nBitte geben Sie eine gültige Sperrzeit ein.";
    private final String STATUSZEILE = "Zahlungskondition wurde angelegt!";

    private final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.mm.yyyy");

    Calendar cal = Calendar.getInstance();
    private String aktuellesDatum;

    private final String PRUEFUNG_TAGE = "|[0-9]{1,2}";
    private NumberFormat nf;

    private final int anzahlFehlerhafterComponenten = 3;

    private boolean sichtAnlegen;
    private boolean sichtAEndern;
    private boolean sichtAnzeigen;

    private final String ZK_ANLEGEN = "Zahlungskondition anlegen";
    private final String ZK_AENDERN = "Zahlungskondition ändern";
    private final String ZK_ANZEIGEN = "Zahlungskondition anzeigen";

    int i = 0;

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     */
    public ZahlungskonditionAnlegen(GUIFactory factory, InterfaceMainView mainView) {
        initComponents();
        this.hauptFenster = mainView;
        this.nf = NumberFormat.getInstance();
        this.factory = factory;
        this.dao = factory.getDAO();
        this.fehlerhafteComponenten = new ArrayList<>();
        this.zkListe = new ArrayList<>();
    }

    /*
     * Methode für die Überprüfung der Daten. Falls ein Textfeld nicht gefüllt ist, wird sie der ArrayList für   
     * fehlerhafte Componenten hinzugefuegt. Falls bei eine Compobox der selektierte Index auf 0 ("Bitte auswählen")
     * steht, wird diese ebenfalls in die ArrayList uebernommen 
     */
    private void ueberpruefeFormular() {
        if (jCB_Auftragsart.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Auftragsart);
        }
        if (jCB_Skonto1.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Skonto1);
        }
        if (jCB_Skonto2.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Skonto2);
        }
    }

    /*
     * Methode, die die Eingaben zurücksetzt, beim Zurücksetzen wird auch die Hintergrundfarbe zurückgesetzt. 
     */
    public final void setzeFormularZurueck() {
        zknummer = this.factory.getDAO().gibNaechsteZahlungskonditionID();
        jTF_ZahlungskonditionID.requestFocus();
        jTF_ZahlungskonditionID.setText("" + zknummer);
        jCB_Auftragsart.setSelectedIndex(0);
        jCB_Auftragsart.setBackground(JCB_FARBE_STANDARD);
        jSP_LieferzeitSOFORT.setValue(1);
        jSP_SperrzeitWUNSCH.setValue(1);
        jSP_Skontozeit1.setValue(1);
        jSP_Skontozeit2.setValue(1);
        jCB_Skonto1.setSelectedIndex(0);
        jCB_Skonto1.setBackground(JCB_FARBE_STANDARD);
        jCB_Skonto2.setSelectedIndex(0);
        jCB_Skonto2.setBackground(JCB_FARBE_STANDARD);
        jSP_Mahnzeit1.setValue(1);
        jSP_Mahnzeit2.setValue(1);
        jSP_Mahnzeit3.setValue(1);

        fehlerhafteComponenten.clear();
    }

    private void beendenEingabeNachfrage() {
        if (this.getTitle().equals(ZK_ANLEGEN) || this.getTitle().equals(ZK_AENDERN)) {
            String meldung = "Möchten Sie die Eingaben verwerfen? Klicken Sie"
                    + " auf JA, wenn Sie die Eingaben verwerfen möchten.";
            String titel = "Achtung Eingaben gehen verloren!";
            ueberpruefeFormular();
            if (this.getTitle().equals(ZK_AENDERN)) {
                meldung = "Möchten Sie die Sicht Artikel ändern verlassen?";
                titel = "Artikel ändern verlassen";
            }
            if (fehlerhafteComponenten.size() < anzahlFehlerhafterComponenten) {
                int antwort = JOptionPane.showConfirmDialog(null, meldung, titel, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (antwort == JOptionPane.YES_OPTION) {
                    this.setVisible(false);
                    setzeFormularZurueck();
                    jB_ZurueckActionPerformed(null);
                } else {
                    fehlerhafteComponenten.clear();
                }
            } else {
                this.setVisible(false);
                setzeFormularZurueck();
            }
        } else {
            this.setVisible(false);
            setzeFormularZurueck();
            jB_ZurueckActionPerformed(null);

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
        jLabel9 = new javax.swing.JLabel();
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
        jSP_Skontozeit1 = new javax.swing.JSpinner();
        jSP_Skontozeit2 = new javax.swing.JSpinner();
        jSP_Mahnzeit1 = new javax.swing.JSpinner();
        jSP_Mahnzeit2 = new javax.swing.JSpinner();
        jSP_Mahnzeit3 = new javax.swing.JSpinner();
        jSP_LieferzeitSOFORT = new javax.swing.JSpinner();
        jSP_SperrzeitWUNSCH = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setTitle("Zahlungskonditonen anlegen");
        setPreferredSize(new java.awt.Dimension(500, 720));
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

        jCB_Auftragsart.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "Sofortauftrag", "Terminauftrag", "Bestellauftrag" }));
        jCB_Auftragsart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_AuftragsartActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Sperrzeit WUNSCH:");

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

        jSP_Skontozeit1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        jSP_Skontozeit1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Skontozeit1PropertyChange(evt);
            }
        });

        jSP_Skontozeit2.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        jSP_Skontozeit2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Skontozeit2PropertyChange(evt);
            }
        });

        jSP_Mahnzeit1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        jSP_Mahnzeit1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Mahnzeit1PropertyChange(evt);
            }
        });

        jSP_Mahnzeit2.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        jSP_Mahnzeit2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Mahnzeit2PropertyChange(evt);
            }
        });

        jSP_Mahnzeit3.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        jSP_Mahnzeit3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Mahnzeit3PropertyChange(evt);
            }
        });

        jSP_LieferzeitSOFORT.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        jSP_SperrzeitWUNSCH.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

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
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel2))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSP_LieferzeitSOFORT, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSP_SperrzeitWUNSCH, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(119, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSP_LieferzeitSOFORT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jSP_SperrzeitWUNSCH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(123, Short.MAX_VALUE))
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
        }
    }//GEN-LAST:event_jCB_AuftragsartActionPerformed

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
            int lieferzeitSOFORT = 0;
            int sperrzeitWunsch = 0;
            int skontozeit1 = 0;
            int skontozeit2 = 0;
            double skonto1 = 0;
            double skonto2 = 0;
            int mahnzeit1 = 0;
            int mahnzeit2 = 0;
            int mahnzeit3 = 0;
            long zknr = 0;

            try {
                zknr = nf.parse(jTF_ZahlungskonditionID.getText()).longValue();
                lieferzeitSOFORT = ((Number) jSP_LieferzeitSOFORT.getValue()).intValue();
                sperrzeitWunsch = ((Number) jSP_SperrzeitWUNSCH.getValue()).intValue();
                skonto1 = nf.parse((String) jCB_Skonto1.getSelectedItem()).doubleValue();
                skonto2 = nf.parse((String) jCB_Skonto2.getSelectedItem()).doubleValue();
                skontozeit1 = ((Number) jSP_Skontozeit1.getValue()).intValue();
                skontozeit2 = ((Number) jSP_Skontozeit2.getValue()).intValue();
                mahnzeit1 = ((Number) jSP_Mahnzeit1.getValue()).intValue();
                mahnzeit2 = ((Number) jSP_Mahnzeit2.getValue()).intValue();
                mahnzeit3 = ((Number) jSP_Mahnzeit3.getValue()).intValue();

                if (this.getTitle().equals(ZK_ANLEGEN)) {
                    if (lieferzeitSOFORT != 1 || sperrzeitWunsch != 1 || skontozeit1 != 1 || skontozeit2 != 1 || mahnzeit1 != 1 || mahnzeit2 != 1 || mahnzeit3 != 1) {
                        this.dao.createPaymentConditions(auftragsart, lieferzeitSOFORT,
                                sperrzeitWunsch, skontozeit1, skontozeit2, skonto1,
                                skonto2, mahnzeit1, mahnzeit2, mahnzeit3);
                        setzeFormularZurueck();
                    } else {
                        String meldung = "Liefer- und Sperrzeit sowie Skonto- und Mahnzeiten sind alle standardmäßig auf 1 Tag gesetzt. Möchten Sie nun speichern?"
                                + "\nKlicken Sie auf Nein, wenn Sie die Eingaben bearbeiten möchten.";
                        String titel = "Zahlungskondititon speichern";
                        int antwort = JOptionPane.showConfirmDialog(null, meldung, titel, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (antwort == JOptionPane.YES_OPTION) {
                            this.dao.createPaymentConditions(auftragsart, lieferzeitSOFORT,
                                    sperrzeitWunsch, skontozeit1, skontozeit2, skonto1,
                                    skonto2, mahnzeit1, mahnzeit2, mahnzeit3);
                            setzeFormularZurueck();
                        } else {
                            fehlerhafteComponenten.clear();
                        }
                    }
                } else {
                    Zahlungskondition ZKVorher = this.dao.getPaymentConditionsById(zknr);
                    Zahlungskondition ZKNachher = new Zahlungskondition(auftragsart, lieferzeitSOFORT,
                            sperrzeitWunsch, skontozeit1, skontozeit2, skonto1, skonto2, mahnzeit1,
                            mahnzeit2, mahnzeit3);
                    if (ZKVorher.equals(ZKNachher)) {
                        JOptionPane.showMessageDialog(null, "Es wurden keine Änderungen gemacht!", "Keine Änderungen", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        int antwort = JOptionPane.showConfirmDialog(null, "Möchten Sie die Änderungen speichern?", ZK_AENDERN, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (antwort == JOptionPane.YES_OPTION) { // falls ja,
                            try { // Geschäftspartner verändern
                                this.dao.aendereZahlungskondition(zknr, auftragsart, lieferzeitSOFORT,
                                        sperrzeitWunsch, skontozeit1, skontozeit2, skonto1, skonto2,
                                        mahnzeit1, mahnzeit2, mahnzeit3);
                            } catch (ApplicationException e) { // falls ein Fehler auftaucht
                                System.out.println("Fehler beim veraendern der ZK in Sicht ZK Anlegen Methode speichern: " + e.getMessage());
                            }
                            setzeFormularZurueck(); // Formular zuruecksetzen
                            this.setVisible(false); // diese Sicht ausblenden 
                            jB_ZurueckActionPerformed(null); // Button Zurueck Action ausführen
                        } else {
                            fehlerhafteComponenten.clear(); // Nein button wird geklickt, keine Aktion nur fehlerhafte Komponenten müssen geleert werden
                        }
                    }
                }

//          Artikel wird in ArrayList für Artikel hinzugefuegt     
            } catch (ParseException | ApplicationException ex) {
                System.out.println(ex.getMessage());
            }
//          das Formular wird zurueckgesetzt  
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

    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
//        System.out.println(((JSpinner.DefaultEditor) jSP_Mahnzeit1.getEditor()).getTextField().getBackground());
//        ((JSpinner.DefaultEditor) jSP_Mahnzeit1.getEditor()).getTextField().setBackground(Color.RED);
        JTextField tf = ((JSpinner.DefaultEditor) jSP_Mahnzeit1.getEditor()).getTextField();
        tf.setDisabledTextColor(Color.red);
//        tf.setText("12");
        System.out.println(((JSpinner.DefaultEditor) jSP_Mahnzeit1.getEditor()).getTextField().getBackground());
    }//GEN-LAST:event_jB_SuchenActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        beendenEingabeNachfrage();
    }//GEN-LAST:event_formInternalFrameClosing

    private void jB_LoeschenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_LoeschenActionPerformed
        try {
            long zknr = nf.parse(jTF_ZahlungskonditionID.getText()).longValue();
            int antwort = JOptionPane.showConfirmDialog(null, "Soll die Zahlungskondition wirklich gelöscht werden?", "Zahlungskondition löschen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (antwort == JOptionPane.YES_OPTION) {
                factory.getDAO().loescheZahlungskondition(zknr);
                jB_ZurueckActionPerformed(evt);
            }
        } catch (ParseException | ApplicationException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_jB_LoeschenActionPerformed

    public JTextField gibjTF_ZahlungskonditionID() {
        return jTF_ZahlungskonditionID;
    }

    public JComboBox gibjCB_Auftragsart() {
        return jCB_Auftragsart;
    }

    public JSpinner gibjSP_LieferzeitSOFORT() {
        return jSP_LieferzeitSOFORT;
    }

    public JSpinner gibjSP_SperrzeitWUNSCH() {
        return jSP_SperrzeitWUNSCH;
    }

    public JSpinner gibjSP_Skontozeit1() {
        return jSP_Skontozeit1;
    }

    public JSpinner gibjSP_Skontozeit2() {
        return jSP_Skontozeit2;
    }

    public JComboBox gibjCB_Skonto1() {
        return jCB_Skonto1;
    }

    public JComboBox gibjCB_Skonto2() {
        return jCB_Skonto2;
    }

    public JSpinner gibjSP_Mahnzeit1() {
        return jSP_Mahnzeit1;
    }

    public JSpinner gibjSP_Mahnzeit2() {
        return jSP_Mahnzeit2;
    }

    public JSpinner gibjSP_Mahnzeit3() {
        return jSP_Mahnzeit3;
    }
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, implemtiereung der Referenzvariable "hauptfenster"*/
    /*----------------------------------------------------------*/

    public void setzeFormularInZKAnlegen() {
        setzeFormularZurueck();
        this.setTitle(ZK_ANLEGEN);
        jCB_Auftragsart.setEnabled(true);
        jSP_LieferzeitSOFORT.setEnabled(true);
        jSP_SperrzeitWUNSCH.setEnabled(true);
        jSP_Skontozeit1.setEnabled(true);
        jSP_Skontozeit2.setEnabled(true);
        jCB_Skonto1.setEnabled(true);
        jCB_Skonto2.setEnabled(true);
        jSP_Mahnzeit1.setEnabled(true);
        jSP_Mahnzeit2.setEnabled(true);
        jSP_Mahnzeit3.setEnabled(true);

        jB_Speichern.setEnabled(true);
        jB_AnzeigenAEndern.setEnabled(false);
        jB_Loeschen.setEnabled(false);

        this.hauptFenster.setComponent(this);//Übergibt der Referenz des Hauptfensters das Internaframe
    }

    public void setzeFormularInZKAEndern() {
        setzeFormularZurueck();
        this.setTitle(ZK_AENDERN);
        jCB_Auftragsart.setEnabled(true);
        jSP_LieferzeitSOFORT.setEnabled(true);
        jSP_SperrzeitWUNSCH.setEnabled(true);
        jSP_Skontozeit1.setEnabled(true);
        jSP_Skontozeit2.setEnabled(true);
        jCB_Skonto1.setEnabled(true);
        jCB_Skonto2.setEnabled(true);
        jSP_Mahnzeit1.setEnabled(true);
        jSP_Mahnzeit2.setEnabled(true);
        jSP_Mahnzeit3.setEnabled(true);

        jB_Speichern.setEnabled(true);
        jB_AnzeigenAEndern.setEnabled(true);
        jB_Loeschen.setEnabled(true);

        this.hauptFenster.setComponent(this);//Übergibt der Referenz des Hauptfensters das Internaframe
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, implemtiereung der Referenzvariable "hauptfenster"*/
    /*----------------------------------------------------------*/
    public void setzeFormularInZKAnzeigen() {
        setzeFormularZurueck();
        this.setTitle(ZK_ANZEIGEN);
        jCB_Auftragsart.setEnabled(false);
        jSP_LieferzeitSOFORT.setEnabled(false);
        jSP_SperrzeitWUNSCH.setEnabled(false);
        jSP_Skontozeit1.setEnabled(false);
        jSP_Skontozeit2.setEnabled(false);
        jCB_Skonto1.setEnabled(false);
        jCB_Skonto2.setEnabled(false);
        jSP_Mahnzeit1.setEnabled(false);
        jSP_Mahnzeit2.setEnabled(false);
        jSP_Mahnzeit3.setEnabled(false);

        jB_Speichern.setEnabled(false);
        jB_AnzeigenAEndern.setEnabled(true);
        jB_Loeschen.setEnabled(false);

        this.hauptFenster.setComponent(this);//Übergibt der Referenz des Hauptfensters das Internaframe
    }

//   setzeMethode, die aus der Suche aufgerufen wird. Es wird die Sicht ZK Anzeigen aufgerufen und die gesuchte ZK angezeigt
    /**
     * 
     * @param zk 
     * Datum Name Was 17.01.2015 SEN angelegt
     */
    public void zeigeZKausSucheAn(Zahlungskondition zk) {
        setzeFormularInZKAnzeigen();
        jTF_ZahlungskonditionID.setText("" + zk.getZahlungskonditionID());
        jCB_Auftragsart.setSelectedItem(zk.getAuftragsart());
        jSP_LieferzeitSOFORT.setValue(zk.getLieferzeitSofort());
        jSP_SperrzeitWUNSCH.setValue(zk.getSperrzeitWunsch());
        jSP_Skontozeit1.setValue(zk.getSkontozeit1());
        jSP_Skontozeit2.setValue(zk.getSkontozeit2());
        jCB_Skonto1.setSelectedItem(("" + zk.getSkonto1()).replace('.', ','));
        jCB_Skonto2.setSelectedItem(("" + zk.getSkonto2()).replace('.', ','));
        jSP_Mahnzeit1.setValue(zk.getMahnzeit1());
        jSP_Mahnzeit2.setValue(zk.getMahnzeit2());
        jSP_Mahnzeit3.setValue(zk.getMahnzeit3());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_AnzeigenAEndern;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JComboBox jCB_Auftragsart;
    private javax.swing.JComboBox jCB_Skonto1;
    private javax.swing.JComboBox jCB_Skonto2;
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
    private javax.swing.JSpinner jSP_LieferzeitSOFORT;
    private javax.swing.JSpinner jSP_Mahnzeit1;
    private javax.swing.JSpinner jSP_Mahnzeit2;
    private javax.swing.JSpinner jSP_Mahnzeit3;
    private javax.swing.JSpinner jSP_Skontozeit1;
    private javax.swing.JSpinner jSP_Skontozeit2;
    private javax.swing.JSpinner jSP_SperrzeitWUNSCH;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTF_ZahlungskonditionID;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
