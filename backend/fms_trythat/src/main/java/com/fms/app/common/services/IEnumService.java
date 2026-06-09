package com.fms.app.common.services;

import java.util.List;

import com.fms.app.common.models.Enums;

public interface IEnumService {

	public List<Enums> getEnums();
	public Enums getEnums(int id);
	public void saveEnums(Enums enums);
	public void delete(int id);
	public void delete(Enums enums);
}
