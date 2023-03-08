package HospitalCollections.repositories;

import HospitalCollections.base.IHospitalRepository;
import HospitalCollections.models.Patient;
import HospitalCollections.models.PatientComparator;

import java.util.ArrayList;
import java.util.List;

import static HospitalCollections.models.PatientComparator.SortOrder.*;

public class PatientListRepository implements IHospitalRepository {
    private final ArrayList<Patient> dataBase = new ArrayList<>();

    @Override
    public List<Patient> getAll() {
        return dataBase;
    }

    @Override
    public Patient getById(String dniPacient) {

        for (Patient patient : dataBase) {
            if (patient.getDni().equals(dniPacient)) {
                return patient;
            }
        }
        return null;
    }

    @Override
    public Patient save(Patient entity) {
        // Update
        for (int i = 0; i < dataBase.size(); i++) {
            if (dataBase.get(i) != null && dataBase.get(i).getDni().equals(entity.getDni())) {
                dataBase.set(i, entity);
                return entity;
            }
        }
        // Add
        dataBase.add(entity);
        return entity;
    }

    @Override
    public Patient deleteById(String dni) {
        for (int i = 0; i < dataBase.size(); i++) {
            if (dataBase.get(i) != null && dataBase.get(i).getDni().equals(dni)) {
                Patient patientDeleted = dataBase.get(i);
                dataBase.set(i, null);
                return patientDeleted;
            }
        }
        return null;
    }

    @Override
    public boolean isFull() {
        int maxSize = 10;
        return dataBase.size() >= maxSize;
    }

    @Override
    public int getNumPacients() {
        return dataBase.size();
    }

    @Override
    public int[] getNumByType() {
        int countNormal = 0;
        int countUrgent = 0;
        for (Patient patient : dataBase) {
            if (patient.getType().equals(Patient.TypePatient.NORMAL)) {
                countNormal++;
            } else if (patient.getType().equals(Patient.TypePatient.URGENT)) {
                countUrgent++;
            }
        }
        int[] counts = new int[2];
        counts[0] = countNormal;
        counts[1] = countUrgent;
        return counts;
    }

    @Override
    public List<Patient> getAllOrderByEnterDate() {
        dataBase.sort(new PatientComparator(DATE_ENTER));
        return dataBase;
    }

    @Override
    public List<Patient> getAllOrderByName() {
        dataBase.sort(new PatientComparator(NAME));
        return dataBase;
    }

    @Override
    public List<Patient> getAllOrderByType() {
        dataBase.sort(new PatientComparator(TYPE));
        return dataBase;
    }
}
