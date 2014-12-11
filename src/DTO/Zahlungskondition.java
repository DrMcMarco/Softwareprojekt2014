/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
/**
 *
 * @author Marco
 */
@Entity
public class Zahlungskondition implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long ZahlungskonditionID;
    
    @ManyToOne
    @JoinColumn(name = "Auftragsart")
    private Auftragsart Auftragsart;
    
    private double LieferzeitSofort;
    private double SperrzeitWunsch;
    private double Skontozeit1;
    private double Skontozeit2;
    private double Skonto1;
    private double Skonto2;
    private double Mahnzeit1;
    private double Mahnzeit2;
    private double Mahnzeit3;

    public Zahlungskondition() {
    }

    public Zahlungskondition(Auftragsart Auftragsart, double LieferzeitSofort, 
            double SperrzeitWunsch, double Skontozeit1, double Skontozeit2, 
            double Skonto1, double Skonto2, double Mahnzeit1, double Mahnzeit2, 
            double Mahnzeit3) {
        this.Auftragsart = Auftragsart;
        this.LieferzeitSofort = LieferzeitSofort;
        this.SperrzeitWunsch = SperrzeitWunsch;
        this.Skontozeit1 = Skontozeit1;
        this.Skontozeit2 = Skontozeit2;
        this.Skonto1 = Skonto1;
        this.Skonto2 = Skonto2;
        this.Mahnzeit1 = Mahnzeit1;
        this.Mahnzeit2 = Mahnzeit2;
        this.Mahnzeit3 = Mahnzeit3;
    }

    public long getZahlungskonditionID() {
        return ZahlungskonditionID;
    }

    public Auftragsart getAuftragsart() {
        return Auftragsart;
    }

    public void setAuftragsart(Auftragsart Auftragsart) {
        this.Auftragsart = Auftragsart;
    }

    public double getLieferzeitSofort() {
        return LieferzeitSofort;
    }

    public void setLieferzeitSofort(double LieferzeitSofort) {
        this.LieferzeitSofort = LieferzeitSofort;
    }

    public double getSperrzeitWunsch() {
        return SperrzeitWunsch;
    }

    public void setSperrzeitWunsch(double SperrzeitWunsch) {
        this.SperrzeitWunsch = SperrzeitWunsch;
    }

    public double getSkontozeit1() {
        return Skontozeit1;
    }

    public void setSkontozeit1(double Skontozeit1) {
        this.Skontozeit1 = Skontozeit1;
    }

    public double getSkontozeit2() {
        return Skontozeit2;
    }

    public void setSkontozeit2(double Skontozeit2) {
        this.Skontozeit2 = Skontozeit2;
    }

    public double getSkonto1() {
        return Skonto1;
    }

    public void setSkonto1(double Skonto1) {
        this.Skonto1 = Skonto1;
    }

    public double getSkonto2() {
        return Skonto2;
    }

    public void setSkonto2(double Skonto2) {
        this.Skonto2 = Skonto2;
    }

    public double getMahnzeit1() {
        return Mahnzeit1;
    }

    public void setMahnzeit1(double Mahnzeit1) {
        this.Mahnzeit1 = Mahnzeit1;
    }

    public double getMahnzeit2() {
        return Mahnzeit2;
    }

    public void setMahnzeit2(double Mahnzeit2) {
        this.Mahnzeit2 = Mahnzeit2;
    }

    public double getMahnzeit3() {
        return Mahnzeit3;
    }

    public void setMahnzeit3(double Mahnzeit3) {
        this.Mahnzeit3 = Mahnzeit3;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (int) (this.ZahlungskonditionID ^ (this.ZahlungskonditionID >>> 32));
        hash = 67 * hash + Objects.hashCode(this.Auftragsart);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.LieferzeitSofort) ^ (Double.doubleToLongBits(this.LieferzeitSofort) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.SperrzeitWunsch) ^ (Double.doubleToLongBits(this.SperrzeitWunsch) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.Skontozeit1) ^ (Double.doubleToLongBits(this.Skontozeit1) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.Skontozeit2) ^ (Double.doubleToLongBits(this.Skontozeit2) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.Skonto1) ^ (Double.doubleToLongBits(this.Skonto1) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.Skonto2) ^ (Double.doubleToLongBits(this.Skonto2) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.Mahnzeit1) ^ (Double.doubleToLongBits(this.Mahnzeit1) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.Mahnzeit2) ^ (Double.doubleToLongBits(this.Mahnzeit2) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.Mahnzeit3) ^ (Double.doubleToLongBits(this.Mahnzeit3) >>> 32));
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
        final Zahlungskondition other = (Zahlungskondition) obj;
        if (this.ZahlungskonditionID != other.ZahlungskonditionID) {
            return false;
        }
        if (!Objects.equals(this.Auftragsart, other.Auftragsart)) {
            return false;
        }
        if (Double.doubleToLongBits(this.LieferzeitSofort) != Double.doubleToLongBits(other.LieferzeitSofort)) {
            return false;
        }
        if (Double.doubleToLongBits(this.SperrzeitWunsch) != Double.doubleToLongBits(other.SperrzeitWunsch)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Skontozeit1) != Double.doubleToLongBits(other.Skontozeit1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Skontozeit2) != Double.doubleToLongBits(other.Skontozeit2)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Skonto1) != Double.doubleToLongBits(other.Skonto1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Skonto2) != Double.doubleToLongBits(other.Skonto2)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Mahnzeit1) != Double.doubleToLongBits(other.Mahnzeit1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Mahnzeit2) != Double.doubleToLongBits(other.Mahnzeit2)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Mahnzeit3) != Double.doubleToLongBits(other.Mahnzeit3)) {
            return false;
        }
        return true;
    }
    
    
    
}
