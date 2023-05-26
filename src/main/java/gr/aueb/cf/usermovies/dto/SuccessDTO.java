package gr.aueb.cf.usermovies.dto;

public class SuccessDTO {
    private String message;

    public SuccessDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
