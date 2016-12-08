package sudoku3000;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MapTest {

	private Map mapMock;

	@BeforeMethod
	public void setup() {
		mapMock = Mockito.mock(Map.class, Mockito.CALLS_REAL_METHODS);
	}

	@Test
	public void printBoardTest() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		int[][] arrayTest = new int[9][9];
		for(int[] row : arrayTest){
			Arrays.fill(row, 9);
		}

		Whitebox.setInternalState(mapMock, "board", arrayTest);

		mapMock.printBoard();

		Assert.assertEquals(outContent.toString().replaceAll("\\s", ""), generateExpectedPrint());
	}

	private String generateExpectedPrint() {
		String result = "";
		for (int[] row : mapMock.board) {
			for (int value : row) {
				result += Integer.toString(value);
			}
		}
		return result;
	}

	@AfterMethod
	public void teardown() {
		System.setErr(null);
		System.setOut(null);
	}
}
