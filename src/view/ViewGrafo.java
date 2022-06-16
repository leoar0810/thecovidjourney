package view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jgraph.Arista;
import jgraph.JGrafo;
import jgraph.Nodo;
import jgraph.Ruta;
import main.Apoyo;
import main.ZoneEvent;
import pointedlist.PointedList;

/**
 *
 * @author Leonardo
 */
public class ViewGrafo extends javax.swing.JFrame {
    
    private ViewStart vs;
    private String directory;
    private boolean iterando=false;
    
    public ViewGrafo(jgraph.JGrafo jGrafo, String directory, ViewStart vs) {
        this.vs=vs;
        this.directory=directory;
        this.jGrafo=jGrafo;
        jGrafo.viewGrafo=this;
        initComponents();
        jGrafo.up();
        radioIterarManual.setSelected(true);
        updateIteracionesGroup();
        switch(jGrafo.getTypeMask()){
            case JGrafo.MASK: radioMask.setSelected(true); break;
            case JGrafo.NO_MASK: radioMaskNo.setSelected(true); break;
            case JGrafo.RANDOM_MASK: radioMaskRandom.setSelected(true);
        }
        updateMaskGroup();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        jGrafo.requestFocus();
        labelIteración.setText("Iteración #"+jGrafo.getIteración());
        initFileChooser();
        dialog.setSize(580, 450);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initNodos();
    }
    
    private void initFileChooser(){
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(new File(directory));
        directory=chooser.getCurrentDirectory().getAbsolutePath();
    }
    
    public void showRutasContagio(PointedList<Ruta> lista, Nodo nodo){
        labelDialogTitle.setText("RUTAS DE POSIBLES CONTAGIOS");
        labelDialogInfo.setText("DATOS DEL INDIVIDUO");
        labelDialogId.setText("Id: "+nodo.getId());
        labelDialogMask.setText("Mascarilla: "+(nodo.getPersona().hasMask()?"Sí":"No"));
        labelDialogCont.setText("Contagiado: "+(nodo.getPersona().isContagiado()?"Sí":"No"));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        areaInfo.setText("");
        if(lista.isEmpty()){
            areaInfo.setText("No hay rutas de contagio para este nodo.\nRegresar al rato.");
            return;
        }
        
        Ruta[] rutas=tortasDePapaya(lista);
        Arrays.sort(rutas);
        for(Ruta r: rutas){
            Nodo[] nodos=r.toNodosVector();
            for(int i=0; i<nodos.length; i++){
                areaInfo.append(nodos[i].getId()+"");
                if(i!=nodos.length-1) areaInfo.append(" -> ");
            }
            areaInfo.append("\nProbabilidad de contagio = "+Apoyo.round(r.probContagio(), 2)+"%\n\n");
        }
    }
    
    private Ruta[] tortasDePapaya(PointedList<Ruta> lista){
        Ruta[] rutas=new Ruta[lista.size()];
        int i=0;
        for(Object o: lista)
            rutas[i++]=(Ruta)o;
        
        return rutas;
    }
    
    public void showPotContagios(PointedList<Arista> lista, Nodo nodo){
        labelDialogTitle.setText("POTENCIALES CONTAGIOS");
        labelDialogInfo.setText("DATOS DEL INDIVIDUO");
        labelDialogId.setText("Id: "+nodo.getId());
        labelDialogMask.setText("Mascarilla: "+(nodo.getPersona().hasMask()?"Sí":"No"));
        labelDialogCont.setText("Contagiado: "+(nodo.getPersona().isContagiado()?"Sí":"No"));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        areaInfo.setText("");
        if(lista.isEmpty()){
            areaInfo.setText("No hay potenciales contagios actualmente.\nRegresar al rato.");
            return;
        }
        
        Arista[] aristas=asereje(lista);
        Arrays.sort(aristas);
        for(Arista a: aristas){
            Nodo n=a.getExtremo1().equals(nodo)?a.getExtremo2():a.getExtremo1();
            areaInfo.append(nodo.getId()+" -> "+n.getId());
            areaInfo.append("\nProbabilidad de contagio = "+Apoyo.round(a.probContagio(), 2)+"%\n\n");
        }
    }
    
