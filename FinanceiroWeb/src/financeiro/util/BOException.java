package financeiro.util;

@SuppressWarnings("serial")
public class BOException extends Exception {

	public BOException(String msg) {
		super(msg);
	}
	
	public BOException(Throwable e) {
		super(e);
	}
}
