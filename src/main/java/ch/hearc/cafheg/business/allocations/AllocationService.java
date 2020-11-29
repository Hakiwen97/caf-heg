package ch.hearc.cafheg.business.allocations;


import ch.hearc.cafheg.business.versements.VersementAllocation;
import ch.hearc.cafheg.business.versements.VersementParentEnfant;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;


import java.util.List;

public class AllocationService {

    private static final String PARENT_1 = "Parent1";
    private static final String PARENT_2 = "Parent2";

    private final AllocataireMapper allocataireMapper;
    private final AllocationMapper allocationMapper;





    public AllocationService(AllocataireMapper allocataireMapper, AllocationMapper allocationMapper) {
        this.allocataireMapper = allocataireMapper;
        this.allocationMapper = allocationMapper;
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
        if (droit.getParent1().hasActiviteLucrative() ^ droit.getParent2().hasActiviteLucrative()) {
            if (droit.getParent1().hasActiviteLucrative()) {
                aDroit = droit.getParent1();
            }
            if (droit.getParent2().hasActiviteLucrative()) {
                aDroit = droit.getParent2();
            }
        } else if (droit.getParent1().hasAutoriteParentale() ^ droit.getParent2().hasAutoriteParentale()) {
            // 1 Parent a l'autorité parentale
            if (droit.getParent1().hasAutoriteParentale()) {
                aDroit = droit.getParent1();
            }
            if (droit.getParent2().hasAutoriteParentale()) {
                aDroit = droit.getParent2();
            }
        } else if (!droit.isParentsEnsemble()) {
            // Parents pas ensemble
            if (droit.getParent1().getResidence().equals(droit.getEnfantResidance())) {
                aDroit = droit.getParent1();
            }
            if (droit.getParent2().getResidence().equals(droit.getEnfantResidance())) {
                aDroit = droit.getParent2();
            }
        }
        //Parent ensemble
        //Parent canton
        if (droit.isParentsEnsemble()) {
            if (droit.getParent1().getCantonTravail().equals(droit.getEnfantCanton()) ^ droit.getParent2().getCantonTravail().equals(droit.getEnfantCanton())) {
                if (droit.getParent1().getCantonTravail().equals(droit.getEnfantCanton())) {
                    aDroit = droit.getParent1();
                }
                if (droit.getParent2().getCantonTravail().equals(droit.getEnfantCanton())) {
                    aDroit = droit.getParent2();
                }
                // 1 salarié ou 2 salariés
            } else if ((droit.getParent1().isIndependant() ^ droit.getParent2().isIndependant()) || (!droit.getParent1().isIndependant() && !droit.getParent2().isIndependant())) {
                if (droit.getParent1().isIndependant() && !droit.getParent2().isIndependant()) {
                    aDroit = droit.getParent2();
                } else if (!droit.getParent1().isIndependant() && droit.getParent2().isIndependant()) {
                    aDroit = droit.getParent1();
                } else if (!droit.getParent1().isIndependant() && !droit.getParent2().isIndependant()) {
                    int resultOfComparison = droit.getParent1().getSalaire().compareTo(droit.getParent2().getSalaire());
                    if (resultOfComparison == -1) {
                        aDroit = droit.getParent2();
                    } else if (resultOfComparison == 0) {
                        aDroit = droit.getParent1();
                        // Choix de conception voir Arnaud
                    } else {
                        aDroit = droit.getParent1();
                    }
                }
            } else if (droit.getParent1().isIndependant() && droit.getParent2().isIndependant()) {
                int resultOfComparison = droit.getParent1().getSalaire().compareTo(droit.getParent2().getSalaire());
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
        boolean reponse = true;
        int i = 0;
        VersementMapper versementMapper = new VersementMapper();
        //cherche tous les versements
        List<VersementParentEnfant> versements = versementMapper.findVersementParentEnfant();
        while (i <= versements.size() || reponse == false) {
            for (VersementParentEnfant verEnfant : versements) {
                if (verEnfant.getParentId() == id) {
                    reponse = false;
                } else {
                    i++;
                }
            }

        }
        if(reponse==true){
           allocataireMapper.deleteAllocataire(id);
        }else{
            System.out.println("pas possible de supprimer ");
        }
        return reponse;
    }
}



