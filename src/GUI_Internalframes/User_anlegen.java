package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.Benutzer;
import java.awt.Component;
import java.util.ArrayList;
import Interfaces.*;
import JFrames.GUIFactory;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Luca Terrasi
 *
 * /* 10.12.2014 Dokumentation und Logik
 */
/* 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button  un der 
 Schnittstellenmethoden*/
/* 08.01.2015 Terrasi, anlegen von SetStatus Methoden*/
/* 13.01.2015 Terrasi, Implementierung der DAO-Methoden */
/* 17.01.2015 Terrasi, ertsellen von Setter-Methode um einen Benutzernamen 
 an das Eingabefeld übergeben zu können. */
public class User_anlegen extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {

    /*
     Speichervariablen
     */
    ArrayList<Component> fehlendeEingaben;// ArrayList für Eingabefelder.
    Component letzteComponent;
    GUIFactory factory;
    InterfaceMainView hauptFenster;

    /*
     Variablen für Farben
     */
    Color warningfarbe = Color.YELLOW;
    Color hintergrundfarbe = Color.WHITE;

    /*
     Ausgabetexte für Meldungen
     */
    final String FEHLERMELDUNG_TITEL = "Fehlerhafte Eingabe";
    final String FEHLERMELDUNG_TEXT = "Es sind nicht alle Eingaben getätigt worden.\n"
            + "Bitte geben Sie alle Eingaben ein um fortzufahren.";
    final String ERFOLGREICHEANMELDUNG = "Der Benutzer wurde erfolgreich angelegt.";
    final String ERFOLGREICHAENDERNDERBENUTZERDATEN = "Die Benutzerdaten wurden erfolgreich geändert.";
    final String ERFOLGREICHBENUTZERGELOESCHT = "Der Benutzer wurde erfolgreich gelöscht";
    final String AENDERUNGVONDATEN_TEXT = "Es wurden Daten geändert. Wollen Sie wirklich"
            + "die Daten überspeichern?";
    final String AENDERUNGVONDATEN_TITEL = "Änderung von Daten";
    final String KEINEAENDERUNGEN_Titel = "Benutzer bereits angelegt";
    final String KEINEAENDERUNGEN_TEXT = "Der Benutzerdaten existieren bereits.";
    final String DATENVERWERFEN_TITEL = "Daten verwerfen";
    final String DATENVERWERFEN_TEXT = "Es wurden Daten eingegeben. Wollen Sie "
            + "diese Verwerfen ?";
    final String BENUTZERLOESCHEN_TITEL = "Benutzer löschen";
    final String BENUTZERLOESCHEN_TEXT = "Wollen sie den Benutzer wirklich löschen?";
    /*
     Spiechervariablen für die Eingabefelder
     */
    private String benutzerame;
    private String passwort;

    boolean gespeichert = false;
    /*
     Variable für die vergabe der Adminrechte.
     */
    private boolean istAdmin;

