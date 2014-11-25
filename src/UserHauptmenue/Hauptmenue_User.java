/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHauptmenue;

import ArtikelVerwalten.ArtikelAEndernEinstieg;
import ArtikelVerwalten.ArtikelAnlegen;
import ArtikelVerwalten.ArtikelAnzeigen;
import AuftragskopfVerwaltung.AuftragskopfAendern;
import AuftragskopfVerwaltung.AuftragskopfAnlegen;
import AuftragskopfVerwaltung.AuftragskopfAnzeigen;
import AuftragspositionVerwaltung.AuftragspositionAendern;
import AuftragspositionVerwaltung.AuftragspositionAnzeigen;
import GeschäftspartnerVerwalten.GeschaeftspartnerAEndernEinstieg;
import GeschäftspartnerVerwalten.GeschaeftspartnerAnlegen;
import GeschäftspartnerVerwalten.GeschaeftspartnerAnzeigen;
import Interfaces.*;
import ZahlungskonditionenVerwalten.ZahlungskonditionAnlegen;
import ZahlungskonditionenVerwalten.ZahlungskonditionAnzeigen;
import ZahlungskonditionenVerwalten.ZahlungskonditionenAEndernEinstieg;
import javax.swing.JInternalFrame;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Luca
 */
public class Hauptmenue_User extends javax.swing.JInternalFrame implements InterfaceJTreeFunction {

    AuftragskopfAnlegen auftragskopfanlegen;
    AuftragskopfAendern auftragskopfaendern;
    AuftragskopfAnzeigen auftragskopfanzeigen;

    AuftragspositionAendern auftragsspositionaender;
    AuftragspositionAnzeigen auftragsspositionanzeigen;

    ArtikelAnlegen artikelanlegen;
    ArtikelAEndernEinstieg artikelaendern;
    ArtikelAnzeigen artikelanzeigen;

    GeschaeftspartnerAnlegen geschaeftspartneranlegen;
    GeschaeftspartnerAnzeigen geschaeftspartneranzeigen;
    GeschaeftspartnerAEndernEinstieg geschaeftspartneraendern;

    ZahlungskonditionAnlegen zahlungskonditionanlegen;
    ZahlungskonditionAnzeigen zahlungskonditionanzeigen;
    ZahlungskonditionenAEndernEinstieg zahlungskonditionaendern;
    
    
    
    
    
    /*
    Hilfsvariablen
    */
    DefaultMutableTreeNode node; // Speichervariable für den Wert des Blattes eines JTrees
    /**
     * Creates new form test
     */
    public Hauptmenue_User() {
        initComponents();
        auftragskopfanlegen = new AuftragskopfAnlegen();
        auftragskopfanzeigen = new AuftragskopfAnzeigen();
        auftragskopfaendern = new AuftragskopfAendern();
        auftragsspositionanzeigen = new AuftragspositionAnzeigen();
        auftragsspositionaender = new AuftragspositionAendern();
        artikelanlegen = new ArtikelAnlegen();
        artikelanzeigen = new ArtikelAnzeigen();
        artikelaendern = new ArtikelAEndernEinstieg();
        geschaeftspartneranlegen = new GeschaeftspartnerAnlegen();
        geschaeftspartneranzeigen = new GeschaeftspartnerAnzeigen();
        geschaeftspartneraendern = new GeschaeftspartnerAEndernEinstieg();
        zahlungskonditionanlegen = new ZahlungskonditionAnlegen();
        zahlungskonditionanzeigen = new ZahlungskonditionAnzeigen();
        zahlungskonditionaendern = new ZahlungskonditionenAEndernEinstieg();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Hauptmenu_jScrollPane = new javax.swing.JScrollPane();
        hauptmenueAdmin_Tree = new javax.swing.JTree();
        Logo_jLabel = new javax.swing.JLabel();
        Statuszeile_jTextField = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jToolBar1 = new javax.swing.JToolBar();
        jB_Zurueck = new javax.swing.JButton();
        jB_Abbrechen = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_Anzeigen = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();

        setResizable(true);
        setTitle("Hauptmenü");
        setVisible(true);

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
        hauptmenueAdmin_Tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        hauptmenueAdmin_Tree.setName("Navigation"); // NOI18N
        hauptmenueAdmin_Tree.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {
            public void treeCollapsed(javax.swing.event.TreeExpansionEvent evt) {
            }
            public void treeExpanded(javax.swing.event.TreeExpansionEvent evt) {
                hauptmenueAdmin_TreeTreeExpanded(evt);
            }
        });
        hauptmenueAdmin_Tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hauptmenueAdmin_TreeMouseClicked(evt);
            }
        });
        Hauptmenu_jScrollPane.setViewportView(hauptmenueAdmin_Tree);

        Logo_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logo_jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AdminHauptmenue/logo.PNG"))); // NOI18N

        Statuszeile_jTextField.setBackground(new java.awt.Color(240, 240, 240));
        Statuszeile_jTextField.setText("Statuszeile");
        Statuszeile_jTextField.setEnabled(false);

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        jB_Zurueck.setText("Zurück");
        jB_Zurueck.setEnabled(false);
        jToolBar1.add(jB_Zurueck);

        jB_Abbrechen.setText("Abbrechen");
        jToolBar1.add(jB_Abbrechen);

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
        jToolBar1.add(jB_Suchen);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Hauptmenu_jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Logo_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE))
            .addComponent(Statuszeile_jTextField)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Logo_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addComponent(Hauptmenu_jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Statuszeile_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hauptmenueAdmin_TreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hauptmenueAdmin_TreeMouseClicked
        node = (DefaultMutableTreeNode) hauptmenueAdmin_Tree.getLastSelectedPathComponent();
        //node =  hauptmenueAdmin_Tree.get;
        //TreePath Path = node.getNewLeadSelectionPath(); 
        if(evt.getClickCount() == 2){
            openJtreeNodes(node);
        }
//        openJtreeNodes(node);
        System.out.println(evt.getClickCount());
        System.out.println(node);
    }//GEN-LAST:event_hauptmenueAdmin_TreeMouseClicked

    private void hauptmenueAdmin_TreeTreeExpanded(javax.swing.event.TreeExpansionEvent evt) {//GEN-FIRST:event_hauptmenueAdmin_TreeTreeExpanded
        // TODO add your handling code here:
    }//GEN-LAST:event_hauptmenueAdmin_TreeTreeExpanded

    @Override
    public void openJtreeNodes(DefaultMutableTreeNode node) {
        if(node != null){
            if(node.equals("Auftrag verwalten")){
                if(node.equals("Auftragskopf")){
                    if(node.equals("Anlegen")){
                        auftragskopfanlegen.setVisible(true);
                    }else if(node.equals("Ändern")){
                        auftragskopfaendern.setVisible(true);
                    }else if(node.equals("Anzeigen")){
                        auftragskopfanzeigen.setVisible(true);
                    }
                }else if(node.equals("Auftragsposition")){
                    if(node.equals("Ändern")){
                        auftragsspositionaender.setVisible(true);
                    }else if(node.equals("Anzeigen")){
                        auftragsspositionanzeigen.setVisible(true);
                    }
                }
                    
            }else if(node.equals("Artikel verwalten")){
            }

    }
    }
     
    @Override
    public void setInternalFrameVisible(JInternalFrame frame){
//        if( frame.getName().equals("Auftragskopf"))
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Hauptmenu_jScrollPane;
    private javax.swing.JLabel Logo_jLabel;
    private javax.swing.JTextField Statuszeile_jTextField;
    private javax.swing.JTree hauptmenueAdmin_Tree;
    private javax.swing.JButton jB_Abbrechen;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
