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
 *
 * @author Marco
 */
@Entity
public class Status implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long StatusID;
    
    private String Status;
    
    @OneToMany(mappedBy = "Status")
    private ArrayList<Auftragskopf> Auftragsliste;

    public Status() {
    }

    public Status(long StatusID, String Status) {
        this.StatusID = StatusID;
        this.Status = Status;
    }

    public long getStatusID() {
        return StatusID;
    }

    public void setStatusID(long StatusID) {
        this.StatusID = StatusID;
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