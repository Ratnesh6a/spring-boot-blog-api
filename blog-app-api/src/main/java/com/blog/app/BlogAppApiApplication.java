package com.blog.app;

import com.blog.app.entity.Role;
import com.blog.app.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {
//implements CommandLineRunner
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}
	@Bean
    public ModelMapper modelMapper() {
	return new ModelMapper();
   }

   @Autowired
   private PasswordEncoder passwordEncoder ;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("Sync@4126"));

			try {
				Role role1 = new Role(1,"ROLE_ADMIN");


				Role role2 = new Role(2,"ROLE_NORMAL");

				Set<Role> roles = Set.of(role1, role2);
				List<Role> roleList = this.roleRepository.saveAll(roles);

				// to see which roles it saved
				roleList.forEach(r->{
					System.out.println(r.getName());
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
