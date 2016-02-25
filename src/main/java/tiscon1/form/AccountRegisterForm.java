package tiscon1.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author kawasima
 */
@Data
public class AccountRegisterForm implements Serializable {
    @Size(min = 1, max = 100)
    @NotBlank
    private String name;


    @NotBlank
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[!\\x22\\#$%&@'()*+,-./_])[\\w!\\x22\\#$%&@'()*+,-./]{8,}$")
    @Size(min = 6)
    @NotBlank
    private String password;
}
