package org.example.exceptions;

public abstract class ItvExceptions extends RuntimeException{
    public ItvExceptions(String message) {
        super(message);
    }
    public static final class ItvMatriculaException extends ItvExceptions {
        public ItvMatriculaException() {
            super("Error -> Matricula no valida");
        }
    }

    public static final class ItvKilometroException extends ItvExceptions {
        public ItvKilometroException() {
            super("Error -> Kilometro no valida");
        }
    }

    public static final class ItvAnioException extends ItvExceptions {
        public ItvAnioException() {
            super("Error -> Anio de matriculacion no valido");
        }
    }

    public static final class ItvNumPuertasException extends ItvExceptions {
        public ItvNumPuertasException() {
            super("Error -> Numero de plazas no valido");
        }
    }

    public static final class ItvCilindradaException extends ItvExceptions {
        public ItvCilindradaException() {
            super("Error -> Cilindrada no valida");
        }
    }
}