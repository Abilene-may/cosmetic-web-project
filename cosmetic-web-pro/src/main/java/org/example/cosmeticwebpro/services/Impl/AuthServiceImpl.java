package org.example.cosmeticwebpro.services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Role;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.TokenAuthDTO;
import org.example.cosmeticwebpro.models.request.LoginReqDTO;
import org.example.cosmeticwebpro.models.request.SignUpReqDTO;
import org.example.cosmeticwebpro.repositories.RoleRepository;
import org.example.cosmeticwebpro.repositories.UserRepository;
import org.example.cosmeticwebpro.services.AuthService;
import org.example.cosmeticwebpro.services.CartService;
import org.example.cosmeticwebpro.services.CustomUserDetailsService;
import org.example.cosmeticwebpro.utils.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CartService cartService;
    private final RoleRepository roleRepository;

    @Override
    public TokenAuthDTO login(LoginReqDTO loginDto) throws CosmeticException{
        Authentication authentication = authenticate(loginDto.getUsernameOrEmail(),loginDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(),authentication);
        TokenAuthDTO tokenAuthDTO = new TokenAuthDTO();
        tokenAuthDTO.setAccessToken(token);
        tokenAuthDTO.setRefreshToken(refreshToken);
        return tokenAuthDTO;
    }


    @Override
    public TokenAuthDTO signUp(SignUpReqDTO signupReqDto) throws Exception {
        var checkExistUser = userRepository.findUserByUserNameOrEmail(
                signupReqDto.getUsername(), signupReqDto.getEmail(), Constants.ACTIVE
        );
        // case email or username has been registered
        if(checkExistUser.isPresent())
            throw new CosmeticException(
                    ExceptionUtils.USER_SIGNUP_1,
                    ExceptionUtils.messages.get(ExceptionUtils.USER_SIGNUP_1)
            );
        // setup role for user
        var role = roleRepository.findRoleByRoleName(Constants.ROLE_USER);
        // set user in DB
        User user =User.builder().userName(signupReqDto.getUsername())
                .email(signupReqDto.getEmail())
                .firstName(signupReqDto.getFirstName())
                .lastName(signupReqDto.getLastName())
                .password(passwordEncoder.encode(signupReqDto.getPassword()))
                .accountStatus(Constants.ACTIVE)
                .createdDate(LocalDateTime.now())
                .roleId(role.get().getId())
                .build();

        User savedUSer  = userRepository.save(user);
        // create a shopping cart
        cartService.createCart(savedUSer);
        Authentication authentication = authenticate(savedUSer.getUserName(),signupReqDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(),authentication);
        TokenAuthDTO tokenAuthDTO = new TokenAuthDTO();
        tokenAuthDTO.setAccessToken(token);
        tokenAuthDTO.setRefreshToken(refreshToken);

        return tokenAuthDTO;
    }

    @Override
    public TokenAuthDTO loginWithGoogle(OAuth2User principal) {
        // Setup user and cart
        return new TokenAuthDTO();
    }


    @Override
    public TokenAuthDTO refreshToken(String refreshToken) throws CosmeticException{
        TokenAuthDTO tokenAuthDTO = new TokenAuthDTO();
        String userName = jwtTokenProvider.getUsername(refreshToken);
//        Optional<Users> user = userRepository.findByUserNameOrEmail(userName,userName);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        if(jwtTokenProvider.validateToken(refreshToken)  && authentication !=null){
            String jwt  = jwtTokenProvider.generateToken(authentication);
            tokenAuthDTO.setRefreshToken(refreshToken);
            tokenAuthDTO.setAccessToken(jwt);
        }
        return tokenAuthDTO;
    }

    private Authentication authenticate(String username, String password) throws CosmeticException{
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid Username");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
