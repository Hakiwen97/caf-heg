package ch.hearc.cafheg.business.allocations;


import ch.hearc.cafheg.business.versements.VersementParentEnfant;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import java.util.List;
import java.util.stream.Stream;

public class AllocationService {

  private static final String PARENT_1 = "Parent1";
  private static final String PARENT_2 = "Parent2";

  private final AllocataireMapper allocataireMapper;
  private final AllocationMapper allocationMapper;
  private final VersementMapper versementMapper;


  public AllocationService(AllocataireMapper allocataireMapper, AllocationMapper allocationMapper,
      VersementMapper versementMapper) {
    this.allocataireMapper = allocataireMapper;
    this.allocationMapper = allocationMapper;
    this.versementMapper = versementMapper;
  }


  public List<Allocataire> findAllAllocataires(String likeNom) {
    return allocataireMapper.findAll(likeNom);
  }

  public List<Allocation> findAllocationsActuelles() {
    return allocationMapper.findAll();
  }


  public Parent getParentDroitAllocation(ParentDroitAllocation droit) {

    Parent aDroit = null;

    // 1Parent a une activité lucrative
    if (droit.getParent1().hasLucrativeActivity() ^ droit.getParent2().hasLucrativeActivity()) {
      if (droit.getParent1().hasLucrativeActivity()) {
        aDroit = droit.getParent1();
      }
      if (droit.getParent2().hasLucrativeActivity()) {
        aDroit = droit.getParent2();
      }
    } else if (droit.getParent1().hasAutoriteParentale() ^ droit.getParent2()
        .hasAutoriteParentale()) {
      // 1 Parent a l'autorité parentale
      if (droit.getParent1().hasAutoriteParentale()) {
        aDroit = droit.getParent1();
      }
      if (droit.getParent2().hasAutoriteParentale()) {
        aDroit = droit.getParent2();
      }
    } else if (!droit.isParentsTogether()) {
      // Parents pas ensemble
      if (droit.getParent1().getParentAddress().equals(droit.getChildAddress())) {
        aDroit = droit.getParent1();
      }
      if (droit.getParent2().getParentAddress().equals(droit.getChildAddress())) {
        aDroit = droit.getParent2();
      }
    }
    //Parent ensemble
    //Parent canton
    if (droit.isParentsTogether()) {
      if (droit.getParent1().getWorkingCanton().equals(droit.getChildCanton()) ^ droit.getParent2()
          .getWorkingCanton().equals(droit.getChildCanton())) {
        if (droit.getParent1().getWorkingCanton().equals(droit.getChildCanton())) {
          aDroit = droit.getParent1();
        }
        if (droit.getParent2().getWorkingCanton().equals(droit.getChildCanton())) {
          aDroit = droit.getParent2();
        }
        // 1 salarié ou 2 salariés
      } else if ((droit.getParent1().isFreelancer() ^ droit.getParent2().isFreelancer()) || (
          !droit.getParent1().isFreelancer() && !droit.getParent2().isFreelancer())) {
        if (droit.getParent1().isFreelancer() && !droit.getParent2().isFreelancer()) {
          aDroit = droit.getParent2();
        } else if (!droit.getParent1().isFreelancer() && droit.getParent2().isFreelancer()) {
          aDroit = droit.getParent1();
        } else if (!droit.getParent1().isFreelancer() && !droit.getParent2().isFreelancer()) {
          int resultOfComparison = droit.getParent1().getSalary()
              .compareTo(droit.getParent2().getSalary());
          if (resultOfComparison == -1) {
            aDroit = droit.getParent2();
          } else if (resultOfComparison == 0) {
            aDroit = droit.getParent1();
            // Choix de conception voir Arnaud
          } else {
            aDroit = droit.getParent1();
          }
        }
      } else if (droit.getParent1().isFreelancer() && droit.getParent2().isFreelancer()) {
        int resultOfComparison = droit.getParent1().getSalary()
            .compareTo(droit.getParent2().getSalary());
        if (resultOfComparison == -1) {
          aDroit = droit.getParent2();
        } else if (resultOfComparison == 0) {
          aDroit = droit.getParent1();
          // Choix de conception voir Arnaud
        } else {
          aDroit = droit.getParent1();
        }
      }
    }
    return aDroit;
  }

  public boolean deleteAllocataire(long id) {
    boolean reponse = false;
    List<VersementParentEnfant> versements = versementMapper.findVersementParentEnfant();
    Stream<VersementParentEnfant> stream = versements.stream();
    //reponse=stream.anyMatch(vers -> vers.equals(id));
    reponse = stream.anyMatch(vers -> vers.getParentId() == id);

    if (reponse == true) {
      System.out.println("pas possible de supprimer");
      return false;
    } else {
      allocataireMapper.deleteAllocataire(id);
      return true;

    }


  }


  public boolean updateAllocataire(Allocataire allocataire, String nom, String prenom) {
    boolean reponse = false;
    if (allocataire.getPrenom().equals(prenom) && allocataire.getNom().equals(nom)) {
      reponse = false;
    } else if (!allocataire.getPrenom().equals(prenom) && !allocataire.getNom().equals(nom)) {
      reponse = false;
    } else {
      allocataireMapper.updateAllocataire(allocataire.getNoAVS().getValue(), nom, prenom);
      reponse = true;
    }
    return reponse;
  }

}



