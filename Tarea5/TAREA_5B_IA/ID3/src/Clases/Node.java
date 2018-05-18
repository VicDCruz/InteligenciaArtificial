/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author Mario
 */
public class Node {
    private String atributoPrincipal;
    private String atributoSecundario;
    private Node hijo;
    
    public Node(String a, String b, Node h){
        atributoPrincipal =a;
        atributoSecundario = b;
        hijo = h;
    }
    
    public String getAtributoPrincipal(){
        return atributoPrincipal;
    }
    public String getAtributoSecundario(){
        return atributoSecundario;
    }
    public Node getHijo(){
        return hijo;
    }
    
    public void setAtributoPrincipal(String nuevo){
        atributoPrincipal = nuevo;
    }
    public void setAtributoSecundario(String nuevo){
        atributoSecundario = nuevo;
    }
    public void setHijo( Node nuevo){
        hijo = nuevo;
    }
    
    public String toString(){
        return "(" + atributoPrincipal + " = " + atributoSecundario + ")"  ;
    }
    
}
