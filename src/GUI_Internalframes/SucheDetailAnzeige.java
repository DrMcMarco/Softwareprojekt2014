/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI_Internalframes;

import DAO.ApplicationException;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*----------------------------------------------------------*/
/* Datum Name Was                                           */
/* 15.01.15 sch angelegt                                    */
/*----------------------------------------------------------*/
/**
 *
 * @author Simon <Simon.Simon at your.org>
 */
public class SucheDetailAnzeige extends javax.swing.JInternalFrame {
    
    /**
     * Fehler-Meldung zur Auswahl.
     */
    private static final String FEHLER_AUSWAHL = "Bitte wählen Sie "
                            + "eine Zeile aus!";
    /**
     * Fehler-Meldung zur Auswahl der Tabelle.
     */
    private static final String FEHLER_TABELLE = "Die Informationen aus dieser "
            + "Tabelle werden in diesem Fenster nicht referenziert!";
    /**
     * Fehler-Meldung zur Anzeige der Tabelle.
     */
    private static final String FEHLER_ANZEIGE = "Es gibt keine Bearbeitung "
            + "/ Anzeige Möglichkeit für ";
    
    /**
     * Zur View Kontrolle.
     */
    private InterfaceMainView hauptFenster;
    
    /**
     * Ergebnis Daten aus der Suche.
     */
    private Collection ergebnisDaten;
    
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
     * Creates new form SucheDetailAnzeige.
     * @param mainView view
     */
    public SucheDetailAnzeige(InterfaceMainView mainView) {
        initComponents();
        this.hauptFenster = mainView;
        this.Positionanzeige_jTable2.setVisible(false);
        this.position_jPanel1.setVisible(false);
        
    }
    
    /**
     * Setzt die gefundenen Daten.
     * @param daten Ergebnis Collection aus der Suche.
     */
    public void setzeDaten(Collection<?> daten) {
        this.ergebnisDaten = daten;
        if (this.hauptFenster.gibLetzteAnzeige() == null) {
            this.Auswaehlen_jButton.setEnabled(false);
            this.Anzeige_jButton.setEnabled(true);
        } else {
            this.Auswaehlen_jButton.setEnabled(true);
            this.Anzeige_jButton.setEnabled(false);
        }
        
    }
    
    /**
     * Setzt die getätigte Sucheingabe.
     * Sie wird noch einmal oberhalb der View angezeigt.
     * @param eingabe die Sucheingabe
     * @param sort Gibt die Sortierung an.
     */
    public void setzeSucheingabe(String eingabe, String sort) {
        this.Suchengabe_jTextArea1.setText(eingabe);
        List<?> liste = new ArrayList();
        List absteigendeListe = new ArrayList();
        //Prüfe, ob eine Sucheingabe getätigt wurde und ob die Sortierung 
        //absteigend erfolgen soll
        if ("".equals(eingabe) && "absteigend".equals(sort)) {
            //übernehme die Daten
            liste.addAll(this.ergebnisDaten);
            //Durchlaufe die Liste von hinten
            for (int i = liste.size() - 1; i >= 0; i--) {
                //Füge das letzte Element an die Erste stelle der 2. Liste...
                absteigendeListe.add(liste.get(i));
            }
            //Setze das ergebnis erneut aber in umgekehrter Reihenfolge...
            this.ergebnisDaten = absteigendeListe;
        } 
    }
    
    /**
     * 
     * @param fenster 
     */
    public void setzeFenster(Component fenster) {
        this.comp = fenster;
    }
    
