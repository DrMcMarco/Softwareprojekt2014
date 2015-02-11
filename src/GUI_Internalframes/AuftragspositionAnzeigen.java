/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.Auftragsposition;
import Documents.*;
import Interfaces.*;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import JFrames.*;
import java.util.Calendar;

/**
 *
 * @author Luca Terrasi
 *
 * 10.12.2014 Dokumentation und Logik 16.12.2014 Terrasi,
 * Funktionsimplementierung im "Zurück"-Button 06.01.2015 Terrasi,
 * Anwendungslogik für das ändern und anzeigen von Auftragspositionen.
 * 08.01.2015 Terrasi, Überarbeitung der Anwendungslogik für anzeigen/ändern
 * Status und das hinzufügen von weiteren Funktion. 17.01.2015 Terrasi, erstelln
 * von Setter-Methoden um Werte den Eingabefelder zu übergeben.
 */
public class AuftragspositionAnzeigen extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {

    /*
     Varibalendefinition
     */
    public Date heute;// heutiges Datum
    public SimpleDateFormat format; //Umwandler für Datum
    Component c;
    GUIFactory factory;
    InterfaceMainView hauptFenster;
    Component letzteComponent;
    String mengenAngabe;
    /*
     Speichervariablen
     */
    ArrayList<Component> fehlendeEingaben;// ArrayList für Eingabefelder des Auftragkopfes.

    Auftragsposition dbPosition;

    String dbAuftragspositionsID;
    String dbPositionsnummer;
    String dbMaterialnummer;
    String dbMenge;
    String dbEinzelwert;
    String dbErfassungsdatum;

    boolean gespeichert = false;

    /*
     Variablen für Farben
     */
    Color warningfarbe = Color.YELLOW;
    Color hintergrundfarbe = Color.WHITE;

    /*
     Syntax
     */
    private static final String auftragspositionsID_syntax = "|\\d{1,9}?";
    private static final String positionsnummer_syntax = "|\\d{1,9}?";
    private static final String material_syntax = "|\\d{1,9}?";
    private static final String menge_syntax = "|\\d{1,9}?";
    private static final String einzelwert_syntax = "|(\\d*.?\\d{1,2})|(\\d{0,3}(\\.\\d{3})*,?\\d{1,2})";

    /*
     Augabetexte für Meldungen
     */
    String fehlermeldung_titel = "Fehlerhafte Eingabe";
    String fehlermeldunAuftragspositionsIDtext = "\"Die eingegebene Auftragspositions-ID ist nicht gültig! "
            + "\\n Bitte geben Sie eine gültige Auftragspositions-ID ein. (z.B. 1 oder 999999999)\"";
    String fehlermeldungPositionsnummertext = "\"Die eingegebene Positionsnummer ist nicht gültig! "
            + "\\n Bitte geben Sie eine gültige Positionsnummer ein. (z.B. 1 oder 999999999)\"";
    String fehlermeldungMaterialnummertext = "\"Die eingegebene Materialnummer ist nicht gültig! "
            + "\\n Bitte geben Sie eine gültige Materialnummer ein. (z.B. 1 oder 999999999)\"";
    String fehlermeldungMengetext = "\"Die eingegebene Menge ist nicht gültig! "
            + "\\n Bitte geben Sie eine Menge ein. (z.B. 0 bis 999999999)\"";
    String fehlermeldungEinzelwerttext = "\"Der eingegebene Einzelwert ist nicht gültig! "
            + "\\n Bitte geben Sie einen gültigen Einzelwert ein. (z.B. 0,00 bis 9999999,99)\"";
    final String aenderungVonDaten_Text = "Es wurden Daten geändert. Wollen sie wirklich"
            + "die Daten überspeichern?";
    final String aenderungVonDaten_Titel = "Änderung von Daten";
    final String ERFOLGREICHGEAENDERT_TEXT = "Die Position  wurde erfolgreich geändert.";
    final String keineAenderung_Text = "Es sind keine Änderungen vorgenommen worden.";
    final String keineAenderungen_Titel = "Auftragsposition existiert bereits.";
    final String ERFOLGREICHGELOESCHT_TEXT = "Auftragsposition wurde erfolgreich "
            + "gelöscht";
    final String ERFOLGREICHGELOESCHT_TITEL = "Auftragsposition löschen";
    final String DATENVERWERFEN_TITEL = "Daten verwerfen";
    final String DATENVERWERFEN_TEXT = "Es wurden Daten eingegeben. Wollen Sie"
            + "diese Verwerfen ?";

