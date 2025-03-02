package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Basic Park Auton")
public class ParkAuton extends LinearOpMode {
    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;
    float speed = .5f;

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

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        resetRuntime();

        while (opModeIsActive() && getRuntime() < .5) {
            frontRight.setPower(speed);
            frontLeft.setPower(speed);
            backRight.setPower(speed);
            backLeft.setPower(speed);

            telemetry.addData("frontRight Power (real)", frontRight.getPower());
            telemetry.addData("frontLeft Power (real)", frontLeft.getPower());
            telemetry.addData("backRight Power (real)", backRight.getPower());
            telemetry.addData("backLeft Power (real)", backLeft.getPower());

            telemetry.update();
        }
    }
}
