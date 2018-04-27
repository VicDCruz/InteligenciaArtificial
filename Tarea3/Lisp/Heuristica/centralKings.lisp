; Obtenemos el numero de Reyes que están en el centro del Tablero, ie:
; Reyes que están en la posición { 10, 11, 14, 15, 18, 19, 22, 23 }
(defun centralKings (info)
  (setq res 0 black (second (first info)) red (second (second info)) pos '(10 11 14 15 18 19 22 23))

  (mapcar #'(lambda (elem)
    (if (not (null (find elem pos :test #'equal)))
      (incf res))) black)


  (mapcar #'(lambda (elem)
    (if (not (null (find elem pos :test #'equal)))
      (incf res))) red)
  res)
