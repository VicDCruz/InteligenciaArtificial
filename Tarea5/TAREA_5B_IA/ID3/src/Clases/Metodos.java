/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Mario Montes
 */
public class Metodos {
   private ArrayList<String> cadena = new ArrayList();
   
   //Este método calcula el logaritmo de un número "num" en una base "base". 
   public static double log(double num, int base) {
      return (Math.log10(num) / Math.log10(base));
   }
    
    //-----------------------------------------------------------------------
    public String[] getString(){
        return cadena.toArray(new String[cadena.size()]); 
    }
    
    public int cuentaAtributos(String tabla[][]){
       ArrayList<String> atributos = new ArrayList<String>(); 
       String primero= tabla[1][0];
       atributos.add(primero);
       int cont=1;
       int i=1;
       while(i<tabla.length){
           if(!atributos.contains(tabla[i][0])){
            atributos.add(tabla[i][0]);
            cont++;
           }
           i++;
       }
       return cont; 
    }
    
    //Este método crea un arrayList con todos los atributos de la columna que se desee de la tabla;
    public ArrayList<String> listaAtributosColumna(String tabla[][], int columna){
        ArrayList<String> atributos = new ArrayList();
        atributos.add(tabla[1][columna]);
        int i=1;
        while(i<tabla.length){
            if(!atributos.contains(tabla[i][columna])){
                atributos.add(tabla[i][columna]);
            }
            i++;
        }
        return atributos;
    }
    
    //Este método regresa una cadena con todos los valores del array.
    public String imprimeArrayList(ArrayList<String> array){
        String resp="";
        int i=0;
        while(i<array.size()){
            resp= resp + "[" + array.get(i) + "]";
            i++;
        }
        return resp;
    }
    
    //Este método te devuelve una cadena con todos los atributos del arrayList de doubles.
    public String imprimeArrayListDoubles(ArrayList<Double> array){
        String resp="";
        int i=0;
        while(i<array.size()){
            resp= resp + "[" + array.get(i) + "]";
            i++;
        }
        return resp;
    }
    public String imprimeArrayListEnteros(ArrayList<Integer> array){
        String resp="";
        int i=0;
        while(i<array.size()){
            resp= resp + "[" + array.get(i) + "]";
            i++;
        }
        return resp;
    }
    
    public String imprimeArrayListBooleano(ArrayList<Boolean> array){
        String resp="";
        int i=0;
        while(i<array.size()){
            resp= resp + "[" + array.get(i) + "]";
            i++;
        }
        return resp;
    }
    //Este método regresa el numero de veces que se repite un atributo dado en una columna dada de la tabla.
    public int repeticiones(String atributo, int columna, String tabla[][]){
        int cont=0;
        int i=1;
        while(i<tabla.length){
            if(tabla[i][columna].equals(atributo)){
                cont++;
            }
           i++;
        }
        return cont;
    }
    
   //Este método te devuelve el valor de la entriopia de la columna "columna" d ela tabla "tabla".
   public double calculaEntriopia(int columna, String tabla[][]){
       double entriopia=0;
       int altoTabla = tabla.length-1;
       int largoTabla = tabla[0].length;
       ArrayList<String> atributos = listaAtributosColumna(tabla, columna);
       ArrayList<String> finales = listaAtributosColumna(tabla, largoTabla-1);
       int totalAtributos = atributos.size();
       int totalFinales = finales.size();
       ArrayList<Integer> repeticiones = arrayListRepeticiones(atributos,columna,tabla);
       //cadena.add("Atributos: " + imprimeArrayList(atributos));
       //cadena.add("Finales: " + imprimeArrayList(finales));
       //cadena.add("Repeticiones: " + imprimeArrayListEnteros(repeticiones));
       int i=0;
       float alfa = 0;
       float beta = 0;
       while(i<atributos.size()){
          alfa = (float)repeticiones.get(i) / altoTabla;
          beta = (float)sumaLogaritmos(atributos.get(i), finales, columna, tabla);
          entriopia = entriopia + alfa * (-1) * beta;
          i++;
       }
       
       return entriopia;
   }
   
