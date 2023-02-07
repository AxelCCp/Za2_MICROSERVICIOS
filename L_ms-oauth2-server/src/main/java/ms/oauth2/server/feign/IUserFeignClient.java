package ms.oauth2.server.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import ms.commons.users.server.models.entity.User;

@FeignClient(name="ms-users-server")
public interface IUserFeignClient {
	
	
	@GetMapping(path="/userdao/search/search-username")
	public User findByUsername(@RequestParam("username")String username);
	
	@PutMapping("/userdao/{id}")
	public User update(@RequestBody User user, @PathVariable Long id);
	
}
