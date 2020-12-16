package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.EmployeeDto;
import softuni.workshop.data.dtos.EmployeeRootDto;
import softuni.workshop.data.dtos.ProjectDto;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.entities.Employee;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEE_PATH = "src/main/resources/files/xmls/employees.xml";

    @Autowired
    private final XmlParser xmlParser;
   private final EmployeeRepository employeeRepository;
   @Autowired
   private final ProjectRepository projectRepository;
   @Autowired
   private final ModelMapper modelMapper;
   @Autowired
   private final CompanyRepository companyRepository;

    public EmployeeServiceImpl(XmlParser xmlParser, EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, CompanyRepository companyRepository) {
        this.xmlParser = xmlParser;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
    }

    @Override
    public void importEmployees() throws JAXBException {
        //TODO seed in database

        EmployeeRootDto employeeRootDto = xmlParser.parseXml(EmployeeRootDto.class, EMPLOYEE_PATH);

        for (EmployeeDto employeeDto : employeeRootDto.getEmployeeDtoList()) {
            ProjectDto projectDto = employeeDto.getProjectDto();

            Project project = projectRepository.findByNameAndDescriptionAndStartDateAndFinishedIsAndPaymentAndCompanyName(projectDto.getName(),projectDto.getDescription(),projectDto.getStartDate(),projectDto.isFinished(),projectDto.getPayment(),projectDto.getCompanyDtoProject().getName());
            Company company = companyRepository.findByName(project.getCompany().getName());

            if (project.getId() != 0 && company.getId() != 0){
                Employee employee = modelMapper.map(employeeDto, Employee.class);
                employee.setProject(project);
                employeeRepository.saveAndFlush(employee);
            }

        }

    }

    @Override
    public boolean areImported() {
        //TODO check if repository has any records
       return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        //TODO read xml file
        return String.join("\n", Files.readAllLines(Path.of(EMPLOYEE_PATH)));
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        //TODO export employees with age above 25
        return null;
    }
}
