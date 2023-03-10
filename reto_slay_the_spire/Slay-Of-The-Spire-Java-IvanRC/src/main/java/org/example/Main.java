package org.example;

import models.Mapa;
import java.util.Scanner;

public class Main {

    private final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        final Mapa mapa = new Mapa();
        mapa.simulacion();
    }
}