package ms.users.server.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ms.commons.users.server.models.entity.User;


@RepositoryRestResource(path="userdao")
public interface IUserDao extends PagingAndSortingRepository<User,Long> {
	
	
	//localhost:8090/z/users/userdao/search/search-username?username=axelccp	
	
	//select u from User u where u.username=?1
	@RestResource(path="search-username")
	public User findByUsername(@Param("username")String username);
	
	
	
	
}
