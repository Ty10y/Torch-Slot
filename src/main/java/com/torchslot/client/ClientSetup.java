package com.torchslot.client;

import com.torchslot.LightSlot;
import com.torchslot.TorchSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

/**
 * Client-only registrations, physical client only. Mod-bus and game-bus events are routed
 * automatically by event type.
 */
@EventBusSubscriber(modid = TorchSlot.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LightSlot.clientVisible = LightSlotUi::isSlotVisible;
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        DynamicLights.tick();
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        DynamicLights.clear();
    }

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        LightSlotUi.onScreenInit(event.getScreen(), event::addListener);
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Pre event) {
        LightSlotUi.onScreenRender(event.getScreen());
    }

    @SubscribeEvent
    public static void onScreenForeground(ScreenEvent.Render.Foreground event) {
        LightSlotUi.drawEmptySlotTooltip(event.getScreen(), event.getGuiGraphics(), event.getMouseX(), event.getMouseY());
    }

    @SubscribeEvent
    public static void onMouseReleased(ScreenEvent.MouseButtonReleased.Pre event) {
        if (LightSlotUi.consumeToggleRelease(event.getScreen())) {
            event.setCanceled(true);
        }
    }
}