    private Arista[] asereje(PointedList<Arista> lista){
        Arista[] aristas=new Arista[lista.size()];
        int i=0;
        for(Object o: lista)
            aristas[i++]=(Arista)o;
        
        return aristas;
    }
    
    private synchronized void iterar(){
        jGrafo.iterar();
        jGrafo.repaint();
        //jGrafo1.up();
        labelIteración.setText("Iteración #"+jGrafo.getIteración());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooser = new javax.swing.JFileChooser();
        dialog = new javax.swing.JDialog();
        panelDialog = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(new java.awt.Color(51, 51, 51));
                g.fillRect(0, 0, getWidth(), 55);
            }
        };
        labelDialogTitle = new javax.swing.JLabel();
        labelDialogInfo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaInfo = new javax.swing.JTextArea();
        labelDialogCont = new javax.swing.JLabel();
        labelDialogId = new javax.swing.JLabel();
        labelDialogMask = new javax.swing.JLabel();
        dialogExport = new javax.swing.JDialog(this,true);
        panelDialogExport = new javax.swing.JPanel();
        labelDialogExportTitle = new javax.swing.JLabel();
        checkExcel = new javax.swing.JCheckBox();
        checkCVJ = new javax.swing.JCheckBox();
        buttonDialogExportSave = new javax.swing.JButton();
        groupIterar = new javax.swing.ButtonGroup();
        groupMask = new javax.swing.ButtonGroup();
        panelOptions = new javax.swing.JPanel();
        buttonIterar = new javax.swing.JButton();
        buttonReorder = new javax.swing.JButton();
        buttonRegen = new javax.swing.JButton();
        labelNodos = new javax.swing.JLabel();
        labelGenerar = new javax.swing.JLabel();
        buttonGen = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        labelParámteros = new javax.swing.JLabel();
        labelSimulación = new javax.swing.JLabel();
        labelIteración = new javax.swing.JLabel();
        buttonArrastre = new javax.swing.JToggleButton();
        labelSecs = new javax.swing.JLabel();
        radioIterarAuto = new javax.swing.JRadioButton();
        buttonIterarEach = new javax.swing.JButton();
        fieldSecs = new javax.swing.JTextField();
        radioIterarManual = new javax.swing.JRadioButton();
        radioMaskRandom = new javax.swing.JRadioButton();
        radioMask = new javax.swing.JRadioButton();
        radioMaskNo = new javax.swing.JRadioButton();
        labelMask = new javax.swing.JLabel();
        buttonAply = new javax.swing.JButton();
        buttonStop = new javax.swing.JButton();
        jGrafo = jGrafo;
        panelTitle = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                updatePanelTitle(g);
            }
        };
        labelTitle = new javax.swing.JLabel();

        panelDialog.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelDialogTitle.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 24)); // NOI18N
        labelDialogTitle.setForeground(new java.awt.Color(255, 255, 255));
        labelDialogTitle.setText("RUTAS DE POSIBLES CONTAGIOS");
        panelDialog.add(labelDialogTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        labelDialogInfo.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelDialogInfo.setText("DATOS DEL INDIVIDUO");
        panelDialog.add(labelDialogInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        areaInfo.setEditable(false);
        areaInfo.setColumns(20);
        areaInfo.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        areaInfo.setRows(5);
        areaInfo.setOpaque(false);
        jScrollPane1.setViewportView(areaInfo);

        jScrollPane2.setViewportView(jScrollPane1);

        panelDialog.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 510, 240));

        labelDialogCont.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelDialogCont.setText("Contagiado:");
        panelDialog.add(labelDialogCont, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, -1));

        labelDialogId.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelDialogId.setText("Id:");
        panelDialog.add(labelDialogId, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        labelDialogMask.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelDialogMask.setText("Mascarilla:");
        panelDialog.add(labelDialogMask, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, -1, -1));

        javax.swing.GroupLayout dialogLayout = new javax.swing.GroupLayout(dialog.getContentPane());
        dialog.getContentPane().setLayout(dialogLayout);
        dialogLayout.setHorizontalGroup(
            dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogLayout.createSequentialGroup()
                .addComponent(panelDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        dialogLayout.setVerticalGroup(
            dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogLayout.createSequentialGroup()
                .addComponent(panelDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        dialogExport.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogExport.setTitle("Exportar Grafo");
        dialogExport.setMinimumSize(new java.awt.Dimension(330, 262));
        dialogExport.setResizable(false);
        dialogExport.setSize(new java.awt.Dimension(0, 0));

        panelDialogExport.setBackground(new java.awt.Color(255, 255, 255));
        panelDialogExport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelDialogExportTitle.setFont(new java.awt.Font("Verdana", 3, 18)); // NOI18N
        labelDialogExportTitle.setForeground(new java.awt.Color(51, 51, 51));
        labelDialogExportTitle.setText("Exportar para:");
        panelDialogExport.add(labelDialogExportTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        checkExcel.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        checkExcel.setForeground(new java.awt.Color(51, 51, 51));
        checkExcel.setText("excel (.csv)");
        checkExcel.setOpaque(false);
        panelDialogExport.add(checkExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        checkCVJ.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        checkCVJ.setForeground(new java.awt.Color(51, 51, 51));
        checkCVJ.setText("The Covid Journery (.cvj)");
        checkCVJ.setOpaque(false);
        panelDialogExport.add(checkCVJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 200, -1));

        buttonDialogExportSave.setBackground(new java.awt.Color(102, 102, 102));
        buttonDialogExportSave.setFont(new java.awt.Font("Verdana", 3, 13)); // NOI18N
        buttonDialogExportSave.setForeground(new java.awt.Color(255, 255, 255));
        buttonDialogExportSave.setText("EXPORTAR");
        buttonDialogExportSave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonDialogExportSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDialogExportSaveActionPerformed(evt);
            }
        });
        panelDialogExport.add(buttonDialogExportSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 100, 30));

        javax.swing.GroupLayout dialogExportLayout = new javax.swing.GroupLayout(dialogExport.getContentPane());
        dialogExport.getContentPane().setLayout(dialogExportLayout);
        dialogExportLayout.setHorizontalGroup(
            dialogExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDialogExport, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );
        dialogExportLayout.setVerticalGroup(
            dialogExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogExportLayout.createSequentialGroup()
                .addComponent(panelDialogExport, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelOptions.setBackground(new java.awt.Color(255, 153, 153));
        panelOptions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonIterar.setBackground(new java.awt.Color(51, 51, 51));
        buttonIterar.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonIterar.setForeground(new java.awt.Color(255, 255, 255));
        buttonIterar.setText("Siguiente Iteración");
        buttonIterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonIterarActionPerformed(evt);
            }
        });
        panelOptions.add(buttonIterar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        buttonReorder.setBackground(new java.awt.Color(51, 51, 51));
        buttonReorder.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonReorder.setForeground(new java.awt.Color(255, 255, 255));
        buttonReorder.setText("Reordenar");
        buttonReorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReorderActionPerformed(evt);
            }
        });
        panelOptions.add(buttonReorder, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 590, -1, -1));

        buttonRegen.setBackground(new java.awt.Color(51, 51, 51));
        buttonRegen.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonRegen.setForeground(new java.awt.Color(255, 255, 255));
        buttonRegen.setText("Generar Grafo (Mismos Parámetros)");
        buttonRegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRegenActionPerformed(evt);
            }
        });
        panelOptions.add(buttonRegen, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, -1, -1));

        labelNodos.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelNodos.setForeground(new java.awt.Color(255, 255, 255));
        labelNodos.setText("NODOS");
        panelOptions.add(labelNodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, -1));

        labelGenerar.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelGenerar.setForeground(new java.awt.Color(255, 255, 255));
        labelGenerar.setText("GENERAR");
        panelOptions.add(labelGenerar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        buttonGen.setBackground(new java.awt.Color(51, 51, 51));
        buttonGen.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonGen.setForeground(new java.awt.Color(255, 255, 255));
        buttonGen.setText("Generar Nuevo Grafo");
        buttonGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGenActionPerformed(evt);
            }
        });
        panelOptions.add(buttonGen, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        buttonSave.setBackground(new java.awt.Color(51, 51, 51));
        buttonSave.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonSave.setForeground(new java.awt.Color(255, 255, 255));
        buttonSave.setText("Exportar Grafo");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });
        panelOptions.add(buttonSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, -1, -1));

        labelParámteros.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelParámteros.setForeground(new java.awt.Color(255, 255, 255));
        labelParámteros.setText("PARÁMETROS");
        panelOptions.add(labelParámteros, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        labelSimulación.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        labelSimulación.setForeground(new java.awt.Color(255, 255, 255));
        labelSimulación.setText("SIMULACIÓN");
        panelOptions.add(labelSimulación, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        labelIteración.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        labelIteración.setText("Iteración #n");
        panelOptions.add(labelIteración, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 15, -1, -1));

        buttonArrastre.setBackground(new java.awt.Color(51, 51, 51));
        buttonArrastre.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonArrastre.setForeground(new java.awt.Color(255, 255, 255));
        buttonArrastre.setText("Arrastre");
        panelOptions.add(buttonArrastre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, -1, -1));

        labelSecs.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        labelSecs.setText("segundos");
        panelOptions.add(labelSecs, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 145, -1, -1));

        groupIterar.add(radioIterarAuto);
        radioIterarAuto.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        radioIterarAuto.setText("Iteraciones Automáticas");
        radioIterarAuto.setOpaque(false);
        radioIterarAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioIterarAutoActionPerformed(evt);
            }
        });
        panelOptions.add(radioIterarAuto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        buttonIterarEach.setBackground(new java.awt.Color(51, 51, 51));
        buttonIterarEach.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonIterarEach.setForeground(new java.awt.Color(255, 255, 255));
        buttonIterarEach.setText("Iterar cada");
        buttonIterarEach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonIterarEachActionPerformed(evt);
            }
        });
        panelOptions.add(buttonIterarEach, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        fieldSecs.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        panelOptions.add(fieldSecs, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 142, 70, -1));

        groupIterar.add(radioIterarManual);
        radioIterarManual.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        radioIterarManual.setText("Iteraciones Manuales");
        radioIterarManual.setOpaque(false);
        radioIterarManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioIterarManualActionPerformed(evt);
            }
        });
        panelOptions.add(radioIterarManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        groupMask.add(radioMaskRandom);
        radioMaskRandom.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        radioMaskRandom.setText("Aleatorio");
        radioMaskRandom.setOpaque(false);
        radioMaskRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMaskRandomActionPerformed(evt);
            }
        });
        panelOptions.add(radioMaskRandom, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, -1, -1));

        groupMask.add(radioMask);
        radioMask.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        radioMask.setText("Sí");
        radioMask.setOpaque(false);
        radioMask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMaskActionPerformed(evt);
            }
        });
        panelOptions.add(radioMask, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        groupMask.add(radioMaskNo);
        radioMaskNo.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        radioMaskNo.setText("No");
        radioMaskNo.setOpaque(false);
        radioMaskNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMaskNoActionPerformed(evt);
            }
        });
        panelOptions.add(radioMaskNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, -1, -1));

        labelMask.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 14)); // NOI18N
        labelMask.setText("Mascarilla");
        panelOptions.add(labelMask, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        buttonAply.setBackground(new java.awt.Color(51, 51, 51));
        buttonAply.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonAply.setForeground(new java.awt.Color(255, 255, 255));
        buttonAply.setText("Aplicar");
        buttonAply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAplyActionPerformed(evt);
            }
        });
        panelOptions.add(buttonAply, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        buttonStop.setBackground(new java.awt.Color(51, 51, 51));
        buttonStop.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        buttonStop.setForeground(new java.awt.Color(255, 255, 255));
        buttonStop.setText("Detener");
        buttonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopActionPerformed(evt);
            }
        });
        panelOptions.add(buttonStop, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        panelTitle.setBackground(new java.awt.Color(51, 51, 51));
        panelTitle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTitle.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 24)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(255, 255, 255));
        labelTitle.setText("THE COVID JOURNEY");
        panelTitle.add(labelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jGrafo, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addComponent(jGrafo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void updateIteracionesGroup(){
        boolean b=radioIterarManual.isSelected();
        buttonIterar.setEnabled(b);
        buttonIterarEach.setEnabled(!b);
        buttonStop.setEnabled(!b && iterando);
        fieldSecs.setEditable(!b);
    }
    
    private void updateMaskGroup(){
        if(radioMaskRandom.isSelected()){
            //buttonAply.setEnabled(jGrafo.getTypeMask()!=JGrafo.RANDOM_MASK);
            buttonAply.setEnabled(true);
        }else if(radioMask.isSelected()){
            buttonAply.setEnabled(jGrafo.getTypeMask()!=JGrafo.MASK);
        }else if(radioMaskNo.isSelected()){
            buttonAply.setEnabled(jGrafo.getTypeMask()!=JGrafo.NO_MASK);
        }
    }
    
    private void updatePanelTitle(Graphics g){
        ZoneEvent ze;
        g.drawImage(Apoyo.back, 10, 10, 55, 30, null);
        ze=new ZoneEvent(10, 10, 55, 30){
            @Override
            public void respuesta(java.awt.event.MouseEvent me){
                dispose();
                vs.setVisible(true);
            }
        };
        try{panelTitle.add(ze);
        }catch(Exception e){
            System.out.println("::'v");
        }
    }
    
    private void buttonIterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonIterarActionPerformed
        iterar();
    }//GEN-LAST:event_buttonIterarActionPerformed

    private void buttonReorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReorderActionPerformed
        jGrafo.up();
    }//GEN-LAST:event_buttonReorderActionPerformed

    private void buttonRegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRegenActionPerformed
        jGrafo.regen(jGrafo.getNumNodos());
        initNodos();
        labelIteración.setText("Iteración #"+jGrafo.getIteración());
    }//GEN-LAST:event_buttonRegenActionPerformed

    private void buttonGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGenActionPerformed
        dispose();
        vs.backToParameters();
    }//GEN-LAST:event_buttonGenActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        //JOptionPane.showMessageDialog(this, "Aviso:\n sistema en desarrollo...","Advertencia", JOptionPane.INFORMATION_MESSAGE);
        checkCVJ.setSelected(false);
        checkExcel.setSelected(false);
        checkExcel.setVisible(false);
        dialogExport.setLocationRelativeTo(this);
        dialogExport.setVisible(true);
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonDialogExportSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDialogExportSaveActionPerformed
        if(!checkCVJ.isSelected()) return;
        
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setFileFilter(new FileNameExtensionFilter("Covid Journey", ".cvj"));
        if(chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
            File file=chooser.getSelectedFile();
            String name=file.getName();
            int l=name.length();
            if(l<5 || (l>=5 && !name.substring(l-4, l).equals(".cvj")))
                file=new File(file.getAbsolutePath()+".cvj");
            
            //if(file.exists())mostrar si desea reemplazar
            if(checkCVJ.isSelected() && !checkExcel.isSelected()) crearCVJ(file);
        }
        dialogExport.dispose();
    }//GEN-LAST:event_buttonDialogExportSaveActionPerformed

    private void buttonIterarEachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonIterarEachActionPerformed
        iterarEach();
    }//GEN-LAST:event_buttonIterarEachActionPerformed
    
    private synchronized void iterarEach(){
        if(iterando) return;
        
        class Iterando implements Runnable {
            @Override
            public synchronized void run() {
                if(!iterando){
                    String campo=fieldSecs.getText();
                    if(campo==null || campo.isEmpty()) return;
                    double secs;
                    try{
                        secs=Double.parseDouble(campo);
                        if(secs<=0) return;
                    }catch(NumberFormatException e){return;}
                    
                    iterando=true;
                    buttonStop.setEnabled(true);
                    buttonIterarEach.setSelected(true);
                    long old=System.currentTimeMillis();
                    while(iterando){
                        if(!jGrafo.hasSanos()){
                            iterando=false;
                            buttonIterarEach.setSelected(false);
                        }
                        long now=System.currentTimeMillis();
                        if(now-old>=secs*1000){
                            old=now;
                            iterar();
                            System.out.println("Iterando "+jGrafo.getIteración()+" en "+now);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Ejecución completada");
                    buttonStop.setEnabled(false);
                }else{
                    iterando=false;
                    buttonIterarEach.setSelected(false);
                }
            }
        }
        try{
            (new Thread(new Iterando())).start();
        }catch(IllegalThreadStateException e){}
    }
    
    private void radioIterarAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioIterarAutoActionPerformed
        if(radioIterarAuto.isSelected())
            updateIteracionesGroup();
    }//GEN-LAST:event_radioIterarAutoActionPerformed

    private void radioIterarManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioIterarManualActionPerformed
        if(radioIterarManual.isSelected())
            updateIteracionesGroup();
    }//GEN-LAST:event_radioIterarManualActionPerformed

    private void radioMaskRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMaskRandomActionPerformed
        updateMaskGroup();
    }//GEN-LAST:event_radioMaskRandomActionPerformed

    private void radioMaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMaskActionPerformed
        updateMaskGroup();
    }//GEN-LAST:event_radioMaskActionPerformed

    private void radioMaskNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMaskNoActionPerformed
        updateMaskGroup();
    }//GEN-LAST:event_radioMaskNoActionPerformed

    private void buttonAplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAplyActionPerformed
        byte selection=radioMask.isSelected()?JGrafo.MASK:radioMaskNo.isSelected()?JGrafo.NO_MASK:JGrafo.RANDOM_MASK;
        if(jGrafo.getTypeMask()==JGrafo.RANDOM_MASK || jGrafo.getTypeMask()!=selection){
            jGrafo.updateMasks(selection);
            buttonAply.setEnabled(jGrafo.getTypeMask()==JGrafo.RANDOM_MASK);
        }
           
    }//GEN-LAST:event_buttonAplyActionPerformed

    private void buttonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopActionPerformed
        iterando=false;
        //buttonStop.setEnabled(false);
    }//GEN-LAST:event_buttonStopActionPerformed

    private void crearCVJ(File file){
        try {
            BufferedWriter bf=new BufferedWriter(new FileWriter(file));
            bf.write("<cvgrafo typeArista=");
            bf.write(String.valueOf(jGrafo.getTypeAristas()));
            
            bf.write(" n=");
            bf.write(String.valueOf(jGrafo.getNumNodos()));
            
            bf.write(" typeMask=");
            bf.write(String.valueOf(jGrafo.getTypeMask()));
            bf.write(">");bf.newLine();
            bf.write("<adj>");bf.newLine();
            double[][]adj=jGrafo.getMatriz();
            
            for (int i = 0; i < adj.length; i++) {
                for (int j = 0; j < adj.length; j++) {
                    bf.write(String.valueOf(adj[i][j]));
                    if(i!=adj.length-1 || j!=adj.length-1){
                        bf.write(",");
                    }
                }
                bf.newLine();
            }
            bf.write("</adj>");bf.newLine();
            bf.write("<vert>");
            String vect[]=new String[jGrafo.getNumNodos()];
            boolean vect1[]=jGrafo.getMascarillas();
            for (int i = 0; i < vect.length; i++) {
                vect[i]=String.valueOf(vect1[i]);
            }
            bf.write(String.join(",", vect));
            
            bf.write("</vert>");
            
            //* espacio para las iteraciones
            if(jGrafo.getIteración()>0){
                bf.newLine();
                bf.write("<iter>");bf.newLine();


                bf.write("<it>");//bf.newLine();
                vect1=jGrafo.getFrstIt();
                for (int i = 0; i < vect1.length; i++) {
                    vect[i]=String.valueOf(vect1[i]);
                }
                bf.write(String.join(",", vect));
                bf.write("</it>");bf.newLine();


                if(jGrafo.getIteración()>=2){
                    bf.write("<it>");//bf.newLine();
                    vect1=jGrafo.getVisitados();
                    for (int i = 0; i < vect1.length; i++) {
                        vect[i]=String.valueOf(vect1[i]);
                    }
                    bf.write(String.join(",", vect));
                    bf.write("</it>");bf.newLine();
                }


                bf.write("</iter>");
            }
            //espacio para las iteraciones*/
            bf.newLine();
            bf.write("</cvgrafo>");
            bf.flush();
            bf.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ViewGrafo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaInfo;
    private javax.swing.JButton buttonAply;
    private javax.swing.JToggleButton buttonArrastre;
    private javax.swing.JButton buttonDialogExportSave;
    private javax.swing.JButton buttonGen;
    private javax.swing.JButton buttonIterar;
    private javax.swing.JButton buttonIterarEach;
    private javax.swing.JButton buttonRegen;
    private javax.swing.JButton buttonReorder;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonStop;
    private javax.swing.JCheckBox checkCVJ;
    private javax.swing.JCheckBox checkExcel;
    private javax.swing.JFileChooser chooser;
    private javax.swing.JDialog dialog;
    private javax.swing.JDialog dialogExport;
    private javax.swing.JTextField fieldSecs;
    private javax.swing.ButtonGroup groupIterar;
    private javax.swing.ButtonGroup groupMask;
    private jgraph.JGrafo jGrafo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelDialogCont;
    private javax.swing.JLabel labelDialogExportTitle;
    private javax.swing.JLabel labelDialogId;
    private javax.swing.JLabel labelDialogInfo;
    private javax.swing.JLabel labelDialogMask;
    private javax.swing.JLabel labelDialogTitle;
    private javax.swing.JLabel labelGenerar;
    private javax.swing.JLabel labelIteración;
    private javax.swing.JLabel labelMask;
    private javax.swing.JLabel labelNodos;
    private javax.swing.JLabel labelParámteros;
    private javax.swing.JLabel labelSecs;
    private javax.swing.JLabel labelSimulación;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JPanel panelDialog;
    private javax.swing.JPanel panelDialogExport;
    private javax.swing.JPanel panelOptions;
    private javax.swing.JPanel panelTitle;
    private javax.swing.JRadioButton radioIterarAuto;
    private javax.swing.JRadioButton radioIterarManual;
    private javax.swing.JRadioButton radioMask;
    private javax.swing.JRadioButton radioMaskNo;
    private javax.swing.JRadioButton radioMaskRandom;
    // End of variables declaration//GEN-END:variables
    
    public void initNodos(){
        for (Component nodo : jGrafo.getComponents()) {
            nodo.addMouseMotionListener(new java.awt.event.MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent me) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if(buttonArrastre.isSelected()){
                        me.translatePoint(nodo.getX()+5, nodo.getY()+5);
                        nodo.setLocation(me.getPoint().getLocation());
                        jGrafo.repaint();
                    }
                }

                @Override
                public void mouseMoved(MouseEvent me) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    }
    
}
