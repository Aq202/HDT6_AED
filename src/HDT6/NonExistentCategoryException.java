package HDT6;

public class NonExistentCategoryException extends Exception{

	public NonExistentCategoryException(String message) {
		super(message);
	}
	
	public NonExistentCategoryException() {
		super("La categoria ingresada no existe");
	}
}
