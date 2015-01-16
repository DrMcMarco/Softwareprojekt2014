/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.Benutzer;
import JFrames.GUIFactory;
import java.awt.Component;
import Interfaces.InterfaceViewsFunctionality;
import Interfaces.InterfaceMainView;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Luca Terrasi
 *
/* 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button */
/* 08.01.2015  Terrasi, implementierung der Schnittstellenmethoden und 
   der Ändern/Anzeigen Funktion. */
public class User_andernEinstieg extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {

    /*
     Hilfsvariablen
     */
    Component c;
    GUIFactory factory;
    User_anlegen userAnlegen;
    InterfaceMainView hauptFenster;

    /*
     Speichervariablen
     */
    ArrayList<Component> fehleingabefelder;

    /**
     * Creates new form Fenster
     */
    /**
     * Konstruktor, Erzeugung eines UseraendernEinstiegobjektes.
     *
     * @param factory
     */
    public User_andernEinstieg(GUIFactory factory, User_anlegen user, InterfaceMainView main) {
        initComponents();
        this.factory = factory;
        this.userAnlegen = user;
        this.hauptFenster = main;
        fehleingabefelder = new ArrayList<>();
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
        BenutzerID_jLabel = new javax.swing.JLabel();
        BenutzerID_jTextField = new javax.swing.JTextField();
        Weiter_jButton = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("User ändern");
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

        jSeparator1.setEnabled(false);

        BenutzerID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BenutzerID_jLabel.setLabelFor(BenutzerID_jTextField);
        BenutzerID_jLabel.setText("Benutzer-ID :");
        BenutzerID_jLabel.setToolTipText("");

        BenutzerID_jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BenutzerID_jTextFieldKeyPressed(evt);
            }
        });

        Weiter_jButton.setText("Weiter");
        Weiter_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Weiter_jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(BenutzerID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BenutzerID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Weiter_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BenutzerID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BenutzerID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Weiter_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(190, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void Weiter_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Weiter_jButtonActionPerformed

        Benutzer benutzer;// Anlegen eines Bentzers
        
        // Überprüft anhand des Framestitels, ob es das nächste Fenster
        // im Anzeigen-/ oder im Ändernmodus anzeigen soll.
        if (fehleingabefelder.isEmpty()) {
            try {
                //Initialisierung eines Benutzers.
                benutzer = GUIFactory.getDAO().gibBenutzer(BenutzerID_jTextField.getText());

                if (benutzer != null) {

                    if (this.getTitle().equals("Benutzer ändern")) {
                        this.userAnlegen.setStatusAender();// Setzt das Internalframe in den Ändernmodus.
                        this.userAnlegen.setBenutzername(benutzer.getBenutzername());// Benutzername aus der DB wird im Eingabefeld angezeigt.
                        if(benutzer.isIstAdmin()){
                            this.userAnlegen.setCheckBoxSelected(true);// Ceckbox wird selektiert.
                        }else{
                            this.userAnlegen.setCheckBoxSelected(false);//Checkbox wird nicht selektiert.
                        }
                        this.userAnlegen.setZustand(false);// Checkbox wird auf Enable false gesetzt.
                        zuruecksetzen();//Methode die bestimmte Eingabefelder leert
                        this.setVisible(false);
                        this.hauptFenster.setFrame(this.userAnlegen);// Hauptfenster macht übergebene Maske sichtbar.
                    } else {
                        this.userAnlegen.setStatusAnzeigen();// Setzt das Internalframe in den Anzeigenmodus.
                        this.userAnlegen.setBenutzername(benutzer.getBenutzername());// Benutzername aus der DB wird im Eingabefeld angezeigt.
                        this.userAnlegen.setCheckBoxSelected(false);//Checkbox wird nicht selektiert.
                        this.userAnlegen.setZustand(false);// Checkbox wird auf Enable false gesetzt.
                        zuruecksetzen();//Methode die bestimmte Eingabefelder leert
                        this.setVisible(false);
                        this.hauptFenster.setFrame(this.userAnlegen);// Hauptfenster macht übergebene Maske sichtbar.
                    }
                } else {

                }
            } catch (ApplicationException e) {
                this.hauptFenster.setStatusMeldung(e.getMessage());
            }
        }
    }//GEN-LAST:event_Weiter_jButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, angelegt, Dokumentation und Logik. */
    /*----------------------------------------------------------*/
    private void BenutzerID_jTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BenutzerID_jTextFieldKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            Weiter_jButtonActionPerformed(null);
        }
    }//GEN-LAST:event_BenutzerID_jTextFieldKeyPressed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden.
     */
    @Override
    public void zuruecksetzen() {
        BenutzerID_jTextField.setText("");
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der geprüft wird ob alle Eingaben getätigt
     * worden sind.
     *
     */
    @Override
    public void ueberpruefen() {
        //IF-Anweisungen mit denen geprüft wird welche eingabefelder keine Eingabe 
        // erhalten haben. Diese Eingabefelder werden in passende Speichervariablen festgehalten.

        //Eingabefeld für Auftragskopf werden in Variable "fehleingabefelder" feestgehalten.
        if (BenutzerID_jTextField.getText().equals("")) {
            fehleingabefelder.add(BenutzerID_jTextField);
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi angelegt,Logik und Dokumentation */
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
    /* 08.01.2015 Terrasi angelegt,Logik und Dokumentation */
    /*----------------------------------------------------------*/
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
    private javax.swing.JLabel BenutzerID_jLabel;
    private javax.swing.JTextField BenutzerID_jTextField;
    private javax.swing.JButton Weiter_jButton;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
