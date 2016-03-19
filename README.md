# CustomVolley

Đây là bộ volley tùy biến dựa trên volley và Gson chuẩn của google để có thể xử dụng một cách nhanh chóng và hiệu quả, đặc biệt là có thể lưu cache ngay cả với những website không cho lưu cache.

## Cấu hình trong Gradle

    dependencies {
    	compile 'com.mcxiaoke.volley:library:1.0.19'
    	compile 'com.google.code.gson:gson:2.4'
    	...
    }

## Cấu hình trong manifest

```xml
 <application
        android:name="com.nguyenvanquan7826.demo.volley.AppController"

		...
	</application>
</manifest>
```
Nếu bạn đang sử dụng một class Application được tùy biến vậy thì chỉ cần kế thừa từ Application đó thay vì  kế thừa android.app.Application
