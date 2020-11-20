package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.business.common.Montant;
import ch.hearc.cafheg.business.versements.Enfant;
import ch.hearc.cafheg.business.versements.VersementAllocation;
import ch.hearc.cafheg.business.versements.VersementAllocationNaissance;
import ch.hearc.cafheg.business.versements.VersementParentEnfant;
import ch.hearc.cafheg.infrastructure.pdf.PDFExporter;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AllocationService {

  private static final String PARENT_1 = "Parent1";
  private static final String PARENT_2 = "Parent2";

  private final AllocataireMapper allocataireMapper;
  private final AllocationMapper allocationMapper;

  public AllocationService(
      AllocataireMapper allocataireMapper,
      AllocationMapper allocationMapper) {
    this.allocataireMapper = allocataireMapper;
    this.allocationMapper = allocationMapper;
  }

  public List<Allocataire> findAllAllocataires(String likeNom) {
    return allocataireMapper.findAll(likeNom);
  }

  public List<Allocation> findAllocationsActuelles() {
    return allocationMapper.findAll();
  }

  public String getParentDroitAllocation(Map<String, Object> parameters) {
    System.out.println("Déterminer le droit aux allocations");
    String eR = (String)parameters.getOrDefault("enfantResidance", "");
    Boolean p1AL = (Boolean)parameters.getOrDefault("parent1ActiviteLucrative", false);
    String p1Residence = (String)parameters.getOrDefault("parent1Residence", "");
    Boolean p2AL = (Boolean)parameters.getOrDefault("parent2ActiviteLucrative", false);
    String p2Residence = (String)parameters.getOrDefault("parent2Residence", "");
    Boolean pEnsemble = (Boolean)parameters.getOrDefault("parentsEnsemble", false);
    Number salaireP1 = (Number) parameters.getOrDefault("parent1Salaire", BigDecimal.ZERO);
    Number salaireP2 = (Number) parameters.getOrDefault("parent2Salaire", BigDecimal.ZERO);

    if(eR.equals(p1Residence) || eR.equals(p2Residence)) {
      return PARENT_1;
    }

    if(salaireP1.doubleValue() > salaireP2.doubleValue()) {
      return PARENT_1;
    }

    if(salaireP1.doubleValue() < salaireP2.doubleValue()) {
      return PARENT_2;
    }

    if(eR.equals(p1Residence) && eR.equals(p2Residence)) {
      return PARENT_1;
    }

    if(eR.equals(p1Residence)) {
      return PARENT_1;
    }

    if(eR.equals(p2Residence)) {
      return PARENT_1;
    }

    return PARENT_2;
  }
 /**
  public String getParentDroitAllocation(ParentDroitAllocation parent) {
    String eR = parent.getEnfantResidance();
    Boolean p1AL=parent.isParent1ActiviteLucrative();
    String p1Residence=parent.getParent1Residence();
    Boolean p2AL=parent.isParent2ActiviteLucrative();
    String p2Residence=parent.getParent2Residence();
    Boolean pEnsemble=parent.isParentsEnsemble();
    BigDecimal salaireP1;
    salaireP1=parent.getParent1Salaire();
    BigDecimal salaireP2=parent.getParent2Salaire();


    System.out.println("Déterminer le droit aux allocations");

    if(eR.equals(p1Residence) || eR.equals(p2Residence)) {
      return PARENT_1;
    }


    if(salaireP1.compareTo(salaireP2)==1) {
      return PARENT_1;
    }

    if(salaireP1.doubleValue() < salaireP2.doubleValue()) {
      return PARENT_2;
    }

    if(eR.equals(p1Residence) && eR.equals(p2Residence)) {
      return PARENT_1;
    }

    if(eR.equals(p1Residence)) {
      return PARENT_1;
    }

    if(eR.equals(p2Residence)) {
      return PARENT_1;
    }

    return PARENT_2;
  }
  **/
}
