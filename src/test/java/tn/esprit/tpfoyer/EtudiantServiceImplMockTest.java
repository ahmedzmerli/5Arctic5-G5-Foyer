package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EtudiantServiceImplMockTest {

    @Mock
    EtudiantRepository etudiantRepository;

    @InjectMocks
    EtudiantServiceImpl etudiantService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddEtudiant() {
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678, null, null);
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant result = etudiantService.addEtudiant(etudiant);

        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    public void testRetrieveEtudiantById() {
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678, null, null);
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.retrieveEtudiant(1L);

        assertEquals(1L, result.getIdEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    public void testRemoveEtudiant() {
        long etudiantId = 1L;
        etudiantService.removeEtudiant(etudiantId);

        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }
}
