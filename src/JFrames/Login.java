package JFrames;

import GUI_Internalframes.Anmeldung;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JOptionPane;

/**
 *
 * @author Luca Terrasi
 */
/* 05.01.2015 Terrasi, Erstellung und Dokumentation*/
/* 18.02.2015 TER, getestet  und freigegeben  */
/* 18.02.2015 TER, getestet und freigegeben */
public class Login extends javax.swing.JFrame {

    /**
     * Variable fuer die Anmeldung.
     */
    private Anmeldung anmeldung;//Variable für die Anmeldungsmaske
    /**
     * Speichervariable für die Größe der DesktopPane.
     */
    private Dimension desktopSize;
    /**
     * Speichervariable für die Größe des InternalFrames.
     */
    private Dimension jInternalFrameSize;
    /**
     * Variable fuer die Meldungen.
     */
    private final String BEENDEN_MELDUNG = "Wollen sie"
            + " wirklich das Programm beenden?";
    /**
     * Variable fuer die Meldungen.
     */
    private final String BEENDEN_MELDUNG_TYP = "Programm beenden";

    /*----------------------------------------------------------*/
    /* Datum      Name    Was */
    /* 05.01.2015 Terrasi, Erstellung und Dokumentation /*
     /* 18.02.2015 Sen     angelegt, Logik und Dokumentation*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Konstruktor
     */
    public Login() {
        initComponents();
        // Initialisierung
        anmeldung = new Anmeldung(this);

        // Maske wird im Zentrum der Frame dargestellt
        setCenterJIF(anmeldung);

        // Maske wird desktopdpane hinzugefügt
        desktopPane.add(anmeldung);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /*----------------------------------------------------------*/
    /* Datum      Name    Was */
    /* 18.02.2015 Sen     angelegt, Logik und Dokumentation*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/

    /**
     * Methode zum Schliessen des Programms.
     *
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // Erzeugen eine Meldung mit Abfrage
        int antwort = JOptionPane.showConfirmDialog(rootPane, BEENDEN_MELDUNG,
                BEENDEN_MELDUNG_TYP, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (antwort == JOptionPane.YES_OPTION) {
            // Antwort ist ja, also schliessen  
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt, Logik und Dokumentation*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode mit der das Fenster geschlossen wird.
     *
     */
    public void schliessen() {
        this.setVisible(false); //Fenster nicht mehr sichtbar machen
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt, Logik und Dokumentation*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode setCenterJIF Methode ermittelt das Zentrum der DesktopPane und
     * setzt das übergebene Internalframe auf die ermittelten Koordinaten.
     *
     * @param jif ,übergebenes InternalFrame
     */
    public void setCenterJIF(Component jif) {
        desktopSize = desktopPane.getSize();
        jInternalFrameSize = jif.getSize();
        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
        jif.setLocation(width, height);
        jif.setVisible(true);
    }

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 05.01.2015 Terrasi angelegt, Logik und Dokumentation*/
    /* 18.02.2015 TER, getestet und freigegeben */
    /*----------------------------------------------------------*/
    /**
     * Methode, um das Objekt der Anmeldung zu erhalten.
     *
     * @return gibt das Objekt der Anmeldung zurueck
     */
    public Anmeldung getAnmeldeFenster() {
        return this.anmeldung;
    }

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
