import java.util.ArrayList;

public class Population {

    private ArrayList<Individual> population;

    public Population() {
        population = new ArrayList<>();
    }

    public void generatePopulation(int initialPopulation){
        for(int i = 0; i<initialPopulation; i++){
            Individual newIndividual = new Individual(Individual.generateRandImage(OriginalImg.getImg()));
            population.add(newIndividual);
            System.out.println(newIndividual);
        }
    }

    public ArrayList<Individual> reproduce(){
        ArrayList <Individual> sons = new ArrayList<>();
        ArrayList <Individual> part1 = getPartPopulation(0, population.size()/4);
        ArrayList <Individual> part2 = getPartPopulation(population.size()/4, population.size()/4*2);
        ArrayList <Individual> part3 = getPartPopulation(population.size()/4*2, population.size()/4*3);
        ArrayList <Individual> part4 = getPartPopulation(population.size()/4*3, population.size());

        int moduleWidth = population.get(0).getWidth()%4;
        int moduleHeight = population.get(0).getHeight()%4;

        for(int i = 0; i<population.size(); i++){
            i = i+1;
        }
        return sons;
    }

    public ArrayList<Individual> getPartPopulation(int begin, int end){
        ArrayList<Individual> partPopulation = new ArrayList<>();
        for (int i = begin; i<end; i++){
            partPopulation.add(population.get(i));
        }
        return partPopulation;
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Individual> population) {
        this.population = population;
    }

    public void reproduce (Individual individual1, Individual individual2){

        return;
    }
}
