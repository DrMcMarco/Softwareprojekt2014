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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ZahlungskonditionID;
    
    private String Auftragsart;
    private int LieferzeitSofort;
    private int SperrzeitWunsch;
    private int Skontozeit1;
    private int Skontozeit2;
    private double Skonto1;
    private double Skonto2;
    private int Mahnzeit1;
    private int Mahnzeit2;
    private int Mahnzeit3;
    private boolean LKZ;

    public Zahlungskondition() {
    }

    public Zahlungskondition(String Auftragsart, int LieferzeitSofort, 
            int SperrzeitWunsch, int Skontozeit1, int Skontozeit2, 
            double Skonto1, double Skonto2, int Mahnzeit1, int Mahnzeit2, 
            int Mahnzeit3) {
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
        this.LKZ = false;
    }

    public long getZahlungskonditionID() {
        return ZahlungskonditionID;
    }

    public String getAuftragsart() {
        return Auftragsart;
    }

    public void setAuftragsart(String Auftragsart) {
        this.Auftragsart = Auftragsart;
    }
    

    public int getLieferzeitSofort() {
        return LieferzeitSofort;
    }

    public void setLieferzeitSofort(int LieferzeitSofort) {
        this.LieferzeitSofort = LieferzeitSofort;
    }

    public int getSperrzeitWunsch() {
        return SperrzeitWunsch;
    }

    public void setSperrzeitWunsch(int SperrzeitWunsch) {
        this.SperrzeitWunsch = SperrzeitWunsch;
    }

    public int getSkontozeit1() {
        return Skontozeit1;
    }

    public void setSkontozeit1(int Skontozeit1) {
        this.Skontozeit1 = Skontozeit1;
    }

    public int getSkontozeit2() {
        return Skontozeit2;
    }

    public void setSkontozeit2(int Skontozeit2) {
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

    public int getMahnzeit1() {
        return Mahnzeit1;
    }

    public void setMahnzeit1(int Mahnzeit1) {
        this.Mahnzeit1 = Mahnzeit1;
    }

    public int getMahnzeit2() {
        return Mahnzeit2;
    }

    public void setMahnzeit2(int Mahnzeit2) {
        this.Mahnzeit2 = Mahnzeit2;
    }

    public int getMahnzeit3() {
        return Mahnzeit3;
    }

    public void setMahnzeit3(int Mahnzeit3) {
        this.Mahnzeit3 = Mahnzeit3;
    }

    public boolean isLKZ() {
        return LKZ;
    }

    public void setLKZ(boolean LKZ) {
        this.LKZ = LKZ;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.ZahlungskonditionID ^ (this.ZahlungskonditionID >>> 32));
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
        if (this.LieferzeitSofort != other.LieferzeitSofort) {
            return false;
        }
        if (this.SperrzeitWunsch != other.SperrzeitWunsch) {
            return false;
        }
        if (this.Skontozeit1 != other.Skontozeit1) {
            return false;
        }
        if (this.Skontozeit2 != other.Skontozeit2) {
            return false;
        }
        if (Double.doubleToLongBits(this.Skonto1) != Double.doubleToLongBits(other.Skonto1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Skonto2) != Double.doubleToLongBits(other.Skonto2)) {
            return false;
        }
        if (this.Mahnzeit1 != other.Mahnzeit1) {
            return false;
        }
        if (this.Mahnzeit2 != other.Mahnzeit2) {
            return false;
        }
        if (this.Mahnzeit3 != other.Mahnzeit3) {
            return false;
        }
        if (this.LKZ != other.LKZ) {
            return false;
        }
        return true;
    }
}
