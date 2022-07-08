package vice.magnesium_extras.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import lombok.val;
import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import net.minecraftforge.common.ForgeConfigSpec;
import java.nio.file.Path;
import static net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraft.client.resources.I18n;

public class MagnesiumExtrasConfig
{
    public static ForgeConfigSpec ConfigSpec;

    public static ConfigValue<String> fadeInQuality;

    public static ConfigValue<String> fpsCounterMode;
    public static ConfigValue<Integer> fpsCounterPosition;
    public static ForgeConfigSpec.ConfigValue<Integer> cloudHeight;


    public static ConfigValue<Integer> maxTileEntityRenderDistanceSquare;
    public static ConfigValue<Integer> maxTileEntityRenderDistanceY;

    public static ConfigValue<Integer> maxEntityRenderDistanceSquare;
    public static ConfigValue<Integer> maxEntityRenderDistanceY;

    public static ConfigValue<Boolean> fog;
    public static ConfigValue<Boolean> enableDistanceChecks;


    // Ok Zoomer
    public static ZoomValues zoomValues = new ZoomValues();
    public static ConfigValue<Boolean> lowerZoomSensitivity;
    public static ConfigValue<String> zoomTransition;
    public static ConfigValue<String> zoomMode;
    public static ConfigValue<String> cinematicCameraMode;
    public static ConfigValue<Boolean> zoomScrolling;
    public static ConfigValue<Boolean> zoomOverlay;


    public static ForgeConfigSpec.EnumValue<FullscreenMode> fullScreenMode;


    // Total Darkness
    public static double darkNetherFogEffective;
    public static double darkEndFogEffective;
    public static ForgeConfigSpec.BooleanValue trueDarknessEnabled;
    public static ForgeConfigSpec.EnumValue<DarknessOption> darknessOption;
    //advanced
    public static ForgeConfigSpec.DoubleValue darkNetherFogConfigured;
    public static ForgeConfigSpec.BooleanValue darkEnd;
    public static ForgeConfigSpec.DoubleValue darkEndFogConfigured;
    public static ForgeConfigSpec.BooleanValue darkSkyless;
    public static ForgeConfigSpec.BooleanValue blockLightOnly;
    public static ForgeConfigSpec.BooleanValue ignoreMoonPhase;
    public static ForgeConfigSpec.DoubleValue minimumMoonLevel;
    public static ForgeConfigSpec.DoubleValue maximumMoonLevel;
    public static ForgeConfigSpec.BooleanValue darkOverworld;
    public static ForgeConfigSpec.BooleanValue darkDefault;
    public static ForgeConfigSpec.BooleanValue darkNether;



    static
    {
        val builder = new ConfigBuilder(I18n.get("mg_extra.dynamic_light.title"));

        builder.Block(I18n.get("mg_extra.misc.option.title"), b -> {
            cloudHeight = b.define(I18n.get("mg_extra.misc.cloud_height.title"), 196);
            fadeInQuality =  b.define(I18n.get("mg_extra.misc.chunk_quality.title"), "FANCY");
            fog = b.define(I18n.get("mg_extra.misc.render_fog.title"), true);
            fullScreenMode = b.defineEnum(I18n.get("mg_extra.misc.full_screen_mode.title"), FullscreenMode.FULLSCREEN);
        });

        builder.Block(I18n.get("mg_extra.fps_counter.option.title"), b -> {
            fpsCounterMode = b.define(I18n.get("mg_extra.fps_counter.mode.title"), "ADVANCED");
            fpsCounterPosition = b.define(I18n.get("mg_extra.fps_counter.position.title"), 12);
        });

        builder.Block(I18n.get("mg_extra.entity_dis.option.title"), b -> {
            enableDistanceChecks = b.define(I18n.get("mg_extra.entity_dis.enable_dis_checks.title"), true);

            maxTileEntityRenderDistanceSquare = b.define(I18n.get("mg_extra.entity_dis.max_TE_render_dis_S.title"), 4096);
            maxTileEntityRenderDistanceY = b.define(I18n.get("mg_extra.entity_distance.max_TE_render_dis_Y.title"), 32);

            maxEntityRenderDistanceSquare = b.define(I18n.get("mg_extra.entity_distance.max_entity_render_dis_S.title"), 4096);
            maxEntityRenderDistanceY = b.define(I18n.get("mg_extra.entity_distance.max_entity_render_dis_Y.title"), 32);
        });

        builder.Block(I18n.get("mg_extra.zoom.option.name"), b -> {
            lowerZoomSensitivity = b.define(I18n.get("mg_extra.zoom.lower_sensitivity.title"), true);
            zoomScrolling = b.define(I18n.get("mg_extra.zoom.scrolling.title"), true);
            zoomTransition = b.define(I18n.get("mg_extra.zoom.transition.title"), ZoomTransitionOptions.SMOOTH.toString());
            zoomMode = b.define(I18n.get("mg_extra.zoom.keybind_mode.title"), ZoomModes.HOLD.toString());
            cinematicCameraMode = b.define(I18n.get("mg_extra.zoom.cinematic_cam_mode.title"), CinematicCameraOptions.OFF.toString());
            zoomOverlay = b.define(I18n.get("mg_extra.zoom.overlay.title"), true);
            //zoomValues = b.define(I18n.get("mg_extra.zoom.values.title"), new ZoomValues());
        });

        builder.Block(I18n.get("mg_extra.ture_darkness.option.title"), b -> {
            trueDarknessEnabled = b.define(I18n.get("mg_extra.ture_darkness.enable_switch.title"), true);
            darknessOption = b.defineEnum(I18n.get("mg_extra.ture_darkness.setting.title"), DarknessOption.DARK);

            builder.Block(I18n.get("mg_extra.advanced.option.title"), b2 -> {
                blockLightOnly = b2.define(I18n.get("mg_extra.advanced.block_light_only.title"), false);
                ignoreMoonPhase = b2.define(I18n.get("mg_extra.advanced.ignore_moon_light.title"), false);
                minimumMoonLevel = b2.defineInRange(I18n.get("mg_extra.advanced.mini_moon_light.title"), 0, 0, 1d);
                maximumMoonLevel = b2.defineInRange(I18n.get("mg_extra.advanced.max_moon_light.title"), 0.25d, 0, 1d);
            });

            builder.Block(I18n.get("mg_extra.dim_setting.option.title"), b2 -> {
                darkOverworld = b2.define(I18n.get("mg_extra.dim_setting.dark_overworld.title"), true);
                darkDefault = b2.define(I18n.get("mg_extra.dim_setting.dark_default.title"), false);
                darkNether = b2.define(I18n.get("mg_extra.dim_setting.dark_nether.title"), false);
                darkNetherFogConfigured = b2.defineInRange(I18n.get("mg_extra.dim_setting.dark_nether_fog_configured.title"), .5, 0, 1d);
                darkEnd = b2.define(I18n.get("mg_extra.dim_setting.dark_end.title"), false);
                darkEndFogConfigured = b.defineInRange(I18n.get("mg_extra.dim_setting.dark_end_fog_configured.title"), 0, 0, 1d);
                darkSkyless = b2.define(I18n.get("mg_extra.dim_setting.dark_skylight.title"), false);
            });
        });

        ConfigSpec = builder.Save();
    }

