package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Op mode for testing the connection and direction of motors
 * Use dpad up and down to adjust power and left and right to select a motor
 * Move the left joystick up and down to run the motor
 * NOTE: This does not respect the motor directions set in OurRobot, all motors are set to run forwards
 */
@TeleOp(name="Servo Test", group="Testing")
public class ServoTest extends LinearOpMode
{
    private double power = 0.01;
    private double lastPowerChangeTime;
    private final ElapsedTime runtime = new ElapsedTime();
    private int servoIndex = 0;
    private boolean leftDownLastFrame = false;
    private boolean rightDownLastFrame = false;
    private float servoPos = 0.5f;
    private double lastMovement;

    @Override
    public void runOpMode()
    {
        // Get all the servos and do basic setup on each one
        List<Servo> servos = hardwareMap.getAll(Servo.class);
        List<String> servoNames = new ArrayList<>();
        for (Servo servo : servos)
        {
            servoNames.add(hardwareMap.getNamesOf(servo).iterator().next());
            servo.setDirection(Servo.Direction.FORWARD);
        }
        telemetry.addData(">", "Robot Ready");
        telemetry.addData("Servos:", servos.size());
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
                    power = Math.max(0, power - 0.01);
                    lastPowerChangeTime = runtime.time();
                }
                else if (gamepad1.dpad_up)
                {
                    power = Math.min(1, power + 0.01);
                    lastPowerChangeTime = runtime.time();
                }
            }

            // Previous motor
            if (gamepad1.dpad_left)
            {
                if (!leftDownLastFrame)
                {
                    servoIndex = (servoIndex + servos.size() - 1) % servos.size();
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
                    servoIndex = (servoIndex + 1) % servos.size();
                    servoPos = 0.5f;
                }
                rightDownLastFrame = true;
            }
            else
            {
                rightDownLastFrame = false;
            }

            // Actually turn the selected motor
            Servo currServo = servos.get(servoIndex);
            //&& lastMovement + 0.5 < runtime.time()
            if (gamepad1.left_bumper) {
                servoPos -= power;
                currServo.setPosition(servoPos);
                lastMovement = runtime.time();
            }
            //&& lastMovement + 0.5 < runtime.time()
            if (gamepad1.right_bumper) {
                servoPos += power;
                currServo.setPosition(servoPos);
                lastMovement = runtime.time();
            }
            if (servoPos > 0.8) servoPos = 0.8f;
            if (servoPos < 0.2) servoPos = 0.2f;


            // Display the current motor name, encoder position, and power
            telemetry.addData("Servo", servoNames.get(servoIndex));
            telemetry.addData("Servo Position", servoPos);
            telemetry.addData("Power", power);
            telemetry.update();
        }
    }
}