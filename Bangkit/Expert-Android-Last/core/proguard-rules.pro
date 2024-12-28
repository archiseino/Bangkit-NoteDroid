# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn java.lang.invoke.StringConcatFactory

-keep class com.example.flickscout.core.** { *; }
-keepclassmembers class com.example.flickscout.core.** { *; }

-keep class com.example.flickscout.core.data.Resource { *; }
-keepclassmembers class com.example.flickscout.core.data.Resource { *; }

-keep class com.example.flickscout.core.data.Resource$* { *; }
-keepclassmembers class com.example.flickscout.core.data.Resource$* { *; }

-keep class com.example.flickscout.core.di.CoreModuleKt { *; }
-keepclassmembers class com.example.flickscout.core.di.CoreModuleKt { *; }

-keep class com.example.flickscout.core.domain.model.Movie { *; }
-keepclassmembers class com.example.flickscout.core.domain.model.Movie { *; }

-keep interface com.example.flickscout.core.domain.repository.IMovieRepository { *; }
-keepclassmembers interface com.example.flickscout.core.domain.repository.IMovieRepository { *; }

-keep class com.example.flickscout.core.domain.usecase.MovieInteractor { *; }
-keepclassmembers class com.example.flickscout.core.domain.usecase.MovieInteractor { *; }

-keep class com.example.flickscout.core.domain.usecase.MovieUseCase { *; }
-keepclassmembers class com.example.flickscout.core.domain.usecase.MovieUseCase { *; }

-keep class com.example.flickscout.core.ui.MoviesAdapter { *; }
-keepclassmembers class com.example.flickscout.core.ui.MoviesAdapter { *; }

-keep class com.example.flickscout.core.data.source.remote.response.MoviesResponse { *; }
-keepclassmembers class com.example.flickscout.core.data.source.remote.response.MoviesResponse { *; }

-keep class com.example.flickscout.core.data.source.remote.response.ResultsItem { *; }
-keepclassmembers class com.example.flickscout.core.data.source.remote.response.ResultsItem { *; }

