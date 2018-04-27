; (setq args nil)
; (let ((in (open "./infoLisp.txt" :if-does-not-exist nil)))
;   (when in
;     (loop for line = (read in nil)
;          while line do (push line args))
;     (close in)))
; (setq info (first args))

(with-open-file (my-stream
               "../solLisp.txt"
               :direction :output
               :if-exists :supersede)
  (print 'HOLA my-stream))
