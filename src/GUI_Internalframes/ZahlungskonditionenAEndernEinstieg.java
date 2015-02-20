package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.Zahlungskondition;
import Documents.UniversalDocument;
import JFrames.GUIFactory;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import Interfaces.*;
import javax.swing.JTextField;

/**
 * Klasse fuer die Maske Zahlingskondition (ZK) aendern/anzeigen Einstieg. Je
 * nachdem von welchem Button diese Klasse aufgerufen wird aendert sie sich in
 * den Zustand ZK aendern Einstieg oder ZK anzeigen Einstieg
 *
 * @author Tahir
 */
public class ZahlungskonditionenAEndernEinstieg extends javax.swing.JInternalFrame {

    /**
     * Variable, die für die Navigation benoetigt wird, in ihr wird gespeichert,
     * welche View zuletzt geöffnet war.
     */
    private Component c;
    /**
     * Varibale für die GUI Factory
     */
    private GUIFactory factory;
    /**
     * Referenzvaribale der Sicht ZK anlegen
     */
    private ZahlungskonditionAnlegen zk;
    /**
     * Variable für das Start Fenster. Es kann sein, dass sich ein Admin
     * anmeldet, dann waere unser StartFenster die StartAdmin. Falls sich ein
     * User anmeldet, ist unser StartFenster Start.
     */
    private InterfaceMainView hauptFenster;
    /**
     * Number Formatter wird benoetigt fuer das Parsen der Eingaben, sowie das
     * Anzeigen von Preisen
     */
    private NumberFormat nf;
    /**
     * Variablen fuer die Fehlermeldungen
     */
    private final String KEINE_ZKNR_EINGEGEBEN = "Bitte geben Sie eine Zahlungskondition-ID ein!";
    private final String KEINE_ZK_IN_DATENBANK = "Keine passender Zahlungskondition in der Datenbank!";

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     *
     * @param factory beinhaltet das factory Obejekt
     * @param zk Referenzvariable der ZK anlegen Klasse
     * @param mainViev beinhaltet das Objekt des StartFenster
     */
    public ZahlungskonditionenAEndernEinstieg(GUIFactory factory, ZahlungskonditionAnlegen zk, InterfaceMainView mainViev) {
        initComponents();
        this.factory = factory;
        this.zk = zk;
        this.hauptFenster = mainViev;
        nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        jTF_Zahlungskondition_ID.setDocument(new UniversalDocument("0123456789", false));
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
        jL_Zahlungskondition_ID = new javax.swing.JLabel();
        jTF_Zahlungskondition_ID = new javax.swing.JTextField();
        jB_Enter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Zahlungskonditionen ändern Einstieg");
        setPreferredSize(new java.awt.Dimension(580, 300));
        setRequestFocusEnabled(false);
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        jB_Zurueck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Home2.PNG"))); // NOI18N
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
        jB_Anzeigen.setActionCommand("Anzeigen/Ändern");
        jB_Anzeigen.setEnabled(false);
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Löschen.PNG"))); // NOI18N
        jB_Loeschen.setEnabled(false);
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Suche.PNG"))); // NOI18N
        jB_Suchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuchenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Suchen);

        jL_Zahlungskondition_ID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jL_Zahlungskondition_ID.setText("Zahlungskondition-ID:");