   //Este método regresa la suma interna de los logaritoms de un atributo específico de la columna d euna tabla señalada.
   public double sumaLogaritmos(String a, ArrayList<String> array, int columna, String tabla[][]){
       double suma=0;
       int rep= repeticiones(a, columna, tabla);
       float alfa = 0;
       int i=0;
       while(i<array.size()){ 
           alfa= (float)(parejas(a, array.get(i), columna, tabla)) / rep;
           if(alfa!=0){
                suma = suma + (alfa * log(alfa, 2));
           }
           i++;
       }
       return suma;
   }
   
   //Este método te regresa el número de veces que se encuentra  a la pareja a y b en la columna "columna" de la tabla "tabla".
   public int parejas(String a, String b, int columna, String tabla[][]){
       int resp=0;
       int i=0;
       while(i<tabla.length){
           if(tabla[i][columna].equals(a) && tabla[i][tabla[0].length - 1].equals(b)){
               resp++;
           }
          i++;
       }
       return resp;
   }
    
   //Este método te regresa un arrayList con la cantidad de veces que se repite cada atributo del arrayList "atributos" en la columna "columna" de la tabla "tabla".
    public ArrayList<Integer> arrayListRepeticiones(ArrayList<String> atributos, int columna, String tabla[][]){
        ArrayList<Integer> repeticiones = new ArrayList();
        int i=0;
        while(i<atributos.size()){
            repeticiones.add(repeticiones(atributos.get(i), columna, tabla));
            i++;
        }
        return repeticiones;
    }
    
    //Este método calcula las entriopias de cada columna de la tabla "tabla" y las regresa en un arrayList.
    public ArrayList<Double> entriopias(String tabla[][]){
        ArrayList<Double> entriopias = new ArrayList();
        ArrayList<String> atributos = new ArrayList();
        ArrayList<Integer> posiciones = new ArrayList();
        posiciones.add(0);
        int i=0;
        while(i<tabla[0].length-1){
            atributos.add(tabla[0][i]);
            entriopias.add((double)Math.round(calculaEntriopia(i,tabla) * 10000d) / 10000d);
            i++;
            posiciones.add(i);
        }
        
        cadena.add("----------------------------------------------------");
        cadena.add("ENTRIOPIAS");
        cadena.add(imprimeArrayList(atributos));
        cadena.add(imprimeArrayListDoubles(entriopias));
        cadena.add("----------------------------------------------------\n");
        ArrayList<String> auxiliar= ordenaAtributos(atributos, entriopias, posiciones, atributos, entriopias, posiciones, tabla);
        return entriopias;
    }
    
    public String[][] creaTabla(int alto, int largo){
        String arg[][]= new String[alto][largo];
        return arg;
    }
    //Este método regresa el indice con la  menor entriopia del ArrayList. 
    public int menorEntriopia(ArrayList<Double> entriopias){
        double menor=50;
        int indice=0;
        int i=0;
        while(i<entriopias.size()){
            if(entriopias.get(i) < menor){
                menor = entriopias.get(i);
                indice=i;
            }
           i++;
        }
        return indice;
    }
    
