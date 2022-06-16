package jgraph;

public class Arista implements Comparable<Arista>{
    double peso;
    boolean dirigida;
    Nodo extremo1,extremo2;
    
    public Arista(double peso){
        this.peso=peso;
    }
    
    public Arista(Nodo extremo1, Nodo extremo2,boolean dirigida,double peso){
        this.extremo1=extremo1;
        extremo1.addArista(this);
        this.extremo2=extremo2;
        this.dirigida=dirigida;
        this.peso=peso;
        if(!dirigida) this.extremo2.addArista(this);
    }
    
    public Nodo getExtremo1(){
        return extremo1;
    }
    
    public Nodo getExtremo2(){
        return extremo2;
    }
    
    public boolean isDirigida(){
        return dirigida;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public void paint(java.awt.Graphics g) {
        java.awt.Point p1 = extremo1.getCenter(), p2 = extremo2.getCenter();
        double dx, dy, x=p1.x, y=p1.y;
        dx = (p2.x - p1.x);
        dy = (p2.y - p1.y);

        double R = p1.distance(p2);
        R = (double) extremo1.rad / R;

        //*
        p1.move((int)(p2.x-2*dx*R),(int)(p2.y-2*dy*R));
        p2.move((int)(p2.x-dx*R),(int)(p2.y-dy*R));
        paintFlecha(g,p1,p2);
        p2=extremo1.getCenter();
        if(dirigida)p2.move((int)(p2.x+dx*R),(int)(p2.y+dy*R));
        else p2.move((int)(p2.x+2*dx*R),(int)(p2.y+2*dy*R));
        g.drawLine(p1.x,p1.y,p2.x,p2.y);
        
        if(!dirigida){
            p1.move((int)(p2.x-dx*R),(int)(p2.y-dy*R));
            paintFlecha(g,p2,p1);
        }
        
        //*/
        
        
        //V1.0
        /*
        p1.move((int) (p1.x + dx * R), (int) (p1.y + dy * R));
        p2.move((int) (p2.x - dx * R), (int) (p2.y - dy * R));
        g.setColor(java.awt.Color.black);
        ((java.awt.Graphics2D) g).setStroke(new java.awt.BasicStroke(2));
        g.drawLine(p1.x, p1.y, p2.x, p2.y);

        // pinta los triangulos
        pTriangulo(g, p1.x, p2.x, p1.y, p2.y);

        if (!dirigida) {
            pTriangulo(g, p1.x + (int) (dx / 6), p1.x, p1.y + (int) (dy / 6), p1.y);

        }//*/
    }

    private void paintFlecha(java.awt.Graphics g, java.awt.Point p1, java.awt.Point p2){
        
        int x1,x2,x3,y1,y2,y3;
        double Dx=p2.x-p1.x , Dy= p2.y - p1.y;
        double d=p1.distance(p2);
        Dx= 10*Dx/d ; Dy=10*Dy/d;
        x2=(int)(p1.x+Dx); y2=(int)(p1.y+Dy);
        x1=(int)(p1.x+Dy/2) ; y1=(int)(p1.y-Dx/2);
        x3=(int)(p1.x-Dy/2) ; y3=(int)(p1.y+Dx/2);
        
        int[] xs={x1,x2,x3},ys={y1,y2,y3};
        //
        g.fillPolygon(xs, ys, 3);
        
        
    }
    
    private void pTriangulo(java.awt.Graphics g, int p1x, int p2x, int p1y, int p2y) {
        int vix1 = p1x;
        int vix2 = p2x;
        int viy1 = p1y;
        int viy2 = p2y;
        double h = vix2, k = viy2;
        double vx1 = vix1, vy1 = viy1, vx2 = vix2, vy2 = viy2;
        double m1 = (vy1 - vy2) / (vx1 - vx2);
        int radio = 90;
        if ((p2y > p1y && p1x > p2x) || (p2y < p1y && p1x > p2x)) {
            double b1 = vy2 - m1 * vx2;
            double x1 = (-1 * (-2 * h + 2 * m1 * b1 - 2 * m1 * k) + Math.sqrt(Math.pow(-2 * h + 2 * m1 * b1 - 2 * m1 * k, 2) - 4 * (1 + m1 * m1) * (-radio + b1 * b1 - 2 * b1 * k + k * k + h * h))) / (2 * (1 + Math.pow(m1, 2)));
            double y1 = m1 * x1 + b1;
            h = x1;
            k = y1;
            vx2 = x1;
            vy2 = y1;
            p2x = (int) x1;
            p2y = (int) y1;
        } else if ((p2y > p1y && p1x < p2x) || (p2y < p1y && p1x < p2x)) {
            double b1 = vy2 - m1 * vx2;
            double x1 = (-1 * (-2 * h + 2 * m1 * b1 - 2 * m1 * k) - Math.sqrt(Math.pow(-2 * h + 2 * m1 * b1 - 2 * m1 * k, 2) - 4 * (1 + m1 * m1) * (-radio + b1 * b1 - 2 * b1 * k + k * k + h * h))) / (2 * (1 + Math.pow(m1, 2)));
            double y1 = m1 * x1 + b1;
            h = x1;
            k = y1;
            vx2 = x1;
            vy2 = y1;
            p2x = (int) x1;
            p2y = (int) y1;
        }
        // se dibujan los traingulos si la pendiente no es cero

        if (vix1 != vix2 && viy1 != viy2) {
            double b1 = vy2 - m1 * vx2;
            double m2 = -1 / m1;
            double b2 = vy2 - m2 * vx2;
            double x1 = (-1 * (-2 * h + 2 * m1 * b1 - 2 * m1 * k) + Math.sqrt(Math.pow(-2 * h + 2 * m1 * b1 - 2 * m1 * k, 2) - 4 * (1 + m1 * m1) * (-radio + b1 * b1 - 2 * b1 * k + k * k + h * h))) / (2 * (1 + Math.pow(m1, 2)));
            double x2 = (-1 * (-2 * h + 2 * m1 * b1 - 2 * m1 * k) - Math.sqrt(Math.pow(-2 * h + 2 * m1 * b1 - 2 * m1 * k, 2) - 4 * (1 + m1 * m1) * (-radio + b1 * b1 - 2 * b1 * k + k * k + h * h))) / (2 * (1 + Math.pow(m1, 2)));
            double x3 = (-1 * (-2 * h + 2 * m2 * b2 - 2 * m2 * k) + Math.sqrt(Math.pow(-2 * h + 2 * m2 * b2 - 2 * m2 * k, 2) - 4 * (1 + m2 * m2) * (-radio + b2 * b2 - 2 * b2 * k + k * k + h * h))) / (2 * (1 + Math.pow(m2, 2)));
            double x4 = (-1 * (-2 * h + 2 * m2 * b2 - 2 * m2 * k) - Math.sqrt(Math.pow(-2 * h + 2 * m2 * b2 - 2 * m2 * k, 2) - 4 * (1 + m2 * m2) * (-radio + b2 * b2 - 2 * b2 * k + k * k + h * h))) / (2 * (1 + Math.pow(m2, 2)));
            double y1 = m1 * x1 + b1;
            double y2 = m1 * x2 + b1;
            double y3 = m2 * x3 + b2;
            double y4 = m2 * x4 + b2;
            if ((viy1 > viy2 && vix1 > vix2) || (viy1 < viy2 && vix1 > vix2)) {
                int[] dx2 = {(int) x2, (int) x3, (int) x4};
                int[] dy2 = {(int) y2, (int) y3, (int) y4};
                g.fillPolygon(dx2, dy2, 3);
            } else {
                int[] dx2 = {(int) x1, (int) x3, (int) x4};
                int[] dy2 = {(int) y1, (int) y3, (int) y4};
                g.fillPolygon(dx2, dy2, 3);
            }
        } else {
            // Esto está extenso pero por lo menos funciona(eso creo)
            if (vix1 == vix2) {
                if (viy1 > viy2) {
                    viy2 = (int) (viy1 + Math.sqrt(radio));
                } else {
                    viy2 = (int) (viy1 - Math.sqrt(radio));
                }
            } else {
                if (vix1 > vix2) {
                    vix2 = (int) (vix1 + Math.sqrt(radio));
                } else {
                    vix2 = (int) (vix1 - Math.sqrt(radio));
                }
            }

            if (vix1 == vix2) {
                if (viy1 > viy2) {
                    g.drawLine((int) (vix2 + Math.sqrt(radio)), (int) viy2, (int) (vix2 - Math.sqrt(radio)), (int) viy2);
                    g.drawLine((int) (vix2 + Math.sqrt(radio)), (int) viy2, (int) vix2, (int) (viy2 - Math.sqrt(radio)));
                    g.drawLine((int) (vix2 - Math.sqrt(radio)), (int) viy2, (int) vix2, (int) (viy2 - Math.sqrt(radio)));
                } else {
                    g.drawLine((int) (vix2 + Math.sqrt(radio)), (int) viy2, (int) (vix2 - Math.sqrt(radio)), (int) viy2);
                    g.drawLine((int) (vix2 + Math.sqrt(radio)), (int) viy2, (int) vix2, (int) (viy2 + Math.sqrt(radio)));
                    g.drawLine((int) (vix2 - Math.sqrt(radio)), (int) viy2, (int) vix2, (int) (viy2 + Math.sqrt(radio)));
                }
            } else {
                if (vix1 > vix2) {
                    g.drawLine((int) vix2, (int) (viy2 + Math.sqrt(radio)), (int) vix2, (int) (viy2 - Math.sqrt(radio)));
                    g.drawLine((int) vix2, (int) (viy2 + Math.sqrt(radio)), (int) (vix2 - Math.sqrt(radio)), (int) viy2);
                    g.drawLine((int) vix2, (int) (viy2 - Math.sqrt(radio)), (int) (vix2 - Math.sqrt(radio)), (int) viy2);
                } else {
                    g.drawLine((int) vix2, (int) (viy2 + Math.sqrt(radio)), (int) vix2, (int) (viy2 - Math.sqrt(radio)));
                    g.drawLine((int) vix2, (int) (viy2 + Math.sqrt(radio)), (int) (vix2 + Math.sqrt(radio)), (int) viy2);
                    g.drawLine((int) vix2, (int) (viy2 - Math.sqrt(radio)), (int) (vix2 + Math.sqrt(radio)), (int) viy2);
                }
            }
        }
        //g.drawLine((int) vix1, (int) viy1, (int) vix2, (int) viy2);
    }
    
    public double probContagio(){
        persona.Persona p1=extremo1.getPersona(), p2=extremo2.getPersona();
        return (p1.hasMask()? (p2.hasMask()? (peso>2)? 20:30 : peso>2? 30:40) : (p2.hasMask()? peso>2? 40:60 :peso>2? 80:90));
    }

    @Override
    public int compareTo(Arista t) {
        double a=probContagio(), b=t.probContagio();
        return a>b? -1: a==b? 0: 1; //I know, wtf
    }
    
    public Nodo getOtroExtremo(Nodo n){
        if(!n.equals(extremo1) && !n.equals(extremo2)) return null;
        return extremo1.equals(n)?extremo2:extremo1;
    }
}