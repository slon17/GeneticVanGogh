import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Individual {

    private BufferedImage image;
    private float fitness;
    private int width;
    private int height;

    public Individual(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public static BufferedImage generateRandImage(BufferedImage img)
    {
        BufferedImage randImage =   new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i=0;i<img.getWidth()/2;i++){
            for (int j=0;j<img.getHeight();j++){
                Random rand= new Random();

                int r1= rand.nextInt(256);
                int g1= rand.nextInt(256);
                int b1= rand.nextInt(256);

                int r2= rand.nextInt(256);
                int g2= rand.nextInt(256);
                int b2= rand.nextInt(256);

                Color newColor1 = new Color((r1+g1+b1)/3,(r1+g1+b1)/3,(r1+g1+b1)/3);
                Color newColor2 = new Color((r2+g2+b2)/3,(r2+g2+b2)/3,(r2+g2+b2)/3);
                randImage.setRGB(i,j,newColor1.getRGB());
                randImage.setRGB(randImage.getWidth()-i-1,randImage.getHeight()-j-1,newColor2.getRGB());
            }
        }
        return  randImage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
