package gr.aueb.cf.usermovies.dto;


public class ErrorDTO {
    private String error;

    public ErrorDTO(String message) {
        this.error = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
