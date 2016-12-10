package sudoku3000;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GeneratorTest extends TestBase{

	Generator generatorTest;

	private int[][] getSolutionTestSudoku() {
		int[][] solution = { { 4, 3, 5, 2, 6, 9, 7, 8, 1 }, { 6, 8, 2, 5, 7, 1, 4, 9, 3 },
				{ 1, 9, 7, 8, 3, 4, 5, 6, 2 }, { 8, 2, 6, 1, 9, 5, 3, 4, 7 }, { 3, 7, 4, 6, 8, 2, 9, 1, 5 },
				{ 9, 5, 1, 7, 4, 3, 6, 2, 8 }, { 5, 1, 9, 3, 2, 6, 8, 7, 4 }, { 2, 4, 8, 9, 5, 7, 1, 3, 6 },
				{ 7, 6, 3, 4, 1, 8, 2, 5, 9 } };
		return solution;
	}

	@BeforeMethod
	public void setup() {
		generatorTest = new Generator();

		int[][] arrayTest = new int[9][9];
		for (int[] row : arrayTest) {
			Arrays.fill(row, 9);
		}

		Whitebox.setInternalState(generatorTest, "board", arrayTest);
	}

	@DataProvider(name = "testLoadCon")
	public static Object[][] testLoadConDataProvider() {
		return new Object[][] {
				{ ":?7543,8169292874601561359284!04319287568@#95107234267453980546879++++++123302615479170234568",true },
				{ "754381692928746015613592840431928756895107234267453980546879123302615479170234568",true },
				{ "75438169292874601561359284043192875689510723426745398054687912330261547917023456",false },
				{ "7543816929287460156135928404319287568951072342674539805468791233026154791702345689",false } };
	}

	/**
	 * Test of loadCon method, of class Generator.
	 */
	@Test(dataProvider = "testLoadCon")
	public void testLoadCon(String sudo, boolean expectedResult) {
		System.out.println("loadCon");

		// 81 cyfr + rozne znaki, ok
		// String sudo =
		// ":?7543,8169292874601561359284!04319287568@#95107234267453980546879++++++123302615479170234568";
		// 81 cyfr
		// String sudo =
		// "754381692928746015613592840431928756895107234267453980546879123302615479170234568";
		// 80 cyfr
		// String sudo =
		// "75438169292874601561359284043192875689510723426745398054687912330261547917023456";
		// 82 cyfry
		// String sudo =
		// "7543816929287460156135928404319287568951072342674539805468791233026154791702345689";
		Generator instance = new Generator();
		boolean result = instance.loadCon(sudo);
		instance.printBoard();
		Assert.assertEquals(result, expectedResult);

	}

	/**
	 * Test of loadMap method, of class Generator.
	 */

//	/* jesli wystapi wyjatek jest ok, w przeciwnym wypadku test failed */
//	@Test(expectedExceptions = Exception.class)
//	public void testLoadMap() throws Exception {
//		System.setOut(this.defaultOut);
//		System.out.println("loadMap");
//		Generator instance = new Generator();
//		instance.loadMap();
//
//	}

	@DataProvider(name = "testCzyRoz")
	public static Object[][] testCzyRozDataProvider() {
		int[] notSolvedSudoku = { 0, 1, 3, 9, 7, 6, 8, 2, 4, 2, 4, 9, 5, 8, 1, 7, 3, 6, 6, 8, 7, 2, 3, 4, 9, 1, 5, 3, 2,
				1, 8, 4, 5, 6, 7, 9, 8, 6, 4, 7, 9, 2, 1, 5, 3, 9, 7, 5, 6, 1, 3, 2, 4, 8, 7, 5, 6, 4, 2, 8, 3, 9, 1, 1,
				9, 8, 3, 5, 7, 4, 6, 2, 4, 3, 2, 1, 6, 9, 5, 8, 7 };
		int[] solvedSudoku = { 5, 1, 3, 9, 7, 6, 8, 2, 4, 2, 4, 9, 5, 8, 1, 7, 3, 6, 6, 8, 7, 2, 3, 4, 9, 1, 5, 3, 2, 1,
				8, 4, 5, 6, 7, 9, 8, 6, 4, 7, 9, 2, 1, 5, 3, 9, 7, 5, 6, 1, 3, 2, 4, 8, 7, 5, 6, 4, 2, 8, 3, 9, 1, 1, 9,
				8, 3, 5, 7, 4, 6, 2, 4, 3, 2, 1, 6, 9, 5, 8, 7 };
		return new Object[][] { { notSolvedSudoku, false }, { solvedSudoku, true } };
	}

	@Test(dataProvider = "testCzyRoz")
	public void testCzyRoz(int[] tab, boolean expectedResult) {
		System.out.println("czyRoz");
		Generator instance = new Generator();

		// int tab[] = { 5, 1, 3, 9, 7, 6, 8, 2, 4, 2, 4, 9, 5, 8, 1, 7, 3, 6,
		// 6, 8, 7, 2, 3, 4, 9, 1, 5, 3, 2, 1, 8, 4, 5,
		// 6, 7, 9, 8, 6, 4, 7, 9, 2, 1, 5, 3, 9, 7, 5, 6, 1, 3, 2, 4, 8, 7, 5,
		// 6, 4, 2, 8, 3, 9, 1, 1, 9, 8, 3, 5,
		// 7, 4, 6, 2, 4, 3, 2, 1, 6, 9, 5, 8, 7 };
		// sudoku nie wypelnione w calosci, pierwsza wartosc 0
		// int tab[] =
		// {0,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};

		int x, y;
		for (int i = 0; i < 81; i++) {
			if ((i % 9) > 0) {
				x = i / 9;
				y = i % 9;

			} else {
				x = i / 9;
				y = 0;
			}

			instance.board[x][y] = tab[i];

		}

		boolean result = instance.czyRoz();
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testSwapRows() {
		Whitebox.setInternalState(generatorTest, "solution", getSolutionTestSudoku());
		generatorTest.swapRows();
		Assert.assertFalse(Arrays.deepEquals(getSolutionTestSudoku(), generatorTest.solution));
	}

	@Test
	public void testRemovePointsToGivenNumber() {
		int difficulty = 65;
		Generator mock = Mockito.mock(Generator.class);
		Whitebox.setInternalState(mock, "solution", getSolutionTestSudoku());
		Whitebox.setInternalState(mock, "board", getSolutionTestSudoku());

		Mockito.doCallRealMethod().when(mock).removePointsToGivenNumber(Matchers.anyInt());
		Mockito.when(mock.countSolutions(mock.board)).thenReturn(0);
		mock.board = getSolutionTestSudoku();
		mock.removePointsToGivenNumber(difficulty);
		System.out.println(Arrays.deepToString(mock.board));
		Assert.assertEquals(difficulty, 81 - countRemovedPoints(mock.board));
	}

	private int countRemovedPoints(int[][] sudoku) {
		int counter = 0;
		for (int[] row : sudoku) {
			for (int value : row) {
				if (value == 0) {
					counter++;
				}
			}
		}
		return counter;
	}

	@Test
	public void testSwapColumns() {
		Whitebox.setInternalState(generatorTest, "solution", getSolutionTestSudoku());
		generatorTest.swapColumns();
		Assert.assertFalse(Arrays.deepEquals(getSolutionTestSudoku(), generatorTest.solution));
	}

	@Test
	public void testPrepare() {
		generatorTest.prepare();
		Assert.assertTrue(Arrays.deepEquals(generatorTest.board, generatorTest.oryginal));
	}

	@Test
	public void testPrintSolution() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		PrintStream stdout = System.out;
		PrintStream stderr = System.err;
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		generatorTest.printSolution();

		Assert.assertEquals(outContent.toString().replaceAll("\\s", ""), generateExpectedPrint());
		System.setOut(stdout);
		System.setErr(stderr);
	}

	private String generateExpectedPrint() {
		String result = "";
		for (int[] row : generatorTest.solution) {
			for (int value : row) {
				result += Integer.toString(value);
			}
		}
		return result;

	}

	@AfterMethod
	public void teardown() {

	}
}
