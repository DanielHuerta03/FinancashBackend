package pe.edu.upc.catchthem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", length = 60, nullable = false)
    private String firstName;
    @Column(name = "lastName", length = 60, nullable = false)
    private String lastName;
    @Column(name = "dni", length = 60, nullable = false, unique = true)
    private String dni;
    @Column(name = "phone", length = 60, nullable = false)
    private String phone;
    @Column(name = "address", length = 60, nullable = false)
    private String address;
    @Column(name = "creditLimit", length = 9, nullable = false)
    private int creditLimit;
    @Column(name = "birthDate")
    private String birthDate;
    @OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private Users userId;

    public Cliente() {
    }

    public Cliente(Long id, String firstName, String lastName, String dni, String phone, String address, int creditLimit, String birthDate, Users userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
        this.address = address;
        this.creditLimit = creditLimit;
        this.birthDate = birthDate;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }
}
