package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.ProjectDto;
import softuni.workshop.data.dtos.ProjectRootDto;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.XmlParser;

import org.springframework.transaction.annotation.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final static String PROJECT_PATH = "src/main/resources/files/xmls/projects.xml";

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final XmlParser xmlParser;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final CompanyRepository companyRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, XmlParser xmlParser, ModelMapper modelMapper, CompanyRepository companyRepository) {
        this.projectRepository = projectRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
    }

    @Override
    public void importProjects() throws JAXBException {
        //TODO seed in database

        ProjectRootDto projectRootDto = xmlParser.parseXml(ProjectRootDto.class, PROJECT_PATH);

        for (ProjectDto projectDto : projectRootDto.getProjectDtoList()) {
            Company company = companyRepository.findByName(projectDto.getCompanyDtoProject().getName());

            if (company != null){
                Project project = modelMapper.map(projectDto, Project.class);
                project.setCompany(company);

                projectRepository.saveAndFlush(project);
            }

        }
    }

    @Override
    public boolean areImported() {
        //TODO check if repository has any records
       return projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        //TODO read xml file
      return String.join("\n", Files.readAllLines(Path.of(PROJECT_PATH)));
    }

    @Override
    public String exportFinishedProjects(){
        //TODO export finished projects
        StringBuilder sb = new StringBuilder();
        for (Project project : projectRepository.findAllByFinishedIsTrue()) {

            sb.append(String.format("Project Name: %s", project.getName()))
                    .append(System.lineSeparator());
            sb.append(String.format("\tDescription: %s", project.getDescription()))
                    .append(System.lineSeparator());
            sb.append(String.format("\t%.2f", project.getPayment()))
                    .append(System.lineSeparator());
        }


        return sb.toString();
    }
}
