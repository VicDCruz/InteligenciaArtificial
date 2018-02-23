(Defun inverso(lst)
  (cond ((null lst) )
        ((atom lst) lst)
        (t (let ((elem (car lst)) (lstR (cdr lst)) (temp nil))
          (loop
            (when (null elem) (return temp))
            (push (inverso elem) temp)
            (setq elem (car lstR) lstR (cdr lstR)))))))
