# Customer App

QuizDuell is a Node.js web application built with Express that allows users to play an interactive quiz game.

## Haverland suggested improvements
:heavy_check_mark: -> implemented
- Save avatar inside JWT instead of using middleware :heavy_check_mark:
- Design a feature to provide feedback on the website indicating whether a question has been answered correctly or incorrectly. :heavy_check_mark:
- NOTICE: Fixed Springer achievement :heavy_check_mark:

## Features

- Generating QR-Codes
- View Stores

## Installation

1. Download Android Studio Giraffe
2. Clone the repository: `git clone https://github.com/raniamo8/CustomerApp.git`


## Configuration

- Create a virtual device in Android Studio

## Usage

1. Run the application

## Folder Structure

- Change the view to `Android`

```
app

├───sampledate
│
├───manifestes
│   └───AndroidManifest.xml
│
├───java
│   └───customerapp
│       ├───activities.customerapp
│       │   ├───MainActivity.java
│       │   └───WelcomeActivity.java
│       │
│       ├───adapters.customerapp
│       │   ├───QRCodeAdapter.java
│       │   └───StoreDetailsAdapter.java
│       │
│       ├───fragments.customerapp
│       │   ├───CodeFragment.java
│       │   ├───ExploreFragment.java
│       │   ├───OwnerInformationFragment.java
│       │   ├───QRCodeDisplayFragment.java
│       │   ├───QRCodeListFragment.java
│       │   ├───SettingFragment.java
│       │   ├───StoreDetailsFragment.java
│       │   ├───WelcomeFragmentOne.java
│       │   └───WelcomeFragmentTwo.java
│       │
│       ├───models.customerapp
│       │   ├───Address.java
│       │   ├───AddressBook.java
│       │   ├───EmojiExcludeFilter.java
│       │   ├───FragmentManagerHelper.java
│       │   ├───Recipient.java
│       │   ├───StoreDetails.java
│       │   └───SwipeToDeleteCallback.java
│       │
│       ├───customerapp.androidtest.customerapp
│       │   ├───IntroUITest.java
│       │   ├───QRCodeUITest.java
│       │   ├───SettingUITest.java
│       │   └───UITestSuite.java
│       │
│       └───customerapp.unittest.customerapp
│           ├───AddressBookUnitTest.java
│           ├───QRCodePerformanceUnitTest.java
│           ├───RecipientUnitTest.java
│           └───UnitTestSuite.java
│
├───res
│   ├───anim
│   │   ├───slide_in.xml
│   │   ├───slide_in_right.xml
│   │   └───slide_out.xml
│   │
│   ├───drawable
│   │   ├───VectorAssets
│   │   └───Images
│   │
│   ├───layout
│   │   ├───activity_welcome.xml
│   │   ├───activitymain.xml
│   │   ├───circular_image_layout.xml
│   │   ├───fragment_code.xml
│   │   ├───fragment_explore.xml
│   │   ├───fragment_owner_information.xml
│   │   ├───fragment_qr_code_display.xml
│   │   ├───fragment_qr_code_list.xml
│   │   ├───fragment_setting.xml
│   │   ├───fragment_store_details.xml
│   │   ├───fragment_welcome_one.xml
│   │   ├───fragment_welcome_two.xml
│   │   ├───item_qr_code.xml
│   │   ├───item_store_details.xml
│   │   └───toolbar_layout.xml
│   │
│   ├───menu
│   │   ├───bottom_nav_menu.xml
│   │   └───menu_qr_code_list.xml
│   │
│   └───values
│       ├───colors.xml
│       ├───dimens
│       │   ├───google_maps_api.xml
│       │   ├───ic_launcher_background.xml
│       │   └───strings.xml
│       │
│       └───styles
│           └───styles.xml
│
|   .gitignore
|   build.gradle
|   gradle.properties
|   gradlew
|   gradlew.bat
|   local.properties
|   README.md
|   settings.gradle


```

