; DUMMY!!!
(defun inicio(dummy)
  (setq ciudades '(A B C D)
      costos '(0 7 10 15) ; Path Cost [h(n)]
      distancias '( ; Step Cost [g(n)]
                  (0 0 1 0) ; Para A
                  (0 0 7 10) ; Para B
                  (1 7 0 6) ; Para C
                  (0 10 6 0) ; Para D
                  )
      num 2
      recorrido '()))

; Estructura del NODO: (# Padre Nom f(n))
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
(defun obtenSucesores(ciudad)
  (setq infoC (buscaCiudad ciudad) vecinos '() distVecinos (nth (- (car infoC) 1) distancias) cont 1)
  (mapcar #'(lambda (elem)
    (if (not (eq elem 0))
      (push (nth (- cont 1) ciudades) vecinos))
    (incf cont)) distVecinos)
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
    (setq minimo (car lst))
    (mapcar #'(lambda (elem)
      (setq minimo (if (and (< (fourth elem) (fourth minimo)) (> (fourth elem) (fourth primerMin))) elem minimo))) lst)
  minimo))

; Suponer que NIL = ∞
(defun mejorRuta(ini fin)
  (inicio nil)
  (setq contPasos 0)
  (rbfs fin (list 1 0 ini (calcF ini fin)) nil))

(defun rbfs(fin nodo fLimit)
  (when (eq contPasos 20) (return-from rbfs 'Nada))
  (when (eq fin (third nodo)) (push nodo recorrido) (return-from rbfs (list (third nodo))))
  (let (sucesores '())
    (let (hijos '())
      (setq hijos (obtenSucesores (third nodo)))
      (print hijos)
      (mapcar #'(lambda (elem)
        (push (list num (car nodo) elem nil) sucesores) ; f(n) se pone después
        (incf num)) hijos))
    (print sucesores)
    (when (null sucesores) (return-from rbfs (list 'Fallo nil)))
    (mapcar #'(lambda (elem) ; CUIDADO puede haber errores, tal vez no se actualice elem al hacer setq
      (setf (fourth elem) (max (fourth nodo) (calcF (third elem) fin)))) sucesores)
    (print sucesores)
    (loop
      (setq mejor (minimum sucesores))
      (when (> (fourth mejor) (if (null fLimit) (+ 1 (fourth mejor)) fLimit)) (return (list 'Fallo (fourth mejor))))
      (setq alternativa (segundoMin sucesores mejor))
      (print alternativa)
      (setq resRBFS (rbfs fin mejor (min (if (null fLimit) (+ 1 (fourth alternativa)) fLimit) (fourth alternativa))) resultado (car resRBFS))
      (setf (fourth mejor) (second resRBFS))
      (if (not (eql resultado 'Fallo)) (return-from rbfs resultado)))))
