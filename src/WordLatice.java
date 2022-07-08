import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
public class WordLatice {
	Graphv4 graph = new Graphv4();
	 Random g = new Random();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Scanner scan = new Scanner(System.in);
	        String fname;
	        System.out.println("Podaj nazwe pliku:");
	        fname = scan.next();
	        WordLatice main = new WordLatice();
	        main.loadLattice(fname);
			System.out.println("has cycle: " + main.containsCycles());	
			String[] array = main.successors("<s>");
			for(int i=0; i<array.length; i++)
			{
				System.out.print(array[i]+ " ");
			}
			
	}
	
	void loadLattice( String fname) { // Tworzy graf z pliku i normalizuje prawdopodobie�stwa
        String line;
        try {
            Scanner scan = new Scanner(new FileReader(fname));
            while(scan.hasNext()) {
                line  = scan.nextLine();
                //System.out.println(line);
                String[] arr = line.split(" ");
               graph.addEdge(arr[0], arr[1], Double.parseDouble(arr[2]));
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
	
	boolean containsCycles()
	{
		 boolean hasCycle = graph.detectCycle();
		 return hasCycle;
	}
	
	String [] successors(String word)
	{
		String[] array = graph.bfs(word);
		return array;
	}
		
	void pathFinderReset()
	{
		String line;
		
	}
	
	String getNextPath()
	{
		return null;
	}
}


