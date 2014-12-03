/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Objects;
import javax.persistence.*;
/**
 *
 * @author Marco
 */
@Entity
public class Benutzer implements java.io.Serializable {
    
    @Id
    private String Benutzername;
    
    private String Passwort;
    
    private boolean istAdmin;
    
    public Benutzer() {
        
    }

    public String getBenutzername() {
        return Benutzername;
    }

    public void setBenutzername(String Benutzername) {
        this.Benutzername = Benutzername;
    }

    public String getPasswort() {
        return Passwort;
    }

    public void setPasswort(String Passwort) {
        this.Passwort = Passwort;
    }

    public boolean isIstAdmin() {
        return istAdmin;
    }

    public void setIstAdmin(boolean istAdmin) {
        this.istAdmin = istAdmin;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.Benutzername);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Benutzer other = (Benutzer) obj;
        if (!Objects.equals(this.Benutzername, other.Benutzername)) {
            return false;
        }
        return true;
    }
}
