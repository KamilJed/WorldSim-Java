package WorldSim.OwnCollections;

import WorldSim.Organisms.Organism;

import java.util.LinkedList;
import java.util.ListIterator;

public class InitiativeList extends LinkedList<Organism> {

    public boolean addOrganism(Organism organism){

        for(ListIterator<Organism> iterator = this.listIterator(); iterator.hasNext();){
            if(organism.getInitiative() > iterator.next().getInitiative()){
                iterator.previous();
                iterator.add(organism);
                return true;
            }
        }
        this.addLast(organism);
        return true;
    }
}
