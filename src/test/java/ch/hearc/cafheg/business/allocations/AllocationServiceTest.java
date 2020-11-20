package ch.hearc.cafheg.business.allocations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import ch.hearc.cafheg.business.common.Montant;
import ch.hearc.cafheg.infrastructure.api.RESTController;
import ch.hearc.cafheg.infrastructure.pdf.PDFExporter;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


class AllocationServiceTest {

  private AllocationService allocationService;

  private AllocataireMapper allocataireMapper;
  private AllocationMapper allocationMapper;
  private Map<String, Object> resultMap;
  private ParentDroitAllocation parent;



  @BeforeEach
  void setUp() {
    allocataireMapper = Mockito.mock(AllocataireMapper.class);
    allocationMapper = Mockito.mock(AllocationMapper.class);

    allocationService = new AllocationService(allocataireMapper, allocationMapper);

    ParentDroitAllocation parent=Mockito.mock(ParentDroitAllocation.class);
    parent=new ParentDroitAllocation("Neuchâtel",true,"Neuchâtel",
            true,"Bienne",true,new BigDecimal(2500),new BigDecimal(3000));


    resultMap=new HashMap<>();
    resultMap.put("enfantResidance","Neuchâtel");
    resultMap.put("parent1Residence", "Neuchâtel");
    resultMap.put("parent2Residence" ,"Bienne");
    resultMap.put("parent1ActiviteLucrative",true);
    resultMap.put("parent2ActiviteLucrative",true);
    resultMap.put("parent1Salaire",2500);
    resultMap.put("parent2Salaire",3000);


  }

  @Test
  void getParentDroitAllocation_GiveTwoSameResidence_ShouldBeParent_1(){
    String resultat=allocationService.getParentDroitAllocation(resultMap);
    assertThat(resultat).isEqualTo("Parent1");

  }
  @Test
  void getParentDroitAllocation_GiveSalaryP1UpperSalaryP2_ShouldBeParent_1(){
    String resultat=allocationService.getParentDroitAllocation(resultMap);
    assertThat(resultat).isEqualTo("Parent1");

  }
  /**
  @Test
  void getParentDroitAllocation_GivenSalaryP1LowerSalaryP2_ShouldBeParent_2(){
    String result=allocationService.getParentDroitAllocation(resultMap);
    assertThat(result).isEqualTo("Parent2");
  }
   **/
  @Test
  void getParentDroitAllocation_GivenParentsResidencesAreTheSame_ShouldBeParent_1(){
    String result=allocationService.getParentDroitAllocation(resultMap);
    assertThat(result).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_GivenChildResidenceEqualToParent_1(){
    String resultat=allocationService.getParentDroitAllocation(resultMap);
    assertThat(resultat).isEqualTo("Parent1");

  }
  @Test
  void getParentDroitAllocation_GivenChildResidenceEqualToParent_2(){
    String resultat=allocationService.getParentDroitAllocation(resultMap);
    assertThat(resultat).isEqualTo("Parent1");
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




}