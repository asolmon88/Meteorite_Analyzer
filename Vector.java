/**
* Clase vector, vista en clase, de apoyo
*
*
*/

public class Vector {
    public static final int DEFAULT_SIZE = 100;
    
    private Meteorito v[];
    private int cantidad;
    
    public Vector(){
       cantidad = 0;
       v = new Meteorito [DEFAULT_SIZE];
    }
    
    public Vector(int length){
      cantidad = 0;
      v = new Meteorito [length];
    }

    public void add(Meteorito meteorito){
      if(cantidad == v.length){
        Meteorito nuevo[] = new Meteorito [ 2 * v.length + 1];
          for(int i=0; i<v.length; ++i){
             nuevo[i] = v[i]; 
          }
          v = nuevo;
      }
      v[cantidad++]= meteorito;
    }
    /*public String toString(){
        String hilera = "{";
        if(cantidad>0){
           hilera+= v[0];
        }    
        for(int i=1;i < cantidad; ++i){
           hilera+=","+v[i];
        }
        return hilera+"}";
    }*/
    
    // Retorna -1 si el número no está
    // Retorna la posición ( indice ) donde lo encontró si está
    /*public int buscar(int valor){
        int posicion = 0;
        for(int i = 0; i < v.length; i++){
            if (v[i] == valor){
                posicion = i;
            }
            else{
                posicion = -1;
            }
        }
        return posicion;
    }*/
    
    /*public int getValor(int pos){
        Meteorito meteorito;
        if(pos > v.length-1){
            valor = 0;
        }
        else{
            valor = v[pos];
        }
        return valor;
    }*/
	
	public Meteorito getMeteorito(int pos){
        Meteorito meteorito = v[pos];
        return meteorito;
    }
    
	public int getCantidad(){
		return this.cantidad;
	}

	public Meteorito[] getListaDeMeteoritos(){
        return this.v;
    }
}


















