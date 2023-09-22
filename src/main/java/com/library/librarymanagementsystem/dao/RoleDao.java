package com.library.librarymanagementsystem.dao;

import com.library.librarymanagementsystem.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
