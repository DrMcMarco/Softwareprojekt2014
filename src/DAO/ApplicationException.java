/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

/**
 *
 * @author Simon <Simon.Simon at your.org>
 */
public class ApplicationException extends Exception {
    
    private String title;

    public ApplicationException() {
    }

    public ApplicationException(String title, String message) {
        super(message);
        this.title = title;
    }

    public ApplicationException(String title, String message, Throwable cause) {
        super(message, cause);
        this.title = title;
    }

    public ApplicationException(String title, Throwable cause) {
        super(cause);
        this.title = title;
    }
    
    
}
