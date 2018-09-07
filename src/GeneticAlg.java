
import java.awt.*;
import java.io.File;
import java.io.IOException;
import  java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
public class GeneticAlg {
    protected int[] histogramOriginalImage;


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
    public static int[] generateHistogram(BufferedImage img)
    {
        String  sector ="";
        int index=0;
        int[] histogram = new int[256];
        int[][] smallMat = new int[3][3];
        for(int i=1; i<256; i++){
            histogram[i]=0;
        }

        for(int i=1; i<img.getWidth()-1; i++){
            for(int j=1; j<img.getHeight()-1; j++){
                int actualPixel = (new Color (img.getRGB(i,j))).getRed();
                index=generateBinaryNumber(generateSmallMat(i,j,img,actualPixel));

                histogram[index]+=1;
                //System.out.println((Integer.parseInt(sector,2)));
                //sector="";
            }
        }
        System.out.println("Histogram done");
 //       for(int i=0;i<256;i++){
 //           System.out.println(histogram[i]);
 //       }
        return histogram;
    }
    public static String[][] generateSmallMat(int i,int j,BufferedImage img,int actualPixel)
    {
        String [][] grid = new String[3][3];
        for(int s=0; s<3; s++){
            for(int d=0; d<3;d++){
                int neighbor=(new Color (img.getRGB((i-1)+s,(j-1)+d))).getRed();
                if(actualPixel<=neighbor){
                    grid[s][d]="1";
                }else{
                    grid[s][d]="0";
                }
            }
        }
        return grid;
    }
    public static int generateBinaryNumber(String[][] grid)
    {
        int binary=0;
        String binaryString="";
        for(int i=0;i<=2;i++)
        {
            binaryString+=(grid[0][i]);
        }
        for(int i=1;i<=2;i++)
        {
            binaryString+=(grid[i][2]);
        }
        for(int i=1;i>=0;i--)
        {
            binaryString+=(grid[2][i]);
        }
        binaryString+=grid[1][0];
        //System.out.println(binaryString);
        //System.out.println((Integer.parseInt(binaryString,2)));
        binary=(Integer.parseInt(binaryString,2));
        return binary;
    }
    public float grade(int[] newImage)
    {
        float sum=0;
        float grade;
        for(int i=0;i<256;i++)
        {
            if(histogramOriginalImage[i]==newImage[i]){
                sum=sum+1;
            }
        }
        grade=(100*sum)/256;
        System.out.println("grade:");
        System.out.println(grade);
        return grade;
    }

}
