(Defun imprime(lst)
(setq abuelos nil padres nil hijos nil) ; Quiero que funcionene como una cola
(push '_ hijos)
(getLvl lst)
(reverse abuelos)) ;Para imprimir la ultima iteracion que se dejo porque el
  ; algoritmo se salio antes

(Defun getLvl(lst)
(loop ; Iteramos toda la lst y vamos elemento a elemento
  ; Al terminar con la lista que se esta iterando:
  (when (null lst)
    (if (eq (car (last hijos)) '_) ; Lo que nos ayuda a ver a todos los hijos
    ; de todos los padres están "agrupados" en Hijos
      (let ((hijosTmp (reverse hijos)) )
        (pop hijosTmp) ; Quitamos el "_" de la cola
        (setq hijos (reverse hijosTmp)) ; Actualizamos Hijos sin ese "_"
        (if (not (null hijos)) ; Para que no se quede en LOOP
          (push '_ hijos))
        (if (not (null abuelos)) ; Para que no imprima a lo menso NIL
          (print (reverse abuelos)))
        ; Pasamos Padres->Abuelos
        (setq abuelos padres)
        (setq padres nil)))
    (return nil)) ; El 8 es solo un valor x
  (setq elem (pop lst)) ; Vamos al nuevo elemento de la lista y lo borramos de ella
  (cond ((null elem) )
    ((atom elem) (push elem padres))
    (t (push elem hijos))))

(if (not (null hijos))
  (let ((hijosTmp (reverse hijos)) )
    (setq nuevoHijo (pop hijosTmp))
    (setq hijos (reverse hijosTmp))
    (getLvl nuevoHijo))))
