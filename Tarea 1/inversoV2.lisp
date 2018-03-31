; INVERSO
(defun inverso(lst)
(let ((lista nil))
  (mapcar #'(lambda (elem)
    (cond ((null elem) )
      ((atom elem) (push elem lista))
      (t (push (inverso elem) lista)))) lst)
  lista))
