package sudoku3000;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WyswietlaneTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private Wyswietlane wyswietlaneTest;
	
	@BeforeMethod
	public void setup(){
	    wyswietlaneTest = new Wyswietlane() {
			
			@Override
			void wyswietlKom(String komunikat) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	@AfterMethod
	public void teardown() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void testWyczysc() {
		System.out.println("wyczysc");
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		wyswietlaneTest.wyczysc();
		//Tricky: there should be 50 times /n in outContent so I use String.trim() to compare it with empty string.
		Assert.assertEquals(outContent.toString().trim(), "");
		
	}
}
