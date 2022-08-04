package vice.rubidium_extras.mixins.SodiumConfig;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import net.minecraft.client.Options;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vice.rubidium_extras.config.MagnesiumExtrasConfig;
import vice.rubidium_extras.mixins.BorderlessFullscreen.MainWindowAccessor;
import net.minecraft.client.resources.language.I18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(SodiumGameOptionPages.class)
public class SodiumGameOptionsMixin
{
    @Shadow
    @Final
    private static SodiumOptionsStorage sodiumOpts;

    //@Inject(at = @At("HEAD"), method = "experimental", remap = false, cancellable = true)

    @Shadow
    @Final
    private static MinecraftOptionsStorage vanillaOpts;

    //private static void experimental(CallbackInfoReturnable<OptionPage> cir)
    @Inject(
            method = "advanced",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;copyOf(Ljava/util/Collection;)Lcom/google/common/collect/ImmutableList;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false,
            cancellable = true
    )
    private static void Inject(CallbackInfoReturnable<OptionPage> cir, List<OptionGroup> groups)
    {
//        groups.removeIf((optionGroup) ->
//            optionGroup
//                    .getOptions()
//                    .stream()
//                    .anyMatch((option) -> Objects.equals(option.getName(), "Display FPS"))
//        );

        Option<MagnesiumExtrasConfig.Complexity> displayFps =  OptionImpl.createBuilder(MagnesiumExtrasConfig.Complexity.class, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.display_fps.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.display_fps.tooltip")))
                .setControl((option) -> new CyclingControl<>(option, MagnesiumExtrasConfig.Complexity.class, new Component[] {
                        Component.nullToEmpty(I18n.get("rb_extra.options.off")),
                        Component.nullToEmpty(I18n.get("rb_extra.options.simple")),
                        Component.nullToEmpty(I18n.get("rb_extra.options.advanced"))
                }))
                .setBinding(
                        (opts, value) -> MagnesiumExtrasConfig.fpsCounterMode.set(value.toString()),
                        (opts) -> MagnesiumExtrasConfig.Complexity.valueOf(MagnesiumExtrasConfig.fpsCounterMode.get()))
                .setImpact(OptionImpact.LOW)
                .build();


        Option<Integer> displayFpsPos = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.position_fps.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.position_fps.tooltip")))
                .setControl((option) -> new SliderControl(option, 4, 64, 2, ControlValueFormatter.translateVariable("rb_extra.options.unit.pixels")))
                .setImpact(OptionImpact.LOW)
                .setBinding(
                        (opts, value) -> MagnesiumExtrasConfig.fpsCounterPosition.set(value),
                        (opts) -> MagnesiumExtrasConfig.fpsCounterPosition.get())
                .build();

        OptionImpl<SodiumGameOptions, Boolean> displayFpsAlignRight = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.right_align_fps.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.right_align_fps.tooltip")))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.fpsCounterAlignRight.set(value),
                        (options) -> MagnesiumExtrasConfig.fpsCounterAlignRight.get())
                .setImpact(OptionImpact.LOW)
                .build();

        groups.add(OptionGroup.createBuilder()
                .add(displayFps)
                .add(displayFpsAlignRight)
                .add(displayFpsPos)
                .build());




        OptionImpl<SodiumGameOptions, Boolean> totalDarkness = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.ture_darkness.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.ture_darkness.tooltip")))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.trueDarknessEnabled.set(value),
                        (options) -> MagnesiumExtrasConfig.trueDarknessEnabled.get())
                .setImpact(OptionImpact.LOW)
                .build();

        Option<MagnesiumExtrasConfig.DarknessOption> totalDarknessSetting =  OptionImpl.createBuilder(MagnesiumExtrasConfig.DarknessOption.class, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.ture_darkness_mode.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.ture_darkness_mode.tooltip")))
                .setControl((option) -> new CyclingControl<>(option, MagnesiumExtrasConfig.DarknessOption.class, new Component[] {
                        Component.nullToEmpty(I18n.get("rb_extra.options.pitch_black")),
                        Component.nullToEmpty(I18n.get("rb_extra.options.really_dark")),
                        Component.nullToEmpty(I18n.get("rb_extra.options.dark")),
                        Component.nullToEmpty(I18n.get("rb_extra.options.dim"))
                }))
                .setBinding(
                        (opts, value) -> MagnesiumExtrasConfig.darknessOption.set(value),
                        (opts) -> MagnesiumExtrasConfig.darknessOption.get())
                .setImpact(OptionImpact.LOW)
                .build();

        groups.add(OptionGroup.createBuilder()
                .add(totalDarkness)
                .add(totalDarknessSetting)
                .build());





//        Option<MagnesiumExtrasConfig.Quality> fadeInQuality =  OptionImpl.createBuilder(MagnesiumExtrasConfig.Quality.class, sodiumOpts)
//                .setName(Component.nullToEmpty("Chunk Fade In Quality"))
//                .setTooltip(Component.nullToEmpty("Controls how fast chunks fade in. No performance hit, Fancy simply takes longer, but looks a bit cooler."))
//                .setControl((option) -> new CyclingControl<>(option, MagnesiumExtrasConfig.Quality.class, new Component[] {
//                        Component.nullToEmpty("Off"),
//                        Component.nullToEmpty("Fast"),
//                        Component.nullToEmpty("Fancy")
//                }))
//                .setBinding(
//                        (opts, value) -> MagnesiumExtrasConfig.fadeInQuality.set(value.toString()),
//                        (opts) -> MagnesiumExtrasConfig.Quality.valueOf(MagnesiumExtrasConfig.fadeInQuality.get()))
//                .setImpact(OptionImpact.LOW)
//                .build();

        OptionImpl<SodiumGameOptions, Boolean> fog = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.fog.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.fog.tooltip")))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.fog.set(value),
                        (options) -> MagnesiumExtrasConfig.fog.get())
                .setImpact(OptionImpact.LOW)
                .build();

//        OptionImpl<SodiumGameOptions, Boolean> hideJEI = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
//                .setName(Component.nullToEmpty(I18n.get("rb_extra.hide_jei_until_searching.name")))
//                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.hide_jei_until_searching.tooltip")))
//                .setControl(TickBoxControl::new)
//                .setBinding(
//                        (options, value) -> MagnesiumExtrasConfig.hideJEI.set(value),
//                        (options) -> MagnesiumExtrasConfig.hideJEI.get())
//                .setImpact(OptionImpact.LOW)
//                .build();

