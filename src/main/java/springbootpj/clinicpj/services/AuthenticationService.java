package springbootpj.clinicpj.services;

import springbootpj.clinicpj.dtos.LoginResponse;
import springbootpj.clinicpj.entities.DoctorExtraInfo;
import springbootpj.clinicpj.entities.Role;
import springbootpj.clinicpj.entities.User;
import springbootpj.clinicpj.dtos.LoginUserDto;
import springbootpj.clinicpj.dtos.RegisterUserDto;
import springbootpj.clinicpj.repositories.DoctorExtraInfoRepository;
import springbootpj.clinicpj.repositories.PasswordResetTokenRepository;
import springbootpj.clinicpj.repositories.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private DoctorExtraInfoRepository doctorExtraInfoRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            DoctorExtraInfoRepository doctorExtraInfoRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.doctorExtraInfoRepository = doctorExtraInfoRepository;
    }

    public User signup(RegisterUserDto input) {
        if (input.getConfirmingPassword().equals(input.getPassword())) {
            User user = new User();
            user.setUserName(input.getUserName());
            user.setEmail(input.getEmail());
            user.setPassword(passwordEncoder.encode(input.getPassword()));
            user.setUserBirthDay(input.getUserBirthDay());
            user.setUserAddress(input.getUserAddress());
            user.setUserPhoneNumber(input.getUserPhoneNumber());
            user.setUserDescription(input.getUserDescription());
            user.setUserGender(input.getUserGender());
            user.setIsActive(input.getIsActive());
            Role role = new Role();
            role.setRoleId(input.getRole().getRoleId());
            role.setRoleName(input.getRole().getRoleName());
            user.setRole(role);
            user.setCreatedAt(LocalDateTime.now());
            if(input.getRole().getRoleId() == 1) {
                DoctorExtraInfo doctorExtraInfo = new DoctorExtraInfo();
                doctorExtraInfo.setGeneralIntro(input.getGeneralIntro());
                doctorExtraInfo.setTrainingProcessing(input.getTrainingProcessing());
                doctorExtraInfo.setAchievement(input.getAchievement());
                doctorExtraInfo.setDepartment(input.getDepartment());
                doctorExtraInfo.setDoctor(user);
                doctorExtraInfoRepository.save(doctorExtraInfo);
            }
            return userRepository.save(user);
        }
        return null;
    }

    public LoginResponse authenticate(LoginUserDto input) {
//        authenticate user from Postman
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        User user = userRepository.getUserByEmail(input.getEmail())
                .orElseThrow();
//        Check locked or unlocked state and generate token
        if (user.getIsActive() == 1) {
            String jwtToken = jwtService.generateToken(user);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            return loginResponse;
        }
        return new LoginResponse("This account is locked!");
    }

}
