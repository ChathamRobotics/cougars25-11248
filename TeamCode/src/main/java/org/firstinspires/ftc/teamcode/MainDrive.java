package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.Math;


@TeleOp (name = "Main Drive")
public class MainDrive extends LinearOpMode {
    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;
    float speed = .7f;

    DcMotorEx slide;
    DcMotorEx arm;

    //Servo clawPivotRight;
    //Servo clawPivotLeft;
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
        //clawPivotRight = hardwareMap.get(Servo.class, "clawPivotRight");
        //clawPivotLeft = hardwareMap.get(Servo.class, "clawPivotLeft");
        clawGrab = hardwareMap.get(Servo.class, "clawGrab");
        slide = hardwareMap.get(DcMotorEx.class, "slide");
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        while (arm.getCurrentPosition() > -1560){
            arm.setPower(-0.05);
            telemetry.addData("Arm", arm.getCurrentPosition());
            telemetry.addData("Slide", slide.getCurrentPosition());
            telemetry.update();
        }
        arm.setPower(0);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //arm.setPower(-0.0007);
        telemetry.addData("Arm", arm.getCurrentPosition());
        telemetry.addData("Slide", slide.getCurrentPosition());
        telemetry.update();

        //clawGrab.scaleRange(.3, .95);
        clawGrab.setPosition(0.3);


        waitForStart();



        // start code
        double slideVel;
        double armVel;
        float servoPos = 0.3f;
        float lastMovement = 0;
        while (opModeIsActive()) {

            float powerFrontRight = 0;
            float powerFrontLeft = 0;
            float powerBackRight = 0;
            float powerBackLeft = 0;
            float power = 0;
            double extendlen;


            powerFrontRight -= gamepad1.left_stick_y;
            powerBackRight -= gamepad1.left_stick_y;
            powerFrontLeft -= gamepad1.left_stick_y;
            powerBackLeft -= gamepad1.left_stick_y;

            powerFrontRight += -(gamepad1.right_trigger - gamepad1.left_trigger);
            powerBackRight += (gamepad1.right_trigger - gamepad1.left_trigger);
            powerFrontLeft += (gamepad1.right_trigger - gamepad1.left_trigger);
            powerBackLeft += -(gamepad1.right_trigger - gamepad1.left_trigger);

            powerFrontRight += -gamepad1.right_stick_x;
            powerBackRight += -gamepad1.right_stick_x;
            powerFrontLeft += gamepad1.right_stick_x;
            powerBackLeft += gamepad1.right_stick_x;

            //armTarget -= gamepad2.right_stick_y * 10;
            //arm.setTargetPosition((int) armTarget);

            if (gamepad2.y){
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if (gamepad2.x){
                slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            slideVel = (gamepad2.left_stick_y*10);
            armVel = (gamepad2.right_stick_y*10);
            //if (armVel > -0.1 && gamepad2.left_bumper){
            //    armVel = -0.1;
            //}


            if (gamepad2.right_stick_y > -0.05 && gamepad2.right_stick_y < 0){
                armVel = -0.0014;
            }
            if (slideVel > 0 && slide.getCurrentPosition() > 0){
                slideVel = 0;
            }
            slide.setPower(slideVel);

            arm.setPower(armVel*0.5);


            if (arm.getCurrentPosition() < -2700){
                arm.setPower(1);
            }
            //Arm -> 45 = 1 degree, slidelen * cos((arm-1000))/45) = truelength
            //Slide -> 120 = 1 inch
            extendlen = slide.getCurrentPosition()/120 * Math.cos(0.0174533*(arm.getCurrentPosition()-1000)/45);
            if(extendlen < -9.5 && gamepad2.left_stick_y < 0){
                slide.setPower(0);
            }
            if(extendlen < -10){
                slide.setPower(1);
            }
            if(extendlen > 2){
                slide.setPower(1);
            }

            telemetry.addData("Arm", arm.getCurrentPosition());
            telemetry.addData("Slide", slide.getCurrentPosition());
            telemetry.addData("Extend", extendlen);


            //&& lastMovement + 0.5 < runtime.time()



            //if (gamepad1.left_bumper) {
            //    clawGrab.setPosition(0.9);
            //}
            //&& lastMovement + 0.5 < runtime.time()
            //if (gamepad1.right_bumper) {
            //    clawGrab.setPosition(0.5);
            //}

            if (gamepad2.right_bumper && lastMovement + 0.3f < getRuntime()){
                servoPos += 0.1f;
                lastMovement = (float)getRuntime();
            }
            if (gamepad2.left_bumper && lastMovement + 0.3f < getRuntime()){
                servoPos -= 0.1f;
                lastMovement = (float)getRuntime();
            }

            clawGrab.setPosition(servoPos);
            //if (servoPos > 0.8) servoPos = 0.8f;
            //if (servoPos < 0.5) servoPos = 0.5f;

            //double armVel = (armTarget > arm.getCurrentPosition() ? 1 : -1);

            //if (((double) slide.getCurrentPosition() / SLIDE_MOTOR_TICKS_PER_IN) * Math.cos((double) arm.getCurrentPosition() / ARM_MOTOR_TICKS_PER_RAD) > 40){
            //    if (gamepad2.left_stick_y < 0) {
            //        slideVel = 0;
            //    }
                //if (gamepad2.right_stick_y > 0) {
                  //  armVel = 0;
                 //   armTarget = arm.getCurrentPosition();
                //}
            //}
            //if ((gamepad2.left_stick_y < 0 && slide.getCurrentPosition() >= SLIDE_MOTOR_MAX) || (gamepad2.left_stick_y > 0 && slide.getCurrentPosition() <= 0)) {
            //    slideVel = 0;
            //}
            //if ((gamepad2.right_stick_y < 0 && arm.getCurrentPosition() >= ARM_MOTOR_MAX) || (gamepad2.right_stick_y > 0 && arm.getCurrentPosition() <= 0)) {
            //    armVel = 0;
            //    armTarget = arm.getCurrentPosition();
            //}



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
                        //clawPivotLeft.setPosition(1);
                        //clawPivotRight.setPosition(1);
                    } else {
                        //clawPivotLeft.setPosition(0);
                        //clawPivotRight.setPosition(0);
                    }
                }
            } else {
                bPressed = false;
            }



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
                speed = 0.7f;
            }
            else if (gamepad1.left_bumper) {
                speed = .3f;
            } else {
                speed = .5f;
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
