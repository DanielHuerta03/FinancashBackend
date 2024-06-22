package pe.edu.upc.catchthem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.catchthem.entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
