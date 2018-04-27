; Obtenemos el nodo de JAVA
; Leemos el archivo que contiene la infoFirst. para analizar
(setq args nil)
(let ((in (open "/Users/daniel/Documents/InteligenciaArtificial/Tarea3/infoLisp.txt" :if-does-not-exist nil)))
  (when in
    (loop for line = (read in nil)
         while line do (push line args))
    (close in)))
(setq maxLvl (first args))
(setq infoFirst (second args))
; Formato de args: Lista con infoFirst, ie:
; (infoFirst( BLACK( PAWN() KING()) RED( PAWN() KING()))

; Algoritmo principal de la búsqueda
(defun alfaBetaSearch (state)
  (setq allSuccessors nil id 0)
  (let ((valFinal (maxValue state most-negative-fixnum most-positive-fixnum)))
    (setq resFinal (getSuccessor valFinal))
    (print 'VALORFINAL)
    (print resFinal))
  (with-open-file (my-stream
                 "/Users/daniel/Documents/InteligenciaArtificial/Tarea3/solLisp.txt"
                 :direction :output
                 :if-exists :supersede)
    (print (first (first (second resFinal))) my-stream)
    (print (second (first (second resFinal))) my-stream)
    (print (first (second (second resFinal))) my-stream)
    (print (second (second (second resFinal))) my-stream)))

; Obtenemos el sucesor que cumple con el VALUE en una lista con todos los nodos
(defun getSuccessor (value)
  (mapcar #'(lambda (node)
    (if (and (eql (third node) value) (eq (fourth node) 1))
      (return-from getSuccessor node))) (reverse allSuccessors)))
