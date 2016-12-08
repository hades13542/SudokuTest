package sudoku3000;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InterfaceTest {

	private Interfejs interfejsTest;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@BeforeMethod
	public void setup() {
		interfejsTest = new Interfejs();
		String[] menuBoczne = new String[] { "(Z)apisz grę", "(P)okaż rozwiązanie", "P(o)dpowiedź", "(M)enu główne" };
		interfejsTest.setMenuBoczne(menuBoczne);
		interfejsTest.setLiniaP(5);
	}

	@Test
	public void wyswietlPlanszeTest() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		final int TEST_VALUE = 9;
		interfejsTest.setObecnaGra(Mockito.mock(Gra.class));
		int[][] tab1 = new int[9][9];
		for (int[] row : tab1)
			Arrays.fill(row, TEST_VALUE);
		for (int i = 5; i <= 17; i++) {
			interfejsTest.wyswietlPlansze(i, tab1, new int[9][9]);
		}
		int nineCounter = 0;
		int minusCounter = 0;
		for (char testChar : outContent.toString().toCharArray()) {
			if (testChar == '9') {
				nineCounter++;
			}
			if (testChar == '-') {
				minusCounter++;
			}
		}
		Assert.assertEquals(nineCounter, 81); // 9*9 fields in classic sudoku
		Assert.assertEquals(minusCounter, 84); // 21 in row * 4 rows
		Assert.assertTrue(outContent.toString().contains("(Z)apisz grę"));
		Assert.assertTrue(outContent.toString().contains("(P)okaż rozwiązanie"));
		Assert.assertTrue(outContent.toString().contains("P(o)dpowiedź"));
		Assert.assertTrue(outContent.toString().contains("(M)enu główne"));
		Assert.assertTrue(outContent.toString().contains(": 0 dostepne"));
	}

	@Test
	public void pobierzPolecenieWinConditionTest() {
		boolean pobierzPolecenieResult = false;
		
		String myResponse = "a";
		InputStream in = new ByteArrayInputStream(myResponse.getBytes());
		
		
		Gra graMock =  Mockito.mock(Gra.class);
		
		Whitebox.setInternalState(interfejsTest, "obecnaGra", graMock);
		Mockito.when(graMock.czyWin()).thenReturn(true);
		Mockito.when(graMock.getOryginal()).thenReturn(new int[9][9]);
		Mockito.when(graMock.returnPuzzle()).thenReturn(new int[9][9]);
		
		try {
			System.setIn(in);
			pobierzPolecenieResult = interfejsTest.pobierzPolecenie();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(pobierzPolecenieResult);
		
//		System.setIn(null);
	}
	
	@AfterMethod
	public void teardown() {
		System.setOut(null);
		System.setErr(null);
	}
}
