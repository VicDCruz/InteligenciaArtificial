; Leemos el archivo que contiene la info. para analizar
(setq args nil)
(let ((in (open "/Users/daniel/Documents/InteligenciaArtificial/Tarea2/infoLisp.txt" :if-does-not-exist nil)))
  (when in
    (loop for line = (read in nil)
         while line do (push line args))
    (close in)))

; Ruta de inicio y de fin
(setq inicio (nth 1 args) fin (nth 0 args))

; Se necesita la matriz de distancias para identificar cuáles son las ciudades
; que están conectadas a otras
(defun inicio(dummy)
  ; (setq ciudades '(A B C D E F)
  ;     costos '(5 8 25 20 21 0) ; Path Cost [h(n)] (Distancia en línea recta al destino final)
  ;     distancias '( ; Step Cost [g(n)] (¿Cuánto me cuesta llegar de una ciudad a otra?)
  ;                 (0 1 0 4 0 0) ; Para A
  ;                 (1 0 5 7 3 6) ; Para B
  ;                 (0 5 0 0 0 9) ; Para C
  ;                 (4 7 0 0 8 0) ; Para D
  ;                 (0 3 0 8 0 2) ; Para E
  ;                 (0 6 9 0 2 0) ; Para F
  ;                 )


  (setq ciudades (nth 4 args)
      costos (nth 2 args) ; Path Cost [h(n)] (Distancia en línea recta al destino final)
      distancias (nth 3 args)
      num 2
      recorrido '()))

; Estructura del NODO: (# Padre Nom f(n) SumGDePadre)
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
  (let ((contVecino 1))
    (mapcar #'(lambda (elem)
      (if (not (eq elem 0))
        (push (nth (- cont 1) ciudades) vecinos))
      (incf cont)
      (incf contVecino)) distVecinos))
  (reverse vecinos))

; Obtenemos el cálculo de f(x)=g(x)+h(x)
(defun calcF(ciudad destino)
  (+ (buscaDist ciudad destino) (third (buscaCiudad destino))))

; Obtenemos el nodo hijo con el valor mínimo de f(x)
(defun minimum (lst)
  (let (minimo '())
    (setq minimo (car lst))
    (mapcar #'(lambda (elem)
      (setq minimo (if (< (fourth minimo) (fourth elem)) minimo elem) )) lst)
  minimo))

; Obtenemos el nodo hijo con el segundo valor mínimo de f(x)
(defun segundoMin(lst primerMin)
  (let (minimo '())
    (setq minimo (if (not (eq (fourth (car lst)) (fourth primerMin))) (car lst) (cadr lst)))
    (if (null minimo) (setq minimo (car lst)))
    (mapcar #'(lambda (elem)
      (setq minimo (if (and (< (fourth elem) (fourth minimo)) (> (fourth elem) (fourth primerMin))) elem minimo))) lst)
    minimo))

; Obtenemos la distancia total entre inicio y fin
(defun getKm(recorrido)
  (cond ((null recorrido) )
        ((listp recorrido)
          (cond ((>= (length recorrido) 2)
                  (incf km (buscaDist (first recorrido) (second recorrido)))
                  (pop recorrido)
                  (getKm recorrido))
            (t km)))
        (t km))
)

; Suponer que NIL = ∞
(defun mejorRuta(ini fin)
  (inicio nil)
  (setq contPasos 0 km 0)
  (rbfs fin (list 1 0 ini (calcF ini ini) 0) nil)
  (push ini recorrido)
  (getKm recorrido)
  ; La solución la ponemos en ese archivo:
  (with-open-file (my-stream
                 "/Users/daniel/Documents/InteligenciaArtificial/Tarea2/solLisp.txt"
                 :direction :output
                 :if-exists :supersede)
    (print recorrido my-stream)
    (print km my-stream)))
  ; Recordar que el nodo inicial esta (# padre Nom f(n) SumGDePadre)

(defun rbfs(fin nodo fLimit)
  (incf contPasos)
  (when (eql fin (third nodo)) (return-from rbfs (list (third nodo))))
  (let ((sucesores '()) (mejor nil))
    (let (hijos '())
      (setq hijos (obtenSucesores (third nodo)))
      (mapcar #'(lambda (elem)
        (push (list num (car nodo) elem nil (+ (fifth nodo) (buscaDist (third nodo) elem))) sucesores) ; f(n) se pone después
        (incf num)) hijos))
    (when (null sucesores) (return-from rbfs (list 'Fallo nil)))
    (mapcar #'(lambda (elem)
      (setf (fourth elem) (max (fourth nodo) (+ (calcF (third nodo) (third elem)) (fifth nodo))))) sucesores)
    (print sucesores)
    (loop
      (setq mejor (minimum sucesores))
      (when (> (fourth mejor) (if (null fLimit) (+ 1 (fourth mejor)) fLimit)) (return-from rbfs (list 'Fallo (fourth mejor))))
      (setq alternativa (segundoMin sucesores mejor))
      (setq resRBFS (rbfs fin mejor (min (if (null fLimit) (+ 1 (fourth alternativa)) fLimit) (fourth alternativa))) resultado (car resRBFS))
      (setf (fourth mejor) (second resRBFS))
      (when (not (eql resultado 'Fallo)) (push resultado recorrido) (return-from rbfs (list (third nodo)))))))

(mejorRuta inicio fin)
