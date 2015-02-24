package GUI_Internalframes;

import DAO.ApplicationException;
import DTO.Anschrift;
import DTO.Geschaeftspartner;
import Documents.UniversalDocument;
import Interfaces.InterfaceMainView;
import JFrames.GUIFactory;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Klasse fuer die Maske Geschaeftspartner (GP) aendern/anzeigen Einstieg je
 * nachdem von welchem Button diese Klasse aufgerufen wird aendert sie sich in
 * den Zustand GP aendern Einstieg oder GP anzeigen Einstieg.
 *
 * @author Tahir
 */
public class GeschaeftspartnerAEndernEinstieg
        extends javax.swing.JInternalFrame {

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
     * Referenzvaribale der Sicht GP anlegen.
     */
    private GeschaeftspartnerAnlegen g;
    /**
     * Number Formatter wird benoetigt fuer das Parsen der Eingaben, sowie das
     * Anzeigen von Preisen.
     */
    private NumberFormat nf;
    /**
     * Variable für das Start Fenster. Es kann sein, dass sich ein Admin
     * anmeldet, dann waere unser StartFenster die StartAdmin. Falls sich ein
     * User anmeldet, ist unser StartFenster Start.
     */
    private InterfaceMainView hauptFenster;
    /**
     * Variablen fuer die Fehlermeldungen
     */
    private final String KEINE_GPNR_EINGEGEBEN
            = "Bitte geben Sie eine Geschäftspartner-ID ein.";
    /**
     * Variablen fuer die Fehlermeldungen
     */
    private final String KEINE_GP_IN_DATENBANK
            = "Kein passender Geschäftspartner in der Datenbank.";

    /**
     * Konstruktor der Klasse, erstellt die benötigten Objekte und setzt die
     * Documents.
     *
     * @param factory beinhaltet das factory Obejekt
     * @param gp Referenzvariable der GP anlegen Klasse
     * @param mainView beinhaltet das Objekt des StartFenster
     */
    public GeschaeftspartnerAEndernEinstieg(GUIFactory factory,
            GeschaeftspartnerAnlegen gp, InterfaceMainView mainView) {
        initComponents();
        this.factory = factory;
        this.g = gp;
        this.hauptFenster = mainView;
        nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        jTF_Geschaeftspartner_ID.setDocument(new UniversalDocument("0123456789",
                false));
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
        jTF_Geschaeftspartner_ID = new javax.swing.JTextField();
        jB_Enter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Geschäftspartner ändern Einstieg");
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
        jB_Anzeigen.setActionCommand("Anzeigen/Ändern");
        jB_Anzeigen.setEnabled(false);
        jToolBar1.add(jB_Anzeigen);

        jB_Loeschen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_Internalframes/Löschen.PNG"))); // NOI18N
        jB_Loeschen.setToolTipText("");
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

        jL_Artikel_ID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jL_Artikel_ID.setText("Geschäftspartner-ID:");

        jTF_Geschaeftspartner_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTF_Geschaeftspartner_IDKeyPressed(evt);
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
                .addComponent(jL_Artikel_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTF_Geschaeftspartner_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jL_Artikel_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_Geschaeftspartner_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Enter))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     * Historie:
     * 14.12.2014   Sen     angelegt
     * 17.12.2014   Sen     ueberarbeitet
     */
    /**
     * Methode, die aufgerufen wird, wenn man auf weiter klickt.
     *
     * @param evt automatisch generiert
     */
    private void jB_EnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_EnterActionPerformed
        String eingabe = jTF_Geschaeftspartner_ID.getText();
        long gpnr = 0;
        try {
//    GP mit der eingegebenen GP nummer wird versucht aus der Datenbank zu laden
//    Alle Felder der Sicht GP anlegen werden befuellt            
            gpnr = nf.parse(eingabe).longValue();
            Geschaeftspartner gp
                    = GUIFactory.getDAO().gibGeschaeftspartner(gpnr);
            Anschrift la = gp.getLieferadresse();
            Anschrift ra = gp.getRechnungsadresse();
            String typ = gp.getTyp();
//            Pruefung des Typs des GP
            if (typ.equals("Kunde")) {
                g.gibjCHB_Kunde().setSelected(true);
            } else {
                g.gibjCHB_Lieferant().setSelected(true);
            }
            g.gibjTF_GeschaeftspartnerID().setText("" + gpnr);
            g.gibjCB_Anrede().setSelectedItem(ra.getTitel());
            g.gibjTF_Name().setText(ra.getName());
            g.gibjTF_Vorname().setText(ra.getVorname());
            g.gibjTF_Telefon().setText(ra.getTelefon());
            g.gibjTF_Fax().setText(ra.getFAX());
            if (la.getGeburtsdatum() != null) {
                g.gibjFTF_Geburtsdatum()
                        .setText(gibDatumAusDatenbank(la.getGeburtsdatum()));
            } else {
                g.gibjFTF_Geburtsdatum().setText("##.##.####");
            }
            g.gibjFTF_Erfassungsdatum()
                    .setText(gibDatumAusDatenbank(la.getErfassungsdatum()));
            g.gibjTF_EMail().setText(ra.getEmail());
            g.gibjTF_Kreditlimit().setText("" + nf.format(gp.getKreditlimit()));
            g.gibjTF_StrasseRechnungsanschrift().setText(ra.getStrasse());
            g.gibjTF_HausnummerRechnungsanschrift().setText(ra.getHausnummer());
            g.gibjTF_PLZRechnungsanschrift().setText(ra.getPLZ());
            g.gibjTF_OrtRechnungsanschrift().setText(ra.getOrt());
//    Pruefung, ob Rechnungsanschrfit und Lieferanschrift gleich:
//    Falls die strasse, hausnummer, ort und plz beider anschriften gleich sind,
//    wird die checkbox wie anschrift in Sicht gp anlegen gesetzt und die 
//    strasse, hausnummer, ort und plz der Lieferanschrift auf enabled false
//    gesetzt
            if (ra.getStrasse().equals(la.getStrasse())
                    && ra.getHausnummer().equals(la.getHausnummer())
                    && ra.getOrt().equals(la.getOrt())
                    && ra.getPLZ().equals(la.getPLZ())) {
                g.gibjCHB_WieAnschrift().setSelected(true);
                g.gibjTF_StrasseLieferanschrift().setText(ra.getStrasse());
                g.gibjTF_StrasseLieferanschrift().setEnabled(false);
                g.gibjTF_HausnummerLieferanschrift()
                        .setText(ra.getHausnummer());
                g.gibjTF_HausnummerLieferanschrift().setEnabled(false);
                g.gibjTF_PLZLieferanschrift().setText(ra.getPLZ());
                g.gibjTF_PLZLieferanschrift().setEnabled(false);
                g.gibjTF_OrtLieferanschrift().setText(ra.getOrt());
                g.gibjTF_OrtLieferanschrift().setEnabled(false);
            } else {
//                Nicht gleich, also checkbox auf false
                g.gibjCHB_WieAnschrift().setSelected(false);
                g.gibjTF_StrasseLieferanschrift().setText(la.getStrasse());
                g.gibjTF_HausnummerLieferanschrift()
                        .setText(la.getHausnummer());
                g.gibjTF_PLZLieferanschrift().setText(la.getPLZ());
                g.gibjTF_OrtLieferanschrift().setText(la.getOrt());
            }
            g.setVisible(true);
            this.setVisible(false);
            zuruecksetzen();
//            entsprechende Fehlermeldungen werden in der Statuszeile angezeigt
        } catch (ParseException e) {
            this.hauptFenster.setStatusMeldung(KEINE_GPNR_EINGEGEBEN);
        } catch (ApplicationException e) {
            this.hauptFenster.setStatusMeldung(KEINE_GP_IN_DATENBANK);
            zuruecksetzen();
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_jB_EnterActionPerformed

    /*
     * Historie:
     * 14.12.2014   Sen     angelegt
     */
    /**
     * Methode, die aus dem uebergebenem Date Objekt ein String mit dem
     * richtigen Format zureueckgibt.
     *
     * @param date date objekt, welches uebergeben wird
     * @return String Datum im richtigen Format (z.B. 01.01.2014)
     */
    private String gibDatumAusDatenbank(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//      tag, monat, jahr werden aus dem Date Objekt herausgenommen
        int tag = cal.get(Calendar.DAY_OF_MONTH);
        int mon = cal.get(Calendar.MONTH);
//      auf den monat wird eine 1 addiert, da sie von 0 anfaengt, d.h 0 = januar
        mon = mon + 1;
        int jahr = cal.get(Calendar.YEAR);
        String tagAlsString;
        String monatAlsString;
//        Preufung ob Tag einstellig
        if (tag < 10) {
//            Falls ja, wird eine 0 davorgetan also wird aus 8 = 08
            tagAlsString = "0" + tag;
        } else {
//          wenn nicht, wird es in ein String gepackt
            tagAlsString = "" + tag;
        }
//      wie in tag
        if (mon < 10) {
            monatAlsString = "0" + mon;
        } else {
            monatAlsString = "" + mon;
        }
//      Ausgabe String wird erzeugt
        String ausgabeDatum = tagAlsString + "." + monatAlsString + "." + jahr;
        return ausgabeDatum;
    }

    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */
    /**
     * Aktion die beim betätigen des Zurück-Buttons ausgeführt wird. Es wird von
     * der Guifactory die letzte aufgerufene Component abgefragt wodurch man die
     * jetzige Component verlässt und zur übergebnen Component zurück kehrt.
     *
     * @param evt automatishc generiert
     */
    private void jB_ZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ZurueckActionPerformed
        c = null;   //Initialisierung der Componentspeichervariable
//        Erhalten über GUIFactorymethode die letzte aufgerufene
//        View und speichern diese in Variable
        c = this.factory.zurueckButton();
        this.setVisible(false); // Internalframe wird nicht mehr dargestellt
        c.setVisible(true); // Übergebene Component wird sichtbar gemacht
    }//GEN-LAST:event_jB_ZurueckActionPerformed

    /*
     * Historie:
     * 01.12.2014   Sen     angelegt
     */
    /**
     * Aktion die beim betätigen schliesen des Fenster ausgefuert wird.
     *
     * @param evt automatishc generiert
     */
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        jB_ZurueckActionPerformed(null);
    }//GEN-LAST:event_formInternalFrameClosing

    /*
     * Historie:
     * 02.01.2015   Sen     angelegt
     */
    /**
     * Aktion die beim klicken auf Enter durchgefuert wird.
     *
     * @param evt automatishc generiert
     */
    private void jTF_Geschaeftspartner_IDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTF_Geschaeftspartner_IDKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            jB_EnterActionPerformed(null);
        }
    }//GEN-LAST:event_jTF_Geschaeftspartner_IDKeyPressed

    /*
     * Historie:
     * 16.01.2015   Terrasi     angelegt
     */
    /**
     * Aktion die beim klicken auf Button Suche durchgefuert wird.
     *
     * @param evt automatishc generiert
     */
    private void jB_SuchenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuchenActionPerformed
        this.hauptFenster.rufeSuche(this);
    }//GEN-LAST:event_jB_SuchenActionPerformed

    /*
     * Historie:
     * 18.01.2015   Sen     angelegt
     */
    /**
     * setterMethode für die Suche.
     *
     * @param geschaeftspartner GP Obkjekt, aus dem die gp nummer gelesen wird
     */
    public void setzeGP_IDAusSuche(Geschaeftspartner geschaeftspartner) {
        jTF_Geschaeftspartner_ID.setText(""
                + geschaeftspartner.getGeschaeftspartnerID());
    }

    /*
     * Historie:
     * 18.01.2015   Sen     angelegt
     */
    /**
     * methode, die die Eingaben zuruecksetzt.
     */
    public void zuruecksetzen() {
        jTF_Geschaeftspartner_ID.setText("");
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
    private javax.swing.JTextField jTF_Geschaeftspartner_ID;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
