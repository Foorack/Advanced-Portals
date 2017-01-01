package com.sekwah.advancedportals.api.registry;

import com.sekwah.advancedportals.api.commands.SubCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sekwah41
 */
public class SubCommandRegistry {

    protected Map<String, SubCommand> subCommands = new HashMap();

    /**
     * @param subCommand
     * @return if the subcommand is registered or not
     */
    public static boolean registerSubCommand(SubCommand subCommand){
        return false;
    }
}