    //Este método imprime una tabla.
    public String imprimeTabla(String tabla[][]){
        String resp = "";
        int i=0;
        while (i<tabla.length){
            int j=0;
            while(j<tabla[0].length){
                resp = resp + "[" + tabla[i][j] + "]";
                j++;
            }
            resp = resp + "\n";
           i++;
        }
        return resp;
    }
    public ArrayList<String[][]> tablas(String atributoPrincipal, int columnaAtributoPrincipal, ArrayList<String> atributosPrimarios, ArrayList<String> atributosSecundarios, ArrayList<Integer> posicionesViejaTabla, ArrayList<Integer> posicionesNuevaTabla, String tabla[][] ){
         ArrayList<String[][]> tablas = new ArrayList();
         int repetidos;
         int m=0;
         while(m<atributosSecundarios.size()){ 
            int i=0;
         while(i<atributosSecundarios.size()){ //Este while es para crear tablas 
             repetidos = repeticiones(atributosSecundarios.get(i), columnaAtributoPrincipal, tabla);
             String t[][] = creaTabla(repetidos + 1, atributosPrimarios.size());
             llenaPrimerNivel(t,atributosPrimarios);
             int j=0;
             while(j<posicionesViejaTabla.size()){ //Este while es para llenar toda la tabla
                int k=1; 
                int b=1;
                while(k<tabla.length){//Este while es para recorrer la tabla y llenar una columna de una subtabla.
                    if(tabla[k][posicionesNuevaTabla.get(m)].equals(atributosSecundarios.get(i))){
                        t[b][j] = tabla[k][posicionesViejaTabla.get(j)];
                        b++;
                    }
                  k++;        
                }
               j++;
             }
             tablas.add(t);
             i++;
           }
          m++;
        }
        cadena.add(imprimeArregloTablas(optimiza(tablas, atributosSecundarios), atributosSecundarios, atributoPrincipal));
        cadena.add("----------------------------------------------------\n");
        return optimiza(tablas, atributosSecundarios);
    }
    
    public ArrayList<String[][]> optimiza(ArrayList<String[][]> tablas, ArrayList<String> atributos){
        ArrayList<String[][]> nuevasTablas = new ArrayList();
        int i=0;
        while(i<atributos.size()){
            nuevasTablas.add(tablas.get(i));
            i++;
        }
        return nuevasTablas;
    }
    
    public String imprimeArregloTablas(ArrayList<String[][]> tablas, ArrayList<String> atributos, String atributoPrincipal){
        String resp= "";
        int i=0;
        while(i<atributos.size()){
            resp = resp + "TABLA " + (i+1) + ": " + atributoPrincipal + " = " + atributos.get(i) + "\n" + imprimeTabla(tablas.get(i)) + "\n";
            i++;
        }
        return resp;
    }
    
    public ArrayList<String> ordenaAtributos(ArrayList<String> atributos, ArrayList<Double> entriopias, ArrayList<Integer> posiciones, ArrayList<String> atributosOriginales, ArrayList<Double> entriopiasOriginales, ArrayList<Integer> posicionesOriginales, String tabla[][]){
       ArrayList<String> nuevosAtributos = new ArrayList();
       ArrayList<Double> nuevasEntriopias = new ArrayList();
       ArrayList<Integer> nuevasPosiciones = new ArrayList();
       return ordenaAtributosAuxiliar(0,0, atributos, entriopias, posiciones, nuevosAtributos, nuevasEntriopias, nuevasPosiciones, atributosOriginales, entriopiasOriginales, posicionesOriginales, tabla);
    }
    
