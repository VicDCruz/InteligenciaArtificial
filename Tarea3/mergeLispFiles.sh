# Creamos un .lisp vacío
rm ./Lisp/alfaBetaSearch.lisp
touch ./Lisp/alfaBetaSearch.lisp

# Pegamos cada función de lisp a alfaBetaSearch
cat ./Lisp/configInicial.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/eval.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/maxValue.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/minValue.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/successors.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/terminalTest.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/utility.lisp >> ./Lisp/alfaBetaSearch.lisp

# Pegamos las funciones heurísticas
cat ./Lisp/Heuristica/attackingPawns.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/Heuristica/centralKings.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/Heuristica/centralPawns.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/Heuristica/defenderPieces.lisp >> ./Lisp/alfaBetaSearch.lisp
cat ./Lisp/Heuristica/diagonalPawns.lisp >> ./Lisp/alfaBetaSearch.lisp
