package HospitalCollections.base;

import HospitalCollections.models.Patient;

import java.util.List;

public interface IHospitalRepository extends ICrudRepository<Patient, String> {

    boolean isFull();

    int getNumPacients();

    int[] getNumByType();

    List<Patient> getAllOrderByEnterDate();

    List<Patient> getAllOrderByName();

    List<Patient> getAllOrderByType();

}
