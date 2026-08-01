package net.labymod.addons.showinginvisible;

import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraft.potion.Effects;

import java.util.List;

public class ShowingInvisibleAddon extends LabyModAddon {

    private static ShowingInvisibleAddon instance;
    private boolean enabled = true;

    @Override
    public void onEnable() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void loadConfig() {
        this.enabled = getConfig().has("enabled") ? getConfig().get("enabled").getAsBoolean() : true;
    }

    @Override
    protected void fillSettings(List<SettingsElement> subSettings) {
        subSettings.add(new BooleanElement(
            "Показывать невидимок", 
            this, 
            new ControlElement.IconData(Material.ENDER_EYE), 
            "enabled", 
            this.enabled
        ));
    }

    public boolean isAddonEnabled() {
        return enabled;
    }

    public static ShowingInvisibleAddon getInstance() {
        return instance;
    }

    @SubscribeEvent
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        if (!enabled) return;
        if (event.getPlayer().isInvisible()) {
            event.getPlayer().setInvisible(false);
        }
    }

    @SubscribeEvent
    public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        if (!enabled) return;
        if (event.getPlayer().isPotionActive(Effects.INVISIBILITY)) {
            event.getPlayer().setInvisible(true);
        }
    }
}
