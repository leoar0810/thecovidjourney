package jgraph;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pointedlist.PointedList;
import persona.Persona;

/**
 *
 * @author Leonardo
 */
public class Nodo extends javax.swing.JButton{
    
    int id, rad;
    private Persona persona;
    private PointedList<Arista> aristas;
    
    Nodo(Persona p, int id){
        this(p);
        this.id=id;
    }
    
    @Override
    public void paint(java.awt.Graphics g){
        g.setColor(Color.black);
        g.setColor(persona.isContagiado()? Color.red : Color.green);
        g.fillOval(0, 0, 2*rad, 2*rad);
        g.setFont(getFont());
        g.setColor(Color.black);
        g.drawString(String.valueOf(id), 3, 2*rad-5);
        
        System.out.println("X: "+getX()+" , Y: "+getY());
    }
    
    Nodo(Persona p){
        persona=p;
        aristas=new PointedList();
        rad=10;
        java.awt.Dimension d= new java.awt.Dimension(2*rad,2*rad);
        setPreferredSize(d);
        setSize(d);
        setFont(new Font("Verdana",Font.BOLD,15));
        setOpaque(true);
        setLayout(null);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                showInfo();
            }
        });
        
        //this.setEnabled(true);
        //this.enableEvents();
    }
    
    public void showInfo(){
        JGrafo jGrafo=((JGrafo)getParent());
        if(!persona.isContagiado()){
            PointedList<Ruta> rutas=jGrafo.getRutasContagio(this);
            //PointedList<Ruta> rutas=jGrafo.getRutasContagioDijkstra(this);
            /*
            System.out.println("\nRutas:");
            for(Object o: rutas){
                PointedList<Nodo> rutaNodos=((Ruta)o).aristasToNodos();
                for(Object o2: rutaNodos){
                    System.out.print(((Nodo)o2).getId()+"\t");
                }
                System.out.println("");
            }
            */
            jGrafo.showRutasContagio(rutas, this);
        }else{
            PointedList<Arista> potContagios=jGrafo.getAristasPotContagios(this);
            /*
            System.out.println("\nPotenciales Contagios:");
            for(Object o: potContagios){
                System.out.print(((Nodo)o).getId()+"\t");
            }
            System.out.println("");
            */
            jGrafo.showPotContagios(potContagios, this);
        }
    }
    
    public Persona getPersona() {
        return persona;
    }
    
    
    public PointedList<Arista> getAristas(){
        return aristas;
    }
    
    public void setId(int id){
        this.id=id;
    }
    
    public void addArista(Arista a){
        aristas.add(a);
    }
    
    public int getId(){
        return id;
    }

    void contagiar() {
        persona.setContagiado(true);
    }
    
    public java.awt.Point getCenter(){
        return new java.awt.Point(this.getX()+rad, this.getY()+rad);
    }
    
    void setMask(boolean mask){
        persona.setMask(mask);
    }
    
    Nodo[] getAdyacentes(){
        int length=aristas.size();
        Nodo[] adyacentes=new Nodo[length];
        for(int i=0; i<length; i++){
            Arista a=aristas.get(i);
            adyacentes[i]=a.getExtremo1().equals(this)?a.getExtremo2():a.getExtremo1();
        }
        return adyacentes;
    }
    
    boolean points(Nodo nodo){
        for(Nodo n: getAdyacentes())
            if(n.equals(nodo)) return true;
            
        return false;
    }
}
