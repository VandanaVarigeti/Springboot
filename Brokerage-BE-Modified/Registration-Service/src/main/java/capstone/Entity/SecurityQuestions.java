package capstone.Entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class SecurityQuestions {
    private  String question;
    private String answer;
}
