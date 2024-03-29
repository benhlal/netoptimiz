/*
 * NetOptimizView.java
 */
package netoptimiz;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import ilog.concert.IloException;
import java.awt.Dimension;
import java.awt.GridLayout;
import netoptimiz.graphe.*;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import netoptimiz.programmelineaire.ProgrammeLineaire;
import netoptimiz.recuit.TelecomRecuit;
import netoptimiz.vns.TelecomVNS;

/**
 * The application's main frame.
 */
public class NetOptimizView extends FrameView {

    public NetOptimizView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    //this.LoadData();
    }

  public void refreshPL(String s) {
    jTextArea2.append(s);
  }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = NetOptimizApp.getApplication().getMainFrame();
            aboutBox = new NetOptimizAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        NetOptimizApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jSpinner4 = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        PLTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        graphPanel1 = new javax.swing.JPanel();
        RecuitTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        graphPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        buttonGroup1 = new javax.swing.ButtonGroup();

        mainPanel.setBorder(new javax.swing.border.MatteBorder(null));
        mainPanel.setMaximumSize(new java.awt.Dimension(912, 689));
        mainPanel.setMinimumSize(new java.awt.Dimension(912, 689));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setRequestFocusEnabled(false);
        mainPanel.setVerifyInputWhenFocusTarget(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(netoptimiz.NetOptimizApp.class).getContext().getActionMap(NetOptimizView.class, this);
        jButton2.setAction(actionMap.get("LoadData")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(netoptimiz.NetOptimizApp.class).getContext().getResourceMap(NetOptimizView.class);
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextField1.setBackground(resourceMap.getColor("jTextField1.background")); // NOI18N
        jTextField1.setEditable(false);
        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setToolTipText(resourceMap.getString("jPanel1.toolTipText")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextField2.setEditable(false);
        jTextField2.setText(resourceMap.getString("jTextField2.text")); // NOI18N
        jTextField2.setName("jTextField2"); // NOI18N
        jTextField2.setPreferredSize(new java.awt.Dimension(60, 20));

        jTextField3.setEditable(false);
        jTextField3.setText(resourceMap.getString("jTextField3.text")); // NOI18N
        jTextField3.setName("jTextField3"); // NOI18N
        jTextField3.setPreferredSize(new java.awt.Dimension(60, 20));

        jTextField4.setEditable(false);
        jTextField4.setText(resourceMap.getString("jTextField4.text")); // NOI18N
        jTextField4.setName("jTextField4"); // NOI18N
        jTextField4.setPreferredSize(new java.awt.Dimension(60, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jButton4.setAction(actionMap.get("solve")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(resourceMap.getString("jRadioButton1.text")); // NOI18N
        jRadioButton1.setActionCommand(resourceMap.getString("jRadioButton1.actionCommand")); // NOI18N
        jRadioButton1.setName("jRadioButton1"); // NOI18N

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText(resourceMap.getString("jRadioButton2.text")); // NOI18N
        jRadioButton2.setActionCommand(resourceMap.getString("jRadioButton2.actionCommand")); // NOI18N
        jRadioButton2.setName("jRadioButton2"); // NOI18N

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText(resourceMap.getString("jRadioButton3.text")); // NOI18N
        jRadioButton3.setActionCommand(resourceMap.getString("jRadioButton3.actionCommand")); // NOI18N
        jRadioButton3.setName("jRadioButton3"); // NOI18N

        jTextField8.setEditable(false);
        jTextField8.setText(resourceMap.getString("jTextField8.text")); // NOI18N
        jTextField8.setName("jTextField8"); // NOI18N
        jTextField8.setPreferredSize(new java.awt.Dimension(60, 20));

        jTextField9.setEditable(false);
        jTextField9.setText(resourceMap.getString("jTextField9.text")); // NOI18N
        jTextField9.setName("jTextField9"); // NOI18N
        jTextField9.setPreferredSize(new java.awt.Dimension(60, 20));

        jTextField10.setEditable(false);
        jTextField10.setText(resourceMap.getString("jTextField10.text")); // NOI18N
        jTextField10.setName("jTextField10"); // NOI18N
        jTextField10.setPreferredSize(new java.awt.Dimension(60, 20));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3)))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(50), null, null, Integer.valueOf(1)));
        jSpinner1.setName("jSpinner1"); // NOI18N

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel());
        jSpinner2.setName("jSpinner2"); // NOI18N

        jCheckBox1.setText(resourceMap.getString("jCheckBox1.text")); // NOI18N
        jCheckBox1.setName("jCheckBox1"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBox1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jCheckBox1))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jSpinner3.setModel(new javax.swing.SpinnerNumberModel());
        jSpinner3.setName("jSpinner3"); // NOI18N

        jSpinner4.setModel(new javax.swing.SpinnerNumberModel());
        jSpinner4.setName("jSpinner4"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("jPanel5.border.title"))); // NOI18N
        jPanel5.setToolTipText(resourceMap.getString("jPanel5.toolTipText")); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jTextField5.setEditable(false);
        jTextField5.setText(resourceMap.getString("jTextField5.text")); // NOI18N
        jTextField5.setName("jTextField5"); // NOI18N
        jTextField5.setPreferredSize(new java.awt.Dimension(60, 20));

        jTextField6.setEditable(false);
        jTextField6.setText(resourceMap.getString("jTextField6.text")); // NOI18N
        jTextField6.setName("jTextField6"); // NOI18N
        jTextField6.setPreferredSize(new java.awt.Dimension(60, 20));

        jTextField7.setEditable(false);
        jTextField7.setText(resourceMap.getString("jTextField7.text")); // NOI18N
        jTextField7.setName("jTextField7"); // NOI18N
        jTextField7.setPreferredSize(new java.awt.Dimension(60, 20));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        PLTabbedPane.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("PLTabbedPane.border.title"))); // NOI18N
        PLTabbedPane.setName("PLTabbedPane"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setName("jTextArea2"); // NOI18N
        jScrollPane2.setViewportView(jTextArea2);

        PLTabbedPane.addTab(resourceMap.getString("jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N

        graphPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        graphPanel1.setName("graphPanel1"); // NOI18N

        javax.swing.GroupLayout graphPanel1Layout = new javax.swing.GroupLayout(graphPanel1);
        graphPanel1.setLayout(graphPanel1Layout);
        graphPanel1Layout.setHorizontalGroup(
            graphPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );
        graphPanel1Layout.setVerticalGroup(
            graphPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
        );

        PLTabbedPane.addTab(resourceMap.getString("graphPanel1.TabConstraints.tabTitle"), graphPanel1); // NOI18N

        RecuitTabbedPane.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("RecuitTabbedPane.border.title"))); // NOI18N
        RecuitTabbedPane.setName("RecuitTabbedPane"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);
        jTextArea1.getAccessibleContext().setAccessibleParent(mainPanel);

        RecuitTabbedPane.addTab(resourceMap.getString("jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        graphPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        graphPanel.setName("graphPanel"); // NOI18N

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
        );

        RecuitTabbedPane.addTab(resourceMap.getString("graphPanel.TabConstraints.tabTitle"), graphPanel); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(RecuitTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PLTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(PLTabbedPane, 0, 0, Short.MAX_VALUE)
                    .addComponent(RecuitTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        PLTabbedPane.getAccessibleContext().setAccessibleName(resourceMap.getString("PLTabbedPane.AccessibleContext.accessibleName")); // NOI18N

        mainPanel.getAccessibleContext().setAccessibleName(resourceMap.getString("mainPanel.AccessibleContext.accessibleName")); // NOI18N

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 921, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 751, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void LoadData() {

        // création d'une boîte de dialogue de sélection de fichier
        JFileChooser jfc1 = new JFileChooser("../Jeu de tests/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Fichiers de Noeuds, d'Arcs ou de Demandes", "nod", "arc", "dem");
        jfc1.setFileFilter(filter);
        this.getFrame().add(jfc1);
        jfc1.showOpenDialog(null);
        if (jfc1.getSelectedFile() != null) {
            File sourcefile = jfc1.getSelectedFile();
            //File sourcefile = new File("d:\\Documents and Settings\\T0031814\\Bureau\\jeu-de-test\\Net_8.arc");
            this.getFrame().remove(jfc1);

            String sourcename = sourcefile.getName().substring(0, sourcefile.getName().lastIndexOf("."));
            String sourcepath = sourcefile.getParent() + "\\" + sourcename;

            File nodefile = new File(sourcepath + ".nod");
            File arcfile = new File(sourcepath + ".arc");
            File requestfile = new File(sourcepath + ".dem");

            // verification de présence et d'accessibilité en lecture
            // des 3 fichiers de données
            if (!nodefile.canRead()) {
                JOptionPane.showMessageDialog(null, "Le fichier de noeuds " + sourcepath + ".nod" +
                        " n'existe pas ou ne peut être lu.\n" +
                        "Le chargement des données a été abdandonné.",
                        "Chargement de données", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (!arcfile.canRead()) {
                JOptionPane.showMessageDialog(null, "Le fichier d'arcs " + sourcepath + ".arc" +
                        " n'existe pas ou ne peut être lu.\n" +
                        "Le chargement des données a été abdandonné.",
                        "Chargement de données", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (!requestfile.canRead()) {
                JOptionPane.showMessageDialog(null, "Le fichier de demandes " + sourcepath + ".dem" +
                        " n'existe pas ou ne peut être lu.\n" +
                        "Le chargement des données a été abdandonné.",
                        "Chargement de données", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Les fichiers sont lus une 1ère fois pour vérification d'intégrité
            // Si tout s'est bien passé, ils sont lus une 2ème fois pour instancier le graphe

            /*il reste à vérifier :
            - les doublons sur les arcs et les demandes
            - (la capacité identique sur tous les arcs)
            - le rapport entre le nbre de noeuds déclarés et le nbre d'arcs / de demandes
            - la faisabilité des demandes ( < capacité )
            - la cohérence du graphe instancié*/

            BufferedReader br = null;
            try {
                String line = null;
                String[] elem = null;
                Integer line_number = 1;
                ArrayList<String> node_names = new ArrayList<String>(); //récupère les noms des noeuds à déclarer
                // à la 1ère lecture du fichier de noeuds
                boolean declarable_node = false;
                double test_double_parse;
                String warning_message = "";

                //
                // vérification d'intégrité des fichiers de données
                //
                // 1. noeuds
                br = new BufferedReader(new FileReader(nodefile));
                br.readLine();
                while ((line = br.readLine()) != null) {
                    line_number++;
                    elem = line.split("\t");
                    // nombre de paramètres récupérés sur chaque ligne
                    if (elem.length != 3) {
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier de noeuds " +
                                sourcepath + ".nod :\n" +
                                "* ligne " + line_number + " : nombre d'arguments incorrect.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // vérification du format des champs de type double
                    try {
                        // abscisse
                        test_double_parse = Double.parseDouble(elem[1]);
                        // ordonnée
                        test_double_parse = Double.parseDouble(elem[2]);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier de noeuds " +
                                sourcepath + ".nod :\n" +
                                "* ligne " + line_number + " : erreur de conversion en type 'double'.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // vérification de redondance de déclaration
                    if (node_names.contains(elem[0])) {
                        warning_message += "\n* le noeud " + elem[0] + " est déclaré plus d'une fois";
                    }
                    // ajout du nom du noeud dans la site des noeuds à déclarer
                    node_names.add(elem[0]);
                }
                br.close();

                // 2. vérification d'intégrité du fichier d'arcs
                br = new BufferedReader(new FileReader(arcfile));
                br.readLine();
                line_number = 1;
                while ((line = br.readLine()) != null) {
                    line_number++;
                    elem = line.split("\t");
                    // nombre de paramètres récupérés sur chaque ligne
                    if (elem.length != 4) {
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier d'arcs " +
                                sourcepath + ".arc :\n" +
                                "* ligne " + line_number + " : nombre d'arguments incorrect.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // existence des noeuds dans le fichier de noeuds
                    // noeud origine
                    declarable_node = false;
                    for (String name : node_names) {
                        if (elem[0].equals(name)) {
                            declarable_node = true;
                        }
                    }
                    if (!declarable_node) {
                        node_names.clear();
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier d'arcs " +
                                sourcepath + ".arc :\n" +
                                "* ligne " + line_number + " : le noeud " + elem[0] + " n'existe pas dans le fichier de noeuds.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // noeud extremité
                    declarable_node = false;
                    for (String name : node_names) {
                        if (elem[1].equals(name)) {
                            declarable_node = true;
                        }
                    }
                    if (!declarable_node) {
                        node_names.clear();
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier d'arcs " +
                                sourcepath + ".arc :\n" +
                                "* ligne " + line_number + " : le noeud " + elem[1] + " n'existe pas dans le fichier de noeuds.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // vérification du format des champs de type double
                    try {
                        // cout
                        test_double_parse = Double.parseDouble(elem[2]);
                        // capcacité
                        test_double_parse = Double.parseDouble(elem[3]);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier d'arcs " +
                                sourcepath + ".nod :\n" +
                                "* ligne " + line_number + " : erreur de conversion en type 'double'.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                br.close();

                // 3. vérification d'intégrité du fichier de demandes
                br = new BufferedReader(new FileReader(requestfile));
                br.readLine();
                line_number = 1;
                while ((line = br.readLine()) != null) {
                    line_number++;
                    elem = line.split("\t");
                    // nombre de paramètres récupérés sur chaque ligne
                    if (elem.length != 3) {
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier de demandes " +
                                sourcepath + ".dem :\n" +
                                "* ligne " + line_number + " : nombre d'arguments incorrect.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // existence des noeuds dans le fichier de noeuds
                    // noeud origine
                    declarable_node = false;
                    for (String name : node_names) {
                        if (elem[0].equals(name)) {
                            declarable_node = true;
                        }
                    }
                    if (!declarable_node) {
                        node_names.clear();
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier de demandes " +
                                sourcepath + ".dem :\n" +
                                "* ligne " + line_number + " : le noeud " + elem[0] + " n'existe pas dans le fichier de noeuds.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // noeud extremité
                    declarable_node = false;
                    for (String name : node_names) {
                        if (elem[1].equals(name)) {
                            declarable_node = true;
                        }
                    }
                    if (!declarable_node) {
                        node_names.clear();
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier de demandes " +
                                sourcepath + ".dem :\n" +
                                "* ligne " + line_number + " : le noeud " + elem[1] + " n'existe pas dans le fichier de noeuds.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // vérification du format des champs de type double
                    try {
                        // flux
                        test_double_parse = Double.parseDouble(elem[2]);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Erreur dans le fichier de demandes " +
                                sourcepath + ".nod :\n" +
                                "* ligne " + line_number + " : erreur de conversion en type 'double'.\n" +
                                "Le chargement des données a été abdandonné.",
                                "Chargement de données", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                br.close();
                node_names.clear();

                //
                // Initialisation des objets depuis les données des fichiers
                //

                this.jTextField1.setText(sourcepath + ".*");

                // Réinitialisation des données précédement chargées
                Application.getSingleton().getgrapheOriginal().reset();
                Noeud.getnoeuds().clear();
                Arc.getarcs().clear();
                Demande.getdemandes().clear();

                // Chargement des noeuds
                br = new BufferedReader(new FileReader(nodefile));
                br.readLine();
                while ((line = br.readLine()) != null) {
                    elem = line.split("\t");
                    new Noeud(elem[0], Double.parseDouble(elem[1]), Double.parseDouble(elem[2]));
                }
                br.close();
                this.jTextField2.setText(String.valueOf(Noeud.getnoeuds().size()));
                Noeud.displayNoeuds();


                // Chargement des arcs
                br = new BufferedReader(new FileReader(arcfile));
                br.readLine();
                while ((line = br.readLine()) != null) {
                    elem = line.split("\t");
                    new Arc(Noeud.getNoeud(elem[0]), Noeud.getNoeud(elem[1]), Double.parseDouble(elem[2]), Double.parseDouble(elem[3]));
                }
                br.close();
                this.jTextField3.setText(String.valueOf(Arc.getarcs().size()));
                Arc.displayArcs();

                // Chargement des demandes
                br = new BufferedReader(new FileReader(requestfile));
                br.readLine();
                while ((line = br.readLine()) != null) {
                    elem = line.split("\t");
                    new Demande(Noeud.getNoeud(elem[0]), Noeud.getNoeud(elem[1]), Double.parseDouble(elem[2]));
                }
                br.close();
                this.jTextField4.setText(String.valueOf(Demande.getdemandes().size()));
                Demande.displayDemandes();

                // Implémentation dans le graphe
                Application.getSingleton().getgrapheOriginal().getnoeuds().addAll(Noeud.getnoeuds());
                Application.getSingleton().getgrapheOriginal().getarcs().addAll(Arc.getarcs());
                Application.getSingleton().getgrapheOriginal().getdemandes().addAll(Demande.getdemandes());
                //JOptionPane.showMessageDialog(null, "Importation des données réussie !",
                //        "Chargement de données", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(Application.getSingleton().getgrapheOriginal().toString());
                if ( !warning_message.equals("") ) {
                    JOptionPane.showMessageDialog(null, "Le graphe a été correctement instancié, cependant, certaines incohérences ont été \ndétectées dans le jeu de données :" +
                            warning_message,
                        "Chargement de données", JOptionPane.WARNING_MESSAGE);
                }
                //Paramètres par défaut du recuit
                this.jSpinner2.getModel().setValue(Arc.getarcs().size() * Arc.getarcs().size());

            } catch (IOException x) {
                System.err.println(x);
            }
        }
        // si en fin de chargement, aucun noeud n'est instancié, on bloque
        // le lancement de résolution
        if ( Application.getSingleton().getgrapheOriginal().getnoeuds().isEmpty() ) {
            this.jButton4.setEnabled(false);
        }
        else this.jButton4.setEnabled(true);
    }

    @Action
    public void solve() {
        // exception vérifiée sur la sélection de la méthode de résolution
        //try {
            String methode_de_resolution = this.buttonGroup1.getSelection().getActionCommand();
            if (methode_de_resolution.equals("cplex")) {
                try {
                    double soluce = (new ProgrammeLineaire()).resoudre();
                    this.refresh("solution_cplex", String.valueOf(soluce));
                } catch (IloException ex) {
                    this.refresh("log_cplex", "Cplex problem : " + ex.getMessage());
                }
            } else if (methode_de_resolution.equals("recuit")) {
                this.refresh("log_recuit", methode_de_resolution + "=> Paliers:" +
                        this.jSpinner1.getModel().getValue().toString() +
                        " Itérations:" + this.jSpinner2.getModel().getValue().toString());
                TelecomRecuit tr = new TelecomRecuit();
                double soluce = tr.resoudre(Integer.parseInt(this.jSpinner1.getModel().getValue().toString()),
                        Integer.parseInt(this.jSpinner2.getModel().getValue().toString()));
                this.refresh("solution_recuit", String.valueOf(soluce));
            } else if (methode_de_resolution.equals("vns")) {
                 this.refresh("log_recuit", methode_de_resolution + "=> Kmax:" +
                        this.jSpinner3.getModel().getValue().toString() +
                        " Itérations:" + this.jSpinner4.getModel().getValue().toString());
                 try {
                TelecomVNS tvns = new TelecomVNS();
                double soluce = tvns.resoudre(Integer.parseInt(this.jSpinner3.getModel().getValue().toString()),
                Integer.parseInt(this.jSpinner4.getModel().getValue().toString()));
                this.refresh("solution_vns", String.valueOf(soluce));
                 } catch (Exception e) {
                     System.out.println("ERREUR VNS " + e.getMessage());
                 }
            }
        /*}catch (Exception e) {
        // l'exception appropriée est InvocationTargetException,
        // mais java ne la reconnait pas comme étant envoyée par this.buttonGroup1.getSelection()
        JOptionPane.showMessageDialog(null, "Choisissez une méthode de résolution",
        "Erreur", JOptionPane.ERROR_MESSAGE);
        }*/
    }

    public boolean advancedLog () {
        if (this.jCheckBox1.isSelected()) {
            return true;
        }
        return false;
    }

    public void refresh(String type, String s) {
        if (type.equals("console")) {
            System.out.println(s);
        } else if (type.equals("log_recuit")) {
            this.jTextArea1.setText(this.jTextArea1.getText() + "\n" + s);
        } else if (type.equals("log_cplex")) {
            this.jTextArea1.setText(this.jTextArea2.getText() + "\n" + s);
        } else if (type.equals("température")) {
            this.jTextField5.setText(s);
        } else if (type.equals("k")) {
            this.jTextField6.setText(s);
        } else if (type.equals("itérations")) {
            this.jTextField7.setText(s);
        } else if (type.equals("solution_cplex")) {
            this.jTextField8.setText(s);
        } else if (type.equals("solution_recuit")) {
            this.jTextField9.setText(s);
        } else if (type.equals("solution_vns")) {
            this.jTextField10.setText(s);
        }
    }

    

    public void drawGraph(Graphe gJungGraphDraw, Methode m) {
        UndirectedSparseMultigraph<Noeud, Arc> gJungGraph = new UndirectedSparseMultigraph<Noeud, Arc>();
        // On l'alimente pas les arcs de notre graphe
        for (Arc a : gJungGraphDraw.getarcs()) {
            // On ne créer que les arcs qui ont une capacité
            if (a.getCapacite() != 0) {
                gJungGraph.addEdge(a, a.getNoeudOrigine(), a.getNoeudExtremite());
            }
        }
        // On alimente les noeuds à notre graphe
        for (Noeud n : gJungGraphDraw.getnoeuds()) {
            gJungGraph.addVertex(n);
        }
        // Layout<V, E>, BasicVisualizationServer<V,E>
        Layout<Integer, String> layout = new CircleLayout(gJungGraph);
        Dimension dim = new Dimension(this.graphPanel.getWidth() -20, this.graphPanel.getHeight() - 20);
        layout.setSize(dim);
        VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(layout);
        vv.setPreferredSize(dim);
        // Setup up a new vertex to paint transformer...
        /*Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
        public Paint transform(Integer i) {
        return Color.GREEN;
        }
        };*/
        // Set up a new stroke Transformer for the edges
        /*float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
        public Stroke transform(String s) {
        return edgeStroke;
        }
        };*/
        //vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        //vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        switch (m) {
            case Recuit:
                this.graphPanel.setLayout(new GridLayout(1, 1));
                this.graphPanel.setPreferredSize(dim);
                this.graphPanel.removeAll();
                this.graphPanel.add(vv);
                this.RecuitTabbedPane.setSelectedIndex(0);
                break;
            case PL:
                this.graphPanel1.setLayout(new GridLayout(1, 1));
                this.graphPanel1.setPreferredSize(dim);
                this.graphPanel1.removeAll();
                this.graphPanel1.add(vv);
                this.PLTabbedPane.setSelectedIndex(0);
                break;
        }

    this.graphPanel.setVisible(true);
    this.graphPanel.revalidate();
    //this.getFrame().pack();
    /*JFrame frame = new JFrame("Graph Recuit");
    frame.getContentPane().add(vv);
    frame.pack();
    frame.setVisible(true);*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane PLTabbedPane;
    private javax.swing.JTabbedPane RecuitTabbedPane;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JPanel graphPanel1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
