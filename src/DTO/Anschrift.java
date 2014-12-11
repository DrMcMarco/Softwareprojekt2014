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
public class Anschrift implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long AnschriftID;
    
    @ManyToOne
    @JoinColumn(name = "Typ")
    private ATyp AnschriftTyp;
    
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

    public Anschrift() {
    }

    public Anschrift(ATyp AnschriftTyp, String Name, String Vorname,
            String Titel, String Strasse, String Hausnummer, String PLZ,
            String Ort, String Staat, String Telefon, String FAX, String Email,
            Date Geburtsdatum, Date Erfassungsdatum) {
        this.AnschriftTyp = AnschriftTyp;
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
    }

    public long getAnschriftID() {
        return AnschriftID;
    }

    public ATyp getAnschriftTyp() {
        return AnschriftTyp;
    }

    public void setAnschriftTyp(ATyp AnschriftTyp) {
        this.AnschriftTyp = AnschriftTyp;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (int) (this.AnschriftID ^ (this.AnschriftID >>> 32));
        hash = 67 * hash + Objects.hashCode(this.Name);
        hash = 67 * hash + Objects.hashCode(this.Vorname);
        hash = 67 * hash + Objects.hashCode(this.Titel);
        hash = 67 * hash + Objects.hashCode(this.Strasse);
        hash = 67 * hash + Objects.hashCode(this.Hausnummer);
        hash = 67 * hash + Objects.hashCode(this.PLZ);
        hash = 67 * hash + Objects.hashCode(this.Ort);
        hash = 67 * hash + Objects.hashCode(this.Staat);
        hash = 67 * hash + Objects.hashCode(this.Telefon);
        hash = 67 * hash + Objects.hashCode(this.FAX);
        hash = 67 * hash + Objects.hashCode(this.Email);
        hash = 67 * hash + Objects.hashCode(this.Geburtsdatum);
        hash = 67 * hash + Objects.hashCode(this.Erfassungsdatum);
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
        if (!Objects.equals(this.Name, other.Name)) {
            return false;
        }
        if (!Objects.equals(this.Vorname, other.Vorname)) {
            return false;
        }
        if (!Objects.equals(this.Titel, other.Titel)) {
            return false;
        }
        if (!Objects.equals(this.Strasse, other.Strasse)) {
            return false;
        }
        if (!Objects.equals(this.Hausnummer, other.Hausnummer)) {
            return false;
        }
        if (!Objects.equals(this.PLZ, other.PLZ)) {
            return false;
        }
        if (!Objects.equals(this.Ort, other.Ort)) {
            return false;
        }
        if (!Objects.equals(this.Staat, other.Staat)) {
            return false;
        }
        if (!Objects.equals(this.Telefon, other.Telefon)) {
            return false;
        }
        if (!Objects.equals(this.FAX, other.FAX)) {
            return false;
        }
        if (!Objects.equals(this.Email, other.Email)) {
            return false;
        }
        if (!Objects.equals(this.Geburtsdatum, other.Geburtsdatum)) {
            return false;
        }
        if (!Objects.equals(this.Erfassungsdatum, other.Erfassungsdatum)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Anschrift{" + "AnschriftID=" + AnschriftID + ", Name=" + Name + ", Vorname=" + Vorname + ", Titel=" + Titel + ", Strasse=" + Strasse + ", Hausnummer=" + Hausnummer + ", PLZ=" + PLZ + ", Ort=" + Ort + ", Staat=" + Staat + ", Telefon=" + Telefon + ", FAX=" + FAX + ", Email=" + Email + ", Geburtsdatum=" + Geburtsdatum + ", Erfassungsdatum=" + Erfassungsdatum + '}';
    }
    
    
    
}
