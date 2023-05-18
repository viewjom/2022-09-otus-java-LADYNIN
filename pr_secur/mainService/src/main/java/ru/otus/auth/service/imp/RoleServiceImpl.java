package ru.otus.auth.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.auth.entity.Role;
import ru.otus.auth.repository.RoleRepo;
import ru.otus.auth.service.RoleService;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepo roleRepo;

    @Autowired
    public void setRoleRepo(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepo.getRoleByName(name);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepo.getRoleById(id);
    }

    @Override
    public List<Role> allRoles() {
        return roleRepo.allRoles();
    }

    @Override
    public Role getDefaultRole() {
        return roleRepo.getDefaultRole();
    }
}
