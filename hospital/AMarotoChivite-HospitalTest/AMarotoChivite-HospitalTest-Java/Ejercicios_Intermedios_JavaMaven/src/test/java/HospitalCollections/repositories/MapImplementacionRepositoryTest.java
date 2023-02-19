package HospitalCollections.repositories;

import org.junit.jupiter.api.BeforeEach;

public class MapImplementacionRepositoryTest extends RepositoryTest {

    @BeforeEach
    public void setUp() {
        // Inicializa la implementación específica de la interfaz
        this.repoToEspecify = new PatientMapRepository();
    }

    // Prueba específicas si lo necesito...
    // Utilizo this.repoToEspecify el tipo de repo...
}
