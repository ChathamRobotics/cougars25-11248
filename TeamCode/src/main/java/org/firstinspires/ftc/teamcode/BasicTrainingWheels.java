package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.MotorConstants.ARM_MOTOR_MAX;
import static org.firstinspires.ftc.teamcode.MotorConstants.ARM_MOTOR_TICKS_PER_RAD;
import static org.firstinspires.ftc.teamcode.MotorConstants.SLIDE_MOTOR_MAX;
import static org.firstinspires.ftc.teamcode.MotorConstants.SLIDE_MOTOR_TICKS_PER_IN;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp (name = "Basic Training Wheels")
public class BasicTrainingWheels extends LinearOpMode {
    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;
    float speed = .7f;


    @Override
    public void runOpMode() throws InterruptedException {
        // Init code

        frontLeft = hardwareMap.get(DcMotorEx.class, "leftFront");
        frontRight = hardwareMap.get(DcMotorEx.class, "rightFront");
        backLeft = hardwareMap.get(DcMotorEx.class, "leftBack");
        backRight = hardwareMap.get(DcMotorEx.class, "rightBack");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();



        // start code
        float servoPos = 0.3f;
        float lastMovement = 0;

        while (opModeIsActive()) {

            float powerFrontRight = 0;
            float powerFrontLeft = 0;
            float powerBackRight = 0;
            float powerBackLeft = 0;


            powerFrontRight -= gamepad1.left_stick_y;
            powerBackRight -= gamepad1.left_stick_y;
            powerFrontLeft -= gamepad1.left_stick_y;
            powerBackLeft -= gamepad1.left_stick_y;

            powerFrontRight += -(gamepad1.right_trigger - gamepad1.left_trigger);
            powerBackRight += (gamepad1.right_trigger - gamepad1.left_trigger);
            powerFrontLeft += (gamepad1.right_trigger - gamepad1.left_trigger);
            powerBackLeft += -(gamepad1.right_trigger - gamepad1.left_trigger);

            powerFrontRight += -gamepad1.right_stick_x * .75f;
            powerBackRight += -gamepad1.right_stick_x * .75f;
            powerFrontLeft += gamepad1.right_stick_x * .75f;
            powerBackLeft += gamepad1.right_stick_x * .75f;

            float maxe = Math.max(Math.abs(powerFrontRight), Math.abs(powerFrontLeft));
            maxe = Math.max(maxe, Math.abs(powerBackRight));
            maxe = Math.max(maxe, Math.abs(powerBackLeft));
            if (maxe > 1){
                powerFrontRight /= maxe;
                powerFrontLeft /= maxe;
                powerBackRight /= maxe;
                powerBackLeft /= maxe;
            }

            if (gamepad1.right_bumper) {
                speed = .4f;
            }
            else if (gamepad1.left_bumper) {
                speed = .1f;
            } else {
                speed = .2f;
            }

            powerFrontRight *= speed;
            powerFrontLeft *= speed;
            powerBackRight *= speed;
            powerBackLeft *= speed;

            frontRight.setPower(powerFrontRight);
            frontLeft.setPower(powerFrontLeft);
            backRight.setPower(powerBackRight);
            backLeft.setPower(powerBackLeft);

            telemetry.addData("frontRight Power", powerFrontRight);
            telemetry.addData("frontLeft Power", powerFrontLeft);
            telemetry.addData("backRight Power", powerBackRight);
            telemetry.addData("backLeft Power", powerBackLeft);

            telemetry.addData("frontRight Power (real)", frontRight.getPower());
            telemetry.addData("frontLeft Power (real)", frontLeft.getPower());
            telemetry.addData("backRight Power (real)", backRight.getPower());
            telemetry.addData("backLeft Power (real)", backLeft.getPower());

            telemetry.update();
        }
    }
}
