package capstone.Repository;

import capstone.Entity.NewCustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<NewCustomerDto,Long> {

    public default boolean customerExists(Long ssn) {
        return existsById(ssn);
    }

    boolean existsByCustomerId(String uniqueId);

  NewCustomerDto findByEmailAddress(String emailAddress);

    NewCustomerDto findByCustomerId(String customerId);

    boolean existsByEmailAddress(String emailAddress);
//    public default Customer findByName(String name) {
//        return findById();
//    }
}
