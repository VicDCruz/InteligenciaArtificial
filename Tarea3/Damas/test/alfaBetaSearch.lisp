; Obtenemos el nodo de JAVA
; Leemos el archivo que contiene la info. para analizar
(setq args nil)
(let ((in (open "./infoLisp.txt" :if-does-not-exist nil)))
  (when in
    (loop for line = (read in nil)
         while line do (push line args))
    (close in)))
(setq info (first args))
(print info)
; Formato de args: Lista con INFO, ie:
; (INFO( BLACK( PAWN() KING()) RED( PAWN() KING()))

(setq allSuccessors nil maxLvl 2 id 0)

; Algoritmo principal de la búsqueda
(defun alfaBetaSearch (state)
  (setq allSuccessors nil id 0)
  (let ((v (maxValue state most-negative-fixnum most-positive-fixnum)))
    (setq resFinal (getSuccessor v)))
  (with-open-file (my-stream
                 "./solLisp.txt"
                 :direction :output
                 :if-exists :supersede)
    (print (first (first (second resFinal))) my-stream)
    (print (second (first (second resFinal))) my-stream)
    (print (first (second (second resFinal))) my-stream)
    (print (second (second (second resFinal))) my-stream)))

; Obtenemos el sucesor que cumple con el VALUE en una lista con todos los nodos
(defun getSuccessor (value)
  (mapcar #'(lambda (node)
    (if (and (eql (third node) value) (eq (fourth node) 1))
      (return-from getSuccessor node))) (reverse allSuccessors)))
  ; Función que regresa el nodo (o estado) con el máximo valor, dado sus 'hijos'.
  ; -∞ = most-negative-fixnum
  ; +∞ = most-positive-fixnum
  (defun maxValue (state a b)
    (print 'Max)
    (when (terminalTest state)
      (setf (third state) (utility state))
      (print 'Utilidad)
      (print (third state))
      (print 'FinUtilidad)
      (push state allSuccessors)
      (return-from maxValue (third state)))
    (successors state)
    (let ((v most-negative-fixnum))
      (mapcar #'(lambda (s)
        (setq v (max v (minValue s a b)))
        (when (>= v b)
          (setf (third state) (utility state))
          (print 'Utilidad)
          (print (third state))
          (print 'FinUtilidad)
          (push state allSuccessors)
          (return-from maxValue v))
        (setq a (max a v))) (successors state))
      (setf (third state) (utility state))
      (print 'Utilidad)
      (print (third state))
      (print 'FinUtilidad)
      (push state allSuccessors)
      (return-from maxValue v)))
; Función que regresa el nodo (o estado) con el máximo valor, dado sus 'hijos'.
; -∞ = most-negative-fixnum
; +∞ = most-positive-fixnum
(defun minValue (state a b)
  (print 'Min)
  (when (terminalTest state)
    (setf (third state) (utility state))
    (print 'Utilidad)
    (print (third state))
    (print 'FinUtilidad)
    (push state allSuccessors)
    (return-from minValue (third state)))
  (let ((v most-positive-fixnum))
    (mapcar #'(lambda (s)
      (setq v (min v (maxValue s a b)))
      (when (<= v a)
        (setf (third state) (utility state))
        (print 'Utilidad)
        (print (third state))
        (print 'FinUtilidad)
        (push state allSuccessors)
        (return-from minValue v))
      (setq b (min b v))) (successors state))
    (setf (third state) (utility state))
    (print 'Utilidad)
    (print (third state))
    (print 'FinUtilidad)
    (push state allSuccessors)
    (return-from minValue v)))
; Obtenemos el árbol completo hasta un nivel 4 de todas las jugadas posibles
; Recordar: Nodo (# info heurística lvl)
; eg: (successors '(0 (((1 2 3 4) (5)) ((25 26 27 28) ())) nil 0))
; (successors '(0 (((1 2 3 4 5 6 7 8 9 10 11 12) ()) ((21 22 23 24 25 26 27 28 29 30 31 32) ())) nil 0))
(defun successors (state)
  (setq myLvl (+ (fourth state) 1) resSuccessors nil tmpPawn nil tmpKing nil)
  (if (oddp myLvl)
    (setq player (first (second state)) rival (second (second state)))
    (setq player (second (second state)) rival (first (second state))))
  (setq tmpPawnRival (first rival) tmpKingRival (second rival))
  (mapcar #'(lambda (checker)

    (setq posib1 (movePawn checker (oddp myLvl)) posib2 (+ posib1 1))
    (if (find checker '(4 12 20 28))
      (setq posib2 nil))
    (if (find checker '(5 13 21 29))
      (setq posib1 nil))
    (cond ((null posib1) )
      ((not (find posib1 (append (first player) (second player) (first rival) (second rival))))
          (push posib1 tmpPawn))
      ((and (find posib1 rival) (not (find (movePawn posib1 (oddp myLvl)) (append (first rival) (second rival)))))
          (push (movePawn posib1 (oddp myLvl)) tmpPawn)
          (setq tmpPawnRival (remove posib1 tmpPawnRival))
          (setq tmpKingRival (remove posib1 tmpKingRival)))
      ((find posib1 (if (oddp myLvl) '(29 30 31 32) '(1 2 3 4)))
          (push posib1 tmpKing)))
    (push2ResSuccessors checker tmpPawn tmpKing tmpPawnRival tmpKingRival)
    (setq tmpPawn nil tmpKing nil tmpPawnRival (first rival) tmpKingRival (second rival))

    (cond ((null posib2) )
      ((not (find posib2 (append (first player) (second player) (first rival) (second rival))))
          (push posib2 tmpPawn))
      ((and (find posib2 rival) (not (find (+ (movePawn posib2 (oddp myLvl)) 1) (append (first rival) (second rival)))))
          (push (+ (movePawn posib2 (oddp myLvl)) 1) tmpPawn)
          (setf tmpPawnRival (remove posib2 tmpPawnRival))
          (setf tmpKingRival (remove posib2 tmpKingRival)))
      ((find posib2 (if (oddp myLvl) '(29 30 31 32) '(1 2 3 4)))
          (push posib2 tmpKing)))
    (push2ResSuccessors checker tmpPawn tmpKing tmpPawnRival tmpKingRival)
    (setq tmpPawn nil tmpKing nil tmpPawnRival (first rival) tmpKingRival (second rival))) (first player))

  (mapcar #'(lambda (checker)
    (setq posib1 (movePawn checker (oddp myLvl)) posib2 (+ posib1 1) posib3 (- posib1 8) posib4 (+ posib3 1) )
    (if (find checker '(1 2 3))
      (setq posib3 nil posib4 nil))
    (if (find checker '(4))
      (setq posib2 nil posib3 nil posib4 nil))
    (if (find checker '(5 13 21))
      (setq posib1 nil posib3 nil))
    (if (find checker '(29))
      (setq posib1 nil posib2 nil posib3 nil))
    (if (find checker '(30 31 32))
      (setq posib1 nil posib2 nil))
    (if (find checker '(12 20 28))
      (setq posib2 nil posib4 nil))

    (cond ((null posib1) )
      ((not (find posib1 (append (first player) (second player) (first rival) (second rival))))
          (push posib1 tmpKing))
      ((and (find posib1 rival) (not (find (movePawn posib1 (oddp myLvl)) (append (first rival) (second rival)))))
          (push (movePawn posib1 (oddp myLvl)) tmpKing)
          (setq tmpPawnRival (remove posib1 tmpPawnRival))
          (setq tmpKingRival (remove posib1 tmpKingRival))))
    (push2ResSuccessors checker tmpPawn tmpKing tmpPawnRival tmpKingRival)
    (setq tmpPawn nil tmpKing nil tmpPawnRival (first rival) tmpKingRival (second rival))

    (cond ((null posib2) )
      ((not (find posib2 (append (first player) (second player) (first rival) (second rival))))
          (push posib2 tmpKing))
      ((and (find posib2 rival) (not (find (+ (movePawn posib2 (oddp myLvl)) 1) (append (first rival) (second rival)))))
          (push (+ (movePawn posib2 (oddp myLvl)) 1) tmpKing)
          (setf tmpPawnRival (remove posib2 tmpPawnRival))
          (setf tmpKingRival (remove posib2 tmpKingRival))))
    (push2ResSuccessors checker tmpPawn tmpKing tmpPawnRival tmpKingRival)
    (setq tmpPawn nil tmpKing nil tmpPawnRival (first rival) tmpKingRival (second rival))

    (cond ((null posib3) )
      ((not (find posib3 (append (first player) (second player) (first rival) (second rival))))
          (push posib3 tmpKing))
      ((and (find posib3 rival) (not (find (- (movePawn posib3 (oddp myLvl)) 8) (append (first rival) (second rival)))))
          (push (- (movePawn posib3 (oddp myLvl)) 8) tmpKing)
          (setf tmpPawnRival (remove posib3 tmpPawnRival))
          (setf tmpKingRival (remove posib3 tmpKingRival))))
    (push2ResSuccessors checker tmpPawn tmpKing tmpPawnRival tmpKingRival)
    (setq tmpPawn nil tmpKing nil tmpPawnRival (first rival) tmpKingRival (second rival))

    (cond ((null posib4) )
      ((not (find posib4 (append (first player) (second player) (first rival) (second rival))))
          (push posib4 tmpKing))
      ((and (find posib4 rival) (not (find (- (movePawn posib4 (oddp myLvl)) 7) (append (first rival) (second rival)))))
          (push (- (movePawn posib4 (oddp myLvl)) 7) tmpKing)
          (setf tmpPawnRival (remove posib4 tmpPawnRival))
          (setf tmpKingRival (remove posib4 tmpKingRival))))
    (push2ResSuccessors checker tmpPawn tmpKing tmpPawnRival tmpKingRival)
    (setq tmpPawn nil tmpKing nil tmpPawnRival (first rival) tmpKingRival (second rival))) (second player))

  (reverse resSuccessors))

(defun movePawn (checker isBlack)
  (if isBlack
    (if (or (and (>= checker 1) (<= checker 4))
            (and (>= checker 9) (<= checker 12))
            (and (>= checker 17) (<= checker 20))
            (and (>= checker 25) (<= checker 28)))
      (return-from movePawn (+ checker 4))
      (return-from movePawn (+ checker 3))))
  (if (not isBlack)
    (if (or (and (>= checker 1) (<= checker 4))
            (and (>= checker 9) (<= checker 12))
            (and (>= checker 17) (<= checker 20))
            (and (>= checker 25) (<= checker 28)))
      (return-from movePawn (- checker 4))
      (return-from movePawn (- checker 5)))))

(defun push2ResSuccessors (checker tmpPawn tmpKing tmpPawnRival tmpKingRival)
  (if (null tmpPawn)
    (setq tmpPawn (first player)) (setq tmpPawn (append tmpPawn (remove checker (first player)))))
  (if (null tmpKing)
    (setq tmpKing (second player)) (setq tmpKing (append tmpKing (remove checker (second player)))))
  (if (not (equal player (list tmpPawn tmpKing)))
    (if (oddp myLvl)
      (push (list (incf id) (list (list tmpPawn tmpKing) (list tmpPawnRival tmpKingRival)) nil myLvl) resSuccessors)
      (push (list (incf id) (list (list tmpPawnRival tmpKingRival) (list tmpPawn tmpKing)) nil myLvl) resSuccessors))))
; Reconocer si el nodo es el último o no
(defun terminalTest (state)
  (eql (fourth state) maxLvl))
; Obtenemos la heurística total con las funciones que checa
; diferentes posiciones de las Damas
(defun utility (state)
  (let ((info (second state)))
    (+ (* 3 (attackingPawns info))
      (* 3 (centralKings info))
      (* 1 (centralPawns info))
      (* 2 (defenderPieces info))
      (* 1 (diagonalPawns info))
      (* 1 (holes info))
      (* 1 (kingsDoubleDiagonal info))
      (* 1 (kingsMainDiagonal info))
      (* 2 (lonerKings info))
      (* 2 (lonerPawns info))
      (* 1 (pawnsDoubleDiagonal info)))))
; Buscamos todas las piezas que estan en la 3º fila de ambos lados, ie:
; piezas que están en las casilas [9 , 12] y [21 , 24]
(defun attackingPawns (info)
  (setq res 0 black (first (first info)) red (first (second info)))
  ; Para BLACK
  (mapcar #'(lambda (elem)
    (if (and (>= elem 9) (<= elem 12))
      (incf res))) black)

  ; Para RED
  (mapcar #'(lambda (elem)
    (if (and (>= elem 21) (<= elem 24))
      (incf res))) red)
  res)
; Funciones auxiliares para las funciones heurísticas

; Función que evalua si un número n pertenece a una lista lst.
(defun pertenece (n lst)
  (cond ((null lst) nil)
    ((eq n (car lst)) T)
    (t (pertenece n (cdr lst)))))

; Función que regresa cuantos elementos de la lista L1 están en la lista L2; r se inicializa en 0.
(defun tieneEn (l1 l2 r)
  (cond ((null l1) r)
    ((pertenece (car l1) l2) (tieneEn (cdr l1) l2 (incf r)))
    (t (tieneEn (cdr l1) l2 r))))

; Función que regresa una lista con los indices adyacentes a un índice dado. (IMAGEN SE ENVÍA ANEXADA A ESTE DOCUMENTO)
(defun adyacentes (i)
  (cond ((null i) 0)
    ((pertenece i '(6 7 8 14 15 16 22 23 24)) (list (- i 5) (- i 4) (+ i 3) (+ i 4)))
    ((pertenece i '(9 10 11 17 18 19 25 26 27)) (list (- i 4) (- i 3) (+ i 4) (+ i 5)))
    ((pertenece i '(5 12 13 20 21 28)) (list (- i 4) (+ i 4)))
    ((pertenece i '(1 2 3)) (list (+ i 4) (+ i 5)))
    ((pertenece i '(30 31 32)) (list (- i 5) (- i 4)))
    ((eql i 29) (list (- i 4)))
    ((eql i 4) (list (+ i 4)))))

; Función que evalua si los adyacentes a cada elemento de la lista lst no están asignados, por lo tanto la pieza en el indice no es adyacente con nada.
(defun solitarios (lst r)
  (cond ((null lst) r)
    ((eq (tieneEn (adyacentes (car lst)) lst 0) 0) (solitarios (cdr lst) (incf r)))
    (t (solitarios (cdr lst) r))))

; Función que regresa una lista con los índices vacíos del tablero.
(defun freeHoles (info)
  (setq fichas (append (first (first info)) (second (first info)) (first (second info)) (second (second info))) tablero '(1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32) lst '())
  (libres tablero fichas lst))

(defun libres (l1 l2 l3)
  (cond ((null l1) l3)
    ((pertenece (car l1) l2) (libres (cdr l1) l2 l3))
    (t (push (car l1) l3) (libres (cdr l1) l2 l3))))
; Obtenemos el numero de Reyes que están en el centro del Tablero, ie:
; Reyes que están en la posición { 10, 11, 14, 15, 18, 19, 22, 23 }
(defun centralKings (info)
  (setq res 0 black (second (first info)) red (second (second info)) pos '(10 11 14 15 18 19 22 23))

  (mapcar #'(lambda (elem)
    (if (not (null (find elem pos :test #'equal)))
      (incf res))) black)


  (mapcar #'(lambda (elem)
    (if (not (null (find elem pos :test #'equal)))
      (incf res))) red)
  res)
; Obtenemos el numero de Peones que están en el centro del Tablero, ie:
; Peones que están en la posición { 10, 11, 14, 15, 18, 19, 22, 23 }
(defun centralPawns (info)
  (setq res 0 black (first (first info)) red (first (second info)) pos '(10 11 14 15 18 19 22 23))

  (mapcar #'(lambda (elem)
    (if (not (null (find elem pos :test #'equal)))
      (incf res))) black)


  (mapcar #'(lambda (elem)
    (if (not (null (find elem pos :test #'equal)))
      (incf res))) red)
  res)
; Obtiene el número de piezas en las dos filas DEFENSIVAS,
; ie: [1 , 8] o [25 , 32]
(defun defenderPieces (info)
  (setq res 0 black (first info) red (second info))

  ; Para BLACK
  (mapcar #'(lambda (elem)
    (if (and (>= elem 1) (<= elem 8))
      (incf res))) (append (first black) (second black)))

  ; Para RED
  (mapcar #'(lambda (elem)
    (if (and (>= elem 25) (<= elem 32))
      (incf res))) (append (first red) (second red)))

  res)
; Obtiene el número de peones que están en la diagonal principal
; ie: la mayor diagonal que va de izquierda a derecha
; en el rango { 29, 25, 22, 18, 15, 11, 8, 4 }
(defun diagonalPawns (info)
  (setq res 0 black (first (first info)) red (first (second info)) pos '( 29 25 22 18 15 11 8 4 ))

  (mapcar #'(lambda (elem)
    (if (find elem pos :test #'equal)
      (incf res))) (append black red))
  res)
; 11) Función que regresa la cantidad de agujeros que tienen por lo menos adyacentes tres fichas del mismo color.
(defun holes (info)
      (setq h (freeHoles info) black (append (first (first info)) (second (first info))) red (append (first (second info)) (second (second info))))
      (nextPawns h black red 0))

(defun nextPawns (lst black red count)
      (cond ((null lst) count)
            ((or (>= (tieneEn (adyacentes (car h)) black 0) 3) (>= (tieneEn (adyacentes (car h)) red 0) 3)) (nextPawns (cdr lst) black red (incf count)))
            (t (nextPawns (cdr lst) black red count))))
; 8) Función que regresa el número de reyes situados en doble diagonal.
(defun kingsDoubleDiagonal (info)
(setq reyes (append (second (first info)) (second (second info))))
(tieneEn reyes '(4 5 8 9 11 14 15 18 22 23 25 27 29 32) 0))
; 6) Función que regresa el número de reyes posicionados en la diagonal principal.
(defun kingsMainDiagonal (info)
(setq reyes (append (second (first info)) (second (second info))))
(tieneEn reyes '(4 8 11 15 18 22 25 29) 0))
; 10) Función que regresa la cantidad de reyes solitarios.
(defun lonerKings (info)
(setq reyes (append (second (first info)) (second (second info))))
(solitarios reyes 0))
; 9) Función que regresa la cantidad de peones solitarios. La pieza solitaria se define como la que no está adyacente a ninguna otra pieza.
(defun lonerPawns (info)
(setq peones (append (first (first info)) (first (second info))))
(solitarios peones 0))
; 7) Función que regresa el número de peones situados en doble diagonal.
(defun pawnsDoubleDiagonal (info)
(setq peones (append (first (first info)) (first (second info))))
(tieneEn peones '(4 5 8 9 11 14 15 18 22 23 25 27 29 32) 0))
