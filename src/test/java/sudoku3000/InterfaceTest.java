package sudoku3000;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InterfaceTest extends TestBase {

	private Interfejs interfejsTest;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@BeforeMethod
	public void setup() {
		interfejsTest = new Interfejs();
		String[] menuBoczne = new String[] { "(Z)apisz grę", "(P)okaż rozwiązanie", "P(o)dpowiedź", "(M)enu główne" };
		Whitebox.setInternalState(interfejsTest, "menuBoczne", menuBoczne);
		Whitebox.setInternalState(interfejsTest, "liniaP", 5);
	}

	@Test
	public void wyswietlPlanszeTest() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		final int TEST_VALUE = 9;
		Whitebox.setInternalState(interfejsTest, "obecnaGra", Mockito.mock(Gra.class));
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
	
	@AfterMethod
	public void teardown() {
		System.setOut(this.defaultOut);
		System.setErr(this.defaultErr);
	}
}
