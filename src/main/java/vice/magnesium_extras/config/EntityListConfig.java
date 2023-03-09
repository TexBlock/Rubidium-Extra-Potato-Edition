package vice.magnesium_extras.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.function.Predicate;

public class EntityListConfig {
    public static ForgeConfigSpec ENTITY_LIST_CONFIG;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITY_LIST;

    static {
        List<? extends String> entity_list = Lists.newArrayList(
                "minecraft:ender_dragon",
                "minecraft:ghast",
                "alexsmobs:void_worm",
                "alexsmobs:void_worm_part",
                "minecraft:wither",
                "twilightforest:naga",
                "twilightforest:lich",
                "twilightforest:yeti",
                "twilightforest:snow_queen",
                "twilightforest:minoshroom",
                "twilightforest:hydra",
                "twilightforest:knight_phantom",
                "twilightforest:ur_ghast",
                "atum:pharaoh",
                "mowziesmobs:barako",
                "mowziesmobs:ferrous_wroughtnaut",
                "mowziesmobs:frostmaw",
                "aoa3:skeletron",
                "aoa3:smash",
                "aoa3:baroness",
                "aoa3:clunkhead",
                "aoa3:corallus",
                "aoa3:cotton_candor",
                "aoa3:craexxeus",
                "aoa3:xxeus",
                "aoa3:creep",
                "aoa3:crystocore",
                "aoa3:dracyon",
                "aoa3:graw",
                "aoa3:gyro",
                "aoa3:hive_king",
                "aoa3:kajaros",
                "aoa3:miskel",
                "aoa3:harkos",
                "aoa3:raxxan",
                "aoa3:okazor",
                "aoa3:king_bambambam",
                "aoa3:king_shroomus",
                "aoa3:kror",
                "aoa3:mechbot",
                "aoa3:nethengeic_wither",
                "aoa3:red_guardian",
                "aoa3:blue_guardian",
                "aoa3:green_guardian",
                "aoa3:yellow_guardian",
                "aoa3:rock_rider",
                "aoa3:shadowlord",
                "aoa3:tyrosaur",
                "aoa3:vinecorne",
                "aoa3:visualent",
                "aoa3:voxxulon",
                "aoa3:bane",
                "aoa3:elusive",
                "gaiadimension:malachite_drone",
                "gaiadimension:malachite_guard",
                "blue_skies:alchemist",
                "blue_skies:arachnarch",
                "blue_skies:starlit_crusher",
                "blue_skies:summoner",
                "stalwart_dungeons:awful_ghast",
                "stalwart_dungeons:nether_keeper",
                "stalwart_dungeons:shelterer_without_armor",
                "dungeonsmod:extrapart",
                "dungeonsmod:king",
                "dungeonsmod:deserted",
                "dungeonsmod:crawler",
                "dungeonsmod:ironslime",
                "dungeonsmod:kraken",
                "dungeonsmod:voidmaster",
                "dungeonsmod:lordskeleton",
                "dungeonsmod:winterhunter",
                "dungeonsmod:sun",
                "forestcraft:beequeen",
                "forestcraft:iguana_king",
                "forestcraft:cosmic_fiend",
                "forestcraft:nether_scourge",
                "cataclysm:ender_golem",
                "cataclysm:ender_guardian",
                "cataclysm:ignis",
                "cataclysm:ignited_revenant",
                "cataclysm:netherite_monstrosity",
                "iceandfire:fire_dragon",
                "iceandfire:ice_dragon",
                "iceandfire:lightning_dragon",
                "iceandfire:dragon_multipart",
                "witherstormmod:wither_storm",
                "witherstormmod:wither_storm_head",
                "witherstormmod:wither_storm_segment"
        );

        Predicate<Object> validator = o -> o instanceof String && ((String) o).contains(":");

        ForgeConfigSpec.Builder ENTITY_LIST_BUILDER = new ForgeConfigSpec.Builder();
        ENTITY_LIST_BUILDER.comment("Magnesium/Rubidium Extra Entity List Config").push("general");
        ENTITY_LIST = ENTITY_LIST_BUILDER
                .defineList("entities which should always be rendered", entity_list, validator);
        ENTITY_LIST_BUILDER.pop();
        ENTITY_LIST_CONFIG = ENTITY_LIST_BUILDER.build();
    }
}
