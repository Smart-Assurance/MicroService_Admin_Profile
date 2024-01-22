package ma.fstt.microserviceprofileadmin.controllers;



import ma.fstt.microserviceprofileadmin.entities.Administrator;
import ma.fstt.microserviceprofileadmin.payload.request.UpdateAdministratorRequest;
import ma.fstt.microserviceprofileadmin.payload.response.MessageResponse;
import ma.fstt.microserviceprofileadmin.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/profiles")

public class ProfileController {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    public AdministratorRepository administratorRepository;

    private final AuthService authService;

    public ProfileController(AdministratorRepository administratorRepository,AuthService authService) {
        this.administratorRepository = administratorRepository;
        this.authService = authService;
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
    //update admin
    @PutMapping("/admin")
    public ResponseEntity<MessageResponse> updateInfoAministrator(
            @PathVariable String adminId,
            @RequestBody UpdateAdministratorRequest updateAdministratorRequest,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            // Extract the token from the Authorization header
            String token = extractTokenFromHeader(authorizationHeader);
            if (!authService.isValidAdministratorToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(401, "Not authorized"));
            }

            Optional<Administrator> optionalAdministrator = administratorRepository.findById(adminId);
            if (optionalAdministrator.isPresent()) {
                Administrator administrator = optionalAdministrator.get();

                // Mettre à jour
                administrator.setL_name(updateAdministratorRequest.getL_name());
                administrator.setF_name(updateAdministratorRequest.getF_name());
                administrator.setUsername(updateAdministratorRequest.getUsername());
                administrator.setPassword(encoder.encode(updateAdministratorRequest.getPassword()));
                administrator.setEmail(updateAdministratorRequest.getEmail());
                administrator.setPhone(updateAdministratorRequest.getPhone());
                administrator.setCity(updateAdministratorRequest.getCity());
                administrator.setAddress(updateAdministratorRequest.getAddress());

                administratorRepository.save(administrator);
                return ResponseEntity.ok(new MessageResponse(200, "Administrator updated successfully"));
            } else {
                return ResponseEntity.status(404).body(new MessageResponse(404, "Administrator not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse(500, "Internal server error"));
        }
    }

}