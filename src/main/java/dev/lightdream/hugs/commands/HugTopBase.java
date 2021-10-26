package dev.lightdream.hugs.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.hugs.Main;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class HugTopBase extends SubCommand {
    public HugTopBase(@NotNull IAPI api) {
        super(api, "", false, false, "");
    }

    @Override
    public void execute(User user, List<String> list) {
        if (Main.instance.scheduleManager.top == null) {
            user.sendMessage(api, Main.instance.lang.topNotCalculate);
            return;
        }

        for (int i = 0; i < Math.min(10, Main.instance.scheduleManager.top.size()); i++) {
            int finalI = i;
            dev.lightdream.hugs.dto.User u = Main.instance.scheduleManager.top.get(finalI);
            user.sendMessage(api, new MessageBuilder(Main.instance.lang.topEntry).addPlaceholders(new HashMap<String, String>() {{
                put("position", String.valueOf(finalI + 1));
                put("player", u.name);
                put("hugs", String.valueOf(u.hugs));
            }}));
        }
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
