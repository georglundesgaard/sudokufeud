package no.lundesgaard.sudokufeud.api.resource;

import no.lundesgaard.sudokufeud.api.model.JsonMove;
import no.lundesgaard.sudokufeud.api.model.JsonRound;
import no.lundesgaard.sudokufeud.model.Move;
import no.lundesgaard.sudokufeud.service.GameService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class RoundsResource {
    public static final String PATH = "rounds";

    private GameService gameService;
    private String profileId;
    private String gameId;

    public RoundsResource(GameService gameService, String profileId, String gameId) {
        this.gameService = gameService;
        this.profileId = profileId;
        this.gameId = gameId;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response executeRound(@Context UriInfo uriInfo, JsonRound jsonRound) {
        Move[] moves = toMoves(jsonRound);
        int roundId = gameService.executeRound(profileId, gameId, moves);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path("{roundId}")
                .build(roundId);

        return Response
                .created(location)
                .build();
    }

    private Move[] toMoves(JsonRound jsonRound) {
        JsonMove[] jsonMoves = jsonRound.getMoves();
        if (jsonMoves == null || jsonMoves.length == 0) {
            return new Move[0];
        }

        Move[] moves = new Move[jsonMoves.length];
        for (int i = 0; i < jsonMoves.length; i++) {
            moves[i] = toMove(jsonMoves[i]);
        }

        return moves;
    }

    private Move toMove(JsonMove jsonMove) {
        return new Move(jsonMove.getX(), jsonMove.getY(), jsonMove.getPiece());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRounds() {
        return Response
                .ok()
                .build();
    }
}
