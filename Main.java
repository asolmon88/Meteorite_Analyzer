import java.io.PrintWriter;
import java.util.Scanner;

/*
* Clase Main
*
* @author Ariel Solis
* @version 1.0
*/

public class Main{
	public static void main(String argv[]){
		Scanner entrada = new Scanner(System.in);
		
		System.out.print("Digite el nombre del archivo: ");
		String archivo = entrada.next();
		
		if(argv.length > 0){
			archivo = argv[0];  
		}
		
		Imagen i = new Imagen(archivo);
	  
		int meteoritos[][] = i.getMatriz();
		int matrizOriginal[][] = i.getMatriz();
		
		Telescopio telescopio = new Telescopio(meteoritos);
		telescopio.cambiarMatriz();
		
		System.out.print("\nDigite la carga m\u00E1xima de la nave: ");
		int cargaMaxima = entrada.nextInt();
		
		Nave nave = new Nave();
		
		int[] peso = telescopio.calcularPeso();
		
		int[] precio  = telescopio.buscarManchas();
		
		int[] meteoritosSeleccionados = nave.elegirMeteoritos(cargaMaxima, peso, precio);
		Vector listaConMeteoritos = telescopio.crearListaMeteoritosCompeta();
		
		System.out.println("Desea imprimir los datos en pantalla o guardarlos en un archivo de texto?(Digite 1 o 2)\n1. Pantalla \n2. Archivo");
		int opcion = 0;
		
		try{
			opcion = entrada.nextInt();
			while(opcion > 2 || opcion < 1){
				System.out.println("Debe digitar 1 o 2.");
				System.out.println("Desea imprimir los datos en pantalla o guardarlos en un archivo de texto?(Digite 1 o 2)\n1. Pantalla \n2. Archivo");
				opcion = entrada.nextInt();
			}
		}
		catch(Exception e){
			System.err.println("Debe digitar un n\u00FAmero (1 o 2).");
		}
		
		if(opcion == 1){
		
			System.out.println("\n \t\t INVENTARIO DE CARGA DE LA NAVE\t\t");
			System.out.println("Capacidad m\u00E1xima: " + cargaMaxima + "\tCarga actual: " + meteoritosSeleccionados[meteoritosSeleccionados.length-2]+ "\t Valor de la carga: " + meteoritosSeleccionados[meteoritosSeleccionados.length-1]);
			
			System.out.println("ID\tValor\t\tPeso\t\t(Fila,Columna)del centro de la imagen en el mapa");
			for(int k = 0; k < meteoritosSeleccionados.length-2; ++k){
				if(meteoritosSeleccionados[k] != -1){
					System.out.println(meteoritosSeleccionados[k]+ "\t" +precio[meteoritosSeleccionados[k]]+"\t\t"+peso[meteoritosSeleccionados[k]]+ "\t\t" + listaConMeteoritos.getMeteorito(k).getCentro()[0] + "," + listaConMeteoritos.getMeteorito(k).getCentro()[1] + "");
				}
			}
		}
		
		else{
	
			try{
			PrintWriter salida = new PrintWriter("Resultado.txt");
			salida.println("\n \t\t INVENTARIO DE CARGA DE LA NAVE\t\t");
			salida.println("Capacidad m\u00E1xima: " + cargaMaxima + "\tCarga actual: " + meteoritosSeleccionados[meteoritosSeleccionados.length-2]+ "\t Valor de la carga: " + meteoritosSeleccionados[meteoritosSeleccionados.length-1]);
			
			salida.println("ID\tValor\t\tPeso\t\t(Fila,Columna)del centro de la imagen en el mapa");
			for(int k = 0; k < meteoritosSeleccionados.length-2; ++k){
				if(meteoritosSeleccionados[k] != -1){
					salida.println(meteoritosSeleccionados[k]+ "\t" +precio[meteoritosSeleccionados[k]]+"\t\t"+peso[meteoritosSeleccionados[k]]+ "\t\t" + listaConMeteoritos.getMeteorito(k).getCentro()[0] + "," + listaConMeteoritos.getMeteorito(k).getCentro()[1] + "");
				}
			}
			salida.close();
			}
			catch(Exception e){
				System.err.println("Error! no se encontr\u00F1 el archvio.");
			}
		}
		Imagen i1 = new Imagen(matrizOriginal);
		
		for(int w = 0; w < meteoritosSeleccionados.length-2; ++w){
			if(meteoritosSeleccionados[w] != -1){
				i1 = new Imagen(telescopio.mira(meteoritosSeleccionados[w]+1, matrizOriginal));
			}
		}
		
		i1.dibujar();
		
	}
}