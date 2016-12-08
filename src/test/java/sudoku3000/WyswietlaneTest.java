package sudoku3000;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WyswietlaneTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private Wyswietlane wyswietlaneMock;
	
	@BeforeMethod
	public void setup(){
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	    wyswietlaneMock = Mockito.mock(Wyswietlane.class, Mockito.CALLS_REAL_METHODS);
	}
	
	@AfterMethod
	public void teardown() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void wyczyscTest() {
		wyswietlaneMock.wyczysc();
		//Tricky: there should be 50 times /n in outContent so I use String.trim() to compare it with empty string.
		Assert.assertEquals("", outContent.toString().trim());
		
	}
}
