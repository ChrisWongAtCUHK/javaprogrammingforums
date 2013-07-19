import java.io.File;

import sun.applet.Main;


public class ListFiles {
	// http://www.javaprogrammingforums.com/java-programming-tutorials/3-java-program-can-list-all-files-given-directory.html
	public static void main(String[] args) {
		
		// http://stackoverflow.com/questions/41894/0-program-name-in-java-discover-main-class
		StackTraceElement[] stack = Thread.currentThread ().getStackTrace ();
	    StackTraceElement main = stack[stack.length - 1];
	    String mainClass = main.getClassName ();

		// Directory path here
		String path = ".";
		if(args.length != 1){
			System.out.println("Usage: " + mainClass + "path");
			System.out.println("Example-- " + mainClass + "D:\\\\RemoteLinux");
			return;
		}
		path = args[0];

		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				System.out.println(files);
			}
		}
	}
}
