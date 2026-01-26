package com.exam.service;

import com.exam.dto.ReturnDTO;
import com.exam.entities.Return;

import java.util.List;

public interface IReturnService {
    List<ReturnDTO> getAll();
    ReturnDTO getById(Long id);
    ReturnDTO save(Return returnEntity);
    void delete(Long id);
}