    /**
     * Creates new form AuftragspositionAnlegen
     */
    public AuftragspositionAnzeigen(GUIFactory factory, InterfaceMainView mainView) {
        initComponents();
        this.factory = factory;
        this.hauptFenster = mainView;
        heute = new Date();

        // Variable, die ein Datum in ein vorgegebenes Format umwandelt.
        format = new SimpleDateFormat("dd.MM.yyyy");// Format dd.MM.yyyy

        //Initialisierung der Speichervariblen
        fehlendeEingaben = new ArrayList<Component>();

        //Zuweisung der Documents
        auftragskofID_jTextField.setDocument(new UniversalDocument("0123456789", false));
        positionsnummer_jTextField.setDocument(new UniversalDocument("0123456789", false));
        materialnummer_jTextField.setDocument(new UniversalDocument("0123456789", false));
        menge_jTextField.setDocument(new UniversalDocument("0123456789", false));
        einzelwert_jTextField.setDocument(new UniversalDocument("0123456789,.", false));

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
        auftragspositionsID_jLabel = new javax.swing.JLabel();
        positionsnummer_jLabel = new javax.swing.JLabel();
        materialnummer_jLabel = new javax.swing.JLabel();
        menge_jLabel = new javax.swing.JLabel();
        einzelwert_jLabel = new javax.swing.JLabel();
        erfassungsdatum_jLabel = new javax.swing.JLabel();
        auftragskofID_jTextField = new javax.swing.JTextField();
        positionsnummer_jTextField = new javax.swing.JTextField();
        materialnummer_jTextField = new javax.swing.JTextField();
        menge_jTextField = new javax.swing.JTextField();
        einzelwert_jTextField = new javax.swing.JTextField();
        erfassungsdatum_jTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setTitle("Auftragsposition anzeigen");

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
        jB_Speichern.setEnabled(false);
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
        jB_Loeschen.setEnabled(false);
        jB_Loeschen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_LoeschenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setText("Suchen");
        jToolBar1.add(jB_Suchen);

        auftragspositionsID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        auftragspositionsID_jLabel.setLabelFor(auftragskofID_jTextField);
        auftragspositionsID_jLabel.setText("Auftragskopf-ID :");

        positionsnummer_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        positionsnummer_jLabel.setLabelFor(positionsnummer_jTextField);
        positionsnummer_jLabel.setText("Positionsnummer :");

        materialnummer_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        materialnummer_jLabel.setLabelFor(materialnummer_jTextField);
        materialnummer_jLabel.setText("Materialnummer :");

        menge_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        menge_jLabel.setLabelFor(menge_jTextField);
        menge_jLabel.setText("Menge :");

        einzelwert_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        einzelwert_jLabel.setLabelFor(einzelwert_jTextField);
        einzelwert_jLabel.setText("Einzelwert :");

        erfassungsdatum_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        erfassungsdatum_jLabel.setLabelFor(erfassungsdatum_jTextField);
        erfassungsdatum_jLabel.setText("Erfassungsdatum :");

        auftragskofID_jTextField.setText("1");
        auftragskofID_jTextField.setEnabled(false);
        auftragskofID_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                auftragskofID_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                auftragskofID_jTextFieldFocusLost(evt);
            }
        });

        positionsnummer_jTextField.setText("1");
        positionsnummer_jTextField.setEnabled(false);
        positionsnummer_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                positionsnummer_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                positionsnummer_jTextFieldFocusLost(evt);
            }
        });

        materialnummer_jTextField.setText("1");
        materialnummer_jTextField.setEnabled(false);
        materialnummer_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                materialnummer_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                materialnummer_jTextFieldFocusLost(evt);
            }
        });

        menge_jTextField.setText("10");
        menge_jTextField.setEnabled(false);
        menge_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                menge_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                menge_jTextFieldFocusLost(evt);
            }
        });

        einzelwert_jTextField.setText("100,00");
        einzelwert_jTextField.setEnabled(false);
        einzelwert_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                einzelwert_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                einzelwert_jTextFieldFocusLost(evt);
            }
        });

        erfassungsdatum_jTextField.setEnabled(false);
        erfassungsdatum_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                erfassungsdatum_jTextFieldFocusGained(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Auftragspositionsdaten :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(auftragspositionsID_jLabel)
                            .addComponent(positionsnummer_jLabel)
                            .addComponent(materialnummer_jLabel)
                            .addComponent(menge_jLabel)
                            .addComponent(einzelwert_jLabel)
                            .addComponent(erfassungsdatum_jLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(auftragskofID_jTextField)
                            .addComponent(positionsnummer_jTextField)
                            .addComponent(materialnummer_jTextField)
                            .addComponent(menge_jTextField)
                            .addComponent(einzelwert_jTextField)
                            .addComponent(erfassungsdatum_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(auftragspositionsID_jLabel)
                    .addComponent(auftragskofID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(positionsnummer_jLabel)
                    .addComponent(positionsnummer_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(materialnummer_jLabel)
                    .addComponent(materialnummer_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(menge_jLabel)
                    .addComponent(menge_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(einzelwert_jLabel)
                    .addComponent(einzelwert_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(erfassungsdatum_jLabel)
                    .addComponent(erfassungsdatum_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(320, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     *
     * @param evt
     */
    private void auftragskofID_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragskofID_jTextFieldFocusGained
        auftragskofID_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        auftragskofID_jTextField.setText("");//Eingabefeld erhält einen leeren String
        auftragskofID_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_auftragskofID_jTextFieldFocusGained

    /**
     * Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     *
     * @param evt
     */
    private void positionsnummer_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_positionsnummer_jTextFieldFocusGained
        positionsnummer_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        positionsnummer_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_positionsnummer_jTextFieldFocusGained

    /**
     * Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     *
     * @param evt
     */
    private void materialnummer_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_materialnummer_jTextFieldFocusGained
        materialnummer_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        materialnummer_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_materialnummer_jTextFieldFocusGained

    /**
     * Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     *
     * @param evt
     */
    private void menge_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_menge_jTextFieldFocusGained
        mengenAngabe = "";
        menge_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        menge_jTextField.selectAll();//Selektion des Eingabefeldes
        mengenAngabe = menge_jTextField.getText();
    }//GEN-LAST:event_menge_jTextFieldFocusGained

    /**
     * Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     *
     * @param evt
     */
    private void einzelwert_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_einzelwert_jTextFieldFocusGained
        einzelwert_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        einzelwert_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_einzelwert_jTextFieldFocusGained

    /**
     * Beim wählen des Eingabefeldes, wird alles selektiert.
     *
     * @param evt
     */
    private void erfassungsdatum_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_erfassungsdatum_jTextFieldFocusGained
        erfassungsdatum_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        erfassungsdatum_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_erfassungsdatum_jTextFieldFocusGained

    /**
     * Beim Focuslost des Eingabefeldes für die Auftragspostions-ID, wird auf
     * die Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void auftragskofID_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragskofID_jTextFieldFocusLost
        if (evt.isTemporary()) {
            return;
        }
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(auftragskofID_jTextField, auftragspositionsID_syntax,
                fehlermeldung_titel, fehlermeldunAuftragspositionsIDtext);
    }//GEN-LAST:event_auftragskofID_jTextFieldFocusLost

    /**
     * Beim Focuslost des Eingabefeldes für die Positionsnummer, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void positionsnummer_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_positionsnummer_jTextFieldFocusLost
        if (evt.isTemporary()) {
            return;
        }
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(positionsnummer_jTextField, positionsnummer_syntax,
                fehlermeldung_titel, fehlermeldungPositionsnummertext);
    }//GEN-LAST:event_positionsnummer_jTextFieldFocusLost

    /**
     * Beim Focuslost des Eingabefeldes für die Materialnummer, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void materialnummer_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_materialnummer_jTextFieldFocusLost
        if (evt.isTemporary()) {
            return;
        }
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(materialnummer_jTextField, material_syntax,
                fehlermeldung_titel, fehlermeldungMaterialnummertext);
    }//GEN-LAST:event_materialnummer_jTextFieldFocusLost

    /**
     * Beim Focuslost des Eingabefeldes für die Menge, wird auf die Richtigkeit
     * der Eingabe geprüft und gibt gegebenen falls eine Fehlermeldung aus.
     * Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void menge_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_menge_jTextFieldFocusLost
        Double neuWert;
        double einzelwert = 0.0;
        if (evt.isTemporary()) {
            return;
        }
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(menge_jTextField, menge_syntax,
                fehlermeldung_titel, fehlermeldungMengetext);
        einzelwert = Double.parseDouble(einzelwert_jTextField.getText())
                / Double.parseDouble(mengenAngabe);
        neuWert = Double.parseDouble(menge_jTextField.getText())
                * einzelwert;
        einzelwert_jTextField.setText(String.valueOf(neuWert));
    }//GEN-LAST:event_menge_jTextFieldFocusLost

    /**
     * Beim Focuslost des Eingabefeldes für den Einzelwert, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void einzelwert_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_einzelwert_jTextFieldFocusLost
        if (evt.isTemporary()) {
            return;
        }
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(einzelwert_jTextField, einzelwert_syntax,
                fehlermeldung_titel, fehlermeldungEinzelwerttext);
    }//GEN-LAST:event_einzelwert_jTextFieldFocusLost

    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed

        if (!(dbMenge.equals(menge_jTextField.getText())
                && dbErfassungsdatum.equals(erfassungsdatum_jTextField.getText()))
                && gespeichert == false) {

            int antwort = JOptionPane.showConfirmDialog(rootPane, DATENVERWERFEN_TEXT,
                    DATENVERWERFEN_TITEL, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //Falls bejaht wird, werden die Daten verworfen..
            if (antwort == JOptionPane.YES_OPTION) {

                zuruecksetzen();// Eingabefelder werden zurückgesetzt.
                letzteComponent = null;   //Initialisierung der Componentspeichervariable
                //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
                letzteComponent = this.factory.zurueckButton();
                this.setVisible(false);// Internalframe wird nicht mehr dargestellt
                letzteComponent.setVisible(true);// Übergebene Component wird sichtbar gemacht
            }
        } else {
            zuruecksetzen();
            c = null;   //Initialisierung der Componentspeichervariable
            //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
            c = this.factory.zurueckButton();
            this.setVisible(false);// Internalframe wird nicht mehr dargestellt
            c.setVisible(true);// Übergebene Component wird sichtbar gemacht
        }
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /*----------------------------------------------------------*/
    /**
     * Beim betätigen des Anzeigen/Ändern -Buttons, wird geprüft wie der Button
     * beschriftet ist und ruft daruaf hin die passende Maske auf.
     *
     * @param evt
     */
    private void jB_AnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AnzeigenActionPerformed
        if (jB_Anzeigen.getText().equals("Anzeigen")) {
            this.setStatusAnzeigen();
        } else {
            this.setStatusAender();
        }
    }//GEN-LAST:event_jB_AnzeigenActionPerformed

    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed

        try {

            if (!(dbAuftragspositionsID.equals(auftragskofID_jTextField.getText())
                    && dbPositionsnummer.equals(positionsnummer_jTextField.getText())
                    && dbMaterialnummer.equals(materialnummer_jTextField.getText())
                    && dbMenge.equals(menge_jTextField.getText())
                    && dbErfassungsdatum.equals(erfassungsdatum_jTextField.getText())
                    && dbEinzelwert.equals(einzelwert_jTextField.getText()))) {
                int antwort = JOptionPane.showConfirmDialog(rootPane, aenderungVonDaten_Text,
                        aenderungVonDaten_Titel, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

//Falls bejaht wird der Auftragskopf verändert gespeichert.
                if (antwort == JOptionPane.YES_OPTION) {

                    GUIFactory.getDAO().aenderePosition(Long.parseLong(auftragskofID_jTextField.getText()),
                            Long.parseLong(positionsnummer_jTextField.getText()),
                            Integer.parseInt(menge_jTextField.getText()));

                    zuruecksetzen();

                    gespeichert = true;
                    jB_ZurueckActionPerformed(evt);
                    this.hauptFenster.setStatusMeldung(ERFOLGREICHGEAENDERT_TEXT);
                } else {
                    gespeichert = false;
                }

            } else {
                JOptionPane.showMessageDialog(null, keineAenderung_Text,
                        keineAenderungen_Titel, JOptionPane.OK_OPTION);
            }
        } catch (ApplicationException e) {
            this.hauptFenster.setStatusMeldung(e.getMessage());
        }

    }//GEN-LAST:event_jB_SpeichernActionPerformed

    private void jB_LoeschenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_LoeschenActionPerformed
        try {
            GUIFactory.getDAO().loeschePositionTransaktion(Long.parseLong(dbAuftragspositionsID),
                    Long.parseLong(dbPositionsnummer));
            this.hauptFenster.setStatusMeldung(ERFOLGREICHGELOESCHT_TEXT);
            jB_ZurueckActionPerformed(evt);
        } catch (ApplicationException e) {
            this.hauptFenster.setStatusMeldung(e.getMessage());
        }
    }//GEN-LAST:event_jB_LoeschenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden
     */
    @Override
    public void zuruecksetzen() {
        //Eingabefelder erhalten einen leeren String
        auftragskofID_jTextField.setText("");
        positionsnummer_jTextField.setText("");
        materialnummer_jTextField.setText("");
        menge_jTextField.setText("");
        einzelwert_jTextField.setText("");
        //Eingabefeld für das Erfassungsdatum erhält das heutige Datum
        erfassungsdatum_jTextField.setText(format.format(heute));
        gespeichert = false;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /*----------------------------------------------------------*/
    /*
     Schnittstellenmethode mit der geprüft wird ob alle Eingaben getätigt worden sind.
     */
    @Override
    public void ueberpruefen() {
        //IF-Anweisungen mit denen geprüft wird welche Eingabefelder keine Eingabe 
        // erhalten haben. Diese Eingabefelder werden in passende Speichervariablen festgehalten

        //Eingabefelder für Auftragsposition werden in Variable "fehlendeEingaben" festgehalten.
        if (auftragskofID_jTextField.getText().equals("")) {
            fehlendeEingaben.add(auftragskofID_jTextField);
        }
        if (positionsnummer_jTextField.getText().equals("")) {
            fehlendeEingaben.add(positionsnummer_jTextField);
        }
        if (materialnummer_jTextField.getText().equals("")) {
            fehlendeEingaben.add(materialnummer_jTextField);
        }
        if (menge_jTextField.getText().equals("")) {
            fehlendeEingaben.add(menge_jTextField);
        }
        if (einzelwert_jTextField.getText().equals("")) {
            fehlendeEingaben.add(einzelwert_jTextField);
        }
        if (erfassungsdatum_jTextField.getText().equals("")) {
            fehlendeEingaben.add(erfassungsdatum_jTextField);
        }
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
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax, String fehlermelgungtitel, String fehlermeldung) {
        if (!textfield.getText().matches(syntax)) {
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
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
    public void fehlEingabenMarkierung(ArrayList<Component> list, String fehlermelgungtitel, String fehlermeldung, Color farbe) {
        //Meldung die darauf hinweist das nicht alle Eingaben getätigt worden sind.
        JOptionPane.showMessageDialog(null, fehlermeldung,
                fehlermelgungtitel, JOptionPane.WARNING_MESSAGE);
        if (!list.isEmpty()) {

            list.get(0).requestFocusInWindow();// Fokus gelangt in das erste leere Eingabefeld

        }
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
        this.setTitle("Auftragsposition ändern Einstieg");
//        zuruecksetzen();
        this.auftragskofID_jTextField.setEnabled(false);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(false);
        this.menge_jTextField.setEnabled(true);
        this.einzelwert_jTextField.setEnabled(false);
        this.erfassungsdatum_jTextField.setEnabled(true);
        jB_Anzeigen.setText("Anzeigen");
        jB_Anzeigen.setEnabled(false);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(true);
        jB_Anzeigen.setEnabled(false);
        this.hauptFenster.setComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 08.01.2015 Terrasi Anwendungslogik überarbeitet*/
    /*----------------------------------------------------------*/
    public void setStatusAnzeigen() {
        this.setTitle("Auftragsposition anzeigen Einstieg");
//        zuruecksetzen();
        this.auftragskofID_jTextField.setEnabled(false);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(false);
        this.menge_jTextField.setEnabled(false);
        this.einzelwert_jTextField.setEnabled(false);
        this.erfassungsdatum_jTextField.setEnabled(false);
        jB_Anzeigen.setText("Ändern");
        jB_Anzeigen.setEnabled(true);
        jB_Speichern.setEnabled(false);
        jB_Loeschen.setEnabled(false);
        this.hauptFenster.setComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    public void setAuftragspositionsID_jTextField(Auftragsposition position) {
        this.auftragskofID_jTextField.setText(String.valueOf(position.getAuftrag().getAuftragskopfID()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    public void setEinzelwert_jTextField(Auftragsposition position) {
        this.einzelwert_jTextField.setText(String.valueOf(position.getEinzelwert()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    public void setErfassungsdatum_jTextField(Auftragsposition position) {
        this.erfassungsdatum_jTextField.setText(gibDatumAlsString(position.getErfassungsdatum()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    public void setMaterialnummer_jTextField(Auftragsposition position) {
        this.materialnummer_jTextField.setText(String.valueOf(position.getArtikel().getArtikelID()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    public void setMenge_jTextField(Auftragsposition position) {
        this.menge_jTextField.setText(String.valueOf(position.getMenge()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    public void setPositionsnummer_jTextField(Auftragsposition position) {
        this.positionsnummer_jTextField.setText(String.valueOf(position.getPositionsnummer()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    public void setzeEingaben(Auftragsposition position) {

        this.auftragskofID_jTextField.setText(String.valueOf(position.getAuftrag().getAuftragskopfID()));
        this.einzelwert_jTextField.setText(String.valueOf(position.getEinzelwert()));
        this.erfassungsdatum_jTextField.setText(gibDatumAlsString(position.getErfassungsdatum()));
        this.materialnummer_jTextField.setText(String.valueOf(position.getArtikel().getArtikelID()));
        this.menge_jTextField.setText(String.valueOf(position.getMenge()));
        this.positionsnummer_jTextField.setText(String.valueOf(position.getPositionsnummer()));

        try {

            dbPosition = GUIFactory.getDAO().
                    gibAuftragsposition(Long.parseLong(auftragskofID_jTextField.getText()),
                            Long.parseLong(positionsnummer_jTextField.getText()));

            dbAuftragspositionsID = String.valueOf(position.getAuftrag().getAuftragskopfID());
            dbPositionsnummer = String.valueOf(position.getPositionsnummer());
            dbMaterialnummer = String.valueOf(position.getArtikel().getArtikelID());
            dbMenge = String.valueOf(position.getMenge());
            dbEinzelwert = String.valueOf(position.getEinzelwert());
            dbErfassungsdatum = gibDatumAlsString(position.getErfassungsdatum());
        } catch (ApplicationException e) {
            this.hauptFenster.setStatusMeldung(e.getMessage());
        }
    }
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/

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
    private javax.swing.JTextField auftragskofID_jTextField;
    private javax.swing.JLabel auftragspositionsID_jLabel;
    private javax.swing.JLabel einzelwert_jLabel;
    private javax.swing.JTextField einzelwert_jTextField;
    private javax.swing.JLabel erfassungsdatum_jLabel;
    private javax.swing.JTextField erfassungsdatum_jTextField;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel materialnummer_jLabel;
    private javax.swing.JTextField materialnummer_jTextField;
    private javax.swing.JLabel menge_jLabel;
    private javax.swing.JTextField menge_jTextField;
    private javax.swing.JLabel positionsnummer_jLabel;
    private javax.swing.JTextField positionsnummer_jTextField;
    // End of variables declaration//GEN-END:variables
}
