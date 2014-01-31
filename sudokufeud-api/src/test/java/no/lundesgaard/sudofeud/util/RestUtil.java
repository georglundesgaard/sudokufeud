package no.lundesgaard.sudofeud.util;

import no.lundesgaard.sudokufeud.api.client.SudokuFeudClient;
import no.lundesgaard.sudokufeud.api.model.JsonMove;

public class RestUtil {
	private static final String ROOT = "http://localhost:8080/api";
	private static final boolean LOGGING = false;

	public static void main(String[] args) {
		SudokuFeudClient client1 = new SudokuFeudClient(ROOT, "georg", "georg", LOGGING);
		// SudokuFeudClient client2 = new SudokuFeudClient(ROOT, "ida", "ida", LOGGING);
		// SudokuFeudClient client3 = new SudokuFeudClient(ROOT, "oddbjørn", "oddbjørn", LOGGING);

		try {
			// print(client3.getProfile());
			// client1.createGame("oddbjørn");

			// print(client1.updateProfile("Georg Lundesgaard"));
			// print(client1.getProfile());
			// client1.createGame("ida");
			// print(client1.getGames());

			// print(client2.updateProfile("Ida Sirnes"));
			// print(client2.getProfile());
			// client2.createGame("georg");
			// print(client2.getGames());

			// JsonGame[] games = client2.getGames();
			// String gameId = games[0].getId();
			// print(client2.acceptInvitation(gameId));

			// JsonGame[] games = client2.getGames();
			// String gameId = games[1].getId();
			// client2.declineInvitation(gameId);

			// games = client1.getGames();
			// gameId = games[0].getId();
			// sampleGame(client1, client2, gameId);

			// for (int i = 0; i < 100; i++) {
			// client1.createGame("ida");
			// client2.createGame("georg");
			// }

			/*
			JsonGame[] games = client1.getGames();
			long totalTime = 0;
			int count = 1000;
			for (int i = 0; i < count; i++) {
			    long start = System.currentTimeMillis();
			    games = client1.getGames();
			    totalTime += System.currentTimeMillis() - start;
			    
			    if (i % 25 == 0) {
			        System.out.print('.');
			        System.out.flush();
			    }
			}
			long avgTime = totalTime / count;
			
			System.out.printf("\ngetGames() returns %d games %d times on average %d ms\n", games.length, count, avgTime);
			*/

			/*
			List<String> names = asList(
			        "georg", 
			        "ida", 
			        "oddbjørn",
			        "erik", 
			        "jon", 
			        "severin", 
			        "helle", 
			        "daniela", 
			        "beate", 
			        "jacob", 
			        "nethe",
			        "hanna", 
			        "petter", 
			        "viktoria", 
			        "bjørg", 
			        "elen",
			        "marius", 
			        "idun", 
			        "hedda", 
			        "henrik",
			        "guri",
			        "richard",
			        "bernt",
			        "anna", 
			        "sigrid",
			        "ester",
			        "rebekka",
			        "kim",
			        "frode",
			        "rune",
			        "stefan",
			        "simone",
			        "kristoffer",
			        "tobias",
			        "kathrine",
			        "christian",
			        "else",
			        "karoline",
			        "ragnhild",
			        "ole",
			        "kjetil",
			        "sverre"
			);
			
			Map<String, SudokuFeudClient> clientMap = names
			        .parallelStream()
			        .collect(toMap(
			                (name) -> name,
			                (name) -> {
			                    SudokuFeudClient client = new SudokuFeudClient(ROOT, name, name, false);
			                    //print(client.getProfile());
			                    return client;
			                }
			        ));

			long start = System.currentTimeMillis();
			int totalGameCount = names
			        .parallelStream()
			        .map((name) -> {
			            SudokuFeudClient client = clientMap.get(name);
			            return client.getGames().length;
			        })
			        .reduce(0, Integer::sum);
			long time = System.currentTimeMillis() - start;
			System.out.printf("%d games counted in %d ms", totalGameCount, time);

			Random random = new Random(13 * System.currentTimeMillis());
			names.parallelStream().forEach((name) -> {
			    SudokuFeudClient client = clientMap.get(name);
			    List<String> filteredNames = names.stream().filter((s) -> !s.equals(name)).collect(toList());

			    int gameCount = 8 + random.nextInt(7);
			    for (int i = 0; i < gameCount; i++) {
			        int opponentIndex = random.nextInt(filteredNames.size());
			        client.createGame(filteredNames.get(opponentIndex));   
			    }
			    
			    System.out.printf("%d games created for %s\n", gameCount, name);
			});
			*/
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static int getGameCount(String name) {
		SudokuFeudClient client = new SudokuFeudClient(ROOT, name, name, false);
		return client.getGames().length;
	};

	private static void print(Object json) {
		System.out.println(json);
	}

	private static void print(Object[] json) {
		System.out.print("[");
		for (int i = 0; i < json.length; i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(json[i]);
		}
		System.out.println("]");
	}

	private static void sampleGame(SudokuFeudClient client1, SudokuFeudClient client2, String gameId) {
		// round 1
		client1.executeRound(                gameId,
 jsonMove(6, 5, 2), jsonMove(0, 1, 3), jsonMove(4, 1, 8));

		client2.executeRound(gameId, jsonMove(3, 3, 5), jsonMove(5, 5, 6), jsonMove(3, 2, 7), jsonMove(3, 0, 2), jsonMove(3, 1, 1));

		// round 2
		client1.executeRound(gameId, jsonMove(6, 0, 1),         jsonMove(8, 7, 3),
                jsonMove(6, 1, 4),
                jsonMove(7, 7, 4),
                jsonMove(8, 3, 4),
                jsonMove(5, 7, 5));

		client2.executeRound( gameId,
                jsonMove(8, 2, 6),
 jsonMove(2, 2, 9), jsonMove(0, 3, 9), jsonMove(6, 3, 8), jsonMove(1, 5, 8), jsonMove(4, 5, 1),
				jsonMove(4, 3, 7));

		// round 3
		client1.executeRound(
gameId, jsonMove(1, 2, 2),
                jsonMove(3, 4, 4),
                jsonMove(7, 3, 6),
                jsonMove(7, 4, 1),
 jsonMove(5, 8, 7), jsonMove(0, 0, 8));

		client2.executeRound(gameId, jsonMove(1, 4, 7), jsonMove(4, 6, 4), jsonMove(1, 7, 6), jsonMove(1, 3, 1), jsonMove(2, 3, 3),    jsonMove(0, 7, 2),
				jsonMove(3, 6, 3));

		// round 4
		client1.executeRound(gameId,       jsonMove(1, 0, 4),
 jsonMove(1, 1, 5), jsonMove(2, 7, 1), jsonMove(0, 6, 7), jsonMove(2, 8, 8));

		client2.executeRound(
                gameId,
 jsonMove(6, 7, 7), jsonMove(3, 7, 8), jsonMove(6, 8, 9));

		// round 5
        client1.executeRound(
gameId, jsonMove(6, 6, 6),
                jsonMove(3, 8, 6));
    }

	private static JsonMove jsonMove(int x, int y, int piece) {
		return new JsonMove(x, y, piece);
	}
}
