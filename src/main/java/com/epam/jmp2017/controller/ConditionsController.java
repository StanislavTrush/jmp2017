package com.epam.jmp2017.controller;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.constants.WebConstants;
import com.epam.jmp2017.model.dao.IConditionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@Controller
@RequestMapping(value = WebConstants.URL_CONDITIONS)
public class ConditionsController {
    @Autowired
    private IConditionDao conditionDaoDb;

    private Predicate<String> nonNull = Objects::nonNull;
    private Function<String, String> format = (value) -> {
        if(nonNull.and(String::isEmpty).test(value)) {
            return null;
        }
        return value;
    };

    @RequestMapping(WebConstants.URL_SHOW)
    private ModelAndView show() {
        ModelAndView model = new ModelAndView("/conditions.jsp");
        model.addObject("conditions", conditionDaoDb.getAllConditions());
        return model;
    }

    @RequestMapping(WebConstants.URL_REMOVE)
    private ModelAndView remove(@RequestParam(BaseConstants.ATTR_CONDITION_ID) int conditionId, RedirectAttributes attr) {
        ModelAndView model = new ModelAndView("redirect:show");
        attr.addFlashAttribute("isDeleted", conditionDaoDb.removeConditionById(conditionId));
        return model;
    }

    @RequestMapping(WebConstants.URL_ADD)
    private ModelAndView add(
            @RequestParam(BaseConstants.ATTR_OPERATION) String operation,
            @RequestParam(BaseConstants.ATTR_ATTRIBUTE) String attribute,
            @RequestParam(BaseConstants.ATTR_VALUE) String value,
            @RequestParam(BaseConstants.ATTR_CLASS_NAME) String className,
            @RequestParam(BaseConstants.ATTR_PARENT_ID) int parentId,
            @RequestParam(BaseConstants.ATTR_ACTION_ID) int actionId,
            RedirectAttributes attr) {
        ModelAndView model = new ModelAndView("redirect:show");
        operation = format.apply(operation);
        attribute = format.apply(attribute);
        value = format.apply(value);
        className = format.apply(className);
        if ((nonNull.test(operation) ||
                (
                    nonNull.test(attribute) &&
                    nonNull.test(value) &&
                    nonNull.test(className)
                )
            ) && parentId != 0) {
            attr.addFlashAttribute("isCreated", conditionDaoDb.addCondition(
                    operation,
                    attribute,
                    value,
                    className,
                    parentId,
                    actionId
            ));
        }
        return model;
    }
}
