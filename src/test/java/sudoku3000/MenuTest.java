package sudoku3000;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.mockito.internal.util.reflection.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MenuTest {

	private Menu menuTest;

	@BeforeMethod
	public void setup() {
		menuTest = new Menu();
	}

	 @Test
	public void testWczytaj() {
		 String myFilePath = "src\\test\\resources\\";
		 InputStream in = new ByteArrayInputStream(myFilePath.getBytes());
		 System.setIn(in);
		 String wczytajResult = "";
		try {
			wczytajResult = menuTest.wczytaj();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(wczytajResult, myFilePath);
		
		System.setIn(null);
	}

	@Test
	public void testWyswietlKom() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		int rozmiarYMock = 21;
		int rozmiarXMock = 70;
		String logoMock[] = {" _____           _       _            _____ _____ _____ _____ ",
				"/  ___|         | |     | |          |____ |  _  |  _  |  _  |",
				"\\ `--. _   _  __| | ___ | | ___   _      / | |/' | |/' | |/' |",
				" `--. | | | |/ _` |/ _ \\| |/ | | | |     \\ |  /| |  /| |  /| |",
				"/\\__/ | |_| | (_| | (_) |   <| |_| | .___/ \\ |_/ \\ |_/ \\ |_/ /",
				"\\____/ \\__,_|\\__,_|\\___/|_|\\_\\\\__,_| \\____/ \\___/ \\___/ \\___/ "
				    
				};
		String miniInstrukcjaTest = "testMiniInstrukcja";
		Whitebox.setInternalState(menuTest, "rozmiarY", rozmiarYMock);
		Whitebox.setInternalState(menuTest, "rozmiarX", rozmiarXMock);
		Whitebox.setInternalState(menuTest, "logo2", logoMock);
		Whitebox.setInternalState(menuTest, "miniInstrukcja", miniInstrukcjaTest);
		String komunikatTest = "TEST";
		
		menuTest.wyswietlKom(komunikatTest);
		
		Assert.assertTrue(outContent.toString().trim().contains(miniInstrukcjaTest));
		Assert.assertTrue(outContent.toString().trim().contains(komunikatTest));
		Assert.assertTrue(outContent.toString().trim().contains("Sudoku ver 1.0"));
		
		System.setOut(null);
		System.setErr(null);
	}
	
	 @DataProvider(name = "nazwaTest")
	 public static Object[][] opcjeTestProvider() {
	 String[] opcjeMenuTest = { "1)Nowa gra", "2)Wczytaj grę", "3)Wyjście" };
	 String[] opcjeNowaGraTest = { "1)poziom łatwy", "2)poziom średni",
	 "3)poziom trudny", "4)gra własna" };
	 return new Object[][] { { 0, opcjeMenuTest }, { 1, opcjeMenuTest }, { 2,
	 opcjeMenuTest },
	 { 0, opcjeNowaGraTest }, { 1, opcjeNowaGraTest }, { 2, opcjeNowaGraTest
	 }, { 3, opcjeNowaGraTest } };
	
	 }
	
	 @Test(dataProvider = "nazwaTest")
	 public void testNazwa(int iValue, String[] opcjeMock) {
	 Whitebox.setInternalState(menuTest, "opcje", opcjeMock);
	 String result = menuTest.nazwa(iValue);
	 Assert.assertEquals(result, opcjeMock[iValue].substring(2));
	 }

	@AfterMethod
	public void teardown() {
	}
}
