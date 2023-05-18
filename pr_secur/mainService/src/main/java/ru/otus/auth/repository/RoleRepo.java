package ru.otus.auth.repository;

import ru.otus.auth.entity.Role;

import java.util.List;

public interface RoleRepo {
    Role getRoleByName(String name);

    Role getRoleById(Long id);

    List<Role> allRoles();

    Role getDefaultRole();
}