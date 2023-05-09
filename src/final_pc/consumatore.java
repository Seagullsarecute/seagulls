package final_pc;

import java.util.concurrent.ThreadLocalRandom;



public class consumatore extends Thread {
	//attributi
	private int id,tmp;
	//Costruttore
	public consumatore(int id)
	{
		this.id= id;
	}
	//run() del thread (ERRORE NON PARTE)
	public void run()
	{
		//rimozione di dati nel buffer
		while(Main.operazioni > 0) {
			if(Main.buff_vuoto) {
				synchronized (Main.lock_produttore) {
					Main.lock_produttore.notify();
				}
				synchronized (Main.lock_consumatore) {
					try {
						Main.lock_consumatore.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			if(Main.indice != Main.rimosso) {
				//monitor Main.buff=lock
				Main.buff_vuoto= false;
				synchronized (Main.lock_consumatore) {
					tmp = Main.buff[Main.rimosso%Main.BUFF_SIZE];//assegnazione valore contenete in specifica celladel buff a tmp
					System.out.println("il consumatore " + id + " ha rimosso " + tmp);//rimozione valore in indice
					Main.rimosso = (Main.rimosso + 1) % Main.BUFF_SIZE;//indice cella da consumare+1
					Main.operazioni--;
				}
				//check buffer vuoto
			}else {
				Main.buff_vuoto= true;
				System.err.println("buffer è vuoto");
			}
			//sleep con tempo variabile(threadLocalRandom è un randomizzatore di numeri appartenente a thread)
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(Main.min_t, Main.max_t + 1));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}