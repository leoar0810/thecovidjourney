package main;

import java.awt.event.MouseEvent;

public class ZoneEvent extends javax.swing.JComponent{
    public static final int USERS=0, POSTS=1, COMMENTS=2, MIX=3, BACK=4, NEXT=5, SETTINGS=6;
    private int type;

    public ZoneEvent(int x, int y, int w, int h){
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                respuesta(evt);
            }
        });
        setBounds(x, y, w, h);
    }
    
    public ZoneEvent(int type, int x, int y, int w, int h){
        this(x, y, w, h);
        if(type<USERS || type>SETTINGS) type=0;
        this.type=type;
    }
    
    public void respuesta(MouseEvent evt){}
}