    public static void loadConfig(Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

        configData.load();
        ConfigSpec.setConfig(configData);
    }


    public static enum Complexity implements TextProvider
    {
        OFF(I18n.get("mg_extra.option.off")),
        SIMPLE(I18n.get("mg_extra.option.simple")),
        ADVANCED(I18n.get("mg_extra.option.advanced"));

        private final String name;

        private Complexity(String name) {
            this.name = name;
        }

        public String getLocalizedName() {
            return this.name;
        }
    }

    public static enum Quality implements TextProvider
    {
        OFF(I18n.get("mg_extra.option.off")),
        FAST(I18n.get("mg_extra.option.fast")),
        FANCY(I18n.get("mg_extra.option.fancy"));

        private final String name;

        private Quality(String name) {
            this.name = name;
        }

        public String getLocalizedName() {
            return this.name;
        }
    }

    public enum DarknessOption {
        PITCH_BLACK(0f),
        REALLY_DARK (0.04f),
        DARK(0.08f),
        DIM(0.12f);

        public final float value;

        private DarknessOption(float value) {
            this.value = value;
        }
    }

    public enum FullscreenMode {
        WINDOWED,
        BORDERLESS,
        FULLSCREEN
    }

    public enum ZoomTransitionOptions {
        OFF,
        SMOOTH
    }

    public enum ZoomModes {
        HOLD,
        TOGGLE,
        PERSISTENT
    }

    public enum CinematicCameraOptions {
        OFF,
        VANILLA,
        MULTIPLIED
    }

    public static class ZoomValues {
        //@Setting.Constrain.Range(min = Double.MIN_NORMAL)
        //@Setting(comment = "The divisor applied to the FOV when zooming.")
        public double zoomDivisor = 4.0;

        //@Setting.Constrain.Range(min = Double.MIN_NORMAL)
        //@Setting(comment = "The minimum value that you can scroll down.")
        public double minimumZoomDivisor = 1.0;

        //@Setting.Constrain.Range(min = Double.MIN_NORMAL)
        //@Setting(comment = "The maximum value that you can scroll up.")
        public double maximumZoomDivisor = 50.0;

        //@Setting.Constrain.Range(min = 0.0)
        //@Setting(comment = "The number which is de/incremented by zoom scrolling. Used when the zoom divisor is above the starting point.")
        public double scrollStep = 1.0;

       //"The number which is de/incremented by zoom scrolling. Used when the zoom divisor is below the starting point.")
        public double lesserScrollStep = 0.5;

        //@Setting.Constrain.Range(min = Double.MIN_NORMAL)
        //@Setting(comment = "The multiplier used for the multiplied cinematic camera.")
        public double cinematicMultiplier = 4.0;

        ////@Setting.Constrain.Range(min = Double.MIN_NORMAL, max = 1.0)
        //@Setting(comment = "The multiplier used for smooth transitions.")
        public double smoothMultiplier = 0.75;

        //@Setting(comment = "The minimum value which the linear transition step can reach.")
        public double minimumLinearStep = 0.125;

        //@Setting.Constrain.Range(min = Double.MIN_NORMAL)
       // @Setting(comment = "The maximum value which the linear transition step can reach.")
        public double maximumLinearStep = 0.25;
    }
}
