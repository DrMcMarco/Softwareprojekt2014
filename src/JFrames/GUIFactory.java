package JFrames;


import DAO.DataAccessObject;
import javax.swing.JFrame;
import Interfaces.InterfaceViewsFunctionality;
import java.awt.Component;
import java.util.ArrayList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luca
 */
public class GUIFactory {
    
    ArrayList<Component> liste;
    private DataAccessObject DAO;
     
    
    public GUIFactory(){
        liste = new ArrayList<>();
        DAO = new DataAccessObject();
    }
    

     public void setComponent(Component component) {

            liste.add(component);
            System.out.println(liste);
    }
    
     
     public Component zurueckButton(){
     Component component = null ;
         try {
         component = liste.get(liste.size()-1);
         liste.remove(liste.size()-1);
         } catch (ArrayIndexOutOfBoundsException e) {
             System.out.println("Es gibt keine weitere Maske.");
         }
         return component;
     }
     
     public Component getComponent(){
         return liste.get(liste.size() - 1);
     }

    /**
     * @return the DAO
     */
    public DataAccessObject getDAO() {
        return DAO;
    }
    
      /**
     * 
     * @param dao 
     */
    public void setDAO(DataAccessObject dao){
        this.DAO = dao;
    }
    

}
