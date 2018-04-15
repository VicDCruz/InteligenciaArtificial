; Lista que almacenará cada nivel de nodos
(setq principal '(nil nil nil nil))
; Cada nivel de principal
(setq n1 (first principal) n2 (second principal)
      n3 (third principal) n4 (fourth principal))

; Obtenemos el nodo de JAVA
; Leemos el archivo que contiene la info. para analizar
(setq args nil)
(let ((in (open "./infoLisp.txt" :if-does-not-exist nil)))
  (when in
    (loop for line = (read in nil)
         while line do (push line args))
    (close in)))
(setq info (first args))
; Formato de args: Lista con INFO, ie:
; (INFO( BLACK( PAWN() KING()) RED( PAWN() KING()))
