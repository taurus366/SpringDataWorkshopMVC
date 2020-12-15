package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.entities.Role;
import softuni.workshop.data.repositories.RoleRepository;
import softuni.workshop.service.models.RoleServiceModel;
import softuni.workshop.service.services.RoleService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        //TODO seed roels in database
//        if(roleRepository.count() == 0){
            Role root = new Role("ROOT");
            Role user = new Role("USER");

            roleRepository.saveAndFlush(root);
            roleRepository.saveAndFlush(user);
       // }


    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        //TODO find all roles



        return roleRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String role) {
        //TODO find role by authority


        return modelMapper.map(roleRepository.findByAuthority(role), RoleServiceModel.class);
    }
}