    /**
     * Creates new form Fenster
     */
    /**
     * Konstruktor, Erzeugung der Benutzeranlegen Maske.
     *
     * @param factory, Überabee einer GuiFactory
     * @param mainView, übergabe des Hauptfensters.
     */
    public User_anlegen(GUIFactory factory, InterfaceMainView mainView) {
        initComponents();
        this.factory = factory;
        this.hauptFenster = mainView;

        //Initialisierung
        this.benutzerame = "";
        this.passwort = "";

        istAdmin = false;// Übergabe eines boolenwertes

        //Initialisierung der Speichervariblen
        fehlendeEingaben = new ArrayList<Component>();
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 16.12.2014 Terrasi, Logik implementiert */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden.
     */
    @Override
    public void zuruecksetzen() {
        //Eingabefelder erhalten einen leeren String
//        benutzername_jTextField.setText("");
        passwort_jTextField.setText("");
        admin_jCheckBox.setSelected(false);
        benutzername_jTextField.setBackground(hintergrundfarbe);
        passwort_jTextField.setBackground(hintergrundfarbe);
        gespeichert = false;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 16.12.2014 Terrasi, Logik implementiert */
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
        if (benutzername_jTextField.getText().equals("")) {
            fehlendeEingaben.add(benutzername_jTextField);
        }
        if (passwort_jTextField.getText().equals("")) {
            fehlendeEingaben.add(passwort_jTextField);
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 16.12.2014 Terrasi, Logik implementiert */
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

    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 16.12.2014 Terrasi, Logik implementiert */
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
    /* 08.01.2015 Terrasi angelegt und Logik implementiert*/
    /*----------------------------------------------------------*/
    public void setStatusAnzeigen() {
        this.setTitle("Benutzer anzeigen");
        zuruecksetzen();
        this.benutzername_jTextField.setEnabled(false);
        this.passwort_jTextField.setEnabled(false);
        this.admin_jCheckBox.setEnabled(true);
//        jB_Anzeigen.setText("Ändern");
        jB_Anzeigen.setEnabled(true);
        jB_Speichern.setEnabled(false);
        jB_Loeschen.setEnabled(false);
        jB_Suchen.setEnabled(false);
        this.hauptFenster.setComponent(this);
        gespeichert = true;
    }
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt und Logik implementiert */
    /*----------------------------------------------------------*/

    /**
     * Methode mit der das Internalframe nicht mehr als Anzeigefenster
     * dargestellt wird, sondern als Fenster in dem man Daten ändern kann.
     */
    public void setStatusAender() {
        Benutzer benutzer;
        try{
            
        this.setTitle("Benutzer ändern");
        zuruecksetzen();
        this.benutzername_jTextField.setEnabled(false);
        this.passwort_jTextField.setEnabled(true);
        benutzer = GUIFactory.getDAO().gibBenutzer(benutzername_jTextField.getText());
        if (benutzer.isIstAdmin()) {
            this.setCheckBoxSelected(true);// Ceckbox wird selektiert.
        } else {
            this.setCheckBoxSelected(false);//Checkbox wird nicht selektiert.
        }
        this.admin_jCheckBox.setEnabled(true);
//        jB_Anzeigen.setText("Anzeigen");
        jB_Anzeigen.setEnabled(false);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(true);
        jB_Suchen.setEnabled(false);
        this.hauptFenster.setComponent(this);
        }catch(ApplicationException e){
            this.hauptFenster.setStatusMeldung(e.getMessage());
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt und Logik implementiert*/
    /*----------------------------------------------------------*/
    public void setStatusAnlegen() {
        this.setTitle("Benutzer anlegen");
        zuruecksetzen();
        this.benutzername_jTextField.setText("");
        this.benutzername_jTextField.setEnabled(true);
        this.passwort_jTextField.setEnabled(true);
        this.admin_jCheckBox.setEnabled(true);
//        jB_Anzeigen.setText("Anzeigen");
        jB_Anzeigen.setEnabled(false);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(false);
        jB_Suchen.setEnabled(false);
        this.hauptFenster.setComponent(this);
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
        benutzername_jLabel = new javax.swing.JLabel();
        passwort_jLabel = new javax.swing.JLabel();
        benutzername_jTextField = new javax.swing.JTextField();
        passwort_jTextField = new javax.swing.JTextField();
        admin_jCheckBox = new javax.swing.JCheckBox();

        setResizable(true);
        setTitle("User anlegen");
        setPreferredSize(new java.awt.Dimension(600, 250));
        setRequestFocusEnabled(false);
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

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
        jB_Speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SpeichernActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Bearbeiten.PNG"))); // NOI18N
        jB_Anzeigen.setEnabled(false);
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

        jSeparator1.setEnabled(false);

        benutzername_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        benutzername_jLabel.setText("Benutzername : ");

        passwort_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        passwort_jLabel.setText("Passwort          :");

        benutzername_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                benutzername_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                benutzername_jTextFieldFocusLost(evt);
            }
        });

        passwort_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwort_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwort_jTextFieldFocusLost(evt);
            }
        });

