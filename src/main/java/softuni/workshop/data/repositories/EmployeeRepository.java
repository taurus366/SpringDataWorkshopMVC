package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.workshop.data.entities.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    //TODO
    @Query("SELECT e FROM Employee e WHERE e.age > 25")
    List<Employee> findAllByAgeAfter25();
}
