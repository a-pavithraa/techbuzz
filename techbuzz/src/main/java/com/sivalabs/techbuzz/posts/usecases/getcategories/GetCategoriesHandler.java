package com.sivalabs.techbuzz.posts.usecases.getcategories;

import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetCategoriesHandler {
    private static final Logger log = LoggerFactory.getLogger(GetCategoriesHandler.class);

    private final CategoryRepository categoryRepository;

    public GetCategoriesHandler(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Cacheable("categories")
    public List<Category> getAllCategories() {
        log.debug("Fetching all categories");
        return categoryRepository.findAll();
    }

    @Cacheable("category")
    public Category getCategory(String categorySlug) {
        log.debug("Fetching category by slug: {}", categorySlug);
        return categoryRepository.findBySlug(categorySlug).orElseThrow();
    }
}
