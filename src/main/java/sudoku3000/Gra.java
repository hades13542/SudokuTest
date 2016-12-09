package sudoku3000;
import java.lang.String;
import java.io.Serializable;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import sun.misc.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Integer;
import java.io.File;


import java.net.URL;
import java.net.URLClassLoader;
/**
*klasa reprezentująca daną grę
*/

public class Gra extends Generator implements Serializable
{
        public long timeAtStart=0;
	private String nazwagry="";		//nazwa gry
	private String poziom;			//poziom trudnosci na jakim toczy sie gra
	private int iloscpodpowiedzi;		//ilosc dostepnych podpowiedzi
	private static final String ALGO = "AES";	//algorytm szyfrujący
	private static final byte[] keyValue = new byte[] { 'g', '5', 'r', '@', 'g', '8', '[','/', '7', 'e', '<','*', '4', 'l', ',', '!' };	//128-bit klucz


	static Key generateKey() throws Exception 
	{
        	Key key = new SecretKeySpec(keyValue, ALGO);
        	return key;
	}
/**
*funckja serializuje, szyfruje i zapisuje do pliku binarnego obiekt, na rzecz którego jest wywoływana
*jako argument przyjmuje string, który jest ścieżką zapisu
*pierwsze cztery bajty zapisanego pliku nie są szyfrowane i stanowią informacje o ilości bajtów w pozostałej części pliku
*@param nazwa nazwa pliku do którego zostanie zapisana gra
*@return true jeśli zapis przebiegł pomyślnie
*/
	public boolean zapisz(String nazwa) throws Exception		
	{
                System.out.println("Zapis w klasie Gra: " +nazwa);
		try
		{
        		Key key = generateKey();					
        		Cipher c = Cipher.getInstance(ALGO);
        		c.init(Cipher.ENCRYPT_MODE, key);
			ByteArrayOutputStream a = new ByteArrayOutputStream();		//} serializacja
        		ObjectOutputStream b = new ObjectOutputStream(a);		//}
        		b.writeObject(this);						//}

        		byte[] tab = c.doFinal(a.toByteArray());		//szyfrujemy zserializowany obiekt Gra
			DataOutputStream strumien = new DataOutputStream(new FileOutputStream(nazwa));
			int rozmiar = tab.length;			
			strumien.writeInt(rozmiar);			//zapisujemy na poczatek pliku int z informacja o rozmiarze
			strumien.write(tab);				//zapis
			strumien.close();
			return true;					//jesli zapis przebiegl pomyslnie to zwracamy prawde	
		} 
		catch(IOException e)			
		{
			System.out.println("Błąd wejścia-wyjścia");
			return false;					//jesli wystapil bląd to zwracamy fałsz
		}

	}
/**
*funkcja wczytuje grę z zaszyfrowanego pliku binarnego
*jako argument przyjmuje ścięzke do wczytywanego pliku
*@param nazwa ścieżka do wczytywanego pliku
*@return obiekt typu Gra z wczytaną grą
*/
	public static Gra wczytaj(String nazwa) throws Exception
	{
            System.out.println("Wczyt w klasie Gra: " +nazwa);
		try
		{
			DataInputStream strumien = new DataInputStream(new FileInputStream(nazwa));
			int rozmiar = strumien.readInt();		//odczytujemy pierwsze 4 najty pliku, które zawierają informację o ropzmiarze pliku
			byte[] bufor = new byte[rozmiar];		//tworzymy bufor
			strumien.read(bufor);				//wczytywanie
			strumien.close();

			Key key = generateKey();
       			Cipher c = Cipher.getInstance(ALGO);
       			c.init(Cipher.DECRYPT_MODE, key);
        		byte[] decValue = c.doFinal(bufor);				//deszyfrujemy
	
			ByteArrayInputStream a = new ByteArrayInputStream(decValue);	//}
        		ObjectInputStream b = new ObjectInputStream(a);			//}
        		Object decryptedValue = b.readObject();				//}  deserializacja
			Gra test = (Gra)decryptedValue;
		
			for(int i=nazwa.length()-1;i>=0;i--)
			{
				if(nazwa.charAt(i)==File.separator.charAt(0))
				{
					nazwa = nazwa.substring(i+1,nazwa.length());		//do pola nazwa wprowadzamy nazwe
					break;
				}
			}
			test.setName(new String(nazwa));
			return test;
	
	
		} 
		catch(FileNotFoundException e)
		{	
			return null;					//w przypadku probemow zwracamy nulla	
		} 				
		catch(IOException e)
		{
			return null;
		}
		catch(OutOfMemoryError e)
		{
			return null;
		}
		catch(IllegalBlockSizeException e)
		{
			return null;
		}
	}
	public void setPodpowiedzi(int i)
	{
		this.iloscpodpowiedzi = i;
	}
	public int getPodpowiedzi()
	{
		return this.iloscpodpowiedzi;
	}
	public void setName(String name)
	{
		this.nazwagry = new String(name);
	}
	public String getName()
	{
		return this.nazwagry;
	}
/**
*@return true zwracva true jeśli plansza, na której toczy się gra jest identyczna z planszą z rozwiązanie sudoku
*/
	public boolean czyWin()				//porownuje plansze z rozwiazaniem
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(this.board[i][j] != this.solution[i][j])
				{
					return false;
				}
			}
		}
		return true;
	}
	public void setPoziom(String x)
	{
		this.poziom = x;
	}
	public String getPoziom()
	{
		return this.poziom;
	}
/**
*@param row - wiersz
*@param col - kolumna
*@return true zwraca true jeśli liczbę pod daną pozycją można zmienić
*/
	public boolean czyMozna(int row, int col)		//sprawdza czy liczbe pod danymi wspolrzednymi mozna zmienic
	{
		if(this.oryginal[row][col]==0)
		{
			return true;
		}
		return false;
	}
/**
*funkcja losuje podpowiedź
*w przypadku braku dostępnych podpowiedzi zwraca o tym informacje
*@return String w ustalonym formacie z informacją, które pole zostąło podpowiedzniane
*/
	public String losowaPodpowiedz()			
	{
		if(this.iloscpodpowiedzi>0)			
		{
			String kom = "";
			int iloscmozliwych=0;
			int ktory;
			int[][] temp = new int[9][9];
			for(int row=0;row<9;row++)
			{
				for(int col=0;col<9;col++)
				{
					if(this.board[row][col] == this.solution[row][col])
					{
						temp[row][col] = 1;
					}
					else
					{
						temp[row][col] = 0;
						iloscmozliwych++;
					}
				}
			}
			Random rand = new Random();
			ktory = 1+rand.nextInt(100)%(iloscmozliwych);
			for(int row=0;row<9 && ktory >= 0;row++)
			{
				for(int col=0;col<9 && ktory >= 0;col++)
				{
					if(temp[row][col]==0 && ktory >= 0)
					{
						ktory--;
						if(ktory==0)
						{
							this.board[row][col] = this.solution[row][col];
							Integer r = new Integer(row+1);
							Integer c = new Integer(col+1);
							kom +="uzupelniono: ";
							kom += r.toString();
							kom += "x";
							kom += c.toString();
						}
					}
				}
			}
			if(ktory>0)
			{
				kom = "sudoku jest wypelnione, a ty zadasz podpowiedzi, ale ta sytuacja raczej nie powinna wystapic, jesli to widzisz to ktos cos skopał ;/";
			}
			this.iloscpodpowiedzi--;
			return new String(kom);
		}
		else
		{
			return new String("Brak dostepnych podpowiedzi");
		}
	}
}
