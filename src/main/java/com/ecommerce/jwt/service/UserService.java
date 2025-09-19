package com.ecommerce.jwt.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.jwt.entity.Role;
import com.ecommerce.jwt.entity.User;
import com.ecommerce.jwt.repository.RoleRepository;
import com.ecommerce.jwt.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registeredNewUser(User user) {
		return userRepository.save(user);
	}
	
	public User registerNewUser(User user) {	
		//Role role= roleRepository.findById("User").get();
		Role role = roleRepository.findByRoleName("User")
			    .orElseThrow(() -> new RuntimeException("Role not found: User"));
		Set<Role> roles= new HashSet<>();
		roles.add(role);
		user.setRole(roles);	
		user.setPassword(getEncodedPassword(user.getPassword()));
		
		return userRepository.save(user);
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
	//public void initRolesAndUser() {
		
//		Role adminRole= new Role();
//		adminRole.setRoleName("Admin");
//		adminRole.setRoleDescription("Admin role");
//		roleRepository.save(adminRole);
//		
//		Role userRole= new Role();
//		userRole.setRoleName("User");
//		userRole.setRoleDescription("User Role");
//		roleRepository.save(userRole);
		
		public void initRolesAndUser() {
		    roleRepository.findByRoleName("Admin").orElseGet(() -> {
		        Role adminRole = new Role();
		        adminRole.setRoleName("Admin");
		        adminRole.setRoleDescription("Admin role");
		        return roleRepository.save(adminRole);
		    });

		    roleRepository.findByRoleName("User").orElseGet(() -> {
		        Role userRole = new Role();
		        userRole.setRoleName("User");
		        userRole.setRoleDescription("User role");
		        return roleRepository.save(userRole);
		    });

		
//		User adminUser= new User();
//		adminUser.setUserName("admin123");
//		adminUser.setFirstName("admin");
//		adminUser.setLastName("admin");
//		adminUser.setPassword(getEncodedPassword("admin@pass"));		
//		Set<Role> adminRoles = new HashSet<>();
//		adminRoles.add(adminRole);
//		adminUser.setRole(adminRoles);
//		userRepository.save(adminUser);
		
//		User user= new User();
//		user.setUserName("devashree17");
//		user.setFirstName("devashree");
//		user.setLastName("lonkar");
//		user.setPassword(getEncodedPassword("devashree@pass"));
//		Set<Role> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		user.setRole(userRoles);
//		userRepository.save(user);
	}
	
	
}
