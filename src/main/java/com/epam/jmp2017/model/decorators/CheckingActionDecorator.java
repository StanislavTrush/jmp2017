package com.epam.jmp2017.model.decorators;

import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;

import javax.enterprise.inject.Model;

public class CheckingActionDecorator extends BaseActionDecorator
{
	public CheckingActionDecorator(ActionModel decorated)
	{
		super(decorated);
	}

	@Override
	public String perform(DataModel data)
	{
		String result = super.perform(data);
		if (result == null && !check(data))
		{
			result = "Check was not passed for action with typeCode " + data.getTypeCode();
		}
		return result;
	}
}
