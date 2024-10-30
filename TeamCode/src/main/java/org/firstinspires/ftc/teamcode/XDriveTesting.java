package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.lang.Math;


@TeleOp (name = "testing")
public class XDriveTesting extends LinearOpMode {
    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;




    @Override
    public void runOpMode() throws InterruptedException {
        // Init code
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");


        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();

        // start code

        while (opModeIsActive()) {

            float powerFrontRight = 0;
            float powerFrontLeft = 0;
            float powerBackRight = 0;
            float powerBackLeft = 0;

            if (gamepad1.left_stick_y != 0){
                powerFrontRight -= gamepad1.left_stick_y;
                powerFrontLeft -= gamepad1.left_stick_y;
                powerBackRight -= gamepad1.left_stick_y;
                powerBackLeft -= gamepad1.left_stick_y;
            }

            if (gamepad1.left_stick_x != 0){
                powerFrontRight += -gamepad1.left_stick_x;
                powerBackRight += gamepad1.left_stick_x;
                powerFrontLeft += gamepad1.left_stick_x;
                powerBackLeft += -gamepad1.left_stick_x;
            }

            if (gamepad1.right_stick_x != 0){
                powerFrontRight += -gamepad1.left_stick_x;
                powerBackRight += -gamepad1.left_stick_x;
                powerFrontLeft += gamepad1.left_stick_x;
                powerFrontLeft += gamepad1.left_stick_x;

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
            frontRight.setPower(powerFrontRight);
            frontLeft.setPower(powerFrontLeft);
            backRight.setPower(powerBackRight);
            backLeft.setPower(powerBackLeft);
        }
    }
}
