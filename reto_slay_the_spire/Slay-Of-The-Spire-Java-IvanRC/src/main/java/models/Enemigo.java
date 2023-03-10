package models;

interface atacante{
    public int atacar();
}

interface curador{
    public int curar();
}

interface potenciador{
    public int potenciar();
}

public class Enemigo implements atacante {
    private int veneno = 0;
    private int vida;
    private int daño;

    public Enemigo(int daño, int vida) {
        this.vida = vida;
        this.daño = daño;
    }

    public Enemigo(){
        this.vida = ((int)(Math.random()*8+8));
        this.daño = ((int)(Math.random()*3+3));
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public int getVeneno() {
        return veneno;
    }

    public void setVeneno(int veneno) {
        this.veneno = veneno;
    }

    @Override
    public String toString() {
        return "Enemigo{" +
                "veneno=" + veneno +
                ", vida=" + vida +
                ", daño=" + daño +
                '}';
    }


    @Override
    public int atacar() {
        return daño;
    }
    static final public class Curador extends Enemigo implements atacante, curador{
        private final int curacion = ((int)(Math.random()*3+3));

        public Curador() {
            super(((int)(Math.random()*3+3)),((int)(Math.random()*8+8)));
        }

        @Override
        public int curar() {
            return curacion;
        }

        @Override
        public String toString() {
            return "Curador{" +
                    "curacion=" + curacion +
                    ", veneno=" + super.veneno +
                    ", vida=" + super.vida +
                    ", daño=" + super.daño +
                    '}';
        }
    }
    static final public class Potenciador extends Enemigo implements atacante, potenciador{
        private final int potenciaPlus = ((int)(Math.random()*2+2));

        public Potenciador() {
            super(((int)(Math.random()*2+2)),((int)(Math.random()*3+6)));
        }

        @Override
        public int potenciar() {
            return potenciaPlus;
        }

        @Override
        public String toString() {
            return "Potenciador{" +
                    "potenciaPlus=" + potenciaPlus +
                    ", veneno=" + super.veneno +
                    ", vida=" + super.vida +
                    ", daño=" + super.daño +
                    '}';
        }
    }

    static final class Jefe extends Enemigo implements curador, potenciador{
        private final int curacion = ((int)(Math.random()*3+3));
        private final int potenciaPlus = ((int)(Math.random()*2+2));

        public Jefe() {
            super(((int)(Math.random()*10+3)), ((int)(Math.random()*30+6)));
        }

        @Override
        public int curar() {
            return curacion;
        }

        @Override
        public int potenciar() {
            return potenciaPlus;
        }

        @Override
        public String toString() {
            return "Jefe{" +
                    "curacion=" + curacion +
                    ", potenciaPlus=" + potenciaPlus +
                    ", veneno=" + super.veneno +
                    ", vida=" + super.vida +
                    ", daño=" + super.daño +
                    '}';
        }
    }
}
