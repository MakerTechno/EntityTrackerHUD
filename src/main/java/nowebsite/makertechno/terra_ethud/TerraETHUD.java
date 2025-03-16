package nowebsite.makertechno.terra_ethud;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import nowebsite.makertechno.terra_ethud.config.TConfig;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TerraETHUD.MOD_ID)
public class TerraETHUD {
    public static final String MOD_ID = "terra_ethud";
    public static final Logger LOGGER = LoggerFactory.getLogger("TerraETHUD");

    public TerraETHUD(IEventBus modEventBus, @NotNull ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, TConfig.SPEC);
    }
}
