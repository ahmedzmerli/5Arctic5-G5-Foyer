package tn.esprit.tpfoyer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllChambres() {
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        List<Chambre> mockChambres = Arrays.asList(chambre1, chambre2);

        when(chambreRepository.findAll()).thenReturn(mockChambres);

        List<Chambre> chambres = chambreService.retrieveAllChambres();

        assertEquals(2, chambres.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveChambre() {
        Chambre mockChambre = new Chambre();
        mockChambre.setIdChambre(1L);

        when(chambreRepository.findById(1L)).thenReturn(Optional.of(mockChambre));

        Chambre chambre = chambreService.retrieveChambre(1L);

        assertEquals(1L, chambre.getIdChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testAddChambre() {
        Chambre newChambre = new Chambre();
        newChambre.setNumeroChambre(101);
        newChambre.setTypeC(TypeChambre.SIMPLE);

        Set<Reservation> reservations = new HashSet<>(); // Mock reservations if needed
        newChambre.setReservations(reservations);

        Bloc bloc = new Bloc(); // Mock Bloc if needed
        newChambre.setBloc(bloc);

        when(chambreRepository.save(newChambre)).thenReturn(newChambre);

        Chambre savedChambre = chambreService.addChambre(newChambre);

        assertEquals(newChambre, savedChambre);
        assertEquals(101, savedChambre.getNumeroChambre());
        assertEquals(TypeChambre.SIMPLE, savedChambre.getTypeC());
        verify(chambreRepository, times(1)).save(newChambre);
    }

    @Test
    void testRecupererChambresSelonTyp() {
        TypeChambre type1 = TypeChambre.SIMPLE;
        TypeChambre type2 = TypeChambre.DOUBLE;

        Chambre chambre1 = new Chambre();
        chambre1.setTypeC(type1);
        Chambre chambre2 = new Chambre();
        chambre2.setTypeC(type2);
        Chambre chambre3 = new Chambre();
        chambre3.setTypeC(type1);

        List<Chambre> mockChambres = Arrays.asList(chambre1, chambre2, chambre3);
        when(chambreRepository.findAllByTypeC(type1)).thenReturn(Arrays.asList(chambre1, chambre3));
        when(chambreRepository.findAllByTypeC(type2)).thenReturn(Arrays.asList(chambre2));

        List<Chambre> chambresType1 = chambreService.recupererChambresSelonTyp(type1);
        List<Chambre> chambresType2 = chambreService.recupererChambresSelonTyp(type2);

        assertEquals(2, chambresType1.size());
        assertEquals(type1, chambresType1.get(0).getTypeC());
        assertEquals(type1, chambresType1.get(1).getTypeC());

        assertEquals(1, chambresType2.size());
        assertEquals(type2, chambresType2.get(0).getTypeC());

        verify(chambreRepository, times(1)).findAllByTypeC(type1);
        verify(chambreRepository, times(1)).findAllByTypeC(type2);
    }

}
