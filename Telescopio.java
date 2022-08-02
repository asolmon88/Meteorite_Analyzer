/**
* Clase Telescopio
*
* @author Ariel, Ximena
* @version 0.0
*/

public class Telescopio {

	public static final String[] nombreDirecciones = {"N", "NE", "E", "SE", "S", "SO", "O", "NO"};
	public static final int[] SUMA_F = {-1, -1, 0, 1, 1, 1, 0, -1};
	public static final int[] SUMA_C = {0, 1, 1, 1, 0, -1, -1, -1};

	private int meteoritos[][];
	private int matrizDeControl[][];
	private int matrizOriginal[][];
	private int matrizManchas[][];
	
	private int fondo;
	private Vector[] listaDeMeteoritos; 


	/**
	 * Constructor de la clase Telescopio, crea matriz original y matriz de control, la cual llena de -1
	 *
	 * @param meteoritos matriz
	 **/
	public Telescopio(int meteoritos[][]) {
		this.meteoritos = meteoritos;
		this.matrizOriginal = meteoritos;
		this.fondo = this.matrizOriginal[0][0];
		this.matrizDeControl = new int[meteoritos.length][meteoritos[0].length];
		this.matrizManchas = new int[meteoritos.length][meteoritos[0].length];

		for (int i = 0; i < matrizDeControl.length; ++i) {
			for (int j = 0; j < matrizDeControl[i].length; ++j) {
				this.matrizDeControl[i][j] = -1;

			}
		}
		
		for (int i = 0; i < matrizDeControl.length; ++i) {
			for (int j = 0; j < matrizDeControl[i].length; ++j) {
				this.matrizManchas[i][j] = 0;

			}
		}
	}


	/**
	 * Metodo cambiarMatriz
	 * 
	 **/

	public void cambiarMatriz() {
		int numBordeMeteorito = 1;
		
        this.meteoritos[0][0] = 0;
		this.matrizDeControl[0][0] = 0;
        identificarFondo(0, 0, this.fondo);
        boolean continuar = true;
        
        for(int i =0; i < this.meteoritos.length; ++i){
            for(int j = 0; j < this.meteoritos[i].length; ++j){
				if(this.matrizDeControl[i][j]>0){
					while(this.matrizDeControl[i][j] != 0){
						++j;
					}
				}
                else if(this.meteoritos[i][j] < 0){
					int color = this.meteoritos[i][j];
					identificarBorde(numBordeMeteorito, i, j, color);
					numBordeMeteorito++;
                }
            }
        }
	}

	/**
	 * Metodo buscarManchas
	 *
	 * @param null
	 * @return lista con precios
	**/

	public int[] buscarManchas(){

		Vector listaMeteoritos = crearListaMeteoritos();
		int[] preciosMeteoritos = new int[listaMeteoritos.getCantidad()]; 
		int fondoGeneral = matrizDeControl[0][0]; 
		
		//recorremos el largo de la lista de meteoritos
		for(int i = 0; i < preciosMeteoritos.length; ++i ){
			//recorremos las filas de la matriz de control
			for(int j = 0; j < matrizDeControl.length; ++j){
				//recorremos las columnas de la matriz de control
				for(int k = 0; k < matrizDeControl[j].length; ++k){

					//si en el color del pixel en el que estamos es diferente al color del fondo de la matriz
					if(this.meteoritos[j][k] == listaMeteoritos.getMeteorito(i).getBorde()){
						//se encontro borde de meteorito
						
						int bordeMeteorito = this.matrizDeControl[j][k];
						int fondoActual = 0;
						int filaActual = 0;
						int columnaActual = 0;
						int manchaActual = 0;
						for(int w = 0; w < this.nombreDirecciones.length; ++w){
							if(!verificarVecino(j, k, w, bordeMeteorito) && !verificarVecino(j, k, w, 0)){
								fondoActual = this.meteoritos[j+SUMA_F[w]][k+SUMA_C[w]];
								filaActual = j+SUMA_F[w];
								columnaActual = k+SUMA_C[w];
								w = this.nombreDirecciones.length;
							}
						}
						
						boolean continuar = true;
						while(continuar){
							if(posicionValida(filaActual, columnaActual)){
								if(this.meteoritos[filaActual][columnaActual] != fondoActual &&
								this.meteoritos[filaActual][columnaActual] != bordeMeteorito
								&& this.meteoritos[filaActual][columnaActual] != 0){
									manchaActual = this.meteoritos[filaActual][columnaActual];
									this.matrizDeControl[filaActual][columnaActual] = -10-i;
								}
								else if(this.meteoritos[filaActual][columnaActual] == bordeMeteorito){
									continuar = false;
								}
								++columnaActual;
							}
							else{
								continuar = false;
							}
						}
						
					}

				}

			}

		}

		for(int i = 0; i < preciosMeteoritos.length; ++i){

			for(int j = 0; j < matrizDeControl.length; ++j){
				for(int k = 0; k < matrizDeControl[j].length; ++k){
					if(this.matrizDeControl[j][k] == -10-i){
						this.matrizManchas[j][k] = -20-i;
						cambiarMancha(j, k, -10-i);
					}
				}

			}

		}

		// recorremos nuevamente la matriz
		for(int i = 0; i < preciosMeteoritos.length; ++i){
			preciosMeteoritos[i] = 0;
			for(int j = 0; j < matrizDeControl.length; ++j){
				for(int k = 0; k < matrizDeControl[j].length; ++k){

					if(this.matrizManchas[j][k] == -20-i){
						++preciosMeteoritos[i];
					}

				}

			}

		}

		return preciosMeteoritos;
	}
	
