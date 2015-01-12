/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Suche;
import Interfaces.*;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTextField;
/**
 *
 * @author Luca
 */
public class AllgemeineSuche extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality{

    /**
     * Creates new form Suche
     */
    public AllgemeineSuche() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Button_jToolBar = new javax.swing.JToolBar();
        Auswaehlen_jButton = new javax.swing.JButton();
        Anzeige_jButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        Auswahl_jLabel = new javax.swing.JLabel();
        Auswahl_jComboBox = new javax.swing.JComboBox();
        Suche_jLabel = new javax.swing.JLabel();
        Suchfeld_jTextField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        Zusatzoption_jLabel = new javax.swing.JLabel();
        Erfasst_jCheckBox = new javax.swing.JCheckBox();
        Freigegeben_jCheckBox = new javax.swing.JCheckBox();
        AbgeschlossenjCheckBox = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        aufsteigend_jRadioButton = new javax.swing.JRadioButton();
        absteigend_jRadioButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setTitle("Suchen");
        setEnabled(false);
        setVisible(true);

        Button_jToolBar.setFloatable(false);
        Button_jToolBar.setRollover(true);
        Button_jToolBar.setEnabled(false);

        Auswaehlen_jButton.setText("Auswählen");
        Auswaehlen_jButton.setEnabled(false);
        Auswaehlen_jButton.setFocusable(false);
        Auswaehlen_jButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Auswaehlen_jButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        Auswaehlen_jButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_jToolBar.add(Auswaehlen_jButton);

        Anzeige_jButton.setText("Anzeigen");
        Anzeige_jButton.setFocusable(false);
        Anzeige_jButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Anzeige_jButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_jToolBar.add(Anzeige_jButton);

        jTextField1.setText("Statuszeile");
        jTextField1.setEnabled(false);

        Auswahl_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Auswahl_jLabel.setLabelFor(Auswahl_jComboBox);
        Auswahl_jLabel.setText("Auswahl der Suche :");

        Auswahl_jComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Auftragskopf-ID", "Auftragspositions-ID", "Artikel-ID", "Geschäftspositions-ID", "Zhalungskonditions-ID", "Artikel-ID" }));
        Auswahl_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Auswahl_jComboBoxActionPerformed(evt);
            }
        });

        Suche_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Suche_jLabel.setLabelFor(Suchfeld_jTextField);
        Suche_jLabel.setText("Suche :");

        Suchfeld_jTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        Suchfeld_jTextField.setText("Suchfeld");
        Suchfeld_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Suchfeld_jTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Suchfeld_jTextFieldFocusLost(evt);
            }
        });

        jSeparator1.setEnabled(false);

        Zusatzoption_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Zusatzoption_jLabel.setLabelFor(Erfasst_jCheckBox);
        Zusatzoption_jLabel.setText("Zusatzoption :");

        Erfasst_jCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Erfasst_jCheckBox.setText("Erfasst");

        Freigegeben_jCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Freigegeben_jCheckBox.setText("Freigegeben");

        AbgeschlossenjCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AbgeschlossenjCheckBox.setText("Abgeschlossen");

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        aufsteigend_jRadioButton.setText("Auftsteigend");

        absteigend_jRadioButton.setText("Absteigend");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Sortierung :");

        jScrollPane1.setEnabled(false);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Legende :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1)
            .addComponent(jSeparator1)
            .addComponent(Button_jToolBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Zusatzoption_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Suche_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Auswahl_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AbgeschlossenjCheckBox)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(aufsteigend_jRadioButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(absteigend_jRadioButton))
                        .addComponent(Suchfeld_jTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Auswahl_jComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(Erfasst_jCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Freigegeben_jCheckBox)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(91, 91, 91))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Button_jToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Auswahl_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Auswahl_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Suchfeld_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Suche_jLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aufsteigend_jRadioButton)
                    .addComponent(absteigend_jRadioButton)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Erfasst_jCheckBox)
                            .addComponent(Freigegeben_jCheckBox)
                            .addComponent(Zusatzoption_jLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AbgeschlossenjCheckBox)
                        .addGap(50, 50, 50)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     Beim wählen des Eingabefeldes, wird alles selektiert.
     */
    private void Suchfeld_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Suchfeld_jTextFieldFocusGained
        Suchfeld_jTextField.selectAll();
    }//GEN-LAST:event_Suchfeld_jTextFieldFocusGained

    private void Suchfeld_jTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Suchfeld_jTextFieldFocusLost
        
    }//GEN-LAST:event_Suchfeld_jTextFieldFocusLost

    private void Auswahl_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswahl_jComboBoxActionPerformed
        if(Auswahl_jComboBox.getSelectedItem().toString().equals("Auftragskopf-ID")){
        System.out.println(Auswahl_jComboBox.getSelectedItem().toString());
    }
    }//GEN-LAST:event_Auswahl_jComboBoxActionPerformed

    @Override
    public void zuruecksetzen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ueberpruefen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax, String fehlermelgungtitel, String fehlermeldung) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void fehlEingabenMarkierung(ArrayList<Component> list, String fehlermelgungtitel, String fehlermeldung, Color farbe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox AbgeschlossenjCheckBox;
    private javax.swing.JButton Anzeige_jButton;
    private javax.swing.JButton Auswaehlen_jButton;
    private javax.swing.JComboBox Auswahl_jComboBox;
    private javax.swing.JLabel Auswahl_jLabel;
    private javax.swing.JToolBar Button_jToolBar;
    private javax.swing.JCheckBox Erfasst_jCheckBox;
    private javax.swing.JCheckBox Freigegeben_jCheckBox;
    private javax.swing.JLabel Suche_jLabel;
    private javax.swing.JTextField Suchfeld_jTextField;
    private javax.swing.JLabel Zusatzoption_jLabel;
    private javax.swing.JRadioButton absteigend_jRadioButton;
    private javax.swing.JRadioButton aufsteigend_jRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
