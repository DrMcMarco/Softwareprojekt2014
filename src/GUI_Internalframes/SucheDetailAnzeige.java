/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI_Internalframes;

import DTO.Anschrift;
import DTO.Artikel;
import DTO.Artikelkategorie;
import DTO.Auftragskopf;
import DTO.Auftragsposition;
import DTO.Geschaeftspartner;
import DTO.Status;
import DTO.Zahlungskondition;
import Interfaces.InterfaceMainView;
import JFrames.GUIFactory;
import JFrames.Start;
import JFrames.StartAdmin;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Simon <Simon.Simon at your.org>
 */
public class SucheDetailAnzeige extends javax.swing.JInternalFrame {
    /**
     * Zur View Kontrolle.
     */
    private  InterfaceMainView hauptFenster;
    
    /**
     * Ergebnis Daten aus der Suche.
     */
    private Collection<?> ergebnisDaten;
    
    /**
     * Die getätigte Sucheingabe.
     */
    private String suchEingabe;
    
    /**
     * Tabelle aus der die Ergebnisse geholt werden.
     */
    private String tabelle;
    
    /**
     * 
     */
    private Component comp;
    
    /**
     * Creates new form SucheDetailAnzeige
     * @param mainView view
     */
    public SucheDetailAnzeige(InterfaceMainView mainView) {
        initComponents();
        this.hauptFenster = mainView;
        this.Positionanzeige_jTable2.setVisible(false);
        this.position_jPanel1.setVisible(false);
    }
    
    public void setzeDaten(Collection<?> daten) {
        this.ergebnisDaten = daten;
    }
    
    public void setzeSucheingabe(String eingabe) {
        this.Suchengabe_jTextArea1.setText(eingabe);
    }
    
    public void setzeFenster(Component fenster) {
        this.comp = fenster;
    }
    
    public void setzeTabelle(String tabelle) {
        this.tabelle = tabelle;
        this.initTabelle();
    }
    
