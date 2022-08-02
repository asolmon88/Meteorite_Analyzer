/**
 * clase Nave
 *
 * @author Kirsly Arias, Ximena Mendez
 * @version 00
 */
public class Nave {
    /**
     * Atributos privados de la clase Nave
     */
    private final Meteorito[] meteoritos;
    private final int CARGA_MAXIMA = 2000;
    private int cargaActual;
    private int peso;
    private int valor;
	int pesoTotal=0;
	int precioTotal=0;

    /**
     * Constructor de la clase Nave
     */
    public Nave() {
		meteoritos = new Meteorito [10];
    }

    /**
     * Metodo que escoge los meteoritos a llevar en la nave, segun peso y valor
     *
     * @param int CARGA_MAXIMA, int peso[], int precio[]
     * @return Devuelve array con posicion de los meteoritos elegidos (de mayor a menor )y -1 en caso que no fuera elegido
     */
	 		
    public static int[] elegirMeteoritos(int CARGA_MAXIMA, int peso[], int precio[]){
        int i;
		int j;
		int numMeteoritos = precio.length;
		int matriz[][] = new int[numMeteoritos+1][CARGA_MAXIMA+1];
        int[] seleccionados = new int[numMeteoritos + 3];

		// Hacer matriz
		for (i = 0; i <= numMeteoritos; i++){
			for (j = 0; j <= CARGA_MAXIMA; j++){
				
				
				if (i==0 || j==0){
					seleccionados[i] = 1;
					matriz[i][j] = 0;
				}
				else if (peso[i-1] <= j){
					int seIncluye = precio[i-1] + matriz[i-1][j-peso[i-1]];
					int noSeIncluye = matriz[i-1][j];
					seleccionados[i] = 1;
					
					if ((seIncluye) > (noSeIncluye)){
						matriz[i][j] = seIncluye;	
					} else {
						matriz[i][j] = noSeIncluye;
					}
				}
				else{
					seleccionados[i]=0;
					matriz[i][j] = matriz[i-1][j];
				}
			}
		}
		int cargaM = CARGA_MAXIMA;
		int y = 0; 
		
		for (int x = numMeteoritos; x > 0; x--){
			if ((cargaM-peso[x-1] >= 0)   &&   (matriz[x][cargaM] - matriz[x-1][cargaM-peso[x-1]] == precio[x-1]) ){
				seleccionados[y++] = x-1; 
				cargaM-=peso[x-1];
			}
		}
		int pesoTotal=0;
		int precioTotal=0;
		for(int e = y-1; e >= 0; e--){
			//System.out.println("Meteorito: "+seleccionados[e]+", precio: "+precio[seleccionados[e]]+", peso: "+peso[seleccionados[e]]);
			pesoTotal = pesoTotal + peso[seleccionados[e]];
			precioTotal = precioTotal + precio[seleccionados[e]];
		}
		for(int e = y; e <= numMeteoritos; e++){
			seleccionados[e] = -1;
		}
		
		seleccionados[seleccionados.length-1] = precioTotal;
		seleccionados[seleccionados.length-2] = pesoTotal;
		/*System.out.println("precio="+precioTotal);
		System.out.println("peso="+pesoTotal);
		System.out.println("peso="+Arrays.toString(seleccionados));*/
		return seleccionados;
	}

	
	public static void imprimirInventario(int CARGA_MAXIMA, int pesoTotal, int precioTotal,int peso[], int precio[]){
		System.out.println("INVENTARIO DE CARGA DE LA NAVE\n");
		System.out.print("\tCapacidad maxima: "+CARGA_MAXIMA); 
		System.out.print("\tCarga actual: "+pesoTotal);
		System.out.print("\tValor de la carga: "+pesoTotal);
		System.out.print("\n "+"\t ID"+"\tValor"+"\tPeso"+"\t(Fila,Columna)");
	}
	
}