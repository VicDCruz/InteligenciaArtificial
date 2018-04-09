# Tarea 3
Requisitos:

* Tener un tablero interactivo que muestre el tiempo, el turno, …
  * Se pueden comer >=1 ficha(s) en un mismo movimiento
* Implementar Alfa-Beta Pruning
* Usar el juego de damas (checkers)

Basado en el [juego de Damas](http://www.coolmath-games.com/0-checkers)

## Weighted Linear Function
Hay 2 tipos de fichas:

  * Normal: Se mueve hacia delante en diagonal
  * Rey: Se mueve en cualquier posición en diagonal

Podemos:

  * Contar ambos tipos de piezas
  * El balanceo del tablero (distribución de piezas entre el lado izquierdo y derecho)
  * Número de reyes atrapados

De PDF:


1. Number of pawns;
2. Number of kings;
3. Number of safe pawns (i.e. adjacent to the edge of the board);
4. Number of safe kings;
5. Number of moveable pawns (i.e. able to perform a move other than capturing).
6. Number of moveable kings. Parameters 5 and 6 are calculated taking no notice of capturing priority;
7. Aggregated distance of the pawns to promotion line;
8. Number of unoccupied fields on promotion line.

Es decir, para cada inciso, le ponemos un peso y sumamos cada característica, como en la sig. fórmula:

![alt text](https://github.com/VicDCruz/InteligenciaArtificial/blob/dev/Tarea3/FormulaDeEvaluacion.png)

## Dependencias:
* LISP

## Estructura de las listas:
Ver como árbol:
![alt text](https://github.com/VicDCruz/InteligenciaArtificial/blob/dev/Tarea3/ejemplo.png)

'('a nil
(('b nil (('e 12) ('f 3) ('g 8)))
('c nil (('h 2) ('i 4) ('j 6)))
('d nil (('k 14) ('l 2) ('m 5)))))

ie: un nodo está compuesto por (INFO Utilidad Sucesores)


## Proceso de *Alfa-Beta Pruning*
Está en la img ./alfaBetaPruning.png

![alt-text](https://github.com/VicDCruz/InteligenciaArtificial/blob/dev/Tarea3/alfaBetaPruning.png)

# Referencias
1. [Artificial Intelligence](https://books.google.com.mx/books?id=_ixmRlL9jcIC&pg=PA117&lpg=PA117&dq=weighted+linear+function+checkers&source=bl&ots=JPLF-ToFUZ&sig=IO5g7W3mFUXnvCMscgagHlKq9Fk&hl=es&sa=X&ved=0ahUKEwia99WdrZjaAhVJVK0KHcgxAyAQ6AEIJzAA#v=onepage&q=weighted%20linear%20function%20checkers&f=false)
2. [Artificial Intelligence Illuminated](https://books.google.com.mx/books?id=LcOLqodW28EC&pg=PA160&lpg=PA160&dq=weighted+linear+function+checkers&source=bl&ots=sXtgcDLDE-&sig=5SCo9xmozkVXBCUUvvbORLLmNlY&hl=es&sa=X&ved=0ahUKEwjS9oaaq5jaAhVPKqwKHVJaA1AQ6AEILjAB#v=onepage&q=weighted%20linear%20function%20checkers&f=false)
3. [Evolutionary-based heuristic generators for checkers and give-away checkers](https://pdfs.semanticscholar.org/91c9/d140267f3b008d00b330b6b0e9182fa4b62e.pdf) (con función heurística)
4. [Intro to AI](https://www.cs.rochester.edu/u/kautz/Courses/242spring2014/242ai06-alpha-beta-pruning.pdf) (de donde se obtuvo el ejemplo.lisp)

5. Referencia del formato: [Set4: Game-Playing](http://www.ics.uci.edu/~kkask/Fall-2016%20CS271/slides/04-games.pdf)

6. Formato de damas en JAVA: [Checkers, anyone?](https://www.javaworld.com/article/3014190/learn-java/checkers-anyone.html)
