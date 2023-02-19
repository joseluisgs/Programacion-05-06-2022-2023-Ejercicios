package HospitalCollections.models;

import java.util.Comparator;

public class PatientComparator implements Comparator<Patient> {
    private final SortOrder sortOrder;

    public PatientComparator(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    /*
    Si quiero en descendente, invertimos el valor que nos devuelve el compareTo, de tal manera:
    return -1 * p1.getName().compareTo(p2.getName());
    */
    @Override
    public int compare(Patient obj1, Patient obj2) {
        return switch (sortOrder) {
            case DNI -> obj1.getDni().compareTo(obj2.getDni());
            case DATE_ENTER -> obj1.getDateEnter().compareTo(obj2.getDateEnter());
            case NAME -> obj1.getName().compareTo(obj2.getName());
            case TYPE -> obj1.getType().compareTo(obj2.getType());
        };
    }

    public enum SortOrder {
        DNI,
        DATE_ENTER,
        NAME,
        TYPE
    }
}
