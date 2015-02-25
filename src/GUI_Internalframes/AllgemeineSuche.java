/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.*;
import Interfaces.*;
import JFrames.GUIFactory;
import JFrames.Start;
import JFrames.StartAdmin;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Luca Terrasi 10.12.2014 Terrasi,Erstellung 12.01.2015 Terrasi,
 * Implementierung der Anwendungslogik
 */
public class AllgemeineSuche extends javax.swing.JInternalFrame 
    implements SchnittstelleFensterFunktionen {

    /**
     * Konstante absteigend.
     */
    private static final String ABSTEIGEND = "absteigend";
    /**
     * Konstante aufsteigend.
     */
    private static final String AUFSTEIGEND = "aufsteigend";
    
    /**
     * Variable für den String aus der Combobox für Suchkategorien.
     */
    private String suchKategorie;
    
    /**
     * Varible die die Sucheingabe speichert.
     */
    private String suchEingabe;
    
    /**
     * Collection in der die gefunden Ergebnisse der Suche gespeichert werden.
     */
    private Collection<?> suchErgebnis;
    
    /**
     * Legt die Sortierung fest.
     */
    private String sortierung;
    
    /**
     * Komponente.
     */
    private Component comp;
    
    /**
     * Das Hauptfenster.
     */
    private SchnittstelleHauptfenster hauptFenster;

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 12.01.2015 Terrasi, GuiFactoryübergeben und text für legende Übergeben.*/
    /*----------------------------------------------------------*/
    /**
     * Konstruktor, Suchfenster wird erzeugt.
     *
     * @param factory übergebene GuiFactory
     * @param mainView Interface
     */
    public AllgemeineSuche(GUIFactory factory, SchnittstelleHauptfenster mainView) {
        initComponents();
        suchErgebnis = new ArrayList<>(); //Initialisierung der Collection.
        try {
            this.hauptFenster = (Start) mainView;
            
        } catch (ClassCastException e) {
 
        }
        if (this.hauptFenster == null) {
            this.hauptFenster = (StartAdmin) mainView;
        }
        this.aufsteigend_jRadioButton.setSelected(true);
        this.absteigend_jRadioButton.setSelected(false);
        this.sortierung = AUFSTEIGEND;
        //Setze das Model für die Combobox
        Auswahl_jComboBox.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] {"Auftragskopf", "Auftragsposition", 
                    "Artikel", "Artikelkategorie", "Geschäftspartner", 
                    "Zahlungskondition", "Anschrift", "Status" }));
        
        this.buttonGroup1.add(this.aufsteigend_jRadioButton);
        this.buttonGroup1.add(this.absteigend_jRadioButton);

        // Variable erhält Wert aus der Combobox mit den Suchkategorien.
        suchKategorie = Auswahl_jComboBox.getSelectedItem().toString();
        //Übergebae der Legende durch Methode gibLegendeAusDB.
        legende_jTextArea.setText(gibLegendeAusDB(suchKategorie));
    }

    /**
     * Setzt das Fenster, das die Suche aufgerufen hat.
     * @param fenster Letztes Fenster.
     */
    public void setzeFenster(Component fenster) {
        this.comp = fenster;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        Button_jToolBar = new javax.swing.JToolBar();
        Zurück_jButton = new javax.swing.JButton();
        Anzeige_jButton = new javax.swing.JButton();
        sucheStarten_jButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        Auswahl_jLabel = new javax.swing.JLabel();
        Auswahl_jComboBox = new javax.swing.JComboBox();
        Suche_jLabel = new javax.swing.JLabel();
        aufsteigend_jRadioButton = new javax.swing.JRadioButton();
        absteigend_jRadioButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        legende_jTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        suchfeld_jTextField = new javax.swing.JTextArea();

        setTitle("Suchen");
        setVisible(true);

        Button_jToolBar.setFloatable(false);
        Button_jToolBar.setRollover(true);
        Button_jToolBar.setEnabled(false);

        Zurück_jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Zurück.PNG"))); // NOI18N
        Zurück_jButton.setFocusable(false);
        Zurück_jButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Zurück_jButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        Zurück_jButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Zurück_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Zurück_jButtonActionPerformed(evt);
            }
        });
        Button_jToolBar.add(Zurück_jButton);

        Anzeige_jButton.setEnabled(false);
        Anzeige_jButton.setFocusable(false);
        Anzeige_jButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Anzeige_jButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Button_jToolBar.add(Anzeige_jButton);

        sucheStarten_jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/DetailSuche.PNG"))); // NOI18N
        sucheStarten_jButton.setFocusable(false);
        sucheStarten_jButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sucheStarten_jButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sucheStarten_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucheStarten_jButtonActionPerformed(evt);
            }
        });
        Button_jToolBar.add(sucheStarten_jButton);

        jTextField1.setEnabled(false);

        Auswahl_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Auswahl_jLabel.setLabelFor(Auswahl_jComboBox);
        Auswahl_jLabel.setText("Suchkategorie:");

        Auswahl_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Auswahl_jComboBoxActionPerformed(evt);
            }
        });

        Suche_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Suche_jLabel.setLabelFor(suchfeld_jTextField);
        Suche_jLabel.setText("Sucheingabe:");

        aufsteigend_jRadioButton.setText("Auftsteigend");
        aufsteigend_jRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aufsteigend_jRadioButtonActionPerformed(evt);
            }
        });

        absteigend_jRadioButton.setText("Absteigend");
        absteigend_jRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                absteigend_jRadioButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Sortierung :");

        jScrollPane1.setEnabled(false);

        legende_jTextArea.setEditable(false);
        legende_jTextArea.setBackground(new java.awt.Color(240, 240, 240));
        legende_jTextArea.setColumns(20);
        legende_jTextArea.setRows(5);
        jScrollPane1.setViewportView(legende_jTextArea);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Legende :");

        suchfeld_jTextField.setColumns(20);
        suchfeld_jTextField.setRows(5);
        suchfeld_jTextField.setName("suchfeld_jTextField"); // NOI18N
        jScrollPane3.setViewportView(suchfeld_jTextField);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Button_jToolBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Auswahl_jLabel)
                            .addComponent(Suche_jLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Auswahl_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(absteigend_jRadioButton)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(aufsteigend_jRadioButton, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Button_jToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Auswahl_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Auswahl_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(aufsteigend_jRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(absteigend_jRadioButton))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(Suche_jLabel)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 16.12.2014 Terrasi, Implementierung der Logik.*/
    /*----------------------------------------------------------*/
    /**
     * Methode die aufgerufen wird beim wählen einer Suchkategorie. Je nach
     * Suchkategorie wird eine passende Legende im in der Maske angezeigt.
     *
     * @param evt ein Event
     */
    private void Auswahl_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Auswahl_jComboBoxActionPerformed
        // Variable erhält Wert aus der Combobox mit den Suchkategorien.
        suchKategorie = Auswahl_jComboBox.getSelectedItem().toString();
        
        // Aufruf der Methode und übergebn Rückgabewert an TextArea.
        legende_jTextArea.setText(gibLegendeAusDB(suchKategorie));
        
        this.suchfeld_jTextField.setText("");
    }//GEN-LAST:event_Auswahl_jComboBoxActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 16.12.2014 Terrasi,angelegt und Logik implementiert.*/
    /*----------------------------------------------------------*/
    /**
     * Methode die anhand der eingegebenen Suchkriterien eine Suche startet und
     * das Ergebnis in der Ergebnistabelle der Maske anzeigt.
     *
     * @param evt Ein Event
     */
    @SuppressWarnings("empty-statement")
    private void sucheStarten_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucheStarten_jButtonActionPerformed
        suchEingabe = suchfeld_jTextField.getText();
        Start framestart = null;
        StartAdmin framestartadmin = null;
        String s = "";
        try {
            //Prüfe ob die suche vom benutzer aufgerufen wurde
            framestart = (Start) this.hauptFenster;
            try {
                //Starte suchabfrage
                suchErgebnis = GUIFactory.getDAO().suchAbfrage(suchEingabe, 
                        suchKategorie, this.sortierung);
                //Setze das ergebnis in die detailsicht
                framestart.detailSuche.setzeDaten(suchErgebnis);
                //Setze die sucheingabe in die detailsicht
                framestart.detailSuche.setzeSucheingabe(suchEingabe, 
                        this.sortierung);
                //Setze die Tabelle
                framestart.detailSuche.setzeTabelle(suchKategorie);
                //Setze das fenster
                framestart.detailSuche.setzeFenster(framestart.getComponent());
                //Setze das fenster im meinframe
                framestart.setzeFrame(framestart.detailSuche);
                this.setVisible(false);
            } catch (Exception e) { //Fehlerbehandlung 
                JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", 
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (ClassCastException e) {
 
        }
        //Wenn die suche vom admin aufgerufen wird.
        if (framestart == null) {
            framestartadmin = (StartAdmin) this.hauptFenster;
            try {
                //Starte suchabfrage
                suchErgebnis = GUIFactory.getDAO().suchAbfrage(suchEingabe, 
                        suchKategorie, this.sortierung);
                //Setze das ergebnis in die detailsicht
                framestartadmin.detailSuche.setzeDaten(suchErgebnis);
                //Setze die sucheingabe in die detailsicht
                framestartadmin.detailSuche.setzeSucheingabe(suchEingabe, 
                        this.sortierung);
                //Setze die Tabelle
                framestartadmin.detailSuche.setzeTabelle(suchKategorie);
                //Setze das fenster im meinframe
                framestartadmin.setzeFrame(framestartadmin.detailSuche);
                this.setVisible(false);
            } catch (Exception e) { //Fehlerbehandlung 
                JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", 
                        JOptionPane.WARNING_MESSAGE);
            }
        }

        
        
    }//GEN-LAST:event_sucheStarten_jButtonActionPerformed

    /**
     * Setzt die Sortierung auf Aufsteigend.
     * @param evt ein Event.
     */
    private void aufsteigend_jRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aufsteigend_jRadioButtonActionPerformed
        //Setze Sortierung auf Aufsteigend.
        this.sortierung = AUFSTEIGEND;

    }//GEN-LAST:event_aufsteigend_jRadioButtonActionPerformed

    /**
     * Setzt die Sortierung auf Absteigend.
     * @param evt ein Event.
     */
    private void absteigend_jRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_absteigend_jRadioButtonActionPerformed
        //Setzt die Sortierung auf Absteigend.
        this.sortierung = ABSTEIGEND;
    }//GEN-LAST:event_absteigend_jRadioButtonActionPerformed

    /**
     * Schließt die Suche.
     * @param evt Ein Event.
     */
    private void Zurück_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Zurück_jButtonActionPerformed
        this.setVisible(false);
        this.suchfeld_jTextField.setText("");
    }//GEN-LAST:event_Zurück_jButtonActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt */
    /* 12.12.2015 Terrasi, Implementierung der Logik.*/
    /*----------------------------------------------------------*/
    /**
     * Methode der man eine Suchkategorie übergibt und 
     * die passenden Suchparameter
     * herauffindet und diese als String wieder zurückgibt.
     *
     * @param suchkategorie drie übergebene Suchkategorie
     * @return String mit den aus der Datenbank gespeicherten Suchparametern.
     */
    public final String gibLegendeAusDB(String suchkategorie) {
        //Methodenvariable die die Suchparameter speichert.
        String parameter = "";
        // Variable die den Text enthält,
        String ausgabelegende = "";
        int count = 0;
        // der als Legende in der Maske ausgegeben wird.
        
        //Try-Block falls eine ApplicationExeption auftritt.
        try {
            
            //Erzeugung eines Iterator um alle Werte der Collection zu erhalten.
            Iterator<String> it = GUIFactory.getDAO()
                    .gibSuchAttribute(suchkategorie).iterator();
            while (it.hasNext()) { //Schleife für das durchinterieren.
                //Übergabe jedes einzelnen Wertes.
                parameter += it.next() + ", ";
                if (count == 5) {
                    parameter += "\n";
                    count = 0;
                }
                count++;
            }
            // Speichervariabel erhält Text für die Legende und 
            //die passenden Suchparameter.
            ausgabelegende = "Suchbefehl für " + suchKategorie + ":\n\n"
                    + parameter + "\n"
                    + "Vergleichsoperatoren: =>  <=  <>  =  <  >  \n\n"
                    + "Wildcards: ? * für die Textsuche. \n\n"
                    + "Sortiert wird nach dem ersten Literal "
                    + "oben im Suchfeld.\n\n"
                    + "Alle Suchabfragen sind durch  ;  zu trennen.";
        
        } catch (ApplicationException | NullPointerException e) {
            //Fehlerbehandlung 
            //einer ApplikationException oder einer NullpointerException
            JOptionPane.showMessageDialog(null, "Die Suchattribute sind nicht"
                    + "vorhanden!", 
                            "Fehler", JOptionPane.WARNING_MESSAGE);
            
        }
        //Rückgabe eines Strings.
        return ausgabelegende;
    }
    
    /**
     * Setzt die Auswahl der Tabellen, je nachdem von wo aus die Suche 
     * aufgerufen wird.
     * @param tabellen Tabellen
     */
    public void setzeModelCombobox(String[] tabellen) {
        this.Auswahl_jComboBox.setModel(new javax.swing.DefaultComboBoxModel(
                tabellen));
        this.Auswahl_jComboBox.setSelectedIndex(0);
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 12.01.2015 Terrasi, Implementierung */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode um alle eingabefelder zurück zu setzen.
     */
    @Override
    public void zuruecksetzen() {
        suchfeld_jTextField.setText("");
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 12.01.2015 Terrasi, Implementierung */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der auf vollständigkeit 
     * der Eingaben geprüft wird.
     */
    @Override
    public void ueberpruefen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 12.01.2015 Terrasi, Implementierung */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der die Richtigkeit der Eingabe
     * beim Focuslost geprüft wird.
     * 
     * @param textfield das zu übergeben JTextfield, indem der Focusgesetzt
     * ist.
     * @param syntax String mit dem eine Eingabe auf das richtige Format hin
     * geprüft wird.
     * @param fehlermelgungtitel Srting der den Titel der Fehlmeldung enthält.
     * @param fehlermeldung String der die Fehlmeldung enthält.
     */
    @Override
    public void ueberpruefungVonFocusLost(JTextField textfield, 
                String syntax, String fehlermelgungtitel, 
                String fehlermeldung) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 12.01.2015 Terrasi, Implementierung */
    /*----------------------------------------------------------*/
    /**
     * Methode mir der die Eingabefelder ohne einer entsprechenden Eingabe,
     * farblich markiert werden und eine Nachricht aufruft, die den Benutzer
     * darauf hinweist alle Eingaben zu tätigen.
     *
     * @param list Arraylist in der die Components die keine Eingaben erhalten
     * haben, gespeichert sind.
     * @param fehlermelgungtitel Srting der den Titel der Fehlmeldung enthält.
     * @param fehlermeldung String der die Fehlmeldung enthält.
     * @param farbe Color in der der Hintergrund der Components markiert werden
     * soll
     */
    @Override
    public void fehlEingabenMarkierung(ArrayList<Component> list, 
                String fehlermelgungtitel, String fehlermeldung, Color farbe) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Anzeige_jButton;
    private javax.swing.JComboBox Auswahl_jComboBox;
    private javax.swing.JLabel Auswahl_jLabel;
    private javax.swing.JToolBar Button_jToolBar;
    private javax.swing.JLabel Suche_jLabel;
    private javax.swing.JButton Zurück_jButton;
    private javax.swing.JRadioButton absteigend_jRadioButton;
    private javax.swing.JRadioButton aufsteigend_jRadioButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextArea legende_jTextArea;
    private javax.swing.JButton sucheStarten_jButton;
    private javax.swing.JTextArea suchfeld_jTextField;
    // End of variables declaration//GEN-END:variables
}
