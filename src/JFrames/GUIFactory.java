package JFrames;


import DAO.DataAccessObject;
import javax.swing.JFrame;
import java.awt.Component;
import java.util.ArrayList;

/**
 *
 * @author Luca
 * 
 * 16.12.2014 Terrasi, Erstellung
 */
public  class  GUIFactory {
    
    static ArrayList<Component> liste;
    private static final DataAccessObject DAO = new DataAccessObject();
     
    
    public GUIFactory(){
        liste = new ArrayList<>();
        //DAO = new DataAccessObject();
    }
    

     public void setComponent(Component component) {
            liste.add(component);
    }
    
     
     public Component zurueckButton(){
     Component component = null ;
         try {
         component = liste.get(liste.size()-1);
         liste.remove(liste.size()-1);
         } catch (ArrayIndexOutOfBoundsException e) {
         }
         return component;
     }
     
     public Component getComponent(){
         return liste.get(liste.size() - 1);
     }

    /**
     * @return the DAO
     */
    public static DataAccessObject getDAO() {
        return DAO;
    }
    


    

}
