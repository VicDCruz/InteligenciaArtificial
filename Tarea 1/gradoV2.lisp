; GRADO
(defun grado(lst)
(let ((hermanos 0) (hijos 0))
  (mapcar #'(lambda (elem)
    (cond ((null elem) )
      ((atom elem) (incf hermanos))
      (t (setq hijos (max hijos (grado elem)))))) lst)
  (max hermanos hijos)))
