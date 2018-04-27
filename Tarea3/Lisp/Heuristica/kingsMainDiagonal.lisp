; 6) Función que regresa el número de reyes posicionados en la diagonal principal.
(defun kingsMainDiagonal (info)
(setq reyes (append (second (first info)) (second (second info))))
(tieneEn reyes '(4 8 11 15 18 22 25 29) 0))
