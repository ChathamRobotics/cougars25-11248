package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.Math;


@TeleOp (name = "testing")
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
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        clawPivotLeft.setDirection(Servo.Direction.REVERSE);


        waitForStart();

        // start code

        while (opModeIsActive()) {

            float powerFrontRight = 0;
            float powerFrontLeft = 0;
            float powerBackRight = 0;
            float powerBackLeft = 0;
            int length = 0;
            float angle = 0;



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

            slide.setPower(gamepad2.left_stick_y);
            length += gamepad2.left_stick_y;
            arm.setPower(((gamepad2.dpad_up ? 1 : 0) - (gamepad1.dpad_down ? 1 : 0)) * .1);
            if (length * Math.cos(angle) > 40){
                slide.setPower(-1);
                length -= 1;
            }



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
            frontRight.setPower(powerFrontRight);
            frontLeft.setPower(powerFrontLeft);
            backRight.setPower(powerBackRight);
            backLeft.setPower(powerBackLeft);
        }
    }
}
