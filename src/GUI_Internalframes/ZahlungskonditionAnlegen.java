package GUI_Internalframes;

import DAO.ApplicationException;
import DAO.DataAccessObject;
import DTO.Zahlungskondition;
import JFrames.GUIFactory;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import Interfaces.SchnittstelleHauptfenster;
import Interfaces.SchnittstelleFensterFunktionen;

/**
 * GUI Klasse für Zahlungskontion (ZK) verwalten. Diese Klasse beinhaltet alle
 * Methoden, die benötigt werden, um eine ZK anzulegen, eine angelegte ZK zu
 * ändern sowie eine angelegte ZK anzeigen zu lassen. Je nach dem von welchem
 * Button aus diese Klasse aufgerufen wird, passt sie sich entsprechend an und
 * ändert sich zum Beispiel in die Sicht ZK ändern. Falls der Button ZK anlegen
 * betätigt wird, ändert sich die Sicht in ZK anlegen.
 *
 * @author Tahir
 */
public class ZahlungskonditionAnlegen extends javax.swing.JInternalFrame implements SchnittstelleFensterFunktionen {

    /**
     * Variable, die für die Navigation benoetigt wird, in ihr wird gespeichert,
     * welche View zuletzt geöffnet war.
     */
    private Component c;
    /**
     * Varibale für die GUI Factory.
     */
    private GUIFactory factory;
    /**
     * Variable für die DAO.
     */
    private DataAccessObject dao;
    /**
     * Variable für das Start Fenster. Es kann sein, dass sich ein Admin
     * anmeldet, dann waere unser StartFenster die StartAdmin. Falls sich ein
     * User anmeldet, ist unser StartFenster Start.
     */
    private SchnittstelleHauptfenster hauptFenster;
    /**
     * Varibale fue die ZK nummer.
     */
    private int zknummer;
    /**
     * Arraylist, um fehlerhafte Componenten zu speichern.
     */
    private ArrayList<Component> fehlerhafteComponenten;
    /**
     * Varible, die die Standard Farbe eine Combobox beinhaltet.
     */
    private final Color JCB_FARBE_STANDARD = new Color(214, 217, 223);
    /**
     * Variable, die die Standard Farbe eines Textfieldes beinhaltet.
     */
    private final Color JTF_FARBE_STANDARD = new Color(255, 255, 255);
    /**
     * Varibale, die die Farbe fuer fehlerhafte Componenten beinhaltet.
     */
    private final Color FARBE_FEHLERHAFT = Color.YELLOW;
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String TITEL_PFLICHTFELDER = "Pflichtfelder nicht ausgefüllt";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String MELDUNG_PFLICHTFELDER = "Einige Pflichtfelder wurden"
            + " nicht ausgefüllt.\nBitte füllen Sie diese aus!";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String MELDUNG_AENDERUNGEN_SPEICHERN = "Möchten Sie die"
            + " Änderungen speichern?";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String MELDUNG_SKONTO_MAHNZEITEN = "Bitte überprüfen Sie die"
            + " Skonto- und Mahnzeiten.\nDiese müssen aufsteigend sein!";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String TITEL_SKONTO_MAHNZEITEN = "Skonto- und/oder"
            + " Mahnzeiten fehlerhaft";
    /**
     * Varibalen fuer die Meldungen.
     */
    private final String FEHLER = "Fehler";
    /**
     * Varibale fuer die Statuszeile.
     */
    private String statuszeile;
    /**
     * Number Formatter wird benoetigt fuer das Parsen der Eingaben, sowie das
     * Anzeigen von Preisen.
     */
    private NumberFormat nf;
    /**
     * Varibale, die die maximale Anzahl von fehlerhaften Componenten
     * beinhaltet.
     */
    private final int anzahlFehlerhafterComponenten = 3;
    /**
     * Variablen für den Titel der jeweiligen Sicht.
     */
    private final String ZK_ANLEGEN = "Zahlungskondition anlegen";
    /**
     * Variablen für den Titel der jeweiligen Sicht.
     */
    private final String ZK_AENDERN = "Zahlungskondition ändern";
    /**
     * Variablen für den Titel der jeweiligen Sicht.
     */
    private final String ZK_ANZEIGEN = "Zahlungskondition anzeigen";
    /**
     * Variablen für die Auftragsart.
     */
    private final String TERMINAUFTRAG = "Terminauftrag";
    /**
     * Variablen für die Auftragsart.
     */
    private final String SOFORTAUFTRAG = "Sofortauftrag";
    /**
     * Variablen für die Auftragsart.
     */
    private final String BESTELLAUFTRAG = "Bestellauftrag";

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     *
     * @param factory beinhaltet das factory Obejekt
     * @param mainView beinhaltet das Objekt des StartFenster
     */
    public ZahlungskonditionAnlegen(GUIFactory factory,
            SchnittstelleHauptfenster mainView) {
        initComponents();
        this.hauptFenster = mainView;
        this.nf = NumberFormat.getInstance();
        this.factory = factory;
        this.dao = GUIFactory.getDAO();
        this.fehlerhafteComponenten = new ArrayList<>();
    }


