package sudoku3000;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.security.Key;

import javax.crypto.Cipher;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GraTest {

	Gra graMock;
	
	@BeforeMethod
	public void setup() {
		graMock = Mockito.mock(Gra.class, Mockito.withSettings().serializable());
	}
	
	@Test
	public void zapiszTest() throws Exception {
		final String ALGO = "AES";
		final String filePath = "E:\\Eclipse_workspace\\SudokuTest\\src\\test\\resources\\save.txt";
		//Prepare our mock save into file.
		Mockito.doCallRealMethod().when(graMock).zapisz(Matchers.anyString());
		graMock.zapisz(filePath);

		//Read saved file and decrypt it
		DataInputStream strumien = new DataInputStream(new FileInputStream(filePath));
		int rozmiar = strumien.readInt();
		byte[] bufor = new byte[rozmiar];
		strumien.read(bufor);
		strumien.close();
		//Decrypt
		Key key = Gra.generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decValue = c.doFinal(bufor); // deszyfrujemy
		//Deserialize
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(decValue));
		Gra graTest = (Gra) ois.readObject();
		//Compare objects
		Assert.assertTrue(compareSaves(graTest, graMock));
	}
	
	@Test(expectedExceptions=FileNotFoundException.class)
	public void zapiszTestFailed() throws Exception {
		final String filePath = "E:\\Eclipse_workspace\\SudokuTest\\src\\test\\resources\\";
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		//Prepare our mock save into file.
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	    
		Mockito.doCallRealMethod().when(graMock).zapisz(Matchers.anyString());
		graMock.zapisz(filePath);
		//failed because of encoding language specific characters
		Assert.assertEquals("Błąd wejścia-wyjścia", outContent.toString().trim());
	}
	
	/**
	 * Method to compare objects {@link Gra.class}. Only few fields are compared.
	 * @param graTest 
	 * @param graMock2
	 * @return
	 */
	private boolean compareSaves(Gra graTest, Gra graMock2) {
		if(graTest.board != graMock2.board){
			return false;
		}
		if(graTest.numberOfSolutions != graMock2.numberOfSolutions){
			return false;
		}
		if(graTest.oryginal != graMock2.oryginal){
			return false;
		}
		if(graTest.solution != graMock2.solution){
			return false;
		}
		if(graTest.getName() != graMock2.getName()){
			return false;
		}
		return true;
	}
//	
//	@Test
//	public void losowaPodpowiedzTest() {
//		
//	}
//	
//	@Test
//	public void generateKeyTest() {
//		
//	}
//	
//	@Test
//	public void wczytajTest() {
//		
//	}
//	
//	@Test
//	public void czyWinTest() {
//		
//	}
//	
//	@Test
//	public void czyMoznaTest() {
//		
//	}


	@AfterMethod 
	public void teardown() {
	    System.setOut(null);
	    System.setErr(null);
	}
}
