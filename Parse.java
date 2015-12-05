import java.net.URL;
import org.jsoup.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import lemurproject.indri.IndexEnvironment;

public class Parse {
	public static void main(String[] args) {
		// extract text from a URL
		String text = "";
		try {
			text = Jsoup.parse(new URL("https://en.wikipedia.org/wiki/Apple_Inc."), 10000).text();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// write text to a file
		String fileName = "temp.txt";
		try {
			FileWriter fileWriter =
					new FileWriter(fileName);
			// always wrap FileWriter in BufferedWriter
			BufferedWriter bufferedWriter =
					new BufferedWriter(fileWriter);
			bufferedWriter.write(text);
			// always close files
			bufferedWriter.close();        
		} catch (Exception e) {
			e.printStackTrace();
		}

		// let Indri parse
		IndexEnvironment env = new IndexEnvironment();
		try {
			env.setMemory(1024*1024*1024); // 1 GB
			env.setStemmer("krovetz");
			env.create("/Users/yba/Documents/U/Sirius/database");
			env.addFile("/Users/yba/Documents/U/Sirius/text.txt");
			env.close();
			System.out.println("documentsIndexed: " + env.documentsIndexed());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}