    public ArrayList<String> ordenaAtributosAuxiliar(int indice,int menor, ArrayList<String> atributos, ArrayList<Double> entriopias, ArrayList<Integer> posiciones,  ArrayList<String> nuevosAtributos, ArrayList<Double> nuevasEntriopias, ArrayList<Integer> nuevasPosiciones, ArrayList<String> atributosOriginales, ArrayList<Double> entriopiasOriginales, ArrayList<Integer> posicionesOriginales, String tabla[][]){
        if(indice<atributos.size()){
            menor=menorEntriopia(entriopias);
            nuevosAtributos.add(atributos.get(menor));
            nuevasEntriopias.add(entriopias.get(menor));
            nuevasPosiciones.add(posiciones.get(menor));
            entriopias.remove(menor);
            entriopias.add(menor, 50.0);
            return ordenaAtributosAuxiliar(indice + 1, 0, atributos, entriopias, posiciones, nuevosAtributos, nuevasEntriopias, nuevasPosiciones, atributosOriginales, entriopiasOriginales, posicionesOriginales, tabla);
        }
        else
        { 
            String atributoPrincipal = atributosOriginales.get(0);
            String atributoFinal = tabla[0][tabla[0].length - 1];
            int columnaAtributoPrincipal = posicionesOriginales.get(0);
            nuevosAtributos.remove(0);
            nuevosAtributos.add(nuevosAtributos.size(), atributoFinal);
            ArrayList<String> atributosSecundarios = listaAtributosColumna(tabla,columnaAtributoPrincipal);
            int tope = posicionesOriginales.remove(posicionesOriginales.size() - 1);
            ArrayList<Integer> posicionesNuevaTabla = posicionesOriginales;
            nuevasPosiciones.remove(0);
            nuevasPosiciones.add(nuevasPosiciones.size(), tope);
            ArrayList<Integer> posicionesViejaTabla = nuevasPosiciones;
            
            //cadena.add("Atributos principal: " + atributoPrincipal);
            //cadena.add("Columna del atributo principal: " + columnaAtributoPrincipal);
            //cadena.add("Atributos originales: " + imprimeArrayList(nuevosAtributos));
            //cadena.add("Atrubutos secundarios: " + imprimeArrayList(atributosSecundarios));
            //cadena.add("Posiciones en la tabla principal: " + imprimeArrayListEnteros(posicionesViejaTabla));
            //cadena.add("Posiciones en la tabla secundaria: " + imprimeArrayListEnteros(posicionesNuevaTabla));
            //cadena.add("Entriopias: " + imprimeArrayListDoubles(entriopias));
            ArrayList<String[][]> T1 = tablas(atributoPrincipal, columnaAtributoPrincipal, nuevosAtributos, atributosSecundarios, posicionesViejaTabla, posicionesNuevaTabla, tabla);
            
            //cadena.add("Tiene todos los atributos iguales finales: " + imprimeArrayListBooleano(atributosIgualesColumnaFinal(T1)));
            //cadena.add("prueba1: " + imprimeArrayListBooleano(parejasIguales(T1,"HIGH","NO PLAY")));
            //cadena.add("prueba2: " + imprimeArrayListBooleano(parejasIguales(T1,"HIGH","PLAY")));
            //cadena.add("prueba3: " + imprimeArrayListBooleano(parejasIguales(T1,"NORMAL","NO PLAY")));
            //cadena.add("prueba4: " + imprimeArrayListBooleano(parejasIguales(T1,"NORMAL","PLAY")));
            
            ArrayList<Node> arbol = generaArbol(atributoPrincipal, nuevosAtributos, atributosSecundarios, posicionesViejaTabla, posicionesNuevaTabla, T1);
            return nuevosAtributos;
        }
    }
    
    public void llenaPrimerNivel(String tabla[][], ArrayList<String> nivel){
        int i=0;
        while(i<nivel.size()){
            tabla[0][i] = nivel.get(i);
            i++;
        }
    }
    
