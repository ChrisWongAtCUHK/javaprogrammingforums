import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ListFilesWithName {
	public static final String dirSeparator = System.getProperty("file.separator");				// window
	public static final String linuxFileSeparator = "/";										// linux
	public static final CharSequence asterisk = "*", target1 = ".", replacement1 = "\\.", target2  = "*", replacement2 = ".*";
	
	public static void main(String[] args) {
		
		// Directory path here
		String path = "", pattern = "";

		if(args.length != 1){
			// If no, use the current directory as path
			System.out.println("Warning: please input with double quotes");
			
			path = System.getProperty("user.dir");
		} else {
		
			// Check if the input has '*'
			if (args[0].contains(asterisk)) {
				// Check if the input has dirSeparator
				if (args[0].contains(dirSeparator)) {
					
					// If yes, split the path and the pattern by dirSeparator
					String spliter = "\\\\";									// window
					if(dirSeparator.equals(linuxFileSeparator)){
						spliter = linuxFileSeparator; 							// linux
					}
					
					String[] strs = args[0].split(spliter);						// window
					//String[] strs = args[0].split("/"); 		// linux
					
					for(int i = 0; i < strs.length -  2; i++){
						path += strs[i] + dirSeparator;
					}
					path += strs[strs.length -  2];
					pattern = strs[strs.length - 1];
				} else {
					// If no, use the current directory as path and use args[0] as pattern
					path = System.getProperty("user.dir");
					pattern = args[0];
				}
				
				// Replacement for regular expression
				pattern = pattern.replace(target1, replacement1);
		        pattern = pattern.replace(target2, replacement2);
			} else {
				// If no '*', just use args[0] as path
				path = args[0];
			}
		}
		
		listRecurrsive(path, pattern);
	}
	
	public static void listRecurrsive(String path, String pattern){
		
		File folder = new File(path);
		// Check if the path exists
		if(!folder.exists()){
			System.out.println("No such path--" + path);
			return;
		} else if(folder.isFile()){
			// Special case for input path is file name
			// Get the last modified timestamp
			long lastModified = folder.lastModified();
			Timestamp ts = new Timestamp(lastModified);
			String date = new SimpleDateFormat("dd/MM/yyyy\tHH:mm:ss").format(ts);

			System.out.println(date + "\t" + path);
			return ; 
		}
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			// Check if file or directory
			if (listOfFiles[i].isFile()) {
				// Check if the pattern match
				
				String fileName = listOfFiles[i].getName();
				if((!pattern.equals("")) &&  !fileName.matches(pattern)){
					continue;
				}
				
				// Get the last modified timestamp
				long lastModified = listOfFiles[i].lastModified();
				Timestamp ts = new Timestamp(lastModified);
				String date = new SimpleDateFormat("dd/MM/yyyy\thh:mm:ss").format(ts);
				
				
				System.out.println(date + "\t" + path + dirSeparator + fileName);			// window
			} else if(listOfFiles[i].isDirectory()){
				String subdir = listOfFiles[i].toString();
				listRecurrsive(subdir, pattern);
			}
		}		
	}

}
