package jgraph;
import main.Apoyo;
import java.awt.Color;
import java.awt.Point;
import persona.Persona;
import pointedlist.PointedList;

/**
 *
 * @author Leonardo
 */
public class JGrafo extends javax.swing.JPanel{
    
    Nodo[] nodos;
    PointedList<Arista> aristas;
    int numNodos, typeGraph, typeMask, typeAristas, numContagiados, iteración;
    Nodo zeroPatient;
    private double matriz[][];
    public view.ViewGrafo viewGrafo;

    
    /**
     *Constantes para definir el tipo de grafo
     */
    public static final byte TYPE_CICLO=0, TYPE_COMPLETO=1, TYPE_BIPARTITO=2, TYPE_REGULAR=3, TYPE_CAMINO=4, TYPE_RUEDA=5,
            TYPE_RANDOM=6;
    
    /**
     * Constantes para los tipos de aristas
     */
    public static final byte ARISTAS_DIRIGIDAS=1, ARISTAS_NO_DIRIGIDAS=0, RANDOM_ARISTAS=-1;
    
    /**
     * 
     */
    public static final byte MASK=1, NO_MASK=0, RANDOM_MASK=-1;
    
    
    private boolean visitados[],frstIt[];//reservado para el recorrido
    
    
    public JGrafo(){
        this(5);
    }
    
    public JGrafo(int cant){
        this(RANDOM_MASK, RANDOM_ARISTAS, cant);
    }
    
    public JGrafo(byte typeMask, byte typeAristas, int cant){
        this.typeAristas=typeAristas;
        this.typeMask=typeMask;
        
        //---------------------------------------------------------------
        java.awt.Dimension d=new java.awt.Dimension(350,350);
        this.setPreferredSize(d);
        this.setSize(d);
        
        this.setOpaque(true);
        this.setBackground(Color.white);
        this.setBorder(null);
        setLayout(null);
        //this.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        numNodos=0;
        nodos=new Nodo[cant];
        matriz=new double[cant][cant];
        aristas=new PointedList();
        if(cant>=1) genGrafo(cant);
        
        up();
        numContagiados=0;
    }
    
    private JGrafo(boolean nuevo,int tam){
        
        //---------------------------------------------------------------
        if(nuevo){
           //this(tam); 
        }else{

            java.awt.Dimension d=new java.awt.Dimension(350,350);
            this.setPreferredSize(d);
            this.setSize(d);

            this.setOpaque(true);
            this.setBackground(Color.white);
            this.setBorder(null);
            setLayout(null);
            //this.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
            
            iteración=0;
            numNodos=0;
            nodos=new Nodo[tam];
            matriz=new double[tam][tam]; //Cuidado!!!
            aristas=new PointedList();
            //if(cant>=1) genGrafo(cant);

            //up();
            numContagiados=0;
            //*/
        }
    }
    
    @Override
    public void revalidate(){
        up();
        super.revalidate();
        //repaint();
    }
    
    @Override
    public void paint(java.awt.Graphics g){
        super.paint(g);
        for (Object arista : aristas) {
            ((Arista)arista).paint(g);
        }
    }
    
    public void up(){
        java.awt.Point positions[]=new java.awt.Point[numNodos];
        
        for (int i = 0; i < numNodos; i++) {
            boolean creado = false;
            while (!creado) {
                int x = (int) (Math.random() * (this.getWidth()-50)) + 25;
                int y = (int) (Math.random() * (this.getHeight()-50)) + 25;
                boolean verX = true, verY = true;
                for (Point position : positions) {
                    if (position != null) {
                        if ((position.getX() < x + 50 && position.getX() > x) || (position.getX() > x - 50 && position.getX() < x)) {
                            verX = false;
                        }
                        if ((position.getY() < y + 50 && position.getY() > y) || (position.getY() > y - 50 && position.getY() < y)) {
                            verY = false;
                        }
                    }
                }
                if (verX && verY) {
                    positions[i] = new java.awt.Point(x, y);
                    creado = true;
                    nodos[i].setLocation(x,y);
                }
            }

        }
    }
    
