package com.kico.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kico.demo.dto.BoardDTO;
import com.kico.demo.entity.Board;
import com.kico.demo.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository repository;

	/*
	 * public BoardService(BoardRepository boardRepository) { 
	 * this.boardRepository = boardRepository; }
	 * 
	 */    
	
	
	@Transactional
    public Long savePost(BoardDTO board) {
        return repository.save(board.toEntity()).getId();
    }
	
    @Transactional
    public List<BoardDTO> getBoardList() {
        List<Board> boardList = repository.findAll();
        List<BoardDTO> boardDtoList = new ArrayList<>();

        for(Board board : boardList) {
            BoardDTO boardDto = BoardDTO.builder()
                    .id(board.getId())
                    .email(board.getEmail())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdate(board.getCreatedate())
                    .fileid(board.getFileid())
                    .filename(board.getFilename())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }
    
    @Transactional
    public BoardDTO getPost(Long id) {
        Board board = repository.findById(id).get();

        BoardDTO boardDto = BoardDTO.builder()
                .id(board.getId())
                .email(board.getEmail())
                .title(board.getTitle())
                .content(board.getContent())
                .createdate(board.getCreatedate())
                .fileid(board.getFileid())
                .filename(board.getFilename())
                .build();
        return boardDto;
    }
    
    @Transactional
    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
