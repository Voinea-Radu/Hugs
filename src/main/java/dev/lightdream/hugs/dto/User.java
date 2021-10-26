package dev.lightdream.hugs.dto;

import dev.lightdream.api.IAPI;
import dev.lightdream.hugs.Main;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.UUID;

@NoArgsConstructor
@DatabaseTable(tableName = "users")
public class User extends dev.lightdream.api.databases.User {

    @DatabaseField(columnName = "hugs")
    public int hugs;
    @DatabaseField(columnName = "last_hug")
    public Long lastHug;

    public User(IAPI api, UUID uuid, String name, String lang) {
        super(api, uuid, name, lang);
        this.hugs = 0;
    }

    @SuppressWarnings("ConstantConditions")
    public void hug() {
        hugs++;
        save();
        if (isOnline()) {
            sendMessage(Main.instance, Main.instance.lang.hugged);
            for (int i = 0; i < 10; i++) {
                if (!isOnline()) {
                    continue;
                }
                Location location = getPlayer().getLocation();

                Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                    ParticleEffect.HEART.display(location);
                }, i * 10L);
            }
        }
    }

    public void hug(User user) {
        user.hug();
        this.lastHug = System.currentTimeMillis();
        save();
    }

    public boolean canHug(){
        return lastHug+Main.instance.config.hugDelay < System.currentTimeMillis();
    }

    @Override
    public void save() {
        save(false);
    }

    @Override
    public String toString() {
        return "User{" +
                "hugs=" + hugs +
                ", lastHug=" + lastHug +
                ", id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", lang='" + lang + '\'' +
                '}';
    }
}
