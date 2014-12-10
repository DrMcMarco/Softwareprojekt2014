/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Internalframes;

import Documents.*;
import Objects.*;
import Interfaces.*;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Luca
 */
public class AuftragspositionAnzeigen extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality {

    /*
     Syntax
     */
    private static final String auftragspositionsID_syntax = "\\d{1,9}?";
    private static final String positionsnummer_syntax = "\\d{1,9}?";
    private static final String material_syntax = "\\d{1,9}?";
    private static final String menge_syntax = "\\d{1,9}?";
    private static final String einzelwert_syntax = "|(\\d*,?\\d{1,2})|(\\d{0,3}(\\.\\d{3})*,?\\d{1,2})";

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

    /**
     * Creates new form AuftragspositionAnlegen
     */
    public AuftragspositionAnzeigen() {
        initComponents();
        erfassungsdatum_jTextField.setText(DateObject.simpleDateFormat());
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
        jB_Abbrechen = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_Anzeigen = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        Statuszeile_jTextField = new javax.swing.JTextField();
        auftragspositionsID_jLabel = new javax.swing.JLabel();
        positionsnummer_jLabel = new javax.swing.JLabel();
        materialnummer_jLabel = new javax.swing.JLabel();
        menge_jLabel = new javax.swing.JLabel();
        einzelwert_jLabel = new javax.swing.JLabel();
        erfassungsdatum_jLabel = new javax.swing.JLabel();
        auftragspositionsID_jTextField = new javax.swing.JTextField();
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

        jB_Zurueck.setText("Zurück");
        jToolBar1.add(jB_Zurueck);

        jB_Abbrechen.setText("Abbrechen");
        jToolBar1.add(jB_Abbrechen);

        jB_Speichern.setText("Speichern");
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setText("Anzeige/Ändern");
        jB_Anzeigen.setEnabled(false);
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setText("Löschen");
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setText("Suchen");
        jToolBar1.add(jB_Suchen);

        Statuszeile_jTextField.setText("Statuszeile");
        Statuszeile_jTextField.setEnabled(false);

        auftragspositionsID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        auftragspositionsID_jLabel.setLabelFor(auftragspositionsID_jTextField);
        auftragspositionsID_jLabel.setText("Auftragspositions-ID :");

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

        auftragspositionsID_jTextField.setText("1");
        auftragspositionsID_jTextField.setEnabled(false);
        auftragspositionsID_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                auftragspositionsID_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                auftragspositionsID_jTextFieldFocusLost(evt);
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

        erfassungsdatum_jTextField.setText("jTextField6");
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
            .addComponent(Statuszeile_jTextField)
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
                            .addComponent(auftragspositionsID_jTextField)
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
                    .addComponent(auftragspositionsID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(Statuszeile_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(402, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     */
    private void auftragspositionsID_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragspositionsID_jTextFieldFocusGained
        auftragspositionsID_jTextField.setText("");
        auftragspositionsID_jTextField.selectAll();
    }//GEN-LAST:event_auftragspositionsID_jTextFieldFocusGained

    /*
     Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     */
    private void positionsnummer_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_positionsnummer_jTextFieldFocusGained
        positionsnummer_jTextField.setText("");
        positionsnummer_jTextField.selectAll();
    }//GEN-LAST:event_positionsnummer_jTextFieldFocusGained

    /*
     Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     */
    private void materialnummer_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_materialnummer_jTextFieldFocusGained
        materialnummer_jTextField.setText("");
        materialnummer_jTextField.selectAll();
    }//GEN-LAST:event_materialnummer_jTextFieldFocusGained

    /*
     Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     */
    private void menge_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_menge_jTextFieldFocusGained
        menge_jTextField.setText("");
        menge_jTextField.selectAll();
    }//GEN-LAST:event_menge_jTextFieldFocusGained

    /*
     Beim wählen des Eingabefeldes, wird alles leer gesetzt und selektiert.
     */
    private void einzelwert_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_einzelwert_jTextFieldFocusGained
        einzelwert_jTextField.setText("");
        einzelwert_jTextField.selectAll();
    }//GEN-LAST:event_einzelwert_jTextFieldFocusGained

    /*
     Beim wählen des Eingabefeldes, wird alles selektiert.
     */
    private void erfassungsdatum_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_erfassungsdatum_jTextFieldFocusGained
        erfassungsdatum_jTextField.selectAll();
    }//GEN-LAST:event_erfassungsdatum_jTextFieldFocusGained

    /**
     * Beim Focuslost des Eingabefeldes für die Auftragspostions-ID, wird auf die
     * Richtigkeit der Eingabe geprüft und gibt gegebenen falls eine
     * Fehlermeldung aus. Dabei springt man zurück in das Eingabefeld.
     *
     * @param evt
     */
    private void auftragspositionsID_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragspositionsID_jTextFieldFocusLost
        if (evt.isTemporary()) {
            return;
        }
        ueberpruefungVonFocusLost(auftragspositionsID_jTextField, auftragspositionsID_syntax,
                fehlermeldung_titel, fehlermeldunAuftragspositionsIDtext);
    }//GEN-LAST:event_auftragspositionsID_jTextFieldFocusLost

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
        if (evt.isTemporary()) {
            return;
        }
        ueberpruefungVonFocusLost(menge_jTextField, menge_syntax,
                fehlermeldung_titel, fehlermeldungMengetext);
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
        ueberpruefungVonFocusLost(einzelwert_jTextField, einzelwert_syntax,
                fehlermeldung_titel, fehlermeldungEinzelwerttext);
    }//GEN-LAST:event_einzelwert_jTextFieldFocusLost

    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden
     */
    @Override
    public void zuruecksetzen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
     Schnittstellenmethode mit der geprüft wird ob alle Eingaben getätigt worden sind.
     */
    @Override
    public void ueberpruefen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
     Schnittstellenmethode mit der die Eingaben beim FocusLost auf Richtigkeit 
     geprüft werden.
     */
    @Override
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax, String fehlermelgungtitel, String fehlermeldung) {
        if(textfield.getText().equals("")){
            
        }else if (!textfield.getText().matches(syntax)) {
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
            textfield.requestFocusInWindow();
            textfield.selectAll();
        }
    }

    @Override
    public void fehlEingabenMarkierung(ArrayList<Component> list, String fehlermelgungtitel, String fehlermeldung, Color farbe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Statuszeile_jTextField;
    private javax.swing.JLabel auftragspositionsID_jLabel;
    private javax.swing.JTextField auftragspositionsID_jTextField;
    private javax.swing.JLabel einzelwert_jLabel;
    private javax.swing.JTextField einzelwert_jTextField;
    private javax.swing.JLabel erfassungsdatum_jLabel;
    private javax.swing.JTextField erfassungsdatum_jTextField;
    private javax.swing.JButton jB_Abbrechen;
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
