# CustomVolley

The configuration strings for my project look like this

```xml
<manifest ...>
	<application android:name="com.activeandroid.app.Application" ...>

		...

		<meta-data android:name="AA_DB_NAME" android:value="Pickrand.db" />
		<meta-data android:name="AA_DB_VERSION" android:value="5" />

	</application>
</manifest>
```

Notice also that the application name points to the ActiveAndroid application class. **This step is required** for ActiveAndroid to work. If you already point to a custom Application class, just make that class a subclass of com.activeandroid.app.Application.

If you are using a custom Application class, just extend com.activeandroid.app.Application instead of android.app.Application

```java
public class MyApplication extends com.activeandroid.app.Application { ...
```

But what if you're already doing this to utilize another library? Simply initialize ActiveAndroid in the Application class. You may call ```ActiveAndroid.dispose();``` if you want to reset the framework for debugging purposes. (Don’t forget to call initialize again.)

```java
public class MyApplication extends SomeLibraryApplication {
	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
	}
}
```

If you want to build a database dynamically.You can use the configuration class.

```java
public class MyApplication extends SomeLibraryApplication {
	@Override
	public void onCreate() {
		super.onCreate();
        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("xxx.db").create();
		ActiveAndroid.initialize(dbConfiguration);
	}
}
