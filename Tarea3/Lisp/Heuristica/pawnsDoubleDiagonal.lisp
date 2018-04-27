; 7) Función que regresa el número de peones situados en doble diagonal.
(defun pawnsDoubleDiagonal (info)
(setq peones (append (first (first info)) (first (second info))))
(tieneEn peones '(4 5 8 9 11 14 15 18 22 23 25 27 29 32) 0))	
