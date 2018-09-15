
import java.awt.*;
import java.io.File;
import java.io.IOException;
import  java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        String link="Flower.png";
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

    public static ArrayList<Color> getMainColors(){
        ArrayList<Color> colors = new ArrayList<Color>();
        ArrayList<Integer> finalColors = getColorApparition();

        for (int i = 0; i<finalColors.size();i++){
            Color color =new Color(finalColors.get(i), finalColors.get(i), finalColors.get(i));
            colors.add(color);
            //System.out.println(color.getRed());
        }
        return colors;
    }
    public static ArrayList<Integer> getColorApparition() {
        ArrayList<Integer> colorApparition = new ArrayList<Integer>();

        for(int i=0; i<256;i++){
            colorApparition.add(0);
        }
        for (int i=0;i<img.getWidth();i++){
            for (int j=0;j<img.getHeight();j++){
                int actualPixel = (new Color (img.getRGB(i,j))).getRed();
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

    public static BufferedImage getImg() {
        return img;
    }
}
