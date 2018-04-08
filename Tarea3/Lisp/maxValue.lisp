; Función que regresa el nodo (o estado) con el máximo valor, dado sus 'hijos'.
; -∞ = most-negative-fixnum
; +∞ = most-positive-fixnum
(defun maxValue (state, a, b)
  (if (terminalTest state)
    (return-from maxValue (utility state)))
  (let ((v most-negative-fixnum))
    (mapcar #'(lambda (s)
      (setq v (max v (minValue s a b)))
      (if (>= v b)
        (return-from maxValue v))
      (setq a (max a v))
      (return-from maxValue v)) (successors state))))
