# SudokuFeud

* 1 Sudoku-brett
* 2 spillere
* ved start har begge spillere 7 "brikker" med verdien 1-9
* spiller 1 plasserer 1 eller flere brikker på brettet og prøve spille dem
* hvis brikkene er riktig plassert får han 1 poeng per brikke plassert
* hvis ikke får han beskjed om at 1 eller flere er feil, men ikke hvilke som er feil
* hvis ingen brikker kan plasseres, kan han stå over en runde eller bytte 1 eller flere
  brikker og stå over en runde
* når et felt, en rad eller en kolonne fullføres gis en bonus på 5 poeng
* spilleren som fullfører brettet får en bonus på 25 poeng
* spilleren med flest poeng vinner
* hvis en spiller bruker alle brikkene sine i én runde, så får han en bonus på 10 poeng

## Eksempel

Sudoku-brett:

	0 0 7  0 6 3  0 9 5
	0 0 6  0 0 9  0 7 2
	1 0 0  0 5 4  3 8 0

	0 0 0  0 0 2  0 0 0
	6 0 2  0 3 8  5 0 9
	5 0 4  9 0 0  0 3 7

	0 9 5  0 0 1  0 2 8
	0 0 0  0 9 0  0 0 0
	4 3 0  0 2 0  0 5 1

Tilgjengelige brikker:

	1 2   4       8
	1   3 4 5     8
	  2       6 7   9
	1   3 4 5 6 7 8 9
	1     4     7
	1 2       6   8
	    3 4   6 7
	1 2 3 4 5 6 7 8
	          6 7 8 9

√ 1. Fordeler brikker til spillerne

	Spiller 1: 1 2 4 8 1 3 4
	Spiller 2: 5 8 2 6 7 9 1

√ 2. Tilgjengelige brikker etter at brikker er fordelt

	    3 4 5 6 7 8 9
	1     4     7
	1 2       6   8

	    3 4   6 7
	1 2 3 4 5 6 7 8
	          6 7 8 9

√ 3. Spiller 1 plasserer brikker på brettet

 	Spiller 1: 	2 3 8
 	Poeng:		3

	0 0 7  0 6 3  0 9 5
	3 0 6  0 8 9  0 7 2		3@0 8@4
	1 0 0  0 5 4  3 8 0

	0 0 0  0 0 2  0 0 0
	6 0 2  0 3 8  5 0 9
	5 0 4  9 0 0  2 3 7		2@6

	0 9 5  0 0 1  0 2 8
	0 0 0  0 9 0  0 0 0
	4 3 0  0 2 0  0 5 1

√ 4. Poeng og brikker igjen hos spillere

	Spiller 1: 3	1 4 1 4
	Spiller 2: 0	5 8 2 6 7 9 1

√ 5. Spiller 1 får nye brikker

	Spiller 1: 3	1 4 1 4 [3 4 5]
	Spiller 2: 0	5 8 2 6 7 9 1

√ 6. Tilgjengelige brikker etter at nye brikker er fordelt

	          6 7 8 9
	1     4     7
	1 2       6   8

	    3 4   6 7
	1 2 3 4 5 6 7 8
	          6 7 8 9

√ 7. Spiller 2 plasserer brikker på brettet

	Spiller 2: 	5 6 7 2 1
	Poeng:		5
	Bonus:		5; firkant 1

	0 0 7  2 6 3  0 9 5     2@3
	3 0 6  1 8 9  0 7 2     1@3
	1 0 0  7 5 4  3 8 0     7@3

	0 0 0  5 0 2  0 0 0		5@3
	6 0 2  0 3 8  5 0 9
	5 0 4  9 0 6  2 3 7		6@5

	0 9 5  0 0 1  0 2 8
	0 0 0  0 9 0  0 0 0
	4 3 0  0 2 0  0 5 1

√ 8. Poeng og brikker igjen hos spillere

	Spiller 1: 3	1 4 1 4 3 4 5
	Spiller 2: 10	8 9

