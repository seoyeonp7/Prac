package kr.or.ddit.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileuploadController {
	@RequestMapping(value="/file/upload.do",method=RequestMethod.POST)
//	@PostMapping("/file/upload.do") //spring
	public String upload(HttpServletRequest req, HttpSession session) throws IOException, ServletException {
		String textPart = req.getParameter("textPart");
		String numPart = req.getParameter("numPart");
		String datePart = req.getParameter("datePart");
		log.info("textPart : {}",textPart);
		log.info("numPart : {}",numPart);
		log.info("datePart : {}",datePart);
		session.setAttribute("textPart", textPart);
		session.setAttribute("numPart", numPart);
		session.setAttribute("datePart", datePart);
		
		String saveFolerURL = "/resources/prodImages"; //논리적 경로
		ServletContext application = req.getServletContext();
		String saveFolerPath = application.getRealPath(saveFolerURL);
		File saveFolder = new File(saveFolerPath);
		if(!saveFolder.exists())
			saveFolder.mkdirs();
		
		List<String> metadata = req.getParts().stream()
					.filter((p)->p.getContentType()!=null&&p.getContentType().startsWith("image/"))
					.map((p)->{
						//메타데이터 두 개
						String originalFilename = p.getSubmittedFileName();
						String saveFilename = UUID.randomUUID().toString();
						File saveFile = new File(saveFolder,saveFilename);
						try {
							p.write(saveFile.getCanonicalPath()); //전체경로
							String saveFileURL = saveFolerURL + "/" + saveFilename;
							return saveFileURL;
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}).collect(Collectors.toList());
		session.setAttribute("fileMetadata", metadata);
		return "redirect:/fileupload/uploadForm.jsp";
	}
}
