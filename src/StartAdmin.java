/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import GUI_Internalframes.*;
import Suche.*;
import UserHauptmenue.Hauptmenue_User;
import AdminHauptmenue.Hauptmenue_Admin;
import javax.swing.JOptionPane;

/**
 *
 * @author Luca Terrasi
 */
public class StartAdmin extends javax.swing.JFrame {

    /**
     * Definition der Attribute
     */
    /*
     Stringvariablen für die einzelnen Meldungen
     */
    private final String Beenden_Meldung = "Wollen sie wirklich das Programm beenden und sich abmelden?";
    private final String Beenden_Meldung_Typ = "Programm beenden";

    AuftragskopfAnlegen auftragskopfanlegen;
    AuftragskopfAendern auftragskopfaendern;
//    AuftragskopfAnzeigen auftragskopfanzeigen;

    /**
     * Creates new form Start
     */
    public StartAdmin() {
        initComponents();
        auftragskopfanlegen = new AuftragskopfAnlegen();
//        auftragskopfanzeigen = new AuftragskopfAnzeigen();
        auftragskopfaendern = new AuftragskopfAendern();
        desktopPane.add(auftragskopfanlegen);
        desktopPane.add(auftragskopfaendern);
//        desktopPane.add(auftragskopfanzeigen);
//        auftragskopfaendern.setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        jM_Navigation = new javax.swing.JMenu();
        jM_AuftragVerwalten = new javax.swing.JMenu();
        jM_Auftragskopf = new javax.swing.JMenu();
        jMI_AuftragskopfAnlegen = new javax.swing.JMenuItem();
        jMI_AuftragkopfAEndern = new javax.swing.JMenuItem();
        jMI_AuftragskopfAnzeigen = new javax.swing.JMenuItem();
        jM_Auftragspos = new javax.swing.JMenu();
        jMI_AuftragsposAEndern = new javax.swing.JMenuItem();
        jMI_AuftragsposAnzeigen = new javax.swing.JMenuItem();
        jM_ArtikelVerwalten = new javax.swing.JMenu();
        jMI_ArtikelAnlegen = new javax.swing.JMenuItem();
        jMI_ArtikelAEndern = new javax.swing.JMenuItem();
        jMI_ArtikelAnzeigen = new javax.swing.JMenuItem();
        jM_GPVerwalten = new javax.swing.JMenu();
        jMI_GPAnlegen = new javax.swing.JMenuItem();
        jMI_GPAEndern = new javax.swing.JMenuItem();
        jMI_GPAnzeigen = new javax.swing.JMenuItem();
        jM_ZKVerwalten = new javax.swing.JMenu();
        jMI_ZKAnlegen = new javax.swing.JMenuItem();
        jMI_ZKAEndern = new javax.swing.JMenuItem();
        jMI_ZKAnzeigen = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMI_ZKAnlegen1 = new javax.swing.JMenuItem();
        jMI_ZKAEndern1 = new javax.swing.JMenuItem();
        jMI_ZKAnzeigen1 = new javax.swing.JMenuItem();
        jM_Logout = new javax.swing.JMenu();
        jMI_Logout = new javax.swing.JMenuItem();
        jM_Hilfe = new javax.swing.JMenu();
        jMI_Benutzerhandbuch = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Hauptmeü");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jM_Navigation.setMnemonic('n');
        jM_Navigation.setText("Navigation");

        jM_AuftragVerwalten.setMnemonic('a');
        jM_AuftragVerwalten.setText("Auftrag Verwalten");
        jM_AuftragVerwalten.setActionCommand("JMenu1");

        jM_Auftragskopf.setText("Auftragskopf");

        jMI_AuftragskopfAnlegen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragskopfAnlegen.setText("Anlegen");
        jMI_AuftragskopfAnlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragskopfAnlegenActionPerformed(evt);
            }
        });
        jM_Auftragskopf.add(jMI_AuftragskopfAnlegen);

        jMI_AuftragkopfAEndern.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragkopfAEndern.setText("Ändern");
        jMI_AuftragkopfAEndern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_AuftragkopfAEndernActionPerformed(evt);
            }
        });
        jM_Auftragskopf.add(jMI_AuftragkopfAEndern);

        jMI_AuftragskopfAnzeigen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragskopfAnzeigen.setText("Anzeigen");
        jM_Auftragskopf.add(jMI_AuftragskopfAnzeigen);

        jM_AuftragVerwalten.add(jM_Auftragskopf);

        jM_Auftragspos.setText("Auftragsposition");

        jMI_AuftragsposAEndern.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragsposAEndern.setText("Ändern");
        jM_Auftragspos.add(jMI_AuftragsposAEndern);

        jMI_AuftragsposAnzeigen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMI_AuftragsposAnzeigen.setText("Anzeigen");
        jM_Auftragspos.add(jMI_AuftragsposAnzeigen);

        jM_AuftragVerwalten.add(jM_Auftragspos);

        jM_Navigation.add(jM_AuftragVerwalten);

        jM_ArtikelVerwalten.setMnemonic('a');
        jM_ArtikelVerwalten.setText("Artikel Verwalten");
        jM_ArtikelVerwalten.setActionCommand("JMenu1");

        jMI_ArtikelAnlegen.setText("Anlegen");
        jM_ArtikelVerwalten.add(jMI_ArtikelAnlegen);

        jMI_ArtikelAEndern.setText("Ändern");
        jM_ArtikelVerwalten.add(jMI_ArtikelAEndern);

        jMI_ArtikelAnzeigen.setText("Anzeigen");
        jM_ArtikelVerwalten.add(jMI_ArtikelAnzeigen);

        jM_Navigation.add(jM_ArtikelVerwalten);

        jM_GPVerwalten.setText("Geschäftspartner Verwalten");
        jM_GPVerwalten.setActionCommand("JMenu1");

        jMI_GPAnlegen.setText("Anlegen");
        jM_GPVerwalten.add(jMI_GPAnlegen);

        jMI_GPAEndern.setText("Ändern");
        jM_GPVerwalten.add(jMI_GPAEndern);

        jMI_GPAnzeigen.setText("Anzeigen");
        jM_GPVerwalten.add(jMI_GPAnzeigen);

        jM_Navigation.add(jM_GPVerwalten);

        jM_ZKVerwalten.setText("Zahlungskonditionen Verwalten");
        jM_ZKVerwalten.setActionCommand("JMenu1");

        jMI_ZKAnlegen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMI_ZKAnlegen.setText("Anlegen");
        jM_ZKVerwalten.add(jMI_ZKAnlegen);

        jMI_ZKAEndern.setText("Ändern");
        jM_ZKVerwalten.add(jMI_ZKAEndern);

        jMI_ZKAnzeigen.setText("Anzeigen");
        jM_ZKVerwalten.add(jMI_ZKAnzeigen);

        jM_Navigation.add(jM_ZKVerwalten);

        jMenu1.setText("Benutzer Verwalten");

        jMI_ZKAnlegen1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMI_ZKAnlegen1.setText("Anlegen");
        jMenu1.add(jMI_ZKAnlegen1);

        jMI_ZKAEndern1.setText("Ändern");
        jMenu1.add(jMI_ZKAEndern1);

        jMI_ZKAnzeigen1.setText("Anzeigen");
        jMenu1.add(jMI_ZKAnzeigen1);

        jM_Navigation.add(jMenu1);

        menuBar.add(jM_Navigation);

        jM_Logout.setText("Logout");
        jM_Logout.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jMI_Logout.setText("Abmelden");
        jMI_Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_LogoutActionPerformed(evt);
            }
        });
        jM_Logout.add(jMI_Logout);

        menuBar.add(jM_Logout);
        jM_Logout.getAccessibleContext().setAccessibleDescription("");

        jM_Hilfe.setText("Hilfe");

        jMI_Benutzerhandbuch.setText("Benutzerhandbuch");
        jMI_Benutzerhandbuch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMI_BenutzerhandbuchActionPerformed(evt);
            }
        });
        jM_Hilfe.add(jMI_Benutzerhandbuch);

        menuBar.add(jM_Hilfe);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
     * Methode in der definiert wird was beim betätigen des Logouts in der
     * Navigation passiert
     *
     * @param evt
     */
    private void jMI_LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_LogoutActionPerformed
        // Erzeugen eine Meldung mit Abfrage
        int antwort = JOptionPane.showConfirmDialog(rootPane, Beenden_Meldung,
                Beenden_Meldung_Typ, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (antwort == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jMI_LogoutActionPerformed

    /**
     * Methode in der definiert wird was des Hilfebuttons passiert wenn man ihn
     * betätigt
     *
     * @param evt
     */
    private void jMI_BenutzerhandbuchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_BenutzerhandbuchActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Benutzerhandbuch.", "Informationen",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMI_BenutzerhandbuchActionPerformed
    /**
     * Aktion in der die Auftragskopfanlegen Maske aufgerufen wird
     */
    private void jMI_AuftragskopfAnlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragskopfAnlegenActionPerformed
        // Aufruf der AuftragskopfAnlegen Maske.
        auftragskopfanlegen.setVisible(true);
    }//GEN-LAST:event_jMI_AuftragskopfAnlegenActionPerformed
    /**
     * Aktion in der die Auftragskopfändern Maske aufgerufen wird
     */
    private void jMI_AuftragkopfAEndernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMI_AuftragkopfAEndernActionPerformed
        // Aufruf der AuftragskopfAendern Maske.
        auftragskopfaendern.setVisible(true);
    }//GEN-LAST:event_jMI_AuftragkopfAEndernActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // Erzeugen eine Meldung mit Abfrage
        int antwort = JOptionPane.showConfirmDialog(rootPane, Beenden_Meldung,
                Beenden_Meldung_Typ, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (antwort == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Start().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem jMI_ArtikelAEndern;
    private javax.swing.JMenuItem jMI_ArtikelAnlegen;
    private javax.swing.JMenuItem jMI_ArtikelAnzeigen;
    private javax.swing.JMenuItem jMI_AuftragkopfAEndern;
    private javax.swing.JMenuItem jMI_AuftragskopfAnlegen;
    private javax.swing.JMenuItem jMI_AuftragskopfAnzeigen;
    private javax.swing.JMenuItem jMI_AuftragsposAEndern;
    private javax.swing.JMenuItem jMI_AuftragsposAnzeigen;
    private javax.swing.JMenuItem jMI_Benutzerhandbuch;
    private javax.swing.JMenuItem jMI_GPAEndern;
    private javax.swing.JMenuItem jMI_GPAnlegen;
    private javax.swing.JMenuItem jMI_GPAnzeigen;
    private javax.swing.JMenuItem jMI_Logout;
    private javax.swing.JMenuItem jMI_ZKAEndern;
    private javax.swing.JMenuItem jMI_ZKAEndern1;
    private javax.swing.JMenuItem jMI_ZKAnlegen;
    private javax.swing.JMenuItem jMI_ZKAnlegen1;
    private javax.swing.JMenuItem jMI_ZKAnzeigen;
    private javax.swing.JMenuItem jMI_ZKAnzeigen1;
    private javax.swing.JMenu jM_ArtikelVerwalten;
    private javax.swing.JMenu jM_AuftragVerwalten;
    private javax.swing.JMenu jM_Auftragskopf;
    private javax.swing.JMenu jM_Auftragspos;
    private javax.swing.JMenu jM_GPVerwalten;
    private javax.swing.JMenu jM_Hilfe;
    private javax.swing.JMenu jM_Logout;
    private javax.swing.JMenu jM_Navigation;
    private javax.swing.JMenu jM_ZKVerwalten;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
