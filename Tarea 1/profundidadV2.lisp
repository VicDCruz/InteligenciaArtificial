; PROFUNDIDAD
(defun profundidad(lst)
(let ((maximo 0))
  (mapcar #'(lambda (elem)
  (cond ((null elem))
    ((atom elem) )
    (t (setq maximo (max maximo (profundidad elem)))))) lst)
    (incf maximo)))
