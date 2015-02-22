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
import java.text.ParseException;
import java.util.Calendar;

/**
 *
 * @author Luca Terrasi
 */
/* 10.12.2014 Dokumentation und Logik */
/* 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button */
/* 06.01.2015 Terrasi, Anwendungslogik für das ändern und anzeigen von 
 Auftragspositionen. */
/* 08.01.2015 Terrasi, Überarbeitung der Anwendungslogik für anzeigen/ändern
 * Status und das hinzufügen von weiteren Funktion.*/
/* 17.01.2015 Terrasi, erstelln  von Setter-Methoden um Werte den Eingabefelder
 zu übergeben.*/
/* 18.02.2015 TER, getestet und freigegeben */
public class AuftragspositionAnzeigen extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {

    // Speichervariablen
    GUIFactory factory;
    InterfaceMainView hauptFenster;
    Component letzteComponent;
    String mengenAngabe;

    // Speichervariablen für Datenbankdaten.
    Auftragsposition dbPosition;

    String dbAuftragspositionsID;
    String dbPositionsnummer;
    String dbMaterialnummer;
    String dbMenge;
    String dbEinzelwert;
    String dbErfassungsdatum;

    // ArrayList für Eingabefelder des Auftragkopfes.
    ArrayList<Component> fehlendeEingaben;

    // Hilfsvariablen
    public Date heute;// heutiges Datum
    public SimpleDateFormat format; //Umwandler für Datum

    // Boolische Variable die benötigt wird um zu prüfen ob die Daten bereits 
    // gespeichert worden sind.
    boolean gespeichert = false;
    private boolean formularOK = true;

    // Konstanten für Farben
    final Color warningfarbe = Color.YELLOW;
    final Color hintergrundfarbe = Color.WHITE;

    // Syntax
    private static final String menge_syntax = "|\\d{1,9}?";

    // Augabetexte für Meldungen
    final String FEHLER = "Fehler";

    final String FEHLERMELDUNG_TITEL = "Fehlerhafte Eingabe";

    final String FEHLERMELDUNG_MENGE_TEXT = "\"Die eingegebene Menge ist nicht gültig! "
            + "\\n Bitte geben Sie eine Menge ein. (z.B. 0 bis 999999999)\"";

    final String AENDERUNGVONDATEN__TITEL = "Änderung von Daten";
    final String AENDERUNGVONDATEN_TEXT = "Es wurden Daten geändert. Wollen Sie wirklich"
            + " die Daten überspeichern?";

    final String ERFOLGREICHGEAENDERT_TEXT = "Die Position  wurde erfolgreich geändert.";

    final String KEINEAENDERUNG_TITEL = "Auftragsposition existiert bereits.";
    final String KEINEAENDERUNG_TEXT = "Es sind keine Änderungen vorgenommen worden.";

    final String ERFOLGREICHGELOESCHT_TITEL = "Auftragsposition löschen";
    final String ERFOLGREICHGELOESCHT_TEXT = "Auftragsposition wurde erfolgreich "
            + "gelöscht";

    final String DATENVERWERFEN_TITEL = "Daten verwerfen";
    final String DATENVERWERFEN_TEXT = "Es wurden Daten eingegeben. Wollen Sie"
            + " diese Verwerfen ?";

    final String KEINEEINGABE_TEXT = "Es wurde keine Eingabe getätigt. Bitte geben"
            + "\n Sie die notwendige Eingabe ein.";

