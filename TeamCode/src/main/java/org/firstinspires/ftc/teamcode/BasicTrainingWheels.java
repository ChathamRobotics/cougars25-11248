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

        slide.setTargetPosition(0);
        slide.setPower(1);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);

        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setTargetPosition(0);
        arm.setPower(1);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Arm", arm.getCurrentPosition());
        telemetry.addData("Slide", slide.getCurrentPosition());
        telemetry.update();

        //clawGrab.scaleRange(.3, .95);
        clawGrab.setPosition(.3);

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

            if (gamepad2.y){
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.x){
                slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
