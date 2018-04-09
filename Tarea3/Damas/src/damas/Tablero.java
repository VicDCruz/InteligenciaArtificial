/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 *
 * @author daniel
 */
public class Tablero extends JComponent{
    private final static int CUADRODIM = (int) (Dama.getDimension() * 1.25);
    private final int TABLERODIM = 8 * CUADRODIM;
    private Dimension dimPreferido;
    private boolean agarrado = false;
    private int deltax, deltay;
    private PosDama posDama;
    private int oldcx, oldcy;
    private List<PosDama> posDamas;
    
    public Tablero(){
        this.posDamas = new ArrayList<>();
        this.dimPreferido = new Dimension(TABLERODIM, TABLERODIM);
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me){
                // Obtenemos las coordenadas
                int x = me.getX(), y = me.getY();
                
                // Obtenemos la Dama debajo del ratón presionado
                for (PosDama posDama : posDamas){
                    if ( Dama.contiene(x, y, posDama.cx, posDama.cy) ) {
                        Tablero.this.posDama = posDama;
                        oldcx = posDama.cx;
                        oldcy = posDama.cy;
                        deltax = x - posDama.cx;
                        deltay = y - posDama.cy;
                        agarrado = true;
                        return;
                    }
                }
            }
            
            @Override
            public void mouseReleased( MouseEvent me ){
                if(agarrado)
                    agarrado = false;
                else
                    return;
                
                int x = me.getX(), y = me.getY();
                posDama.cx = (x - deltax) / CUADRODIM * CUADRODIM + CUADRODIM / 2;
                posDama.cy = (y - deltay) / CUADRODIM * CUADRODIM + CUADRODIM / 2;
                
                for(PosDama posDama : posDamas)
                    if (posDama != Tablero.this.posDama &&
                        posDama.cx == Tablero.this.posDama.cx &&
                        posDama.cy == Tablero.this.posDama.cy) {
                        Tablero.this.posDama.cx = oldcx;
                        Tablero.this.posDama.cy = oldcy;
                    }
                posDama = null;
                System.out.println(Tablero.this.posDamas.toString());
                repaint();
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent me){
                if(agarrado){
                    posDama.cx = me.getX() - deltax;
                    posDama.cy = me.getY() - deltay;
                    repaint();
                }
            }
        });
        
    }
    
    public void add(Dama dama, int row, int col){
        if ( row < 1 || row > 8)
            throw new IllegalArgumentException("Fila fuera de rango: " + row);
        if ( col < 1 || col > 8)
            throw new IllegalArgumentException("Columna fuera de rango: " + col);
        PosDama posDama = new PosDama();
        posDama.dama = dama;
        posDama.cx = (col - 1) * CUADRODIM + CUADRODIM/2;
        posDama.cy = (row - 1) * CUADRODIM + CUADRODIM/2;
        for(PosDama _posDama : posDamas)
            if (posDama.cx == _posDama.cx && posDama.cy == _posDama.cy)
                throw new LugarOcupadoException("Cuadro en (" + row + "," +
                                               col + ") está ocupado");
        posDamas.add(posDama);
    }
    
    @Override
    public Dimension getPreferredSize(){
        return this.dimPreferido;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        pintaTableroDamas(g);
        for(PosDama posDama : posDamas)
            if (posDama != Tablero.this.posDama)
                posDama.dama.dibuja(g, posDama.cx, posDama.cy);
            
        if (posDama != null) {
            posDama.dama.dibuja(g, posDama.cx, posDama.cy);
        }
    }
    
    private void pintaTableroDamas(Graphics g){
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (int row = 0; row < 8; row++) {
            g.setColor( ((row & 1) != 0) ? Color.BLACK : Color.WHITE );
            for (int col = 0; col < 8; col++)
            {
               g.fillRect(col * CUADRODIM, row * CUADRODIM, CUADRODIM, CUADRODIM);
               g.setColor( (g.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK );
            }
        }
    }
    
    private class PosDama{
        public Dama dama;
        public int cx;
        public int cy;
        
        public String toString(){
            return "Dama: "+dama+", CX: "+cx+", CY: "+cy+"\n";
        }
    }
}
