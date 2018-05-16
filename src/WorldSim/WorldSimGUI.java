package WorldSim;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class WorldSimGUI {

    private JPanel worldPanel;
    private JPanel worldGUI;
    private JButton newTurnButton;
    private JPanel world;
    private JLabel messagesOutput;
    private JButton setWorldSizeButton;
    private JSpinner xSizeSpinner;
    private JSpinner ySizeSpinner;
    private JButton saveButton;
    private JButton loadButton;
    private JComboBox organismChooser;
    private int messagesCount = 0;


    private WorldSimGUI() {
        newTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagesOutput.setText("");
                World w = (World)world;
                w.newTurn();
                messagesCount = 0;
            }
        });
        setWorldSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                World w = (World)world;
                w.initWorld((int)xSizeSpinner.getValue(), (int)ySizeSpinner.getValue());
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    World w = (World)world;
                    w.saveGame(selectedFile);
                }
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    World w = (World)world;
                    w.loadGame(selectedFile);
                }
            }
        });
    }

    private void createUIComponents() {
        world = new World(this);
        SpinnerModel xSpinner = new SpinnerNumberModel(20, 2, 50, 1);
        SpinnerModel ySpinner = new SpinnerNumberModel(20, 2, 50, 1);
        xSizeSpinner =  new JSpinner(xSpinner);
        ySizeSpinner = new JSpinner(ySpinner);
    }

    private JPanel getWorld() {
        return world;
    }

    public void setMessagesOutput(String message) {
        if(messagesCount < 4){
            messagesCount++;
            String labelText = messagesOutput.getText();
            labelText = labelText.replaceAll("<html>","");
            labelText = labelText.replaceAll("</html>","");
            messagesOutput.setText("<html>"+labelText+message+"</html>");
        }
    }

    public String getChosenOrganism(){
        return (String) organismChooser.getSelectedItem();
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("World Sim Kamil Jedrzejczak 171660");
        WorldSimGUI worldSimGUI = new WorldSimGUI();
        frame.setContentPane(worldSimGUI.worldGUI);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        World world = (World) worldSimGUI.getWorld();
        world.initWorld();
        frame.setVisible(true);
        world.startGame();
    }
}
