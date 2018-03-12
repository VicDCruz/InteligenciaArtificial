# Tarea 2
Requisitos:

* Obtener más de 80 ciudades
* Tener un DDL con las ciudades para Inicio y Fin
* Implementar el método *Búsqueda A estrella*

## Dependencias:
* SailsJS
* LISP


## Estructura de las listas:
1. La relación entre A, B, C y D será: enlistar el nombre de todas las ciudades y su costo: ((A 1) (B 2) (C 3) (D 4))

2. Habrá también una matriz de distancias

|      |      |      |      |      |
| :------------- | :------------- | :------------- | :------------- |
|   | A | B | C | D |
| A | 0 | 8 | 1 | 5 |
| B | 8 | 0 | 7 | 10 |
| C | 1 | 7 | 0 | 6 |
| D | 5 | 10 | 6 | 0 |

Ya en LISP será (cada fila será una sub-lista):
((0 8 1 5)
(8 0 7 10)
(1 7 0 6)
(5 10 6 0))

Así, si la distancia es 0, quiere decir que no existe una relación entre ese nodo y el que se dice, por ejemplo, no hay relación entre el nodo A y A

## Proceso de *Recursive Beast First Search*
1. Pongo el nodo inicial en SUCESORES
2. Mi límite es ∞
2. Expando a mis hijos
	3. ¿Quién es el que tiene el menor costo de la suma de la distancia a esa ciudad y su distancia en línea recta?
	4. El límite de ese nodo es el 2º menor costo de sus hermanos
	5. En caso de que no se cumpla de que el límite de ese nodo sea mayor que la suma de mis hijos, entonces me voy al 2º mejor y expando ese
4. Expandamos el 1º menor
5. Regreso al (2)
