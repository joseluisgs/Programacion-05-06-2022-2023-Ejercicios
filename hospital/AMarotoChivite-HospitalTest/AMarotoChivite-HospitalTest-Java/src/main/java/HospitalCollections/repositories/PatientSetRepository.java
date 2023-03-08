package HospitalCollections.repositories;

import HospitalCollections.base.IHospitalRepository;
import HospitalCollections.models.Patient;
import HospitalCollections.models.PatientComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static HospitalCollections.models.PatientComparator.SortOrder.*;

public class PatientSetRepository implements IHospitalRepository {
    // Es un conjunto si valores repetidos pero ordenados
    private final TreeSet<Patient> dataBase = new TreeSet<>(new PatientComparator(PatientComparator.SortOrder.DNI));

    @Override
    public List<Patient> getAll() {
        // Pasamos a lista el conjunto
        return new ArrayList<>(dataBase);
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
        dataBase.add(entity);
        return entity;
    }

    @Override
    public Patient deleteById(String dni) {
        // Únicamente podemos recorrer con un for-each, ya que por índices no podemos al ser un conjunto
        for (Patient patient : dataBase) {
            if (patient.getDni().equals(dni)) {
                Patient patientDeleted = patient;
                dataBase.remove(patient);
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
        List<Patient> orderedPatients = new ArrayList<>(dataBase);
        orderedPatients.sort(new PatientComparator(DATE_ENTER));
        return orderedPatients;
    }

    @Override
    public List<Patient> getAllOrderByName() {
        List<Patient> orderedPatients = new ArrayList<>(dataBase);
        orderedPatients.sort(new PatientComparator(NAME));
        return orderedPatients;
    }

    @Override
    public List<Patient> getAllOrderByType() {
        List<Patient> orderedPatients = new ArrayList<>(dataBase);
        orderedPatients.sort(new PatientComparator(TYPE));
        return orderedPatients;
    }
}
