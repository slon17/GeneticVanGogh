import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Gui1 {

    private JButton btnLoad;
    public JLabel imageShowMain;
    private JPanel panel;
    private JLabel imageShowTest;

    public Gui1() {
        btnLoad.addActionListener(new ActionListener() {
            @Override
            //button pressed
            public void actionPerformed(ActionEvent e) {
                //loadImageTest
                OriginalImg original = new OriginalImg();
                original.load();
                //newGeneticClass
                GeneticAlg geneticAlg =new GeneticAlg();
                geneticAlg.histogramOriginalImage=geneticAlg.generateHistogram(original.img);
                BufferedImage randImg = geneticAlg.generateRandImage(original.img);
                //comparing 2 histograms
                geneticAlg.grade(geneticAlg.generateHistogram(randImg));
                //displayTest

                //BufferedImage resized= resize(geneticAlg.generateRandImage(original.img),original.img.getHeight()*2,original.img.getWidth()*2);
                System.out.println("load_done");
                setImageShowMain(original.img,randImg);
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
        mainframe.setContentPane(new Gui1().panel);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(600,600);
        mainframe.setVisible(true);
        System.out.println("frame_build");
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
    public void setImageShowMain(BufferedImage img,BufferedImage resized)
    {
        imageShowMain.setIcon(new ImageIcon(img));
        imageShowTest.setIcon(new ImageIcon(resized));
        System.out.println("image_displayed");
    }
}
