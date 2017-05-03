package grenier.tiffany.app.exchangerate.web;

//TODO: create error page
public class ApiError {
    private final String errorMessage;
    private final String requestURI;

    public ApiError(final String message, final String requestURI) {
        this.errorMessage = message;
        this.requestURI = requestURI;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getRequestURI() {
        return requestURI;
    }
}
