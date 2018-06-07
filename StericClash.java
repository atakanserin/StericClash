
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class StericClash{

    //numComparison is a global variable because it is used in quick sort method.
	public static int numComparison;
		
	public static void main(String[] args) {
		
		ArrayList<Atom> firstList = new ArrayList<Atom>();
		ArrayList<Atom> secondList = new ArrayList<Atom>();
		
		
        //Files are hardcoded into the program, first file is hold in firstList ArrayList<Atom>, secondFile is hold in secondList ArrayList<Atom>
		
        //First File
        try {
			File file = new File("/Users/mac/Desktop/1CDH.pdb");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			Atom atom;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.substring(0,4).equals("ATOM") || line.substring(0,6).equals("HETATM")) {
					atom = new Atom(Integer.parseInt(line.substring(7,11).trim()), line.substring(13,16) , line.substring(17,20), Double.parseDouble(line.substring(31,38).trim()), Double.parseDouble(line.substring(39,46).trim()) , Double.parseDouble(line.substring(47,54).trim()) ) ;
					firstList.add(atom);
				}
			}
			fileReader.close();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        //Second File
		try {
			File file = new File("/Users/mac/Desktop/2CSN.pdb");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			Atom atom;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.substring(0,4).equals("ATOM") || line.substring(0,6).equals("HETATM")) {
					atom = new Atom(Integer.parseInt(line.substring(7,11).trim()), line.substring(13,16) , line.substring(17,20), Double.parseDouble(line.substring(31,38).trim()), Double.parseDouble(line.substring(39,46).trim()) , Double.parseDouble(line.substring(47,54).trim()) ) ;
					secondList.add(atom);
				}
			}
			fileReader.close();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		int numClashes = 0;
		numComparison = 0;
		
		quickSort(firstList, 0, firstList.size()-1);
		quickSort(secondList, 0, secondList.size()-1);
		//numComparison++ is embedded in the quickSort method too.
		
		
		//Trim unnecessary atoms.
		
		Atom minXFirst = firstList.get(0);
		Atom minXSec = secondList.get(0);
		
		if(minXSec.x < minXFirst.x) {
		for(int i=0; i<secondList.size(); i++) {
			numComparison++;
			if(minXFirst.x -4 < secondList.get(0).x) {
				numComparison++;
				break;
			}
			secondList.remove(0);
		}
		
		}
		
		if(minXFirst.x < minXSec.x) {
			for(int i=0; i<firstList.size(); i++) {
				numComparison++;
				if(minXSec.x -4 < firstList.get(0).x) {
					numComparison++;
					break;
				}
				firstList.remove(0);
			}
			
			}
		
		
		
		Atom maxXSec = secondList.get((secondList.size()-1));
		Atom maxXFirst = firstList.get((firstList.size()-1));
		
		
		if(maxXFirst.x > maxXSec.x) {
			for(int i=0; i<firstList.size(); i++) {
				numComparison++;
				if(firstList.get(firstList.size()-1).x - 4 < maxXSec.x) {
					numComparison++;
					break;
				}
				firstList.remove(firstList.size()-1);
			}
		}
		
		if(maxXSec.x > maxXFirst.x) {
			for(int i=0; i<secondList.size(); i++) {
				numComparison++;
				if(secondList.get(secondList.size()-1).x - 4 < maxXFirst.x) {
					numComparison++;
					break;
				}
				secondList.remove(secondList.size()-1);
			}
		}
		
        
        //Find Clashes of the first file.
        int coreComparison = numComparison;
        numComparison = 0;
		            System.out.println("FIRST                        SECOND");
		for(int i=0; i<firstList.size(); i++) {
			numComparison++;
			for(int j=0; j<secondList.size(); j++) {
				numComparison++;
				if(isClashing(firstList.get(i),secondList.get(j))) {
					System.out.println("Atom no: "+firstList.get(i).atomNum+" clashes with Atom no: "+secondList.get(j).atomNum);
					numComparison++;
					numClashes++;
					break;
				}	
			}
		}
		
        //Find clashes of the second file.
		System.out.println("Number of Clashes: "+numClashes);
		System.out.println("Number of Comparisons: "+ (coreComparison + numComparison));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
		
		numComparison = 0;
		numClashes = 0;
                    System.out.println("SECOND                       FIRST");
		for(int i=0; i<secondList.size(); i++) {
			numComparison++;
			for(int j=0; j<firstList.size(); j++) {
				numComparison++;
				if(isClashing(secondList.get(i),firstList.get(j))) {
					System.out.println("Atom no: "+secondList.get(i).atomNum+" clashes with Atom no: "+firstList.get(j).atomNum);
					numComparison++;
					numClashes++;
					break;
				}	
			}
		}
		
		System.out.println("Number of Clashes: "+numClashes);
		System.out.println("Number of Comparisons: "+ (coreComparison + numComparison));
	  
	}
//Clash calculator assuming atoms are spheres with radii 2 Angsroms.
public static boolean isClashing(Atom a, Atom b) {
	return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) + (a.z - b.z) * (a.z - b.z)) < 4;
}

// Quick sort with according to x coordinates of atoms.
public static void quickSort(ArrayList<Atom> list, int low, int high) {
	
	double pivot = list.get((low + high)/2).x;

	
	int i = low, j = high;
	while (i <= j) {
		numComparison++;
		while (list.get(i).x < pivot) {
			numComparison++;
			i++;
		}

		while (list.get(j).x > pivot) {
			numComparison++;
			j--;
		}

		if (i <= j) {
			//numComparison++;
			Atom temp = list.get(i);
			list.set(i,list.get(j));
			list.set(j,  temp);
			i++;
			j--;
		}
	}

	if (low < j) {
		numComparison++;
		quickSort(list, low, j);
	}
	if (high > i) {
		numComparison++;
		quickSort(list, i, high);
	}
}

}

//Atom class to hold atoms necessary||unnecessary features.
class Atom {
	public int atomNum; 
	public String atomName;
	public String resName; 
	public double x;  
	public double y; 
	public double z;

	public Atom(int atomNum, String atomName, String resName, double x, double y, double z ) {
		this.atomNum = atomNum;
		this.atomName = atomName;
		this.resName = resName;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return atomNum+" "+atomName+" "+resName+" "+x+" "+y+" "+z+"\n";
	}
}