	/**
	* Metodo cambiarManchas
	*@param fila, columna, el color al que se va a cambiar
	*@return null
	*/

	public void cambiarMancha(int j, int k, int color){

		for(int d = 0; d < nombreDirecciones.length; ++d){
			if(verificarVecinoParaManchas(j, k, d, color)){
				this.matrizDeControl[j + SUMA_F[d]][k + SUMA_C[d]] = -1;
				cambiarMancha(j + SUMA_F[d], k + SUMA_C[d], color);

			}

		}

	}
	
	/**
	 * Metodo identificarBorde
	 *
	 * @param int numBordeMeteorito, int i, int j, int color
	 * @return --null--
	 **/
	private void identificarBorde(int numBordeMeteorito, int i, int j, int color){
		for(int k = 0; k < nombreDirecciones.length; ++k){
			if(posicionValida(i+SUMA_F[k], j+SUMA_F[k])){
				if(verificarVecino(i, j, k, color)){
					this.meteoritos[i+SUMA_F[k]][j+SUMA_C[k]] = numBordeMeteorito;
					this.matrizDeControl[i+SUMA_F[k]][j+SUMA_C[k]] = numBordeMeteorito;
					identificarBorde(numBordeMeteorito, i+SUMA_F[k], j+SUMA_C[k], color);
				}
			}
		}
	}
	
	/**
	 * Metodo identificarBorde
	 *
	 * @param int numBordeMeteorito, int i, int j, int color
	 * @return --null--
	 **/
	private void identificarFondo(int i, int j, int color){
		for(int k = 0; k < nombreDirecciones.length; ++k){
			if(k%2 == 0){
				if(posicionValida(i+SUMA_F[k],j+SUMA_C[k])){
					if(verificarFondo(i, j, k, color)){
						identificarFondo(i+SUMA_F[k], j+SUMA_C[k], color);
					}
				}
			}
		}
	}
	 
	/**
	 * Metodo verificarVecino
	 *
	 * @param int fila, int columna, int direccion, int color
	 * @return boolean coinciden
	 **/
	private boolean verificarVecino(int fila, int columna, int direccion, int color){
		boolean coinciden = false;
		if(posicionValida(fila + SUMA_F[direccion], fila + SUMA_F[direccion])){
			if (this.meteoritos[fila + SUMA_F[direccion]][columna + SUMA_C[direccion]] == color) {
				coinciden = true;
			}
		}
		return coinciden;
	}
	
	/**
	 * Metodo verificarVecinoParaManchas
	 *
	 * @param int fila, int columna, int direccion, int color
	 * @return boolean coinciden
	 **/
	private boolean verificarVecinoParaManchas(int fila, int columna, int direccion, int color){
		boolean coinciden = false;
		if(posicionValida(fila + SUMA_F[direccion],columna + SUMA_C[direccion])){
			if (this.matrizDeControl[fila + SUMA_F[direccion]][columna + SUMA_C[direccion]] == color) {
				coinciden = true;
			}
		}
		
		return coinciden;
	}
	
