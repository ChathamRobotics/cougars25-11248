package org.firstinspires.ftc.teamcode;

public class MotorConstants {
    public static final long MOTOR_TICKS_PER_SEC = 6000 * 28;
    public static final long MOTOR_TICKS_PER_REV = 28;
    public static final long SLIDE_MOTOR_TICKS_PER_IN = Math.round(MOTOR_TICKS_PER_REV * 18.9304029 / (Math.PI * 1.5));
    public static final long SLIDE_MOTOR_MAX = 4200;
    public static final long ARM_MOTOR_TICKS_PER_RAD = Math.round((MOTOR_TICKS_PER_REV * 143.118798 * 2.7) / (2 * Math.PI));
    public static final long ARM_MOTOR_MAX = 3000;

    public static final int BASE_MOTOR_TICKS_PER_IN = (int) Math.round(MOTOR_TICKS_PER_REV * 10.4329 / (3.54330709 * Math.sqrt(2)));
}
