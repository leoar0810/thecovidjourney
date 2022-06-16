package view;
import jgraph.JGrafo;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.Apoyo;
import main.ZoneEvent;

/**
 *
 * @author leonardo
 */
public class ViewStart extends javax.swing.JFrame {
    
    private JGrafo grafo;
    private int numNodos;
    private byte typeAristas, typeMask;
    private String directory;
    private ViewGrafo viewGrafo;
    
    public ViewStart() {
        initComponents();
        numNodos=10;
        typeAristas=JGrafo.ARISTAS_NO_DIRIGIDAS;
        typeMask=JGrafo.RANDOM_MASK;
        
        setLocationRelativeTo(null);
        setResizable(false);
        dialogSettings.setResizable(false);
        dialogSettings.setSize(500, 280);
        frameParam.setResizable(false);
        frameParam.setSize(500, 700);
        
        frameParam.setDefaultCloseOperation(EXIT_ON_CLOSE);
        dialogSettings.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        panelFondo.requestFocus();
        directory="/src/res/TheCovidJourneyFiles/";
        initFileChooser(true);
    }
    
    private void initFileChooser(boolean conf){
        if(conf){
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setCurrentDirectory(new File(directory));
            directory=chooser.getCurrentDirectory().getAbsolutePath();
        }else{
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("Covid Journey","cvj"));
        }
    }
    
    private void updatePanelFondo(Graphics g){
        int x=getWidth()/2, y=getHeight()/4, w=getWidth()/2, h=getHeight()/4;
        g.drawImage(Apoyo.logo, x-w/2, y-h/2, w, h, null);
    }
    
    private void updatePanelParameters(Graphics g){
        if(!panelParam.isVisible()) return;
        ZoneEvent ze;
        g.drawImage(Apoyo.back, 10, 10, 55, 30, null);
        ViewStart vs=this;
        ze=new ZoneEvent(10, 10, 55, 30){
            @Override
            public void respuesta(java.awt.event.MouseEvent me){
                frameParam.setVisible(false);
                vs.setVisible(true);
            }
        };
        try{panelParam.add(ze);
        }catch(Exception e){}
        g.setFont(new Font("Nirmala UI Semilight", Font.BOLD, 20));
        int x, y, w, h, delta=30;
        
        //Población**************************************************************
        x=frameParam.getWidth()/2; y=frameParam.getHeight()/4;
        w=frameParam.getWidth()/2; h=frameParam.getHeight()/4;
        g.drawImage(Apoyo.people, x-w/2, y-h/2+delta, w, h, null);
        g.drawString("Población = "+numNodos, x-65, y-75);
        g.drawImage(Apoyo.minus, x-w/2-40-20, y-65/2, 40, 65, null);
        ze=new ZoneEvent(x-w/2-40-20, y-65/2, 40, 65){
            @Override
            public void respuesta(java.awt.event.MouseEvent me){
                if(--numNodos<0) numNodos=0;
                panelParam.repaint();
            }
        };
        try{panelParam.add(ze);
        }catch(Exception e){}
        g.drawImage(Apoyo.plus, x+w/2+20, y-65/2, 40, 65, null);
        ze=new ZoneEvent(x+w/2+20, y-65/2, 40, 65){
            @Override
            public void respuesta(java.awt.event.MouseEvent me){
                if(++numNodos>10);//numNodos=10;
                panelParam.repaint();
            }
        };
        try{panelParam.add(ze);
        }catch(Exception e){}
        
        //Mascarilla**************************************************************
        double h2=h*1/2, w2=w*3/4;
        y=frameParam.getHeight()/2; w=(int)w2; h=(int)h2;
        g.drawImage(Apoyo.mask, x-w/2, y-h/2+delta, w, h, null);
        if(typeMask!=JGrafo.MASK){
            w2=w/4; h2=h/2;
            switch(typeMask){
                case JGrafo.NO_MASK: g.drawImage(Apoyo.not, x-(int)w2/2, y-(int)h2/2+delta, (int)w2, (int)h2, null); break;
                default: g.drawImage(Apoyo.random, x-(int)w2/2, y-(int)h2/2+delta, (int)w2, (int)h2, null);
            }
        }
        g.drawString("Mascarilla", x-45, y-40+10);
        g.drawImage(Apoyo.minus, x-w/2-40-20, y-65/2+30, 40, 65, null);
        ze=new ZoneEvent(x-w/2-40-20, y-65/2+30, 40, 65){
            @Override
            public void respuesta(java.awt.event.MouseEvent me){
                if(--typeMask<JGrafo.RANDOM_MASK) typeMask=JGrafo.MASK;
                panelParam.repaint();
            }
        };
        try{panelParam.add(ze);
        }catch(Exception e){}
        g.drawImage(Apoyo.plus, x+w/2+20, y-65/2+30, 40, 65, null);
        ze=new ZoneEvent(x+w/2+20, y-65/2+30, 40, 65){
            @Override
            public void respuesta(java.awt.event.MouseEvent me){
                if(++typeMask>JGrafo.MASK) typeMask=JGrafo.RANDOM_MASK;
                panelParam.repaint();
            }
        };
        try{panelParam.add(ze);
        }catch(Exception e){}
        
        //Aristas**************************************************************
        y=frameParam.getHeight()*3/4;
        h2=h/2; delta-=20;
        if(typeAristas==JGrafo.ARISTAS_NO_DIRIGIDAS)
            g.drawImage(Apoyo.ar_not_dir, x-w/2, y-(int)h2/2+delta, w, (int)h2, null);
        else{
            g.drawImage(Apoyo.ar_dir, x-w/2, y-(int)h2/2+delta, w, (int)h2, null);
            if(typeAristas==JGrafo.RANDOM_ARISTAS){
                w2=w/4; h2*=3/2;
                g.drawImage(Apoyo.random, x-(int)w2/2, y-(int)h2/2+delta, (int)w2, (int)h2, null);
            }
        }
        g.drawString("Relaciones", x-45, y-40+10);
        g.drawImage(Apoyo.minus, x-w/2-40-20, y-65/2+10, 40, 65, null);
        ze=new ZoneEvent(x-w/2-40-20, y-65/2+10, 40, 65){
            @Override
            public void respuesta(java.awt.event.MouseEvent me){
                if(--typeAristas<JGrafo.RANDOM_ARISTAS) typeAristas=JGrafo.ARISTAS_DIRIGIDAS;
                panelParam.repaint();
            }
        };
        try{panelParam.add(ze);
        }catch(Exception e){}
        g.drawImage(Apoyo.plus, x+w/2+20, y-65/2+10, 40, 65, null);
        ze=new ZoneEvent(x+w/2+20, y-65/2+10, 40, 65){
            @Override
            public void respuesta(java.awt.event.MouseEvent me){
                if(++typeAristas>JGrafo.ARISTAS_DIRIGIDAS) typeAristas=JGrafo.RANDOM_ARISTAS;
                panelParam.repaint();
            }
        };
        try{panelParam.add(ze);
        }catch(Exception e){}
    }
    
    public void backToParameters(){
        frameParam.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frameParam = new javax.swing.JFrame();
        panelParam = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(new java.awt.Color(51, 51, 51));
                g.fillRect(0, 0, getWidth(), 70);
                updatePanelParameters(g);
            }
        };
        labelTitle = new javax.swing.JLabel();
        buttonStart = new javax.swing.JButton();
        chooser = new javax.swing.JFileChooser();
        dialogSettings = new javax.swing.JDialog(this, true);
        panelSettings = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(new java.awt.Color(51,51,51));
                g.fillRect(0, 0, 500, 60);
            }
        };
        labelError = new javax.swing.JLabel();
        labelOpciones = new javax.swing.JLabel();
        buttonBrowse = new javax.swing.JButton();
        fieldBrowse = new javax.swing.JTextField();
        labelBrowse = new javax.swing.JLabel();
        buttonSave = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        panelFondo = panelFondo=new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(new java.awt.Color(51, 51, 51));
                g.fillRect(0, 0, getWidth()/2+20, getHeight());
                updatePanelFondo(g);
            }
        };
        buttonSettings = new javax.swing.JButton();
        buttonImport = new javax.swing.JButton();
        buttonParámetros = new javax.swing.JButton();

        panelParam.setBackground(new java.awt.Color(255, 255, 255));
        panelParam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTitle.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 36)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(255, 255, 255));
        labelTitle.setText("PARÁMETROS");
        panelParam.add(labelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        buttonStart.setBackground(new java.awt.Color(51, 51, 51));
        buttonStart.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        buttonStart.setForeground(new java.awt.Color(255, 102, 102));
        buttonStart.setText("Generar Grafo");
        buttonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStartActionPerformed(evt);
            }
        });
        panelParam.add(buttonStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 610, -1, -1));

        javax.swing.GroupLayout frameParamLayout = new javax.swing.GroupLayout(frameParam.getContentPane());
        frameParam.getContentPane().setLayout(frameParamLayout);
        frameParamLayout.setHorizontalGroup(
            frameParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameParamLayout.createSequentialGroup()
                .addComponent(panelParam, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        frameParamLayout.setVerticalGroup(
            frameParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameParamLayout.createSequentialGroup()
                .addComponent(panelParam, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelSettings.setForeground(new java.awt.Color(255, 255, 255));
        panelSettings.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelError.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        labelError.setForeground(new java.awt.Color(204, 0, 0));
        labelError.setText("Directorio no válido");
        panelSettings.add(labelError, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, -1, -1));

        labelOpciones.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelOpciones.setForeground(new java.awt.Color(255, 255, 255));
        labelOpciones.setText("OPCIONES");
        panelSettings.add(labelOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        buttonBrowse.setBackground(new java.awt.Color(51, 51, 51));
        buttonBrowse.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        buttonBrowse.setForeground(new java.awt.Color(255, 255, 255));
        buttonBrowse.setText("Examinar");
        buttonBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBrowseActionPerformed(evt);
            }
        });
        panelSettings.add(buttonBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, -1));

        fieldBrowse.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        panelSettings.add(fieldBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 330, -1));

        labelBrowse.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        labelBrowse.setText("Directorio Actual:");
        panelSettings.add(labelBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        buttonSave.setBackground(new java.awt.Color(51, 51, 51));
        buttonSave.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        buttonSave.setForeground(new java.awt.Color(255, 255, 255));
        buttonSave.setText("Guardar Cambios");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });
        panelSettings.add(buttonSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, -1, -1));

        buttonCancel.setBackground(new java.awt.Color(51, 51, 51));
        buttonCancel.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        buttonCancel.setForeground(new java.awt.Color(255, 255, 255));
        buttonCancel.setText("Cancelar");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });
        panelSettings.add(buttonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        javax.swing.GroupLayout dialogSettingsLayout = new javax.swing.GroupLayout(dialogSettings.getContentPane());
        dialogSettings.getContentPane().setLayout(dialogSettingsLayout);
        dialogSettingsLayout.setHorizontalGroup(
            dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogSettingsLayout.createSequentialGroup()
                .addComponent(panelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        dialogSettingsLayout.setVerticalGroup(
            dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelFondo.setBackground(new java.awt.Color(255, 255, 255));
        panelFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonSettings.setBackground(new java.awt.Color(255, 255, 255));
        buttonSettings.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonSettings.setText("Opciones");
        buttonSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSettingsActionPerformed(evt);
            }
        });
        panelFondo.add(buttonSettings, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        buttonImport.setBackground(new java.awt.Color(255, 102, 102));
        buttonImport.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonImport.setText("Importar Grafo");
        buttonImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonImportActionPerformed(evt);
            }
        });
        panelFondo.add(buttonImport, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, -1, -1));

        buttonParámetros.setBackground(new java.awt.Color(255, 102, 102));
        buttonParámetros.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        buttonParámetros.setText("Iniciar Nueva Simulación");
        buttonParámetros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonParámetrosActionPerformed(evt);
            }
        });
        panelFondo.add(buttonParámetros, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonParámetrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonParámetrosActionPerformed
        setVisible(false);
        frameParam.setLocationRelativeTo(this);
        frameParam.setVisible(true);
        panelParam.requestFocus();
    }//GEN-LAST:event_buttonParámetrosActionPerformed

    private void buttonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStartActionPerformed
        if(numNodos==0) return;
        
        grafo=new JGrafo(typeMask, typeAristas, numNodos);
        viewGrafo=new ViewGrafo(grafo, directory, this);
        //setVisible(false);
        frameParam.setVisible(false);
        viewGrafo.setLocationRelativeTo(this);
        viewGrafo.setVisible(true);
    }//GEN-LAST:event_buttonStartActionPerformed

    private void buttonSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSettingsActionPerformed
        initFileChooser(true);
        fieldBrowse.setText(directory);
        dialogSettings.setLocationRelativeTo(this);
        labelError.setVisible(false);
        dialogSettings.setVisible(true);
        panelSettings.requestFocus();
    }//GEN-LAST:event_buttonSettingsActionPerformed

    private void buttonImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImportActionPerformed
        // TODO add your handling code here:
        initFileChooser(false);
        
        if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            try{
                JGrafo grafo=importando(chooser.getSelectedFile());

                //System.out.println("HolaHola  "+grafo);
                if(grafo!=null){
                    //System.out.println("a su naranfa");
                    viewGrafo=new ViewGrafo(grafo, directory, this);
                    viewGrafo.setLocationRelativeTo(this);
                    viewGrafo.setVisible(true);
                }
            }catch(IOException e){
                JOptionPane.showMessageDialog(this, "archivo contaminado o presenta errores");
            }
        }
        
    }//GEN-LAST:event_buttonImportActionPerformed

    private void buttonBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBrowseActionPerformed
        labelError.setVisible(false);
        if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            fieldBrowse.setText(chooser.getSelectedFile()!=null?
                    chooser.getSelectedFile().getAbsolutePath():chooser.getCurrentDirectory().getAbsolutePath());
        }
    }//GEN-LAST:event_buttonBrowseActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        if(validDirectory()){
            directory=fieldBrowse.getText();
            chooser.setCurrentDirectory(new File(directory));
            dialogSettings.setVisible(false);
        }else labelError.setVisible(true);
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        dialogSettings.setVisible(false);
    }//GEN-LAST:event_buttonCancelActionPerformed
    
    private JGrafo importando(File file) throws IOException{
        
        java.io.BufferedReader br=new java.io.BufferedReader(new java.io.FileReader(file));
        StringBuilder b=new StringBuilder();
        String l;
        while((l=br.readLine())!=null){
            b.append(l);
        }
        
        //System.out.println(adj.length);
        return Apoyo.grafoByText(b.toString());
        //return JGrafo.grafoByInfo(vert,adj, it, typeMask, typeAristas);
    }
    
    private boolean validDirectory(){
        String dir=fieldBrowse.getText();
        if(dir!=null && !dir.isEmpty()){
            File f=new File(dir);
            if(f.exists() && !f.isFile()) return true;
        }
        return false;
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
            java.util.logging.Logger.getLogger(ViewStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewStart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBrowse;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonImport;
    private javax.swing.JButton buttonParámetros;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonSettings;
    private javax.swing.JButton buttonStart;
    private javax.swing.JFileChooser chooser;
    private javax.swing.JDialog dialogSettings;
    private javax.swing.JTextField fieldBrowse;
    private javax.swing.JFrame frameParam;
    private javax.swing.JLabel labelBrowse;
    private javax.swing.JLabel labelError;
    private javax.swing.JLabel labelOpciones;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelParam;
    private javax.swing.JPanel panelSettings;
    // End of variables declaration//GEN-END:variables
}