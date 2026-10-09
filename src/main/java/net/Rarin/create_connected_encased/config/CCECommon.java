package net.Rarin.create_connected_encased.config;

import net.createmod.catnip.config.ConfigBase;

public class CCECommon extends ConfigBase {

    public ConfigBool encasedBlocksUsesOwnKeys = b(false,"encasedBlocksUsesOwnKeys", Comments.encasedBlocksUsesOwnKeys);
    public ConfigBool casingBlockSwappable = b(true,"casingBlockSwappable", Comments.casingBlockSwappable);
    public ConfigBool shouldWoodenShaftBreak = b(false,"shouldWoodenShaftBreak", Comments.shouldWoodenShaftBreak);
    public ConfigInt maxSpeedWoodenShaft = i(32,2, 256,"maxSpeedWoodenShaft", Comments.maxSpeedWoodenShaft);

    @Override
    public String getName() {
        return "common";
    }

    private static class Comments {
        static String casingBlockSwappable = "Can swap casing between block (Brake, Clutch, Gearshift, Gearbox, etc...)";
        static String encasedBlocksUsesOwnKeys = "Does the encased variants of the blocks use their own stress keys or the base block stress keys (default false, use base block stress keys)";
        static String shouldWoodenShaftBreak = "Should Wooden Shaft break if the speed is too high";
        static String maxSpeedWoodenShaft = "The max speed wooden shafts can endure";
    }
}
