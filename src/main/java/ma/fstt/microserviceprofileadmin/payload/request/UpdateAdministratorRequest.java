package ma.fstt.microserviceprofileadmin.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class UpdateAdministratorRequest {
    @NotBlank
    private String l_name;
    @NotBlank
    private String f_name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
}