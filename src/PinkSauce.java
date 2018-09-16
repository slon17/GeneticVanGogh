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
            population.get(i).setFitness(finalGrade(qColors(population.get(i).getImage()),verifyNeighbors(population.get(i).getImage(),generateCoordinates()),10,50,40));
            //System.out.println(population.get(i).getFitness());
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
    public float verifyNeighbors(BufferedImage image,ArrayList<int[][]> coordinates){
        int gruposGrandesSimilaresAOriginal = 0;
        float suma = 0;
        ArrayList<Integer> bigGroups = new ArrayList<>();
        for(int x = 0; x<image.getWidth(); x++){
            for(int y = 0; y<image.getHeight();y++){
                Color actual = new Color(image.getRGB(x,y));
                float cuenta = 0;
                int validos=0;
                for(int i = 0;i<coordinates.size();i++){
                    for(int j = 0; j<coordinates.size();j++){
                        int a = x+coordinates.get(i)[j][0];
                        int b = y+coordinates.get(i)[j][1];
                        if(a>=0 && b>=0 && a<image.getWidth() && b <image.getHeight()){

                            Color neighbor = new Color(image.getRGB(a,b));
                            if(!(a==x && b==y)){
                                validos+=1;
                                if(neighbor.getRed()==actual.getRed()){
                                    cuenta+=1;
                                }
                            }
                        }
                    }
                }


                if(cuenta>=((float)validos/100)*10){
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
        qColoresIguales = ((float)coloresOriginalesEnteros.size()/100)*gruposGrandesSimilaresAOriginal;
        System.out.println("Colores Iguales:"+qColoresIguales);
        float grade = (((float)suma/(32*32))*((float)2/100));
        return grade;
    }
    public int qColors(BufferedImage img){
        int[] colors = new int[256];
        int qColors=0;
        for (int i=0;i<img.getWidth();i++){
            for (int j=0;j<img.getWidth();j++){
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
        float colorGrade=((float)100/qColorsOriginal)*colors;
        float total = (colors*(colorImportance/100))+(groupedColors*(groupedImportance/100))+(qColoresIguales*(equalColorImportance/100));
        System.out.println(total);
        return total;
    }
}
