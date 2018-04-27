# Creamos el archivo de heurísticas (utility.lisp)
rm ./Heuristica/utility.lisp
touch ./Heuristica/utility.lisp

# Pegamos todas las funciones de la heurística
cat ./Heuristica/utilidad.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/attackingPawns.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/auxiliar.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/centralKings.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/centralPawns.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/defenderPieces.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/diagonalPawns.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/holes.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/kingsDoubleDiagonal.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/kingsMainDiagonal.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/lonerKings.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/lonerPawns.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/pawnsDoubleDiagonal.lisp >> ./Heuristica/utility.lisp
cat ./Heuristica/eatsRival.lisp >> ./Heuristica/utility.lisp
