package vice.magnesium_extras.mixins.SodiumConfig;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import net.minecraft.client.GameSettings;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vice.magnesium_extras.config.MagnesiumExtrasConfig;
import vice.magnesium_extras.mixins.BorderlessFullscreen.MainWindowAccessor;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(SodiumGameOptionPages.class)
public class SodiumGameOptionsMixin
{
    @Shadow @Final private static SodiumOptionsStorage sodiumOpts;

    //@Inject(at = @At("HEAD"), method = "experimental", remap = false, cancellable = true)

    @Shadow @Final private static MinecraftOptionsStorage vanillaOpts;

    //private static void experimental(CallbackInfoReturnable<OptionPage> cir)
    @Inject(
            method = "experimental",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;copyOf(Ljava/util/Collection;)Lcom/google/common/collect/ImmutableList;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false,
            cancellable = true
    )
    private static void Inject(CallbackInfoReturnable<OptionPage> cir, List<OptionGroup> groups)
    {
        groups.removeIf((optionGroup) ->
            optionGroup
                    .getOptions()
                    .stream()
                    .anyMatch((option) -> Objects.equals(option.getName(), I18n.get("mg_extra.display_fps.display.title")))
        );

        Option<MagnesiumExtrasConfig.Complexity> displayFps =  OptionImpl.createBuilder(MagnesiumExtrasConfig.Complexity.class, sodiumOpts)
                .setName(I18n.get("mg_extra.display_fps.display.title"))
                .setTooltip(I18n.get("mg_extra.display_fps.display.desc"))
                .setControl((option) -> new CyclingControl<>(option, MagnesiumExtrasConfig.Complexity.class, new String[] { I18n.get("mg_extra.option.off"), I18n.get("mg_extra.option.simple"), I18n.get("mg_extra.option.advanced")}))
                .setBinding(
                        (opts, value) -> MagnesiumExtrasConfig.fpsCounterMode.set(value.toString()),
                        (opts) -> MagnesiumExtrasConfig.Complexity.valueOf(MagnesiumExtrasConfig.fpsCounterMode.get()))
                .setImpact(OptionImpact.LOW)
                .build();


        Option<Integer> displayFpsPos = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(I18n.get("mg_extra.display_fps.position.title"))
                .setTooltip(I18n.get("mg_extra.display_fps.position.desc"))
                .setControl((option) -> {
                    return new SliderControl(option, 4, 64, 2, ControlValueFormatter.quantity(I18n.get("mg_extra.option.unit.pixels")));
                })
                .setImpact(OptionImpact.LOW)
                .setBinding(
                        (opts, value) -> MagnesiumExtrasConfig.fpsCounterPosition.set(value),
                        (opts) -> MagnesiumExtrasConfig.fpsCounterPosition.get())
                .build();

        groups.add(OptionGroup.createBuilder()
                .add(displayFps)
                .add(displayFpsPos)
                .build());




        OptionImpl<SodiumGameOptions, Boolean> totalDarkness = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
                .setName(I18n.get("mg_extra.ture_darkness.options.title"))
                .setTooltip(I18n.get("mg_extra.ture_darkness.options.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.trueDarknessEnabled.set(value),
                        (options) -> MagnesiumExtrasConfig.trueDarknessEnabled.get())
                .setImpact(OptionImpact.LOW)
                .build();

        Option<MagnesiumExtrasConfig.DarknessOption> totalDarknessSetting =  OptionImpl.createBuilder(MagnesiumExtrasConfig.DarknessOption.class, sodiumOpts)
                .setName(I18n.get("mg_extra.ture_darkness.mode.title"))
                .setTooltip(I18n.get("mg_extra.ture_darkness.mode.desc"))
                .setControl((option) -> new CyclingControl<>(option, MagnesiumExtrasConfig.DarknessOption.class, new String[] { I18n.get("mg_extra.option.pitch_black"), I18n.get("mg_extra.option.really_dark"), I18n.get("mg_extra.option.dark"), I18n.get("mg_extra.option.dim")}))
                .setBinding(
                        (opts, value) -> MagnesiumExtrasConfig.darknessOption.set(value),
                        (opts) -> MagnesiumExtrasConfig.darknessOption.get())
                .setImpact(OptionImpact.LOW)
                .build();

        groups.add(OptionGroup.createBuilder()
                .add(totalDarkness)
                .add(totalDarknessSetting)
                .build());





        Option<MagnesiumExtrasConfig.Quality> fadeInQuality =  OptionImpl.createBuilder(MagnesiumExtrasConfig.Quality.class, sodiumOpts)
                .setName(I18n.get("mg_extra.misc.chunk_fade_quality.title"))
                .setTooltip(I18n.get("mg_extra.misc.chunk_fade_quality.desc"))
                .setControl((option) -> new CyclingControl<>(option, MagnesiumExtrasConfig.Quality.class, new String[] { I18n.get("mg_extra.option.off"), I18n.get("mg_extra.option.fast"), I18n.get("mg_extra.option.fancy")}))
                .setBinding(
                        (opts, value) -> MagnesiumExtrasConfig.fadeInQuality.set(value.toString()),
                        (opts) -> MagnesiumExtrasConfig.Quality.valueOf(MagnesiumExtrasConfig.fadeInQuality.get()))
                .setImpact(OptionImpact.LOW)
                .build();

        OptionImpl<SodiumGameOptions, Boolean> fog = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
                .setName(I18n.get("mg_extra.fog.title"))
                .setTooltip(I18n.get("mg_extra.fog.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.fog.set(value),
                        (options) -> MagnesiumExtrasConfig.fog.get())
                .setImpact(OptionImpact.LOW)
                .build();

        OptionImpl<SodiumGameOptions, Integer> cloudHeight = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(I18n.get("mg_extra.cloud_height.title"))
                .setTooltip(I18n.get("mg_extra.cloud_height.desc"))
                .setControl((option) -> new SliderControl(option, 64, 256, 4, ControlValueFormatter.quantity(I18n.get("mg_extra.option.unit.blocks"))))
                .setBinding(
                        (options, value) -> {
                            MagnesiumExtrasConfig.cloudHeight.set(value);
                        },
                        (options) ->  MagnesiumExtrasConfig.cloudHeight.get())
                .setImpact(OptionImpact.LOW)
                .build();


        groups.add(OptionGroup.createBuilder()
                .add(fadeInQuality)
                .add(fog)
                .add(cloudHeight)
                .build());





        OptionImpl<SodiumGameOptions, Boolean> enableDistanceChecks = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
                .setName(I18n.get("mg_extra.enable_max_entity_distance.title"))
                .setTooltip(I18n.get("mg_extra.enable_max_entity_distance.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.enableDistanceChecks.set(value),
                        (options) -> MagnesiumExtrasConfig.enableDistanceChecks.get())
                .setImpact(OptionImpact.LOW)
                .build();


        groups.add(OptionGroup
                .createBuilder()
                .add(enableDistanceChecks)
                .build()
        );





        OptionImpl<SodiumGameOptions, Integer> maxEntityDistance = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(I18n.get("mg_extra.max_entity_distance.title"))
                .setTooltip(I18n.get("mg_extra.max_entity_distance.desc"))
                .setControl((option) -> new SliderControl(option, 16, 192, 8, ControlValueFormatter.quantity(I18n.get("mg_extra.option.unit.blocks"))))
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.maxEntityRenderDistanceSquare.set(value * value),
                        (options) ->  Math.toIntExact(Math.round(Math.sqrt(MagnesiumExtrasConfig.maxEntityRenderDistanceSquare.get()))))
                .setImpact(OptionImpact.EXTREME)
                .build();

        OptionImpl<SodiumGameOptions, Integer> maxEntityDistanceVertical = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(I18n.get("mg_extra.vertical_entity_distance.title"))
                .setTooltip(I18n.get("mg_extra.vertical_entity_distance.desc"))
                .setControl((option) -> new SliderControl(option, 16, 64, 4, ControlValueFormatter.quantity(I18n.get("mg_extra.option.unit.blocks"))))
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.maxEntityRenderDistanceY.set(value ),
                        (options) -> MagnesiumExtrasConfig.maxEntityRenderDistanceY.get())
                .setImpact(OptionImpact.EXTREME)
                .build();


        groups.add(OptionGroup
                .createBuilder()
                .add(maxEntityDistance)
                .add(maxEntityDistanceVertical)
                .build()
        );





        OptionImpl<SodiumGameOptions, Integer> maxTileEntityDistance = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(I18n.get("mg_extra.max_tile_distance.title"))
                .setTooltip(I18n.get("mg_extra.max_tile_distance.desc"))
                .setControl((option) -> new SliderControl(option, 16, 256, 8, ControlValueFormatter.quantity(I18n.get("mg_extra.option.unit.blocks"))))
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.maxTileEntityRenderDistanceSquare.set(value * value),
                        (options) -> Math.toIntExact(Math.round(Math.sqrt(MagnesiumExtrasConfig.maxTileEntityRenderDistanceSquare.get()))))
                .setImpact(OptionImpact.HIGH)
                .build();

        OptionImpl<SodiumGameOptions, Integer> maxTileEntityDistanceVertical = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(I18n.get("mg_extra.vertical_entity_distance.title"))
                .setTooltip(I18n.get("mg_extra.vertical_entity_distance.desc"))
                .setControl((option) -> new SliderControl(option, 16, 64, 4, ControlValueFormatter.quantity(I18n.get("mg_extra.option.unit.blocks"))))
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.maxTileEntityRenderDistanceY.set(value ),
                        (options) -> MagnesiumExtrasConfig.maxTileEntityRenderDistanceY.get())
                .setImpact(OptionImpact.HIGH)
                .build();

