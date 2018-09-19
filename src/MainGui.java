import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainGui {

    private JButton btnLoad;
    public JLabel imageShow0;
    private JPanel panel;
    private JLabel imageShow9;
    private JButton runLBPButton;
    private JLabel imageShow1;
    private JLabel imageShow2;
    private JLabel imageShow3;
    private JLabel imageShow5;
    private JLabel imageShow6;
    private JLabel imageShow7;
    private JLabel imageShow8;
    private JButton btnPink;
    private JLabel imageShow4;
    private JLabel lblImage5;
    private JLabel lblImage9;
    private JLabel lblImage6;
    private JLabel lblImage7;
    private JLabel lblImage8;
    private JLabel imageShowOriginal;
    private JLabel lblImage0;
    private JLabel lblImage1;
    private JLabel lblImage2;
    private JLabel lblImage3;
    private JLabel lblImage4;
    private JButton btnReset;
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

                setImageShowOriginal(resize(original.img, 100, 100));
                population.beginGenerationsEuclidean(original.getMainColors(), 5, true, false, 1200, 100);

                fillLabels(population.getProcessIndividuals());

            }
        });
        runLBPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setImageShowOriginal(resize(original.img, 100, 100));
                population.beginGenerationsLBP(original.getMainColors(), 5, true, false, 80, 250);

                fillLabels(population.getProcessIndividuals());
            }
        });
        btnPink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setImageShowOriginal(resize(original.img, 100, 100));
                population.beginGenerationsPink(original.getMainColors(), 5, false, false, 80, 250, 7);

                fillLabels(population.getProcessIndividuals());
            }
        });
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                population.resetPopulation();
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
        JFrame mainframe = new JFrame("Genetic Van Gogh");
        mainframe.setContentPane(new MainGui().panel);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(600,700);
        mainframe.setVisible(true);
        //System.out.println("frame_build");
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
    public void setImageShow0(BufferedImage img)
    {
        imageShow0.setIcon(new ImageIcon(img));
        //System.out.println("image_displayed");
    }

    public void setImageShow1(BufferedImage img)
    {
        imageShow1.setIcon(new ImageIcon(img));
    }

    public void setImageShow2(BufferedImage img)
    {
        imageShow2.setIcon(new ImageIcon(img));
    }

    public void setImageShow3(BufferedImage img)
    {
        imageShow3.setIcon(new ImageIcon(img));
    }

    public void setImageShow4(BufferedImage img)
    {
        imageShow4.setIcon(new ImageIcon(img));
    }

    public void setImageShow5(BufferedImage img)
    {
        imageShow5.setIcon(new ImageIcon(img));
    }

    public void setImageShow6(BufferedImage img)
    {
        imageShow6.setIcon(new ImageIcon(img));
    }

    public void setImageShow7(BufferedImage img)
    {
        imageShow7.setIcon(new ImageIcon(img));
    }

    public void setImageShow8(BufferedImage img)
    {
        imageShow8.setIcon(new ImageIcon(img));
    }

    public void setImageShow9(BufferedImage img)
    {
        imageShow9.setIcon(new ImageIcon(img));
        //System.out.println("image_displayed");
    }

    public void setImageShowOriginal(BufferedImage img)
    {
        imageShowOriginal.setIcon(new ImageIcon(img));
        //System.out.println("image_displayed");
    }


    public void fillLabels(ArrayList<Individual> processIndividuals){
        setImageShow9(resize(processIndividuals.get(processIndividuals.size()-1).getImage(), 100, 100));
        lblImage9.setText(Float.toString(processIndividuals.get(processIndividuals.size()-1).getFitness()));
        setImageShow8(resize(processIndividuals.get(processIndividuals.size()-2).getImage(), 100, 100));
        lblImage8.setText(Float.toString(processIndividuals.get(processIndividuals.size()-2).getFitness()));
        setImageShow7(resize(processIndividuals.get(processIndividuals.size()-3).getImage(), 100, 100));
        lblImage7.setText(Float.toString(processIndividuals.get(processIndividuals.size()-3).getFitness()));
        setImageShow6(resize(processIndividuals.get(processIndividuals.size()-4).getImage(), 100, 100));
        lblImage6.setText(Float.toString(processIndividuals.get(processIndividuals.size()-4).getFitness()));
        setImageShow5(resize(processIndividuals.get(processIndividuals.size()-5).getImage(), 100, 100));
        lblImage5.setText(Float.toString(processIndividuals.get(processIndividuals.size()-5).getFitness()));
        setImageShow4(resize(processIndividuals.get(processIndividuals.size()-6).getImage(), 100, 100));
        lblImage4.setText(Float.toString(processIndividuals.get(processIndividuals.size()-6).getFitness()));
        setImageShow3(resize(processIndividuals.get(processIndividuals.size()-7).getImage(), 100, 100));
        lblImage3.setText(Float.toString(processIndividuals.get(processIndividuals.size()-7).getFitness()));
        setImageShow2(resize(processIndividuals.get(processIndividuals.size()-8).getImage(), 100, 100));
        lblImage2.setText(Float.toString(processIndividuals.get(processIndividuals.size()-8).getFitness()));
        setImageShow1(resize(processIndividuals.get(processIndividuals.size()-9).getImage(), 100, 100));
        lblImage1.setText(Float.toString(processIndividuals.get(processIndividuals.size()-9).getFitness()));
        setImageShow0(resize(processIndividuals.get(0).getImage(), 100,100));
        lblImage0.setText(Float.toString(processIndividuals.get(0).getFitness()));
    }
}
