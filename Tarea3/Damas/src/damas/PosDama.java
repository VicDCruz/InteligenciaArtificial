/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas;

/**
 *
 * @author daniel
 */
public class PosDama {
    
    public Dama dama;
    public int cx;
    public int cy;

    @Override
    public String toString(){
        return "Dama: "+dama+", CX: "+cx+", CY: "+cy+"\n";
    }
    
}
