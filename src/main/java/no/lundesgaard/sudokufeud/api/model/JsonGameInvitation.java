package no.lundesgaard.sudokufeud.api.model;

public class JsonGameInvitation {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public enum Response {
        ACCEPT,
        DECLINE
    }
}
