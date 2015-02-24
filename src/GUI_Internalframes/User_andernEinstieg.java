package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.Benutzer;
import JFrames.GUIFactory;
import java.awt.Component;
import Interfaces.InterfaceViewsFunctionality;
import Interfaces.InterfaceMainView;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Luca Terrasi
 */
/* 10.12.2014 Terrasi,Erstellung
 */
/* 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button
 */
/* 08.01.2015  Terrasi, implementierung der Schnittstellenmethoden und 
 der Ändern/Anzeigen Funktion. */
/* 10.03.2015 Terrasi,  getestet und freigegeben */
/* 18.02.2015 TER, getestet und freigegeben */
public class User_andernEinstieg extends javax.swing.JInternalFrame
        implements InterfaceViewsFunctionality {

    // Hilfsvariablen
    Component c;
    GUIFactory factory;
    User_anlegen userAnlegen;
    InterfaceMainView hauptFenster;

    // Konstanten für Meldungen.
    final String USER_NICHT_GEFUNDEN
            = "Keine passender Benutzer in der Datenbank.";
    final String KEINE_EINGABE = "Bitte geben Sie einen Benutzernamen ein.";
    final String FEHLER = "Fehler bei Eingabe ";

    // Speichervariablen
    ArrayList<Component> fehleingabefelder; // Liste für Fehleingaben.

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.14 TER Erstellung */
    /* 18.02.15 TER getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Konstruktor, Erzeugung eines UseraendernEinstiegobjektes.
     *
     * @param factory, Übergabe eines GUIFactoryobjektes.
     * @param user, Übergabe eines Useranlegenobjektes.
     * @param main, Übergabe eines InterfaceMainViewobjektes
     */
    public User_andernEinstieg(GUIFactory factory, User_anlegen user,
            InterfaceMainView main) {
        initComponents();
        //Übergabe der Parameter.
        this.factory = factory;
        this.userAnlegen = user;
        this.hauptFenster = main;

        //Initialisierung einer ArrayList für fehlende Eingaben.
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

        setTitle("User ändern Einstieg");
        setPreferredSize(new java.awt.Dimension(580, 300));
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
        jB_Zurueck.setToolTipText("Hauptmenü");
        jB_Zurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ZurueckActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Zurueck);

        jB_Speichern.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Speichern.PNG"))); // NOI18N
        jB_Speichern.setEnabled(false);
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Bearbeiten.PNG"))); // NOI18N
        jB_Anzeigen.setEnabled(false);
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Löschen.PNG"))); // NOI18N
        jB_Loeschen.setEnabled(false);
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Suche.PNG"))); // NOI18N
        jB_Suchen.setEnabled(false);
        jToolBar1.add(jB_Suchen);

        jSeparator1.setEnabled(false);

        BenutzerID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BenutzerID_jLabel.setLabelFor(BenutzerID_jTextField);
        BenutzerID_jLabel.setText("Benutzername :");
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
                .addComponent(BenutzerID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BenutzerID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Weiter_jButton)
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BenutzerID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BenutzerID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Weiter_jButton))
                .addContainerGap(141, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.14 TER Erstellung */
    /* 16.02.14 TER Überarbeitung */
    /* 18.02.15 TER getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        c = null;   //Initialisierung der Componentspeichervariable
        // Erhalten über GUIFactorymethode die letzte aufgerufene View 
        // und speichern diese in Variable.
        c = this.factory.zurueckButton();// Übergabe des Hauptmenüs
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        c.setVisible(true);// Übergebene Component wird sichtbar gemacht
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.14 TER Erstellung */
    /* 16.02.14 TER Überarbeitung */
    /* 18.02.15 TER getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Weiter-Button Funktion.
     *
     * @param evt
     */
    private void Weiter_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Weiter_jButtonActionPerformed
        String benutzerAendernEinstieg = "Benutzer ändern Einstieg";
        Benutzer benutzer;// Anlegen eines Bentzers
        try {
            //Initialisierung eines Benutzers.
            if (!BenutzerID_jTextField.getText().equals("")) {
                // Aufruf des Benutzers der in der DB hinterlegt ist.
                benutzer = GUIFactory.getDAO().
                        gibBenutzer(BenutzerID_jTextField.getText());

                // Wird geprüft ob eseien Benutzer gibt.
                if (benutzer != null) {
                    // Benutzer ist vorhanden und nund wird geprüft welche Maske die
                    // Funktion aufruft.
                    if (this.getTitle().equals(benutzerAendernEinstieg)) {
                        // Benutzername aus der DB wird im Eingabefeld angezeigt.
                        this.userAnlegen.setBenutzername(
                                benutzer.getBenutzername());
                        if (benutzer.isIstAdmin()) {//Falls Benutzer Admin ist.
                            // Ceckbox wird selektiert.
                            this.userAnlegen.setCheckBoxSelected(true);
                        } else {
                            // Checkbox wird nicht selektiert.
                            this.userAnlegen.setCheckBoxSelected(false);
                        }
                        // Setzt das Internalframe in den Ändernmodus.
                        this.userAnlegen.setStatusAender();
                        //Methode die bestimmte Eingabefelder leert
                        zuruecksetzen();
                        this.setVisible(false);//Maske ist nicht mehr Sichtbar.
                        // Hauptfenster macht übergebene Maske sichtbar.
                        this.hauptFenster.setFrame(this.userAnlegen);

                    } else {// Falls Maske nicht "Benutzer ändern Einstieg" ist.

                        // Setzt das Internalframe in den Anzeigenmodus.
                        this.userAnlegen.setStatusAnzeigen();
                        // Benutzername aus der DB wird im Eingabefeld angezeigt.
                        this.userAnlegen.setBenutzername(
                                benutzer.getBenutzername());
                        if (benutzer.isIstAdmin()) {//Falls Benutzer Admin ist.                       
                            // Ceckbox wird selektiert.
                            this.userAnlegen.setCheckBoxSelected(true);
                        } else {
                            // Checkbox wird nicht selektiert.
                            this.userAnlegen.setCheckBoxSelected(false);
                        }
                        // Checkbox wird auf Enable false gesetzt.
                        this.userAnlegen.setZustand(false);
                        //Methode die bestimmte Eingabefelder leert
                        zuruecksetzen();
                        this.setVisible(false);//Maske ist nicht mehr Sichtbar.
                        // Hauptfenster macht übergebene Maske sichtbar.
                        this.hauptFenster.setFrame(this.userAnlegen);
                    }
                }
            } else {

                this.hauptFenster.setStatusMeldung(KEINE_EINGABE);
            }
        } catch (ApplicationException e) {//Fehlerbehandlung "ApplicationException"
            //Fehler wird in der Statuszeile übergeben.
            this.hauptFenster.setStatusMeldung(USER_NICHT_GEFUNDEN);
            // eingabefeld erhält leeren String
            BenutzerID_jTextField.setText("");
        }
    }//GEN-LAST:event_Weiter_jButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 TER, Erstellung, Dokumentation und Logik. */
    /* 18.02.15 TER getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode um mit dem Enter-Button die Weiter-Button Funktion zu betätigen.
     *
     * @param evt
     */
    private void BenutzerID_jTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BenutzerID_jTextFieldKeyPressed
        // Wird geprft ob die Entertste betätigt worden ist.
        if (evt.getKeyCode() == evt.VK_ENTER) {
            // Weiter_ActionPerformed wird ausgeführt.
            Weiter_jButtonActionPerformed(null);
        }
    }//GEN-LAST:event_BenutzerID_jTextFieldKeyPressed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 TER angelegt,Logik und Dokumentation */
    /* 18.02.15 TER getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden.
     */
    @Override
    public void zuruecksetzen() {
        BenutzerID_jTextField.setText(""); // Feld erhält leeren String
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 TER angelegt,Logik und Dokumentation */
    /* 18.02.15 TER getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der geprüft wird ob alle Eingaben getätigt
     * worden sind.
     *
     */
    @Override
    public void ueberpruefen() {
        // IF-Anweisungen mit denen geprüft wird, welche Eingabefelder  
        // keine Eingabe erhalten haben. Diese Eingabefelder werden in der 
        // passenden Speichervariable festgehalten.

        if (BenutzerID_jTextField.getText().equals("")) {
            fehleingabefelder.add(BenutzerID_jTextField);// Übergabe des Feldes.
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 TER angelegt,Logik und Dokumentation */
    /* 18.02.15 TER getestet und freigegeben */
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
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax,
            String fehlermelgungtitel, String fehlermeldung) {
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
    /* 08.01.2015 TER angelegt,Logik und Dokumentation */
    /* 18.02.15 TER getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der die Eingabefelder die nicht ausgefüllt
     * worden sind, farblich markiert werden und eine Meldung ausgegeben wird,
     * inder der Benutzer darauf hingewiesen wird alle Eingaben zu tätigen.
     *
     * @param list, Arraylist in der die Components die keine Eingaben erhalten
     * haben, gespeichert sind.
     * @param fehlermelgungtitel, Srting der den Titel der Fehlmeldung enthält.
     * @param fehlermeldung, String der die Fehlmeldung enthält.
     * @param farbe, Color in der der Hintergrund der Components markiert werden
     * soll
     */
    @Override
    public void fehlEingabenMarkierung(ArrayList<Component> list,
            String fehlermelgungtitel, String fehlermeldung, Color farbe) {
        //Meldung die darauf hinweist das nicht alle Eingaben getätigt worden sind.
        JOptionPane.showMessageDialog(null, fehlermeldung,
                fehlermelgungtitel, JOptionPane.WARNING_MESSAGE);
        if (!list.isEmpty()) {

            // Fokus gelangt in das erste leere Eingabefeld
            list.get(0).requestFocusInWindow();

        }
        //ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
        list.clear();
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
