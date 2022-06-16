package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.imageio.ImageIO;

public class Apoyo {
    
    public static final BufferedImage logo=imageFrom("/logo.png");
    public static final BufferedImage mask=imageFrom("/mask.png");
    public static final BufferedImage people=imageFrom("/people.png");
    public static final BufferedImage ar_dir=imageFrom("/ar_dir.png");
    public static final BufferedImage ar_not_dir=imageFrom("/ar_not_dir.png");
    public static final BufferedImage back=imageFrom("/back.png");
    public static final BufferedImage next=imageFrom("/next.png");
    public static final BufferedImage plus=imageFrom("/plus.png");
    public static final BufferedImage minus=imageFrom("/minus.png");
    public static final BufferedImage not=imageFrom("/not.png");
    public static final BufferedImage random=imageFrom("/random.png");
    
    private Apoyo(){}
    
    public static boolean randomBoolean(){
        return Math.random()>0.5;
    }
    
    public static int randomIntRange(int min, int max){
        if(min>max){
            min+=max;//tiene ambos
            max=min-max;//tiene el original min
            min-=max;//ahora tiene el original max
            //;v
        }
        
        return (int)(Math.random()*(max-min)+min);
    }
    
    public static double randomDoubleInRange(double min, double max){
        if(min>max){
            double aux=min;
            min=max;
            max=aux;
        }
        return (Math.random()*(max-min)+min);
    }
    
    public static double randomDistance(){
        return randomDoubleInRange(0, 4);
    }
    
    public static boolean booleanByProb(double i){
        double r=Math.random();
        return r<(i/100)? true:r==(i/100)? booleanByProb(i):false;
    }
    
    public static BufferedImage imageFrom(String ruta){
        try{
            return ImageIO.read(new File("src/res"+ruta));
        }catch(IOException e){
            System.out.println("awantaaaa");
            Logger.getLogger(Apoyo.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    public static double round(double num, int round){
        if(round<0) round=0;
        int t=(int)Math.pow(10, round);
        return ((double)(int)(num*t))/t;
    }
    
    public static jgraph.JGrafo grafoByText(String s){
        Matcher m;
        if(!(m=java.util.regex.Pattern.compile("<cvgrafo(((?=.* typeArista=(0|-1|1))(?=.* n=(\\d+))(?=.* typeMask=(0|-1|1))[^>]+))?>(.*)</cvgrafo>").matcher(s)).find())return null;
        
        int typeArista=Integer.parseInt(m.group(3));
        int typeMask=Integer.parseInt(m.group(5));
        String info=m.group(6);
        boolean msk[]=null , it[][]=null;
        double adj[][]=null;
        int n=Integer.parseInt(m.group(4));
        if((m=java.util.regex.Pattern.compile("<adj>((\\d+([.]\\d+)?,?){"+(n*n)+"})</adj>").matcher(info)).find()){
            String matriz[]=m.group(1).split(",");
            adj=new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    adj[i][j]=Double.parseDouble(matriz[i*n+j]);
                }
            }
        }
        if((m=java.util.regex.Pattern.compile("<vert>(((true|false),?){"+n+"})</vert>").matcher(info)).find()){
            String vector[]=m.group(1).split(",");
            msk=new boolean[n];
            for (int i = 0; i < n; i++) {
                msk[i]=Boolean.getBoolean(vector[i]);
            }
        }
        
        //aquí cuestiones de la iteración
        if((m=java.util.regex.Pattern.compile("<iter>((<it>(?=((true|false),?)*true)(((true|false),?){"+n+"})</it>)*)</iter>").matcher(info)).find()){
            m=java.util.regex.Pattern.compile("<it>(((true|false),?)*)</it>").matcher(m.group(1));
            int i=0;
            while(m.find()){
                i++;
            }
            m.reset();
            
            it=new boolean[i][n];
            
            for (int j = 0; j < i; j++) {
                m.find();
                String vector[]=m.group(1).split(",");
                for (int k = 0; k < vector.length; k++) {
                    it[j][k]=Boolean.parseBoolean(vector[k]);
                }
            }
        }
        
        //return jgraph.JGrafo.grafoByInfo(msk, adj, it, (byte)typeMask, (byte)typeArista);
        return jgraph.JGrafo.grafoByInfo(n, (byte)typeMask, (byte) typeArista, msk, adj, it);
    }
    
}