package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.MotorConstants.ARM_MOTOR_MAX;
import static org.firstinspires.ftc.teamcode.MotorConstants.ARM_MOTOR_TICKS_PER_RAD;
import static org.firstinspires.ftc.teamcode.MotorConstants.MOTOR_TICKS_PER_SEC;
import static org.firstinspires.ftc.teamcode.MotorConstants.SLIDE_MOTOR_MAX;
import static org.firstinspires.ftc.teamcode.MotorConstants.SLIDE_MOTOR_TICKS_PER_IN;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.Math;


@TeleOp (name = "XDrive")
public class XDriveTesting extends LinearOpMode {
    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;

    DcMotorEx slide;
    DcMotorEx arm;

    Servo clawPivotRight;
    Servo clawPivotLeft;
    Servo clawGrab;

    boolean positionUp = true;
    boolean aPressed = false;
    boolean clawOpen = true;
    boolean bPressed = false;

    float armTarget = 0;





    @Override
    public void runOpMode() throws InterruptedException {
        // Init code

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        clawPivotRight = hardwareMap.get(Servo.class, "clawPivotRight");
        clawPivotLeft = hardwareMap.get(Servo.class, "clawPivotLeft");
        clawGrab = hardwareMap.get(Servo.class, "clawGrab");
        slide = hardwareMap.get(DcMotorEx.class, "slide");
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        clawPivotLeft.setDirection(Servo.Direction.REVERSE);

        arm.setVelocity(0);
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        clawGrab.scaleRange(.65, .83);


        waitForStart();

        // start code

        while (opModeIsActive()) {

            float powerFrontRight = 0;
            float powerFrontLeft = 0;
            float powerBackRight = 0;
            float powerBackLeft = 0;


            powerFrontRight -= gamepad1.left_stick_y;
            powerFrontLeft -= gamepad1.left_stick_y;
            powerBackRight -= gamepad1.left_stick_y;
            powerBackLeft -= gamepad1.left_stick_y;

            powerFrontRight += -gamepad1.left_stick_x;
            powerBackRight += gamepad1.left_stick_x;
            powerFrontLeft += gamepad1.left_stick_x;
            powerBackLeft += -gamepad1.left_stick_x;

            powerFrontRight += -gamepad1.right_stick_x;
            powerBackRight += -gamepad1.right_stick_x;
            powerFrontLeft += gamepad1.right_stick_x;
            powerFrontLeft += gamepad1.right_stick_x;

            armTarget -= gamepad2.right_stick_y * 10;
            arm.setTargetPosition((int) armTarget);

            double slideVel = (-gamepad2.left_stick_y * MOTOR_TICKS_PER_SEC);
            double armVel = (armTarget > arm.getCurrentPosition() ? 1 : -1);

            if (((double) slide.getCurrentPosition() / SLIDE_MOTOR_TICKS_PER_IN) * Math.cos((double) arm.getCurrentPosition() / ARM_MOTOR_TICKS_PER_RAD) > 40){
                if (gamepad2.left_stick_y < 0) {
                    slideVel = 0;
                }
                if (gamepad2.right_stick_y > 0) {
                    armVel = 0;
                    armTarget = arm.getCurrentPosition();
                }
            }
            if ((gamepad2.left_stick_y < 0 && slide.getCurrentPosition() >= SLIDE_MOTOR_MAX) || (gamepad2.left_stick_y > 0 && slide.getCurrentPosition() <= 0)) {
                slideVel = 0;
            }
            if ((gamepad2.right_stick_y < 0 && arm.getCurrentPosition() >= ARM_MOTOR_MAX) || (gamepad2.right_stick_y > 0 && arm.getCurrentPosition() <= 0)) {
                armVel = 0;
                armTarget = arm.getCurrentPosition();
            }

            slide.setVelocity(slideVel);
            arm.setVelocity(armVel);



            if (gamepad1.a){
                if (!aPressed){
                    aPressed = true;
                    clawOpen = !clawOpen;
                    if (clawOpen) {
                        clawGrab.setPosition(1);
                    } else {
                        clawGrab.setPosition(0);
                    }
                }
            } else {
                aPressed = false;
            }

            if (gamepad1.b){
                if (!bPressed){
                    bPressed = true;
                    positionUp = !positionUp;
                    if (positionUp) {
                        clawPivotLeft.setPosition(1);
                        clawPivotRight.setPosition(1);
                    } else {
                        clawPivotLeft.setPosition(0);
                        clawPivotRight.setPosition(0);
                    }
                }
            } else {
                bPressed = false;
            }



            float maxe = Math.max(Math.abs(powerFrontRight), Math.abs(powerFrontLeft));
            maxe = Math.max(maxe, Math.abs(powerBackRight));
            maxe = Math.max(maxe, Math.abs(powerBackLeft));
            if (maxe > 0.7){
                powerFrontRight /= maxe;
                powerFrontLeft /= maxe;
                powerBackRight /= maxe;
                powerBackLeft /= maxe;
            }
            frontRight.setVelocity(powerFrontRight * MOTOR_TICKS_PER_SEC);
            frontLeft.setVelocity(powerFrontLeft * MOTOR_TICKS_PER_SEC);
            backRight.setVelocity(powerBackRight * MOTOR_TICKS_PER_SEC);
            backLeft.setVelocity(powerBackLeft * MOTOR_TICKS_PER_SEC);
        }
    }
}
