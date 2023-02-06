package ms.oauth2.server.service;

import ms.commons.users.server.models.entity.User;

public interface IUserService {
	
	
	public User findByUsername(String username);
	

}
