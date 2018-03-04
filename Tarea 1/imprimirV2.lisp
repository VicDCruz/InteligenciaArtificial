; (defun sum(lst)
; (mapcar #'(lambda (elem)
;   (cond ((null elem) )
;     ((atom elem) (+ elem elem))
;     (t (sum elem)))) lst))

(defun imprime(lst)
  (setq thisLvl nil)
  (setq nxtLvl nil)
  (push nil nxtLvl) ; De lo contrario, sería una lista impropia
  (getLvl lst))

(defun getLvl(lst)
(mapcar #'(lambda (elem)
  (cond ((null elem) )
    ((atom elem) (push elem thisLvl)) ; Guarda a todos los hermanos
    (t (push elem nxtLvl)))) lst) ; Almacena a hijos y primos en una cola aparte
(setq rev (reverse nxtLvl) revElem (pop rev) nxtLvl (reverse rev))
(cond ((equal revElem nil) ; Si el elemento es NIL => No
       (print (reverse thisLvl)) ; Cuando ya no hay mas de sus hijos por imprimir
       (setq thisLvl nil)
       ; Obtenemos el último elemento de la (disque)cola
       (setq rev (reverse nxtLvl) revElem (pop rev) nxtLvl (reverse rev))
       (push nil nxtLvl)))
(if (not (equal revElem nil))
  (getLvl revElem)
  '--))
