/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.*;
/**
 * Entitätsklasse für den Status.
 * @author Marco Loewe
 */
@Entity
public class Status implements Serializable {
    
    /**
     * Eindeutige Nummer eines Status.
     * Wird generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long StatusID;
    
    /**
     * Name des Status.
     */
    private String Status;
    
    /**
     * Gibt an ob der Status gelöscht ist oder nicht.
     */
    private boolean LKZ;
    
    /**
     * Eine Liste aller Aufträge die einen bestimmtem Status besitzen.
     */
    @OneToMany(mappedBy = "Status")
    private ArrayList<Auftragskopf> Auftragsliste;

    public Status() {
    }

    public Status(String Status) {
        this.Status = Status;
        this.LKZ = false;
    }

    public long getStatusID() {
        return StatusID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public ArrayList<Auftragskopf> getAuftragsliste() {
        return Auftragsliste;
    }

    public void setAuftragsliste(ArrayList<Auftragskopf> Auftragsliste) {
        this.Auftragsliste = Auftragsliste;
    }

    public boolean isLKZ() {
        return LKZ;
    }

    public void setLKZ(boolean LKZ) {
        this.LKZ = LKZ;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.StatusID ^ (this.StatusID >>> 32));
        hash = 23 * hash + Objects.hashCode(this.Status);
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
        final Status other = (Status) obj;
        if (this.StatusID != other.StatusID) {
            return false;
        }
        if (!Objects.equals(this.Status, other.Status)) {
            return false;
        }
        return true;
    }
    
    
}
