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

| Metode | Sti                   | Beskrivelse                         | Eksempel
| ------ | --------------------- | ----------------------------------- |--------------------
| GET    | profile               | Hent brukerprofil                   | 
| PUT    | profile               | Oppdater/opprett brukerprofil       | {"userId": "georg","name": "Georg Lundesgaard","password": "georg"}
| GET    | games                 | Hent spill                          |
| POST   | games                 | Opprett et spill                    | {"opponent": "georg","difficulty": "EASY"}
| GET    | games/{gameId}        | Hent ett spill                      |
| PUT    | games/{gameId}        | Aksepter/avslå invitasjon til spill | {"response": "ACCEPT"}
| POST   | games/{gameId}/rounds | Spill runde                         | {"moves": [{"x": 1,"y": 1,"piece": 9}, ...]}
| GET    | games/{gameId}/rounds | Hent spilte runder                  |

# Sikkerhet

API-et krever Basic Authentication. Brukeren opprettes med passordet som sendes inn 
hvis den ikke finnes fra før.

# Runtime-oppsett

Applikasjonen er satt opp med [Spring Boot](http://projects.spring.io/spring-boot/). Spring Boot inkluderer et Emedded Tomcat-kjøremiljø 
som standard. Den er satt opp med [spring-boot-actuator](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-actuator) 
som setter opp en rekke standard-tjenster i tillegg til API ovenfor. En rekke av standard-tjenestene
er kun tilgjengelig for admin-brukeren. Hvis admin-brukeren ikke finnes fra før, opprettes den ved
oppstart og det genererte passordet skrives til loggen som en INFO-melding.

Kjøres med følgende kommando fra prosjekt-roten:

	./run.sh

Alternativt kan main-klassen [SudokuFeudApiConfiguration](sudokufeud-api/src/main/java/no/lundesgaard/sudokufeud/api/SudokuFeudApiConfiguration.java) kjøres. 

Kjøre-parametre:

* -Xms64m 
* -Xmx512m

Kjørende instans (localhost, port 8080) stoppes med følgende kommando:

	./shutdown.sh admin-passord

Programmet kan installeres i definert hjemmekatalog (default: /opt/sudokufeud) med følgende kommando:

	./install.sh [hjemmekatalog]

Installerings-script kopierer .jar-fil, scripts og config til en ny katalog, navngitt med tag-navn og commit-id, i hjemmekatalogen
og lager en symbolsk link til fra latest i hjemmekatalogen.

# Systemkrav

* Java 8
* Maven 3
