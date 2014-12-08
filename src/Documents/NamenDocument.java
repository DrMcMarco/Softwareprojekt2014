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
    public class NamenDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String s, AttributeSet a)
                throws BadLocationException {

            String nichtErlaubt = "0123456789,.^<>_+#!§$%&/()=}{[]?´´´~°:;'*";
            for (int i = 0; i < s.length(); i++) {
                if (nichtErlaubt.contains("" + s.charAt(i))) {
                    Toolkit.getDefaultToolkit().beep();
                    return;
                }
            }
            super.insertString(offs, s, a);

        }
    }