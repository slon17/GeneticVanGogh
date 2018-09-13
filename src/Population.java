import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

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

    public Individual reproduce(Individual individual1, Individual individual2){
        /*ArrayList <Individual> sons = new ArrayList<>();
        ArrayList <Individual> part1 = getPartPopulation(0, population.size()/4);
        ArrayList <Individual> part2 = getPartPopulation(population.size()/4, population.size()/4*2);
        ArrayList <Individual> part3 = getPartPopulation(population.size()/4*2, population.size()/4*3);
        ArrayList <Individual> part4 = getPartPopulation(population.size()/4*3, population.size());

        int moduleWidth = population.get(0).getWidth()%4;
        int moduleHeight = population.get(0).getHeight()%4;*/

        Random random = new Random();
        int randomWidth = random.nextInt(individual1.getWidth());
        int randomHeight = random.nextInt(individual1.getHeight());
        int randomSide = random.nextInt(1);
        System.out.println(randomSide);

        BufferedImage newImage = new BufferedImage(individual1.getWidth(), individual1.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int i = 0; i<individual1.getWidth(); i++){
            for(int j = 0; j<individual1.getHeight(); j++){
                if(randomSide == 0){
                    if(i<randomWidth && j<randomHeight){
                        Color newColor = new Color(individual1.getImage().getRGB(i, j));
                        newImage.setRGB(i, j, newColor.getRGB());
                    }
                    else{
                        Color newColor = new Color(individual2.getImage().getRGB(i, j));
                        newImage.setRGB(i, j, newColor.getRGB());
                    }
                }
                else{
                    if(i<randomWidth && j<randomHeight){
                        Color newColor = new Color(individual2.getImage().getRGB(i, j));
                        newImage.setRGB(i, j, newColor.getRGB());
                    }
                    else{
                        Color newColor = new Color(individual1.getImage().getRGB(i, j));
                        newImage.setRGB(i, j, newColor.getRGB());
                    }
                }
            }
        }

        Individual newIndividual = new Individual(newImage);
        return newIndividual;
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
