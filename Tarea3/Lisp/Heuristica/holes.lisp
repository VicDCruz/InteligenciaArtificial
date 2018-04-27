; 11) Función que regresa la cantidad de agujeros que tienen por lo menos adyacentes tres fichas del mismo color.
(defun holes (info)
      (setq h (freeHoles info) black (append (first (first info)) (second (first info))) red (append (first (second info)) (second (second info))))
      (nextPawns h black red 0))

(defun nextPawns (lst black red count)
      (cond ((null lst) count)
            ((or (>= (tieneEn (adyacentes (car h)) black 0) 3) (>= (tieneEn (adyacentes (car h)) red 0) 3)) (nextPawns (cdr lst) black red (incf count)))
            (t (nextPawns (cdr lst) black red count))))