    /**
     * Setzt den Tabellennamen in dem gesucht wurde.
     * @param tabelle Tabelle
     */
    public void setzeTabelle(String tabelle) {
        this.tabelle = tabelle;
        this.initTabelle();
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 15.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Initialisiert die erste Tabelle.
     */
    private void initTabelle() {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //In Abhängigkeit der gesuchten Tabelle, wird die jTable erzeugt und 
        //gesetzt.
        switch (tabelle) {
            //Es wurde in Auftragskopf gesucht
            case "Auftragskopf":
                //Setze Spaltennamen
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
                //dtm.addColumn("Zahlungskondition");
                //Iteriere über alle gefundenen Datensätze und zeige sie 
                //In der Anzeige Tabelle an.
                for (Object o : ergebnisDaten) {
                    //Caste nach Auftragskopf
                    Auftragskopf ak = (Auftragskopf) o;
                    
                    //Füge dem Model einen neuen Datensatz als Zeile hinzu
                    dtm.addRow(new Object[] {ak.getAuftragskopfID(), 
                        new SimpleDateFormat("dd.MM.yyyy").format(
                                ak.getAbschlussdatum()), ak.getAuftragstext(),
                        new SimpleDateFormat("dd.MM.yyyy").format(
                                ak.getErfassungsdatum()), new SimpleDateFormat(
                                        "dd.MM.yyyy").format(
                                                ak.getLieferdatum()), 
                        ak.getWert(), ak.getTyp(), 
                        ak.getGeschaeftspartner().getGeschaeftspartnerID(), 
                        ak.getGeschaeftspartner().getRechnungsadresse()
                                .getName(), 
                        ak.getStatus().getStatus()});
                    
                }
                //Setze das Model
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Auftragsposition":
                //Setze Spaltennamen
                dtm.addColumn("Artikelnummer");
                dtm.addColumn("Artikelname");
                dtm.addColumn("Auftragsnummer");
                dtm.addColumn("Positionsnummer");
                dtm.addColumn("Einzelwert");
                dtm.addColumn("Menge");
                dtm.addColumn("Erfassungsdatum");
                //Iteriere über alle gefundenen Datensätze und zeige sie 
                //In der Anzeige Tabelle an.
                for (Object o : ergebnisDaten) {
                    //Caste nach Auftragsposition
                    Auftragsposition position = (Auftragsposition) o;
                    //Füge dem Model einen neuen Datensatz als Zeile hinzu
                    dtm.addRow(new Object[] {position.getArtikel()
                            .getArtikelID(),
                                position.getArtikel().getArtikeltext(), 
                                position.getAuftrag().getAuftragskopfID(), 
                                position.getPositionsnummer(), 
                                position.getEinzelwert(), 
                                position.getMenge(),
                                new SimpleDateFormat("dd.MM.yyyy").format(
                                        position.getErfassungsdatum())});
                }
                //Setze das Model
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Artikel":
                //Setze Spaltennamen
                dtm.addColumn("Artnr");
                dtm.addColumn("Arttext");
                dtm.addColumn("Bestelltext");
                dtm.addColumn("Wert");
                dtm.addColumn("Verkaufswert");
                dtm.addColumn("Kategorie");
                dtm.addColumn("Mwst");
                dtm.addColumn("Frei");
                dtm.addColumn("Reserviert");
                dtm.addColumn("Verkauft");
                dtm.addColumn("Zulauf");
                //Iteriere über alle gefundenen Datensätze und zeige sie 
                //In der Anzeige Tabelle an.
                for (Object o : ergebnisDaten) {
                    //Caste nach Artikel
                    Artikel artikel = (Artikel) o;
                    //Erstelle ein Objekt Array mit allen Informationen aus dem
                    //Artikel Objekt
                    Object[] ausgabe = {artikel.getArtikelID(), 
                        artikel.getArtikeltext(), artikel.getBestelltext(), 
                        artikel.getEinkaufswert(), artikel.getVerkaufswert(),
                        artikel.getKategorie().getKategoriename(), 
                        artikel.getMwST(), artikel.getFrei(), 
                        artikel.getReserviert(),  
                        artikel.getVerkauft(), artikel.getZulauf() };
                    //Füge dem Model einen neuen Datensatz als Zeile hinzu
                    dtm.addRow(ausgabe);
                }
                //Setze das Model
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Artikelkategorie":
                //Setze Spaltennamen
                dtm.addColumn("Kategorienummer");
                dtm.addColumn("Beschreibung");
                dtm.addColumn("Name");
                dtm.addColumn("Kommentar");
                //Iteriere über alle gefundenen Datensätze und zeige sie 
                //In der Anzeige Tabelle an.
                for (Object o : ergebnisDaten) {
                    //Caste nach Artikelkategorie
                    Artikelkategorie ak = (Artikelkategorie) o;
                    //Füge dem Model einen neuen Datensatz als Zeile hinzu
                    dtm.addRow(new Object[] {ak.getKategorieID(), 
                        ak.getBeschreibung(), ak.getKategoriename(), 
                        ak.getKommentar()});
                }
                //Setze das Model
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Status":
                //Setze Spaltennamen
                dtm.addColumn("Statusnummer");
                dtm.addColumn("Status");
                //Iteriere über alle gefundenen Datensätze und zeige sie 
                //In der Anzeige Tabelle an.
                for (Object o : ergebnisDaten) {
                    //Caste nach Status
                    Status ak = (Status) o;
                    //Füge dem Model einen neuen Datensatz als Zeile hinzu
                    dtm.addRow(new Object[] {ak.getStatusID(), 
                        ak.getStatus() });
                }
                //Setze das Model
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Geschäftspartner":
                //Setze Spaltennamen
                dtm.addColumn("GpNummer");
                dtm.addColumn("Kreditlimit");
                dtm.addColumn("Typ");
                //Iteriere über alle gefundenen Datensätze und zeige sie 
                //In der Anzeige Tabelle an.
                for (Object o : ergebnisDaten) {
                    //Caste nach Geschaeftspartner
                    Geschaeftspartner partner = (Geschaeftspartner) o;
                    //Füge dem Model einen neuen Datensatz als Zeile hinzu
                    Object[] ausgabe = {partner.getGeschaeftspartnerID(), 
                        partner.getKreditlimit(), partner.getTyp()};
                    dtm.addRow(ausgabe);
                }
                //Setze das Model
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Zahlungskondition":
                //Setze Spaltennamen
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
                //Iteriere über alle gefundenen Datensätze und zeige sie 
                //In der Anzeige Tabelle an.
                for (Object o : ergebnisDaten) {
                    //Caste nach Zahlungskondition
                    Zahlungskondition zk = (Zahlungskondition) o;
                    //Füge dem Model einen neuen Datensatz als Zeile hinzu
                    dtm.addRow(new Object[] {zk.getZahlungskonditionID(), 
                        zk.getAuftragsart(), zk.getLieferzeitSofort(), 
                        zk.getMahnzeit1(), zk.getMahnzeit2(), zk.getMahnzeit3(),
                        zk.getSkonto1(), zk.getSkonto2(), zk.getSkontozeit1(),
                        zk.getSkontozeit2(), zk.getSperrzeitWunsch()});
                }
                //Setze das Model
                this.Anzeige_jTable1.setModel(dtm);
                break;
            case "Anschrift" : 
                //Setze Spaltennamen
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
                //Iteriere über alle gefundenen Datensätze und zeige sie 
                //In der Anzeige Tabelle an.
                for (Object o : ergebnisDaten) {
                    //Caste nach Anschrift
                    Anschrift anschrift = (Anschrift) o;
                    //Füge dem Model einen neuen Datensatz als Zeile hinzu
                    Object[] ausgabe = {anschrift.getAnschriftID(), 
                        anschrift.getTitel(), anschrift.getName(),
                        anschrift.getVorname(), new SimpleDateFormat(
                                "dd.MM.yyyy").format(
                                        anschrift.getGeburtsdatum()),
                        anschrift.getStrasse(), anschrift.getPLZ(),
                        anschrift.getOrt(), anschrift.getEmail(),
                        anschrift.getFAX(), anschrift.getTelefon()};
                    dtm.addRow(ausgabe);
                }
                //Setze das Model
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

        setTitle("Ergebnis");

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
        Anzeige_jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Anzeige_jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Anzeige_jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Anzeige_jTable1);

        jToolBar1.setRollover(true);

        Zurueck_jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Zurück.PNG"))); // NOI18N
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
        Positionanzeige_jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
        status_jTextField1.setText("Es werden maximal 200 Treffer angezeigt!");
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
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
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

    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 15.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Wird auf ein Datensatz in der Tabelle "Anzeige_jTable" getätigt,
     * so wird überprüft, ob es weitere Informationen zu diesem Datensatz gibt.
     * Beispiel: Geschäftspartner -> die Anschriftsdaten werden seperat 
     * unterhalb in der positions_jTable angezeigt.
     * Auftragskopf -> Die Positionen werden seperat 
     * unterhalb in der positions_jTable angezeigt.
     * 
     * @param evt event.
     */
    private void Anzeige_jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Anzeige_jTable1MouseClicked
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ArrayList<Geschaeftspartner> listePartner = null;
        ArrayList<Auftragskopf> listeAuftrag = null;
        Geschaeftspartner gp = null;
        Auftragskopf ak = null;
        //Prüfe, ob nach einem Geschäftspartner gesucht wurde
        if (this.tabelle.equals("Geschäftspartner")) {
            //Setze die positions Tabelle auf sichtbar
            this.position_jPanel1.setVisible(true);
            this.Positionanzeige_jTable2.setVisible(true);
            //Setze die Spaltennamen
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
            //Initialisiere die Informationen aus der Suche in eine 
            //nach GP Typisierte Liste
            listePartner = new ArrayList<>((Collection<? 
                    extends Geschaeftspartner>) this.ergebnisDaten);
            //Hole aus dieser Liste das selektierte GP-Objekt anhand, der
            //getSelectedRow index
            gp = listePartner.get(this.Anzeige_jTable1.getSelectedRow());
            //Erstelle ein Objekt Array mit allen Informationen aus dem GP
            //Zunächst die Rechnungsanschrift
            Object[] ausgabeRechnung = {gp.getRechnungsadresse().getTitel(), 
                gp.getRechnungsadresse().getName(), 
                gp.getRechnungsadresse().getVorname(), 
                new SimpleDateFormat("dd.MM.yyyy").format(
                        gp.getRechnungsadresse().getGeburtsdatum()),
                gp.getRechnungsadresse().getStrasse(), 
                gp.getRechnungsadresse().getPLZ(),
                gp.getRechnungsadresse().getOrt(), 
                gp.getRechnungsadresse().getEmail(),
                gp.getRechnungsadresse().getFAX(), 
                gp.getRechnungsadresse().getTelefon(),
                "Rechnung"};
            //Füge dem Tabellen-Modell einen Datensatz als Zeile hinzu
            dtm.addRow(ausgabeRechnung);
            //Wie oben nur mit der Lieferadresse
            Object[] ausgabeLiefer = {gp.getLieferadresse().getTitel(), 
                gp.getLieferadresse().getName(),
                gp.getLieferadresse().getVorname(), 
                new SimpleDateFormat("dd.MM.yyyy").format(
                        gp.getLieferadresse().getGeburtsdatum()),
                gp.getLieferadresse().getStrasse(), 
                gp.getLieferadresse().getPLZ(),
                gp.getLieferadresse().getOrt(), 
                gp.getLieferadresse().getEmail(),
                gp.getLieferadresse().getFAX(), 
                gp.getLieferadresse().getTelefon(),
                "Liefer"};
            //Füge dem Tabellen-Modell einen Datensatz als Zeile hinzu
            dtm.addRow(ausgabeLiefer);
            //Übergebe das Model an die Table
            this.Positionanzeige_jTable2.setModel(dtm);
        } else if (this.tabelle.equals("Auftragskopf")) {
            //Setze die positions Tabelle auf sichtbar
            this.position_jPanel1.setVisible(true);
            this.Positionanzeige_jTable2.setVisible(true);
            //Setze die Spaltennamen
            dtm.addColumn("Artikelnummer");
            dtm.addColumn("Artikelname");
            dtm.addColumn("Auftragsnummer");
            dtm.addColumn("Positionsnummer");
            dtm.addColumn("Einzelwert");
            dtm.addColumn("Menge");
            dtm.addColumn("Erfassungsdatum");
            //Initialisiere die Informationen aus der Suche in eine 
            //nach Auftragskopf Typisierte Liste
            listeAuftrag = new ArrayList<>((Collection<? 
                    extends Auftragskopf>) this.ergebnisDaten);
            //Hole aus dieser Liste das selektierte GP-Objekt anhand, der
            //getSelectedRow index
            ak = listeAuftrag.get(this.Anzeige_jTable1.getSelectedRow());
            //Iteriere über alle Positionen 
            for (Auftragsposition position : ak.getPositionsliste()) {
                
                //Füge dem Tabellen-Modell einen Datensatz als Zeile hinzu
                dtm.addRow(new Object[] {position.getArtikel().getArtikelID(),
                    position.getArtikel().getArtikeltext(), 
                    position.getAuftrag().getAuftragskopfID(),
                    position.getPositionsnummer(), 
                    position.getEinzelwert(), position.getMenge(),
                    new SimpleDateFormat("dd.MM.yyyy").format(
                            position.getErfassungsdatum())});
            }
            //Übergebe das Model an die Table
            this.Positionanzeige_jTable2.setModel(dtm);
        }
    }//GEN-LAST:event_Anzeige_jTable1MouseClicked

    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 15.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Der Anzeige Button übernimmt alle Informationen aus dem Datensatz
     * und ruft die entsprechende View zur Anzeige dieser Daten auf.
     * Von dort an ist es möglich die Daten zu bearbeiten.
     * @param evt event
     */
    private void Anzeige_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Anzeige_jButtonActionPerformed
        ArrayList<Geschaeftspartner> gpListe = null;
        ArrayList<Zahlungskondition> zkListe = null;
        ArrayList<Artikel> artikelListe = null;
        ArrayList<Auftragskopf> akListe = null;
        ArrayList<Auftragsposition> positionen = null;
        
        //Prüfe, ob ein Datensatz selektiert wurde.
        if (this.Anzeige_jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            //Fehlerbehandlung daher wird hier abgebrochen...
            return;
        }
        
        //Prüfe, um welche Tabelle es sich handelt.
        //Hier nach wird bestimmt, welche Maske aufgerufen wird.
        switch (this.tabelle) {
            case "Auftragskopf" :
                //Lade die gefundenen Datensätze typisiert
                akListe = new ArrayList<>((Collection<? 
                                extends Auftragskopf>) this.ergebnisDaten);
                //Setze die Daten in die Eingabefelder der Maske
                this.hauptFenster.gibAuftragskopfanlegenFenster().setStatusAnzeigen();
                this.hauptFenster.gibAuftragskopfanlegenFenster()
                        .setzeEingabeFuerSuche(akListe.get(
                                this.Anzeige_jTable1.getSelectedRow()));              
                
                //Mach das Fenster sichtbar
                this.hauptFenster.gibAuftragskopfanlegenFenster()
                        .setVisible(true);
                //Setze ggf. die Positions-Table auf nicht sichtbar
                this.position_jPanel1.setVisible(false);
                this.Positionanzeige_jTable2.setVisible(false);
                //Schliesse das Suchfenster
                this.setVisible(false);
                break;
            case "Artikel" :
                //Lade die gefundenen Datensätze typisiert
                artikelListe = new ArrayList<>((Collection<? 
                                extends Artikel>) this.ergebnisDaten);
                //Setze die Daten in die Eingabefelder der Maske
                this.hauptFenster.gibArtikelAnlegenFenster()
                        .zeigeArtikelAusSucheAn(artikelListe.get(
                                this.Anzeige_jTable1.getSelectedRow()));
                //Mach das Fenster sichtbar
                this.hauptFenster.gibArtikelAnlegenFenster().setVisible(true);
                //Setze ggf. die Positions-Table auf nicht sichtbar
                this.position_jPanel1.setVisible(false);
                this.Positionanzeige_jTable2.setVisible(false);
                //Schliesse das Suchfenster
                this.setVisible(false);
                break;
            case "Geschäftspartner" :
                //Lade die gefundenen Datensätze typisiert
                gpListe = new ArrayList<>((Collection<? 
                                extends Geschaeftspartner>) this.ergebnisDaten);
                //Setze die Daten in die Eingabefelder der Maske
                this.hauptFenster.gibGeschaeftspartneranlegenFenster()
                        .zeigeGPausSucheAN(gpListe.get(
                                this.Anzeige_jTable1.getSelectedRow()));
                //Mach das Fenster sichtbar
                this.hauptFenster.gibGeschaeftspartneranlegenFenster()
                        .setVisible(true);
                //Setze ggf. die Positions-Table auf nicht sichtbar
                this.position_jPanel1.setVisible(false);
                this.Positionanzeige_jTable2.setVisible(false);
                //Schliesse das Suchfenster
                this.setVisible(false);
                break;
            case "Zahlungskondition" :
                //Lade die gefundenen Datensätze typisiert
                zkListe = new ArrayList<>((Collection<? 
                                extends Zahlungskondition>) this.ergebnisDaten);
                //Setze die Daten in die Eingabefelder der Maske
                this.hauptFenster.gibZkAnlegen().zeigeZKausSucheAn(
                        zkListe.get(this.Anzeige_jTable1.getSelectedRow()));
                //Mach das Fenster sichtbar
                this.hauptFenster.gibZkAnlegen().setVisible(true);
                //Setze ggf. die Positions-Table auf nicht sichtbar
                this.position_jPanel1.setVisible(false);
                this.Positionanzeige_jTable2.setVisible(false);
                //Schliesse das Suchfenster
                this.setVisible(false);
                break;
            case "Auftragsposition" :
                //Lade die gefundenen Datensätze typisiert
                positionen = new ArrayList<>((Collection<? 
                                extends Auftragsposition>) this.ergebnisDaten);
                //Setze die Daten in die Eingabefelder der Maske
                this.hauptFenster.gibApAnzeigen().setzeEingaben(
                        positionen.get(this.Anzeige_jTable1.getSelectedRow()));
                //Mach das Fenster sichtbar
                this.hauptFenster.gibApAnzeigen().setVisible(true);
                //Setze ggf. die Positions-Table auf nicht sichtbar
                this.position_jPanel1.setVisible(false);
                this.Positionanzeige_jTable2.setVisible(false);
                //Schliesse das Suchfenster
                this.setVisible(false);
                break;
            case "Artikelkategorie" : 
                JOptionPane.showMessageDialog(null, FEHLER_ANZEIGE 
                        + "Artikelkategorie.", "Information", 
                        JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Status" : 
                JOptionPane.showMessageDialog(null, FEHLER_ANZEIGE + "Status.", 
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Anschrift" : 
                JOptionPane.showMessageDialog(null, FEHLER_ANZEIGE 
                        + "Anschrift.\nDie Bearbeitung erfolgt über "
                        + "Geschäftspartner.", "Information", 
                        JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }//GEN-LAST:event_Anzeige_jButtonActionPerformed
    
    /*----------------------------------------------------------*/
    /* Datum Name Was                                           */
    /* 15.01.15 sch angelegt                                    */
    /*----------------------------------------------------------*/
    /**
     * Auswählen Button übernimmt den gesuchten Datensatz in die View,
     * von wo die Suche aufgerufen wurde.
     * Hier wird geprüft, wohin die Informationen gehen sollen, und welche
     * Informationen benötigt werden.
     */
    private void Auswaehlen_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswaehlen_jButtonActionPerformed
        //Einzelne Typisierte Listen 
        ArrayList<Artikelkategorie> kategorieListe = null;
        ArrayList<Geschaeftspartner> gpListe = null;
        ArrayList<Artikel> artikelListe = null;
        ArrayList<Anschrift> anschriftListe = null;
        ArrayList<Zahlungskondition> zkListe = null;
        ArrayList<Auftragsposition> positionen = null;
        ArrayList<Auftragskopf> akListe = null;
        //Prüfe, aus welcher View die Suche aufgerufen worden ist.
        //Dies ist entscheiden, da in jeder View nur IN speziellen Tabellen
        //und nach speziellen Attributen gesucht werden kann
        //Prüfe, ob die Suche von Artikel anlegen oder ändern gerufen wurde.
        if (this.hauptFenster.gibLetzteAnzeige().getTitle().equals("Artikel "
                + "anlegen") || this.hauptFenster.gibLetzteAnzeige().getTitle()
                        .equals("Artikel ändern")
                || this.hauptFenster.gibLetzteAnzeige().getTitle()
                        .equals("Artikel anzeigen")) {
            //Prüfe, ob sich die Suche auf Artikelkategorie bezieht
            if (this.tabelle.equals("Artikelkategorie")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Artikelkat.
                    kategorieListe = new ArrayList<>(
                            (Collection<? extends Artikelkategorie>) 
                                    this.ergebnisDaten);
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibArtikelAnlegenFenster()
                            .gibjCB_Artikelkategorie().setSelectedItem(
                                    kategorieListe.get(this.Anzeige_jTable1
                                            .getSelectedRow())
                                            .getKategoriename());
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else if (this.tabelle.equals("Artikel")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Artikelkat.
                    artikelListe = new ArrayList<>(
                            (Collection<? extends Artikel>) 
                                    this.ergebnisDaten);
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibArtikelAnlegenFenster()
                            .zeigeArtikelAusSucheAn(artikelListe.get(
                                this.Anzeige_jTable1.getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else {
                JOptionPane.showMessageDialog(null, FEHLER_TABELLE, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        //Prüfe, ob die Suche von Auftragskopf anlegen oder ändern gerufen wurde
        } else if (this.hauptFenster.gibLetzteAnzeige().getTitle().equals(
                "Auftragskopf anlegen") || this.hauptFenster.gibLetzteAnzeige()
                        .getTitle().equals("Auftragskopf ändern")
                || this.hauptFenster.gibLetzteAnzeige().getTitle()
                        .equals("Auftragskopf anzeigen")) {
            //Prüfe, ob sich die Suche auf GP bezieht
            if (this.tabelle.equals("Geschäftspartner")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach GP.
                    gpListe = new ArrayList<>(
                        (Collection<? 
                                extends Geschaeftspartner>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibAuftragskopfanlegenFenster()
                            .setGeschaeftspartnerID(String.valueOf(gpListe.get(
                                    this.Anzeige_jTable1.getSelectedRow())
                                    .getGeschaeftspartnerID()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Prüfe, ob sich die Suche auf Artikel bezieht
            } else if (this.tabelle.equals("Artikel")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Artikel.
                    artikelListe = new ArrayList<>(
                        (Collection<? 
                                extends Artikel>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibAuftragskopfanlegenFenster()
                            .setArtikelid_jTextField(String.valueOf(
                                    artikelListe.get(
                                    this.Anzeige_jTable1.getSelectedRow())
                                    .getArtikelID()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } 
                //Prüfe, ob sich die Suche auf Auftragskopf bezieht
            } else if (this.tabelle.equals("Auftragskopf")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Anschrift.
                    akListe = new ArrayList<>(
                        (Collection<? 
                                extends Auftragskopf>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibAuftragskopfanlegenFenster()
                            .setzeEingabe(akListe.get(
                                    this.Anzeige_jTable1.getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else {
                JOptionPane.showMessageDialog(null, FEHLER_TABELLE, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        //Prüfe, ob die Suche von Geschäftspartner anlegen 
        //oder ändern gerufen wurde
        } else if (this.hauptFenster.gibLetzteAnzeige().getTitle().equals(
                "Geschäftspartner anlegen") || this.hauptFenster
                        .gibLetzteAnzeige().getTitle().equals("Geschäftspartner"
                                + " ändern")
                || this.hauptFenster.gibLetzteAnzeige().getTitle()
                        .equals("Geschäftspartner anzeigen")) {
            //Prüfe, ob sich die Suche auf Anschrift bezieht
            if (this.tabelle.equals("Anschrift")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Anschrift.
                    anschriftListe = new ArrayList<>(
                        (Collection<? 
                                extends Anschrift>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibGeschaeftspartneranlegenFenster()
                            .setzeAnschrift(anschriftListe.get(
                                    this.Anzeige_jTable1.getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Gp aus der Anzeige heraus suchen
            } else if (this.tabelle.equals("Geschäftspartner")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Anschrift.
                    gpListe = new ArrayList<>(
                        (Collection<? 
                                extends Geschaeftspartner>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibGeschaeftspartneranlegenFenster()
                            .zeigeGPausSucheAN(gpListe.get(
                                    this.Anzeige_jTable1.getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else {
                JOptionPane.showMessageDialog(null, FEHLER_TABELLE, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        //Prüfe, ob die Suche von X anlegen oder ändern gerufen wurde
        } else if (this.hauptFenster.gibLetzteAnzeige().getTitle().equals(
                "Artikel ändern Einstieg")
                || this.hauptFenster.gibLetzteAnzeige().getTitle()
                        .equals("Artikel anzeigen Einstieg")) {
            //Prüfe, ob sich die Suche auf Anschrift bezieht
            if (this.tabelle.equals("Artikel")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Anschrift.
                    artikelListe = new ArrayList<>(
                        (Collection<? 
                                extends Artikel>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibArtikelaendernEinstieg()
                            .setzeGP_IDAusSuche(artikelListe.get(
                                    this.Anzeige_jTable1.getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else {
                JOptionPane.showMessageDialog(null, FEHLER_TABELLE, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        } else if (this.hauptFenster.gibLetzteAnzeige().getTitle().equals(
                "Auftragskopf ändern Einstieg") 
                || this.hauptFenster.gibLetzteAnzeige().getTitle()
                        .equals("Auftragskopf anzeigen Einstieg")) {
            //Prüfe, ob sich die Suche auf Auftragskopf bezieht
            if (this.tabelle.equals("Auftragskopf")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Anschrift.
                    akListe = new ArrayList<>(
                        (Collection<? 
                                extends Auftragskopf>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibAkAendernEinstieg()
                            .setAuftragskopfID_jTextField(akListe.get(
                                    this.Anzeige_jTable1.getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else {
                JOptionPane.showMessageDialog(null, FEHLER_TABELLE, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        } else if (this.hauptFenster.gibLetzteAnzeige().getTitle().equals(
                "Geschäftspartner ändern Einstieg") 
                || this.hauptFenster.gibLetzteAnzeige().getTitle()
                        .equals("Geschäftspartner anzeigen Einstieg")) {
            //Prüfe, ob sich die Suche auf Anschrift bezieht
            if (this.tabelle.equals("Geschäftspartner")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Anschrift.
                    gpListe = new ArrayList<>(
                        (Collection<? 
                                extends Geschaeftspartner>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibGeschaeftspartnerAendernEinstieg()
                            .setzeGP_IDAusSuche(gpListe.get(
                                    this.Anzeige_jTable1.getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else {
                JOptionPane.showMessageDialog(null, FEHLER_TABELLE, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        } else if (this.hauptFenster.gibLetzteAnzeige().getTitle().equals(
                "Zahlungskondition ändern Einstieg")
                || this.hauptFenster.gibLetzteAnzeige().getTitle()
                                .equals("Zahlungskondition "
                                        + "anzeigen Einstieg")) {
            //Prüfe, ob sich die Suche auf Anschrift bezieht
            if (this.tabelle.equals("Zahlungskondition")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Anschrift.
                    zkListe = new ArrayList<>(
                         (Collection<? 
                                extends Zahlungskondition>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibZkAendernEinstieg()
                            .setzeGP_IDAusSuche(zkListe.get(
                                    this.Anzeige_jTable1.getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else {
                JOptionPane.showMessageDialog(null, FEHLER_TABELLE, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        } else if (this.hauptFenster.gibLetzteAnzeige().getTitle().equals(
                "Auftragsposition ändern Einstieg") 
                        || this.hauptFenster.gibLetzteAnzeige().getTitle()
                                .equals("Auftragsposition anzeigen Einstieg")) {
            //Prüfe, ob sich die Suche auf Anschrift bezieht
            if (this.tabelle.equals("Auftragskopf")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1 
                        && this.Positionanzeige_jTable2.getSelectedRow() 
                        != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Anschrift.
                    akListe = new ArrayList<>(
                         (Collection<? 
                                extends Auftragskopf>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibPositionAendernEinstieg()
                            .setAuftragskopfID_jTextField(akListe.get(
                                    this.Anzeige_jTable1.getSelectedRow()));
                    
                    this.hauptFenster.gibPositionAendernEinstieg()
                            .setAuftragspositionID_jTextField(akListe.get(
                                    this.Anzeige_jTable1.getSelectedRow())
                                    .getPositionsliste().get(
                                            this.Positionanzeige_jTable2
                                                    .getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else {
                JOptionPane.showMessageDialog(null, FEHLER_TABELLE, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        } else if (this.hauptFenster.gibLetzteAnzeige().getTitle()
                                .equals("Zahlungskondition anzeigen")) {
            //Prüfe, ob sich die Suche auf Anschrift bezieht
            if (this.tabelle.equals("Zahlungskondition")) {
                //Prüfe, ob ein Datensatz selektiert wurde.
                if (this.Anzeige_jTable1.getSelectedRow() != -1) {
                    //Caste die Ergebnisliste aus der Suche nach Anschrift.
                    zkListe = new ArrayList<>(
                         (Collection<? 
                                extends Zahlungskondition>) this.ergebnisDaten);
                    
                    //Setze in der View entsprechend das Feld mit den 
                    //Informationen
                    this.hauptFenster.gibZkAnlegen()
                            .zeigeZKausSucheAn(zkListe.get(
                                    this.Anzeige_jTable1.getSelectedRow()));
                    //Schließe die Suche
                    this.setVisible(false);
                    //Setze ggf. die Positions-Table auf nicht sichtbar
                    this.position_jPanel1.setVisible(false);
                    this.Positionanzeige_jTable2.setVisible(false);
                //Wenn kein Datensatz ausgewählt wurde, wird der Benutzer
                //Daraufhin aufmerksam gemacht
                } else {
                    JOptionPane.showMessageDialog(null, FEHLER_AUSWAHL, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            //Wenn eine Tabelle ausgewählt wurde die nicht hierauf 
            //Referenziert, dann gib eine Meldung aus und der Benutzer
            //Muss seine Eingabe wiederholen
            } else {
                JOptionPane.showMessageDialog(null, FEHLER_TABELLE, 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        }
        
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
