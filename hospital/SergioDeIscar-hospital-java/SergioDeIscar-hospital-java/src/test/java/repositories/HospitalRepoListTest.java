package repositories;

import org.example.interfaces.HospitalExtension;
import org.example.repositories.HospitalRepoList;

public class HospitalRepoListTest extends HospitalRepoGeneric {
    @Override
    HospitalExtension createRepoEmpty() {
        return new HospitalRepoList();
    }

    @Override
    HospitalExtension createRepoShort() {
        return new HospitalRepoList(3);
    }
}