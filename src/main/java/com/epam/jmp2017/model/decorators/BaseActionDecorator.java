package com.epam.jmp2017.model.decorators;

import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;

import javax.enterprise.inject.Model;

public class BaseActionDecorator extends ActionModel
{
	private ActionModel decorated;

	public BaseActionDecorator(ActionModel decorated) {
		this.decorated = decorated;
	}

	@Override
	public boolean check(DataModel data)
	{
		return decorated.check(data);
	}

	@Override
	public String perform(DataModel data)
	{
		return decorated.perform(data);
	}
}
