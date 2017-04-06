package com.epam.jmp2017.model.dao.json;

import com.epam.jmp2017.model.dao.IDataDto;
import com.epam.jmp2017.model.json.DataModel;

import java.util.List;


public class DataDtoJson implements IDataDto
{
	@Override
	public boolean save(List<DataModel> dataList)
	{
		return false;
	}
}
