package com.company.schedule.management.system.model.formatter;

import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.service.GroupService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class GroupFormatter implements Formatter<Group> {

    private final GroupService groupService;

    public GroupFormatter(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public Group parse(String text, Locale locale) throws ParseException {
        return groupService.getGroupById(Long.valueOf(text));
    }

    @Override
    public String print(Group group, Locale locale) {
        return group.toString();
    }
}
