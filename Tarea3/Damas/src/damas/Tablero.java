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
    public final static int CUADRODIM = (int) (Dama.getDimension() * 1.25);
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
    
    private Jugador maquina = new Jugador();
    
    public static int N;
    
    private boolean statusMov = true;
    private boolean statusForzoso = false;

    public Tablero(){
        this.posDamas = new ArrayList<>();
        this.dimPreferido = new Dimension(TABLERODIM, TABLERODIM);
        
        Object[] options = {
                    "6",
                    "4",
                    "2"};
        N = JOptionPane.showOptionDialog(null,
            "Número de NIVELES:",
            "A Silly Question",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]);

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
                        break;
                    }
                }
                
//                if(!goMachine){
//                // ver que agarre la pieza cuando se deba comer
//                boolean status = false;
//                for (PosDama dama : posDamas){
//                    if(dama.dama.getTipoDamas().equals(TipoDamas.BLACK_REGULAR)
//                                || dama.dama.getTipoDamas().equals(TipoDamas.BLACK_KING)){
//                        int yComeAntes = ( dama.cy - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                        int xComeAntes = ( dama.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                        int yComeOld = ( oldcy - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                        int xComeOld = ( oldcx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                        
//                        for (PosDama dama1 : posDamas){
//                            if(dama1.dama.getTipoDamas().equals(TipoDamas.RED_REGULAR)
//                                || dama1.dama.getTipoDamas().equals(TipoDamas.RED_KING)){
//                                int yCome = ( dama1.cy - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                int xCome = ( dama1.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                if((yCome == yComeAntes+1) && (xCome==xComeAntes-1)){
//                                    for (PosDama dama2 : posDamas){
//                                        int yCome2 = ( dama2.cy - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                        int xCome2 = ( dama2.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                        System.out.println(yComeAntes+" "+xComeAntes+", "
//                                                +yComeOld+" "+xComeOld+", "+yCome+" "+xCome+", "+yCome2+" "+xCome2);
//                                        boolean ocupado = false;
//                                        if(yCome2==yComeAntes-1 && xCome2==xComeAntes+1)
//                                            ocupado = true;
//                                        System.out.println("Ocupado:"+ocupado);
//                                        if( !ocupado && (yCome!=yComeOld || xCome!=xComeOld)){
//                                            System.out.println("S2");
//                                            status = true;
//                                            //break;
//                                        }
//                                        else
//                                            status=false;
//                                    }
//                                }
//                                else if((yCome == yComeAntes+1) && (xCome==xComeAntes+1)){
//                                    for (PosDama dama2 : posDamas){
//                                        int yCome2 = ( dama2.cy - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                        int xCome2 = ( dama2.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                        System.out.println(yComeAntes+" "+xComeAntes+", "
//                                                +yComeOld+" "+xComeOld+", "+yCome+" "+xCome+", "+yCome2+" "+xCome2);
//                                        boolean ocupado = false;
//                                        if(yCome2==yComeAntes-1 && xCome2==xComeAntes-1)
//                                            ocupado = true;
//                                        System.out.println("Ocupado:"+ocupado);
//                                        if( !ocupado && (yCome!=yComeOld || xCome!=xComeOld)){
//                                            System.out.println("S2");
//                                            status = true;
//                                            //break;
//                                        }
//                                        else
//                                            status=false;
//                                    }
//                                }
//                                
//                            }
//                        }
//                    }
//                }
//                if(status){
//                    JOptionPane.showMessageDialog(null, "Comer forzoso");
//                    Tablero.this.posDama.cx = oldcx;
//                    Tablero.this.posDama.cy = oldcy;
//                    statusMov = false;
//                    statusForzoso = true;
//                }
//                else{
//                    statusMov = true;
//                    statusForzoso = false;
//                }
//                System.out.println(statusForzoso);
//                }
            }

            @Override
            public void mouseReleased( MouseEvent me ){
                if(!statusForzoso){
                    // When mouse released, clear inDrag (to
                    // indicate no drag in progress) if inDrag is
                    // already set.
                    if(agarrado)
                        agarrado = false;
                    else
                        return;
                    //System.out.println(Tablero.this.posDamas.toString());

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

                    // Checar si debe de comer forzoso
                    int yComeAntes = ( oldcy - CUADRODIM/2 )/( CUADRODIM ) + 1;
                    int xComeAntes = ( oldcx - CUADRODIM/2 )/( CUADRODIM ) + 1;
                    //int yComeDesp = ( posDama.cy - CUADRODIM/2 )/( CUADRODIM ) + 1;
                    //int xComeDesp = ( posDama.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                    if(!goMachine){
//                    for(PosDama dama: Tablero.this.posDamas){
//                        int yCome = ( dama.cy - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                        int xCome = ( dama.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                        boolean status = false;
//                        if(posDama.dama.getTipoDamas().equals(TipoDamas.BLACK_REGULAR)
//                                && (dama.dama.getTipoDamas().equals(TipoDamas.RED_REGULAR)
//                                    ||dama.dama.getTipoDamas().equals(TipoDamas.RED_KING))
//                                && yCome == yComeAntes +1){
//                            if(xCome == xComeAntes-1){
//                                for(PosDama dama1: Tablero.this.posDamas){
//                                    int tmp = ( dama1.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                    status = tmp != xCome-1;
//                                }
//                            }
//
//                            if(xCome == xComeAntes+1){
//                                for(PosDama dama1: Tablero.this.posDamas){
//                                    int tmp = ( dama1.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                    status = tmp != xCome+1;
//                                }
//                            }
//
//                        }
//
//                        else if(posDama.dama.getTipoDamas().equals(TipoDamas.RED_REGULAR)
//                                && (dama.dama.getTipoDamas().equals(TipoDamas.BLACK_REGULAR)
//                                    || dama.dama.getTipoDamas().equals(TipoDamas.BLACK_KING))
//                                && yCome == yComeAntes-1){
//                            if(xCome == xComeAntes-1){
//                                for(PosDama dama1: Tablero.this.posDamas){
//                                    int tmp = ( dama1.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                    status = tmp != xCome-1;
//                                }
//                            }
//
//                            if(xCome == xComeAntes+1){
//                                for(PosDama dama1: Tablero.this.posDamas){
//                                    int tmp = ( dama1.cx - CUADRODIM/2 )/( CUADRODIM ) + 1;
//                                    status = tmp != xCome+1;
//                                }
//                            }
//
//                        }
//
//                        if(status){
//                            JOptionPane.showMessageDialog(null, "Comer forzoso");
//                            Tablero.this.posDama.cx = oldcx;
//                            Tablero.this.posDama.cy = oldcy;
//                            statusMov = false;
//                        }
//                    }
//                    }


                    posDama = null;
                    repaint();

                    if(statusMov){
                        nextTurn();
                        if(goMachine){
                            mueveMaquina();
                            nextTurn();
                            repaint();
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Movimiento no válido");
                }
                else{
                    Tablero.this.posDama.cx = oldcx;
                    Tablero.this.posDama.cy = oldcy;
                    posDama = null;
                    repaint();
                }
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
        
        this.goMachine = !goMachine;

        if ( this.goMachine )
            mss += "máquina";
        else
            mss += "jugador";

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
    
    private void mueveMaquina(){
        String[] blackPeonStr = null;
        String[] blackReyStr = null;
        String[] redPeonStr = null;
        String[] redReyStr = null;
        
        maquina.setPosDamas(posDamas);
        String nextMov = maquina.mueveMaquina().replace("\n", "").replace(") ", ")").replace(" (", "(");
        
        if(!nextMov.substring(0, 3).equals("NIL")){
            blackPeonStr = nextMov.substring(0, nextMov.indexOf(")")).replace("(", "").replace(")", " ").split(" ");
            nextMov = nextMov.substring(nextMov.indexOf(")")+1);
        }else{
            nextMov = nextMov.substring(nextMov.indexOf("L")+1);
        }

        if(!nextMov.substring(0, 3).equals("NIL")){
            blackReyStr = nextMov.substring(0, nextMov.indexOf(")")).replace("(", "").replace(")", " ").split(" ");
            nextMov = nextMov.substring(nextMov.indexOf(")")+1);
        }else{
            nextMov = nextMov.substring(nextMov.indexOf("L")+1);
        }

        if(!nextMov.substring(0, 3).equals("NIL")){
            redPeonStr = nextMov.substring(0, nextMov.indexOf(")")).replace("(", "").replace(")", " ").split(" ");;
            nextMov = nextMov.substring(nextMov.indexOf(")")+1);
        }else{
            nextMov = nextMov.substring(nextMov.indexOf("L")+1);
        }

        if(!nextMov.substring(0, 3).equals("NIL")){
            redReyStr = nextMov.substring(0, nextMov.indexOf(")")).replace("(", "").replace(")", " ").split(" ");;
        }else{
            nextMov = nextMov.substring(nextMov.indexOf("L")+1);
        }
        
        posDamas = new ArrayList<>();
        if(blackPeonStr!=null){
            for(String elem: blackPeonStr){
                int[] coord = num2coord(Integer.parseInt(elem));
                add(new Dama(TipoDamas.BLACK_REGULAR), coord[0], coord[1]);
            }
        }
        if(blackReyStr!=null){
            for(String elem: blackReyStr){
                int[] coord = num2coord(Integer.parseInt(elem));
                add(new Dama(TipoDamas.BLACK_KING), coord[0], coord[1]);
            }
        }
        if(redPeonStr!=null){
            for(String elem: redPeonStr){
                int[] coord = num2coord(Integer.parseInt(elem));
                add(new Dama(TipoDamas.RED_REGULAR), coord[0], coord[1]);
            }
        }
        if(redReyStr!=null){
            for(String elem: redReyStr){
                int[] coord = num2coord(Integer.parseInt(elem));
                add(new Dama(TipoDamas.RED_KING), coord[0], coord[1]);
            }
        }
    }
    
    private int[] num2coord(int index){
    int[] res = new int[2];

    res[0] = ( index%4==0 ) ? index/4:index/4+1;
    res[1] = ( res[0]%2==1 ) ? ( index-4*( res[0]-1 ))*2:( index-4*( res[0]-1 )-1 )*2+1;
    
    if(res[0] > 8)
        res[0] = 8;
    if(res[0] < 1)
        res[0] = 1;
    
    if(res[1] > 8)
        res[1] = 8;
    if(res[1] < 1)
        res[1] = 1;
        

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

}
