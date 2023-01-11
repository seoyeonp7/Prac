package kr.or.ddit.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.vo.FileTestVO;

@Controller
@RequestMapping("/fileUpload")
public class FileController {
	@Inject
	private WebApplicationContext context;
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		saveFolderRes = context.getResource("classpath:kr/or/ddit/file");
		saveFolder = saveFolderRes.getFile();
	}
	
	@GetMapping
	public String fileForm() {
		return "file/uploadForm";
	}
	
	@PostMapping
	public String fileProcessCase2(
		@ModelAttribute("fileVO") FileTestVO commandObject //request
		, RedirectAttributes redirectAttributes
	) throws IllegalStateException, IOException { //FileTestVO에는 part data와 metadata 둘다 o
		commandObject.file1SaveTo(saveFolder);
		commandObject.file2SaveTo(saveFolder);
		redirectAttributes.addFlashAttribute("result", commandObject);
		return "redirect:/fileUpload";
	}
	
//	@PostMapping
	public String fileProcessCase1(
		@RequestParam String textParam	
		, @RequestParam String dateParam
		, @RequestPart MultipartFile file1
		, @RequestPart MultipartFile[] file2
		, RedirectAttributes redirectAttributes
	) throws IllegalStateException, IOException {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("textParam", textParam);
		result.put("dateParam", dateParam);
		
		if(!file1.isEmpty()) {
			File dest = new File(saveFolder,UUID.randomUUID().toString());
			file1.transferTo(dest);
			result.put("file1",Collections.singletonMap("savename", dest.getName()));
		}
		
		result.put("file2", Arrays.stream(file2)
								.filter((f)->!f.isEmpty())
								.map((f)->{	//map 엘리먼트 하나 변환
									try {
										File dest = new File(saveFolder,UUID.randomUUID().toString());
										f.transferTo(dest);
										return Collections.singletonMap("savename", dest.getName());
									} catch (IOException e) {
										throw new RuntimeException(e);
									}
								}).collect(Collectors.toList())
					);
//		session.setAttribute("result", result);
		redirectAttributes.addFlashAttribute("result", result); //꺼내는 순간 삭제
		return "redirect:/fileUpload";
		
	}
}
