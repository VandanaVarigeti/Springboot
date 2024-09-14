package capstone.Controller;

import capstone.Entity.Registration;
import capstone.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegistrationService registrationService;

    //Registration
    @PostMapping("/newregister")
    public Registration registerUser(@RequestBody Registration registration){
        return registrationService.registerUser(registration);
    }
    @GetMapping("/user/{userName}")
    public Optional<Registration> getUser(@PathVariable String userName) {
        return registrationService.getUser(userName);
    }
    @DeleteMapping("/user/{userName}")
    public void deleteUser(@PathVariable String userName) {
        registrationService.deleteUser(userName);
    }
    @PutMapping("/user/{username}")
    public Registration updateUser(@PathVariable String username,@RequestBody Registration registration){
        return registrationService.updateUser(username,registration);
    }
    @PostMapping("/check-password")
    public boolean checkPassword(@RequestBody Map<String, String> payload) {
        String rawPassword = payload.get("rawPassword");
        String userName = payload.get("userName");
        Optional<Registration> user = registrationService.getUser(userName);
        if (user.isPresent()) {
            return registrationService.checkPassword(rawPassword, user.get().getPassword());
        }
        return false;
    }
    @PutMapping("/update-email")
    public ResponseEntity<Registration> updateEmailAddress(@RequestParam String customerId, @RequestParam String newEmailAddress) {
        registrationService.updateEmailAddress(customerId, newEmailAddress);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/password/{userName}")
    public Registration updatePassword(@PathVariable("userName")String userName,@RequestBody Map<String,String> payload){
        String newPassword = payload.get("newPassword");
        return registrationService.updateUserPassword(userName,newPassword);
    }

}