    /**
     * Realiza o inicializa una iteración de contagios.
     * 
     */
    public void iterar(){
        if(!hasSanos()) return;
        
        if(zeroPatient==null){
            zeroPatient=typeAristas==ARISTAS_NO_DIRIGIDAS?nodos[Apoyo.randomIntRange(0, numNodos-1)]:nodos[0];
            zeroPatient.contagiar();
            frstIt=new boolean[numNodos];
            frstIt[zeroPatient.getId()]=true;
            numContagiados++;
        }else{
            visitados=new boolean[numNodos];
            visitados[zeroPatient.getId()]=true;
            iterar(zeroPatient);
        }
        iteración++;
    }
    
    private void iterar(Nodo n){
        for (Object o: n.getAristas()) {
            Arista arista=(Arista)o;
            Nodo n2=arista.getExtremo1()==n? arista.getExtremo2() : arista.getExtremo1();
            
            int id=n2.getId();
            if(visitados[id]==false){
                if(n2.getPersona().isContagiado()){
                    visitados[id]=true;
                    iterar(n2);
                }else{
                    Persona p1=n.getPersona(),p2=n2.getPersona();
                    if(Apoyo.booleanByProb(arista.probContagio())){
                        p2.setContagiado(true);
                        visitados[id]=true;
                        numContagiados++;
                    }
                }
            }
        }
        
    }
    
    public void regen(int cant){
        regen((byte)typeMask, (byte)typeAristas, cant);
    }
    
    public void regen(byte typeMask, byte typeAristas, int cant){
        zeroPatient=null;
        this.removeAll();
        this.typeAristas=typeAristas;
        this.typeMask=typeMask;
        nodos=new Nodo[cant];
        matriz=new double[cant][cant];
        numNodos=0;
        aristas=new PointedList();
        genGrafo(cant);
        up();
        this.paintAll(getGraphics());
    }
    
    /**
     * Genera un grafo conexo con una determinada cantidad de nodos, 
     * si se quiere generar un nuevo grafo recomendamos usar el método
     * @see #regen(int) 
     * @param cant 
     * La cantidad de nodos del grafo
     */
    private void genGrafo(int cant){
        Nodo nodo=new Nodo(new Persona(booleanMask()));
        addNodo(nodo);
        genGrafo(nodo,(cant-1));
        aristasAdicionales();
        iteración=numContagiados=0;
    }
    
    private void genGrafo(Nodo nodo, int rest){
        if(rest>0){
            rest=caminoAlterno(nodo,rest);
            if(rest>0){
                Nodo nodo2=new Nodo(new Persona(booleanMask()));
                addNodo(nodo2);
                addArista(new Arista(nodo, nodo2, booleanArista(), Apoyo.randomDistance()));
                genGrafo(nodo2,(rest-1));
            }
        }
    }
    
    private int caminoAlterno(Nodo nodo, int rest){
        
        if(rest>0)
            if(Apoyo.randomBoolean()){
                Nodo nodo2=new Nodo(new Persona(booleanMask()));
                rest--;
                addNodo(nodo2);
                addArista(new Arista(nodo, nodo2, booleanArista(), Apoyo.randomDistance()));
                rest=caminoAlterno(nodo2,rest);
            }
        
        return rest;
    }
    
