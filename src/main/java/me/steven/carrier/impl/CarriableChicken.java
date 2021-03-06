package me.steven.carrier.impl;

import me.steven.carrier.api.EntityCarriable;
import me.steven.carrier.api.Holder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ChickenEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class CarriableChicken  extends EntityCarriable<ChickenEntity> {

    public static final Identifier TYPE = new Identifier("carrier", "chicken");
    @Environment(EnvType.CLIENT)
    private static ChickenEntity dummyChicken;
    @Environment(EnvType.CLIENT)
    private static ChickenEntityRenderer chickenRenderer;

    public CarriableChicken() {
        super(TYPE, EntityType.CHICKEN);
    }

    @NotNull
    @Override
    public EntityType<ChickenEntity> getParent() {
        return EntityType.CHICKEN;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ChickenEntity getEntity() {
        if (dummyChicken == null)
            dummyChicken = new ChickenEntity(EntityType.CHICKEN, MinecraftClient.getInstance().world);
        return dummyChicken;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public EntityRenderer<ChickenEntity> getEntityRenderer() {
        if (chickenRenderer == null)
            chickenRenderer = new ChickenEntityRenderer(MinecraftClient.getInstance().getEntityRenderDispatcher());
        return chickenRenderer;
    }


    @Override
    @Environment(EnvType.CLIENT)
    public void render(@NotNull PlayerEntity player, @NotNull Holder holder, @NotNull MatrixStack matrices, @NotNull VertexConsumerProvider vcp, float tickDelta, int light) {
        updateEntity(holder.getHolding());
        matrices.push();
        matrices.scale(0.9f, 0.9f, 0.9f);
        float yaw = MathHelper.lerpAngleDegrees(tickDelta, player.prevBodyYaw, player.bodyYaw);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-yaw + 90));
        matrices.translate(-0.4, 0.6, 0.00);
        getEntityRenderer().render(getEntity(), 0, tickDelta, matrices, vcp, light);
        matrices.pop();
    }
}