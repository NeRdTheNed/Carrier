package me.steven.carrier.impl;

import me.steven.carrier.api.Carriable;
import me.steven.carrier.api.CarriableRegistry;
import me.steven.carrier.api.EntityCarriable;
import me.steven.carrier.api.Holder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.SheepEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class CarriableSheep extends EntityCarriable<SheepEntity> {

    public static final Identifier TYPE = new Identifier("carrier", "sheep");
    public static final Carriable INSTANCE = CarriableRegistry.INSTANCE.register(TYPE, new CarriableSheep());
    private static SheepEntity dummySheep;
    private static SheepEntityRenderer sheepRenderer;

    private CarriableSheep() {
        super(TYPE, EntityType.SHEEP);
    }

    @Override
    public SheepEntity getEntity() {
        if (dummySheep == null)
            dummySheep = new SheepEntity(EntityType.SHEEP, MinecraftClient.getInstance().world);
        return dummySheep;
    }

    @Override
    public EntityRenderer<SheepEntity> getEntityRenderer() {
        if (sheepRenderer == null)
            sheepRenderer = new SheepEntityRenderer(MinecraftClient.getInstance().getEntityRenderDispatcher());
        return sheepRenderer;
    }


    @Override
    public void render(@NotNull Holder holder, @NotNull MatrixStack matrices, @NotNull VertexConsumerProvider vcp, float tickDelta, int light) {
        PlayerEntity player = (PlayerEntity) holder;
        updateEntity(holder.getHolding());
        matrices.push();
        matrices.scale(0.6f, 0.6f, 0.6f);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-player.bodyYaw + 90));
        matrices.translate(-0.6, 0.8, -0.1);
        getEntityRenderer().render(getEntity(), 0, tickDelta, matrices, vcp, light);
        matrices.pop();
    }
}