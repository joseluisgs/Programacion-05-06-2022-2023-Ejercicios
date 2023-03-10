package models;

public sealed class Carta{
    private final int costeDeMana;

    public Carta(int costeDeMana) {
        this.costeDeMana = costeDeMana;
    }

    public int getCosteDeMana() {
        return costeDeMana;
    }

    public static final class CartaDaño extends Carta {
        private int daño = 4;
        public CartaDaño() {
            super(1);
        }

        public int getDaño() {
            return daño;
        }

        public void setDaño(int daño) {
            this.daño = daño;
        }

        @Override
        public String toString() {
            return "CartaDaño{" +
                    "daño=" + daño +
                    ", costeDeMana=" + super.costeDeMana +
                    '}';
        }
    }
    public static final class CartaDañoPlus extends Carta {
        private int daño = 8;
        public CartaDañoPlus() {
            super(2);
        }

        public int getDañoPlus() {
            return daño;
        }

        public void setDañoPlus(int daño) {
            this.daño = daño;
        }

        @Override
        public String toString() {
            return "CartaDañoPlus{" +
                    "daño=" + daño +
                    ", costeDeMana=" + super.costeDeMana +
                    '}';
        }
    }
    public static final class CartaCuracion extends Carta {
        private int curacion = 4;
        public CartaCuracion() {
            super(1);
        }

        public int getCuracion() {
            return curacion;
        }

        public void setCuracion(int curacion) {
            this.curacion = curacion;
        }

        @Override
        public String toString() {
            return "CartaCuracion{" +
                    "curacion=" + curacion +
                    ", costeDeMana=" + super.costeDeMana +
                    '}';
        }
    }
    public static final class CartaCuracionPlus extends Carta {
        private int curacion = 7;
        public CartaCuracionPlus() {
            super(2);
        }

        public int getCuracionPlus() {
            return curacion;
        }

        public void setCuracionPlus(int curacion) {
            this.curacion = curacion;
        }

        @Override
        public String toString() {
            return "CartaCuracionPlus{" +
                    "curacion=" + curacion +
                    ", costeDeMana=" + super.costeDeMana +
                    '}';
        }
    }
    public static final class CartaVeneno extends Carta {
        private int dañoVeneno = 1;
        public CartaVeneno() {
            super(1);
        }

        public int getDañoVeneno() {
            return dañoVeneno;
        }

        public void setDañoVeneno(int dañoVeneno) {
            this.dañoVeneno = dañoVeneno;
        }

        @Override
        public String toString() {
            return "CartaVeneno{" +
                    "dañoVeneno=" + dañoVeneno +
                    ", costeDeMana=" + super.costeDeMana +
                    '}';
        }
    }
}
