import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class ListFiles {
	// http://www.javaprogrammingforums.com/java-programming-tutorials/3-java-program-can-list-all-files-given-directory.html
	public static void main(String[] args) {
		
		// http://stackoverflow.com/questions/41894/0-program-name-in-java-discover-main-class
		StackTraceElement[] stack = Thread.currentThread ().getStackTrace ();
	    StackTraceElement main = stack[stack.length - 1];
	    String mainClass = main.getClassName ();
	    
	    //String mainClass = System.getProperty("sun.java.command");

		// Directory path here
		String path = ".";
		if(args.length != 1){
			System.out.println("Usage: java " + mainClass + " path");
			System.out.println("Example-- java " + mainClass + " D:\\RemoteLinux");
			return;
		}
		
		path = args[0];
		listRecurrsive(path);
		
		
	}
	
	public static void listRecurrsive(String path){
		String file;
		File folder = new File(path);
		// Check if the path exists
		if(!folder.exists()){
			System.out.println("No such path--" + path);
			return;
		}
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			// Check if file or directory
			if (listOfFiles[i].isFile()) {
				// Get the last modified timestamp
				long lastModified = listOfFiles[i].lastModified();
				Timestamp ts = new Timestamp(lastModified);
				String date = new SimpleDateFormat("dd/MM/yyyy\thh:mm:ss").format(ts);
				
				file = listOfFiles[i].getName();
				System.out.println(date + "\t" + path + "\\" + file);
			} else if(listOfFiles[i].isDirectory()){
				String subdir = listOfFiles[i].toString();
				listRecurrsive(subdir);
			}
		}		
	}
}
