/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Internalframes;

import Documents.*;
import Interfaces.*;
import JFrames.GUIFactory;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
 *
 * @author Luca Terrasi
 *
 * 10.12.2014 Terrasi, Dokumentation und Logik
 * 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button
 * 06.01.2015 Terrasi, Anwendungslogik für das anzeigen der Auftragspositions-
 * maske.
 * 08.01.2015 Terrasi, Überarbeitung der Anwendungslogik 
 * und das hinzufügen von weiteren Funktion.
 */
public class AuftragspositionAendern extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {

    /*
     Speichervariablen
     */
    ArrayList<Component> fehlendeEingaben;// ArrayList für Eingabefelder des Auftragkopfes.
    
    Component c;
    GUIFactory factory;
    AuftragspositionAnzeigen auftragspositionAnzeigen;
    InterfaceMainView hauptFenster;  
    

    /*
     Syntax
     */
    private static final String auftragskopfID_syntax = "\\d{1,9}?";
    private static final String auftragspositionsID_syntax = "\\d{1,9}?";

    /*
     Variablen für Farben
     */
    Color warningfarbe = Color.YELLOW;
    Color hintergrundfarbe = Color.WHITE;

    /*
     Augabetexte für Meldungen
     */
    String fehlermeldung_titel = "Fehlerhafte Eingabe";
    String fehlermeldungAuftragskopfIDtext = "\"Die eingegebene Auftragskopf-ID ist nicht gültig! "
            + "\\n Bitte geben Sie eine gültige Auftragskopf-ID ein. (z.B. 1 oder 999999999)\"";
    String fehlermeldungAuftragspositionIDtext = "\"Die eingegebene Auftragspositions-ID ist nicht gültig! "
            + "\\n Bitte geben Sie eine gültige AuftragspositionID_jTextField ein. (z.B. 1 oder 999999999)\"";
    String fehlermeldung_unvollständig = "Fehlerhafte Eingabe";
    String fehlermeldungVollstaendigkeitAuftragskopf = " Es wurde keine Auftragskopf-Id eingegeben.\n"
            + "Bitte geben sie eine Auftragskopf-Id ein.";

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, Dokumentation und Logik. */
    /* 06.01.2015 Terrasi, hinzufügen eines Interfaceobjektes
        und eines AuftragspositionAnzeigenobjektes.*/
    /*----------------------------------------------------------*/
    /**
     * Konstruktor, 
     * Erzeugung eines Auftragspositionsobjektes.
     * @param factory
     * @param positionAnzeigenView
     * @param mainView 
     */
    public AuftragspositionAendern(GUIFactory factory, 
            AuftragspositionAnzeigen positionAnzeigenView,
            InterfaceMainView mainView ) {
        initComponents();

        this.factory = factory;
        this.auftragspositionAnzeigen = positionAnzeigenView;
        this.hauptFenster = mainView;

        //Initialisierung der Speichervariblen
        fehlendeEingaben = new ArrayList<>();

        //Zuweisung der Documents
        AuftragskopfID_jTextField.setDocument(new UniversalDocument("0123456789", false));
        AuftragspositionID_jTextField.setDocument(new UniversalDocument("0123456789", false));
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
        jTextField1 = new javax.swing.JTextField();
        AuftragskopfID_jLabel = new javax.swing.JLabel();
        AuftragskopfID_jTextField = new javax.swing.JTextField();
        Enter_jButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        AuftragspositionID_jTextField = new javax.swing.JTextField();

        setResizable(true);
        setTitle("Auftragsposition ändern");
        setPreferredSize(new java.awt.Dimension(600, 400));
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
        jB_Speichern.setEnabled(false);
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setText("Anzeige/Ändern");
        jB_Anzeigen.setEnabled(false);
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setText("Löschen");
        jB_Loeschen.setEnabled(false);
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setText("Suchen");
        jToolBar1.add(jB_Suchen);

        jTextField1.setEditable(false);
        jTextField1.setText("Statuszeile");
        jTextField1.setEnabled(false);

        AuftragskopfID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        AuftragskopfID_jLabel.setLabelFor(AuftragskopfID_jTextField);
        AuftragskopfID_jLabel.setText("Auftragskopf-ID :");
        AuftragskopfID_jLabel.setToolTipText("");

        AuftragskopfID_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AuftragskopfID_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                AuftragskopfID_jTextFieldFocusLost(evt);
            }
        });

        Enter_jButton.setText("Enter");
        Enter_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Enter_jButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setLabelFor(AuftragspositionID_jTextField);
        jLabel1.setText("Auftragsposition-ID :");

        AuftragspositionID_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AuftragspositionID_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                AuftragspositionID_jTextFieldFocusLost(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextField1)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AuftragskopfID_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AuftragspositionID_jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(AuftragskopfID_jTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Enter_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AuftragskopfID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AuftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(AuftragspositionID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Enter_jButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Beim wählen des Eingabefeldes, wird alles selektiert.
     *
     * @param evt
     */
    private void AuftragskopfID_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AuftragskopfID_jTextFieldFocusGained
        AuftragskopfID_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        AuftragskopfID_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
        AuftragskopfID_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_AuftragskopfID_jTextFieldFocusGained

    /**
     * Beim wählen des Eingabefeldes, wird alles selektiert.
     *
     * @param evt
     */
    private void AuftragspositionID_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AuftragspositionID_jTextFieldFocusGained
        AuftragspositionID_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        AuftragspositionID_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
        AuftragspositionID_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_AuftragspositionID_jTextFieldFocusGained

    /**
     * Beim Focuslost des Eingabefeldes für die Auftragskopf-ID, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void AuftragskopfID_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AuftragskopfID_jTextFieldFocusLost
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(AuftragskopfID_jTextField, auftragskopfID_syntax,
                fehlermeldung_titel, fehlermeldungAuftragskopfIDtext);
    }//GEN-LAST:event_AuftragskopfID_jTextFieldFocusLost

    /**
     * Beim Focuslost des Eingabefeldes für die Auftragspositions-ID, wird auf
     * die Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void AuftragspositionID_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AuftragspositionID_jTextFieldFocusLost
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(AuftragspositionID_jTextField, auftragspositionsID_syntax,
                fehlermeldung_titel, fehlermeldungAuftragspositionIDtext);
    }//GEN-LAST:event_AuftragspositionID_jTextFieldFocusLost

    // Falls alle Eingaben getätigt wurden sind, wird nach der gesuchten Auftragsposition
    // gesucht.
    // 08.01.2015
    private void Enter_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Enter_jButtonActionPerformed
        ueberpruefen();
        if (fehlendeEingaben.isEmpty()) {
            // Überprüft anhand des Framestitels, ob es das nächste Fenster
            // im Anzeigen-/ oder im Ändernmodus anzeigen soll.
            if(this.getTitle().equals("Auftragsposition ändern")){
                this.auftragspositionAnzeigen.setStatusAender();// Setzt das Internalframe in den Ändernmodus.
                zuruecksetzen();//Methode die bestimmte Eingabefelder leert
                this.setVisible(false);
                this.hauptFenster.setFrame(this.auftragspositionAnzeigen);// Hauptfenster macht übergebene Maske sichtbar.
            }else{
                this.auftragspositionAnzeigen.setStatusAnzeigen();// Setzt das Internalframe in den Anzeigenmodus.
                zuruecksetzen();//Methode die bestimmte Eingabefelder leert
                this.setVisible(false);
                this.hauptFenster.setFrame(this.auftragspositionAnzeigen);// Hauptfenster macht übergebene Maske sichtbar.
            }
            
        } else {//Wenn Eingaben fehlen.
            // Methodenaufruf um daraufhinzuweisen das nicht alle eingaben 
            // getätigt worden sind
            fehlEingabenMarkierung(fehlendeEingaben, fehlermeldung_titel,
                    fehlermeldungAuftragskopfIDtext, warningfarbe);//Meldung wird ausgegeben und          
        }
    }//GEN-LAST:event_Enter_jButtonActionPerformed

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

    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden
     */
    @Override
    public void zuruecksetzen() {
        //Eingabefelder erhalten einen leeren String
        AuftragskopfID_jTextField.setText("");
        AuftragspositionID_jTextField.setText("");
    }

    /*
     Schnittstellenmethode mit der geprüft wird ob alle Eingaben getätigt worden sind.
     */
    @Override
    public void ueberpruefen() {
        //IF-Anweisungen mit denen geprüft wird welche Eingabefelder keine Eingabe 
        // erhalten haben. Diese Eingabefelder werden in passende Speichervariablen festgehalten

        //Eingabefelder für Auftragsposition werden in Variable "fehlendeEingaben" festgehalten.
        if (AuftragskopfID_jTextField.getText().equals("")) {
            fehlendeEingaben.add(AuftragskopfID_jTextField);
        } else if (AuftragspositionID_jTextField.getText().equals("")) {
            fehlendeEingaben.add(AuftragspositionID_jTextField);
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
        if (!textfield.getText().matches(syntax)) {
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
            //Mit dem Focus in das übergebene Eingabefeld springen
            textfield.requestFocusInWindow();
            textfield.selectAll();
        }
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AuftragskopfID_jLabel;
    private javax.swing.JTextField AuftragskopfID_jTextField;
    private javax.swing.JTextField AuftragspositionID_jTextField;
    private javax.swing.JButton Enter_jButton;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