    /*
     * Historie:
     * 05.12.2014   Sen     angelegt
     * 15.12.2014   Sen     ueberarbeitet
     */
    /**
     * Methode für die Überprüfung der Daten. Falls ein Textfeld nicht gefüllt
     * ist, wird sie der ArrayList für fehlerhafte Componenten hinzugefuegt.
     * Falls bei eine Compobox der selektierte Index auf 0 ("Bitte auswählen")
     * steht, wird diese ebenfalls in die ArrayList uebernommen
     */
    @Override
    public void ueberpruefen() {
//      Falls die Auftragsart nicht ausgewaehlt ist, wird die Componente in
//      ArrayList fehlerhafteComponenten hinzugefuegt und so weiter.  
        if (jCB_Auftragsart.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Auftragsart);
        }
        if (jCB_Skonto1.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Skonto1);
        }
        if (jCB_Skonto2.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Skonto2);
        }
    }


    /*
     * Historie:
     * 05.12.2014   Sen     angelegt
     * 17.12.2014   Sen     ueberarbeitet
     * 18.01.2015   Sen     ueberarbeitet
     */
    /**
     * Schnittstellen Methode, die die Eingaben zurücksetzt, beim Zurücksetzen
     * wird auch die Hintergrundfarbe zurückgesetzt.
     */
    @Override
    public final void zuruecksetzen() {
        try {
//          Die naechste freie ZK ID wird aus der Datenbank geladen und die 
//          Felder in den Anfangszustand zurueckgesetzt.  
            zknummer = this.dao.gibNaechsteZahlungskonditionID();
            jTF_ZahlungskonditionID.requestFocus();
            jTF_ZahlungskonditionID.setText("" + zknummer);
            jCB_Auftragsart.setSelectedIndex(0);
            jCB_Auftragsart.setBackground(JCB_FARBE_STANDARD);
            jSP_LieferzeitSOFORT.setValue(1);
            jSP_SperrzeitWUNSCH.setValue(1);
            jSP_Skontozeit1.setValue(1);
            jSP_Skontozeit2.setValue(2);
            jCB_Skonto1.setSelectedIndex(0);
            jCB_Skonto1.setBackground(JCB_FARBE_STANDARD);
            jCB_Skonto2.setSelectedIndex(0);
            jCB_Skonto2.setBackground(JCB_FARBE_STANDARD);
            jSP_Mahnzeit1.setValue(1);
            jSP_Mahnzeit2.setValue(2);
            jSP_Mahnzeit3.setValue(3);

            fehlerhafteComponenten.clear();
        } catch (NullPointerException e) {
        }
    }

    /*
     * Historie:
     * 05.12.2014   Sen     angelegt
     */
    /**
     * Schnittstellen Methode, die bei focusloost ueberprueft, ob Eingaben
     * korrekt sind Da in dieser Klasse diese Methode keine Funktion hat, musste
     * ich sie als Deprecated deklarieren. Sie wird nicht benutzt, da keine
     * Textfield implementiert wurden. Falls aber das Programm erweitert wird
     * und textfield spaeter in diese Klasse hinzukommen, kann man diese Methode
     * mit Leben fuellen.
     *
     * @param textfield textfield, von wo die Daten geprueft werden sollen
     * @param syntax Pruefsyntax(Regulaerer Ausdruck)
     * @param fehlermelgungtitel Titel der Fehlermeldung, die erzugt wird
     * @param fehlermeldung Text der Fehlermeldung
     */
    @Override
    public void ueberpruefungVonFocusLost(JTextField textfield,
            String syntax, String fehlermelgungtitel, String fehlermeldung) {
//            Prüfung, ob die Eingabe mit der Synstax passen
        if (!textfield.getText().matches(syntax)) {
//            Eingabe nicht korrekt, also wird eine Fehlermeldung ausgegeben      
            JOptionPane.showMessageDialog(null, fehlermeldung,
                    fehlermelgungtitel, JOptionPane.ERROR_MESSAGE);
//            Focus wird auf das Feld für die Eingabe des Einzelwertes
//            gesetzt und der Eingabefeld wird auf leer gesetzt.
            textfield.requestFocusInWindow();
            textfield.setText("");
        } else if (!textfield.getText().equals("")) {
//            Eingabe ist ok, nun Pruefung ob etwas im Feld steht, 
//            falls ja wird Hitnergrund auf normal gestzt
            textfield.setBackground(JTF_FARBE_STANDARD);
        }
    }

    /*
     * Historie:
     * 05.12.2014   Sen     angelegt
     */
    /**
     * Schnittstellen Methode, die am Ende der Speichern Aktion aufgerufen wird.
     * Aufgabe ist es, den Hintergrund der jeweiligen Componenten der List in
     * die uebergebene Farbe zu setzen.
     *
     * @param list liste mit den Componenten, wo der Hintergrund gefaerbt werden
     * soll
     * @param fehlermelgungtitel Titel der Fehlermeldung, dass nicht alle
     * eingaben gemacht wurden
     * @param fehlermeldung Text der Meldung
     * @param farbe Farbe, in die die Hintergruende gefaerbt werden sollen
     */
    @Override
    public void fehlEingabenMarkierung(ArrayList<Component> list,
            String fehlermelgungtitel, String fehlermeldung, Color farbe) {
//          fehlerhafteComponenten ist nicht leer (es sind fehlerhafte
//          Componenten vorhanden)
//          eine Meldung wird ausgegeben  
        JOptionPane.showMessageDialog(null, fehlermeldung, fehlermelgungtitel,
                JOptionPane.WARNING_MESSAGE);
//          an die erste fehlerhafte Componenten wird der Focus gesetzt  
        list.get(0).requestFocusInWindow();
//          ueber die fehlerhaften Komponenten wird iteriert und bei allen
//          fehlerhaften Componenten wird der Hintergrund in der fehlerhaften 
//          Farbe gefaerbt 
        for (int i = 0; i <= list.size() - 1; i++) {
            list.get(i).setBackground(farbe);
        }
//          ArrayList fue fehlerhafte Componenten wird geleert
        list.clear();
    }


    /*
     * Historie:
     * 10.12.2014   Sen     angelegt
     */
    /**
     * Methode, die beim Schließen des Fenster aufgerufen wird.
     */
    private void beendenEingabeNachfrage() {
//        varibablen fuer Titel und Text der erzeugten Meldung
        String meldung = "Möchten Sie die Eingaben verwerfen?";
        String titel = "Eingaben verwerfen";
//        Falls Titel des Fensters Artikel anlegen oder Artikel
//        aendern ist wird eine Nachfrage gemacht  
        if (this.getTitle().equals(ZK_ANLEGEN)) {
//            zunaechst wird ueberprueft, ob alle Eingaben getaetigt wurden
            ueberpruefen();
//            Falls anzahl fehlerhafter Componenten kleiner ist als max Anzahl
//            von fehlerhaften Componenten
//            heist dass, dass Eingaben getaetigt wurden            
            if (fehlerhafteComponenten.size() < anzahlFehlerhafterComponenten) {
                int antwort = JOptionPane.showConfirmDialog(null, meldung,
                        titel, JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (antwort == JOptionPane.YES_OPTION) {
                    this.setVisible(false);
                    zurueckInsHauptmenue();
                } else {
                    fehlerhafteComponenten.clear();
                }
//            keine Eingaben getaetigt, direkt Fenster schließen
            } else {
                this.setVisible(false);
                zurueckInsHauptmenue();
            }
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
        jB_AnzeigenAEndern = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jTF_ZahlungskonditionID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jCB_Auftragsart = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jCB_Skonto1 = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jCB_Skonto2 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jSP_Skontozeit1 = new javax.swing.JSpinner();
        jSP_Skontozeit2 = new javax.swing.JSpinner();
        jSP_Mahnzeit1 = new javax.swing.JSpinner();
        jSP_Mahnzeit2 = new javax.swing.JSpinner();
        jSP_Mahnzeit3 = new javax.swing.JSpinner();
        jSP_LieferzeitSOFORT = new javax.swing.JSpinner();
        jSP_SperrzeitWUNSCH = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Zahlungskonditonen anlegen");
        setPreferredSize(new java.awt.Dimension(460, 650));
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
        jB_Zurueck.setToolTipText("Hauptmenü");
        jB_Zurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ZurueckActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Zurueck);

        jB_Speichern.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Speichern.PNG"))); // NOI18N
        jB_Speichern.setToolTipText("Speichern");
        jB_Speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SpeichernActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Speichern);

        jB_AnzeigenAEndern.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Bearbeiten.PNG"))); // NOI18N
        jB_AnzeigenAEndern.setToolTipText("Bearbeiten");
        jB_AnzeigenAEndern.setActionCommand("Anzeigen/Ändern");
        jB_AnzeigenAEndern.setEnabled(false);
        jB_AnzeigenAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_AnzeigenAEndernActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_AnzeigenAEndern);

        jB_Loeschen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Löschen.PNG"))); // NOI18N
        jB_Loeschen.setToolTipText("Löschen");
        jB_Loeschen.setEnabled(false);
        jB_Loeschen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_LoeschenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Loeschen);

        jB_Suchen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Suche.PNG"))); // NOI18N
        jB_Suchen.setToolTipText("Allgemeine Suche");
        jB_Suchen.setEnabled(false);
        jB_Suchen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuchenActionPerformed(evt);
            }
        });
        jToolBar1.add(jB_Suchen);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Auftragsart*:");

        jTF_ZahlungskonditionID.setText("1");
        jTF_ZahlungskonditionID.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Lieferzeit SOFORT*:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Skontozeit 2*:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Mahnzeit 1*:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Skontozeit 1*:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Skonto 1*:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Skonto 2*:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Zahlungskondition-ID:");

        jCB_Auftragsart.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "Sofortauftrag", "Terminauftrag", "Bestellauftrag" }));
        jCB_Auftragsart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_AuftragsartActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Sperrzeit WUNSCH*:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Mahnzeit 2*:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Mahnzeit 3*:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Tage");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Tage");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Tage");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Tage");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Tage");

        jCB_Skonto1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "1,0", "2,0", "3,0" }));
        jCB_Skonto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_Skonto1ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("%");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("%");

        jCB_Skonto2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "1,0", "2,0", "3,0" }));
        jCB_Skonto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_Skonto2ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Skontodaten:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Mahnzeiten:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Allgemeine Daten:");

        jSP_Skontozeit1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        jSP_Skontozeit1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Skontozeit1PropertyChange(evt);
            }
        });

        jSP_Skontozeit2.setModel(new javax.swing.SpinnerNumberModel(2, 1, 31, 1));
        jSP_Skontozeit2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Skontozeit2PropertyChange(evt);
            }
        });

        jSP_Mahnzeit1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        jSP_Mahnzeit1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Mahnzeit1PropertyChange(evt);
            }
        });

        jSP_Mahnzeit2.setModel(new javax.swing.SpinnerNumberModel(2, 1, 31, 1));
        jSP_Mahnzeit2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Mahnzeit2PropertyChange(evt);
            }
        });

        jSP_Mahnzeit3.setModel(new javax.swing.SpinnerNumberModel(3, 1, 31, 1));
        jSP_Mahnzeit3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSP_Mahnzeit3PropertyChange(evt);
            }
        });

        jSP_LieferzeitSOFORT.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        jSP_SperrzeitWUNSCH.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Tage");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText("Tage");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel24.setText("*Pflichtfelder");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jSP_LieferzeitSOFORT, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel22))
                        .addComponent(jLabel24)
                        .addComponent(jLabel20)
                        .addComponent(jLabel21)
                        .addComponent(jLabel19)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(21, 21, 21)
                            .addComponent(jSP_SperrzeitWUNSCH, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel23))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel1))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jCB_Auftragsart, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTF_ZahlungskonditionID, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel3)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel4)
                                .addComponent(jLabel11))
                            .addGap(52, 52, 52)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jSP_Mahnzeit3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel16))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jSP_Mahnzeit1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel14))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jCB_Skonto2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jCB_Skonto1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel17))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jSP_Skontozeit2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel13))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jSP_Skontozeit1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel12))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jSP_Mahnzeit2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel15))))))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTF_ZahlungskonditionID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCB_Auftragsart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSP_LieferzeitSOFORT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jSP_SperrzeitWUNSCH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jSP_Skontozeit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSP_Skontozeit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jCB_Skonto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jCB_Skonto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jSP_Mahnzeit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jSP_Mahnzeit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jSP_Mahnzeit3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addContainerGap(173, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */

    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     *
     * @param evt
     */
    private void jCB_AuftragsartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_AuftragsartActionPerformed
//      Falls der Titel des Fenster ZK Anzeigen ist, werden beide Eingabe
//      nicht aktiviert.  
        if (this.getTitle().equals(ZK_ANZEIGEN)) {
            jSP_SperrzeitWUNSCH.setEnabled(false);
            jSP_LieferzeitSOFORT.setEnabled(false);
        } else {
//          Falls Titel ZK Anlegen oder ZK Aendern wird der Hintergrund 
//          der Combobox der Auftragsart in normal gefaerbt, falls Eingabe
//          gemacht wurde.  
            if (jCB_Auftragsart.getSelectedIndex() != 0) {
                jCB_Auftragsart.setBackground(JCB_FARBE_STANDARD);
            }
//          Wenn Terminauftrag ausgeawhlt, wird Lieferzeit SOFORT nicht 
//          aktiviert.  
            if (((String) jCB_Auftragsart.getSelectedItem())
                    .equals(TERMINAUFTRAG)) {
                jSP_LieferzeitSOFORT.setEnabled(false);
                jSP_LieferzeitSOFORT.setValue(1);
                jSP_SperrzeitWUNSCH.setEnabled(true);
            } else if (((String) jCB_Auftragsart.getSelectedItem())
                    .equals(SOFORTAUFTRAG)) {
//          Wenn Sofortauftrag ausgeawhlt, wird Sperrzeit WUNSCH nicht 
//          aktiviert.                 
                jSP_SperrzeitWUNSCH.setEnabled(false);
                jSP_SperrzeitWUNSCH.setValue(1);
                jSP_LieferzeitSOFORT.setEnabled(true);
            } else if (((String) jCB_Auftragsart.getSelectedItem())
                    .equals(BESTELLAUFTRAG)) {
//          Wenn Bestellauftrag ausgeawhlt, wird Lieferzeit Sofort und
//          Sperrzeit WUNSCH nicht aktiviert.                 
                jSP_SperrzeitWUNSCH.setEnabled(false);
                jSP_SperrzeitWUNSCH.setValue(1);
                jSP_LieferzeitSOFORT.setEnabled(false);
                jSP_LieferzeitSOFORT.setValue(1);
            } else {
                jSP_SperrzeitWUNSCH.setEnabled(true);
                jSP_SperrzeitWUNSCH.setValue(1);
                jSP_LieferzeitSOFORT.setEnabled(true);
                jSP_LieferzeitSOFORT.setValue(1);
            }
        }
    }//GEN-LAST:event_jCB_AuftragsartActionPerformed
    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */

    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     *
     * @param evt
     */
    private void jSP_Skontozeit1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Skontozeit1PropertyChange
        jSP_Skontozeit1.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Skontozeit1PropertyChange
    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */

    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     *
     * @param evt
     */
    private void jSP_Skontozeit2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Skontozeit2PropertyChange
        jSP_Skontozeit2.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Skontozeit2PropertyChange
    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */

    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     *
     * @param evt
     */
    private void jCB_Skonto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_Skonto1ActionPerformed
        if (jCB_Skonto1.getSelectedIndex() != 0) {
            jCB_Skonto1.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_Skonto1ActionPerformed
    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */

    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     *
     * @param evt
     */
    private void jCB_Skonto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_Skonto2ActionPerformed
        if (jCB_Skonto2.getSelectedIndex() != 0) {
            jCB_Skonto2.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_Skonto2ActionPerformed
    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */

    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     *
     * @param evt
     */
    private void jSP_Mahnzeit1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Mahnzeit1PropertyChange
        jSP_Mahnzeit1.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Mahnzeit1PropertyChange
    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */

    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     *
     * @param evt
     */
    private void jSP_Mahnzeit2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Mahnzeit2PropertyChange
        jSP_Mahnzeit2.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Mahnzeit2PropertyChange
    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */

    /**
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund
     * in standard gefärbt.
     *
     * @param evt
     */
    private void jSP_Mahnzeit3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSP_Mahnzeit3PropertyChange
        jSP_Mahnzeit3.setBackground(JTF_FARBE_STANDARD);
    }//GEN-LAST:event_jSP_Mahnzeit3PropertyChange
    /*
     * Historie:
     * 21.12.2014   Sen     angelegt
     * 02.01.2015   Sen     Funtkion fuer Sicht Artikel anlegen implementiert
     * 10.01.2015   Sen     Funtkion fuer Sicht Artikel aendern implementiert
     */

    /**
     * Methode für das Speichern der Daten. Falls die Sicht Aritkel anlegen
     * geoeffnet ist, wird eine Artikel angelegt. Ist Sicht Artikel aendern
     * geoffnet, wird der Artikel ueberschieben
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed
//      Variablen fuer die Meldung und den Titel
        String meldung = "Liefer- und Sperrzeit sowie Skonto- und Mahnzeiten"
                + " sind alle standardmäßig auf 1 Tag gesetzt."
                + "\nMöchten Sie nun speichern?"
                + " Klicken Sie auf Nein, wenn Sie die Eingaben bearbeiten"
                + " möchten.";
        String titel = "Zahlungskondititon speichern";
//      zunaechst werdne die Eingaben ueberprueft.    
        ueberpruefen();
//      falls fehlerhafteComponenten leer ist
//      (es sind keine fehlerhaften Componenten verfuegbar), 
//      werden die Eingaben in die entsprechenden Variablen gespeichert
        if (fehlerhafteComponenten.isEmpty()) {
            String auftragsart = (String) jCB_Auftragsart.getSelectedItem();
            int lieferzeitSOFORT;
            int sperrzeitWunsch;
            int skontozeit1;
            int skontozeit2;
            double skonto1;
            double skonto2;
            int mahnzeit1;
            int mahnzeit2;
            int mahnzeit3;
            long zknr;

            try {
//                einzel- und bestelltwert sowie die mwst und die bestandsmenge
//                muessen geparst werden, da die dao Methode int und double
//                Typen moechte Skonto und Mahnzeiten aus jSpinner mussen
//                geholt werdne
                zknr = nf.parse(jTF_ZahlungskonditionID.getText()).longValue();
                lieferzeitSOFORT = ((Number) jSP_LieferzeitSOFORT.getValue())
                        .intValue();
                sperrzeitWunsch = ((Number) jSP_SperrzeitWUNSCH.getValue())
                        .intValue();
                skonto1 = nf.parse((String) jCB_Skonto1.getSelectedItem())
                        .doubleValue();
                skonto2 = nf.parse((String) jCB_Skonto2.getSelectedItem())
                        .doubleValue();
                skontozeit1 = ((Number) jSP_Skontozeit1.getValue()).intValue();
                skontozeit2 = ((Number) jSP_Skontozeit2.getValue()).intValue();
                mahnzeit1 = ((Number) jSP_Mahnzeit1.getValue()).intValue();
                mahnzeit2 = ((Number) jSP_Mahnzeit2.getValue()).intValue();
                mahnzeit3 = ((Number) jSP_Mahnzeit3.getValue()).intValue();
//              Ueberpruefung, ob Mahnzeiten stimmen  
                if (mahnzeit3 > mahnzeit2 && mahnzeit2 > mahnzeit1
                        && skontozeit2 > skontozeit1) {
//                  Ueberpruefung in welche Sicht wir sind
                    if (this.getTitle().equals(ZK_ANLEGEN)) {
//                      Sicht ZK anlegen
//                      pruefung, ob irgendein Mahn oder skontozeit veraendert
//                      wurde, das wuede bedeuten
//                      dass der benutzer das Aendern der Mahn Skontozeiten 
//                      nicht vergessen hat
                        if (lieferzeitSOFORT != 1 || sperrzeitWunsch != 1
                                || skontozeit1 != 1 || skontozeit2 != 1
                                || mahnzeit1 != 1 || mahnzeit2 != 1
                                || mahnzeit3 != 1) {
//                          veraendern--> neue ZK wird in datenbank geschrieben                        
                            this.dao.erstelleZahlungskondition(auftragsart, lieferzeitSOFORT,
                                    sperrzeitWunsch, skontozeit1, skontozeit2, skonto1,
                                    skonto2, mahnzeit1, mahnzeit2, mahnzeit3);
//                          Meldung fuer die Statuszeile wird angepasst
                            statuszeile = "Zahlungskondition mit der"
                                    + " Zahlungskonditon-ID " + zknr
                                    + " wurde erfolgreich angelegt. ";
                            this.hauptFenster.setzeStatusMeldung(statuszeile);
                            zuruecksetzen();
                        } else {
//                          Kein Skonto und Mahnzeit geaendert,
//                          nachfrage ob so gespeichert werden soll
                            int antwort = JOptionPane.showConfirmDialog(null,
                                    meldung, titel, JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (antwort == JOptionPane.YES_OPTION) {
//                              Antowort ja, also speichern
                                this.dao.erstelleZahlungskondition(auftragsart, lieferzeitSOFORT,
                                        sperrzeitWunsch, skontozeit1, skontozeit2, skonto1,
                                        skonto2, mahnzeit1, mahnzeit2, mahnzeit3);
                                zuruecksetzen();
                            } else {
//                              Antwort nein, ueberarbeiten
                                fehlerhafteComponenten.clear();
                            }
                        }
                    } else {
//                  Sicht ZK aendern: zunaechst ZK aus der Datenbank geladen.
//                  Artikel aus Datenbank ist Variable ZKVorher  
                        Zahlungskondition ZKVorher
                                = this.dao.gibZahlungskonditionNachId(zknr);
//                    Vergleich ZK erzeugen mit den Eingabedaten
                        Zahlungskondition ZKNachher
                                = new Zahlungskondition(auftragsart,
                                        lieferzeitSOFORT, sperrzeitWunsch,
                                        skontozeit1, skontozeit2, skonto1,
                                        skonto2, mahnzeit1, mahnzeit2,
                                        mahnzeit3);
//                    Pruefung ob ZkVorher gleich ist wie ZK nachher
                        if (ZKVorher.equals(ZKNachher)) {
//                         Keine Veraenderung -> Meldung
                            JOptionPane.showMessageDialog(null,
                                    "Es wurden keine Änderungen gemacht!",
                                    "Keine Änderungen",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
//                          Veraenderung: Abfrage, ob Änderungen gespeichert
//                          werden sollen
                            int antwort = JOptionPane.showConfirmDialog(null,
                                    MELDUNG_AENDERUNGEN_SPEICHERN, ZK_AENDERN,
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (antwort == JOptionPane.YES_OPTION) {
//                              falls ja, wird der Artikel geaendert
                                this.dao.aendereZahlungskondition(zknr,
                                        auftragsart, lieferzeitSOFORT,
                                        sperrzeitWunsch, skontozeit1,
                                        skontozeit2, skonto1, skonto2,
                                        mahnzeit1, mahnzeit2, mahnzeit3);
//                              Meldung fuer die Statuszeile wird angepassz
                                statuszeile = "Zahlungskondition mit der"
                                        + " Zahlungskonditon-ID " + zknr
                                        + " wurde erfolgreich geändert. ";
                                this.hauptFenster.setzeStatusMeldung(statuszeile);
                                zuruecksetzen(); // Formular zuruecksetzen
                                this.setVisible(false); //diese Sicht ausblenden
                                zurueckInsHauptmenue();
                            } else {
//                              Nein button wird geklickt, keine Aktion nur 
//                              fehlerhafte Komponenten müssen geleert werden
                                fehlerhafteComponenten.clear();
                            }
                        }
                    }
                } else {
//                  Mahnzeiten stimmen nicht, also Fehlermeldung  
                    JOptionPane.showMessageDialog(null,
                            MELDUNG_SKONTO_MAHNZEITEN, TITEL_SKONTO_MAHNZEITEN,
                            JOptionPane.ERROR_MESSAGE);
                }
//          Fehler abfangen    
            } catch (ApplicationException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        FEHLER, JOptionPane.ERROR_MESSAGE);
            } catch (ParseException | NullPointerException e) {
            }
//          das Formular wird zurueckgesetzt  
        } else {
//          fehlerhafteComponenten ist nicht leer (es sind fehlerhafte
//          Componenten vorhanden)
//          wird methode fuer das Markieren ausgefuehrt 
            fehlEingabenMarkierung(fehlerhafteComponenten,
                    TITEL_PFLICHTFELDER, MELDUNG_PFLICHTFELDER,
                    FARBE_FEHLERHAFT);
        }
    }//GEN-LAST:event_jB_SpeichernActionPerformed

    /*
     * Historie:
     * 14.12.2014   Sen     angelegt
     */
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt Event, der ausgeloest wird
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        if (this.getTitle().equals(ZK_ANLEGEN)) {
            beendenEingabeNachfrage();
        } else if (this.getTitle().equals(ZK_AENDERN)) {
//      zunaechst werdne die Eingaben ueberprueft.    
            ueberpruefen();
//      falls fehlerhafteComponenten leer ist (es sind keine fehlerhaften
//      Componenten verfuegbar), 
//      werden die Eingaben in die entsprechenden Variablen gespeichert
            if (fehlerhafteComponenten.isEmpty()) {
                String auftragsart = (String) jCB_Auftragsart.getSelectedItem();
                int lieferzeitSOFORT;
                int sperrzeitWunsch;
                int skontozeit1;
                int skontozeit2;
                double skonto1;
                double skonto2;
                int mahnzeit1;
                int mahnzeit2;
                int mahnzeit3;
                long zknr;

                try {
//                einzel- und bestelltwert sowie die mwst und die bestandsmenge
//                muessen geparst werden, da die dao Methode int und double
//                Typen moechte
//                Skonto und Mahnzeiten aus jSpinner mussen geholt werdne
                    zknr = nf.parse(jTF_ZahlungskonditionID.getText())
                            .longValue();
                    lieferzeitSOFORT = ((Number) jSP_LieferzeitSOFORT
                            .getValue()).intValue();
                    sperrzeitWunsch = ((Number) jSP_SperrzeitWUNSCH
                            .getValue()).intValue();
                    skonto1 = nf.parse((String) jCB_Skonto1.getSelectedItem())
                            .doubleValue();
                    skonto2 = nf.parse((String) jCB_Skonto2.getSelectedItem())
                            .doubleValue();
                    skontozeit1 = ((Number) jSP_Skontozeit1.getValue())
                            .intValue();
                    skontozeit2 = ((Number) jSP_Skontozeit2.getValue())
                            .intValue();
                    mahnzeit1 = ((Number) jSP_Mahnzeit1.getValue()).intValue();
                    mahnzeit2 = ((Number) jSP_Mahnzeit2.getValue()).intValue();
                    mahnzeit3 = ((Number) jSP_Mahnzeit3.getValue()).intValue();
//                  ZK aus Datenbank ist Variable ZKVorher  
                    Zahlungskondition ZKVorher
                            = this.dao.gibZahlungskonditionNachId(zknr);
//                    Vergleich ZK erzeugen mit den Eingabedaten
                    Zahlungskondition ZKNachher
                            = new Zahlungskondition(auftragsart,
                                    lieferzeitSOFORT,
                                    sperrzeitWunsch, skontozeit1,
                                    skontozeit2, skonto1, skonto2, mahnzeit1,
                                    mahnzeit2, mahnzeit3);
//                    Pruefung ob ZkVorher gleich ist wie ZK nachher
                    if (ZKVorher.equals(ZKNachher)) {
                        this.setVisible(false); // diese Sicht ausblenden 
                        zurueckInsHauptmenue();
                    } else {
//                Veraenderung: Abfrage, ob Änderungen gespeichert werden sollen
                        int antwort = JOptionPane.showConfirmDialog(null,
                                MELDUNG_AENDERUNGEN_SPEICHERN, ZK_AENDERN,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (antwort == JOptionPane.YES_OPTION) {
//                          Ueberpruefungob, Mahnzeiten stimmen  
                            if (mahnzeit3 > mahnzeit2 && mahnzeit2 > mahnzeit1
                                    && skontozeit2 > skontozeit1) {
//                     falls ja, wird die ZK geaendert
                                this.dao.aendereZahlungskondition(zknr,
                                        auftragsart, lieferzeitSOFORT,
                                        sperrzeitWunsch, skontozeit1,
                                        skontozeit2, skonto1, skonto2,
                                        mahnzeit1, mahnzeit2, mahnzeit3);
//                          Meldung fuer die Statuszeile wird angepassz
                                statuszeile = "Zahlungskondition"
                                        + " mit der Zahlungskonditon-ID "
                                        + zknr + " wurde erfolgreich geändert.";
                                this.hauptFenster.setzeStatusMeldung(statuszeile);

                                zuruecksetzen(); // Formular zuruecksetzen
                                this.setVisible(false); //diese Sicht ausblenden 
                                zurueckInsHauptmenue();
                            } else {
//                              Mahnzeiten stimmen nicht, also Fehlermeldung  
                                JOptionPane.showMessageDialog(null,
                                        MELDUNG_SKONTO_MAHNZEITEN,
                                        TITEL_SKONTO_MAHNZEITEN,
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (antwort == JOptionPane.CLOSED_OPTION) {
//                                das x wird geklickt, es soll nichts passieren
                        } else {
                            this.setVisible(false); // diese Sicht ausblenden 
                            zurueckInsHauptmenue();
                        }
                    }
//          Fehler abfangen    
                } catch (ApplicationException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), FEHLER,
                            JOptionPane.ERROR_MESSAGE);
                } catch (ParseException | NullPointerException e) {
                }
//          das Formular wird zurueckgesetzt  
            } else {
//                     etwas ist nicht gleich
//                     Abfrage, ob Änderungen gespeichert werden sollen
                int antwort = JOptionPane.showConfirmDialog(null,
                        MELDUNG_AENDERUNGEN_SPEICHERN, ZK_AENDERN,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (antwort == JOptionPane.YES_OPTION) {
                    fehlEingabenMarkierung(fehlerhafteComponenten,
                            TITEL_PFLICHTFELDER, MELDUNG_PFLICHTFELDER,
                            FARBE_FEHLERHAFT);
                } else {
                    this.setVisible(false); // diese Sicht ausblenden 
                    zurueckInsHauptmenue();
                }
            }
        } else {
            this.setVisible(false); // diese Sicht ausblenden 
            zurueckInsHauptmenue();
        }
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*
     * Historie:
     * 03.02.2015   Sen     angelegt
     */
    /**
     * Methode, die das zurueckspringen in das Hauptmenue ermoeglicht.
     */
    private void zurueckInsHauptmenue() {
        c = null;   //Initialisierung der Componentspeichervariable
//        Erhalten über GUIFactorymethode die letzte
//        aufgerufene View und speichern diese in Variable
        c = this.factory.zurueckButton();
        this.setVisible(false); // Internalframe wird nicht mehr dargestellt
        c.setVisible(true); // Übergebene Component wird sichtbar gemacht
    }

    /*
     * Historie:
     * 15.01.2015   Sen     angelegt
     */
    /**
     * Methode fuer den Aufruf der Suche Maske.
     *
     * @param evt Event der ausgeloest wird
     */
    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        this.hauptFenster.rufeSuche(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed

    /*
     * Historie:
     * 07.12.2014   Sen     angelegt
     */
    /**
     * Aktion die beim betätigen schliesen des Fenster ausgefuert wird.
     *
     * @param evt automatisch generiert
     */
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        beendenEingabeNachfrage();
    }//GEN-LAST:event_formInternalFrameClosing

    /*
     * Historie:
     * 30.12.2014   Sen     angelegt
     */
    /**
     * Methode fuer das Loeschen eines Artikel.
     *
     * @param evt Event der ausgeloest wird
     */
    private void jB_LoeschenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_LoeschenActionPerformed
//      Variablen fuer die Meldung und Titel  
        String meldung = "Soll die Zahlungskondition wirklich gelöscht werden?";
        String titel = "Zahlungskondition löschen";
        try {
            long zknr = nf.parse(jTF_ZahlungskonditionID.getText()).longValue();
            int antwort = JOptionPane.showConfirmDialog(null, meldung, titel,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (antwort == JOptionPane.YES_OPTION) {
                this.dao.loescheZahlungskondition(zknr);
//              Meldung fuer die Statuszeile wird angepasst
                statuszeile = "Zahlungskondition mit der Zahlungskonditon-ID "
                        + zknr + " wurde erfolgreich gelöscht. ";
                this.hauptFenster.setzeStatusMeldung(statuszeile);
//                jB_ZurueckActionPerformed(evt);
                zurueckInsHauptmenue();
            }
        } catch (ApplicationException e) {
//          Es ist ein Fehler aufgetretetn, dieser wird ausgegeben  
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    FEHLER, JOptionPane.ERROR_MESSAGE);
        } catch (ParseException | NullPointerException e) {
        }
    }//GEN-LAST:event_jB_LoeschenActionPerformed

    /*
     * Historie:
     * 17.01.2015   Sen     angelegt
     */
    /**
     * Methode fuer den Button Anzeigen/AEnden.
     *
     * @param evt Event der ausgeloest wird
     */
    private void jB_AnzeigenAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AnzeigenAEndernActionPerformed
        if (this.getTitle().equals(ZK_ANZEIGEN)) {
            setzFormularInZKAEndernFuerButton();
        }
    }//GEN-LAST:event_jB_AnzeigenAEndernActionPerformed

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return textfield der ZK nummer
     */
    public JTextField gibjTF_ZahlungskonditionID() {
        return jTF_ZahlungskonditionID;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return combobox der Auftragsart
     */
    public JComboBox gibjCB_Auftragsart() {
        return jCB_Auftragsart;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return jspinner der lieferzeitSOFORT
     */
    public JSpinner gibjSP_LieferzeitSOFORT() {
        return jSP_LieferzeitSOFORT;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return jspinner der SperrzeitWUNSCH
     */
    public JSpinner gibjSP_SperrzeitWUNSCH() {
        return jSP_SperrzeitWUNSCH;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return jspinner der Skontozeit 1
     */
    public JSpinner gibjSP_Skontozeit1() {
        return jSP_Skontozeit1;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return jspinner der Skontozeit 2
     */
    public JSpinner gibjSP_Skontozeit2() {
        return jSP_Skontozeit2;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return combobox der Skonto 1
     */
    public JComboBox gibjCB_Skonto1() {
        return jCB_Skonto1;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return combobox der Skonto 2
     */
    public JComboBox gibjCB_Skonto2() {
        return jCB_Skonto2;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return jspinner der mahnzeit 1
     */
    public JSpinner gibjSP_Mahnzeit1() {
        return jSP_Mahnzeit1;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return jspinner der mahnzeit 2
     */
    public JSpinner gibjSP_Mahnzeit2() {
        return jSP_Mahnzeit2;
    }

    /*
     * Historie:
     * 10.01.2015   Sen     angelegt
     */
    /**
     * getter Methode fuer die Sicht ZK aendern Einstieg.
     *
     * @return jspinner der mahnzeit 1
     */
    public JSpinner gibjSP_Mahnzeit3() {
        return jSP_Mahnzeit3;
    }

    /*
     * Historie:
     * 9.01.2015   Sen     angelegt
     */
    /**
     * Methode, die das Formular in die Sicht ZK anlegen aendert.
     */
    public void setzeFormularInZKAnlegen() {
//      Formular wird zurueckgesetzt. 
        zuruecksetzen();
//      Titel wird geaendert und entsprechende Eingabefelder werden aktiviert   
        this.setTitle(ZK_ANLEGEN);
        jCB_Auftragsart.setEnabled(true);
        jSP_LieferzeitSOFORT.setEnabled(true);
        jSP_SperrzeitWUNSCH.setEnabled(true);
        jSP_Skontozeit1.setEnabled(true);
        jSP_Skontozeit2.setEnabled(true);
        jCB_Skonto1.setEnabled(true);
        jCB_Skonto2.setEnabled(true);
        jSP_Mahnzeit1.setEnabled(true);
        jSP_Mahnzeit2.setEnabled(true);
        jSP_Mahnzeit3.setEnabled(true);
//      Button werden auch entsprechend aktiviert oder nicht aktiviert.
        jB_Speichern.setEnabled(true);
        jB_AnzeigenAEndern.setEnabled(false);
        jB_Loeschen.setEnabled(false);
        jB_Suchen.setEnabled(false);
//      Übergibt der Referenz des Hauptfensters das Internaframe
        this.hauptFenster.setzeComponent(this);
    }

    /*
     * Historie:
     * 06.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    /**
     * Methode, die das Formular in die Sicht ZK aendern aendert.
     */
    public void setzeFormularInZKAEndern() {
//      Formular wird zurueckgestzt  
        zuruecksetzen();
//      Entsprechende Methode wird aufgerufen 
        setzFormularInZKAEndernFuerButton();
//      Übergibt der Referenz des Hauptfensters das Internaframe
        this.hauptFenster.setzeComponent(this);
    }

    /*
     * Historie:
     * 05.01.2015   Sen     angelegt
     */
    /**
     * Methode wird aufgerufen, wenn auf Button Anzeigen/Aendern geklickt wird,
     * das Formular wird nicht zurueckgesetzt, deswegen musste eine extra
     * Methode geschrieben werden.
     */
    private void setzFormularInZKAEndernFuerButton() {
//      Titel wird gesetzt.  
        this.setTitle(ZK_AENDERN);
        jCB_Auftragsart.setEnabled(false);
        if (((String) jCB_Auftragsart.getSelectedItem())
                .equals(SOFORTAUFTRAG)) {
            jSP_LieferzeitSOFORT.setEnabled(true);
        } else if (((String) jCB_Auftragsart.getSelectedItem())
                .equals(TERMINAUFTRAG)) {
            jSP_SperrzeitWUNSCH.setEnabled(true);
        }
//      Eingabefelder werden aktiviert oder entsprechend nicht aktiviert.
        jSP_Skontozeit1.setEnabled(true);
        jSP_Skontozeit2.setEnabled(true);
        jCB_Skonto1.setEnabled(true);
        jCB_Skonto2.setEnabled(true);
        jSP_Mahnzeit1.setEnabled(true);
        jSP_Mahnzeit2.setEnabled(true);
        jSP_Mahnzeit3.setEnabled(true);
//      Buttons auch.
        jB_Speichern.setEnabled(true);
        jB_AnzeigenAEndern.setEnabled(false);
        jB_Loeschen.setEnabled(true);
        jB_Suchen.setEnabled(false);
    }

    /*
     /* Datum Name Was */
    /* 12.12.2014   Sen     angelegt
     /* 08.01.2015 Terrasi, implemtiereung der Referenzvariable "hauptfenster"*/
    /**
     * Methode wird aufgerufen, wenn auf Sicht ZK Anzeigen aufgerufen wird.
     */
    public void setzeFormularInZKAnzeigen() {
//      Formular wird zurueckgestzt.  
        zuruecksetzen();
//      Entsprechende Methode wird aufgerufen.  
        setzFormularInZKAnzeigenFuerButton();
//      Übergibt der Referenz des Hauptfensters das Internaframe
        this.hauptFenster.setzeComponent(this);
    }

    /*
     * Historie:
     * 06.01.2015   Sen     angelegt
     * 08.01.2015   Terrasi implemtiereung der Referenzvariable "hauptfenster"
     */
    /**
     * Methode, die das Formular in die Sicht Artikel anzeigen aendert.
     */
    private void setzFormularInZKAnzeigenFuerButton() {
//      Titel wird gesetzt.  
        this.setTitle(ZK_ANZEIGEN);
//      Buttons werden entsprechend ausgeawaehlt oder nicht ausgewaehlt.  
        jCB_Auftragsart.setEnabled(false);
        jSP_LieferzeitSOFORT.setEnabled(false);
        jSP_SperrzeitWUNSCH.setEnabled(false);
        jSP_Skontozeit1.setEnabled(false);
        jSP_Skontozeit2.setEnabled(false);
        jCB_Skonto1.setEnabled(false);
        jCB_Skonto2.setEnabled(false);
        jSP_Mahnzeit1.setEnabled(false);
        jSP_Mahnzeit2.setEnabled(false);
        jSP_Mahnzeit3.setEnabled(false);
//      Buttons auch.
        jB_Speichern.setEnabled(false);
        jB_AnzeigenAEndern.setEnabled(true);
        jB_Loeschen.setEnabled(false);
        jB_Suchen.setEnabled(true);
    }

    /*
     * Historie:
     * 17.01.2015   Sen     angelegt
     */
    /**
     * setzeMethode, die aus der Suche aufgerufen wird. Es wird die Sicht ZK
     * Anzeigen aufgerufen und der gesuchte ZK angezeigt.
     *
     * @param zk ZK aus der Suche
     */
    public void zeigeZKausSucheAn(Zahlungskondition zk) {
//      Formular wird in Zustand ZK Anzeigen geaendert 
        setzeFormularInZKAnzeigen();
//      ZK Daten aus der Suche weerden in die Felder hinzugefuegt.  
        jTF_ZahlungskonditionID.setText("" + zk.getZahlungskonditionID());
        jCB_Auftragsart.setSelectedItem(zk.getAuftragsart());
        jSP_LieferzeitSOFORT.setValue(zk.getLieferzeitSofort());
        jSP_SperrzeitWUNSCH.setValue(zk.getSperrzeitWunsch());
        jSP_Skontozeit1.setValue(zk.getSkontozeit1());
        jSP_Skontozeit2.setValue(zk.getSkontozeit2());
        jCB_Skonto1.setSelectedItem(("" + zk.getSkonto1()).replace('.', ','));
        jCB_Skonto2.setSelectedItem(("" + zk.getSkonto2()).replace('.', ','));
        jSP_Mahnzeit1.setValue(zk.getMahnzeit1());
        jSP_Mahnzeit2.setValue(zk.getMahnzeit2());
        jSP_Mahnzeit3.setValue(zk.getMahnzeit3());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_AnzeigenAEndern;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JComboBox jCB_Auftragsart;
    private javax.swing.JComboBox jCB_Skonto1;
    private javax.swing.JComboBox jCB_Skonto2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner jSP_LieferzeitSOFORT;
    private javax.swing.JSpinner jSP_Mahnzeit1;
    private javax.swing.JSpinner jSP_Mahnzeit2;
    private javax.swing.JSpinner jSP_Mahnzeit3;
    private javax.swing.JSpinner jSP_Skontozeit1;
    private javax.swing.JSpinner jSP_Skontozeit2;
    private javax.swing.JSpinner jSP_SperrzeitWUNSCH;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTF_ZahlungskonditionID;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
