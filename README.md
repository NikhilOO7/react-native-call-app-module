
# react-native-react-native-call-app

## Getting started

`$ npm install react-native-react-native-call-app --save`

### Mostly automatic installation

`$ react-native link react-native-react-native-call-app`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-react-native-call-app` and add `RNReactNativeCallApp.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNReactNativeCallApp.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.nikhil.callapp.RNReactNativeCallAppPackage;` to the imports at the top of the file
  - Add `new RNReactNativeCallAppPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-react-native-call-app'
  	project(':react-native-react-native-call-app').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-react-native-call-app/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-react-native-call-app')
  	```


## Usage
```javascript
import RNReactNativeCallApp from 'react-native-react-native-call-app';

// TODO: What to do with the module?
RNReactNativeCallApp;
```
  