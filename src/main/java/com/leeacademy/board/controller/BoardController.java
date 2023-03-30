package com.leeacademy.board.controller;

import com.leeacademy.board.entity.Board;
import com.leeacademy.board.repository.BoardRepository;
import com.leeacademy.board.vo.BoardPaginationVo;
import com.leeacademy.board.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class BoardController {

	private BoardRepository boardRepository;

	public BoardController(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	@RequestMapping("/")
	public String list(Model model,
					   @ModelAttribute BoardVO boardVO,
					   @PageableDefault(page = 0, size = 10)
					   @SortDefault(sort = "no",direction = Sort.Direction.DESC)
					   Pageable pageable
					   ) {
		Page<Board> data = boardRepository.findAll(boardVO.specification(), pageable);
		model.addAttribute("data",data);
//		model.addAttribute("list", boardRepository.findAll(pageable));
//		model.addAttribute("list",boardRepository.findAllByOrderByNoDesc());
		model.addAttribute("pagination",new BoardPaginationVo(data));
		return "list";
	}

	@GetMapping("/write")
	public String write() {
		return "write";
	}

	@PostMapping("/write")
	public String write(@ModelAttribute Board board) {
		Board result = boardRepository.save(board);
		return "redirect:detail?no="+result.getNo();
	}

	@GetMapping("/detail")
	public String detail(@RequestParam long no, Model model) {
		Board board = boardRepository.findById(no).orElseThrow();

		//조회수 증가
		board.setReadcount(board.getReadcount()+1);
		Board result = boardRepository.save(board);

		model.addAttribute("board", result);
		return "detail";
	}

	//비밀번호 로직이 추가되었으므로 단순하게 번호를 받는 것이 아닌 FlashMap을 수신하는 코드로 변경
	@GetMapping("/edit")
	public String edit(HttpServletRequest request, Model model) {
		//FlashMap 수신코드
		Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
		if(map == null) throw new RuntimeException("권한 없음");
		Long no = (Long)map.get("no");

		Board board = boardRepository.findById(no).orElseThrow();
		model.addAttribute("board", board);
		return "edit";
	}



	@PostMapping("/edit")
	public String edit(@ModelAttribute Board board, RedirectAttributes attr) {
		Board origin = boardRepository.findById(board.getNo()).orElseThrow();
		origin.setTitle(board.getTitle());
		origin.setWriter(board.getWriter());
		origin.setContent(board.getContent());
//		비밀번호 추가
		origin.setPassword(board.getPassword());
		Board result = boardRepository.save(origin);
		attr.addAttribute("no", result.getNo());
		return "redirect:detail";
	}

	//비밀번호 로직이 추가되었으므로 단순하게 번호를 받는 것이 아닌 FlashMap을 수신하는 코드로 변경
	@GetMapping("/delete")
	public String delete(HttpServletRequest request){

		//FlashMap 수신코드
		Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
		if(map == null) throw new RuntimeException("권한 없음");
		Long no = (Long)map.get("no");


		boardRepository.deleteById(no);
		return "redirect:/";
	}

	//비밀번호 검새 매핑 추가
	// -/password/모드/번호
	// - 모드는 반드시 edit 또는 delete중 하나가 오도록 정규식 검사 처리
	@GetMapping("/password/{mode:edit|delete}/{no}")
	public String password(@PathVariable String mode, @PathVariable int no, Model model) {
		model.addAttribute("mode",mode);
		model.addAttribute("no",no);
		return "password";
	}

	@PostMapping("/password/{mode:edit|delete}/{no}")
	public String password(@PathVariable String mode, @PathVariable long no, @RequestParam String password,
						   RedirectAttributes attr) {
		//글번호가 없으면 오류 처리

		Board board = boardRepository.findById(no).orElseThrow();

		if(board.getPassword().equals(password)){//비밀번호 일치 - 다음 단계로 이동
			//이동은 하지만 번호를 알 수 가 없으며, 이페이지를 통과했다는 보증이 필요하므로 FlashAttribute를 사용
			//FlashAttribute는 세션을 사용하며 요청 후 소멸하므로 현재 상태에서 유용하게 사용할 수 있는 값
			//-> 하지만 edit와 delete의 로직을 변경해야함
			attr.addFlashAttribute("no",no);
			return "redirect:/"+mode;
		}else {//비밀번호 불일치 - 이전단계로 복귀(+error표식)
			return "redirect:/password/"+mode+"/"+no+"?error";
		}
	}

}
