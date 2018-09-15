import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Euclidean {
    private BufferedImage original;

    public void setOriginal(BufferedImage original) {
        this.original = original;
    }

    public void euclidean(ArrayList<Individual> population) {
        //System.out.println("Euclidean:");
        for(int i = 0;i<population.size();i++) {
            population.get(i).setFitness(sum(population.get(i).getImage()));
            System.out.println(population.get(i).getFitness());
        }
    }
    public float sum(BufferedImage actual){
        int sum=0;
        for(int i = 0;i<original.getWidth();i++){
            for (int j = 0;j<original.getHeight();j++){
                int color1=(new Color(original.getRGB(i,j))).getRed();
                //System.out.println(color1);
                int color2=(new Color(actual.getRGB(i,j))).getRed();
                //System.out.println(color2);
                sum=sum+(int)Math.pow(color1-color2,2);
            }
        }
        sum=(int)Math.pow(sum,0.5);
        //System.out.println(sum);
        return Math.abs(100-(((float)100/8160)*sum));
    }

}
