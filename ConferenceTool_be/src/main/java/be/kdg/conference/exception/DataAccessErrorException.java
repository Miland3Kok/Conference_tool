package be.kdg.conference.exception;

import org.springframework.dao.DataAccessException;

public class DataAccessErrorException extends DataAccessException {
    public DataAccessErrorException(String msg, Throwable cause) {
        super("Database error: "+ msg, cause);
    }
}
