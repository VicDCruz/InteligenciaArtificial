; Función que crea el valor de utilidad de un estado, cuando no hay más
; movimientos posibles para ese estado
(defun eval (state)
  (+ (* 4 (attackingPawns (second state))) (* 4 (centralKings (second state)))
     (* 4 (centralPawns (second state))) (* 4 (defenderPieces (second state)))
     (* 4 (diagonalPawns (second state)))))
