// package com.rumpus.common;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.transaction.annotation.Transactional;

// // @Service("customUserDetailsService")
// public class CommonUserDetailsService implements UserDetailsService {

// // static final Logger logger = LoggerFactory.getLogger(CommonUserDetailsService.class);

// @Autowired
// private com.fortsolution.schedmate.data.services.UserService userService;

// @Transactional(readOnly=true)
// public UserDetails loadUserByUsername(String ssoId)
//         throws UsernameNotFoundException {
//     System.out.println("fine here murtaza");
//     int id = Integer.parseInt(ssoId);
//     User user = userService.findById(id);
//     // logger.info("User : {}", user);
//     if(user==null){
//         // logger.info("User not found");
//         throw new UsernameNotFoundException("Username not found");
//     }
//     return new org.springframework.security.core.userdetails.User(""+user.getId(), user.getPassword(), 
//              true, true, true, true, getGrantedAuthorities(user));
// }


// private List<GrantedAuthority> getGrantedAuthorities(User user){
//     List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

//     for(UserProfile userProfile : user.getUserProfiles()){
//         // logger.info("UserProfile : {}", userProfile);
//         authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType()));

//     }

//     return authorities;
// }
