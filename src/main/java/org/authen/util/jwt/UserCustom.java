//package org.authen.util.jwt;
//
//import lombok.Builder;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//@Builder
//public class UserCustom extends User {
//
//	public UserCustom(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//		super(username, password, authorities);
//	}
//
//	public UserCustom(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//	}
//}
