/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.Anschrift;
import DTO.Geschaeftspartner;
import DTO.Zahlungskondition;
import JFrames.GUIFactory;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 *
 * @author Tahir
 *
 * 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button
 */
public class ZahlungskonditionenAEndernEinstieg extends javax.swing.JInternalFrame {

    /*
     Hilfsvariablen
     */
    Component c;
    GUIFactory factory;
    ZahlungskonditionAnlegen z;
    private NumberFormat nf;

    /**
     * Creates new form Fenster
     */
    public ZahlungskonditionenAEndernEinstieg(GUIFactory factory, ZahlungskonditionAnlegen z) {
        initComponents();
        this.factory = factory;
        this.z = z;
        nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
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
        jL_Zahlungskondition_ID = new javax.swing.JLabel();
        jTF_Zahlungskondition_ID = new javax.swing.JTextField();
        jB_Enter = new javax.swing.JButton();
        jTF_Statuszeile = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Zahlungskonditionen ändern");
        setPreferredSize(new java.awt.Dimension(580, 300));
        setRequestFocusEnabled(false);
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

        jB_Abbrechen.setText("Abbrechen");
        jToolBar1.add(jB_Abbrechen);

        jB_Speichern.setText("Speichern");
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setText("Anzeige/Ändern");
        jB_Anzeigen.setActionCommand("Anzeigen/Ändern");
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setText("Löschen");
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setText("Suchen");
        jToolBar1.add(jB_Suchen);

        jL_Zahlungskondition_ID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jL_Zahlungskondition_ID.setText("Zahlungskondition-ID:");

        jB_Enter.setText("Weiter");
        jB_Enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_EnterActionPerformed(evt);
            }
        });
        jB_Enter.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jB_EnterFocusLost(evt);
            }
        });

        jTF_Statuszeile.setText("Statuszeile");
        jTF_Statuszeile.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jTF_Statuszeile)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jL_Zahlungskondition_ID)
                .addGap(18, 18, 18)
                .addComponent(jTF_Zahlungskondition_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jB_Enter)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jL_Zahlungskondition_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_Zahlungskondition_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Enter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(jTF_Statuszeile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jB_EnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_EnterActionPerformed
//        String eingabe = jTF_Zahlungskondition_ID.getText();
//        long zknr = 0;
////        try {
////            zknr = nf.parse(eingabe).longValue();
////            Zahlungskondition za = this.factory.getDAO().getPaymentConditionsById(zknr);
////            z.gibjTF_ZahlungskonditionID().setText("" + za.getZahlungskonditionID());
////            z.gibjCB_Auftragsart().setSelectedItem(za.getAuftragsart());
////            z.gibjFTF_LieferzeitSOFORT().setText("" + za.get);
//////            
////        } catch (ParseException ex) {
////            System.out.println("Fehler beim Parsen in der Klasse ArtikelAnlegen!");
////        } catch (ApplicationException ex) {
////            Logger.getLogger(ArtikelAEndernEinstieg.class.getName()).log(Level.SEVERE, null, ex);
//            jTF_Statuszeile.setText("Kein passender Geschäftspartner in Datenbank!");
////            jTF_Geschaeftspartner_ID.setText("");
//        }
//        if (jTF_Zahlungskondition_ID.getText().equals("Ändern")) {
////            Daten aus Datenbank laden
//            z.gibjCB_Auftragsart().setSelectedItem("Terminauftrag");
//            z.gibjFTF_LieferzeitSOFORT().setText("10");
//            z.gibjFTF_SperrzeitWUNSCH().setText("9");
//            z.gibjSP_Skontozeit1().setValue(2);
//            z.gibjSP_Skontozeit2().setValue(3);
//            z.gibjCB_Skonto1().setSelectedItem("1,0");
//            z.gibjCB_Skonto2().setSelectedItem("2,0");
//            z.gibjSP_Mahnzeit1().setValue(4);
//            z.gibjSP_Mahnzeit2().setValue(5);
//            z.gibjSP_Mahnzeit3().setValue(6);
//            z.setVisible(true);
//            this.setVisible(false);
//            jTF_Zahlungskondition_ID.setText("");
//        } else {
//            jTF_Statuszeile.setText("Kein passender Artikel in Datenbank!");
//        }
    }//GEN-LAST:event_jB_EnterActionPerformed

    private void jB_EnterFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jB_EnterFocusLost
        
        jTF_Statuszeile.setText("");
    }//GEN-LAST:event_jB_EnterFocusLost

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_Abbrechen;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Enter;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JLabel jL_Zahlungskondition_ID;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTF_Statuszeile;
    private javax.swing.JTextField jTF_Zahlungskondition_ID;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
