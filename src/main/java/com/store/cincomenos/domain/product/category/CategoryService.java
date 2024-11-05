package com.store.cincomenos.domain.product.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.product.category.DataListCategories;
import com.store.cincomenos.domain.dto.product.category.DataRegisterCategory;
import com.store.cincomenos.domain.dto.product.category.DataResponseCategory;
import com.store.cincomenos.domain.dto.product.category.DataUpdateCategory;
import com.store.cincomenos.infra.exception.responsive.NullPointerException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public DataResponseCategory register(DataRegisterCategory data) {
        Category category = categoryRepository.save(new Category(data));
        return new DataResponseCategory(category);
    }

    public Page<DataListCategories> getList(Long id, String name, Pageable pagination) {
        Page<DataListCategories> list = categoryRepository.findByParameters(id, name, pagination).map(DataListCategories::new);
        return list;
    }

    public DataResponseCategory update(DataUpdateCategory data) {
        Category category = categoryRepository.getReferenceById(data.id());
        category.updateData(data);
        return new DataResponseCategory(category);
    }

    public void logicalDelete(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new NullPointerException(HttpStatus.BAD_REQUEST, "Could not get the desired category or not exists"));
        categoryRepository.delete(category);
    }

}
