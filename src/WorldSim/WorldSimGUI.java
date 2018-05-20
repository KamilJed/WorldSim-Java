package WorldSim;

import WorldSim.World.HexWorld;
import WorldSim.World.SquareWorld;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class WorldSimGUI {

    private JPanel worldPanel;
    private JPanel worldGUI;
    private JButton newTurnButton;
    private JPanel worldView;
    private JLabel messagesOutput;
    private JButton setWorldSizeButton;
    private JSpinner xSizeSpinner;
    private JSpinner ySizeSpinner;
    private JButton saveButton;
    private JButton loadButton;
    private JComboBox organismChooser;
    private JRadioButton squareGridRadioButton;
    private JRadioButton hexGridRadioButton;
    private int messagesCount = 0;

    private WorldSimGUI() {

        newTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagesOutput.setText("");
                WorldView w = (WorldView) worldView;
                w.newTurn();
                messagesCount = 0;
            }
        });
        setWorldSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(squareGridRadioButton.isSelected()){
                    SquareWorld w = new SquareWorld((int)xSizeSpinner.getValue(), (int)ySizeSpinner.getValue(), (WorldView)worldView);
                    ((WorldView) worldView).setWorld(w);
                }
                else if(hexGridRadioButton.isSelected()){
                    HexWorld w = new HexWorld((WorldView)worldView);
                    ((WorldView) worldView).setWorld(w);
                }

                worldView.repaint();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    WorldView w = (WorldView) worldView;
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
                    WorldView w = (WorldView) worldView;
                    w.loadGame(selectedFile);
                }
            }
        });
    }

    private void createUIComponents() {
        worldView = new WorldView(this);
        SpinnerModel xSpinner = new SpinnerNumberModel(20, 2, 50, 1);
        SpinnerModel ySpinner = new SpinnerNumberModel(20, 2, 50, 1);
        xSizeSpinner =  new JSpinner(xSpinner);
        ySizeSpinner = new JSpinner(ySpinner);
    }

    private JPanel getWorldView() {
        return worldView;
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
        WorldView world = (WorldView) worldSimGUI.getWorldView();
        world.initWorldView();
        frame.setVisible(true);
        world.startGame();
    }
}
