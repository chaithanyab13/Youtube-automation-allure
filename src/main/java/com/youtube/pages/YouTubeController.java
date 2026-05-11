package com.youtube.pages;

import com.youtube.base.BasePage;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class YouTubeController extends BasePage {

    private static final Logger loggerManager = LogManager.getLogger(YouTubeController.class);

    public YouTubeController(WebDriver driver) {
        super(driver);
    }

    /* Opens a YouTube video given its URL
     * @param testUrl : URL of the YouTube video to open
     */
    public void openYouTubeVideo(String testUrl) {
        loggerManager.info("Navigating to YouTube video URL: {}", testUrl);
        driver.get(testUrl);
        driver.manage().window().maximize();
        waitForPageToLoad();
        loggerManager.info("Page loaded successfully");

        /* YouTube often displays a cookie consent dialog on the first visit.
         * This code attempts to locate and click the "Accept all" button to dismiss it.
         */
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Accept all')]")));
            acceptButton.click();
            /* A short wait to ensure the dialog is dismissed */
            waitForTimeout(2);
            loggerManager.info("Accepted cookie consent dialog");
        } catch (Exception e) {
            /* Cookie consent might not be present */
            loggerManager.info("No cookie consent dialog present");
        }
    }

    /* Plays the video by clicking the play button */
    public void playVideo() {
        try {
            /* This ensures that the play button is visible and interactable,
             * as YouTube's controls often appear only when the mouse is over the video area.
             */

            loggerManager.info("Attempting to play the video");
            WebElement playButton = driver.findElement(By.xpath("//button[@data-tooltip-title='Play (k)']"));

            /* Move mouse pointer to ensure controls are visible */
            moveMouseSmoothly(0,0,20,20,3);

            loggerManager.info("Play button {}", playButton);
            playButton.click();
            loggerManager.info("Play button clicked");

            /* A short wait to ensure the video starts playing
             * before any further actions are taken.
             */
            waitForTimeout(5);

        } catch (Exception e) {
            try {
                /* Sometimes the play button might not be directly accessible,
                 * so clicking on the video player area can also start playback.
                 */
                moveMouseSmoothly(0,0,20,20,3);
                loggerManager.info("Play button not found directly, clicking on video player area");
                WebElement videoPlayer = driver.findElement(By.xpath("//button[@data-title-no-tooltip='Play']"));
                videoPlayer.click();
                loggerManager.info("Video player area clicked to start playback");
            } catch (Exception ex) {
                moveMouseSmoothly(0,0,20,20,3);
                executeGenericJavaScript("const v=document.querySelector('video'); return v && !v.paused && v.currentTime > 0;", Void.class);
                loggerManager.info("clicked play button using JavaScriptMethod");
            }
        }
    }

    /* Pauses the video if it is currently playing */
    public void pauseVideo() {
        try {
            Allure.step("Ensure video is playing before pause test");
            loggerManager.info("Checking if video is playing");
            if (!isVideoPlaying()) {
                playVideo();
            }

            loggerManager.info("Attempting to pause the video");
            WebElement pauseButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-title-no-tooltip='Play']")));

            /* Move mouse pointer to ensure controls are visible */
            moveMouseSmoothly(0,0,20,20,3);
            if ("pause".equalsIgnoreCase(pauseButton.getAttribute("title"))) {
                pauseButton.click();
                waitForTimeout(5);
                loggerManager.info("Video paused successfully");
            }
        } catch (Exception e) {
            try {
                /* Sometimes the play button might not be directly accessible,
                 * so clicking on the video player area can also start playback.
                 */
                moveMouseSmoothly(0,0,20,20,3);
                loggerManager.info("Pause button not found directly because of error {}, clicking on video player area", e.getMessage());
                WebElement videoPlayer = driver.findElement(By.xpath("//button[@data-title-no-tooltip='Pause']"));
                videoPlayer.click();
                loggerManager.info("Video player area clicked to pause playback");
            } catch (Exception ex) {
                moveMouseSmoothly(0,0,20,20,3);
                loggerManager.info("Pause button not found directly because of error {}, clicking using JavaScript", ex.getMessage());
                executeGenericJavaScript("document.querySelector('video').pause();", Void.class);
                loggerManager.info("clicked pause button using JavaScriptMethod");
            }
        }
    }

    /* Seeks the video to a specific time in seconds
     * @param seconds : time in seconds to seek to
     */
    public void seekToTime(int seconds) {
        moveMouseSmoothly(0,0,20,20,3);

        loggerManager.info("Seeking video to {} seconds", seconds);
        executeGenericJavaScript("document.querySelector('video').currentTime = " + seconds, Void.class);
    }

    /* Sets the volume of the video player
     * @param volume : volume percentage (0 to 100)
     */
    public void setVolume(int volume) {
        /* Move mouse pointer to ensure controls are visible */
        moveMouseSmoothly(0,0,20,20,3);

        loggerManager.info("Setting video volume to {}%", volume);
        /* Convert volume percentage to a value between 0.0 and 1.0 */
        executeGenericJavaScript("document.querySelector('video').volume = " + (volume / 100.0), Void.class);
        moveMouseSmoothly(0,0,20,20,3);
    }

    /* Mutes the video player */
    public void muteVideo() {
        loggerManager.info("Current URL: {}", driver.getCurrentUrl());
        loggerManager.info("Inside iframe? Video count: {}", driver.findElements(By.tagName("video")).size());

        WebElement muteButton = null;
        try {
            moveMouseSmoothly(0,0,20,20,3);

            muteButton = driver.findElement(By.cssSelector("button.ytp-mute-button"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.ytp-mute-button")));
            String label = muteButton.getAttribute("aria-label");
            if (label != null && label.toLowerCase().contains("unmute")) {
                muteButton.click();
                loggerManager.info("Mute button clicked successfully");
            }
        } catch (Exception e) {
            try {
                moveMouseSmoothly(0,0,20,20,3);
                loggerManager.info("Mute button not found directly because of error {}, clicking using JavaScript", e.getMessage());
                executeGenericJavaScript("document.querySelector('.ytp-mute-button').click();", Void.class);
                loggerManager.info("Clicked mute button using JavaScriptMethod");
            } catch (Exception ex)
            {
                moveMouseSmoothly(0,0,20,20,3);
                loggerManager.info("Unable to click mute button using Java script because of error {}, clicking using KeyBoard key", ex.getMessage());
                Actions actions = new Actions(driver);
                actions.moveToElement(muteButton).click().sendKeys("m").perform();
                loggerManager.info("Clicked mute button using keyboard key");
            }
        }
    }

    /* Unmute the video if it is currently muted */
    public void unMuteVideo() {
        loggerManager.info("Current URL: {}", driver.getCurrentUrl());
        loggerManager.info("Inside iframe? Video count: {}", driver.findElements(By.tagName("video")).size());
        WebElement unMuteButton = null;
        try {
            /* Move mouse pointer to ensure controls are visible */
            moveMouseSmoothly(0,0,20,20,3);

            unMuteButton = driver.findElement(By.cssSelector("button.ytp-mute-button"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.ytp-mute-button")));
            String label = unMuteButton.getAttribute("aria-label");
            if (label != null && label.toLowerCase().contains("unmute")) {
                loggerManager.info("Unmuting the video using unmute button");
                unMuteButton.click();
                loggerManager.info("UnMute button clicked successfully");
            }
        } catch (Exception e) {
            try {
                moveMouseSmoothly(0,0,20,20,3);
                loggerManager.info("UnMute button not found directly because of error {}, clicking using Java Script", e.getMessage());
                executeGenericJavaScript("var v = document.querySelector('video'); if(v){ v.muted = false; }", Void.class);
                loggerManager.info("clicked unmute button using JavaScriptMethod");
            } catch (Exception ex){
                moveMouseSmoothly(0,0,20,20,3);
                loggerManager.info("Unable to click unmute button using Java Script because of error {}, clicking using keyboard key", ex.getMessage());
                Actions actions = new Actions(driver);
                actions.moveToElement(unMuteButton).click().sendKeys("m").perform();
                loggerManager.info("Clicked unmute button using keyboard key");
            }
        }
        driver.switchTo().defaultContent();
    }

    /* Toggles fullscreen mode for the video player */
    public void toggleFullScreen() {
        loggerManager.info("Toggling fullscreen mode for the video");
        WebElement fullscreenButton = driver.findElement(By.cssSelector("button.ytp-fullscreen-button"));
        fullscreenButton.click();
    }

    /* Returns the current playback time of the video in seconds, or null if video element is not found */
    public Double getCurrentTime() {
        /* Move mouse pointer to ensure controls are visible */
        moveMouseSmoothly(0,0,20,20,3);

        loggerManager.info("Retrieving current playback time of the video");
        return executeGenericJavaScript("var v = document.querySelector('video'); return v ? v.currentTime : null;", Double.class);
    }

    /* Returns the total duration of the video in seconds, or null if video element is not found */
    public Double getDuration() {
        /* Move mouse pointer to ensure controls are visible */
        moveMouseSmoothly(0,0,20,20,3);

        /* Retrieves the total duration of the video in seconds */
        loggerManager.info("Retrieving total duration of the video");
        Boolean isMetaDataLoaded = wait.until(d -> executeGenericJavaScript("var v = document.querySelector('video'); return v ? v.readyState >= 1 : false;", Boolean.class));
        loggerManager.info("Video metadata loaded: {}", isMetaDataLoaded);

        /* Ensure metadata is loaded before retrieving duration */
        if(!isMetaDataLoaded){
            throw new RuntimeException("Video metadata not loaded, cannot retrieve duration.");
        }
        Object duration = executeGenericJavaScript("var v = document.querySelector('video'); return v ? v.duration : null;", Object.class);
        loggerManager.info("Retrieved video duration: {}", duration);

        /* Ensure duration is not null before casting */
        if(duration == null){
            throw new RuntimeException("Video duration is null, cannot retrieve duration.");
        }

        loggerManager.info("Video duration retrieved successfully");
        return ((Number) duration).doubleValue();
    }

    /* Returns true if the video is playing, false if paused, and null if video element is not found */
    public Boolean isVideoPlaying() {
        /* Move mouse pointer to ensure controls are visible */
        moveMouseSmoothly(0,0,20,20,3);

        loggerManager.info("Checking if the video is currently playing");
        return executeGenericJavaScript("var v = document.querySelector('video'); return v ? !v.paused : null;", Boolean.class);
    }

    /* Returns true if the video is muted, false if unmuted, and null if video element is not found */
    public Boolean isVideoMutedUnMuted() {
        /* Move mouse pointer to ensure controls are visible */
        moveMouseSmoothly(0,0,20,20,3);

        loggerManager.info("Checking if the video is currently muted");
        Boolean volume = executeGenericJavaScript("var v = document.querySelector('video'); return v ? v.muted : null;", Boolean.class);
        loggerManager.info("Current mute status: {}", volume);

        if(!volume){
            Actions actions = new Actions(driver);
            actions.sendKeys("m").build().perform();
            waitForTimeout(15);
        }
        return executeGenericJavaScript("var v = document.querySelector('video'); return v ? v.muted : null;", Boolean.class);
    }

    public void removeAdsIfPresent() {
        try {
            loggerManager.info("Attempting to remove ads from the YouTube video page");
            executeGenericJavaScript("var adElements = document.querySelectorAll('.ad-container, .video-ads, .ytp-ad-module');" +
                    "adElements.forEach(function(ad) { ad.remove(); });", Void.class);
        } catch (Exception e) {
            /* Log the exception if ad removal fails */
            loggerManager.info("Failed to remove ads: {}", e.getMessage());
        }
    }

    /* Skips the advertisement if the skip ad button is present */
    public void skipAdIfPresent() {
        try {
            /* The skip ad button usually has the class 'ytp-skip-ad-button'
             * but it may vary, so you might need to adjust the selector based on actual YouTube DOM.
             */
            loggerManager.info("Checking for skip ad button");
            WebElement skipButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.ytp-skip-ad-button")));
            skipButton.click();
        } catch (Exception e) {
            /* You can log this event if needed */
            loggerManager.info("No skip ad button found, or unable to click it {}", e.getMessage());
        }
    }

    /* Waits for the page to fully load by checking the document ready state */
    private void waitForPageToLoad() {
        loggerManager.info("Waiting for page to fully load");
        wait.until(webDriver -> Objects.equals(executeGenericJavaScript("return document.readyState", String.class), "complete"));
        loggerManager.info("Page is fully loaded");
    }

    /* Returns the underlying WebDriver instance */
    public WebDriver getDriver() {
        loggerManager.info("Retrieving the underlying WebDriver instance");
        return driver;
    }

    /* Moves the mouse pointer smoothly from start coordinates to end coordinates in specified steps
     * @param startX : starting X coordinate
     * @param startY : starting Y coordinate
     * @param endX   : ending X coordinate
     * @param endY   : ending Y coordinate
     * @param steps  : number of steps to move the mouse
     */
    public void moveMouseSmoothly(int startX, int startY, int endX, int endY, int steps) {

        Actions actions = new Actions(driver);

        int xStep = (endX - startX) / steps;
        int yStep = (endY - startY) / steps;

        actions.moveByOffset(startX, startY).perform();

        for (int i = 0; i < steps; i++) {
            actions.moveByOffset(xStep, yStep).perform();
            try {
                waitForTimeout(5);
                loggerManager.info("Moving mouse to ({}, {})", startX + (i + 1) * xStep, startY + (i + 1) * yStep);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void moveMousePointer() {
        loggerManager.info("Moving mouse pointer to ensure video controls are visible");
        Actions actions = new Actions(driver);

        // Move mouse 100px right and 50px down from current position
        actions.moveByOffset(10, 10).perform();

    }

    public void moveMouseOverProgressBar() {
        loggerManager.info("Moving mouse pointer over the video progress bar to ensure controls are visible");
        Actions actions = new Actions(driver);

        WebElement progressBar = driver.findElement(By.cssSelector(".ytp-progress-bar"));

        // Hover and seek
        actions.moveToElement(progressBar)
                .moveByOffset(100, 0)
                .click()
                .perform();
    }

    public void dismissYouTubeMusicPopupIfPresent() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("""
            let buttons = document.querySelectorAll('button');
            for (let btn of buttons) {
                if (btn.innerText.trim().toLowerCase() === 'no thanks') {
                    btn.click();
                    return true;
                }
            }
            return false;""");
            loggerManager.info("YouTube Music popup dismissed (if present)");
        } catch (Exception e) {
            loggerManager.warn("YouTube Music popup not present or already dismissed");
        }
    }

    public void dismissPopupUsingWait() {
        try {
            WebElement noThanksBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//text()[contains(.,'No thanks')]]")
            ));

            noThanksBtn.click();
            loggerManager.info("Popup dismissed using Selenium wait");

        } catch (TimeoutException e) {
            loggerManager.info("Popup not shown");
        }
    }

}
