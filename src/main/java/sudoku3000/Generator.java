package sudoku3000;

import java.util.Arrays;
import java.io.LineNumberReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Collections;
import java.io.Serializable;
/**
   *Klasa do generowania plansz sudoku
   *@author Adrian Dolubizno
   *@version 1337.101
*/
public class Generator extends Solver implements Serializable
{ 
	protected int[][] oryginal = new int[9][9];		
/**
*Metoda ładuje losowe sudoku z pliku database.txt
*jesli taki plik nie istnieje wysylamy odpowiedni komunikat
*/
	public void loadMap()  
	{
		try
		{
                        InputStream inpStr = Generator.class.getResourceAsStream("database.txt");
                        LineNumberReader read = new LineNumberReader(new InputStreamReader(inpStr));
			Random rand = new Random();
			int skipper = rand.nextInt(6);
                        for(int i=0; i<skipper; i++) {
                            read.readLine();
                        }
			String s = read.readLine();
			for(int i = 0; i < 81; i++) 
			{
           			char c = s.charAt(i);
           			int row = i / 9;
           			int col = i % 9;
           			if(c != '.')
				{
					this.solution[row][col] = c-'0';
           			}
			} 
		}
                catch(Exception FileNotFoundException) 
		{
			System.out.print("Brak krytycznych plikow gry");
			System.exit(0);
		} 
	}
/**
*funkcja ładuje Sudoku podane przez użytkownika
**znaki inne niż cyfry są ingorowane
*jeśli sudoku ma ilość cyfr różną od 81 zwracany jest fałsz
*@param sudo String z ciagiem cyfr
*@return true jesli wczytano 81 cyfr, false w innym wypadku
*/
	public boolean loadCon(String sudo)  
	{
		int i=0;					//przechowuje informacje o ilosci podanych przez uzytkownika cyfr
		int sciiValue=0;				//wartosc podanych znakow w ascii
		for(int j=0; j<sudo.length();j++)
		{		
			sciiValue = sudo.codePointAt(j);
			if(sciiValue > 47 && sciiValue < 58)	//sprawdzamy czy podany znak jest cyfra 0-9
			{
           			int row = i / 9;
           			int col = i % 9;
				try
				{
					this.board[row][col] = Character.getNumericValue(sciiValue);
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					return false;
				}
				i++;
			}
	
		}
		if(i == 81)					
		{
			return true;
		}
		else
		{
			return false;
		}
	}
/**
*Metoda usuwa okreslona ilosc cyfr z tablicy
*Cyfry sa usuwane dopóki nie zostanie okreslona ich ilosc
*lub do momentu az usuniecie dowolnej z cyfr spowoduje
*ze sudoku bedzie mialo wiecej niz jedno rozwiazanie
*@param difficulty ilosc liczb ktora powinna zostac w tablicy
*/
	void kebabRemover(int difficulty)  
	{
		
		board = new int[9][9];
		for(int i=0; i<9; i++) 
		{
			System.arraycopy(solution[i], 0, board[i], 0, 9); 

		}
		int temp;
		int removed=0;
		Integer[] shuffled = new Integer[81]; 
		for(int i=0; i<81; i++)
		{ 
			shuffled[i]=i;	
		}	
		Collections.shuffle(Arrays.asList(shuffled));
		for(int i=0; i<81; i++) 
		{
			temp=board[shuffled[i]/9][shuffled[i]%9]; 
			board[shuffled[i]/9][shuffled[i]%9]=0; 
			removed++; 
			//System.out.println(i+". Current solutions: " + solveSudoku(board));
			if(countSolutions(board)>1)  
			{
				board[shuffled[i]/9][shuffled[i]%9]=temp;
				removed--;
			}
			if(81-removed <= difficulty) 
			{
			break;
			}
		}
	}
/**
*Metoda zamieniajaca kolumny miejscami zachowujac poprawnosc sudoku
*/
	void swapColumns() 
	{
		int col1, col2, temp;
		Random rand = new Random();
		for(int j=0; j<20; j++) 
		{
			int block = rand.nextInt(3);
			do
			{
				col1 = rand.nextInt(3);
				col2 = rand.nextInt(3);
			}
			while(col1==col2);
			for(int i=0; i<9; i++)
			{
				temp=solution[i][block*3+col1];
				solution[i][block*3+col1]=solution[i][block*3+col2];
				solution[i][block*3+col2]=temp;
			}
		}
	}
/**
*Metoda zamieniajaca wiersze miejscami zachowujac poprawnosc sudoku
*/
	void swapRows()  //zamienia miejscami wiersze 
	{
		int row1, row2, temp;
		Random rand = new Random();
		for(int j=0; j<20; j++) 
		{
			int block = rand.nextInt(3);
			do
			{
				row1 = rand.nextInt(3);
				row2 = rand.nextInt(3);
			}
			while(row1==row2);
			for(int i=0; i<9; i++)
			{
				temp=solution[block*3+row1][i];
				solution[block*3+row1][i]=solution[block*3+row2][i];
				solution[block*3+row2][i]=temp;
			}
		}
	}	

	int[][] returnSolution() 
	{
		return solution;
	}

	int[][] returnPuzzle() 
	{
		return board;
	}
	public int[][] getOryginal()
	{
		return this.oryginal;
	}
	public void prepare()			
	{
		for(int j=0;j<9;j++)
		{
			for(int i=0;i<9;i++)
			{
				this.oryginal[i][j] = this.board[i][j];
			}
		}
	}
/**
*@return true jeśli sudoku jest rozwiązane
*/
	public boolean czyRoz()
	{
		for(int i = 0; i<9; i++) 
		{
			for(int j = 0; j<9; j++) 
			{
				if(this.board[i][j]==0)
				{
					return false;
				}
			}	
		}
		return true;
	}
/**
*Metoda wypisujaca rozwiazanie na ekran
*/
	void printSolution(){
		for(int i=0; i<9 ;i++){
			for(int j=0; j<9; j++){
				System.out.print(solution[i][j] + " ");
			}
			System.out.println();
		}
	}
}
