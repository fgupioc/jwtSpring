package com.fgupioc.jwtAuth.models.services;

import com.fgupioc.jwtAuth.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
