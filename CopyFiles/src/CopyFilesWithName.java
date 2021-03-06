import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFilesWithName {
	public static final String dirSeparator = System.getProperty("file.separator");				// window
	public static final String linuxFileSeparator = "/";										// linux
	public static final CharSequence asterisk = "*", target1 = ".", replacement1 = "\\.", target2  = "*", replacement2 = ".*";

	public static String spliter = "\\\\";
	static int copyCount = 0;
	
	// Directory path here
	static String srcPath = "", pattern = "", distPath = "";
			
	public static void main(String[] args) {
		
		// If yes, split the path and the pattern by dirSeparator	// window
		if(dirSeparator.equals(linuxFileSeparator)){
			spliter = linuxFileSeparator; 							// linux
		}
		
		if(args.length != 2){
			System.out.println("Usage: java " + System.getProperty("sun.java.command") + " srcPath" + " dstPath");
			srcPath = System.getProperty("user.dir");
		} else {
		
			// Check if the input has '*'
			if (args[0].contains(asterisk)) {
				// Check if the input has dirSeparator
				if (args[0].contains(dirSeparator)) {
					
					String[] strs = args[0].split(spliter);						// window
					
					for(int i = 0; i < strs.length -  2; i++){
						srcPath += strs[i] + dirSeparator;
					}
					srcPath += strs[strs.length -  2];
					pattern = strs[strs.length - 1];
				} else {
					// If no, use the current directory as path and use args[0] as pattern
					srcPath = System.getProperty("user.dir");
					pattern = args[0];
				}
				
				// Replacement for regular expression
				pattern = pattern.replace(target1, replacement1);
		        pattern = pattern.replace(target2, replacement2);
			} else {
				// If no '*', just use args[0] as path
				srcPath = args[0];
			}
			distPath = args[1];
			
			copyRecursive(srcPath, pattern);
			
			System.out.println("No. of copy: "+ copyCount);
		}

	}

	// List the files recurrsivly
	public static void copyRecursive(String path, String pattern){
		
		File folder = new File(path);
		// Check if the path exists
		if(!folder.exists()){
			System.out.println("No such path--" + path);
			return;
		} else if(folder.isFile()){
			// Special case for input path is file name
			return ; 
		}
		
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			// Check if file or directory
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
				
				// Check if the pattern match		
				if((!pattern.equals("")) &&  !fileName.matches(pattern)){
					continue;
				}
				
				// Check if the file exists
				File foundFile = new File(listOfFiles[i].toString().replace(srcPath, distPath));
				if(foundFile.exists()){
					// Check if the file is most update
					if(foundFile.lastModified() >= listOfFiles[i].lastModified()){
						// Do nothing
						System.out.println(listOfFiles[i] + " is most update.");
						continue;
					}
				} 
				
				// process copy file operation
				copyFile(listOfFiles[i].toString(), listOfFiles[i].toString().replace(srcPath, distPath));

			} else if(listOfFiles[i].isDirectory()){
				
				// Check if the sub directory exists
				File foundFile = new File(listOfFiles[i].toString().replace(srcPath, distPath));
				if(!foundFile.exists()){
					// If not exist, create the directory
					mkdir(listOfFiles[i].toString().replace(srcPath, distPath));
				}
				
				copyRecursive(listOfFiles[i].toString(), pattern);
			}
		}		
	}
	
	// Copy file from one to one
	public static void copyFile(String src, String dist){
		copyCount++;
		System.out.println("Copy from " + src + " to " + dist);
		try {
			File f1 = new File(src);
			File f2 = new File(dist);
			InputStream in = new FileInputStream(f1);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			
			in.close();
			out.close();
			System.out.println("File copied.");
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage() + " in the specified directory.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Create directory
	public static void mkdir(String dir){
		File theDir = new File(dir);
		System.out.println("mkdir " + dir);
		theDir.mkdir();
	}
}
