import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainGui {

    private JButton btnLoad;
    public JLabel imageShowMain;
    private JPanel panel;
    private JLabel imageShowTest;
    private JButton runLBPButton;
    private OriginalImg original;


    public MainGui() {
        Population population = new Population(100);


        btnLoad.addActionListener(new ActionListener() {
            @Override
            //button pressed
            public void actionPerformed(ActionEvent e) {
                PinkSauce pink =new PinkSauce();
                pink.setDimension(5);

                //loadImageTest
                original = new OriginalImg();
                original.load();
                ArrayList<Color> colorsOriginal=original.getMainColors();
                //poblacion de prueba
                ArrayList<Individual> populationTest= new ArrayList<Individual>();
                for (int i=0;i<20;i++){
                    Individual individual = new Individual();
                    individual.setImage(individual.generateRandImage(original.img));
                    populationTest.add(individual);
                }
                pink.setqColorsOriginal(pink.qColors(original.img));
                pink.setOriginalColors(original.getMainColors());
                System.out.println("main colors " + original.getMainColors().size());
                pink.colorsToInt();
                pink.pinkSauce(populationTest);

                //population.generatePopulation(100);
                //Individual ind = new Individual();
                //ind.setImage(original.img);
                //population.getPopulation().add(ind);
                /*for(int j=0; j<100;j++) {
                    population.getPopulation().get(j).setFitness(j);
                    System.out.println("Individuo: "+population.getPopulation().get(j).getFitness());
                }*/
                //Euclidean euclidean = new Euclidean();
                //euclidean.setOriginal(original.img);
                //euclidean.euclidean(population.getPopulation());

                //LocalBinaryPattern LCB = new LocalBinaryPattern();
                //LCB.histogramOriginalImage = LCB.generateHistogram(original.img);
                //LCB.localBinaryPattern(population.getPopulation());

                //population.sort(population.getPopulation(),0,499);
                /*System.out.println("ordenados:");
                for(int i = 0; i<100; i++){
                    System.out.println(population.getPopulation().get(i).getFitness());
                }*/


                //for(int i = 0; i<2000; i++){
                  //  euclidean.euclidean(population.getPopulation());
                    //LCB.localBinaryPattern(population.getPopulation());
                    //population.reproduceAllByFour(original.getMainColors());
                //}

                //setImageShowTest(resize(population.getPopulation().get(population.getPopulation().size()-1).getImage(),100,100));
                //setImageShowMain(resize(original.img,100,100));

                //Individual ind = new Individual();
                /*ind.setImage(original.img);
                //population.getPopulation().add(ind);
                Individual ind2 = new Individual();
                //population.getPopulation().add(ind);
                ind2.setImage(ind2.generateRandImage(original.img));
                Individual ind3 = population.reproduceByFour(ind,ind2,original.getMainColors());

                setImageShowTest(resize(ind3.getImage(),100,100));
                setImageShowMain(resize(original.img,100,100));*/






                /*
                //POBLACION DE PRUEBA
                ArrayList<Individual> populationTest= new ArrayList<Individual>();
                for (int i=0;i<20;i++){
                    Individual individual = new Individual(Individual.generateRandImage(original.img));
                    //individual.setImage(individual.generateRandImage(original.img));
                    populationTest.add(individual);
                }
                //CORRIDA CON EUCLIDEANO
                euclidean.euclidean(populationTest);
                setImageShowMain(original.img);
                //CORRIDA CON LBP
                LocalBinaryPattern localBinaryPattern =new LocalBinaryPattern();
                localBinaryPattern.setHistogramOriginalImage(localBinaryPattern.generateHistogram(original.img));
                localBinaryPattern.localBinaryPattern(populationTest);

                //populationTest.get(0).getMainColors();

                for (int i=0;i<20;i++){

                }*/

            }
        });
        runLBPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalBinaryPattern lbp = new LocalBinaryPattern();
                //geneticAlg.histogramOriginalImage=geneticAlg.generateHistogram(original.img);
                //BufferedImage randImg = geneticAlg.generateRandImage(original.img);
                //comparing 2 histograms
                //geneticAlg.grade(geneticAlg.generateHistogram(randImg));
                //displayTest

                //BufferedImage resized= resize(geneticAlg.generateRandImage(original.img),original.img.getHeight()*2,original.img.getWidth()*2);
            }
        });
    }
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    public static void main(String[] args){
        JFrame mainframe = new JFrame("start");
        mainframe.setContentPane(new MainGui().panel);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(600,600);
        mainframe.setVisible(true);
        System.out.println("frame_build");
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
    public void setImageShowMain(BufferedImage img)
    {
        imageShowMain.setIcon(new ImageIcon(img));
        System.out.println("image_displayed");
    }

    public void setImageShowTest(BufferedImage img)
    {
        imageShowTest.setIcon(new ImageIcon(img));
        System.out.println("image_displayed");
    }
}
