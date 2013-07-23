import java.io.File;
import java.util.ArrayList;


public class FileList<T> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	private String spliter;
	
	public FileList(String path, String spliter){
		super();
		this.path = path;
		this.spliter = spliter;
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(T e){
		// File only
		if(!(e instanceof File))
			return false;
		File file = (File)e;
		
		return super.add((T) file);
	}
	
	// Debug
	public void show(){
		for(T t: this){
			System.out.println(t.toString());
		}
		System.out.println();
	}
	
	// Check if contains by comparing relative file name
	public File contains(String relativeFileName){
		for(T t: this){
			if(relativeFileName.equals(t.toString().replace(this.path, "").replaceFirst(spliter, ""))){
				return (File) t;
			}
		}
		
		return null;
	}
	

}
