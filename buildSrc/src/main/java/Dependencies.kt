
import Versions.androidGradleVersion
import Versions.daggerVersion
import Versions.kotlinVersion
import Versions.okHttpVersion
import Versions.retrofitVersion
import Versions.supportLibraryVersion

object Versions {
  val androidGradleVersion = "3.1.1"
  val kotlinVersion = "1.2.31"
  val supportLibraryVersion = "27.1.1"
  val daggerVersion = "2.15"
  val okHttpVersion = "3.10.0"
  val retrofitVersion = "2.4.0"
}

@Suppress("unused")
object Deps {
  // classpath
  val gradle = "com.android.tools.build:gradle:${androidGradleVersion}"
  val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}"
  val kotlinAndroidExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:${kotlinVersion}"
  val gradleAndroidCommandPlugin = "com.novoda:gradle-android-command-plugin:2.0.1"
  val buildScanPlugin = "com.gradle:build-scan-plugin:1.13.1"
  val dexcountGradlePlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:0.8.2"
  val gradleAndroidApkSizePlugin = "com.vanniktech:gradle-android-apk-size-plugin:0.4.0"
  val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.17.0"

  // implementation
  val design = "com.android.support:design:${supportLibraryVersion}"
  val appcompatv7 = "com.android.support:appcompat-v7:${supportLibraryVersion}"
  val supportv4 = "com.android.support:support-v4:${supportLibraryVersion}"
  val recyclerviewv7 = "com.android.support:recyclerview-v7:${supportLibraryVersion}"
  val cardviewv7 = "com.android.support:cardview-v7:${supportLibraryVersion}"
  val supportAnnotations = "com.android.support:support-annotations:${supportLibraryVersion}"
  val constraintLayout = "com.android.support.constraint:constraint-layout:1.0.2"
  val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}"
  val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}"
  val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.0.2"
  val rxJava = "io.reactivex.rxjava2:rxjava:2.1.12"
  val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.0.2"
  val rxBinding = "com.jakewharton.rxbinding2:rxbinding-kotlin:2.1.1"
  val supportDesignRxBinding = "com.jakewharton.rxbinding2:rxbinding-design-kotlin:2.1.1"
  val dagger = "com.google.dagger:dagger:${daggerVersion}"
  val daggerAndroid = "com.google.dagger:dagger-android:${daggerVersion}"
  val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${daggerVersion}"
  val okhttp = "com.squareup.okhttp3:okhttp:${okHttpVersion}"
  val retrofit = "com.squareup.retrofit2:retrofit:${retrofitVersion}"
  val converterMoshi = "com.squareup.retrofit2:converter-moshi:${retrofitVersion}"
  val moshiAdapters = "com.squareup.moshi:moshi-adapters:1.5.0"
  val moshiKotlin = "com.squareup.moshi:moshi-kotlin:1.5.0"
  val adapterRxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${retrofitVersion}"
  val groupie = "com.xwray:groupie:2.0.3"
  val groupieExtensions = "com.xwray:groupie-kotlin-android-extensions:2.0.3"
  val room =  "android.arch.persistence.room:runtime:1.1.0"
  val roomRxJava = "android.arch.persistence.room:rxjava2:1.1.0"
  val ktLint = "com.github.shyiko:ktlint:0.23.1"

  // kapt
  val daggerCompiler = "com.google.dagger:dagger-compiler:${daggerVersion}"
  val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${daggerVersion}"
  val roomAnnotationProcessor = "android.arch.persistence.room:compiler:1.1.0"

  // androidTestUtil
  val orchestrator = "com.android.support.test:orchestrator:1.0.1"

  // testImplementation
  val junit = "junit:junit:4.12"
  val mockitoInline = "org.mockito:mockito-inline:2.18.0"
  val mockitoKotlin = "com.nhaarman:mockito-kotlin-kt1.1:1.5.0"
  val mockwebserver = "com.squareup.okhttp3:mockwebserver:${okHttpVersion}"
}
