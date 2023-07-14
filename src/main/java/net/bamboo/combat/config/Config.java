package net.bamboo.combat.config;

public class Config {

    public SpearProperties bambooSpear = new SpearProperties();
    public SpearProperties stoneBambooSpear = new SpearProperties();
    public SpearProperties ironBambooSpear = new SpearProperties();
    public SpearProperties copperBambooSpear = new SpearProperties();
    public SpearProperties goldenBambooSpear = new SpearProperties();
    public SpearProperties diamondBambooSpear = new SpearProperties();
    public SpearProperties netheriteBambooSpear = new SpearProperties();
    
    public Config() {
        bambooSpear.canCriticalThrow = true;
        bambooSpear.canPierce = true;
        bambooSpear.durability = 100;
        bambooSpear.attackDamage = 5;
        bambooSpear.attackSpeed = 1.6F;
        bambooSpear.throwDistance = 1.2F;
        bambooSpear.dragInWater = 0.9F;
        bambooSpear.throwDelay = 3;
        bambooSpear.pierceLevel = 0;
        bambooSpear.burnTicks = 0;
        bambooSpear.durabilityDecreaseAfterThrown = 2;
        bambooSpear.throwDamageDecreaseAfterPierce = 1;
        
        stoneBambooSpear.canCriticalThrow = true;
        stoneBambooSpear.canPierce = true;
        stoneBambooSpear.durability = 200;
        stoneBambooSpear.attackDamage = 6;
        stoneBambooSpear.attackSpeed = 1.4F;
        stoneBambooSpear.throwDistance = 1.5F;
        stoneBambooSpear.dragInWater = 0.92F;
        stoneBambooSpear.throwDelay = 6;
        stoneBambooSpear.pierceLevel = 1;
        stoneBambooSpear.burnTicks = 5;
        stoneBambooSpear.durabilityDecreaseAfterThrown = 2;
        stoneBambooSpear.throwDamageDecreaseAfterPierce = 1;

        ironBambooSpear.canCriticalThrow = true;
        ironBambooSpear.canPierce = true;
        ironBambooSpear.durability = 400;
        ironBambooSpear.attackDamage = 8;
        ironBambooSpear.attackSpeed = 1.1F;
        ironBambooSpear.throwDistance = 2.0F;
        ironBambooSpear.dragInWater = 0.95F;
        ironBambooSpear.throwDelay = 11;
        ironBambooSpear.pierceLevel = 2;
        ironBambooSpear.burnTicks = 20;
        ironBambooSpear.durabilityDecreaseAfterThrown = 2;
        ironBambooSpear.throwDamageDecreaseAfterPierce = 1;

        copperBambooSpear.canCriticalThrow = true;
        copperBambooSpear.canPierce = true;
        copperBambooSpear.durability = 350;
        copperBambooSpear.attackDamage = 7;
        copperBambooSpear.attackSpeed = 1.4F;
        copperBambooSpear.throwDistance = 1.7F;
        copperBambooSpear.dragInWater = 0.94F;
        copperBambooSpear.throwDelay = 8;
        copperBambooSpear.pierceLevel = 2;
        copperBambooSpear.burnTicks = 15;
        copperBambooSpear.durabilityDecreaseAfterThrown = 2;
        copperBambooSpear.throwDamageDecreaseAfterPierce = 1;

        goldenBambooSpear.canCriticalThrow = true;
        goldenBambooSpear.canPierce = true;
        goldenBambooSpear.durability = 100;
        goldenBambooSpear.attackDamage = 7;
        goldenBambooSpear.attackSpeed = 1.2F;
        goldenBambooSpear.throwDistance = 2.4F;
        goldenBambooSpear.dragInWater = 0.98F;
        goldenBambooSpear.throwDelay = 17;
        goldenBambooSpear.pierceLevel = 3;
        goldenBambooSpear.burnTicks = 5;
        goldenBambooSpear.durabilityDecreaseAfterThrown = 2;
        goldenBambooSpear.throwDamageDecreaseAfterPierce = 1;

        diamondBambooSpear.canCriticalThrow = true;
        diamondBambooSpear.canPierce = true;
        diamondBambooSpear.durability = 1000;
        diamondBambooSpear.attackDamage = 8;
        diamondBambooSpear.attackSpeed = 1.3F;
        diamondBambooSpear.throwDistance = 2.1F;
        diamondBambooSpear.dragInWater = 0.96F;
        diamondBambooSpear.throwDelay = 11;
        diamondBambooSpear.pierceLevel = 4;
        diamondBambooSpear.burnTicks = 40;
        diamondBambooSpear.durabilityDecreaseAfterThrown = 2;
        diamondBambooSpear.throwDamageDecreaseAfterPierce = 1;

        netheriteBambooSpear.canCriticalThrow = true;
        netheriteBambooSpear.canPierce = true;
        netheriteBambooSpear.durability = 1500;
        netheriteBambooSpear.attackDamage = 9;
        netheriteBambooSpear.attackSpeed = 1.1F;
        netheriteBambooSpear.throwDistance = 2.7F;
        netheriteBambooSpear.dragInWater = 0.98F;
        netheriteBambooSpear.throwDelay = 13;
        netheriteBambooSpear.pierceLevel = 5;
        netheriteBambooSpear.burnTicks = -1;
        netheriteBambooSpear.durabilityDecreaseAfterThrown = 2;
        netheriteBambooSpear.throwDamageDecreaseAfterPierce = 1;
    }
    
}