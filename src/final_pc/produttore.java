package final_pc;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class produttore extends Thread{
	private int id,tmp;
	private Random rnd = new Random();
	//costruttore
	public produttore(int id)
	{
		this.id= id;
	}
	
	public void run()
	{
	
		while(Main.operazioni > 0) {
			if(Main.buff_pieno) {
				synchronized (Main.lock_consumatore) {
					Main.lock_consumatore.notify();
				}
				synchronized (Main.lock_produttore) {
					try {
						Main.lock_produttore.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			if((Main.indice + 1) % Main.BUFF_SIZE != Main.rimosso) {
				//monitor
				Main.buff_pieno= false;
				synchronized (Main.lock_produttore) {
					
					tmp = rnd.nextInt(100);//numero random
					Main.buff[Main.indice%Main.BUFF_SIZE] = tmp;//assegnazione numero random a cella indice
					System.out.println("produttore " + id + " ha aggiunto: " + tmp);//stampa a console
					Main.indice = (Main.indice + 1) % Main.BUFF_SIZE;//indice +1
					Main.operazioni--;
				}
				//check buffer pieno
			}else {
				Main.buff_pieno= true;
				System.err.println("il buffer è pieno");
			}
			try {
				//sleep con tempo variabile(threadLocalRandom è un randomizzatore di numeri appartenente a thread)
				Thread.sleep(ThreadLocalRandom.current().nextInt(Main.min_t, Main.max_t + 1));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
}
