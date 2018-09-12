
import java.awt.*;
import java.io.File;
import java.io.IOException;
import  java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/*https://www.youtube.com/watch?v=lGX0Gc6d51s*/

public class OriginalImg {

    static MainGui gui=null;
    static BufferedImage img;
    static int[] histograma = new int[256];

    public static void main(String[] args)
    {

        gui=new MainGui();

    }
    public static void load()
    {
        File file;
        String link="C:\\Users\\User\\IdeaProjects\\GeneticVanGogh\\Flower.png";
        try{
            file = new File(link);
            img = turnToGray(ImageIO.read(file));


        }catch (IOException e){
            System.out.println("error");
        }
    }
    public static BufferedImage turnToGray(BufferedImage img)
    {

        for (int i=0;i<img.getWidth()/2;i++){
            for (int j=0;j<img.getHeight();j++){
                int r1= (new Color(img.getRGB(i,j))).getRed();
                int g1=(new Color(img.getRGB(i,j))).getGreen();
                int b1=(new Color(img.getRGB(i,j))).getBlue();
                int r2= (new Color(img.getRGB(img.getWidth()-i-1,img.getHeight()-j-1))).getRed();
                int g2=(new Color(img.getRGB(img.getWidth()-i-1,img.getHeight()-j-1))).getGreen();
                int b2=(new Color(img.getRGB(img.getWidth()-i-1,img.getHeight()-j-1))).getBlue();
                Color newColor1 = new Color((r1+g1+b1)/3,(r1+g1+b1)/3,(r1+g1+b1)/3);
                Color newColor2 = new Color((r2+g2+b2)/3,(r2+g2+b2)/3,(r2+g2+b2)/3);
                img.setRGB(i,j,newColor1.getRGB());
                img.setRGB(img.getWidth()-i-1,img.getHeight()-j-1,newColor2.getRGB());
            }
        }
        return img;
    }

}
