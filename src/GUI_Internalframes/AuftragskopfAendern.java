package GUI_Internalframes;

import DAO.ApplicationException;
import JFrames.GUIFactory;
import Documents.*;
import Interfaces.*;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import DTO.*;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Luca Terrasi
 */
/* 10.12.2014 Dokumentation und Logik */
/* 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button */
/* 08.01.2015 Terrasi, Überarbeitung der Anwendungslogik und das hinzufügen von
 weiteren Funktion. */
/* 14.01.2015 Terrasi, Implementierung der DAO-Methode für das finden 
 eines Auftrags. */
/* 18.02.2015 TER, getestet und freigegeben */
public class AuftragskopfAendern extends javax.swing.JInternalFrame
        implements SchnittstelleFensterFunktionen {

    // Speichervariablen
    Component letzteComponent;
    GUIFactory factory;
    SchnittstelleHauptfenster hauptFenster;
    AuftragskopfAnlegen auftragskopfAnlegen;

    ArrayList<Component> fehleingabefelder;

    // Augabetexte für Meldungen
    private final String FEHLERMELDUNG_STATUS_TITEL = "Abgeschlossener Auftrag";
    private final String FEHLERMMELDUNG_STATUSABGESCHLOSSEN_TEXT = "Der Auftrag ist "
            + "bereits abgeschlossen und kann nicht"
            + "\n bearbeitet werden. ";

    final String FEHLERMELDUNG_AUFTRAGSKOPFID_TEXT = "\"Die eingegebene "
            + "Auftragskopf-ID ist nicht gültig! "
            + "\\n Bitte geben Sie eine gültige Auftragskopf-ID ein. "
            + "(z.B. 1 oder 999999999)\"";
    final String FEHLERMELDUNG_UNVOLLSTAENDIG_TEXT = "Es wurden nicht alle"
            + " Eingaben getätigt.";
    private final String AUFTRAG_NICHT_GEFUNDEN = "Keine passender Auftrag in der "
            + "Datenbank.";
    private final String KEINE_EINGABE = "Bitte geben Sie eine Auftragskopf-ID ein.";

    // Konstanten für Farben
    Color warningfarbe = Color.YELLOW;
    private Color hintergrundfarbe = Color.WHITE;

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Kostruktor, Erzeugung eines AuftragskopfAendernobjektes.
     *
     * @param factory, Übergabe eines GUIFactoryobjektes.
     * @param auftragsKopf, Übergabe eines AUftragskopfAnlegenobjektes.
     * @param mainView, Übergabe eines InterfaceMainViewobjektes.
     */
    public AuftragskopfAendern(GUIFactory factory,
            AuftragskopfAnlegen auftragsKopf, SchnittstelleHauptfenster mainView) {
        initComponents();
        // Übergabe der Parameter.
        this.factory = factory;
        this.auftragskopfAnlegen = auftragsKopf;
        this.hauptFenster = mainView;

        // Initialisierung der Speichervariblen
        fehleingabefelder = new ArrayList<>();

        // Zuweisung der Documents an die Eingabefelder
        auftragskopfID_jTextField.setDocument(
                new UniversalDocument("0123456789", false, 10));
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
        auftragskopfID_jLabel = new javax.swing.JLabel();
        auftragskopfID_jTextField = new javax.swing.JTextField();
        weiter_jButton = new javax.swing.JButton();

        setTitle("Auftragskopf ändern Einstieg");
        setMinimumSize(new java.awt.Dimension(63, 33));
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
        jB_Suchen.setToolTipText("Allgemeine Suche");
        jB_Suchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuchenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Suchen);

        auftragskopfID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        auftragskopfID_jLabel.setLabelFor(auftragskopfID_jTextField);
        auftragskopfID_jLabel.setText("Auftragskopf-ID :");
        auftragskopfID_jLabel.setToolTipText("");

        auftragskopfID_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                auftragskopfID_jTextFieldFocusGained(evt);
            }
        });
        auftragskopfID_jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                auftragskopfID_jTextFieldKeyPressed(evt);
            }
        });

        weiter_jButton.setText("Weiter");
        weiter_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weiter_jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(auftragskopfID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(weiter_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(162, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(auftragskopfID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weiter_jButton)
                    .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(243, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Beim wählen des Eingabefeldes, wird alles selektiert.
     *
     * @param evt
     */
    private void auftragskopfID_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragskopfID_jTextFieldFocusGained
        //Setzen der Hintergrundsfarbe des Eingabefeldes
        auftragskopfID_jTextField.setBackground(hintergrundfarbe);
        //Übergabe eines leeren Strings an das Eingabefeld
        auftragskopfID_jTextField.setText("");
    }//GEN-LAST:event_auftragskopfID_jTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und dokumentiert*/
    /* 14.01.2015 Terrasi, implementierung der DAOMethode "gibAuftragskopf"
     und Logiküberarbeitung.*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Auszuführende Aktion beim betätigen des "Weiter"-Buttons. Es wird geprüft
     * ob das eingabefeld leer ist und wenn ja wird eine Meldung ausgegeben in
     * der der Benutzer darauf hingewiesen wird, dass eine Eingabe zur weiteren
     * Durchführung fehlt. Das Eingabefeld mit der fehlenden Eingabe wird
     * farblich markiert.
     *
     * @param evt
     */
    private void weiter_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weiter_jButtonActionPerformed

        // Klassenvariable
        final String auftragskopfAendern = "Auftragskopf ändern Einstieg";

        Auftragskopf aKopf;// Variable für einen Auftragskopf.

        try {// Try-Block
            // Wird geprüft ob eine Eingabe getätigt worden ist.
            if (!(auftragskopfID_jTextField.getText().equals(""))) {

                // Initialisieirung eines Auftragskopfes mit der DAO-Methode
                // "gibAuftragskopf".
                aKopf = GUIFactory.getDAO().
                        gibAuftragskopf(
                                Long.parseLong(
                                        auftragskopfID_jTextField.getText()));

                // Überprüft anhand des Framestitels, ob es das nächste Fenster
                // im Anzeigen-/ oder im Ändernmodus anzeigen soll.
                if (this.getTitle().equals(auftragskopfAendern)) {
                    if (aKopf != null) {// Falls Auftragskopf vorhanden ist.
                        if (!(aKopf.getStatus().getStatus().
                                equals("abgeschlossen"))) {//Status des Auftrags 
                            // ist nicht "Abgeschlossen".
                            // Maske AuftragsKopfAnlegen zurücksetzen
                            this.auftragskopfAnlegen.zuruecksetzen();
                            // Setzt das Internalframe in den Ändernmodus.
                            this.auftragskopfAnlegen.setzeStatusAender();
                            // Übergabe des Auftrags an die AuftragskopfAnlege 
                            // Maske.
                            this.auftragskopfAnlegen.
                                    setzeEingabe(aKopf);
                            // Methode die bestimmte Eingabefelder leert
                            zuruecksetzen();
                            // Fenster wird nicht mehr sichtbar gemacht.
                            this.setVisible(false);
                            // Hauptfenster macht übergebene Maske sichtbar.
                            this.hauptFenster.setzeFrame(this.auftragskopfAnlegen);
                        } else {
                            // Fehlermeldung als PopUp
                            JOptionPane.showMessageDialog(null,
                                    FEHLERMMELDUNG_STATUSABGESCHLOSSEN_TEXT,
                                    FEHLERMELDUNG_STATUS_TITEL,
                                    JOptionPane.WARNING_MESSAGE);
                        }

                    }
                } else {// Falls Maske "Auftragskopf anzeigen Einstieg" ist.
                    // Maske AuftragsKopfAnlegen zurcksetzen
                    this.auftragskopfAnlegen.zuruecksetzen();
                    //Übergabe des Auftrags an die AuftragskopfAnlege Maske.
                    this.auftragskopfAnlegen.
                            setzeEingabe(aKopf);
                    // Setzt das Internalframe in den Anzeigenmodus.
                    this.auftragskopfAnlegen.setzeStatusAnzeigen();
                    //Methode die bestimmte Eingabefelder leert
                    zuruecksetzen();
                    // Fenster wird nicht mehr sichtbar gemacht.
                    this.setVisible(false);
                    // Hauptfenster macht übergebene Maske sichtbar.
                    this.hauptFenster.setzeFrame(this.auftragskopfAnlegen);
                }
            } else {

                this.hauptFenster.setzeStatusMeldung(KEINE_EINGABE);
            }
        } catch (ApplicationException |
                NullPointerException e) {// Fehlerbehandlung.
            // Fehlermeldung als PopUp
            this.hauptFenster.setzeStatusMeldung(AUFTRAG_NICHT_GEFUNDEN);
            // Eingabefeld erhält leeren String
            auftragskopfID_jTextField.setText("");
        }
    }//GEN-LAST:event_weiter_jButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 16.12.2014 Terrasi angelegt und dokumentiert*/
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
        //Initialisierung der Componentspeichervariable
        letzteComponent = null;
        //Erhalten über GUIFactorymethode die letzte aufgerufene View und 
        // speichern diese in Variable
        letzteComponent = this.factory.zurueckButton();
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        // Übergebene Component wird sichtbar gemacht
        letzteComponent.setVisible(true);
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, angelegt, Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    private void auftragskopfID_jTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_auftragskopfID_jTextFieldKeyPressed
        // Wird geprft ob die Entertste betätigt worden ist.
        if (evt.getKeyCode() == evt.VK_ENTER) {
            // Weiter_ActionPerformed wird ausgeführt.
            weiter_jButtonActionPerformed(null);
        }
    }//GEN-LAST:event_auftragskopfID_jTextFieldKeyPressed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, angelegt, Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed für den Aufruf der Suche.
     *
     * @param evt, Event
     */
    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        // Übergabe des Fenster an die "rufeSuche"-Methode.
        this.hauptFenster.rufeSuche(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed

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
        auftragskopfID_jTextField.setText("");
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
        //IF-Anweisungen mit denen geprüft wird welche eingabefelder keine 
        // Eingabe erhalten haben. Diese Eingabefelder werden in passende 
        // Speichervariablen festgehalten.

        // Eingabefeld für Auftragskopf wird in Variable "fehleingabefelder" 
        // festgehalten.
        if (auftragskopfID_jTextField.getText().equals("")) {
            fehleingabefelder.add(auftragskopfID_jTextField);
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
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax,
            String fehlermelgungtitel, String fehlermeldung) {
        //Falls Eingabe nicht mit der Syntax übereinstimmt.
        if (!textfield.getText().matches(syntax)) {
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
            //Mit dem Focus in das übergebene Eingabefeld springen
            textfield.requestFocusInWindow();
            textfield.selectAll();// Eingabefeld komplett auswählen.
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
    public void fehlEingabenMarkierung(ArrayList<Component> list,
            String fehlermelgungtitel, String fehlermeldung, Color farbe) {
        // Meldung die darauf hinweist das nicht alle Eingaben getätigt 
        // worden sind.
        JOptionPane.showMessageDialog(null, fehlermeldung,
                fehlermelgungtitel, JOptionPane.WARNING_MESSAGE);
        if (!list.isEmpty()) {
            // Fokus gelangt in das erste leere Eingabefeld.
            list.get(0).requestFocusInWindow();
        }
        //ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
        list.clear();
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode die ein Dateobjekt in ein passenden String umwandelt.
     *
     * @param date, Datumsobjekt das als String umgewandelt wird.
     * @return String Objekt, Datum als String
     */
    public String gibDatumAlsString(Date date) {
        // Speichervariablen für den Tag und den Monat des Datums
        String t;
        String m;
        // Erzeugung eines Calendarobjektes
        Calendar cal = Calendar.getInstance();
        String datum = "";
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
            t = "0" + tag;
        } else {
            t = "" + tag;
        }

        // Falls Monat einstellig ist, wird es mit einer "0" ergänzt und 
        // zweistellig gespeichert.
        if (mon < 10) {
            m = "0" + mon;
        } else {
            m = "" + mon;
        }
        //Zusammenfhrung der einzelnen Speichervariablen.
        datum = t + "." + m + "." + jahr;
        return datum;
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der die Auftragskopf-ID des Auftragskopf gesetzt wird.
     *
     * @param auftragskopf, Auftragskopf
     */
    public void setzeAuftragskopfID_jTextField(Auftragskopf auftragskopf) {
        //  Auftragskopf-ID des Auftragskopf wird in das Eingabefeld gesetzt.
        this.auftragskopfID_jTextField.setText(
                String.valueOf(auftragskopf.getAuftragskopfID()));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel auftragskopfID_jLabel;
    private javax.swing.JTextField auftragskopfID_jTextField;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton weiter_jButton;
    // End of variables declaration//GEN-END:variables
}
