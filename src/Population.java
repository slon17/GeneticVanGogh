import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Population {

    private ArrayList<Individual> population;
    private int individualsQuantity;
    private int currentGeneration;


    public Population(int individualsQuantity) {
        population = new ArrayList<>();
        this.individualsQuantity = individualsQuantity;
        currentGeneration = 0;
    }

    public void generatePopulation(int initialPopulation){
        for(int i = 0; i<initialPopulation; i++){
            //Individual newIndividual = new Individual(Individual.generateRandImage(OriginalImg.getImg()));
            Individual newIndividual = new Individual();
            newIndividual.setImage(newIndividual.generateRandImage(OriginalImg.img));
            population.add(newIndividual);
            //System.out.println("Individuo: "+population.get(population.size()-1).getImage());
            //System.out.println(newIndividual);
        }
    }

    public Color mutation(ArrayList<Color> mainColors){
        Random random = new Random(System.currentTimeMillis());
        int randomPercentage = random.nextInt(256);
        if(randomPercentage < 2){
            int randomColor = random.nextInt(mainColors.size());
            return mainColors.get(randomColor);
        }
        return null;
    }


    public Individual reproduceByPixel(Individual individual1, Individual individual2){
        Individual son = new Individual();
        BufferedImage newImage = new BufferedImage(individual1.getWidth(), individual1.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int i=0; i<individual1.getWidth(); i++){
            for(int j=0; j<individual1.getHeight(); j++){
                if(j/2==0){
                    newImage.setRGB(i,j,individual1.getImage().getRGB(i,j));
                }
                else{
                    newImage.setRGB(i,j,individual2.getImage().getRGB(i,j));
                }
            }
        }
        son.setImage(newImage);
        return son;
    }

    public Individual reproduce(Individual individual1, Individual individual2, ArrayList<Color> mainColors) {

        Random random = new Random(System.currentTimeMillis());
        int randomWidth = random.nextInt(individual1.getWidth());
        int randomHeight = random.nextInt(individual1.getHeight());
        int randomSide = random.nextInt(2);
        //System.out.println(randomSide);

        BufferedImage newImage = new BufferedImage(individual1.getWidth(), individual1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Color mutationColor = mutation(mainColors);

        for (int i = 0; i < individual1.getWidth(); i++) {
            for (int j = 0; j < individual1.getHeight(); j++) {
                if (mutationColor != null) {//mutation
                    newImage.setRGB(i, j, mutationColor.getRGB());
                    continue;
                }
                if (randomSide == 0) {
                    if (i < randomWidth && j < randomHeight) {
                        Color newColor = new Color(individual1.getImage().getRGB(i, j));
                        newImage.setRGB(i, j, newColor.getRGB());
                    }
                    else {
                        Color newColor = new Color(individual2.getImage().getRGB(i, j));
                        newImage.setRGB(i, j, newColor.getRGB());
                    }
                }
                else {
                    if (i < randomWidth && j < randomHeight) {
                        Color newColor = new Color(individual2.getImage().getRGB(i, j));
                        newImage.setRGB(i, j, newColor.getRGB());
                    }
                    else {
                        Color newColor = new Color(individual1.getImage().getRGB(i, j));
                        newImage.setRGB(i, j, newColor.getRGB());
                    }
                }
            }
        }
        Individual newIndividual = new Individual(newImage);
        return newIndividual;
    }

    public void reproduceAll(ArrayList<Color> mainColors){
        ArrayList<Individual> sons = new ArrayList<>();
        ArrayList<Individual> usedParents = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());

        sort(population, 0, population.size()-1);

        ArrayList <Individual> badIndividuals = getPartPopulation(0, population.size()/3);
        ArrayList <Individual> regularIndividuals = getPartPopulation(population.size()/3, (population.size()/3)*2);
        ArrayList <Individual> bestIndividuals = getPartPopulation((population.size()/3)*2, population.size());

        int badQuantity = badIndividuals.size();
        int tempBadQuantity = badQuantity;
        int regularQuantity = regularIndividuals.size();
        int tempRegularQuantity = regularQuantity;
        int bestQuantity = bestIndividuals.size();
        int tempBestQuantity = bestQuantity;
        int tempTotal = individualsQuantity;

        while(tempTotal>1){
            Individual parent1 = null;
            Individual parent2 = null;
            Individual son = null;
            if(bestIndividuals.size() != 0){
                if(tempBestQuantity > bestQuantity/2){//reproduce best with best
                    int randomParent1 = random.nextInt(bestIndividuals.size());
                    parent1 = bestIndividuals.remove(randomParent1);
                    int randomParent2 = random.nextInt(bestIndividuals.size());
                    parent2 = bestIndividuals.remove(randomParent2);
                    son = reproduce(parent1, parent2, mainColors);
                    tempBestQuantity = tempBestQuantity-2;
                }
                else{//reproduce best with regular
                    //System.out.println("Best size: "+bestIndividuals.size());
                    int randomParent1 = random.nextInt(bestIndividuals.size());
                    parent1 = bestIndividuals.remove(randomParent1);
                    int randomParent2 = random.nextInt(regularIndividuals.size());
                    parent2 = regularIndividuals.remove(randomParent2);
                    son = reproduce(parent1, parent2, mainColors);
                    tempBestQuantity--;
                    tempRegularQuantity--;
                    if(tempBestQuantity == 1){
                        regularIndividuals.add(bestIndividuals.remove(0));
                        regularQuantity++;
                        tempRegularQuantity++;
                    }
                }
            }
            else if(badIndividuals.size() != 0){
                    if(tempBadQuantity > badQuantity/2){//reproduce bad with bad
                        int randomParent1 = random.nextInt(badIndividuals.size());
                        parent1 = badIndividuals.remove(randomParent1);
                        int randomParent2 = random.nextInt(badIndividuals.size());
                        parent2 = badIndividuals.remove(randomParent2);
                        son = reproduce(parent1, parent2, mainColors);
                        tempBadQuantity = tempBadQuantity-2;
                    }
                    else{//reproduce regular with bad
                        int randomParent1 = random.nextInt(badIndividuals.size());
                        parent1 = badIndividuals.remove(randomParent1);
                        int randomParent2 = random.nextInt(regularIndividuals.size());
                        parent2 = regularIndividuals.remove(randomParent2);
                        son = reproduce(parent1, parent2, mainColors);
                        tempBestQuantity--;
                        tempRegularQuantity--;
                        //System.out.println("Regular with bad");
                    }
            }
            tempTotal--;
            if(son !=null || parent1!=null || parent2!=null){
                sons.add(son);
                //System.out.println("1");
                usedParents.add(parent1);
                //System.out.println("2");
                usedParents.add(parent2);
                //System.out.println("3");
            }
        }
        population = sons;
        while(sons.size()<individualsQuantity){
            //System.out.println(usedParents.size());
            if(usedParents.size()==0){
                Individual tempIndividual = population.get(0);
                population.add(tempIndividual);
            }
            else {
                int randomParent = random.nextInt(usedParents.size());
                population.add(usedParents.remove(randomParent));
            }
        }
        sort(population,0,population.size()-1);
        currentGeneration++;
        //System.out.println("Current generation: "+currentGeneration);
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

    public int partition(ArrayList<Individual> population, int low, int high)
    {
        Individual pivot = population.get(high);
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (population.get(j).getFitness() <= pivot.getFitness())
            {
                i++;

                // swap arr[i] and arr[j]
                Individual temp = population.get(i);
                population.set(i,population.get(j));
                population.set(j,temp);
            }
        }
        Individual temp = population.get(i+1);
        population.set(i+1,population.get(high));
        population.set(high,temp);

        return i+1;
    }

    public void sort(ArrayList<Individual> population, int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(population, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(population, low, pi-1);
            sort(population, pi+1, high);
        }
    }

}
