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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import javax.swing.JOptionPane;

/**
 *
 * @author daniel
 */
public class Tablero extends JComponent{
    // dimension of checkerboard square (25% bigger than checker)
    private final static int CUADRODIM = (int) (Dama.getDimension() * 1.25);
    // dimension of checkerboard (width of 8 squares)
    private final int TABLERODIM = 8 * CUADRODIM;
    // preferred size of Board component
    private Dimension dimPreferido;
    // dragging flag -- set to true when user presses mouse button over checker
    // and cleared to false when user releases mouse button
    private boolean agarrado = false;
    // displacement between drag start coordinates and checker center coordinates
    private int deltax, deltay;
    // reference to positioned checker at start of drag
    private PosDama posDama;
    // center location of checker at start of drag
    private int oldcx, oldcy;
    // list of Checker objects and their initial positions
    private List<PosDama> posDamas;
    
    private boolean goMachine = false;
    
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
                        repaint();
                        return;
                    }
                }
            }
            
            @Override
            public void mouseReleased( MouseEvent me ){
                // When mouse released, clear inDrag (to
                // indicate no drag in progress) if inDrag is
                // already set.
                if(agarrado)
                    agarrado = false;
                else
                    return;
                System.out.println(Tablero.this.posDamas.toString());

                // Snap checker to center of square. 
                int x = me.getX(), y = me.getY();
                posDama.cx = (x - deltax) / CUADRODIM * CUADRODIM + CUADRODIM / 2;
                posDama.cy = (y - deltay) / CUADRODIM * CUADRODIM + CUADRODIM / 2;

                // Do not move checker onto an occupied square.
                for(PosDama posDama : posDamas)
                    if (posDama != Tablero.this.posDama &&
                        posDama.cx == Tablero.this.posDama.cx &&
                        posDama.cy == Tablero.this.posDama.cy ) {
                            Tablero.this.posDama.cx = oldcx;
                            Tablero.this.posDama.cy = oldcy;
                    }
                
                boolean statusMov = true;
                if ( !movCorrecto(posDama) ) {
                    Tablero.this.posDama.cx = oldcx;
                    Tablero.this.posDama.cy = oldcy;
                    statusMov = false;
                }

                // Checar si una pieza se comió otra
                PosDama tmpCome = null;
                for(PosDama posDama : posDamas){
                    if( Tablero.this.come(posDama) ){
                        tmpCome = posDama;
                    }
                }
                if( tmpCome != null )
                    posDamas.remove(tmpCome);
                
                if(isKing()){
                    if(posDama.dama.getTipoDamas().equals(TipoDamas.BLACK_REGULAR))
                        posDama.dama.setTipoDamas(TipoDamas.BLACK_KING);
                    else if(posDama.dama.getTipoDamas().equals(TipoDamas.RED_REGULAR))
                        posDama.dama.setTipoDamas(TipoDamas.RED_KING);
                }

                posDama = null;
                repaint();
                
                if(statusMov){
                    nextTurn();
//                    if(goMachine){
//                        mueveMaquina();
//                        repaint();
//                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Movimiento no válido");

            }
        });
        
        // Attach a mouse motion listener to the applet. That listener listens
        // for mouse drag events.
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
        
        nextTurn();
        
    }
    
    private boolean isKing(){
        int y = ( posDama.cy - CUADRODIM/2 )/( CUADRODIM ) + 1;

        return ( y==1 )||( y==8 );
    }
    
    private boolean movCorrecto(PosDama dama){
        boolean res = ( ( Math.abs(dama.cy-this.oldcy)<3*CUADRODIM )
                    &&( Math.abs(dama.cx-this.oldcx)<3*CUADRODIM ) );
        
        if(dama.dama.getTipoDamas().equals(TipoDamas.BLACK_REGULAR))
            res = res && ( dama.cy > this.oldcy );
        else if(dama.dama.getTipoDamas().equals(TipoDamas.RED_REGULAR))
            res = res && ( dama.cy < this.oldcy );
        
        return res;
    }
    
    private void nextTurn(){
        String mss = "Turno de ";
        
        if ( this.goMachine )
            mss += "máquina";
        else
            mss += "jugador";
        
        this.goMachine = !goMachine;
        
        JOptionPane.showMessageDialog(null, mss);
    }
    
    /**
     * Checa si la posDama actual puede comer a otherDama
     * @param otherDama Dama en la lista posDamas
     * @return True - posDama puede comerse a otherDama  
     *         False - posDama NO puede comer a otherDama
     */
    private boolean come(PosDama otherDama){
        boolean res;
        int minX = Math.min(this.oldcx, this.posDama.cx),
            maxX = Math.max(this.oldcx, this.posDama.cx),
            minY = Math.min(this.oldcy, this.posDama.cy),
            maxY = Math.max(this.oldcy, this.posDama.cy);
        
        res = ( minX < otherDama.cx && otherDama.cx < maxX )
               && ( minY < otherDama.cy && otherDama.cy < maxY );
        
        return res;
    }
    
    /**
     * Siguiente movimiento de la máquina
     */
    private void mueveMaquina(){
        String str = "( ", blackPeon = "( ", redPeon = "( ";
        String blackRey = "( ", redRey = "( ";
        
        for(PosDama dama: posDamas){
            int tmp = this.coord2num(dama);
            switch (dama.dama.getTipoDamas()) {
                case BLACK_REGULAR:
                    blackPeon += tmp+" ";
                    break;
                case BLACK_KING:
                    blackRey += tmp+" ";
                    break;
                case RED_REGULAR:
                    redPeon += tmp+" ";
                    break;
                default:
                    redRey += tmp+" ";
                    break;
            }
        }
        blackPeon += ")"; blackRey += ")"; redPeon += ")"; redRey += ")";
        str += "("+blackPeon+" "+blackRey+") ("+redPeon+" "+redRey+") )";
        
        FileWriter infoLisp = null;
        BufferedWriter bw = null;
        try{
            infoLisp = new FileWriter("../infoLisp.txt");
            bw = new BufferedWriter(infoLisp);
            bw.write(str);
            
            System.out.println("Archivo LISP creado!");
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                if (bw != null)
                    bw.close();
                if (infoLisp != null)
                    infoLisp.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        String nextMov = execCmd("clisp ../Lisp/alfaBetaSearch.lisp");
        String[] blackPeonStr = nextMov.substring(0, nextMov.indexOf("\n")-1).replace("(", "").replace(")", "").split(" ");
        nextMov = nextMov.substring(nextMov.indexOf("\n"));
        String[] blackReyStr = nextMov.substring(0, nextMov.indexOf("\n")-1).replace("(", "").replace(")", "").split(" ");
        nextMov = nextMov.substring(nextMov.indexOf("\n"));
        String[] redPeonStr = nextMov.substring(0, nextMov.indexOf("\n")-1).replace("(", "").replace(")", "").split(" ");;
        nextMov = nextMov.substring(nextMov.indexOf("\n"));
        String[] redReyStr = nextMov.substring(0, nextMov.indexOf("\n")-1).replace("(", "").replace(")", "").split(" ");;

        posDamas = new ArrayList<>();
        for(String elem: blackPeonStr){
            int[] coord = num2coord(Integer.parseInt(elem));
            add(new Dama(TipoDamas.BLACK_REGULAR), coord[0], coord[1]);
        }
        for(String elem: blackReyStr){
            int[] coord = num2coord(Integer.parseInt(elem));
            add(new Dama(TipoDamas.BLACK_KING), coord[0], coord[1]);
        }
        for(String elem: redPeonStr){
            int[] coord = num2coord(Integer.parseInt(elem));
            add(new Dama(TipoDamas.RED_REGULAR), coord[0], coord[1]);
        }
        for(String elem: blackReyStr){
            int[] coord = num2coord(Integer.parseInt(elem));
            add(new Dama(TipoDamas.RED_KING), coord[0], coord[1]);
        }
        
    }
    
    private String execCmd(String cmd) {
        StringBuffer output = new StringBuffer();
        Process p;
        BufferedReader br = null;
        FileReader solLisp = null;
        
        try {
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            solLisp = new FileReader("../solLiso.txt");
            br = new BufferedReader(solLisp);
            String line = "";			
            while ((line = br.readLine())!= null)
                output.append(line + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
    
    private int[] num2coord(int index){
        int[] res = new int[2];
        
        res[0] = ( index%4==0 ) ? index/4:index/4+1;
        res[1] = ( res[0]%2==1 ) ? ( index-4*( res[0]-1 ))*2:( index-4*( res[0]-1 )-1 )*2;
        
        return res;
    }
    
    private int coord2num(PosDama dama){
        int x = ( dama.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
        int y = ( dama.cy - CUADRODIM/2 )/( CUADRODIM ) + 1;
        int res = 4 * ( x-1 );

        if( x%2==1 )
            res += y/2+1;
        else
            res += y/2+1;
        
        return res;
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
