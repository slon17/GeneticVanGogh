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
        Population population = new Population();

        original = new OriginalImg();
        original.load();
        Individual ind = new Individual();
        ind.setImage(original.img);

        btnLoad.addActionListener(new ActionListener() {
            @Override
            //button pressed
            public void actionPerformed(ActionEvent e) {

                population.beginGenerationsEuclidean(original.getMainColors(), 5, true, false, 1000, 100);

                setImageShowTest(resize(population.getPopulation().get(population.getPopulation().size()-1).getImage(),100,100));
                setImageShowMain(resize(original.img,100,100));

            }
        });
        runLBPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                population.beginGenerationsLBP(original.getMainColors(), 5, true, false, 1000, 100);

                setImageShowTest(resize(population.getPopulation().get(population.getPopulation().size()-1).getImage(),100,100));
                setImageShowMain(resize(original.img,100,100));
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
