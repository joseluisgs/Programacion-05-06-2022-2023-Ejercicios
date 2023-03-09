package repositories;

import org.example.interfaces.ItvExtension;
import org.example.repositories.ItvRepository;

public class ItvRepoTest extends ItvRepoGeneric {
    @Override
    ItvExtension createRepoEmpty() {
        return new ItvRepository();
    }

    @Override
    ItvExtension createRepoShort() {
        return new ItvRepository(3);
    }
}
