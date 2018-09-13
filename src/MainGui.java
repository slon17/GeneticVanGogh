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



        btnLoad.addActionListener(new ActionListener() {
            @Override
            //button pressed
            public void actionPerformed(ActionEvent e) {
                //loadImageTest
                original = new OriginalImg();
                original.load();
                Euclidean euclidean = new Euclidean();
                euclidean.setOriginal(original.img);
                //POBLACION DE PRUEBA
                ArrayList<Individual> populationTest= new ArrayList<Individual>();
                for (int i=0;i<20;i++){
                    Individual individual = new Individual();
                    individual.setImage(individual.generateRandImage(original.img));
                    populationTest.add(individual);
                }
                //CORRIDA CON EUCLIDEANO
                euclidean.euclidean(populationTest);
                setImageShowMain(original.img);
                //CORRIDA CON LBP
                LocalBinaryPattern localBinaryPattern =new LocalBinaryPattern();
                localBinaryPattern.setHistogramOriginalImage(localBinaryPattern.generateHistogram(original.img));
                localBinaryPattern.localBinaryPattern(populationTest);
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
}
