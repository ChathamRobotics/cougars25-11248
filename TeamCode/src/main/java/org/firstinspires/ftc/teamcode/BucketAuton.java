package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.MotorConstants.ARM_MOTOR_MAX;
import static org.firstinspires.ftc.teamcode.MotorConstants.ARM_MOTOR_TICKS_PER_RAD;
import static org.firstinspires.ftc.teamcode.MotorConstants.BASE_MOTOR_TICKS_PER_IN;
import static org.firstinspires.ftc.teamcode.MotorConstants.SLIDE_MOTOR_MAX;
import static org.firstinspires.ftc.teamcode.MotorConstants.SLIDE_MOTOR_TICKS_PER_IN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Bucket Auton")
public class BucketAuton extends LinearOpMode {
    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;
    float speed = .7f;

    DcMotorEx slide;
    int slideTarget = 0;
    DcMotorEx arm;
    int armTarget = 0;

    Servo clawGrab;







    @Override
    public void runOpMode() throws InterruptedException {
        // Init code

        frontLeft = hardwareMap.get(DcMotorEx.class, "leftFront");
        frontRight = hardwareMap.get(DcMotorEx.class, "rightFront");
        backLeft = hardwareMap.get(DcMotorEx.class, "leftBack");
        backRight = hardwareMap.get(DcMotorEx.class, "rightBack");
        //clawPivotRight = hardwareMap.get(Servo.class, "clawPivotRight");
        //clawPivotLeft = hardwareMap.get(Servo.class, "clawPivotLeft");
        clawGrab = hardwareMap.get(Servo.class, "clawGrab");
        slide = hardwareMap.get(DcMotorEx.class, "slide");
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        frontRight.setPower(.3);
        frontLeft.setPower(.3);
        backRight.setPower(.3);
        backLeft.setPower(.3);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(0);
        frontLeft.setTargetPosition(0);
        backRight.setTargetPosition(0);
        backLeft.setTargetPosition(0);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slide.setTargetPosition(0);
        slide.setPower(.6);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);

        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setTargetPosition(1420);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        while (arm.getCurrentPosition() < 1400){
            arm.setPower(0.6);
            telemetry.addData("Arm", arm.getCurrentPosition());
            telemetry.addData("Slide", slide.getCurrentPosition());
            telemetry.update();
        }

        telemetry.addData("Arm", arm.getCurrentPosition());
        telemetry.addData("Slide", slide.getCurrentPosition());
        telemetry.update();

        //clawGrab.scaleRange(.3, .95);
        clawGrab.setPosition(.4);

        waitForStart();

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 2) {
            frontRight.setTargetPosition(4 * BASE_MOTOR_TICKS_PER_IN);
            frontLeft.setTargetPosition(4 * BASE_MOTOR_TICKS_PER_IN);
            backRight.setTargetPosition(4 * BASE_MOTOR_TICKS_PER_IN);
            backLeft.setTargetPosition(4 * BASE_MOTOR_TICKS_PER_IN);
            telemetry.update();
        }

        resetRuntime();
        while (opModeIsActive() && getRuntime() < .5) {
            frontRight.setTargetPosition(1 * BASE_MOTOR_TICKS_PER_IN - 30);
            frontLeft.setTargetPosition(1 * BASE_MOTOR_TICKS_PER_IN + 30);
            backRight.setTargetPosition(1 * BASE_MOTOR_TICKS_PER_IN + 30);
            backLeft.setTargetPosition(1 * BASE_MOTOR_TICKS_PER_IN - 30);
            telemetry.update();
        }

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 1.5) {
            slide.setTargetPosition((int) (8 * SLIDE_MOTOR_TICKS_PER_IN));
            arm.setTargetPosition(160);
            clawGrab.setPosition(.7);
            telemetry.update();
        }

        clawGrab.setPosition(.95);

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 0.5) {
            telemetry.update();
        }

        while (opModeIsActive() && getRuntime() < 1) {
            slide.setTargetPosition(0);
            arm.setTargetPosition(2900);
            telemetry.update();
        }

        resetRuntime();
        while (opModeIsActive() && getRuntime() < 1) {
            slide.setTargetPosition(2000);
            telemetry.update();
        }

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 2.5) {
            slide.setTargetPosition(2750);
            frontRight.setTargetPosition(1100 + 150);
            frontLeft.setTargetPosition(75 - 150);
            backRight.setTargetPosition(1358 - 150);
            backLeft.setTargetPosition(-162 + 150);
            telemetry.update();
        }

        resetRuntime();

        while (opModeIsActive() && getRuntime() < .5) {
            frontRight.setTargetPosition(1100 + 300);
            frontLeft.setTargetPosition(75);
            backRight.setTargetPosition(1358);
            backLeft.setTargetPosition(-162 + 300);
            telemetry.update();
        }

        clawGrab.setPosition(0.5);

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 0.5) {
            frontRight.setTargetPosition(1100 -200);
            frontLeft.setTargetPosition(75 -200);
            backRight.setTargetPosition(1358 -200);
            backLeft.setTargetPosition(-162 -200);
            telemetry.update();
        }

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 2) {
            slide.setTargetPosition(0);
            telemetry.update();
        }

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 1) {
            arm.setTargetPosition(0);
            telemetry.update();
        }

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 1) {
            frontRight.setTargetPosition(1100 -1000 - 200);
            frontLeft.setTargetPosition(75 -1000 + 200);
            backRight.setTargetPosition(1358 -1000 + 200);
            backLeft.setTargetPosition(-162 -1000 - 200);
            telemetry.update();
        }


        resetRuntime();

        while (opModeIsActive() && getRuntime() < 1) {
            arm.setTargetPosition(2200);
            slide.setTargetPosition(600);
            telemetry.update();
        }

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 1) {
            frontRight.setTargetPosition(1100 -1000 - 200 + 11 * BASE_MOTOR_TICKS_PER_IN);
            frontLeft.setTargetPosition(75 -1000 + 200 - 11 * BASE_MOTOR_TICKS_PER_IN);
            backRight.setTargetPosition(1358 -1000 + 200 + 11 * BASE_MOTOR_TICKS_PER_IN);
            backLeft.setTargetPosition(-162 -1000 - 200 - 11 * BASE_MOTOR_TICKS_PER_IN);
            telemetry.update();
        }

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 2) {
            frontRight.setTargetPosition(1100 -600 - 200 + 11 * BASE_MOTOR_TICKS_PER_IN);
            frontLeft.setTargetPosition(75 -600 + 200 - 11 * BASE_MOTOR_TICKS_PER_IN);
            backRight.setTargetPosition(1358 -600 + 200 + 11 * BASE_MOTOR_TICKS_PER_IN);
            backLeft.setTargetPosition(-162 -600 - 200 - 11 * BASE_MOTOR_TICKS_PER_IN);
            telemetry.update();
        }
    }
}
