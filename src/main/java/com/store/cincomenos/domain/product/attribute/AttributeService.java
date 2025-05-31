package com.store.cincomenos.domain.product.attribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.product.attribute.DataListAttributes;
import com.store.cincomenos.domain.dto.product.attribute.DataRegisterAttribute;
import com.store.cincomenos.domain.dto.product.attribute.DataResponseAttribute;
import com.store.cincomenos.domain.dto.product.attribute.DataUpdateAttribute;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;

@Service
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    public DataResponseAttribute register(DataRegisterAttribute data) {
        Attribute attribute = attributeRepository.save(new Attribute(data));
        return new DataResponseAttribute(attribute);
    }

    public Page<DataListAttributes> getList(Long id, String name, Pageable pagination) {
        Page<DataListAttributes> list = attributeRepository.findByParameters(id, name, pagination).map(DataListAttributes::new);
        return list;
    }

    public DataResponseAttribute update(DataUpdateAttribute data) {
        Attribute attribute = attributeRepository.findById(data.id())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired attribute or not exists"));
        attribute.updateData(data);
        return new DataResponseAttribute(attribute);
    }
        
    public void logicalDelete(Long id) {
        Attribute attribute = attributeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired attribute or not exists"));
        attributeRepository.delete(attribute);
    }

}
