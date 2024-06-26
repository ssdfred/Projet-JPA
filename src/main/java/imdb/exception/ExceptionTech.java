package imdb.exception;

import java.io.IOException;

/**
 * Exception technique incompatible avec la poursuite de l'application
 * 
 * @author RichardBONNAMY
 *
 */
public class ExceptionTech extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = -5592068178491785832L;

	/**
	 * Constructeur
	 * 
	 * @param message message d'erreur associé à l'exception
	 */
    public ExceptionTech(String message) {
        super(message);
    }

    public ExceptionTech(String message, Throwable cause) {
        super(message, cause);
    }
}