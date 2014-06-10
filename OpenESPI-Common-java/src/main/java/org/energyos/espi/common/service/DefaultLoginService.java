package org.energyos.espi.common.service;

import org.energyos.espi.common.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultLoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			return userRepository.findByUsername(username);
		} catch (EmptyResultDataAccessException x) {
			throw new UsernameNotFoundException("Unable to find user");
		}
	}
}
