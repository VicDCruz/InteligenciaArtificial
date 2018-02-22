(Defun profundidad(lst)
       (cond ((null lst) 0)
         ((atom lst) 0) ; Cuando entra un solo Atom porque es del formato (L)
         ; de la iteración anterior
         ; Crea variables locales
         (t (let ((elem (car lst)) (lstR (cdr lst)) (cont 0))
           (loop
             ; Lo hace mientras que el elemento de la lista no es nula: ()
             (when (null elem) (return (incf cont)))
             ; Ve si lo que tiene como la profundida de uno de sus hijos es
             ; mayor que el que le sigue
             (setq cont (max cont (profundidad elem)))
             (setq elem (car lstR) lstR (cdr lstR)))))
         ))
