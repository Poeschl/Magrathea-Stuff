package de.poeschl.bukkit.magratheaStuff.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.util.ArrayList;
import java.util.List;

import static de.poeschl.bukkit.magratheaStuff.PojoUtilities.$PreventDispenseListener;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 26.06.2016.
 */
@PrepareForTest({BlockDispenseEvent.class})
public class PreventDispenseListenerTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Test
    public void onDispenseWater() {
        //WHEN
        List<Material> blockedMaterial = new ArrayList<>();
        blockedMaterial.add(Material.LAVA_BUCKET);
        PreventDispenseListener listenerToTest = $PreventDispenseListener().withBlockedMaterialsToDispense(blockedMaterial).build();

        BlockDispenseEvent testEvent = createEvent(Material.WATER_BUCKET, Material.DISPENSER);

        //THEN
        listenerToTest.onDispense(testEvent);

        //VERIFY
        verify(testEvent, never()).setCancelled(eq(true));
    }

    @Test
    public void onDispenseLava() {
        //WHEN
        List<Material> blockedMaterial = new ArrayList<>();
        blockedMaterial.add(Material.LAVA_BUCKET);
        PreventDispenseListener listenerToTest = $PreventDispenseListener().withBlockedMaterialsToDispense(blockedMaterial).build();

        BlockDispenseEvent testEvent = createEvent(Material.LAVA_BUCKET, Material.DISPENSER);

        //THEN
        listenerToTest.onDispense(testEvent);

        //VERIFY
        verify(testEvent).setCancelled(eq(true));
    }

    @Test
    public void onDispenseLavaBucketFromDropper() {
        //WHEN
        List<Material> blockedMaterial = new ArrayList<>();
        blockedMaterial.add(Material.LAVA_BUCKET);
        PreventDispenseListener listenerToTest = $PreventDispenseListener().withBlockedMaterialsToDispense(blockedMaterial).build();

        BlockDispenseEvent testEvent = createEvent(Material.LAVA_BUCKET, Material.DROPPER);

        //THEN
        listenerToTest.onDispense(testEvent);

        //VERIFY
        verify(testEvent, never()).setCancelled(eq(true));
    }

    //FIXME: Breaks because event.getBlock is final -.- No clue to proceed
    private BlockDispenseEvent createEvent(Material disposeMaterial, Material disposeBlock) {
        BlockDispenseEvent event = PowerMockito.mock(BlockDispenseEvent.class);
        ItemStack mockedItemStack = Mockito.mock(ItemStack.class);
        when(event.getItem()).thenReturn(mockedItemStack);
        when(mockedItemStack.getType()).thenReturn(disposeMaterial);
        Block dispenseBlock = Mockito.mock(Block.class);
        when(event.getBlock()).thenReturn(dispenseBlock);
        when(dispenseBlock.getType()).thenReturn(disposeBlock);

        return event;
    }

}