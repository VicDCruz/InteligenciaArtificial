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
