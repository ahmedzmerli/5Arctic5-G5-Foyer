package tn.esprit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.tpfoyer.entity.Foyer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class FoyerTest {

    @InjectMocks
    private Foyer foyer;

    private static final int MAX_CAPACITY = 500;

    @BeforeEach
    void setUp() {
        // Set up attributes for the foyer object directly
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Test Foyer");
        foyer.setCapaciteFoyer(100);
    }

    @Test
    void testFoyerAttributes() {
        assertThat(foyer.getIdFoyer()).isEqualTo(1L);
        assertThat(foyer.getNomFoyer()).isEqualTo("Test Foyer");
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(100);
    }

    @Test
    void testIncreaseCapacity() {
        foyer.setCapaciteFoyer(foyer.getCapaciteFoyer() + 50);
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(150);
    }

    @Test
    void testDecreaseCapacity() {
        foyer.setCapaciteFoyer(foyer.getCapaciteFoyer() - 30);
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(70);
    }


}