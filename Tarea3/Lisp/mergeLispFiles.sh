# Creamos un .lisp vacío
rm alfaBetaSearch.lisp
touch alfaBetaSearch.lisp

# Pegamos cada función de lisp a alfaBetaSearch
cat configInicial.lisp >> alfaBetaSearch.lisp
cat maxValue.lisp >> alfaBetaSearch.lisp
cat minValue.lisp >> alfaBetaSearch.lisp
cat successors.lisp >> alfaBetaSearch.lisp
cat terminalTest.lisp >> alfaBetaSearch.lisp

# Ponemos las heurísticas
./mergeHeuristicas.sh
cat ./Heuristica/utility.lisp >> alfaBetaSearch.lisp

echo "(alfaBetaSearch (list 0 infoFirst nil 0))" >> alfaBetaSearch.lisp
