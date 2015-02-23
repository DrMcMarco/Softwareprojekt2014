/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Internalframes;

import DAO.ApplicationException;
import JFrames.Login;
import JFrames.*;
import GUI_Internalframes.*;
import DTO.*;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

/**
 *
 * @author Luca Terrasi
 *
 * 05.01.2015 Terrasi, Erstellung und Dokumentation 
 * 06.01.2015 Terrasi, Übergabe einer Stausmeldung implementiert
 */
public class Anmeldung extends javax.swing.JInternalFrame {

    /*
     Erzeugung von Varibalen für Objekte
     */
    GUIFactory factory;// erzeugung von GuiFactory.
    Benutzer benutzer;// erzeugung eines Benutzers.
    StartAdmin adminStart;//Erzuegung der Adminansicht.
    Start userStart;// Erzeugung der Useransicht.
    Login login;// Erzeugung des Anmeldefensters.

    //Variablen für Meldungen
    final String WILLKOMMENSMELDUNG = "Willkommen ";
    final String FEHLERMELDUNGSTITEL = "Fehler";
    

    
    /**
     * Erstellt neues Hauptmenü.
     * @param test login
     */
    public Anmeldung(Login test) {
        initComponents();
        this.login = test;
        // Try-Block
        try {
            factory = new GUIFactory();// Erzeugung eines Guifactoryobjektes.
            // Erzeugung eines DAO-Objektes.
        } catch (PersistenceException e) {// Fehlerbehandlung falls bei der 
            // Erzeugung etwas nicht funktioniert hat.
            
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, e.getMessage(), 
                    FEHLERMELDUNGSTITEL, JOptionPane.ERROR_MESSAGE);
        }
        benutzer = new Benutzer();// Initialisierung eines Benutzers.

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Benutzername_Label = new javax.swing.JLabel();
        Passwort_Label = new javax.swing.JLabel();
        benutzername_jTextField = new javax.swing.JTextField();
        passwort_jPasswordField = new javax.swing.JPasswordField();
        passwort_vergessen_label = new java.awt.Label();
        Anmelde_button = new java.awt.Button();

        setTitle("Anmeldung");
        setVisible(true);

        jPanel1.setPreferredSize(new java.awt.Dimension(531, 400));

        Benutzername_Label.setText("Benutzername    :");
        Benutzername_Label.setToolTipText("");

        Passwort_Label.setLabelFor(passwort_jPasswordField);
        Passwort_Label.setText("Passwort            :");

