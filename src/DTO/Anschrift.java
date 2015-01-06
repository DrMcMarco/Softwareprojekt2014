/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author Marco
 */

@Entity
@Inheritance
@DiscriminatorColumn(name = "Typ")
@Table(name = "Anschrift")
public abstract class Anschrift implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AnschriftID;
    
    private String Name;
    private String Vorname;
    private String Titel;
    private String Strasse;
    private String Hausnummer;
    private String PLZ;
    private String Ort;
    private String Staat;
    private String Telefon;
    private String FAX;
    private String Email;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Geburtsdatum;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Erfassungsdatum;
    
    private boolean LKZ;

    public Anschrift() {
    }

    public Anschrift(String Name, String Vorname,
            String Titel, String Strasse, String Hausnummer, String PLZ,
            String Ort, String Staat, String Telefon, String FAX, String Email,
            Date Geburtsdatum, Date Erfassungsdatum) {
        this.Name = Name;
        this.Vorname = Vorname;
        this.Titel = Titel;
        this.Strasse = Strasse;
        this.Hausnummer = Hausnummer;
        this.PLZ = PLZ;
        this.Ort = Ort;
        this.Staat = Staat;
        this.Telefon = Telefon;
        this.FAX = FAX;
        this.Email = Email;
        this.Geburtsdatum = Geburtsdatum;
        this.Erfassungsdatum = Erfassungsdatum;
        this.LKZ = false;
    }

    public long getAnschriftID() {
        return AnschriftID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getVorname() {
        return Vorname;
    }

    public void setVorname(String Vorname) {
        this.Vorname = Vorname;
    }

    public String getTitel() {
        return Titel;
    }

    public void setTitel(String Titel) {
        this.Titel = Titel;
    }

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String Strasse) {
        this.Strasse = Strasse;
    }

    public String getHausnummer() {
        return Hausnummer;
    }

    public void setHausnummer(String Hausnummer) {
        this.Hausnummer = Hausnummer;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String Ort) {
        this.Ort = Ort;
    }

    public String getStaat() {
        return Staat;
    }

    public void setStaat(String Staat) {
        this.Staat = Staat;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String Telefon) {
        this.Telefon = Telefon;
    }

    public String getFAX() {
        return FAX;
    }

    public void setFAX(String FAX) {
        this.FAX = FAX;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Date getGeburtsdatum() {
        return Geburtsdatum;
    }

    public void setGeburtsdatum(Date Geburtsdatum) {
        this.Geburtsdatum = Geburtsdatum;
    }

    public Date getErfassungsdatum() {
        return Erfassungsdatum;
    }

    public void setErfassungsdatum(Date Erfassungsdatum) {
        this.Erfassungsdatum = Erfassungsdatum;
    }

    public boolean isLKZ() {
        return LKZ;
    }

    public void setLKZ(boolean LKZ) {
        this.LKZ = LKZ;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (int) (this.AnschriftID ^ (this.AnschriftID >>> 32));
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
        final Anschrift other = (Anschrift) obj;
        if (this.AnschriftID != other.AnschriftID) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "Anschrift{" + "AnschriftID=" + AnschriftID + ", Name=" + Name + ", Vorname=" + Vorname + ", Titel=" + Titel + ", Strasse=" + Strasse + ", Hausnummer=" + Hausnummer + ", PLZ=" + PLZ + ", Ort=" + Ort + ", Staat=" + Staat + ", Telefon=" + Telefon + ", FAX=" + FAX + ", Email=" + Email + ", Geburtsdatum=" + Geburtsdatum + ", Erfassungsdatum=" + Erfassungsdatum + '}';
    }
    
    
    
}
