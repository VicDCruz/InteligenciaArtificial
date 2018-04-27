; 10) Función que regresa la cantidad de reyes solitarios.
(defun lonerKings (info)
(setq reyes (append (second (first info)) (second (second info))))
(solitarios reyes 0))
