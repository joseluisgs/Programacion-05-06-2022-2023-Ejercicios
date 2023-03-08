package HospitalCollections.repositories;

import org.junit.jupiter.api.BeforeEach;

public class SetImplementacionRepositoryTest extends RepositoryTest {

    @BeforeEach
    public void setUp() {
        // Inicializa la implementación específica de la interfaz
        this.repoToEspecify = new PatientSetRepository();
    }

    // Prueba específicas si lo necesito...
    // Utilizo this.repoToEspecify el tipo de repo...
}
