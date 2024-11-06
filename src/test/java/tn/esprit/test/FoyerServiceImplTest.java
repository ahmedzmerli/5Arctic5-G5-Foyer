package tn.esprit.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;
import java.util.Optional;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FoyerServiceImplTest {

    @Mock
    private FoyerRepository foyerRepository; // Mock the FoyerRepository

    @InjectMocks
    private FoyerServiceImpl foyerService; // The service class where the mock will be injected

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    void testRetrieveAllFoyers() {
        // Arrange
        Foyer foyer1 = new Foyer();
        Foyer foyer2 = new Foyer();
        List<Foyer> mockFoyers = Arrays.asList(foyer1, foyer2);

        when(foyerRepository.findAll()).thenReturn(mockFoyers); // Mock the repository call

        // Act
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Assert
        assertEquals(2, foyers.size());
        verify(foyerRepository, times(1)).findAll(); // Verify that the method was called once
    }

    @Test
    void testRetrieveFoyerById() {
        // Arrange
        Foyer mockFoyer = new Foyer();
        mockFoyer.setIdFoyer(1L);
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(mockFoyer)); // Mock findById

        // Act
        Foyer foyer = foyerService.retrieveFoyer(1L);

        // Assert
        assertEquals(1L, foyer.getIdFoyer());
        verify(foyerRepository, times(1)).findById(1L); // Verify that the method was called once
    }

    @Test
    void testAddFoyer() {
        // Arrange
        Foyer newFoyer = new Foyer();
        newFoyer.setNomFoyer("Test Foyer");
        newFoyer.setCapaciteFoyer(100);

        when(foyerRepository.save(newFoyer)).thenReturn(newFoyer); // Mock save

        // Act
        Foyer savedFoyer = foyerService.addFoyer(newFoyer);

        // Assert
        assertEquals(newFoyer, savedFoyer);
        assertEquals("Test Foyer", savedFoyer.getNomFoyer());
        assertEquals(100, savedFoyer.getCapaciteFoyer());
        verify(foyerRepository, times(1)).save(newFoyer); // Verify save was called
    }

    @Test
    void testModifyFoyer() {
        // Arrange
        Foyer existingFoyer = new Foyer();
        existingFoyer.setIdFoyer(1L);
        existingFoyer.setNomFoyer("Modified Foyer");
        existingFoyer.setCapaciteFoyer(200);

        when(foyerRepository.save(existingFoyer)).thenReturn(existingFoyer); // Mock save

        // Act
        Foyer modifiedFoyer = foyerService.modifyFoyer(existingFoyer);

        // Assert
        assertEquals("Modified Foyer", modifiedFoyer.getNomFoyer());
        assertEquals(200, modifiedFoyer.getCapaciteFoyer());
        verify(foyerRepository, times(1)).save(existingFoyer); // Verify save was called
    }

    @Test
    void testRemoveFoyer() {
        // Arrange
        Foyer mockFoyer = new Foyer();
        mockFoyer.setIdFoyer(1L);

        // No need to mock findById if it's not used

        // Act
        foyerService.removeFoyer(1L);

        // Assert
        verify(foyerRepository, times(1)).deleteById(1L); // Verify delete was called
    }
}
