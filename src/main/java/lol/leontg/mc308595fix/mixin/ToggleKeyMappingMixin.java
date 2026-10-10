package lol.leontg.mc308595fix.mixin;

import net.minecraft.client.ToggleKeyMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BooleanSupplier;

/**
 * @author LeonTG
 */
@Mixin(ToggleKeyMapping.class)
public abstract class ToggleKeyMappingMixin {

    @Shadow
    @Final
    private BooleanSupplier needsToggle;

    @Shadow
    private boolean releasedByScreenWhenDown;

    @Shadow
    @Final
    private boolean shouldRestore;

    @Inject(method = "shouldRestoreStateOnScreenClosed", at = @At("HEAD"), cancellable = true)
    private void onShouldRestoreStateOnScreenClosed(CallbackInfoReturnable<Boolean> cir) {
        boolean shouldRestore = this.shouldRestore && this.needsToggle.getAsBoolean() && this.releasedByScreenWhenDown;
        this.releasedByScreenWhenDown = false;

        cir.setReturnValue(shouldRestore);
    }
}