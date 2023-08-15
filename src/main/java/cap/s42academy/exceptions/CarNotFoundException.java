package cap.s42academy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarNotFoundException extends RuntimeException {

    private CarNotFoundException(String message) {
        super(message);
    }

    public CarNotFoundException(Long id) {
        this(String.format("Car with ID=%s was not found", id));
    }

}