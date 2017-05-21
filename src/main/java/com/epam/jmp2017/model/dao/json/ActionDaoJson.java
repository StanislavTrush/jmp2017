package com.epam.jmp2017.model.dao.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.model.dao.IActionDao;
import com.epam.jmp2017.model.json.ActionModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Repository;

@Repository
public class ActionDaoJson implements IActionDao {
    @Override
    public List<ActionModel> getAllActions() {
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        List<ActionModel> actions = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        URL actionsUrl = classLoader.getResource(BaseConstants.FILE_ACTIONS);
        if (actionsUrl != null) {
            File file = new File(actionsUrl.getFile());
            try {
                JsonArray jsonActions = (JsonArray) parser.parse(new FileReader(file));
                ActionModel action;

                for (JsonElement jsonElement : jsonActions) {
                    if (jsonElement.isJsonObject()) {
                        action = gson.fromJson(jsonElement.getAsJsonObject(), ActionModel.class);
                        if (action != null) {
                            actions.add(action);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return decorateActions(actions);
    }
}
