# Android Toolbox (Core functions, Nano edition)

[![Twitter](https://img.shields.io/twitter/url/https/github.com/intari/AndroidToolboxCore.svg?style=social)](https://twitter.com/intent/tweet?text=Wow:&url=https%3A%2F%2Fgithub.com%2Fintari%2FAndroidToolboxCore)

[![GitHub license](https://img.shields.io/github/license/intari/AndroidToolboxCore.svg)](https://github.com/intari/AndroidToolboxCore/blob/master/LICENSE)
[![Release](https://jitpack.io/v/net.intari/AndroidToolboxCore.svg)](https://jitpack.io/#net.intari/AndroidToolboxCore)
<a href='https://travis-ci.org/intari/AndroidToolboxCore/builds'><img src='https://api.travis-ci.org/intari/AndroidToolboxCore.svg?branch=master'></a>
[![GitHub issues](https://img.shields.io/github/issues/intari/AndroidToolboxCore.svg)](https://github.com/intari/AndroidToolboxCore/issues)
[![GitHub forks](https://img.shields.io/github/forks/intari/AndroidToolboxCore.svg)](https://github.com/intari/AndroidToolboxCore/network)
[![GitHub stars](https://img.shields.io/github/stars/intari/AndroidToolboxCore.svg)](https://github.com/intari/AndroidToolboxCore/stargazers)

[![GitHub stars](https://img.shields.io/github/stars/intari/AndroidToolbox.svg)](https://github.com/intari/AndroidToolboxCore/stargazers)
[![Github commits (since latest release)](https://img.shields.io/github/commits-since/intari/AndroidToolboxCore/latest.svg)](https://github.com/intari/AndroidToolboxCore)
[![Read the Docs](https://img.shields.io/readthedocs/androidtoolboxcore.svg)](http://androidtoolboxcore.readthedocs.io/)


Nano edition doesn't even link to Yandex.AppMetrica/Amplitude. 
There will be separate library in future for common analytics logic. 

Tools I usually use in my android apps
Usage:

Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and:

```gradle
dependencies {
    compile 'net.intari:AndroidToolboxCore:{latest version}'
}
```


TODO: add more descriptions

TODO: note about libs we import  and requirements (Java 8) 


Kotlin helpers
===

Write-once read-only properties:
```kotlin
  val glSurfaceViewDelegate = WriteOnceVal<GLSurfaceView>()
  val glSurfaceView by glSurfaceViewDelegate
   ...
   glSurfaceViewDelegate.writeOnce(MyGLSurfaceView(this))
   // use glSurfaceView as regular glSurfaceView      
```
IllegalStateException will be thrown if it will actually be used before writeOnce call.
It's better than Delegates.notNull because we can have r/o property (val) instead of r/w property (var) this way.


Logging extensions for any types (using CustomLogger). class.simpleName will be used as TAG.
```kotlin
   "HelloWorld".logInfo("message")
   this.logException(ex,"message to go with exception")
   42.logVerbose("The Answer")
   "20!8".logError("Replace and press any key")
   1984.logWarning("Big brother watches you")
   2000.logInfo("error2k")
   2038.logVerbose("error2038")
   "amazon".logDebug("debug aws code")
```

View extensions
```kotlin
  
```

Android helpers 
===
BackgroundExecutionManager

Utils

CoroutineAndroidLoader
(must be called from from lifecycle provider like activity)
Android Lifecycle Components are used (so we can do things from https://hellsoft.se/simple-asynchronous-loading-with-kotlin-coroutines-f26408f97f46 ) like
(must be called from from lifecycle provider like activity)
```java 
load {
  loadBitmapFromMediaStore(imageId, imagesBaseUri)
} then {
  imageView.setImageBitmap(it)
}
```


