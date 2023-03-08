package org.example.exceptions;

public abstract class HospitalExceptions extends RuntimeException {
    public HospitalExceptions(String message) {
        super(message);
    }

    public static final class HospitalFullException extends HospitalExceptions {
        public HospitalFullException() {
            super("Error -> Hospital lleno.");
        }
    }

    public static final class DniNotValidException extends HospitalExceptions {
        public DniNotValidException() {
            super("Error -> DNI no válido.");
        }
    }
}
