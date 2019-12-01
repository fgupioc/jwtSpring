package com.fgupioc.jwtAuth.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fgupioc.jwtAuth.models.dao.IUsuarioDao;
import com.fgupioc.jwtAuth.models.entity.Usuario;

// para implementare el proceso de login.
@Service
public class UsuarioService implements UserDetailsService, IUsuarioService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// se inyecta el repositorio 
	@Autowired
	private IUsuarioDao usuarioDao;

	// 
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// se obtiene el usuario 
		Usuario usuario = usuarioDao.findByUsername(username);
		
		//
		if (usuario == null) {
			logger.info("Error el usuario no existe");
			throw new UsernameNotFoundException("Usuario no existe");
		}

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info(authority.getAuthority()))
				.collect(Collectors.toList());

		// se crea la implementacion concreta del objeto userdatails
		return new User(username, usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {		
		return usuarioDao.findByUsername(username);
	}

}
