package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.workshop.data.entities.Project;

import java.math.BigDecimal;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
    //TODO
    @Query("SELECT p FROM Project p WHERE p.name = :name and p.description = :description and p.startDate = :startDate and p.isFinished = :finished and p.payment = :payment and p.company.name = :company_name")
    Project findByNameAndDescriptionAndStartDateAndFinishedIsAndPaymentAndCompanyName(String name, String description, String startDate, boolean finished, BigDecimal payment, String company_name);
}
