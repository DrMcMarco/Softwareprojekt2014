package GUI_Internalframes;

import Documents.UniversalDocument;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * GUI Klasse für Artikel verwalten.
 *
 * @author Tahir
 */
public class ArtikelAnlegen extends javax.swing.JInternalFrame {

    /*
     * Instanzvariablen der Klasse. 
     */
    private int artikelnummer;

//  ArrayList, um fehlerhafte Componenten zu speichern.    
    private ArrayList<Component> fehlerhafteComponenten;
//  ArrayList, um angelegte Artikel zu speichern     
    public ArrayList<Component> artikelListe;
    public ArrayList<String> kategorien;
//  Insantzvariablen für die standard Farben der Componenten    
    private final Color JCB_FARBE_STANDARD = new Color(214, 217, 223);
    private final Color JTF_FARBE_STANDARD = new Color(255, 255, 255);
//  Insantzvariablen für die Farben von fehlerhaften Componenten         
    private final Color FARBE_FEHLERHAFT = new Color(255, 165, 79);
//  Insantzvariablen für reguläre Ausdrücke, um Prüfungen durchzuführen          
    private final String PREUFUNG_PREIS = "|(\\d*,?\\d{1,2})|(\\d{0,3}(\\.\\d{3})*,?\\d{1,2})";
//  Insantzvariablen für die Meldungen         
    private final String TITEL_PFLICHTFELDER = "Felder nicht ausgefüllt";
    private final String TEXT_PFLICHTFELDER = "Einige Felder wurden nicht ausgefüllt! Bitte füllen Sie diese aus!";
    private final String TEXT_EINZELWERT = "Der eingegebene Preisr ist nicht richtig! \n Bitte geben Sie eine richtige Preis ein. (z.B. 99,99 oder 999.999,99)";
    private final String TITEL_FEHLERHAFTE_EINGABE = "Fehlerhafte Eingabe";
    private final String STATUSZEILE = "Artikel wurde angelegt!";

    private NumberFormat nf;

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     */
    public ArtikelAnlegen() {
        initComponents();
        fehlerhafteComponenten = new ArrayList<>();
        artikelListe = new ArrayList<>();
        kategorien = new ArrayList<>();

        nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
//        Documente werden gesetzt
        jTF_Artikelname.setDocument(new UniversalDocument("-.´", true));
        jTF_Einzelwert.setDocument(new UniversalDocument("0123456789.,", false));
        jTF_Bestellwert.setDocument(new UniversalDocument("0123456789.,", false));
        jTF_Bestandsmenge_FREI.setDocument(new UniversalDocument("0123456789", false));
    }
    
    private void ladeArtikelkategorie() {
//        kategorien = DAO
    }