        benutzername_jTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        benutzername_jTextField.setText("Benutzername");
        benutzername_jTextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        benutzername_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                benutzername_jTextFieldFocusGained(evt);
            }
        });

        passwort_jPasswordField.setText("jPasswordField1");
        passwort_jPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwort_jPasswordFieldFocusGained(evt);
            }
        });
        passwort_jPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwort_jPasswordFieldKeyPressed(evt);
            }
        });

        passwort_vergessen_label.setText("Passwort vergessen");

        Anmelde_button.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Anmelde_button.setLabel("Anmelden");
        Anmelde_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Anmelde_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Anmelde_button, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Passwort_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Benutzername_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(benutzername_jTextField)
                            .addComponent(passwort_jPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwort_vergessen_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(54, 54, 54))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Benutzername_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(benutzername_jTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Passwort_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwort_jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(passwort_vergessen_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addComponent(Anmelde_button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 05.01.2015 Terrasi, Erstellung
     * 
     * Es wird überprüft ob der Benutzername und das dazugehrige Passwort
     * bereits in der Datenbank hinterlegt ist. Falls der Suer in der DB
     * existiert, wird überprüft ob der User ein Admin ist oder nicht.
     * Es wird dann bei erfolgreichen Anmeldung und überprüfung der Identität
     * des Users, wird das entsprechende Fenster aufgerufen und man erhält
     * eine Statusmeldung im jeweiligen Fenster.
     * @param evt 
     */
    private void Anmelde_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Anmelde_buttonActionPerformed
        try {//Try-Block, falls Benutzername oder Passwort nicht mit DB
            // Einträgen übereinstimmen, wird der Fehler passend im Catchblock
            // abgefangen.
            
            // Benutzer bekommt Daten von der DB.
            // DAO-Methode prüft ob Benutzername und Passwort mit hinterlegten
            // Daten in der DB übereinstimmen.Falls ja erhält das Benutzer-Objekt
            // Daten von der DB.
            benutzer = GUIFactory.getDAO().login(benutzername_jTextField.getText()
                    , new String(passwort_jPasswordField.getPassword()));
            
            if (benutzer == null) {
                throw new ApplicationException("Fehler",
                        "Das Passwort ist falsch!");
            }

            if (benutzer.isIstAdmin()) {//Überprüfung ob der user ein Admin ist
                adminStart = new StartAdmin(this.login);// Initialisierung der Adminansicht.
                adminStart.setStatusMeldung(WILLKOMMENSMELDUNG 
                        + benutzername_jTextField.getText());// Übergabe einer Statusmeldung
                adminStart.setVisible(true);// Adminansicht wird sichtbar.
                this.login.setVisible(false);//Anmeldefenster wird nicht mehr 
                //sichtbar dargestellt.
            } else {
                userStart = new Start(this.login);// Initialisierung der Useransicht.
                userStart.setStatusMeldung(WILLKOMMENSMELDUNG 
                        + benutzername_jTextField.getText());// Übergabe einer Statusmeldung
                // an die User ansicht.
                userStart.setVisible(true);// Useransicht wird sichtbar.
                this.login.setVisible(false);//Anmeldefenster wird nicht mehr 
                //sichtbar dargestellt.
            }

        } catch (ApplicationException e) {
            // Fehlerausgabe falls Passwort oder Benutzernamen nicht in der
            // DB vorhanden sind.
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, e.getMessage(), 
                    FEHLERMELDUNGSTITEL, JOptionPane.WARNING_MESSAGE);
            
        }
    }//GEN-LAST:event_Anmelde_buttonActionPerformed

    /**
     * 06.01.2015 Terrasi, Erstellung
     * Methode mit der alles ausgewählt wird wen man das Feld wählt. 
     * @param evt 
     */
    private void benutzername_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_benutzername_jTextFieldFocusGained
        benutzername_jTextField.selectAll();// Selektiert das Eingabefeld.
    }//GEN-LAST:event_benutzername_jTextFieldFocusGained

    /**
     * 06.01.2015 Terrasi, Erstellung
     * Methode mit der alles ausgewählt wird wen man das Feld wählt. 
     * @param evt 
     */
    private void passwort_jPasswordFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwort_jPasswordFieldFocusGained
        passwort_jPasswordField.selectAll();// Selektiert das Eingabefeld.
    }//GEN-LAST:event_passwort_jPasswordFieldFocusGained

    /**
     * 14.01.2015 Terrasi, Erstellung. 
     * 
     * Methode mit der man anhand des Enterbuttons, man die gleiche Funktion
     * aufruft, wie wenn man auf den Anmeldebutton klickt.
     * @param evt 
     */
    private void passwort_jPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwort_jPasswordFieldKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            Anmelde_buttonActionPerformed(null);
        }
    }//GEN-LAST:event_passwort_jPasswordFieldKeyPressed

    public void zurueckSetzen(){
        benutzername_jTextField.setText("");
        passwort_jPasswordField.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button Anmelde_button;
    private javax.swing.JLabel Benutzername_Label;
    private javax.swing.JLabel Passwort_Label;
    private javax.swing.JTextField benutzername_jTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField passwort_jPasswordField;
    private java.awt.Label passwort_vergessen_label;
    // End of variables declaration//GEN-END:variables
}
