package sudoku3000;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@PrepareForTest(MenuTest.class)
public class MenuTest {

	private Menu menuMock;

	@BeforeMethod
	public void setup() {
		menuMock = Mockito.mock(Menu.class, Mockito.CALLS_REAL_METHODS);
	}

	 @Test
	public void wczytajTest() {
		 String myFilePath = "E:\\Eclipse_workspace\\SudokuTest\\src\\test\\resources\\";
		 InputStream in = new ByteArrayInputStream(myFilePath.getBytes());
		 System.setIn(in);
		 String wczytajResult = "";
		try {
			wczytajResult = menuMock.wczytaj();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(wczytajResult, myFilePath);
		
		System.setIn(null);
	}

	@Test
	public void wyswietlKomTest() {
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
		String miniInstrukcjaMock = "testMiniInstrukcja";
		Whitebox.setInternalState(menuMock, "rozmiarY", rozmiarYMock);
		Whitebox.setInternalState(menuMock, "rozmiarX", rozmiarXMock);
		Whitebox.setInternalState(menuMock, "logo2", logoMock);
		Whitebox.setInternalState(menuMock, "miniInstrukcja", miniInstrukcjaMock);
		String komunikatTest = "TEST";
		
		menuMock.wyswietlKom(komunikatTest);
		
		Assert.assertTrue(outContent.toString().trim().contains(miniInstrukcjaMock));
		Assert.assertTrue(outContent.toString().trim().contains(komunikatTest));
		Assert.assertTrue(outContent.toString().trim().contains("Sudoku ver 1.0"));
		
		System.setOut(null);
		System.setErr(null);
	}
	
	 @DataProvider(name = "nazwaTest")
	 public static Object[][] opcjeTestProvider() {
	 String[] opcjeMenuMock = { "1)Nowa gra", "2)Wczytaj grę", "3)Wyjście" };
	 String[] opcjeNowaGraMock = { "1)poziom łatwy", "2)poziom średni",
	 "3)poziom trudny", "4)gra własna" };
	 return new Object[][] { { 0, opcjeMenuMock }, { 1, opcjeMenuMock }, { 2,
	 opcjeMenuMock },
	 { 0, opcjeNowaGraMock }, { 1, opcjeNowaGraMock }, { 2, opcjeNowaGraMock
	 }, { 3, opcjeNowaGraMock } };
	
	 }
	
	 @Test(dataProvider = "nazwaTest")
	 public void nazwaTest(int iValue, String[] opcjeMock) {
	 Whitebox.setInternalState(menuMock, "opcje", opcjeMock);
	 String result = menuMock.nazwa(iValue);
	 Assert.assertEquals(result, opcjeMock[iValue].substring(2));
	 }

	@AfterMethod
	public void teardown() {
	}
}
