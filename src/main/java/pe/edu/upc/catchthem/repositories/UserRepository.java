package pe.edu.upc.catchthem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.catchthem.entities.Schedule;
import pe.edu.upc.catchthem.entities.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query(value="SELECT * FROM Users u WHERE u.Email = :email AND u.Password = :password", nativeQuery = true)
    Users findUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);  // Add this method
}