    final String POSITIONLOESCHEN_TITEL = "Position löschen";
    final String POSITIONLOESCHEN_TEXT = "Wollen Sie die Auftragsposition "
            + "wirklich löschen?";

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Konstruktor, Erzeugung eines AuftragspositionAnzeigensobjektes.
     *
     * @param factory, Übergabe eines GUIFactoryobjektes.
     * @param mainView, Übergabe eines InterfaceMainViewobjektes.
     */
    public AuftragspositionAnzeigen(GUIFactory factory, InterfaceMainView mainView) {
        initComponents();
        // Übergabe der Parameter.
        this.factory = factory;
        this.hauptFenster = mainView;
        // Erzeugung eines Dateobjektes mit dem heutigen Datum.
        heute = new Date();

        // Variable, die ein Datum in ein vorgegebenes Format umwandelt.
        format = new SimpleDateFormat("dd.MM.yyyy");// Format dd.MM.yyyy

        // Initialisierung der Speichervariblen
        fehlendeEingaben = new ArrayList<Component>();

        // Zuweisung der Documents an die Eingabefelder
        auftragskofID_jTextField.setDocument(
                new UniversalDocument("0123456789", false));
        positionsnummer_jTextField.setDocument(
                new UniversalDocument("0123456789", false));
        materialnummer_jTextField.setDocument(
                new UniversalDocument("0123456789", false));
        menge_jTextField.setDocument(
                new UniversalDocument("0123456789", false));
        einzelwert_jTextField.setDocument(
                new UniversalDocument("0123456789,.", false));

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

        jB_Zurueck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Home2.PNG"))); // NOI18N
        jB_Zurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ZurueckActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Zurueck);

        jB_Speichern.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Speichern.PNG"))); // NOI18N
        jB_Speichern.setEnabled(false);
        jB_Speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SpeichernActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Bearbeiten.PNG"))); // NOI18N
        jB_Anzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_AnzeigenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Löschen.PNG"))); // NOI18N
        jB_Loeschen.setEnabled(false);
        jB_Loeschen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_LoeschenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Suche.PNG"))); // NOI18N
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

        positionsnummer_jTextField.setText("1");
        positionsnummer_jTextField.setEnabled(false);

        materialnummer_jTextField.setText("1");
        materialnummer_jTextField.setEnabled(false);

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

