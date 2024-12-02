package org.firstinspires.ftc.teamcode;

public class MotorConstants {
    public static final long MOTOR_TICKS_PER_SEC = 6000 * 28;
    public static final long MOTOR_TICKS_PER_REV = 28;
    public static final long SLIDE_MOTOR_TICKS_PER_IN = Math.round(MOTOR_TICKS_PER_REV * 18.9304029 / (Math.PI * 1.5));
    public static final long SLIDE_MOTOR_MAX = 4200;
    public static final long ARM_MOTOR_TICKS_PER_RAD = Math.round((MOTOR_TICKS_PER_REV * 79.2523975 * 5.4) / (2 * Math.PI));
    public static final long ARM_MOTOR_MAX = 3200;
}
