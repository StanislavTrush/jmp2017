package com.epam.jmp2017.model.decorators;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;


public class LoggingActionDecorator extends BaseActionDecorator
{
	private static final Logger LOG = Logger.getLogger(LoggingActionDecorator.class.getName());

	public LoggingActionDecorator(ActionModel decorated)
	{
		super(decorated);
		FileHandler fh;
		SimpleFormatter formatter = new SimpleFormatter();
		try {
			fh = new FileHandler("jmp.log");
			LOG.addHandler(fh);
			fh.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public String perform(DataModel data)
	{
		final String result = super.perform(data);
		if (result != null)
		{
			LOG.fine(data.print());
		}
		return result;
	}
}
