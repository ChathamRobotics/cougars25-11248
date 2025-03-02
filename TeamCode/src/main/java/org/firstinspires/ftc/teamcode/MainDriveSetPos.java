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


@TeleOp (name = "Main Drive Set Pos")
public class MainDriveSetPos extends LinearOpMode {
    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;
    float speed = .7f;

    DcMotorEx slide;
    int slideTarget = 0;
    DcMotorEx arm;
    int armTarget = 0;

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

        frontLeft = hardwareMap.get(DcMotorEx.class, "leftFront");
        frontRight = hardwareMap.get(DcMotorEx.class, "rightFront");
        backLeft = hardwareMap.get(DcMotorEx.class, "leftBack");
        backRight = hardwareMap.get(DcMotorEx.class, "rightBack");
        //clawPivotRight = hardwareMap.get(Servo.class, "clawPivotRight");
        //clawPivotLeft = hardwareMap.get(Servo.class, "clawPivotLeft");
        clawGrab = hardwareMap.get(Servo.class, "clawGrab");
        slide = hardwareMap.get(DcMotorEx.class, "slide");
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slide.setTargetPosition(0);
        slide.setPower(0);
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

        /*
        while (arm.getCurrentPosition() < 1400){
            arm.setPower(0.45);
            telemetry.addData("Arm", arm.getCurrentPosition());
            telemetry.addData("Slide", slide.getCurrentPosition());
            telemetry.update();
        }*/ 

        telemetry.addData("Arm", arm.getCurrentPosition());
        telemetry.addData("Slide", slide.getCurrentPosition());
        telemetry.update();

        //clawGrab.scaleRange(.3, .95);
        clawGrab.setPosition(.3);

        waitForStart();



        // start code
        float servoPos = 0.3f;
        float lastMovement = 0;
        boolean editMode = false;

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



            if (gamepad2.left_stick_y * (slideTarget - slide.getCurrentPosition()) > 0) slideTarget = slide.getCurrentPosition();
            slideTarget += (int) (-gamepad2.left_stick_y*50);
            if (slideTarget > SLIDE_MOTOR_MAX) {
                slideTarget = (int) SLIDE_MOTOR_MAX;
            }
            if (slideTarget < 0 && !gamepad2.a) {
                slideTarget = 0;
            }

            if (gamepad2.right_stick_y * (armTarget - arm.getCurrentPosition()) > 0) armTarget = arm.getCurrentPosition();
            if (gamepad2.left_trigger > 0.1) {
                armTarget += (int) (-gamepad2.right_stick_y*20);
            } else {
                armTarget += (int) (-gamepad2.right_stick_y*50);
            }
            if (armTarget > ARM_MOTOR_MAX) {
                armTarget = (int) ARM_MOTOR_MAX;
            }
            if (armTarget < 0 && !gamepad2.a) {
                armTarget = 0;
            }

            if (gamepad2.a) {
                editMode = true;
            }
            if (gamepad2.y || (editMode && !gamepad2.a)){
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armTarget = 0;
            }
            if (gamepad2.x || (editMode && !gamepad2.a)){
                slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slideTarget = 0;
                editMode = false;
            }

            double extendTarget = ((float) slideTarget / SLIDE_MOTOR_TICKS_PER_IN) * Math.cos(((float) armTarget-1000) / ARM_MOTOR_TICKS_PER_RAD);
            if(extendTarget > 16){
                slideTarget = (int) ((16 / (Math.cos(((float) armTarget-1000) / ARM_MOTOR_TICKS_PER_RAD))) * SLIDE_MOTOR_TICKS_PER_IN);
            }
            telemetry.addData("extendTarget", extendTarget);
            telemetry.addData("length", (float) slideTarget / SLIDE_MOTOR_TICKS_PER_IN);
            telemetry.addData("angle", Math.cos(((float) armTarget-1000) / ARM_MOTOR_TICKS_PER_RAD));
            telemetry.addData("armTargetAngleTHingy", ((float) armTarget-1000) / ARM_MOTOR_TICKS_PER_RAD);
            telemetry.addData("#", ARM_MOTOR_TICKS_PER_RAD);

            slide.setTargetPosition(slideTarget);
            slide.setPower(1);

            arm.setTargetPosition(armTarget);
            arm.setPower(1);




            telemetry.addData("Arm", arm.getCurrentPosition());
            telemetry.addData("Arm Target", armTarget);
            telemetry.addData("Arm Power", arm.getPower());
            telemetry.addData("Slide", slide.getCurrentPosition());
            telemetry.addData("Slide Target", slideTarget);
            telemetry.addData("Slide Power", slide.getPower());


            if (gamepad2.right_bumper && lastMovement + 0.3f < getRuntime()){
                servoPos += 0.1f;
                lastMovement = (float)getRuntime();
            }
            if (gamepad2.left_bumper && lastMovement + 0.3f < getRuntime()){
                servoPos -= 0.1f;
                lastMovement = (float)getRuntime();
            }

            clawGrab.setPosition(servoPos);



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
                speed = 1f;
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

            telemetry.addData("frontRight pos", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft pos", frontLeft.getCurrentPosition());
            telemetry.addData("backRight pos", backRight.getCurrentPosition());
            telemetry.addData("backLeft pos", backLeft.getCurrentPosition());

            telemetry.update();
        }
    }
}
