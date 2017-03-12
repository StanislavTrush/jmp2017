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
	private static Logger LOG = null;

	public LoggingActionDecorator(ActionModel decorated)
	{
		super(decorated);
		if (LOG == null) {
			synchronized (LoggingActionDecorator.class) {
				if (LOG == null) {
					FileHandler fh;
					SimpleFormatter formatter = new SimpleFormatter();
					LOG = Logger.getLogger(LoggingActionDecorator.class.getName());
					try {
						fh = new FileHandler("C://jmp.log", true);
						LOG.addHandler(fh);
						fh.setFormatter(formatter);
					} catch (SecurityException | IOException e) {
						LOG.log(Level.SEVERE, e.getMessage(), e);
					}
				}
			}
		}
	}

	@Override
	public String perform(DataModel data)
	{
		final String result = super.perform(data);
		if (result != null)
		{
			LOG.info(data.print());
		}
		return result;
	}
}
