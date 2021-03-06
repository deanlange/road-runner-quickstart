package org.firstinspires.ftc.teamcode.drive.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.ICEMecanumDrive;
@Disabled

@Autonomous
public class BlueA extends LinearOpMode {
       protected Servo leftServo, rightServo, armServo;

       @Override
    public void runOpMode() {
           ICEMecanumDrive drive = new ICEMecanumDrive(hardwareMap);

           Pose2d startPose = new Pose2d(-62,55, Math.toRadians(0));

           drive.setPoseEstimate(startPose);

           Trajectory targetZoneA = drive.trajectoryBuilder(startPose)
                   .splineTo(new Vector2d(10, 55), Math.toRadians(0))
              .addDisplacementMarker(drive::releaseGoal)
               .build();

           Trajectory aToGoal = drive.trajectoryBuilder(targetZoneA.end(),true)
                   .splineTo(new Vector2d(-30,29), Math.toRadians(180))
                   .addDisplacementMarker(drive::grabGoal)
                   .build();

           Trajectory goalToA = drive.trajectoryBuilder(aToGoal.end())
                   .splineTo(new Vector2d(10,50), Math.toRadians(270))
                   .addDisplacementMarker(drive::releaseGoal)
                   .build();

           init();


           waitForStart();

           if(isStopRequested()) return;
           drive.grabGoal();
           drive.followTrajectory(targetZoneA);
           drive.arm(.5);
           sleep(1000);
           drive.stopArm();
           drive.followTrajectory(aToGoal);
           drive.followTrajectory(goalToA);


       }
}
