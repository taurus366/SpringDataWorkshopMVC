package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.service.services.impl.ProjectServiceImpl;

@Controller
public class HomeController extends BaseController {
    //TODO

    @Autowired
    private final ProjectService projectService;

    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    private final CompanyService companyService;

    public HomeController(ProjectServiceImpl projectService, EmployeeService employeeService, CompanyService companyService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView modelAndView = super.view("home");

        boolean areImported = companyService.areImported() && projectService.areImported()&& employeeService.areImported();
        modelAndView.addObject("areImported", areImported);
        return modelAndView;
    }


}