        jTF_Zahlungskondition_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTF_Zahlungskondition_IDKeyPressed(evt);
            }
        });

        jB_Enter.setText("Weiter");
        jB_Enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_EnterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
            .addComponent(jSeparator1)
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
                .addContainerGap(135, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Methode, die aufgerufen wird, wenn man auf weiter klickt.
     *
     * @param evt automatisch generiert
     */
    /*
     * Historie:
     * 11.12.2014   Sen     angelegt
     * 14.12.2014   Sen     ueberarbeitet
     */
    private void jB_EnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_EnterActionPerformed
        String eingabe = jTF_Zahlungskondition_ID.getText();
        long zknr;
        try {
//             ZK mit der eingegebenen ZK nummer wird versucht aus der Datenbank zu laden
//              Alle Felder der Sicht ZK anlegen werden befuellt
            zknr = nf.parse(eingabe).longValue();
            Zahlungskondition za = GUIFactory.getDAO().gibZahlungskonditionNachId(zknr);
            zk.gibjTF_ZahlungskonditionID().setText("" + za.getZahlungskonditionID());
            zk.gibjCB_Auftragsart().setSelectedItem(za.getAuftragsart());
            if (za.getAuftragsart().equals("Bestellauftrag")) {
                zk.gibjSP_LieferzeitSOFORT().setEnabled(false);
                zk.gibjSP_SperrzeitWUNSCH().setEnabled(false);
            } else if (za.getAuftragsart().equals("Terminauftrag")) {
                zk.gibjSP_LieferzeitSOFORT().setEnabled(false);
            } else if (za.getAuftragsart().equals("Sofortauftrag")) {
                zk.gibjSP_SperrzeitWUNSCH().setEnabled(false);
            }
            zk.gibjSP_LieferzeitSOFORT().setValue(za.getLieferzeitSofort());
            zk.gibjSP_SperrzeitWUNSCH().setValue(za.getSperrzeitWunsch());
            zk.gibjSP_Skontozeit1().setValue(za.getSkontozeit1());
            zk.gibjSP_Skontozeit2().setValue(za.getSkontozeit2());
            zk.gibjCB_Skonto1().setSelectedItem(("" + za.getSkonto1()).replace('.', ','));
            zk.gibjCB_Skonto2().setSelectedItem(("" + za.getSkonto2()).replace('.', ','));
            zk.gibjSP_Mahnzeit1().setValue(za.getMahnzeit1());
            zk.gibjSP_Mahnzeit2().setValue(za.getMahnzeit2());
            zk.gibjSP_Mahnzeit3().setValue(za.getMahnzeit3());
            zk.setVisible(true);
            this.setVisible(false);
            zuruecksetzen();
//            entsprechende Fehlermeldungen werden in der Statuszeile angezeigt
        } catch (ParseException ex) {
            System.out.println("Fehler beim Parsen in der Klasse ArtikelAnlegen! " + ex.getMessage());
            this.hauptFenster.setStatusMeldung(KEINE_ZKNR_EINGEGEBEN);
        } catch (ApplicationException ex) {
            this.hauptFenster.setStatusMeldung(KEINE_ZK_IN_DATENBANK);
            jTF_Zahlungskondition_ID.setText("");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jB_EnterActionPerformed

    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt automatisch generiert
     */
    /*
     * Historie:
     * 01.12.2014   Sen     angelegt
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        c = null;   //Initialisierung der Componentspeichervariable
        //Erhalten über GUIFactorymethode die letzte aufgerufene View und speichern diese in Variable
        c = this.factory.zurueckButton();
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        c.setVisible(true);// Übergebene Component wird sichtbar gemacht
    }//GEN-LAST:event_jB_ZurueckActionPerformed
    /**
     * Aktion die beim betätigen schliesen des Fenster ausgefuert wird
     *
     * @param evt automatisch generiert
     */
    /*
     * Historie:
     * 01.12.2014   Sen     angelegt
     */
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        jB_ZurueckActionPerformed(null);
    }//GEN-LAST:event_formInternalFrameClosing
    /**
     * Aktion die beim klicken auf Enter durchgefuert wird
     *
     * @param evt automatisch generiert
     */
    /*
     * Historie:
     * 02.01.2015   Sen     angelegt
     */
    private void jTF_Zahlungskondition_IDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTF_Zahlungskondition_IDKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            jB_EnterActionPerformed(null);
        }
    }//GEN-LAST:event_jTF_Zahlungskondition_IDKeyPressed
    /**
     * Aktion die beim klicken auf Button Suche durchgefuert wird
     *
     * @param evt automatisch generiert
     */
    /*
     * Historie:
     * 16.01.2015   Sen     angelegt
     */
    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        this.hauptFenster.rufeSuche(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed

    /**
     * setterMethode für die Suche
     *
     * @param zk ZK Obkjekt, aus dem die ZK nummer gelesen wird
     */
    /*
     * Historie:
     * 18.01.2015   Sen     angelegt
     */
    public void setzeGP_IDAusSuche(Zahlungskondition zk) {
        jTF_Zahlungskondition_ID.setText("" + zk.getZahlungskonditionID());
    }

    /**
     * methode, die die Eingaben zuruecksetzt
     */
    /*
     * Historie:
     * 18.01.2015   Sen     angelegt
     */
    public void zuruecksetzen() {
        jTF_Zahlungskondition_ID.setText("");
    }

    public JTextField gibjTF_Zahlungskondition_ID() {
        return jTF_Zahlungskondition_ID;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Enter;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JLabel jL_Zahlungskondition_ID;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTF_Zahlungskondition_ID;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
