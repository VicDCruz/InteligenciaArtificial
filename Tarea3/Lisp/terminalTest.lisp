; Reconocer si el nodo es el último o no
(defun terminalTest (state)
  (setq ult (car (last principal)) elem (pop ult))
  (loop
    (when (and (not (null elem)) (not (atom elem)) (find state elem :test #'equal)) (return T))
    (when (null elem) (return nil))
    (setq elem (pop ult)))))
