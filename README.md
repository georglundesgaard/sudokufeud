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

    run.sh
    
Alternativt kan main-klassen [SudokuFeudApiConfiguration](sudokufeud-api/src/main/java/no/lundesgaard/sudokufeud/api/SudokuFeudApiConfiguration.java) kjøres. 
Kjøre-parametre:

* -Xms64m 
* -Xmx512m 
* -Dhazelcast.config=config/hazelcast-dev.xml

Applikasjon er konfigurert for å enkelt kunne kjøres i et cluster på Amazon AWS. Lag en 
Hazelcast-konfigurasjon for AWS (config/hazelcast-aws.xml) basert på malen 
config/hazelcast-aws-template.xml. For AWS er det viktig å sette opp acces- og secret-key
AWS-kontoen og hvilken security group instansene tilhører slik Hazelcast kan finne alle
instansene som tilhører clusteret.

Kjøres med følgende kommando fra prosjekt-roten:

    run-aws.sh
    
* -Xms64m 
* -Xmx512m 
* -Dhazelcast.config=config/hazelcast-aws.xml

# Systemkrav

* Java 8
* Maven 3
