import java.awt.*;
import java.io.File;
import java.io.IOException;
import  java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
public class PinkSauce {
    private ArrayList<Color> originalColors;
    private ArrayList<Integer> coloresOriginalesEnteros;
    private int qColorsOriginal;
    private int dimension;
    float qColoresIguales;


    public void setOriginalColors(ArrayList<Color> poriginalColors) {
        //System.out.println("largo "+poriginalColors.size());
        this.originalColors = poriginalColors;
    }



    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setqColorsOriginal(int qColorsOriginal) {
        this.qColorsOriginal = qColorsOriginal;
    }

    public void pinkSauce(ArrayList<Individual> population){
        for (int i=0;i<population.size();i++){
            population.get(i).setFitness(finalGrade(qColors(population.get(i).getImage()),verifyNeighbors(population.get(i).getImage(),generateCoordinates()),0,0,100));
            System.out.println(population.get(i).getFitness());
        }
    }
    public void colorsToInt(){
        coloresOriginalesEnteros=new ArrayList<>();
       // System.out.println("colores Original:" + this.originalColors.size());
        for(int i=0;i<originalColors.size();i++){
            coloresOriginalesEnteros.add(originalColors.get(i).getRed());
            //System.out.println(originalColors.get(i).getRed());
        }
    }

    public ArrayList<int[][]> generateCoordinates(){
        ArrayList<int[][]> array = new ArrayList<int[][]>();

        int inicio=0-(dimension/2);

        for(int i=0; i<dimension;i++){
            int [][] fila = new int[dimension][2];
            for (int j=0; j<dimension;j++){
                int [] par=new int[2];
                par[1]=inicio+i;
                par[0]=inicio+j;
                fila[j]=par;
            }
            array.add(fila);
        }
        /*for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                System.out.println("Coordenadas:"+array.get(i)[j][0]+" "+array.get(i)[j][1]);
            }
        }*/
        return array;
    }
    public float verifyNeighbors(BufferedImage image,ArrayList<int[][]> coordinates){   //7*7*n^2 = O(n^2)
        int gruposGrandesSimilaresAOriginal = 0;
        float suma = 0;
        ArrayList<Integer> bigGroups = new ArrayList<>();
        for(int x = 0; x<image.getWidth(); x++){                                        //(m+1)*c = 7+1*c (tamano de matriz usada)
            for(int y = 0; y<image.getHeight();y++){                                    //(m+1)*c = 7+1*c (tamano de matriz usada)
                Color actual = new Color(image.getRGB(x,y));                            //1+1 = c (asignacion + indexacion)
                float cuenta = 0;                                                       //1+1 = c (asignacion + indexacion)
                int validos=0;                                                          //1+1 = c (asignacion + indexacion)
                for(int i = 0;i<coordinates.size();i++){                                //(n+1)*c (n = tamano de la imagen)
                    for(int j = 0; j<coordinates.size();j++){                           //(n+1)*c (n = tamano de la imagen) lstlisting
                        int a = x+coordinates.get(i)[j][0];                             //1+1+1 = c (asignacion + indexacion + suma)
                        int b = y+coordinates.get(i)[j][1];                             //1+1+1 = c (asignacion + indexacion + suma)
                        if(a>=0 && b>=0 && a<image.getWidth() && b <image.getHeight()){ //1+1+1+1 = c (comparacion*4)

                            Color neighbor = new Color(image.getRGB(a,b));              //1+1+ = c (asignacion + indexacion)
                            if(!(a==x && b==y)){                                        //1+1 = c (comparacion)
                                validos+=1;                                             //1+1 = c (asignacion + suma)
                                if(neighbor.getRed()==actual.getRed()){                 //1 = c (comparacion)
                                    cuenta+=1;                                          //1+1 = c (asignacion + suma)
                                }
                            }
                        }
                    }
                }


                if(((float)100/validos)*cuenta<=30){
                    if(coloresOriginalesEnteros.contains(actual.getRed())){
                        gruposGrandesSimilaresAOriginal+=1;
                    }
                }

                float porcentaje=((float)100/validos)*cuenta;
                //System.out.println("porcentaje: "+ porcentaje);
                suma+=porcentaje;
            }
        }
        //System.out.println((float)suma/(32*32));
        qColoresIguales = ((float)100/(32*32)) * gruposGrandesSimilaresAOriginal;
        gruposGrandesSimilaresAOriginal=0;
        //System.out.println("Colores Iguales:"+qColoresIguales);
        float grade = (((float)suma/(32*32))*((float)2/100));
        return grade;
    }
    public int qColors(BufferedImage img){
        int[] colors = new int[256];
        int qColors=0;
        for (int i=0;i<img.getWidth();i++){
            for (int j=0;j<img.getHeight();j++){
                int color = (new Color(img.getRGB(i,j))).getRed();
                colors[color]+=1;
            }
        }
        for(int i=0;i<256;i++){
            if(colors[i]!=0){
                qColors+=1;
            }
        }
        //System.out.println(qColors);
        return  qColors;
    }
    public float finalGrade(float colors, float groupedColors, float colorImportance, float groupedImportance, float equalColorImportance){
        float colorGrade=((float)qColorsOriginal/100)*colors;
        float total = (qColoresIguales);
        //System.out.println(total);
        return total;
    }
}
