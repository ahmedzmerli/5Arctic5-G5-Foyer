package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.entity.Universite;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UniversiteServiceImplTest {
  List<Universite> unis= new ArrayList<>();

  @Autowired
  UniversiteServiceImpl universiteService;

  @BeforeAll
  void setup() {
    Universite uni1 = Universite.builder()
        .nomUniversite("Esprit")
        .adresse("Ariana Mini")
        .build();
    Universite uni2 = Universite.builder()
        .nomUniversite("Insat")
        .adresse("Charg")
        .build();
    unis.add(uni1);
    unis.add(uni2);
    unis=unis.stream().map(universiteService::addUniversite).collect(Collectors.toList());
  }

  @Test
  @Order(1)
  void retrieveAllUniversites() {
    List<Universite> unis_from_db=universiteService.retrieveAllUniversites();
    assertEquals(unis.size(), unis_from_db.size());
  }

  @Test
  @Order(2)
  void retrieveUniversite() {
    Universite uni = universiteService.retrieveUniversite(unis.get(0).getIdUniversite());
    assertNotNull(uni);
  }

  @Test
  @Order(3)
  void addUniversite() {
    Universite uni3 = Universite.builder()
        .nomUniversite("ISI")
        .adresse("Ariana")
        .build();
    Universite uni_from_db=universiteService.addUniversite(uni3);
    assertNotNull(uni_from_db);
    unis.add(uni_from_db);
  }

  @Test
  @Order(4)
  void modifyUniversite() {
    Universite uni=universiteService.retrieveUniversite(unis.get(0).getIdUniversite());
    uni.setAdresse("Ariana XS");
    Universite uni_from_db=universiteService.modifyUniversite(uni);
    assertEquals(uni.getAdresse(), uni_from_db.getAdresse());
  }

  @Test
  @Order(5)
  void removeUniversite() {
    unis.remove(unis.get(0));
    universiteService.removeUniversite(unis.get(0).getIdUniversite());
    assertEquals(unis.size(), 2);
  }

  @AfterAll
  void cleanup() {
    unis.forEach(uni -> universiteService.removeUniversite(uni.getIdUniversite()));
    unis.clear();
  }
}
