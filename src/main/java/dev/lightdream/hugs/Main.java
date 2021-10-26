package dev.lightdream.hugs;

import dev.lightdream.api.API;
import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.configs.SQLConfig;
import dev.lightdream.api.managers.CommandManager;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.hugs.commands.HugBase;
import dev.lightdream.hugs.commands.HugTopBase;
import dev.lightdream.hugs.configs.Config;
import dev.lightdream.hugs.configs.Lang;
import dev.lightdream.hugs.dto.User;
import dev.lightdream.hugs.managers.DatabaseManager;
import dev.lightdream.hugs.managers.ScheduleManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;

public final class Main extends LightDreamPlugin {

    public static Main instance;

    //Settings
    public Config config;
    public Lang lang;

    //Managers
    public DatabaseManager databaseManager;
    public ScheduleManager scheduleManager;

    @Override
    public void onEnable() {
        instance = this;

        init("Hugs", "hug", "1.0");

        databaseManager = new DatabaseManager(this);
        scheduleManager = new ScheduleManager(this);

        new CommandManager(this, "hugstop", Arrays.asList(
                new HugTopBase(this)
        ));
    }


    @SuppressWarnings({"SwitchStatementWithTooFewBranches", "ConstantConditions"})
    @Override
    public @NotNull String parsePapi(OfflinePlayer offlinePlayer, String s) {
        User user;
        switch (s) {
            case "lightdreamapi_hugs":
                user = databaseManager.getUser(offlinePlayer.getUniqueId());
                if (user == null) {
                    return "0";
                }
                return String.valueOf(user.hugs);
        }
        return "";
    }

    @Override
    public void loadConfigs() {
        sqlConfig = fileManager.load(SQLConfig.class);
        config = fileManager.load(Config.class);
        baseConfig = config;
        lang = fileManager.load(Lang.class, fileManager.getFile(baseConfig.baseLang));
        baseLang = lang;
    }

    @Override
    public void disable() {

    }

    @Override
    public void registerFileManagerModules() {

    }

    @Override
    public void loadBaseCommands() {
        baseSubCommands.add(new HugBase(this));
    }

    @Override
    public MessageManager instantiateMessageManager() {
        return new MessageManager(this, Main.class);
    }

    @Override
    public void registerLangManager() {
        API.instance.langManager.register(Main.class, getLangs());
    }

    @Override
    public HashMap<String, Object> getLangs() {
        HashMap<String, Object> langs = new HashMap<>();

        baseConfig.langs.forEach(lang -> {
            Lang l = fileManager.load(Lang.class, fileManager.getFile(lang));
            langs.put(lang, l);
        });

        return langs;
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void setLang(Player player, String s) {
        User user = databaseManager.getUser(player);
        user.setLang(s);
        databaseManager.save(user);
    }


}
