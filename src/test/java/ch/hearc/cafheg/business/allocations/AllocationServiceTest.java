package ch.hearc.cafheg.business.allocations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.hearc.cafheg.business.common.Montant;
import ch.hearc.cafheg.business.versements.VersementParentEnfant;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class AllocationServiceTest {

  private AllocationService allocationService;
  private AllocataireMapper allocataireMapper;
  private AllocationMapper allocationMapper;
  private VersementMapper versementMapper;
  private ParentDroitAllocation parents;
  private Parent parent1;
  private Parent parent2;


  @BeforeEach
  void setUp() {
    allocataireMapper = Mockito.mock(AllocataireMapper.class);
    allocationMapper = Mockito.mock(AllocationMapper.class);
    versementMapper = Mockito.mock(VersementMapper.class);

    allocationService = new AllocationService(allocataireMapper, allocationMapper, versementMapper);

    parent1 = new Parent(true, true, true, "Neuchâtel", Canton.NE, new BigDecimal(2500));
    parent2 = new Parent(true, false, true, "Bienne", Canton.BE, new BigDecimal(5000));

    parents = new ParentDroitAllocation("Neuchâtel", Canton.NE, false, parent1, parent2);

//  TRANSFORMATION INTO JSON
//  A mettre dans un test
//    try {
//      ObjectMapper mapper = new ObjectMapper();
//      System.out.println(mapper.writeValueAsString(parents));
//    }catch (Exception e){}

  }


  @Test
  void getParentDroitAllocation_GivenParent1LucrativeButNot2_ShouldBeParent1() {

    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(false);
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent1);
  }

  @Test
  void getParentDroitAllocation_GivenParent2LucrativeButNot1_ShouldBeParent2() {
    parents.getParent1().setLucrativeActivity(false);
    parents.getParent2().setLucrativeActivity(true);
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent2);
  }

  @Test
  void getParentDroitAllocation_GivenBothLucrativeButSolelyParent1HasAuthority_ShouldBeParent1() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(true);
    parents.getParent2().setParentalAuthority(false);
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent1);
  }

  @Test
  void getParentDroitAllocation_GivenBothLucrativeButSolelyParent2HasAuthority_ShouldBeParent2() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent2);
  }

  @Test
  void getParentDroitAllocation_GivenBothAuthoryChildrenLivesWithP1_ShouldBeParent1() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(true);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(false);
    parents.getParent1().setParentAddress("Neuchâtel");
    parents.getParent2().setParentAddress("La Chaux de Fonds");
    parents.setChildAddress("Neuchâtel");
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent1);
  }

  @Test
  void getParentDroitAllocation_GivenBothAuthoryChildrenLivesWithP2_ShouldBeParent2() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(true);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(false);
    parents.getParent2().setParentAddress("Neuchâtel");
    parents.getParent1().setParentAddress("La Chaux de Fonds");
    parents.setChildAddress("Neuchâtel");
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent2);
  }

  @Test
  void getParentDroitAllocation_GivenParentsLiveTogetherParent1WorksInChildrenResidingCanton_ShouldBeParent1() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.NE);
    parents.getParent2().setWorkingCanton(Canton.BE);
    parents.setChildCanton(Canton.NE);
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent1);
  }

  @Test
  void getParentDroitAllocation_GivenParentsLiveTogetherParent2WorksInChildrenResidingCanton_ShouldBeParent2() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.FR);
    parents.getParent2().setWorkingCanton(Canton.NE);
    parents.setChildCanton(Canton.NE);
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent2);
  }

  @Test
  void getParentDroitAllocation_GivenParent1IsIndependantButParent2Salarie_ShouldBeParent2() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.FR);
    parents.getParent2().setWorkingCanton(Canton.SH);
    parents.getParent1().setFreelancer(true);
    parents.getParent2().setFreelancer(false);
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent2);
  }

  @Test
  void getParentDroitAllocation_GivenParent2IsIndependantButParent1Salarie_ShouldBeParent1() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.FR);
    parents.getParent2().setWorkingCanton(Canton.SH);
    parents.getParent1().setFreelancer(false);
    parents.getParent2().setFreelancer(true);
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent1);
  }

  @Test
  void getParentDroitAllocation_GivenParentBothSalarieButP1GreaterSalary_ShouldBeP1() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.FR);
    parents.getParent2().setWorkingCanton(Canton.SH);
    parents.getParent1().setFreelancer(false);
    parents.getParent2().setFreelancer(false);
    parents.getParent1().setSalary(new BigDecimal(5000));
    parents.getParent2().setSalary(new BigDecimal(3000));
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent1);
  }

  @Test
  void getParentDroitAllocation_GivenParentBothSalarieButP2GreaterSalary_ShouldBeP2() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.FR);
    parents.getParent2().setWorkingCanton(Canton.SH);
    parents.getParent1().setFreelancer(false);
    parents.getParent2().setFreelancer(false);
    parents.getParent1().setSalary(new BigDecimal(2000));
    parents.getParent2().setSalary(new BigDecimal(3000));
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent2);
  }

  @Test
  void getParentDroitAllocation_GivenBothSalarieSameSalary_ShoulbBeP1() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.FR);
    parents.getParent2().setWorkingCanton(Canton.SH);
    parents.getParent1().setFreelancer(false);
    parents.getParent2().setFreelancer(false);
    parents.getParent1().setSalary(new BigDecimal(3000));
    parents.getParent2().setSalary(new BigDecimal(3000));
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent1);
  }

  @Test
  void getParentDroitAllocation_GivenBothIndependantButP1GreatSalary_ShouldBeP1() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.FR);
    parents.getParent2().setWorkingCanton(Canton.SH);
    parents.getParent1().setFreelancer(true);
    parents.getParent2().setFreelancer(true);
    parents.getParent1().setSalary(new BigDecimal(5000));
    parents.getParent2().setSalary(new BigDecimal(3000));
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent1);
  }

  @Test
  void getParentDroitAllocation_GivenBothIndependantButP2GreatSalary_ShouldBeP2() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.FR);
    parents.getParent2().setWorkingCanton(Canton.SH);
    parents.getParent1().setFreelancer(true);
    parents.getParent2().setFreelancer(true);
    parents.getParent1().setSalary(new BigDecimal(2000));
    parents.getParent2().setSalary(new BigDecimal(5000));
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent2);
  }

  @Test
  void getParentDroitAllocation_GivenBothIndependantButSameSalary_ShouldBeP1() {
    parents.getParent1().setLucrativeActivity(true);
    parents.getParent2().setLucrativeActivity(true);
    parents.getParent1().setParentalAuthority(false);
    parents.getParent2().setParentalAuthority(true);
    parents.setParentsTogether(true);
    parents.getParent1().setWorkingCanton(Canton.FR);
    parents.getParent2().setWorkingCanton(Canton.SH);
    parents.getParent1().setFreelancer(true);
    parents.getParent2().setFreelancer(true);
    parents.getParent1().setSalary(new BigDecimal(5000));
    parents.getParent2().setSalary(new BigDecimal(5000));
    Parent result = allocationService.getParentDroitAllocation(parents);
    assertThat(result).isEqualTo(parent1);
  }


  @Test
  void findAllAllocataires_GivenEmptyAllocataires_ShouldBeEmpty() {
    Mockito.when(allocataireMapper.findAll("Geiser")).thenReturn(Collections.emptyList());
    List<Allocataire> all = allocationService.findAllAllocataires("Geiser");
  }

  @Test
  void findAllAllocataires_Given2Geiser_ShouldBe2() {
    Mockito.when(allocataireMapper.findAll("Geiser"))
        .thenReturn(Arrays.asList(new Allocataire(new NoAVS("1000-2000"), "Geiser", "Arnaud"),
            new Allocataire(new NoAVS("1000-2001"), "Geiser", "Aurélie")));
    List<Allocataire> all = allocationService.findAllAllocataires("Geiser");
    assertAll(() -> assertThat(all.size()).isEqualTo(2),
        () -> assertThat(all.get(0).getNoAVS()).isEqualTo(new NoAVS("1000-2000")),
        () -> assertThat(all.get(0).getNom()).isEqualTo("Geiser"),
        () -> assertThat(all.get(0).getPrenom()).isEqualTo("Arnaud"),
        () -> assertThat(all.get(1).getNoAVS()).isEqualTo(new NoAVS("1000-2001")),
        () -> assertThat(all.get(1).getNom()).isEqualTo("Geiser"),
        () -> assertThat(all.get(1).getPrenom()).isEqualTo("Aurélie"));
  }

  @Test
  void findAllocationsActuelles() {
    Mockito.when(allocationMapper.findAll())
        .thenReturn(Arrays.asList(new Allocation(new Montant(new BigDecimal(1000)), Canton.NE,
            LocalDate.now(), null), new Allocation(new Montant(new BigDecimal(2000)), Canton.FR,
            LocalDate.now(), null)));
    List<Allocation> all = allocationService.findAllocationsActuelles();
    assertAll(() -> assertThat(all.size()).isEqualTo(2),
        () -> assertThat(all.get(0).getMontant()).isEqualTo(new Montant(new BigDecimal(1000))),
        () -> assertThat(all.get(0).getCanton()).isEqualTo(Canton.NE),
        () -> assertThat(all.get(0).getDebut()).isEqualTo(LocalDate.now()),
        () -> assertThat(all.get(0).getFin()).isNull(),
        () -> assertThat(all.get(1).getMontant()).isEqualTo(new Montant(new BigDecimal(2000))),
        () -> assertThat(all.get(1).getCanton()).isEqualTo(Canton.FR),
        () -> assertThat(all.get(1).getDebut()).isEqualTo(LocalDate.now()),
        () -> assertThat(all.get(1).getFin()).isNull());
  }

  @Test
  void deleteAllocataire_GivenId1_ShouldBeFalse() {
    Mockito.when(versementMapper.findVersementParentEnfant()).thenReturn(
        Arrays.asList(new VersementParentEnfant(1, 1, new Montant(new BigDecimal(300)))));
    boolean rep = allocationService.deleteAllocataire(1);
    assertFalse(rep);


  }

  @Test
  void deleteAllocataire_GivenId21_ShouldBeTrue() {
    Mockito.when(versementMapper.findVersementParentEnfant()).thenReturn(
        Arrays.asList(new VersementParentEnfant(1, 1, new Montant(new BigDecimal(300)))));
    boolean rep = allocationService.deleteAllocataire(21);
    assertTrue(rep);
  }

  @Test
  void updateAllocataire_GivenSameNameAndLastName_ShouldBeFalse() {
    Allocataire allocataire = new Allocataire(new NoAVS("1000-2001"), "Sigrist", "Adrien");
    boolean rep = allocationService.updateAllocataire(allocataire, "Sigrist", "Adrien");
    assertFalse(rep);
  }

  @Test
  void updateAllocataire_GivenNotTheSameNameButSameLastName_ShouldBeTrue() {
    Allocataire allocataire = new Allocataire(new NoAVS("1000-2001"), "Sigrist", "Adrien");
    boolean rep = allocationService.updateAllocataire(allocataire, "Sigrist", "Adrian");
    assertTrue(rep);

  }

  @Test
  void updateAllocataire_GivenTheSameNameButNotSameLastName_ShouldBeTrue() {
    Allocataire allocataire = new Allocataire(new NoAVS("1000-2001"), "Sigrist", "Adrien");
    boolean rep = allocationService.updateAllocataire(allocataire, "Siegrist", "Adrien");
    assertTrue(rep);

  }

  @Test
  void updateAllocataire_GivenNotTheSameNameAndNotSameLastName_ShouldBeFalse() {
    Allocataire allocataire = new Allocataire(new NoAVS("1000-2001"), "Sigrist", "Adrien");
    boolean rep = allocationService.updateAllocataire(allocataire, "Siegrist", "Adrienne");
    assertFalse(rep);

  }

}