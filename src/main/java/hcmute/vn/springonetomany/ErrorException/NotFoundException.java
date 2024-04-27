package hcmute.vn.springonetomany.ErrorException;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
