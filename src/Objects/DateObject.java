/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Datumsklasse,
 * Erzeugung eines Datumobjektes
 * @author Luca
 */
public class DateObject {
    /*
    Methode die das heutige Datum in einem String wiedergibt.
    */
    public static String simpleDateFormat(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }
    public static void main(String[] args) {
        Date d = new Date();
        System.out.println(d);
    }
}
