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
