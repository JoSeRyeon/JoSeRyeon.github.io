package com.kico.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kico.demo.auth.MyUserDetail;
import com.kico.demo.dto.BoardDTO;
import com.kico.demo.dto.FileDto;
import com.kico.demo.service.BoardService;
import com.kico.demo.service.FileService;
import com.kico.demo.util.MD5Generator;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	
	private final BoardService service;
	private final FileService fileService;
	
	//게시판
	@GetMapping("/user_access")
	public String user_access(Model model, Authentication authentication) {
		List<BoardDTO> boardDTOList = service.getBoardList();
		model.addAttribute("dataList", boardDTOList);
		
		return "user_access";
	}

	//게시글 작성
	@GetMapping("/write")
	public String userAccess(Model model, Authentication authentication) {
		
		return "write";
	}
	
	//게시글 DB 저장
	@PostMapping("/post")
	public String board_db(@RequestParam("file") MultipartFile files, BoardDTO boardDto, Authentication authentication) {
		
		MyUserDetail userDetail = (MyUserDetail)authentication.getPrincipal();
		
		try {
            String origFilename = files.getOriginalFilename();
            boardDto.setFilename(origFilename);
            
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOrig_filename(origFilename);
            fileDto.setFile_name(filename);
            fileDto.setFile_path(filePath);
            fileDto.setUpload_user(userDetail.getUsername());
            fileDto.setSize(String.valueOf(files.getSize()));

            Long fileid = fileService.saveFile(fileDto);
            boardDto.setFileid(fileid);
            service.savePost(boardDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
		return "redirect:/user_access";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		BoardDTO boardDto = service.getPost(id);
        model.addAttribute("post", boardDto);
        System.out.println(boardDto);
		return "detail";
	}
	
	@GetMapping("/download/{fileid}")
	public ResponseEntity<Resource> fileDownload(@PathVariable("fileid") Long fileid) throws IOException {
	    FileDto fileDto = fileService.getFile(fileid);
	    Path path = Paths.get(fileDto.getFile_path());
	    InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));
	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getOrig_filename() + "\"")
	            .body(resource);
	}
	
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDto = service.getPost(id);
        model.addAttribute("post", boardDto);
        return "edit";
    }
	
    @PutMapping("/update/{id}")
    public String update(BoardDTO boardDto) {
        service.savePost(boardDto);
        return "redirect:/user_access";
    }
    
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.deletePost(id);
        return "redirect:/user_access";
    }
	
}