    public ArrayList<Node> generaArbol(String atributoPrincipal, ArrayList<String> atributosPrimarios, ArrayList<String> atributosSecundarios, ArrayList<Integer> posicionesViejas, ArrayList<Integer> posicionesNuevas, ArrayList<String[][]> tablas){
        ArrayList<Node> arbol = new ArrayList();
        ArrayList<String> atributosSecundariosNuevaTabla = listaAtributosColumna(tablas.get(0), 0);
        ArrayList<String> producciones = new ArrayList();
        ArrayList<String> atributosColumnaFinal = new ArrayList();
        ArrayList<Boolean> booleanos = atributosIgualesColumnaFinal(tablas);
        int index = 0;
        if(booleanos.contains(true)){ //CASO 1: cuando existe una tabla cuyos atributos de la columna final son todos iguales.
            int i=0;
            while(i<booleanos.size()){
                if(booleanos.get(i) == true){
                    index = i;
                }
               i++;
            }
            String p = "SI (" + atributoPrincipal + " = " + atributosSecundarios.get(index) + ") ENTONCES " + tablas.get(index)[1][tablas.get(index)[0].length - 1];
            producciones.add(p);
            atributosSecundarios.remove(index);
        }
        int i = 0;//indiceTablaEquivalente(tablas, atributosSecundariosNuevaTabla.get(0), atributosColumnaFinal);
        //cadena.add("index: " + i);
        if(i != -1){
        String nuevo = "(" + atributoPrincipal + " = " + atributosSecundarios.get(i) + ")";
        ArrayList<String> nuevasProducciones = creaProduccionesEquivalentes(tablas, i, atributosPrimarios.get(0), nuevo, atributosSecundariosNuevaTabla);
        int m=0;
        while(m<nuevasProducciones.size()){
            producciones.add(nuevasProducciones.get(m));
            m++;
        }
        }
        cadena.add("PRODUCCIONES \n" + imprimeArrayList(producciones) );
        
        
        return arbol;
    }
    public ArrayList<String> creaProduccionesEquivalentes(ArrayList<String [][]> tablas, int i, String atributoPrincipal, String antecesor, ArrayList<String> atributos){
        ArrayList<String> a = new ArrayList();
        String resp = "";
        int k=0;
        while(k<atributos.size()){
            int j=1;
            while(j<tablas.get(i).length){
                if(tablas.get(i)[j][0].equals(atributos.get(i))){
                    resp = "SI "  + antecesor + " ^ (" + atributoPrincipal + " = " + atributos.get(i) + ") ENTONCES " + tablas.get(i)[j][tablas.get(i)[0].length -1];
                }
              j++;
            }
            a.add(resp);
           k++;
        }
        return a;
    }
    
    
    public ArrayList<ArrayList<String>> produccionesConjuntas(String [][] tabla, int columna ){
        ArrayList<ArrayList<String>> pc = new ArrayList();
        ArrayList<String> producciones = new ArrayList();
        ArrayList<String> atributosSecundarios = listaAtributosColumna(tabla, columna);
        int i=0;
        while(i<atributosSecundarios.size()){
            producciones.add("(" + tabla[0][i] + " = " + atributosSecundarios.get(i) + ")");
            i++;
        }
        pc.add(producciones);
        pc.add(atributosSecundarios);
        return pc;
    }
    
    
    //Este método regresa un arraylist con true si una tabla del arrayList tiene los atributos de la última columna iguales y false si no.
    public ArrayList<Boolean> atributosIgualesColumnaFinal(ArrayList<String[][]> tablas){
        ArrayList<Boolean> resp = new ArrayList();
        int k=0;
        while(k<tablas.size()){
          resp.add(true);
          k++;
        }
        int i=0;
        while(i<tablas.size()){
            int j=1;
            while(j<tablas.get(i).length){
                String primero = tablas.get(i)[1][tablas.get(i)[0].length-1];
                if(!tablas.get(i)[j][tablas.get(i)[0].length-1].equals(primero)){
                    resp.remove(i);
                    resp.add(i,false); 
                }
              j++;
            }
          i++; 
        }
        
        return resp;
    }
    
    public int indiceTablaEquivalente(ArrayList<String[][]> tablas, String primero, ArrayList<String> segundos){
        ArrayList<Boolean> C = new ArrayList();
        int i=0;
        int index = -1;
        while(i<segundos.size()){
            C = parejasIguales(tablas, primero, segundos.get(i));
            if(C.contains(true)){
                int j=0;
                while(j<C.size()){
                    if(C.get(index) == true){
                        index=i;
                    }
                   j++;
                } 
            }
          i++; 
        }
        return index;
    }
    
