(Defun grado(lst)
       (Cond ((null lst) )
             ((atom lst) )
             (t (let ((elem (car lst)) (lstR (cdr lst)) (cont 0))
               ; Contar cada ATOMO=Hijo
                (loop
                 (when (null elem) (return cont))
                 (if (atom elem)
                   (incf cont))
                 (setq elem (car lstR) lstR (cdr lstR)))
                (let ((elem (car lst)) (lstR (cdr lst)))
                 ; Checar si mi número de hijos es menor que el número de hijos
                 ; de mis hijos
                 (loop
                   (when (null elem) (return cont))
                   (if (listp elem)
                    (setq cont (max cont (grado elem))))
                   (setq elem (car lstR) lstR (cdr lstR))))))))
