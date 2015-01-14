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
 *
 * 10.12.2014 Dokumentation und Logik 16.12.2014 Terrasi,
 * Funktionsimplementierung im "Zurück"-Button 08.01.2015 Terrasi, Überarbeitung
 * der Anwendungslogik und das hinzufügen von weiteren Funktion.
 * 14.01.2015 Terrasi, Implementierung der DAO-Methode für das finden eines Auftrags.
 */
public class AuftragskopfAendern extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {
    /*
     Hilfsvariable
     */

    Component c;
    GUIFactory factory;
    InterfaceMainView hauptFenster;
    AuftragskopfAnlegen auftragskopfAnlegen;

    /*
     Syntax
     */
    private static final String auftragskopfID_syntax = "|\\d{1,9}?";

    /*
     Augabetexte für Meldungen
     */
    String fehlermeldung_titel = "Fehlerhafte Eingabe";
    String fehlermeldungAuftragskopfIDtext = "\"Die eingegebene Auftragskopf-ID ist nicht gültig! "
            + "\\n Bitte geben Sie eine gültige Auftragskopf-ID ein. (z.B. 1 oder 999999999)\"";
    String fehlermeldungUnvollstaendig = "Es wurden nicht alle Eingaben getätigt.\n"
            + "Bitte geben Sie die benötigte Eingabe in dem markierten Eingabefeld ein.";

    /*
     Speichervariablen
     */
    ArrayList<Component> fehleingabefelder;

    /*
     Variablen für Farben
     */
    Color warningfarbe = Color.YELLOW;
    Color hintergrundfarbe = Color.WHITE;

