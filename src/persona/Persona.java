package persona;

import main.Apoyo;

public class Persona {
    public String nombres, apellidos, id, municipio;
    public Dirección dirección;
    public long lat, lng;
    public boolean mask,contagiado;
    public byte estrato;
    public float ingresosMensuales;
    public static int genId=2064681;

    public Persona(boolean mask){
        this.mask=mask;
        contagiado=false;
        id=String.valueOf(genId);
        genId+=Apoyo.randomIntRange(3621, 7206);
    }

    public boolean isContagiado() {
        return contagiado;
    }

    public void setContagiado(boolean contagiado) {
        this.contagiado = contagiado;
    }
    
    public boolean hasMask(){
        return mask;
    }
    
    public void setMask(boolean mask){
        this.mask=mask;
    }
    
    class Dirección{
        protected String calle, carrera, diagonal;
        
        public Dirección(String calle, String carrera, String diagonal){
            this.calle=calle;
            this.carrera=carrera;
            this.diagonal=diagonal;
        }
        
        public Dirección(String calle, String carrera){
            this(calle, carrera, null);
            
        }
    }
}