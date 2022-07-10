package vice.rubidium_extras.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.common.ForgeConfigSpec;
import java.nio.file.Path;

import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

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

    public static ConfigValue<Boolean> hideJEI;

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
        var builder = new ConfigBuilder(I18n.get("rb_extra.misc.dynamic_light.name"));

        builder.Block(I18n.get("rb_extra.misc.page.name"), b -> {
            cloudHeight = b.define(I18n.get("rb_extra.misc.cloud_height.name"), 256);
            fadeInQuality =  b.define(I18n.get("rb_extra.chunk_quality.name"), "FANCY");
            fog = b.define(I18n.get("rb_extra.render_fog.name"), true);
            hideJEI = b.define(I18n.get("rb_extra.misc.hide_jei_until_searching.name"), true);
            fullScreenMode = b.defineEnum(I18n.get("rb_extra.misc.full_screen_mode.name"), FullscreenMode.FULLSCREEN);
        });

        builder.Block(I18n.get("rb_extra.fps_counter.object.name"), b -> {
            fpsCounterMode = b.define(I18n.get("rb_extra.fps_counter.mode.name"), "ADVANCED");
            fpsCounterPosition = b.define(I18n.get("rb_extra.fps_counter.position.name"), 12);
        });

        builder.Block(I18n.get("rb_extra.entity_dis.option.name"), b -> {
            enableDistanceChecks = b.define(I18n.get("rb_extra.entity_dis.enable_dis_checks.name"), true);

            maxTileEntityRenderDistanceSquare = b.define(I18n.get("rb_extra.entity_dis.max_TE_render_dis_S.name"), 4096);
            maxTileEntityRenderDistanceY = b.define(I18n.get("rb_extra.entity_distance.max_TE_render_dis_Y.name"), 32);

            maxEntityRenderDistanceSquare = b.define(I18n.get("rb_extra.entity_distance.max_entity_render_dis_S.name"), 4096);
            maxEntityRenderDistanceY = b.define(I18n.get("rb_extra.entity_distance.max_entity_render_dis_Y.name"), 32);
        });

        builder.Block("Zoom", b -> {
            lowerZoomSensitivity = b.define(I18n.get("rb_extra.zoom.lower_zoom_sensitivity.name"), true);
            zoomScrolling = b.define(I18n.get("rb_extra.zoom.scrolling_enable.name"), true);
            zoomTransition = b.define(I18n.get("rb_extra.zoom.transition_mode.name"), ZoomTransitionOptions.SMOOTH.toString());
            zoomMode = b.define(I18n.get("rb_extra.zoom.keybind_mode.name"), ZoomModes.HOLD.toString());
            cinematicCameraMode = b.define(I18n.get("rb_extra.zoom.cinematic_cam_mode_.name"), CinematicCameraOptions.OFF.toString());
            zoomOverlay = b.define(I18n.get("rb_extra.zoom.overlay_.name"), true);
            //zoomValues = b.define("Zoom Advanced Values", new ZoomValues());
        });

        builder.Block(I18n.get("rb_extra.ture_darkness.option.name"), b -> {
            trueDarknessEnabled = b.define(I18n.get("rb_extra.ture_darkness.enable_switch.name"), true);
            darknessOption = b.defineEnum(I18n.get("rb_extra.ture_darkness.setting.name"), DarknessOption.DARK);

            builder.Block("Advanced", b2 -> {
                blockLightOnly = b2.define(I18n.get("rb_extra.block_light_only.name"), false);
                ignoreMoonPhase = b2.define(I18n.get("rb_extra.ignore_moon_light.name"), false);
                minimumMoonLevel = b2.defineInRange(I18n.get("rb_extra.max_moon_light.name"), 0, 0, 1d);
                maximumMoonLevel = b2.defineInRange(I18n.get("rb_extra.mini_moon_light.name"), 0.25d, 0, 1d);
            });

            builder.Block(I18n.get("rb_extra.dim_setting.name"), b2 -> {
                darkOverworld = b2.define(I18n.get("rb_extra.dark_overworld.name"), true);
                darkDefault = b2.define(I18n.get("rb_extra.dark_default.name"), false);
                darkNether = b2.define(I18n.get("rb_extra.dark_nether.name"), false);
                darkNetherFogConfigured = b2.defineInRange(I18n.get("rb_extra.dark_nether_fog_configured.name"), .5, 0, 1d);
                darkEnd = b2.define(I18n.get("rb_extra.dark_end.name"), false);
                darkEndFogConfigured = b.defineInRange(I18n.get("rb_extra.dark_end_fog_configured.name"), 0, 0, 1d);
                darkSkyless = b2.define(I18n.get("rb_extra.dark_skylight.name"), false);
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
        OFF(I18n.get("rb_extra.options.off")),
        SIMPLE(I18n.get("rb_extra.options.simple")),
        ADVANCED(I18n.get("rb_extra.options.advanced"));

        private final String name;

        private Complexity(String name) {
            this.name = name;
        }

        public Component getLocalizedName() {
            return new TextComponent(this.name);
        }
    }

    public static enum Quality implements TextProvider
    {
        OFF(I18n.get("rb_extra.options.off")),
        FAST(I18n.get("rb_extra.options.fast")),
        FANCY(I18n.get("rb_extra.options.fancy"));

        private final String name;

        private Quality(String name) {
            this.name = name;
        }

        public Component getLocalizedName() {

            return new TextComponent(this.name);
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
