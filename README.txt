# StericClash

Mehmet Atakan Serin

In order to find sterical clashes of atoms naive method uses about~ 3M comparisons.
In StericalClash.java I used a method that that uses 1.9M and 1.6M comparisons accordingly.

Main idea in the method was trimming the proteins, in other words
getting rid of atoms that are never going to clash.
Atoms are stored in ArrayList of type Atom and it is assumed that atoms have 2 Angsrom radii.
Firstly both array lists are sorted in quick sort according to their x coordinates. Luckily sorting did not cost much comparisons. The reason of sorting was that I wanted to see atoms which share a feature closer and also useless atoms are located closely to each other(soon will be explained). Below observation and illustration brings a deeper understanding to reason of sorting atoms by using their x coordinates:

- If two atoms x coordinates difference is greater than 4 than these two atoms can not clash.(Of course it can be x, y or z coordinate but I used x coordinate for no reason.)

I used this key fact to trim the array lists from unnecessary atoms. Illustration below shows that sorting helps to detect and cluster unnecessary atoms together.



x coordinates increasing order direction —>  	

	—-unused atoms—-
File 1	aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
			|                |
		        |                | 
File 2			bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
					  ——-unused atoms———



Above there are are two proteins and the atoms that are never going to be used. For this specific example File 2’s minimum x coordinate is greater than File 1’s minimum X coordinate. So File 1’s lower end(left part) should be trimmed. Same situation applies for File 2’s upper end(right part): File 2’s maximum x coordinate is greater than File 1’s, so there should happen the trimming.

As a side note my program understands how File’s lower and upper ands are shaped and know where to trim. If there is nothing to trim it does not trim. It is not specifically written for 1CDH and 2CSN. But for convenience File paths are hardcoded into the program. So please check 1CSN and 2PDB file paths in the StericClash.java before compiling.

Compilation, Running the File

javac StericClash.java
java StericClash > output.txt

There is only one output and it contains outputs of 1CDH_2CSN and also 2CSN_1PDB.
