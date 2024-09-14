package capstone.Repository;

import capstone.Entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration,String> {

    boolean existsByUserName(String userName);

    Registration findByCustomerId(String customerId);

    boolean existsByEmailAddress(String emailAddress);

    boolean existsByEmailAddressAndUserNameNot(String emailAddress, String userName);
}
