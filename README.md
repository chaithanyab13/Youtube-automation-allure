# YouTube Automation with Allure Reports

## This project automates YouTube video controls and generates detailed Allure reports.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Driver Manager will automatically handle browser drivers (Chrome, Firefox, Edge)
- Internet connection to access YouTube
- Allure Commandline for report generation (optional, can be installed via npm or downloaded from Allure website)

## Setup

1. Pass the test url either in  `testng.xml` or in `TestConstants.java`
2. All the dependencies are handled in `pom.xml`
3. All the execution logs are saved in `Logs` directory
4. All the screenshots and HTML reports are saved in `Reports` directory
5. Each browser has its own options manager class to handle browser-specific configurations and capabilities, ensuring that the tests run smoothly across different browsers. The `DriverFactory` class is responsible for initializing the WebDriver instances based on the specified browser type, allowing for easy scalability and maintenance of the test suite.
6. The `DriverFactory.java` class is responsible for initializing the WebDriver instances based on the specified browser type, allowing for easy scalability and maintenance of the test suite. 
7. The `BaseTest.java` class serves as a foundation for all test classes, providing common setup and tear down methods to ensure consistent test execution and resource management across the entire test suite.

## Running Tests

```bash
# Run tests
mvn clean test

# Generate and serve Allure report
mvn allure:serve

# Generate report without serving
mvn allure:report

# Open the generated report with browser
open Reports/YouTubeReport_2026-02-16_21-49-21.html
```