√ 9. Spiller 2 får nye brikker

	Spiller 1: 3	1 4 1 4 3 4 5
	Spiller 2: 10	8 9 [6 7 8 9 1]

√10. Tilgjengelige brikker etter at nye brikker er fordelt

	      4     7
	1 2       6   8

	    3 4   6 7
	1 2 3 4 5 6 7 8
	          6 7 8 9

√11. Spiller 1 plasserer brikker på brettet

	Spiller 1:	1 3 4 4 4 5
	Poeng:		6

	0 0 7  2 6 3  1 9 5		1@6
	3 0 6  1 8 9  4 7 2		4@6
	1 0 0  7 5 4  3 8 0

	0 0 0  5 0 2  0 0 4		4@8
	6 0 2  0 3 8  5 0 9
	5 0 4  9 0 6  2 3 7

	0 9 5  0 0 1  0 2 8
	0 0 0  0 9 5  0 4 3		3@8 4@7 5@5
	4 3 0  0 2 0  0 5 1

√12. Poeng og brikker igjen hos spillere

	Spiller 1: 9	1
	Spiller 2: 10	8 9 6 7 8 9 1

√13. Spiller 1 får nye brikker

	Spiller 1: 9	1 [4 7 1 2 6 8]
	Spiller 2: 10	8 9 6 7 8 9 1

√14. Tilgjengelige brikker etter at nye brikker er fordelt

	    3 4   6 7
	1 2 3 4 5 6 7 8
	          6 7 8 9

√15. Spiller 2 plasserer brikker på brettet

	Spiller 2:	6 9 9 8 8 1 7
	Poeng:		7+5+5+5+10
	Bonus:		5; firkant 2
				5; kolonne 8
				5; rad 5
				10; bingo

	0 0 7  2 6 3  1 9 5
	3 0 6  1 8 9  4 7 2
	1 0 9  7 5 4  3 8 6		6@8 9@2

	9 0 0  5 7 2  8 0 4		9@0 8@6 7@4
	6 0 2  0 3 8  5 0 9
	5 8 4  9 1 6  2 3 7		8@1 1@4

	0 9 5  0 0 1  0 2 8
	0 0 0  0 9 5  0 4 3
	4 3 0  0 2 0  0 5 1

√16. Poeng og brikker igjen hos spillere

	Spiller 1: 9	1 4 7 1 2 6 8
	Spiller 2: 42

√17. Spiller 2 får nye brikker

	Spiller 1: 9	1 4 7 1 2 6 8
	Spiller 2: 42	[3 4 6 7 1 2 3]

√18. Tilgjengelige brikker etter at nye brikker er fordelt

	      4 5 6 7 8
	          6 7 8 9

√19. Spiller 1 plasserer brikker på brettet

	Spiller 1:	2 4 6 1 7 8
	Poeng:		6
	Bonus:		5; rad 2
				5; firkant 4
				5; firkant 5
				5; kolonne 7
				5; kolonne 5

	8 0 7  2 6 3  1 9 5		8@0
	3 0 6  1 8 9  4 7 2
	1 2 9  7 5 4  3 8 6		2@1

	9 0 0  5 7 2  8 6 4 	6@7
	6 0 2  4 3 8  5 1 9 	4@3 1@7
	5 8 4  9 1 6  2 3 7

	0 9 5  0 0 1  0 2 8
	0 0 0  0 9 5  0 4 3
	4 3 0  0 2 7  0 5 1 	7@5

√20. Poeng og brikker igjen hos spillere

	Spiller 1: 40	1
	Spiller 2: 42	3 4 6 7 1 2 3

√21. Spiller 1 får nye brikker

	Spiller 1: 40	1 [4 5 6 7 8 6]
	Spiller 2: 42	3 4 6 7 1 2 3

√22. Tilgjengelige brikker etter at nye brikker er fordelt

	            7 8 9

