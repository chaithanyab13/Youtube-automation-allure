package com.youtube.tests;

import com.youtube.base.BaseTest;
import com.youtube.base.DriverManager;
import com.youtube.utils.AllureReportUtils;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;

import static com.youtube.utils.ScreenShotUtil.takeScreenshot;
import static com.youtube.utils.TestConstants.hyphens;

@Epic("YouTube Video Automation")
@Feature("Video Playback Controls")
@Owner("Automation Team")
@Severity(SeverityLevel.CRITICAL)
public class YouTubeVideoControlTest extends BaseTest {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    WebDriver driver = DriverManager.getDriver();

    @Test(priority = 1)
    @Story("Video Playback Functionality")
    @Description("Verify that YouTube video can be played successfully with proper controls")
    @Severity(SeverityLevel.BLOCKER)
    @TmsLink("YT-001")
    @Issue("AUT-123")
    public void testVideoPlayFunctionality(Method method) {
        loggerManager.info(hyphens);
        youtubeController.dismissPopupUsingWait();
        loggerManager.info("Starting test: testVideoPlayFunctionality at {}", java.time.LocalDateTime.now().format(formatter));
        Allure.step("Remove ads if present before starting the test");
        youtubeController.removeAdsIfPresent();

        loggerManager.info("Ads removed if present");
        Allure.step("Click play button on YouTube video");
        youtubeController.playVideo();

        loggerManager.info("Play button clicked");
        Allure.step("Skip advertisement if present");
        youtubeController.skipAdIfPresent();

        loggerManager.info("Advertisement skipped if present");
        Allure.step("Skip advertisement if present");
        youtubeController.skipAdIfPresent();

        loggerManager.info("Advertisement skipped if present");
        Allure.step("Click play button on YouTube video");
        youtubeController.playVideo();

        loggerManager.info("Play button clicked");
        Allure.step("Verify video playback state");
        boolean isPlaying = youtubeController.isVideoPlaying();
        loggerManager.info("Value of isPlaying: {}", isPlaying);
        Assert.assertTrue(isPlaying, "Video should be in playing state");

        loggerManager.info("Video is playing as expected");
        Allure.step("Capture video information for report");
        double currentTime = youtubeController.getCurrentTime();
        double duration = youtubeController.getDuration();
        AllureReportUtils.addVideoDurationInfo(currentTime, duration);

        loggerManager.info("Video duration info captured: Current Time - {}, Duration - {}", currentTime, duration);
        Allure.step("Take screenshot of playing video");
        takeScreenshot("video_playing_state", method.getName());

        loggerManager.info("Screenshot of playing video taken");
        Allure.step("Log playback information");
        Allure.addAttachment("Playback Info",
                "Video is playing at " + currentTime + " seconds of " + duration + " total duration");

        loggerManager.info("Playback information logged");
        loggerManager.info(hyphens);
    }

    @Test(priority = 2, dependsOnMethods = "testVideoPlayFunctionality")
    @Story("Video Pause Functionality")
    @Description("Verify that YouTube video can be paused successfully")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("YT-002")
    public void testVideoPauseFunctionality(Method method) {
        loggerManager.info(hyphens);
        loggerManager.info("Starting test: testVideoPauseFunctionality at {}", java.time.LocalDateTime.now().format(formatter));

        loggerManager.info("Video is playing, proceeding to pause");
        Allure.step("Pause the video using pause button");
        youtubeController.pauseVideo();

        loggerManager.info("Pause button clicked");
        Allure.step("Verify video is paused");
        boolean isPlaying = youtubeController.isVideoPlaying();
        loggerManager.info("Value of isPlaying {}", isPlaying);
        Assert.assertFalse(isPlaying, "Video should be in paused state");

        loggerManager.info("Video is paused as expected");
        Allure.step("Take screenshot of paused video");
        takeScreenshot("video_paused_state", method.getName());

        loggerManager.info("Screenshot of paused video taken");
        Allure.step("Capture current time when paused");
        double pausedTime = youtubeController.getCurrentTime();
        Allure.addAttachment("Pause Time", String.valueOf(pausedTime));

        loggerManager.info("Video paused at {} seconds", pausedTime);
        loggerManager.info(hyphens);
    }

    /* Flaky test disabled in CI environment */
    @Test(priority = 3, dependsOnMethods = "testVideoPauseFunctionality")
    @Story("Video Seeking Functionality")
    @Description("Verify that user can seek to different time positions in the video")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("YT-003")
    public void testVideoSeekFunctionality(Method method) {
        int seekTime = 45;

        loggerManager.info(hyphens);
        loggerManager.info("Starting test: testVideoSeekFunctionality at {}", java.time.LocalDateTime.now().format(formatter));
        Allure.step("Play video before seeking");
        youtubeController.playVideo();

        loggerManager.info("Video playback started");
        Allure.step("Seek to " + seekTime + " seconds in the video");
        youtubeController.seekToTime(seekTime);

        /* Wait for a short duration to allow seek operation to complete */
        waitForTimeout(5);

        loggerManager.info("Seeked to {} seconds", seekTime);
        Allure.step("Verify video seeked to correct position");
        double currentTime = youtubeController.getCurrentTime();
        Assert.assertTrue(currentTime >= seekTime - 5 && currentTime <= seekTime + 5,
                "Video should be seeked to approximately " + seekTime + " seconds");

        loggerManager.info("Video seek verified at {} seconds", currentTime);
        Allure.step("Take screenshot after seeking");
        takeScreenshot("video_seeked_to_" + seekTime + "s" , method.getName());

        loggerManager.info("Screenshot after seeking taken");
        Allure.step("Log seek operation details");
        Allure.addAttachment("Seek Operation",
                "Requested: " + seekTime + "s, Actual: " + currentTime + "s");

        loggerManager.info("Seek operation details logged");
        loggerManager.info(hyphens);
    }

