; Obtenemos el numero de Peones que están en el centro del Tablero, ie:
; Peones que están en la posición { 10, 11, 14, 15, 18, 19, 22, 23 }
(defun centralPawns (info)
  (setq res 0 black (first (first info)) red (first (second info)) pos '(10 11 14 15 18 19 22 23))

  (mapcar #'(lambda (elem)
    (if (not (null (find elem pos :test #'equal)))
      (incf res))) black)


  (mapcar #'(lambda (elem)
    (if (not (null (find elem pos :test #'equal)))
      (incf res))) red)
  res)
