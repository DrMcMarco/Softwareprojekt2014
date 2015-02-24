/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entitätsklasse für die Steuertabelle.
 * Diese Tabelle enthält einige Informationen über das Programm, z.B. wann das 
 * Programm das letzte Mal gestartet wurde.
 * @author Marco Loewe
 */
@Entity
public class Steuertabelle implements Serializable {
    
    /**
     * Name des Steuerparameters
     */
    @Id
    private String Steuerparameter;
    
    /**
     * Wert des Steuerparameters
     */
    private String Wert;
    
    public Steuertabelle() {
        
    }

    public Steuertabelle(String Parameter, String Wert) {
        this.Steuerparameter = Parameter;
        this.Wert = Wert;
    }

    public String getParameter() {
        return Steuerparameter;
    }

    public void setParameter(String Parameter) {
        this.Steuerparameter = Parameter;
    }

    public String getWert() {
        return Wert;
    }

    public void setWert(String Wert) {
        this.Wert = Wert;
    } 
}
