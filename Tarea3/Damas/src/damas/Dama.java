/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author daniel
 */
public final class Dama {
    private final static int DIMENSION = 50;
    private TipoDamas tipoDamas;
    
    public Dama(TipoDamas tipoDamas){
        this.tipoDamas = tipoDamas;
    }
    
    public void dibuja(Graphics g, int cx, int cy){
        int x = cx - DIMENSION/2;
        int y = cy - DIMENSION/2;
        
        g.setColor( (tipoDamas == TipoDamas.BLACK_REGULAR ||
                 tipoDamas == TipoDamas.BLACK_KING) ? Color.BLACK : 
                 Color.RED );
        
        g.fillOval(x, y, DIMENSION, DIMENSION);
        g.setColor(Color.WHITE);
        g.drawOval(x, y, DIMENSION, DIMENSION);
        
        if (tipoDamas == TipoDamas.RED_KING || 
          tipoDamas == TipoDamas.BLACK_KING)
            g.drawString("K", cx, cy);
    }
    
    public static boolean contiene(int x, int y, int cx, int cy){
        return (cx - x) * (cx - x) + (cy - y) * (cy - y) < DIMENSION / 2 * 
             DIMENSION / 2;
    }
    
    public static int getDimension(){
      return DIMENSION;
   }   
}