	/**
	 * Metodo verificarFondo
	 *
	 * @param int fila, int columna, int direccion, int color
	 * @return boolean coinciden
	 **/
	private boolean verificarFondo(int fila, int columna, int direccion, int color){

		int numFondo = 0;
		boolean coinciden = false;

		if(this.meteoritos[fila + SUMA_F[direccion]][columna + SUMA_C[direccion]] == color){
			this.matrizDeControl[fila + SUMA_F[direccion]][columna + SUMA_C[direccion]] = numFondo;
			this.meteoritos[fila + SUMA_F[direccion]][columna + SUMA_C[direccion]] = numFondo;
			coinciden = true;
		}
		return coinciden;
	}
	/**
	 * Metodo posicionValida
	 *
	 * @param int fila, int columna
	 * @return boolean valida
	 **/
	private boolean posicionValida(int fila, int columna){
		boolean valida = false;
		if(fila >= 0 && columna >= 0){
			if(fila < this.meteoritos.length && columna < this.meteoritos[fila].length){
				valida = true;
			}
		}
		return valida;
	}
	/**
	 * Metodo calcularPeso
	 *
	 * @param --null--
	 * @return peso
	 **/
	public int[] calcularPeso(){
		Vector meteoritos = crearListaMeteoritosCompeta();
		int[] peso = new int [meteoritos.getCantidad()];
		int bordeActual = 0;
		
		for(int w = 0; w < peso.length; ++w){
			peso[w] = 0;
		}
		
		for(int k =0; k < meteoritos.getCantidad(); ++k){
			bordeActual = meteoritos.getMeteorito(k).getBorde();
			for(int i =0; i < this.meteoritos.length; ++i){
				for(int j = 0; j < this.meteoritos[i].length; ++j){
					if(this.matrizDeControl[i][j] == bordeActual){
						while(this.matrizDeControl[i][j] != 0){
							peso[k] += 1;
							++j;
						}
					}
				}
			}
		}
		return peso;
	}
	/**
	 * Metodo crearListaMeteoritosCompeta
	 *
	 * @param --null--
	 * @return meteoritos
	 **/
	public Vector crearListaMeteoritosCompeta(){
		Vector meteoritos = identificarLados();
		return meteoritos;
	}
	/**
	 * Metodo crearListaMeteoritos
	 *
	 * @param --null--
	 * @return meteoritos
	 **/
	private Vector crearListaMeteoritos(){ //solo con borde
		Vector meteoritos = new Vector();
		int bordeActual = 0;
		boolean esta = false;
		
		for(int i = 0; i < this.meteoritos.length; ++i){
			for(int j = 0; j < this.meteoritos[i].length; ++j){
				if(this.meteoritos[i][j]>0){
					bordeActual = this.meteoritos[i][j];
					if(meteoritos.getCantidad() == 0){
						Meteorito meteorito = new Meteorito(bordeActual,0,i+1,j+1);
						meteoritos.add(meteorito);
					}
					else{
						esta = false;
						for(int w = 0; w < meteoritos.getCantidad(); ++w){
							Meteorito meteoritoActual = meteoritos.getMeteorito(w);
							if(meteoritoActual.getBorde() == bordeActual){
								esta = true;
							}
						}
						
						if(esta == false){
							Meteorito meteorito = new Meteorito(bordeActual);
							meteoritos.add(meteorito);
						}
					}
				}
			}
		}
		//listaDeMeteoritos = meteoritos.getListaDeMeteoritos();
		return meteoritos;
	}
	/**
	 * Metodo identificarLados
	 *
	 * @param --null--
	 * @return meteoritos
	 **/
	public Vector identificarLados(){
		Vector meteoritos = crearListaMeteoritos();
		int[] ladoActual;
		int bordeActual = 0;
		
		int[] esquinaArribaIzq = new int[2];
		int[] esquinaAbajoDer = new int[2];
		
		for(int i = 0; i < meteoritos.getCantidad(); ++i){
			bordeActual = meteoritos.getMeteorito(i).getBorde();
			meteoritos.getMeteorito(i).setLadoArriba(buscarLadoLimite(bordeActual, 0));
			meteoritos.getMeteorito(i).setLadoDer(buscarLadoLimite(bordeActual, 2));
			meteoritos.getMeteorito(i).setLadoAbajo(buscarLadoLimite(bordeActual, 4));
			meteoritos.getMeteorito(i).setLadoIzq(buscarLadoLimite(bordeActual, 6));
			
			esquinaArribaIzq[0] = meteoritos.getMeteorito(i).getLadoArriba()[0];
			esquinaArribaIzq[1] = meteoritos.getMeteorito(i).getLadoIzq()[1];
			
			esquinaAbajoDer[0] = meteoritos.getMeteorito(i).getLadoAbajo()[0];
			esquinaAbajoDer[1] = meteoritos.getMeteorito(i).getLadoDer()[1];
			
			int[] centro = calcularCentro(esquinaArribaIzq, esquinaAbajoDer);
			
			meteoritos.getMeteorito(i).setCentro(centro);
		}
		
		
		
		return meteoritos;
	}
	/**
	 * Metodo buscarLadoLimite
	 *
	 * @param int borde, int direccion
	 * @return int[] lado
	 **/
	private int[] buscarLadoLimite(int borde, int direccion){
		int[] lado = new int [2];
		lado[0] = 0;
		lado[1] = 0;
		
		for(int i = 0; i < this.meteoritos.length; ++i){
			for(int j = 0; j < this.meteoritos[i].length; ++j){
				if(this.meteoritos[i][j] == borde){
					if(lado[1] == 0){
						lado[0] = i;
						lado[1] = j;
					}
					//lado arriba
					else if(direccion == 0 && lado [0] > i){
						lado[0] = i;
					}
					//lado derecho
					else if(direccion == 2 && lado [1] < j){
						lado[1] = j;
					}
					//lado abajo
					else if(direccion == 4 && lado [0] < i){
						lado[0] = i;
					}
					//lado derecho
					else if(direccion == 6 && lado [1] > j){
						lado[1] = j;
					}
				}
			}
		}
		return lado;
	}
	/**
	 * Metodo calcularCentro
	 *
	 * @param int esquinaArribaIzq[], int esquinaAbajoDer[]
	 * @return int[] centro
	 **/
	private int[] calcularCentro(int esquinaArribaIzq[], int esquinaAbajoDer[]){
		int[] centro = new int[2];
		
		centro[0] = (esquinaArribaIzq[0] + esquinaAbajoDer[0])/2;
		centro[1] = (esquinaArribaIzq[1] + esquinaAbajoDer[1])/2;
		return centro;
	}
	/**
	 * Metodo mira
	 *
	 * @param int borde, int m[][]
	 * @return m
	 **/
	public int[][] mira(int borde, int m[][]){
		Vector meteoritos = crearListaMeteoritosCompeta();
		int[] esquinaArribaIzq = new int[2];
		int[] esquinaArribaDer = new int[2];
		int[] esquinaAbajoIzq = new int[2];
		int[] esquinaAbajoDer = new int[2];
		Meteorito meteoritoActual = meteoritos.getMeteorito(borde-1);
		
		
		esquinaArribaIzq[0] = meteoritoActual.getLadoArriba()[0];
		esquinaArribaIzq[1] = meteoritoActual.getLadoIzq()[1];
		
		esquinaArribaDer[0] = meteoritoActual.getLadoArriba()[0];
		esquinaArribaDer[1] = meteoritoActual.getLadoDer()[1];
		
		esquinaAbajoIzq[0] = meteoritoActual.getLadoAbajo()[0];
		esquinaAbajoIzq[1] = meteoritoActual.getLadoIzq()[1];
		
		esquinaAbajoDer[0] = meteoritoActual.getLadoAbajo()[0];
		esquinaAbajoDer[1] = meteoritoActual.getLadoDer()[1];
		
		m[esquinaArribaIzq[0]][esquinaArribaIzq[1]] = 0;
		int j = 0;
		int k = 1;
		while(j < 4){
			if(j < 2){
				m[esquinaArribaIzq[0]][esquinaArribaIzq[1]+k] = 0;
				++k;
			}
			else{
				--k;
				m[esquinaArribaIzq[0]+k][esquinaArribaIzq[1]] = 0;
			}
			++j;
		}
		
		m[esquinaArribaDer[0]][esquinaArribaDer[1]] = 0;
		j = 0;
		while(j < 4){
			if(j < 2){
				m[esquinaArribaDer[0]][esquinaArribaDer[1]-k] = 0;
				++k;
			}
			else{
				--k;
				m[esquinaArribaDer[0]+k][esquinaArribaDer[1]] = 0;
			}
			++j;
		}
		
		m[esquinaAbajoIzq[0]][esquinaAbajoIzq[1]] = 0;
		j = 0;
		while(j < 4){
			if(j < 2){
				m[esquinaAbajoIzq[0]][esquinaAbajoIzq[1]+k] = 0;
				++k;
			}
			else{
				--k;
				m[esquinaAbajoIzq[0]-k][esquinaAbajoIzq[1]] = 0;
			}
			++j;
		}
		
		m[esquinaAbajoDer[0]][esquinaAbajoDer[1]] = 0;
		j = 0;
		while(j < 4){
			if(j < 2){
				m[esquinaAbajoDer[0]][esquinaAbajoDer[1]-k] = 0;
				++k;
			}
			else{
				--k;
				m[esquinaAbajoDer[0]-k][esquinaAbajoDer[1]] = 0;
			}
			++j;
		}
		
		int[] centro = calcularCentro(esquinaArribaIzq, esquinaAbajoDer);
		m[centro[0]][centro[1]] = 0;
		j = 0;
		while(j < 8){
			if(j < 2){
				m[centro[0]][centro[1]+k] = 0;
				++k;
			}
			else if(j > 1 && j < 4){
				--k;
				m[centro[0]-k][centro[1]] = 0;
			}
			else if(j > 3 && j < 6){
				m[centro[0]][centro[1]-k] = 0;
				++k;
			}
			else if(j > 5){
				--k;
				m[centro[0]+k][centro[1]] = 0;
			}
			++j;
		}
		
		return m;
	}
	/**
	 * Metodos get
	 **/
	public int[][] getMeteoritos(){
		return this.meteoritos;
	}
	
	public int[][] getMatrizControl(){
		return this.matrizDeControl;
	}
	
	public int[][] getMatrizOriginal(){
		return this.matrizOriginal;
	}
	
}
