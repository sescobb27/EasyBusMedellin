package easybus.medellin.movil.exceptions;

public class NoLocationServiceEnable extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2714858917205014653L;
	
	public NoLocationServiceEnable() {
		super("Gps and Network based Location are disable, please enable at least one of them");
	}

}
