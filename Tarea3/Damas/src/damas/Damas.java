/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author daniel
 */
public class Damas extends JFrame{
    
    public Damas(String titulo){
        super(titulo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Tablero tablero = new Tablero();
        tablero.add(new Dama(TipoDamas.RED_REGULAR), 4, 1);
        tablero.add(new Dama(TipoDamas.BLACK_REGULAR), 6, 3);
        tablero.add(new Dama(TipoDamas.RED_KING), 5, 6);
        
//        for (int x = 2; x < 9; x+=2)
//            tablero.add(new Dama(TipoDamas.BLACK_REGULAR), 1, x);
//        for (int x = 1; x < 9; x+=2)
//            tablero.add(new Dama(TipoDamas.BLACK_REGULAR), 2, x);
//        for (int x = 2; x < 9; x+=2)
//            tablero.add(new Dama(TipoDamas.BLACK_REGULAR), 3, x);
//        
//        for (int x = 1; x < 9; x+=2)
//            tablero.add(new Dama(TipoDamas.RED_REGULAR), 6, x);
//        for (int x = 2; x < 9; x+=2)
//            tablero.add(new Dama(TipoDamas.RED_REGULAR), 7, x);
//        for (int x = 1; x < 9; x+=2)
//            tablero.add(new Dama(TipoDamas.RED_REGULAR), 8, x);
        setContentPane(tablero);
        
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args){
      Runnable r = new Runnable(){
                      @Override
                      public void run(){
                         new Damas("Damas");
                      }
                   };
      EventQueue.invokeLater(r);
   }
    
}
