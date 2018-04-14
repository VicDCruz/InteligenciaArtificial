; Buscamos todas las piezas que estan en la 3º fila de ambos lados, ie:
; piezas que están en las casilas [9 , 12] y [21 , 24]
(defun attackingPawns (info)
  (setq res 0 black (first (first info)) red (first (second info)))
  (print black)
  (print red)
  ; Para BLACK
  (mapcar #'(lambda (elem)
    (if (and (>= elem 9) (<= elem 12))
      (incf res))) black)

  ; Para RED
  (mapcar #'(lambda (elem)
    (if (and (>= elem 21) (<= elem 24))
      (incf res))) red)
  res)
