package PhysicalPendulum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GrahpWindow extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Final Variables">
    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 400;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UI Variables">
    private javax.swing.JPanel buttonPanel;
    private GraphComponent graphComponent;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton clearButton;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Interface">
    public GrahpWindow() {
        initialize();
        customize();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setTitle("Physical Pendulum: graph [alpha(t)]");
        setResizable(false);

        //set LookAndFeel for this window:
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException ex) {
            java.util.logging.Logger.getLogger(GrahpWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }

    public void drawGraph() {
        graphComponent.redrawGraph();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Interface">
    @SuppressWarnings("unchecked")
    private void initialize() {

        buttonPanel = new javax.swing.JPanel();
        exitButton = new javax.swing.JButton();
        graphComponent = new GrahpWindow.GraphComponent();
        clearButton = new javax.swing.JButton();



        buttonPanel.setPreferredSize(new java.awt.Dimension(600, 40));
        exitButton.setText("Exit");
        clearButton.setText("Clear");

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
                buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(buttonPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(exitButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearButton)
                                .addContainerGap(428, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
                buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(exitButton)
                                        .addComponent(clearButton))
                                .addContainerGap())
        );
        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_END);


        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphComponent);
        graphComponent.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
                graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 600, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
                graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 360, Short.MAX_VALUE)
        );
        getContentPane().add(graphComponent, java.awt.BorderLayout.CENTER);

        pack();
    }

    private void customize() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphComponent.points.clear();
                graphComponent.counter = 0;
                drawGraph();
            }
        });
    }

    class GraphComponent extends JPanel {

        ArrayList<Point> points;
        int counter;

        int numberOfPoints = DEFAULT_WIDTH;
        int multipler = (int) (1 / 3.14 * 150);

        public GraphComponent() {
            super();

            points = new ArrayList<Point>(numberOfPoints);
            counter = 0;
            repaint();
        }

        public void redrawGraph() {

            //calculate next point and save it in ArrayList<Point>
            double timePoint = counter % numberOfPoints;
            double anglePoint = Main.getInstanceOfPhysicalPendulum().getAngle();
            Point point = new Point(timePoint, anglePoint);

            points.add(0, point);
            //if its more points remove last (now it is First In Last Out)
            if (points.size() >= numberOfPoints) {
                points.remove(points.size()-1);
            }

            counter++;
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {

            //background
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());

            //line X and Y
            g.setColor(Color.black);
            g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2); //X line
            g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight()); //Y line

            //axis descp
            g.drawString("alfa [rad]", (getWidth() / 2) + 20, 20 );
            g.drawString("t [s]", getWidth() - 30, (getHeight() / 2) - 20);

            //referece for painting points
            int refX = 0;
            int refY = getHeight() / 2;

            g.setColor(Color.gray);
            @SuppressWarnings("unchecked")
            ArrayList<Point> copyOfList = (ArrayList<Point>) points.clone();
            Iterator<Point> iterator = copyOfList.iterator();
            while (iterator.hasNext()) {
                try {
                    Point p = iterator.next();

                    int time = refX + (int) p.getX();
                    int angle = refY + (int) (p.getY() * multipler);

                    g.fillOval(time, angle, 3, 3);
                } catch (ConcurrentModificationException ex) {
                    System.out.println(ex.getStackTrace());
                    //Because of multithreading there may occur exception. We ignore them.
                    //They dont have influence on result of program.
                    //Update:
                    //They wont occur beacuse we are using copy of data
                }
            }
        }
    }
    // </editor-fold>
}
