; 9) Función que regresa la cantidad de peones solitarios. La pieza solitaria se define como la que no está adyacente a ninguna otra pieza.
(defun lonerPawns (info)
(setq peones (append (first (first info)) (first (second info))))
(solitarios peones 0))