    @Test(priority = 4, dependsOnMethods = "testVideoSeekFunctionality")
    @Story("Fullscreen Mode Functionality")
    @Description("Verify that video can be toggled between fullscreen and normal mode")
    @Severity(SeverityLevel.MINOR)
    @TmsLink("YT-005")
    public void testFullScreenFunctionality(Method method) {
        loggerManager.info(hyphens);
        loggerManager.info("Starting test: testFullScreenFunctionality at {}", java.time.LocalDateTime.now().format(formatter));
        Allure.step("Toggle to fullscreen mode");
        youtubeController.toggleFullScreen();

        loggerManager.info("Fullscreen button clicked");
        Allure.step("Wait for fullscreen transition");

        loggerManager.info("Waiting for fullscreen mode to activate");
        waitForTimeout(5);
        takeScreenshot("fullscreen_mode_activated", method.getName());
        Allure.addAttachment("Fullscreen", "Entered fullscreen mode");

        loggerManager.info("Fullscreen mode activated");
        Allure.step("Toggle back to normal mode");
        youtubeController.toggleFullScreen();

        loggerManager.info("Exited fullscreen mode");
        Allure.step("Wait for normal mode transition");

        loggerManager.info("Waiting for normal mode to activate");
        waitForTimeout(5);
        takeScreenshot("normal_mode_restored", method.getName());
        Allure.addAttachment("Normal Mode", "Returned to normal mode");

        loggerManager.info("Normal mode restored");
        loggerManager.info(hyphens);
    }

    @Test(priority = 5, dependsOnMethods = "testFullScreenFunctionality")
    @Story("Volume Control Functionality")
    @Description("Verify that video volume can be controlled (set volume) ")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("YT-004")
    public void testVolumeSetControlFunctionality(Method method) {
        loggerManager.info(hyphens);
        loggerManager.info("Starting test: testVolumeSetControlFunctionality at {}", java.time.LocalDateTime.now().format(formatter));

        Allure.step("Set volume to 50%");
        youtubeController.setVolume(50);
        Allure.addAttachment("Volume Set", "Volume set to 50%");
        takeScreenshot("volume_set_50%", method.getName());
        loggerManager.info("Volume set to 50%");

        Allure.step("Set volume to 20%");
        youtubeController.setVolume(20);
        Allure.addAttachment("Volume Set", "Volume set to 20%");
        takeScreenshot("volume_set_20%", method.getName());
        loggerManager.info("Volume set to 20%");

        Allure.step("Set volume to 80%");
        youtubeController.setVolume(80);
        Allure.addAttachment("Volume Set", "Volume set to 80%");
        takeScreenshot("volume_set_80%", method.getName());
        loggerManager.info("Volume set to 80%");

        loggerManager.info(hyphens);
    }

    @Test(priority = 6, dependsOnMethods = "testVolumeSetControlFunctionality")
    @Story("Video Mute Unmute Control Functionality")
    @Description("Verify that video mute/unmute can be controlled ")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("YT-004")
    public void testVideoMuteUnmuteFunctionality(Method method) {
        loggerManager.info(hyphens);
        loggerManager.info("Starting test: testVolumeMuteUnmuteFunctionality at {}", java.time.LocalDateTime.now().format(formatter));

        Allure.step("Mute the video");
        loggerManager.info("Muting the video");
        youtubeController.muteVideo();

        /* Wait for a short duration to allow mute operation to complete */
        waitForTimeout(5);
        takeScreenshot("video_muted", method.getName());
        loggerManager.info("Screenshot of muted video taken");

        Allure.step("Verify video is muted");
        boolean isMuted = youtubeController.isVideoMutedUnMuted();
        loggerManager.info("Value of isMuted {}", isMuted);
        Assert.assertTrue(isMuted, "Video should be muted");
        takeScreenshot("video_mute_verified", method.getName());
        loggerManager.info("Screenshot of muted video taken");

        Allure.step("Un-muting the video");
        loggerManager.info("Un-muting the video");
        youtubeController.unMuteVideo();

        /* Wait for a short duration to allow mute operation to complete */
        waitForTimeout(5);
        takeScreenshot("video_unmuted", method.getName());
        loggerManager.info("Screenshot of unmuted video taken");

        Allure.step("Verify video is un-muted");
        boolean isUnMuted = youtubeController.isVideoMutedUnMuted();
        loggerManager.info("Value of isUnMuted {}", isUnMuted);
        Assert.assertFalse(isUnMuted, "Video should be un-muted");
        takeScreenshot("video_un-muted", method.getName());
        loggerManager.info("Screenshot of un-muted video taken");

        loggerManager.info(hyphens);
    }

}