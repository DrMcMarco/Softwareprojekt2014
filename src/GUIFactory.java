
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
    //Speicgervariable in der die Componenten Reihenfolge gespeichert werden soll.
    private static ArrayList<Component> liste;
    
    
    public GUIFactory(){
        liste = new ArrayList();
    }
    public static void creatJFrame(String titel,Component view){
        try{
            System.out.println(view.toString());
//        liste.add(view);
            if(view.getName().equals("Start")){
                System.out.println("sssssssssssssssssssssssssssssss");
            }
            view.setVisible(true);

            System.out.println(liste);
        }catch(NullPointerException e){
            System.out.println("Fehler");
        }
    }
}
