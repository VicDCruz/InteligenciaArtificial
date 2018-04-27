; Obtiene el número de peones que están en la diagonal principal
; ie: la mayor diagonal que va de izquierda a derecha
; en el rango { 29, 25, 22, 18, 15, 11, 8, 4 }
(defun diagonalPawns (info)
  (setq res 0 black (first (first info)) red (first (second info)) pos '( 29 25 22 18 15 11 8 4 ))

  (mapcar #'(lambda (elem)
    (if (find elem pos :test #'equal)
      (incf res))) (append black red))
  res)
