package Documents;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Tahir
 */
public class UniversalDocument extends PlainDocument {

    /**
     * Boolische Variable, ob Buchtsaben zulässig sind oder nicht.
     */
    private boolean buchstaben;
    /**
     * String Variable, die beinhaltet, welche Zeichen erlaubt sind.
     */
    private String erlaubteZeichen;
    /**
     * Varibale fuer die Laenge der Eingabe.
     */
    private int maxLen = -1;

    /**
     * Konstruktor 1: Angabe ob buchstaben erlaubt sind und welche Zeichen
     * erlaubt sind
     *
     * @param e erlaubte Zeichen
     * @param b buchtstaben erlaubt oder nicht.
     */
    public UniversalDocument(String e, boolean b) {
        this.erlaubteZeichen = e;
        this.buchstaben = b;
    }

    /**
     * Konstruktor 2: Angabe ob buchstaben erlaubt sind und welche Zeichen
     * erlaubt sind. Ausserdem kann die laenge der Eingabe durch die varibale l
     * festgelegt werden.
     *
     * @param e erlaubte Zeichen
     * @param b buchtstaben erlaubt oder nicht.
     * @param l anzahl der Eingabenlaenge.
     */
    public UniversalDocument(String e, boolean b, int l) {
        this.erlaubteZeichen = e;
        this.buchstaben = b;
        this.maxLen = l;
    }

    /*
     * Historie:
     * 03.12.2014   Sen     angelegt
     */
    /**
     * insert String methode, die die Eingaben ueberprueft. Bei einer nicht
     * zulaessigen Eingabe ertoent ein Piepton.
     *
     * @param offs offset
     * @param s erlaubte Zeichen
     * @param a AttributeSet
     * @throws BadLocationException Wirft eine Exception
     */
    @Override
    public void insertString(int offs, String s, AttributeSet a)
            throws BadLocationException {

//      Zeichen pruefen
        for (int i = 0; i < s.length(); i++) {
            if (!this.erlaubteZeichen.contains("" + s.charAt(i))
                    && !(this.buchstaben
                            ? Character.isLetter(s.charAt(i))
                            : false)) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
        }

//      Laenge pruefen 
        if (maxLen != -1 && (super.getLength() + s.length() > this.maxLen)) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        super.insertString(offs, s, a);
    }
}
