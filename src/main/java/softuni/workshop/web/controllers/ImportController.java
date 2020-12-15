package softuni.workshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final CompanyService companyService;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public ImportController(EmployeeService employeeService, ProjectService projectService, CompanyService companyService, XmlParser xmlParser, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.companyService = companyService;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/xml")
    public ModelAndView xml(){

        ModelAndView view = super.view("xml/import-xml");

        boolean[] areImported = {companyService.areImported(),projectService.areImported(),employeeService.areImported()};
        view.addObject("areImported",areImported);
        return view;
    }

    @GetMapping("/companies")
    public ModelAndView companies() throws IOException {
        ModelAndView modelAndView = super.view("xml/import-companies");
        modelAndView.addObject("companies",companyService.readCompaniesXmlFile());
        return modelAndView;
    }

    @PostMapping("/companies")
    public ModelAndView companiesConfirm() throws JAXBException {
        companyService.importCompanies();
       return super.redirect("/import/xml");
    }
}
