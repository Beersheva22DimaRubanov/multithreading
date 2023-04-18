package telran.multithreading;

public class Printer extends Thread {
	private String str;
	private int nRuns;
	
	public Printer(String str, int nRuns) {
		super();
		this.str = str;
		this.nRuns = nRuns;
	}

	@Override
	public void run() {
		for(int i =0; i< nRuns; i++ ) {
			System.out.println(str);
			try {
				sleep((long) (Math.random() *(5 - 2 + 1) + 2));
			} catch (InterruptedException e) {
			}
		}
			
	}
}
