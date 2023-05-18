package ru.otus.auth.repository.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.auth.entity.Role;
import ru.otus.auth.repository.RoleRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleRepoImpl implements RoleRepo {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = null;

        System.out.println("getRoleByName=" + name);
        try {
            role = getEntityManager()
                    .createQuery("SELECT r FROM t_role r WHERE r.name=:name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Роли с таким именем не существует!");
        }

        System.out.println("getRoleByName2=" + role.getName());
        return role;
    }

    @Override
    public Role getRoleById(Long id) {
        return getEntityManager().find(Role.class, id);
    }

    @Override
    public List<Role> allRoles() {
        return getEntityManager()
                .createQuery("select r.* from Role r", Role.class)
                .getResultList();
    }

    @Override
    public Role getDefaultRole() {
        return getRoleByName("ROLE_USER");
    }
}