    private void initTabelle() {
        DefaultTableModel dtm = new DefaultTableModel();
        switch (tabelle) {
            case "Auftragskopf":
                dtm.addColumn("Auftragsnummer");
                dtm.addColumn("Abschlussdatum");
                dtm.addColumn("Auftragstext");
                dtm.addColumn("Eingangsdatum");
                dtm.addColumn("Lieferdatum");
                dtm.addColumn("Wert");
                dtm.addColumn("Auftragsart");
                dtm.addColumn("Kundennummer");
                dtm.addColumn("Kundenname");
                dtm.addColumn("Status");
                dtm.addColumn("Zahlungskondition");
                for (Object o : ergebnisDaten) {
                    Auftragskopf ak = (Auftragskopf) o;
                    dtm.addRow(new Object[] {ak.getAuftragskopfID(), ak.getAbschlussdatum(), 
                    ak.getAuftragstext(), ak.getErfassungsdatum(), ak.getLieferdatum(),
                    ak.getWert(), "Art", ak.getGeschaeftspartner().getGeschaeftspartnerID(),
                    ak.getGeschaeftspartner().getRechnungsadresse().getName(),
                    ak.getStatus().getStatus(), "ZK"});
                    
                }
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Auftragsposition":
                dtm.addColumn("Artikelnummer");
                dtm.addColumn("Artikelname");
                dtm.addColumn("Auftragsnummer");
                dtm.addColumn("Positionsnummer");
                dtm.addColumn("Einzelwert");
                dtm.addColumn("Menge");
                dtm.addColumn("Erfassungsdatum");
                for (Object o : ergebnisDaten) {
                    Auftragsposition position = (Auftragsposition) o;
                    dtm.addRow(new Object[] {position.getArtikel().getArtikelID(),
                                position.getArtikel().getArtikeltext(), 
                                position.getAuftrag().getAuftragskopfID(), 
                                position.getPositionsnummer(), 
                                position.getEinzelwert(), 
                                position.getMenge(),
                                position.getErfassungsdatum()});
                }
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Artikel":
 
                dtm.addColumn("Artnr");
                dtm.addColumn("Arttext");
                dtm.addColumn("Bestelltext");
                dtm.addColumn("Wert");
                dtm.addColumn("Frei");
                dtm.addColumn("Kategorie");
                dtm.addColumn("Mwst");
                dtm.addColumn("Reserviert");
                dtm.addColumn("Verkaufswert");
                dtm.addColumn("Verkauft");
                dtm.addColumn("Zulauf");
                for (Object o : ergebnisDaten) {
                    Artikel artikel = (Artikel) o;
                    Object[] ausgabe = {artikel.getArtikelID(), 
                        artikel.getArtikeltext(), artikel.getBestelltext(), 
                        artikel.getEinkaufswert() , artikel.getFrei() ,
                        artikel.getKategorie().getKategoriename(), 
                        artikel.getMwST(), artikel.getReserviert(), 
                        artikel.getVerkaufswert(), artikel.getVerkaufswert(), 
                        artikel.getVerkauft(), artikel.getZulauf() };
                    dtm.addRow(ausgabe);
                }
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Artikelkategorie":
                dtm.addColumn("Kategorienummer");
                dtm.addColumn("Beschreibung");
                dtm.addColumn("Name");
                dtm.addColumn("Kommentar");
                for (Object o : ergebnisDaten) {
                    Artikelkategorie ak = (Artikelkategorie) o;
                    dtm.addRow(new Object[] {ak.getKategorieID(), ak.getBeschreibung(),
                    ak.getKategoriename(), ak.getKommentar()});
                }
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Status":
                dtm.addColumn("Statusnummer");
                dtm.addColumn("Status");
                for (Object o : ergebnisDaten) {
                    Status ak = (Status) o;
                    dtm.addRow(new Object[] {ak.getStatusID(), 
                        ak.getStatus() });
                }
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Geschäftspartner":
                dtm.addColumn("GpNummer");
                dtm.addColumn("Kreditlimit");
                dtm.addColumn("Typ");
                for (Object o : ergebnisDaten) {
                    Geschaeftspartner partner = (Geschaeftspartner) o;
                    Object[] ausgabe = {partner.getGeschaeftspartnerID(), 
                        partner.getKreditlimit(), partner.getTyp()};
                    dtm.addRow(ausgabe);
                }
                    
                   
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Zahlungskondition":
                dtm.addColumn("Nummer");
                dtm.addColumn("Auftragsart");
                dtm.addColumn("Lieferzeitsofort");
                dtm.addColumn("Mahnzeit1");
                dtm.addColumn("Mahnzeit2");
                dtm.addColumn("Mahnzeit3");
                dtm.addColumn("Skonto1");
                dtm.addColumn("Skonto2");
                dtm.addColumn("Skontozeit1");
                dtm.addColumn("Skontozeit2");
                dtm.addColumn("Sperrzeitwunsch");
                for (Object o : ergebnisDaten) {
                    Zahlungskondition zk = (Zahlungskondition) o;
                    dtm.addRow(new Object[] {zk.getZahlungskonditionID(),
                    zk.getAuftragsart(), zk.getLieferzeitSofort(),
                    zk.getMahnzeit1(), zk.getMahnzeit2(), zk.getMahnzeit3(),
                    zk.getSkonto1(), zk.getSkonto2(), zk.getSkontozeit1(),
                    zk.getSkontozeit2(), zk.getSperrzeitWunsch()});
                }
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Anschrift" : 
                dtm.addColumn("Nummer");
                dtm.addColumn("Titel");
                dtm.addColumn("Name");
                dtm.addColumn("Vorname");
                dtm.addColumn("Geb Datum");
                dtm.addColumn("Strasse");
                dtm.addColumn("Postleitzahl");
                dtm.addColumn("Ort");
                dtm.addColumn("Email");
                dtm.addColumn("Fax");
                dtm.addColumn("Telefonnummer");
                
                for (Object o : ergebnisDaten) {
                    Anschrift anschrift = (Anschrift) o;
                    Object[] ausgabe = {anschrift.getAnschriftID(), anschrift.getTitel(), anschrift.getName(),
                        anschrift.getVorname(), anschrift.getGeburtsdatum(),
                        anschrift.getStrasse(), anschrift.getPLZ(),
                        anschrift.getOrt(), anschrift.getEmail(),
                        anschrift.getFAX(), anschrift.getTelefon()};
                    dtm.addRow(ausgabe);
                }
                this.Anzeige_jTable1.setModel(dtm);
                break;
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Anzeige_jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        Zurueck_jButton1 = new javax.swing.JButton();
        Anzeige_jButton = new javax.swing.JButton();
        Auswaehlen_jButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        position_jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Positionanzeige_jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        Suchengabe_jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        status_jTextField1 = new javax.swing.JTextField();

        Anzeige_jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        Anzeige_jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Anzeige_jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Anzeige_jTable1);

        jToolBar1.setRollover(true);

        Zurueck_jButton1.setText("Zurück");
        Zurueck_jButton1.setFocusable(false);
        Zurueck_jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Zurueck_jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Zurueck_jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Zurueck_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Zurueck_jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(Zurueck_jButton1);

        Anzeige_jButton.setText("Anzeigen");
        Anzeige_jButton.setFocusable(false);
        Anzeige_jButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Anzeige_jButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Anzeige_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Anzeige_jButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(Anzeige_jButton);

        Auswaehlen_jButton.setText("Auswählen");
        Auswaehlen_jButton.setFocusable(false);
        Auswaehlen_jButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Auswaehlen_jButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Auswaehlen_jButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Auswaehlen_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Auswaehlen_jButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(Auswaehlen_jButton);

        jLabel1.setText("Ergebnis:");

        Positionanzeige_jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        Positionanzeige_jTable2.setEnabled(false);
        jScrollPane2.setViewportView(Positionanzeige_jTable2);

        javax.swing.GroupLayout position_jPanel1Layout = new javax.swing.GroupLayout(position_jPanel1);
        position_jPanel1.setLayout(position_jPanel1Layout);
        position_jPanel1Layout.setHorizontalGroup(
            position_jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, position_jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );
        position_jPanel1Layout.setVerticalGroup(
            position_jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(position_jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Suchengabe_jTextArea1.setEditable(false);
        Suchengabe_jTextArea1.setColumns(20);
        Suchengabe_jTextArea1.setRows(5);
        jScrollPane3.setViewportView(Suchengabe_jTextArea1);

        jLabel2.setText("Eingabe:");

        status_jTextField1.setEditable(false);
        status_jTextField1.setText("Es werden maximal 20 Treffer angezeigt!");
        status_jTextField1.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(position_jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(status_jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(position_jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(status_jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Zurueck_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Zurueck_jButton1ActionPerformed
        Start framestart = null;
        StartAdmin framestartadmin = null;
        
        try {
            framestart = (Start) this.hauptFenster;
            framestart.setFrame(framestart.suche);
            this.position_jPanel1.setVisible(false);
            this.Positionanzeige_jTable2.setVisible(false);
            this.setVisible(false);
        } catch (ClassCastException e) {
 
        }
        if (framestart == null) {
            framestartadmin = (StartAdmin) this.hauptFenster;
            framestartadmin.setFrame(framestartadmin.suche);
            this.position_jPanel1.setVisible(false);
            this.Positionanzeige_jTable2.setVisible(false);
            this.setVisible(false);
        }
        
        
    }//GEN-LAST:event_Zurueck_jButton1ActionPerformed

    private void Anzeige_jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Anzeige_jTable1MouseClicked
        DefaultTableModel dtm = new DefaultTableModel();
        ArrayList<Geschaeftspartner> listePartner = null;
        ArrayList<Auftragskopf> listeAuftrag = null;
        Geschaeftspartner gp = null;
        Auftragskopf ak = null;
        
        if (this.tabelle.equals("Geschäftspartner")) {
            this.position_jPanel1.setVisible(true);
            this.Positionanzeige_jTable2.setVisible(true);
            dtm.addColumn("Titel");
            dtm.addColumn("Name");
            dtm.addColumn("Vorname");
            dtm.addColumn("Geb Datum");
            dtm.addColumn("Strasse");
            dtm.addColumn("Postleitzahl");
            dtm.addColumn("Ort");
            dtm.addColumn("Email");
            dtm.addColumn("Fax");
            dtm.addColumn("Telefonnummer");
            dtm.addColumn("Anschrift");
            
            listePartner = new ArrayList<Geschaeftspartner>((Collection<? extends Geschaeftspartner>) this.ergebnisDaten);
            gp = listePartner.get(this.Anzeige_jTable1.getSelectedRow());
            Object[] ausgabeRechnung = {gp.getRechnungsadresse().getTitel(), gp.getRechnungsadresse().getName(),
                gp.getRechnungsadresse().getVorname(), gp.getRechnungsadresse().getGeburtsdatum(),
                gp.getRechnungsadresse().getStrasse(), gp.getRechnungsadresse().getPLZ(),
                gp.getRechnungsadresse().getOrt(), gp.getRechnungsadresse().getEmail(),
                gp.getRechnungsadresse().getFAX(), gp.getRechnungsadresse().getTelefon(),
                "Rechnung"};
            dtm.addRow(ausgabeRechnung);
            Object[] ausgabeLiefer = {gp.getLieferadresse().getTitel(), gp.getLieferadresse().getName(),
                gp.getLieferadresse().getVorname(), gp.getLieferadresse().getGeburtsdatum(),
                gp.getLieferadresse().getStrasse(), gp.getLieferadresse().getPLZ(),
                gp.getLieferadresse().getOrt(), gp.getLieferadresse().getEmail(),
                gp.getLieferadresse().getFAX(), gp.getLieferadresse().getTelefon(),
                "Liefer"};
            dtm.addRow(ausgabeLiefer);
            this.Positionanzeige_jTable2.setModel(dtm);
        } else if (this.tabelle.equals("Auftragskopf")) {
            this.position_jPanel1.setVisible(true);
            this.Positionanzeige_jTable2.setVisible(true);
            dtm.addColumn("Artikelnummer");
            dtm.addColumn("Artikelname");
            dtm.addColumn("Auftragsnummer");
            dtm.addColumn("Positionsnummer");
            dtm.addColumn("Einzelwert");
            dtm.addColumn("Menge");
            dtm.addColumn("Erfassungsdatum");
            
            listeAuftrag = new ArrayList<Auftragskopf>((Collection<? extends Auftragskopf>) this.ergebnisDaten);
            ak = listeAuftrag.get(this.Anzeige_jTable1.getSelectedRow());
            
            for (Auftragsposition position : ak.getPositionsliste()) {
                dtm.addRow(new Object[] {position.getArtikel().getArtikelID(),
                position.getArtikel().getArtikeltext(), position.getAuftrag().getAuftragskopfID(),
                position.getPositionsnummer(), position.getEinzelwert(), position.getMenge(),
                position.getErfassungsdatum()});
            }
            this.Positionanzeige_jTable2.setModel(dtm);
        }
    }//GEN-LAST:event_Anzeige_jTable1MouseClicked

    private void Anzeige_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Anzeige_jButtonActionPerformed
        Start framestart = null;
        StartAdmin framestartadmin = null;
        Artikel artikel = null;
        
        ArrayList<Artikel> artikelListe = null;
        if (this.hauptFenster instanceof Start) {
            framestart = (Start) this.hauptFenster;
            if (this.comp == null) {
                switch (this.tabelle) {
                    case "Auftragskopf" :
                        framestart.auftragskopfanlegen.setStatusAnzeigen();
                        framestart.setFrame(framestart.auftragskopfanlegen);
                        break;
                    case "Artikel" :
                        artikelListe = new ArrayList<Artikel>((Collection<? extends Artikel>) this.ergebnisDaten);
                        artikel = artikelListe.get(this.Anzeige_jTable1.getSelectedRow());
                        framestart.artikelanlegen.setzeFormularInArtikelAnzeigen();
                        framestart.artikelanlegen.gibjTF_Artikelnummer().setText(String.valueOf(artikel.getArtikelID()));
                        framestart.artikelanlegen.gibjTF_Artikelname().setText(artikel.getArtikeltext());
                        framestart.artikelanlegen.gibjTA_Artikelbeschreibung().setText(artikel.getBestelltext());
                        framestart.artikelanlegen.gibjTF_BestandsmengeFREI().setText(String.valueOf(artikel.getFrei()));
                        framestart.artikelanlegen.gibjTF_BestandsmengeRESERVIERT().setText(String.valueOf(artikel.getReserviert()));
                        framestart.artikelanlegen.gibjCB_Artikelkategorie().setSelectedItem(artikel.getKategorie().getKategoriename());
                        
                        framestart.setFrame(framestart.artikelanlegen);
                        break;
                }
            }
        } else {
            framestartadmin = (StartAdmin) this.hauptFenster;
        }
        
        
            
            
        
        this.setVisible(false);
        
    }//GEN-LAST:event_Anzeige_jButtonActionPerformed

    private void Auswaehlen_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswaehlen_jButtonActionPerformed
        ArrayList<Artikelkategorie> artikelListe = null;
        
        
        if (this.hauptFenster.gibLetzteAnzeige().getTitle().equals("Artikel anlegen")) {
            if (this.tabelle.equals("Artikelkategorie")) {
                artikelListe = new ArrayList<Artikelkategorie>(
                        (Collection<? extends Artikelkategorie>) this.ergebnisDaten);
                this.hauptFenster.gibArtikelAnlegenFenster()
                        .gibjCB_Artikelkategorie().setSelectedItem(
                                artikelListe.get(
                                        this.Anzeige_jTable1.getSelectedRow())
                                        .getKategoriename());
            }
        }
        this.setVisible(false);
    }//GEN-LAST:event_Auswaehlen_jButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Anzeige_jButton;
    private javax.swing.JTable Anzeige_jTable1;
    private javax.swing.JButton Auswaehlen_jButton;
    private javax.swing.JTable Positionanzeige_jTable2;
    private javax.swing.JTextArea Suchengabe_jTextArea1;
    private javax.swing.JButton Zurueck_jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel position_jPanel1;
    private javax.swing.JTextField status_jTextField1;
    // End of variables declaration//GEN-END:variables
}
