/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku3000;
import java.io.*;
import java.util.*;
import org.fusesource.jansi.AnsiConsole;


/**
   *Klasa do tworzenia "okienek" z menu
   *@author Ryszard Mikulec
   *@version 1.0
*/
public class Menu extends Wyswietlane
{
    
public String logo[] = { " _____           _       _          ",
"/  ___|         | |     | |         ",
"\\ `--. _   _  __| | ___ | | ___   _ ",
" `--. | | | |/ _` |/ _ \\| |/ | | | |",
"/\\__/ | |_| | (_| | (_) |   <| |_| |",
"\\____/ \\__,_|\\__,_|\\___/|_|\\_\\\\__,_|"
};    


public String logo2[] = {" _____           _       _            _____ _____ _____ _____ ",
"/  ___|         | |     | |          |____ |  _  |  _  |  _  |",
"\\ `--. _   _  __| | ___ | | ___   _      / | |/' | |/' | |/' |",
" `--. | | | |/ _` |/ _ \\| |/ | | | |     \\ |  /| |  /| |  /| |",
"/\\__/ | |_| | (_| | (_) |   <| |_| | .___/ \\ |_/ \\ |_/ \\ |_/ /",
"\\____/ \\__,_|\\__,_|\\___/|_|\\_\\\\__,_| \\____/ \\___/ \\___/ \\___/ "
    
};
/**
   *Konstruktor domyślny
*/
   Menu(){this.opcje=null;}
/**
   *Konstruktor z jednym parametrem
   *@param opcje tablica stringów z opcjami do wyboru w menu
*/
   Menu(String[] opcje)
   {
      this.opcje = opcje;
   }
/**
   *Konstruktor z dwoma parametrami 
   *@param opcje tablica stringów z opcjami do wyboru w menu
   *@param instrukcja String do zmiany domyślnej instrukcji pojawiającej się na górze "okienka" 
*/
   Menu(String[] opcje, String instrukcja)
   {
      this.opcje = opcje;
      this.miniInstrukcja = instrukcja;
   }
   private int rozmiarX = 70,
   rozmiarY = 21;
   private String miniInstrukcja = new String("Aby wrócić do Menu wybierz 'M'");	//miejsce na twoją reklamę
   private String[] opcje;

/**
   *Metoda przeszukująca katalog "zapisy" w poszukiwaniu plików z zapisanymi grami
   *@return String z katalogiem lub "M" jeśli gracz wybierze wyjście do menu
*/
   public String wczytaj() throws Exception
   {
	boolean czyistnieje;
	//String katalog = "zapisy";		//domyslny katalog z zapisanymi stanami gry
        String katalog = System.getenv("APPDATA");
        if (katalog == null) {
            katalog = System.getProperty("user.home");
           }
        katalog=katalog + "\\Sudoku3000";
        System.out.println(katalog);
	do
	{
		try
		{
      			File dir = new File(katalog);
      			opcje = new String[dir.listFiles().length+1];
      			int licznik = 0;
      			opcje[licznik++] = new String("Wczytaj plik:");
      			for(File plik : dir.listFiles())
			{
         			opcje[licznik] = new String(""+licznik++ +")"+plik.getName());
			}
			czyistnieje = true;
			
		}
		catch(NullPointerException e)
		{
			System.out.print("Blad. Nie znaleziono domyslnego katalogu z zapisanymi stanami gry lub podano bledna sciezke. Prosze wprowadzic sciezke do katalogu ze stanami gry:");
			czyistnieje = false;
			Scanner in = new Scanner(System.in);
			katalog = in.next();
			if(katalog.length()==1 && katalog.charAt(0)=='M')
			{
				return new String("M");
			}
		}
	}
	while(!czyistnieje);
        System.out.println("wczyt z klasy menu: " + katalog);
	return new String(katalog);
   }

/**
   *Metoda wyświetlająca całe menu 
   *@param komunikat wyświetlany na samym dole "okienka"
*/
   void wyswietlKom(String komunikat)
   {
      int licznik = 0;
      System.out.print('+');
      for(int i=0; i<rozmiarX-2; i++)
         System.out.print('-');
      System.out.println('+');

      for(int i=1; i<rozmiarY-1; i++)
      {
         System.out.print('|');
         for(int j=0; j<rozmiarX -2; j++)
            System.out.print(' ');
         System.out.print('|');
         if(i==1)
         {
            for(int j=0; j<45; j++)
               System.out.print('\b');
            System.out.print("Sudoku ver 1.0");
         }
         if(i==3)
         {
            for(int j=0; j<rozmiarX-1; j++)
               System.out.print('\b');
            System.out.print(miniInstrukcja);
         }
         if(i>3 && i<10) {
             for(int j=0; j<66; j++)
               System.out.print('\b');
            System.out.print(logo2[i-4]);
         }
         if(i==rozmiarY-2)
         {
            for(int j=0; j<rozmiarX-1; j++)
               System.out.print('\b');
		    AnsiConsole.systemInstall();
			AnsiConsole.out.print( NIEBIESKI + komunikat + NORMALNY);
            AnsiConsole.systemUninstall();
         }
         if(this.opcje !=null && i>=(rozmiarY-opcje.length)/1.5 && licznik < opcje.length)
         {
            for(int j=0; j<rozmiarX*2/3; j++)
               System.out.print('\b');
		    AnsiConsole.systemInstall();
			AnsiConsole.out.print( CZERWONY + opcje[licznik] + NORMALNY);
            AnsiConsole.systemUninstall();
            licznik++;
         }
         System.out.print('\n');
      }

      System.out.print('+');
      for(int i=0; i<rozmiarX -2; i++)
         System.out.print('-');
      System.out.println('+');

   }
   public String nazwa(int i)		//zwraca nazwe, którą wybrano podczas wczytywania
   {	
	int j=0;
	for(int k = i ; k != 0 ; k/=10, j++){}
	return this.opcje[i].substring(j+1);

   }
}
