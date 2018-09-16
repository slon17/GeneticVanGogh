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

    public Color mutation(ArrayList<Color> mainColors, int mutationPercentage, Random random){
        //Random random = new Random(System.currentTimeMillis());
        int randomPercentage = random.nextInt(100);
        if(randomPercentage < 80){
            int randomColor = random.nextInt(mainColors.size());
            return mainColors.get(randomColor);
        }
        return null;
    }

    public Individual mirrorXMutation(Individual individual, int mutationPercentage, Random random){
        int randomPercentage = random.nextInt(100);
        if(randomPercentage < mutationPercentage){
            BufferedImage tempImage = new BufferedImage(individual.getWidth(), individual.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for(int i = 0; i<individual.getWidth(); i++){
                for(int j = 0; j<individual.getHeight(); j++){
                    Color color1 = new Color(individual.getImage().getRGB(individual.getWidth()-i-1, j));
                    tempImage.setRGB(i, j, color1.getRGB());
                }
            }
            Individual mirrorIndividual = new Individual();
            mirrorIndividual.setImage(tempImage);
            return mirrorIndividual;
        }
        return individual;
    }

    public Individual mirrorYMutation(Individual individual, int mutationPercentage, Random random){
        int randomPercentage = random.nextInt(100);
        if(randomPercentage < mutationPercentage){
            BufferedImage tempImage = new BufferedImage(individual.getWidth(), individual.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for(int i = 0; i<individual.getWidth(); i++){
                for(int j = 0; j<individual.getHeight(); j++){
                    Color color1 = new Color(individual.getImage().getRGB(i, individual.getHeight()-j-1));
                    tempImage.setRGB(i, j, color1.getRGB());
                }
            }
            Individual mirrorIndividual = new Individual();
            mirrorIndividual.setImage(tempImage);
            return mirrorIndividual;
        }
        return individual;
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

    public Individual reproduceByFour(Individual individual1, Individual individual2, ArrayList<Color> mainColors, int mutationPercentage, Random random, boolean mutationFlag){

        //Random random = new Random(System.currentTimeMillis());

        Individual newIndividual1X = new Individual();
        Individual newIndividual1Y = new Individual();
        ArrayList<BufferedImage> dividedImage = divideImage(individual1.getImage());
        newIndividual1X.setImage(dividedImage.get(0));
        newIndividual1Y.setImage(dividedImage.get(1));

        Individual newIndividual2X = new Individual();
        Individual newIndividual2Y = new Individual();
        dividedImage = divideImage(individual2.getImage());
        newIndividual2X.setImage(dividedImage.get(0));
        newIndividual2Y.setImage(dividedImage.get(1));

        int randomOrder = random.nextInt(100);
        BufferedImage newImage;
        if(randomOrder > mutationPercentage){
            newImage = joinImage(reproduce(newIndividual1X, newIndividual2X, mainColors, mutationPercentage, random, 5, mutationFlag).getImage(),
                    reproduce(newIndividual1Y, newIndividual2Y, mainColors, mutationPercentage, random, 5, mutationFlag).getImage());
        }
        else{
            newImage = joinImage(reproduce(newIndividual1X, newIndividual2Y, mainColors, mutationPercentage, random, 5, mutationFlag).getImage(),
                    reproduce(newIndividual1Y, newIndividual2X, mainColors, mutationPercentage, random, 5, mutationFlag).getImage());
        }

        //BufferedImage newImage = joinImage(reproduce(newIndividual1X, newIndividual2X, mainColors, mutationPercentage, random).getImage(), reproduce(newIndividual1Y, newIndividual2Y, mainColors, mutationPercentage, random).getImage());
        Individual finalIndividual = new Individual();
        finalIndividual.setImage(newImage);
        //System.out.println("Reproducida: "+newImage.getWidth()+ " "+ newImage.getHeight());
        int mirrorPercentage = random.nextInt(100);
        if(mutationPercentage<mirrorPercentage){
            finalIndividual = mirrorXMutation(finalIndividual, mutationPercentage, random);
            finalIndividual = mirrorYMutation(finalIndividual, mutationPercentage, random);
        }


        return finalIndividual;
    }

    public Individual reproduce(Individual individual1, Individual individual2, ArrayList<Color> mainColors, int mutationPercentage, Random random, int randomChance, boolean mutationFlag) {

        //Random random = new Random(System.currentTimeMillis());
        int randomWidth = random.nextInt(individual1.getWidth()/4*3-individual1.getWidth()/4)+individual1.getWidth()/4;
        //System.out.println("random width: "+randomWidth);
        int randomHeight = random.nextInt(individual1.getHeight()/4*3-individual1.getHeight()/4)+individual1.getHeight()/4;
        int randomSide = random.nextInt(100);
        //System.out.println(randomSide);

        BufferedImage newImage = new BufferedImage(individual1.getWidth(), individual1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int randomChancePercentage = random.nextInt(100);
        Color mutationColor = mutation(mainColors, mutationPercentage, random);

        for (int i = 0; i < individual1.getWidth(); i++) {
            for (int j = 0; j < individual1.getHeight(); j++) {
                if (mutationColor != null && randomChance<randomChancePercentage && mutationFlag == true) {//mutation
                    newImage.setRGB(i, j, mutationColor.getRGB());
                    mutationColor = mutation(mainColors, mutationPercentage, random);
                    continue;
                }
                if (randomSide > 50) {
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

    public void reproduceAllBy4(ArrayList<Color> mainColors, int mutationPercentage){
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
                    son = reproduceByFour(parent1, parent2, mainColors, mutationPercentage, random, false);
                    tempBestQuantity = tempBestQuantity-2;
                }
                else{//reproduce best with regular
                    //System.out.println("Best size: "+bestIndividuals.size());
                    int randomParent1 = random.nextInt(bestIndividuals.size());
                    parent1 = bestIndividuals.remove(randomParent1);
                    int randomParent2 = random.nextInt(regularIndividuals.size());
                    parent2 = regularIndividuals.remove(randomParent2);
                    son = reproduceByFour(parent1, parent2, mainColors, mutationPercentage, random, true);
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
                    son = reproduceByFour(parent1, parent2, mainColors, mutationPercentage, random, false);
                    tempBadQuantity = tempBadQuantity-2;
                }
                else{//reproduce regular with bad
                    int randomParent1 = random.nextInt(badIndividuals.size());
                    parent1 = badIndividuals.remove(randomParent1);
                    int randomParent2 = random.nextInt(regularIndividuals.size());
                    parent2 = regularIndividuals.remove(randomParent2);
                    son = reproduceByFour(parent1, parent2, mainColors, mutationPercentage, random, true);
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

    public void reproduceAllByFour(ArrayList<Color> mainColors, int mutationPercentage){
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

        ArrayList<Individual> bestThree = new ArrayList<>();
        for(int i = 0; i<3; i++){
            bestThree.add(bestIndividuals.remove(bestIndividuals.size()-i-1));
            bestQuantity--;
            tempBestQuantity--;
        }

        while(tempTotal>1){
            Individual parent1 = null;
            Individual parent2 = null;
            Individual son = null;
            if(tempBestQuantity != 0){//best 3 with rest of best
                int randomBestParent = random.nextInt(3);
                parent1 = bestThree.get(randomBestParent);
                int randomParent = random.nextInt(bestIndividuals.size());
                parent2 = bestIndividuals.get(randomParent);
                son = reproduceByFour(parent1, parent2, mainColors, mutationPercentage, random, false);
                tempBestQuantity--;
            }
            else if(tempRegularQuantity != 0){//best rest with regular randomly
                int randomParent1 = random.nextInt(bestIndividuals.size());
                parent1 = bestIndividuals.get(randomParent1);
                int randomParent2 = random.nextInt(regularIndividuals.size());
                parent2 = regularIndividuals.get(randomParent2);
                son = reproduceByFour(parent1, parent2, mainColors, mutationPercentage, random, false);
                tempRegularQuantity--;
            }
            else if(tempBadQuantity != 0){//wost with worst pixel by pixel
                int randomParent1 = random.nextInt(badIndividuals.size());
                parent1 = badIndividuals.get(randomParent1);
                int randomParent2 = random.nextInt(badIndividuals.size());
                parent2 = badIndividuals.get(randomParent2);
                son = reproduceByFour(parent1, parent2, mainColors, mutationPercentage, random, true);
                tempBadQuantity--;
            }
            else{
                int randomIndividual = random.nextInt(population.size());
                son = population.get(randomIndividual);
            }
            tempTotal--;
            sons.add(son);
            //System.out.println("1");
            usedParents.add(parent1);
            //System.out.println("2");
            usedParents.add(parent2);
            //System.out.println("3");
        }
        population = sons;
        /*while(sons.size()<individualsQuantity){
            //System.out.println(usedParents.size());
            if(usedParents.size()==0){
                Individual tempIndividual = population.get(0);
                population.add(tempIndividual);
            }
            else {
                int randomParent = random.nextInt(usedParents.size());
                population.add(usedParents.remove(randomParent));
            }
        }*/
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
    public ArrayList<BufferedImage> divideImage(BufferedImage individual){
        ArrayList<BufferedImage> array = new ArrayList<BufferedImage>();
        BufferedImage newImage1 = new BufferedImage(individual.getWidth(), individual.getHeight()/2, BufferedImage.TYPE_INT_ARGB);
        BufferedImage newImage2 = new BufferedImage(individual.getWidth(), individual.getHeight()/2, BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<individual.getWidth();i++){
            for(int j=0;j<individual.getHeight()/2;i++){
                newImage1.setRGB(i,j,individual.getRGB(i,j));
            }
        }
        for(int i=0;i<individual.getWidth();i++){
            for(int j=individual.getHeight()/2;j<individual.getHeight();i++){
                newImage2.setRGB(i,j,individual.getRGB(i,j));
            }
        }
        array.add(newImage1);
        array.add(newImage2);

        return array;
    }
    public BufferedImage joinImage(BufferedImage img1, BufferedImage img2){
        BufferedImage newImage = new BufferedImage(img1.getWidth()*2, img1.getHeight()*2, BufferedImage.TYPE_INT_ARGB);
        ArrayList<BufferedImage> array = new ArrayList<BufferedImage>();
        for(int i=0;i<img1.getWidth();i++){
            for(int j=0;j<img1.getHeight();i++){
                newImage.setRGB(i,j,img1.getRGB(i,j));
            }
        }
        for(int i=0;i<img2.getWidth();i++){
            for(int j=img2.getHeight();j<img2.getHeight();i++){
                newImage.setRGB(i,j+img2.getHeight(),img2.getRGB(i,j));
            }
        }
        return  newImage;
    }

}
