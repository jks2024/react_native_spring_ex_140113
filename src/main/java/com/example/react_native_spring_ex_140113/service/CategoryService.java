package com.example.react_native_spring_ex_140113.service;

import com.example.react_native_spring_ex_140113.dto.CategoryDto;
import com.example.react_native_spring_ex_140113.entity.Category;
import com.example.react_native_spring_ex_140113.entity.Member;
import com.example.react_native_spring_ex_140113.repository.CategoryRepository;
import com.example.react_native_spring_ex_140113.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    // 카테고리 등록
    public boolean saveCategory(CategoryDto categoryDto) {
        try {
            Category category = new Category();
            Member member = memberRepository.findByEmail(categoryDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            category.setCategoryName(categoryDto.getCategoryName());
            category.setMember(member);
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during saveCategory: {}", e.getMessage(), e);
            return false;
        }
    }
    // 카테고리 수정
    public boolean modifyCategory(Long id, CategoryDto categoryDto) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );
            Member member = memberRepository.findByEmail(categoryDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            category.setCategoryName(categoryDto.getCategoryName());
            category.setCategoryId(categoryDto.getCategoryId());
            category.setMember(member);
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during modifyCategory: {}", e.getMessage(), e);
            return false;
        }
    }
    // 카테고리 삭제
    public boolean deleteCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );
            categoryRepository.delete(category);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during deleteCategory: {}", e.getMessage(), e);
            return false;
        }
    }
    // 카테고리 목록 조회
    public List<CategoryDto> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for(Category category : categories) {
            categoryDtos.add(convertEntityToDto(category));
        }
        return categoryDtos;
    }

    // 카테고리 엔티티를 카테고리 DTO로 변환
    private CategoryDto convertEntityToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setEmail(category.getMember().getEmail());
        return categoryDto;
    }
}
