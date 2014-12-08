/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Suche;
import Interfaces.*;
import javax.swing.JTextField;
/**
 *
 * @author Luca
 */
public class Suche extends javax.swing.JInternalFrame implements InterfaceViewsFunctionality{

    /**
     * Creates new form Suche
     */
    public Suche() {
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
        Legende_jButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setTitle("Suchen");
        setVisible(true);

        Button_jToolBar.setFloatable(false);
        Button_jToolBar.setRollover(true);
        Button_jToolBar.setEnabled(false);

        Auswaehlen_jButton.setText("Auswählen");
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

        Legende_jButton.setText("Legende");

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
                    .addComponent(Zusatzoption_jLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(Suche_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Auswahl_jLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AbgeschlossenjCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Suchfeld_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Auswahl_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Erfasst_jCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Freigegeben_jCheckBox)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Legende_jButton)))
                .addContainerGap(104, Short.MAX_VALUE))
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
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Suchfeld_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Suche_jLabel)
                            .addComponent(Legende_jButton))))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Erfasst_jCheckBox)
                    .addComponent(Freigegeben_jCheckBox)
                    .addComponent(Zusatzoption_jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AbgeschlossenjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        // TODO add your handling code here:
    }//GEN-LAST:event_Suchfeld_jTextFieldFocusLost

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
    private javax.swing.JButton Legende_jButton;
    private javax.swing.JLabel Suche_jLabel;
    private javax.swing.JTextField Suchfeld_jTextField;
    private javax.swing.JLabel Zusatzoption_jLabel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
