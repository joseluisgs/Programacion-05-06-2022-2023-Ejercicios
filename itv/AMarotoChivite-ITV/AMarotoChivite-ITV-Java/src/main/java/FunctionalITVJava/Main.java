package FunctionalITVJava;

import FunctionalITVJava.repositories.ITVrepo;

public class Main {
    public static void main(String[] args) {
        var repoITV = new ITVrepo();

        System.out.println("=== getAll ===");
        repoITV.getAll().forEach(it -> System.out.println(it));
        // repoITV.searchByBrand("Toyota").forEach(System.out::println);
        System.out.println();

        System.out.println("=== getOnlyCars ===");
        repoITV.getOnlyCars().forEach(it -> System.out.println(it));
        System.out.println();

        System.out.println("=== getMoreModern ===");
        System.out.println(repoITV.getMoreModern());
        System.out.println();

        System.out.println("=== getLessKm ===");
        System.out.println(repoITV.getLessKm());
        System.out.println();

        System.out.println("=== searchByBrand ===");
        repoITV.searchByBrand("Toyota").forEach(it -> System.out.println(it));
        System.out.println();

        System.out.println("=== getAverageKmAllMoto ===");
        System.out.println(repoITV.getAverageKmAllMoto());
        System.out.println();

        System.out.println("=== getCarMoreAncientWithMoreTwoDoors ===");
        System.out.println(repoITV.getCarMoreAncientWithMoreTwoDoors());
        System.out.println();

        System.out.println("=== getNumByBrand ===");
        repoITV.getNumByBrand().forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println();

        System.out.println("=== getNumAptosByVehicle ===");
        repoITV.getNumAptosByVehicle().forEach((key, value) -> System.out.println("Aptos" + key + ":" + value));
        System.out.println();

        System.out.println("=== getByVehicleAverageYearFabricate ===");
        repoITV.getByVehicleAverageYearFabricate().forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println();

        System.out.println("=== getAllGroupByBrand ===");
        repoITV.getAllGroupByBrand().forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println();

        System.out.println("=== getAllOrderByYearFabricate ===");
        repoITV.getAllOrderByYearFabricate().forEach(System.out::println);
        System.out.println();

        System.out.println("=== getAllOrderByBrandDesc ===");
        repoITV.getAllOrderByBrandDesc().forEach(System.out::println);
        System.out.println();

        System.out.println("=== getAllGroupByBrandAndSortedByKmDesc ===");
        repoITV.getAllGroupByBrandAndSortedByKmDesc().forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println();
    }
}
