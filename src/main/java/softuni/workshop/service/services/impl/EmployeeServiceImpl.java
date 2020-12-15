package softuni.workshop.service.services.impl;

import org.springframework.stereotype.Service;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.service.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

   private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void importEmployees() {
        //TODO seed in database
    }

    @Override
    public boolean areImported() {
        //TODO check if repository has any records
       return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() {
        //TODO read xml file
        return null;
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        //TODO export employees with age above 25
        return null;
    }
}
