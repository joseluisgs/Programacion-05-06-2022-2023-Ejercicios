package HospitalCollections.base;

import HospitalCollections.exceptions.FullRepositoryException;
import HospitalCollections.exceptions.PatientBadRequestException;
import HospitalCollections.exceptions.PatientNotFoundException;

import java.util.List;

public interface ICrudRepository<T, ID> {

    List<T> getAll();

    T getById(ID id) throws PatientNotFoundException;

    T save(T entity) throws PatientBadRequestException, FullRepositoryException;

    T deleteById(ID id) throws PatientNotFoundException;
}
