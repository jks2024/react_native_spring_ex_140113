package com.example.react_native_spring_ex_140113.service;

import com.example.react_native_spring_ex_140113.dto.BoardDto;
import com.example.react_native_spring_ex_140113.entity.Board;
import com.example.react_native_spring_ex_140113.entity.Category;
import com.example.react_native_spring_ex_140113.entity.Location;
import com.example.react_native_spring_ex_140113.entity.Member;
import com.example.react_native_spring_ex_140113.repository.BoardRepository;
import com.example.react_native_spring_ex_140113.repository.CategoryRepository;
import com.example.react_native_spring_ex_140113.repository.LocationRepository;
import com.example.react_native_spring_ex_140113.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    // 게시글 등록
    @Transactional
    public boolean saveBoard(BoardDto boardDto) {
        try {
            Board board = createBoardFromDto(boardDto);
            Board savedBoard = boardRepository.save(board);
            Location location = createLocationFromDto(boardDto, savedBoard);
            locationRepository.save(location);
            return true;
        } catch (Exception e) {
            log.error("Error occurred during saveBoard: {}", e.getMessage(), e);
            return false;
        }
    }

    // 게시글 전체 조회
    public List<BoardDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boards) {
            boardDtos.add(convertEntityToDto(board));
        }
        return boardDtos;
    }
    // 게시글 페이징
    public List<BoardDto> getBoardList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Board> boards = boardRepository.findAll(pageable).getContent();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boards) {
            boardDtos.add(convertEntityToDto(board));
        }
        return boardDtos;
    }
    // 게시글 상세 조회
    public BoardDto getBoardDetail(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
        return convertEntityToDto(board);
    }
    // 게시글 수정
    public boolean modifyBoard(Long id, BoardDto boardDto) {
        try {
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
            );
            Member member = memberRepository.findByEmail(boardDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            Category category = categoryRepository.findById(boardDto.getCategoryId()).orElseThrow(
                    () -> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );
            board.setTitle(boardDto.getTitle());
            board.setCategory(category);
            board.setContent(boardDto.getContent());
            board.setImgPath(boardDto.getImg());
            board.setMember(member);
            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during modifyBoard: {}", e.getMessage(), e);
            return false;
        }
    }
    // 게시글 삭제
    public boolean deleteBoard(Long id) {
        try {
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
            );
            boardRepository.delete(board);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during deleteBoard: {}", e.getMessage(), e);
            return false;
        }
    }
    // 게시글 검색
    public List<BoardDto> searchBoard(String keyword) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boards) {
            boardDtos.add(convertEntityToDto(board));
        }
        return boardDtos;
    }

    // 게시글 엔티티를 DTO로 변환
    private BoardDto convertEntityToDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setTitle(board.getTitle());
        boardDto.setCategoryId(board.getCategory().getCategoryId());
        boardDto.setContent(board.getContent());
        boardDto.setImg(board.getImgPath());
        boardDto.setEmail(board.getMember().getEmail());
        boardDto.setRegDate(board.getRegDate());

        Location location = board.getLocation(); // Board 엔티티에 Location 엔티티에 대한 참조가 필요
        if (location != null) { // Location 정보가 있는 경우에만 설정
            boardDto.setAddress(location.getAddress());
            boardDto.setLatitude(location.getLatitude());
            boardDto.setLongitude(location.getLongitude());
        }
        return boardDto;
    }

    private Board createBoardFromDto(BoardDto boardDto) {
        Member member = memberRepository.findByEmail(boardDto.getEmail()).orElseThrow(
                () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
        );

        Category category = categoryRepository.findById(boardDto.getCategoryId()).orElseThrow(
                () -> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
        );

        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setImgPath(boardDto.getImg());
        board.setMember(member);
        board.setCategory(category);
        return board;
    }

    private Location createLocationFromDto(BoardDto boardDto, Board board) {
        Location location = new Location();
        location.setBoard(board);
        location.setAddress(boardDto.getAddress());
        location.setLatitude(boardDto.getLatitude());
        location.setLongitude(boardDto.getLongitude());
        return location;
    }

}
