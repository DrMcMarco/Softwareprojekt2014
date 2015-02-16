/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hauptmenue;

import JFrames.GUIFactory;
import JFrames.Start;
import java.awt.Component;
import Interfaces.InterfaceJTreeFunction;
import JFrames.StartAdmin;
import javax.swing.JInternalFrame;
import javax.swing.tree.TreePath;

/**
 *
 * @author Luca
 *
 * 10.12.2014 Terrasi, Erstellung. 
 * 02.01.2015 Terrasi, JTreenavigation erstellt
 * und Einbindung der Guifactory und der Methoden des Internalframes
 * "StartAdmin".
 */
public class Hauptmenue_Admin extends javax.swing.JInternalFrame
        implements InterfaceJTreeFunction {

    /*
     Hilfsvariablen
     */
    TreePath node; // Speichervariable für den Pfand des Jtrees
    String pfad;// Speichervariable für den Wert des Blattes eines JTrees
    StartAdmin internal;// Referenzvariable des Internalframes

    GUIFactory factory;//Speichervariable für die zu übergebenen Factory

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Dokumentation */
    /* 02.01.2015 Terrasi Überarbeitung des Konstruktors und Dokumentation*/
    /*----------------------------------------------------------*/
    /**
     * Konstruktor, es wird eine GuiFactory gespeichert sowie ein Component das
     * in ein "StartAdmin"-Internalframe gecastet wird. Eine Fehlerbehandlung
     * findet statt, falls nicht gecastet werden kann.
     *
     * @param factory
     * @param frame
     */
    public Hauptmenue_Admin(GUIFactory factory, Component frame) {
        initComponents();
        this.factory = factory;//Übergabe der Factory.
        try {
            this.internal = (StartAdmin) frame;//Übergebene Component wird gecastet und
            // in Hilfsvariable gespeichert.
        } catch (ClassCastException e) {// Fehlerbehandlung, falls ein ein Component nicht  
            // gecastet werden kann.
            System.out.println(e.getMessage());// Ausgabe des Fehlers
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

        jToolBar1 = new javax.swing.JToolBar();
        jB_Zurueck = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_Anzeigen = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        Hauptmenu_jScrollPane = new javax.swing.JScrollPane();
        hauptmenueAdmin_Tree = new javax.swing.JTree();
        Logo_jLabel = new javax.swing.JLabel();

        setResizable(true);
        setTitle("Hauptmenü");
        setVisible(true);

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        jB_Zurueck.setText("Zurück");
        jB_Zurueck.setEnabled(false);
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
        jB_Suchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuchenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Suchen);

        hauptmenueAdmin_Tree.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Navigation");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Auftrag verwalten");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Auftragskopf");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Anlegen");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Ändern");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Anzeigen");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Auftragsposition");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Ändern");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Anzeigen");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Artikel verwalten");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Anlegen");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ändern");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Anzeigen");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Geschäftspartner verwalten");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Anlegen");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ändern");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Anzeigen");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Zahlungskonditionen verwalten");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Anlegen");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ändern");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Anzeigen");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Benutzer verwalten");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Anlegen");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ändern");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Anzeigen");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        hauptmenueAdmin_Tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        hauptmenueAdmin_Tree.setName("Navigation"); // NOI18N
        hauptmenueAdmin_Tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hauptmenueAdmin_TreeMouseClicked(evt);
            }
        });
        Hauptmenu_jScrollPane.setViewportView(hauptmenueAdmin_Tree);

        Logo_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logo_jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hauptmenue/logo.PNG"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Hauptmenu_jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Logo_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Hauptmenu_jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addComponent(Logo_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 02.01.2015 Terrasi  Zuweisung der Funktion und Dokumentation*/
    /*----------------------------------------------------------*/
    /**
     * TreeMouseClicke Event, bei diesem Event soll der Pfad der Baumstruktur
     * gelesen und gespeichert werden. Beim Doppelklick auf ein Blatt des
     * Baumes, wird die überschriebene Schnittstellenmethode aufgerufen. Dieser
     * wird der gespeicherte Baumpfad übergeben. Bei einem Fehler wird dieser
     * auch dementsprechend abgefangen.
     *
     * @param evt
     */
    private void hauptmenueAdmin_TreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hauptmenueAdmin_TreeMouseClicked
        //Try-Block für Fehlerbehandlungen
        try {
            node = hauptmenueAdmin_Tree.getSelectionPath();//Speichern des Pfades.
            pfad = node.toString();// Pfand wird in String gecastet und gesepichert.
            if (evt.getClickCount() == 2) {// Bei Doppelklick auf ein Blatt oder
                // Knoten des Baums.
                openJtreeNodes(pfad);//Aufruf der Schnittstellenmethode.
            }
        } catch (NullPointerException e) {//Fehlerbehandlung.
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_hauptmenueAdmin_TreeMouseClicked

    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        internal.rufeSuche(null);
         this.factory.setComponent(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Dokumentation*/
    /* 02.01.2015 Terrasi  Überarbeitung der Funktion und Dokumentation*/
    /* 08.01.2015 Terrasi  implementierung von User,Auftrgaskopf,
    Auftragspositionen und Zahlungskonditionen Masken in die Baumnavigation*/
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode openJtreeNodes, diese methode bekommt einen String
     * übergeben mit dem verglichen wird, um das entsprechende Fenster
     * aufzurufen.
     *
     * @param node, Variable mit der Verglichen wird um das richtige Fenster zu
     * öffnen.
     */
    @Override
    public void openJtreeNodes(String node) {
        //Try-Block für Fehlerbehandlungen
        try {
            if (node != null) {// Wird geprüft ob es einen Pfad gibt.
                // Vergleich des Pfades mit String um "Auftragskopf Anlegen" aufzurufen.
                if (node.equals("[Navigation, Auftrag verwalten, Auftragskopf, Anlegen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.auftragskopfanlegen.setTitle("Auftragskopf anlegen");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.auftragskopfanlegen);
                    this.internal.auftragskopfanlegen.setStatusAnlegen();
                }
                // Vergleich des Pfades mit String um "Auftragskopf Ändern" aufzurufen.
                if (node.equals("[Navigation, Auftrag verwalten, Auftragskopf, Ändern]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.auftragskopfaendern.setTitle("Auftragskopf ändern Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.auftragskopfaendern);
                    this.internal.auftragskopfanlegen.setStatusAender();
                }
                // Vergleich des Pfades mit String um "Auftragskopf Anzeigen" aufzurufen.
                if (node.equals("[Navigation, Auftrag verwalten, Auftragskopf, Anzeigen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.auftragskopfaendern.setTitle("Auftragskopf anzeigen Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.auftragskopfaendern);
                    this.internal.auftragskopfanlegen.setStatusAnzeigen();
                }
                // Vergleich des Pfades mit String um "Auftragsposition Ändern" aufzurufen.
                if (node.equals("[Navigation, Auftrag verwalten, Auftragsposition, Ändern]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.auftragsspositionaender.setTitle("Auftragsposition ändern Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    this.internal.auftragsspositionanzeigen.setStatusAender();
                    setComponentVisible(this.internal.auftragsspositionaender);
                }
                // Vergleich des Pfades mit String um "Auftragsposition Anzeigen" aufzurufen.
                if (node.equals("[Navigation, Auftrag verwalten, Auftragsposition, Anzeigen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.auftragsspositionaender.setTitle("Auftragsposition anzeigen Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    this.internal.auftragsspositionanzeigen.setStatusAnzeigen();
                    setComponentVisible(this.internal.auftragsspositionaender);
                }
                // Vergleich des Pfades mit String um "Artikel Anlegen" aufzurufen.
                if (node.equals("[Navigation, Artikel verwalten, Anlegen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.artikelanlegen.setTitle("Artikel anlegen");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.artikelanlegen);
                    this.internal.artikelanlegen.setzeFormularInArtikelAnlegen();
                }
                // Vergleich des Pfades mit String um "Artikel Ändern" aufzurufen.
                if (node.equals("[Navigation, Artikel verwalten, Ändern]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.artikelaendern.setTitle("Artikel ändern Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.artikelaendern);
                    this.internal.artikelanlegen.setzeFormularInArtikelAEndern();
                }
                // Vergleich des Pfades mit String um "Artikel Anzeigen" aufzurufen.
                if (node.equals("[Navigation, Artikel verwalten, Anzeigen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.artikelaendern.setTitle("Artikel anzeigen Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.artikelaendern);
                    this.internal.artikelanlegen.setzeFormularInArtikelAnzeigen();
                }
                // Vergleich des Pfades mit String um "Geschäftspartner Anlegen" aufzurufen.
                if (node.equals("[Navigation, Geschäftspartner verwalten, Anlegen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen.Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.geschaeftspartneranlegen.setTitle("Geschäftspartner anlegen");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.geschaeftspartneranlegen);
                    this.internal.geschaeftspartneranlegen.setzeFormularInGPAnlegen();
                }
                // Vergleich des Pfades mit String um "Geschäftspartner Ändern" aufzurufen.
                if (node.equals("[Navigation, Geschäftspartner verwalten, Ändern]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufendenen Maske ein Titel übergeben.
                    this.internal.geschaeftspartneraendern.setTitle("Geschäftspartner ändern Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.geschaeftspartneraendern);
                    this.internal.geschaeftspartneranlegen.setzeFormularInGPAEndern();
                }
                // Vergleich des Pfades mit String um "Geschäftspartner Anzeigen" aufzurufen.
                if (node.equals("[Navigation, Geschäftspartner verwalten, Anzeigen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufendenen Maske ein Titel übergeben.
                    this.internal.geschaeftspartneraendern.setTitle("Geschäftspartner anzeigen Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.geschaeftspartneraendern);
                    this.internal.geschaeftspartneranlegen.setzeFormularInGPAnzeigen();
                }
                // Vergleich des Pfades mit String um "Zahlungskonditionen Anlegen" aufzurufen.
                if (node.equals("[Navigation, Zahlungskonditionen verwalten, Anlegen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufendenen Maske ein Titel übergeben.
                    this.internal.zahlungskonditionaendern.setTitle("Zahlungskonditionen anlegen");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.zahlungskonditionanlegen);
                    this.internal.zahlungskonditionanlegen.setzeFormularInZKAnlegen();
                }
                // Vergleich des Pfades mit String um "Zahlungskonditionen Ändern" aufzurufen.
                if (node.equals("[Navigation, Zahlungskonditionen verwalten, Ändern]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufendenen Maske ein Titel übergeben.
                    this.internal.zahlungskonditionaendern.setTitle("Zahlungskondition ändern Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.zahlungskonditionaendern);
                    this.internal.zahlungskonditionanlegen.setzeFormularInZKAEndern();
                }
                // Vergleich des Pfades mit String um "Zahlungskonditionen Anzeigen" aufzurufen.
                if (node.equals("[Navigation, Zahlungskonditionen verwalten, Anzeigen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufendenen Maske ein Titel übergeben.
                    this.internal.zahlungskonditionaendern.setTitle("Zahlungskondition anzeigen Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.zahlungskonditionaendern);
                    this.internal.zahlungskonditionanlegen.setzeFormularInZKAnzeigen();
                }
                // Vergleich des Pfades mit String um "User Anlegen" aufzurufen.
                if (node.equals("[Navigation, Benutzer verwalten, Anlegen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.useranlegen.setTitle("Benutzer anlegen");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.useranlegen);
                }
                // Vergleich des Pfades mit String um "User Ändern" aufzurufen.
                if (node.equals("[Navigation, Benutzer verwalten, Ändern]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.useraendern.setTitle("Benutzer ändern Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.useraendern);
                }
                // Vergleich des Pfades mit String um "User Anzeigen" aufzurufen.
                if (node.equals("[Navigation, Benutzer verwalten, Anzeigen]")) {
                    // Aufruf der setComponentVisible-Methode um die 
                    // entsprechende Maske sichtbar zu machen. Es wird der 
                    // aufzurufednen Maske ein Titel übergeben.
                    this.internal.useraendern.setTitle("Benutzer anzeigen Einstieg");
                    
                    this.internal.suche.setVisible(false);
                    this.internal.detailSuche.setVisible(false);
                    
                    setComponentVisible(this.internal.useraendern);

                }
            }
        } catch (NullPointerException e) {//Fehlerbehandlung bei einer NullpointerException
            System.out.println(e.getMessage());
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 02.01.2015 Terrasi angelegt und Überschrieben*/
    /*----------------------------------------------------------*/
    /**
     * Methode setComponentVisible erhält ein Frame das sichtbar gemacht werden
     * soll und ruft dafür die Methoden des InternalFrames. Sie übergibt sich
     * selbst auch der Guifactory, damit beim betätigen des Zurück-Buttons auch
     * immer das Menü aufgerufen wird. Bei jedem aufruf dieser Methode, wird das
     * Menü nicht mehr sichtbar für den User.
     *
     * @param frame, Frame das sichtbar gemacht wird
     */
    @Override
    public void setComponentVisible(JInternalFrame frame) {
        this.internal.setCenterJIF(frame);// Aufruf der Methode "setCenterJIF"
        this.internal.setFrame(frame);//Aufruf der Internalframe-Methode "setFrame".
        this.internal.setComponent(frame);// Aufruf der Internalframe-Methode "setComponent".
        this.factory.setComponent(this.internal.hauptmenueadmin);// Aufruf der
        // "setComponent"-Methode der Guifactory.
        this.setVisible(false);// Setzt sich selbst auf visible false um nicht
        // mehr sichtbar zu sein.
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Hauptmenu_jScrollPane;
    private javax.swing.JLabel Logo_jLabel;
    private javax.swing.JTree hauptmenueAdmin_Tree;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
