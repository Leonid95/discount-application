package project.discountapplication.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import project.discountapplication.dao.RoleDao;
import project.discountapplication.entity.Category;
import project.discountapplication.entity.Role;


@Component
public class StringToRoleConverter implements Converter<String, Role> {

	@Autowired
	RoleDao roleDao;

	@Override
	public Role convert(String roleId) {
		
		long rId = Long.parseLong(roleId);

		Role role = roleDao.findRoleById(rId);

		return role;
	}

}