        OptionImpl<SodiumGameOptions, Integer> cloudHeight = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.cloud_height.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.cloud_height.tooltip")))
                .setControl((option) -> new SliderControl(option, 64, 364, 4, ControlValueFormatter.translateVariable("rb_extra.options.unit.blocks")))
                .setBinding(
                        (options, value) -> {
                            MagnesiumExtrasConfig.cloudHeight.set(value);
                        },
                        (options) ->  MagnesiumExtrasConfig.cloudHeight.get())
                .setImpact(OptionImpact.LOW)
                .build();


        groups.add(OptionGroup.createBuilder()
                //.add(fadeInQuality)
                .add(fog)
                .add(cloudHeight)
                .build());





        OptionImpl<SodiumGameOptions, Boolean> enableDistanceChecks = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.enable_max_entity_distance.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.enable_max_entity_distance.tooltip")))
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
                .setName(Component.nullToEmpty(I18n.get("rb_extra.max_entity_distance.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.max_entity_distance.tooltip")))
                .setControl((option) -> new SliderControl(option, 16, 192, 8, ControlValueFormatter.translateVariable("rb_extra.options.unit.blocks")))
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.maxEntityRenderDistanceSquare.set(value * value),
                        (options) ->  Math.toIntExact(Math.round(Math.sqrt(MagnesiumExtrasConfig.maxEntityRenderDistanceSquare.get()))))
                .setImpact(OptionImpact.HIGH)
                .build();

        OptionImpl<SodiumGameOptions, Integer> maxEntityDistanceVertical = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.vertical_entity_distance.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.vertical_entity_distance.tooltip")))
                .setControl((option) -> new SliderControl(option, 16, 64, 4, ControlValueFormatter.translateVariable(I18n.get("rb_extra.options.unit.blocks"))))
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.maxEntityRenderDistanceY.set(value ),
                        (options) -> MagnesiumExtrasConfig.maxEntityRenderDistanceY.get())
                .setImpact(OptionImpact.HIGH)
                .build();


        groups.add(OptionGroup
                .createBuilder()
                .add(maxEntityDistance)
                .add(maxEntityDistanceVertical)
                .build()
        );





        OptionImpl<SodiumGameOptions, Integer> maxTileEntityDistance = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.max_tile_distance.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.max_tile_distance.tooltip")))
                .setControl((option) -> new SliderControl(option, 16, 256, 8, ControlValueFormatter.translateVariable("rb_extra.options.unit.blocks")))
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.maxTileEntityRenderDistanceSquare.set(value * value),
                        (options) -> Math.toIntExact(Math.round(Math.sqrt(MagnesiumExtrasConfig.maxTileEntityRenderDistanceSquare.get()))))
                .setImpact(OptionImpact.HIGH)
                .build();

        OptionImpl<SodiumGameOptions, Integer> maxTileEntityDistanceVertical = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.vertical_tile_distance.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.vertical_tile_distance.tooltip")))
                .setControl((option) -> new SliderControl(option, 16, 64, 4, ControlValueFormatter.translateVariable("rb_extra.options.unit.blocks")))
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
        OptionImpl<Options, MagnesiumExtrasConfig.FullscreenMode> fullscreenMode = OptionImpl.createBuilder( MagnesiumExtrasConfig.FullscreenMode.class, vanillaOpts)
                .setName(Component.nullToEmpty(I18n.get("rb_extra.full_screen_mode.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("rb_extra.full_screen_mode.tooltip")))
                .setControl((opt) -> new CyclingControl<>(opt, MagnesiumExtrasConfig.FullscreenMode.class, new Component[] {
                        Component.nullToEmpty(I18n.get("rb_extra.options.windowed")),
                        Component.nullToEmpty(I18n.get("rb_extra.options.borderless")),
                        Component.nullToEmpty(I18n.get("rb_extra.options.fullscreen"))
                }))
                .setBinding(
                        (opts, value) -> {
                            MagnesiumExtrasConfig.fullScreenMode.set(value);
                            opts.fullscreen = value != MagnesiumExtrasConfig.FullscreenMode.WINDOWED;

                            Minecraft client = Minecraft.getInstance();
                            Window window = client.getWindow();
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

        ReplaceOption(groups, I18n.get("rb_extra.options.fullscreen"), fullscreenMode);
    }


    private static void ReplaceOption(List<OptionGroup> groups, String name, Option<?> replaceOption)
    {
        List<OptionGroup> newList = new ArrayList<>();

        for (OptionGroup optionGroup : groups)
        {

            OptionGroup.Builder builder = OptionGroup.createBuilder();

            for (Option<?> option : optionGroup.getOptions())
            {
                builder.add(Objects.equals(option.getName().getString(), name) ? replaceOption : option);
            }

            newList.add(builder.build());
        }

        groups.clear();
        groups.addAll(newList);
    }

    @ModifyConstant(method = "advanced", remap = false, constant = @Constant(stringValue = "sodium.options.pages.advanced"))
    private static String ChangeCategoryName(String old) {
        return I18n.get("rb_extra.page.extras");
    }
}