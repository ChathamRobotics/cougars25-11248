package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.MotorConstants.SLIDE_MOTOR_TICKS_PER_IN;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Op mode for testing the connection and direction of motors
 * Use dpad up and down to adjust power and left and right to select a motor
 * Move the left joystick up and down to run the motor
 * NOTE: This does not respect the motor directions set in OurRobot, all motors are set to run forwards
 */
@TeleOp(name="Motor Test", group="Testing")
public class MotorTest extends LinearOpMode
{
    private double power = 0.5;
    private double lastPowerChangeTime;
    private final ElapsedTime runtime = new ElapsedTime();
    private int motorIndex = 0;
    private boolean leftDownLastFrame = false;
    private boolean rightDownLastFrame = false;

    @Override
    public void runOpMode()
    {
        // Get all the motors and do basic setup on each one
        List<DcMotor> motors = hardwareMap.getAll(DcMotor.class);
        List<String> motorNames = new ArrayList<>();
        for (DcMotor motor : motors)
        {
            motorNames.add(hardwareMap.getNamesOf(motor).iterator().next());
            motor.setDirection(Direction.FORWARD);
            motor.setMode(RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(RunMode.RUN_USING_ENCODER);
        }
        telemetry.addData(">", "Robot Ready");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while(opModeIsActive())
        {
            // Adjust Power
            if (runtime.time() > lastPowerChangeTime + 0.5)
            {
                if (gamepad1.dpad_down)
                {
                    power = Math.max(0, power - 0.1);
                    lastPowerChangeTime = runtime.time();
                }
                else if (gamepad1.dpad_up)
                {
                    power = Math.min(1, power + 0.1);
                    lastPowerChangeTime = runtime.time();
                }
            }

            // Previous motor
            if (gamepad1.dpad_left)
            {
                if (!leftDownLastFrame)
                {
                    motorIndex = (motorIndex + motors.size() - 1) % motors.size();
                }
                leftDownLastFrame = true;
            }
            else
            {
                leftDownLastFrame = false;
            }

            // Next motor
            if (gamepad1.dpad_right)
            {
                if (!rightDownLastFrame)
                {
                    motorIndex = (motorIndex + 1) % motors.size();
                }
                rightDownLastFrame = true;
            }
            else
            {
                rightDownLastFrame = false;
            }

            // Reset Encoder
            if (gamepad1.a) {
                motors.get(motorIndex).setMode(RunMode.STOP_AND_RESET_ENCODER);
                motors.get(motorIndex).setMode(RunMode.RUN_USING_ENCODER);
            }

            // Actually turn the selected motor
            DcMotor currMotor = motors.get(motorIndex);
            currMotor.setPower(-gamepad1.left_stick_y * power);

            // Display the current motor name, encoder position, and power
            telemetry.addData("Motor", motorNames.get(motorIndex));
            telemetry.addData("Motor Position", currMotor.getCurrentPosition());
            telemetry.addData("Slide Motor Position (in)", currMotor.getCurrentPosition() / (float) SLIDE_MOTOR_TICKS_PER_IN);
            telemetry.addData("Motor Power", currMotor.getPower());
            telemetry.addData("Power", power);
            telemetry.update();
        }
    }
}