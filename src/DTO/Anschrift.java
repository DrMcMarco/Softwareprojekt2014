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
 * Entitätsklasse für Anschriften.
 * - Oberklasse für Liefer-/Rechnungsanschrift
 * - In der Spalte "Typ" wird die Art der Anschrift angegeben
 * - Enthält neben der Anschrift auch Informationen des Geschäftspartners
 * @author Marco Loewe
 * @version 1.0
 */

@Entity
@Inheritance
@DiscriminatorColumn(name = "Typ")
@Table(name = "Anschrift")
public abstract class Anschrift implements Serializable {
    
    /**
     * ID der Anschrift, wird generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AnschriftID;
    
    /**
    * Nachname des Geschäftspartners.
    */
    private String Name;
    
    /**
     * Vorname des Geschäftspartners.
     */
    private String Vorname;
    
    /**
     * Anrede des Geschäftspartners.
     */
    private String Titel;
    
    /**
     * Straßenname für die Anschrift.
     */
    private String Strasse;
    
    /**
     * Hausnummer des Anschrift.
     */
    private String Hausnummer;
    
    /**
     * Postleitzahl der Anschrift.
     */
    private String PLZ;
    
    /**
     * Ort der Anschrift.
     */
    private String Ort;
    
    /**
     * Staat für die Anschrift.
     */
    private String Staat;
    
    /**
     * Telefonnummer des Geschäftspartners.
     */
    private String Telefon;
    
    /**
     * Faxnummer des Geschäftspartners.
     */
    private String FAX;
    
    /**
     * Emailadresse des Geschäftspartners.
     */
    private String Email;
    
    /**
     * Geburtsdatum des Geschäftspartner, muss für die Datenbank als Datum 
     * markiert werden.
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Geburtsdatum;
    
    /**
     * Erfassungsdatun des Geschäftspartner, muss für die Datenbank als Datum 
     * markiert werden.
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Erfassungsdatum;
    /**
     * Generiert.
     */
    private boolean LKZ;

    /**
     * Leerer Konstruktur
     */
    public Anschrift() {
    }

    /**
     * Generiert.
     * @param Name Name
     * @param Vorname Vorname
     * @param Titel Titel
     * @param Strasse Strasse
     * @param Hausnummer Hausnummer
     * @param PLZ PLZ
     * @param Ort Ort
     * @param Staat Staat
     * @param Telefon Telefon
     * @param FAX FAX
     * @param Email Email
     * @param Geburtsdatum Geburtsdatum
     * @param Erfassungsdatum  Erfassungsdatum
     */
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

    /**
     * Generiert.
     * @return id
     */
    public long getAnschriftID() {
        return AnschriftID;
    }

    /**
     * Generiert.
     * @return name
     */
    public String getName() {
        return Name;
    }

    /**
     * Generiert.
     * @param Name name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Generiert.
     * @return vname
     */
    public String getVorname() {
        return Vorname;
    }

    /**
     * Generiert.
     * @param Vorname vname
     */
    public void setVorname(String Vorname) {
        this.Vorname = Vorname;
    }

    /**
     * Generiert.
     * @return titel
     */
    public String getTitel() {
        return Titel;
    }

    /**
     * Generiert.
     * @param Titel titel
     */
    public void setTitel(String Titel) {
        this.Titel = Titel;
    }

    /**
     * Generiert.
     * @return strasse
     */
    public String getStrasse() {
        return Strasse;
    }

    /**
     * Generiert.
     * @param Strasse strasse
     */
    public void setStrasse(String Strasse) {
        this.Strasse = Strasse;
    }

    /**
     * Generiert.
     * @return hausnr
     */
    public String getHausnummer() {
        return Hausnummer;
    }

    /**
     * Generiert.
     * @param Hausnummer hsnr
     */
    public void setHausnummer(String Hausnummer) {
        this.Hausnummer = Hausnummer;
    }

    /**
     * Generiert.
     * @return plz
     */
    public String getPLZ() {
        return PLZ;
    }

    /**
     * Generiert.
     * @param PLZ 
     */
    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    /**
     * Generiert.
     * @return ort
     */
    public String getOrt() {
        return Ort;
    }

