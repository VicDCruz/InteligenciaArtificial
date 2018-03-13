; DUMMY!!!
; Se necesita la matriz de distancias para identificar cuáles son las ciudades
; que están conectadas a otras
(defun inicio(dummy)
  (setq ciudades '(A B C D E F)
      costos '(5 8 25 20 21 0) ; Path Cost [h(n)] (Distancia en línea recta al destino final)
      distancias '( ; Step Cost [g(n)] (¿Cuánto me cuesta llegar de una ciudad a otra?)
                  (0 1 0 4 0 0) ; Para A
                  (1 0 5 7 3 6) ; Para B
                  (0 5 0 0 0 9) ; Para C
                  (4 7 0 0 8 0) ; Para D
                  (0 3 0 8 0 2) ; Para E
                  (0 6 9 0 2 0) ; Para F
                  )
      num 2
      recorrido '()))

; Estructura del NODO: (# Padre Nom f(n) NomPadre)
; f(n) = g(n) + h(n)

; Regresa un nodo de una ciudad con su posición, su nombre y su costo, dado un NOMBRE
; EG: (1 A 1)
(defun buscaCiudad(nom)
  (setq tmp (car ciudades) tmpC (cdr ciudades) cont 1)
  (loop
    (when (eq nom tmp) (return (list cont tmp (nth (- cont 1) costos))))
    (setq tmp (car tmpC) tmpC (cdr tmpC) cont (+ 1 cont))))

; Busca la distancia entre un nodo de INICIO y uno de FIN (con NOMBRES)
; ie: Va a la matriz de distancias, INICIO es la tupla y FIN es la columna
(defun buscaDist(ini fin)
  (setq numIC (car (buscaCiudad ini)) numFC (car (buscaCiudad fin))
    iniC (nth (- numIC 1) distancias))
  (nth (- numFC 1) iniC))

; Va a la matriz de distancias, obtiene la tupla de CIUDAD y obtiene el nombre
; de la ciudad que tiene una distancia diferente de 0
(defun obtenSucesores(ciudad padre)
  (setq infoC (buscaCiudad ciudad) vecinos '() distVecinos (nth (- (car infoC) 1) distancias) cont 1)
  (let ((contVecino 1))
    (mapcar #'(lambda (elem)
      (if (and (not (eq elem 0)) (not (eql padre (nth (- cont 1) ciudades))))
        (push (nth (- cont 1) ciudades) vecinos))
      (incf cont)
      (incf contVecino)) distVecinos))
  (reverse vecinos))

(defun calcF(ciudad destino)
  (+ (buscaDist ciudad destino) (third (buscaCiudad ciudad))))

(defun minimum (lst)
  (let (minimo '())
    (setq minimo (car lst))
    (mapcar #'(lambda (elem)
      (setq minimo (if (< (fourth minimo) (fourth elem)) minimo elem) )) lst)
  minimo))

(defun segundoMin(lst primerMin)
  (let (minimo '())
    (setq minimo (if (not (eq (fourth (car lst)) (fourth primerMin))) (car lst) (cadr lst)))
    (if (null minimo) (setq minimo (car lst)))
    (mapcar #'(lambda (elem)
      (setq minimo (if (and (< (fourth elem) (fourth minimo)) (> (fourth elem) (fourth primerMin))) elem minimo))) lst)
    minimo))

; Suponer que NIL = ∞
(defun mejorRuta(ini fin)
  (inicio nil)
  (setq contPasos 0)
  (rbfs fin (list 1 0 ini (calcF ini ini) nil) nil)
  (push ini recorrido)
  recorrido)
  ; Recordar que el nodo inicial esta (# padre Nom f(n) NomPadre)

(defun rbfs(fin nodo fLimit)
  (incf contPasos)
  (when (eq contPasos 200) (return-from rbfs 'Nada))
  (when (eq fin (third nodo)) (return-from rbfs (list (third nodo))))
  (let (sucesores '())
    (let (hijos '())
      (setq hijos (obtenSucesores (third nodo) (fifth nodo)))
      (print hijos)
      (mapcar #'(lambda (elem)
        (push (list num (car nodo) elem nil (third nodo)) sucesores) ; f(n) se pone después
        (incf num)) hijos))
    (print sucesores)
    (when (null sucesores) (return-from rbfs (list 'Fallo nil)))
    (mapcar #'(lambda (elem) ; CUIDADO puede haber errores, tal vez no se actualice elem al hacer setq
      (setf (fourth elem) (max (fourth nodo) (calcF (third elem) fin)))) sucesores)
    (print sucesores)
    (loop
      (setq mejor (minimum sucesores))
      (print 'mejor)
      (print mejor)
      (when (> (fourth mejor) (if (null fLimit) (+ 1 (fourth mejor)) fLimit)) (return (list 'Fallo (fourth mejor))))
      (setq alternativa (segundoMin sucesores mejor))
      (print 'alternativa)
      (print alternativa)
      (setq resRBFS (rbfs fin mejor (min (if (null fLimit) (+ 1 (fourth alternativa)) fLimit) (fourth alternativa))) resultado (car resRBFS))
      (setf (fourth mejor) (second resRBFS))
      (when (not (eql resultado 'Fallo)) (push resultado recorrido) (return-from rbfs (list (third nodo))))))))
