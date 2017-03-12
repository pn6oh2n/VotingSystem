package com.example;

import com.example.dao.UserRepository;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.example.model.Role.ROLE_ADMIN;

@Component
public class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

	private final UserRepository userRepository;

	@Autowired
	public MyApplicationListener(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		if (userRepository.findByName("admin") == null){
			User admin = new User();
			admin.setName("admin");
			admin.setPassword("admin");
			admin.setRole(ROLE_ADMIN);
			userRepository.save(admin);
		}
	}
}
