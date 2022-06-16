package pointedlist;

class Nodo<T>{
    T dato;
    Nodo<T> link;
    
    Nodo(){
        dato=null;
        link=null;
    }
    
    Nodo(T dato){
        this.dato=dato;
    }
}