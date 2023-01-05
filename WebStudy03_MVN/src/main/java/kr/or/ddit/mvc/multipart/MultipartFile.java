package kr.or.ddit.mvc.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface MultipartFile {
	public byte[] getbytes() throws IOException;
	public String getContentType();
	public InputStream getInputStream() throws IOException;
	public String getName();
	public String getOriginalFIlename();
	public long getSize();
	public boolean isEmpty();
	public void transferTo(File dest) throws IOException;
}
