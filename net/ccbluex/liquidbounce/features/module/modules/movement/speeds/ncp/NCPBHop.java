// 
// Decompiled by Procyon v0.5.36
// 

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.client.entity.EntityPlayerSP;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.event.events.MoveEvent;
import net.minecraft.entity.Entity;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;

public class NCPBHop extends SpeedMode
{
    private int level;
    private double moveSpeed;
    private double lastDist;
    private int timerDelay;
    
    public NCPBHop() {
        super("NCPBHop");
        this.level = 1;
        this.moveSpeed = 0.2873;
    }
    
    @Override
    public void onEnable() {
        NCPBHop.mc.field_71428_T.field_74278_d = 1.0f;
        this.level = ((NCPBHop.mc.field_71441_e.func_72945_a((Entity)NCPBHop.mc.field_71439_g, NCPBHop.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, NCPBHop.mc.field_71439_g.field_70181_x, 0.0)).size() > 0 || NCPBHop.mc.field_71439_g.field_70124_G) ? 1 : 4);
    }
    
    @Override
    public void onDisable() {
        NCPBHop.mc.field_71428_T.field_74278_d = 1.0f;
        this.moveSpeed = this.getBaseMoveSpeed();
        this.level = 0;
    }
    
    @Override
    public void onMotion() {
        final double xDist = NCPBHop.mc.field_71439_g.field_70165_t - NCPBHop.mc.field_71439_g.field_70169_q;
        final double zDist = NCPBHop.mc.field_71439_g.field_70161_v - NCPBHop.mc.field_71439_g.field_70166_s;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    @Override
    public void onUpdate() {
    }
    
    @Override
    public void onMove(final MoveEvent event) {
        ++this.timerDelay;
        this.timerDelay %= 5;
        if (this.timerDelay != 0) {
            NCPBHop.mc.field_71428_T.field_74278_d = 1.0f;
        }
        else {
            if (MovementUtils.isMoving()) {
                NCPBHop.mc.field_71428_T.field_74278_d = 32767.0f;
            }
            if (MovementUtils.isMoving()) {
                NCPBHop.mc.field_71428_T.field_74278_d = 1.3f;
                final EntityPlayerSP field_71439_g = NCPBHop.mc.field_71439_g;
                field_71439_g.field_70159_w *= 1.0199999809265137;
                final EntityPlayerSP field_71439_g2 = NCPBHop.mc.field_71439_g;
                field_71439_g2.field_70179_y *= 1.0199999809265137;
            }
        }
        if (NCPBHop.mc.field_71439_g.field_70122_E && MovementUtils.isMoving()) {
            this.level = 2;
        }
        if (this.round(NCPBHop.mc.field_71439_g.field_70163_u - (int)NCPBHop.mc.field_71439_g.field_70163_u) == this.round(0.138)) {
            final EntityPlayerSP field_71439_g3;
            final EntityPlayerSP thePlayer = field_71439_g3 = NCPBHop.mc.field_71439_g;
            field_71439_g3.field_70181_x -= 0.08;
            event.y -= 0.09316090325960147;
            final EntityPlayerSP entityPlayerSP = thePlayer;
            entityPlayerSP.field_70163_u -= 0.09316090325960147;
        }
        if (this.level == 1 && (NCPBHop.mc.field_71439_g.field_70701_bs != 0.0f || NCPBHop.mc.field_71439_g.field_70702_br != 0.0f)) {
            this.level = 2;
            this.moveSpeed = 1.35 * this.getBaseMoveSpeed() - 0.01;
        }
        else if (this.level == 2) {
            this.level = 3;
            NCPBHop.mc.field_71439_g.field_70181_x = 0.399399995803833;
            event.y = 0.399399995803833;
            this.moveSpeed *= 2.149;
        }
        else if (this.level == 3) {
            this.level = 4;
            final double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
            this.moveSpeed = this.lastDist - difference;
        }
        else {
            if (NCPBHop.mc.field_71441_e.func_72945_a((Entity)NCPBHop.mc.field_71439_g, NCPBHop.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, NCPBHop.mc.field_71439_g.field_70181_x, 0.0)).size() > 0 || NCPBHop.mc.field_71439_g.field_70124_G) {
                this.level = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
        }
        this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
        final MovementInput movementInput = NCPBHop.mc.field_71439_g.field_71158_b;
        float forward = movementInput.field_78900_b;
        float strafe = movementInput.field_78902_a;
        float yaw = NCPBHop.mc.field_71439_g.field_70177_z;
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
        }
        else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
                strafe = 0.0f;
            }
            else if (strafe <= -1.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double mx2 = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz2 = Math.sin(Math.toRadians(yaw + 90.0f));
        event.x = forward * this.moveSpeed * mx2 + strafe * this.moveSpeed * mz2;
        event.z = forward * this.moveSpeed * mz2 - strafe * this.moveSpeed * mx2;
        NCPBHop.mc.field_71439_g.field_70138_W = 0.6f;
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
        }
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (NCPBHop.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
            baseSpeed *= 1.0 + 0.2 * (NCPBHop.mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1);
        }
        return baseSpeed;
    }
    
    private double round(final double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
