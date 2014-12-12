/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author Marco
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "Autragsart")
@Table(name = "Auftragskopf")
public abstract class Auftragskopf implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long AuftragskopfID;
    
    private String Auftragstext;
    
    private double Wert;
    
    @ManyToOne
    @JoinColumn(name = "Status")
    private Status Status;
    
    @Temporal(TemporalType.DATE)
    private Date Abschlussdatum;
    
    @Temporal(TemporalType.DATE)
    private Date Erfassungsdatum;
    
    @Temporal(TemporalType.DATE)
    private Date Lieferdatum;
    
    @OneToMany(mappedBy = "Auftrag")
    private ArrayList<Auftragsposition> Positionsliste;

    public Auftragskopf() {
    }

    public Auftragskopf(String Auftragstext, double Wert, Status Status, Date Abschlussdatum, Date Erfassungsdatum, Date Lieferdatum) {
        this.Auftragstext = Auftragstext;
        this.Wert = Wert;
        this.Status = Status;
        this.Abschlussdatum = Abschlussdatum;
        this.Erfassungsdatum = Erfassungsdatum;
        this.Lieferdatum = Lieferdatum;
    }

    public long getAuftragskopfID() {
        return AuftragskopfID;
    }

    public String getAuftragstext() {
        return Auftragstext;
    }

    public void setAuftragstext(String Auftragstext) {
        this.Auftragstext = Auftragstext;
    }

    public double getWert() {
        return Wert;
    }

    public void setWert(double Wert) {
        this.Wert = Wert;
    }

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status Status) {
        this.Status = Status;
    }

    public Date getAbschlussdatum() {
        return Abschlussdatum;
    }

    public void setAbschlussdatum(Date Abschlussdatum) {
        this.Abschlussdatum = Abschlussdatum;
    }

    public Date getErfassungsdatum() {
        return Erfassungsdatum;
    }

    public void setErfassungsdatum(Date Erfassungsdatum) {
        this.Erfassungsdatum = Erfassungsdatum;
    }

    public Date getLieferdatum() {
        return Lieferdatum;
    }

    public void setLieferdatum(Date Lieferdatum) {
        this.Lieferdatum = Lieferdatum;
    }

    public ArrayList<Auftragsposition> getPositionsliste() {
        return Positionsliste;
    }

    public void setPositionsliste(ArrayList<Auftragsposition> Positionsliste) {
        this.Positionsliste = Positionsliste;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.AuftragskopfID ^ (this.AuftragskopfID >>> 32));
        hash = 79 * hash + Objects.hashCode(this.Auftragstext);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.Wert) ^ (Double.doubleToLongBits(this.Wert) >>> 32));
        hash = 79 * hash + Objects.hashCode(this.Status);
        hash = 79 * hash + Objects.hashCode(this.Abschlussdatum);
        hash = 79 * hash + Objects.hashCode(this.Erfassungsdatum);
        hash = 79 * hash + Objects.hashCode(this.Lieferdatum);
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
        final Auftragskopf other = (Auftragskopf) obj;
        if (this.AuftragskopfID != other.AuftragskopfID) {
            return false;
        }
        if (!Objects.equals(this.Auftragstext, other.Auftragstext)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Wert) != Double.doubleToLongBits(other.Wert)) {
            return false;
        }
        if (!Objects.equals(this.Status, other.Status)) {
            return false;
        }
        if (!Objects.equals(this.Abschlussdatum, other.Abschlussdatum)) {
            return false;
        }
        if (!Objects.equals(this.Erfassungsdatum, other.Erfassungsdatum)) {
            return false;
        }
        if (!Objects.equals(this.Lieferdatum, other.Lieferdatum)) {
            return false;
        }
        return true;
    }
    
    
    
}
