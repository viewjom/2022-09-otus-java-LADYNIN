package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.auth.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}