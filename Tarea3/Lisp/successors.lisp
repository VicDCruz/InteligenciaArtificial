; Función que dado un estado, regresa sus estados sucesores
; ESTADO: (# INFO Utilidad)
(defun successors (state)
  (setq id (first state) cont 0 lvl (nth cont principal) elem (pop lvl) status nil)
  ; Obtenemos en qué nivel está STATE
  (loop
    ; Vamos a cada nivel de principal
    (when status (return (pop lvl)))
    (loop
      ; Vamos a cada elemento de cada nivel
      (when (and (not (null elem)) (eql id elem )) (setq status T) (return status))
      (when (null lvl) (setq status nil) (setq status nil) (return status))
      (setq elem (pop lvl)))
    (incf cont)
    (if (not status) (setq lvl (nth cont principal)))))
