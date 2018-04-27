/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas;

import static damas.TipoDamas.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author daniel
 */
public class Jugador {

    private List<PosDama> posDamas;

    public Jugador() {
        posDamas = new ArrayList<>();
    }

    public void setPosDamas(List<PosDama> posDamas) {
        this.posDamas = posDamas;
    }

    /**
     * Siguiente movimiento de la máquina
     */
    public String mueveMaquina() {
        String str = "( ", blackPeon = "( ", redPeon = "( ";
        String blackRey = "( ", redRey = "( ";

        for (PosDama dama : posDamas) {
            int tmp = this.coord2num(dama);
            switch (dama.dama.getTipoDamas()) {
                case BLACK_REGULAR:
                    blackPeon += tmp + " ";
                    break;
                case BLACK_KING:
                    blackRey += tmp + " ";
                    break;
                case RED_REGULAR:
                    redPeon += tmp + " ";
                    break;
                default:
                    redRey += tmp + " ";
                    break;
            }
        }
        blackPeon += ")";
        blackRey += ")";
        redPeon += ")";
        redRey += ")";
        str += "(" + blackPeon + " " + blackRey + ") (" + redPeon + " " + redRey + ") ) ";
        str += (Tablero.N == 0) ? 4:(Tablero.N == 1) ? 3: 2;
        System.out.println(Tablero.N);

        FileWriter infoLisp = null;
        BufferedWriter bw = null;
        try {
            infoLisp = new FileWriter("/Users/daniel/Documents/InteligenciaArtificial/Tarea3/infoLisp.txt");
            bw = new BufferedWriter(infoLisp);
            bw.write(str);

            System.out.println("Archivo LISP creado!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (infoLisp != null) {
                    infoLisp.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        String nextMov = execCmd("curl localhost:1337/");
        //String nextMov = execCmd("clisp /Users/daniel/Documents/InteligenciaArtificial/Tarea3/Lisp/alfaBetaSearch.lisp");

        return nextMov;
    }

    private String execCmd(String cmd) {
        StringBuffer output = new StringBuffer();
        Process p;
        BufferedReader br = null;
        FileReader solLisp = null;

        try {
            p = Runtime.getRuntime().exec(cmd);
            //p.waitFor();
            System.out.println(p.waitFor());
            
            solLisp = new FileReader("../solLisp.txt");
            br = new BufferedReader(solLisp);
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    private int coord2num(PosDama dama) {
        int y = (dama.cx - Tablero.CUADRODIM / 2) / (Tablero.CUADRODIM) + 1;
        int x = (dama.cy - Tablero.CUADRODIM / 2) / (Tablero.CUADRODIM) + 1;
        int res = 0;
        
        switch (x) {
            case 1:
                switch (y) {
                    case 2:
                        res = 1;
                        break;
                    case 4:
                        res = 2;
                        break;
                    case 6:
                        res = 3;
                        break;
                    case 8:
                        res = 4;
                        break;
                    default:
                        break;
                }   break;
            case 2:
                switch (y) {
                    case 1:
                        res = 5;
                        break;
                    case 3:
                        res = 6;
                        break;
                    case 5:
                        res = 7;
                        break;
                    case 7:
                        res = 8;
                        break;
                    default:
                        break;
                }   break;
            case 3:
                switch (y) {
                    case 2:
                        res = 9;
                        break;
                    case 4:
                        res = 10;
                        break;
                    case 6:
                        res = 11;
                        break;
                    case 8:
                        res = 12;
                        break;
                    default:
                        break;
                }   break;
            case 4:
                switch (y) {
                    case 1:
                        res = 13;
                        break;
                    case 3:
                        res = 14;
                        break;
                    case 5:
                        res = 15;
                        break;
                    case 7:
                        res = 16;
                        break;
                    default:
                        break;
                }   break;
            case 5:
                switch (y) {
                    case 2:
                        res = 17;
                        break;
                    case 4:
                        res = 18;
                        break;
                    case 6:
                        res = 19;
                        break;
                    case 8:
                        res = 20;
                        break;
                    default:
                        break;
                }   break;
            case 6:
                switch (y) {
                    case 1:
                        res = 21;
                        break;
                    case 3:
                        res = 22;
                        break;
                    case 5:
                        res = 23;
                        break;
                    case 7:
                        res = 24;
                        break;
                    default:
                        break;
                }   break;
            case 7:
                switch (y) {
                    case 2:
                        res = 25;
                        break;
                    case 4:
                        res = 26;
                        break;
                    case 6:
                        res = 27;
                        break;
                    case 8:
                        res = 28;
                        break;
                    default:
                        break;
                }   break;
            case 8:
                switch (y) {
                    case 1:
                        res = 29;
                        break;
                    case 3:
                        res = 30;
                        break;
                    case 5:
                        res = 31;
                        break;
                    case 7:
                        res = 32;
                        break;
                    default:
                        break;
                }   break;
            default:
                break;
        }

        return res;
    }

}
