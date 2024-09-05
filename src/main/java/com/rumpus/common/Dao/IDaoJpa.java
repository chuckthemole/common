package com.rumpus.common.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rumpus.common.Model.AbstractModel;

public interface IDaoJpa<MODEL extends AbstractModel<MODEL>> extends JpaRepository<MODEL, String> {
}
