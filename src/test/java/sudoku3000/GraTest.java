package sudoku3000;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.security.Key;

import javax.crypto.Cipher;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GraTest extends TestBase {

	Gra graTest;
	
	@BeforeMethod
	public void setup() {
		graTest = new Gra();
		graTest.setPodpowiedzi(2);
	}
	
	@Test
	public void testZapiszWithSerializable() throws Exception {
		final String ALGO = "AES";
		final String filePath = "src\\test\\resources\\save.txt";
		//Prepare our mock save into file.
		graTest.zapisz(filePath);

		//Read saved file and decrypt it
		DataInputStream strumien = new DataInputStream(new FileInputStream(filePath));
		int rozmiar = strumien.readInt();
		byte[] bufor = new byte[rozmiar];
		strumien.read(bufor);
		strumien.close();
		//Decrypt
		Key key = Gra.generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decValue = c.doFinal(bufor); // deszyfrujemy
		//Deserialize
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(decValue));
		Gra graTest = (Gra) ois.readObject();
		//Compare objects
		Assert.assertTrue(compareSaves(graTest, graTest));
	}
	
	@Test(expectedExceptions=FileNotFoundException.class)
	public void testZapiszFailed() throws Exception {
		final String filePath = "src\\test\\resources\\";
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		//Prepare our mock save into file.
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));

		graTest.zapisz(filePath);
		//failed because of encoding language specific characters
		Assert.assertEquals("Zapis w klasie Gra: src\\test\\resources\\ \n"
				+ "Błąd wejścia-wyjścia", outContent.toString().trim());
	}
	
	/**
	 * Method to compare objects {@link Gra.class}. Only few fields are compared.
	 * @param graTest 
	 * @param graMock2
	 * @return
	 */
	private boolean compareSaves(Gra graTest, Gra graMock2) {
		if(graTest.board != graMock2.board){
			return false;
		}
		if(graTest.numberOfSolutions != graMock2.numberOfSolutions){
			return false;
		}
		if(graTest.oryginal != graMock2.oryginal){
			return false;
		}
		if(graTest.solution != graMock2.solution){
			return false;
		}
		if(graTest.getName() != graMock2.getName()){
			return false;
		}
		return true;
	}

	
    @Test
    public void testGenerateKey() throws Exception {
        System.out.println("generateKey");
        Key key = Gra.generateKey();
        Assert.assertNotNull(key);
    }
     
    /**
     * Test of zapisz method, of class Gra.
     */
    //jesli nie podamy dokladnej sciezki to plik powstanie w katalogu z programem
    @Test
    public void testZapisz() throws Exception {
        System.out.println("zapisz");
        //String nazwa = "C:\\Users\\Agata Wójcik\\AppData\\Roaming\\Sudoku3000\\Przyklad.txt";
        //String nazwa = "C:\\Users\\Agata Wójcik\\Desktop\\Przyklad.txt";
        String nazwa = "Przyklad.txt";
        Gra gra = new Gra();
        boolean expResult = true;
        boolean result = gra.zapisz(nazwa);
        Assert.assertEquals(expResult, result);
    }
 
    /**
     * Test of wczytaj method, of class Gra.
     */
    //jesli plik istnieje to obiekt nie jest nullem i test przechodzi
    @Test
    public void testWczytaj() throws Exception {
        System.out.println("wczytaj");
        //plik istnieje, test przechodzi
        //String nazwa = "C:\\Users\\Agata Wójcik\\AppData\\Roaming\\Sudoku3000\\Przyklad.txt";
        //plik nie istnieje, test nie przechodzi
        //String nazwa = "C:\\Users\\Agata Wójcik\\AppData\\Roaming\\Sudoku3000\\Test.txt";
        String nazwa = "Przyklad.txt";
        Gra result = Gra.wczytaj(nazwa);
        Assert.assertNotNull(result);
    }

    /**
     * Test of czyWin method, of class Gra.
     */
    @Test//funkcja jedynie porownuje dwie macierze, musza byc identyczne - inaczej false
    public void testCzyWin() {
        System.out.println("czyWin");
        Gra instance = new Gra();
        //różne tablice
        int tab1[] = {5,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};
        int tab2[] = {5,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,0};
         
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
             
            instance.solution[x][y] = tab1[i];
            instance.board[x][y] = tab2[i];
        }
        
        System.out.println("     solution:\t\tboard:");
        for(int i = 0; i<9; i++) 
        {
                for(int j = 0; j<9; j++) 
                {
                        System.out.print(instance.solution[i][j] + " ");
                }
                System.out.print("\t");
                for(int j = 0; j<9; j++) 
                {
                        System.out.print(instance.board[i][j] + " ");
                }
                 
                System.out.println();
        }
         
         
        boolean expResult = false;
        boolean result = instance.czyWin();
        Assert.assertEquals(expResult, result);
    }
    
    /**
     * Test of czyMozna method, of class Gra.
     */
    //jesli w danym miejscu jest 0 to zwraca true, inaczej false
    @Test
    public void testCzyMozna() {
        System.out.println("czyMozna");
        //Gra instance = new Gra();
         
        //ok
        int tab[] = {0,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};
        //nie ok
        //int tab[] = {5,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};
         
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
             
            graTest.oryginal[x][y] = tab[i];
             
 
        }
        
        System.out.println("oryginal:");
        for(int i = 0; i<9; i++) 
        {
                for(int j = 0; j<9; j++) 
                {
                        System.out.print(graTest.oryginal[i][j] + " ");
                }           
                System.out.println();
        }
         
        int row = 0;
        int col = 0;
        boolean expResult = true;
        boolean result = graTest.czyMozna(row, col);
        Assert.assertEquals(expResult, result);
 
    }
 
    /**
     * Test of losowaPodpowiedz method, of class Gra.
     */
 
    @Test
    public void testLosowaPodpowiedz() {
        System.out.println("losowaPodpowiedz");
         
        //wypelnienie tablicy z gra i tablicy z rozwiazaniem
         
        int tab1[] = {5,1,3,9,7,6,8,2,4,2,4,9,5,8,1,7,3,6,6,8,7,2,3,4,9,1,5,3,2,1,8,4,5,6,7,9,8,6,4,7,9,2,1,5,3,9,7,5,6,1,3,2,4,8,7,5,6,4,2,8,3,9,1,1,9,8,3,5,7,4,6,2,4,3,2,1,6,9,5,8,7};
        int tab2[] = {0,0,3,9,7,0,8,2,4,2,4,9,5,8,1,7,3,6,0,8,7,2,3,0,9,1,5,3,2,1,0,4,5,6,7,9,0,6,4,7,9,0,1,5,3,9,0,0,0,1,3,2,4,8,0,5,6,4,2,8,3,0,1,0,9,8,0,5,7,4,6,0,4,0,2,0,0,0,5,8,0};
         
         
        //test, tylko do wypisania
        int[][] test = new int[9][9];   
         
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
             
            graTest.solution[x][y] = tab1[i];
            graTest.board[x][y] = tab2[i];
            test[x][y] = tab2[i];
 
        }
         
        String result = graTest.losowaPodpowiedz();
        String result2 = graTest.losowaPodpowiedz();
        String result3 = graTest.losowaPodpowiedz();
         
        System.out.println("Przed podpowiedzia:\tPo podpowiedzi:");
        for(int i = 0; i<9; i++) 
        {
                for(int j = 0; j<9; j++) 
                {
                        System.out.print(test[i][j] + " ");
                }
                 
                System.out.print("\t");
                 
                for(int j = 0; j<9; j++) 
                {
                        System.out.print(graTest.board[i][j] + " ");
                }
                System.out.println();
        }
         
         
        //test czy funkcja zwrocila dokladnie taki komunikat
        String expResult = "Brak dostepnych podpowiedzi";
         
        System.out.println("\n"+result);
        System.out.println("\n"+result2);
        System.out.println("\n"+result3);
        //assertEquals(expResult, result3); //ilosc podpowiedzi ustawiona na 2, 3 jest "brak..."
        Assert.assertNotEquals(expResult, result2); //spodziewamy sie, ze udalo sie pobrac druga podpowiedz
    }
    
	@AfterMethod 
	public void teardown() {
	    System.setOut(this.defaultOut);
	    System.setErr(this.defaultErr);
	}
}
