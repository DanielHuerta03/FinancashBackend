package pe.edu.upc.catchthem.serviceInterfaces;

import pe.edu.upc.catchthem.entities.Loan;

import java.util.List;

public interface LoanService {
    public void insert(Loan loan);
    Loan findById(Long id);
    List<Loan> findAll();
    List<Loan> findLoansWithoutSchedules();
    void update(Loan loan);

    List<Loan> findLoansByClientId(Long clientId);

}
