package ma.fstt.microserviceprofileadmin.controllers;



import ma.fstt.microserviceprofileadmin.entities.Administrator;
import ma.fstt.microserviceprofileadmin.payload.request.UpdateAdministratorRequest;
import ma.fstt.microserviceprofileadmin.payload.response.MessageResponse;
import ma.fstt.microserviceprofileadmin.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    //update admin
    @PutMapping("/admin")
    public ResponseEntity<MessageResponse> updateInfoAministrator(
            @PathVariable String adminId,
            @RequestBody UpdateAdministratorRequest updateAdministratorRequest
    ) {
        try {
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