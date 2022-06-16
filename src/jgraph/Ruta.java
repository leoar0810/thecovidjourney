package jgraph;

import pointedlist.PointedList;

public class Ruta implements Comparable<Ruta>{
    
    Nodo start, end;
    PointedList<Arista> aristas;
    boolean iwt[], successful;
    
    Ruta(int numNodos, Nodo start, Nodo end){
        this.start=start;
        this.end=end;
        aristas=new PointedList();
        iwt=new boolean[numNodos];
        successful=false;
    }
    
    PointedList<Arista> aristasDispDe(Nodo n){
        PointedList<Arista> disponibles=new PointedList();
        for(Object o: n.getAristas()){
            Arista a=(Arista)o;
            Nodo n2=(a.getExtremo1().equals(n))? a.getExtremo2():a.getExtremo1();
            if(!hasNodo(n2)) disponibles.add(a);
        }

        return disponibles;
    }
    
    boolean hasArista(Arista arista){
        for(Object a: aristas)
            if(((Arista)a).equals(arista)) return true;

        return false;
    }

    boolean hasNodo(Nodo n){
        return iwt[n.getId()];
    }

    boolean isFull(){
        for(boolean b: iwt)
            if(!b) return false;
        
        return true;
    }
    
    void addArista(Arista a){
        iwt[a.getExtremo1().getId()]=iwt[a.getExtremo2().getId()]=true;
        aristas.add(a);
        /*
        if(a.getExtremo1().getPersona().isContagiado() || a.getExtremo2().getPersona().isContagiado())
            successful=true;
        */
        if(a.getExtremo1().equals(end) || a.getExtremo2().equals(end)) successful=true;
    }
    
    void setElementosDe(Ruta r2){
        for(Object o: r2.aristas)
            addArista((Arista)o);
    }
    
    static Ruta rutaFrom(Ruta r){
        Ruta ruta=new Ruta(r.iwt.length, r.start, r.end);
        for(Object o: r.aristas)
            ruta.addArista((Arista)o);
        
        return ruta;
    }
    
    public void print(){
        for(Object o: aristas){
            Arista a=(Arista)o;
            System.out.print(a.getExtremo1().getId()+"->"+a.getExtremo2().getId()+"\t");
        }
        System.out.println("");
    }
    
    public PointedList<Nodo> toNodos(){
        PointedList<Nodo> nodos=new PointedList();
        Nodo n=start;
        nodos.add(n);
        for(Object o: aristas){
            Arista a=(Arista)o;
            n=a.getExtremo1().equals(n)?a.getExtremo2():a.getExtremo1();
            nodos.add(n);
        }
        return nodos;
    }
    
    public Nodo[] toNodosVector(){
        int size=aristas.size();
        Nodo[] nodos=new Nodo[size+1];
        if(size==0) return nodos;
        Nodo n=start;
        nodos[0]=n;
        int i=0;
        for(Object o: aristas){
            Arista a=(Arista)o;
            n=a.getExtremo1().equals(n)?a.getExtremo2():a.getExtremo1();
            nodos[++i]=n;
        }
        return nodos;
    }
    
    public double pesoNeto(){
        double peso=0.0;
        for(Object o: aristas)
            peso+=((Arista)o).getPeso();
        
        return peso;
    }
    
    public double probContagio(){
        double prob=1.0;
        for(Object o: aristas)
            prob*=((Arista)o).probContagio()/100;
        
        return prob*100;
    }

    @Override
    public int compareTo(Ruta t) {
        double a=probContagio(), b=t.probContagio();
        return a>b? -1: a==b? 0: 1; //I know, wtf
    }
}