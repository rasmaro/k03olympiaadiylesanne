import java.io.*;
import java.util.*;

public class Search {
	public static void main(String arg[]) throws Exception {
		//FilterText("tekstsis.txt");
		FilterText("input0.txt", "output0.txt");
		FilterText("input1.txt", "output1.txt");
		FilterText("input2.txt", "output2.txt");
		FilterText("input3.txt", "output3.txt");
		FilterText("input4.txt", "output4.txt");
		FilterText("input5.txt", "output5.txt");
		FilterText("input6.txt", "output6.txt");
		FilterText("input7.txt", "output7.txt");
		FilterText("input8.txt", "output8.txt");
		FilterText("input9.txt", "output9.txt");
		FilterText("input10.txt", "output10.txt");
	}
	
	public static void FilterText(String filename, String filenameOutput) throws Exception {
		System.out.println("Faili <"+filename+"> töödeldakse");
		FileReader freader = new FileReader(filename);
		BufferedReader reader = new BufferedReader(freader);
		List<String> names = new ArrayList<String>();
		String row = reader.readLine();
		String jukuTeema = row;
		
		while(row != null) {
			names.add(row);
			row = reader.readLine();
			if(row != null && row.length() > 0) jukuTeema = row;
		}
		
		System.out.println("Faili <"+filename+"> teema: "+jukuTeema);
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		FileWriter fwriter = new FileWriter(filenameOutput);
		PrintWriter writer = new PrintWriter(fwriter);
		String modifiedJukuTeema = jukuTeema;
		int wildcardCount = 0;
		for (int m = 0; m < jukuTeema.length(); m++){
			if(jukuTeema.charAt(m) == '*') {
				wildcardCount++;
			}
		}
		
		for(String name: names)
		{
			modifiedJukuTeema = jukuTeema;
			int lastSkip = 0;
			boolean foundMatch = false;
			
			if(name.length() == modifiedJukuTeema.length()) {
				
				for (int m = 0; m < modifiedJukuTeema.length(); m++){
					if(modifiedJukuTeema.charAt(m) == '*') {
						
						for (int xx = 0; xx < wildcardCount; xx++){
							for (int i = 0; i < alphabet.length(); i++){
								char c = alphabet.charAt(i);
								
								if(filename.equals("input8.txt")) {
									System.out.println(modifiedJukuTeema);
								}
								
								if(modifiedJukuTeema.equals(name) && !jukuTeema.equals(modifiedJukuTeema)) {
									writer.println(name);
									System.out.println("Faili <"+filenameOutput+"> lisati: "+modifiedJukuTeema);
									foundMatch = true;
								}
								if(foundMatch) { foundMatch = false; break;}
								String editedTeema = "";
								editedTeema = modifiedJukuTeema.substring(0,m)+c+modifiedJukuTeema.substring(m+1);	
								
								for (int ii = 0; ii < alphabet.length(); ii++){
									char cc = alphabet.charAt(ii);
									for (int mm = 0; mm < modifiedJukuTeema.length(); mm++){
										if(modifiedJukuTeema.charAt(mm) == '*') {
											modifiedJukuTeema = modifiedJukuTeema.substring(0,mm)+cc+modifiedJukuTeema.substring(mm+1);
											
											if(modifiedJukuTeema.equals(name) && !jukuTeema.equals(modifiedJukuTeema)) {
												foundMatch = true;
											}
										}
										modifiedJukuTeema = editedTeema;
										
										if(foundMatch) { foundMatch = false; break;}
									}
								}
							}
						}
					}	
				}
			}
		}
		writer.close();
	}
}