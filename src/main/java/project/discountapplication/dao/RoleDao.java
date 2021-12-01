package project.discountapplication.dao;

import java.util.Collection;

import project.discountapplication.entity.Category;
import project.discountapplication.entity.Role;

public interface RoleDao {
	Role findRoleById(Long roleId);
	
	Collection <Role> getAllRoles();
}
