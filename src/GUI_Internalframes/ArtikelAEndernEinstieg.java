/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.Artikel;
import Documents.UniversalDocument;
import Interfaces.InterfaceMainView;
import Interfaces.InterfaceViewsFunctionality;
import JFrames.*;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author Tahir * Klassenhistorie: 30.11.2014 Sen, angelegt 01.12.2014 Sen,
 * textfeld,Button angelegt 07.12.2014 Sen, Componenten mit leben befuellt
 * 10.12.2014 Sen, grundlegenden Ueberarbeitung der Maske, Fehler korigiert
 * 15.12.2014 Sen, taskleiste implementiert und funktionen erweitert 19.12.2014
 * Terrasi, Funktionsimplementierung im "Zurück"-Button der Schnittstelle für
 * InternalFrames 25.12.2014 Sen, UniversalDocument() implementiert, denn es
 * sollen nur Zahlen eingegeben werden können 26.12.2014 Sen, ArtikelAnlegen in
 * AritkelAnzeigen Funktion angefangen 01.01.2015 Sen, Methode zum Ändern von
 * ArtikelAnlegen in ArtikelAnzeigen implementiert 02.01.2015 Sen, Löschen von
 * Artikel Funktion implementiert 07.01.2015 Sen, Löschen von Artikel Funktion
 * Fehler korriegiert 08.01.2015 Terrasi, Implementierung der Anzeigen/Ändern
 * Funktion, hinzufügen 12.01.2015 Sen, Artikel aus Datenbank laden und diese
 * anzegien lassen angelegt, bzw Felder mit den Daten der Artikel befuellt
 * 14.01.2015 Sen, ArtikelAendern speichern Funktion angefangen 15.01.2015 Sen,
 * ArtikelAendern speichern Funktion anbgeschlossen 16.12.2014 Terrasi,
 * Funktionsimplementierung im "Zurück"-Button
 */
public class ArtikelAEndernEinstieg extends javax.swing.JInternalFrame {

    private Component c;
    private GUIFactory factory;

    private InterfaceMainView hauptFenster;

    private ArtikelAnlegen a;
    private NumberFormat nf;

    /**
     * Creates new form Fenster
     */
    public ArtikelAEndernEinstieg(GUIFactory factory, ArtikelAnlegen a, InterfaceMainView mainView) {
        initComponents();
        this.factory = factory;
        this.a = a;
        this.hauptFenster = mainView;

        nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        jTF_Artikel_ID.setDocument(new UniversalDocument("0123456789", false));
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
        jL_Artikel_ID = new javax.swing.JLabel();
        jTF_Artikel_ID = new javax.swing.JTextField();
        jB_Enter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setTitle("Artikel ändern Einstieg");
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

        jB_Zurueck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/home.PNG"))); // NOI18N
        jB_Zurueck.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jB_Zurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ZurueckActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Zurueck);

        jB_Speichern.setText("Speichern");
        jToolBar1.add(jB_Speichern);

        jB_Anzeigen.setText("Anzeige/Ändern");
        jB_Anzeigen.setActionCommand("Anzeigen/Ändern");
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setText("Löschen");
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setText("Suchen");
        jB_Suchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuchenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Suchen);

        jL_Artikel_ID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jL_Artikel_ID.setText("Artikelnummer:");

        jTF_Artikel_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTF_Artikel_IDKeyPressed(evt);
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
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jL_Artikel_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTF_Artikel_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jB_Enter)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jL_Artikel_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_Artikel_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Enter))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jB_EnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_EnterActionPerformed
        String artikelnummer = jTF_Artikel_ID.getText();
        long artikelnr = 0;
        try {
            artikelnr = nf.parse(artikelnummer).longValue();
            Artikel artikel = GUIFactory.getDAO().getItem(artikelnr);
            a.gibjTF_Artikelnummer().setText("" + artikel.getArtikelID());
            a.gibjTF_Artikelname().setText(artikel.getArtikeltext());
            a.gibjTA_Artikelbeschreibung().setText(artikel.getBestelltext());
            a.gibjCB_Artikelkategorie().setSelectedItem(artikel.getKategorie().getKategoriename());
            a.gibjTF_Einzelwert().setText("" + nf.format(artikel.getVerkaufswert()));
            a.gibjTF_Bestellwert().setText("" + nf.format(artikel.getEinkaufswert()));
            a.gibjCB_MwST().setSelectedItem("" + artikel.getMwST());
            a.gibjTF_BestandsmengeFREI().setText("" + artikel.getFrei());
            a.gibjTF_BestandsmengeRESERVIERT().setText("" + artikel.getReserviert());
            a.gibjTF_BestandsmengeZULAUF().setText("" + artikel.getZulauf());
            a.gibjTF_BestandsmengeVERKAUFT().setText("" + artikel.getVerkauft());
            a.setVisible(true);
            this.setVisible(false);
//            jTF_Artikel_ID.setText("");
            zuruecksetzen();

        } catch (ParseException ex) {
            System.out.println("Fehler beim Parsen in der Klasse ArtikelAEndernEinstieg!");
            this.hauptFenster.setStatusMeldung("Bitte geben Sie eine Artikelnummer ein!");
        } catch (ApplicationException ex) {
            this.hauptFenster.setStatusMeldung("Kein passender Artikel in Datenbank!");
//            jTF_Artikel_ID.setText("");
            zuruecksetzen();
        }
    }//GEN-LAST:event_jB_EnterActionPerformed

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

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        jB_ZurueckActionPerformed(null);
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTF_Artikel_IDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTF_Artikel_IDKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            jB_EnterActionPerformed(null);
        }
    }//GEN-LAST:event_jTF_Artikel_IDKeyPressed

    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        this.hauptFenster.rufeSuche(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed
    /**
     * setterMethode für Simon
     *
     * @param artikel Datum Name Was 18.01.2015 SEN angelegt
     */
    public void setzeGP_IDAusSuche(Artikel artikel) {
        jTF_Artikel_ID.setText("" + artikel.getArtikelID());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Enter;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JLabel jL_Artikel_ID;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTF_Artikel_ID;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    public void zuruecksetzen() {
        jTF_Artikel_ID.setText("");
    }
}
