# Creamos un .lisp vacío
rm ./Lisp/alfaBetaSearch.lisp
touch ./Lisp/alfaBetaSearch.lisp

# Pegamos cada función de lisp a alfaBetaSearch
cat ./Lisp/eval.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/maxValue.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/successors.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/eval.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/eval.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/eval.lisp >> ./Lisp/alfaBetaSearch.lisp