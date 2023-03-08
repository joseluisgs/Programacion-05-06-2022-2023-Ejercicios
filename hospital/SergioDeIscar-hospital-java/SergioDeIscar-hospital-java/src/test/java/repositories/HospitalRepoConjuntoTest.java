package repositories;

import org.example.interfaces.HospitalExtension;
import org.example.repositories.HospitalRepoConjunto;

public class HospitalRepoConjuntoTest extends HospitalRepoGeneric {
    @Override
    HospitalExtension createRepoEmpty() {
        return new HospitalRepoConjunto();
    }

    @Override
    HospitalExtension createRepoShort() {
        return new HospitalRepoConjunto(3);
    }
}
