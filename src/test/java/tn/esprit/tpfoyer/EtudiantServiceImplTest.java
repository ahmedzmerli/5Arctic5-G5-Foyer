package tn.esprit.tpfoyer;

import org.junit.jupiter.api.Test;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EtudiantServiceImplTest {

    EtudiantRepository etudiantRepository = mock(EtudiantRepository.class);
    EtudiantServiceImpl etudiantService = new EtudiantServiceImpl(etudiantRepository);

    @Test
    public void testRetrieveAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant(1L, "John", "Doe", 12345678, null, null));
        etudiants.add(new Etudiant(2L, "Jane", "Doe", 87654321, null, null));
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        assertEquals(2, result.size());
    }

    @Test
    public void testRetrieveEtudiant() {
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678, null, null);
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.retrieveEtudiant(1L);

        assertEquals("John", result.getNomEtudiant());
    }

    @Test
    public void testAddEtudiant() {
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678, null, null);
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.addEtudiant(etudiant);

        assertEquals("John", result.getNomEtudiant());
    }

    @Test
    public void testModifyEtudiant() {
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678, null, null);
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.modifyEtudiant(etudiant);

        assertEquals("John", result.getNomEtudiant());
    }

    @Test
    public void testRemoveEtudiant() {
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678, null, null);
        etudiantService.removeEtudiant(1L);
        assertTrue(true);  // Ensures no exception is thrown
    }
}