√23. Spiller 2 plasserer brikker på brettet

	Spiller 2:	7 4 6 1 3 2 3
	Poeng:		7
	Bonus:		5; rad 4
				5; kolonne 4
				5; firkant 3
				5; rad 3
				10; bingo

	8 0 7  2 6 3  1 9 5
	3 0 6  1 8 9  4 7 2
	1 2 9  7 5 4  3 8 6

	9 1 3  5 7 2  8 6 4 	1@1 3@2
	6 7 2  4 3 8  5 1 9 	7@1
	5 8 4  9 1 6  2 3 7

	0 9 5  3 4 1  0 2 8		4@4 3@3
	2 6 0  0 9 5  0 4 3		6@1 2@0
	4 3 0  0 2 7  0 5 1

√24. Poeng og brikker igjen hos spillere

	Spiller 1: 40	1 4 5 6 7 8 6
	Spiller 2: 79

√25. Spiller 2 får nye brikker

	Spiller 1: 40	1 4 5 6 7 8 6
	Spiller 2: 79	[7 8 9]

√26. Tilgjengelige brikker etter at nye brikker er fordelt

	Ingen

√27. Spiller 1 plasserer brikker på brettet

	Spiller 1:	4 5 1 7 8
	Poeng:		5
	Bonus:		5; rad 0
				5; rad 1
				5; kolonne 1
				5; firkant 0
				5; kolonne 0
				5; kolonne 2
				5; firkant 6

	8 4 7  2 6 3  1 9 5		4@1
	3 5 6  1 8 9  4 7 2 	5@1
	1 2 9  7 5 4  3 8 6

	9 1 3  5 7 2  8 6 4
	6 7 2  4 3 8  5 1 9
	5 8 4  9 1 6  2 3 7

	7 9 5  3 4 1  0 2 8		7@0
	2 6 1  0 9 5  0 4 3		1@2
	4 3 8  0 2 7  0 5 1 	8@2

√28. Poeng og brikker igjen hos spillere

	Spiller 1: 80	6 6
	Spiller 2: 79	7 8 9

√29. Ingen nye brikker tilgjengelig for spiller 1

	Spiller 1: 80	6 6
	Spiller 2: 79	7 8 9

√30. Tilgjengelige brikker etter at ingen nye brikker er fordelt

	Ingen

√31. Spiller 2 plasserer brikker på brettet

	Spiller 2:	7 8 9
	Poeng:		3
	Bonus:		5; rad 7
				10; bingo

	8 4 7  2 6 3  1 9 5
	3 5 6  1 8 9  4 7 2
	1 2 9  7 5 4  3 8 6

	9 1 3  5 7 2  8 6 4
	6 7 2  4 3 8  5 1 9
	5 8 4  9 1 6  2 3 7

	7 9 5  3 4 1  0 2 8
	2 6 1  8 9 5  7 4 3 	7@6 8@3
	4 3 8  0 2 7  9 5 1 	9@6

√32. Poeng og brikker igjen hos spillere

	Spiller 1: 80	6 6
	Spiller 2: 97

√33. Ingen nye brikker tilgjengelig for spiller 2

	Spiller 1: 80	6 6
	Spiller 2: 97

√34. Tilgjengelige brikker etter at ingen nye brikker er fordelt

	Ingen

√35. Spiller 1 plasserer brikker på brettet

	Spiller 1: 	6 6
	Poeng:		2
	Bonus:		5; rad 6
				5; kolonne 6
				5; firkant 8
				5; rad 8
				5; kolonne 3
				5; firkant 7
				10; bingo

	8 4 7  2 6 3  1 9 5
	3 5 6  1 8 9  4 7 2
	1 2 9  7 5 4  3 8 6

	9 1 3  5 7 2  8 6 4
	6 7 2  4 3 8  5 1 9
	5 8 4  9 1 6  2 3 7

	7 9 5  3 4 1  6 2 8		6@6
	2 6 1  8 9 5  7 4 3
	4 3 8  6 2 7  9 5 1 	6@3

√36. Poeng og brikker igjen hos spillere

	Spiller 1: 122
	Spiller 2: 97

√37. Vinner funnet

	Spiller 1
