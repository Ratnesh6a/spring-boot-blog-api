package com.blog.app.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements  UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Please enter the name")
	private String name;
	
	@NotBlank(message="Please enter the emailid")
	@Column(unique= true)
	@Email
	private String email;
	
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$", message = "Password must be at least 8 characters long and contain at least one letter and one number")
	private String password;
	
	private String about;
	 @OneToMany(mappedBy = "users" , cascade=CascadeType.ALL ,  fetch= FetchType.LAZY)
     private List<Post> post = new ArrayList<Post>();

	 @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	 @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "users",referencedColumnName = "id"),
	 inverseJoinColumns = @JoinColumn(name = "role",referencedColumnName = "id"))
	 private  List<Role> roles = new ArrayList<>();


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		authorities.forEach(System.out::println);
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
