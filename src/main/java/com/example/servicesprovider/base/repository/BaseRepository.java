package com.example.servicesprovider.base.repository;

import com.example.servicesprovider.base.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface BaseRepository<E extends BaseModel<ID>, ID extends Serializable> extends JpaRepository<E, ID> {
}