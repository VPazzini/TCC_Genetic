package GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Genetic;
import main.Individual;
import main.Population;
import main.Sequence;

public class MainWindow extends javax.swing.JFrame {

    private Genetic g;
    Thread t;

    public MainWindow() {
        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(this.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerMotifSize = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jSpinnerPopulationSize = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jSpinnerGenerations = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jSpinnerComparisonThreshold = new javax.swing.JSpinner();
        jComboBoxPopulationMethod = new javax.swing.JComboBox();
        jComboBoxCrossOverMethod = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jSpinnerSurvivors = new javax.swing.JSpinner();
        jComboBoxSelectionMethod = new javax.swing.JComboBox();
        jTextFieldFilePath = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaOutPut = new javax.swing.JTextArea();
        jLabelPopSize = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButtonRun = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabelGeneration = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelSequences = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelMeanLenght = new javax.swing.JLabel();
        jTextFieldMotifFilePath = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(558, 472));

        jLabel2.setText("Motif Size");

        jSpinnerMotifSize.setModel(new javax.swing.SpinnerNumberModel(11, 1, 1000, 1));

        jLabel13.setText("Population Size");

        jSpinnerPopulationSize.setModel(new javax.swing.SpinnerNumberModel(100, 0, 100000, 50));

        jLabel14.setText("Max Generations");

        jSpinnerGenerations.setModel(new javax.swing.SpinnerNumberModel(100, 1, 100000, 50));

        jLabel15.setText("Comparasion Threshold");

        jSpinnerComparisonThreshold.setModel(new javax.swing.SpinnerNumberModel(0.7d, 0.0d, 1.0d, 0.05d));
        jSpinnerComparisonThreshold.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerComparisonThresholdStateChanged(evt);
            }
        });

        jComboBoxPopulationMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Population Creation Method", "Random", "Clustering" }));

        jComboBoxCrossOverMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CrossOver Method" }));

        jLabel17.setText("Survaviors from Previous Generation");

        jSpinnerSurvivors.setModel(new javax.swing.SpinnerNumberModel(0.05d, 0.0d, 0.8d, 0.01d));

        jComboBoxSelectionMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selection Method", "Random", "Roullete Wheel" }));

        jButton1.setText("FASTA File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextAreaOutPut.setColumns(20);
        jTextAreaOutPut.setRows(5);
        jScrollPane1.setViewportView(jTextAreaOutPut);

        jLabelPopSize.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPopSize.setText("0");

        jLabel4.setText("Population");

        jButtonRun.setText("Run");
        jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunActionPerformed(evt);
            }
        });

        jLabel5.setText("Generation");

        jLabelGeneration.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelGeneration.setText("0");

        jLabel1.setText("Sequences");

        jLabelSequences.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSequences.setText("0");

        jLabel6.setText("Mean Lenght");

        jLabelMeanLenght.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelMeanLenght.setText("0");

        jButton2.setText("Motifs File");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxCrossOverMethod, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSpinnerMotifSize)
                                    .addComponent(jSpinnerPopulationSize, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jSpinnerGenerations))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBoxPopulationMethod, javax.swing.GroupLayout.Alignment.LEADING, 0, 160, Short.MAX_VALUE)
                                    .addComponent(jComboBoxSelectionMethod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(36, 36, 36)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSpinnerSurvivors, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                            .addComponent(jSpinnerComparisonThreshold))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldMotifFilePath)
                            .addComponent(jTextFieldFilePath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelPopSize, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                                    .addComponent(jLabelMeanLenght, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelSequences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelGeneration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonRun, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel4, jLabel5, jLabel6});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSpinnerMotifSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jSpinnerComparisonThreshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerPopulationSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17)
                    .addComponent(jSpinnerSurvivors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerGenerations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBoxPopulationMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCrossOverMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSelectionMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMotifFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabelSequences))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabelMeanLenght))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabelGeneration))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPopSize)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                        .addComponent(jButtonRun))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunActionPerformed
        if (jButtonRun.getText().equals("Run")) {
            g = new Genetic();

            if (jTextFieldFilePath.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Please enter a fasta file");
                return;
            }

            g.readFile(jTextFieldFilePath.getText());

            g.setUp((int) jSpinnerGenerations.getValue(), (int) jSpinnerPopulationSize.getValue(),
                    (int) jSpinnerMotifSize.getValue(), (int) jComboBoxPopulationMethod.getSelectedIndex(),
                    jTextFieldMotifFilePath.getText(),
                    (double) jSpinnerSurvivors.getValue(), (int) jComboBoxSelectionMethod.getSelectedIndex(),
                    this);

            //System.out.println(g.getSequences().size() + " Sequences");
            this.jLabelSequences.setText(g.getSequences().size() + "");
            float size = 0;
            for (Sequence s : g.getSequences()) {
                size += s.lenght();
            }
            size = size / g.getSequences().size();
            this.jLabelMeanLenght.setText(size + "");

            Population.getInstance().setThresholdComparison((double) jSpinnerComparisonThreshold.getValue());

            t = new Thread(g);
            t.start();

            jButtonRun.setText("Stop");
        } else {
            if (t.isAlive()) {
                t.stop();
            }
            jButtonRun.setText("Run");
        }
    }//GEN-LAST:event_jButtonRunActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        JFileChooser fileChooser = new JFileChooser((new File("")).getAbsoluteFile()+ "/input");

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.jTextFieldFilePath.setText(fileChooser.getSelectedFile().toString());
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jSpinnerComparisonThresholdStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerComparisonThresholdStateChanged
        double d = (double) jSpinnerComparisonThreshold.getValue();
        Population.getInstance().setThresholdComparison(d);
    }//GEN-LAST:event_jSpinnerComparisonThresholdStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser fileChooser = new JFileChooser((new File("")).getAbsoluteFile());

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.jTextFieldMotifFilePath.setText(fileChooser.getSelectedFile().toString());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public void attGeneration(ArrayList<Individual> list) {
        this.jTextAreaOutPut.setText("");
        String s = "";
        int i = 0;
        for (Individual ind : list) {
            s += ind.toString() + "\n";
            //s += ind.toStringFull((int) (((double)jSpinnerComparisonThreshold.getValue())*100)) + "\n";
            if (i++ == 30) {
                break;
            }
        }
        this.jTextAreaOutPut.setText(s);
        this.jLabelGeneration.setText(g.getGen() + "");
        this.jLabelPopSize.setText(Population.getInstance().getPopulation().size() + "");
    }

    public void finished() {
        //JOptionPane.showMessageDialog(this, "Computation Finished");

        File f = new File(jTextFieldFilePath.getText());
        String path = (new File("")).getAbsoluteFile()+"/output/"+f.getName()+"/";
        File newDir = new File(path);
        if(!newDir.exists()){
            newDir.mkdir();
        }
        
        JFileChooser fileChooser = new JFileChooser((new File("")).getAbsoluteFile()+"/output/"+f.getName() + "/");
        
        
        
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setSelectedFile(new File("/output/output_S"
                + this.jSpinnerMotifSize.getValue() + "_"
                + (int) ((double) this.jSpinnerComparisonThreshold.getValue() * 100) + "_"
                + f.getName()));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (fileToSave.exists()) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Overwrite the existing file?", "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        new BufferedWriter(new FileWriter(fileToSave, false));
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //System.out.println("Save as file: " + fileToSave.getAbsolutePath());

            for (Individual ind : Population.getInstance().getPopulation()) {
                ind.writeToFile(fileToSave);
            }

        };

        jButtonRun.setText("Run");


        //fileChooser.showOpenDialog(this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonRun;
    private javax.swing.JComboBox jComboBoxCrossOverMethod;
    private javax.swing.JComboBox jComboBoxPopulationMethod;
    private javax.swing.JComboBox jComboBoxSelectionMethod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelGeneration;
    private javax.swing.JLabel jLabelMeanLenght;
    private javax.swing.JLabel jLabelPopSize;
    private javax.swing.JLabel jLabelSequences;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerComparisonThreshold;
    private javax.swing.JSpinner jSpinnerGenerations;
    private javax.swing.JSpinner jSpinnerMotifSize;
    private javax.swing.JSpinner jSpinnerPopulationSize;
    private javax.swing.JSpinner jSpinnerSurvivors;
    private javax.swing.JTextArea jTextAreaOutPut;
    private javax.swing.JTextField jTextFieldFilePath;
    private javax.swing.JTextField jTextFieldMotifFilePath;
    // End of variables declaration//GEN-END:variables
}