    public ArrayList<Boolean> parejasIguales(ArrayList<String[][]> tablas, String atributoPrimario, String atributoSecundario){
        ArrayList<Boolean> resp = new ArrayList();
        int k=0;
        while(k<tablas.size()){
          resp.add(true);
          k++;
        }
        int i=0;
        while(i<tablas.size()){
            int j=1;
            while(j<tablas.get(i).length){
                String primero = tablas.get(i)[j][0];
                String segundo = tablas.get(i)[j][tablas.get(i)[0].length-1];
                if((primero.equals(atributoPrimario)) && (!segundo.equals(atributoSecundario))){
                        resp.remove(i);
                        resp.add(i, false);
                }  
              j++;
            }
          i++; 
        }
        
        return resp;
    }
    
   
    public static void main(String args[]){
         Metodos m1 = new Metodos();
         String[][] tabla1 = {{"OUTLOOK", "TEMPERATURE","HUMIDITY", "WINDY", "ACTIVITY"}, 
                              {"SUNNY", "HOT", "HIGH", "FALSE", "NO PLAY"}, 
                              {"SUNNY", "HOT", "HIGH", "TRUE", "NO PLAY"}, 
                              {"OVERCAST","HOT", "HIGH", "FALSE", "PLAY"}, 
                              {"RAIN", "MILD", "HIGH", "FALSE", "PLAY"},
                              {"RAIN","COOL", "NORMAL", "FALSE", "PLAY"}, 
                              {"RAIN", "COOL", "NORMAL", "TRUE", "NO PLAY"},
                              {"OVERCAST", "COOL", "NORMAL", "TRUE", "PLAY"},
                              {"SUNNY", "MILD", "HIGH", "FALSE", "NO PLAY"},
                              {"SUNNY", "COOL", "NORMAL", "FALSE", "PLAY"},
                              {"RAIN", "MILD", "NORMAL", "FALSE", "PLAY"},
                              {"SUNNY", "MILD", "NORMAL", "TRUE", "PLAY"},
                              {"OVERCAST", "MILD", "HIGH", "TRUE", "PLAY"},
                              {"OVERCAST", "HOT", "NORMAL", "FALSE", "PLAY"},
                              {"RAIN", "MILD", "HIGH", "TRUE", "NO PLAY"}};
        
         String tabla2[][] = {{"CIELO", "BAROMETRO", "VIENTO", "LLUVIA"},
                             {"LIMPIO", "SUBIENDO", "NORTE", "NO"},
                             {"NUBLADO", "SUBIENDO", "SUR", "SI"},
                             {"NUBLADO", "ESTABLE", "NORTE", "SI"},
                             {"LIMPIO", "BAJANDO", "NORTE", "NO"},
                             {"NUBLADO", "BAJANDO", "NORTE", "SI"},
                             {"NUBLADO", "SUBIENDO", "NORTE", "SI"},
                             {"NUBLADO", "BAJANDO", "SUR", "NO"},
                             {"LIMPIO", "SUBIENDO", "SUR", "NO"}};
        //m1.entriopias(tabla1); 
        m1.entriopias(tabla1);
        m1.getString();
        //cadena.add(m1.imprimeTabla(tabla2));
        for(String linea: m1.getString()){
            System.out.println(linea);
        }
       
         
        //ArrayList<Double> prueba = new ArrayList();
        //prueba.add(2.3421);
        //prueba.add(1.12);
        //prueba.add(2.5);
        //cadena.add("El indice de menor entriopia: " + m1.menorEntriopia(prueba));
        //cadena.add("Alto de la tabla: " + (ejemplo.length - 1) );
        //cadena.add("Largo de la tabla: " + ejemplo[0].length);
        //cadena.add("Número de atributos de la tabla:" + m1.cuentaAtributos(ejemplo));
        //cadena.add("Cantidad de veces que se repite SUNNY:" + m1.repeticiones("SUNNY",0, tabla1));
        //cadena.add("Cantidad de veces que se repite OVERCAST:" + m1.repeticiones("OVERCAST",0, ejemplo));
        //cadena.add("Cantidad de veces que se repite RAIN:" + m1.repeticiones("RAIN",0, ejemplo));
        //cadena.add(m1.imprimeArrayList(prueba));
        //cadena.add(m1.imprimeArrayList(m1.listaAtributosColumna(ejemplo, 4)));
        //cadena.add(m1.parejas("SUNNY","PLAY" , 0, ejemplo));
        //cadena.add("Logaritmo de prueba:" + log(1, 2));
        //cadena.add("Suma de logaritmos de SUNNY: " + m1.sumaLogaritmos("SUNNY", prueba, 0, ejemplo));
        //cadena.add("Entriopia de la columna n: " + m1.calculaEntriopia(3, ejemplo));
        //double number = 1.32453245;
        //cadena.add((double)Math.round(number * 10000d) / 10000d);
        
    }

    private ArrayList<Node> ArrayList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