    private void aristasAdicionales(){
        
        //NoDirigido
        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                if(i!=j && matriz[i][j]==0 && matriz[j][i]==0){
                    if(Apoyo.booleanByProb(1)){
                        addArista(new Arista(nodos[i], nodos[j], booleanArista(), Apoyo.randomDistance()));
                    }
                }
            }
        }
        //NoDirigido
        
    }
    
    public boolean[] getMascarillas(){
        boolean r[]=new boolean[numNodos];
        for (int i = 0; i < numNodos; i++) {
            r[i]=nodos[i].getPersona().isContagiado();
        }
        
        return r;
    }
    
    public static JGrafo grafoByInfo(int n, byte typeMask, byte typeAristas,boolean[] msk,double[][] adj,boolean[][]it){
        
        JGrafo grafo=null;
        
        if(adj==null){
            grafo=new JGrafo(typeMask, typeAristas, n);
            if(typeMask==RANDOM_MASK && msk!=null){
                for (int i = 0; i < n; i++) {
                    grafo.nodos[i].getPersona().mask=msk[i];
                }
            }//Se completan n, TM-TA-msk-adj
        }else{
            grafo=new JGrafo(false, n);//lleva n
            for (int i = 0; i < n; i++) {//mask
                boolean m=typeMask==RANDOM_MASK? msk[i]: typeMask==MASK;//Lleva Msk
                grafo.addNodo(new Nodo(new Persona(msk[i])));
            }
            grafo.setTypeAristas(typeAristas);//lleva TA
            grafo.aristasByMatris(adj);//lleva adj
            grafo.setTypeMask(typeMask);// lleva TM
        }
        
        if(it!=null && it.length>0){
            
            for (int i = 0; i < it[0].length; i++) {
                if(it[0][i]){
                    grafo.zeroPatient=grafo.nodos[i];
                    break;
                }
            }
            
            
            grafo.setIteración(it.length);
            grafo.setVisitados(it[it.length-1]);
            grafo.setFrstIt(it[0]);
            
            for (int i = 0; i < it[0].length; i++) {
                if(it[it.length-1][i]){
                    grafo.nodos[i].contagiar();
                }
            }
        }
        
        return grafo;
    }
    
    private void aristasByMatris(double[][] adj){
        if(typeAristas==ARISTAS_NO_DIRIGIDAS){
            
            for (int j = 0; j < adj.length; j++) {
                for (int i = 0; i < j; i++) {
                    if(adj[i][j]!=0)addArista(new Arista(nodos[i], nodos[j], false, adj[i][j]));
                }
            }
            
        }else if(typeAristas==ARISTAS_DIRIGIDAS){
            
            for (int j = 0; j < adj.length; j++){
                for (int i = 0; i < j; i++) {
                    if(adj[i][j]!=0)addArista(new Arista(nodos[i], nodos[j], true, adj[i][j]));
                }
                for (int i = j+1; i < adj.length; i++) {
                    if(adj[i][j]!=0)addArista(new Arista(nodos[i], nodos[j], true, adj[i][j]));
                }
            } 
        }else{
            for (int j = 0; j < adj.length; j++) {
                for (int i = 0; i < adj.length; i++) {
                    if(i!=j)
                    if(i<j){
                        if(adj[i][j]!=0)addArista(new Arista(nodos[i], nodos[j], adj[i][j]!=adj[j][i], adj[i][j]));
                    }else{
                        if(adj[i][j]!=0 && adj[i][j]!=adj[j][i]) addArista(new Arista(nodos[i], nodos[j], true, adj[i][j]));
                    }
                }
            }
        }
    }
    
    public void updateMasks(byte typeMask){
        this.typeMask=typeMask;
        for(Object o: nodos)
            ((Nodo)o).setMask(typeMask==MASK?true:typeMask==NO_MASK?false:Apoyo.randomBoolean());
    }
    
    public PointedList<Arista> getAristasPotContagios(Nodo n){
        PointedList<Arista> potContagios=new PointedList();
        for(Object o: n.getAristas()){
            Arista a=(Arista)o;
            Nodo n2=(a.getExtremo1().equals(n))? a.getExtremo2():a.getExtremo1();
            if(!n2.getPersona().isContagiado()) potContagios.add(a);
        }
        return potContagios;
    }
    
    public PointedList<Ruta> getRutasContagio(Nodo nodo){
        PointedList<Ruta> rutas=new PointedList();
        for(Nodo n: nodos){
            if(n.getPersona().isContagiado()){
                Ruta r=new Ruta(numNodos, n, nodo);
                rutas.add(r);
                rutas=rutasContagio(n, nodo, r, rutas);
            }
        }
        PointedList<Ruta> rutas2=new PointedList();
        for(Object o: rutas){
            Ruta r2=(Ruta)o;
            if(r2.successful) rutas2.add(r2);
        }
        return rutas2;
    }
    
    private PointedList<Ruta> rutasContagio(Nodo origen, Nodo destino, Ruta r, PointedList<Ruta> rutas){
        //if(r.isFull()) return rutas;
        
        PointedList<Arista> aristasDisp=r.aristasDispDe(origen);
        int cont=0, size=aristasDisp.size();
        
        for(Object o: aristasDisp){
            Arista a=(Arista)o;
            Nodo n2=(a.getExtremo1().equals(origen))? a.getExtremo2():a.getExtremo1();
            Ruta r2=null;
            if(cont<size-1){
                r2=Ruta.rutaFrom(r);
                //r2=new Ruta(numNodos);
                rutas.add(r2);
                //r2.setElementosDe(r);
                
            }
            r.addArista(a);
            if(!n2.equals(destino) && !n2.getPersona().isContagiado()) rutasContagio(n2, destino, r, rutas);
            if(r2!=null) r=r2;
            
            cont++;
        }
        
        return rutas;
    }
    /*
    private PointedList<Ruta> rutasContagio(Nodo n, Ruta r, PointedList<Ruta> rutas){
        //if(r.isFull()) return rutas;
        
        PointedList<Arista> aristasDisp=r.aristasDispDe(n);
        int cont=0, size=aristasDisp.size();
        
        for(Object o: aristasDisp){
            Arista a=(Arista)o;
            Nodo n2=(a.getExtremo1().equals(n))? a.getExtremo2():a.getExtremo1();
            Ruta r2=null;
            if(cont<size-1){
                r2=Ruta.rutaFrom(r);
                //r2=new Ruta(numNodos);
                rutas.add(r2);
                //r2.setElementosDe(r);
                
            }
            r.addArista(a);
            if(!n2.getPersona().isContagiado()) rutasContagio(n2, r, rutas);
            if(r2!=null) r=r2;
            
            cont++;
        }
        
        return rutas;
    }
    */
    public void showRutasContagio(PointedList<Ruta> rutas, Nodo n){
        if(viewGrafo!=null) viewGrafo.showRutasContagio(rutas, n);
    }
    
    public void showPotContagios(PointedList<Arista> potContagios, Nodo n){
        if(viewGrafo!=null) viewGrafo.showPotContagios(potContagios, n);
    }
    
    /*
    public PointedList<Ruta> getRutasContagioDijkstra(Nodo nodo){
        if(!hasContagiados()) return null;
        
        PointedList<Ruta> rutasContagio=new PointedList();
        for(Object o: nodos){
            Nodo n=(Nodo)o;
            for(Ruta r: dijkstra(n)){
                Nodo[] r2=r.toNodosVector();
                if(r2[r2.length-1].equals(nodo)){
                    rutasContagio.add(r);
                }
            }
        }
        return rutasContagio;
    }
    
    private Ruta[] dijkstra(Nodo origen){
        PointedList<Ruta> rutas=new PointedList();
        double[][] di=new double[numNodos][numNodos-1];
        int[][] an=new int[numNodos][numNodos-1];
        boolean[] visited=new boolean[numNodos];
        for(int i=0; i<numNodos; i++){
            visited[i]=false;
            for(int j=0; j<numNodos-1; j++){
                di[i][j]=an[i][j]=-1;
            }
        }
        int id=origen.getId();
        di[id][id]=0;
        an=dijkstra(0, id, di, an, visited, 0);
        for(int i=0; i<numNodos; i++){
            System.out.print(an[i][numNodos-2]+" ");
        }System.out.println("");
        
        for(int i=0; i<numNodos-1; i++){
            int ant, pos=i;
            Ruta r=new Ruta(numNodos, nodos[pos]);
            do{
                ant=an[pos][numNodos-2];
                boolean found=false;
                for(Object o: nodos[pos].getAristas()){
                    Arista a=(Arista)o;
                    Nodo n=a.getOtroExtremo(nodos[pos]);
                    if(n.equals(nodos[ant])){
                        found=true;
                        r.addArista(a);
                        break;
                    }
                }
                if(!found){
                    System.out.println("tschale");
                    for(Object o: nodos[ant].getAristas()){
                        Arista a=(Arista)o;
                        Nodo n=a.getOtroExtremo(nodos[ant]);
                        if(n.equals(nodos[pos])){
                            found=true;
                            r.addArista(a);
                            break;
                        }
                    }
                }
                if(!found) System.out.println(":'c");
                pos=ant;
            }while(id!=ant);
        }
        int size=rutas.size();
        Ruta[] rutasVector=new Ruta[size];
        int i=size-1;
        for(Object o: rutas){
            ((Ruta)o).print();
            rutasVector[i]=(Ruta)o;
            i--;
        }
        return rutasVector;
    }
    
    private int[][] dijkstra(double dist, int pos, double[][] di, int[][] an, boolean[] visited, int it){
        if(it>numNodos-2) return an;
        
        visited[pos]=true;
        double dist2=5.0;
        int pos2=-1;
        for(int i=0; i<numNodos; i++){
            if(!visited[i]){
                if(matriz[pos][i]>0 && (di[i][it]==-1 || dist+matriz[pos][i]<Math.abs(di[i][it]))){
                    di[i][it]=dist+matriz[pos][i];
                    an[i][it]=pos;
                }
                if(di[i][it]!=-1 && di[i][it]<dist2){
                    dist2=di[i][it];
                    pos2=i;
                }
            }
        }
        if(dist2==5.0 || pos2==-1){
            System.out.println("noooooooooooooooooooooooooooooooo");
            dist2=dist; pos2=pos;
        }
        return dijkstra(dist2, pos2, di, an, visited, it+1);
    }
    */
    
    public void addNodo(Nodo nodo){
        nodos[numNodos]=nodo;
        nodo.setId(numNodos++);
        add(nodo);
    }
    
    public void addArista(Arista a){
        int id1=a.getExtremo1().getId(),id2=a.getExtremo2().getId();
        matriz[id1][id2]=a.peso;
        if(!a.isDirigida())matriz[id2][id1]=a.peso;
        aristas.add(a);
    }

    public int getTypeGraph() {
        return typeGraph;
    }

    public void setTypeGraph(int typeGraph) {
        this.typeGraph = typeGraph;
    }

    public int getTypeMask() {
        return typeMask;
    }

    public void setTypeMask(int typeMask) {
        this.typeMask = typeMask;
    }

    public int getTypeAristas() {
        return typeAristas;
    }

    public void setTypeAristas(int typeAristas) {
        this.typeAristas = typeAristas;
    }

    public double[][] getMatriz() {
        return matriz;
    }

    public int getNumNodos() {
        return numNodos;
    }
    
    public boolean hasSanos(){
        return numContagiados<numNodos;
    }
    
    public boolean hasContagiados(){
        return numContagiados>0;
    }

    public Nodo[] getNodos() {
        return nodos;
    }

    public void setNodos(Nodo[] nodos) {
        this.nodos = nodos;
    }

    public PointedList<Arista> getAristas() {
        return aristas;
    }

    public void setAristas(PointedList<Arista> aristas) {
        this.aristas = aristas;
    }

    public int getNumContagiados() {
        return numContagiados;
    }

    public void setNumContagiados(int numContagiados) {
        this.numContagiados = numContagiados;
    }

    public int getIteración() {
        return iteración;
    }

    public void setIteración(int iteración) {
        this.iteración = iteración;
    }

    public Nodo getZeroPatient() {
        return zeroPatient;
    }

    public void setZeroPatient(Nodo zeroPatient) {
        this.zeroPatient = zeroPatient;
    }

    public boolean[] getVisitados() {
        return visitados;
    }

    public boolean[] getFrstIt() {
        return frstIt;
    }

    public void setFrstIt(boolean[] frstIt) {
        this.frstIt = frstIt;
    }
    
    public void setVisitados(boolean[] visitados) {
        this.visitados = visitados;
    }
    
    private boolean booleanMask(){
        return (typeMask==MASK)?true:(typeMask==NO_MASK)?false:Apoyo.randomBoolean();
    }
    
    private boolean booleanArista(){
        return (typeAristas==ARISTAS_DIRIGIDAS)?true:(typeAristas==ARISTAS_NO_DIRIGIDAS)?false:Apoyo.randomBoolean();
    }
}
