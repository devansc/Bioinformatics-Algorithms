import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Font;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author devan
 */
public class GUI extends javax.swing.JFrame {

    File sequenceFile;
    File queryFile;
    private ArrayList<String> sequences;
    private ArrayList<String> sequenceNames;
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        title.setFont(new Font("Serif", Font.PLAIN, 28));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        sequenceFileButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        queryFileButton = new javax.swing.JButton();
        pamRadio = new javax.swing.JRadioButton();
        blosumRadioButton = new javax.swing.JRadioButton();
        goButton = new javax.swing.JButton();
        sequenceFileText = new javax.swing.JLabel();
        queryFileText = new javax.swing.JLabel();
        gapPenaltyField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        title.setText("DNA Sequence Aligner");

        jLabel1.setText("FASTA DNA Sequence(s) to Align");

        sequenceFileButton.setText("Find File");
        sequenceFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sequenceFileButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("FASTA Query Sequence");

        queryFileButton.setText("Find File");
        queryFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queryFileButtonActionPerformed(evt);
            }
        });

        pamRadio.setText("PAM");
        pamRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pamRadioActionPerformed(evt);
            }
        });

        blosumRadioButton.setText("BLOSUM");
        blosumRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blosumRadioButtonActionPerformed(evt);
            }
        });

        goButton.setText("Find Alignment");
        goButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goButtonActionPerformed(evt);
            }
        });

        gapPenaltyField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gapPenaltyFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Gap Penalty:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(title)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(queryFileButton)
                                    .addComponent(sequenceFileButton)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(goButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(sequenceFileText))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(queryFileText))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(pamRadio)
                                .addGap(96, 96, 96))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blosumRadioButton)
                            .addComponent(gapPenaltyField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(title)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sequenceFileButton))
                .addGap(1, 1, 1)
                .addComponent(sequenceFileText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(queryFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(queryFileText)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pamRadio)
                    .addComponent(blosumRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gapPenaltyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(goButton)
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sequenceFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sequenceFileButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filename = file + "";
            this.sequenceFile = file;
            sequenceFileText.setText(filename);
        }
    }//GEN-LAST:event_sequenceFileButtonActionPerformed

    private void queryFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queryFileButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filename = file + "";
            this.queryFile = file;
            queryFileText.setText(filename);
        }
    }//GEN-LAST:event_queryFileButtonActionPerformed

    private void error(String text) {
        showPopup("ERROR", text);
    }
    
    private void showPopup(String title, String text) {
        JOptionPane error = new JOptionPane();
        error.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
        error.setVisible(true);
    }
    
    private void goButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goButtonActionPerformed
        if (queryFileText.getText().equals("") || sequenceFileText.getText().equals("")) {
            error("Need to find both a sequence file and a query file");
            return;
        }
        if (!pamRadio.isSelected() && !blosumRadioButton.isSelected()) {
            error("Need to select PAM or BLOSUM");
            return;
        }
        
        int gapPenalty = 0;
        
        try {
            gapPenalty = Integer.parseInt(gapPenaltyField.getText());
        } catch (Exception e) {
            error("Need to input an integer for the gap penalty");
        }
        
        runAlignments(gapPenalty);
        // showPopup("DONE", "CSV file " + filename " created");
    }//GEN-LAST:event_goButtonActionPerformed

    private void runAlignments(int gapPenalty) {
        ArrayList<Integer> alignmentScores = new ArrayList<Integer>();
        String pattern = "";
        int[][] subMatrix;

        subMatrix = pamRadio.isSelected() ? PAM250.getMatrix() : BLOSUM.getMatrix();

        try {
            getDNASequences();
            pattern = getPattern();
        } catch (FileNotFoundException e) {
            error(e.toString());
            return;
        }

        for (int i = 0; i < sequences.size(); i++) {
            Alignment algn = new Alignment(sequences.get(i), pattern, gapPenalty, subMatrix);
            alignmentScores.add(algn.getAlignmentScore());
        }
        
        printScores(alignmentScores);
    }


    private static void printScores(ArrayList<Integer> alignmentScores) {
        for(Integer score : alignmentScores) {
            System.out.println(score);
        }
    }

    private String getPattern() throws FileNotFoundException {
        Scanner scan = new Scanner(queryFile);
        String pattern = "";
        
        try {
            scan.nextLine();
            while (scan.hasNextLine()) {
                pattern += scan.nextLine();
            }
            return pattern;
        } catch (Exception e) {
            error("Query sequence not FASTA file");
            return "";
        }
        
    }

    private void getDNASequences() throws FileNotFoundException {
        Scanner scan = new Scanner(sequenceFile);
        sequences = new ArrayList<String>();
        sequenceNames = new ArrayList<String>();

        int sequenceNumber = -1;
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.contains(">")) {
                sequenceNumber++;
                sequences.add("");
                if (line.contains(",")) 
                    sequenceNames.add(line.substring(1, line.indexOf(",")));
                else if (line.length() < 16)
                    sequenceNames.add(line);
                else 
                    sequenceNames.add(line.substring(1, 15));
            }
            else {
                sequences.set(sequenceNumber, sequences.get(sequenceNumber) + line);
            }
        }
    }
    
    private void pamRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pamRadioActionPerformed
        if (blosumRadioButton.isSelected())
            blosumRadioButton.setSelected(false);
    }//GEN-LAST:event_pamRadioActionPerformed

    private void blosumRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blosumRadioButtonActionPerformed
        if (pamRadio.isSelected()) 
            pamRadio.setSelected(false);
    }//GEN-LAST:event_blosumRadioButtonActionPerformed

    private void gapPenaltyFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gapPenaltyFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gapPenaltyFieldActionPerformed

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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton blosumRadioButton;
    private javax.swing.JTextField gapPenaltyField;
    private javax.swing.JButton goButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JRadioButton pamRadio;
    private javax.swing.JButton queryFileButton;
    private javax.swing.JLabel queryFileText;
    private javax.swing.JButton sequenceFileButton;
    private javax.swing.JLabel sequenceFileText;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
