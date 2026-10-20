package net.Rarin.create_connected_encased.config;

import net.createmod.catnip.config.ConfigBase;

public class CCECommon extends ConfigBase {

    public ConfigBool encasedBlocksUsesOwnKeys = b(false,"encasedBlocksUsesOwnKeys", Comments.encasedBlocksUsesOwnKeys);
    public ConfigBool casingBlockSwappable = b(true,"casingBlockSwappable", Comments.casingBlockSwappable);
    public ConfigBool shouldWoodenShearPinBreak = b(false,"shouldWoodenShearPinBreak", Comments.shouldWoodenShearPinBreak);
    public ConfigInt maxSpeedWoodenShearPin = i(32,2, 256,"maxSpeedWoodenShearPin", Comments.maxSpeedWoodenShearPin);

    @Override
    public String getName() {
        return "common";
    }

    private static class Comments {
        static String casingBlockSwappable = "Can swap casing between block (Brake, Clutch, Gearshift, Gearbox, etc...)";
        static String encasedBlocksUsesOwnKeys = "Does the encased variants of the blocks use their own stress keys or the base block stress keys (default false, use base block stress keys)";
        static String shouldWoodenShearPinBreak = "Should Wooden Shear Pins break if the speed is too high";
        static String maxSpeedWoodenShearPin = "The max speed wooden Shear Pins can endure";
    }
}
