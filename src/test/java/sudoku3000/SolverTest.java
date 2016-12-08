package sudoku3000;

import java.util.Arrays;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SolverTest {

	private Solver solverMock;

	@BeforeMethod
	public void setup() {
		solverMock = Mockito.mock(Solver.class, Mockito.CALLS_REAL_METHODS);
	}

	private int[][] getNotSolvedTestSudoku() {
		int[][] notSolvedSudoku = { { 0, 0, 0, 2, 6, 0, 7, 0, 1 }, { 6, 8, 0, 0, 7, 0, 0, 9, 0 },
				{ 1, 9, 0, 0, 0, 4, 5, 0, 0 }, { 8, 2, 0, 1, 0, 0, 0, 4, 0 }, { 0, 0, 4, 6, 0, 2, 9, 0, 0 },
				{ 0, 5, 0, 0, 0, 3, 0, 2, 8 }, { 0, 0, 9, 3, 0, 0, 0, 7, 4 }, { 0, 4, 0, 0, 5, 0, 0, 3, 6 },
				{ 7, 0, 3, 0, 1, 8, 0, 0, 0 } };
		return notSolvedSudoku;
	}

	private int[][] getSolutionTestSudoku() {
		int[][] solution = { { 4, 3, 5, 2, 6, 9, 7, 8, 1 }, { 6, 8, 2, 5, 7, 1, 4, 9, 3 },
				{ 1, 9, 7, 8, 3, 4, 5, 6, 2 }, { 8, 2, 6, 1, 9, 5, 3, 4, 7 }, { 3, 7, 4, 6, 8, 2, 9, 1, 5 },
				{ 9, 5, 1, 7, 4, 3, 6, 2, 8 }, { 5, 1, 9, 3, 2, 6, 8, 7, 4 }, { 2, 4, 8, 9, 5, 7, 1, 3, 6 },
				{ 7, 6, 3, 4, 1, 8, 2, 5, 9 } };
		return solution;
	}

	private int[][] getSudokuWithMultipleSolutions() {
		int[][] solution = { { 2, 8, 6, 1, 5, 9, 7, 4, 3 }, { 3, 5, 7, 6, 4, 8, 2, 1, 9 },
				{ 4, 1, 0, 0, 0, 0, 0, 0, 0 }, { 8, 0, 0, 0, 0, 5, 0, 0, 0 }, { 6, 0, 0, 8, 0, 4, 0, 0, 0 },
				{ 7, 0, 0, 3, 0, 0, 0, 0, 6 }, { 5, 0, 0, 0, 0, 0, 0, 7, 0 }, { 1, 3, 0, 5, 0, 0, 0, 8, 0 },
				{ 9, 7, 2, 4, 0, 0, 0, 5, 0 } };
		return solution;
	}

	@Test
	public void solveTest() {
		int[][] sudoTest = getNotSolvedTestSudoku();
		int[][] solutionTest = new int[9][9];
		solverMock.solution = solutionTest;
		int index = 0;
		solverMock.solve(sudoTest, index);
		Assert.assertEquals(Arrays.deepToString(solverMock.solution), Arrays.deepToString(getSolutionTestSudoku()));
	}

	@Test
	public void solveWithIndex81Test() {
		int[][] sudoTest = getNotSolvedTestSudoku();
		int[][] solutionTest = new int[9][9];
		solverMock.solution = solutionTest;
		int index = 81;
		solverMock.solve(sudoTest, index);
		Assert.assertEquals(Arrays.deepToString(solverMock.solution), Arrays.deepToString(sudoTest));
	}

	@Test
	public void counterWithOneSolutionTest() {
		int[][] sudoTest = getNotSolvedTestSudoku();
		int[][] solutionTest = new int[9][9];
		solverMock.solution = solutionTest;
		int index = 0;
		solverMock.counter(sudoTest, index);
		Assert.assertEquals(solverMock.numberOfSolutions, 1);
	}

	@Test
	public void counterWithMoreSolutionsTest() {
		int[][] sudoTest = getSudokuWithMultipleSolutions();
		int[][] solutionTest = new int[9][9];
		solverMock.solution = solutionTest;
		int index = 0;
		solverMock.counter(sudoTest, index);
		Assert.assertEquals(solverMock.numberOfSolutions, 2);
	}
	
	@Test
	public void counterWith81IndexTest() {
		int[][] sudoTest = getSudokuWithMultipleSolutions();
		int[][] solutionTest = new int[9][9];
		solverMock.solution = solutionTest;
		int index = 81;
		solverMock.counter(sudoTest, index);
		Assert.assertEquals(solverMock.numberOfSolutions, 1);
	}

	@AfterMethod
	public void teardown() {

	}
}