        admin_jCheckBox.setText("Admin");
        admin_jCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admin_jCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(passwort_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(benutzername_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(admin_jCheckBox)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(benutzername_jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                        .addComponent(passwort_jTextField)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(benutzername_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(benutzername_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwort_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwort_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(admin_jCheckBox)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt und Logik implementiert*/
    /*----------------------------------------------------------*/
    /**
     * Selektiert das Eingabefeld des Benutzernamens
     *
     * @param evt
     */
    private void benutzername_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_benutzername_jTextFieldFocusGained

        benutzername_jTextField.selectAll();// Eingabefeld wrd selektiert.
    }//GEN-LAST:event_benutzername_jTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt und Logik implementiert*/
    /*----------------------------------------------------------*/
    /**
     * Selektiert das Eingabefeld des Passworts
     *
     * @param evt
     */
    private void passwort_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwort_jTextFieldFocusGained

        passwort_jTextField.selectAll();
    }//GEN-LAST:event_passwort_jTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2015 Terrasi, angelegt und Logik implementiert*/
    /*----------------------------------------------------------*/
    /**
     * Beim Speichern wird geprüft ob alle notwendigen Eingaben vorhanden sind.
     * Wenn diese vorhadnen sind, wird ein User mit seinem dazugehörigem
     * Passwort angelegt. Falls Eingaben fehlen, wird eine Warnmeldung
     * ausgegeben und es wird dem User farblich gezeigt, wo die fehlenden
     * Eingabe getätigt werden müssen.
     *
     * @param evt
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed
        //Aufruf der Schnittstellenmethode um auf Vollständigkeit der Eingaben zu prüfen.
        ueberpruefen();
        try {
            if (fehlendeEingaben.isEmpty()) {
                //Übergabe der Eingaben an die Speichervariablen.
                benutzerame = benutzername_jTextField.getText();
                passwort = passwort_jTextField.getText();
                if (this.getTitle().equals("Benutzer anlegen")) {//Überprüft aus in welcher Maske man sich befindet
                    // Aufruf der erstelleBenutzer-Methode mit der ein Benutzer erzeugt wird.
                    GUIFactory.getDAO().erstelleBenutzer(benutzerame, passwort, istAdmin);
                    zuruecksetzen();
                    this.hauptFenster.setStatusMeldung(ERFOLGREICHEANMELDUNG);// Ausgabe einer Medung.
                } else if (this.getTitle().equals("Benutzer ändern")) {// Überprüft ob man sich in der Benutzer ändern Maske befindet.

                    if (!(passwort_jTextField.getText().equals(
                            GUIFactory.getDAO().gibBenutzer(benutzername_jTextField.getText()).getPasswort()))) {

                        int antwort = JOptionPane.showConfirmDialog(rootPane, AENDERUNGVONDATEN_TEXT,
                                AENDERUNGVONDATEN_TITEL, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        //Falls bejaht wird der Auftragskopf verändert gespeichert.
                        if (antwort == JOptionPane.YES_OPTION) {

                            GUIFactory.getDAO().aendereBenutzer(benutzerame, passwort, istAdmin);// Methodenaufruf zum ändern von Benutzerdaten.

                            zuruecksetzen();
                            benutzername_jTextField.setText("");

                            gespeichert = true;
                            jB_ZurueckActionPerformed(evt);
                            this.hauptFenster.setStatusMeldung(ERFOLGREICHAENDERNDERBENUTZERDATEN);// Ausgabe einer Medung.
                        } else {
                            gespeichert = false;
                        }
                    } else {

                        JOptionPane.showMessageDialog(null, KEINEAENDERUNGEN_TEXT,
                                KEINEAENDERUNGEN_Titel, JOptionPane.OK_OPTION);
                    }

//                    zuruecksetzen();//Methode die bestimmte Eingabefelder leert
//                    benutzername_jTextField.setText("");
                }
            } else {

                fehlEingabenMarkierung(fehlendeEingaben, FEHLERMELDUNG_TITEL,
                        FEHLERMELDUNG_TEXT, warningfarbe);
            }
        } catch (ApplicationException e) {
            this.hauptFenster.setStatusMeldung(e.getMessage());
        }
    }//GEN-LAST:event_jB_SpeichernActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2015 Terrasi, Implementierung der DAO-Methoden und Überarbeitung der Anwendungslogik*/
    /*----------------------------------------------------------*/
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        if (this.getTitle().equals("Benutzer anlegen")) {

            if (!(benutzername_jTextField.getText().equals("")
                    && passwort_jTextField.getText().equals(""))) {
                int antwort = JOptionPane.showConfirmDialog(rootPane, DATENVERWERFEN_TEXT,
                        DATENVERWERFEN_TITEL, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //Falls bejaht wird, werden die Daten verworfen..
                if (antwort == JOptionPane.YES_OPTION) {

                    zuruecksetzen();// Eingabefelder werden zurückgesetzt.
                    benutzername_jTextField.setText("");
                    letzteComponent = null;   //Initialisierung der Componentspeichervariable
                    //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
                    letzteComponent = this.factory.zurueckButton();
                    this.setVisible(false);// Internalframe wird nicht mehr dargestellt
                    letzteComponent.setVisible(true);// Übergebene Component wird sichtbar gemacht
                }
            } else {

                zuruecksetzen();// Eingabefelder werden zurückgesetzt.
                benutzername_jTextField.setText("");
                letzteComponent = null;   //Initialisierung der Componentspeichervariable
                //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
                letzteComponent = this.factory.zurueckButton();
                this.setVisible(false);// Internalframe wird nicht mehr dargestellt
                letzteComponent.setVisible(true);// Übergebene Component wird sichtbar gemacht
            }
        } else if (this.getTitle().equals("Benutzer anzeigen")) {
            zuruecksetzen();// Eingabefelder werden zurückgesetzt.
            benutzername_jTextField.setText("");
            letzteComponent = null;   //Initialisierung der Componentspeichervariable
            //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
            letzteComponent = this.factory.zurueckButton();
            this.setVisible(false);// Internalframe wird nicht mehr dargestellt
            letzteComponent.setVisible(true);// Übergebene Component wird sichtbar gemacht
        } else {
            if (!(passwort_jTextField.getText().equals("")
                    && gespeichert == false)) {
                int antwort = JOptionPane.showConfirmDialog(rootPane, DATENVERWERFEN_TEXT,
                        DATENVERWERFEN_TITEL, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //Falls bejaht wird, werden die Daten verworfen..
                if (antwort == JOptionPane.YES_OPTION) {

                    zuruecksetzen();// Eingabefelder werden zurückgesetzt.
                    benutzername_jTextField.setText("");
                    letzteComponent = null;   //Initialisierung der Componentspeichervariable
                    //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
                    letzteComponent = this.factory.zurueckButton();
                    this.setVisible(false);// Internalframe wird nicht mehr dargestellt
                    letzteComponent.setVisible(true);// Übergebene Component wird sichtbar gemacht
                }

            } else {
                zuruecksetzen();// Eingabefelder werden zurückgesetzt.
                benutzername_jTextField.setText("");
                letzteComponent = null;   //Initialisierung der Componentspeichervariable
                //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
                letzteComponent = this.factory.zurueckButton();
                this.setVisible(false);// Internalframe wird nicht mehr dargestellt
                letzteComponent.setVisible(true);// Übergebene Component wird sichtbar gemacht
            }
        }
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2015 Terrasi, angelegt und Logik implementiert*/
    /*----------------------------------------------------------*/
    private void jB_AnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AnzeigenActionPerformed
        if (jB_Anzeigen.getText().equals("Anzeigen")) {
            this.setStatusAnzeigen();
        } else {
            this.setStatusAender();
            
        }
    }//GEN-LAST:event_jB_AnzeigenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode in der geprüft wird ob der User ein Admin werden soll oder nur
     * ein normaler User sein soll.
     *
     * @param evt
     */
    private void admin_jCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admin_jCheckBoxActionPerformed
        //Wenn selektiert wird, erhält der anzulegende User den Status eines Admins.
        if (admin_jCheckBox.isSelected()) {
            istAdmin = true;// Übergabe eines Boelenwertes.
        } else {// Falls User kein Admin werden soll.
            istAdmin = false;
        }
    }//GEN-LAST:event_admin_jCheckBoxActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der ein Benutzer aus der Datenbank gelscht wird.
     *
     * @param evt
     */
    private void jB_LoeschenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_LoeschenActionPerformed
        benutzerame = benutzername_jTextField.getText();// Erhält den Benutzername aus dem Eingabefeld.
        try {//TryBlock
            int antwort = JOptionPane.showConfirmDialog(rootPane, BENUTZERLOESCHEN_TEXT,
                    BENUTZERLOESCHEN_TITEL, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            //Falls bejaht wird der Auftragskopf verändert gespeichert.
            if (antwort == JOptionPane.YES_OPTION) {

                GUIFactory.getDAO().loescheBenutzer(benutzerame);// Methodenaufruf zum lschen des Benutzers aus der DB.
                this.hauptFenster.setStatusMeldung(ERFOLGREICHBENUTZERGELOESCHT);//Ausgabe einer Meldung.
                this.benutzername_jTextField.setText("");// Eingabefeld ist leer nachdem man den User gelöscht hat.
            }
        } catch (ApplicationException e) {// Fehlerbehandlung.
            this.hauptFenster.setStatusMeldung(e.getMessage());//Ausgabe einer Meldung.
        }
    }//GEN-LAST:event_jB_LoeschenActionPerformed

    private void benutzername_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_benutzername_jTextFieldFocusLost
        benutzername_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
    }//GEN-LAST:event_benutzername_jTextFieldFocusLost

    private void passwort_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwort_jTextFieldFocusLost
        passwort_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
    }//GEN-LAST:event_passwort_jTextFieldFocusLost

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man dem eingabefeldes des Benutzernamens eine String
     * übergibt.
     *
     * @param benutzername, String der übergebn wird und angezeigt wird.
     */
    public void setBenutzername(String benutzername) {
        benutzername_jTextField.setText(benutzername);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man einen String dem Eingabefeldes des Passworts
     * übergibt.
     *
     * @param passwort, String das das Passwort repräsentiert wird übergeben und
     * dargestellt.
     */
    public void setPasswort(String passwort) {
        passwort_jTextField.setText(passwort);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man die Checkbox, um den Zustand des User festzulegen,
     * auf enable false oder true setzen kann.
     *
     * @param bearbeitung, bolleanwert mit der man entscheidet ob die Checkbox
     * bearbeitet werden kann.
     */
    public void setZustand(boolean bearbeitung) {
        admin_jCheckBox.setEnabled(bearbeitung);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 13.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    /**
     * Methode um die Checkbox zu selektieren.
     *
     * @param selektieren, Variable mit der man die Checkboy selektiert oder
     * nicht.
     */
    public void setCheckBoxSelected(boolean selektieren) {
        this.admin_jCheckBox.setSelected(selektieren);// Checkbox wird selektiert oder nicht.

    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 17.01.2015 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    public void setBenutzername_jTextField(String benutzername) {
        this.benutzername_jTextField.setText(benutzername);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox admin_jCheckBox;
    private javax.swing.JLabel benutzername_jLabel;
    private javax.swing.JTextField benutzername_jTextField;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel passwort_jLabel;
    private javax.swing.JTextField passwort_jTextField;
    // End of variables declaration//GEN-END:variables
}
