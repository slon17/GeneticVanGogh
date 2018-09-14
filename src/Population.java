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
        if(randomPercentage < 20){
            int randomColor = random.nextInt(mainColors.size());
            return mainColors.get(randomColor);
        }
        return null;
    }

    public Individual reproduce(Individual individual1, Individual individual2, ArrayList<Color> mainColors) {
        /*ArrayList <Individual> sons = new ArrayList<>();
        ArrayList <Individual> part1 = getPartPopulation(0, population.size()/4);
        ArrayList <Individual> part2 = getPartPopulation(population.size()/4, population.size()/4*2);
        ArrayList <Individual> part3 = getPartPopulation(population.size()/4*2, population.size()/4*3);
        ArrayList <Individual> part4 = getPartPopulation(population.size()/4*3, population.size());

        int moduleWidth = population.get(0).getWidth()%4;
        int moduleHeight = population.get(0).getHeight()%4;*/

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
        while(population.size()>1){
            int randomParent1 = random.nextInt(population.size());
            Individual parent1 = population.remove(randomParent1);
            int randomParent2 = random.nextInt(population.size());
            Individual parent2 = population.remove(randomParent2);
            Individual son = reproduce(parent1, parent2, mainColors);
            sons.add(son);
            usedParents.add(parent1);
            usedParents.add(parent2);
        }
        population = sons;
        while(population.size()<individualsQuantity){
            int randomParent = random.nextInt(usedParents.size());
            population.add(usedParents.remove(randomParent));
        }
        currentGeneration++;
        System.out.println("Current generation: "+currentGeneration);
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

}