    /**
     * Generiert.
     * @param Ort ort
     */
    public void setOrt(String Ort) {
        this.Ort = Ort;
    }

    /**
     * Generiert.
     * @return staat
     */
    public String getStaat() {
        return Staat;
    }

    /**
     * Generiert.
     * @param Staat staat
     */
    public void setStaat(String Staat) {
        this.Staat = Staat;
    }

    /**
     * Generiert.
     * @return telefon
     */
    public String getTelefon() {
        return Telefon;
    }

    /**
     * Generiert.
     * @param Telefon telefon
     */
    public void setTelefon(String Telefon) {
        this.Telefon = Telefon;
    }

    /**
     * Generiert.
     * @return fax
     */
    public String getFAX() {
        return FAX;
    }

    /**
     * Generiert.
     * @param FAX fax
     */
    public void setFAX(String FAX) {
        this.FAX = FAX;
    }

    /**
     * Generiert.
     * @return email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Generiert.
     * @param Email email
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * Generiert.
     * @return datum
     */
    public Date getGeburtsdatum() {
        return Geburtsdatum;
    }

    /**
     * Generiert.
     * @param Geburtsdatum gebdatum
     */
    public void setGeburtsdatum(Date Geburtsdatum) {
        this.Geburtsdatum = Geburtsdatum;
    }

    /**
     * Generiert.
     * @return datum
     */
    public Date getErfassungsdatum() {
        return Erfassungsdatum;
    }

    /**
     * Generiert.
     * @param Erfassungsdatum erfassungsdatum
     */
    public void setErfassungsdatum(Date Erfassungsdatum) {
        this.Erfassungsdatum = Erfassungsdatum;
    }

    /**
     * Generiert.
     * @return ob gelöscht
     */
    public boolean isLKZ() {
        return LKZ;
    }

    /**
     * Generiert.
     * @param LKZ lkz
     */
    public void setLKZ(boolean LKZ) {
        this.LKZ = LKZ;
    }

    /**
     * Generiert.
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.AnschriftID ^ (this.AnschriftID >>> 32));
        hash = 79 * hash + Objects.hashCode(this.Name);
        hash = 79 * hash + Objects.hashCode(this.Vorname);
        hash = 79 * hash + Objects.hashCode(this.Titel);
        hash = 79 * hash + Objects.hashCode(this.Strasse);
        hash = 79 * hash + Objects.hashCode(this.Hausnummer);
        hash = 79 * hash + Objects.hashCode(this.PLZ);
        hash = 79 * hash + Objects.hashCode(this.Ort);
        hash = 79 * hash + Objects.hashCode(this.Staat);
        hash = 79 * hash + Objects.hashCode(this.Telefon);
        hash = 79 * hash + Objects.hashCode(this.FAX);
        hash = 79 * hash + Objects.hashCode(this.Email);
        hash = 79 * hash + Objects.hashCode(this.Geburtsdatum);
        hash = 79 * hash + Objects.hashCode(this.Erfassungsdatum);
        hash = 79 * hash + (this.LKZ ? 1 : 0);
        return hash;
    }

    /**
     * Generiert.
     * @param obj Ein Objekt.
     * @return Wahr oder Falsch.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final Anschrift other = (Anschrift) obj;
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
        if (this.LKZ != other.LKZ) {
            return false;
        }
        return true;
    }
    
    /**
     * Generiert.
     * @return Tostring
     */
    @Override
    public String toString() {
        return "Anschrift{" + "AnschriftID=" + AnschriftID 
                + ", Name=" + Name + ", Vorname=" + Vorname + ", Titel=" 
                + Titel + ", Strasse=" + Strasse + ", Hausnummer=" + Hausnummer 
                + ", PLZ=" + PLZ + ", Ort=" + Ort + ", Staat=" + Staat 
                + ", Telefon=" + Telefon + ", FAX=" + FAX + ", Email=" 
                + Email + ", Geburtsdatum=" + Geburtsdatum 
                + ", Erfassungsdatum=" + Erfassungsdatum + '}';
    }
    
    
    
}
