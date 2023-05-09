package final_pc;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Main{
	
	public static int operazioni,max_t, min_t,prod_n,usr_n,indice = 0,rimosso = 0;
	public static final int BUFF_SIZE = 10;
	//dichiarazione del buffer(array)
	public static int[] buff = new int[BUFF_SIZE];
	public static Object lock_produttore = new Object();
	public static Object lock_consumatore = new Object();
	public static boolean buff_pieno = false, buff_vuoto = false;
	
	public static void main(String []args) throws Exception
	{
		//estrazione input da terminale
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//richiesta in input di N operazioni
		System.out.println("N operazioni: ");
		operazioni = Integer.parseInt(in.readLine());
		//richiesta in input del tempo minimo e massimo con i vari controlli
		System.out.println("tempo minimo: ");
		min_t = Integer.parseInt(in.readLine());
		if(min_t < 1) {
			throw new Exception("il tempo minimo non può essere minore di  1");
		}
		
		System.out.println("tempo massimo: ");
		max_t = Integer.parseInt(in.readLine());
		if(max_t <= min_t) {
			throw new Exception("tempo massimo non può essere minore di tempo minimo");
		}
		
		
		//richiesta in input di produttori e consumatori
		System.out.println("N produttori: ");
		prod_n = Integer.parseInt(in.readLine());
		System.out.println("N consumatori: ");
		usr_n = Integer.parseInt(in.readLine());
		
		for(int i = prod_n; i > 0; i--) {
			new produttore(i).start();
		}
		for(int i = usr_n; i > 0; i--) {
			new consumatore(i).start();

		}
	}
}