        erfassungsdatum_jTextField.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Auftragspositionsdaten :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(79, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(320, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     *
     * @param evt
     */
    private void menge_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_menge_jTextFieldFocusGained
        mengenAngabe = "";// Übergabe von leerem String.
        // Übergabe von eingegebenem Wert an Variable.
        mengenAngabe = menge_jTextField.getText();

        menge_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_menge_jTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Beim Focuslost des Eingabefeldes für die Menge, wird auf die Richtigkeit
     * der Eingabe geprüft und gibt gegebenen falls eine Fehlermeldung aus.
     * Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void menge_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_menge_jTextFieldFocusLost
        // Klassenvariable
        double neuWert;
        double einzelwert = 0.0;

        // Überprüfung ob Meldung mehrmals ausgegeben wird
        if (evt.isTemporary()) {
            return;
        }
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(menge_jTextField, menge_syntax,
                FEHLERMELDUNG_TITEL, FEHLERMELDUNG_MENGE_TEXT);
        // Überprüfung ob Mengenangabe getätigt worden ist.
        if (!(menge_jTextField.getText().equals(""))) {

            // Einzelwert des Matrials wird berechnet und der Speichervariable 
            // übergeben.
            einzelwert = Double.parseDouble(einzelwert_jTextField.getText())
                    / Double.parseDouble(mengenAngabe);
            // Wert der Position wird neu berechnet anhand des Einzelwertes und
            // der Menge.
            neuWert = Double.parseDouble(menge_jTextField.getText())
                    * einzelwert;
            // Übergabe des neuen Wertes an das entsprechende Eingabefeld.
            einzelwert_jTextField.setText(String.valueOf(neuWert));
            //Setzen der Hintergrundsfarbe des Eingabefeldes
            menge_jTextField.setBackground(hintergrundfarbe);
        }
    }//GEN-LAST:event_menge_jTextFieldFocusLost

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Erstellung,Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        if (formularOK) {// Falls das Formula ok ist
            // Wird geprüft ob sich eingegebene Daten von den Daten in der 
            // Datenbank unterscheiden. Es wird auch geprüft ob die 
            // Prüfvariable false ist oder nicht.
            if (!(dbMenge.equals(menge_jTextField.getText())
                    && dbErfassungsdatum.equals(
                            erfassungsdatum_jTextField.getText()))
                    && gespeichert == false) {

                //PopUp mit "JA/Nein"-Abfrage.
                int antwort = JOptionPane.showConfirmDialog(rootPane,
                        DATENVERWERFEN_TEXT,
                        DATENVERWERFEN_TITEL, JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                //Falls bejaht wird, kehrt man zurück ins Hauptmenü.
                if (antwort == JOptionPane.YES_OPTION) {
                    // Methode um ins Hauptmenü zurück zu kehren.
                    zurueckInsHauptmenue();
                }
            } else {//Falls keine Änderungen an den Daten vorgenommen worden sind

                zurueckInsHauptmenue();// Methode um ins Menü zurückzukehren.
            }
        }
        // Variable wird wieder auf true gesetzt, da nochmals eine Prüfung
        // stattfindet. 
        formularOK = true;
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
    /* 08.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Beim betätigen des Ändern -Buttons wird die passende Maske aufgerufen.
     *
     * @param evt
     */
    private void jB_AnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AnzeigenActionPerformed

        this.setStatusAender();// Methode um den Modus zu wechseln.
    }//GEN-LAST:event_jB_AnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Speichermethode, mit der die Position gespeichert wird oder verändert
     * gespeichert wird.
     *
     * @param evt
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed
        if (formularOK) {// Falls das Formula ok ist
            try {
                // Wird geprüft ob sich eingegebene Daten von den Daten in der 
                // Datenbank unterscheiden. 
                if (!(dbAuftragspositionsID.equals(
                        auftragskofID_jTextField.getText())
                        && dbPositionsnummer.equals(
                                positionsnummer_jTextField.getText())
                        && dbMaterialnummer.equals(
                                materialnummer_jTextField.getText())
                        && dbMenge.equals(menge_jTextField.getText())
                        && dbErfassungsdatum.equals(
                                erfassungsdatum_jTextField.getText())
                        && dbEinzelwert.equals(
                                einzelwert_jTextField.getText()))) {

                    //PopUp mit "JA/Nein"-Abfrage.
                    int antwort = JOptionPane.showConfirmDialog(rootPane,
                            AENDERUNGVONDATEN_TEXT,
                            AENDERUNGVONDATEN__TITEL, JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    //Falls bejaht wird,wird die Position gespeichert.
                    if (antwort == JOptionPane.YES_OPTION) {
                        // DAO-Methode um Position zu speichern.
                        GUIFactory.getDAO().aenderePosition(Long.parseLong(
                                auftragskofID_jTextField.getText()),
                                Long.parseLong(
                                        positionsnummer_jTextField.getText()),
                                Integer.parseInt(menge_jTextField.getText()));

                        zuruecksetzen();// Methode um Formular zurückzusetzen.

                        gespeichert = true;// Speichervariable auf true setzen

                        // ActionPerformed-Methode um ins Menü zurückzukehren.
                        jB_ZurueckActionPerformed(evt);
                        // Methodenaufruf um Meldung in der Statuszeile 
                        // anzeigen zu lassen.
                        this.hauptFenster.setStatusMeldung(
                                ERFOLGREICHGEAENDERT_TEXT);
                    } else {
                        gespeichert = false;// Speichervariable auf false setzen
                    }

                } else {
                    // Meldung als PopUp.
                    JOptionPane.showMessageDialog(null, KEINEAENDERUNG_TEXT,
                            KEINEAENDERUNG_TITEL, JOptionPane.OK_OPTION);
                }

            } catch (ApplicationException e) {// Fehlerbehandlung.
                // Fehlermeldung als PopUp
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        FEHLER, JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {// Fehlerbehandlung.
                // Fehlermeldung als PopUp
                JOptionPane.showMessageDialog(null, KEINEEINGABE_TEXT,
                        FEHLER, JOptionPane.ERROR_MESSAGE);
                // Fokus in das Eingabefeld der Menge setzen.
                menge_jTextField.requestFocusInWindow();
            }
        }
        // Variable wird wieder auf true gesetzt, 
        // da nochmals eine Pruefung stattfindet 
        formularOK = true;
    }//GEN-LAST:event_jB_SpeichernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Lösch-ActionPerformed, Methode mit der eine Position gelöscht werden
     * kann.
     *
     * @param evt
     */
    private void jB_LoeschenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_LoeschenActionPerformed
        if (formularOK) {// Falls das Formula ok ist
            try {
                //PopUp mit "JA/Nein"-Abfrage.
                int antwort = JOptionPane.showConfirmDialog(rootPane,
                        POSITIONLOESCHEN_TEXT,
                        POSITIONLOESCHEN_TITEL, JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                //Falls bejaht wird, wird der Benutzer gelöscht.
                if (antwort == JOptionPane.YES_OPTION) {

                    // Aufruf der DAO-Methode um eine Position zu löschen
                    GUIFactory.getDAO().loeschePositionTransaktion(Long.parseLong(dbAuftragspositionsID),
                            Long.parseLong(dbPositionsnummer));
                    // Methodenaufruf um Meldung in der Statuszeile 
                    // anzeigen zu lassen.
                    this.hauptFenster.setStatusMeldung(ERFOLGREICHGELOESCHT_TEXT);
                    // ActionPerformed-Methode um ins Menü zurückzukehren.
                    jB_ZurueckActionPerformed(evt);
                }

            } catch (ApplicationException e) {// Fehlerbehandlung
                // Fehlermeldung als PopUp
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        FEHLER, JOptionPane.ERROR_MESSAGE);
            }
        }
        // Variable wird wieder auf true gesetzt, da nochmals eine Pruefung 
        // stattfindet. 
        formularOK = true;
    }//GEN-LAST:event_jB_LoeschenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
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

        menge_jTextField.setBackground(hintergrundfarbe);
        erfassungsdatum_jTextField.setBackground(hintergrundfarbe);

        // Eingabefeld für das Erfassungsdatum erhält das heutige Datum
        erfassungsdatum_jTextField.setText(format.format(heute));
        // Boolischevariablen erhalten neue Werte.
        gespeichert = false;
        formularOK = true;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der geprüft wird ob alle Eingaben getätigt
     * worden sind.
     */
    @Override
    public void ueberpruefen() {
        // IF-Anweisungen mit denen geprüft wird welche Eingabefelder keine 
        // Eingabe erhalten haben. Diese Eingabefelder werden in der passenden 
        // Speichervariable festgehalten.

        // Eingabefelder für Auftragsposition werden in Variable 
        // "fehlendeEingaben" festgehalten.
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
    public void ueberpruefungVonFocusLost(JTextField textfield,
            String syntax, String fehlermelgungtitel, String fehlermeldung) {
        // Wird geprüft ob Eingabe mit Syntax übereinstimmt.
        if (!textfield.getText().matches(syntax)) {
            formularOK = false;
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
            //Mit dem Focus in das übergebene Eingabefeld springen
            textfield.requestFocusInWindow();
            textfield.selectAll();
        } else if (!textfield.getText().equals("")) {// Falls Eingabefeld nicht 
            // leer ist.
            formularOK = true;
            // Setzen Von Hintergrundsfarbe.
            textfield.setBackground(hintergrundfarbe);
        } else {//Falls Eingabe nicht leer ist oder nicht mit Syntax übereinstimmt
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
        // Meldung die darauf hinweist das nicht alle Eingaben getätigt worden 
        // sind.
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
    public void setStatusAender() {
        this.setTitle("Auftragsposition ändern"); // Setzen des Fenstertitels

        // Componenten werden auf Enabled gesetzt mit false oder true.
        this.auftragskofID_jTextField.setEnabled(false);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(false);
        this.menge_jTextField.setEnabled(true);
        this.einzelwert_jTextField.setEnabled(false);
        this.erfassungsdatum_jTextField.setEnabled(false);
        jB_Anzeigen.setEnabled(false);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(true);
        jB_Anzeigen.setEnabled(false);
        jB_Suchen.setEnabled(false);

        // Hauptfenster macht übergebene Maske sichtbar.
        this.hauptFenster.setComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 06.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /* 08.01.2015 Terrasi Anwendungslogik überarbeitet*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Internalframe im Anzeigenmodus dargestellt wird.
     */
    public void setStatusAnzeigen() {
        this.setTitle("Auftragsposition anzeigen");// Setzen des Fenstertitels
        // Componenten werden auf Enabled gesetzt mit false oder true.
        this.auftragskofID_jTextField.setEnabled(false);
        this.positionsnummer_jTextField.setEnabled(false);
        this.materialnummer_jTextField.setEnabled(false);
        this.menge_jTextField.setEnabled(false);
        this.einzelwert_jTextField.setEnabled(false);
        this.erfassungsdatum_jTextField.setEnabled(false);
        jB_Anzeigen.setEnabled(true);
        jB_Speichern.setEnabled(false);
        jB_Loeschen.setEnabled(false);
        jB_Suchen.setEnabled(false);
        // Hauptfenster macht übergebene Maske sichtbar.
        this.hauptFenster.setComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der die ID der Position gesetzt wird.
     * @param position, Auftragsposition 
     */
    public void setAuftragspositionsID_jTextField(Auftragsposition position) {
        // ID von Position wird in Eingabefeld gesetzt.
        this.auftragskofID_jTextField.setText(String.valueOf(
                position.getAuftrag().getAuftragskopfID()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der der Einzelwert der Position gesetzt wird.
     * @param position, Auftragsposition 
     */
    public void setEinzelwert_jTextField(Auftragsposition position) {
        // Einzelwert der Position wird in das Eingabefeld gesetzt.
        this.einzelwert_jTextField.setText(String.valueOf(
                position.getEinzelwert()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Erfassungsdatum der Position gesetzt wird.
     * @param position, Auftragsposition 
     */
    public void setErfassungsdatum_jTextField(Auftragsposition position) {
        // Datum der Position wird in das Eingabefeld gesetzt.
        this.erfassungsdatum_jTextField.setText(
                gibDatumAlsString(position.getErfassungsdatum()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der die Materialnummer der Position gesetzt wird.
     * @param position, Auftragsposition 
     */
    public void setMaterialnummer_jTextField(Auftragsposition position) {
        // Artikel ID der Position wird in das Eingabefeld gesetzt.
        this.materialnummer_jTextField.setText(
                String.valueOf(position.getArtikel().getArtikelID()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der die Menge der Position gesetzt wird.
     * @param position, Auftragsposition 
     */
    public void setMenge_jTextField(Auftragsposition position) {
        // Menge der Position wird in das Eingabefeld gesetzt.
        this.menge_jTextField.setText(String.valueOf(position.getMenge()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der die Positionsnummer der Position gesetzt wird.
     * @param position, Auftragsposition 
     */
    public void setPositionsnummer_jTextField(Auftragsposition position) {
        // Positionsnummer der Position wird in das Eingabefeld gesetzt.
        this.positionsnummer_jTextField.setText(
                String.valueOf(position.getPositionsnummer()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode der man ein Positionsobjekt übergibt und dessen Daten in den
     * passenden Eingabefeldern wiedergibt. Die Daten der Position werden des
     * Weiteren in Speichervariablen abgelegt.
     *
     * @param position
     */
    public void setzeEingaben(Auftragsposition position) {
        // Übergeben der Positionsdaten an die jeweiligen Eingabefelder.
        this.auftragskofID_jTextField.setText(
                String.valueOf(position.getAuftrag().getAuftragskopfID()));
        this.einzelwert_jTextField.setText(
                String.valueOf(position.getEinzelwert()));
        this.erfassungsdatum_jTextField.setText(
                gibDatumAlsString(position.getErfassungsdatum()));
        this.materialnummer_jTextField.setText(
                String.valueOf(position.getArtikel().getArtikelID()));
        this.menge_jTextField.setText(
                String.valueOf(position.getMenge()));
        this.positionsnummer_jTextField.setText(
                String.valueOf(position.getPositionsnummer()));

        try {
            // DAO-Methode mit der man die Position aufruft.
            dbPosition = GUIFactory.getDAO().
                    gibAuftragsposition(
                            Long.parseLong(auftragskofID_jTextField.getText()),
                            Long.parseLong(positionsnummer_jTextField.getText()));
            // Positionsdaten werden in Variablen gespeichert.
            dbAuftragspositionsID = String.valueOf(
                    position.getAuftrag().getAuftragskopfID());
            dbPositionsnummer = String.valueOf(
                    position.getPositionsnummer());
            dbMaterialnummer = String.valueOf(
                    position.getArtikel().getArtikelID());
            dbMenge = String.valueOf(position.getMenge());
            dbEinzelwert = String.valueOf(position.getEinzelwert());
            dbErfassungsdatum = gibDatumAlsString(
                    position.getErfassungsdatum());

        } catch (ApplicationException e) {// Fehlerbehandlung
            // Fehlermeldung als PopUp
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    FEHLER, JOptionPane.ERROR_MESSAGE);
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
