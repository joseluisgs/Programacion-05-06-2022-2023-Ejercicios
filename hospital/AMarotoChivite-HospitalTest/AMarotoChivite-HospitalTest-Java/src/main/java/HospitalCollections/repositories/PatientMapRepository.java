package HospitalCollections.repositories;

import HospitalCollections.base.IHospitalRepository;
import HospitalCollections.models.Patient;
import HospitalCollections.models.PatientComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import static HospitalCollections.models.PatientComparator.SortOrder.*;

public class PatientMapRepository implements IHospitalRepository {
    // El mapa ordena por defecto por la clave
    private final TreeMap<String, Patient> dataBase = new TreeMap<>();

    @Override
    public List<Patient> getAll() {
        // Pasamos a lista los valores del mapa
        return new ArrayList<>(dataBase.values());
    }

    @Override
    public Patient getById(String dniPacient) {
        return dataBase.get(dniPacient);
    }

    @Override
    public Patient save(Patient entity) {
        dataBase.put(entity.getDni(), entity);
        return entity;
    }

    @Override
    public Patient deleteById(String dni) {
        Patient patientDeleted = dataBase.get(dni);
        dataBase.remove(dni);
        return patientDeleted;
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

        Collection<Patient> values = dataBase.values();

        for (Patient patient : values) {
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
        List<Patient> orderedPatients = new ArrayList<>(dataBase.values());
        orderedPatients.sort(new PatientComparator(DATE_ENTER));
        return orderedPatients;
    }

    @Override
    public List<Patient> getAllOrderByName() {
        List<Patient> orderedPatients = new ArrayList<>(dataBase.values());
        orderedPatients.sort(new PatientComparator(NAME));
        return orderedPatients;
    }

    @Override
    public List<Patient> getAllOrderByType() {
        List<Patient> orderedPatients = new ArrayList<>(dataBase.values());
        orderedPatients.sort(new PatientComparator(TYPE));
        return orderedPatients;
    }
}
