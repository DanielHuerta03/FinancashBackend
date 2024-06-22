package pe.edu.upc.catchthem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.catchthem.entities.Cliente;
import pe.edu.upc.catchthem.entities.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query(value = "SELECT * FROM Contract WHERE client_id = :id", nativeQuery = true)
    public Contract GetContractByClientId(@Param("id") long id);

}
