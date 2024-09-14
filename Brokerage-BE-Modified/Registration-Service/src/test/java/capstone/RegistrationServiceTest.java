package capstone;

import capstone.Entity.Registration;
import capstone.Repository.RegistrationRepository;
import capstone.Service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldRegisterUser_WhenUserNameDoesNotExist() {
        Registration registration = new Registration();
        registration.setUserName("testUser");
        registration.setPassword("password");
        registration.setConfirmPassword("password");
        registration.setEmailAddress("test@example.com");

        when(registrationRepository.existsByUserName("testUser")).thenReturn(false);
        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration result = registrationService.registerUser(registration);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserNameExists() {
        Registration registration = new Registration();
        registration.setUserName("testUser");

        when(registrationRepository.existsByUserName("testUser")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registrationService.registerUser(registration);
        });

        assertEquals("UserName already exits!", exception.getMessage());
    }

    @Test
    void getUser_ShouldReturnUser_WhenUserExists() {
        Registration registration = new Registration();
        registration.setUserName("testUser");

        when(registrationRepository.findById("testUser")).thenReturn(Optional.of(registration));

        Optional<Registration> result = registrationService.getUser("testUser");

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUserName());
    }

    @Test
    void getUser_ShouldReturnEmpty_WhenUserDoesNotExist() {
        when(registrationRepository.findById("testUser")).thenReturn(Optional.empty());

        Optional<Registration> result = registrationService.getUser("testUser");

        assertFalse(result.isPresent());
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        doNothing().when(registrationRepository).deleteById("testUser");

        registrationService.deleteUser("testUser");

        verify(registrationRepository, times(1)).deleteById("testUser");
    }

//    @Test
//    void checkPassword_ShouldReturnTrue_WhenPasswordMatches() {
//        when(bCryptPasswordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);
//
//        boolean result = registrationService.checkPassword("password", "encodedPassword");
//
//        assertTrue(result);
//    }

    @Test
    void checkPassword_ShouldReturnFalse_WhenPasswordDoesNotMatch() {
        when(bCryptPasswordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(false);

        boolean result = registrationService.checkPassword("password", "encodedPassword");

        assertFalse(result);
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        Registration existingRegistration = new Registration();
        existingRegistration.setUserName("testUser");
        existingRegistration.setPassword("oldPassword");

        Registration updatedRegistration = new Registration();
        updatedRegistration.setPassword("newPassword");

        when(registrationRepository.findById("testUser")).thenReturn(Optional.of(existingRegistration));
        when(bCryptPasswordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(registrationRepository.save(any(Registration.class))).thenReturn(existingRegistration);

        Registration result = registrationService.updateUser("testUser", updatedRegistration);

        assertNotNull(result);
        assertEquals("encodedNewPassword", result.getPassword());
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        Registration updatedRegistration = new Registration();
        updatedRegistration.setPassword("newPassword");

        when(registrationRepository.findById("testUser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registrationService.updateUser("testUser", updatedRegistration);
        });

        assertEquals("User not found!", exception.getMessage());
    }
}