    /*
     * Methode für die Überprüfung der Daten. Falls ein Textfeld nicht gefüllt ist, wird sie der ArrayList für   
     * fehlerhafte Componenten hinzugefuegt. Falls bei eine Compobox der selektierte Index auf 0 ("Bitte auswählen")
     * steht, wird diese ebenfalls in die ArrayList uebernommen 
     */
    private void ueberpruefeFormular() {
        if (jTF_Artikelname.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Artikelname);
        }
        if (jTF_Artikelname.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Artikelname);
        }
        if (jTA_Artikelbeschreibung.getText().equals("")) {
            fehlerhafteComponenten.add(jTA_Artikelbeschreibung);
        }
        if (jCB_Kategorie.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_Kategorie);
        }
        if (jTF_Einzelwert.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Einzelwert);
        }
        if (jTF_Bestellwert.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Bestellwert);
        }
        if (jCB_MwST.getSelectedIndex() == 0) {
            fehlerhafteComponenten.add(jCB_MwST);
        }
        if (jTF_Bestandsmenge_FREI.getText().equals("")) {
            fehlerhafteComponenten.add(jTF_Bestandsmenge_FREI);
        }
    }

    /*
     * Methode, die die Eingaben zurücksetzt, beim Zurücksetzen wird auch die Hintergrundfarbe zurückgesetzt. 
     */
    public final void setzeFormularZurueck() {
        jTF_Artikelnummer.setText("" + artikelnummer);
        jTF_Artikelname.setText("");
        jTF_Artikelname.setBackground(JTF_FARBE_STANDARD);
        jTA_Artikelbeschreibung.setText("");
        jTA_Artikelbeschreibung.setBackground(JTF_FARBE_STANDARD);
        jCB_Kategorie.setSelectedIndex(0);
        jCB_Kategorie.setBackground(JCB_FARBE_STANDARD);
        jTF_Einzelwert.setText("");
        jTF_Einzelwert.setBackground(JTF_FARBE_STANDARD);
        jTF_Bestellwert.setText("");
        jTF_Bestellwert.setBackground(JTF_FARBE_STANDARD);
        jCB_MwST.setSelectedIndex(0);
        jCB_MwST.setBackground(JCB_FARBE_STANDARD);
        jTF_Bestandsmenge_FREI.setText("");
        jTF_Bestandsmenge_FREI.setBackground(JTF_FARBE_STANDARD);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField4 = new javax.swing.JTextField();
        jTB_Menueleiste = new javax.swing.JToolBar();
        jB_Zurueck = new javax.swing.JButton();
        jB_Abbrechen = new javax.swing.JButton();
        jB_Speichern = new javax.swing.JButton();
        jB_Anzeigen = new javax.swing.JButton();
        jB_Loeschen = new javax.swing.JButton();
        jB_Suchen = new javax.swing.JButton();
        jS_Trenner = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jTF_Artikelnummer = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTF_Einzelwert = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTF_Bestandsmenge_FREI = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTF_Bestellwert = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jCB_MwST = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jTF_Artikelname = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTF_Statuszeile = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTF_Bestandsmenge_RES = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTF_Bestandsmenge_ZULAUF = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTF_Bestandsmenge_VERKAUFT = new javax.swing.JTextField();
        jCB_Kategorie = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jSP_Artikelbeschreibung = new javax.swing.JScrollPane();
        jTA_Artikelbeschreibung = new javax.swing.JTextArea();

        jTextField4.setText("jTextField4");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Artikel anlegen");
        setPreferredSize(new java.awt.Dimension(500, 830));
        setRequestFocusEnabled(false);
        setVisible(true);

        jTB_Menueleiste.setBorder(null);
        jTB_Menueleiste.setRollover(true);
        jTB_Menueleiste.setEnabled(false);

        jB_Zurueck.setText("Zurück");
        jB_Zurueck.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTB_Menueleiste.add(jB_Zurueck);

        jB_Abbrechen.setText("Abbrechen");
        jTB_Menueleiste.add(jB_Abbrechen);

        jB_Speichern.setText("Speichern");
        jB_Speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SpeichernActionPerformed(evt);
            }
        });
        jTB_Menueleiste.add(jB_Speichern);

        jB_Anzeigen.setText("Anzeige/Ändern");
        jB_Anzeigen.setActionCommand("Anzeigen/Ändern");
        jTB_Menueleiste.add(jB_Anzeigen);

        jB_Loeschen.setText("Löschen");
        jTB_Menueleiste.add(jB_Loeschen);

        jB_Suchen.setText("Suchen");
        jTB_Menueleiste.add(jB_Suchen);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Artikelname:");

        jTF_Artikelnummer.setText("1");
        jTF_Artikelnummer.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Artikelbeschreibung:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Einzelwert:");

        jTF_Einzelwert.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_EinzelwertFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Bestandsmenge FREI:");

        jTF_Bestandsmenge_FREI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_Bestandsmenge_FREIFocusLost(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Kategorie:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Bestellwert:");

        jTF_Bestellwert.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_BestellwertFocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("MwST Satz:");

        jCB_MwST.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "0", "7", "19" }));
        jCB_MwST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_MwSTActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Artikelnummer:");

        jTF_Artikelname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTF_ArtikelnameFocusLost(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("€");

        jLabel10.setText("Stk.");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("€");

        jTF_Statuszeile.setText("Statuszeile");
        jTF_Statuszeile.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Allgemeine Daten:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Preisdaten:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Bestand:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Bestandsmenge RESERVIERT:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Bestandsmenge ZULAUF:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Bestandsmenge VERKAUFT:");

        jLabel19.setText("Stk.");

        jTF_Bestandsmenge_RES.setEnabled(false);

        jLabel20.setText("Stk.");

        jTF_Bestandsmenge_ZULAUF.setEnabled(false);

        jLabel21.setText("Stk.");

        jTF_Bestandsmenge_VERKAUFT.setEnabled(false);

        jCB_Kategorie.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bitte auswählen", "Kategorie 1", "Kategorie 2" }));
        jCB_Kategorie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_KategorieActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("%");

        jTA_Artikelbeschreibung.setColumns(20);
        jTA_Artikelbeschreibung.setRows(5);
        jTA_Artikelbeschreibung.setPreferredSize(new java.awt.Dimension(166, 94));
        jTA_Artikelbeschreibung.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTA_ArtikelbeschreibungFocusLost(evt);
            }
        });
        jSP_Artikelbeschreibung.setViewportView(jTA_Artikelbeschreibung);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jS_Trenner)
            .addComponent(jTB_Menueleiste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTF_Statuszeile)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTF_Bestandsmenge_VERKAUFT, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTF_Bestandsmenge_ZULAUF, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTF_Bestandsmenge_RES, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(57, 57, 57)
                        .addComponent(jTF_Bestandsmenge_FREI, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jSP_Artikelbeschreibung, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTF_Artikelname, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(226, 226, 226)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(46, 46, 46)
                        .addComponent(jTF_Artikelnummer, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCB_MwST, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTF_Einzelwert, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTF_Bestellwert, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCB_Kategorie, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTB_Menueleiste, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jS_Trenner, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTF_Artikelnummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_Artikelname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jSP_Artikelbeschreibung, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jCB_Kategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTF_Einzelwert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTF_Bestellwert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jCB_MwST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTF_Bestandsmenge_FREI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTF_Bestandsmenge_RES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTF_Bestandsmenge_ZULAUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTF_Bestandsmenge_VERKAUFT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jTF_Statuszeile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     * Methode für das Speichern der Daten. 
     */
    private void jB_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SpeichernActionPerformed
//      zunaechst werdne die Eingaben ueberprueft.    
        ueberpruefeFormular();
//      falls fehlerhafteComponenten leer ist (es sind keine fehlerhaften Componenten verfuegbar), 
//      werden die Eingaben in die entsprechenden Variablen gespeichert
        if (fehlerhafteComponenten.isEmpty()) {
            String artikelname;
            String artikelbeschreibung;
            String kategorie;
            double einzelwert = 0;
            double bestellwert = 0;
            double mwst = 0;
            int bestandsmengeFREI = 0;
            int bestandsmengeRESERVIERT = 0;
            int bestandsmengeZULAUF = 0;
            int bestandsmengeVERKAUFT = 0;
//            String einzelwert;
//            String bestellwert;
//            String mwst;
//            String bestandsmengeFREI;
            try {

                einzelwert = nf.parse(jTF_Einzelwert.getText()).doubleValue();
                bestellwert = nf.parse(jTF_Bestellwert.getText()).doubleValue();

                mwst = nf.parse((String) jCB_MwST.getSelectedItem()).doubleValue();
                bestandsmengeFREI = nf.parse(jTF_Bestandsmenge_FREI.getText()).intValue();
            } catch (ParseException ex) {
                System.out.println("Fehler beim Parsen in der Klasse ArtikelAnlegen!");
            }
//
            artikelname = jTF_Artikelname.getText();
            artikelbeschreibung = jTA_Artikelbeschreibung.getText();
            kategorie = (String) jCB_Kategorie.getSelectedItem();
                System.out.println("Geschäftspartner: \n"
                        + "Artikelnummer:                  " + artikelnummer + "\n"
                        + "Artikelname:                    " + artikelname + "\n"
                        + "Artikelbeschreibung:            " + artikelbeschreibung + "\n"
                        + "Kategorie:                      " + kategorie + "\n"
                        + "Einzelwert:                     " + einzelwert + "\n"
                        + "Bestellwert:                    " + bestellwert + "\n"
                        + "MwSt:                           " + mwst + "\n"
                        + "Bestangsmenge FREI:             " + bestandsmengeFREI + "\n"
                        + "Bestangsmenge RESERVIERT:       " + bestandsmengeRESERVIERT + "\n"
                        + "Bestangsmenge ZULAUF:           " + bestandsmengeZULAUF + "\n"
                        + "Bestangsmenge VERKAUFT:         " + bestandsmengeVERKAUFT + "\n");
//          Artikel wird in ArrayList für Artikel hinzugefuegt    

//            artikelListe.add(new Artikel(artikelnummer, artikelname, artikelbeschreibung, kategorie, einzelwert, bestellwert, mwst, bestandsmengeFREI, "0", "0", "0"));
//          provisorisch wird der Artikel ausgegeben  
//            System.out.println(artikelListe.get(artikelListe.size() - 1).toString());
//          die artikelnummer wird erhöht  
            artikelnummer++;
            jTF_Statuszeile.setText(STATUSZEILE);
//          das Formular wird zurueckgesetzt  
            setzeFormularZurueck();
        } else {
//          fehlerhafteComponenten ist nicht leer (es sind fehlerhafte Componenten vorhanden)
//          eine Meldung wird ausgegeben  
            JOptionPane.showMessageDialog(null, TEXT_PFLICHTFELDER, TITEL_PFLICHTFELDER, JOptionPane.ERROR_MESSAGE);
//          an die erste fehlerhafte Componenten wird der Focus gesetzt  
            fehlerhafteComponenten.get(0).requestFocusInWindow();
//          ueber die fehlerhaften Komponenten wird iteriert und bei allen fehlerhaften Componenten wird der Hintergrund in der fehlerhaften Farbe gefaerbt 
            for (int i = 0; i <= fehlerhafteComponenten.size() - 1; i++) {
                fehlerhafteComponenten.get(i).setBackground(FARBE_FEHLERHAFT);
            }
//          ArrayList fue fehlerhafte Componenten wird geleert
            fehlerhafteComponenten.clear();
        }
    }//GEN-LAST:event_jB_SpeichernActionPerformed

    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jTF_ArtikelnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_ArtikelnameFocusLost
        if (!jTF_Artikelname.getText().equals("")) {
            jTF_Artikelname.setBackground(JTF_FARBE_STANDARD);
            jTF_Statuszeile.setText("");
        }
    }//GEN-LAST:event_jTF_ArtikelnameFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jCB_KategorieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_KategorieActionPerformed
        if (jCB_Kategorie.getSelectedIndex() != 0) {
            jCB_Kategorie.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_KategorieActionPerformed
    /*
     * Methode wür die Überprüfung der Eingabe des Einzelwertes.
     */
    private void jTF_EinzelwertFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_EinzelwertFocusLost
//            Prüfung, ob die Eingabe ein Preis ist
        if (!jTF_Einzelwert.getText().matches(PREUFUNG_PREIS)) {
//            Eingabe ist kein Preis, also wird eine Fehlermeldung ausgegeben
            JOptionPane.showMessageDialog(null, TEXT_EINZELWERT, TITEL_FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
//            Focus wird auf das Feld für die Eingabe des Einzelwertes gesetzt und der Eingabefeld wird auf leer gesetzt.
            jTF_Einzelwert.requestFocusInWindow();
            jTF_Einzelwert.setText("");
        } else if (!jTF_Einzelwert.getText().equals("")) {
//            Eingabe ist ein Preis
            try {
//            Eingabe wird in ein double geparst    
                double einzelwert = nf.parse(jTF_Einzelwert.getText()).doubleValue();
//            der format wird angepasst und ausgegeben, Hintergrund des Feldes wird auf normalgestzt    
                jTF_Einzelwert.setText(nf.format(einzelwert));
                jTF_Einzelwert.setBackground(JTF_FARBE_STANDARD);
            } catch (ParseException ex) {
//            Fehler beim Parsen
                System.out.println("Fehler in der Methode jTF_EinzelwertFocusLost()");
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_jTF_EinzelwertFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jTF_BestellwertFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_BestellwertFocusLost
//            Prüfung, ob die Eingabe ein Preis ist
        if (!jTF_Bestellwert.getText().matches(PREUFUNG_PREIS)) {
//            Eingabe ist kein Preis, also wird eine Fehlermeldung ausgegeben            
            JOptionPane.showMessageDialog(null, TEXT_EINZELWERT, TITEL_FEHLERHAFTE_EINGABE, JOptionPane.ERROR_MESSAGE);
//            Focus wird auf das Feld für die Eingabe des Einzelwertes gesetzt und der Eingabefeld wird auf leer gesetzt.            
            jTF_Bestellwert.requestFocusInWindow();
            jTF_Bestellwert.setText("");
        } else if (!jTF_Bestellwert.getText().equals("")) {
//            Eingabe ist ein Preis           
            try {
//            Eingabe wird in ein double geparst                 
                double bestellwert = nf.parse(jTF_Bestellwert.getText()).doubleValue();
//            der format wird angepasst und ausgegeben, Hintergrund des Feldes wird auf normalgestzt                  
                jTF_Bestellwert.setText(nf.format(bestellwert));
                jTF_Bestellwert.setBackground(JTF_FARBE_STANDARD);
            } catch (ParseException ex) {
//            Fehler beim Parsen             
                System.out.println("Fehler in der Methode jTF_BestellwertFocusLost()");
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_jTF_BestellwertFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jCB_MwSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_MwSTActionPerformed
        if (jCB_MwST.getSelectedIndex() != 0) {
            jCB_MwST.setBackground(JCB_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jCB_MwSTActionPerformed
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jTF_Bestandsmenge_FREIFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTF_Bestandsmenge_FREIFocusLost
        if (!jTF_Bestandsmenge_FREI.getText().equals("")) {
            jTF_Bestandsmenge_FREI.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTF_Bestandsmenge_FREIFocusLost
    /*
     * Methode prüft, ob Eingabe getätigt wurde. Falls ja, wird der Hintergrund in standard gefärbt.
     */
    private void jTA_ArtikelbeschreibungFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTA_ArtikelbeschreibungFocusLost
        if (!jTA_Artikelbeschreibung.getText().equals("")) {
            jTA_Artikelbeschreibung.setBackground(JTF_FARBE_STANDARD);
        }
    }//GEN-LAST:event_jTA_ArtikelbeschreibungFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_Abbrechen;
    private javax.swing.JButton jB_Anzeigen;
    private javax.swing.JButton jB_Loeschen;
    private javax.swing.JButton jB_Speichern;
    private javax.swing.JButton jB_Suchen;
    private javax.swing.JButton jB_Zurueck;
    private javax.swing.JComboBox jCB_Kategorie;
    private javax.swing.JComboBox jCB_MwST;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jSP_Artikelbeschreibung;
    private javax.swing.JSeparator jS_Trenner;
    private javax.swing.JTextArea jTA_Artikelbeschreibung;
    private javax.swing.JToolBar jTB_Menueleiste;
    private javax.swing.JTextField jTF_Artikelname;
    private javax.swing.JTextField jTF_Artikelnummer;
    private javax.swing.JTextField jTF_Bestandsmenge_FREI;
    private javax.swing.JTextField jTF_Bestandsmenge_RES;
    private javax.swing.JTextField jTF_Bestandsmenge_VERKAUFT;
    private javax.swing.JTextField jTF_Bestandsmenge_ZULAUF;
    private javax.swing.JTextField jTF_Bestellwert;
    private javax.swing.JTextField jTF_Einzelwert;
    private javax.swing.JTextField jTF_Statuszeile;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
