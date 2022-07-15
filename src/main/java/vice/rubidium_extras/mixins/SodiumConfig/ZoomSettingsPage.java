package vice.rubidium_extras.mixins.SodiumConfig;


import com.google.common.collect.ImmutableList;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vice.rubidium_extras.config.MagnesiumExtrasConfig;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;

@Pseudo
@Mixin(SodiumOptionsGUI.class)
public abstract class ZoomSettingsPage
{

    @Shadow
    @Final
    private List<OptionPage> pages;
    private static final SodiumOptionsStorage sodiumOpts = new SodiumOptionsStorage();


    @Inject(method = "<init>", at = @At("RETURN"))
    private void DynamicLights(Screen prevScreen, CallbackInfo ci)
    {
        List<OptionGroup> groups = new ArrayList();

        //OptionImpl<SodiumGameOptions, Boolean> lowerSensitivity = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
        //        .setName(I18n.get("ru_extras.zoom.lower_sensitivity.title"))
        //        .setTooltip("Lowers your sensitivity when zooming to make it feel more consistent.")
        //        .setControl(TickBoxControl::new)
        //        .setBinding(
        //                (options, value) -> MagnesiumExtrasConfig.lowerZoomSensitivity.set(value),
        //                (options) -> MagnesiumExtrasConfig.lowerZoomSensitivity.get())
        //        .setImpact(OptionImpact.LOW)
        //        .build();

        OptionImpl<SodiumGameOptions, Boolean> zoomScrolling = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
                .setName(I18n.get("ru_extras.zoom.scrolling.title"))
                .setTooltip(I18n.get("ru_extras.zoom.scrolling.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.zoomScrolling.set(value),
                        (options) -> MagnesiumExtrasConfig.zoomScrolling.get())
                .setImpact(OptionImpact.LOW)
                .build();

        OptionImpl<SodiumGameOptions, Boolean> zoomOverlay = OptionImpl.createBuilder(Boolean.class, sodiumOpts)
                .setName(I18n.get("ru_extras.zoom.overlay.title"))
                .setTooltip(I18n.get("ru_extras.zoom.overlay.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> MagnesiumExtrasConfig.zoomOverlay.set(value),
                        (options) -> MagnesiumExtrasConfig.zoomOverlay.get())
                .setImpact(OptionImpact.LOW)
                .build();

        groups.add(OptionGroup
                .createBuilder()
                //.add(lowerSensitivity)
                .add(zoomScrolling)
                .add(zoomOverlay)
                .build()
        );



        Option<MagnesiumExtrasConfig.ZoomTransitionOptions> zoomTransition =  OptionImpl.createBuilder(MagnesiumExtrasConfig.ZoomTransitionOptions.class, sodiumOpts)
                .setName(I18n.get("ru_extras.zoom.transition.title"))
                .setTooltip(I18n.get("ru_extras.zoom.transition.desc"))
                .setControl((option) -> new CyclingControl<>(option, MagnesiumExtrasConfig.ZoomTransitionOptions.class, new String[] { I18n.get("ru_extras.option.off"), I18n.get("ru_extras.option.smooth") }))
                .setBinding(
                        (opts, value) -> MagnesiumExtrasConfig.zoomTransition.set(value.toString()),
                        (opts) -> MagnesiumExtrasConfig.ZoomTransitionOptions.valueOf(MagnesiumExtrasConfig.zoomTransition.get()))
                .setImpact(OptionImpact.LOW)
                .build();

        Option<MagnesiumExtrasConfig.ZoomModes> zoomMode =  OptionImpl.createBuilder(MagnesiumExtrasConfig.ZoomModes.class, sodiumOpts)
                .setName(I18n.get("ru_extras.zoom.keybind.title"))
                .setTooltip(I18n.get("ru_extras.zoom.keybind.desc"))
                .setControl((option) -> new CyclingControl<>(option, MagnesiumExtrasConfig.ZoomModes.class, new String[] { I18n.get("ru_extras.option.hold"), I18n.get("ru_extras.option.toggle"), I18n.get("ru_extras.option.persistent")}))
                .setBinding(
                        (opts, value) -> MagnesiumExtrasConfig.zoomMode.set(value.toString()),
                        (opts) -> MagnesiumExtrasConfig.ZoomModes.valueOf(MagnesiumExtrasConfig.zoomMode.get()))
                .setImpact(OptionImpact.LOW)
                .build();

        //Option<MagnesiumExtrasConfig.CinematicCameraOptions> cinematicCameraMode =  OptionImpl.createBuilder(MagnesiumExtrasConfig.CinematicCameraOptions.class, sodiumOpts)
        //        .setName(I18n.get("ru_extras.zoom.cinematic_cam_mode.options_title"))
        //        .setTooltip(I18n.get("ru_extras.zoom.cinematic_cam_mode.title"))
        //        .setControl((option) -> new CyclingControl<>(option, MagnesiumExtrasConfig.CinematicCameraOptions.class, new String[] { I18n.get("ru_extras.option.off"), I18n.get("ru_extras.option.vanilla"), I18n.get("ru_extras.option.multiplied")}))
        //        .setBinding(
        //                (opts, value) -> MagnesiumExtrasConfig.cinematicCameraMode.set(value.toString()),
        //                (opts) -> MagnesiumExtrasConfig.CinematicCameraOptions.valueOf(MagnesiumExtrasConfig.cinematicCameraMode.get()))
        //        .setImpact(OptionImpact.LOW)
        //        .build();

        groups.add(OptionGroup
                .createBuilder()
                .add(zoomTransition)
                .add(zoomMode)
                //.add(cinematicCameraMode)
                .build()
        );


        pages.add(new OptionPage(I18n.get("ru_extras.zoom.option.name"), ImmutableList.copyOf(groups)));
    }
}