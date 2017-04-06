package PhysicalPendulum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Final Variables">
    public static final int DEFAULT_WIDTH = 1200;
    public static final int DEFAULT_HEIGHT = 900;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Simulation Variables">
    private static long lastTime;
    private static boolean simulationRunning = false;
    private static PhysicalPendulum instanceOfPhysicalPendulum = new PhysicalPendulum(9.8f, 0.8f, 10);
    private Thread simulationThread = null;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UI Variables">
    //UI
    private GrahpWindow graphWindow; //graph window
    private SimulationComponent simulationPanel;
    private javax.swing.JLabel accelerationLabel;
    private javax.swing.JTextField accelerationTextField;
    private javax.swing.JLabel angleLabel;
    private javax.swing.JTextField angleTextField;
    private javax.swing.JLabel distanceLabel;
    private javax.swing.JTextField distanceTextField;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton graphButton;
    private javax.swing.JTextArea infoTextArea;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel interiaMomentLabel;
    private javax.swing.JTextField interiaMomentTextField;
    private javax.swing.JLabel massLabel;
    private javax.swing.JTextField massTextField;
    private javax.swing.JPanel propertiesPanel;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JButton infoButton;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Interface">
    public static void main(String... args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mainFrame = new Main();
                mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                mainFrame.setTitle("Physical Pendulum");
                mainFrame.setVisible(true);
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Main() {
        initialize();
        customize();
        infoTextArea.setText("Text");
        pack();

        setGraphWindowLocation();
    }

    public static boolean isSimulationRunning() {
        return simulationRunning;
    }

    public static PhysicalPendulum getInstanceOfPhysicalPendulum() {
        return instanceOfPhysicalPendulum;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Interface">
    private void initialize() {
        //initliazie grahpWindow:
        graphWindow = new GrahpWindow();
        graphWindow.setVisible(true);


        //initialize
        infoPanel = new javax.swing.JPanel();
        infoTextArea = new javax.swing.JTextArea();
        propertiesPanel = new javax.swing.JPanel();
        accelerationLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();
        interiaMomentLabel = new javax.swing.JLabel();
        massLabel = new javax.swing.JLabel();
        distanceLabel = new javax.swing.JLabel();
        accelerationTextField = new javax.swing.JTextField();
        angleTextField = new javax.swing.JTextField();
        interiaMomentTextField = new javax.swing.JTextField();
        massTextField = new javax.swing.JTextField();
        distanceTextField = new javax.swing.JTextField();
        exitButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        infoButton = new javax.swing.JButton();
        graphButton = new javax.swing.JButton();

        simulationPanel = new SimulationComponent();

        infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Informations:"));
        infoTextArea.setText("info");
        infoTextArea.setColumns(50);
        infoTextArea.setRows(7);
        infoTextArea.setBackground(this.getBackground());
        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
                infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(infoPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(infoTextArea)
                                .addContainerGap(760, Short.MAX_VALUE))
        );
        infoPanelLayout.setVerticalGroup(
                infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(infoPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(infoTextArea)
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(infoPanel, java.awt.BorderLayout.PAGE_END);
        propertiesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));
        propertiesPanel.setPreferredSize(new java.awt.Dimension(250, 600));
        accelerationLabel.setText("Acceleration:");
        angleLabel.setText("Angle (in radiands): ");
        interiaMomentLabel.setText("Moment of inertia:");
        massLabel.setText("Mass:");
        distanceLabel.setText("Distance to the center of gravity:");
        accelerationTextField.setText("9.8");
        angleTextField.setText("0.79");
        interiaMomentTextField.setText("20");
        massTextField.setText("1");
        distanceTextField.setText("2");
        exitButton.setText("Exit");
        stopButton.setText("Stop");
        startButton.setText("Start");
        infoButton.setText("Info");
        graphButton.setText("Open/Close graph");

        javax.swing.GroupLayout propertiesPanelLayout = new javax.swing.GroupLayout(propertiesPanel);
        propertiesPanel.setLayout(propertiesPanelLayout);
        propertiesPanelLayout.setHorizontalGroup(
                propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(propertiesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(massTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(interiaMomentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(angleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(accelerationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(accelerationLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(angleLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(interiaMomentLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(massLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(distanceLabel, javax.swing.GroupLayout.Alignment.LEADING)))))
                                        .addGroup(propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(distanceTextField)
                                                .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(infoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(graphButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(stopButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))
                                .addContainerGap(17, Short.MAX_VALUE))
        );
        propertiesPanelLayout.setVerticalGroup(
                propertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(propertiesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(accelerationLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(accelerationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(angleLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(angleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(interiaMomentLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(interiaMomentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(massLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(massTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(distanceLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(distanceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                .addComponent(startButton)
                                .addGap(18, 18, 18)
                                .addComponent(stopButton)
                                .addGap(18, 18, 18)
                                .addComponent(graphButton)
                                .addGap(18, 18, 18)
                                .addComponent(infoButton)
                                .addGap(18, 18, 18)
                                .addComponent(exitButton)
                                .addContainerGap())
        );
        getContentPane().add(propertiesPanel, java.awt.BorderLayout.LINE_END);

        simulationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Simulation"));
        javax.swing.GroupLayout drawPanelLayout = new javax.swing.GroupLayout(simulationPanel);
        simulationPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
                drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 588, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
                drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 454, Short.MAX_VALUE)
        );
        getContentPane().add(simulationPanel, java.awt.BorderLayout.CENTER);

    }

    private void customize() {

        //startButton:
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!simulationRunning) {
                    try {
                        instanceOfPhysicalPendulum.reset(getDataFromUser());
                        simulationRunning = true;
                        lastTime = System.currentTimeMillis();
                        simulationThread = new Thread(new SimulationRunnable());
                        simulationThread.start();
                    } catch (NumberFormatException ignored) {
                        infoTextArea.setText("Wrong data!");
                    }
                }

            }
        });

        //stopButton:
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (simulationRunning) {
                    simulationThread.interrupt();
                    //infoTextArea.setText("Simulation stopped.");
                    infoTextArea.setText(infoTextArea.getText() + "\nSimulation stopped");
                    simulationRunning = false;
                }
            }
        });

        //open/close graph button:
        graphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (graphWindow.isVisible()) {
                    graphWindow.setVisible(false);
                }
                else {
                    setGraphWindowLocation();
                    graphWindow.setVisible(true);
                }
            }
        });

        //infoButton:
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!simulationRunning) {
                    infoTextArea.setText("Text");
                }
            }
        });

        //exitButton:
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void setGraphWindowLocation() {
        graphWindow.setLocation(this.getBounds().width - 5, 0);
    }

    private double[] getDataFromUser() throws NumberFormatException {
        String acceleration = accelerationTextField.getText();
        String angle = angleTextField.getText();
        String interia = interiaMomentTextField.getText();
        String mass = massTextField.getText();
        String distance = distanceTextField.getText();

        double acc = Double.parseDouble(acceleration);
        double ang = Double.parseDouble(angle);
        double in = Double.parseDouble(interia);
        double m = Double.parseDouble(mass);
        double dis = Double.parseDouble(distance);

        return new double[]{acc, ang, in, m, dis};
    }

    class SimulationComponent extends JPanel {

        double scaleRate = 10;

        public SimulationComponent() {
            super();
            infoTextArea.setText(instanceOfPhysicalPendulum.toString());
            repaint();
        }

        public void move() {
            double deltaTime = System.currentTimeMillis() - lastTime;
            deltaTime /= 1000; //change into s
            instanceOfPhysicalPendulum.update(deltaTime);
            infoTextArea.setText(instanceOfPhysicalPendulum.toString());
            repaint();
            lastTime = System.currentTimeMillis();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight()); //fill background

            g.setColor(Color.BLACK);
            int refX = getWidth() / 2;
            int refY = getHeight() / 3;

            int objX = refX + (int) (scaleRate * instanceOfPhysicalPendulum.getPosition().getX());
            int objY = refY + (int) (scaleRate * instanceOfPhysicalPendulum.getPosition().getY());

            g.drawLine(refX, refY, objX, objY); //line from centre
            g.fillRect(refX - 5, refY - 5, 10, 10);
            g.fillOval(objX - 10, objY - 10, 20, 20);
        }
    }

    class SimulationRunnable implements Runnable {

        @Override
        public void run() {
            try {
                while (simulationRunning) {
                    simulationPanel.move();
                    graphWindow.drawGraph();
                    Thread.sleep(15);
                }
            } catch (InterruptedException ignored) {

            }
        }
    }
    // </editor-fold>
}