package sudoku3000;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SolverTest {

	private Solver solverTest;

	@BeforeMethod
	public void setup() {
		solverTest = new Solver();
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

	/**
     * Test of canBePlaced method, of class Solver.
     */
    //zwraca true tylko jesli wstawiamy cyfre w puste pole i jesli jest ono poprawne
    @Test
    public void testCanBePlaced() {
        System.out.println("canBePlaced");
        int[][] sudo = new int[9][9];
        //
        //int tab[] = {5,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};
        int tab[] = {0,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};
        Solver instance = new Solver();
         
         
        //uzupelnienie sudoku
        int x,y;
        for(int i = 0; i < 81; i++)
        {
            if((i % 9)>0)
            {
                x = i/9;
                y = i%9;
 
            }else
            {
                x = i/9;
                y = 0;
            }
             
            sudo[x][y] = tab[i];
 
        }
         
        //wypisanie sudoku na ekran
        for(int i = 0; i<9; i++) 
        {
                for(int j = 0; j<9; j++) 
                {
                        System.out.print(sudo[i][j] + " ");
                }   
                System.out.println();
        }
         
        int row = 0;
        int column = 0;
        int num = 5;
        boolean expResult = true;
        boolean result = instance.canBePlaced(sudo, row, column, num);
        Assert.assertEquals(expResult, result);
 
    }
	
	@Test
	public void testSolve() {
		int[][] sudoTest = getNotSolvedTestSudoku();
		int[][] solutionTest = new int[9][9];
		solverTest.solution = solutionTest;
		int index = 0;
		solverTest.solve(sudoTest, index);
		Assert.assertEquals(Arrays.deepToString(solverTest.solution), Arrays.deepToString(getSolutionTestSudoku()));
	}

	@Test
	public void testSolveWithIndex81() {
		int[][] sudoTest = getNotSolvedTestSudoku();
		int[][] solutionTest = new int[9][9];
		solverTest.solution = solutionTest;
		int index = 81;
		solverTest.solve(sudoTest, index);
		Assert.assertEquals(Arrays.deepToString(solverTest.solution), Arrays.deepToString(sudoTest));
	}

	@Test
	public void testCounterWithOneSolution() {
		int[][] sudoTest = getNotSolvedTestSudoku();
		int[][] solutionTest = new int[9][9];
		solverTest.solution = solutionTest;
		int index = 0;
		solverTest.counter(sudoTest, index);
		Assert.assertEquals(solverTest.numberOfSolutions, 1);
	}

	@Test
	public void testCounterWithMoreSolutions() {
		int[][] sudoTest = getSudokuWithMultipleSolutions();
		int[][] solutionTest = new int[9][9];
		solverTest.solution = solutionTest;
		int index = 0;
		solverTest.counter(sudoTest, index);
		Assert.assertEquals(solverTest.numberOfSolutions, 2);
	}
	
	@Test
	public void testCounterWith81Index() {
		int[][] sudoTest = getSudokuWithMultipleSolutions();
		int[][] solutionTest = new int[9][9];
		solverTest.solution = solutionTest;
		int index = 81;
		solverTest.counter(sudoTest, index);
		Assert.assertEquals(solverTest.numberOfSolutions, 1);
	}

	@AfterMethod
	public void teardown() {

	}
}
