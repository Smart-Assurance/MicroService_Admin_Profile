package ma.fstt.microserviceprofileadmin.repository;

import ma.fstt.microserviceprofileadmin.entities.Administrator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AdministratorRepository extends MongoRepository<Administrator, String> {

}