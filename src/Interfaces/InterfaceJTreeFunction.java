package Interfaces;

import javax.swing.JInternalFrame;
import javax.swing.tree.DefaultMutableTreeNode;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Luca
 */
public interface InterfaceJTreeFunction {
    public void openJtreeNodes(DefaultMutableTreeNode node);
//    public void openJtreeNodes(String node);
    public void setInternalFrameVisible(JInternalFrame frame);
}
