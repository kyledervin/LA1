import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;

public class MusicStore {
	
	String filePath = "";

	try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
		
		String line;
		while ((line = reader.readLine()) != null) {
		}
	}
	
	catch(FileNotFoundException e) {
		System.out.println("Can't locate file.");
	}
	catch(IOException e) {
		System.out.println("Something went wrong.");
	}
}
