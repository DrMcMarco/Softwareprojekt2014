package Interfaces;

import javax.swing.JInternalFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Luca Terrasi
 *
 * 10.12.2014 Terrasi, Erstellung.
 */
public interface InterfaceJTreeFunction {

    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode openJtreeNode die für eine Baumstruktur
     * verwendet werden soll.
     * @param node, Stringvariable
     * 
     */ 
    public void openJtreeNodes(String node);

    
    /*----------------------------------------------------------*/
    /* Datum Name Was */
    /* 10.12.2014 Terrasi angelegt und Dokumentation */
    /*----------------------------------------------------------*/
    /**
     * Schnittstellenmethode setComponentVisible die verwendet werden soll
     * um einzelne Frames sichtbar zu machen.
     */ 
    public void setComponentVisible(JInternalFrame frame);
}
