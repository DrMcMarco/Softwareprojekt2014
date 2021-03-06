package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.Auftragskopf;
import DTO.Auftragsposition;
import Documents.*;
import Interfaces.*;
import JFrames.GUIFactory;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Luca Terrasi
 */
/* 10.12.2014 Terrasi, Dokumentation und Logik*/
/* 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button */
/* 06.01.2015 Terrasi, Anwendungslogik für das anzeigen der 
 Auftragspositionsmaske. */
/* 08.01.2015 Terrasi, Überarbeitung der Anwendungslogik und das hinzufügen
 *  von weiteren Funktion sowie die Suche.*/
/* 18.02.2015 Terrasi, getestet und freigegeben */
public class AuftragspositionAendern extends javax.swing.JInternalFrame
        implements SchnittstelleFensterFunktionen {

    // Speichervariablen
    Component letzteComponente;
    GUIFactory factory;
    AuftragspositionAnzeigen auftragspositionAnzeigen;
    SchnittstelleHauptfenster hauptFenster;

    private ArrayList<Component> fehlendeEingaben;// ArrayList für Eingabefelder

    // Konstanten für Farben    
    private final Color hintergrundfarbe = Color.WHITE;

    // Augabetexte für Meldungen
    final String FEHLER = "Fehler";
    private final String FEHLENDEEINGABEN = "Fehlende Eingaben";
    final String FEHLERMELDUNG_TITEL = "Fehlerhafte Eingabe";
    final String FEHLERMELDUNG_AUFTRAGSKOPFID_TEXT = "Bitte geben Sie alle "
            + "notwendigen Eingaben ein!";
    private final String FEHLERMELDUNG_STATUS_TITEL = "Abgeschlossener Auftrag";
    private final String FEHLERMMELDUNG_STATUSABGESCHLOSSEN_TEXT = "Der Auftrag ist "
            + "bereits abgeschlossen und kann nicht"
            + "\n bearbeitet werden. ";
    private final String AUFTRAG_NICHT_GEFUNDEN = "Keine passender Auftrag in der "
            + "Datenbank.";
    private final String KEINE_EINGABE = "Bitte geben Sie eine Auftragskopf-ID und die"
            + " Positionsnummer ein.";
    private final String KEINE_AUFTRAGSID = "Bitte geben Sie eine Auftragskopf-ID ein.";
    private final String KEINE_POSITIONSID = "Bitte geben Sie eine Positionsnummer"
            + " ein.";


    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 TER, Dokumentation und Logik. */
    /* 06.01.2015 TER, hinzufügen eines Interfaceobjektes
     und eines AuftragspositionAnzeigenobjektes.*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Konstruktor, Erzeugung eines Auftragspositionsobjektes.
     *
     * @param factory, Übergabe eines GUIFactoryobjektes.
     * @param positionAnzeigenView, Übergabe eines AutragspositionAnzeigen-
     * objektes.
     * @param mainView, Übergabe eines InterfaceMainViewobjektes.
     */
    public AuftragspositionAendern(GUIFactory factory,
            AuftragspositionAnzeigen positionAnzeigenView,
            SchnittstelleHauptfenster mainView) {
        initComponents();

        // Übergabe der Parameter.
        this.factory = factory;
        this.auftragspositionAnzeigen = positionAnzeigenView;
        this.hauptFenster = mainView;

        // Initialisierung der Speichervariblen
        fehlendeEingaben = new ArrayList<>();

        // Zuweisung der Documents an die Eingabefelder
        auftragskopfID_jTextField.setDocument(new UniversalDocument(
                "0123456789", false, 10));
        AuftragspositionID_jTextField.setDocument(new UniversalDocument(
                "0123456789", false, 10));
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
        AuftragskopfID_jLabel = new javax.swing.JLabel();
        auftragskopfID_jTextField = new javax.swing.JTextField();
        Weiter_jButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        AuftragspositionID_jTextField = new javax.swing.JTextField();

        setTitle("Auftragsposition ändern");
        setPreferredSize(new java.awt.Dimension(580, 300));
        setRequestFocusEnabled(false);
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        jB_Zurueck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Home2.PNG"))); // NOI18N
        jB_Zurueck.setToolTipText("Hauptmenü");
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
        jB_Anzeigen.setEnabled(false);
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Löschen.PNG"))); // NOI18N
        jB_Loeschen.setEnabled(false);
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Suche.PNG"))); // NOI18N
        jB_Suchen.setToolTipText("Allgemeine Suche");
        jB_Suchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuchenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Suchen);

        AuftragskopfID_jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        AuftragskopfID_jLabel.setLabelFor(auftragskopfID_jTextField);
        AuftragskopfID_jLabel.setText("Auftragskopf-ID      :");
        AuftragskopfID_jLabel.setToolTipText("");

        auftragskopfID_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                auftragskopfID_jTextFieldFocusGained(evt);
            }
        });
        auftragskopfID_jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                auftragskopfID_jTextFieldKeyPressed(evt);
            }
        });

        Weiter_jButton.setText("Weiter");
        Weiter_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Weiter_jButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setLabelFor(AuftragspositionID_jTextField);
        jLabel1.setText("Positionsnummer    :");

        AuftragspositionID_jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AuftragspositionID_jTextFieldFocusGained(evt);
            }
        });
        AuftragspositionID_jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AuftragspositionID_jTextFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AuftragskopfID_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(AuftragspositionID_jTextField))
                .addGap(18, 18, 18)
                .addComponent(Weiter_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AuftragskopfID_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(auftragskopfID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(AuftragspositionID_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Weiter_jButton))
                .addContainerGap(214, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, angelegt und Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Beim wählen des Eingabefeldes, wird alles selektiert.
     *
     * @param evt
     */
    private void auftragskopfID_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auftragskopfID_jTextFieldFocusGained
        //Setzen der Hintergrundsfarbe des Eingabefeldes
        auftragskopfID_jTextField.setBackground(hintergrundfarbe);
        auftragskopfID_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_auftragskopfID_jTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, angelegt und Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Beim wählen des Eingabefeldes, wird alles selektiert.
     *
     * @param evt
     */
    private void AuftragspositionID_jTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AuftragspositionID_jTextFieldFocusGained
        //Setzen der Hintergrundsfarbe des Eingabefeldes
        AuftragspositionID_jTextField.setBackground(hintergrundfarbe);
        AuftragspositionID_jTextField.selectAll();//Selektion des Eingabefeldes
    }//GEN-LAST:event_AuftragspositionID_jTextFieldFocusGained

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, angelegt und Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * ActionPerformed für den Weiter-Button.
     *
     * @param evt
     *
     */
    private void Weiter_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Weiter_jButtonActionPerformed
        // Klassenvariable
        final String auftragspositionaendern
                = "Auftragsposition ändern Einstieg";
        Auftragskopf aKopf;
//        Ueberpruefung, ob beide Felder leer sind
        if (!auftragskopfID_jTextField.getText().equals("")
                && !AuftragspositionID_jTextField.getText().equals("")) {
            // Überprfung, ob auch alle Eingaben getätigt worden sind.
            ueberpruefen();
            try {// Try-Block
                // Auftragskopf wird mit DAO-MEthode gerufen und initialisiert
                aKopf = GUIFactory.getDAO().
                        gibAuftragskopf(
                                Long.parseLong(
                                        auftragskopfID_jTextField.getText()));

                // Erzeugung eines Auftragspositionsobjektes mit der DAO-Methode
                // "gibAuftragsposition". Es werden dafür die Eingaben genutzt.
                Auftragsposition position = GUIFactory.getDAO().
                        gibAuftragsposition(Long.parseLong(
                                        auftragskopfID_jTextField.getText()),
                                Long.parseLong(
                                        AuftragspositionID_jTextField.
                                        getText()));
                if (position != null) {//Wird geprüft ob eine Position vorhanden ist

                    if (this.getTitle().equals(auftragspositionaendern)) {

                        // Überprüft anhand des Framestitels, ob es das nächste 
                        // Fenster im Anzeigen-/ oder im Ändernmodus anzeigen
                        // soll.
                        if (!(aKopf.getStatus().getStatus().
                                equals("abgeschlossen"))) {//Status des Auftrags 
                            // Setzt das Internalframe in den Ändernmodus.
                            this.auftragspositionAnzeigen.setzeStatusAender();
                            //Methode die bestimmte Eingabefelder leert
                            zuruecksetzen();
                            this.auftragspositionAnzeigen.setzeEingaben(
                                    position);
                            // Fenster wird nicht mehr sichtbar.
                            this.setVisible(false);
                            // Hauptfenster macht übergebene Maske sichtbar.
                            this.hauptFenster.setzeFrame(
                                    this.auftragspositionAnzeigen);
                        } else {
                            // Fehlermeldung als PopUp
                            JOptionPane.showMessageDialog(null,
                                    FEHLERMMELDUNG_STATUSABGESCHLOSSEN_TEXT,
                                    FEHLERMELDUNG_STATUS_TITEL,
                                    JOptionPane.WARNING_MESSAGE);
                            AuftragspositionID_jTextField.setText("");
                            auftragskopfID_jTextField.setText("");
                            auftragskopfID_jTextField.requestFocusInWindow();
                        }
                    } else {//Falls Titel gleich "Auftragsposition anzeigen Einstieg"

                        // Setzt das Internalframe in den Anzeigenmodus.
                        this.auftragspositionAnzeigen.setzeStatusAnzeigen();
                        //Methode die bestimmte Eingabefelder leert
                        zuruecksetzen();
                        this.auftragspositionAnzeigen.setzeEingaben(position);
                        // Fenster wird nicht mehr sichtbar.
                        this.setVisible(false);
                        // Hauptfenster macht übergebene Maske sichtbar.
                        this.hauptFenster.setzeFrame(
                                this.auftragspositionAnzeigen);
                    }

                }
            } catch (NumberFormatException e) {// Fehlerbehandlung.
                // Meldung wird an die Statuszeile übergeben.
                this.hauptFenster.setzeStatusMeldung(KEINE_EINGABE);
            } catch (ApplicationException e) {// Fehlerbehandlung.
                // Meldung wird an die Statuszeile übergeben
                this.hauptFenster.setzeStatusMeldung(AUFTRAG_NICHT_GEFUNDEN);
                // Eingabefelder erhalten leeren String
                auftragskopfID_jTextField.setText("");
                AuftragspositionID_jTextField.setText("");
            } catch (NullPointerException e) {// Fehlerbehandlung.
                // Fehlermeldung als PopUp
                this.hauptFenster.setzeStatusMeldung(FEHLENDEEINGABEN);
            }
        } else {
            // Ein oder beide Felder sind leer
            // Ueberpruefung welches Feld leer ist
            if (auftragskopfID_jTextField.getText().equals("")
                    && AuftragspositionID_jTextField.getText().equals("")) {
                //Beide Felder sind leer, Fehlermeldung
                this.hauptFenster.setzeStatusMeldung(KEINE_EINGABE);
            } else if (AuftragspositionID_jTextField.getText().equals("")) {
                // Auftragspoisitons-ID ist leer, entsprechende Fehlermeldung
                this.hauptFenster.setzeStatusMeldung(KEINE_POSITIONSID);
            } else {
                // Auftrags-ID ist leer, entsprechende Fehlermeldung
                this.hauptFenster.setzeStatusMeldung(KEINE_AUFTRAGSID);
            }
        }

    }//GEN-LAST:event_Weiter_jButtonActionPerformed


    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, Dokumentation und Logik*/
    /* 16.12.2014 Terrasi, Funktionsimplementierung im "Zurück"-Button */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        letzteComponente = null;   //Initialisierung der Componentspeichervariable
        // Erhalten über GUIFactorymethode die letzte aufgerufene View und 
        // speichern diese in Variable.
        letzteComponente = this.factory.zurueckButton();
        this.setVisible(false);// Internalframe wird nicht mehr dargestellt
        letzteComponente.setVisible(true);// Übergebene Component wird sichtbar gemacht
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, angelegt, Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode um mit dem Enter-Button die Weiter-Button Funktion zu betätigen.
     *
     * @param evt
     */
    private void AuftragspositionID_jTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AuftragspositionID_jTextFieldKeyPressed
        // Wird geprft ob die Entertste betätigt worden ist.
        if (evt.getKeyCode() == evt.VK_ENTER) {
            // Weiter_ActionPerformed wird ausgeführt.
            Weiter_jButtonActionPerformed(null);
        }
    }//GEN-LAST:event_AuftragspositionID_jTextFieldKeyPressed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 Terrasi, angelegt, Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode um mit dem Enter-Button die Weiter-Button Funktion zu betätigen.
     *
     * @param evt
     */
    private void auftragskopfID_jTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_auftragskopfID_jTextFieldKeyPressed
        // Wird geprft ob die Entertste betätigt worden ist.
        if (evt.getKeyCode() == evt.VK_ENTER) {
            // Weiter_ActionPerformed wird ausgeführt.
            Weiter_jButtonActionPerformed(null);
        }
    }//GEN-LAST:event_auftragskopfID_jTextFieldKeyPressed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 08.01.2015 TER, angelegt, Dokumentation und Logik. */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode um die Suche aufzurufen.
     *
     * @param evt
     */
    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        // Übergabe des Fenster an die "rufeSuche"-Methode.
        this.hauptFenster.rufeSuche(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der alle Eingabefelder zurückgesetzt werden
     */
    @Override
    public void zuruecksetzen() {
        //Eingabefelder erhalten einen leeren String
        auftragskopfID_jTextField.setText("");
        AuftragspositionID_jTextField.setText("");
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /*
     Schnittstellenmethode mit der geprüft wird ob alle Eingaben getätigt 
     worden sind.
     */
    @Override
    public void ueberpruefen() {
        // IF-Anweisungen mit denen geprüft wird welche Eingabefelder keine 
        // Eingabe erhalten haben. Diese Eingabefelder werden in der passende 
        // Speichervariable festgehalten.
        if (auftragskopfID_jTextField.getText().equals("")) {
            fehlendeEingaben.add(auftragskopfID_jTextField);
        } else if (AuftragspositionID_jTextField.getText().equals("")) {
            fehlendeEingaben.add(AuftragspositionID_jTextField);
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der die Eingaben beim FocusLost auf Richtigkeit
     * geprüft werden.
     *
     * @param textfield, das zu übergeben JTextfield, indem der Focusgesetzt
     * ist.
     * @param syntax, String mit dem eine Eingabe auf das richtige Format hin
     * geprüft wird.
     * @param fehlermelgungtitel, Srting der den Titel der Fehlmeldung enthält.
     * @param fehlermeldung, String der die Fehlmeldung enthält.
     */
    @Override
    public void ueberpruefungVonFocusLost(JTextField textfield, String syntax,
            String fehlermelgungtitel, String fehlermeldung) {
        if (!textfield.getText().matches(syntax)) {
            //Ausgabe einer Fehlermeldung
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
            //Mit dem Focus in das übergebene Eingabefeld springen
            textfield.requestFocusInWindow();
            textfield.selectAll();// Eingabefeld komplett auswählen.
        }
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode mit der die Eingabefelder die nicht ausgefüllt
     * worden sind, farblich markiert werden und eine Meldung ausgegeben wird,
     * inder der Benutzer darauf hingewiesen wird, alle Eingaben zu tätigen.
     *
     * @param list, Arraylist in der die Components die keine Eingaben erhalten
     * haben, gespeichert sind.
     * @param fehlermelgungtitel, Srting der den Titel der Fehlmeldung enthält.
     * @param fehlermeldung, String der die Fehlmeldung enthält.
     * @param farbe, Color in der der Hintergrund der Components markiert werden
     * soll
     */
    @Override
    public void fehlEingabenMarkierung(ArrayList<Component> list,
            String fehlermelgungtitel, String fehlermeldung, Color farbe) {
        //Meldung die darauf hinweist das nicht alle Eingaben getätigt worden sind.
        JOptionPane.showMessageDialog(null, fehlermeldung,
                fehlermelgungtitel, JOptionPane.WARNING_MESSAGE);
        if (!list.isEmpty()) {
            // Fokus gelangt in das erste leere Eingabefeld.
            list.get(0).requestFocusInWindow();
        }
        //ArrayList mit leeren Eingabefeldern für den Auftragskopf leeren.
        list.clear();
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man die Auftragskopf-ID erhält und diese im passendem
     * Feld dargestellt bekommt.
     *
     * @param auftragskopf, Auftragskopfobjekt wird übergeben
     */
    public void setzeAuftragskopfID_jTextField(Auftragskopf auftragskopf) {
        // Eingabefeld erhält ID vom Auftragskopfobjekt.
        this.auftragskopfID_jTextField.
                setText(String.valueOf(auftragskopf.getAuftragskopfID()));
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi, angelegt */
    /* 08.01.2015 Terrasi, Logik implementiert */
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der man die Positionsnummer erhält und diese im passendem
     * Feld dargestellt bekommt.
     *
     * @param position , Auftragskopfobjekt wird übergeben
     */
    public void setzeAuftragspositionID_jTextField(Auftragsposition position) {
        // Eingabefeld erhält Positionsnummer von Auftragsposition.
        this.AuftragspositionID_jTextField.
                setText(String.valueOf(position.getPositionsnummer()));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AuftragskopfID_jLabel;
    private javax.swing.JTextField AuftragspositionID_jTextField;
    private javax.swing.JButton Weiter_jButton;
    private javax.swing.JTextField auftragskopfID_jTextField;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
