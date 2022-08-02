/**
 * clase Meteorito
 *
 * @author Arias Krisly, Ariel Solis
 * @version 0.1
 */

public class Meteorito{

	/**Atributos**/
	private int borde;
	
	private int[] ladoIzquierdo = new int [2];  //para agregar la mira. vector con fila y columna
	private int[] ladoDerecho = new int [2];
	private int[] ladoArriba = new int [2]; 
	private int[] ladoAbajo = new int [2];
	private int[] centro = new int [2];

	private int manchas;       //para calcular valor
	private int peso;
	public int x;
	public int y;
	private int precio;
	
	
	/**
     * Metodo Constructor
	 * 
     *@param  fondo, borde, manchas, pixeles
     *@return null
	**/
	
	public Meteorito(int borde){
		this.borde = borde;
	}
	public Meteorito(int borde, int peso, int x, int y){
		this.borde = borde;
		this.peso = peso;
		this.x = x;
		this.y = y;
	}
	
	/**
     * Metodos Sets y Gets
	 *
	**/
	
	public void setLadoIzq(int v[]){
		this.ladoIzquierdo = v;
	}
	
	public void setLadoDer(int v[]){
		this.ladoDerecho = v;
	}
	
	public void setLadoAbajo(int v[]){
		this.ladoAbajo = v;
	}
	
	public void setLadoArriba(int v[]){
		this.ladoArriba = v;
	}
	
	public void setCentro(int v[]){
		this.centro = v;
	}
	
	public void setManchas(int manchas){
		this.manchas = manchas;
	}
	
	public void setPeso(int peso){
		this.peso = peso;
	}
	
	public int getBorde(){
		return this.borde;
	}
	
	public int[] getLadoIzq(){
		return this.ladoIzquierdo;
	}
	
	public int[] getLadoDer(){
		return this.ladoDerecho;
	}
	
	public int[] getLadoAbajo(){
		return this.ladoAbajo;
	}
	
	public int[] getLadoArriba(){
		return this.ladoArriba;
	}
	
	public int[] getCentro(){
		return this.centro;
	}
	
	public int getManchas(){
		return this.manchas;
	}
	
	public int getPeso(){
		return this.peso;
	}
	
}
	