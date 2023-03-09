package repositories;

import org.example.interfaces.HospitalExtension;
import org.example.repositories.HospitalRepoMapa;

public class HospitalRepoMapTest extends HospitalRepoGeneric {
    @Override
    HospitalExtension createRepoEmpty() {
        return new HospitalRepoMapa();
    }

    @Override
    HospitalExtension createRepoShort() {
        return new HospitalRepoMapa(3);
    }
}
