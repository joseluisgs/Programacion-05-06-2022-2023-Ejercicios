package factory;

import models.Enemigo;

public class EnemigoFactory {
    private static EnemigoFactory instance;
    public EnemigoFactory() {}
    public static EnemigoFactory getInstance(){
        if(instance == null){
            instance = new EnemigoFactory();
        }
        return instance;
    }

    public Enemigo enemigoRandom(){
        Enemigo enemigo;
        int chance = (int) (Math.random()*100+1);
        if(chance <= 60){
            enemigo = new Enemigo();
        }else{
            if(chance <= 80){
                enemigo = new Enemigo.Curador();
            }else{
                enemigo = new Enemigo.Potenciador();
            }
        }
        return enemigo;
    }
}
