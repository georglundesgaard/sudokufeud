# SudokuFeud

Sudoku for 2 spillere konseptuelt likt Wordfeud.

## Konsept

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

Se [eksempelspill](sample.md) for gjennomgang av et typisk spill.

## API

Rot: http://sudokufeud.lundesgaard.no/api

| Metode | Sti                   | Beskrivelse                         |
| ------ | --------------------- | ----------------------------------- |
| GET    | profile               | Hent brukerprofil                   |
| PUT    | profile               | Oppdater/opprett brukerprofil       |
| GET    | games                 | Hent spill                          |
| POST   | games                 | Opprett et spill                    |
| GET    | games/{gameId}        | Hent ett spill                      |
| PUT    | games/{gameId}        | Aksepter/avslå invitasjon til spill |
| POST   | games/{gameId}/rounds | Spill runde                         |
| GET    | games/{gameId}/rounds | Hent spilte runder                  |

# Runtime-oppsett

Applikasjonen er satt opp med [Spring Boot](http://projects.spring.io/spring-boot/). Spring Boot inkluderer et Emedded Tomcat-kjøremiljø 
som standard. Den er satt opp med [spring-boot-actuator](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-actuator) 
som setter opp en rekke standard-tjenster i tillegg til API ovenfor.

Kjøres med (fra prosjekt-roten)

    java -jar sudokufeud-api/target/sudokufeud-api-1.0-SNAPSHOT.jar
    
Alternativt kan main-klassen [SudokuFeudApiConfiguration](sudokufeud-api/src/main/java/no/lundesgaard/sudokufeud/api/SudokuFeudApiConfiguration.java) kjøres.
