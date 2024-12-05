package com.shinhan.myapp.board;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.shinhan.myapp.vo.MemberDTO;

import lombok.extern.slf4j.Slf4j;
import net.firstzone.util.UploadFileUtils;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	
		@Autowired
		BoardService boardService;
		
		@GetMapping("/list.do")
		public String list(Model model) {
			model.addAttribute("boardlist", boardService.findAll());
			return "board/boardList";
		}
		
		@GetMapping("/insert.do")
		public String insertGet() {
			return "board/boardInsert";
		}
		
		@PostMapping("/insert.do")
		public String insertPost(MultipartHttpServletRequest multipart, HttpSession session) {
			MemberDTO member =  (MemberDTO)session.getAttribute("loginMember");
			if(member == null) member = MemberDTO.builder().member_id("guest").build();
			String writer = member.getMember_id();
			
			HttpServletRequest request = (HttpServletRequest)multipart;
			
			String uploadPath = session.getServletContext().getRealPath("./resources/upload");
			MultipartFile martipartFile = multipart.getFile("pic");
			String fileName = martipartFile.getOriginalFilename();	//이미지이름
			String newfileName = "";
			String ymdpath = UploadFileUtils.calcPath(uploadPath);
			try {
				newfileName = UploadFileUtils.fileUpload(uploadPath, fileName, martipartFile.getBytes(), ymdpath);
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:insert.do";
			}
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			BoardDTO board = BoardDTO.builder().title(title).content(content).build();
			board.setWriter(writer);
			board.setPic(ymdpath + File.separator + newfileName);
			log.info(board.toString());
			boardService.insert(board);
			return "redirect:list.do";
		}
		
		@PostMapping("/update.do")
		public String updatePost(BoardDTO board) {
			boardService.update(board);
			return "redirect:list.do";
		}
		
		@GetMapping("/detail.do")
		public String detailGet(Long bno , Model model) {
			model.addAttribute("board",boardService.findById(bno));
			return "board/boardDetail";
		}
		
		@GetMapping("/delete.do")
		public String updateGet(Long bno) {
			boardService.delete(bno);
			return "redirect:list.do";
		}
}