    /**
     * Creates new form Fenster
     */
    /**
     *
     *
     * @param factory
     * @param auftragsKopf
     * @param mainView
     */
    public AuftragskopfAendern(GUIFactory factory, AuftragskopfAnlegen auftragsKopf, InterfaceMainView mainView) {
        initComponents();
        this.factory = factory;
        this.auftragskopfAnlegen = auftragsKopf;
        this.hauptFenster = mainView;

        /*
         Initialisierung von Variablen
         */
        fehleingabefelder = new ArrayList<>();

        /*
         Zuweisung von verschiedenen Documents 
         */
        auftragskopfID_jTextField.setDocument(new UniversalDocument("0123456789", false));
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
        auftragskopfID_jLabel = new javax.swing.JLabel();
        auftragskopfID_jTextField = new javax.swing.JTextField();
        weiter_jButton = new javax.swing.JButton();

        setResizable(true);
        setTitle("Auftragskopf ändern");
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
        jB_Suchen.setEnabled(false);
        jToolBar1.add(jB_Suchen);

        jTextField1.setEditable(false);
        jTextField1.setText("Statuszeile");
        jTextField1.setEnabled(false);

        auftragskopfID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        auftragskopfID_jLabel.setLabelFor(auftragskopfID_jTextField);
        auftragskopfID_jLabel.setText("Auftragskopf-ID :");
        auftragskopfID_jLabel.setToolTipText("");

        auftragskopfID_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                auftragskopfID_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                auftragskopfID_jTextFieldFocusLost(evt);
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
                .addContainerGap()
                .addComponent(auftragskopfID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(weiter_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(174, Short.MAX_VALUE))
            .addComponent(jTextField1)
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(auftragskopfID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weiter_jButton)
                    .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
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
    private void auftragskopfID_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragskopfID_jTextFieldFocusGained
        auftragskopfID_jTextField.setBackground(hintergrundfarbe);//Setzen der Hintergrundsfarbe des Eingabefeldes
        auftragskopfID_jTextField.setText("");//Übergabe eines leeren Strings an das Eingabefeld
    }//GEN-LAST:event_auftragskopfID_jTextFieldFocusGained

    /**
     * Beim Focuslost des Eingabefeldes für die Auftragskopf-ID, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void auftragskopfID_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragskopfID_jTextFieldFocusLost
        // Aufruf der Schnittstellenmethode für die Focuslostüberprüfung
        ueberpruefungVonFocusLost(auftragskopfID_jTextField, auftragskopfID_syntax,
                fehlermeldung_titel, fehlermeldungAuftragskopfIDtext);

    }//GEN-LAST:event_auftragskopfID_jTextFieldFocusLost

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
        //Aufruf der Schnittstellenmethode um auf Vollständigkeit der Eingaben zu prüfen.
        ueberpruefen();
        if (fehleingabefelder.isEmpty()) {//Bei ausgefüllten Eingabenfeldern
            try {
                Auftragskopf aKopf = GUIFactory.getDAO().getOrderHead(Long.parseLong(auftragskopfID_jTextField.getText()));
                // Überprüft anhand des Framestitels, ob es das nächste Fenster
                // im Anzeigen-/ oder im Ändernmodus anzeigen soll.
                if (this.getTitle().equals("Auftragskopf ändern")) {

                    if (GUIFactory.getDAO().getOrderHead(Long.parseLong(auftragskopfID_jTextField.getText())) != null) {

                        this.auftragskopfAnlegen.setStatusAender();// Setzt das Internalframe in den Ändernmodus.

                        this.auftragskopfAnlegen.setzeEingabe(
                                String.valueOf(aKopf.getGeschaeftspartner().getGeschaeftspartnerID()),
                                String.valueOf(aKopf.getAuftragskopfID()),
                                String.valueOf(aKopf.getWert()),
                                aKopf.getAuftragstext(), aKopf.getClass().getName(),
                                gibDatumAlsString(aKopf.getErfassungsdatum()),
                                gibDatumAlsString(aKopf.getLieferdatum()),
                                gibDatumAlsString(aKopf.getAbschlussdatum()),
                                aKopf.getStatus().getStatus());

                        zuruecksetzen();//Methode die bestimmte Eingabefelder leert
                        this.setVisible(false);
                        this.hauptFenster.setFrame(this.auftragskopfAnlegen);// Hauptfenster macht übergebene Maske sichtbar.

                    }
                } else {
                    this.auftragskopfAnlegen.setStatusAnzeigen();// Setzt das Internalframe in den Anzeigenmodus.

                    this.auftragskopfAnlegen.setzeEingabe(
                            String.valueOf(aKopf.getGeschaeftspartner().getGeschaeftspartnerID()),
                            String.valueOf(aKopf.getAuftragskopfID()),
                            String.valueOf(aKopf.getWert()),
                            aKopf.getAuftragstext(), aKopf.getClass().getName(),
                            gibDatumAlsString(aKopf.getErfassungsdatum()),
                            gibDatumAlsString(aKopf.getLieferdatum()),
                            gibDatumAlsString(aKopf.getAbschlussdatum()),
                            aKopf.getStatus().getStatus());

                    zuruecksetzen();//Methode die bestimmte Eingabefelder leert
                    this.setVisible(false);
                    this.auftragskopfAnlegen.setStatusAnzeigen();
                    this.hauptFenster.setFrame(this.auftragskopfAnlegen);// Hauptfenster macht übergebene Maske sichtbar.
                }
            } catch (ApplicationException | NumberFormatException | NullPointerException e) {
                this.hauptFenster.setStatusMeldung(e.getMessage());
            }

        } else {//Wenn Eingaben fehlen.
            // Methodenaufruf um daraufhinzuweisen das nicht alle eingaben 
            // getätigt worden sind
            fehlEingabenMarkierung(fehleingabefelder, fehlermeldung_titel,
                    fehlermeldungUnvollstaendig, warningfarbe);//Meldung wird ausgegeben und

        }
    }//GEN-LAST:event_weiter_jButtonActionPerformed

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
     *
     */
    @Override
    public void zuruecksetzen() {
        //Eingabefelder erhalten einen leeren String
        auftragskopfID_jTextField.setText("");
    }

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
        if (auftragskopfID_jTextField.getText().equals("")) {
            fehleingabefelder.add(auftragskopfID_jTextField);
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
        if (!textfield.getText().matches(syntax)) {//Falls Eingabe nicht mit der Syntax übereinstimmt.
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

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 14.01.2015 Terrasi angelegt und dokumentiert*/
    /*----------------------------------------------------------*/
    public String gibDatumAlsString(Date date) {
        Calendar cal = Calendar.getInstance();
        String datum = "";
        cal.setTime(date);
        int tag = cal.get(Calendar.DAY_OF_MONTH);
        int mon = cal.get(Calendar.MONTH);
        mon = mon + 1;
        System.out.println(mon);
        int jahr = cal.get(Calendar.YEAR);
        String t;
        String m;
        if (tag < 10) {
            t = "0" + tag;
        } else {
            t = "" + tag;
        }

        if (mon < 10) {
            m = "0" + mon;
        } else {
            m = "" + mon;
        }

        datum = t + "." + m + "." + jahr;
        return datum;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton weiter_jButton;
    // End of variables declaration//GEN-END:variables
}