        groups.add(OptionGroup
                .createBuilder()
                .add(maxTileEntityDistance)
                .add(maxTileEntityDistanceVertical)
                .build()
        );
    }


    @Inject(
            method = "general",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;copyOf(Ljava/util/Collection;)Lcom/google/common/collect/ImmutableList;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false,
            cancellable = true
    )
    private static void InjectGeneral(CallbackInfoReturnable<OptionPage> cir, List<OptionGroup> groups)
    {
        OptionImpl<GameSettings, MagnesiumExtrasConfig.FullscreenMode> fullscreenMode = OptionImpl.createBuilder( MagnesiumExtrasConfig.FullscreenMode.class, vanillaOpts)
                .setName(I18n.get("mg_extra.full_screen_mode.title"))
                .setTooltip(I18n.get("mg_extra.full_screen_mode.desc"))
                .setControl((opt) -> new CyclingControl<>(opt, MagnesiumExtrasConfig.FullscreenMode.class, new String[] { I18n.get("mg_extra.option.windowed"), I18n.get("mg_extra.option.borderless"), I18n.get("mg_extra.option.fullscreen")}))
                .setBinding(
                        (opts, value) -> {
                            MagnesiumExtrasConfig.fullScreenMode.set(value);
                            opts.fullscreen = value != MagnesiumExtrasConfig.FullscreenMode.WINDOWED;

                            Minecraft client = Minecraft.getInstance();
                            MainWindow window = client.getWindow();
                            if (window != null && window.isFullscreen() != opts.fullscreen)
                            {
                                window.toggleFullScreen();
                                opts.fullscreen = window.isFullscreen();
                            }

                            if (window != null && opts.fullscreen)
                            {
                                ((MainWindowAccessor) (Object) window).setDirty(true);
                                window.changeFullscreenVideoMode();
                            }
                        },
                        (opts) -> MagnesiumExtrasConfig.fullScreenMode.get())
                .build();

        ReplaceOption(groups, I18n.get("mg_extra.option.fullscreen"), fullscreenMode);
    }


    private static void ReplaceOption(List<OptionGroup> groups, String name, Option<?> replaceOption)
    {
        List<OptionGroup> newList = new ArrayList<>();

        for (OptionGroup optionGroup : groups)
        {
            OptionGroup.Builder builder = OptionGroup.createBuilder();

            for (Option<?> option : optionGroup.getOptions())
            {
                builder.add(Objects.equals(option.getName(), name) ? replaceOption : option);
            }

            newList.add(builder.build());
        }

        groups.clear();
        groups.addAll(newList);
    }

    @ModifyConstant(method = "experimental", remap = false, constant = @Constant(stringValue = "Experimental"))
    private static String ChangeCategoryName(String old) {
        return I18n.get("mg_extra.extras.options.name");
    }
}