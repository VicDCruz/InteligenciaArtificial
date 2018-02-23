(Defun imprime(lst)
(cond ((null lst) )
  ((atom lst) lst)
  (t (let ((elem (car lst)) (lstR (cdr lst)) (hermanos nil) (hijos nil))
    (loop
        (when (null elem) (return (reverse (list hermanos hijos))))
        (cond ((null elem) )
              ((atom elem) (push (imprime elem) hermanos) )
              (t (push (imprime elem) hijos) ))
        (setq elem (car lstR) lstR (cdr lstR)))))))
