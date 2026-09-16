package com.example.eventmanagement.repository;

import com.example.eventmanagement.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}