package HospitalCollections.controllers;

import HospitalCollections.base.IHospitalRepository;
import HospitalCollections.exceptions.FullRepositoryException;
import HospitalCollections.exceptions.PatientBadRequestException;
import HospitalCollections.exceptions.PatientNotFoundException;
import HospitalCollections.models.Patient;
import HospitalCollections.repositories.PatientListRepository;
import HospitalCollections.repositories.PatientMapRepository;
import HospitalCollections.repositories.PatientSetRepository;

import java.util.List;

public class PatientController implements IHospitalRepository {
    private final PatientSetRepository patientSetRepository;
    private final PatientMapRepository patientMapRepository;
    private final PatientListRepository patientListRepository;
    private TypeRepo typeRepo;

    public PatientController(PatientListRepository patientListRepository, PatientSetRepository patientSetRepository, PatientMapRepository patientMapRepository) {
        this.patientListRepository = patientListRepository;
        this.patientSetRepository = patientSetRepository;
        this.patientMapRepository = patientMapRepository;
    }

    private static Boolean filterDni(String dni) {
        String regex = ("[0-9]{8}[A-Za-z]");
        return dni.matches(regex);
    }

    private static boolean filterName(String name) {
        String regex = "[a-zA-Z ]{3,20}";
        return name.matches(regex);
    }

    private static boolean filterDateIso(String dateIso) {
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        return dateIso.matches(regex);
    }

    private void checkMaxCapacity() throws FullRepositoryException {
        switch (this.typeRepo) {
            case LIST -> {
                if (patientListRepository.isFull()) {
                    throw new FullRepositoryException("La base de datos está llena, no se pueden guardar más pacientes.");
                }
            }
            case SET -> {
                if (patientSetRepository.isFull()) {
                    throw new FullRepositoryException("La base de datos está llena, no se pueden guardar más pacientes.");
                }
            }
            case MAP -> {
                if (patientMapRepository.isFull()) {
                    throw new FullRepositoryException("La base de datos está llena, no se pueden guardar más pacientes.");
                }
            }
        }
    }

    /*
    Para cambiar el tipo de repositorio que controlaremos
     */
    public void setTypeRepo(TypeRepo typeRepo) {
        this.typeRepo = typeRepo;
    }

    public TypeRepo getTypeRepo(TypeRepo typeRepo) {
        return this.typeRepo;
    }

    @Override
    public List<Patient> getAll() {
        return switch (this.typeRepo) {
            case LIST -> patientListRepository.getAll();
            case SET -> patientSetRepository.getAll();
            case MAP -> patientMapRepository.getAll();
        };
    }

    @Override
    public Patient getById(String dni) throws PatientNotFoundException {
        Patient patient = null;
        switch (this.typeRepo) {
            case LIST -> patient = patientListRepository.getById(dni);
            case SET -> patient = patientSetRepository.getById(dni);
            case MAP -> patient = patientMapRepository.getById(dni);
        }
        if (patient == null) {
            throw new PatientNotFoundException("El paciente con DNI " + dni + " no se encuentra en la base de datos.");
        }
        return patient;
    }

    @Override
    public Patient save(Patient entity) throws PatientBadRequestException, FullRepositoryException {
        // Comprobamos si tiene los datos correctors
        checkCorrectPatient(entity);

        // Comprobamos que se puedan introducir más objetos
        checkMaxCapacity();

        return switch (this.typeRepo) {
            case LIST -> patientListRepository.save(entity);
            case SET -> patientSetRepository.save(entity);
            case MAP -> patientMapRepository.save(entity);
        };
    }

    @Override
    public Patient deleteById(String dni) throws PatientNotFoundException {
        Patient patient = null;
        switch (this.typeRepo) {
            case LIST -> patient = patientListRepository.deleteById(dni);
            case SET -> patient = patientSetRepository.deleteById(dni);
            case MAP -> patient = patientMapRepository.deleteById(dni);
        }
        if (patient == null) {
            throw new PatientNotFoundException("El paciente con DNI " + dni + " no se encuentra en la base de datos.");
        }
        return patient;
    }

    @Override
    public boolean isFull() {
        return switch (this.typeRepo) {
            case LIST -> patientListRepository.isFull();
            case SET -> patientSetRepository.isFull();
            case MAP -> patientMapRepository.isFull();
        };
    }

    @Override
    public int getNumPacients() {
        return switch (this.typeRepo) {
            case LIST -> patientListRepository.getNumPacients();
            case SET -> patientSetRepository.getNumPacients();
            case MAP -> patientMapRepository.getNumPacients();
        };
    }

    @Override
    public int[] getNumByType() {
        return switch (this.typeRepo) {
            case LIST -> patientListRepository.getNumByType();
            case SET -> patientSetRepository.getNumByType();
            case MAP -> patientMapRepository.getNumByType();
        };
    }

    @Override
    public List<Patient> getAllOrderByEnterDate() {
        return switch (this.typeRepo) {
            case LIST -> patientListRepository.getAllOrderByEnterDate();
            case SET -> patientSetRepository.getAllOrderByEnterDate();
            case MAP -> patientMapRepository.getAllOrderByEnterDate();
        };
    }

    @Override
    public List<Patient> getAllOrderByName() {
        return switch (this.typeRepo) {
            case LIST -> patientListRepository.getAllOrderByName();
            case SET -> patientSetRepository.getAllOrderByName();
            case MAP -> patientMapRepository.getAllOrderByName();
        };
    }

    @Override
    public List<Patient> getAllOrderByType() {
        return switch (this.typeRepo) {
            case LIST -> patientListRepository.getAllOrderByType();
            case SET -> patientSetRepository.getAllOrderByType();
            case MAP -> patientMapRepository.getAllOrderByType();
        };
    }

    private void checkCorrectPatient(Patient patient) throws PatientBadRequestException {
        if (patient.getDni() == null || patient.getDni().equals("")) {
            throw new PatientBadRequestException("El DNI no puede estar vacío");
        }
        if (!filterDni(patient.getDni())) {
            throw new PatientBadRequestException("El DNI debe tener 8 número y una letra: (12345678A)!");
        }
        if (patient.getName() == null || patient.getName().equals("")) {
            throw new PatientBadRequestException("El nombre no puede estar vacío");
        }
        if (!filterName(patient.getName())) {
            throw new PatientBadRequestException("El nombre debe tener entre 3 y 20 caracteres");
        }
        if (patient.getDateNac() == null || patient.getDateNac().equals("")) {
            throw new PatientBadRequestException("La fecha de nacimiento no puede estar vacía");
        }
        if (!filterDateIso(patient.getDateNac())) {
            throw new PatientBadRequestException("La fecha debe estar en formato (aaaa-mm-dd)");
        }
        if (patient.getType() == null) {
            throw new PatientBadRequestException("El tipo del paciente no puede estar vacío");
        }
        if (patient.getDateEnter() == null || patient.getDateEnter().equals("")) {
            throw new PatientBadRequestException("La fecha de entrada no puede estar vacía");
        }
        if (!filterDateIso(patient.getDateEnter())) {
            throw new PatientBadRequestException("La fecha debe estar en formato (aaaa-mm-dd)");
        }
    }

    public enum TypeRepo {
        LIST,
        SET,
        MAP
    }
}
