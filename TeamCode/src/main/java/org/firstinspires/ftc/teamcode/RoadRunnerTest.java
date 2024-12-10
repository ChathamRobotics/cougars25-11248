package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.BaseMecanumDrive;

public class RoadRunnerTest extends LinearOpMode {
    private BaseMecanumDrive robot = new BaseMecanumDrive(hardwareMap, new Pose2d(0,0, 0));
    @Override
    public void runOpMode() {

    }
}
