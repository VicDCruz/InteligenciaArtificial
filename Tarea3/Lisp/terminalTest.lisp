; Reconocer si el nodo es el último o no
(defun terminalTest (state)
  (eql (fourth state) maxLvl))
