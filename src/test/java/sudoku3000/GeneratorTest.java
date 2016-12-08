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
import org.testng.annotations.Test;

public class GeneratorTest {

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

	@Test
	public void swapRowsTest() {
		Whitebox.setInternalState(generatorTest, "solution", getSolutionTestSudoku());
		generatorTest.swapRows();
		Assert.assertFalse(Arrays.deepEquals(getSolutionTestSudoku(), generatorTest.solution));
	}

	 @Test
	public void kebabRemoverTest() {
		int difficulty = 65;
		Generator mock = Mockito.mock(Generator.class);
		Whitebox.setInternalState(mock, "solution", getSolutionTestSudoku());
		Whitebox.setInternalState(mock, "board", getSolutionTestSudoku());
		
		Mockito.doCallRealMethod().when(mock).kebabRemover(Matchers.anyInt());
		Mockito.when(mock.countSolutions(mock.board)).thenReturn(0);
		mock.board = getSolutionTestSudoku();
		mock.kebabRemover(difficulty);
		Mockito.verify(mock).countSolutions(mock.board);
	}
	
	@Test
	public void swapColumnsTest() {
		Whitebox.setInternalState(generatorTest, "solution", getSolutionTestSudoku());
		generatorTest.swapColumns();
		Assert.assertFalse(Arrays.deepEquals(getSolutionTestSudoku(), generatorTest.solution));
	}

	@Test
	public void prepareTest() {
		generatorTest.prepare();
		Assert.assertTrue(Arrays.deepEquals(generatorTest.board, generatorTest.oryginal));
	}

	@Test
	public void printSolutionTest() {
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
