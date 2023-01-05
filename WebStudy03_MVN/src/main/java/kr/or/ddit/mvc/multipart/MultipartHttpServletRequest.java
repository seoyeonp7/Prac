package kr.or.ddit.mvc.multipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Part -> MultipartFile
 *
 */
public class MultipartHttpServletRequest extends HttpServletRequestWrapper {

	private Map<String, List<MultipartFile>> fileMap; //key값 : name, value 파일list
	
	public MultipartHttpServletRequest(HttpServletRequest request) throws IOException, ServletException { // 파트들에 대한 전처리 구조 필요
		super(request);
		parseRequest(request);
	}
	
	private void parseRequest(HttpServletRequest request) throws IOException, ServletException {
		fileMap = new LinkedHashMap<>();
		request.getParts().stream()
					.filter((p)->p.getContentType()!=null)
					.forEach((p)->{
						String partName = p.getName();
						StandardServletMultipartFile file = new StandardServletMultipartFile(p);
						List<MultipartFile> fileList = Optional.ofNullable(fileMap.get(partName))
																.orElse(new ArrayList<>());
						fileList.add(file);
						fileMap.put(partName, fileList);
					});
	}

	public Map<String, List<MultipartFile>> getFileMap() {
		return fileMap;
	}
	
	public MultipartFile getFile(String name){
		List<MultipartFile> files = fileMap.get(name);
		if(files!=null && !files.isEmpty())
			return files.get(0);
		else return null;
	}
	
	public List<MultipartFile> getFiles(String name){
		return fileMap.get(name);
	}
	
	public Enumeration<String> getFileNames(){
		Iterator<String> names = fileMap.keySet().iterator();
		//Enumeration,Iterator => collectionVIew
		return new Enumeration<String>() { //익명객체

			@Override
			public boolean hasMoreElements() {
				return names.hasNext();
			}

			@Override
			public String nextElement() {
				return names.next();
			} 
		};
	}

}
