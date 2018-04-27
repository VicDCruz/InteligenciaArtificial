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
      (* 1 (pawnsDoubleDiagonal info))
      (* 3 (eatsRival info (fourth state))))))
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
(defun eatsRival (info lvl)

  (if (and (< (+ (length (first (first info))) (length (second (first info))) (length (first (second info))) (length (second (second info))))
              (+ (length (first (first infoFirst))) (length (second (first infoFirst))) (length (first (second infoFirst))) (length (second (second infoFirst)))))
            (oddp lvl))
    9999 0))
