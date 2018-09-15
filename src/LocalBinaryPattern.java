import java.awt.*;
import java.io.File;
import java.io.IOException;
import  java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
public class LocalBinaryPattern {
    protected static int[] histogramOriginalImage;

    public static void localBinaryPattern(ArrayList<Individual> population){
        //System.out.println("LocalBinaryPattern:");
        for(int i = 0;i<population.size();i++) {
            population.get(i).setFitness(grade(generateHistogram(population.get(i).getImage())));
            System.out.println(population.get(i).getFitness());
        }
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
            }
        }
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

    public static void setHistogramOriginalImage(int[] histogramOriginalImage) {
        LocalBinaryPattern.histogramOriginalImage = histogramOriginalImage;
    }

    public static float grade(int[] newImage)
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
        //System.out.println("grade:");
        //System.out.println(grade);
        return grade;
    }

}
