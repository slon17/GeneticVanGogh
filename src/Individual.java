import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Individual {

    private static BufferedImage image;
    private float fitness;
    private int[] histogramOriginalImage;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public float getFitness() {
        return fitness;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public static BufferedImage generateRandImage(BufferedImage img) {
        BufferedImage randImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth() / 2; i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Random rand = new Random();

                int r1 = rand.nextInt(256);
                int g1 = rand.nextInt(256);
                int b1 = rand.nextInt(256);

                int r2 = rand.nextInt(256);
                int g2 = rand.nextInt(256);
                int b2 = rand.nextInt(256);

                Color newColor1 = new Color((r1 + g1 + b1) / 3, (r1 + g1 + b1) / 3, (r1 + g1 + b1) / 3);
                Color newColor2 = new Color((r2 + g2 + b2) / 3, (r2 + g2 + b2) / 3, (r2 + g2 + b2) / 3);
                randImage.setRGB(i, j, newColor1.getRGB());
                randImage.setRGB(randImage.getWidth() - i - 1, randImage.getHeight() - j - 1, newColor2.getRGB());
            }
        }
        return randImage;
    }

    public static ArrayList<Color> getMainColors(){
        ArrayList<Color> colors = new ArrayList<Color>();
        ArrayList<Integer> finalColors = getColorApparition();

        for (int i = 0; i<finalColors.size();i++){
            Color color =new Color(finalColors.get(i), finalColors.get(i), finalColors.get(i));
            colors.add(color);
            System.out.println(color.getRed());
        }
        return colors;
    }
    public static ArrayList<Integer> getColorApparition() {
        ArrayList<Integer> colorApparition = new ArrayList<Integer>();

        for(int i=0; i<256;i++){
            colorApparition.add(0);
        }
        for (int i=0;i<image.getWidth();i++){
            for (int j=0;j<image.getHeight();j++){
                int actualPixel = (new Color (image.getRGB(i,j))).getRed();
                colorApparition.set(actualPixel,colorApparition.get(actualPixel)+1);
            }
        }
        return  getDifferentFromZero(colorApparition);
    }
    public static ArrayList<Integer> getDifferentFromZero(ArrayList<Integer> colorApparition){

        ArrayList<Integer[]> differentFromZero = new ArrayList<Integer[]>();

        for (int i=0;i<colorApparition.size();i++){
            if(colorApparition.get(i)!=0){
                Integer[] par = new Integer[2];
                par[0]=colorApparition.get(i);
                par[1]=i;
                differentFromZero.add(par);
            }
        }
        Collections.sort(differentFromZero, new Comparator<Integer[]>() {
            public int compare(Integer[] int1, Integer[] int2) {
                Integer numOfKeys1 = int1[0];
                Integer numOfKeys2 = int2[0];
                return numOfKeys1.compareTo(numOfKeys2);
            }
        });
        return getTwentyPercentage(differentFromZero);
    }
    public static ArrayList<Integer> getTwentyPercentage (ArrayList<Integer[]> differentFromZero){
        float twentyPercent =Math.round(((float)(differentFromZero.size())/100)*20);
        ArrayList<Integer> twentyPercentColors = new ArrayList<Integer>();
        for (int i=differentFromZero.size()-1;i>differentFromZero.size()-twentyPercent;i--){
            twentyPercentColors.add(differentFromZero.get(i)[1]);
        }
        return twentyPercentColors;
    }
}
