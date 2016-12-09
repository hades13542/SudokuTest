package sudoku3000;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.mockito.internal.util.reflection.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MapTest extends TestBase {

	private Map mapTest;

	@BeforeMethod
	public void setup() {
		mapTest = new Map();
	}

	@Test
	public void testPrintBoard() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		int[][] arrayTest = new int[9][9];
		for (int[] row : arrayTest) {
			Arrays.fill(row, 9);
		}

		Whitebox.setInternalState(mapTest, "board", arrayTest);

		mapTest.printBoard();

		Assert.assertEquals(outContent.toString().replaceAll("\\s", ""), generateExpectedPrint());
	}

	private String generateExpectedPrint() {
		String result = "";
		for (int[] row : mapTest.board) {
			for (int value : row) {
				result += Integer.toString(value);
			}
		}
		return result;
	}

	@DataProvider(name = "testValidator")
	public static Object[][] testValidatorDataProvider() {
		int[] poprawneSudoku = { 5, 1, 3, 9, 7, 6, 8, 2, 4, 2, 4, 9, 5, 8, 1, 7, 3, 6, 6, 8, 7, 2, 3, 4, 9, 1, 5, 3, 2,
				1, 8, 4, 5, 6, 7, 9, 8, 6, 4, 7, 9, 2, 1, 5, 3, 9, 7, 5, 6, 1, 3, 2, 4, 8, 7, 5, 6, 4, 2, 8, 3, 9, 1, 1,
				9, 8, 3, 5, 7, 4, 6, 2, 4, 3, 2, 1, 6, 9, 5, 8, 7 };
		int[] podwojnaCyfraWWierszu = { 5, 1, 9, 9, 7, 6, 8, 2, 4, 2, 4, 9, 5, 8, 1, 7, 3, 6, 6, 8, 7, 2, 3, 4, 9, 1, 5,
				3, 2, 1, 8, 4, 5, 6, 7, 9, 8, 6, 4, 7, 9, 2, 1, 5, 3, 9, 7, 5, 6, 1, 3, 2, 4, 8, 7, 5, 6, 4, 2, 8, 3, 9,
				1, 1, 9, 8, 3, 5, 7, 4, 6, 2, 4, 3, 2, 1, 6, 9, 5, 8, 7 };
		int[] podwojnaCyfraWKolumnie = { 5, 1, 3, 9, 7, 6, 8, 2, 4, 5, 4, 9, 5, 8, 1, 7, 3, 6, 6, 8, 7, 2, 3, 4, 9, 1,
				5, 3, 2, 1, 8, 4, 5, 6, 7, 9, 8, 6, 4, 7, 9, 2, 1, 5, 3, 9, 7, 5, 6, 1, 3, 2, 4, 8, 7, 5, 6, 4, 2, 8, 3,
				9, 1, 1, 9, 8, 3, 5, 7, 4, 6, 2, 4, 3, 2, 1, 6, 9, 5, 8, 7 };
		int[] podwojnaCyfraWKwadracie = { 5, 1, 3, 9, 7, 6, 8, 2, 4, 2, 4, 9, 5, 8, 1, 7, 3, 6, 6, 8, 5, 2, 3, 4, 9, 1,
				5, 3, 2, 1, 8, 4, 5, 6, 7, 9, 8, 6, 4, 7, 9, 2, 1, 5, 3, 9, 7, 5, 6, 1, 3, 2, 4, 8, 7, 5, 6, 4, 2, 8, 3,
				9, 1, 1, 9, 8, 3, 5, 7, 4, 6, 2, 4, 3, 2, 1, 6, 9, 5, 8, 7 };
		return new Object[][] { { poprawneSudoku, true }, { podwojnaCyfraWWierszu, false },
				{ podwojnaCyfraWKolumnie, false }, { podwojnaCyfraWKwadracie, false } };
	}

	/**
	 * Test of validator method, of class Map.
	 */
	// sprawdza czy nic nie koliduje
	@Test(dataProvider = "testValidator")
	public void testValidator(int[] tab, boolean expectedResult) {
		System.out.println("validator");
		Map instance = new Map();

		// poprawnie uzupelnione sudoku
		// int tab[] =
		// {5,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};

		// powtarza się cyfra w wierszu
		// int tab[] =
		// {5,1,9,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};

		// powtarza sie cyfra w kolumnie
		// int tab[] =
		// {5,1,3,9,7,6,8,2,4,5,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};

		// powtarza się w malym kwadracie
		// int tab[] =
		// {5,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,5,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};

		int x, y;
		for (int num = 0; num < 81; num++) {
			if ((num % 9) > 0) {
				x = num / 9;
				y = num % 9;

			} else {
				x = num / 9;
				y = 0;
			}

			instance.board[x][y] = tab[num];

		}

		// wyswietlanie planszy dla sprawdzenia
		instance.printBoard();

		boolean result = instance.validator();
		Assert.assertEquals(expectedResult, result);

	}

	@AfterMethod
	public void teardown() {
		System.setErr(this.defaultErr);
		System.setOut(this.defaultOut);
	}
}
