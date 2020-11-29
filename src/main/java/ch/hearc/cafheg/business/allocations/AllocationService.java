package ch.hearc.cafheg.business.allocations;


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
        if (droit.getParent1().hasLucrativeActivity() ^ droit.getParent2().hasLucrativeActivity()) {
            if (droit.getParent1().hasLucrativeActivity()) {
                aDroit = droit.getParent1();
            }
            if (droit.getParent2().hasLucrativeActivity()) {
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
            if (droit.getParent1().getWorkingCanton().equals(droit.getChildCanton()) ^ droit.getParent2().getWorkingCanton().equals(droit.getChildCanton())) {
                if (droit.getParent1().getWorkingCanton().equals(droit.getChildCanton())) {
                    aDroit = droit.getParent1();
                }
                if (droit.getParent2().getWorkingCanton().equals(droit.getChildCanton())) {
                    aDroit = droit.getParent2();
                }
                // 1 salarié ou 2 salariés
            } else if ((droit.getParent1().isFreelancer() ^ droit.getParent2().isFreelancer()) || (!droit.getParent1().isFreelancer() && !droit.getParent2().isFreelancer())) {
                if (droit.getParent1().isFreelancer() && !droit.getParent2().isFreelancer()) {
                    aDroit = droit.getParent2();
                } else if (!droit.getParent1().isFreelancer() && droit.getParent2().isFreelancer()) {
                    aDroit = droit.getParent1();
                } else if (!droit.getParent1().isFreelancer() && !droit.getParent2().isFreelancer()) {
                    int resultOfComparison = droit.getParent1().getSalary().compareTo(droit.getParent2().getSalary());
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
                int resultOfComparison = droit.getParent1().getSalary().compareTo(droit.getParent2().getSalary());
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