# Project structure
```bash
youtube-automation-allure/
├── Allure
├── Logs
│   ├── automation-2026-02-09.log.gz
│   └── automation.log
├── README.md
├── Reports
│   ├── 2026-02-16_21-49-21.html
│   ├── screenshot_2026-02-16_21-51-28_video_playing_state_testVideoPlayFunctionality.png
│   ├── screenshot_2026-02-16_21-51-30_Test_case_passed_testVideoPlayFunctionality.png
│   ├── screenshot_2026-02-16_21-51-32_null_testVideoPlayFunctionality.png
│   ├── screenshot_2026-02-16_21-51-35_after_test_execution_testVideoPlayFunctionality.png
│   ├── screenshot_2026-02-16_21-52-12_video_paused_state_testVideoPauseFunctionality.png
│   ├── screenshot_2026-02-16_21-52-15_Test_case_passed_testVideoPauseFunctionality.png
│   ├── screenshot_2026-02-16_21-52-16_null_testVideoPauseFunctionality.png
│   ├── screenshot_2026-02-16_21-52-18_after_test_execution_testVideoPauseFunctionality.png
│   ├── screenshot_2026-02-16_21-52-23_video_seeked_to_45s_testVideoSeekFunctionality.png
│   ├── screenshot_2026-02-16_21-52-25_Test_case_passed_testVideoSeekFunctionality.png
│   ├── screenshot_2026-02-16_21-52-27_null_testVideoSeekFunctionality.png
│   ├── screenshot_2026-02-16_21-52-30_after_test_execution_testVideoSeekFunctionality.png
│   ├── screenshot_2026-02-16_21-52-32_fullscreen_mode_activated_testFullScreenFunctionality.png
│   ├── screenshot_2026-02-16_21-52-34_normal_mode_restored_testFullScreenFunctionality.png
│   ├── screenshot_2026-02-16_21-52-35_Test_case_passed_testFullScreenFunctionality.png
│   ├── screenshot_2026-02-16_21-52-37_null_testFullScreenFunctionality.png
│   ├── screenshot_2026-02-16_21-52-39_after_test_execution_testFullScreenFunctionality.png
│   ├── screenshot_2026-02-16_21-52-44_volume_set_50%_testVolumeSetControlFunctionality.png
│   ├── screenshot_2026-02-16_21-52-49_volume_set_20%_testVolumeSetControlFunctionality.png
│   ├── screenshot_2026-02-16_21-52-53_volume_set_80%_testVolumeSetControlFunctionality.png
│   ├── screenshot_2026-02-16_21-52-55_Test_case_passed_testVolumeSetControlFunctionality.png
│   ├── screenshot_2026-02-16_21-52-56_null_testVolumeSetControlFunctionality.png
│   ├── screenshot_2026-02-16_21-52-59_after_test_execution_testVolumeSetControlFunctionality.png
│   ├── screenshot_2026-02-16_21-53-08_video_muted_testVideoMuteUnmuteFunctionality.png
│   ├── screenshot_2026-02-16_21-53-12_video_mute_verified_testVideoMuteUnmuteFunctionality.png
│   ├── screenshot_2026-02-16_21-53-21_video_unmuted_testVideoMuteUnmuteFunctionality.png
│   ├── screenshot_2026-02-16_21-53-25_video_un-muted_testVideoMuteUnmuteFunctionality.png
│   ├── screenshot_2026-02-16_21-53-27_Test_case_passed_testVideoMuteUnmuteFunctionality.png
│   ├── screenshot_2026-02-16_21-53-29_null_testVideoMuteUnmuteFunctionality.png
│   └── screenshot_2026-02-16_21-53-32_after_test_execution_testVideoMuteUnmuteFunctionality.png
├── jenkins.pipeline
├── pom.xml
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── youtube
│   │               ├── base
│   │               │   ├── BasePage.java
│   │               │   ├── ChromeOptionsManager.java
│   │               │   ├── CommonBrowserOptions.java
│   │               │   ├── DriverFactory.java
│   │               │   ├── EdgeOptionsManager.java
│   │               │   └── FirefoxOptionsManager.java
│   │               ├── pages
│   │               │   └── YouTubeController.java
│   │               └── utils
│   │                   ├── AllureReportUtils.java
│   │                   ├── ExtentReportManager.java
│   │                   ├── LoggerManager.java
│   │                   └── TestConstants.java
│   └── test
│       ├── java
│       │   └── com
│       │       └── youtube
│       │           ├── Listeners
│       │           │   ├── ExtentTestListener.java
│       │           │   └── TestListener.java
│       │           ├── base
│       │           │   ├── BaseTest.java
│       │           │   └── DriverManager.java
│       │           ├── tests
│       │           │   └── YouTubeVideoControlTest.java
│       │           └── utils
│       │               └── ScreenShotUtil.java
│       └── resources
│           ├── allure-categories.json
│           ├── allure.properties
│           ├── extent-config.xml
│           └── log4j2.xml
├── target
│   ├── allure-results
│   │   ├── 0e732e2f-9509-4697-ae3b-140cd228f3c9-container.json
│   │   ├── 17526cda-6b0e-4c8d-9f75-110cfb39caba-container.json
│   │   ├── 1d4edb74-7f35-4e56-9134-e7249f8cb98c-attachment.txt
│   │   ├── 1f71fbff-6357-4997-95bb-24da29b418c7-container.json
│   │   ├── 1fed3ac2-87f6-497a-b6c2-9b0631b741ca-attachment
│   │   ├── 20886f3c-bc87-4b3c-b64e-210b4d136459-attachment.txt
│   │   ├── 27618037-f65c-486f-8385-6862d2eb1145-attachment
│   │   ├── 2783834d-f94f-4ee4-b4be-a2353122b7af-attachment
│   │   ├── 28bc93f3-511a-471d-a617-bbf7e151ecce-attachment
│   │   ├── 39a5d393-bb6f-41ef-a434-16a380c4f263-attachment
│   │   ├── 3b4bc0a9-7936-4837-9a22-dfed1f647a05-attachment.txt
│   │   ├── 4377de8c-ef22-4c02-a728-1176cc083898-result.json
│   │   ├── 43ed3c0c-7be0-430f-a0ae-0025e98b7740-result.json
│   │   ├── 48f037b2-cd21-4585-82e0-d59d50058184-attachment
│   │   ├── 5d8cc0ee-eb95-408d-b671-664eb5a22180-attachment.txt
│   │   ├── 5fc44c43-7713-4aa7-85a2-080d560a61e0-attachment
│   │   ├── 63749efd-4864-46ae-b1be-c58aa0e1df9f-attachment
│   │   ├── 650dd164-f557-4167-bece-e476daecf4a1-attachment
│   │   ├── 702dbfdf-4250-450b-b1e4-e1dd654fb0db-attachment
│   │   ├── 74c53239-d27d-4522-81cd-be4fbb3fc60c-attachment.txt
│   │   ├── 76a0e031-442c-48a6-9239-f57d7786c3f8-attachment
│   │   ├── 7879107c-27d5-46e5-a0d4-bd9f758e8fb5-container.json
│   │   ├── 7a914941-ce88-47f0-b29a-cb98b86be98c-container.json
│   │   ├── 7c4bfdc1-81c7-44e1-9084-4b9c89b09447-attachment.txt
│   │   ├── 853f56ee-01db-4497-aa06-016da3662b74-attachment
│   │   ├── 8908e177-d0cd-42d3-92ed-0777d559ed69-attachment
│   │   ├── 89acdcbe-a99b-45a2-b100-d35b6eb17ea2-attachment
│   │   ├── 8cce82fa-b20f-4ecb-b7d5-c344c6712c71-attachment
│   │   ├── 9243dc46-048c-4964-9e00-0f0e7bb420d3-attachment
│   │   ├── 9d09fe38-7886-4d4e-917c-a0afe315b22e-attachment
│   │   ├── a32b336c-ddf4-45b2-bf18-7233ad9841db-attachment
│   │   ├── aa2ce25e-e7f1-41d2-bb54-1c972d665661-attachment.txt
│   │   ├── aa9f9b69-e0e6-43c0-adce-b986843f075f-result.json
│   │   ├── af2eb696-d38c-41a1-89c5-96330bdf58d8-container.json
│   │   ├── b1513060-5218-466c-bae4-29a903965368-attachment.txt
│   │   ├── b254fa91-50c5-4710-8899-01bc8ac91d4c-result.json
│   │   ├── ba3b0090-5593-4fb0-9b74-7dc091e42dac-container.json
│   │   ├── bbab2bc1-6c6c-41a6-af03-cf50e859469b-attachment
│   │   ├── bc3255da-b815-4087-9ac6-246cf4a64886-attachment
│   │   ├── c453fff9-7d3d-44ac-bc89-3cfd23b072b2-attachment
│   │   ├── c87a2c06-3d27-44fa-ba4b-1fb17e14a98d-attachment
│   │   ├── d001b3a9-b4f4-4df6-9f02-db13fa7fab84-attachment
│   │   ├── d14c4776-f86f-42db-8e2e-31574fafd076-container.json
│   │   ├── d58abffc-2290-4a96-9dc0-f16d760a0948-attachment
│   │   ├── d7a5c44a-634c-4722-91b8-39b36010fc97-result.json
│   │   ├── db445348-7b0c-490d-bd7d-3a10af9b32b4-result.json
│   │   ├── dfc96a25-afc6-450c-b96d-89166333d6a5-attachment
│   │   ├── e1a30182-400e-4722-b5f0-1fafb5f75539-attachment
│   │   ├── e32a9112-0f3c-4675-90fb-6659871a7268-attachment.txt
│   │   ├── e6234447-20cd-4d84-969d-ce5a304d51fb-attachment
│   │   ├── e931c87c-3348-459e-9ddb-e202f2da2e89-attachment
│   │   ├── eb586135-4589-45fe-86ec-73be08b38a64-attachment.txt
│   │   ├── f2c23a4c-8366-4e89-9327-a1e5070dd4d1-attachment
│   │   ├── f7d57699-2310-431d-9e88-b495a659f746-attachment
│   │   ├── fb964068-03c9-48e7-9040-a8178ae08ab1-container.json
│   │   └── ff5be197-e56a-448c-891d-7d1d86a1909b-attachment.txt
│   ├── classes
│   │   └── com
│   │       └── youtube
│   │           ├── base
│   │           │   ├── BasePage.class
│   │           │   ├── ChromeOptionsManager.class
│   │           │   ├── CommonBrowserOptions.class
│   │           │   ├── DriverFactory.class
│   │           │   ├── EdgeOptionsManager.class
│   │           │   └── FirefoxOptionsManager.class
│   │           ├── pages
│   │           │   └── YouTubeController.class
│   │           └── utils
│   │               ├── AllureReportUtils.class
│   │               ├── ExtentReportManager.class
│   │               ├── LoggerManager.class
│   │               └── TestConstants.class
│   ├── generated-sources
│   │   └── annotations
│   ├── generated-test-sources
│   │   └── test-annotations
│   ├── maven-status
│   │   └── maven-compiler-plugin
│   │       ├── compile
│   │       │   └── default-compile
│   │       │       ├── createdFiles.lst
│   │       │       └── inputFiles.lst
│   │       └── testCompile
│   │           └── default-testCompile
│   │               ├── createdFiles.lst
│   │               └── inputFiles.lst
│   ├── surefire-reports
│   │   ├── Surefire suite
│   │   │   ├── Surefire test.html
│   │   │   ├── Surefire test.xml
│   │   │   └── testng-failed.xml
│   │   ├── TEST-TestSuite.xml
│   │   ├── TestSuite.txt
│   │   ├── bullet_point.png
│   │   ├── collapseall.gif
│   │   ├── emailable-report.html
│   │   ├── failed.png
│   │   ├── index.html
│   │   ├── jquery-3.6.0.min.js
│   │   ├── junitreports
│   │   │   └── TEST-com.youtube.tests.YouTubeVideoControlTest.xml
│   │   ├── navigator-bullet.png
│   │   ├── passed.png
│   │   ├── skipped.png
│   │   ├── testng-failed.xml
│   │   ├── testng-reports.css
│   │   ├── testng-reports.js
│   │   ├── testng-reports1.css
│   │   ├── testng-reports2.js
│   │   └── testng-results.xml
│   └── test-classes
│       ├── allure-categories.json
│       ├── allure.properties
│       ├── com
│       │   └── youtube
│       │       ├── Listeners
│       │       │   ├── ExtentTestListener.class
│       │       │   └── TestListener.class
│       │       ├── base
│       │       │   ├── BaseTest.class
│       │       │   └── DriverManager.class
│       │       ├── tests
│       │       │   └── YouTubeVideoControlTest.class
│       │       └── utils
│       │           └── ScreenShotUtil.class
│       ├── extent-config.xml
│       └── log4j2.xml
└── testng.xml

```
