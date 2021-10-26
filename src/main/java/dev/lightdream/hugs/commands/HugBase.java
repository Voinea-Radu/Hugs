package dev.lightdream.hugs.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.hugs.Main;
import dev.lightdream.hugs.dto.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HugBase extends SubCommand {
    public HugBase(@NotNull IAPI api) {
        super(api, "", true, false, "[player]");
    }

    @Override
    public void execute(dev.lightdream.api.databases.User u, List<String> args) {
        if (args.size() != 1) {
            sendUsage(u);
            return;
        }

        User user = (User) u;

        if(!user.canHug()){
            user.sendMessage(api, Main.instance.lang.hugDelay);
            return;
        }

        User target = Main.instance.databaseManager.getUser(args.get(0));

        if (target == null) {
            user.sendMessage(api, Main.instance.lang.invalidUser);
            return;
        }

        user.hug(target);
    }

    @Override
    public List<String> onTabComplete(dev.lightdream.api.databases.User user, List<String> list) {
        return null;
    }
}
