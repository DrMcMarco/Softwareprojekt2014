/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    private boolean buchstaben;
    private String erlaubteZeichen;

    /* SETTER GENERIEREN */
    private int maxLen = -1;

    public UniversalDocument(String e, boolean b) {
        this.erlaubteZeichen = e;
        this.buchstaben = b;
    }

    @Override
    public void insertString(int offs, String s, AttributeSet a)
            throws BadLocationException {

        /* Zeichen pruefen */
        for (int i = 0; i < s.length(); i++) {
            if (!this.erlaubteZeichen.contains("" + s.charAt(i))
                    && !(this.buchstaben
                            ? Character.isLetter(s.charAt(i))
                            : false)) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
        }

        /* Laenge pruefen */
        if (maxLen != -1 && (super.getLength() + s.length() > this.maxLen)) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        super.insertString(offs, s, a);
    }
}
