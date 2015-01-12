package GUI_Internalframes;

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
 * 10.12.2014 Dokumentation und Logik
 * 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button
 */
public class User_anlegen extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {

    /*
     Speichervariablen
     */
    ArrayList<Component> fehlendeEingaben;// ArrayList für Eingabefelder.
    Component c;
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
    String fehlermeldung_titel = "Fehlerhafte Eingabe";
    String fehlermeldung_text = "Es sind nicht alle Eingaben getätigt worden.\n"
            + "Bitte geben Sie alle Eingaben ein um fortzufahren.";

    /**
     * Creates new form Fenster
     */
    public User_anlegen(GUIFactory factory, InterfaceMainView mainView) {
        initComponents();
        this.factory = factory;
        this.hauptFenster = mainView;
        //Initialisierung der Speichervariblen
        fehlendeEingaben = new ArrayList<Component>();
    }

    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden.
     */
    @Override
    public void zuruecksetzen() {
        //Eingabefelder erhalten einen leeren String
        benutzername_jTextField.setText("");
        passwort_jTextField.setText("");
    }

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
        } else if (passwort_jTextField.getText().equals("")) {
            fehlendeEingaben.add(passwort_jTextField);
        }
    }

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
        list.get(0).requestFocusInWindow();// Fokus gelangt in das erste leere Eingabefeld
        // Alle leeren Eingabefelder werden farblich markiert.
        for (int i = 0; i <= list.size() - 1; i++) {
            list.get(i).setBackground(farbe);
        }

        list.clear();//ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /*----------------------------------------------------------*/
    public void setStatusAnzeigen() {
        this.setTitle("Benutzer anzeigen");
        zuruecksetzen();
        this.benutzername_jTextField.setEnabled(false);
        this.passwort_jTextField.setEnabled(false);        
        jB_Anzeigen.setText("Ändern");
        jB_Anzeigen.setEnabled(true);
        jB_Speichern.setEnabled(false);
        jB_Loeschen.setEnabled(false);
        passwort_Generieren_jButton.setEnabled(false);
        this.hauptFenster.setComponent(this);
    }
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Internalframe nicht mehr als Anzeigefenster 
     * dargestellt wird, sondern als Fenster in dem man Daten ändern kann.
     */
    public void setStatusAender(){
        this.setTitle("Benutzer ändern");
        zuruecksetzen();
        this.benutzername_jTextField.setEnabled(true);
        this.passwort_jTextField.setEnabled(true);        
        jB_Anzeigen.setText("Anzeigen");
        jB_Anzeigen.setEnabled(true);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(true);
        passwort_Generieren_jButton.setEnabled(true);
        this.hauptFenster.setComponent(this);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public void setStatusAnlegen(){
        this.setTitle("Benutzer anlegen");
        zuruecksetzen();
        this.benutzername_jTextField.setEnabled(true);
        this.passwort_jTextField.setEnabled(true);        
        jB_Anzeigen.setText("Anzeigen");
        jB_Anzeigen.setEnabled(false);
        jB_Speichern.setEnabled(true);
        jB_Loeschen.setEnabled(true);
        passwort_Generieren_jButton.setEnabled(true);
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
        statuszeile_jTextField = new javax.swing.JTextField();
        passwort_Generieren_jButton = new javax.swing.JButton();
        passwort_jTextField = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
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
        jB_Anzeigen.setEnabled(false);
        jB_Anzeigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_AnzeigenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setText("Löschen");
        jB_Loeschen.setEnabled(false);
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setText("Suchen");
        jToolBar1.add(jB_Suchen);

        jSeparator1.setEnabled(false);

        benutzername_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        benutzername_jLabel.setText("Benutzername : ");

        passwort_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        passwort_jLabel.setText("Passwort          :");

        benutzername_jTextField.setText("jTextField1");
        benutzername_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                benutzername_jTextFieldFocusGained(evt);
            }
        });

        statuszeile_jTextField.setText("Statuszeile");
        statuszeile_jTextField.setEnabled(false);

        passwort_Generieren_jButton.setText("Passwort generieren");
        passwort_Generieren_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwort_Generieren_jButtonActionPerformed(evt);
            }
        });

        passwort_jTextField.setText("jTextField1");
        passwort_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwort_jTextFieldFocusGained(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(statuszeile_jTextField)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(passwort_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(benutzername_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(benutzername_jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(passwort_jTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwort_Generieren_jButton)
                .addContainerGap(67, Short.MAX_VALUE))
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
                    .addComponent(passwort_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwort_Generieren_jButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(statuszeile_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Es wird ein Passwort wird den anzulegenden User generiert. Das generierte
     * PAsswort erscheint im Eingabefeld.
     *
     * @param evt
     */
    private void passwort_Generieren_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwort_Generieren_jButtonActionPerformed
        //Algo zum passwortgenerieren

        // übergabe des passworts in das eingabefeld.
    }//GEN-LAST:event_passwort_Generieren_jButtonActionPerformed

    /**
     * Selektiert das Eingabefeld des Benutzernamens
     *
     * @param evt
     */
    private void benutzername_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_benutzername_jTextFieldFocusGained
        benutzername_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        benutzername_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
    }//GEN-LAST:event_benutzername_jTextFieldFocusGained

    /**
     * Selektiert das Eingabefeld des Passworts
     *
     * @param evt
     */
    private void passwort_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwort_jTextFieldFocusGained
        passwort_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        passwort_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
    }//GEN-LAST:event_passwort_jTextFieldFocusGained

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
        if (fehlendeEingaben.isEmpty()) {
//            GUIFactory.getDAO().
            //User anlegen
            zuruecksetzen();//Methode die bestimmte Eingabefelder leert
        } else {
            fehlEingabenMarkierung(fehlendeEingaben, fehlermeldung_titel,
                    fehlermeldung_text, warningfarbe);
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

    private void jB_AnzeigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AnzeigenActionPerformed
        if(jB_Anzeigen.getText().equals("Anzeigen")){
            this.setStatusAnzeigen();
        }else{
            this.setStatusAender();
        }
    }//GEN-LAST:event_jB_AnzeigenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel benutzername_jLabel;
    private javax.swing.JTextField benutzername_jTextField;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton passwort_Generieren_jButton;
    private javax.swing.JLabel passwort_jLabel;
    private javax.swing.JTextField passwort_jTextField;
    private javax.swing.JTextField statuszeile_jTextField;
    // End of variables declaration//GEN-END:variables
}
