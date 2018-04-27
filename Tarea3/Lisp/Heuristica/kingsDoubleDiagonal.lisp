; 8) Función que regresa el número de reyes situados en doble diagonal.
(defun kingsDoubleDiagonal (info)
(setq reyes (append (second (first info)) (second (second info))))
(tieneEn reyes '(4 5 8 9 11 14 15 18 22 23 25 27 29 32) 0))
