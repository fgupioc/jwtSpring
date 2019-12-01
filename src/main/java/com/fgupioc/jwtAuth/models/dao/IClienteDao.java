package com.fgupioc.jwtAuth.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fgupioc.jwtAuth.models.entity.Cliente;
import com.fgupioc.jwtAuth.models.entity.Region;
 

public interface IClienteDao extends JpaRepository<Cliente, Long>{

	@Query("from Region")
	public List<Region> findAllRegiones();
}
